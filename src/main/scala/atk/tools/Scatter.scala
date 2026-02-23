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

object Scatter extends Tool with ScatterTrait {
  case class Config(val input: File = null, val output: File = null, val x: String = "X-axis", val y: String = "Y-axis", val log: Boolean = false, val minX: Double = Double.MinValue, val maxX: Double = Double.MaxValue, val minY: Double = Double.MinValue, val maxY: Double = Double.MaxValue)

  def main(args: Array[String]): Unit = {

    val parser = new scopt.OptionParser[Config]("java -jar atk.jar scatter") {
      val default = new Config()
      opt[File]('i', "input").required().action { (x, c) => c.copy(input = x) }.text ("Input data. This should be a two column file with numberic values.")
      opt[File]('o', "output").action { (x, c) => c.copy(output = x) }.text ("Output file")
      opt[String]('x', "x-label").action { (x, c) => c.copy(x = x) }.text ("X-axis label")
      opt[String]('y', "y-label").action { (x, c) => c.copy(y = x) }.text ("Y-axis label")
      opt[Double]("x-min").action { (x, c) => c.copy(minX = x) }.text ("Minimum value to display on the X-axis")
      opt[Double]("y-min").action { (x, c) => c.copy(minY = x) }.text ("Minimum value to display on the Y-axis")
      opt[Double]("x-max").action { (x, c) => c.copy(maxX = x) }.text ("Maximum value to display on the X-axis")
      opt[Double]("y-max").action { (x, c) => c.copy(maxY = x) }.text ("Maximum value to display on the Y-axis")
      opt[Unit]("log").action { (x, c) => c.copy(log = true) }.text ("Take log of values. Default = " + default.log)

    }
    parser.parse(args, Config()) map { config =>
      scatter(config)

    }
  }

  private def scatter(config: Config): Unit = {

    val data = tColumns(List(0, 1), tLines(config.input)).map { list => list(0).toDouble -> list(1).toDouble }

    scatterPlot(data, config.x, config.y, config.output.toString,config.minX,config.maxX,config.minY,config.maxY)

  }

}
