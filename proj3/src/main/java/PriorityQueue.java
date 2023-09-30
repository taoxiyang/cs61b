/**
 * @author qiushui
 * @Date 2023/9/30
 */
public class PriorityQueue<T extends PriorityItem> {

    private int size;

    private Object[] items;

    public PriorityQueue(int size){
        items = new Object[size + 1];
    }

    public int size(){
        return size;
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

}
