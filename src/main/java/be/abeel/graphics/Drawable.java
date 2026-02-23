/**
 * %HEADER%
 */
package be.abeel.graphics;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * This interface provides the methods that should be implemented for object
 * that can paint themselves on a graphics context within a rectangle.
 * 
 * @author Thomas Abeel
 * 
 */
public interface Drawable {
    /**
     * Draw the object onto the graphics context within the provided rectangle.
     * 
     * @param g the graphics context
     * @param rec the rectangle
     */
    public void draw(Graphics2D g, Rectangle2D rec);
}
