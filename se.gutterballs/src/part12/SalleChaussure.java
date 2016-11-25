package part12;

public class SalleChaussure {
	public synchronized void prendre(Client client) {
		//Mettre ses chaussures prend un certains temps
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		client.setChaussures();
		System.out.println(client + " " + client.getGroupe() + " à ses chaussures");
		notifyAll();
		
		while(!client.getGroupe().allHaveChaussures()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
