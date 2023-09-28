import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */

    private Map<Node, Set<Node>> nodeEdgeMap = new HashMap<>();

    private Map<Long, Node> nodeMap = new HashMap<>();

    private KDTree kdTree;

    private Map<Node, Set<Way>> nodeWayMap = new HashMap<>();


    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        List<Long> ids = nodeMap.keySet().stream().collect(Collectors.toList());
        for(Long id : ids){
            Node node = nodeMap.get(id);
            if(nodeEdgeMap.get(node).isEmpty()){
                nodeEdgeMap.remove(node);
                nodeMap.remove(id);
            }
        }
        kdTree = new KDTree();
        for(Node node : nodeMap.values()){
            kdTree.insert(node);
        }
    }

    void addNode(Node node){
        if(nodeMap.get(node.getId()) == null){
            nodeMap.put(node.getId(),node);
            nodeEdgeMap.put(node,new HashSet<>());
        }
    }



    Node getNode(long v){
        Node node = nodeMap.get(v);
        if(node == null){
            throw new IllegalArgumentException("no such node, id : " + v);
        }
        return node;
    }

    void connect(long v, long w){
        Node nodeV = nodeMap.get(v);
        Node nodeW = nodeMap.get(w);
        if(nodeV == null){
            throw new IllegalArgumentException("no such node, id : " + v);
        }
        if(nodeW == null){
            throw new IllegalArgumentException("no such node, id : " + w);
        }
        nodeEdgeMap.get(nodeV).add(nodeW);
        nodeEdgeMap.get(nodeW).add(nodeV);
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.
        return nodeMap.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        Node node = nodeMap.get(v);
        if(node == null){
            throw new IllegalArgumentException("no such node, id : " + v);
        }
        return nodeEdgeMap.get(node).stream().map(Node::getId).collect(Collectors.toList());
    }

    Set<Way> ways(long v){
        Node node = nodeMap.get(v);
        if(node == null){
            throw new IllegalArgumentException("no such node, id : " + v);
        }
        return nodeWayMap.get(node);
    }

    void addWay(Node node, Way way){
        if(nodeWayMap.get(node) == null){
            nodeWayMap.put(node,new HashSet<>());
        }
        nodeWayMap.get(node).add(way);
    }

    Way commonWay(long v, long w){
        Set<Way> wWays = ways(w);
        for(Way way : ways(v)){
            if(wWays.contains(way)){
                return way;
            }
        }
        return null;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param longitude The target longitude.
     * @param latitude The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double longitude, double latitude) {
        Node best = kdTree.closest(new Node(-1L,longitude,latitude)).item;
        return best.getId();
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        Node node = nodeMap.get(v);
        if(node == null){
            throw new IllegalArgumentException("no such node, id : " + v);
        }
        return node.getLongitude();
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        Node node = nodeMap.get(v);
        if(node == null){
            throw new IllegalArgumentException("no such node, id : " + v);
        }
        return node.getLatitude();
    }

    QuadTree insert(Node node, QuadTree tree){
        if(tree == null){
            return new QuadTree(node);
        }
        QuadTree.Dir dir = tree.dir(node.longitude,node.latitude);
        if(dir == QuadTree.Dir.northEast){
            tree.northEast = insert(node, tree.northEast);
        }else if(dir == QuadTree.Dir.northWest){
            tree.northWest = insert(node, tree.northWest);
        }else if(dir == QuadTree.Dir.southEast){
            tree.southEast = insert(node, tree.southEast);
        }else if(dir == QuadTree.Dir.southWest){
            tree.southWest = insert(node, tree.southWest);
        }
        return tree;
    }

    QuadTree closest(double longitude, double latitude, QuadTree node,  QuadTree best){
        if(node == null){
            return best;
        }
        if(distance(node.node.longitude,node.node.latitude,longitude,latitude)
                < distance(best.node.longitude,best.node.latitude,longitude,latitude)){
            best = node;
        }
        QuadTree.Dir dir = node.dir(longitude,latitude);

        best = closest(longitude,latitude,best,best);



        return best;
    }



    static class QuadTree{

        enum Dir{
            northEast, northWest, southEast, southWest
        }

        Node node;

        QuadTree northEast;

        QuadTree northWest;

        QuadTree southEast;

        QuadTree southWest;

        QuadTree(Node node){
            this.node = node;
        }

        private Dir dir(double longitude, double latitude){
            if(longitude >= this.node.longitude){
                if(latitude >= this.node.latitude){
                    return Dir.northEast;
                }else {
                    return Dir.southEast;
                }
            }else{
                if(latitude >= this.node.latitude){
                    return Dir.northWest;
                }else {
                    return Dir.southWest;
                }
            }
        }

        public Node closest(double lon, double lat){
            return closest(lon,lat,node);
        }

        private Node closest(double lon, double lat, Node best){
            if(distance(node.longitude,node.latitude,lon,lat) < distance(best.longitude,best.latitude,lon,lat)){
                best = node;
            }
            Dir dir = dir(lon,lat);
            if(dir == Dir.northEast){
                return northEast == null ? best : northEast.closest(lon,lat,best);
            }else if(dir == Dir.northWest){
                return northWest == null ? best : northWest.closest(lon,lat,best);
            }else if(dir == Dir.southEast){
                return southEast == null ? best : southEast.closest(lon,lat,best);
            }else{
                return southWest == null ? best : southWest.closest(lon,lat,best);
            }
        }
    }

    static class Node{
        private Long id;

        private double latitude;

        private double longitude;

        public Node(Long id,  double longitude, double latitude) {
            this.id = id;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        private String name;

        public Long getId() {
            return id;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isLocation(){
            return name != null && !name.isBlank();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(id, node.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    static class Way{
        private Long id;

        public Way(Long id) {
            this.id = id;
            nodes = new LinkedList<>();
        }

        private String name;

        private String maxspeed;

        private String highway;

        public String getMaxspeed() {
            return maxspeed;
        }

        public void setMaxspeed(String maxspeed) {
            this.maxspeed = maxspeed;
        }

        public String getHighway() {
            return highway;
        }

        public void setHighway(String highway) {
            this.highway = highway;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public LinkedList<Node> getNodes() {
            return nodes;
        }

        private LinkedList<Node> nodes;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Way way = (Way) o;
            return Objects.equals(id, way.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
