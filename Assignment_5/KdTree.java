import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {

	private int level, size;
	private Node root, current, tmp_node, nearest_node;;
	private RectHV recthv;
	private double current_min, new_distance;
	private ArrayList<Point2D> arraylist;

	public KdTree() // construct an empty set of points
	{
		size = 0;
	}

	public boolean isEmpty() // is the set empty?
	{
		return (size == 0);
	}

	public int size() // number of points in the set
	{
		return size;
	}

	public void insert(Point2D p) // add the point to the set (if it is not
									// already in the set)
	{
		level = 0;
		size++;

		if (root == null) {
			root = new Node();
			root.p = p;
			recthv = new RectHV(0, 0, 1, 1);
			root.rect = recthv;
		} else {
			tmp_node = new Node();
			tmp_node.p = p;

			iter_down(root, p);

		}
		
		
	}

	public boolean contains(Point2D p) // does the set contain point p?
	{
		tmp_node = root;

		level = 1;

		while (tmp_node != null) {
			if (tmp_node.p.toString().equals(p.toString()))
				return true;
			else if ((level % 2) == 1) {
				if (p.x() < tmp_node.p.x())
					tmp_node = tmp_node.lb;
				else
					tmp_node = tmp_node.rt;
			} else {
				if (p.y() < tmp_node.p.y())
					tmp_node = tmp_node.lb;
				else
					tmp_node = tmp_node.rt;
			}
			level++;
		}

		return false;
	}

	public void draw() // draw all points to standard draw
	{

	}

	public Iterable<Point2D> range(RectHV rect) // all points that are inside
												// the rectangle (or on the
												// boundary)
	{
		level = 0;

		arraylist = new ArrayList<Point2D>();

		points_contained(rect, root, level);

		return arraylist;

	}

	public Point2D nearest(Point2D p) // a nearest neighbor in the set to point
										// p; null if the set is empty
	{
		level = 0;
		current_min = root.p.distanceSquaredTo(p);;
		nearest_node = root;
	
		getNearest(root, p, level);
		
		return nearest_node.p;
	}

	private static class Node {

		private Point2D p; // the point
		private RectHV rect; // the axis-aligned rectangle corresponding to this
								// node
		private Node lb; // the left/bottom subtree
		private Node rt; // the right/top subtree

	}

	private void getNearest(Node node, Point2D p, int level) {

		level++;
		
		//System.out.println("level : "+level+" point : "+node.p.toString());
		
		//System.out.println("node : "+node.p.toString()+"  min_distance : "+current_min+"  current_distance : "+node.p.distanceSquaredTo(p));

		if (node.p.distanceSquaredTo(p) < current_min) {
			current_min = node.p.distanceSquaredTo(p);
			nearest_node = node;
			//System.out.println("nearest_node : "+nearest_node.p.toString()+"  min_distance : "+current_min);
		}
	

		if ((level % 2) == 1) {
			if (p.x() < node.p.x()) {
				if (node.lb != null) {
					getNearest(node.lb, p, level);
				}
				if((node.rt != null) && current_min > node.rt.rect.distanceSquaredTo(p))
				{
					getNearest(node.rt, p, level);
				}
					//if (node == nearest_node)
						//getNearest(node.rt, p, level);
			} 
			
			else {
				if (node.rt != null)
				{
					getNearest(node.rt, p, level);
				}
				if((node.lb != null) && current_min > node.lb.rect.distanceSquaredTo(p))
				{
					getNearest(node.lb, p, level);
				}
					//if (node == nearest_node)
					//	getNearest(node.lb, p, level);
				
			}
		} 
		
		else {
			if (p.y() < node.p.y()) {
				if (node.lb != null)
				{
					getNearest(node.lb, p, level);
				}
				if((node.rt != null) && current_min > node.rt.rect.distanceSquaredTo(p))
				{
						getNearest(node.rt, p, level);
				}
					//if (node == nearest_node)
					//	getNearest(node.rt, p, level);
				
			} 
			
			else {
				if (node.rt != null)
				{
					getNearest(node.rt, p, level);
				}
			    if((node.lb != null) && current_min > node.lb.rect.distanceSquaredTo(p))
			    {
			    		getNearest(node.lb, p, level);
			    }
					//if (node == nearest_node)
					//	getNearest(node.lb, p, level);
			}
		}
		
	}

	private void points_contained(RectHV query_rect, Node node, int level) {
		
	/*	
		level++;
		
		if (query_rect.contains(node.p)) {
			arraylist.add(node.p);
		}

		if (((level % 2) == 1) && (query_rect.xmin() < node.p.x()) && (node.p.x() < query_rect.xmax())) {
			if (node.lb != null)
				points_contained(query_rect, node.lb, level);
			if (node.rt != null)
				points_contained(query_rect, node.rt, level);
		}

		else if (((level % 2) == 0) && (query_rect.ymin() < node.p.y()) && (node.p.y() < query_rect.ymax())) {
			if (node.lb != null)
				points_contained(query_rect, node.lb, level);
			if (node.rt != null)
				points_contained(query_rect, node.rt, level);
		}

		else if ((level % 2) == 1) {
			if (query_rect.xmax() < node.p.x()) {
				if (node.lb != null)
					points_contained(query_rect, node.lb, level);
			} else if(query_rect.xmin() > node.p.x()) {
				if (node.rt != null)
					points_contained(query_rect, node.rt, level);
			}
		}

		else if ((level % 2) == 0) {
			if (query_rect.ymax() < node.p.y()) {
				if (node.lb != null)
					points_contained(query_rect, node.lb, level);
			} else if (query_rect.ymin() > node.p.y()) {
				if (node.rt != null)
					points_contained(query_rect, node.rt, level);
			}
		}
		
		*/
		
		//System.out.println("node : "+node.p.toString());
		//System.out.println("query_rect : "+query_rect.toString());
		
		level++;
		
		if (query_rect.contains(node.p)) {
			arraylist.add(node.p);
		}

		if ((level % 2) == 1) {
			if (query_rect.xmax() < node.p.x()) {
				if (node.lb != null)
					points_contained(query_rect, node.lb, level);
			} else if (query_rect.xmin() > node.p.x()){
				if (node.rt != null)
					points_contained(query_rect, node.rt, level);
			}
			else{
				if (node.lb != null)
					points_contained(query_rect, node.lb, level);
				if (node.rt != null)
					points_contained(query_rect, node.rt, level);
			}
		}

		else if ((level % 2) == 0) {
			if (query_rect.ymax() < node.p.y()) {
				if (node.lb != null)
					points_contained(query_rect, node.lb, level);
			} else if (query_rect.ymin() > node.p.y()) {
				if (node.rt != null)
					points_contained(query_rect, node.rt, level);
			}
			else{
				if (node.lb != null)
					points_contained(query_rect, node.lb, level);
				if (node.rt != null)
					points_contained(query_rect, node.rt, level);
			}
		}
		
	}

	private void iter_down(Node node, Point2D point) {
		level++;

		if ((level % 2) == 1) {
			if (point.x() < node.p.x()) {
				if (node.lb != null) {
					//System.out.println("Level : "+level+" point : "+point.toString());
					iter_down(node.lb, point);
				} else {
					tmp_node = new Node();
					tmp_node.p = point;
					tmp_node.lb = null;
					tmp_node.rt = null;
					tmp_node.rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.p.x(), node.rect.ymax());
					node.lb = tmp_node;
					//System.out.println("Level : "+level+" point : "+point.toString());
				}
			}

			else {
				if (node.rt != null) {
					//System.out.println("Level : "+level+" point : "+point.toString());
					iter_down(node.rt, point);
				} else {
					tmp_node = new Node();
					tmp_node.p = point;
					tmp_node.lb = null;
					tmp_node.rt = null;
					tmp_node.rect = new RectHV(node.p.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
					node.rt = tmp_node;
					//System.out.println("Level : "+level+" point : "+point.toString());
				}
			}

		} else {
			if (point.y() < node.p.y()) {
				if (node.lb != null) {
					//System.out.println("Level : "+level+" point : "+point.toString());
					iter_down(node.lb, point);
				} else {
					tmp_node = new Node();
					tmp_node.p = point;
					tmp_node.lb = null;
					tmp_node.rt = null;
					tmp_node.rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.p.y());
					node.lb = tmp_node;
					//System.out.println("Level : "+level+" point : "+point.toString());
				}
			}

			else {
				if (node.rt != null) {
					//System.out.println("Level : "+level+" point : "+point.toString());
					iter_down(node.rt, point);
				} else {
					tmp_node = new Node();
					tmp_node.p = point;
					tmp_node.lb = null;
					tmp_node.rt = null;
					tmp_node.rect = new RectHV(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.rect.ymax());
					node.rt = tmp_node;
					//System.out.println("Level : "+level+" point : "+point.toString());
				}
			}
		}
	}

	public static void main(String[] args) // unit testing of the methods
											// (optional)
	{
		/*
		KdTree kdtree = new KdTree();
		Point2D point = new Point2D(0.8, 0.9);
		kdtree.insert(point);
		point = new Point2D(0.7,0.6);
		kdtree.insert(point);
		point = new Point2D(0.5,0.4);
		kdtree.insert(point);
		if(kdtree.contains(point))
			System.out.println("it contains "+point.toString());
		RectHV recthv = new RectHV(0.3,0.3,0.75,0.65);
		ArrayList<Point2D> arraylist = (ArrayList<Point2D>) kdtree.range(recthv);
		
		for(Point2D tmp_point : arraylist)
		{
			System.out.println(tmp_point.toString());
		}
		
		point = new Point2D(0.9,0.8);
		Point2D tmp_point = kdtree.nearest(point);
		System.out.println(tmp_point.toString());
		
		*/
		
		KdTree kdtree = new KdTree();
		Point2D point = new Point2D(0.75, 0.75);
		kdtree.insert(point);
		point = new Point2D(0.5,0.0);
		kdtree.insert(point);
		point = new Point2D(0.5,0.25);
		kdtree.insert(point);
		point = new Point2D(0.5,0.0);
		kdtree.insert(point);
		point = new Point2D(0.25,0.75);
		kdtree.insert(point);
		point = new Point2D(0.5,1.0);
		kdtree.insert(point);
		point = new Point2D(0.0,0.75);
		kdtree.insert(point);
		point = new Point2D(0.75,0.75);
		kdtree.insert(point);
		point = new Point2D(0.0,0.25);
		kdtree.insert(point);
		point = new Point2D(0.5,1.0);
		kdtree.insert(point);
		
	//	point = new Point2D(0.3,1.0);
	//	Point2D tmp_point = kdtree.nearest(point);
		
	//	System.out.println("Nearest Point :"+tmp_point.toString());
		
		RectHV recthv = new RectHV(0.6875,0.8125,0.6875,0.8125);
		
		ArrayList<Point2D> arraylist = (ArrayList<Point2D>) kdtree.range(recthv);
		
		for(Point2D tmp_points : arraylist)
		{
			System.out.println(tmp_points.toString());
		}
		

		
		//point = new Point2D(0.875, 0.125);
		//System.out.println(kdtree.contains(point));
	}

}