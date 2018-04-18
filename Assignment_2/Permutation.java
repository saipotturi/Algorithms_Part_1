import java.util.Iterator;

public class Permutation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int k = Integer.parseInt(args[0]);
		int tmp = 0;
		
		RandomizedQueue randq = new RandomizedQueue<String>();
		
		for(int i=1; i<args.length; i++)
		{
			randq.enqueue(args[i]);
		}
		
		Iterator<String> iterator = randq.iterator();
		
		while(iterator.hasNext())
		{
			if(tmp < k)
			{
				System.out.println(iterator.next());
				tmp++;
			}
			else
				break;
			
		}

	}

}
