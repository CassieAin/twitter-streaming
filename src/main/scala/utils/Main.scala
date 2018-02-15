package utils

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import api.ApiRoute
import models._
import slick.jdbc.PostgresProfile.api._
import stream.{Counter, TwitterStreamFilters}

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.io.StdIn

object Main extends ApiRoute {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val twitterStream = TwitterStreamFilters.configureTwitterStream()
    val counter = new Counter
    twitterStream.addListener(counter)
    val bindingFuture = Http().bindAndHandle(routes, "localhost", 8080)
    println("Server started!")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())

//    createTable()
//    filterTweets()
  }
  val createTweetTable = TweetTable.table.schema.create

  def createTable() =
    Await.result(db.run(DBIO.seq(createTweetTable)), Duration.Inf)

  def filterTweets() = {
    TwitterStreamFilters.getTwitterStream(twitterStream)
    TwitterStreamFilters.closeTwitterStream(twitterStream)

//    TwitterStreamFilters.filterTwitterStreamByWord(twitterStream, Array("christmas", "scala"))
//    TwitterStreamFilters.filterTwitterStreamByUserID(twitterStream, Array(534563976, 17765013, 526339343,
//      18318677, 15612251, 14706299, 345673106))
//    TwitterStreamFilters.filterTwitterStreamByHashtag(twitterStream, Array("christmas", "new year"))
//    TwitterStreamFilters.filterTwitterStreamByHashtag(twitterStream, Array("#christmas"))
//    val locationBox = Array(Array(-97.8, 30.25), Array(-97.65, 30.35))
//    TwitterStreamFilters.filterTwitterStreamByLocation(twitterStream, locationBox)
//375416840 19243303 3268678291
  }
}
