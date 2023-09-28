import java.util.Comparator;

/**
 * @author qiushui
 * @Date 2023/9/28
 */
public class PointKDTreeComparator implements MultiDimensionalComparator<Point>{

    private final static Comparator<Point> comparatorX = new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
            return o1.x - o2.x;
        }
    };

    private final static Comparator<Point> comparatorY = new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
            return o1.y - o2.y;
        }
    };

    private final static Comparator<Point>[] comparators = new Comparator[]{comparatorX, comparatorY};

    private final static int dimensional = 2;

    @Override
    public int dimensional() {
        return dimensional;
    }

    @Override
    public Comparator<Point> comparator(int depth) {
        return comparators[depth % dimensional];
    }

    @Override
    public Point bestGuess(Point goal, Point node, int depth) {
        if(depth % dimensional == 0){
            return new Point(node.x,goal.y);
        }
        return new Point(goal.x,node.y);
    }

    @Override
    public double distance(Point o1, Point o2) {
        return Math.sqrt(Math.pow(o1.x - o2.x, 2) + Math.pow(o1.y - o2.y, 2));
    }
}
