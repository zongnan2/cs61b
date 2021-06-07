package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.CharArrayReader;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final long SEED = 19971215;
    private static final Random RANDOM = new Random(SEED);

    public static void addHexagon(int length, int startX, int startY, TETile choice, TETile[][] board) {
        int boundaryX = 3*(length-1)+startX;
        int boundaryY = 2*length-1+startY;


        for(int y=startY;y<=boundaryY;y++) {
            for(int x=startX;x<=boundaryX;x++) {
                int upperboundX;
                int lowerboundX;
                if(y-startY<length) {
                    upperboundX = upperCAL(length,y,startX,startY,true);
                    lowerboundX = lowerCAL(length,y,startX,startY,true);
                } else {
                    upperboundX = upperCAL(length,y,startX,startY,false);
                    lowerboundX = lowerCAL(length,y,startX,startY,false);
                }
                if(x<lowerboundX||x>upperboundX) {
                    continue;
                } else {
                    board[x][y] = choice;
                }
            }
        }

    }

    public static void tesslate(int length, TETile[][] board, int startX, int startY) {
        for(int i=0;i<9;i++) {
            if(i==0||i==8) {
                addHexagon(length,startX+2*(2*length-1),startY+length*i,randomTile(),board);
            } else if(i%2==1) {
                addHexagon(length,startX+1*(2*length-1),startY+length*i,randomTile(),board);
                addHexagon(length,startX+3*(2*length-1),startY+length*i,randomTile(),board);
            } else {
                addHexagon(length,startX+0*(2*length-1),startY+length*i,randomTile(),board);
                addHexagon(length,startX+2*(2*length-1),startY+length*i,randomTile(),board);
                addHexagon(length,startX+4*(2*length-1),startY+length*i,randomTile(),board);
            }
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(10);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.AVATAR;
            case 3: return Tileset.FLOOR;
            case 4: return Tileset.GRASS;
            case 5: return Tileset.WATER;
            case 6: return Tileset.SAND;
            case 7: return Tileset.MOUNTAIN;
            case 8: return Tileset.TREE;
            case 9: return Tileset.LOCKED_DOOR;
            default: return Tileset.NOTHING;
        }
    }

    private static int upperCAL(int length, int row, int startX, int startY, boolean type) {
        if(type) {
            return 2*length+row-startY-2+startX;
        } else {
            return 4*length-row+startY-3+startX;
        }
    }

    private static int lowerCAL(int length, int row, int startX, int startY, boolean type) {
        if(type) {
            return length-row+startY-1+startX;
        } else {
            return row-startY-length+startX;
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] hexBoard = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                hexBoard[x][y] = Tileset.NOTHING;
            }
        }
        tesslate(5,hexBoard,0,0);
        ter.renderFrame(hexBoard);
    }
}
