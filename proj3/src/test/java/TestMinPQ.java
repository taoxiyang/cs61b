import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * @author qiushui
 * @Date 2023/9/27
 */
public class TestMinPQ {

    @Test
    public void test(){
        MinPQ<TestObject> minPQ = new MinPQ();
        minPQ.insert(new TestObject(1L,0.1));
        minPQ.insert(new TestObject(2L,0.2));
        minPQ.insert(new TestObject(3L,0.3));
        minPQ.insert(new TestObject(4L,0.4));
        minPQ.insert(new TestObject(5L,0.5));
        minPQ.insert(new TestObject(6L,0.6));
        Assert.assertEquals(minPQ.remove(), new TestObject(1L,0.3));
        minPQ.insert(new TestObject(7L,0.01));
        Assert.assertEquals(minPQ.remove(), new TestObject(7L,0.3));

        minPQ.insert(new TestObject(5L,0.01));
        Assert.assertEquals(minPQ.remove(), new TestObject(5L,0.3));
    }


    @Test
    public void testTime(){
        MinPQ<PriorityObject> minPQ = new MinPQ();
        int size = 1000000;
        Random random = new Random(1000);
        Stopwatch timer1 = new Stopwatch();
        for(int i = 0; i < size; i++){
            minPQ.insert(new PriorityObject(random.nextDouble()));
        }
        System.out.println(String.format("MinPQ insert take (%.2f seconds)",timer1.elapsedTime()));

        PriorityQueue<PriorityObject> pq = new PriorityQueue();
        timer1 = new Stopwatch();
        for(int i = 0; i < size; i++){
            pq.add(new PriorityObject(random.nextDouble()));
        }
        System.out.println(String.format("PriorityQueue insert take (%.2f seconds)",timer1.elapsedTime()));

        timer1 = new Stopwatch();
        while (!minPQ.isEmpty()){
            minPQ.remove();
        }
        System.out.println(String.format("MinPQ remove take (%.2f seconds)",timer1.elapsedTime()));

        timer1 = new Stopwatch();
        while (!pq.isEmpty()){
            pq.remove();
        }
        System.out.println(String.format("PriorityQueue remove take (%.2f seconds)",timer1.elapsedTime()));

    }


    class PriorityObject implements PriorityItem{

        private double priority;

        public PriorityObject(double priority){
            this.priority = priority;
        }

        @Override
        public double getPriority() {
            return priority;
        }
    }


    class TestObject implements PriorityItem{

        private double priority;

        private Long id;

        public TestObject(Long id,double priority) {
            this.priority = priority;
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestObject that = (TestObject) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public double getPriority() {
            return priority;
        }
    }
}
