package atk.tools

import be.abeel.graphics.GraphicsFileExport
import java.awt.Color
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.data.xy.DefaultXYDataset
import org.jfree.chart.title.LegendTitle
import org.jfree.ui.RectangleEdge
import java.awt.BasicStroke
import org.jfree.ui.RectangleInsets
import org.jfree.chart.block.LineBorder
import java.awt.Font
import org.jfree.chart.ChartFactory
import be.abeel.jfreechart.JFreeChartWrapper
import org.jfree.chart.plot.PlotOrientation

trait ScatterTrait {
  def scatterPlot(data: List[(Double, Double)], xLabel: String, yLabel: String, output: String, minX:Double=Double.MinValue, maxX:Double=Double.MaxValue, minY:Double=Double.MinValue, maxY:Double=Double.MaxValue): Unit = {

    val arr = Array.ofDim[Array[Double]](2)
    arr(0) = Array.ofDim(data.size)
    arr(1) = Array.ofDim(data.size)
    for (i <- 0 until data.size) {
      arr(0)(i) = data(i)._1
      arr(1)(i) = data(i)._2
    }
    val dcd = new DefaultXYDataset();
    dcd.addSeries("series", arr);

    val chart = ChartFactory.createScatterPlot("Scatterplot", xLabel, yLabel, dcd, PlotOrientation.VERTICAL, false, false, false)
    //    (null, x, false, y, new XYBarDataset(dcd, w),
    //      PlotOrientation.VERTICAL, false, false, false);
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

    val xy = chart.getXYPlot().getRenderer().asInstanceOf[XYLineAndShapeRenderer];
    
    val xStart=if(minX>Double.MinValue)minX else chart.getXYPlot.getDomainAxis.getRange.getLowerBound
    val xEnd=if(maxX<Double.MaxValue)maxX else chart.getXYPlot.getDomainAxis.getRange.getUpperBound
    val yStart=if(minY>Double.MinValue)minY else chart.getXYPlot.getRangeAxis.getRange.getLowerBound
    val yEnd=if(maxY<Double.MaxValue)maxY else chart.getXYPlot.getRangeAxis.getRange.getUpperBound
    
    chart.getXYPlot().getDomainAxis().setRange(xStart, xEnd);
    chart.getXYPlot().getRangeAxis().setRange(yStart, yEnd);
    
    chart.setBackgroundPaint(Color.WHITE);
    chart.getXYPlot().setBackgroundPaint(Color.WHITE);
    //    xy.setShadowVisible(false);
    //    // xy.setDrawBarOutline(false);
    //    // xy.setDefaultBarPainter(new StandardXYBarPainter());
    //    xy.setBarPainter(new StandardXYBarPainter());
    //    xy.setGradientPaintTransformer(null);
    // xy.setDefaultBarPainter(new StandardXYBarPainter());
    // System.out.println(xy.getBaseFillPaint());
    // System.out.println(xy.getBaseShape());
    // System.out.println(xy.getBaseStroke());
    
    val colors = List(new Color(0,0,0,100), Color.red, new Color(0, 0, 0xCC), Color.GREEN, new Color(0xFF, 0x99, 0x33))
    for (i <- 0 until chart.getXYPlot().getSeriesCount()) {
      xy.setSeriesPaint(i, colors(i));

    }

    GraphicsFileExport.exportPNG(new JFreeChartWrapper(chart), output, 1024, 800);

  }
}