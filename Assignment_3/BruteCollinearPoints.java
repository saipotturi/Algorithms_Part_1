import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

	private Point[] points, tmparray;
	private LineSegment segment;
	private List<LineSegment> linesegments;

	public BruteCollinearPoints(Point[] points) // finds all line segments
												// containing 4 points
	{
		if (points == null)
			throw new java.lang.IllegalArgumentException();

		for (int i = 0; i < points.length; i++) {
			if (points[i] == null)
				throw new java.lang.IllegalArgumentException();
		}

		this.points = Arrays.copyOf(points, points.length);
		Arrays.sort(this.points);

		for (int i = 0; i < points.length; i++) {
			if ((i != 0) && (this.points[i] == this.points[i - 1]))
				throw new java.lang.IllegalArgumentException();
		}
		
		populatesegments();

	}

	public int numberOfSegments() // the number of line segments
	{
		return linesegments.size();
	}

	public LineSegment[] segments() // the line segments
	{
		return linesegments.toArray(new LineSegment[linesegments.size()]);
	}

	private void populatesegments() {

		linesegments = new ArrayList<LineSegment>();
		tmparray = new Point[4];

		Arrays.sort(this.points);

		for (int i = 0; i < points.length - 3; i++) {

			for (int j = i + 1; j < points.length - 2; j++) {

				for (int k = j + 1; k < points.length - 1; k++) {

					if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[k]))
						continue;

					for (int m = k + 1; m < points.length; m++) {

						if (points[i].slopeTo(points[k]) == points[i].slopeTo(points[m])) {

							tmparray[0] = points[i];
							tmparray[1] = points[j];
							tmparray[2] = points[k];
							tmparray[3] = points[m];
							Arrays.sort(tmparray);
							segment = new LineSegment(tmparray[0], tmparray[3]);
							linesegments.add(segment);

						}

					}
				}
			}
		}
	}

}