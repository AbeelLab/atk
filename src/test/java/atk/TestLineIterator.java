package atk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;

import junit.framework.Assert;
import be.abeel.io.LineIterator;

public class TestLineIterator {
	private final static String TEXTFILE = "src/test/resources/COPYING";
	private final static String GZTFILE = "src/test/resources/COPYING.gz";
	

	@Test
	public void testLineIterator(){
		LineIterator it=new LineIterator(TEXTFILE);
		LineIterator it2=new LineIterator(GZTFILE);
		while(it.hasNext()){
			Assert.assertEquals(it.next(), it2.next());
		}
	}
	@Test
	public void testLineIterator2(){
		LineIterator it=new LineIterator(TEXTFILE);
		LineIterator it2=new LineIterator(GZTFILE);

		while(it.hasNext()){
			Assert.assertEquals(it.next(), it2.next());
		}
	}
	
	@Test
	public void testLineIterator3(){
		LineIterator it=new LineIterator(TEXTFILE);
		LineIterator it2=null;
		try {
			it2 = new LineIterator(new FileInputStream(GZTFILE));
		} catch (FileNotFoundException e) {
			Assert.fail();
		}
		while(it.hasNext()){
			Assert.assertEquals(it.next(), it2.next());
		}
	}
	
}
