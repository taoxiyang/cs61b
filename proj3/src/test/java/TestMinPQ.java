import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

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
        Assert.assertEquals(minPQ.size(), 5);
        Assert.assertEquals(minPQ.remove(), new TestObject(5L,0.3));
        minPQ.update(new TestObject(3L,0.01));
        Assert.assertEquals(minPQ.remove(), new TestObject(3L,0.3));

        minPQ.insert(new TestObject(16L,0.6));
        minPQ.insert(new TestObject(17L,0.61));
        minPQ.insert(new TestObject(18L,0.62));
        minPQ.insert(new TestObject(19L,0.63));
        minPQ.insert(new TestObject(20L,0.64));
        minPQ.insert(new TestObject(21L,0.59));

        Assert.assertEquals(minPQ.remove(), new TestObject(2L,0.3));

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
