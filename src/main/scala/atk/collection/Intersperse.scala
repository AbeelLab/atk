package atk.collection

trait Intersperse {

  def intersperse[A](a: List[A], b: List[A]): List[A] = a match {
    case first :: rest => first :: intersperse(b, rest)
    case _ => b
  }
}