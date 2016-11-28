package part2;


public class Guichet {
	
	private int ids;
	private int capacite;
	private Groupe groupe;
	public Guichet(int capaciteGroupe) {
		this.capacite = capaciteGroupe;
		this.ids = 0;
		this.groupe = new Groupe(this.ids++, this.capacite);
	}
	
	public void inscription(Client client) {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.groupe.addClient(client);
	}

	public void paiement(Client client) {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(client + "[règle sa facture]");
	}
	
	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}
}
