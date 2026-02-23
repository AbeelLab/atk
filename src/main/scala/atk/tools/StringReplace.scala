package atk.tools

import atk.util.Tool
import scala.collection.immutable.Map
import java.io.PrintWriter
import java.io.File
import scala.io.Source

object StringReplace extends Tool {

  case class Config(val input: File=null, val replace: File=null, val output: File=null)

  def main(args: Array[String]): Unit = {

    val parser = new scopt.OptionParser[Config]("java -jar atk.jar string-replace") {
      opt[File]('i', "input").required (). action { (x, c) => c.copy(input = x) }.text ("Input file")
      opt[File]('r', "replace").required ().action { (x, c) => c.copy(replace = x) }.text ("Replace key value pairs file")
      opt[File]('o', "output").required ().action { (x, c) => c.copy(output = x) }.text ("Output file")

    }
    parser.parse(args, Config()) map { config =>
      replace(config)

    }
  }

  def replace(config: Config): Unit = {

    val in = Source.fromFile(config.input).getLines.toList.mkString("\n")
    val replace = tMap(tLines(config.replace),keyColumn=0,valueColumn=1,limitSplit=false)

    var tmp=in
    replace.keySet.map {
      g =>
        tmp = tmp.replaceAll(g, replace(g))

    }
    val pw = new PrintWriter(config.output)
    pw.print(tmp)
    pw.close
  }

}