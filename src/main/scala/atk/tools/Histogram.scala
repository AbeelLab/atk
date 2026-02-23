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
import org.jfree.chart.axis.ValueAxis

object Histogram extends Tool {
  case class HistogramConfig(val input: File = null, val outputPrefix: String = "histogram", val column: Int = 0, val bin: Int = 1, val x: String = "X-axis", val y: String = "Y-axis", val log: Boolean = false, val limit: Int = -1, val tab: Boolean = false, val nobin: Boolean = false,val domainStart:Double=Double.NaN,val domainEnd:Double=Double.NaN)

  def main(args: Array[String]): Unit = {

    val parser = new scopt.OptionParser[HistogramConfig]("java -jar atk.jar histogram") {
      opt[File]('i', "input").required().action { (x, c) => c.copy(input = x) }.text ("Input data")
      opt[String]('o', "output").action{ (x, c) => c.copy(outputPrefix = x) }.text ("Output prefix")
      opt[String]('x', "x-label").action{ (x, c) => c.copy(x = x) }.text ("X-axis label")
      opt[String]('y', "y-label").action{ (x, c) => c.copy(y = x) }.text ("Y-axis label")
      opt[Int]('c', "column").action { (x, c) => c.copy(column = x) }.text ("Column from which to extract values. Default = 0")
      opt[Int]("stdev-limit").action { (x, c) => c.copy(limit = x) }.text ("Maximum standard devitations on domain. Default = unlimited")
      opt[Unit]("log").action { (x, c) => c.copy(log = true) }.text ("Take log of values. Default = false")
      opt[Unit]("tabulated").action { (x, c) => c.copy(tab = true) }.text ("Export tab-delimited file with data in histogram")
      opt[Unit]("no-bin").action { (x, c) => c.copy(nobin = true) }.text ("Do not automagically bin data")
     
    }
    parser.parse(args, HistogramConfig()) map { config =>
      histo(config)

    }
  }

  private def histo(config: HistogramConfig): Unit =  {
    assume(config.input.exists(), "Input file does not exist: " + config.input)
    val values = tLines(config.input).map { f =>
      val v = f.split("\t")(config.column).toDouble
      if (config.log)
        math.log10(v)
      else
        v
    }

    plot(values, config)
  }

  private def binbin(data: List[Double], config: HistogramConfig): Map[Double, Double] = {

    val dsA = new DescriptiveStatistics(data.toArray);
    val q1A = dsA.getPercentile(25)
    val q2A = dsA.getPercentile(50)
    val q3A = dsA.getPercentile(75)
    println("n=" + data.length)
    println("pow=" + Math.pow(data.length, -1 / 3))
    println("Q1=" + q1A)
    println("Q2=" + q2A)
    println("Q3=" + q3A)
    val sd = dsA.getStandardDeviation

    println("Truncating data...")
    val cleanData = if (config.limit > 0)
      data.filter(d => d >= q2A - config.limit * sd && d <= q2A + config.limit * sd)
    else data

    val ds = new DescriptiveStatistics(cleanData.toArray);
    val q1 = ds.getPercentile(25)
    val q2 = ds.getPercentile(50)
    val q3 = ds.getPercentile(75)

    println("n=" + cleanData.length)
    println("Q1=" + q1)
    println("Q2=" + q2)
    println("Q3=" + q3)

    /* Freedman-Diaconis rule */
    val h = 2.0 * (q3 - q1) * Math.pow(data.length, -1.0 / 3)
    println("h: " + h)
    val range = h
    println("range= " + range)
    val binned:Map[Double,Double] = cleanData.groupBy(g => (g / range).toInt)
    	.map{ case (k,v) => ( k*range, v.size.toDouble / cleanData.size) }
    	// mapValues is lazy and does not return the correct type.
    	//.mapValues(_.size).map(f => (f._1 * range, f._2.toDouble / cleanData.size))

    binned

  }

  def plot(values: List[Double], config: HistogramConfig): Unit = {
    val input = if (config.nobin) {
      values.groupBy { identity }.mapValues(_.size.toDouble)
    } else {
      binbin(values, config)
    }

    if (config.tab) {
      val pw = new NixWriter(config.outputPrefix + ".tabulated.tsv", config)
      input.map(p => pw.println(p._1 + "\t" + p._2))
      pw.close
    }

    val dcd = new DefaultXYDataset();

    val data = input.toList.sortBy(_._1)
    val arr = Array.ofDim[Array[Double]](2)
    arr(0) = Array.ofDim(data.size)
    arr(1) = Array.ofDim(data.size)
    for (i <- 0 until data.size) {

      arr(0)(i) = data(i)._1
      arr(1)(i) = data(i)._2
    }
    dcd.addSeries("series", arr);

    val w = (arr(0)(0) - arr(0)(1)) / 1.1

    val chart = ChartFactory.createXYBarChart(null, config.x, false, config.y, new XYBarDataset(dcd, w),
      PlotOrientation.VERTICAL, false, false, false);

    /* Move legends */
    val lt = new LegendTitle(chart.getXYPlot());
    lt.setItemFont(new Font("Dialog", Font.PLAIN, 9));
    lt.setBackgroundPaint(new Color(0xff, 0xff, 0xcc, 100));
    lt.setPosition(RectangleEdge.LEFT);
    lt.setFrame(new LineBorder(Color.GRAY, new BasicStroke(), new RectangleInsets()));

    val xy = chart.getXYPlot().getRenderer().asInstanceOf[XYBarRenderer];
    val origDomain=chart.getXYPlot.getDomainAxis
    if(!config.domainStart.isNaN())
      origDomain.setLowerBound(config.domainStart)
    if(!config.domainEnd.isNaN())
      origDomain.setUpperBound(config.domainEnd)
        
    
    chart.setBackgroundPaint(Color.WHITE);
    chart.getXYPlot().setBackgroundPaint(Color.WHITE);
    xy.setShadowVisible(false);
    xy.setBarPainter(new StandardXYBarPainter());
    xy.setGradientPaintTransformer(null);
    val colors = List(Color.black, Color.red, new Color(0, 0, 0xCC), Color.GREEN, new Color(0xFF, 0x99, 0x33))
    for (i <- 0 until chart.getXYPlot().getSeriesCount()) {
      xy.setSeriesPaint(i, colors(i));

    }

    GraphicsFileExport.exportPNG(new JFreeChartWrapper(chart), config.outputPrefix, 1024, 800);

  }

}