import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    //Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/

    @Test
    public void testEqualChars(){
        Assert.assertTrue(offByOne.equalChars('a','b'));
        Assert.assertTrue(offByOne.equalChars('b','a'));
        Assert.assertFalse(offByOne.equalChars('a','a'));
        Assert.assertFalse(offByOne.equalChars('a','c'));
        Assert.assertFalse(offByOne.equalChars('c','a'));
        Assert.assertFalse(offByOne.equalChars('f','a'));
        Assert.assertFalse(offByOne.equalChars('f','h'));
        Assert.assertFalse(offByOne.equalChars('f','F'));
        Assert.assertFalse(offByOne.equalChars('f','G'));

    }
}
