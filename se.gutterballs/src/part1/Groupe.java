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
	
	public Groupe(String nom) {
		this.clients = new ArrayList<Client>();
		capacite = 8;
		numPiste = -1;
		this.nom=nom;
	}
	
	public synchronized boolean isComplete() {
		return this.clients.size() == capacite;
	}
	
	public synchronized void addClient(Client c) {
		if(!isComplete()) {
			this.clients.add(c);
			c.setGroupe(this);
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

	
}
