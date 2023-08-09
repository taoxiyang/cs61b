import org.junit.Assert;
import org.junit.Test;

/**
 * @author qiushui
 * @Date 2023/8/9
 */
public class FlikTest {

    @Test
    public void testOne(){
        Assert.assertTrue(Flik.isSameNumber(1,1));
        Assert.assertTrue(Flik.isSameNumber(0,0));
        Assert.assertTrue(Flik.isSameNumber(-1,-1));
        Assert.assertTrue(Flik.isSameNumber(127,127));
        Assert.assertTrue(Flik.isSameNumber(128,128));
        Assert.assertTrue(Flik.isSameNumber(256,256));
    }
}
