import org.junit.Assert;
import org.junit.Test;

/**
 * @author qiushui
 * @Date 2023/8/10
 */
public class TestOffByN {

    @Test
    public void testEqualChars(){
        CharacterComparator offBy4 = new OffByN(4);
        CharacterComparator offBy5 = new OffByN(5);
        Assert.assertTrue(offBy4.equalChars('a','b'));
        Assert.assertTrue(offBy4.equalChars('a','c'));
        Assert.assertTrue(offBy4.equalChars('a','e'));
        Assert.assertFalse(offBy4.equalChars('a','f'));


        Assert.assertTrue(offBy5.equalChars('a','b'));
        Assert.assertTrue(offBy5.equalChars('a','c'));
        Assert.assertTrue(offBy5.equalChars('a','e'));
        Assert.assertTrue(offBy5.equalChars('a','f'));
        Assert.assertFalse(offBy5.equalChars('a','g'));

    }

}
