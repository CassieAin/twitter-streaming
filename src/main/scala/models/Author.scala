package models

import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

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

class AuthorRepository(db: Database) {
  lazy val authorTableQuery = TableQuery[AuthorTable]

  def create(author: Author): Future[Author] =
    db.run(authorTableQuery returning authorTableQuery += author)

  def createInBatch(authorSeq: Seq[Author]) =
    db.run(DBIO.sequence(authorSeq.map(t=>authorTableQuery+=t) ).transactionally)

  def update(author: Author): Future[Int] =
    db.run(authorTableQuery.filter(_.userId === author.userId).update(author))

  def delete(author: Author): Future[Int] =
    db.run(authorTableQuery.filter(_.userId === author.userId).delete)

  def deleteById(id: Option[Long]): Future[Int] =
    db.run(authorTableQuery.filter(_.userId === id).delete)

  def getById(idAuthor: Option[Long]): Future[Option[Author]] =
    db.run(authorTableQuery.filter(_.userId === idAuthor).result.headOption)
}