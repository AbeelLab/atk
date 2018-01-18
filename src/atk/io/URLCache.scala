package atk.io

import atk.util.MD5Tools
import java.io.File
import java.net.URL
import scala.io.Source
import java.io.PrintWriter
import java.util.Date
import java.nio.charset.StandardCharsets
import java.io.FileOutputStream
import java.io.OutputStreamWriter
/**
 * Simple object to cache URL reads
 */
object URLCache {

  var debug = false

  var queryWait: Long = 15 * 1000

  private var lastQuery:Long = 0

  def query(url: String, refresh: Long = (1000L * 60 * 60 * 24 * 30)): List[String] = {

    /**
     *  Older than a month
     */
    def old(time: Long) = {
      (System.currentTimeMillis() - time) > refresh
    }

    if (debug)
      println("cache:query  " + url)

    val hash = MD5Tools.md5(url)

    val cached = new File(".url-cache/" + hash + ".blob")
    cached.getParentFile().mkdirs()

    if (debug)
      println("cache:hash - " + hash)

    if (!cached.exists() || cached.length() == 0 || old(cached.lastModified())) {

      cached.delete()
      while (!cached.exists()) {
        /* Wait at least 15 seconds between queries */
        while (System.currentTimeMillis() - lastQuery < queryWait) {
          Thread.sleep(100)
        }
        lastQuery = System.currentTimeMillis()

        val u = new URL(url);
        val conn = u.openConnection();
        if (debug)
          println("cache:encoding - " + conn.getContentEncoding())
        val lines = Source.fromInputStream(conn.getInputStream())("UTF-8").getLines.toList

        if (!lines.mkString(" ").contains("Timed out")) {
          val pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cached), "UTF8"), true)
          pw.println(lines.mkString("\n"))
          pw.close
        }

      }
    }
    Source.fromFile(cached)("UTF-8").getLines.toList

  }

}