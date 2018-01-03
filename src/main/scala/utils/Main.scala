package utils

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import api.ApiRoute

import scala.io.StdIn

object Main  extends ApiRoute{

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val bindingFuture = Http().bindAndHandle(routes, "localhost", 8080)
    println("Server started!")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())

//    val twitterStream = TwitterStreamFilters.configureTwitterStream()
//    val counter = new Counter
//    twitterStream.addListener(counter)
    //    getTwitterStream(twitterStream)
    //    filterTwitterStreamByWord(twitterStream, "christmas")
    //    filterTwitterStreamByUserID(twitterStream,534563976)
    //    filterTwitterStreamByLocation(twitterStream, Array(-97.8,30.25))
    //    filterTwitterStreamByHashtag(twitterStream, "christmas")
//    TwitterStreamFilters.filterTwitterStreamByHashtag(twitterStream, "christmas")
//    TwitterStreamFilters.closeTwitterStream(twitterStream)
  }
}
