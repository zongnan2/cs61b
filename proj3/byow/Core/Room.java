package byow.Core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Room {

    private int X;
    private int Y;
    private int Width;
    private int Height;
    private List<Room> neighbors;
    private List<Integer> occupiedWall;

    public Room(int X, int Y, int Width, int Height) {
        this.X = X;
        this.Y = Y;
        this.Width = Width;
        this.Height = Height;
        neighbors = new ArrayList<>();
        occupiedWall = new ArrayList<>();
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getWidth() {
        return Width;
    }

    public int getHeight() {
        return Height;
    }

    public List<Room> getNeighbors() {
        return neighbors;
    }

    public void addNeighbors(Room neighbor) {
        neighbors.add(neighbor);
    }

    public void removeNeighbors(Room neighbor) {
        neighbors.remove(neighbor);
    }

    private static double distance(int X1, int X2, int Y1, int Y2) {
        return Math.pow(X1-X2,2)+Math.pow(Y1-Y2,2);
    }

    public static double distance(Room a, Room b) {
        return distance(a.X,b.X,a.Y,b.Y);
    }

    public List<Integer> getOccupiedWall() {
        return occupiedWall;
    }

    public void addWall(int a) {
        occupiedWall.add(a);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room a = (Room) o;
        return (a.X == X) &&
                (a.Y == Y) &&
                (a.Width == Width) &&
                (a.Height == Height) &&
                (a.neighbors.equals(neighbors));
    }
}
