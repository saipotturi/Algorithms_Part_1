import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private Percolation percolation;

	private int p, q, size, num_trials;

	private double[] opensites_array;
	
	private double mean, stddev;

	public PercolationStats(int n, int trials) // perform trials independent
												// experiments on an n-by-n grid
	{
		size = n;

		num_trials = trials;

		if (n <= 0 || trials <= 0)
			throw new java.lang.IllegalArgumentException();

		opensites_array = new double[num_trials];
		
		for (int i = 0; i < trials; i++) {
			percolation = new Percolation(n);

			while (!percolation.percolates()) {

				p = StdRandom.uniform(1, size + 1);
				q = StdRandom.uniform(1, size + 1);

				percolation.open(p, q);

			}
			opensites_array[i] = (double) (percolation.numberOfOpenSites()) / (size * size);
		}

	}

	public double mean() // sample mean of percolation threshold
	{
		mean = StdStats.mean(opensites_array);
		return mean;
	}

	public double stddev() // sample standard deviation of percolation threshold
	{
		stddev = StdStats.stddev(opensites_array);
		return stddev;
	}

	public double confidenceLo() // low endpoint of 95% confidence interval
	{
		if(mean == 0)
			mean();
		return (mean - (1.96 * stddev()) / Math.sqrt(num_trials));
	}

	public double confidenceHi() // high endpoint of 95% confidence interval
	{
		if(mean == 0)
			mean();
		return (mean + (1.96 * stddev()) / Math.sqrt(num_trials));
	}

	public static void main(String[] args) // test client (described below)
	{
		int n = 0, trials=0;
		
		if(args.length == 2)
		{
			n = Integer.parseInt(args[0]);
			trials = Integer.parseInt(args[1]);
		}
		
		PercolationStats percolationstats = new PercolationStats(n, trials);

		System.out.println("Mean = " + percolationstats.mean());
		System.out.println("stddev = " +percolationstats.stddev());
		System.out.println("95% confidence interval = [" + percolationstats.confidenceLo() +", "+percolationstats.confidenceHi() + "]");

	}
}