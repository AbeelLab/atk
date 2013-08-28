package atk.util

import org.scalatest.FunSuite

object TestTool extends Tool {
  def main(args: Array[String]) {

  }
}

class TestTool extends FunSuite with Tool {

  test("test time stamp") {
    println(timestamp)
  }

  test("testLog") {
    log("Test")
  }

}