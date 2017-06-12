package atk.compbio.vcf

sealed trait SNP {

}
sealed trait Variation {
  override def toString(): String = {
    strType
  }
  lazy val strType = this.getClass().getSimpleName()

}

sealed trait LP;

case object Match extends Variation with SNP

class Substitution extends Variation
case object SingleSubstitution extends Substitution with SNP
case object LongSubstitution extends Substitution with LP
//case class ComplexSubstitution extends Variation

class Deletion extends Variation
case object SingleDeletion extends Deletion with SNP
case object LongDeletion extends Deletion with LP

class Insertion extends Variation
case object SingleInsertion extends Insertion with SNP
case object LongInsertion extends Insertion with LP

class VCFLine(val line: String) {

  lazy val arr = line.split("\t")

  override def toString() = line //arr.mkString("\t")

  lazy val zeroPos = arr(1).toInt - 1

  lazy val pos = arr(1).toInt

  def ref = arr(3)
  def alt = arr(4)

  def refGenome = arr(0)

  lazy val refLength = ref.length()
  lazy val altLength = alt.length()
  lazy val diff = refLength - altLength
  lazy val variation: Variation = {
    if (alt.equals(".")) {
      Match
    } else if (ref.length() == alt.length()) {
      if (ref.equals(alt))
        Match
      else {
        if (ref.length() == 1)
          SingleSubstitution
        else
          LongSubstitution
      }
    } else {
      assume(ref.length() > 0 && alt.length() > 0)
      if (ref.length() == 1 || alt.length() == 1) {

        //if (ref.length() > alt.length())
        if (diff > 1)
          LongDeletion
        else if (diff > 0)
          SingleDeletion
        else if (diff < -1)
          LongInsertion
        else if (diff < 0)
          SingleInsertion
        else
          throw new RuntimeException("This is not supposed to happen!")
      } else {
        LongSubstitution
      }

    }
  }

  lazy val score = if (arr(5)(0) == '.') 0 else arr(5).toDouble

  lazy val blankFilter = filter.equals(".")

  lazy val pass = filter.equalsIgnoreCase("PASS")
  
  lazy val info= arr(7).split(";").map(pair=>{
   
    val pp=pair.split("=");
    if(pp.size==2)
      pp(0)->pp(1)
    else
      pair->"raw"
  
  }).toMap

  lazy val passOrUnfiltered = pass || filter.equals(".")

  def filter = if (arr.length > 6) arr(6) else "."

}