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
    } ~
    pathPrefix("locations") {
      pathPrefix(Segment) {
        location: String =>
          get {
            onSuccess(new DAO().selectTweetsByLocation(location)) {
              result => complete(result.asJson)
            }
          }
      }
    } ~
    pathPrefix("words") {
      pathPrefix(Segment) {
        word: String =>
          get {
            onSuccess(new DAO().selectTweetsByWord(word)) {
              result => complete(result.asJson)
            }
          }
      }
    } ~
    pathPrefix("hashtags") {
      pathPrefix(Segment) {
        word: String =>
          get {
            onSuccess(new DAO().selectTweetsByHashtag(word)) {
              result => complete(result.asJson)
            }
          }
      } 
    }
}
