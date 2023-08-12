package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        addHexagon(world,5,8,4,Tileset.FLOWER);
        addHexagon(world,12,4,4,Tileset.WATER);
        addHexagon(world,5,0,4,Tileset.GRASS);
        ter.renderFrame(world);
    }

    public static void addHexagon(TETile[][] board, int startX, int startY, int size, TETile teTile){
        for(int y = 0; y < size; y++){
            for(int x = 0; x < size + 2 * y; x++){
                int posX = startX + x - y ;
                int posY = startY + y;
                if(posX >= 0 && posX < WIDTH && posY >= 0 && posY < HEIGHT){
                    board[posX][posY] = teTile;
                }
            }
            for(int x = 0; x < size + 2 * y; x++){
                int posX = startX + x - y ;
                int posY = startY + 2 * size - y - 1;
                if(posX >= 0 && posX < WIDTH && posY >= 0 && posY < HEIGHT){
                    board[posX][posY] = teTile;
                }
            }
        }
    }


}
