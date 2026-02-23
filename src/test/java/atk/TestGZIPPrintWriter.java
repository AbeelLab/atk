/**
 * %HEADER%
 */
package atk;

import java.io.IOException;

import org.junit.Test;

import be.abeel.io.GZIPPrintWriter;

public class TestGZIPPrintWriter {

   

    @Test
    public void testGZIPPrintWriter() {
        try {
            GZIPPrintWriter out=new GZIPPrintWriter("test.txt");
            out.println("test document");
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
