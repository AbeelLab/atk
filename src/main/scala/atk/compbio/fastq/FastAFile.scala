package atk.compbio.fastq

import java.io.File
import scala.io.Source

object FastAFile {

  case class FastaRecord(name: String, seq: String);

  /**
  @param path a File
  @return list of the FASTA records in the file. 
  
  WARNING 
  It seems that due to bug, last record is missing.
  Also pulling all FASTA into memory takes lots of memory
  and doesn't scale up well. There should be better ways. 
  Seems only used in genometools faq2gc. I guess the real fasta
  reader is in htsjdk/samtools?
  **/
  def apply(path: File): Iterator[FastaRecord] = {
    val lines=Source.fromFile(path).getLines.filterNot(_.trim().length() == 0).toList
    // get  list of line numbers starting with '>'. Normal fasta has only 1 such line
    // this probably aims at also reading multi-fasta files.
    // last line number is missing however?
    val idx=lines.zipWithIndex.filter(_._1(0)=='>').map(_._2)
 
 	// slices is list of Array(a,b) where a,b 2 subsequent line nrs starting with ">"
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