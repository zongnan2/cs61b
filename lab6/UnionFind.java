import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class UnionFind {

    private int[] disjointSet;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        this.disjointSet = new int[n];
        for(int i = 0; i < disjointSet.length; i++) {
            disjointSet[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if(vertex < 0 || vertex >= disjointSet.length) {
            throw new NoSuchElementException("Index out of range");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
        int rootVertex = find(v1);
        return -disjointSet[rootVertex];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        return disjointSet[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        if(find(v1) == find(v2)) {
            return true;
        }
        return false;
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        if(v1 == v2 || connected(v1,v2)) {
            return;
        }
        if(sizeOf(v1) > sizeOf(v2)) {
            int sizeOfV2 = sizeOf(v2);
            disjointSet[find(v2)] = find(v1);
            disjointSet[find(v1)] = -(sizeOf(v1) + sizeOfV2);
        } else {
            int sizeOfV1 = sizeOf(v1);
            disjointSet[find(v1)] = find(v2);
            disjointSet[find(v2)] = -(sizeOf(v2) + sizeOfV1);
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        int tempVertex = vertex;
        LinkedList<Integer> path = new LinkedList<>();
        while(parent(tempVertex) >= 0) {
            path.add(tempVertex);
            tempVertex = parent(tempVertex);
        }
        while(!path.isEmpty()) {
            disjointSet[path.removeFirst()] = tempVertex;
        }
        return tempVertex;
    }

}
