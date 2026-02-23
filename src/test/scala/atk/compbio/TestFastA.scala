package atk.compbio

//import org.scalatest.Matchers._
import org.scalatest.funsuite.*

import atk.compbio.fastq.FastAFile


class TestFastA extends AnyFunSuite {
  val FASTA="src/test/resources/test.fasta"

  /**
  Disabled. bug in FastAFile
  **/
//  test("test fasta") {
//    for (entry <- FastAFile(FASTA)) {
//      println(entry)
//    }
//
//    FastAFile(FASTA).filter(_.name.contains("1")).toList.map(println(_))
//  }

}