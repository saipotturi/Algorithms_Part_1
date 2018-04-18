import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

	private Board twin, pred_twin, pred_org, tmp_twin, tmp_org;
	private int moves = 0, tmp_moves;
	private MinPQ<Node> org_pq, twin_pq;
	private boardComparator comparator;
	private Iterator<Board> org_neighbors, twin_neighbors;
	private ArrayList<Board> org_list, tmp_list;
	private ArrayList<Board> test_list;
	private Node node, twin_node, tmp_node, min_twin, min_org;
	private Stack<Board> stack;

	private class Node {
		Board board;
		Node pred_node;
		int moves;

		public Node(Board b, Node pred_node) {
			this.board = b;
			this.pred_node = pred_node;
		}

		private void setMoves(int moves) {
			this.moves = moves;
		}

		private int getMoves() {
			return moves;
		}
	}

	public Solver(Board initial) // find a solution to the initial board (using
									// the A* algorithm)
	{
		if(initial == null)
			throw new java.lang.IllegalArgumentException();

		twin = initial.twin();

		org_pq = new MinPQ<Node>(this.getComparator());
		twin_pq = new MinPQ<Node>(this.getComparator());

		node = new Node(initial, null);
		twin_node = new Node(twin, null);

		node.setMoves(0);
		twin_node.setMoves(0);

		org_pq.insert(node);
		twin_pq.insert(twin_node);

		org_list = new ArrayList<Board>();
		tmp_list = new ArrayList<Board>();
		
		min_org = org_pq.min();
		min_twin = twin_pq.min();
		
		org_pq.delMin();
		twin_pq.delMin();

		while ((!min_org.board.isGoal()) && (!min_twin.board.isGoal())) {

			moves++;
			
			org_list.add(min_org.board);
			
			org_neighbors = min_org.board.neighbors().iterator();

			while (org_neighbors.hasNext()) {
				tmp_org = org_neighbors.next();

				tmp_node = new Node(tmp_org, min_org);
				tmp_moves = getMoves(tmp_node);
				tmp_node.setMoves(tmp_moves);

				if (min_org.pred_node == null || !tmp_org.equals(min_org.pred_node.board)) {
					org_pq.insert(tmp_node);
				}

			}
			
			min_org = org_pq.min();
			
			//System.out.println("Manhattan : "+min_org.board.manhattan()+" Moves : "+min_org.getMoves());
			
			org_pq.delMin();
			
			tmp_list.add(min_twin.board);

			twin_neighbors = min_twin.board.neighbors().iterator();

			while (twin_neighbors.hasNext()) {

				tmp_twin = twin_neighbors.next();

				tmp_node = new Node(tmp_twin, min_twin);
				tmp_moves = getMoves(tmp_node);
				tmp_node.setMoves(tmp_moves);

				if (min_twin.pred_node == null || !tmp_twin.equals(min_twin.pred_node.board)) {
					twin_pq.insert(tmp_node);
				}

			}
			
			min_twin = twin_pq.min();
			
			twin_pq.delMin();

		}

		//org_pq.insert(min_org);
		//min_org = org_pq.min();
		org_list.add(min_org.board);
		//org_pq.delMin();

		//twin_pq.insert(min_twin);
		//min_twin = twin_pq.min();
		tmp_list.add(min_twin.board);
		//twin_pq.delMin();

	}

	public boolean isSolvable() // is the initial board solvable?
	{
		return min_org.board.isGoal();
	}

	public int moves() // min number of moves to solve initial board; -1 if
						// unsolvable
	{
		if(isSolvable())
			return (getMoves(min_org));
		else
			return -1;
	}
	
	private int getMoves(Node node)
	{
		int moves = 0;
		
		Node c_node = node;
		
		while(c_node.pred_node != null)
		{
			c_node = c_node.pred_node;
			moves++;
		}
		
		return moves;
	}

	public Iterable<Board> solution() // sequence of boards in a shortest
										// solution; null if unsolvable
	{
		if (isSolvable())
		{
			stack = new Stack<Board>();
			
			Node t_node = min_org;
			
			while(t_node.pred_node != null)
			{
				stack.push(t_node.board);
				t_node = t_node.pred_node;
			}
			
			stack.push(t_node.board);
			return stack;
		}
		else
			return null;
	}

	private class boardComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {

		/*	if ((o1.board.manhattan() + o1.getMoves()) < (o2.board.manhattan() + o2.getMoves()))
				return -1;
			if ((o1.board.manhattan() + o1.getMoves()) > (o2.board.manhattan() + o2.getMoves()))
				return 1;
			return 0;
			*/
			
			return (o1.board.manhattan() + getMoves(o1) - o2.board.manhattan() - getMoves(o2));

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
		int[][] test_array = { { 5, 6, 2 }, { 1, 8, 4 }, { 7, 3, 0 } };
		//int[][] test_array = {{ 1, 2, 3 }, { 4, 5, 6 }, { 8, 7, 0 }};
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