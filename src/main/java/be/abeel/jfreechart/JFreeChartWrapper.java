/**
 * %HEADER%
 */
package be.abeel.jfreechart;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.JFreeChart;

import be.abeel.graphics.Drawable;

/**
 * Wrapper around a JFreeChart to be able to use make it a Drawable object.
 * 
 * @author Thomas Abeel
 * 
 */
public class JFreeChartWrapper implements Drawable {

    private JFreeChart chart;

    public JFreeChartWrapper(JFreeChart chart) {
        this.chart = chart;
    }

    public void draw(Graphics2D g, Rectangle2D rec) {
        chart.draw(g, rec);

    }
}
