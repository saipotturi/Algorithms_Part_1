import java.util.Arrays;

public class Testdup {
	
	private final int P = 4, E = 7;
	
	public void fnctn(int pl, int a[])
	{
		int start = pl, end = E - (P - pl);
		int tmp[] = a;
		while(start <= end)
		{
			a[pl] = start;
			if(pl < P)
			{
				fnctn(pl+1,tmp);
			}
			System.out.println(Arrays.toString(tmp));
			start++;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Test test = new Test();
		
		int[] a = new int[4];
		
		test.fnctn(1, a);
		
		

	}

}
