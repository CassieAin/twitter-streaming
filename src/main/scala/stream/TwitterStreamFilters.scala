package stream

import com.typesafe.config.ConfigFactory
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{FilterQuery, TwitterStream, TwitterStreamFactory}

object TwitterStreamFilters {

  def closeTwitterStream(twitterStream: TwitterStream) = {
    Thread.sleep(100000)
    twitterStream.cleanUp
    twitterStream.shutdown
  }

  def filterTwitterStreamByWord(twitterStream: TwitterStream, word: Array[String]) = {
    twitterStream.filter(new FilterQuery().track(word))
  }

  def getTwitterStream(twitterStream: TwitterStream) = {
    twitterStream.sample()
  }

  def filterTwitterStreamByUserID(twitterStream: TwitterStream, userId: Array[Long]) = {
    twitterStream.filter(new FilterQuery().follow(userId))
  }


  def filterTwitterStreamByLocation(twitterStream: TwitterStream, coordinates: Array[Array[Double]]) = {
    twitterStream.filter(new FilterQuery().locations(coordinates))
  }

  def filterTwitterStreamByHashtag(twitterStream: TwitterStream, hashtag: Array[String]) = {
    twitterStream.filter(new FilterQuery().track(hashtag))
  }

  def configureTwitterStream() = {
    lazy val config = ConfigFactory.load("application.conf")
    val twitterConf = config.getConfig("twitter")

    val configuration = new ConfigurationBuilder()
      .setOAuthConsumerKey(twitterConf.getString("consumerKey"))
      .setOAuthConsumerSecret(twitterConf.getString("consumerSecret"))
      .setOAuthAccessToken(twitterConf.getString("token"))
      .setOAuthAccessTokenSecret(twitterConf.getString("tokenSecret"))
      .build

    new TwitterStreamFactory(configuration).getInstance()
  }
}
