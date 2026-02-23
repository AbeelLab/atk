/**
 * %HEADER%
 */
package be.abeel.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Implements additional operations on sets.
 * 
 * @author Thomas Abeel
 * 
 */
public class SetTools {
    /**
     * Returns the union of the two sets provided as arguments.
     * 
     * @param a
     *            the first set
     * @param b
     *            the second set
     * @return union of a and b
     */
    public static Set<?> union(Set<?> a, Set<?> b) {
        Set<Object> out = new HashSet<Object>();
        out.addAll(a);
        out.addAll(b);
        return out;

    }

    /**
     * Returns the intersection of the two sets provided as arguments.
     * 
     * @param a
     *            the first set
     * @param b
     *            the second set
     * @return intersection of a and b
     */
    public static Set<?> intersection(Set<?> a, Set<?> b) {
        Set<Object> out = new HashSet<Object>();
        out.addAll(a);
        out.retainAll(b);
        return out;
    }
}
