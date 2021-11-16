package assignment2;

public final class Caller implements Runnable {
	private final CallCenter callCenter;

	public Caller(long id, CallCenter callCenter) {
		this.callCenter = callCenter;
	}

	@Override
	public void run() {
		try {
			callCenter.receive(new Call());
		} catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
