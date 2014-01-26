package atk.util

import java.io.File
import java.net.URL
import java.io.FileOutputStream
import org.apache.commons.io.IOUtils
/**
 * Small tools to work with URLs.
 * 
 * @author Thomas Abeel
 */
object URLTools {

  def asFile(url: String, keep: Boolean = false) = {
    val id = MD5Tools.md5(url)
    val file = new File(id + ".urltools.tmp")
    if (!keep)
      file.deleteOnExit()
    if (!file.exists() || file.length() == 0)
      retrieve(url, file)
    file;
  }

  private def retrieve(urlS: String, out: File) = {

    val conn = new URL(urlS).openConnection();
    val fof = new FileOutputStream(out);
    IOUtils.copy(conn.getInputStream(), fof);
    fof.close();

  }

}