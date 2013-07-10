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

/**
 * 
 * Filefilter that uses a regular expression to match file names.
 * 
 * This only matches the file name, not the entire path.
 * 
 * @author Thomas Abeel
 * 
 */
public class PatternFileFilter implements FileFilter {

	private String pattern;

	public PatternFileFilter(String pattern) {
		this.pattern = pattern;

	}

	public boolean accept(File arg0) {
		return arg0.getName().matches(pattern);
	}

}
