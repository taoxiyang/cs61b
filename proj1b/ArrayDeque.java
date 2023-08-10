/**
 * @author qiushui
 * @Date 2023/8/9
 */
public class ArrayDeque<T> implements Deque<T>{

    private T[] items;
    private int startIndex = 0;
    private int size = 0;

    public ArrayDeque(){
        items = (T[]) new Object[8];
    }

    @Override
    public void addFirst(T item) {
        if(isEmpty()){
            items[startIndex] = item;
        }else{
            if(size == items.length){
                resize(size << 1);
            }
            startIndex -= 1;
            if(startIndex < 0){
                startIndex += items.length;
            }
            items[startIndex] = item;
        }
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if(isEmpty()){
            items[startIndex] = item;
        }else{
            if(size == items.length){
                resize(size << 1);
            }
            int index = startIndex + size;
            if(index >= items.length){
                index -= items.length;
            }
            items[index] = item;
        }
        size += 1;
    }

    private void resize(int newSize){
        T[] newItems = (T[]) new Object[newSize];

        if(startIndex + size <= items.length){
            System.arraycopy(items,startIndex,newItems,0,size);
        }else{
            int firstHalf = items.length-startIndex;
            System.arraycopy(items,startIndex,newItems,0,firstHalf);
            System.arraycopy(items,0,newItems,firstHalf,size-firstHalf);
        }
        startIndex = 0;
        this.items = newItems;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        if (!isEmpty()) {
            for(int i = 0; i < size; i++){
                System.out.print(get(i) + " ");
            }
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if(isEmpty()){
            return null;
        }
        T t = getFirst();
        startIndex += 1;
        if(startIndex >= items.length){
            startIndex = 0;
        }
        size -= 1;
        tryShrink();
        return t;
    }

    @Override
    public T removeLast() {
        if(isEmpty()){
            return null;
        }
        T t = getLast();
        size -= 1;
        tryShrink();
        return t;
    }

    private void tryShrink(){
        if(items.length >= 16 && size <= (items.length >> 2)){
            resize(items.length >> 1);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size){
            return null;
        }
        index += startIndex;
        if(index >= items.length){
            index -= items.length;
        }
        return items[index];
    }


    private T getLast() {
        return get(size -1);
    }


    private T getFirst() {
        return get(0);
    }


    private T getRecursive(int index) {
        return null;
    }
}
