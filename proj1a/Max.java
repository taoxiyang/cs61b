/**
 * @author qiushui
 * @Date 2023/8/10
 */
public class Max {

    public static <T extends Comparable<T>> T max(T[] t){
        t[0].compareTo(t[1]);
        return t[0];
    }
}
