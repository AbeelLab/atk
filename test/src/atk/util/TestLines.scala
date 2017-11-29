package atk.util

import org.scalatest.FunSuite
import java.io.PrintWriter
import atk.io.NixWriter

object TestLines extends Lines {
  def main(args: Array[String]) {

  }
}

class TestLines extends FunSuite with Lines {

  test("testReadComment") {
    val pw = new PrintWriter("tmp.txt")
    pw.println("# Test\nTest")
    pw.close
    val lines = tLines("tmp.txt")
     println(lines)
    assert(lines.size == 1)

  }

  test("testNixWriter") {
    val pw = new NixWriter("tmp2.txt")
    pw.println("# Test\nTest")
    pw.close
    val lines = tLines("tmp2.txt")
    println(lines)
    assert(lines.size == 1)

  }
}