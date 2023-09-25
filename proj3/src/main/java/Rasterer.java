import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    private Quadtree quadtree;

    public Rasterer() {
        quadtree = new Quadtree(MapServer.ROOT_ULLON,MapServer.ROOT_ULLAT,
                MapServer.ROOT_LRLON, MapServer.ROOT_LRLAT,0,0,0);
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        Map<String, Object> results = new HashMap<>();
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");
        double width = params.get("w");

        int depth = findSuitableDepth(Math.abs(lrlon - ullon) / width);

        double queryLrlon = lrlon;
        double queryUllon = ullon;
        double queryUllat = ullat;
        double queryLrlat = lrlat;

        if(queryUllon < MapServer.ROOT_ULLON){
            queryUllon = MapServer.ROOT_ULLON + MapServer.LON_DELT;
        }
        if(queryUllat > MapServer.ROOT_ULLAT){
            queryUllat = MapServer.ROOT_ULLAT - MapServer.LAT_DELT;
        }
        if(queryLrlon > MapServer.ROOT_LRLON){
            queryLrlon = MapServer.ROOT_LRLON - MapServer.LON_DELT;
        }
        if(queryLrlat < MapServer.ROOT_LRLAT){
            queryLrlat = MapServer.ROOT_LRLAT + MapServer.LAT_DELT;
        }

        if(queryUllon >= queryLrlon || queryUllat <= queryLrlat){
            results.put("query_success",false);
            results.put("depth",depth);
            results.put("raster_ul_lon",0);
            results.put("raster_ul_lat",0);
            results.put("raster_lr_lon",0);
            results.put("raster_lr_lat",0);
            results.put("render_grid",null);
            return results;
        }

        String ulPic = quadtree.getPic(depth,queryUllon,queryUllat);
        int x1 = getX(ulPic);
        int y1 = getY(ulPic);
        String lrPic = quadtree.getPic(depth,queryLrlon,queryLrlat);
        int x2 = getX(lrPic);
        int y2 = getY(lrPic);

        int rows = y2 - y1 + 1;
        int cols = x2 - x1 + 1;
        String[][] render_grid = new String[rows][cols];
        for(int col = 0; col < cols; col++){
            for(int row = 0; row < rows; row++){
                render_grid[row][col] = String.format("d%d_x%d_y%d.png",depth,x1 + col,y1 + row);
            }
        }

        results.put("query_success",true);
        results.put("depth",depth);
        results.put("raster_ul_lon",getPicUllon(render_grid[0][0]));
        results.put("raster_ul_lat",getPicUllat(render_grid[0][0]));
        results.put("raster_lr_lon",getPicLrlon(render_grid[rows-1][cols-1]));
        results.put("raster_lr_lat",getPicLrlat(render_grid[rows-1][cols-1]));
        results.put("render_grid",render_grid);
        return results;
    }

    /**
     * takes the picture name and returns the upper left longitude
     * @param picture
     * @return
     */
    private double getPicUllon(String picture){
        PictureName pictureName = PictureName.fromString(picture);
        int depth = pictureName.getDepth();
        int x = pictureName.getX();
        double delt = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / ( Math.pow(2, depth) );
        return MapServer.ROOT_ULLON + delt * x;
    }

    /**
     * takes the picture name and returns the upper left latitude
     * @param picture
     * @return
     */
    private double getPicUllat(String picture){
        PictureName pictureName = PictureName.fromString(picture);
        int depth = pictureName.getDepth();
        int y = pictureName.getY();
        double delt = (MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT) / ( Math.pow(2, depth) );
        return MapServer.ROOT_ULLAT + delt * y;
    }

    /**
     * takes the picture name and returns the lower right longitude
     * @param picture
     * @return
     */
    private double getPicLrlon(String picture){
        PictureName pictureName = PictureName.fromString(picture);
        int depth = pictureName.getDepth();
        int x = pictureName.getX();
        double delt = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / ( Math.pow(2, depth) );
        return MapServer.ROOT_ULLON + delt * (x + 1);
    }

    /**
     * takes the picture name and returns the lower right latitude
     * @param picture
     * @return
     */
    private double getPicLrlat(String picture){
        PictureName pictureName = PictureName.fromString(picture);
        int depth = pictureName.getDepth();
        int y = pictureName.getY();
        double delt = (MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT) / ( Math.pow(2, depth) );
        return MapServer.ROOT_ULLAT + delt * (y + 1);
    }

    private int getX(String picture){
        return PictureName.fromString(picture).getX();
    }

    private int getY(String picture){
        return PictureName.fromString(picture).getY();
    }

    /**
     * Takes a user query LonDPP and return suitable depth
     * Have the greatest LonDPP that is less than or equal to the LonDPP of the query box (as zoomed out as possible).
     * If the requested LonDPP is less than what is available in the data files, use the lowest LonDPP available instead (i.e. depth 7 images).
     * @param queryLonDPP
     * @return
     */
    private int findSuitableDepth(double queryLonDPP){
        double dLonDPP = Math.abs(MapServer.ROOT_ULLON - MapServer.ROOT_LRLON) / MapServer.TILE_SIZE;
        int depth = 0;
        while (queryLonDPP <  dLonDPP && depth < 7){
            dLonDPP = dLonDPP / 2;
            depth++;
        }
        return depth;
    }

}
