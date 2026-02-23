package atk.util

import java.util.logging.Level
import java.util.logging.LogManager

trait LoggingTrait {
  
  setDebugLevel(Level.INFO)
  
  def setDebugLevel(newLvl: Level):Unit= {
    val rootLogger = LogManager.getLogManager().getLogger("");
    val handlers = rootLogger.getHandlers();
    rootLogger.setLevel(newLvl);
    for (h <- handlers) {

      h.setLevel(newLvl);
    }
  }
}