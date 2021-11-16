package assignment2;

import java.util.Queue;
import java.util.LinkedList;

public final class BoundedCallCenterSemaphore implements BoundedCallCenter {
	private final Queue<Call> pendingCalls = new LinkedList<Call>();

	public void receive(Call call) throws Exception {
		// simulating call retrival
		while(pendingCalls.size() >= MAX_NUMBER_OF_PENDING_CALLS) {
		}
		pendingCalls.add(call);
	}

	public Call answer() throws InterruptedException {
		// waiting for a call
		while(pendingCalls.size() == 0) {
		}
		return pendingCalls.poll();
	}
}
