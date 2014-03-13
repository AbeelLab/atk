package atk.compbio.vcf

import scala.io.Source
import java.io.File
/**
 * @author Thomas Abeel
 */
object VCFFile {

  def apply(path: File): Iterator[VCFLine] = {
    Source.fromFile(path).getLines.filterNot(_.startsWith("#")).filterNot(_.trim().length()==0).map(new VCFLine(_))
  }

  def apply(path: String): Iterator[VCFLine] = {
    apply(new File(path))
  }

}