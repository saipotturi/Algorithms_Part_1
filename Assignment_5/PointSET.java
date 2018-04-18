import java.util.ArrayList;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;


public class PointSET {
	
	private final TreeSet<Point2D> treeset;
	private ArrayList<Point2D> arraylist;
	
   public PointSET()                               // construct an empty set of points 
   {
	   treeset = new TreeSet<Point2D>();
	  
   }
   
   public boolean isEmpty()                      // is the set empty? 
   {
	   return (treeset.size() == 0);
   }
   
   public int size()                         // number of points in the set
   {
	   return treeset.size();
   }
   
   public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
	   treeset.add(p);
   }
   
   public boolean contains(Point2D p)            // does the set contain point p? 
   {
	   return (treeset.contains(p));
   }
   
   public void draw()                         // draw all points to standard draw 
   {
	   
   }
   
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
   {
	   arraylist = new ArrayList<Point2D>();
	   
	   for(Point2D point : treeset)
	   {
		   if(rect.contains(point))
			   arraylist.add(point);
	   }
	   
	   return arraylist;
   }
   
   public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
   {
	  // double current_min = p.distanceSquaredTo(treeset.first());
	   double current_min = 0;
	   Point2D minpoint = null;
	   
	   for(Point2D point : treeset)
	   {
		   if(p.distanceSquaredTo(point) < current_min)
		   {
			   current_min = p.distanceSquaredTo(point);
			   minpoint = point;
		   }
	   }
	   
	   return minpoint;
   }

   public static void main(String[] args)                  // unit testing of the methods (optional) 
   {
	   
   }
}
