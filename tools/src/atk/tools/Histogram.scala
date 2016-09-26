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

object Histogram extends Tool {
  case class HistogramConfig(val input: File = null, val outputPrefix: String = "histogram", val column: Int = 0, val bin: Int = 1, val x: String = "X-axis", val y: String = "Y-axis", val log: Boolean = false)

  def main(args: Array[String]): Unit = {

    val parser = new scopt.OptionParser[HistogramConfig]("java -jar atk.jar histogram") {
      opt[File]('i', "input") required () action { (x, c) => c.copy(input = x) } text ("Input data")
      opt[String]('o', "output") action { (x, c) => c.copy(outputPrefix = x) } text ("Output prefix")
      opt[String]('x', "x-label") action { (x, c) => c.copy(x = x) } text ("X-axis label")
      opt[String]('y', "y-label") action { (x, c) => c.copy(y = x) } text ("Y-axis label")
      opt[Int]('c', "column") action { (x, c) => c.copy(column = x) } text ("Column from which to extract values. Default = 0")
      opt[Unit]("log") action { (x, c) => c.copy(log = true) } text ("Take log of values. Default = false")
    }
    parser.parse(args, HistogramConfig()) map { config =>
      histo(config)

    }
  }

  private def histo(config: HistogramConfig) {
    assume(config.input.exists(), "Input file does not exist: " + config.input)
    val values = tLines(config.input).map { f =>
      val v = f.split("\t")(config.column).toDouble
      if (config.log)
        math.log10(v)
      else
        v
    }

    plot(values,config.outputPrefix, config.x,config.y)
  }

  private def binbin(data: List[Double]): Map[Double, Double] = {

    val ds = new DescriptiveStatistics(data.toArray);
    val q1 = ds.getPercentile(25)
    val q2 = ds.getPercentile(50)
    val q3 = ds.getPercentile(75)
    /* Freedman-Diaconis rule */
    val h = 2.0 * (q3 - q1) * Math.pow(data.length, -1.0 / 3)
    // double bottom=0;

    println("n=" + data.length)
    println("pow=" + Math.pow(data.length, -1 / 3))
    println("Q1=" + q1)
    println("Q2=" + q2)
    println("Q3=" + q3)
    //		println("Min=" + ds.getMin() + "\tMax=" + top + "\t" + data.length+"\t"+h);
    println("h: " + h)
    //		val range=(ds.getMax() - ds.getMin()) / h
    val range = h
    println("range= " + range)
    val binned = data.groupBy(g => (g / range).toInt).mapValues(_.size).map(f => (f._1 * range, f._2.toDouble / data.size))

    binned
    //		double ysum = 0;
    //		for (int i = 0; i < bins[1].length; i++)
    //			ysum += bins[1][i];
    //
    //		for (int i = 0; i < bins[1].length; i++)
    //			bins[1][i] /= ysum;
    //		;
    //		return bins;
  }

  def plot(values: List[Double],outputPrefix:String, x:String,y:String) {
    val input = binbin(values)

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

    val chart = ChartFactory.createXYBarChart(null, x, false, y, new XYBarDataset(dcd, w),
      PlotOrientation.VERTICAL, false, false, false);
    // if (countNormalization) {
    // chart.getXYPlot().getRangeAxis().setRange(0, 0.5);
    // }

    /* Move legends */
    val lt = new LegendTitle(chart.getXYPlot());
    lt.setItemFont(new Font("Dialog", Font.PLAIN, 9));
    lt.setBackgroundPaint(new Color(0xff, 0xff, 0xcc, 100));
    lt.setPosition(RectangleEdge.LEFT);
    lt.setFrame(new LineBorder(Color.GRAY, new BasicStroke(), new RectangleInsets()));
    // XYTitleAnnotation ta = new XYTitleAnnotation(0.02, 0.98, lt,
    // RectangleAnchor.TOP_LEFT);

    // ta.setMaxWidth(0.48);
    // chart.getXYPlot().addAnnotation(ta);

    val xy = chart.getXYPlot().getRenderer().asInstanceOf[XYBarRenderer];
    // chart.getXYPlot().getDomainAxis().setRange(50, 100);
    chart.setBackgroundPaint(Color.WHITE);
    chart.getXYPlot().setBackgroundPaint(Color.WHITE);
    xy.setShadowVisible(false);
    // xy.setDrawBarOutline(false);
    // xy.setDefaultBarPainter(new StandardXYBarPainter());
    xy.setBarPainter(new StandardXYBarPainter());
    xy.setGradientPaintTransformer(null);
    // xy.setDefaultBarPainter(new StandardXYBarPainter());
    // System.out.println(xy.getBaseFillPaint());
    // System.out.println(xy.getBaseShape());
    // System.out.println(xy.getBaseStroke());
    val colors = List(Color.black, Color.red, new Color(0, 0, 0xCC), Color.GREEN, new Color(0xFF, 0x99, 0x33))
    for (i <- 0 until chart.getXYPlot().getSeriesCount()) {
      xy.setSeriesPaint(i, colors(i));

    }

    GraphicsFileExport.exportPNG(new JFreeChartWrapper(chart), outputPrefix, 1024, 800);

  }

  //	public static void plotReverseCumulative(double[] dd, String string) {
  //		double[][] data = binbin(dd);
  //
  //		if (data == null)
  //			return;
  //		for (int i = data[0].length - 2; i >= 0; i--) {
  //			data[1][i] = data[1][i + 1] + data[1][i];
  //		}
  //
  //		plot(data, string);
  //
  //	}
  //
  //	public static void plotCumulative(double[] dd, String string) {
  //		double[][] data = binbin(dd);
  //
  //		if (data == null)
  //			return;
  //		for (int i = 1; i < data[0].length; i++) {
  //			data[1][i] = data[1][i - 1] + data[1][i];
  //		}
  //
  //		plot(data, string);
  //
  //	}

}