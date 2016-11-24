package part1;

import java.util.ArrayList;
import java.util.List;

public class Groupe {
	//extends ThreadGroup ?
	//groupe de 8
	
	private int capacite;
	private int numPiste;
	private String nom;
	
	private List<Client> clients;//Client or Thread
	
	public Groupe(String nom, int capacite) {
		this.clients = new ArrayList<Client>();
		this.capacite = capacite;
		numPiste = Bowling.PISTE_INDISPONIBLE;
		this.nom = nom;
	}
	
	public synchronized boolean isComplete() {
		return this.clients.size() == capacite;
	}
	
	public synchronized void addClient(Client c) {
		if(!isComplete()) {
			this.clients.add(c);
			c.setGroupe(this);
			System.out.println("[" + c + "] inscrit dans le groupe [" + this + "]");
		}
	}
	
	public synchronized boolean toutesChaussuresBoowling(){
		boolean res = true;
		for(Client c : clients){
			res = res && c.aSesChaussureDebowling();
		}
		
		return res;
	}
	
	public synchronized boolean tousSurPiste(){
		boolean res = true;
		for(Client c : clients){
			res = res && c.estSurLaPiste();
		}
		return res;
	}
	
	public synchronized boolean personneSurPiste(){
		boolean res = false;
		for(Client c : clients){
			res = res || c.estSurLaPiste();
		}
		return !res;
	}
	
	public synchronized boolean tousjouer(){
		boolean res = true;
		for(Client c : clients){
			res = res && c.isJouer();
		}
		return res;
	}
	
	public synchronized void setNumPiste(int numPiste) {
		this.numPiste = numPiste;
	}
	
	public synchronized int pisteReservee(){
		return numPiste;
	}

	public String getNom() {
		return nom;
	}

	public String toString() {
		return nom;
	}
}
