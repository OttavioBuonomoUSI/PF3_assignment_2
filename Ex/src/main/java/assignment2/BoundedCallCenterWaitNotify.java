package assignment2;

import java.util.Queue;
import java.util.LinkedList;

public final class BoundedCallCenterWaitNotify implements BoundedCallCenter {
	private final Queue<Call> pendingCalls = new LinkedList<Call>();
	private final Object lock = new Object();

	public void receive(Call call) throws Exception {
		// simulating call retrival
		while (MAX_NUMBER_OF_PENDING_CALLS == pendingCalls.size()) {
			synchronized (lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		synchronized (lock) {
			pendingCalls.add(call);
			lock.notifyAll();
		}
	}

	public Call answer() throws InterruptedException {
		// waiting for a call
		Call c;
		while (pendingCalls.size() <= 0) {
			synchronized (lock) {
				lock.wait();
			}
		}
		synchronized (lock) {
			c = pendingCalls.poll();
			lock.notifyAll();
		}

		return c;
	}
}
