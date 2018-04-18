import java.util.Arrays;

public class Test {

	private final int P = 4, E = 7;

	public void fnctn(int pl, int a[]) {
		int start = pl;
		if (pl != 0)
			start = a[pl - 1] + 1;

		int end = E - (P - pl);
		int tmp[] = a;
		while (start <= end) {
			a[pl] = start;
			if (pl < P - 1) {
				fnctn(pl + 1, tmp);
			}

			System.out.println(Arrays.toString(tmp));

			start++;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Test test = new Test();

		int[] a = new int[4];

		double[] testd = { Double.NEGATIVE_INFINITY, 0, 7, 5, 9, 4 };

		Arrays.sort(testd);

		System.out.println(Arrays.toString(testd));

		for (int i = 0; i < 10; i++) {
			
			if(i==1)
			{
				continue;
			}
			
			System.out.println(i);

		}

		// test.fnctn(0, a);

	}

}
