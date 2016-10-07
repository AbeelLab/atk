/**
 * %HEADER%
 */
package be.abeel.concurrency;
/**
 * 
 * @author Thomas Abeel
 *
 */
public class DaemonThread extends Thread {

	public DaemonThread(Runnable r) {
		super(r);
		this.setPriority(Thread.MIN_PRIORITY);
		this.setDaemon(true);
	}
}
