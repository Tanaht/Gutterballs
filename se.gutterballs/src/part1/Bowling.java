package part1;

import java.util.List;


import java.util.ArrayList;
import java.util.HashMap;

public class Bowling {
	
	/**
	* Dans la v1 il n'y a qu'un seul guichet. Celui-ci gere l'inscription et le paiement
	*/
	private Guichet guichet;
	/**
	* Salle permettant de prendre et de rendre les chaussures
	*/
	private SalleChaussure vestiaire;
	/**
	* zone pour faire patienter (wait) les client le temps qu'une piste de bowling se libere
	*/
	private PisteDeDanse pisteDeDanse;
	/**
	* liste de toutes les pistes de bowling
	*/
	private List<Piste> pistes;
	/**
	* Map de couple (Groupe,Piste) representant les reservation
	* Un groupe reserve une piste
	*/
	private HashMap<Groupe, Piste> reservations;
	/**
	* Utilise pour les stats
	* associe a chaque piste le nombre de partie ayant eue lieue
	*/
	private HashMap<Piste, Integer> partiesParPiste;
	/**
	* Utilise pour les stats
	* represente le nombre total de parties ayant ete jouees
	* ce nombre est egal au total de parties jouees par piste de partiesParPiste
	*/
	private int totalParties;
	/**
	* constructeur
	* @param nbPiste le nombre de piste totale que possede le bowling
	* @param capaciteGroupe represente la taille que devrons faire les groupes
	*/
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
	
	/**
	* accesseur sur l'attribut partiesParPiste
	*/
	public synchronized HashMap<Piste, Integer> getPartiesParPiste() {
		return this.partiesParPiste;
	}
	
	/**
	* accesseur sur l'attribut totalParties
	*/
	public synchronized int getTotalParties() {
		return this.totalParties;
	}
	
	/**
	* compare la taille de la liste des pistes avec la taille de la map des reservations
	* Il y a au maximum une seule reservation par piste
	* donc si leurs tailles sont equivalentes c'est qu'il n'y a pas de piste libre
	* @return true si toutes les pistes sont libres ou reservees, si non false
	*/
	public synchronized boolean pisteOccupee() {
		return this.pistes.size() == this.reservations.size();
	}
	
	/**
	* permet d'ajouter une reservation de piste sur la premire piste libre trouvee
	* cette methode est appelee uniquement quand une piste est liberee
	* si aucune n'est libre il s'agit donc d'une erreur
	* @param groupe faisant le reservation
	*/
	public synchronized void reserverPiste(Groupe groupe) {
		for(Piste piste : this.pistes) {
			if(!this.reservations.containsValue(piste)) {
				this.reservations.put(groupe, piste);
				this.totalParties++;
				this.partiesParPiste.put(piste, this.partiesParPiste.get(piste)+1);
				System.out.println(groupe + "[" + Thread.currentThread().getName() + "][piste reserver]" +  this.reservations.get(groupe));
				
				if(this.pisteOccupee())
					this.pisteDeDanse.pistesOccupee();
				
				return;
			}
		}
		System.err.println("Attention aucune piste n'a ete reservee par ce groupe"+groupe);
	}
	
	/**
	* permet de liberer une piste
	* dans la map reservation on supprime l'entree correspondante
	* @param groupe quittant sa piste
	*/
	private synchronized void libererPiste(Groupe groupe) {
		if(this.reservations.containsKey(groupe)) {
			System.out.println("[un membre de]" + groupe + "[indique que le groupe à finit de jouer sur]" + this.reservations.get(groupe));
			this.reservations.remove(groupe);
			this.pisteDeDanse.pisteLiberee();
		}
	}
	
	/**
	* Si le groupe de ce client n'a pas encore reserve de piste ce client effectue la reservation
	* si non ne fait rien
	* @param client
	*/
	public synchronized void reservation(Client client) {
		
		if(!this.reservations.containsKey(client.getGroupe())) {
			System.out.println("Demande de reservation par " + client);
			this.reserverPiste(client.getGroupe());
		}
		else {
			System.out.println(client + "" + client.getGroupe() + "[piste déjà reserver]" + this.reservations.get(client.getGroupe()));
		}
	}
	
	/**
	* permet de savoir si un groupe a une piste de reservee
	* @param groupe cherchant a savoir s'il posse une reservation
	* @return true si le groupe a une reservation, si non false
	*/
	public boolean reserverPar(Groupe groupe) {
		return this.reservations.containsKey(groupe);
	}
	
	/**
	* point d'entre du client
	* le parcourt du client est ordonne dans cette methode
	* il va s'inscrire, attendre que son groupe soit complet
	* prendre ses chaussures, attendre que tout son groupe ait ses chaussures
	* si aucune piste n'est libre il va patienter en dansant
	* il va faire une reservation de piste pour son groupe
	* il se positionne sur la piste et joue
	* il quitte la piste, quitte son groupe
	* il paye et s'en va
	* @param client
	*/
	public void entrer(Client client) {
		//phase d'inscription (prend 200 ms)
		this.guichet.inscription(client);
		
		Groupe groupe = client.getGroupe();
		//attente des membres du groupe
		groupe.waitUntilComplete();
		
		this.vestiaire.prendre(client);
		
		groupe.waitAllChausser(client);
		
		if(this.pisteOccupee() && !this.reserverPar(client.getGroupe()))
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
