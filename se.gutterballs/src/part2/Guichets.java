package part2;

import java.util.Random;

public class Guichets {
	private Guichet guichet1, guichet2, guichet3;
	private Groupe groupe;
	private int ids, capaciteGroupe, allowClient;
	public Guichets(int capaciteGroupe) {
		this.guichet1 = new Guichet(capaciteGroupe);
		this.guichet2 = new Guichet(capaciteGroupe);
		this.guichet3 = new Guichet(capaciteGroupe);
		this.ids = 0;
		this.capaciteGroupe = capaciteGroupe;
		this.groupe = new Groupe(this.ids++, capaciteGroupe);
		this.allowClient = 0;
		this.wait = false;
	}
	private boolean wait;
	
	public synchronized void setWait(boolean b) {
		this.wait = wait;
	}
	
	public synchronized void nouveauClient() {
		while(wait) {
			try {
				this.groupe.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notify();
	}
	
	public void inscription(Client client) {
		
		
		int numeroGuichet = 1 + new Random().nextInt(3);
		
		switch(numeroGuichet) {
		case 1:
			System.out.println(client + "[inscription:Guichet 1]");
			//this.guichet1.inscription(client);
			synchronized (this.groupe) {
				if(this.groupe.isComplete()) {
					this.setWait(true);
					System.out.println("GROUPE COMPLET !!!!");
					this.groupe = new Groupe(this.ids++, capaciteGroupe);
					this.setWait(false);
					//this.guichet1.setGroupe(groupe);
					//this.guichet2.setGroupe(groupe);
					//this.guichet3.setGroupe(groupe);
				}
				
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				this.groupe.addClient(client);
			}
			break;
		case 2:
			System.out.println(client + "[inscription:Guichet 2]");
			//this.guichet2.inscription(client);
			synchronized (this.groupe) {
				if(this.groupe.isComplete()) {
					this.setWait(true);
					System.out.println("GROUPE COMPLET !!!!");
					this.groupe = new Groupe(this.ids++, capaciteGroupe);
					this.setWait(false);
					//this.guichet1.setGroupe(groupe);
					//this.guichet2.setGroupe(groupe);
					//this.guichet3.setGroupe(groupe);
				}
				
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				this.groupe.addClient(client);
			}
			break;
		case 3:
			System.out.println(client + "[inscription:Guichet 3]");
			//this.guichet3.inscription(client);
			synchronized (this.groupe) {
				if(this.groupe.isComplete()) {
					this.setWait(true);
					System.out.println("GROUPE COMPLET !!!!");
					this.groupe = new Groupe(this.ids++, capaciteGroupe);
					this.setWait(false);
					//this.guichet1.setGroupe(groupe);
					//this.guichet2.setGroupe(groupe);
					//this.guichet3.setGroupe(groupe);
				}
				
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				this.groupe.addClient(client);
			}
			break;
		}
		
	}

	public void paiement(Client client) {
		
		int numeroGuichet = 1 + new Random().nextInt(3);
		
		switch(numeroGuichet) {
		case 1:
			System.out.println(client + "[paiement:Guichet 1]");
			this.guichet1.paiement(client);
			break;
		case 2:
			System.out.println(client + "[paiement:Guichet 2]");
			this.guichet2.paiement(client);
			break;
		case 3:
			System.out.println(client + "[paiement:Guichet 3]");
			this.guichet3.paiement(client);
			break;
		}
	}
}
