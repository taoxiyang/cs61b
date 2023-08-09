/**
 * @author qiushui
 * @Date 2023/8/9
 */
public class LinkedListDeque<T>{

    private Link<T> sentinel = new Link<>(null,null,null);
    private int size = 0;

    public LinkedListDeque() {
    }


    public void addFirst(T item) {
        Link<T> orginalFirst = getFirstLink();
        if(orginalFirst == null){
            orginalFirst = sentinel;
        }
        Link<T> first = new Link<>(item, sentinel , orginalFirst);
        sentinel.next = first;
        orginalFirst.prev = first;
        size += 1;
    }


    public void addLast(T item) {
        Link<T> orginalLast = getLastLink();
        if(orginalLast == null){
            orginalLast = sentinel;
        }
        Link<T> last = new Link<>(item, orginalLast , sentinel);
        sentinel.prev = last;
        orginalLast.next = last;
        size += 1;
    }


    public boolean isEmpty() {
        return size == 0;
    }


    public int size() {
        return size;
    }


    public void printDeque() {
        Link<T> p = sentinel.next;
        while (p != null && p != sentinel){
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }


    public T removeFirst() {
        if(size == 0){
            return null;
        }
        T t = getFirst();
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return t;
    }


    public T removeLast() {
        if(size == 0){
            return null;
        }
        T t = getLast();
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        return t;
    }


    public T get(int index) {
        if(index >= size){
            return null;
        }
        Link<T> p = sentinel.next;
        for(int i = 0; i < index; i++){
            p = p.next;
        }
        return p.item;
    }


    public T getLast() {
        if(size == 0){
            return null;
        }
        return getLastLink().item;
    }


    public T getFirst() {
        if(size == 0){
            return null;
        }
        return getFirstLink().item;
    }

    private Link<T> getLastLink(){
        return sentinel.prev;
    }

    private Link<T> getFirstLink(){
        return sentinel.next;
    }


    public T getRecursive(int index) {
        return getRecursiveHelper(sentinel.next,index);
    }

    private T getRecursiveHelper(Link<T> start, int index){
        if(start == null || start == sentinel){
            return null;
        }
        if(index == 0){
            return start.item;
        }else {
            return getRecursiveHelper(start.next, --index);
        }
    }

    private static class Link<T>{

        static Link empty = new Link(null,null,null);

        T item;
        Link<T> next;
        Link<T> prev;

        Link(T item, Link<T> prev, Link<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

}
