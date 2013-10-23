package atk.compbio.gff

import java.io.File
import scala.io.Source
/**
 * @author Thomas Abeel
 */
object GFFFile {


  def apply(path: File): Iterator[GFFLine] = {
    Source.fromFile(path).getLines.filterNot(_.startsWith("#")).map(new GFFLine(_))
  }

  def apply(path: String): Iterator[GFFLine] = {
    apply(new File(path))
  }

}