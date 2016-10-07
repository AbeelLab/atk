/**
 * %HEADER%
 */
package be.abeel.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.abeel.concurrency.DaemonThread;

/**
 * 
 * LRU Cache with advanced features like clearing entries when memory load is
 * high or timed to discard items after a TTL.
 * 
 * 
 * @author jrobinso
 * @author Thomas Abeel
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = 3108325620729125294L;

	private Logger log = Logger.getLogger(LRUCache.class.getCanonicalName());

	private int maxEntries = 100;

	private long reset = System.currentTimeMillis();

	public LRUCache(int maxEntries, final long survival) {
		super(maxEntries, 0.75f, true);
		this.maxEntries = maxEntries;

		DaemonThread dt = new DaemonThread(new Runnable() {
			private long ttl = survival;

			public void run() {
				while(true){
					if(System.currentTimeMillis()-reset>ttl){
						clear();
				
					}
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.log(Level.INFO,"LRU cleaner interrupted",e);
					}
				}

			}
		});
		dt.start();
	}

	public LRUCache(int maxEntries) {
		super(maxEntries, 0.75f, true);
		this.maxEntries = maxEntries;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedHashMap#get(java.lang.Object)
	 */
	@Override
	public V get(Object key) {
		reset = System.currentTimeMillis();
		return super.get(key);
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		if (size() > maxEntries) {
			return true;
		} else if (getAvailableMemoryFraction() < 0.1) {
			log.info("Memory low.  Free cache entry");
			return true;
		} else {
			return false;
		}
	}

	private double getAvailableMemoryFraction() {
		Runtime runtime = Runtime.getRuntime();
		long maxMemory = runtime.maxMemory();
		long allocatedMemory = runtime.totalMemory();
		long freeMemory = runtime.freeMemory();
		return (double) ((freeMemory + (maxMemory - allocatedMemory))) / maxMemory;

	}
}
