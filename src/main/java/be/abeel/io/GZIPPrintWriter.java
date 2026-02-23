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
 * Copyright 2014-2016 Thomas Abeel
 */
package be.abeel.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

/**
 * PrintWriter that automatically GZIPs the output
 * 
 * @author Thomas Abeel
 *
 */
public class GZIPPrintWriter extends PrintWriter {

    public GZIPPrintWriter(File file, String csn) throws IOException {
        super(new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(ExtensionManager.extension(file, "gz"))),csn));
    }

    public GZIPPrintWriter(File file) throws IOException {
        super(new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(ExtensionManager.extension(file, "gz")))));
    }

    public GZIPPrintWriter(OutputStream out, boolean autoFlush) throws IOException {
        super(new OutputStreamWriter(new GZIPOutputStream(out)));
    }

    public GZIPPrintWriter(OutputStream out) throws IOException {
        super(new OutputStreamWriter(new GZIPOutputStream(out)));
    }

    public GZIPPrintWriter(String fileName, String csn) throws IOException {
        super(new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(ExtensionManager.extension(fileName, "gz"))), csn));
    }

    public GZIPPrintWriter(String fileName) throws IOException {
        super(new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(ExtensionManager.extension(fileName, "gz")))));
    }

}
