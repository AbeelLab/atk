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
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager
import java.security.cert.X509Certificate
import javax.net.ssl.KeyManager
import java.security.SecureRandom
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * Simple object to cache URL reads
 */
object URLCache {

  val DAY = 1000L * 60 * 60 * 24
  val WEEK = 1000L * 60 * 60 * 24 * 7
  val MONTH = 1000L * 60 * 60 * 24 * 30;
  val YEAR = 1000L * 60 * 60 * 24 * 365;
  val ALWAYS = 0;
  var debug = false

  var queryWait: Long = 15 * 1000

  private var lastQuery: Long = 0

  def age(url: String, encoding: String = "ISO-8859-1", aging: Long):Unit= {
    val hash = md5key(url, encoding)

    val cached = new File(".url-cache/" + hash + ".blob")

    if (cached.exists()) {
      cached.setLastModified(cached.lastModified() - aging)
    }
  }

  def delete(urlKey:String, encoding: String = "ISO-8859-1"): Unit= {
     val hash = md5key(urlKey, encoding)
     val cached = new File(".url-cache/" + hash + ".blob")
     cached.delete()
  }
  private def md5key(url: String, encoding: String) = {
    MD5Tools.md5(url + "-" + encoding)
  }

  def query(url: String, refresh: Long = MONTH, cookies: String = null, bypassCertificates: Boolean = false, encoding: String = "ISO-8859-1", key: String = null): List[String] = {

    val urlKey = if (key != null) key else url
    /**
     *  Older than a month
     */
    def old(time: Long) = {
      (System.currentTimeMillis() - time) > refresh
    }

    if (debug)
      println("cache:query  " + url)

    val hash = md5key(urlKey, encoding)

    val cached = new File(".url-cache/" + hash + ".blob")
    cached.getParentFile().mkdirs()

    if (debug)
      println("cache:hash - " + hash)

    if (!cached.exists() || cached.length() == 0 || old(cached.lastModified())) {
      if (debug) {
        println("--retrieve--exist:" + cached.exists() + ";len:" + cached.length() + ";old:" + old(cached.lastModified()))
      }
      cached.delete()
      while (!cached.exists()) {
        /* Wait at least 15 seconds between queries */
        while (System.currentTimeMillis() - lastQuery < queryWait) {
          Thread.sleep(100)
        }
        lastQuery = System.currentTimeMillis()

        val u = new URL(url);
        val conn = if (url.startsWith("https")) {
          val ctx = SSLContext.getInstance("TLS");
          ctx.init(Array.empty[KeyManager], (List(new DefaultTrustManager())).toArray, new SecureRandom());
          SSLContext.setDefault(ctx);
          val conns = (u.openConnection()).asInstanceOf[HttpsURLConnection]
          conns.setHostnameVerifier(new HostnameVerifier() {
            override def verify(arg0: String, arg1: SSLSession): Boolean = true
          });

          conns
        } else {
          u.openConnection();
        }

        

        if (cookies != null) {
          if (debug)
            println("setting cookies: " + cookies)
          conn.setRequestProperty("Cookie", cookies);
        }

        conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/24.0");
        conn.connect()
        if (debug)
          println("cache:encoding - " + conn.getContentEncoding())
        val lines = Source.fromInputStream(conn.getInputStream())(encoding).getLines.toList

        if (!lines.mkString(" ").contains("Timed out")) {
          val pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cached), encoding), true)
          pw.println(lines.mkString("\n"))
          pw.close
        }

      }
    }
    Source.fromFile(cached)(encoding).getLines.toList

  }

  private class DefaultTrustManager extends X509TrustManager {

    override def checkClientTrusted(arg0: Array[X509Certificate], arg1: String) = {}
    override def checkServerTrusted(arg0: Array[X509Certificate], arg1: String) = {}

    override def getAcceptedIssuers(): Array[X509Certificate] = null
  }
}