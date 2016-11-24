package part1;

public class Danse {
	public synchronized void notifier(){
		this.notifyAll();
	}
}
