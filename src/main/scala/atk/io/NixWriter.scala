package atk.io

import java.io.PrintWriter
import java.io.File
import java.time.LocalDateTime
import atk.util.Tool

class NixWriter(f: String,config:AnyRef=null,noheader:Boolean=false) extends PrintWriter(f) with Tool{

  def this(f:File,config:AnyRef)={
    this(f.toString,config)
  }
  
  if(!noheader)
    println(generatorInfo(config))
  
  override def println(str: String): Unit= {
    print(str + "\n")
  }

  override def close(): Unit = {
    if(!noheader)
      println("## This analysis finished " + LocalDateTime.now())
    super.close
    
  }
  
  
}