package venn

import scala.io.Source
import org.apache.batik.transcoder.image.PNGTranscoder
import org.apache.batik.transcoder.TranscoderInput
import java.io.ByteArrayInputStream
import org.apache.batik.transcoder.SVGAbstractTranscoder
import java.io.FileOutputStream
import org.apache.batik.transcoder.TranscoderOutput
import java.io.File
import java.io.PrintWriter
/**
 * Helper object to make Venn diagrams
 *
 */
object Venn {

  /**
   * List is a list of pairs where the first piece is the label of the set and the second part is all the elements belonging to that set.
   *
   */
  def makeVenn(outFile: File, list: List[(String, List[String])], exportTxt: Boolean = false) {

    val membership = scala.collection.mutable.Map.empty[String, String]
    val chars = ('A' to 'E').toList //List('A', 'B', 'C', 'D', 'E')
    assume(list.length <= chars.length && list.length >= 2)

    val labels = list.map(_._1)

    val labelMap = chars.zip(labels).toMap

    for ((label, l) <- list) {
      val letter = chars(labels.indexOf(label))
      l.map(e => {
        membership.update(e, membership.getOrElse(e, "") + letter)
      })

    }

    val counts = membership.values.toList.groupBy(x => x).mapValues(_.size)
    if (exportTxt) {
      val export = membership.groupBy(f=>f._2).toList
      export.map(f=>{
    	  val pw=new PrintWriter(outFile+".set."+f._1+".txt")
    	  pw.println(f._2.keySet.mkString("\n"))
    	  pw.close
      })
      
    }

    var venn = Source.fromFile("resources/venn/venn" + list.length + "a.svg").mkString

    val keyCombinations = (1 to chars.size).flatMap(chars.combinations).toList

    for (key <- keyCombinations.map(_.mkString("")))
      venn = venn.replaceAll(">" + key + "<", ">" + counts.getOrElse(key, 0) + "<");

    for ((key, label) <- labelMap)
      venn = venn.replaceAll(">List" + key + "<", ">" + label + "<");

    /* Convert to PNG */
    val streamBytes = venn.getBytes()
    val t = new PNGTranscoder();
    val input = new TranscoderInput(new ByteArrayInputStream(streamBytes));

    t.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, 800.0f);
    // GraphicsFileExport.exportPNG(new ImageDrawable(img), CompareConfig.folder + "/venn." + genome + "." + infix + ".png", 800, 600)

    val ostream = new FileOutputStream(outFile)
    val output = new TranscoderOutput(ostream);

    t.transcode(input, output);

    ostream.flush();

    ostream.close();

  }
}