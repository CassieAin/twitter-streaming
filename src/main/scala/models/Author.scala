package models

import slick.jdbc.PostgresProfile.api._

case class Author(userId: Long, username: String, followersCount: Int)

class AuthorTable(tag: Tag) extends Table[Author](tag, "authors"){
  val userId = column[Long]("userId", O.PrimaryKey, O.AutoInc)
  val username = column[String]("username")
  val followersCount = column[Int]("followersCount")

  def * = (userId, username, followersCount) <> (Author.apply _ tupled, Author.unapply)
}

object AuthorTable{
  lazy val table = TableQuery[AuthorTable]
}