package atk.io

import atk.util.MD5Tools
import java.io.File
import java.net.URL
import scala.io.Source
import java.io.PrintWriter
/**
 * Simple object to cache URL reads
 */
object URLCache {

  /**
   *  Older than a month
   */
  def old(time: Long) = {
    (System.currentTimeMillis() - time) > (1000 * 60 * 60 * 24 * 30)
  }
  var lastQuery = System.currentTimeMillis()
  def query(url: String): List[String] = {

    val hash = MD5Tools.md5(url)

    val cached = new File(".url-cache/" + hash + ".blob")
    cached.getParentFile().mkdirs()
    if (!cached.exists() || cached.length() == 0 || old(cached.lastModified())) {

      //    println(hmac)
	/* Wait at least 10 seconds between queries */
      while (System.currentTimeMillis() - lastQuery < 10 * 1000) {
        Thread.sleep(1000)
      }
      lastQuery = System.currentTimeMillis()

      val u = new URL(url);
      val conn = u.openConnection();
      val lines = Source.fromInputStream(conn.getInputStream()).getLines.toList
      val pw = new PrintWriter(cached)
      pw.println(lines.mkString("\n"))
      pw.close
      lines
    } else {
      Source.fromFile(cached).getLines.toList
    }

  }

}