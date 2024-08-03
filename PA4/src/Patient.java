public class Patient implements Comparable<Patient> {
	private String name;
	private int urgency;
	private Long time_in;
	private int queuePosition;
	private int index;

	public Patient(String name, int urgency, int time) {
		this.name = name;
		this.urgency = urgency;
		this.time_in = (long) time;
	}
	
	public Patient(String name, int urgency, long time) {
		this.name = name;
		this.urgency = urgency;
		this.time_in = time;
	}

	public String name() {
		return this.name;
	}

	public int urgency() {
		return this.urgency;
	}

	public void setUrgency(int urgency) {
		this.urgency = urgency;
	}

	public int compareTo(Patient other) {
	    // Higher urgency has priority
	    int urgencyComparison = Integer.compare(other.urgency, this.urgency);
	    if (urgencyComparison != 0) {
	        return urgencyComparison;
	    }
	    // For same urgency, earlier time_in has priority
	    return Long.compare(this.time_in, other.time_in);
	}

	public String toString() {
		// Assuming positionInQueue is managed and updated correctly outside this method
		return name + ", " + urgency + ", " + time_in + ", " + queuePosition;
	}

	public void setPositionInQueue(int queuePosition) {
		this.queuePosition = queuePosition;
	}

	public int posInQueue() {
		return this.queuePosition;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return this.index;
	}
}