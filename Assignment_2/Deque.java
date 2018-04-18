import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

	private Node first, last, node, old_first, old_last;
	private Item tmp_item;
	private int count;

	public Deque() // construct an empty deque
	{
		first = null;
		last = null;
		count = 0;
	}

	private class Node {
		private Node front, back;
		private Item item;
	}

	public boolean isEmpty() // is the deque empty?
	{
		return count == 0;
	}

	public int size() // return the number of items on the deque
	{
		return count;
	}

	public void addFirst(Item item) // add the item to the front
	{
		if(item == null)
			throw new java.lang.IllegalArgumentException();

		node = new Node();
		node.item = item;

		count++;

		if (first == null) {
			first = node;
			last = node;
		} else {

			old_first = first;
			node.back = old_first;
			old_first.front = node;
			first = node;
		}

	}

	public void addLast(Item item) // add the item to the end
	{
		if(item == null)
			throw new java.lang.IllegalArgumentException();

		node = new Node();
		node.item = item;

		count++;

		if (last == null) {
			last = first = node;
		} else {

			old_last = last;
			node.front = old_last;
			old_last.back = node;
			last = node;

		}

	}

	public Item removeFirst() // remove and return the item from the front
	{

		if (first == null) {
			throw new java.util.NoSuchElementException();
		}

		tmp_item = first.item;
		old_first = first;
		first = old_first.back;
		old_first = null;
		if (first != null) {
			first.front = null;
		}
		else
		{
			last = null;
			first = null;
		}
		count--;
		return tmp_item;

	}

	public Item removeLast() // remove and return the item from the end
	{

		if (last == null) {
			throw new java.util.NoSuchElementException();
		}
		
		tmp_item = last.item;
		old_last = last;
		last = old_last.front;
		old_last = null;
		if(last != null)
		{
			last.back = null;
		}
		else
		{
			last = null;
			first = null;
		}
		count--;
		return tmp_item;

	}
	
	
	public Iterator<Item> iterator() // return an iterator over items in order
										// from front to end
	{

		return new dequeIterator();
		
	}
	
	
	private class dequeIterator implements Iterator<Item>
	{
		private Node current = first;
		
		private Item item;

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return current != null;
		}

		@Override
		public Item next() {
			
			if ( current == null)
				throw new java.util.NoSuchElementException();
			
			item = current.item;
			
			current = current.back;
			
			return item;
			
		}

	}
	

	public static void main(String[] args) // unit testing (optional)
	{
 
	}
}
