package atk.io

import org.scalatest.funsuite.*
import java.io.PrintWriter

object TestURLCache {
  def main(args: Array[String]): Unit = {
    URLCache.debug=true
    URLCache.queryWait = 1000
   val us = URLCache.query("https://brickset.com/buy/amazon", URLCache.DAY, cookies = "PreferredCountry2=CountryCode=US&CountryName=United States", key = "brickset_US")
    val eu = URLCache.query("https://brickset.com/buy/amazon", URLCache.DAY, encoding = "UTF-8", cookies = "PreferredCountry2=CountryCode=NL&CountryName=Netherlands", key = "brickset_EU")

  }
}

class TestURLCache extends AnyFunSuite  {

  test("testCertificateBypass") {
    println(URLCache.query("https://www.brickwatch.net/de-DE/",bypassCertificates=true))

  }
  
  test("testMultiDownload"){
    val us = URLCache.query("https://brickset.com/buy/amazon", URLCache.DAY, cookies = "PreferredCountry2=CountryCode=US&CountryName=United States", key = "brickset_US")
    val eu = URLCache.query("https://brickset.com/buy/amazon", URLCache.DAY, encoding = "UTF-8", cookies = "PreferredCountry2=CountryCode=NL&CountryName=Netherlands", key = "brickset_EU")

  }

  
}