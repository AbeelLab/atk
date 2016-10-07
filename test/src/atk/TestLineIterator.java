package atk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;

import junit.framework.Assert;
import be.abeel.io.LineIterator;

public class TestLineIterator {

	@Test
	public void testLineIterator(){
		LineIterator it=new LineIterator("COPYING");
		LineIterator it2=new LineIterator("COPYING.gz");
		while(it.hasNext()){
			Assert.assertEquals(it.next(), it2.next());
		}
	}
	@Test
	public void testLineIterator2(){
		LineIterator it=new LineIterator("COPYING");
		LineIterator it2=new LineIterator(new File("COPYING.gz"));
		while(it.hasNext()){
			Assert.assertEquals(it.next(), it2.next());
		}
	}
	
	@Test
	public void testLineIterator3(){
		LineIterator it=new LineIterator("COPYING");
		LineIterator it2=null;
		try {
			it2 = new LineIterator(new FileInputStream("COPYING.gz"));
		} catch (FileNotFoundException e) {
			Assert.fail();
		}
		while(it.hasNext()){
			Assert.assertEquals(it.next(), it2.next());
		}
	}
	
}
