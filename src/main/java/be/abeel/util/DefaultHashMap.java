/**
 * %HEADER%
 */
package be.abeel.util;

import java.util.HashMap;

/**
 * Extension of the HashMap that will return a default value instead of null
 * when a key is not present in the map.
 * 
 * @author Thomas Abeel
 * 
 * @param <K>
 * @param <V>
 */
public class DefaultHashMap<K, V> extends HashMap<K, V> {

    private static final long serialVersionUID = -3738594693170825324L;

    private V defaultValue = null;

    public DefaultHashMap(V defaultValue) {
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
