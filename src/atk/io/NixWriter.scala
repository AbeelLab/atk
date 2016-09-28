package atk.io

import java.io.PrintWriter
import java.io.File
import java.time.LocalDateTime
import atk.util.Tool

class NixWriter(f: String,config:AnyRef=null) extends PrintWriter(f) with Tool{

  def this(f:File,config:AnyRef)={
    this(f.toString,config)
  }
  
  
  println(generatorInfo(config))
  
  override def println(str: String) {
    print(str + "\n")
  }

  override def close(){
    println("## This analysis finished " + LocalDateTime.now())
    super.close
    
  }
  
  
}