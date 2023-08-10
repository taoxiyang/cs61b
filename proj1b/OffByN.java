/**
 * @author qiushui
 * @Date 2023/8/10
 */
public class OffByN implements CharacterComparator {

    private int n;


    public OffByN(int N) {
        n = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == n;
    }
}
