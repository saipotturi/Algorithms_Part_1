import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

import edu.princeton.cs.algs4.StdRandom;

public class Board {

	private final int[][] init_block; 
	private int[][] tmp_block;
	private int dimension, hamming, manhattan, tmp_i, tmp_j, tmp, priority;
	private Board tmp_board;
	private ArrayList<Board> arraylist = new ArrayList<Board>();

	public Board(int[][] blocks) // construct a board from an n-by-n array of
									// blocks (where blocks[i][j] = block in row
									// i, column j)
	{
		init_block = new int[blocks.length][blocks.length];
		//System.arraycopy(blocks, 0, init_block, 0, blocks.length);
		//init_block = blocks;
		for(int i=0; i<blocks.length; i++)
		{
			for(int j=0; j<blocks.length; j++)
			{
				init_block[i][j] = blocks[i][j];
			}
		}
		
		dimension = init_block.length;
		hamming = hamming();
		manhattan = manhattan();
		
	}

	public int dimension() // board dimension n
	{
		return dimension;
	}

	public int hamming() // number of blocks out of place
	{
		hamming = 0;
		
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if ((init_block[i][j] != (i * dimension) + (j + 1)) && ((i != dimension) && (j != dimension))
						&& (init_block[i][j] != 0))
					hamming++;
			}

		}

		return hamming;
	}

	public int manhattan() // sum of Manhattan distances between blocks and goal
	{
		manhattan = 0;
		
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {

				if (init_block[i][j] == 0)
					continue;

				if (init_block[i][j] % dimension == 0) {
					tmp_j = dimension - 1;
					tmp_i = (init_block[i][j] / dimension) - 1;
				} else {
					tmp_j = (init_block[i][j] % dimension) - 1;
					tmp_i = init_block[i][j] / dimension;
				}

				manhattan = manhattan + Math.abs(i - tmp_i) + Math.abs(j - tmp_j);
			}
		}

		return manhattan;
	}

	private void setPriority(int moves) {
		priority = hamming + moves;
	}

	private int getPriority() {
		return priority;
	}

	public boolean isGoal() // is this board the goal board?
	{
		return (hamming == 0);
	}

	public Board twin() // a board that is obtained by exchanging any pair of
						// blocks
	{
		tmp_i = 0;
		tmp_j = 0;
		
		tmp_block = new int[dimension][dimension];
		for(int k=0; k<dimension;k++)
		{
			for(int l=0; l<dimension;l++)
			{
				tmp_block[k][l] = init_block[k][l];
			}
		}

		
		while(true)
		{
			if(tmp_block[tmp_i][tmp_j] != 0)
			{
				break;
			}
			tmp_i = StdRandom.uniform(0, dimension);
			tmp_j = StdRandom.uniform(0, dimension);
		}		
		
		tmp = tmp_block[tmp_i][tmp_j];
		

		if ((tmp_i - 1) >= 0 && (tmp_block[tmp_i - 1][tmp_j] != 0)) {
			tmp_block[tmp_i][tmp_j] = tmp_block[tmp_i - 1][tmp_j];
			tmp_block[tmp_i - 1][tmp_j] = tmp;
			return new Board(tmp_block);
		}

		if ((tmp_i + 1) < dimension && (tmp_block[tmp_i + 1][tmp_j] != 0)) {
			tmp_block[tmp_i][tmp_j] = tmp_block[tmp_i + 1][tmp_j];
			tmp_block[tmp_i + 1][tmp_j] = tmp;
			return new Board(tmp_block);
		}

		if ((tmp_j - 1) >= 0 && (tmp_block[tmp_i][tmp_j - 1] != 0)) {
			tmp_block[tmp_i][tmp_j] = tmp_block[tmp_i][tmp_j - 1];
			tmp_block[tmp_i][tmp_j - 1] = tmp;
			return new Board(tmp_block);
		}

		if ((tmp_j + 1) >= 0 && (tmp_block[tmp_i][tmp_j + 1] != 0)) {
			tmp_block[tmp_i][tmp_j] = tmp_block[tmp_i][tmp_j + 1];
			tmp_block[tmp_i][tmp_j + 1] = tmp;
			return new Board(tmp_block);
		}

	
		return null;
		
		

	}

	public boolean equals(Object y) // does this board equal y?
	{
		if (y == null)
			return false;
		if(y == this)
			return true;
		
		if(y.getClass() != this.getClass())
			return false;
		
		Board that = (Board) y;
		
		if(this.dimension != that.dimension)
			return false;
		
		for(int i = 0; i<this.dimension; i++)
		{
			for(int j = 0; j<this.dimension; j++)
			{
				if(this.init_block[i][j] != that.init_block[i][j])
					return false;
			}
		}
		
		return true;
	}

	public Iterable<Board> neighbors() // all neighboring boards
	{
		
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (init_block[i][j] == 0) {
					if ((j - 1) >= 0) {
//						System.out.println("loop 1");
						tmp_block = new int[dimension][dimension];
						for(int k=0; k<dimension;k++)
						{
							for(int l=0; l<dimension;l++)
							{
								tmp_block[k][l] = init_block[k][l];
							}
						}
						//System.arraycopy(init_block, 0, tmp_block, 0, init_block.length);
						tmp_block[i][j] = tmp_block[i][j - 1];
						tmp_block[i][j - 1] = 0;
						tmp_board = new Board(tmp_block);
//						System.out.println("manhattan : "+tmp_board.manhattan());
						arraylist.add(tmp_board);

					}

					if ((j + 1) < dimension) {
//						System.out.println("loop 2");
						tmp_block = new int[dimension][dimension];
						for(int k=0; k<dimension;k++)
						{
							for(int l=0; l<dimension;l++)
							{
								tmp_block[k][l] = init_block[k][l];
							}
						}

						tmp_block[i][j] = tmp_block[i][j + 1];
						tmp_block[i][j + 1] = 0;
						tmp_board = new Board(tmp_block);
						arraylist.add(tmp_board);
					}

					if ((i - 1) >= 0) {
//						System.out.println("loop 3");
						tmp_block = new int[dimension][dimension];
						for(int k=0; k<dimension;k++)
						{
							for(int l=0; l<dimension;l++)
							{
								tmp_block[k][l] = init_block[k][l];
							}
						}

						tmp_block[i][j] = tmp_block[i - 1][j];
						tmp_block[i - 1][j] = 0;
						tmp_board = new Board(tmp_block);
//						System.out.println("manhattan : "+tmp_board.manhattan());
						arraylist.add(tmp_board);

					}

					if ((i + 1) < dimension) {
//						System.out.println("loop 4");
						tmp_block = new int[dimension][dimension];
						for(int k=0; k<dimension;k++)
						{
							for(int l=0; l<dimension;l++)
							{
								tmp_block[k][l] = init_block[k][l];
							}
						}

						tmp_block[i][j] = tmp_block[i + 1][j];
						tmp_block[i + 1][j] = 0;
						tmp_board = new Board(tmp_block);

						arraylist.add(tmp_board);
					}

				}
			}
		}

		return arraylist;
	}

	public String toString() // string representation of this board (in the
								// output format specified below)
	{
		StringBuilder s = new StringBuilder();
		s.append(dimension + "\n");
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				s.append(String.format("%2d ", init_block[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

}

