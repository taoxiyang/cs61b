/**
 * @author qiushui
 * @Date 2023/9/25
 */
public class Quadtree {

    private final static String name = "d%d_x%d_y%d.png";

    private String picname;

    private int depth;

    private int x;

    private int y;

    private double ullon;

    private double ullat;

    private double lrlon;

    private double lrlat;

    private Quadtree ul;

    private Quadtree ur;

    private Quadtree ll;

    private Quadtree lr;

    public Quadtree(double ullon, double ullat, double lrlon, double lrlat, int depth, int x, int y){
        this.ullon = ullon;
        this.ullat = ullat;
        this.lrlon = lrlon;
        this.lrlat = lrlat;
        this.depth = depth;
        this.x = x;
        this.y = y;
        this.picname = String.format(name,depth,x,y);
        if(depth < 7){
            double halfLon = (lrlon - ullon) / 2;
            double halfLat =  (lrlat - ullat) / 2;
            this.ul = new Quadtree(ullon, ullat, ullon + halfLon, ullat + halfLat,
                    depth+1,2 * x, 2 * y);
            this.ur = new Quadtree(ullon + halfLon, ullat,lrlon, ullat + halfLat,
                    depth+1,2 * x + 1,2 * y);
            this.ll = new Quadtree(ullon,ullat + halfLat,ullon + halfLon,lrlat, depth+1,2 * x,2 * y + 1);
            this.lr = new Quadtree(ullon + halfLon,ullat + halfLat,lrlon,lrlat, depth+1,2 * x + 1,2 * y + 1);
        }
    }

    public String getPic(int depth, double lon, double lat){
        if(!contains(lon,lat)){
            return "";
        }
        if(depth == this.depth){
            return this.picname;
        }else if(depth < this.depth){
            throw new IllegalArgumentException("wrong depth! this depth : " + this.depth + ", query depth : " + depth);
        }
        return ul.getPic(depth,lon,lat)
                + ur.getPic(depth,lon,lat)
                + ll.getPic(depth,lon,lat)
                + lr.getPic(depth,lon,lat);
    }

    private boolean contains(double lon, double lat){
        return lon < lrlon && lon > ullon && lat < ullat && lat > lrlat;
    }

}
