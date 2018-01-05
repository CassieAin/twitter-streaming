package api

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import db.Databases
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import stream.{Counter, TwitterStreamFilters}

import scala.concurrent.Future

trait ApiRoute extends Databases with FailFastCirceSupport{
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
//            complete("tweets by user")
            onSuccess(Future.successful(TwitterStreamFilters.filterTwitterStreamByUserID(twitterStream, Array(userId)))) {
//              result => complete(result)
              complete(StatusCodes.OK)
//              case tweets: Set[Tweet] => complete(StatusCodes.OK, tweets)
            }
          }
      }
    }
}
