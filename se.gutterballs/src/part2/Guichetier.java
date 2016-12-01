package part2;

public class Guichetier implements Runnable {
	/**
	* guichet auquel le guichetier est ratache
	*/
	private Guichets guichets;
	/**
	* etat internet du guichetier. Il est soit en train de faire une inscription, soit un paiement. Jamais les deux a la fois
	*/
	private int travail;
	/**
	* idetificateur du guichetier
	*/
	private int nom;
	/**
	* etat pour inscription
	*/
	private static final int inscription = 0;
	/**
	* etat pour paiement
	*/
	private static final int paiement = 1;
	/**
	* constructeur
	* @param guichets guichet ou travaille le guichetier
	* @param nom du guichetier
	*/
	public Guichetier(Guichets guichets, int nom) {
		this.guichets = guichets;
	}
	@Override
	public void run() {
		this.guichets.travailler(this);
	}
	
	/**
	* le guichetier inscrit le client dans le groupe qui est en cours de creation, cela prend un certain temps
	* @param client voulant s'inscrire
	*/
	private void inscrire(Client client) {
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.guichets.getGroupe().addClient(client);
		this.guichets.notifierFinInscription();
	}
	/**
	* mutateur de l'etat de travail en paiement
	*/
	public void sePreparerPaiement(){
		this.travail = this.paiement;
	}
	/**
	* mutateur de l'etat de travail en inscription
	*/
	public void sePreparerInscription(){
		this.travail = this.inscription;
	}
	/**
	* reception d'un client et traitement en fonction de l'etat interne travail
	* @param client devant faire son inscription ou payer sa note
	*/
	public void gererClient(Client client) {
		if(travail == Guichetier.inscription)
			this.inscrire(client);
		else
			this.fairePayer(client);
	}
	/**
	* traitement correspondant au paiement de la note du client, cela prend un certain temps
	* @param client devant payer sa note
	*/
	private void fairePayer(Client client) {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(this + "[fais payer un client]");
		this.guichets.notifierFinPaiement();
	}
	
	public String toString() {
		return "[Guichetier-" + nom + "]";
	}
}
