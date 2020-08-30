package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private int[][] grid;                      // 0 is close, 1 is open, 2 is full
    private WeightedQuickUnionUF disjointSet;
    private int numberOfOpen;

    public Percolation(int N) {                // create N-by-N grid, with all sites initially blocked
        if(N <= 0) {
            throw new java.lang.IllegalArgumentException("Illegal input size of N!");
        }
        size = N;
        grid = new int[N][N];
        disjointSet = new WeightedQuickUnionUF(size*size);
        numberOfOpen = 0;
    }

    /**
     * return if it is open already, if not, open it and set the value to
     * open or full depend on its row value. Check if its neighbor is open,
     * union them if true.
     */
    public void open(int row, int col) {      // open the site (row, col) if it is not open already
        cornerCheck(row,col);
        if(isOpen(row,col)) {
            return;
        }
        grid[row][col] = 1;
        numberOfOpen ++;
        int curIndex = xyTo1D(row,col);
        int[] neighbor = checkNeighbor(row,col);
        for(int i=0;i<4;i++) {
            if(neighbor[i] == 0) {
                unionGrid(curIndex,i,row,col);
            }
        }
    }

    private void unionGrid(int curIndex, int dirIndex, int row, int col) {
        int targetIndex = 0;
        if(dirIndex == 0) {
            if(!isOpen(row-1,col)) { return;}
            targetIndex = curIndex - size;
        }
        if(dirIndex == 1) {
            if(!isOpen(row,col+1)) { return;}
            targetIndex = curIndex + 1;
        }
        if(dirIndex == 2) {
            if(!isOpen(row+1,col)) { return;}
            targetIndex = curIndex + size;
        }
        if(dirIndex == 3) {
            if(!isOpen(row,col-1)) { return;}
            targetIndex = curIndex - 1;
        }
        disjointSet.union(curIndex,targetIndex);
    }

    private int[] checkNeighbor(int row, int col) {
        int[] neighbor = new int[4];
        if(row == 0) {
            neighbor[0] = -1;
        }
        if(col == size-1) {
            neighbor[1] = -1;
        }
        if(row == size-1) {
            neighbor[2] = -1;
        }
        if(col == 0) {
            neighbor[3] = -1;
        }
        return neighbor;
    }

    public boolean isOpen(int row, int col) { // is the site (row, col) open?
        cornerCheck(row,col);
        if(grid[row][col] != 0) {
            return true;
        }
        return false;
    }

    public boolean isFull(int row, int col) { // is the site (row, col) full?
        cornerCheck(row,col);
        if(!isOpen(row,col)) {
            return false;
        }
        for(int i=0;i<size;i++) {
            if(disjointSet.connected(xyTo1D(row,col),xyTo1D(0,i))) {
                return true;
            }
        }
        return false;
    }

    public int numberOfOpenSites() {          // number of open sites
        return numberOfOpen;
    }

    public boolean percolates() {             // does the system percolate?
        for(int i=0;i<size;i++) {
            for(int j=0;j<size;j++) {
                if(disjointSet.connected(xyTo1D(0,i),xyTo1D(size-1,j))) {
                    return true;
                }
            }
        }
        return false;
    }


    private void cornerCheck(int row, int col) {
        if(row < 0 || row > size-1 || col < 0 || col > size-1) {
            throw new java.lang.IndexOutOfBoundsException("Index out of Bounds!");
        }
    }

    private int xyTo1D(int row, int col) {
        return row*size + col;
    }

    public static void main(String[] args) {  // use for unit testing (not required, but keep this here for the autograder)
        Percolation example = new Percolation(5);
        example.open(1,1);
        example.open(2,1);
        example.open(3,1);
        example.open(4,4);
    }
}
