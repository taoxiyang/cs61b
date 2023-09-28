import java.util.Comparator;

/**
 * @author qiushui
 * @Date 2023/9/28
 */
public class KDTree<T> {

    private MultiDimensionalComparator<T> comparator;

    private KDTreeNode<T> root;

    private int size;

    public KDTree(MultiDimensionalComparator<T> comparator){
        this.comparator = comparator;
    }

    public void insert(T t){
        root = insert(t, root, root,0);
    }

    private KDTreeNode insert(T t, KDTreeNode<T> node,KDTreeNode<T> parent, int depth){
        if(node == null){
            size++;
            KDTreeNode newNode = new KDTreeNode(t);
            newNode.parent = parent;
            return newNode;
        }
        if(t.equals(node.item)){
            return node;
        }
        Comparator<T> cmp = comparator.comparator(depth);
        if(cmp.compare(t,node.item) < 0){
            node.left = insert(t, node.left, node,depth+1);
        }else{
            node.right = insert(t, node.right, node,depth+1);
        }
        return node;
    }

    public boolean contains(T t){
        return contains(t, root,0);
    }

    private boolean contains(T t, KDTreeNode<T> node, int depth){
        if(node == null){
            return false;
        }
        if(t.equals(node.item)){
            return true;
        }
        Comparator<T> cmp = comparator.comparator(depth);
        if(cmp.compare(t,node.item) < 0){
            return contains(t, node.left, depth + 1);
        }else{
            return contains(t, node.right, depth + 1);
        }
    }

    public int size(){
        return size;
    }

    public T closest(T goal){
        if(size == 0){
            return null;
        }
        return closest(goal,root,root,0).item;
    }

    private KDTreeNode<T> closest(T goal, KDTreeNode<T> node, KDTreeNode<T> best, int depth){
        if(node == null){
            return best;
        }
        if(comparator.distance(goal,node.item) < comparator.distance(goal,best.item)){
            best = node;
        }

        KDTreeNode<T> good;
        KDTreeNode<T> bad;
        if(comparator.comparator(depth).compare(goal,node.item) < 0){
            good = node.left;
            bad = node.right;
        }else{
            good = node.right;
            bad = node.left;
        }
        best = closest(goal,good,best,depth + 1);
        if(canBeBetter(goal, node, best, depth)){
            best = closest(goal, bad, best,depth + 1);
        }
        return best;
    }

    private boolean canBeBetter(T goal, KDTreeNode<T> node, KDTreeNode<T> best, int depth){
        T buestGuess = comparator.bestGuess(goal,node.item,depth);
        double bestDistance = comparator.distance(goal,best.item);
        double guessDistance = comparator.distance(goal,buestGuess);
        return guessDistance < bestDistance;
    }

    static class KDTreeNode<T>{
        KDTreeNode parent;
        KDTreeNode left;
        KDTreeNode right;
        final T item;
        public KDTreeNode(T item) {
            this.item = item;
        }
    }


}
