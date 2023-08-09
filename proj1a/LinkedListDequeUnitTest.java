import jh61b.junit.TestRunner;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author qiushui
 * @Date 2023/8/9
 */
public class LinkedListDequeUnitTest {

    @Test
    public void test(){
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        lld1.addFirst(1);
        Assert.assertTrue(!lld1.isEmpty());
        Assert.assertEquals(lld1.removeLast(),new Integer(1));
        Assert.assertTrue(lld1.isEmpty());
        lld1.addFirst(1);
        lld1.addFirst(2);
        lld1.addFirst(3);
        lld1.addFirst(4);
        lld1.addFirst(5);
        Assert.assertEquals(lld1.removeLast(),new Integer(1));
        Assert.assertEquals(lld1.removeFirst(),new Integer(5));
        Assert.assertEquals(lld1.size(),3);
        lld1.printDeque();
        lld1.addLast(1);
        Assert.assertEquals(lld1.size(),4);
        lld1.printDeque();
        Assert.assertEquals(lld1.get(1),new Integer(3));
        Assert.assertEquals(lld1.get(3),new Integer(1));
    }

    public static void main(String[] args) {
        TestRunner.runTests("all", LinkedListDequeUnitTest.class);
    }
}
