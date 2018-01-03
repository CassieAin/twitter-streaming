package utils

import com.typesafe.config.ConfigFactory
import stream.Counter
import twitter4j.TwitterStreamFactory
import twitter4j.conf.ConfigurationBuilder

object Main{

  def main(args: Array[String]): Unit = {
    val conf = ConfigFactory.load("twitter-authorization.conf")
    val consumerKey = conf.getString("twitter.consumer.key")
    val consumerSecret = conf.getString("twitter.consumer.secret")
    val accessToken = conf.getString("twitter.access.token")
    val accessTokenSecret = conf.getString("twitter.access.secret")

    val configuration = new ConfigurationBuilder()
      .setOAuthConsumerKey(consumerKey)
      .setOAuthConsumerSecret(consumerSecret)
      .setOAuthAccessToken(accessToken)
      .setOAuthAccessTokenSecret(accessTokenSecret)
      .build

    val twitterStream = new TwitterStreamFactory(configuration)
      .getInstance()

    val counter = new Counter
    twitterStream.addListener(counter)
//    twitterStream.filter(new FilterQuery().track("scala"))
    twitterStream.sample
    Thread.sleep(10000)
    twitterStream.cleanUp
    twitterStream.shutdown
  }
}
