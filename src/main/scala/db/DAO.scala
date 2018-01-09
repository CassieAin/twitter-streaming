package db

import models._
import slick.jdbc.PostgresProfile.api._

class DAO extends Databases{

  def selectTweetsByAuthor(userId: Long) = {
    val queryTweetsByAuthor = TweetTable.table
      .filter(_.author === userId)
      .take(1)

    db.run(queryTweetsByAuthor.result)
  }

  def selectTweetsByLanguage(language: String) = {
    val queryTweetsByLanguage = TweetTable.table
      .filter(_.language === language)
      .take(20)

    db.run(queryTweetsByLanguage.result)
  }

  def selectFirstTweets() = {
    val queryFirstTweets = TweetTable.table.take(50)

    db.run(queryFirstTweets.result)
  }
}
