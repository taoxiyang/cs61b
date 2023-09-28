import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {

//        stlon = -122.25997195302324;
//        stlat = 37.842982707731025;
//        destlon = -122.28965562773821;
//        destlat = 37.83418402502091;
//
//        Double d1 = GraphDB.distance(stlon,stat,g.getNode(93069817).getLongitude(),g.getNode(93069817).getLatitude());
//        Double d2 = GraphDB.distance(stlon,stlat,g.getNode(266433383).getLongitude(),g.getNode(266433383).getLatitude());
//

        Long start = g.closest(stlon,stlat);
        Long end = g.closest(destlon,destlat);

        MinPQ<SearchNode> pq = new MinPQ<>();
        SearchNode searchNode = new SearchNode(start,end,0,g,null);
        while (!searchNode.isDest()){
            for(Long w : g.adjacent(searchNode.id)){
                if(searchNode.parent != null && w.longValue() == searchNode.parent.id.longValue()){
                    //do nothing
                }else{
                    double distance = searchNode.distance + g.distance(w,searchNode.id);
                    SearchNode t = new SearchNode(w,end,distance, g,searchNode);
                    if(!pq.containes(t)){
                        pq.insert(t);
                    }else if(pq.getPriority(t) > t.getPriority()){
                        pq.update(t);
                    }
                }
            }
            if(pq.isEmpty()){
                break;
            }
            searchNode = pq.remove();
        }
        if(!searchNode.isDest()){
            throw new IllegalStateException("no path found!");
        }
        List<Long> list = new ArrayList<>();
        while (searchNode != null){
            list.add(searchNode.id);
            searchNode = searchNode.parent;
        }
        Collections.reverse(list);
        return list;
    }

    static class SearchNode implements PriorityItem {

        private Long id;

        private Long dest;

        private double distance;

        SearchNode parent;

        private double estimateDistance;

        public SearchNode(Long id, Long dest, double distance,GraphDB g, SearchNode parent) {
            this.id = id;
            this.dest = dest;
            this.distance = distance;
            this.parent = parent;
            this.estimateDistance = g.distance(id,dest);
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SearchNode that = (SearchNode) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        public boolean isDest(){
            return dest.longValue() == id.longValue();
        }

        @Override
        public double getPriority() {
            return distance + estimateDistance;
        }
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        int a = 1;
        if(a == 1){
            return new ArrayList<>();
        }

        List<NavigationDirection> directions = new ArrayList<>();
        NavigationDirection direction = null;
        double preBearing = 0;
        for(int i = 1; i < route.size(); i++){
            long v = route.get(i-1);
            long w = route.get(i);
            GraphDB.Way way = g.commonWay(v,w);
            String wayName = (way.getName() == null || way.getName().isBlank()) ? NavigationDirection.UNKNOWN_ROAD : way.getName();
            double curBearing = g.bearing(w,v);
            double bearing = curBearing - preBearing;
            preBearing = curBearing;
            double distance = g.distance(v,w);
            if(direction == null){
                direction = new NavigationDirection();
                direction.direction = NavigationDirection.START;
                direction.distance = distance;
                direction.way = wayName;
                directions.add(direction);
            }else {
                if(wayName.equals(direction.way)){
                    direction.distance += distance;
                }else{
                    direction = new NavigationDirection();
                    if(bearing <= 15 && bearing >= -15){
                        direction.direction = NavigationDirection.STRAIGHT;
                    }else if(bearing < -15 && bearing >= -30){
                        direction.direction = NavigationDirection.SLIGHT_LEFT;
                    }else if(bearing > 15 && bearing <= 30){
                        direction.direction = NavigationDirection.SLIGHT_RIGHT;
                    }else if(bearing < -30 && bearing >= -100){
                        direction.direction = NavigationDirection.LEFT;
                    }else if(bearing > 30 && bearing <= 100){
                        direction.direction = NavigationDirection.RIGHT;
                    }else if(bearing < -100){
                        direction.direction = NavigationDirection.SHARP_LEFT;
                    }else if(bearing > 100){
                        direction.direction = NavigationDirection.SHARP_RIGHT;
                    }
                    direction.distance = distance;
                    direction.way = wayName;
                    directions.add(direction);
                }
            }
        }
        return directions;
    }

    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}
