package assignment2;

import java.util.Queue;
import java.util.LinkedList;

public final class UnboundedCallCenterWaitNotify implements CallCenter {
	private final Queue<Call> pendingCalls = new LinkedList<Call>();

	public void receive(Call call) throws Exception {
		pendingCalls.add(call);
	}

	public Call answer() throws InterruptedException {
		// waiting for a call
		while(pendingCalls.size() == 0) {
		}
		return pendingCalls.poll();
	}
}
