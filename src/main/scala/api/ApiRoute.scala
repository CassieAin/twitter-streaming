package api

import akka.http.scaladsl.server.Directives._
import db.{DAO, Databases}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import io.circe.syntax._
import stream.{Counter, TwitterStreamFilters}


trait ApiRoute extends Databases with FailFastCirceSupport {
  val twitterStream = TwitterStreamFilters.configureTwitterStream()
  val counter = new Counter
  twitterStream.addListener(counter)

  val routes = pathPrefix("tweets") {
    pathSingleSlash {

      onSuccess(new DAO().selectFirstTweets()) {
        result => complete(result.asJson)
      }
    }
  } ~
    pathPrefix("tweets") {
      pathPrefix(LongNumber) {
        userId =>
          get {
            onSuccess(new DAO().selectTweetsByAuthor(userId)) {
              result => complete(result.asJson)
            }
//            onSuccess(Future.successful(TwitterStreamFilters.filterTwitterStreamByUserID(twitterStream, Array(userId)))) {
//              result =>
//                complete(result)
//                complete(StatusCodes.OK)
            }
          } ~
            pathPrefix(Segment) {
              language: String =>
                get {
                  onSuccess(new DAO().selectTweetsByLanguage(language)) {
                    result => complete(result.asJson)
                  }
                }
            }
      }
    }
