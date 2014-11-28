package atk.util

import java.awt.Color

object ColorTools {

  def combineColorsAsAlpha(bg: Color, color: Color, alpha: Float): Color = {
    val a = alpha

    new Color(
      math.max(math.min(((1 - a) * bg.getRed() + a * color.getRed()).toInt, 255), 0),
      math.max(math.min(((1 - a) * bg.getGreen() + a * color.getGreen()).toInt, 255), 0),
      math.max(math.min(((1 - a) * bg.getBlue() + a * color.getBlue).toInt, 255), 0))
  }

  val LIGHEST_GRAY = new Color(230, 230, 230);
  /**
   * Make a string encoding of the color
   *
   * @param c
   *            the color to encode
   * @return a string representation for the color
   */
  def encode(c: Color): String = {
    //return "RGB(" + c.getRed() +","+ c.getGreen()+"," + c.getBlue() + ")";
    val rgb = Integer.toHexString(c.getRGB());
    val out = "#" + rgb.substring(2, rgb.length());
    out

  }

  def checkedColor(r: Int, g: Int, b: Int): Color = {
    val rl = if (r < 0) 0 else r;
    val gl = if (g < 0) 0 else g;
    val bl = if (b < 0) 0 else b;

    val ru = if (rl > 255) 255 else rl;
    val gu = if (g > 255) 255 else gl;
    val bu = if (b > 255) 255 else bl;

    new Color(ru, gu, bu);
  }

  def getColorCoding(v: Double): Color = {
    var value = v;
    var average1 = 0.33333;
    var average2 = 0.66667;
    if (value > average2) { // red-yellow
      value -= average2;
      value /= 1 - average2;
      return checkedColor(255, 255 - (value * 255).toInt, 0);
    } else if (value > average1) { // yellow-green
      value -= average1;
      value /= (average2 - average1);
      return checkedColor((value * 255).toInt, 255, 0);
    } else { // green-blue
      value -= 0;
      value /= (average1 - 0);
      return checkedColor(0, (value * 255).toInt, 255 - (value * 255).toInt);
    }

  }

  /**
   * Takes a color String in the format of RGB(FFFFFF) or a common color name
   * (red, blue, gray, black, ...) (case insensitive) and returns the matching
   * <code>Color</code>
   *
   * @param colorString
   *            string with RGB value or textual color. Will always return a
   *            color. If no matching color is found, returns gray.
   * @return the matching color object.
   */
  def decodeColor(colorString: String): Color = {
    if (colorString == null)
      Color.GRAY;

    val color = if (colorString.startsWith("#")) {
      Color.decode(colorString);
    } else if (colorString.startsWith("RGB")) {
      // extract RGB value and create Color object
      val rgb = colorString.substring(colorString.indexOf('(') + 1, colorString.indexOf(')'));
      val arr = rgb.split(",");
      new Color(Integer.parseInt(arr(0)), Integer.parseInt(arr(1)), Integer.parseInt(arr(2)));
    } else if (colorString.contains(",")) {
      val arr = colorString.split(",");
      new Color(Integer.parseInt(arr(0)), Integer.parseInt(arr(1)), Integer.parseInt(arr(2)));
    } else if(colorString.startsWith("0x")){
      Color.decode(colorString)
    }else{
      // if a color name is given, see if a Color constant is
      // defined, otherwise use gray.
      val colorField = classOf[Color].getDeclaredField(colorString);
      colorField.get(classOf[Color]).asInstanceOf[Color]
    }
    color
  }

  def getShadeColor(shapeColor: Color) = {
    // TODO better measurement for darkness?
    if (shapeColor == Color.BLUE) {
      Color.CYAN;
    } else if (shapeColor.getBlue() < 20 && shapeColor.getRed() < 50 && shapeColor.getGreen() < 50
      || (shapeColor.getBlue() > 180 && (shapeColor.getRed() < 100 && shapeColor.getGreen() < 100))) {
      shapeColor.brighter().brighter().brighter().brighter().brighter().brighter();
    } else {
      shapeColor.darker();
    }
  }

  def getTextColor(shapeColor: Color): Color = {
    if (shapeColor == Color.BLUE) {
      Color.WHITE;
    } else if (shapeColor.getBlue() < 20 && shapeColor.getRed() < 50 && shapeColor.getGreen() < 50
      || (shapeColor.getBlue() > 180 && (shapeColor.getRed() < 100 && shapeColor.getGreen() < 100))) {
      Color.WHITE;
    } else {
      Color.BLACK;
    }
  }

}