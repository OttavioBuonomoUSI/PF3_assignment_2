package assignment2;

import java.util.concurrent.atomic.AtomicLong;

public final class Call {
	private static final AtomicLong currentId = new AtomicLong();
	private final long id = currentId.getAndIncrement();

	public long getId() {
		return id;
	}
}