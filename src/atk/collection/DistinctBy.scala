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
 * Copyright 2014 Thomas Abeel
 */

package atk.collection
/**
 * Trait to add an additional distinctBy method to any Seq.
 *
 * This code is based on gist:1189097
 * https://gist.github.com/daithiocrualaoich/1189097
 *
 */
trait DistinctBy {
  implicit def seq2Distinct[T, C[T] <: Seq[T]](tees: C[T]) = new {
    import collection.generic.CanBuildFrom
    import collection.mutable.{ HashSet => MutableHashSet }

    def distinctBy[S](hash: T => S)(implicit cbf: CanBuildFrom[C[T], T, C[T]]): C[T] = {
      val builder = cbf()
      val seen = MutableHashSet[S]()

      for (t <- tees) {
        if (!seen(hash(t))) {
          builder += t
          seen += hash(t)
        }
      }

      builder.result
    }
  }
}