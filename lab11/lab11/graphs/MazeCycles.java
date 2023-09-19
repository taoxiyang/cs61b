package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;

    private boolean cycle = false;


    public MazeCycles(Maze m) {
        super(m);
        s = maze.xyTo1D(1, 1);
        distTo[s] = 0;
        edgeTo[s] = Integer.MAX_VALUE;
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        dfs(s);
    }

    private void dfs(int v){
        if(cycle){
            return;
        }
        marked[v] = true;
        announce();
        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                announce();
                distTo[w] = distTo[v] + 1;
                dfs(w);
                if (cycle) {
                    return;
                }
            }else if(edgeTo[v] != w) {

                int preNode = edgeTo[w];
                while (preNode != Integer.MAX_VALUE){
                    int pre = edgeTo[preNode];
                    edgeTo[preNode] = Integer.MAX_VALUE;
                    preNode = pre;
                }

                edgeTo[w] = v;
                announce();
                cycle = true;
                return;
            }
        }
    }



    // Helper methods go here
}

