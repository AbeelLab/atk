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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Deprecated 2016/10/07 -- will be removed 2017/04/07
 * @author Thomas Abeel
 *
 */

@Deprecated
public class LineIteratorFactory {

    public static LineIterator createFromGZip(File f) throws IOException {
        return new LineIterator(new GZIPInputStream(new FileInputStream(f)));
    }

    public static LineIterator createFromZip(File f) throws IOException {
        ZipInputStream zipinputstream = new ZipInputStream(new FileInputStream(f));

        ZipEntry zipentry = zipinputstream.getNextEntry();
//        System.out.println(zipentry);
        if (zipentry != null) {
            // for each entry to be extracted
            String entryName = zipentry.getName();
//            System.out.println("File ::" + entryName);
            // RandomAccessFile rf;
            File newFile = new File(entryName);
            return new LineIterator(newFile);

            // out = read(it, classIndex, separator);
            // zipinputstream.closeEntry();
        }
        return null;
    }
}
