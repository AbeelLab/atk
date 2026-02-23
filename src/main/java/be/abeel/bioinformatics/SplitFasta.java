/**
 * %HEADER%
 */
package be.abeel.bioinformatics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class to split a multi fasta file in multiple single sequence fasta files.
 * 
 * @author Thomas Abeel
 * 
 */
public class SplitFasta {

    
    public static void main(String[]args){
        if(args.length==0)
            System.out.println("You should give the files to split as arguments to the program");
        else{
            for(String file:args)
                splitFile(file);
        }
            
    }
    public static void splitFile(File file) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            int entryCount = 0;
            String line = in.readLine();
            PrintWriter out = null;
            while (line != null) {
                if (line.startsWith(">")) {
                    if (out != null)
                        out.close();
                    out = new PrintWriter(file.toString()+ "_" + line.substring(1) + ".fa");
                    entryCount++;

                }
                out.println(line);
                line = in.readLine();
            }
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void splitFile(String file) {
        splitFile(new File(file));
    }
}
