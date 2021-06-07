package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Avatar {
    private int X;
    private int Y;
    private int lives;
    private TETile tile;
    private int degreeInfect;
    private final int degreeMax = 5;
    private int rocks;
    private Point next;
    private int sightLevel;
    private List<String> weapon;

    public Avatar(int X, int Y) {
        this.X = X;
        this.Y = Y;
        this.lives = 3;
        this.degreeInfect = 0;
        this.rocks = 0;
        this.next = new Point(X,Y);
        this.sightLevel = 3;
        weapon = new ArrayList<>();
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getLives() {
        return lives - degreeInfect/degreeMax;
    }

    public int getRocks() {
        return rocks;
    }

    public Point getNext() {
        return next;
    }

    public int getSightLevel() {
        return sightLevel;
    }

    public void getInfect() {
        degreeInfect++;
    }

    public List<String> getWeapon() {
        return weapon;
    }

    public String downWeapon() {
        if(weapon.isEmpty()) {
            return "empty";
        }
        return weapon.remove(0);
    }

    public void upLives() {
        lives++;
    }

    public void upRocks() {
        rocks++;
    }

    public void downRocks() {
        rocks--;
    }

    public void downLives() {
        lives--;
    }

    public void upSightLevel() {
        sightLevel++;
    }

    public void addWeapon(String w) {
        weapon.add(w);
    }

    public void setNext(int prevX, int prevY) {
        next = new Point(prevX,prevY);
    }

    public TETile getTile() {
        return tile;
    }

    public void setX(int newX) {
        X = newX;
    }

    public void setY(int newY) {
        Y = newY;
    }

    public void setTile(char gender, String playerInfo) {
        switch (gender) {
            case 'G':
            case 'g':
                tile = new TETile('♀', Color.white, Color.black, playerInfo);
                break;
            default:
                tile = new TETile('♂', Color.white, Color.black, playerInfo);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avatar a = (Avatar) o;
        return (a.X == X) &&
                (a.Y == Y);
    }
}
