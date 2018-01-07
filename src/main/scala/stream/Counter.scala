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
import slick.jdbc.PostgresProfile.api._

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

  val createTweetTable = TweetTable.table.schema.create
  val createAuthorTable = AuthorTable.table.schema.create
  val createLocationTable = LocationTable.table.schema.create

  val sink: Sink[Any, Future[Done]] = Sink.foreach(println)
  val flow: Flow[Status, String, NotUsed] = Flow[Status].map(status => status.getText)
  val graph = statusSource via flow to sink
  val queue = graph.run()

//  val insertSink = Source[Status] foreach tweetRepository.create(_)
//  val insertGraph = statusSource runWith insertSink

  override def onStatus(status: Status) = {
    Await.result(queue.offer(status), Duration.Inf)
    //    println(status.getText())
  }
}
