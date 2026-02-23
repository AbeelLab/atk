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
import java.util.ArrayList;

/**
 * 
 * @author Thomas Abeel
 * 
 */
public class IOTools {

	/**
	 * Recursively search a folder for all files that fit the give FileFilter.
	 * 
	 * @param rootFolder
	 *            root folder to start the search
	 * @param fileFilter
	 *            FileFilter to match file names
	 * @return list of files
	 */
	public static File[] recurse(File rootFolder, FileFilter fileFilter) {
		ArrayList<File> list = new ArrayList<File>();

		list(rootFolder, fileFilter, list);
		return list.toArray(new File[0]);
	}

	private static void list(File root, FileFilter filter, ArrayList<File> out) {
		File[] files = root.listFiles(new DirectoryFilter());
		for (File file : files)
			if (file.isDirectory())
				list(file, filter, out);

		files = root.listFiles(filter);
		for (File file : files)
			out.add(file);

	}

}
