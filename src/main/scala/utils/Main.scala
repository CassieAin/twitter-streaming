package utils

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import api.ApiRoute
import stream.{Counter, TwitterStreamFilters}

object Main extends ApiRoute {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val twitterStream = TwitterStreamFilters.configureTwitterStream()
    val counter = new Counter
    twitterStream.addListener(counter)
//    val bindingFuture = Http().bindAndHandle(routes, "localhost", 8080)
//    println("Server started!")
//    StdIn.readLine()
//    bindingFuture
//      .flatMap(_.unbind())
//      .onComplete(_ => system.terminate())


        TwitterStreamFilters.getTwitterStream(twitterStream)
//    TwitterStreamFilters.filterTwitterStreamByWord(twitterStream, Array("christmas", "scala"))
//    TwitterStreamFilters.filterTwitterStreamByUserID(twitterStream, Array(534563976, 17765013, 526339343,
//      18318677, 15612251, 14706299, 345673106))
    //        TwitterStreamFilters.filterTwitterStreamByHashtag(twitterStream, Array("christmas", "new year"))
    //    TwitterStreamFilters.filterTwitterStreamByHashtag(twitterStream, Array("#christmas"))
    //    val locationBox = Array(Array(-97.8,30.25),Array(-97.65,30.35))
    //    TwitterStreamFilters.filterTwitterStreamByLocation(twitterStream, locationBox)

    TwitterStreamFilters.closeTwitterStream(twitterStream)
  }


}
