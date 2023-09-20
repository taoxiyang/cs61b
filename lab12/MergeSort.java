import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        // Your code here!
        Queue<Queue<Item>> queues = new Queue<>();
        while (!items.isEmpty()){
            Queue<Item> queue = new Queue<>();
            queue.enqueue(items.dequeue());
            queues.enqueue(queue);
        }
        return queues;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        // Your code here!
        Queue<Item> items = new Queue<>();
        while (!q1.isEmpty() || !q2.isEmpty()){
            items.enqueue(getMin(q1,q2));
        }
        return items;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        int size = items.size();
        if(size == 1){
            return items;
        }

        Queue<Item> left = new Queue<>();
        Queue<Item> right = new Queue<>();
        int i = 0;
//        Iterator<Item> it = items.iterator();
//        while (i < size / 2){
//            left.enqueue(it.next());
//            i++;
//        }
//        while (it.hasNext()){
//            right.enqueue(it.next());
//        }
        while (i < size / 2){
            left.enqueue(items.dequeue());
            i++;
        }
        while (!items.isEmpty()){
            right.enqueue(items.dequeue());
        }
        return mergeSortedQueues(mergeSort(left),mergeSort(right));
    }

    public static void main(String[] args) {
        Queue<String> students = new Queue<String>();
        students.enqueue("Alice");
        students.enqueue("Teacher");
        students.enqueue("Vanessa");
        students.enqueue("Black");
        students.enqueue("Right");
        students.enqueue("Students");
        students.enqueue("Users");
        students.enqueue("Ethan");
        students.enqueue("Zebra");
        students.enqueue("Yes");
        System.out.println(mergeSort(students));
        System.out.println(students);

        Queue<Integer> items = new Queue<Integer>();
        items.enqueue(0);
        items.enqueue(0);
        items.enqueue(2);
        items.enqueue(1);
        items.enqueue(3);
        items.enqueue(4);
        items.enqueue(4);
        items.enqueue(4);
        items.enqueue(13);
        items.enqueue(15);
        items.enqueue(19);
        items.enqueue(6);
        items.enqueue(15);
        items.enqueue(5);
        items.enqueue(12);
        items.enqueue(11);
        items.enqueue(19);
        items.enqueue(11);
        items.enqueue(8);
        items.enqueue(5);

        for(int i = 20; i < 10000; i++){
            items.enqueue(10000-i);
        }
        System.out.println(mergeSort(items));
        System.out.println(items);
    }
}
