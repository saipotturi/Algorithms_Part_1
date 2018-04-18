
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private int[][] matrix;
	private WeightedQuickUnionUF wqu;
	private int size = 0, conv, opensites = 0;

	private void isvalidindices(int p, int q) {
		if (((p <= 0) || (p > size)) || ((q <= 0) || (q > size)))
			throw new java.lang.IllegalArgumentException();
	}

	private int dconv(int p, int q) {
		conv = size * p + q + 1;
		return conv;
	}

	public Percolation(int n) // create n-by-n grid, with all sites blocked
	{
		if (n <= 0)
			throw new java.lang.IllegalArgumentException();

		matrix = new int[n][n];

		wqu = new WeightedQuickUnionUF((n * n) + 2);

		size = n;

	}

	public void open(int row, int col) // open site (row, col) if it is not open
										// already
	{

		isvalidindices(row, col);
		
		
		if (!isOpen(row, col)) {
			row = row - 1;
			col = col - 1;
			
			matrix[row][col] = 1;
			opensites++;

			if ((col - 1 >= 0) && (matrix[row][col - 1] == 1))
				wqu.union(dconv(row, col), dconv(row, col - 1));
			if ((col + 1 < size) && (matrix[row][col + 1] == 1))
				wqu.union(dconv(row, col), dconv(row, col + 1));
			if ((row - 1 >= 0) && (matrix[row - 1][col] == 1))
				wqu.union(dconv(row, col), dconv(row - 1, col));
			if ((row + 1 < size) && (matrix[row + 1][col] == 1))
				wqu.union(dconv(row, col), dconv(row + 1, col));
			if (row == 0)
				wqu.union(dconv(row, col), 0);
			if (row == size - 1)
				wqu.union(dconv(row, col), (size * size) + 1);

		}

		else
			return;

	}

	public boolean isOpen(int row, int col) // is site (row, col) open?
	{
		isvalidindices(row, col);

		if (matrix[row-1][col-1] == 1)
			return true;
		else
			return false;
	}

	public boolean isFull(int row, int col) // is site (row, col) full?
	{
		isvalidindices(row, col);

		if (wqu.connected(0, dconv(row - 1, col - 1)))
			return true;
		else
			return false;
	}

	public int numberOfOpenSites() // number of open sites
	{
		return opensites;
	}

	public boolean percolates() // does the system percolate?
	{
		if (wqu.connected(0, (size * size) + 1))
			return true;
		else
			return false;
	}

	public static void main(String[] args) // test client (optional)
	{

	}

}
