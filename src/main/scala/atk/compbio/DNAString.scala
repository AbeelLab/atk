package atk.compbio
/**
 * Efficient compact implementation to start DNA strings
 *
 * This implementation only supports ACGTN- in the DNA alphabet and will silently lose coding for other letters.
 *
 */
class DNAString(str: String) {

  def this(arr:Array[Byte]) = {
   this(new String(arr)) 
  }
  
  private val len = str.size
  def size = len
  def apply(i: Int) = get(i)

  private var hash: Long = 1
  override def hashCode() = hash.toInt
  override def equals(o: Any) = {
    o.isInstanceOf[DNAString] && o.asInstanceOf[DNAString].len == this.len && o.asInstanceOf[DNAString].hash == this.hash

  }

  private var n: Boolean = false
  private var gap: Boolean = false
  private var undefined: Boolean = false
  

  def hasN = n
  def hasGap = gap
  def hasUndefined=undefined

  def longHashCode = hash

  private val bytes = Array.ofDim[Byte]((str.length() + 1) / 2)

  for (i <- 0 until len) {

    set(i, str.charAt(i));
  }

  private def decode(b: Int): Char = b match {

    case 1 => 'A'
    case 2 => 'C'
    case 3 => 'G'
    case 4 => 'T'
    case 5 => 'N'
    case 6 => '-'
    case _ => '_'
  }

  private def encode(c: Char) = c match {

    case 'a' | 'A' =>
      1;
    case 'c' | 'C' =>
      2;
    case 'g' | 'G' =>
      3;
    case 't' | 'T' =>
      4;
    case 'n' | 'N' =>{
      n=true
      5;
    }
    case '-' =>{
      gap=true
      6;
    }
    case _ =>{
      undefined=true
      0;
    }
  }

  /* Zero based setter for the sequence */
  private def set(pos: Int, c: Char):Unit= {
    var coded = encode(c);
    var mask = 15
    if (pos % 2 == 1) {
      coded = coded << 4;

    } else
      mask <<= 4;

    var current = bytes(pos / 2).toInt;

    hash += current
    hash *= 8

    current &= mask;

    var newCurrent = current | coded;
    bytes(pos / 2) = newCurrent.toByte
  }

  /* Zero based getter for the sequence */

  def get(pos: Int): Char = {
    if (pos < 0 || pos >= len)
      throw new IndexOutOfBoundsException(pos + " is out of bounds")

    var current = bytes(pos / 2).toInt;
    var mask = 15;
    if (pos % 2 == 1) {
      current >>= 4;
    } else {
      current &= mask;
    }
    return decode(current);
  }

  override def toString() = {
    (0 until len).map(i => get(i)).mkString("")
  }

}
