package atk.tools

import java.io.File
import atk.util.Tool
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import org.apache.commons.math3.stat.descriptive.SummaryStatistics
import atk.io.NixWriter

object ZscoreNormalize extends Tool {

  case class Config(val input: File = null, val output: File = null, val column: Int = 0)

  override val description = """Tool to normalize a single value column in a file."""

  def main(args: Array[String]): Unit = {

    val parser = new scopt.OptionParser[Config]("java -jar atk.jar zscore") {
      opt[File]('i', "input").required().action { (x, c) => c.copy(input = x) }.text ("Input data")
      opt[File]('o', "output").action { (x, c) => c.copy(output = x) }.text ("Output file")
      opt[Int]('c', "column").action { (x, c) => c.copy(column = x) }.text ("Column from which to extract and replace values with z-scores. Default = " + new Config().column)

    }
    parser.parse(args, Config()) map { config =>
      zscore(config)

    }

  }
  private def zscore(config: Config): Unit= {

    val summary=new SummaryStatistics
    
    tColumn(config.column, tLines(config.input)).map(f=>summary.addValue(f.toDouble))
    
    val pw=new NixWriter(config.output,config)
    tLines(config.input).map{line=>
      
      val arr=line.split("\t")
      val value=arr(config.column).toDouble
      val zscore=(value-summary.getMean)/summary.getStandardDeviation
      
      arr(config.column)=""+zscore
      
      pw.println(arr.mkString("\t"))
      
      
    }
    pw.close
    
  }
}