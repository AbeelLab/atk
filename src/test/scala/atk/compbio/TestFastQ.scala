package atk.compbio

//import org.scalatest.Matchers._
import org.scalatest.funsuite.*

import atk.compbio.fastq.FastQFile

object TestFastQ {

}

class TestFastQ extends AnyFunSuite {

  test("test fq") {
    for (entry <- FastQFile("src/test/resources/test.fastq")) {
      println(entry)
    }

    FastQFile("src/test/resources/test.fastq").filter(_.name.contains("1")).toList.map(println(_))
  }

}