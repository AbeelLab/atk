package atk.compbio.fastq

import java.io.File
import scala.io.Source

object FastAFile {

  case class FastaRecord(name: String, seq: String);

  def apply(path: File): Iterator[FastaRecord] = {
    Source.fromFile(path).getLines.filterNot(_.trim().length() == 0).grouped(2).map(group => {
      new FastaRecord(group(0).drop(1),group(1))
    })
  }

  def apply(path: String): Iterator[FastaRecord] = {
    apply(new File(path))
  }

}