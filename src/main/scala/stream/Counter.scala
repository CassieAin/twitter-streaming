package stream

import akka.actor.ActorSystem
import akka.event.Logging
import akka.stream._
import akka.stream.scaladsl._
import akka.{Done, NotUsed}
import db.Databases
import models._
import twitter4j.{Status, StatusAdapter}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class Counter extends StatusAdapter with Databases{
  implicit val system = ActorSystem("TweetsExtractor")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  implicit val LoggingAdapter =
    Logging(system, classOf[Counter])

  val overflowStrategy = OverflowStrategy.backpressure
  val bufferSize = 1000
  val statusSource = Source.queue[Status](
    bufferSize,
    overflowStrategy
  )

  val flow: Flow[Status, String, NotUsed] = Flow[Status].map(status => status.getText)
  val sink: Sink[Any, Future[Done]] = Sink.foreach(println)

  val insertFlow: Flow[Status, Tweet, NotUsed] =
    Flow[Status].map(status => Tweet(status.getId, status.getUser.getId, status.getText, status.getLang,
      status.getFavoriteCount, status.getRetweetCount))
//status.getPlace.getName,
  val insertSink: Sink[Tweet, Future[Done]] = Sink.foreach(tweetRepository.create)

  val graph = statusSource via flow to sink
  val queuePrint = graph.run()

  val insertGraph = statusSource via insertFlow to insertSink
//  val insertSink = Source[Status] foreach tweetRepository.create(_)
//  val insertGraph = statusSource runWith insertSink
  val queueInsert = insertGraph.run()

  override def onStatus(status: Status) = {
    Await.result(queueInsert.offer(status), Duration.Inf)
    //    println(status.getText())
  }
}
