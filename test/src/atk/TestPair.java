/**
 * %HEADER%
 */
package atk;

import junit.framework.Assert;

import org.junit.Test;

import be.abeel.util.Pair;

public class TestPair {
    @Test
    public void testPair() {
        Pair<Integer, Integer> p1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> p2 = new Pair<Integer, Integer>(1, 2);
       
        Assert.assertEquals(p1.hashCode(), p2.hashCode());
        Assert.assertTrue(p1.equals(p2));
        Assert.assertTrue(p2.equals(p1));
        Pair<String, String> q1 = new Pair<String, String>("Hallo kroket", "yow");
        Pair<String, String> q2 = new Pair<String, String>("Hallo kroket", "yow");
        Pair<String, String> q3 = new Pair<String, String>("Hallo kroket", "verkeerd");
        Assert.assertEquals(q1.hashCode(), q2.hashCode());
        Assert.assertTrue(q1.equals(q2));
        Assert.assertTrue(q2.equals(q1));
        Assert.assertFalse(q3.equals(q1));
        Assert.assertFalse(q3.equals(q2));
        Assert.assertFalse(q1.equals(q3));
        Assert.assertFalse(q2.equals(q3));
        Pair<String, Integer> s1=new Pair<String, Integer>("chr6_qbl_hap2",4452925);
        Pair<String, Integer> s2=new Pair<String, Integer>("chr6_qbl_hap2",4452925);
        Assert.assertEquals(s1.hashCode(), s2.hashCode());
        Assert.assertTrue(s1.equals(s2));
        Assert.assertTrue(s2.equals(s1));
    }

}
