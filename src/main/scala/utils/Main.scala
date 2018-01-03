package utils

import com.typesafe.config.ConfigFactory
import stream.Counter
import twitter4j.TwitterStreamFactory
import twitter4j.conf.ConfigurationBuilder

object Main{

  def main(args: Array[String]): Unit = {
    lazy val config = ConfigFactory.load("application.conf")
    val twitterConf = config.getConfig("twitter")

    val configuration = new ConfigurationBuilder()
      .setOAuthConsumerKey(twitterConf.getString("consumerKey"))
      .setOAuthConsumerSecret(twitterConf.getString("consumerSecret"))
      .setOAuthAccessToken(twitterConf.getString("token"))
      .setOAuthAccessTokenSecret(twitterConf.getString("tokenSecret"))
      .build

    val twitterStream = new TwitterStreamFactory(configuration)
      .getInstance()

    val counter = new Counter
    twitterStream.addListener(counter)
    twitterStream.sample
    Thread.sleep(10000)
    twitterStream.cleanUp
    twitterStream.shutdown
  }
}
