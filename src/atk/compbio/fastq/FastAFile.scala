package atk.compbio.fastq

import java.io.File
import scala.io.Source

object FastAFile {

  case class FastaRecord(name: String, seq: String);

  def apply(path: File): Iterator[FastaRecord] = {
    val lines=Source.fromFile(path).getLines.filterNot(_.trim().length() == 0).toList
    val idx=lines.zipWithIndex.filter(_._1(0)=='>').map(_._2)
 
    val slices=idx.sliding(2).toList
    
    val list=for(slice <- slices.iterator) yield{
      val (from,to)=slice(0)->slice(1)
      val group=lines.slice(from, to).toList
      new FastaRecord(group(0).drop(1),group.drop(1).mkString(""))
    }
    list
    
    
  }

  def apply(path: String): Iterator[FastaRecord] = {
    apply(new File(path))
  }

}