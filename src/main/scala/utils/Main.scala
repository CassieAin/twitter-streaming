package utils

import stream.{Counter, TwitterStreamFilters}

object Main {

  def main(args: Array[String]): Unit = {
    val twitterStream = TwitterStreamFilters.configureTwitterStream()
    val counter = new Counter
    twitterStream.addListener(counter)
    //    getTwitterStream(twitterStream)
    //    filterTwitterStreamByWord(twitterStream, "christmas")
    //    filterTwitterStreamByUserID(twitterStream,534563976)
    //    filterTwitterStreamByLocation(twitterStream, Array(-97.8,30.25))
    //    filterTwitterStreamByHashtag(twitterStream, "christmas")
    TwitterStreamFilters.filterTwitterStreamByHashtag(twitterStream, "christmas")
    TwitterStreamFilters.closeTwitterStream(twitterStream)
  }
}
