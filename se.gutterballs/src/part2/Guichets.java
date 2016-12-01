package part2;

import java.util.Stack;

public class Guichets {
	/**
	* groupe en cours de creation
	*/
	private Groupe groupe;
	/**
	* numero d'identification du guichet
	*/
	private int ids; 
	/**
	* nombre de client que doit atteindre un groupe avant d'etre complet
	*/
	private int capaciteGroupe;
	/**
	* file des clients s'inscrivants
	*/
	private Stack<Client> fileInscriptions;
	/**
	* file des clients allant payer
	*/
	private Stack<Client> filePaiements;
	/**
	* constructeur
	* @param capaciteGroupe capacite du groupe a atteindre
	*/
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
	/**
	* accesseur sur le groupe en cours de creation
	* si le groupe est complet on en cree un nouveau
	*/
	public synchronized Groupe getGroupe() {
		if(this.groupe.isComplete())
			this.groupe = new Groupe(this.ids++, this.capaciteGroupe);
		return this.groupe;
	}
	/**
	* permet a un client de s'incrire dans le groupe qui est en cours de creation
	* @param clientvoulant s'inscrire
	*/
	public void inscription(Client client) {
		this.arriverClient(client);
		this.attendreTraitementInscription(client);
		
	}
	/**
	* permet a un client de regler sa note
	* @param client voulant payer sa partie
	*/
	public void paiement(Client client) {
		this.departClient(client);
		this.attendreTraitementPaiement(client);
	}

	/**
	* met le client dans la file d'attente pour les inscription
	* @param client qui arrive
	*/
	public synchronized void arriverClient(Client client) {
		this.fileInscriptions.add(client);
		notifyAll();
	}
	/**
	* met le client en attente le temps que son inscription soit terminee
	* @param client devant attendre
	*/
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
	/**
	* met le client dans la file d'attente pour les paiements 
	* @param client voulant payer sa note
	*/
	private synchronized void departClient(Client client) {
		this.filePaiements.add(client);
		notifyAll();
	}
	/**
	* met le client en attente le temps que son paiement soit termine
	* @param client
	*/
	private synchronized void attendreTraitementPaiement(Client client) {
		while(this.filePaiements.contains(client)) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	* fait demarer le travail des guichetiers
	* @param guichetier
	*/
	public void travailler(Guichetier guichetier) {
		while(true) {//les Threads qui exécutent cette fonction sont des démons
			guichetier.gererClient(this.attendreClients(guichetier));
		}
	}
	/**
	* permet de notifier qu'une inscription a ete completee
	*/
	public synchronized void notifierFinInscription() {
		notifyAll();
	}
	/**
	* permet de notifier qu'un paiement a ete completee
	*/
	public synchronized void notifierFinPaiement() {
		notifyAll();
	}
	/**
	* 
	* @param guichetier
	* @return le prochain client a devoir etre traiter par un guichetier
	*/
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
