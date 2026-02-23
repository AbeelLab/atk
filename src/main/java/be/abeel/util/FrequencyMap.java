/**
 * %HEADER%
 */
package be.abeel.util;

import java.util.HashSet;
import java.util.Set;

/**
 * An extension of a TreeMap<Integer,Integer> that is designed to be used as a
 * frequency table.
 * 
 * @author Thomas Abeel
 */
public class FrequencyMap extends DefaultTreeMap<Integer, Integer> {

	public int min(){
		return firstKey();
	}
	
	public int max(){
		return lastKey();
	}
	
	public double std() {
		double avg = average();
		
		double sum = 0;
		double count = totalCount();
		int manualCount = 0;
		for (java.util.Map.Entry<Integer, Integer> e : this.entrySet()) {
			double diff = e.getKey() - avg;
			sum += (diff * diff * e.getValue());
			manualCount += e.getValue();

		}
		//System.out.println("CC:" + count + " " + manualCount);

		return Math.sqrt(sum / count);
	}

	public int median() {
		int tc = totalCount();
		int runningCount = 0;
		for (java.util.Map.Entry<Integer, Integer> e : this.entrySet()) {
			runningCount += e.getValue();
			if (runningCount > tc / 2)
				return e.getKey();
		}
		return this.lastKey();
	}

	public double sum() {
		double sum = 0;
		for (java.util.Map.Entry<Integer, Integer> e : this.entrySet()) {
			sum += e.getKey() * e.getValue();
		}
		return sum;
	}

	public double average() {
		double sum = 0;
		int count = 0;
		for (java.util.Map.Entry<Integer, Integer> e : this.entrySet()) {
			sum += e.getKey() * e.getValue();
			count += e.getValue();
		}
		return sum / count;

	}

	public int mode() {
		int maxV = 0;
		int maxK = 0;
		for (java.util.Map.Entry<Integer, Integer> e : this.entrySet()) {
			if (e.getValue() > maxV) {
				maxV = e.getValue();
				maxK = e.getKey();
			}
		}
		return maxK;
	}

	public int totalCount() {
		int totalCount = 0;
		for (int k : this.keySet())
			totalCount += this.get(k);
		return totalCount;
	}

	public FrequencyMap() {
		super(0);

	}

	private static final long serialVersionUID = -2086288572103735727L;

	public void count(int value, int increment) {
		if (!this.containsKey(value))
			this.put(value, 0);

		this.put(value, this.get(value) + increment);
	}

	/**
	 * Adds one to the count of value.
	 * 
	 * @param value
	 *            the value to count
	 */
	public void count(int value) {
		count(value, 1);

	}

	/**
	 * Remove all entries that do not have at least a minimum count.
	 * 
	 * @param min
	 *            the minimum count. This value will still be in the map.
	 */
	public FrequencyMap removeBelow(int min) {
		Set<Integer> toRemove = new HashSet<Integer>();
		for (int k : this.keySet())
			if (this.get(k) < min)
				toRemove.add(k);
		for (int k : toRemove)
			this.remove(k);
		return this;
	}

	/**
	 * Shift the keys in this frequency map
	 * 
	 * @param shift
	 *            the number of positions to shift the keys
	 * 
	 * @return a reference to this FrequencyMap
	 */
	public FrequencyMap shift(int shift) {
		Set<Integer> keys = new HashSet<Integer>();
		keys.addAll(this.keySet());
		for (int k : keys) {
			int val = this.get(k);
			this.put(k + shift, val);
			this.put(k, 0);

		}
		return this;
	}

	/**
	 * Take values together in bins.
	 * 
	 * @param size
	 *            the size of the bin
	 * @return a reference to this FrequencyMap
	 */
	public FrequencyMap bin(int size) {
		// int min = Integer.MAX_VALUE;
		// int max = Integer.MIN_VALUE;
		// /* Determine min-max for the keys */
		// for (int k : this.keySet()) {
		// if (k < min)
		// min = k;
		// if (k > max)
		// max = k;
		// }
		Set<Integer> keys = new HashSet<Integer>();
		keys.addAll(this.keySet());
		for (int k : keys) {
			int val = this.get(k);
			if (k % size != 0) {
				this.put((k / size) * size, this.get((k / size) * size) + val);
				this.put(k, 0);
			}
		}
		return this;
	}

	/**
	 * Removes all entries that occurred more than the specified threshold.
	 * 
	 * @param max
	 *            the maximum allowed count. This value will still be in the map
	 */
	public FrequencyMap removeAbove(int max) {
		Set<Integer> toRemove = new HashSet<Integer>();
		for (int k : this.keySet())
			if (this.get(k) > max)
				toRemove.add(k);
		for (int k : toRemove)
			this.remove(k);
		return this;
	}

	/**
	 * Remove all entries that do not fall within the provided range.
	 * 
	 * @param lower
	 *            lower bound to keep elements
	 * @param upper
	 *            upper bound to keep elements
	 */
	public FrequencyMap truncate(int lower, int upper) {
		Set<Integer> toRemove = new HashSet<Integer>();
		for (int k : this.keySet())
			if (k < lower || k > upper)
				toRemove.add(k);
		for (int k : toRemove)
			this.remove(k);
		return this;
	}

	/**
	 * Create a deep copy of this object.
	 */
	public FrequencyMap copy() {
		FrequencyMap out = new FrequencyMap();
		for (int i : this.keySet())
			out.put(i, this.get(i));
		return out;
	}
}
