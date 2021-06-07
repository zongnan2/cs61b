package byow.Core;

import java.util.ArrayList;

public class Point {
    private int X;
    private int Y;

    public Point(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point a = (Point) o;
        return (a.X == X) &&
                (a.Y == Y);
    }
}
