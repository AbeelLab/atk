/**
 * %HEADER%
 */
package atk;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.junit.Test;

import be.abeel.graphics.Drawable;
import be.abeel.graphics.GraphicsFileExport;

public class TestGraphics {

    class Rect implements Drawable {

        public void draw(Graphics2D g, Rectangle2D rec) {
            g.setColor(Color.white);
            g.fillRect((int) rec.getMinX(), (int) rec.getMinY() , (int) rec.getWidth(), (int) rec
                    .getHeight());
            g.setColor(Color.black);
            g.drawRect((int) rec.getMinX() + 5, (int) rec.getMinY() + 5, (int) rec.getWidth() - 10, (int) rec
                    .getHeight() - 10);
            g.setColor(Color.red);
            g.fillOval((int) rec.getMinX() + 5, (int) rec.getMinY() + 5, (int) rec.getWidth() - 10, (int) rec
                    .getHeight() - 10);

        }

    }

    @Test
    public void testGraphics() {
        System.out.println("BMP");
        GraphicsFileExport.exportBMP(new Rect(), "rect", 100, 50);
        System.out.println("PDF");
        GraphicsFileExport.exportPDF(new Rect(), "rect", 100, 50);
        System.out.println("PNG");
        GraphicsFileExport.exportPNG(new Rect(), "rect", 100, 50);
        System.out.println("ICO");
        GraphicsFileExport.exportICO(new Rect(), "rect", 64, 64);
        System.out.println("JPG");
        GraphicsFileExport.exportJPG(new Rect(), "rect", 100, 50);
        System.out.println("GIF");
        GraphicsFileExport.exportGIF(new Rect(), "rect", 100, 50);
        
    }

}
