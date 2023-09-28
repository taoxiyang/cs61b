import java.util.HashMap;
import java.util.Map;

/**
 * @author qiushui
 * @Date 2023/9/27
 */
public class MinPQ<T extends PriorityItem> {

    /**
     * 存放T的数组，T[0] 为空不使用，第一个元素从T[1]开始，最后一个元素为T[size]
     */
    private Object[] items;

    /**
     * 存放元素T在数组中的index位置
     */
    private Map<T,Integer> map = new HashMap<>();

    /**
     * 元素个数
     */
    private int size;

    public MinPQ(){
        items = new Object[8];
    }

    /**
     * 删除最小的元素，也就是第一个元素
     * 交换第一个元素和最后一个元素，将交换后的第一个元素下沉到合适位置
     * @return
     */
    public T remove(){
        if(size == 0){
            return null;
        }
        T t = (T) items[1];
        swap(1,size);
        map.remove(t);
        size--;
        sink(1);
        return t;
    }

    /**
     * 插入一个新元素，放在数组最后，新放入的元素往上冒泡，直到合适位置
     * @param t
     */
    public void insert(T t){
        if(t == null){
            throw new IllegalArgumentException("PriorityItem can not be null");
        }
        if(size == items.length - 1){
            Object[] newItems = new Object[items.length * 2];
            System.arraycopy(items,0,newItems,0,items.length);
            items = newItems;
        }
        if(containes(t)){
            update(t);
        }else{
            items[++size] = t;
            map.put(t,size);
            swam(size);
        }
    }

    public int size(){
        return size;
    }

    public double getPriority(T t){
        Integer idx = map.get(t);
        if(idx == null || idx <= 0 || idx > size){
            throw new IllegalArgumentException("not containes [" + t + "]");
        }
        return ((T)items[idx]).getPriority();
    }

    public boolean containes(T t){
        return map.containsKey(t);
    }

    public void update(T t){
        if(!containes(t)){
            throw new IllegalArgumentException("not containes [" + t + "]");
        }
        int idx = map.get(t);
        items[idx] = t;
        int pIdx = parent(idx);
        if(pIdx > 0 && t.compareTo((T)items[pIdx]) < 0){
            swam(idx);
        }else {
            sink(idx);
        }
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    /**
     * 返回idx位置的左子元素的index
     * @param idx
     * @return
     */
    private int left(int idx){
        return 2 * idx;
    }

    /**
     * 返回idx位置的右子元素的index
     * @param idx
     * @return
     */
    private int right(int idx){
        return 2 * idx + 1;
    }

    private int parent(int idx){
        return idx / 2;
    }

    /**
     * 将idx位置的元素下沉到合适位置
     * @param idx
     */
    private void sink(int idx){
        T left = left(idx) > size ? null : (T)items[left(idx)];
        T right = right(idx) > size ? null : (T)items[right(idx)];
        if(left == null && right == null){
            return;
        }
        T minChild = left == null ? right : (right == null ? left : (left.compareTo(right) < 0 ? left : right));
        int minChildIdx = minChild == left ? left(idx) : right(idx);
        T current = (T)items[idx];
        if(current.compareTo(minChild) > 0){
            swap(minChildIdx,idx);
            sink(minChildIdx);
        }
    }

    /**
     * 将idx位置的元素冒泡到合适位置
     * @param idx
     */
    private void swam(int idx){
        if(idx == 1){
            return;
        }
        int pIdx = parent(idx);
        if(((T)items[idx]).compareTo((T)items[pIdx]) < 0){
            swap(idx,pIdx);
            swam(pIdx);
        }
    }

    /**
     * 交换两个位置的元素
     * @param i
     * @param j
     */
    private void swap(int i, int j){
        T t = (T)items[i];
        items[i] = items[j];
        items[j] = t;
        map.put((T)items[i],i);
        map.put((T)items[j],j);
    }

}
