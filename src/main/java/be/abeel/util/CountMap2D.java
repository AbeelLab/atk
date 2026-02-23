/**
 * %HEADER%
 */
package be.abeel.util;

/**
 * A two dimensional contingency table
 * 
 * @see FrequencyMap
 * @see CountMap
 * 
 * @author Thomas Abeel
 */
public class CountMap2D<K, L> extends DefaultHashMap2D<K, L, Integer> {

	private static final long serialVersionUID = 4132022242476898467L;
	

	/**
	 * Creates a new 2D CountMap with the provided number of pseudocounts.
	 * 
	 * @param pseudoCounts
	 */
	public CountMap2D(int pseudoCounts) {
		super(pseudoCounts);

	}

	public CountMap2D() {
		super(0);

	}

	/**
	 * Adds one to the count of value.
	 * 
	 * @param value
	 *            the value to count
	 */
	public void count(K k, L l) {

		this.put(k, l, this.get(k, l) + 1);
		
	}

	/**
	 * Calculates the total sum of the counts in the map
	 * 
	 * @return the total count
	 */
	public int totalCount() {
		int totalCount = 0;
		for (K k : this.keySet())
			for (L l : this.get(k).keySet())
				totalCount += this.get(k, l);
		return totalCount;
	}

	/**
	 * Calculates the sum of the counts for which the second component has a
	 * certain value.
	 * 
	 * @param l
	 *            component to get count for
	 * @return sum of counts for component l
	 */
	public int totalYCount(L l) {
		int totalCount = 0;
		for (K k : this.keySet())
			totalCount += this.get(k, l);
		return totalCount;
	}
	/**
	 * Calculates the sum of the counts for which the first component has a
	 * certain value.
	 * 
	 * @param k
	 *            component to get count for
	 * @return sum of counts for component k
	 */
	public int totalXCount(K k) {
		int totalCount = 0;

		if(this.get(k)==null)
			return super.getDefault();
		for (L l : this.get(k).keySet())
			totalCount += this.get(k, l);
		return totalCount;
	}

	/**
	 * Returns the normalized value between [0,1] for this key-pair.
	 * Normalization is done over the complete matrix
	 * 
	 * @param k
	 *            the first part of the key pair
	 * @param l
	 *            the second part of the key pair
	 * 
	 * @return normalized count
	 */
	public double getNormalized(K k, L l) {
		return get(k, l) / (double) totalCount();
	}
}
