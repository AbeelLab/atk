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

import scala.collection.mutable.BitSet

/**
 * @author Thomas Abeel
 */
object BitSetTools {

  def toString(bs: BitSet, nbits: Int): String = {
    val bytes=Array.ofDim[Byte](nbits);
    bs.map(f => bytes(f) = 1)
    bytes.mkString("")
  }

  def fromString(str: String): BitSet = {
    val tmp=str.zipWithIndex.filter(f=>f._1=='1').map(_._2)
    BitSet(tmp: _*)
    
   
        
  }

}