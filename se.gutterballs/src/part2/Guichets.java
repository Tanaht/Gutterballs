package part2;

import java.util.Stack;

public class Guichets {
	private Groupe groupe;
	private int ids, capaciteGroupe;
	private Stack<Client> fileInscriptions;
	private Stack<Client> filePaiements;
	
	public Guichets(int capaciteGroupe) {
		this.ids = 0;
		this.capaciteGroupe = capaciteGroupe;
		this.groupe = new Groupe(this.ids++, capaciteGroupe);
		this.fileInscriptions = new Stack<>();
		this.filePaiements = new Stack<>();
		
		Guichetier un = new Guichetier(this,1);
		Guichetier deux = new Guichetier(this,2);
		Guichetier trois = new Guichetier(this,3);
		
		Thread th1 = new Thread(un);
		Thread th2 = new Thread(deux);
		Thread th3 = new Thread(trois);
		th1.setDaemon(true);
		th2.setDaemon(true);
		th3.setDaemon(true);
		th1.start();
		th2.start();
		th3.start();
		
	}
	
	public synchronized Groupe getGroupe() {
		if(this.groupe.isComplete())
			this.groupe = new Groupe(this.ids++, this.capaciteGroupe);
		return this.groupe;
	}
	
	public void inscription(Client client) {
		this.arriverClient(client);
		this.attendreTraitementInscription(client);
		
	}

	public void paiement(Client client) {
		this.departClient(client);
		this.attendreTraitementPaiement(client);
	}

	public synchronized void arriverClient(Client client) {
		this.fileInscriptions.add(client);
		notifyAll();
	}
	
	public synchronized void attendreTraitementInscription(Client client) {
		while(this.fileInscriptions.contains(client) || client.getGroupe() == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(client.getGroupe() == null)
			System.err.println(client + "[groupe not found]");
	}
	
	private synchronized void departClient(Client client) {
		this.filePaiements.add(client);
		notifyAll();
	}
	
	private synchronized void attendreTraitementPaiement(Client client) {
		while(this.filePaiements.contains(client)) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	public void travailler(Guichetier guichetier) {
		while(true) {//les Threads qui exécutent cette fonction sont des démons
			guichetier.gererClient(this.attendreClients(guichetier));
		}
	}
	
	public synchronized void notifierFinInscription() {
		notifyAll();
	}
	
	public synchronized void notifierFinPaiement() {
		notifyAll();
	}
	
	private synchronized Client attendreClients(Guichetier guichetier) {
		while(this.fileInscriptions.isEmpty() && this.filePaiements.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(this.filePaiements.isEmpty()) {
			guichetier.sePreparerInscription();
			return this.fileInscriptions.pop();
		}
		
		guichetier.sePreparerPaiement();
		return this.filePaiements.pop();
		
	}
}
