import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver2 {

	private Board twin, pred_twin, pred_org, min_twin, min_org, tmp_twin, tmp_org;
	private int moves = 0;
	private MinPQ<Board> org_pq, twin_pq;
	private boardComparator comparator;
	private Iterator<Board> org_neighbors, twin_neighbors;
	private ArrayList org_list, tmp_list;
	private ArrayList<Board> test_list;

	public Solver2(Board initial) // find a solution to the initial board (using
									// the A* algorithm)
	{

		twin = initial.twin();

		System.out.println("this is initial array");
		System.out.println(initial.toString());
		System.out.println("this is the twin");
		System.out.println(twin.toString());

		org_pq = new MinPQ(this.getComparator());
		twin_pq = new MinPQ(this.getComparator());

		org_pq.insert(initial);
		twin_pq.insert(twin);

		org_list = new ArrayList<Board>();
		tmp_list = new ArrayList<Board>();

		int dummy = 0;

		while ((!org_pq.min().isGoal()) && (!twin_pq.min().isGoal())) {

			moves++;
			
			min_org = org_pq.min();
			org_list.add(min_org);

			org_neighbors = min_org.neighbors().iterator();

			org_pq.delMin();

			while (org_neighbors.hasNext()) {
				tmp_org = org_neighbors.next();

				if (!tmp_org.equals(pred_org)) {
					
					org_pq.insert(tmp_org);
				}

			}

			pred_org = min_org;

			min_twin = twin_pq.min();
			tmp_list.add(min_twin);
			
			twin_neighbors = min_twin.neighbors().iterator();
			twin_pq.delMin();
			
			while (twin_neighbors.hasNext()) {
				
				tmp_twin = twin_neighbors.next();
				
				if (!tmp_twin.equals(pred_twin)) {
					twin_pq.insert(tmp_twin);
				}
				
			}

			pred_twin = min_twin;

		}

		min_org = org_pq.min();
		org_list.add(min_org);
		org_pq.delMin();

		min_twin = twin_pq.min();
		tmp_list.add(min_twin);
		twin_pq.delMin();

	}

	public boolean isSolvable() // is the initial board solvable?
	{
		return min_org.isGoal();
	}

	public int moves() // min number of moves to solve initial board; -1 if
						// unsolvable
	{
		return moves;
	}

	public Iterable<Board> solution() // sequence of boards in a shortest
										// solution; null if unsolvable
	{
		if (isSolvable())
			return org_list;
		else
			return null;
	}

	private class boardComparator implements Comparator<Board> {

		@Override
		public int compare(Board o1, Board o2) {

			if ((o1.manhattan() + moves) < (o2.manhattan() + moves))
				return -1;
			if ((o1.manhattan() + moves) > (o2.manhattan() + moves))
				return 1;
			return 0;

		}

	}

	private boardComparator getComparator() {
		if (comparator == null)
			comparator = new boardComparator();

		return comparator;

	}

	public static void main(String[] args) // solve a slider puzzle (given
											// below)
	{
		int[][] test_array = {{1, 0, 8}, {7, 2, 4}, {5, 6, 3}};
		Board initial = new Board(test_array);
		System.out.println(initial.toString());
		
		Solver solver = new Solver(initial);
		
		if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
	}
}