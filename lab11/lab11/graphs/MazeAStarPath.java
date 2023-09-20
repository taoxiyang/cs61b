package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private MinPQ<SearchNode> pq = new MinPQ();

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        SearchNode searchNode = new SearchNode(0,s);
        pq.insert(searchNode);

        while (!pq.isEmpty() && !targetFound){
            SearchNode node = pq.delMin();
            marked[node.v] = true;
            announce();

            if(node.v == t){
                targetFound = true;
                break;
            }

            for(int w : maze.adj(node.v)){
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = node.v;
                    announce();
                    distTo[w] = distTo[node.v] + 1;
                    pq.insert(new SearchNode(node.distance + 1, w));
                }else {
                    int distance = distTo[w];
                    if(distance > node.distance + 1){
                        marked[w] = true;
                        edgeTo[w] = node.v;
                        announce();
                        distTo[w] = distTo[node.v] + 1;
                        pq.insert(new SearchNode(node.distance + 1, w));
                    }
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

    private class SearchNode implements Comparable<SearchNode>{

        int distance;

        int v;

        int estimateDistance;

        public SearchNode(int distance, int v) {
            this.distance = distance;
            this.v = v;
            this.estimateDistance = h(v);
        }

        @Override
        public int compareTo(SearchNode o) {
            if(distance + estimateDistance == o.distance + o.estimateDistance){
                return o.distance - distance;
            }
            return distance + estimateDistance - o.distance - o.estimateDistance;
        }
    }

}

