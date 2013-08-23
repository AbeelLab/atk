/*
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit
 * http://creativecommons.org/licenses/by-nc-nd/3.0/
 * or send a letter to Creative Commons, 444 Castro Street,
 * Suite 900, Mountain View, California, 94041, USA.
 *
 * A copy of the license is included in LICENSE.txt
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * Copyright 2005-2013 Thomas Abeel
 */
package atk.util

import java.io.PrintWriter
import java.util.Date
import java.lang.management.ManagementFactory
import scala.io.Source
import java.io.File

/**
 * Utility methods to create tools
 *
 * @author Thomas Abeel
 */
trait Tool extends Lines {

  private var logger: PrintWriter = null;

  private val startTime = System.currentTimeMillis();

  def log(str: Any) = {
    if (logger == null) {
      logger.print(generatorInfo + "\n")
      new PrintWriter(classFileName + "." + System.currentTimeMillis() + ".log")
    }
    logger.println(str)
    logger.flush()
    println(str)
  }

  def finish(out: PrintWriter = logger) = {
    print("## This analysis finished " + new Date() + "\n")
    if (out != null) {
      out.print("## This analysis finished " + new Date() + "\n")
      out.print("## Run time: " + new TimeInterval(System.currentTimeMillis() - startTime) + "\n")
      out.close()
    }
  }
  def classFileName() = { Thread.currentThread().getStackTrace()(2).getFileName() };

  def classInfo() = { Thread.currentThread().getStackTrace()(2).getClassName() };

  def generatorInfo() = { "# Generated with " + classInfo() + " defined in " + classFileName() + " on " + new Date() + "\n# Please contact Thomas (tabeel@broadinstitute.org) for problems or questions\n#\n# Configuration summary: \n#\t " + "Current date and time: " + new Date() + "\n#\t " + "Number of processors: " + Integer.toString(Runtime.getRuntime().availableProcessors()) + "\n#\t " + "Free memory :" + Runtime.getRuntime().freeMemory() + "\n#\t " + "Max memory: " + Runtime.getRuntime().maxMemory() + "\n#\t " + "Total JVM: " + Runtime.getRuntime().totalMemory() + "\n#\t " + "OS: " + ManagementFactory.getOperatingSystemMXBean().getName() + " " + ManagementFactory.getOperatingSystemMXBean().getVersion() + "\n#\t " + "Architecture: " + ManagementFactory.getOperatingSystemMXBean().getArch() + "\n#\t " + "JVM version: " + System.getProperty("java.version") }

}