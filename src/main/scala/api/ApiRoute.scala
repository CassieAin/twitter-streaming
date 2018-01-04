package api

import akka.http.scaladsl.server.Directives._
import db.Databases

trait ApiRoute extends Databases{
    val routes = pathSingleSlash {
        complete("hello")
    } ~
      pathPrefix("tweets") {
        pathSingleSlash {
          get {
              complete("")
            }
          }
        }
}
