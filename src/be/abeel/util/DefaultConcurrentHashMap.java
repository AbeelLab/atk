/**
 * %HEADER%
 */
package be.abeel.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Extension of the ConcurrentHashMap that will return a default value instead
 * of null when a key is not present in the map.
 * 
 * @author Thomas Abeel
 * 
 * @param <K>
 * @param <V>
 */
public class DefaultConcurrentHashMap<K, V> extends ConcurrentHashMap<K, V> {

    private static final long serialVersionUID = -2508631956990518242L;

    private V defaultValue = null;

    public DefaultConcurrentHashMap(V defaultValue) {
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
