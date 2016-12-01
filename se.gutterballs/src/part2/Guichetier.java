package part2;

public class Guichetier implements Runnable {
	private Guichets guichets;
	private int travail;
	private int nom;
	public static final int inscription = 0;
	public static final int paiement = 1;
	
	public Guichetier(Guichets guichets, int nom) {
		this.guichets = guichets;
	}
	@Override
	public void run() {
		this.guichets.travailler(this);
	}
	
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
	
	public void sePreparerPaiement(){
		this.travail = this.paiement;
	}
	
	public void sePreparerInscription(){
		this.travail = this.inscription;
	}
	
	public void gererClient(Client client) {
		if(travail == Guichetier.inscription)
			this.inscrire(client);
		else
			this.fairePayer(client);
	}
	
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
