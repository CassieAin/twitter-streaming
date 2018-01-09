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
}
