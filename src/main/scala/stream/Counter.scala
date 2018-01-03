package stream

import akka.actor.ActorSystem
import akka.event.Logging
import akka.stream._
import akka.stream.scaladsl._
import twitter4j.{Status, StatusAdapter}


class Counter extends StatusAdapter{

  private implicit val system = ActorSystem("EmojiTrends")
  private implicit val materializer = ActorMaterializer()
  private implicit val executionContext = system.dispatcher
  private implicit val LoggingAdapter =
    Logging(system, classOf[Counter])

  private val overflowStrategy = OverflowStrategy.backpressure
  private val bufferSize = 1000
  private val statusSource = Source.queue[Status](
    bufferSize,
    overflowStrategy
  )

  private val graph = statusSource
    .log("QUEUED", {status => status.getText})
    .to(Sink.ignore)
  private val queue = graph.run()

  override def onStatus(status: Status) =
//    Await.result(queue.offer(status), Duration.Inf)
    println(status.getText())

}
