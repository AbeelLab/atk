/**
 * %HEADER%
 */
package be.abeel.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import net.sf.image4j.codec.bmp.BMPEncoder;
import net.sf.image4j.codec.ico.ICOEncoder;
import be.abeel.io.ExtensionManager;

public class Image4Jexport {

    public static void exportBMP(Drawable d, String fileName, int x, int y) {
        try {
            BufferedImage bimage = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bimage.createGraphics();
            d.draw(g2d, new Rectangle(x, y));
            BMPEncoder.write(bimage, new File(ExtensionManager.extension(fileName, ExtensionManager.BMP)));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    public static void exportICO(Drawable d, String fileName, int x, int y) {
        try {
            BufferedImage bimage = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bimage.createGraphics();
            d.draw(g2d, new Rectangle(x, y));
            ICOEncoder.write(bimage, new File(ExtensionManager.extension(fileName, ExtensionManager.ICO)));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
