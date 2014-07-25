package atk.util

import java.awt.Color

object ColorTools {

  def combineColorsAsAlpha(bg: Color, color: Color, alpha: Float): Color = {
    val a = alpha

    new Color(
      math.max(math.min(((1 - a) * bg.getRed() + a * color.getRed()).toInt, 255),0),
      math.max(math.min(((1 - a) * bg.getGreen() + a * color.getGreen()).toInt, 255),0),
      math.max(math.min(((1 - a) * bg.getBlue() + a * color.getBlue).toInt, 255),0))
  }

}