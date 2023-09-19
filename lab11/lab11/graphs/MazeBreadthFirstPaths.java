package lab11.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int s;
    private int t;

    private Queue<Integer> fringe = new LinkedList<>();

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(int v) {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        fringe.add(v);

        while (!fringe.isEmpty()){
            int x = fringe.remove();
            marked[x] = true;
            announce();
            if(x == t){
                break;
            }
            for(int w : maze.adj(x)){
                if(!marked[w]){
                    marked[w] = true;
                    edgeTo[w] = x;
                    announce();
                    distTo[w] = distTo[x] + 1;
                    fringe.add(w);
                }
            }
        }
    }


    @Override
    public void solve() {
        // bfs();
        bfs(s);
    }
}

