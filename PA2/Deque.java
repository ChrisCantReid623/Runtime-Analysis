public class Deque<T> {
	public static void main(String args[]) {
		System.out.println("Lectura compile test successful.");
	}

	private Array<T> array;

	private int size;
	private int frontIndex;
	private int backIndex;
	private static final int DEFAULT_CAPACITY = 16;

	public Deque() {
		this.array = new Array<T>(DEFAULT_CAPACITY);
		this.size = 0;
		this.frontIndex = 0;
		this.backIndex = DEFAULT_CAPACITY - 1;
	}

	public Deque(int cap) {
		this.array = new Array<T>(cap);
		this.size = 0;
		this.frontIndex = 0;
		this.backIndex = cap - 1;
	}

	public void addLast(T item) {
		if (size == array.length()) {
			resize(2 * array.length());
		}
		array.setVal(backIndex, item);
		backIndex = (backIndex + 1) % array.length();
		size++;
	}

	public void addFirst(T item) {
		if (size == array.length()) {
			resize(2 * array.length());
		}
		frontIndex = (frontIndex - 1 + array.length()) % array.length();
		array.setVal(frontIndex, item);
		size++;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public T removeFirst() throws EmptyDequeException {
		if (isEmpty()) {
			throw new EmptyDequeException();
		}
		T removed = array.getVal(frontIndex);
		array.setVal(frontIndex, null);
		frontIndex = (frontIndex + 1) % array.length();
		size--;
		if (size > 0 && size == array.length() / 4) {
			resize(array.length() / 2);
		}
		return removed;
	}

	public T removeLast() throws EmptyDequeException {
		if (isEmpty()) {
			throw new EmptyDequeException();
		}
		backIndex = (backIndex - 1 + array.length()) % array.length();
		T removed = array.getVal(backIndex);
		array.setVal(backIndex, null);
		size--;
		if (size > 0 && size == array.length() / 4) {
			resize(array.length() / 2);
		}
		return removed;
	}

	public T peekFirst() throws EmptyDequeException {
		if (isEmpty()) {
			throw new EmptyDequeException();
		}
		return array.getVal(frontIndex);
	}

	public T peekLast() throws EmptyDequeException {
		if (isEmpty()) {
			throw new EmptyDequeException();
		}
		return array.getVal((backIndex - 1 + array.length()) % array.length());
	}

	private void resize(int capacity) {
		Array<T> newArray = new Array<>(capacity);
		for (int i = 0; i < size; i++) {
			newArray.setVal(i, array.getVal((frontIndex + i) % array.length()));
		}
		array = newArray;
		frontIndex = 0;
		backIndex = size;
	}

	public int getAccessCount() {
		return array.getAccessCount();
	}

	public void resetAccessCount() {
		this.array.resetAccessCount();
	}

	public int size() {
		return this.size;
	}
}