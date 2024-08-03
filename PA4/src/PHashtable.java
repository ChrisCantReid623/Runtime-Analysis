import java.util.LinkedList;

public class PHashtable {
	private LinkedList<Entry>[] table;
	private int size;
	private final int START_CAPACITY = 11;
	private final float LOAD_FACTOR_INCREASE_THRESHOLD = 0.75f;
    private final float LOAD_FACTOR_DECREASE_THRESHOLD = 0.25f;

	@SuppressWarnings("unchecked")
	public PHashtable() {
		this.table = new LinkedList[START_CAPACITY];
		this.size = 0;
	}

	@SuppressWarnings("unchecked")
	public PHashtable(int cap) {
		int initialCapacity = Math.max(cap, START_CAPACITY);
		initialCapacity = nextPrime(initialCapacity);
		this.table = new LinkedList[initialCapacity];
		this.size = 0;
	}

	public Patient get(String name) {
		int index = Math.abs(name.hashCode()) % table.length;
        LinkedList<Entry> list = table[index];
        if (list != null) {
            for (Entry entry : list) {
                if (entry.key.equals(name)) {
                    return entry.value;
                }
            }
        }
        return null;
	}

	public void put(Patient newPatient) {
        resizeCheck(); 
        int hashIndex = Math.abs(newPatient.name().hashCode()) % table.length;
        boolean exists = false;

        if (table[hashIndex] != null) {
            for (Entry entry : table[hashIndex]) {
                if (entry.key.equals(newPatient.name())) {
                    entry.value = newPatient;
                    exists = true;
                    break;
                }
            }
        } else {
            table[hashIndex] = new LinkedList<>();
        }

        if (!exists) {
            table[hashIndex].add(new Entry(newPatient.name(), newPatient));
            size++;
        }
    }

	public Patient remove(String name) {
	    int hashIndex = Math.abs(name.hashCode()) % table.length;
	    LinkedList<Entry> bucket = table[hashIndex];

	    if (bucket != null) {
	    	for (int i = 0; i < bucket.size(); i++) {
	            Entry entry = bucket.get(i);
	            if (entry.key.equals(name)) {
	                bucket.remove(i);
	                size--;
	                resizeCheck();
	                return entry.value; 
	            }
	        }
	    }
	    return null;
	}

	public int size() {
		return size;
	}

	private int nextPrime(int num) {
		while (!isPrime(num)) {
			num++;
		}
		return num;
	}

	private boolean isPrime(int num) {
		if (num <= 1)
			return false;
		for (int i = 2; i * i <= num; i++) {
			if (num % i == 0)
				return false;
		}
		return true;
	}

	private void resizeCheck() {
        float loadFactor = (float) size / table.length;
        if (loadFactor > LOAD_FACTOR_INCREASE_THRESHOLD) {
            resize(nextPrime(table.length * 2));
        } else if (loadFactor < LOAD_FACTOR_DECREASE_THRESHOLD && table.length > START_CAPACITY) {
            resize(Math.max(nextPrime(table.length / 2), START_CAPACITY));
        }
    }

    
	private void resize(int newCapacity) {
        LinkedList<Entry>[] newTable = new LinkedList[newCapacity];
        for (LinkedList<Entry> bucket : table) {
            if (bucket != null) {
                for (Entry entry : bucket) {
                    int newIndex = Math.abs(entry.key.hashCode()) % newCapacity;
                    if (newTable[newIndex] == null) {
                        newTable[newIndex] = new LinkedList<>();
                    }
                    newTable[newIndex].add(entry);
                }
            }
        }
        table = newTable;
    }

	
    private class Entry {
		String key;
		Patient value;

		Entry(String key, Patient value) {
			this.key = key;
			this.value = value;
		}
	}
}