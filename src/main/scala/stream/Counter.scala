package stream

import akka.actor.ActorSystem
import akka.event.Logging
import akka.stream._
import akka.stream.scaladsl._
import twitter4j.{Status, StatusAdapter}

class Counter extends StatusAdapter{

  implicit val system = ActorSystem("EmojiTrends")
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

  val graph = statusSource
    .log("QUEUED", {status => status.getText})
    .to(Sink.ignore)
  val queue = graph.run()

  override def onStatus(status: Status) =
//    Await.result(queue.offer(status), Duration.Inf)
    println(status.getText())

}
