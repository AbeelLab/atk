/**
 * %HEADER%
 */
package be.abeel.util;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * Extension of the TreeMap that will return a default value instead of null
 * when a key is not present in the map.
 * 
 * @author Thomas Abeel
 * 
 * @param <K>
 * @param <V>
 */
public class DefaultTreeMap<K, V> extends TreeMap<K, V> {

    private static final long serialVersionUID = -3738594693170825324L;

    private V defaultValue = null;

    public DefaultTreeMap(V defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public V get(Object key) {
        if (super.containsKey(key))
            return super.get(key);
        else
            return defaultValue;
    }

}
