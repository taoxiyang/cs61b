/**
 * @author qiushui
 * @Date 2023/9/27
 */
public class KDTree {

    private KDNode root;

    public KDTree() {
    }

    public void insert(GraphDB.Node item){
        root = insert(item, root, 0);
    }

    private KDNode insert(GraphDB.Node item, KDNode node, int depth){
        if(node == null){
            return new KDNode(item);
        }
        if(item.getLatitude() == node.item.getLatitude()
                && item.getLatitude() == node.item.getLatitude()){
            KDNode newNode = new KDNode(item);
            newNode.left = node.left;
            newNode.right = node.right;
            return newNode;
        }
        if(depth % 2 == 0){
            if(item.getLongitude() < node.item.getLongitude()){
                node.left = insert(item, node.left, depth+1);
            }else {
                node.right = insert(item, node.right, depth+1);
            }
        }else{
            if(item.getLatitude() < node.item.getLatitude()){
                node.left = insert(item, node.left, depth+1);
            }else {
                node.right = insert(item, node.right, depth+1);
            }
        }
        return node;
    }

    public KDNode closest(GraphDB.Node goal){
        KDNode node =  closest(goal,root,root, 0);
        return node;
    }

    private KDNode closest(GraphDB.Node goal, KDNode node, KDNode best, int depth){
        if(node == null){
            return best;
        }
        if(distance(goal,node) < distance(goal,best)){
            best = node;
        }
        Side goodSide;
        Side badSide;
        if(depth % 2 == 0){
            if(goal.getLongitude() < node.item.getLongitude()){
                goodSide = Side.left;
                badSide = Side.right;
            }else{
                goodSide = Side.right;
                badSide = Side.left;
            }
        }else {
            if(goal.getLatitude() < node.item.getLatitude()){
                goodSide = Side.left;
                badSide = Side.right;
            }else{
                goodSide = Side.right;
                badSide = Side.left;
            }
        }
        KDNode good = goodSide == Side.left ? node.left : node.right;
        KDNode bad = badSide == Side.left ? node.left : node.right;
        best = closest(goal,good,best,depth+1);
        if(canHaveCloser(goal, node, best, depth)){
            best = closest(goal,bad, best,depth+1);
        }
        return best;
    }

    /**
     * 有进一步优化空间
     * @param goal
     * @param node
     * @param best
     * @param depth
     * @return
     */
    private boolean canHaveCloser(GraphDB.Node goal, KDNode node, KDNode best, int depth){
        double closestDistance = distance(goal,best);
        double bestDistance = 0;
        if(depth % 2 == 0){
            bestDistance = distance(goal,new GraphDB.Node(-1L,node.item.getLongitude(),goal.getLatitude()));
        }else {
            bestDistance = distance(goal,new GraphDB.Node(-1L,goal.getLongitude(),node.item.getLatitude()));
        }
        return closestDistance > bestDistance;
    }

    private double distance(GraphDB.Node item, GraphDB.Node node){
        return GraphDB.distance(item.getLongitude(),item.getLatitude(),
                node.getLongitude(),node.getLatitude());
    }

    private double distance(GraphDB.Node item, KDNode node){
        return distance(item, node.item);
    }

    enum Side{
        left, right;
    }

    class KDNode{
        final GraphDB.Node item;

        KDNode left;

        KDNode right;

        public KDNode(GraphDB.Node item) {
            this.item = item;
        }
    }
}
