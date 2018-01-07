package db

import models._
import slick.jdbc.PostgresProfile.api._

trait Databases {
  val db = Database.forConfig("scalaxdb")
  val tweetRepository = new TweetRepository(db)
  val authorRepository = new AuthorRepository(db)
  val locationRepository = new LocationRepository(db)
}
