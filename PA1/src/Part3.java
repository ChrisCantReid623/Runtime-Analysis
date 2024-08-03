
public class Part3 {
	/**
	 * Given an array of integers A and an integer m, determine the maximum product
	 * of m consecutive integers in the array.
	 */
	public static int maxProduct(Array a, int m) {
		int maxProduct = Integer.MIN_VALUE;

		for (int i = 0; i <= a.length() - m; i++) {
			int currentProduct = 1;
			for (int j = 0; j < m; j++) {
				currentProduct *= a.getVal(i + j);
			}
			maxProduct = Math.max(maxProduct, currentProduct);
		}
		return maxProduct;
	}
}
