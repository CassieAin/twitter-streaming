package db

import slick.jdbc.PostgresProfile.api._

trait Databases {
  val db = Database.forConfig("scalaxdb")
}
