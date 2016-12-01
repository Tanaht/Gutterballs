package part1;

import java.util.Random;
/**
* 
* Moniteur qui realise le regroupement des clients en groupe
*/
public class Guichet {
	/**
	* numero d'identification du guichet
	*/
	private int ids;
	/**
	* nombre de client que doit atteindre un groupe avant d'etre complet
	*/
	private int capacite;
	/**
	* groupe en cours de creation
	*/
	private Groupe groupe;
	
	/**
	* constructeur
	* @param capaciteGroupe capacite du groupe a atteindre
	*/
	public Guichet(int capaciteGroupe) {
		this.capacite = capaciteGroupe;
		this.ids = 0;
		this.groupe = new Groupe(this.ids++, this.capacite);
	}
	
	/**
	* permet a un client de s'inscrire dans le groupe qui est en cours de creation, cela prend un certain temps
	* @param client voulant s'inscrire
	*/
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

	/**
	* permet a un client de regler sa note, cela prend un certain temps
	* @param client voulant payer sa partie
	*/
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
