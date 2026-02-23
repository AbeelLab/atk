package atk.tools

object ATKConsole {

 def main(args: Array[String]): Unit = {

    if (args.length == 0) {

      listInstructions()
    } else {
      args(0) match {
        case "list" => listInstructions()
        case "help" => listInstructions()
        case "histogram" => Histogram.main(args.drop(1))
        case "string-replace" =>StringReplace.main(args.drop(1))
        case "fisher-list"=>FisherList.main(args.drop(1))
        case "scatter" => Scatter.main(args.drop(1))
        case "scatter-xy" =>ScatterXY.main(args.drop(1))
        case "zscore" => ZscoreNormalize.main(args.drop(1))
        case _ => listInstructions()
      }
    }

  }

  def listInstructions():Unit = {
    println("Usage:java -jar atk.jar [instruction] [instruction options...]")
    println("Instructions:")
    println("\thistogram          Create a histogram plot from data in file")
    println("\tstring-replace     Replace string with other from a key-value file")
    println("\ttree2list          Converts nwk tree into list")
    println("\tfisher-list        Do Fisher exact test on each value pair in a list taking the complete list as population.")
    println("\tscatter            Generate scatter plot")
    println("\tscatter-xy         Generate scatter plot")
    println("\tzscore             Zscore normalize a value column")
    println("\tlist\t\tShow help")
    println("\thelp\t\tShow help")

  }

}