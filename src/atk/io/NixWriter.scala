package atk.io

import java.io.PrintWriter
import java.io.File

class NixWriter(f: String) extends PrintWriter(f) {

  
  
  
  override def println(str: String) {
    print(str + "\n")
  }

}