package atk.compbio.fastq

import java.io.File
import scala.io.Source

object FastQFile {

  case class FastqRecord(name: String, seq: String, qual: String);

  def apply(path: File): Iterator[FastqRecord] = {
    Source.fromFile(path).getLines.filterNot(_.startsWith("#")).filterNot(_.trim().length() == 0).grouped(4).map(group => {
      new FastqRecord(group(0).drop(1),group(1),group(3))
    })
  }

  def apply(path: String): Iterator[FastqRecord] = {
    apply(new File(path))
  }

}