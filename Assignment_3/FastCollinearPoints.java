import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

	private Point[] points, tpoints, tmparray;
	private Point point;
	private double slope;
	private int breakpoint = 1, start, end, enablebreak;
	private LineSegment segment;
	private ArrayList<LineSegment> linesegments;

	public FastCollinearPoints(Point[] points) // finds all line segments
												// containing 4 or more points
	{
		if (points == null)
			throw new java.lang.IllegalArgumentException();

		for (int i = 0; i < points.length; i++) {
			if (points[i] == null)
				throw new java.lang.IllegalArgumentException();
		}

		this.points = Arrays.copyOf(points, points.length);
		this.tpoints = Arrays.copyOf(points, points.length);
		linesegments = new ArrayList<LineSegment>();

		for (int i = 0; i < points.length; i++) {
			if ((i != 0) && (this.points[i] == this.points[i - 1]))
				throw new java.lang.IllegalArgumentException();
		}

		collinearity();
	}

	public int numberOfSegments() // the number of line segments
	{
		return linesegments.size();
	}

	public LineSegment[] segments() // the line segments
	{
		return linesegments.toArray(new LineSegment[linesegments.size()]);
	}

	private void collinearity() {

		for (int i = 0; i < points.length; i++) {

			point = points[i];

			Arrays.sort(tpoints, point.slopeOrder());

			breakpoint = 1;
			enablebreak = 1;

			for (int j = 0; j < points.length; j++) {

				if (j == 0) {
					slope = point.slopeTo(tpoints[j]);
					continue;
				}

				if (slope == point.slopeTo(tpoints[j])) {
					breakpoint++;

					if (breakpoint >= 3) {
						start = j - (breakpoint);
						end = j;
						tmparray = new Point[(end - start) + 1];
						tmparray[0] = point;

						for (int k = 1; k <= (end - start); k++) {
							tmparray[k] = tpoints[end - k];
						}

						Arrays.sort(tmparray);

						segment = new LineSegment(tmparray[0], tmparray[tmparray.length - 1]);

						// if(!linesegments.contains(segment))
						// linesegments.add(segment);
						boolean added = false;

						for (LineSegment l : linesegments) {
							if (l.toString().equals(segment.toString())) {
								added = true;
								break;
							}
						}

						if (!added)
							linesegments.add(segment);
					}

				} else {
					slope = point.slopeTo(tpoints[j]);

					breakpoint = 1;
				}

			}

		}
	}
}