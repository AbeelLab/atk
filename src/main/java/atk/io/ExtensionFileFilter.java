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
package atk.io;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.Vector;
/**
 * FileFilter that allows files with a particular extension.
 * 
 * @author Thomas Abeel
 *
 */
public class ExtensionFileFilter implements FileFilter {
    private Vector<String> extension = new Vector<String>();

    private void addExtension(String ext) {
        // Add a dot, it it doesn't start with one.
        if (!ext.startsWith("\\."))
            ext = "." + ext;
        this.extension.add(ext.toLowerCase());
    }

    public ExtensionFileFilter(List<String> ext) {
        for (String s : ext)
            addExtension(s.toLowerCase());
    }

    public ExtensionFileFilter(String ext) {
        addExtension(ext.toLowerCase());
    }

    public boolean accept(File file) {
        boolean accept = false;
        for (String s : extension) {
            if (file.getName().toLowerCase().endsWith(s))
                accept = true;
        }
        return accept;
        // return extension.indexOf(file.getName().toLowerCase().e;
    }
}
