package assignment2;

import java.util.Queue;
import java.util.LinkedList;

public final class UnboundedCallCenterWaitNotify implements CallCenter {
	private final Queue<Call> pendingCalls = new LinkedList<Call>();
	private final Object lock = new Object();

	public void receive(Call call) throws Exception {
		while (pendingCalls.size() >= 1) {
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
			lock.notify();
		}
	}

	public Call answer() throws InterruptedException {
		// waiting for a call
		Call c;
		synchronized (lock) {
			while (pendingCalls.size() <= 0) {
				lock.wait();
			}
			c = pendingCalls.poll();
			lock.notify();
		}
		return c;
	}
}
