package abeel.io

import java.io.PrintWriter

class HTMLPrintWriter(str: String) extends PrintWriter(str: String) {

  def printTableOpen(option:String="") = {
    println("<table "+option+" >")
  }

  def h2(str:String)=tag(str,"h2")
  
  def printTableClose = { println("</table>") }

  def pre(str:String)=tag(str,"pre")
  
  def tag(str:String,tag:String)={println("<"+tag+">"+str+"</"+tag+">")}
  
  
  def printTableRow(row: String) = {
    println("<tr><td>" + row.replaceAll("\t", "</td><td>") + "</td></tr>")
  }
    def printTableHeading(row: String) = {
    println("<tr><th>" + row.replaceAll("\t", "</th><th>") + "</th></tr>")
  }
  
  def wrapBold(str:String)="<b>"+str+"</b>"
  
  
}