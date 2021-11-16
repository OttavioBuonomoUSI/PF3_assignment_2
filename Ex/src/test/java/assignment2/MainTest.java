package assignment2;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

@Timeout(7)
public class MainTest {

    public void test(Class<? extends CallCenter> callCenterClass, int numberOfCallers, int numberOfOperators) throws Exception {
        List<Thread> threads = new ArrayList<Thread>(numberOfCallers + numberOfOperators);
		CallCenter callCenter = callCenterClass
                        .getDeclaredConstructor()
                        .newInstance();
		Caller[] callers = new Caller[numberOfCallers];
		Operator[] operators = new Operator[numberOfOperators];

		for (int i = 0; i < numberOfOperators; i++) {
			operators[i] = new Operator(callCenter);
			threads.add(new Thread(operators[i]));
		}

		for (int i = 0; i < numberOfCallers; i++) {
			callers[i] = new Caller(i, callCenter);
			threads.add(new Thread(callers[i]));
		}

		Collections.shuffle(threads);

		for (Thread thread : threads) {
			thread.start();
		}

		for (Thread thread : threads) {
			thread.join();
		}

		// assert that the same call has been answered only once
		Map<Call, Integer> duplicates = new HashMap<Call, Integer>();
        for (int i = 0; i < operators.length; i++) {
            Operator operator = operators[i];
            Call call = operator.getCall();

            if (call != null) {
                int numberOfOccurences = duplicates.getOrDefault(call, 0);
                duplicates.put(call, numberOfOccurences + 1);
            }
        }

		for(Map.Entry<Call, Integer> entry : duplicates.entrySet()) {
            Call call = entry.getKey();
            int times = entry.getValue();
        
            assertEquals(
                "Unique call number " + call.getId() +
                " has been answered " + times + " times.\n",
                1,
                times
            );
        }

		int actual = duplicates.size();
		assertEquals(
			"Expected callCenter to anwer " + numberOfCallers + " calls but " +
			actual + " have been answered instead.\n",
			numberOfCallers,
			actual
		);
    }

    @Test
    public void BoundedCallCenterImpl() throws Exception {
        test(BoundedCallCenterImpl.class, 100, 100);
    }

    @Test
    public void BoundedCallCenterWaitNotify() throws Exception {
        test(BoundedCallCenterWaitNotify.class, 100, 100);
    }

    @Test
    public void BoundedCallCenterSemaphore() throws Exception {
        test(BoundedCallCenterSemaphore.class, 100, 100);
    }

    @Test
    public void BoundedCallCenterReentrantLock() throws Exception {
        test(BoundedCallCenterReentrantLock.class, 100, 100);
    }

    @Test
    public void UnboundedCallCenterImpl() throws Exception {
        test(UnboundedCallCenterImpl.class, 100, 100);
    }

    @Test
    public void UnboundedCallCenterWaitNotify() throws Exception {
        test(UnboundedCallCenterWaitNotify.class, 100, 100);
    }

    @Test
    public void UnboundedCallCenterSemaphore() throws Exception {
        test(UnboundedCallCenterSemaphore.class, 100, 100);
    }

    @Test
    public void UnboundedCallCenterReentrantLock() throws Exception {
        test(UnboundedCallCenterReentrantLock.class, 100, 100);
    }
    
}
