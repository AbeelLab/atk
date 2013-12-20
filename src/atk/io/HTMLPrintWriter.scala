/*
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit
 * http://creativecommons.org/licenses/by-nc-nd/3.0/
 * or send a letter to Creative Commons, 444 Castro Street,
 * Suite 900, Mountain View, California, 94041, USA.
 *
 * A copy of the license is included in LICENSE.txt
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * Copyright 2005-2013 Thomas Abeel
 */
package atk.io

import java.io.PrintWriter

class HTMLPrintWriter(str: String) extends PrintWriter(str: String) {

  def printTableOpen(option:String="") = {
    println("<table "+option+" >")
  }

  def h1(str:String)=tag(str,"h1")
  
  def h2(str:String)=tag(str,"h2")
  
  def h3(str:String)=tag(str,"h3")
  
  def h4(str:String)=tag(str,"h4")
  
  def printTableClose = { println("</table>") }

  def pre(str:String)=tag(str,"pre")
  
  def tag(str:String,tag:String)={println("<"+tag+">"+str+"</"+tag+">")}
  
  
  def printTableRow(row: String) = {
    println("<tr><td>" + row.replaceAll("\t", "</td><td>") + "</td></tr>")
  }
    def printTableHeading(row: String) = {
    println("<tr><th>" + row.replaceAll("\t", "</th><th>") + "</th></tr>")
  }
  
   @Deprecated
  def wrapBold(str:String)="<b>"+str+"</b>"
  
  def b(str:String)=tag(str,"b")
  
  def i(str:String)=tag(str,"i")
  
  def td(str:String)=tag(str,"td")
  
  def tr(str:String)=tag(str,"tr")
  
  
}