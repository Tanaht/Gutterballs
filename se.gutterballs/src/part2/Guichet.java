package part2;

import java.util.Random;

public class Guichet {
	
	private int ids;
	private int capacite;
	private Groupe groupe;
	
	public Guichet(int capaciteGroupe) {
		this.capacite = capaciteGroupe;
		this.ids = 0;
		this.groupe = new Groupe(this.ids++, this.capacite);
	}
	
	public synchronized void inscription(Client client) {
		if(this.groupe.isComplete())
			this.groupe = new Groupe(this.ids++, this.capacite);
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.groupe.addClient(client);
	}

	public synchronized void payer(Client client) {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(client + "[règle sa facture]");
	}
}
