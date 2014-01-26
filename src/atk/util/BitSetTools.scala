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

import scala.collection.BitSet
/**
 * @author Thomas Abeel
 */
trait BitSetTools {

  def bitset2String(bs: BitSet, len: Int): String = {
    val str = "0" * len
    val bytes = str.getBytes()
    bs.map(f => bytes(f) = '1')
    new String(bytes)
  }

}