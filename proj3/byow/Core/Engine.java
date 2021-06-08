package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    private static final int TILE_SIZE = 16;
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final int menuWidth = 30;
    public static final int menuHeight = 30;
    private long SEED;
    private Random RANDOM;
    private boolean gameOver;
    private boolean menuPage;
    private Avatar player;
    private Avatar player2;
    private int count;
    private List<Point> damageArea;
    private String imageToDraw = "./byow/Core/pubg.jpg";
    private String selectToDraw = "./byow/Core/select.png";
    private String seedToDraw = "./byow/Core/seed.jpg";
    private String endToDraw = "./byow/Core/end.jpg";


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        drawMain();
        String typedString = "";
        gameOver = false;
        menuPage = true;
        while(!gameOver) {
            if(menuPage) {
                if(StdDraw.hasNextKeyTyped()) {
                    char newTyped = StdDraw.nextKeyTyped();
                    typedString = typedString + newTyped;
                    switch (newTyped) {
                        case 'n':
                        case 'N':
                            drawPlayerSelection("","");
                            typedString = typedString + solicitPlayer();
                            drawSeed("");
                            typedString = typedString + solicitSeed();
                            menuPage = false;
                            ter.initialize(WIDTH,HEIGHT+3);
                            break;
                        case 'l':
                        case 'L':
                            drawLoad();
                            StdDraw.pause(2000);
                            typedString = readData();
                            menuPage = false;
                            ter.initialize(WIDTH,HEIGHT+3);
                            break;
                        case 'q':
                        case 'Q':
                            System.exit(0);
                        default:
                            break;
                    }
                }
            } else {
                TETile[][] frame = interactWithInputString(typedString);
                ter.renderFrame(frame);
                while(!StdDraw.hasNextKeyTyped()) {
                    drawHUD(frame);
                }
                char newTyped = StdDraw.nextKeyTyped();

                if(newTyped == ':') {
                    while(!isPressd('Q')){
                        drawInstruction("Press (Q) to save and quit");
                    }
                    saveFile(typedString);
                    System.exit(0);
                }
                typedString = typedString + newTyped;
                if(player.getLives()<1||player2.getLives()<1) {
                    gameOver = true;
                    drawEnd();
                }
            }
        }
        StdDraw.pause(2000);
    }

    private void drawEnd() {
        StdDraw.setCanvasSize(menuWidth * TILE_SIZE, menuHeight * TILE_SIZE);
        StdDraw.setXscale(0, menuHeight);
        StdDraw.setYscale(0, menuHeight);
        StdDraw.clear(Color.darkGray);
        StdDraw.picture(menuWidth/2,menuHeight/2,endToDraw,menuWidth,menuHeight);
        StdDraw.setPenColor(Color.WHITE);
        Font smallFont = new Font("Arial", Font.BOLD, 15);
        StdDraw.setFont(smallFont);
        StdDraw.text(menuWidth/2, menuHeight - 1, "Game finished");
        StdDraw.line(0, menuHeight - 2, menuWidth, menuHeight - 2);

        StdDraw.setPenColor(Color.orange);
        StdDraw.filledRectangle(menuWidth/2,menuHeight/2,6,1);

        String winner = (player.getLives()>0) ? "Player 1":"Player 2";
        Font font = new Font("HeadLineA", Font.PLAIN, 20);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.darkGray);
        StdDraw.text(menuWidth/2,menuHeight/2,winner+" wins the game");
        StdDraw.show();
    }

    private String solicitPlayer() {
        String typedPlayer = "";
        while(typedPlayer.length()<2) {
            if(StdDraw.hasNextKeyTyped()) {
                char nextKey = StdDraw.nextKeyTyped();
                if(nextKey=='B' || nextKey=='b' || nextKey=='G'|| nextKey=='g') {
                    typedPlayer = typedPlayer + nextKey;
                    if(typedPlayer.length() == 1) {
                        drawPlayerSelection(typedPlayer,"");
                    } else {
                        String[] players = typedPlayer.split("");
                        drawPlayerSelection(players[0],players[1]);
                    }
                } else {
                    continue;
                }
            }
        }
        StdDraw.pause(500);
        return typedPlayer;
    }

    private void drawPlayerSelection(String player1, String player2) {
        StdDraw.clear(Color.darkGray);
        StdDraw.picture(menuWidth/2,menuHeight/2,selectToDraw,menuWidth,menuHeight);
        StdDraw.setPenColor(Color.white);
        Font smallFont = new Font("Arial", Font.BOLD, 15);
        StdDraw.setFont(smallFont);
        StdDraw.text(menuWidth/2, menuHeight - 1, "Player one: Boy(B) or Girl(G)");
        StdDraw.line(0, menuHeight - 2, menuWidth, menuHeight - 2);

        StdDraw.setPenColor(Color.orange);
        StdDraw.filledRectangle(menuWidth/2,menuHeight*3/4,5,1);

        Font font = new Font("HeadLineA", Font.PLAIN, 20);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.darkGray);
        if(player1.equals("B")||player1.equals("b")) {
            StdDraw.text(menuWidth/2,menuHeight*3/4,"♂");
        } else if(player1.equals("G")||player1.equals("g")) {
            StdDraw.text(menuWidth/2,menuHeight*3/4,"♀");
        } else {
            StdDraw.text(menuWidth/2,menuHeight*3/4,player1);
        }

        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(smallFont);
        StdDraw.text(menuWidth/2, menuHeight/2 - 1, "Player two: Boy(B) or Girl(G)");
        StdDraw.line(0, menuHeight/2 - 2, menuWidth, menuHeight/2 - 2);

        StdDraw.setPenColor(Color.orange);
        StdDraw.filledRectangle(menuWidth/2,menuHeight*1/4,5,1);

        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.darkGray);
        if(player2.equals("B")||player2.equals("b")) {
            StdDraw.text(menuWidth/2,menuHeight*1/4,"♂");
        } else if(player2.equals("G")||player2.equals("g")) {
            StdDraw.text(menuWidth/2,menuHeight*1/4,"♀");
        } else {
            StdDraw.text(menuWidth/2,menuHeight*1/4,player2);
        }

        StdDraw.show();
    }

    private String readData() {
        Path fileName = Paths.get("Data.txt");
        String data = "";
        try {
            data = Files.readString(fileName);
        } catch (IOException ex) {
            System.out.print("Error");
        }
        return data;
    }

    private void drawLoad() {
        StdDraw.clear(Color.darkGray);
        StdDraw.picture(menuWidth/2,menuHeight/2,seedToDraw,menuWidth,menuHeight);
        StdDraw.setPenColor(Color.orange);
        Font smallFont = new Font("Arial", Font.BOLD, 15);
        StdDraw.setFont(smallFont);
        StdDraw.text(menuWidth/2, menuHeight - 1, "Loading...");
        StdDraw.line(0, menuHeight - 2, menuWidth, menuHeight - 2);
        StdDraw.show();
    }


    private void drawInstruction(String instruction) {
        StdDraw.setPenColor(Color.white);
        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(smallFont);
        StdDraw.text(WIDTH/2,HEIGHT+1,instruction);
        StdDraw.show();
    }

    private void saveFile(String typedString) {
        Path path = Paths.get("data.txt");
        String contents = typedString;
        try {
            Files.writeString(path,contents);
        } catch (IOException ex) {
            System.out.print("Error");
        }
    }

    private boolean isPressd(char key) {
        if(StdDraw.hasNextKeyTyped()) {
            char nextKey = StdDraw.nextKeyTyped();
            return (nextKey == key) || (nextKey == Character.toLowerCase(key));
        }
        return false;
    }

    private void drawHUD(TETile[][] frame) {
        StdDraw.setPenColor(Color.darkGray);
        StdDraw.filledRectangle(WIDTH/2,HEIGHT+1.5,WIDTH/2,1.5);

        String description = "";
        char characterP1 = player.getTile().character();
        String symbolP1 = String.valueOf(characterP1);
        String livesP1 = symbolP1.repeat(player.getLives());
        char characterP2 = player2.getTile().character();
        String symbolP2 = String.valueOf(characterP2);
        String livesP2 = symbolP2.repeat(player2.getLives());
        if((int)StdDraw.mouseX()>=0&&(int)StdDraw.mouseX()<WIDTH&&(int)StdDraw.mouseY()>=0&&(int)StdDraw.mouseY()<HEIGHT) {
            if(!isSightRegion((int)StdDraw.mouseX(),(int)StdDraw.mouseY())) {
                description = "No Signal";
            } else if(isVirusRegion((int)StdDraw.mouseX(),(int)StdDraw.mouseY())) {
                description = "Coronavirus";
            } else {
                description = frame[(int)StdDraw.mouseX()][(int)StdDraw.mouseY()].description();
            }
        }
        char characterRock = Tileset.BABYROCK.character();
        String symbolRock = String.valueOf(characterRock);
        String rocksP1 = symbolRock.repeat(player.getRocks());
        String rocksP2 = symbolRock.repeat(player2.getRocks());

        char characterLaser = Tileset.LASER.character();
        String symbolLaser = String.valueOf(characterLaser);
        char characterGrenade = Tileset.GRENADE.character();
        String symbolGrenade = String.valueOf(characterGrenade);
        String weaponsP1 = "";
        for(String weapon: player.getWeapon()) {
            if(weapon.equals("laser")) {
                weaponsP1 = weaponsP1 + symbolLaser;
            } else {
                weaponsP1 = weaponsP1 + symbolGrenade;
            }
        }
        String weaponsP2 = "";
        for(String weapon: player2.getWeapon()) {
            if(weapon.equals("laser")) {
                weaponsP2 = weaponsP2 + symbolLaser;
            } else {
                weaponsP2 = weaponsP2 + symbolGrenade;
            }
        }

        StdDraw.setPenColor(Color.white);
        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(smallFont);
        StdDraw.textLeft(1, HEIGHT + 2, "Player 1:"+livesP1+" "+rocksP1+" "+weaponsP1);
        StdDraw.textLeft(1, HEIGHT + 1, "Player 2:"+livesP2+" "+rocksP2+" "+weaponsP2);
        StdDraw.textRight(WIDTH-1,HEIGHT+1,description);
        StdDraw.line(0, HEIGHT, WIDTH, HEIGHT);
        StdDraw.show();
    }

    private String solicitSeed() {
        String typedSeed = "";
        while(!typedSeed.endsWith("S")&&!typedSeed.endsWith("s")) {
            if(StdDraw.hasNextKeyTyped()) {
                char typedKey = StdDraw.nextKeyTyped();
                if(Character.isDigit(typedKey) || typedKey=='S' || typedKey=='s') {
                    typedSeed = typedSeed + typedKey;
                    drawSeed(typedSeed);
                } else {
                    continue;
                }
            }
        }
        return typedSeed;
    }

    private void drawSeed(String seed) {
        StdDraw.clear(Color.darkGray);
        StdDraw.picture(menuWidth/2,menuHeight/2,seedToDraw,menuWidth,menuHeight);
        StdDraw.setPenColor(Color.white);
        Font smallFont = new Font("Arial", Font.BOLD, 15);
        StdDraw.setFont(smallFont);
        StdDraw.text(menuWidth/2, menuHeight - 1, "Enter a number finished with (S)");
        StdDraw.line(0, menuHeight - 2, menuWidth, menuHeight - 2);

        StdDraw.setPenColor(Color.orange);
        StdDraw.filledRectangle(menuWidth/2,menuHeight/2,5,1);

        Font font = new Font("HeadLineA", Font.PLAIN, 20);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.darkGray);
        StdDraw.text(menuWidth/2,menuHeight/2,seed);
        StdDraw.show();
    }

    private void drawMain() {
        ter.initialize(menuWidth,menuHeight);
        StdDraw.clear(Color.darkGray);
        StdDraw.picture(menuWidth/2,menuHeight/2,imageToDraw,menuWidth,menuHeight);
        StdDraw.setPenColor(Color.ORANGE);
        StdDraw.filledRectangle(menuWidth/2,menuHeight*7/10,4.5,0.6);

        Font title1 = new Font("HeadLineA", Font.PLAIN, 20);
        Font title2 = new Font("HeadLineA", Font.PLAIN, 40);
        Font title3 = new Font("Chalkduster",Font.PLAIN,20);
        Font menu = new Font("Arial", Font.BOLD, 15);
        StdDraw.setFont(title1);
        StdDraw.setPenColor(Color.darkGray);
        StdDraw.text(menuWidth/2,menuHeight*7/10, "PLAYERUNKNOWN'S");
        StdDraw.setFont(title2);
        StdDraw.setPenColor(Color.orange);
        StdDraw.text(menuWidth/2,menuHeight*6.35/10, "BATTLEGROUNDS");

        StdDraw.setPenColor(new Color(200,37,37));
        StdDraw.filledRectangle(menuWidth/2,menuHeight*5.9/10,3.5,0.6);

        StdDraw.setFont(title3);
        StdDraw.setPenColor(Color.darkGray);
        StdDraw.text(menuWidth/2,menuHeight*5.9/10, "COVID-19");

        StdDraw.setFont(menu);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(menuWidth/2,menuHeight*3/10,"NEW GAME (N)");
        StdDraw.text(menuWidth/2,menuHeight*2.5/10,"LOAD GAME (L)");
        StdDraw.text(menuWidth/2,menuHeight*2/10,"QUIT (Q)");
        StdDraw.show();
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        String seedString = "";
        String action = "";
        String lowInput = input.toLowerCase();
        char playerGender = lowInput.charAt(1);
        char player2Gender = lowInput.charAt(2);
        for(int i=3;i<lowInput.length();i++) {
            if(lowInput.charAt(i) == 's') {
                action = lowInput.substring(i+1);
                break;
            }
            seedString = seedString + lowInput.charAt(i);
        }
        SEED = Integer.parseInt(seedString);
        RANDOM = new Random(SEED);
        while(!randomWorldGenerate(finalWorldFrame)) {
            //System.out.println("Not Fully Connected, Generate Again");
        }
        renderWall(finalWorldFrame);
        renderHeart(finalWorldFrame);
        renderRock(finalWorldFrame);
        renderSun(finalWorldFrame);
        renderLaser(finalWorldFrame);
        renderGrenade(finalWorldFrame);
        Point playerPos = landObject(finalWorldFrame);
        player = new Avatar(playerPos.getX(),playerPos.getY());
        player.setTile(playerGender,"player1");
        renderAvatar(finalWorldFrame,player);
        Point player2Pos = landObject(finalWorldFrame);
        player2 = new Avatar(player2Pos.getX(),player2Pos.getY());
        player2.setTile(player2Gender,"player2");
        renderAvatar(finalWorldFrame,player2);
        count = 0;
        damageArea = new ArrayList<>();
        for(int i=0;i<action.length();i++) {
            count++;
            damageArea.clear();
            renderAction(finalWorldFrame,action.charAt(i));
            playerStatusUpdate();
        }
        renderVirus(finalWorldFrame);
        if(!damageArea.isEmpty()) {
            renderDamage(finalWorldFrame);
        }
        renderMask(finalWorldFrame);
        return finalWorldFrame;
    }

    private boolean randomWorldGenerate(TETile[][] board) {
        initializeBoard(board);
        int roomNum = RandomUtils.uniform(RANDOM,12,18);
        List<Room> rooms = new ArrayList<>();

        for(int i=0;i<=roomNum;i++) {
            Map<String, Integer> curRoomInfo = randomRoomGenerate(board);
            Room curRoom = new Room(curRoomInfo.get("X"),curRoomInfo.get("Y"),curRoomInfo.get("Width"),curRoomInfo.get("Height"));
            rooms.add(curRoom);
        }

        randomHallwayGenerate(rooms,board);
        /**
        try {
            randomHallwayGenerate(rooms,board);
        }
        catch (Error e) {
            randomWorldGenerate(board);
        }
        */

        if(!fullyConnectCheck(rooms)) {
            return false;
        }
        return true;
    }

    private void renderDamage(TETile[][] board) {
        for(Point damagePoint: damageArea) {
            board[damagePoint.getX()][damagePoint.getY()] = TETile.backOrange(board[damagePoint.getX()][damagePoint.getY()]);
        }
    }

    private void renderMask(TETile[][] board) {
        for(int i=0;i<WIDTH;i++) {
            for(int j=0;j<HEIGHT;j++) {
                if(!isSightRegion(i,j)) {
                    board[i][j] = Tileset.MASK;
                }
            }
        }
    }

    private boolean isSightRegion(int x, int y) {
        /**
        if(x>=(player.getX()-player.getSightLevel())
                &&x<=(player.getX()+player.getSightLevel())
                &&y>=(player.getY()-player.getSightLevel())
                &&y<=(player.getY()+player.getSightLevel())) {
            return true;
        }
        if(x>=(player2.getX()-player2.getSightLevel())
                &&x<=(player2.getX()+player2.getSightLevel())
                &&y>=(player2.getY()-player2.getSightLevel())
                &&y<=(player2.getY()+player2.getSightLevel())) {
            return true;
        }
        return false;
         */
        if(distanceCal(x,y,player)<player.getSightLevel()) {
            return true;
        }
        if(distanceCal(x,y,player2)<player2.getSightLevel()) {
            return true;
        }
        return false;
    }

    private double distanceCal(int x, int y, Avatar p) {
        return Math.sqrt(Math.pow(p.getX()-x,2)+Math.pow(p.getY()-y,2));
    }

    private void renderSun(TETile[][] board) {
        int sunNum = RandomUtils.uniform(RANDOM,8,12);
        while(sunNum>0) {
            Point posSun = randomLanding(board);
            board[posSun.getX()][posSun.getY()] = Tileset.SUN;
            sunNum--;
        }
    }

    private void renderRock(TETile[][] board) {
        int rockNum = RandomUtils.uniform(RANDOM,2,5);
        while(rockNum>0) {
            Point posRock = randomLanding(board);
            board[posRock.getX()][posRock.getY()] = Tileset.BABYROCK;
            rockNum--;
        }
    }

    private void renderLaser(TETile[][] board) {
        int laserNum = RandomUtils.uniform(RANDOM,2,5);
        while(laserNum>0) {
            Point posLaser = randomLanding(board);
            board[posLaser.getX()][posLaser.getY()] = Tileset.LASER;
            laserNum--;
        }
    }

    private void renderGrenade(TETile[][] board) {
        int grenadeNum = RandomUtils.uniform(RANDOM,2,5);
        while(grenadeNum>0) {
            Point posGrenade = randomLanding(board);
            board[posGrenade.getX()][posGrenade.getY()] = Tileset.GRENADE;
            grenadeNum--;
        }
    }

    private void renderHeart(TETile[][] board) {
        int heartNum = RandomUtils.uniform(RANDOM,2,5);
        while(heartNum>0) {
            Point posHeart = randomLanding(board);
            board[posHeart.getX()][posHeart.getY()] = Tileset.HEART;
            heartNum--;
        }
    }

    private void playerStatusUpdate() {
        if(isVirusRegion(player.getX(),player.getY())) {
            player.getInfect();
        }
        if(isVirusRegion(player2.getX(),player2.getY())) {
            player2.getInfect();
        }
    }

    private void renderVirus(TETile[][] board) {
        for(int i=0;i<WIDTH;i++) {
            for(int j=0;j<HEIGHT;j++) {
                if(isVirusRegion(i,j)) {
                    board[i][j] = TETile.textRed(board[i][j]);
                }
            }
        }
    }

    private boolean isVirusRegion(int x, int y) {
        int levelW = count/18;
        int levelH = count/48;
        if(x<levelW||x>WIDTH-1-levelW||y<levelH||y>HEIGHT-1-levelH) {
            return true;
        }
        return false;
    }

    private void renderAction(TETile[][] board, char action) {

        switch (action) {
            case 'a':
                if(checkAction(board, player.getX()-1, player.getY(), player)) {
                    board[player.getX()][player.getY()] = Tileset.GRASS;
                    board[player.getX()-1][player.getY()] = player.getTile();
                    player.setNext(player.getX()-2,player.getY());
                    player.setX(player.getX()-1);
                } else {
                    player.setNext(player.getX()-1,player.getY());
                }
                return;
            case 'w':
                if(checkAction(board, player.getX(), player.getY()+1, player)) {
                    board[player.getX()][player.getY()] = Tileset.GRASS;
                    board[player.getX()][player.getY()+1] = player.getTile();
                    player.setNext(player.getX(),player.getY()+2);
                    player.setY(player.getY()+1);
                } else {
                    player.setNext(player.getX(),player.getY()+1);
                }
                return;
            case 's':
                if(checkAction(board, player.getX(), player.getY()-1, player)) {
                    board[player.getX()][player.getY()] = Tileset.GRASS;
                    board[player.getX()][player.getY()-1] = player.getTile();
                    player.setNext(player.getX(),player.getY()-2);
                    player.setY(player.getY()-1);
                } else {
                    player.setNext(player.getX(),player.getY()-1);
                }
                return;
            case 'd':
                if(checkAction(board, player.getX()+1, player.getY(), player)) {
                    board[player.getX()][player.getY()] = Tileset.GRASS;
                    board[player.getX()+1][player.getY()] = player.getTile();
                    player.setNext(player.getX()+2,player.getY());
                    player.setX(player.getX()+1);
                } else {
                    player.setNext(player.getX()+1,player.getY());
                }
                return;
            case 'e':
                if(player.getRocks()>0&&board[player.getNext().getX()][player.getNext().getY()].equals(Tileset.GRASS)) {
                    board[player.getNext().getX()][player.getNext().getY()] = Tileset.ROCK;
                    player.downRocks();
                }
                return;
            case 'q':
                String nextWeaponP1 = player.downWeapon();
                if(nextWeaponP1.equals("empty")) {
                    return;
                }
                if(player.getX()==player.getNext().getX()+1) {
                    damageArea = damageAreaCal(board,player,1,nextWeaponP1);
                } else if(player.getY()==player.getNext().getY()-1) {
                    damageArea = damageAreaCal(board,player,2,nextWeaponP1);
                } else if(player.getY()==player.getNext().getY()+1) {
                    damageArea = damageAreaCal(board,player,3,nextWeaponP1);
                } else {
                    damageArea = damageAreaCal(board,player,4,nextWeaponP1);
                }
                for(Point damagePoint:damageArea) {
                    if(damagePoint.getX()==player2.getX()&&damagePoint.getY()==player2.getY()) {
                        player2.downLives();
                    }
                }
                return;
            case 'j':
                if(checkAction(board, player2.getX()-1, player2.getY(), player2)) {
                    board[player2.getX()][player2.getY()] = Tileset.GRASS;
                    board[player2.getX()-1][player2.getY()] = player2.getTile();
                    player2.setNext(player2.getX()-2,player2.getY());
                    player2.setX(player2.getX()-1);
                } else {
                    player2.setNext(player2.getX()-1,player2.getY());
                }
                return;
            case 'i':
                if(checkAction(board, player2.getX(), player2.getY()+1, player2)) {
                    board[player2.getX()][player2.getY()] = Tileset.GRASS;
                    board[player2.getX()][player2.getY()+1] = player2.getTile();
                    player2.setNext(player2.getX(),player2.getY()+2);
                    player2.setY(player2.getY()+1);
                } else {
                    player2.setNext(player2.getX(),player2.getY()+1);
                }
                return;
            case 'k':
                if(checkAction(board, player2.getX(), player2.getY()-1, player2)) {
                    board[player2.getX()][player2.getY()] = Tileset.GRASS;
                    board[player2.getX()][player2.getY()-1] = player2.getTile();
                    player2.setNext(player2.getX(),player2.getY()-2);
                    player2.setY(player2.getY()-1);
                } else {
                    player2.setNext(player2.getX(),player2.getY()-1);
                }
                return;
            case 'l':
                if(checkAction(board, player2.getX()+1, player2.getY(), player2)) {
                    board[player2.getX()][player2.getY()] = Tileset.GRASS;
                    board[player2.getX()+1][player2.getY()] = player2.getTile();
                    player2.setNext(player2.getX()+2,player2.getY());
                    player2.setX(player2.getX()+1);
                } else {
                    player2.setNext(player2.getX()+1,player2.getY());
                }
                return;
            case 'o':
                if(player2.getRocks()>0&&board[player2.getNext().getX()][player2.getNext().getY()].equals(Tileset.GRASS)) {
                    board[player2.getNext().getX()][player2.getNext().getY()] = Tileset.ROCK;
                    player2.downRocks();
                }
                return;
            case 'u':
                String nextWeaponP2 = player2.downWeapon();
                if(nextWeaponP2.equals("empty")) {
                    return;
                }
                if(player2.getX()==player2.getNext().getX()+1) {
                    damageArea = damageAreaCal(board,player2,1,nextWeaponP2);
                } else if(player2.getY()==player2.getNext().getY()-1) {
                    damageArea = damageAreaCal(board,player2,2,nextWeaponP2);
                } else if(player2.getY()==player2.getNext().getY()+1) {
                    damageArea = damageAreaCal(board,player2,3,nextWeaponP2);
                } else {
                    damageArea = damageAreaCal(board,player2,4,nextWeaponP2);
                }
                for(Point damagePoint:damageArea) {
                    if(damagePoint.getX()==player.getX()&&damagePoint.getY()==player.getY()) {
                        player.downLives();
                    }
                }
                return;
            default:
                return;
        }
    }

    private List<Point> damageAreaCal(TETile[][] board, Avatar p, int dir, String weaponName) {
        List<Point> damageArea = new ArrayList<>();
        if(weaponName.equals("laser")) {
            //Laser
            Point curLoc = new Point(p.getX(),p.getY());
            //1A 2W 3S 4D
            switch (dir) {
                case 1:
                    while(!board[nextLaserDam(curLoc,1).getX()][nextLaserDam(curLoc,1).getY()].equals(Tileset.MOUNTAIN)) {
                        if(board[nextLaserDam(curLoc,1).getX()][nextLaserDam(curLoc,1).getY()].equals(Tileset.ROCK)) {
                            board[nextLaserDam(curLoc,1).getX()][nextLaserDam(curLoc,1).getY()] = Tileset.GRASS;
                            damageArea.add(nextLaserDam(curLoc,1));
                            break;
                        }
                        curLoc = nextLaserDam(curLoc,1);
                        damageArea.add(curLoc);
                    }
                    break;
                case 2:
                    while(!board[nextLaserDam(curLoc,2).getX()][nextLaserDam(curLoc,2).getY()].equals(Tileset.MOUNTAIN)) {
                        if(board[nextLaserDam(curLoc,2).getX()][nextLaserDam(curLoc,2).getY()].equals(Tileset.ROCK)) {
                            board[nextLaserDam(curLoc,2).getX()][nextLaserDam(curLoc,2).getY()] = Tileset.GRASS;
                            damageArea.add(nextLaserDam(curLoc,2));
                            break;
                        }
                        curLoc = nextLaserDam(curLoc,2);
                        damageArea.add(curLoc);
                    }
                    break;
                case 3:
                    while(!board[nextLaserDam(curLoc,3).getX()][nextLaserDam(curLoc,3).getY()].equals(Tileset.MOUNTAIN)) {
                        if(board[nextLaserDam(curLoc,3).getX()][nextLaserDam(curLoc,3).getY()].equals(Tileset.ROCK)) {
                            board[nextLaserDam(curLoc,3).getX()][nextLaserDam(curLoc,3).getY()] = Tileset.GRASS;
                            damageArea.add(nextLaserDam(curLoc,3));
                            break;
                        }
                        curLoc = nextLaserDam(curLoc,3);
                        damageArea.add(curLoc);
                    }
                    break;
                case 4:
                    while(!board[nextLaserDam(curLoc,4).getX()][nextLaserDam(curLoc,4).getY()].equals(Tileset.MOUNTAIN)) {
                        if(board[nextLaserDam(curLoc,4).getX()][nextLaserDam(curLoc,4).getY()].equals(Tileset.ROCK)) {
                            board[nextLaserDam(curLoc,4).getX()][nextLaserDam(curLoc,4).getY()] = Tileset.GRASS;
                            damageArea.add(nextLaserDam(curLoc,4));
                            break;
                        }
                        curLoc = nextLaserDam(curLoc,4);
                        damageArea.add(curLoc);
                    }
                    break;
                default:
                    break;
            }
        } else {
            //grenade
            List<Point> allTargets;
            switch (dir) {
                case 1:
                    allTargets = grenadeTarget(p.getX(),p.getY(),1);
                    for(Point damageZone: allTargets) {
                        if(grenadeAva(board,damageZone.getX(),damageZone.getY())) {
                            damageArea.add(damageZone);
                            if(board[damageZone.getX()][damageZone.getY()].equals(Tileset.ROCK)) {
                                board[damageZone.getX()][damageZone.getY()] = Tileset.GRASS;
                            }
                        }
                    }
                    break;
                case 2:
                    allTargets = grenadeTarget(p.getX(),p.getY(),2);
                    for(Point damageZone: allTargets) {
                        if(grenadeAva(board,damageZone.getX(),damageZone.getY())) {
                            damageArea.add(damageZone);
                            if(board[damageZone.getX()][damageZone.getY()].equals(Tileset.ROCK)) {
                                board[damageZone.getX()][damageZone.getY()] = Tileset.GRASS;
                            }
                        }
                    }
                    break;
                case 3:
                    allTargets = grenadeTarget(p.getX(),p.getY(),3);
                    for(Point damageZone: allTargets) {
                        if(grenadeAva(board,damageZone.getX(),damageZone.getY())) {
                            damageArea.add(damageZone);
                            if(board[damageZone.getX()][damageZone.getY()].equals(Tileset.ROCK)) {
                                board[damageZone.getX()][damageZone.getY()] = Tileset.GRASS;
                            }
                        }
                    }
                    break;
                case 4:
                    allTargets = grenadeTarget(p.getX(),p.getY(),4);
                    for(Point damageZone: allTargets) {
                        if(grenadeAva(board,damageZone.getX(),damageZone.getY())) {
                            damageArea.add(damageZone);
                            if(board[damageZone.getX()][damageZone.getY()].equals(Tileset.ROCK)) {
                                board[damageZone.getX()][damageZone.getY()] = Tileset.GRASS;
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return damageArea;
    }

    private List<Point> grenadeTarget(int curX, int curY, int dir) {
        List<Point> targetPoints = new ArrayList<>();
        switch (dir) {
            case 1:
                targetPoints.add(new Point(curX-5,curY-1));
                targetPoints.add(new Point(curX-5,curY));
                targetPoints.add(new Point(curX-5,curY+1));
                targetPoints.add(new Point(curX-4,curY-1));
                targetPoints.add(new Point(curX-4,curY));
                targetPoints.add(new Point(curX-4,curY+1));
                targetPoints.add(new Point(curX-3,curY-1));
                targetPoints.add(new Point(curX-3,curY));
                targetPoints.add(new Point(curX-3,curY+1));
                return targetPoints;
            case 2:
                targetPoints.add(new Point(curX-1,curY+5));
                targetPoints.add(new Point(curX,curY+5));
                targetPoints.add(new Point(curX+1,curY+5));
                targetPoints.add(new Point(curX-1,curY+4));
                targetPoints.add(new Point(curX,curY+4));
                targetPoints.add(new Point(curX+1,curY+4));
                targetPoints.add(new Point(curX-1,curY+3));
                targetPoints.add(new Point(curX,curY+3));
                targetPoints.add(new Point(curX+1,curY+3));
                return targetPoints;
            case 3:
                targetPoints.add(new Point(curX-1,curY-5));
                targetPoints.add(new Point(curX,curY-5));
                targetPoints.add(new Point(curX+1,curY-5));
                targetPoints.add(new Point(curX-1,curY-4));
                targetPoints.add(new Point(curX,curY-4));
                targetPoints.add(new Point(curX+1,curY-4));
                targetPoints.add(new Point(curX-1,curY-3));
                targetPoints.add(new Point(curX,curY-3));
                targetPoints.add(new Point(curX+1,curY-3));
                return targetPoints;
            default:
                targetPoints.add(new Point(curX+5,curY-1));
                targetPoints.add(new Point(curX+5,curY));
                targetPoints.add(new Point(curX+5,curY+1));
                targetPoints.add(new Point(curX+4,curY-1));
                targetPoints.add(new Point(curX+4,curY));
                targetPoints.add(new Point(curX+4,curY+1));
                targetPoints.add(new Point(curX+3,curY-1));
                targetPoints.add(new Point(curX+3,curY));
                targetPoints.add(new Point(curX+3,curY+1));
                return targetPoints;
        }
    }

    private boolean grenadeAva(TETile[][] board, int x, int y) {
        if(x<0||x>=WIDTH||y<0||y>=HEIGHT) {
            return false;
        }
        if(board[x][y].equals(Tileset.MOUNTAIN)) {
            return false;
        }
        return true;
    }

    private Point nextLaserDam(Point curDamage, int dir) {
        //1A 2W 3S 4D
        switch (dir) {
            case 1:
                return new Point(curDamage.getX()-1,curDamage.getY());
            case 2:
                return new Point(curDamage.getX(),curDamage.getY()+1);
            case 3:
                return new Point(curDamage.getX(),curDamage.getY()-1);
            case 4:
                return new Point(curDamage.getX()+1,curDamage.getY());
            default:
                return new Point(0,0);
        }
    }


    private boolean checkAction(TETile[][] board, int targetX, int targetY, Avatar p) {
        if(board[targetX][targetY].equals(Tileset.GRASS)) {
            return true;
        } else if(board[targetX][targetY].equals(Tileset.HEART)) {
            p.upLives();
            return true;
        } else if(board[targetX][targetY].equals(Tileset.BABYROCK)) {
            p.upRocks();
            return true;
        } else if(board[targetX][targetY].equals(Tileset.SUN)) {
            p.upSightLevel();
            return true;
        } else if(board[targetX][targetY].equals(Tileset.LASER)) {
            p.addWeapon("laser");
            return true;
        } else if(board[targetX][targetY].equals(Tileset.GRENADE)) {
            p.addWeapon("grenade");
            return true;
        }
        return false;
    }

    private void renderAvatar(TETile[][] board, Avatar p) {
        board[p.getX()][p.getY()] = p.getTile();
    }

    private Point landObject(TETile[][] board) {
        Point newPos = randomLanding(board);
        return newPos;
    }

    private Point randomLanding(TETile[][] board) {
        int x = RandomUtils.uniform(RANDOM,0,WIDTH);
        int y = RandomUtils.uniform(RANDOM,0,HEIGHT);
        if(board[x][y].equals(Tileset.GRASS)) {
            return new Point(x,y);
        } else {
            return randomLanding(board);
        }
    }

    private void renderWall(TETile[][] board) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if(board[x][y].equals(Tileset.GRASS)) {
                    wallRender(x,y,board);
                }
            }
        }
    }

    private void wallRender(int x, int y, TETile[][] board) {
        if(board[x-1][y+1].equals(Tileset.NOTHING)) {
            board[x-1][y+1] = Tileset.MOUNTAIN;
        }
        if(board[x][y+1].equals(Tileset.NOTHING)) {
            board[x][y+1] = Tileset.MOUNTAIN;
        }
        if(board[x+1][y+1].equals(Tileset.NOTHING)) {
            board[x+1][y+1] = Tileset.MOUNTAIN;
        }
        if(board[x-1][y].equals(Tileset.NOTHING)) {
            board[x-1][y] = Tileset.MOUNTAIN;
        }
        if(board[x+1][y].equals(Tileset.NOTHING)) {
            board[x+1][y] = Tileset.MOUNTAIN;
        }
        if(board[x-1][y-1].equals(Tileset.NOTHING)) {
            board[x-1][y-1] = Tileset.MOUNTAIN;
        }
        if(board[x][y-1].equals(Tileset.NOTHING)) {
            board[x][y-1] = Tileset.MOUNTAIN;
        }
        if(board[x+1][y-1].equals(Tileset.NOTHING)) {
            board[x+1][y-1] = Tileset.MOUNTAIN;
        }
    }

    private boolean fullyConnectCheck(List<Room> rooms) {
        Room startRoom = rooms.get(0);
        List<Room> visited = new ArrayList<>();
        DFS(startRoom,visited);
        if(visited.size()==rooms.size()) {
            return true;
        }
        return false;
    }

    private void DFS(Room startRoom, List<Room> visited) {
        visited.add(startRoom);
        for(Room neighbor: startRoom.getNeighbors()) {
            if(!visited.contains(neighbor)) {
                DFS(neighbor, visited);
            }
        }
    }

    private void initializeBoard(TETile[][] board) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                board[x][y] = Tileset.NOTHING;
            }
        }
    }

    private Map<String, Integer> randomRoomGenerate(TETile[][] board) {
        int roomWidth = RandomUtils.uniform(RANDOM,4,6);
        int roomHeight = RandomUtils.uniform(RANDOM,4,6);
        int startX = RandomUtils.uniform(RANDOM,3,WIDTH-roomWidth-2);
        int startY = RandomUtils.uniform(RANDOM,3,HEIGHT-roomHeight-2);
        if(validateRoom(startX,startY,roomWidth,roomHeight,board)) {
            renderRoom(startX,startY,roomWidth,roomHeight,board);
            Map<String, Integer> roomInfo = new HashMap<>();
            roomInfo.put("X",startX);
            roomInfo.put("Y",startY);
            roomInfo.put("Width",roomWidth);
            roomInfo.put("Height",roomHeight);
            return roomInfo;
        } else {
            return randomRoomGenerate(board);
        }
    }

    private boolean validateRoom(int startX, int startY, int roomWidth, int roomHeight, TETile[][] board) {
        for(int i=startY-3;i<=startY+roomHeight+2;i++) {
            for(int j=startX-3;j<=startX+roomWidth+2;j++) {
                if(!board[j][i].equals(Tileset.NOTHING)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void renderRoom(int startX, int startY, int roomWidth, int roomHeight, TETile[][] board) {
        for(int i=startY-1;i<=startY+roomHeight;i++) {
            for(int j=startX-1;j<=startX+roomWidth;j++) {
                if(i==startY-1||i==startY+roomHeight||j==startX-1||j==startX+roomWidth) {
                    //board[j][i] = Tileset.WALL;
                } else {
                    board[j][i] = Tileset.GRASS;
                }
            }
        }
    }

    private void randomHallwayGenerate(List<Room> rooms, TETile[][] board) {
        for(Room curRoom: rooms) {
            if(curRoom.getOccupiedWall().size()>=3) {
                continue;
            }
            int neighborNum = RandomUtils.uniform(RANDOM,1,3);
            //int neighborNum = 1;
            List<Room> newNeighbors = choices(rooms,curRoom,neighborNum);
            for(Room newNeighbor: newNeighbors) {
                curRoom.addNeighbors(newNeighbor);
                newNeighbor.addNeighbors(curRoom);
                if(hallwayBuild(curRoom,newNeighbor,board)) {
                    continue;
                } else {
                    curRoom.removeNeighbors(newNeighbor);
                    newNeighbor.removeNeighbors(curRoom);
                }
            }
        }
    }

    private boolean hallwayBuild(Room a, Room b, TETile[][] board) {
        Map<String, Integer> exit1 = exitRandomSelect(a,b);
        Map<String, Integer> exit2 = exitRandomSelect(b,a);
        int aX = exit1.get("X");
        int aY = exit1.get("Y");
        int bX = exit2.get("X");
        int bY = exit2.get("Y");
        if(validateHallway(aX,bX,aY,bY,board)) {
            List<Point> solvedPath = solvePath(aX,bX,aY,bY,board);
            solvedPath.add(new Point(bX,bY));
            renderHallway(solvedPath,board);
            int aWall = exit1.get("Wall");
            a.addWall(aWall);
            int bWall = exit2.get("Wall");
            b.addWall(bWall);
            return true;
        } else {
            //hallwayBuild(a,b,board);
            return false;
        }
    }

    private void renderHallway(List<Point> path, TETile[][] board) {
        for(Point curStep: path) {
            board[curStep.getX()][curStep.getY()] = Tileset.GRASS;
        }
    }

    private boolean validateHallway(int aX, int bX, int aY, int bY, TETile[][] board) {
        List<Point> path = solvePath(aX,bX,aY,bY,board);
        if(path.get(path.size()-1).getX()==-1 && path.get(path.size()-1).getY()==-1) {
            return false;
        }
        return true;
    }

    private List<Point> solvePath(int aX, int bX, int aY, int bY, TETile[][] board) {
        int tempX = aX;
        int tempY = aY;
        List<Point> path = new ArrayList<>();
        while(tempX!=bX || tempY!=bY) {
            Point curStep = new Point(tempX, tempY);
            path.add(curStep);
            Map<String, Integer> nextStepInfo = nextStep(bX,bY,path,board);
            if(nextStepInfo.get("X") == -1 && nextStepInfo.get("Y")==-1) {
                path.add(new Point(-1,-1));
                break;
            }
            tempX = nextStepInfo.get("X");
            tempY = nextStepInfo.get("Y");
        }
        return path;
    }

    private Map<String, Integer> nextStep(int targetX, int targetY, List<Point> path, TETile[][] board) {
        Point curStep = path.get(path.size()-1);
        int curX = curStep.getX();
        int curY = curStep.getY();
        List<Point> priorityStep = octupleCAL(curX,curY,targetX,targetY);
        Point nextPoint = pickPoint(priorityStep,path,targetX,targetY,board);
        Map<String, Integer> PointInfo = new HashMap<>();
        PointInfo.put("X",nextPoint.getX());
        PointInfo.put("Y",nextPoint.getY());
        return PointInfo;
    }

    private Point pickPoint(List<Point> priorityList, List<Point> path, int targetX, int targetY, TETile[][] board) {
        for(Point nextPoint: priorityList) {
            if(nextPoint.getX()==targetX && nextPoint.getY()==targetY) {
                return nextPoint;
            }
            if(checkPoint(nextPoint,path,board)) {
                return nextPoint;
            }
        }
        return new Point(-1,-1);
    }

    private boolean checkPoint(Point nextPoint, List<Point> path, TETile[][] board) {
        List<Point> nextNearby = nearbyPoints(path.get(path.size()-1),nextPoint);
        for(Point nextCheck: nextNearby) {
            if(path.contains(nextCheck)) {
                return false;
            }
            if(nextCheck.getX()<0 || nextCheck.getX()>=WIDTH || nextCheck.getY()<0 ||nextCheck.getY()>=HEIGHT) {
                return false;
            }
            if(!board[nextCheck.getX()][nextCheck.getY()].equals(Tileset.NOTHING)) {
                return false;
            }
        }
        return true;
    }

    private List<Point> nearbyPoints(Point curPoint, Point nextPoint) {
        int curX = curPoint.getX();
        int curY = curPoint.getY();
        int nextX = nextPoint.getX();
        int nextY = nextPoint.getY();
        List<Point> returnList = new ArrayList<>();
        if(curX == nextX-1) {
            returnList.add(new Point(nextX,nextY+1));
            returnList.add(new Point(nextX,nextY-1));
            returnList.add(new Point(nextX+1,nextY+1));
            returnList.add(new Point(nextX+1,nextY));
            returnList.add(new Point(nextX+1,nextY-1));
        } else if(curX == nextX+1) {
            returnList.add(new Point(nextX,nextY+1));
            returnList.add(new Point(nextX,nextY-1));
            returnList.add(new Point(nextX-1,nextY+1));
            returnList.add(new Point(nextX-1,nextY));
            returnList.add(new Point(nextX-1,nextY-1));
        } else if(curY == nextY-1) {
            returnList.add(new Point(nextX-1,nextY));
            returnList.add(new Point(nextX+1,nextY));
            returnList.add(new Point(nextX-1,nextY+1));
            returnList.add(new Point(nextX,nextY+1));
            returnList.add(new Point(nextX+1,nextY+1));
        } else if(curY == nextY+1) {
            returnList.add(new Point(nextX-1,nextY));
            returnList.add(new Point(nextX+1,nextY));
            returnList.add(new Point(nextX-1,nextY-1));
            returnList.add(new Point(nextX,nextY-1));
            returnList.add(new Point(nextX+1,nextY-1));
        }
        return returnList;
    }

    private List<Point> octupleCAL(int startX, int startY, int endX, int endY) {
        Point R = new Point(startX+1,startY);
        Point L = new Point(startX-1,startY);
        Point U = new Point(startX,startY+1);
        Point D = new Point(startX,startY-1);
        List<Point> priorityList = new ArrayList<>();
        double bearing = Math.atan2(endY-startY,endX-startX)*180/Math.PI;
        if(bearing>=0 && bearing<45) {
            priorityList.add(R);
            priorityList.add(U);
            priorityList.add(D);
            priorityList.add(L);
        } else if(bearing>=45 && bearing<90) {
            priorityList.add(U);
            priorityList.add(R);
            priorityList.add(L);
            priorityList.add(D);
        } else if(bearing>=90 && bearing<135) {
            priorityList.add(U);
            priorityList.add(L);
            priorityList.add(R);
            priorityList.add(D);
        } else if(bearing>=135 && bearing<=180) {
            priorityList.add(L);
            priorityList.add(U);
            priorityList.add(D);
            priorityList.add(R);
        } else if(bearing>=-45 && bearing<0) {
            priorityList.add(R);
            priorityList.add(D);
            priorityList.add(U);
            priorityList.add(L);
        } else if(bearing>=-90 && bearing<-45) {
            priorityList.add(D);
            priorityList.add(R);
            priorityList.add(L);
            priorityList.add(U);
        } else if(bearing>=-135 && bearing<-90) {
            priorityList.add(D);
            priorityList.add(L);
            priorityList.add(R);
            priorityList.add(U);
        } else {
            priorityList.add(L);
            priorityList.add(D);
            priorityList.add(U);
            priorityList.add(R);
        }
        return priorityList;
    }

    private int octupleWALL(int startX, int startY, int endX, int endY, Room startRoom) {
        double bearing = Math.atan2(endY-startY,endX-startX)*180/Math.PI;
        List<Integer> wallPriority = new ArrayList<>();
        int U = 1;
        int D = 2;
        int L = 3;
        int R = 4;
        if(bearing>=0 && bearing<45) {
            wallPriority.add(R);
            wallPriority.add(U);
            wallPriority.add(D);
            wallPriority.add(L);
        } else if(bearing>=45 && bearing<90) {
            wallPriority.add(U);
            wallPriority.add(R);
            wallPriority.add(L);
            wallPriority.add(D);
        } else if(bearing>=90 && bearing<135) {
            wallPriority.add(U);
            wallPriority.add(L);
            wallPriority.add(R);
            wallPriority.add(D);
        } else if(bearing>=135 && bearing<=180) {
            wallPriority.add(L);
            wallPriority.add(U);
            wallPriority.add(D);
            wallPriority.add(R);
        } else if(bearing>=-45 && bearing<0) {
            wallPriority.add(R);
            wallPriority.add(D);
            wallPriority.add(U);
            wallPriority.add(L);
        } else if(bearing>=-90 && bearing<-45) {
            wallPriority.add(D);
            wallPriority.add(R);
            wallPriority.add(L);
            wallPriority.add(U);
        } else if(bearing>=-135 && bearing<-90) {
            wallPriority.add(D);
            wallPriority.add(L);
            wallPriority.add(R);
            wallPriority.add(U);
        } else {
            wallPriority.add(L);
            wallPriority.add(D);
            wallPriority.add(U);
            wallPriority.add(R);
        }
        for(Integer wall: startRoom.getOccupiedWall()) {
            if(wallPriority.contains(wall)) {
                wallPriority.remove(Integer.valueOf(wall));
            }
        }
        return wallPriority.remove(0);
    }


    private Map<String, Integer> exitRandomSelect(Room start, Room end) {
        int startX = start.getX();
        int startY = start.getY();
        int startW = start.getWidth();
        int startH = start.getHeight();
        int startCX = startX+startW/2;
        int startCY = startY+startH/2;

        int endX = end.getX();
        int endY = end.getY();
        int endW = end.getWidth();
        int endH = end.getHeight();
        int endCX = endX+endW/2;
        int endCY = endY+endH/2;


        int wallSelect = octupleWALL(startCX,startCY,endCX,endCY, start);
        Map<String,Integer> location = new HashMap<>();
        switch (wallSelect) {
            case 1:
                location.put("X",RandomUtils.uniform(RANDOM,startX,startX+startW-1));
                location.put("Y",startY+startH);
                location.put("Wall",1);
                return location;
            case 2:
                location.put("X",RandomUtils.uniform(RANDOM,startX,startX+startW-1));
                location.put("Y",startY-1);
                location.put("Wall",2);
                return location;
            case 3:
                location.put("X",startX-1);
                location.put("Y",RandomUtils.uniform(RANDOM,startY,startY+startH-1));
                location.put("Wall",3);
                return location;
            case 4:
                location.put("X",startX+startW);
                location.put("Y",RandomUtils.uniform(RANDOM,startY,startY+startH-1));
                location.put("Wall",4);
                return location;
            default:
                return location;
        }
    }

    private List<Room> choices(List<Room> rooms, Room curRoom, int neighborNum) {
        List<Room> choices = new ArrayList<>();
        for(Room nextRoom: rooms) {
            if(curRoom.getNeighbors().contains(nextRoom) || curRoom.equals(nextRoom) || nextRoom.getOccupiedWall().size()==4) {
                continue;
            }
            if(choices.size()<neighborNum) {
                choices.add(nextRoom);
            } else {
                if(Room.distance(curRoom,nextRoom)<Room.distance(curRoom,farestRoomSearch(choices,curRoom))) {
                    choices.remove(farestRoomSearch(choices,curRoom));
                    choices.add(nextRoom);
                }
            }
        }
        return choices;
    }

    private Room farestRoomSearch(List<Room> rooms, Room targetRoom) {
        Room farestRoom = null;
        double farestDist = 0;
        for(Room nextCandidate: rooms) {
            if(Room.distance(targetRoom,nextCandidate)>farestDist) {
                farestRoom = nextCandidate;
                farestDist = Room.distance(targetRoom,nextCandidate);
            }
        }
        return farestRoom;
    }



    public static void main(String[] args) {
        /**
         ter.initialize(WIDTH, HEIGHT);
         TETile[][] Board = new TETile[WIDTH][HEIGHT];
         randomWorldGenerate(Board);
         ter.renderFrame(Board);
         */

        /**
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        Engine engine = new Engine();
        TETile[][]finalWorldFrame = engine.interactWithInputString("NBG7SWWII");
        ter.renderFrame(finalWorldFrame);
        */


        Engine engine = new Engine();
        engine.interactWithKeyboard();



        /**
        String fonts[] =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        for ( int i = 0; i < fonts.length; i++ )
        {
            System.out.println(fonts[i]);
        }
        */


    }





}


