package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

/**
 * @author qiushui
 * @Date 2023/9/18
 */
public class Solver {

    private SearchNode stateSeq;

    private MinPQ<SearchNode> minPQ = new MinPQ<>();

    public Solver(WorldState initial){
        minPQ.insert(new SearchNode(null,0, initial));
        stateSeq = minPQ.delMin();
        while (!stateSeq.item.isGoal()){
            int size = stateSeq.size + 1;
            for(WorldState neghbor : stateSeq.item.neighbors()){
                if(stateSeq.parent != null && stateSeq.parent.item.equals(neghbor)){
                }else {
                    minPQ.insert(new SearchNode(stateSeq, size, neghbor));
                }
            }
//            System.out.println("minPQ size " + minPQ.size() );
            if(minPQ.isEmpty()){
                break;
            }
            stateSeq = minPQ.delMin();
        }
    }
    public int moves(){
        return stateSeq.size();
    }

    public Iterable<WorldState> solution(){
        List<WorldState> list = new ArrayList<>();
        SearchNode p = stateSeq;
        while (p != null){
            list.add(p.item);
            p = p.parent;
        }
        Collections.reverse(list);
        return list;
    }

    private class SearchNode implements Comparable<SearchNode>{
        SearchNode parent;

        int size;

        WorldState item;

        private int distance;


        public SearchNode(SearchNode parent,int size, WorldState item) {
            this.parent = parent;
            this.size = size;
            this.item = item;
            this.distance = size + item.estimatedDistanceToGoal();
        }

        public int size(){
            return size;
        }

        @Override
        public int compareTo(SearchNode o) {
            return distance - o.distance;
        }

    }
}
