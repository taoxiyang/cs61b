import org.junit.Assert;
import org.junit.Test;

/**
 * @author qiushui
 * @Date 2023/9/15
 */
public class HeapTest {

    @Test
    public void test(){
        ExtrinsicPQ heap = new ArrayHeap();
        heap.insert(1,1);
        heap.insert(2,2);
        heap.insert(3,3);
        heap.insert(-1,-1);

        Assert.assertEquals(-1,heap.peek());
        Assert.assertEquals(-1,heap.removeMin());
        Assert.assertEquals(3,heap.size());

        heap.changePriority(1,5);
        Assert.assertEquals(2,heap.removeMin());
        Assert.assertEquals(3,heap.removeMin());
        Assert.assertEquals(1,heap.removeMin());

        Assert.assertEquals(0,heap.size());

        for(int i = 0; i < 1000; i++){
            heap.insert(i,i);
        }
        Assert.assertEquals(0,heap.removeMin());
        Assert.assertEquals(1,heap.removeMin());
        heap.changePriority(2,100);
        Assert.assertEquals(3,heap.removeMin());

    }
}
