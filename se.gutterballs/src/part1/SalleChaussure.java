package part1;

public class SalleChaussure {
	public synchronized void prendre(Client client) {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		client.putChaussures();
		
	}
	
	public synchronized void rendre(Client client) {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		client.removeChaussures();
	}
}
