package stream

import com.typesafe.config.ConfigFactory
import twitter4j.{FilterQuery, TwitterStream, TwitterStreamFactory}
import twitter4j.conf.ConfigurationBuilder

object TwitterStreamFilters {

  def startTwitterStream(twitterStream: TwitterStream) = {

  }

  def closeTwitterStream(twitterStream: TwitterStream) = {
    Thread.sleep(100000)
    twitterStream.cleanUp
    twitterStream.shutdown
  }

  def filterTwitterStreamByWord(twitterStream: TwitterStream, word: String) = {
    twitterStream.filter(new FilterQuery().track(word))
  }

  def getTwitterStream(twitterStream: TwitterStream) = {
    twitterStream.sample
  }

  def filterTwitterStreamByUserID(twitterStream: TwitterStream, userId: Long) = {
    twitterStream.filter(new FilterQuery().follow(userId))
  }


  def filterTwitterStreamByLocation(twitterStream: TwitterStream, coordinates: Array[Double]) = {
    twitterStream.filter(new FilterQuery().locations(coordinates))
  }

  def filterTwitterStreamByHashtag(twitterStream: TwitterStream, hashtag: String) = {
    twitterStream.filter(new FilterQuery().track("#" + hashtag))
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
