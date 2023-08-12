package synthesizer;

/**
 * @author qiushui
 * @Date 2023/8/11
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {

    protected int fillCount;
    protected int capacity;

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

}
