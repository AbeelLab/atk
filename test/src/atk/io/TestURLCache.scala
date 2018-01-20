package atk.io

import org.scalatest.FunSuite
import java.io.PrintWriter

object TestURLCache {
  def main(args: Array[String]) {

  }
}

class TestURLCache extends FunSuite  {

  test("testCertificateBypass") {
    println(URLCache.query("https://www.brickwatch.net/de-DE/",bypassCertificates=true))

  }

  
}