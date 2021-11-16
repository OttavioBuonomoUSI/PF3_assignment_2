package assignment2;

public interface CallCenter {
	void receive(Call call) throws Exception;
	Call answer() throws InterruptedException;
}
