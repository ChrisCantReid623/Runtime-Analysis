
public class Part4 {

	/*
	 * Problem Statement: Given an array of integers A, rearrange A so that all the
	 * 0’s are pushed to the end of the array. All other values should stay in the
	 * same relative order.
	 */
	public static void pushZeroes(Array a) {
		int lastNonZero = 0;
		
		for (int i = 0; i < a.length(); i++) {
			if (a.getVal(i) != 0) {
				int temp = a.getVal(lastNonZero);
		        a.setVal(lastNonZero, a.getVal(i)); 
		        a.setVal(i, temp);
				lastNonZero ++;
			}
		}
	}
}
