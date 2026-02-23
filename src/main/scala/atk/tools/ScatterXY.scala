package atk.tools

import java.io.File
import atk.util.Tool
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import org.jfree.data.xy.DefaultXYDataset
import org.jfree.chart.ChartFactory
import org.jfree.chart.title.LegendTitle
import java.awt.Font
import java.awt.Color
import org.jfree.ui.RectangleEdge
import org.jfree.chart.block.LineBorder
import java.awt.BasicStroke
import org.jfree.ui.RectangleInsets
import org.jfree.data.xy.XYBarDataset
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.renderer.xy.XYBarRenderer
import org.jfree.chart.renderer.xy.StandardXYBarPainter
import be.abeel.graphics.GraphicsFileExport
import be.abeel.jfreechart.JFreeChartWrapper
import atk.io.NixWriter
import org.jfree.data.xy.XYSeries
import org.jfree.chart.renderer.category.ScatterRenderer
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer

object ScatterXY extends Tool with ScatterTrait{
  case class Config(val inputX: File = null, val inputY: File = null, val output: File = null, val x: String = null, val y: String = null, val log: Boolean = false, val valueColumn: Int = 1, val keyColumn: Int = 0, val missing: Double = 0)

  def main(args: Array[String]): Unit = {

    val parser = new scopt.OptionParser[Config]("java -jar atk.jar scatter-XY") {
      val default = new Config()
      opt[File]('x', "x-input").required ().action { (x, c) => c.copy(inputX = x) }.text ("Input data X, keys must match Y")
      opt[File]('y', "y-input").required ().action { (x, c) => c.copy(inputY = x) }.text ("Input data Y, keys must match X")
      opt[File]('o', "output").action { (x, c) => c.copy(output = x) }.text ("Output file")
      opt[String]("x-label").action { (x, c) => c.copy(x = x) }.text ("X-axis label")
      opt[String]("y-label").action { (x, c) => c.copy(y = x) }.text ("Y-axis label")
      opt[Int]("value-column").action { (x, c) => c.copy(valueColumn = x) }.text ("Column from which to extract values. Default = " + default.valueColumn)
      opt[Int]("key-column").action { (x, c) => c.copy(keyColumn = x) }.text ("Column from which to extract keys. Default = " + default.keyColumn)
      opt[Double]("missing-value").action { (x, c) => c.copy(missing = x) }.text ("Value to use for missing entries. Default = " + default.missing)
      opt[Unit]("log"). action { (x, c) => c.copy(log = true) }.text ("Take log of values. Default = " + default.log)

    }
    parser.parse(args, Config()) map { config =>
      scatter(config)

    }
  }

  private def scatter(config: Config): Unit = {
    assume(config.inputX.exists(), "Input file does not exist: " + config.inputX)
    assume(config.inputY.exists(), "Input file does not exist: " + config.inputY)

    def vs(input: File) = {
      (tMap(tLines(input), config.keyColumn, config.valueColumn, limitSplit = false)).map(pair => pair._1.toDouble -> pair._2.toDouble).mapValues { v =>
        if (config.log)
          math.log10(v)
        else
          v
      }
    }

    val valuesX = vs(config.inputX)
    val valuesY = vs(config.inputY)

    val data = (for (key <- valuesX.keySet ++ valuesY.keySet) yield {
      valuesX.getOrElse(key, config.missing) -> valuesY.getOrElse(key, config.missing)
    }).toList.sortBy(_._1)
    val xLabel = if (config.x != null) config.x else config.inputX.toString
    val yLabel = if (config.y != null) config.y else config.inputY.toString
    
    scatterPlot(data, xLabel, yLabel, config.output.toString)
  }

 


}
