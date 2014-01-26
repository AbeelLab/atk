package atk.compbio.gff

import atk.util.MD5Tools
/**
 * Utility class to make and parse GFF lines
 *
 * @author Thomas Abeel
 */
class GFFLine(val line: String) {

  def this(seqid: String, kind: String, start: Int, end: Int, source: String = "No source", score: Float = 0, strand: String = ".", phase: String = ".", attributes: Map[String, String] = Map.empty[String, String]) = {

    this(List(seqid, source, kind, start, end, score, strand, phase).mkString("\t") + "\t" + (if (attributes.size > 0)
      attributes.map(f => f._1 + "=" + f._2).mkString(";")
    else
      "ID=" + MD5Tools.md5(seqid + source + kind + start + end + score + strand + phase)))

  }
  lazy val start: Int = arr(3).toInt
  lazy val end: Int = arr(4).toInt

  def overlap(other: GFFLine) = {
    start <= other.end && end >= other.start
  }

  def kind = arr(2)

  lazy val attributes: Map[String, String] = arr(8).split(";").map(v => { val ax = v.split("=", 2);assume(ax.size==2,"Failed split: "+line); ax(0) -> ax(1) }).toMap

  def attribute(key: String) = {
    attributes.getOrElse(key,null)
  }
  private lazy val arr = line.split("\t",9)

  override def toString() = line

}