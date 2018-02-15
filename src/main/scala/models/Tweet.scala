package models
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

case class Tweet(tweetId: Long, author: Long, text: String, language: String, favoriteCount: Int, retweetCount: Int
                , location: String)
class TweetTable(tag: Tag) extends Table[Tweet](tag, "tweets"){
  val tweetId = column[Long]("tweetId", O.PrimaryKey, O.AutoInc)
  val author = column[Long]("author")
  val text = column[String]("text")
  val language = column[String]("language")
  val location = column[String]("location")
  val favoriteCount = column[Int]("favoriteCount")
  val retweetCount = column[Int]("retweetCount")

  def * = (tweetId, author, text, language, favoriteCount, retweetCount, location) <> (Tweet.apply _ tupled, Tweet.unapply)
}

object TweetTable{
  lazy val table = TableQuery[TweetTable]
}

class TweetRepository(db: Database) {
  lazy val tweetTableQuery = TableQuery[TweetTable]

  def create(tweet: Tweet): Future[Tweet] =
    db.run(tweetTableQuery returning tweetTableQuery += tweet)

  def createInBatch(tweetSeq: Seq[Tweet]) =
    db.run(DBIO.sequence(tweetSeq.map(t=>tweetTableQuery+=t) ).transactionally)

  def update(tweet: Tweet): Future[Int] =
    db.run(tweetTableQuery.filter(_.tweetId === tweet.tweetId).update(tweet))

  def delete(tweet: Tweet): Future[Int] =
    db.run(tweetTableQuery.filter(_.tweetId === tweet.tweetId).delete)

  def deleteById(id: Option[Long]): Future[Int] =
    db.run(tweetTableQuery.filter(_.tweetId === id).delete)

  def getById(idTweet: Option[Long]): Future[Option[Tweet]] =
    db.run(tweetTableQuery.filter(_.tweetId === idTweet).result.headOption)
}