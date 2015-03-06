package atk.tools

object ATKConsole {

 def main(args: Array[String]): Unit = {

    if (args.length == 0) {

      listInstructions
    } else {
      args(0) match {
        case "list" => listInstructions
        case "help" => listInstructions
        case "histogram" => Histogram.main(args.drop(1))
        case "string-replace" =>StringReplace.main(args.drop(1))
        case _ => listInstructions
      }
    }

  }

  def listInstructions() {
    println("Usage:java -jar atk.jar [instruction] [instruction options...]")
    println("Instructions:")
    println("\thistogram\t\tCreate a histogram plot from data in file")
    println("\tstring-replace\t\tReplace string with other from a key-value file")
    println("\tlist\t\tShow help")
    println("\thelp\t\tShow help")

  }

}