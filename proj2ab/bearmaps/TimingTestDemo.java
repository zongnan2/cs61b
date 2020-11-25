package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug. Demonstrates how you can use either
 * System.currentTimeMillis or the Princeton Stopwatch
 * class to time code.
 */
public class TimingTestDemo {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ArrayHeapMinPQ<Integer> alphabet = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 10; i += 1) {
            for (int j = 0; j < 100; j += 1) {
                alphabet.add(i*100+j,Math.random());
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start)/1000.0 +  " seconds.");

        Stopwatch sw = new Stopwatch();
        ArrayHeapMinPQ<Integer> Beta = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 10; i += 1) {
            for (int j = 0; j < 100; j += 1) {
                Beta.add(i*100+j,Math.random());
            }
        }
        System.out.println("Total time elapsed: " + sw.elapsedTime() +  " seconds.");
    }
}
