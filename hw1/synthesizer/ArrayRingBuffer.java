package synthesizer;// TODO: Make sure to make this class a part of the synthesizer package
// package <package name>;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        rb = (T[]) new Object[capacity];
        this.capacity = capacity;
        first = 0;
        last = 0;
        fillCount = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if(isFull())
            throw new RuntimeException("Ring buffer overflow");
        rb[last++] = x;
        if(last >= capacity)
            last = 0;
        fillCount++;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if(isEmpty())
            throw new RuntimeException("Ring buffer underflow");
        T t = rb[first++];
        if(first >= capacity)
            first = 0;
        fillCount--;
        return t;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if(isEmpty())
            throw new RuntimeException("Ring buffer underflow");
        return rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.

    private class MyIterator implements Iterator<T>{

        private int index = first;

        @Override
        public boolean hasNext() {
            if(first <= last){
                return index >= first && index <= last;
            }
            return index >= first || index <= last;
        }

        @Override
        public T next() {
            T t = rb[index];
            index++;
            if(index >= capacity)
                index = 0;
            return t;
        }
    }
}
