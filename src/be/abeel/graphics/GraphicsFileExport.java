/**
 * %HEADER%
 */
package be.abeel.graphics;


/**
 * This class provides utility methods to export objects that implement the
 * Drawable interface to files.
 * 
 * @author Thomas Abeel
 * 
 */
public class GraphicsFileExport {

    /**
     * Export the drawable to a file in PNG format.
     * 
     * @param d
     *            the object to export
     * @param fileName
     *            the filename of the file
     * @param x
     *            the width in pixels
     * @param y
     *            the height in pixels
     */
    public static void exportPNG(Drawable d, String fileName, int x, int y) {
        ImageIOexport.exportPNG(d, fileName, x, y);
    }

    /**
     * Export the drawable to a file in JPG format.
     * 
     * @param d
     *            the object to export
     * @param fileName
     *            the filename of the file
     * @param x
     *            the width in pixels
     * @param y
     *            the height in pixels
     */
    public static void exportJPG(Drawable d, String fileName, int x, int y) {
        ImageIOexport.exportJPG(d, fileName, x, y);
    }

    /**
     * Export the drawable to a file in GIF format.
     * 
     * @param d
     *            the object to export
     * @param fileName
     *            the filename of the file
     * @param x
     *            the width in pixels
     * @param y
     *            the height in pixels
     */
    public static void exportGIF(Drawable d, String fileName, int x, int y) {
        ImageIOexport.exportGIF(d, fileName, x, y);
    }

    /**
     * Export the drawable to a file in PDF format
     * 
     * @param d
     *            the object to export
     * @param fileName
     *            the filename of the file
     * @param x
     *            the width in pixels
     * @param y
     *            the height in pixels
     */
    public static void exportPDF(Drawable d, String fileName, int x, int y) {
        PDFexport.exportPDF(d, fileName, x, y);
    }

    /**
     * Export the Drawable to a file in BMP format.
     * 
     * @param d
     *            the object to export
     * @param fileName
     *            the filename of the file
     * @param x
     *            the width in pixels
     * @param y
     *            the height in pixels
     */
    public static void exportBMP(Drawable d, String fileName, int x, int y) {
       Image4Jexport.exportBMP(d,fileName,x,y);
    }

    /**
     * Export the Drawable to a file in ICO format.
     * 
     * @param d
     *            the object to export
     * @param fileName
     *            the filename of the file
     * @param x
     *            the width in pixels
     * @param y
     *            the height in pixels
     */
    public static void exportICO(Drawable d, String fileName, int x, int y) {
        Image4Jexport.exportICO(d,fileName,x,y);
    }

    
}
