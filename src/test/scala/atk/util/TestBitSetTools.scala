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
 * Copyright 2005-2016 Thomas Abeel
 */
package atk.util

import org.scalatest.funsuite.*
import scala.collection.mutable.BitSet

/**
 * THIS IS NOT A PROPER TEST-CASE --> needs assume statements
 */

class TestBitSetTools extends AnyFunSuite {

  test("testToString") {

    val fbs = BitSet(1, 2)

    println(BitSetTools.toString(fbs, 5))
  }

  test("constructFromString") {
    val fbs = BitSetTools.fromString("01100")
    println(fbs)
  }
  
  test("testLonger64"){
    val input="00011111101111110111100100000000000000000000000000000111111011111101111001000000000000000000000000000001111110111111011110010000000000000000000000000000011111101111110111100100000000000000000000000000"
    println("1="+input)
    val fbs=BitSetTools.fromString(input)
    println("2="+fbs)
    val str=BitSetTools.toString(fbs, input.size)
    println("3="+str)
  }

}