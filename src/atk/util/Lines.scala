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
/**
 * Utility methods to handle lines in files
 *
 * @author Thomas Abeel
 */
trait Lines {
  def tColumn(column:Int,list:List[String], sep: String = "\t")={
    list.map(_.split(sep)(column))
  }
  
  def tMap(list: List[String], keyColumn: Int = 0, valueColumn: Int = 1, sep: String = "\t", limitSplit:Boolean=true,splitLimit: Int = 2): Map[String, String] = {
    list.map(l => { val arr = (if(limitSplit)l.split(sep, splitLimit) else l.split(sep)); arr(keyColumn) -> arr(valueColumn) }).toMap
  }

  implicit def toLeft[String, File](left: String): Either[String, File] = Left(left)

  implicit def toRight[String, File](right: File): Either[String, File] = Right(right)

  /**
   *
   */
  def tLines(file: Either[String, File], skipComments: Boolean = true, skipBlank: Boolean = true): List[String] = {
    val ff= file.fold(new File(_), identity)
    if(!ff.exists()){
      var parent=ff
      while(parent != null && !parent.exists()){
        System.err.println("Invalid path: "+parent)
        parent=parent.getParentFile()
      }
    }
    Source.fromFile(ff).getLines.filterNot(f => skipComments && f.startsWith("#")).filterNot(f => skipBlank && f.trim.size == 0).toList
  }
}