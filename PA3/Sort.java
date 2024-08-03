import java.util.Random;

public class Sort {
	/***************************************
	 * Public Methods
	 ***************************************/

	/* Sort Array A using an iterative Merge Sort algorithm. */
	public static void iterativeMerge(Array A) {
		// System.out.println("Testing: iterativeMerge");
		int length = A.length();
		Array extraArray = new Array(length);

		for (int subArraySize = 1; subArraySize < length; subArraySize = subArraySize + subArraySize) {
			for (int start = 0; start < length - subArraySize; start += subArraySize + subArraySize) {
				int mid = start + subArraySize - 1;
				int end = Math.min(start + subArraySize + subArraySize - 1, length - 1);
				mergeSubArrays(A, extraArray, start, mid, end);
			}
			// printArray(A);
		}
	}

	/*
	 * Sort Array A using a combination of Insertion Sort and Merge Sort. Do this
	 * iteratively. The algorithm should first sort sections (of size size) of the
	 * Array with insertion sort, then merge the sorted parts together
	 */
	public static void insertMerge(Array A, int size) {
		// System.out.println("Testing: insertMerge, Size = " + size);
		int length = A.length();
		// Apply Insertion Sort on sections of the given size
		for (int start = 0; start < length; start += size) {
			int end = Math.min(start + size, length);
			insertionSortSelection(A, start, Math.max(0, end - 1));
		}

		// Auxiliary array for merging
		Array extraArray = new Array(length);

		// Iteratively merge sections, doubling the section size each time
		for (int mergeSize = size; mergeSize < length; mergeSize *= 2) {
			for (int start = 0; start < length - mergeSize; start += mergeSize * 2) {
				int mid = start + mergeSize - 1;
				int rightEnd = Math.min(start + mergeSize * 2 - 1, length - 1);
				mergeSubArrays(A, extraArray, start, mid, rightEnd);
			}
		}

		// Ensure the final merge step covers the entire array
		if (size < length) {
			for (int leftStart = 0; leftStart < length; leftStart += size * 2) {
				int mid = leftStart + size - 1;
				int rightEnd = Math.min(leftStart + size * 2 - 1, length - 1);
				// Check if the mid point exceeds array bounds
				if (mid >= length)
					break;
				mergeSubArrays(A, extraArray, leftStart, Math.min(mid, length - 1), rightEnd);
			}
		}
	}

	/* Sort Array A using a recursive 3-way Merge Sort algorithm */
	public static void threeWayMerge(Array A) {
		// System.out.println("Testing: threeWayMerge");
		// printArray(A, "Starting Three-Way Merge Sort:");
		threeWayMerge(A, 0, A.length() - 1, new Array(A.length()));
		// printArray(A, "Three-Way Merge Sort Completed:");
	}

	/* Sort Array A using a five-way QuickSort */
	public static void fiveWayQuick(Array A) {
		// System.out.println("Testing: fiveWayQuick");
		fiveWayQuickSort(A, 0, A.length() - 1);
	}

	/* Sort Array A using a locality-aware version of Selection Sort */
	public static void locSelect(Array A, int d) {
		// System.out.println("Testing: locSelect");
		// Iterate through each element in the array
		for (int i = 0; i < A.length(); i++) {
			// Initialize the position for the minimum element as the current index
			int minPos = i;
			// Check up to 'd' positions ahead for a smaller element
			for (int j = i + 1; j <= Math.min(i + d, A.length() - 1); j++) {
				// If a smaller element is found, update minPos
				if (A.getVal(j) < A.getVal(minPos)) {
					minPos = j;
				}
			}
			// Swap the current element with the found minimum element
			if (minPos != i) {
				A.swap(i, minPos);
				// printArray(A, "After swapping index " + i + " with index " + minPos);
			}
		}
	}

	// Sorts the array using a locality-aware version of HeapSort.
	public static void locHeap(Array A, int d) {
		int n = A.length();

		// Build a heap for the first (d+1) elements.
		for (int i = d / 2; i >= 0; i--) {
			minHeapify(A, i, Math.min(d + 1, n), n);
		}

		// Process the array from the start to the end minus the window size
		for (int i = d + 1; i < n; i++) {
			// Place the root at the start of the sorted section
			swap(A, 0, i - (d + 1));

			// Heapify the new root element to maintain heap properties
			minHeapify(A, 0, d + 1, Math.min(i + 1, n));
		}

		// Final pass to sort the remaining elements
		for (int i = n - (d + 1); i < n; i++) {
			swap(A, 0, n - (i - (n - (d + 1)) + 1));
			minHeapify(A, 0, d + 1, n - (i - (n - (d + 1)) + 1));
		}
	}

	/* Sort the LinkedList using a recursive version of Merge Sort */
	public static LinkedList mergeSort(LinkedList list) {
		// Base cases: list is empty or has only one element
		if (list.isEmpty() || list.head().next() == null) {
			return list;
		}
		// Split list into two halves
		LinkedList[] halves = splitList(list);
		LinkedList left = halves[0];
		LinkedList right = halves[1];

		// Recursively sort both halves and capture the sorted sublists
		LinkedList sortedLeft = mergeSort(left);
		LinkedList sortedRight = mergeSort(right);

		// Merge the sorted halves and return the merged sorted list
		return mergeSortedLists(sortedLeft, sortedRight);
	}

	/* Sort the LinkedList using a recursive version of 2-way QuickSort */
	public static LinkedList quickSort(LinkedList list) {
		// Base case: list is empty or has only one element
		if (list == null || list.isEmpty() || list.tail() == null || list.tail().isEmpty()) {
			return list;
		}
		// Choosing the first element as the pivot
		int pivot = list.head().val();

		// Partitioning
		LinkedList less = new LinkedList(); // Elements less than pivot
		LinkedList greater = new LinkedList(); // Elements greater than or equal to pivot
		Node current = list.head().next(); // Start with the second element

		while (current != null) {
			if (current.val() < pivot) {
				less.add(current.val());
			} else {
				greater.add(current.val());
			}
			current = current.next();
		}

		// Recursively sort both partitions
		LinkedList sortedLess = quickSort(less);
		LinkedList sortedGreater = quickSort(greater);

		// Merge sorted partitions and pivot
		return mergeQuickSortLists(sortedLess, pivot, sortedGreater);
	}

	/* Sort the list using a recursive version of Insertion Sort */
	public static LinkedList insertionSort(LinkedList list) {
		if (list.isEmpty() || list.head().next() == null) {
			return list; // Base case: empty list or a single element list is already sorted.
		}

		// Recursively sort the rest of the list excluding the head
		LinkedList sortedTail = insertionSort(list.tail());

		// Insert the head element into the sorted list
		return insert(list.head().val(), sortedTail);
	}

	// Entry method for the recursive bubble sort
	public static LinkedList bubbleSort(LinkedList list) {
		if (list.isEmpty() || list.head().next() == null) {
			return list; // No sorting needed for empty or single-element lists.
		}

		for (int i = 0; i < sizeOfList(list); i++) {
			list = bubble(list);
		}
		return list;
	}

	/***************************************
	 * Utility Methods
	 ***************************************/

	/* Clones a Linked List */
	public static LinkedList copyLinkedList(LinkedList original) {
		LinkedList copy = new LinkedList();

		// Iterate through the original list and copy each node
		Node current = original.head();
		Node copyCurrent = null;
		Node copyPrev = null;

		while (current != null) {
			Node newNode = new Node(current.val());
			if (copy.head() == null) {
				copy = new LinkedList(newNode);
			} else {
				copyPrev.setNext(newNode);
			}
			copyPrev = newNode;

			if (current == original.head()) {
				copyCurrent = copy.head();
			}

			current = current.next();
		}

		return copy;
	}

	/* Merge Function */
	private static void mergeSubArrays(Array mainArray, Array extraArray, int start, int mid, int end) {
		// Copy to auxiliary array
		for (int k = start; k <= end; k++) {
			extraArray.setVal(k, mainArray.getVal(k));
		}

		int i = start;
		int j = mid + 1;

		for (int k = start; k <= end; k++) {
			if (i > mid) {
				mainArray.setVal(k, extraArray.getVal(j++));
			} else if (j > end) {
				mainArray.setVal(k, extraArray.getVal(i++));
			} else if (extraArray.getVal(j) < extraArray.getVal(i)) {
				mainArray.setVal(k, extraArray.getVal(j++));
			} else {
				mainArray.setVal(k, extraArray.getVal(i++));
			}
		}
	}

	/* insertMerge helper function */
	private static void insertionSortSelection(Array mainArray, int start, int end) {
		for (int i = start + 1; i <= end; i++) {
			int value = mainArray.getVal(i);
			int j = i - 1;

			while (j >= start && mainArray.getVal(j) > value) {
				mainArray.setVal(j + 1, mainArray.getVal(j));
				j = j - 1;
			}
			mainArray.setVal(j + 1, value);
		}
	}

	/* Recursive threeWayMerge() signature */
	private static void threeWayMerge(Array mainArray, int low, int high, Array copyArray) {
		if (low < high) {
			int third1 = (high - low) / 3;
			int third2 = low + third1;
			int third3 = low + (2 * third1);

			// System.out.println("\nSorting section from " + low + " to " + third2);
			threeWayMerge(mainArray, low, third2, copyArray);
			// System.out.println("\nSorting section from " + (third2 + 1) + " to " +
			// third3);
			threeWayMerge(mainArray, third2 + 1, third3, copyArray);
			// System.out.println("\nSorting section from " + (third3 + 1) + " to " + high);
			threeWayMerge(mainArray, third3 + 1, high, copyArray);

			int a = low;
			int b = third2 + 1;
			int c = third3 + 1;
			int insert = low;

			// Merge the sorted thirds
			while (a <= third2 && b <= third3 && c <= high) {
				if (mainArray.getVal(a) <= mainArray.getVal(b) && mainArray.getVal(a) <= mainArray.getVal(c)) {
					copyArray.setVal(insert++, mainArray.getVal(a++));
				} else if (mainArray.getVal(b) <= mainArray.getVal(a) && mainArray.getVal(b) <= mainArray.getVal(c)) {
					copyArray.setVal(insert++, mainArray.getVal(b++));
				} else {
					copyArray.setVal(insert++, mainArray.getVal(c++));
				}
			}

			// Handle remaining elements for first and second thirds
			while (a <= third2 && b <= third3) {
				copyArray.setVal(insert++,
						mainArray.getVal(a) <= mainArray.getVal(b) ? mainArray.getVal(a++) : mainArray.getVal(b++));
			}

			// Handle remaining elements for second and third thirds
			while (b <= third3 && c <= high) {
				copyArray.setVal(insert++,
						mainArray.getVal(b) <= mainArray.getVal(c) ? mainArray.getVal(b++) : mainArray.getVal(c++));
			}

			// Handle remaining elements for first and third thirds
			while (a <= third2 && c <= high) {
				copyArray.setVal(insert++,
						mainArray.getVal(a) <= mainArray.getVal(c) ? mainArray.getVal(a++) : mainArray.getVal(c++));
			}

			// Copy any leftovers from each third
			while (a <= third2)
				copyArray.setVal(insert++, mainArray.getVal(a++));
			while (b <= third3)
				copyArray.setVal(insert++, mainArray.getVal(b++));
			while (c <= high)
				copyArray.setVal(insert++, mainArray.getVal(c++));

			// Copy back the merged elements from the copy array to the original array
			for (int i = low; i <= high; i++) {
				mainArray.setVal(i, copyArray.getVal(i));
			}
			// System.out.println("After merging from " + low + " to " + high + ":");
			// printArray(A, "Array state");
		}

	}

	/* Recursive fiveWayQuick() signature */
	private static void fiveWayQuickSort(Array mainArray, int low, int high) {
		if (low < high) {
			// printArray(A, "Initial array state before sorting between " + low + " and " +
			// high + ":");

			if ((mainArray.getVal(low) > mainArray.getVal(high))) {
				mainArray.swap(low, high);
				// printArray(A, "Swapped pivots because pivot at low was greater than pivot at
				// high.");
			}
			int pivot1 = mainArray.getVal(low);
			int pivot2 = mainArray.getVal(high);

			// Split Indices
			int less = low + 1;
			int greater = high - 1;

			int i = less;
			while (i <= greater) {
				if (mainArray.getVal(i) < pivot1) {
					mainArray.swap(i++, less++);
					// printArray(A, "Swapped for < pivot1:");
				} else if (mainArray.getVal(i) > pivot2) {
					mainArray.swap(i, greater--);
					// printArray(A, "Swapped for > pivot2:");
				} else {
					i++;
				}
			}

			// Move pivots to their correct positions
			mainArray.swap(low, --less);
			mainArray.swap(high, ++greater);

			// printArray(A, "After swapping pivots into their correct positions:");

			// Recursively sort partitions
			fiveWayQuickSort(mainArray, low, less - 1); // Elements < pivot 1
			fiveWayQuickSort(mainArray, less + 1, greater - 1); // pivot 1 > Elements < pivot 2
			fiveWayQuickSort(mainArray, greater + 1, high); // Elements > pivot 2
		}

	}

	// Maintains the min-heap property within the heap boundaries.
	private static void minHeapify(Array A, int i, int size, int n) {
		int smallest = i;
		int left = 2 * i + 1;
		int right = 2 * i + 2;

		if (left < size && A.getVal(left) < A.getVal(smallest)) {
			smallest = left;
		}
		if (right < size && A.getVal(right) < A.getVal(smallest)) {
			smallest = right;
		}
		if (smallest != i) {
			swap(A, i, smallest);
			minHeapify(A, smallest, size, n);
		}
	}

	/* Split given linked list into two halves and return as array of LinkedLists */
	private static LinkedList[] splitList(LinkedList list) {
		if (list.isEmpty() || list.head().next() == null) {
			return new LinkedList[] { list, new LinkedList() };
		}

		Node slow = list.head();
		Node fast = list.head().next(); // Start fast one step ahead for an even split on even number lists

		while (fast != null && fast.next() != null) {
			slow = slow.next();
			fast = fast.next().next();
		}

		Node secondHalfHead = slow.next();
		slow.setNext(null); // Split the list

		LinkedList left = new LinkedList(list.head());
		LinkedList right = new LinkedList(secondHalfHead);
		return new LinkedList[] { left, right };
	}

	/* Merge two sorted lists. Return new LinkedList */
	private static LinkedList mergeSortedLists(LinkedList left, LinkedList right) {
		Node dummyHead = new Node(0); // Temporary starter of the result list
		Node current = dummyHead; // The last node of the result list
		Node l = left.head();
		Node r = right.head();

		while (l != null && r != null) {
			if (l.val() <= r.val()) {
				current.setNext(l);
				l = l.next();
			} else {
				current.setNext(r);
				r = r.next();
			}
			current = current.next();
		}

		// Attach the remaining elements
		if (l != null) {
			current.setNext(l);
		} else if (r != null) {
			current.setNext(r);
		}

		current.setNext(l != null ? l : r);
		return new LinkedList(dummyHead.next());
	}

	/* Merge two sorted lists and a pivot. Return new LinkedList */
	private static LinkedList mergeQuickSortLists(LinkedList sortedLess, int pivot, LinkedList sortedGreater) {
		LinkedList result = new LinkedList();

		// Add elements from 'less'
		Node current = sortedLess.head();
		while (current != null) {
			result.add(current.val());
			current = current.next();
		}

		// Add pivot
		result.add(pivot);

		// Add elements from 'greater'
		current = sortedGreater.head();
		while (current != null) {
			result.add(current.val());
			current = current.next();
		}

		// Reverse the list to maintain order since we've been adding to the head
		return reverseList(result);
	}

	/* Reverse a LinkedList */
	private static LinkedList reverseList(LinkedList list) {
		LinkedList reversed = new LinkedList();
		Node current = list.head();
		while (current != null) {
			Node next = current.next();
			current.setNext(reversed.head());
			reversed = new LinkedList(current);
			current = next;
		}
		return reversed;
	}

	/* Insert a new element into a sorted list, either iteratively or recursively */
	private static LinkedList insert(int value, LinkedList sortedList) {
		if (sortedList.isEmpty() || value <= sortedList.head().val()) {
			// Insert at the beginning if the sorted list is empty or value is less than the
			// head of the list
			sortedList.add(value);
			return sortedList;
		} else {
			// Recursively find the correct spot for the value
			Node current = sortedList.head();
			LinkedList rest = new LinkedList(current.next());
			LinkedList insertedRest = insert(value, rest);
			current.setNext(insertedRest.head());
			return sortedList;
		}
	}

	// Recursive bubble processing
	private static LinkedList bubble(LinkedList list) {
		if (list.isEmpty() || list.head().next() == null) {
			return list; // Base case: empty list or single element.
		}

		Node a = list.head();
		LinkedList tail = list.tail();
		if (tail.isEmpty()) {
			return list; // Base case if tail is empty.
		}

		Node b = tail.head();

		if (a.val() <= b.val()) {
			// Correct order: Keep 'a', sort the rest, and add 'a' back.
			LinkedList sortedTail = bubble(tail);
			sortedTail.add(a.val());
			return sortedTail;
		} else {
			// Incorrect order: Place 'b' before 'a', sort the tail, and add 'b'.
			LinkedList newTail = tail.tail();
			newTail.add(a.val()); // Add 'a' to the remaining list.
			LinkedList sortedNewTail = bubble(newTail);
			sortedNewTail.add(b.val());
			return sortedNewTail;
		}
	}

	// Utility method to determine the size of the LinkedList
	private static int sizeOfList(LinkedList list) {
		int count = 0;
		Node current = list.head();
		while (current != null) {
			count++;
			current = current.next();
		}
		return count;
	}

	// Swaps two elements in the array.
	private static void swap(Array A, int i, int j) {
		int temp = A.getVal(i);
		A.setVal(i, A.getVal(j));
		A.setVal(j, temp);
	}

	/***************************************
	 * Testing Functions
	 ***************************************/

	private class LinkedListGenerator {

		private static LinkedList generateRandomLinkedList(int size, int maxValue) {
			Random rand = new Random();
			LinkedList list = new LinkedList();

			for (int i = 0; i < size; i++) {
				int randomVal = rand.nextInt(maxValue + 1);
				list.add(randomVal);
			}
			return list;
		}

		private static void printLinkedList(LinkedList list, String msg) {
			System.out.print(msg + ": ");
			Node current = list.head();
			while (current != null) {
				System.out.print(current.val() + " -> ");
				current = current.next();
			}
			System.out.println("null");
		}
	}

	private static void printArray(Array array, String msg) {
		System.out.print(msg + ": ");
		for (int i = 0; i < array.length(); i++) {
			System.out.print(array.getVal(i) + " ");
		}
		System.out.println();
	}

	private static void test1() {
		System.out.println("TEST 1");
		int[] randNums = ArrayGen.getRand(10, 10);
		Array testArray = new Array(randNums.length);
		for (int i = 0; i < randNums.length; i++) {
			testArray.setVal(i, randNums[i]);
		}

		printArray(testArray, "Original array");
		System.out.println();
		int size = 2; // adjust as necessary

		// Algorithm Functions
		// -------------------
		iterativeMerge(testArray);
		insertMerge(testArray, size);
		threeWayMerge(testArray);
		fiveWayQuick(testArray);

		printArray(testArray, "Sorted array");
		System.out.println("Access Count: " + testArray.getAccessCount() + "\n");
	}

	private static void test2() {
		System.out.println("TEST 2");
		int[][] testCases = { ArrayGen.getRand(5, 10) };

		for (int[] testCase : testCases) {
			Array testArray = new Array(testCase.length);
			for (int i = 0; i < testCase.length; i++) {
				testArray.setVal(i, testCase[i]);
			}

			printArray(testArray, "Original array");
			System.out.println();
			int maxDisplacement = 10; // adjust as necessary
			System.out.println("maxDisplacement: " + maxDisplacement);

			// Algorithm Functions
			// -------------------
			locSelect(testArray, maxDisplacement);
			locHeap(testArray, maxDisplacement);

			printArray(testArray, "\nSorted array");
			System.out.println("Access Count: " + testArray.getAccessCount() + "\n");
		}
	}

	private static void test3() {
		System.out.println("TEST 3");
		LinkedList randomList = LinkedListGenerator.generateRandomLinkedList(10, 99);
		LinkedListGenerator.printLinkedList(randomList, "Original Linked List");

		// Algorithm Functions
		// -------------------
		// LinkedList bubbleSorted = bubbleSort(copyLinkedList(randomList));
		// LinkedList sortedList = mergeSort(randomList);
		// LinkedList sortedList = quickSort(randomList);
		// LinkedList sortedList = insertionSort(randomList);

		// LinkedListGenerator.printLinkedList(mergeSort(randomList), "Sorted Linked
		// List");
		// LinkedListGenerator.printLinkedList(quickSort(randomList), "Sorted Linked
		// List");
		// LinkedListGenerator.printLinkedList(insertionSort(randomList), "Sorted Linked
		// List");
		// LinkedListGenerator.printLinkedList(bubbleSorted, "Bubble Sorted Linked
		// List");
	}

	public static void main(String[] args) {
		System.out.println("Lectura compile test successful.");
		// test1();
		// test2();
		// test3();
	}
}