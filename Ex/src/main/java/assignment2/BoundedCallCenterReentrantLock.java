package assignment2;

import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public final class BoundedCallCenterReentrantLock implements BoundedCallCenter {
	private final Queue<Call> pendingCalls = new LinkedList<Call>();
	private final ReentrantLock reentrantLock = new ReentrantLock();
	private final Condition condition = reentrantLock.newCondition();

	public void receive(Call call) throws Exception {
		// simulating call retrival
		reentrantLock.lock();
		while (MAX_NUMBER_OF_PENDING_CALLS == pendingCalls.size()) {
			condition.await();
		}
		pendingCalls.add(call);
		condition.signal();
		reentrantLock.unlock();
	}

	public Call answer() throws InterruptedException {
		// waiting for a call
		reentrantLock.lock();
		Call c;
		while (pendingCalls.size() <= 0) {
			condition.await();
		}
		c = pendingCalls.poll();
		condition.signal();
		reentrantLock.unlock();
		return c;
	}
}
