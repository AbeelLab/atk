/**
 * %HEADER%
 */
package be.abeel.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import be.abeel.io.ExtensionManager;

public class ImageIOexport {
    private static void imageIOExport(String type, Drawable d, String fileName, int x, int y) {
    	System.out.println("EXporting");
        BufferedImage bi = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bi.getGraphics();
        d.draw(g, new Rectangle(x, y));
        try {
            ImageIO.write(bi, type, new File(fileName));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void exportPNG(Drawable d, String fileName, int x, int y) {
        imageIOExport("PNG", d, ExtensionManager.extension(fileName, ExtensionManager.PNG), x, y);

    }

    public static void exportJPG(Drawable d, String fileName, int x, int y) {
        imageIOExport("JPG", d, ExtensionManager.extension(fileName, ExtensionManager.JPG), x, y);

    }

    public static void exportGIF(Drawable d, String fileName, int x, int y) {
        imageIOExport("GIF", d, ExtensionManager.extension(fileName, ExtensionManager.GIF), x, y);
        
    }
}
