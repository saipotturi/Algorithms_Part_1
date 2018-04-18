import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

	private final int x, y;
	private pointComparator comparator;

	public Point(int x, int y) // constructs the point (x, y)
	{
		this.x = x;
		this.y = y;
	}

	public void draw() {
		/* DO NOT MODIFY */
		StdDraw.point(x, y);
	}

	public void drawTo(Point that) {
		/* DO NOT MODIFY */
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	public String toString() {
		/* DO NOT MODIFY */
		return "(" + x + ", " + y + ")";
	}

	public int compareTo(Point that) // compare two points by y-coordinates,
										// breaking ties by x-coordinates
	{
		if (this.y < that.y)
			return -1;
		if (this.y > that.y)
			return 1;
		if (this.x < that.x)
			return -1;
		if (this.x > that.x)
			return 1;
		return 0;
	}

	public double slopeTo(Point that) // the slope between this point and that
										// point
	{
		if ((this.y == that.y) && (this.x == that.x))
			return Double.NEGATIVE_INFINITY;
		if (this.y == that.y)
			return 0.0;
		if (this.x == that.x)
			return Double.POSITIVE_INFINITY;
		return (double) (this.y - that.y) / (this.x - that.x);
	}

	private class pointComparator implements Comparator<Point> {

		public int compare(Point o1, Point o2) {

			double slope1 = Point.this.slopeTo(o1);
			double slope2 = Point.this.slopeTo(o2);

			if (slope1 < slope2)
				return -1;
			if (slope1 > slope2)
				return 1;
			return 0;
		}

	}

	public Comparator<Point> slopeOrder() // compare two points by slopes they
											// make with this point
	{
		if (comparator == null)
			comparator = new pointComparator();
		return comparator;
	}

}