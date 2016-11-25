package part12;

import java.util.ArrayList;
import java.util.List;

public class Groupe {
	/**
	 * La capacité du groupe
	 */
	private final int capacite;
	
	private boolean alreadyComplete;
	
	/**
	 * Le nom du groupe
	 */
	private String nom;
	
	/**
	 * Les différentes positions possibles pour un groupe
	 */
	public static final int BOWLING = 0;
	public static final int SALLE_CHAUSSURE = 1;
	public static final int GUICHET = 2;
	public static final int PISTE = 3;
	public static final int DANSE = 4;
	
	/**
	 * Les clients du groupe
	 */
	private List<Client> clients;
	/**
	 * Les positions relatives à chaque clients du groupe
	 */
	private List<Integer> positions;
	
	/**
	 * Instanciation d'un Groupe
	 * @param nom
	 * @param capacite
	 */
	public Groupe(String nom, int capacite) {
		this.alreadyComplete = false;
		this.clients = new ArrayList<Client>();
		this.positions = new ArrayList<Integer>();
		this.capacite = capacite;
		this.nom = nom;
	}
	
	/**
	 * Vérifie si un groupe est au complet
	 * @return Vrai si la taille de la liste des clients égale à la capacité du groupe.
	 */
	public synchronized boolean isComplete() {
		if(alreadyComplete)
			return true;
		
		if(this.clients.size() == capacite) {
			System.out.println(this + " est au complet !!!");
			alreadyComplete = true;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Ajoute un client dans le groupe
	 * @param c
	 */
	public void addClient(Client c) {
		this.clients.add(c);
		this.positions.add(-1);
		c.setGroupe(this);
		System.out.println(c + " est inscrit dans le groupe " + this);
	}
	
	/**
	 * Change la position d'un client
	 * @param client
	 * @param position
	 */
	public void moveClientTo(Client client, int position) {
		int index = this.clients.indexOf(client);
		this.positions.set(index, position);
	}
	
	/**
	 * Bloque les clients du groupe tant qu'ils ne sont pas tous dans la position voulue
	 * @param client un client du groupe
	 * @param position la position que le client doit avoir.
	 */
	public synchronized void beAllIn(Client client, int position) {
		while(!this.allIn(position)) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		notify();
	}
	
	public synchronized boolean allIn(int position) {
		int ensemble = 0;
			for(int i = 0 ; i < this.positions.size() ; i++) {
				if(this.positions.get(i) == position)
					ensemble++;
			}
		
		return ensemble == capacite;
	}
	
	public String toString() {
		return nom;
	}

	public boolean allHaveChaussures() {
		int haveChaussures = 0;
		for(int i = 0 ; i < this.clients.size() ; i++) {
			haveChaussures += this.clients.get(i).getChaussures();
		}
		
		return haveChaussures == capacite;
	}

	public synchronized void waitUntilComplete(Client client) {
		while(!this.isComplete()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		notify();
	}
}
