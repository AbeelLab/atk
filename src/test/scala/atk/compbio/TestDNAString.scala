package atk.compbio

import org.scalatest.funsuite.*
//import org.scalatest.Matchers._

object TestDNAString {

}

class TestDNAString extends AnyFunSuite {
  val str = "ACTGATCACCAGACTGATCG"
  val dna = new DNAString(str)
  test("test string") {

    assert(dna.size == str.size)
    assert(dna.get(0) == str.charAt(0))
    assert(dna.get(10) == str.charAt(10))
    assert(dna.get(11) == str.charAt(11))

    assert(dna(0) == 'A')
    println(str)
    for (i <- 0 until str.size)
      print(dna(i))

  }

  test("test oob") {
 
    assertThrows[IndexOutOfBoundsException](dna.get(-1))
    assertThrows[IndexOutOfBoundsException](dna.get(dna.size))
  }

}