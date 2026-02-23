package atk.tools

import java.io.File
import atk.util.Tool
import edu.northwestern.at.utils.math.statistics.FishersExactTest
import java.io.PrintWriter

object FisherList extends Tool{

  case class Config(val input: File = null, val output: File = null)

  def main(args: Array[String]): Unit = {

    val parser = new scopt.OptionParser[Config]("java -jar atk.jar fisher-list") {
      opt[File]('i', "input").required().action{ (x, c) => c.copy(input = x) }.text("Input file. ")
      opt[File]('o', "output").action{ (x, c) => c.copy(output = x) }.text("Output file")
      
    }
    parser.parse(args, Config()) map { config =>
      fishlist(config)

    }

  }
  def fishlist(config: Config): Unit =  {

    //Takes a list of triplets consisting of a label and a pair of values and checks whether it's different from the population of all pairs.
    
    val triplets=tLines(config.input).map{f=>
      val arr=f.split("\t")
      arr(0)->(arr(1).toInt->arr(2).toInt)
      
      
    
    }
    
    /**	Calculate Fisher's exact test from the four cell counts.
	 *
	 *	@param	n11		Frequency for cell(1,1).
	 *	@param	n12		Frequency for cell(1,2).
	 *	@param	n21		Frequency for cell(2,1).
	 *	@param	n22		Frequency for cell(2,2).
	 *
	 *	@return			double vector with three entries.
	 *              	[0]	= two-sided Fisher's exact test.
	 *					[1]	= left-tail Fisher's exact test.
	 *					[2]	= right-tail Fisher's exact test.
	 */
    val sum1=triplets.map(_._2._1).sum
    val sum2=triplets.map(_._2._2).sum
    
    val pw=new PrintWriter(config.output)
    pw.println(generatorInfo())
    pw.println("##")
    pw.println("## label\tX\tY\tn11\tn12\tn21\tn22\tbonferroni(p)")
    pw.println("##")
    for((label,(x,y))<-triplets){
        val n11=x
        val n12=y
        val n21=sum1-x
        val n22=sum2-y
        val p=FishersExactTest.fishersExactTest(n11, n12, n21, n22)(0)
        val correctedP=math.min(p*triplets.size,1)
        pw.println(label+"\t"+x+"\t"+y+"\t"+n11+"\t"+n12+"\t"+n21+"\t"+n22+"\t"+correctedP)
    }
    pw.close
    
    
    
    
  }

}
