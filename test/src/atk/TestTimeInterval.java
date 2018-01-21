/**
 * %HEADER%
 */
package atk;

import junit.framework.Assert;

import org.junit.Test;

import atk.util.TimeInterval;



public class TestTimeInterval {
	@Test
	public void testTimeInterval() {
		long time=System.currentTimeMillis();
		TimeInterval t=new TimeInterval(time);
		Assert.assertEquals(time, t.getLengthInMilliseconds());
		String st=t.toString();
		TimeInterval t2=new TimeInterval(st);
		Assert.assertEquals(time,t2.getLengthInMilliseconds());
		Assert.assertEquals(st, t2.toString());
		
	}
}
