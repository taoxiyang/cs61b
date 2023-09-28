import java.util.Comparator;

/**
 * @author qiushui
 * @Date 2023/9/28
 */
public interface MultiDimensionalComparator<T> {

    int dimensional();

    Comparator<T> comparator(int depth);

    T bestGuess(T goal, T node, int depth);

    double distance(T o1, T o2);
}
