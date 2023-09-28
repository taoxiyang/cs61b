import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author qiushui
 * @Date 2023/9/28
 */
public class TestKDTree {

//    @Test
    public void test(){
        KDTree<Point> kdTree = new KDTree(new PointKDTreeComparator());
        List<Point> pointList = new ArrayList<>();
        Random random = new Random(1000);

        pointList.add(new Point(2,3));
        pointList.add(new Point(1,5));
        pointList.add(new Point(4,2));
        pointList.add(new Point(4,5));
        pointList.add(new Point(4,4));
        pointList.add(new Point(3,3));
        pointList.add(new Point(1,4));
        pointList.add(new Point(0,6));
        pointList.add(new Point(8,4));
        pointList.add(new Point(2,2));
        pointList.add(new Point(10,11));
        pointList.add(new Point(9,8));
        pointList.add(new Point(5,5));
        pointList.add(new Point(4,0));
        pointList.add(new Point(19,7));
        pointList.add(new Point(10,7));
        pointList.add(new Point(8,7));
        pointList.add(new Point(8,5));

        for(Point point : pointList){
            kdTree.insert(point);
        }


        for(int i = 0; i < 10000; i++){
            Point goal = new Point(random.nextInt(40) - 20 ,random.nextInt(40) - 20);
            Point p1 = kdTree.closest(goal);
            Point p2 = closest(goal,pointList);
            Double d1 = p1.distance(goal);
            Double d2 = p2.distance(goal);
            Assert.assertEquals(d1,d2);
        }

    }

    @Test
    public void testMore(){
        KDTree<Point> kdTree = new KDTree(new PointKDTreeComparator());
        List<Point> pointList = new ArrayList<>();
        Random random = new Random(1000);

        for(int i = 0; i < 10000; i++){
            pointList.add(new Point(random.nextInt(150) - 150,random.nextInt(150) - 150));
        }

        for(Point point : pointList){
            kdTree.insert(point);
        }

        for(int i = 0; i < 1000; i++){
            Point goal = new Point(random.nextInt(200) - 200 ,random.nextInt(200) - 200);
            Point p1 = kdTree.closest(goal);
            Point p2 = closest(goal,pointList);
            Double d1 = p1.distance(goal);
            Double d2 = p2.distance(goal);
            Assert.assertEquals(d1,d2);
        }
    }

    private Point closest(Point goal, List<Point> pointList){
        Point best = null;
        for(Point point : pointList){
            if(best == null){
                best = point;
            }else {
                if(goal.distance(best) > goal.distance(point)){
                    best = point;
                }
            }
        }
        return best;
    }
}
