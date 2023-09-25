import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author qiushui
 * @Date 2023/9/25
 */
public class PictureName {

    private int x;

    private int y;

    private int depth;

    public PictureName(int x, int y, int depth) {
        this.x = x;
        this.y = y;
        this.depth = depth;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDepth() {
        return depth;
    }

    public static PictureName fromString(String picture){
        Pattern filePattern = Pattern.compile("d(\\d+)_x(\\d+)_y(\\d+)\\.png");
        Matcher matcher = filePattern.matcher(picture);
        if(!matcher.find()){
            throw new IllegalArgumentException("illegal file name " + picture);
        }
        int depth = Integer.parseInt(matcher.group(1));
        int x = Integer.parseInt(matcher.group(2));
        int y = Integer.parseInt(matcher.group(3));
        return new PictureName(x,y,depth);
    }
}
