public class DequeCompare {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);

        // Test Deque
        Deque<Integer> deque = new Deque<>();
        long startTime = System.nanoTime();
        for (int i = 0; i < N; i++) {
            deque.addLast(i);
        }
        long endTime = System.nanoTime();
        long durationDeque = endTime - startTime;

        // Test Deque1
        Deque1<Integer> deque1 = new Deque1<>();
        startTime = System.nanoTime();
        for (int i = 0; i < N; i++) {
            deque1.addLast(i);
        }
        endTime = System.nanoTime();
        long durationDeque1 = endTime - startTime;

        System.out.println("Time taken by Deque for " + N + " elements: " + durationDeque + " nanoseconds");
        System.out.println("Time taken by Deque1 for " + N + " elements: " + durationDeque1 + " nanoseconds");
    }
}