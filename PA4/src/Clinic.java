public class Clinic {
	private final int erThreshold;
	private PatientQueue queue;
	private int processedCount;
	private int seenByDoctorCount;
	private int sentToErCount;
	private int walkedOutCount;

	public Clinic(int er_threshold) {
		this.erThreshold = er_threshold;
		this.queue = new PatientQueue();
		this.processedCount = 0;
		this.seenByDoctorCount = 0;
		this.sentToErCount = 0;
		this.walkedOutCount = 0;
	}

	public int er_threshold() {
		return this.erThreshold;
	}

	public String process(String name, int urgency) {
	    Patient newPatient = new Patient(name, urgency, (int) System.currentTimeMillis());
	    if (urgency > erThreshold) {
	        sentToErCount++;
	        return name;
	    } else {
	        queue.insert(newPatient);
	        processedCount++;
	        return name;
	    }
	}

	public String seeNext() throws EmptyQueueException {
	    if (queue.isEmpty()) {
	        return "Empty queue";
	    } else {
	        Patient nextPatient = queue.removeNext();
	        seenByDoctorCount++;
	        return nextPatient.name();
	    }
	}

	public boolean handle_emergency(String name, int urgency) {
		if (urgency > erThreshold) {
			queue.remove(name);
			sentToErCount++;
			return true;
		} else {
			Patient patient = queue.update(name, urgency);
			if (patient != null) {
				return false;
			} else {
				return false;
			}
		}
	}

	public void walk_out(String name) {
		Patient patient = queue.remove(name);
		if (patient != null) {
			walkedOutCount++;
		}
	}

	public int processed() {
		return this.processedCount;
	}

	public int sentToER() {
		return this.sentToErCount;
	}

	public int seenByDoctor() {
		return this.seenByDoctorCount;
	}

	public int walkedOut() {
		return this.walkedOutCount;
	}

}