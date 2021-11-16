package assignment2;

import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public final class UnboundedCallCenterSemaphore implements CallCenter {
	private final Queue<Call> pendingCalls = new LinkedList<Call>();
	private final Semaphore full = new Semaphore(0);
	private final Semaphore lock = new Semaphore(1);

	public void receive(Call call) throws Exception {
		lock.acquire();
		pendingCalls.add(call);
		lock.release();
		full.release();
	}

	public Call answer() throws InterruptedException {
		// waiting for a call
		full.acquire();
		lock.acquire();
		Call c = pendingCalls.poll();
		lock.release();
		return c;
	}
}
