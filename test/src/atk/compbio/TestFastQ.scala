package atk.compbio

import org.scalatest.FunSuite
import org.scalatest.Matchers._
import atk.compbio.fastq.FastQFile

object TestFastQ {

}

class TestFastQ extends FunSuite {

  test("test fq") {
    for (entry <- FastQFile("test/test.fastq")) {
      println(entry)
    }

    FastQFile("test/test.fastq").filter(_.name.contains("1")).toList.map(println(_))
  }

}