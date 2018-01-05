package api

import akka.http.scaladsl.server.Directives._
import db.Databases
import stream.{Counter, TwitterStreamFilters}

trait ApiRoute extends Databases {
  val twitterStream = TwitterStreamFilters.configureTwitterStream()
  val counter = new Counter
  twitterStream.addListener(counter)

  val routes = pathPrefix("tweets") {
    pathSingleSlash {
      complete("hello")
    }
  } ~
    pathPrefix("tweets") {
      pathPrefix(LongNumber) {
        userId =>
          get {
            complete("tweets by user")
//            onSuccess(Future.successful(TwitterStreamFilters.filterTwitterStreamByUserID(twitterStream, Array(userId)))) {
//              result => complete(StatusCodes.OK)
//            }

//            complete{
//              Future.successful(TwitterStreamFilters.filterTwitterStreamByUserID(twitterStream, Array(534563976)))
//            }
          }
      }
    }
}
