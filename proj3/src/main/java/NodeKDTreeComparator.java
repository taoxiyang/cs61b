import java.util.Comparator;

/**
 * @author qiushui
 * @Date 2023/9/28
 */
public class NodeKDTreeComparator implements MultiDimensionalComparator<GraphDB.Node>{

    private final static Comparator<GraphDB.Node> comparatorLongitude = new Comparator<GraphDB.Node>() {
        @Override
        public int compare(GraphDB.Node o1, GraphDB.Node o2) {
            double cmp = o1.getLongitude() - o2.getLongitude();
            return cmp == 0 ? 0 : (cmp > 0 ? 1 : -1);
        }
    };

    private final static Comparator<GraphDB.Node> comparatorLatitude = new Comparator<GraphDB.Node>() {
        @Override
        public int compare(GraphDB.Node o1, GraphDB.Node o2) {
            double cmp = o1.getLatitude() - o2.getLatitude();
            return cmp == 0 ? 0 : (cmp > 0 ? 1 : -1);
        }
    };

    private final static Comparator<GraphDB.Node>[] comparators = new Comparator[]{comparatorLongitude, comparatorLatitude};

    private final static int dimensional = 2;

    @Override
    public int dimensional() {
        return dimensional;
    }

    @Override
    public Comparator<GraphDB.Node> comparator(int depth) {
        return comparators[depth % dimensional];
    }

    @Override
    public GraphDB.Node bestGuess(GraphDB.Node goal, GraphDB.Node node, int depth) {
        if(depth % dimensional == 0){
            return new GraphDB.Node(-1L, node.getLongitude(),goal.getLatitude());
        }
        return new GraphDB.Node(-1L, goal.getLongitude(),node.getLatitude());
    }

    @Override
    public double distance(GraphDB.Node o1, GraphDB.Node o2) {
        return GraphDB.distance(o1.getLongitude(),o1.getLatitude(),o2.getLongitude(),o2.getLatitude());
    }
}
