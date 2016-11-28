package part1;

import java.util.List;


import java.util.ArrayList;
import java.util.HashMap;

public class Bowling {
	private Guichet guichet;
	private SalleChaussure vestiaire;
	private PisteDeDanse pisteDeDanse;
	
	private List<Piste> pistes;
	private HashMap<Groupe, Piste> reservations;
	private HashMap<Piste, Integer> partiesParPiste;
	private int totalParties;
	
	public Bowling(int nbPiste, int capaciteGroupe) {
		this.pisteDeDanse = new PisteDeDanse();
		this.guichet = new Guichet(capaciteGroupe);
		this.vestiaire = new SalleChaussure();
		this.totalParties = 0;
		
		this.pistes = new ArrayList<>();
		this.reservations = new HashMap<>();
		this.partiesParPiste = new HashMap<>();

		for(int i = 0 ; i < nbPiste ; i++) {
			this.pistes.add(new Piste(i+1));
			this.partiesParPiste.put(this.pistes.get(i), 0);
		}
		
	}
	
	public synchronized HashMap<Piste, Integer> getPartiesParPiste() {
		return this.partiesParPiste;
	}
	
	public synchronized int getTotalParties() {
		return this.totalParties;
	}
	public synchronized boolean pisteOccupee() {
		return this.pistes.size() == this.reservations.size();
	}
	
	public synchronized void reserverPiste(Groupe groupe) {
		for(Piste piste : this.pistes) {
			if(!this.reservations.containsValue(piste)) {
				this.reservations.put(groupe, piste);
				this.totalParties++;
				this.partiesParPiste.put(piste, this.partiesParPiste.get(piste)+1);
				System.out.println(groupe + "[" + Thread.currentThread().getName() + "][piste reserver]");
				return;
			}
		}
	}
	
	private synchronized void libererPiste(Groupe groupe) {
		if(this.reservations.containsKey(groupe)) {
			System.out.println("[un membre de]" + groupe + "[indique que le groupe à finit de jouer sur]" + this.reservations.get(groupe));
			this.reservations.remove(groupe);
			this.pisteDeDanse.pisteLiberee();
		}
	}
	
	public synchronized void reservation(Client client) {
		
		if(!this.reservations.containsKey(client.getGroupe())) {
			this.reserverPiste(client.getGroupe());
		}
		else {
			System.out.println(client + "" + client.getGroupe() + "[piste déjà reserver]");
		}
	}
	
	public void entrer(Client client) {
		//phase d'inscription (prend 200 ms)
		this.guichet.inscription(client);
		
		Groupe groupe = client.getGroupe();
		//attente des membres du groupe
		groupe.waitUntilComplete();
		
		this.vestiaire.prendre(client);
		
		groupe.waitAllChausser(client);
		
		if(this.pisteOccupee())
			this.pisteDeDanse.echauffement(client);
		
		this.reservation(client);
		
		Piste piste = this.reservations.get(groupe);
		
		piste.utiliser(client);
		
		//Un membre du groupe va indiquer que la piste est libre.
		this.libererPiste(groupe);
		client.setGroupe(null);
		System.out.println(client + "[quitte le groupe]" + groupe);
		guichet.payer(client);
		vestiaire.rendre(client);
	}
}
