package bearmaps;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class KDTreeTest {
    // Test Discussion Case
    @Test
    public void KDTreeGeneralTest() {
        Point p1 = new Point(5, 6); // constructs a Point with x = 2, y = 3
        Point p2 = new Point(1, 5);
        Point p3 = new Point(7, 3);
        Point p4 = new Point(2, 2);
        Point p5 = new Point(4, 9);
        Point p6 = new Point(9, 1);
        Point p7 = new Point(8, 7);
        Point p8 = new Point(6, 2);
        KDTree nn = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7, p8));
        assertEquals(nn.nearest(3,6),p1);
    }
}
