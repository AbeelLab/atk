/*
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit
 * http://creativecommons.org/licenses/by-nc-nd/3.0/
 * or send a letter to Creative Commons, 444 Castro Street,
 * Suite 900, Mountain View, California, 94041, USA.
 *
 * A copy of the license is included in LICENSE.txt
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * Copyright 2005-2013 Thomas Abeel
 */
package atk.util

import scala.io.Source
import java.io.File
import java.io.PushbackInputStream
import java.util.zip.GZIPInputStream
import java.io.InputStream
import java.io.FileInputStream

/**
 * Utility methods to handle lines in files
 *
 * @author Thomas Abeel
 */
trait Lines {

  @Deprecated
  def tColumns(columns: List[Int], list: List[String]): List[List[String]] = {
    tColumns(list, columns)

  }

  def tColumns(list: List[String], columns: List[Int] = null, sep: String = "\t") = {
    list.map(line => {
      val arr = line.split(sep)
      if (columns != null)
        columns.map(arr(_))
      else
        arr.toList

    })

  }

  def tColumn(column: Int, list: List[String], sep: String = "\t") = {
    list.map(_.split(sep)(column))
  }

  def tMap(list: List[String], keyColumn: Int = 0, valueColumn: Int = 1, sep: String = "\t", limitSplit: Boolean = true, splitLimit: Int = 2, trim: Boolean = false): Map[String, String] = {
    list.map(l => {
      val arr = (
        if (limitSplit)
          l.split(sep, List(splitLimit, keyColumn + 1, valueColumn + 1).max)
        else
          l.split(sep));
      assume(keyColumn < arr.size, "Key column (" + keyColumn + ") out of range: " + arr.mkString(","))
      assume(valueColumn < arr.size, "Value column (" + valueColumn + ") out of range: " + arr.mkString(","))
      if (trim)
        arr(keyColumn) -> arr(valueColumn)
      else
        arr(keyColumn).trim -> arr(valueColumn).trim
    }).toMap
  }

  implicit def toLeft[String, File](left: String): Either[String, File] = Left(left)

  implicit def toRight[String, File](right: File): Either[String, File] = Right(right)

  @Deprecated
  def tLinesIterator(file: Either[String, File], skipComments: Boolean = true, skipBlank: Boolean = true): Iterator[String] = {
    tIterator(file, skipComments, skipBlank)._1
  }

  def tIterator(file: Either[String, File], skipComments: Boolean = true, skipBlank: Boolean = true): (Iterator[String], Source) = {
    val ff = file.fold(new File(_), identity)
    if (!ff.exists()) {
      var parent = ff
      while (parent != null && !parent.exists()) {
        System.err.println("Invalid path: " + parent)
        parent = parent.getParentFile()
      }
    }
    val src = Source.fromInputStream(wrap(new FileInputStream(ff)))("iso-8859-1")
    src.getLines.filterNot(f => skipComments && f.startsWith("#")).filterNot(f => skipBlank && f.trim.size == 0) -> src
  }

  private def wrap(stream: InputStream): InputStream = {

    val tmp = new PushbackInputStream(stream, 2);
    //			PushbackInputStream tmp = (PushbackInputStream) stream;
    val a = tmp.read();
    val b = tmp.read();
    tmp.unread(b);
    tmp.unread(a);
    if (a == 0x1f && b == 0x8b) {
      new GZIPInputStream(tmp);
    } else{ 
      tmp;
    }

  }

  /**
   *
   */
  def tLines(file: Either[String, File], skipComments: Boolean = true, skipBlank: Boolean = true): List[String] = {
    val it = tIterator(file, skipComments, skipBlank)
    val list = it._1.toList
    it._2.close
    list
  }

  def tBlob(file: Either[String, File], skipComments: Boolean = true, skipBlank: Boolean = true): String = {
    tLines(file, skipComments, skipBlank).mkString("\n")
  }
}