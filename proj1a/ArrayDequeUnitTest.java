import org.junit.Assert;
import org.junit.Test;

/**
 * @author qiushui
 * @Date 2023/8/9
 */
public class ArrayDequeUnitTest {

    @Test
    public void test(){
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
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
        lld1.addLast(1);
        Assert.assertEquals(lld1.size(),4);
        Assert.assertEquals(lld1.get(1),new Integer(3));
        Assert.assertEquals(lld1.get(3),new Integer(1));

        lld1.addFirst(5);
        lld1.addFirst(6);
        Assert.assertEquals(lld1.size(),6);

        lld1.addFirst(7);
        lld1.addFirst(8);
        Assert.assertEquals(lld1.size(),8);
        lld1.addFirst(9);
        Assert.assertEquals(lld1.size(),9);
        lld1.addFirst(10);
        Assert.assertEquals(lld1.size(),10);
        lld1.addFirst(11);
        lld1.addFirst(12);
        lld1.addFirst(13);
        lld1.addFirst(14);
        lld1.addLast(0);
        lld1.addLast(-1);
        lld1.addLast(-2);
        lld1.addFirst(15);
        lld1.addFirst(16);
        lld1.printDeque();

        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.printDeque();

    }

    @Test
    public void testTwo(){

    }
}
