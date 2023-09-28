import java.util.Objects;

/**
 * @author qiushui
 * @Date 2023/9/28
 */
public class Point {

    public final int x;

    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point o){
        double v = Math.pow(x - o.x, 2) + Math.pow(y - o.y, 2);
        return Math.sqrt(v);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
