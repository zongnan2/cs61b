package bearmaps;

import java.util.List;

public class KDTree implements PointSet{
    KD_BST KDT;
    int K = 2;

    private class KD_BST {
        Point P;
        KD_BST left;
        KD_BST right;
        int depth;

        KD_BST(Point p, KD_BST left, KD_BST right, int depth) {
            this.P = p;
            this.left = left;
            this.right = right;
            this.depth = depth;
        }

        KD_BST add(KD_BST kdt, Point p, int dep) {
            if(kdt == null) {
                return new KD_BST(p,null,null, dep);
            }
            if(dep%K == 0) {
                if(p.getX()<kdt.P.getX()) {
                    dep++;
                    kdt.left = add(kdt.left, p, dep);
                } else {
                    dep++;
                    kdt.right = add(kdt.right,p,dep);
                }
            } else {
                if(p.getY()<kdt.P.getY()) {
                    dep++;
                    kdt.left = add(kdt.left, p, dep);
                } else {
                    dep++;
                    kdt.right = add(kdt.right,p,dep);
                }
            }
            return kdt;
        }
    }




    public KDTree(List<Point> points) {
        for(Point P: points) {
            if(KDT == null) {
                KDT = new KD_BST(P,null,null, 0);
            } else {
                KDT = KDT.add(KDT, P, 0);
            }
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point target = new Point(x,y);
        return nearestHelper(KDT, target, KDT);
    }

    private Point nearestHelper(KD_BST current, Point query, KD_BST best) {
        if(current == null) {
            return best.P;
        }
        if(current.P.distance(current.P,query)<best.P.distance(best.P,query)) {
            best.P = new Point(current.P.getX(),current.P.getY());
        }
        if(current.depth%K == 0) {
            if(query.getX() < current.P.getX()) {
                best.P = nearestHelper(current.left, query, best);
                if(Math.pow(current.P.getX()-query.getX(), 2) < best.P.distance(best.P,query)) {
                    best.P = nearestHelper(current.right, query, best);
                }
            } else {
                best.P = nearestHelper(current.right, query, best);
                if(Math.pow(query.getX()-current.P.getX(), 2) < best.P.distance(best.P,query)) {
                    best.P = nearestHelper(current.left, query, best);
                }
            }
        } else {
            if(query.getY() < current.P.getY()) {
                best.P = nearestHelper(current.left, query, best);
                if(Math.pow(current.P.getY()-query.getY(), 2) < best.P.distance(best.P,query)) {
                    best.P = nearestHelper(current.right, query, best);
                }
            } else {
                best.P = nearestHelper(current.right, query, best);
                if(Math.pow(query.getY()-current.P.getY(), 2) < best.P.distance(best.P,query)) {
                    best.P = nearestHelper(current.left, query, best);
                }
            }
        }
        return best.P;
    }


    public static void main(String[] args) {
        Point p1 = new Point(5, 6); // constructs a Point with x = 2, y = 3
        Point p2 = new Point(1, 5);
        Point p3 = new Point(7, 3);
        Point p4 = new Point(2, 2);
        Point p5 = new Point(4, 9);
        Point p6 = new Point(9, 1);
        Point p7 = new Point(8, 7);
        Point p8 = new Point(6, 2);


        KDTree nn = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7, p8));
        System.out.print(nn.nearest(3,6));
    }
}
