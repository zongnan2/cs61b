package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double mean;
    private double stddev;
    private double confidenceLow;
    private double confidenceHigh;

    public PercolationStats(int N, int T, PercolationFactory pf) {  // perform T independent experiments on an N-by-N grid
        if(N<=0 || T<=0) {
            throw new java.lang.IllegalArgumentException("Incorrect input T or N");
        }
        double[] fractions = new double[T];
        for(int i=0;i<T;i++) {
            Percolation testSample = pf.make(N);
            while(!testSample.percolates()) {
                int randomRow = StdRandom.uniform(0,N);
                int randomCol = StdRandom.uniform(0,N);
                testSample.open(randomRow, randomCol);
            }
            fractions[i] = (testSample.numberOfOpenSites()/(N*N));
        }
        mean = StdStats.mean(fractions);

        stddev = StdStats.stddev(fractions);

        confidenceLow = mean - 1.96*stddev/Math.sqrt(T);

        confidenceHigh = mean + 1.96*stddev/Math.sqrt(T);
    }

    public double mean() {                                          // sample mean of percolation threshold
        return mean;
    }

    public double stddev() {                                        // sample standard deviation of percolation threshold
        return stddev;
    }

    public double confidenceLow() {                                 // low endpoint of 95% confidence interval
        return confidenceLow;
    }

    public double confidenceHigh() {                                // high endpoint of 95% confidence interval
        return confidenceHigh;
    }
}
