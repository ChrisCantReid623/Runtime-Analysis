public class PatientQueue {
	private Patient[] heap;
	private int size;
	private PHashtable nameTable;
	private final static int START_CAPACITY = 11;

	public PatientQueue() {
		this(START_CAPACITY);
	}

	public PatientQueue(int cap) {
		int capacity = Math.max(cap, START_CAPACITY);
		heap = new Patient[capacity];
		size = 0;
		this.nameTable = new PHashtable(capacity);
	}

	public void insert(Patient newPatient) {
		if (size == heap.length - 1) {
			resize(heap.length * 2);
		}
		heap[++size] = newPatient;
		newPatient.setIndex(size);
		newPatient.setPositionInQueue(size);
		nameTable.put(newPatient);
		heapifyUp(size);
		resizeCheck();
	}

	public Patient removeNext() throws EmptyQueueException {
	    if (size == 0) {
	        throw new EmptyQueueException();
	    }

	    Patient removedPatient = heap[1];
	    heap[1] = heap[size];
	    heap[size] = null;
	    size--;

	    if (size != 0) { 
	        heap[1].setIndex(1); 
	    }

	    heapifyDown(1);
	    resizeCheck();

	    return removedPatient;
	}

	public Patient remove(String name) {
	    Patient patientToRemove = nameTable.remove(name);
	    if (patientToRemove == null) return null;
	    int indexToRemove = patientToRemove.getIndex();
	    if (indexToRemove != size) { // If not the last element
	        swap(indexToRemove, size); // Swap with last element
	        heap[size] = null; // Remove the last element after swap
	        size--;
	        if (indexToRemove <= size) {
	            heapifyDown(indexToRemove);
	            heapifyUp(indexToRemove);
	        }
	    } else { // If the last element, just remove it
	        heap[size] = null;
	        size--;
	    }
	    patientToRemove.setIndex(-1); // Mark the removed patient as no longer in the queue
	    patientToRemove.setPositionInQueue(-1);
	    return patientToRemove;
	}

	public Patient getNext() throws EmptyQueueException {
		if (size == 0) {
			throw new EmptyQueueException();
		}
		return heap[1];
	}

	public Patient update(String name, int newUrgency) {
	    Patient patient = nameTable.get(name);
	    if (patient == null) {
	        return null;
	    }
	    
	    int oldUrgency = patient.urgency();
	    patient.setUrgency(newUrgency);
	    
	    if (newUrgency > oldUrgency) {
	        heapifyUp(patient.getIndex());
	    } else if (newUrgency < oldUrgency) {
	        heapifyDown(patient.getIndex());
	    }
	    return patient;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	private void resize(int newCap) {
		Patient[] newHeap = new Patient[newCap];
		System.arraycopy(heap, 1, newHeap, 1, size);
		heap = newHeap;
	}
	
	private void resizeCheck() {
	    if (size >= heap.length - 1) {
	        resize(heap.length * 2);
	    } else if (size > START_CAPACITY && size <= heap.length / 4) {
	        resize(Math.max(heap.length / 2, START_CAPACITY));
	    }
	}

	private void heapifyUp(int index) {
	    while (hasParent(index) && heap[getParentIndex(index)].compareTo(heap[index]) > 0) {
	        swap(index, getParentIndex(index));
	        index = getParentIndex(index);
	    }
	}


	private void heapifyDown(int index) {
	    while (hasLeftChild(index)) {
	        int leftChildIndex = getLeftChildIndex(index);
	        int rightChildIndex = getRightChildIndex(index);
	        int highestPriorityChildIndex = leftChildIndex;

	        if (hasRightChild(index) && heap[rightChildIndex].compareTo(heap[leftChildIndex]) < 0) {
	            highestPriorityChildIndex = rightChildIndex;
	        }

	        if (heap[index].compareTo(heap[highestPriorityChildIndex]) <= 0) {
	            break;
	        }

	        swap(index, highestPriorityChildIndex);
	        index = highestPriorityChildIndex;
	    }
	}


	private void swap(int i, int j) {
		Patient temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
		heap[i].setIndex(i);
		heap[j].setIndex(j);
		heap[i].setPositionInQueue(i);
		heap[j].setPositionInQueue(j);
	}

	private boolean hasParent(int index) {
		return index > 1;
	}

	private boolean hasLeftChild(int index) {
		return getLeftChildIndex(index) <= size;
	}

	private boolean hasRightChild(int index) {
		return getRightChildIndex(index) <= size;
	}

	private int getParentIndex(int index) {
		return index / 2;
	}

	private int getLeftChildIndex(int index) {
		return 2 * index;
	}

	private int getRightChildIndex(int index) {
		return 2 * index + 1;
	}
}