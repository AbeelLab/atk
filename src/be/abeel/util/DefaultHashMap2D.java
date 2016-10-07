/**
 * %HEADER%
 */
package be.abeel.util;

/**
 * Two-dimensional HashMap with a default value. Each value has a pair of keys.
 * The outer Map consists of HashMaps keyed by the first key, and each
 * corresponding HashMap is keyed by the second key.
 * 
 * @author Thomas Abeel
 */
public class DefaultHashMap2D<K, L, V> extends HashMap2D<K, L, V> {
	

	
	private static final long serialVersionUID = -5725812876666561447L;
	private V defaultValue;

	/**
	 * Constructs an empty LinkedHashMap.
	 */
	public DefaultHashMap2D(V defaultValue) {
		super();
		this.defaultValue = defaultValue;
	}



	/**
	 * Gets the value of the property.
	 * 
	 * @param key1
	 *            The outer key of the property
	 * @param key2
	 *            The inner key of the property.
	 * @return The value of the property
	 */
	public V get(K key1, L key2) {
		V value = defaultValue;
		if (super.get(key1,key2)!=null) {
			value = this.get(key1).get(key2);
		}
		return value;
	}

	

	/**
	 * Returns the value that will be returned as a default for missing values.
	 * 
	 * @return default value
	 */
	public V getDefault() {
		return defaultValue;
	}
}