package synthesizer;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author qiushui
 * @Date 2023/8/11
 */
public class ArrayRingBufferTest {

    @Test
    public void test(){
        ArrayRingBuffer<Integer> arrayRingBuffer = new ArrayRingBuffer(8);
        Assert.assertTrue(arrayRingBuffer.isEmpty());
        Assert.assertEquals(arrayRingBuffer.fillCount(),0);
        Assert.assertEquals(arrayRingBuffer.capacity(),8);
        arrayRingBuffer.enqueue(1);
        Assert.assertTrue(!arrayRingBuffer.isEmpty());
        Assert.assertEquals(arrayRingBuffer.fillCount(),1);
        Assert.assertEquals(arrayRingBuffer.capacity(),8);
        arrayRingBuffer.enqueue(2);
        Assert.assertEquals(arrayRingBuffer.dequeue(),new Integer(1));
        Assert.assertEquals(arrayRingBuffer.dequeue(),new Integer(2));
        Assert.assertTrue(arrayRingBuffer.isEmpty());
        Assert.assertEquals(arrayRingBuffer.fillCount(),0);
        Assert.assertEquals(arrayRingBuffer.capacity(),8);

        arrayRingBuffer.enqueue(1);
        arrayRingBuffer.enqueue(2);
        arrayRingBuffer.enqueue(3);
        arrayRingBuffer.enqueue(4);
        arrayRingBuffer.enqueue(5);
        arrayRingBuffer.enqueue(6);
        arrayRingBuffer.enqueue(7);
        arrayRingBuffer.enqueue(8);

        Assert.assertTrue(!arrayRingBuffer.isEmpty());
        Assert.assertEquals(arrayRingBuffer.fillCount(),8);
        Assert.assertTrue(arrayRingBuffer.isFull());

        try {
            arrayRingBuffer.enqueue(1);
            Assert.fail("can not enqueue");
        }catch (Exception e){
        }

        Assert.assertEquals(arrayRingBuffer.dequeue(),new Integer(1));
        Assert.assertEquals(arrayRingBuffer.dequeue(),new Integer(2));

        arrayRingBuffer.enqueue(1);
        arrayRingBuffer.enqueue(2);

        try {
            arrayRingBuffer.enqueue(1);
            Assert.fail("can not enqueue");
        }catch (Exception e){
        }

        Assert.assertEquals(arrayRingBuffer.dequeue(),new Integer(3));
        Assert.assertEquals(arrayRingBuffer.dequeue(),new Integer(4));

        Assert.assertEquals(arrayRingBuffer.peek(),new Integer(5));
        Assert.assertEquals(arrayRingBuffer.peek(),new Integer(5));

        Assert.assertEquals(arrayRingBuffer.dequeue(),new Integer(5));
        Assert.assertEquals(arrayRingBuffer.dequeue(),new Integer(6));
        Assert.assertEquals(arrayRingBuffer.dequeue(),new Integer(7));
        Assert.assertEquals(arrayRingBuffer.dequeue(),new Integer(8));
        Assert.assertEquals(arrayRingBuffer.dequeue(),new Integer(1));
        Assert.assertEquals(arrayRingBuffer.dequeue(),new Integer(2));

        Assert.assertTrue(arrayRingBuffer.isEmpty());
        Assert.assertEquals(arrayRingBuffer.fillCount(),0);
        Assert.assertEquals(arrayRingBuffer.capacity(),8);

        try {
            arrayRingBuffer.dequeue();
            Assert.fail("can not dequeue");
        }catch (Exception e){
        }

        try {
            arrayRingBuffer.peek();
            Assert.fail("can not peek");
        }catch (Exception e){
        }
    }

    @Test
    public void testIterator(){
        ArrayRingBuffer<Integer> arrayRingBuffer = new ArrayRingBuffer(8);
        arrayRingBuffer.enqueue(1);
        arrayRingBuffer.enqueue(2);
        arrayRingBuffer.enqueue(3);
        arrayRingBuffer.enqueue(4);
        arrayRingBuffer.enqueue(5);
        arrayRingBuffer.enqueue(6);
        arrayRingBuffer.enqueue(7);
        arrayRingBuffer.enqueue(8);

        int i = 0;
        for(Integer integer : arrayRingBuffer){
            Assert.assertEquals(integer,new Integer(++i));
        }
        Assert.assertTrue(arrayRingBuffer.isEmpty());
    }
}
