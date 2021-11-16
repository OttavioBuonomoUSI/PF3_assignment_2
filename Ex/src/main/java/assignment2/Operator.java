package assignment2;

public final class Operator implements Runnable {
	private final CallCenter callCenter;
	private Call call;

	public Operator(CallCenter callCenter) {
		this.callCenter = callCenter;
	}

	@Override
	public void run() {
		try {
			this.call = callCenter.answer();
		} catch(InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}

	public Call getCall() {
		return this.call;
	}	
}