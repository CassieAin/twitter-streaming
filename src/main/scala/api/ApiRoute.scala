package api

import akka.http.scaladsl.server.Directives._
import db.Databases

trait ApiRoute extends Databases {
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
          }
      }
    }
}
