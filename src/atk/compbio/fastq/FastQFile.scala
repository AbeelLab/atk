package atk.compbio.fastq

import java.io.File
import scala.io.Source
import atk.util.Lines

object FastQFile extends Lines {

  case class FastqRecord(name: String, seq: String, qual: String);

  def apply(path: File): Iterator[FastqRecord] = {
    
    
    
    tLinesIterator(path,skipComments=false).grouped(4).map(group => {
      new FastqRecord(group(0).drop(1),group(1),group(3))
    })
  }

  def apply(path: String): Iterator[FastqRecord] = {
    apply(new File(path))
  }

}