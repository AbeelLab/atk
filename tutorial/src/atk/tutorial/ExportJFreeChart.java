/**
 * %HEADER%
 */
package atk.tutorial;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import be.abeel.graphics.GraphicsFileExport;
import be.abeel.jfreechart.JFreeChartWrapper;

public class ExportJFreeChart {

    public static void main(String[] args) {
        XYSeries ser = new XYSeries("Sample");

        ser.add(0, 1);
        ser.add(1, 5);
        ser.add(2, 3);
        ser.add(3, 0);
        XYSeriesCollection col = new XYSeriesCollection();
        col.addSeries(ser);
        JFreeChart chart = ChartFactory.createXYLineChart("Sample chart", "x-axis", "y-axis", col,
                PlotOrientation.VERTICAL, true, false, false);
        GraphicsFileExport.exportPDF(new JFreeChartWrapper(chart), "export", 640, 480);

    }
}
