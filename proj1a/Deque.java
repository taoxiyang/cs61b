/**
 * @author qiushui
 * @Date 2023/8/9
 */
public interface Deque<T> {

    public void addFirst(T item);

    public void addLast(T item);

    public boolean isEmpty();

    public int size();

    public void printDeque();

    public T removeFirst();

    public T removeLast();

    public T get(int index);

    public T getLast();

    public T getFirst();

    public T getRecursive(int index);
}
