package db

import models._
import slick.jdbc.PostgresProfile.api._

class DAO extends Databases {

  def selectTweetsByAuthor(userId: Long) = {
    val queryTweetsByAuthor = TweetTable.table
      .filter(_.author === userId)

    db.run(queryTweetsByAuthor.result)
  }

  def selectTweetsByLanguage(language: String) = {
    val queryTweetsByLanguage = TweetTable.table
      .filter(_.language === language)
      .take(20)

    db.run(queryTweetsByLanguage.result)
  }

  def selectTweetsByLocation(location: String) = {
    val queryTweetsByAuthor = TweetTable.table
      .filter(_.location === location)

    db.run(queryTweetsByAuthor.result)
  }

  def selectFirstTweets() = {
    val queryFirstTweets = TweetTable.table.take(50)

    db.run(queryFirstTweets.result)
  }

  def selectTweetsByWord(word: String) = {
    val queryTweetsByWord = for {
      tweet <- TweetTable.table if tweet.text like "%"+word+"%"
    } yield (tweet)

    db.run(queryTweetsByWord.result)
  }
}
