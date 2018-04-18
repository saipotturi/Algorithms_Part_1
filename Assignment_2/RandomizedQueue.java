import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] s, resize;
	private int n, rand;
	private Item item;

	public RandomizedQueue() // construct an empty randomized queue
	{
		n = 0;
		s = (Item[]) new Object[1];

	}

	public boolean isEmpty() // is the randomized queue empty?
	{
		return (n == 0);
	}

	public int size() // return the number of items on the randomized queue
	{
		return n;
	}

	public void enqueue(Item item) // add the item
	{
		if(item == null)
			throw new java.lang.IllegalArgumentException();
		
		if (n == s.length)
			resizeArray(2 * n);
		s[n] = item;
		n++;
	}

	public Item dequeue() // remove and return a random item
	{
		if(n == 0)
			throw new java.util.NoSuchElementException();
		
		rand = StdRandom.uniform(n);
		item = s[rand];
		s[rand] = s[n-1];
		s[n-1] = null;
		n--;
		
		if ((n == s.length / 4))
			resizeArray(s.length/ 2);
		
		return item;
	}

	public Item sample() // return a random item (but do not remove it)
	{
		if(n == 0)
			throw new java.util.NoSuchElementException();
		
		rand = StdRandom.uniform(n);
		return s[rand];
	}

	public Iterator<Item> iterator() // return an independent iterator over
										// items in random order
	{
		return new randomizedIterator();
	}

	private class randomizedIterator implements Iterator<Item>
	{
		int[] int_array = new int[n];
		int current=0, tmp;
		
		public randomizedIterator()
		{
			for(int i = 0; i<n; i++)
			{
				int_array[i] = i;
			}
			
			StdRandom.shuffle(int_array);
		}

		@Override
		public boolean hasNext() {

			return (current < n);
		}

		@Override
		public Item next() {
			if(current >= n)
				throw new java.util.NoSuchElementException();
			
			tmp = int_array[current];
			current++;
			return s[tmp];
		}
		
	}

	private void resizeArray(int capacity) {
		
		if(capacity == 0)
			capacity = 1;
		resize = (Item[]) new Object[capacity];
		for (int i = 0; i < n; i++) {
			resize[i] = s[i];
		}
		s = resize;
		resize = null;
	}

	public static void main(String[] args) // unit testing (optional)
	{
		
	}
}