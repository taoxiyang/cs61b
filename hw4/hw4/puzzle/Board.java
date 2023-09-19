package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;

import java.util.Iterator;
import java.util.Objects;

import static hw4.puzzle.TestSolver.readBoard;

public class Board implements WorldState{

    private final static int BLANK = 0;

    private final long signature;

    /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public boolean equals(Object y){
        if(y == this){
            return true;
        }
        if(y instanceof Board)
            return false;

//        for(int i = 0; i < size(); i++){
//            for(int j = 0; j < size(); j++){
//                if(tileAt(i,j) != ((Board) y).tileAt(i,j))
//                    return false;
//            }
//        }
        return signature == ((Board) y).signature;
    }

    @Override
    public int hashCode() {
        return Objects.hash(signature);
    }

    private int[][] tiles;


    public Board(int[][] tiles){
        int N = tiles.length;
        this.tiles = new int[N][N];
        long signature = 0;
        int power = 0;
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int v = tiles[i][j];
                this.tiles[i][j] = v;
                signature += v * power(N, power);
                power++;
            }
        }
        this.signature = signature;
    }

    private long power(int N, int power){
        long r = 1;
        while (power > 0){
            r = r * N * N;
            power--;
        }
        return r;
    }

    public int tileAt(int i, int j){
        if(!inBound(i) || !inBound(j)){
            throw new IndexOutOfBoundsException();
        }
        return tiles[i][j];
    }

    private int goalAt(int i, int j){
        if(!inBound(i) || !inBound(j)){
            throw new IndexOutOfBoundsException();
        }
        int N = size();
        if(i == N - 1 && j == N - 1){
            return BLANK;
        }
        return i * N + j + 1;
    }

    private int goalAtRow(int x){
        return (x - 1) / size();
    }

    private int goalAtCol(int x){
        return (x - 1) % size();
    }

    private boolean inBound(int i){
        if(i < 0 || i >= size()){
            return false;
        }
        return true;
    }

    public int size(){
        return tiles.length;
    }

    private int estimatedDistanceToGoal = Integer.MAX_VALUE;

    @Override
    public int estimatedDistanceToGoal() {
        if(estimatedDistanceToGoal == Integer.MAX_VALUE){
            estimatedDistanceToGoal = manhattan();
        }
        return estimatedDistanceToGoal;
    }

    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

//    @Override
//    public Iterable<WorldState> neighbors() {
//        Queue<WorldState> neighbors = new Queue<>();
//        move(neighbors,DIR.left);
//        move(neighbors,DIR.right);
//        move(neighbors,DIR.up);
//        move(neighbors,DIR.down);
//        return neighbors;
//    }

    private void move(Queue<WorldState> neighbors,DIR dir){
        Pos blank = new Pos();
        int[][] b = copy(blank);
        Pos movPos = null;
        if(dir == DIR.left){
            movPos = new Pos(blank.x, blank.y - 1);
        }else if(dir == DIR.right){
            movPos = new Pos(blank.x, blank.y + 1);
        }else if(dir == DIR.up){
            movPos = new Pos(blank.x - 1, blank.y);
        }else{
            movPos = new Pos(blank.x + 1, blank.y);
        }
        if(inBound(movPos.x) && inBound(movPos.y)){
            swap(b,blank,movPos);
            Board board = new Board(b);
            neighbors.enqueue(board);
        }
    }


    private int[][] copy(Pos blank){
        int N = size();
        int[][] b = new int[N][N];
        for(int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                int x = tileAt(i,j);
                b[i][j] = x;
                if(x == BLANK){
                    blank.x = i;
                    blank.y = j;
                }
            }
        }
        return b;
    }

    public int hamming(){
        int wrongSize = 0;
        for(int i = 0; i < size(); i++){
            for(int j = 0; j < size(); j++){
                int x = tileAt(i,j);
                if(x == BLANK)
                    continue;
                if(x != goalAt(i,j))
                    wrongSize++;
            }
        }
        return wrongSize;
    }
    public int manhattan(){
        int wrongSize = 0;
        for(int i = 0; i < size(); i++){
            for(int j = 0; j < size(); j++){
                int x = tileAt(i,j);
                if(x == BLANK)
                    continue;
                if(x != goalAt(i,j)){
                    wrongSize += Math.abs(i - goalAtRow(x)) + Math.abs(j - goalAtCol(x));
                }
            }
        }
        return wrongSize;
    }

    private void swap(int[][] board, Pos a, Pos b){
        int tmp = board[a.x][a.y];
        board[a.x][a.y] = board[b.x][b.y];
        board[b.x][b.y] = tmp;
    }

    private static class Pos{
        int x;
        int y;

        public Pos() {
        }

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private enum DIR{
        left, right, up, down
    }

    public static void main(String[] args) {

//        int[][] b = {{8,1,3},{4,0,2},{7,6,5}};
//        Board board = new Board(b);
//        System.out.println(board.estimatedDistanceToGoal());
        Board b = readBoard("input/puzzle50.txt");
//        System.out.println(b);

        System.out.println(b.goalAtCol(9));
        System.out.println(b.goalAtRow(9));
//
        System.out.println("estimatedDistanceToGoal is " + b.estimatedDistanceToGoal());
//
//        System.out.println("---------->");
//        Iterable<WorldState> ita = b.neighbors();
//        Iterator<WorldState> it = ita.iterator();
//        while (it.hasNext()){
//            System.out.println(it.next());
//        }
    }
}
