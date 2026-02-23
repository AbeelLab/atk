package atk.util

import org.scalatest.funsuite.*

object TestTool extends Tool {
  def main(args: Array[String]): Unit= {

  }
}

class TestTool extends AnyFunSuite with Tool {

  test("test time stamp") {
    println(timestamp())
  }

  test("testLog") {
    log("Test")
  }
  
  test("testConfigOutput"){
    case class Config(val input:String="inputString");
    val config=new Config;
    val s=generatorInfo(config)
    println(s)
    assert(s.contains("(input,inputString)"))
    
  }

}