package atk.util

import java.awt.Color
/**
 * Color palette based on Wong et al 2011
 * http://www.nature.com/nmeth/journal/v8/n6/fig_tab/nmeth.1618_F2.html
 *
 *  @author Thomas Abeel
 */
object ColorPalette {

  private val colors = List(new Color(230, 159, 0),//orange 
      new Color(86, 180, 233),//sky blue
      new Color(0, 158, 115), //bluish green
      new Color(240, 228, 66), //yellow
      new Color(0, 114, 178), //blue
      new Color(213, 94, 0), //vermillion
      new Color(204, 121, 167),//reddish purple
      Color.GRAY,Color.LIGHT_GRAY)

  def apply(n: Int): Color = {
    colors(n % colors.length)
  }
}