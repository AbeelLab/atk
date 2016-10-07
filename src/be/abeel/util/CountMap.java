/**
 * %HEADER%
 */
package be.abeel.util;

/**
 * An extension of a TreeMap<A,Integer> that is designed to be used as a
 * frequency table for any object of type A. While this implementation allows
 * you to count any type of object, it is more restricted in terms of post
 * modifications than the <code>FrequencyMap</code>.
 * 
 * @see FrequencyMap
 * 
 * @author Thomas Abeel
 */
public class CountMap<A> extends DefaultTreeMap<A, Integer> {

	private static final long serialVersionUID = -3368572739262620314L;

	/**
	 * Creates a CountMap with the provided number of pseudo counts.
	 * 
	 * @param pseudoCounts
	 */
	public CountMap(int pseudoCounts) {
		super(pseudoCounts);
	}

	public int totalCount() {
		int totalCount = 0;
		for (A k : this.keySet())
			totalCount += this.get(k);
		return totalCount;
	}

	public CountMap() {
		super(0);

	}

	/**
	 * Returns the normalized value between [0,1] for this key.
	 * 
	 * @param key
	 *            the key to get a normalized value for
	 * @return normalized count
	 */
	public double getNormalized(A key) {
		return get(key) / (double) totalCount();
	}

	/**
	 * Adds one to the count of value.
	 * 
	 * @param value
	 *            the value to count
	 */
	public void count(A value) {
		count(value,1);

	}

	/**
	 * Create a deep copy of this object.
	 */
	public CountMap<A> copy() {
		CountMap<A> out = new CountMap<A>();
		for (A i : this.keySet())
			out.put(i, this.get(i));
		return out;
	}

	public void count(A key, int value) {
		this.put(key, this.get(key) + value);
		
	}
}
