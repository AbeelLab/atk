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
import java.io.FileFilter;

public class PatternFileFilter implements FileFilter {

    private String pattern;
    
    public PatternFileFilter(String pattern){
        this.pattern=pattern;
        
    }

    public boolean accept(File arg0) {
        return arg0.getName().matches(pattern);
    }
    
    
}
