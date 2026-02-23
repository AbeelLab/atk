package atk.compbio

object DNAHash {

  def main(args: Array[String]):Unit = {
    hash("ACGGTGCGA")

    hash("AACGGTGCGA")
    hash("CGGTGCGA")
  }

  def hash(str: String): Long = {
    hash(str.getBytes)
  }

  def hash(arr: Array[Byte]): Long = {

    assert(arr.size < 32, "Cannot hash string longer than 32 bases")

    val map = arr.map { x => encode(x) }
    val coded:Long = map.foldLeft(0L)((b, a) => b * 4 + a)
//    println(new String(arr))
//    println(map.mkString)
//    println(coded)
//    println(unhash(coded, arr.size))
//    println("--")
    coded

    

  }

  def unhash(h: Long, len: Int): String = {

    if (len > 1) {
      unhash(h / 4, len - 1) + decode(h % 4)

    } else {
      decode(h);
    }

  }

  private def decode(c: Long): String = c match {

    case 0 => "A";
    case 1 => "C";
    case 2 => "G";
    case 3 => "T";
    case _ => { assert(false, "Decoding only individual bases"); "-" }
  }

  private def encode(c: Byte): Int = c match {

    case 'a' | 'A' =>
      0;
    case 'c' | 'C' =>
      1;
    case 'g' | 'G' =>
      2;
    case 't' | 'T' =>
      3;
    case _ => {
      assert(false, "Cannot encode " + c)
      -1;
    }
  }

}