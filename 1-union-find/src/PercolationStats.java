import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.Math;

public class PercolationStats {
    private final double[] x;
    private final int n_trials;
    private final int n_grid;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.n_trials = trials;
        this.n_grid = n;
        this.x = new double[trials];

        for (int i=0; i<n_trials; i++) {
            x[i] = estimate_p();
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(x);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(x) / (double)(n_trials-1);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean()-1.96*stddev()/Math.sqrt(n_trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean()+1.96*stddev()/Math.sqrt(n_trials);
    }

    private int random_coord() {
        return StdRandom.uniform(1, n_grid+1);
    }

    //Compute percolation probability
    private double estimate_p() {
        Percolation p = new Percolation(n_grid);
        int i = 0;
        while (!p.percolates()) {
            int x = random_coord();
            int y = random_coord();

            if (!p.isOpen(x,y)) {
                p.open(x,y);
                i++;
            }
        }
        return i / Math.pow(n_grid, 2);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n_grid = Integer.parseInt(args[0]);
        int n_trials = Integer.parseInt(args[1]);

        PercolationStats perc = new PercolationStats(n_grid, n_trials);
        System.out.println(perc.mean());
    }


}