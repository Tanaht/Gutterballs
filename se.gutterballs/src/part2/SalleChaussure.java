package part2;

public class SalleChaussure {
	/**
	* fait prendre une paire de chaussure au client, cela prend un certain temps
	* @param client devant prendre une paire
	*/
	public synchronized void prendre(Client client) {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		client.putChaussures();
		
	}
	/**
	*  fait rendre sa paire de chaussure au client, cela prend un certain temps
	* @ param client  devant rendre sa paire de chaussures
	*/
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
