package part12;


public class Bowling {
	private Guichet guichet;
	private SalleChaussure salleChaussure;

	public Bowling(int capaciteGroupe) {
		this.guichet = new Guichet(capaciteGroupe);
		this.salleChaussure = new SalleChaussure();
	}

	public void nouveauClient(Client client) {
		//Inscription des clients dans un groupe, ils vont dans la position: GUICHET
		guichet.inscription(client);	
		
		client.getGroupe().waitUntilComplete(client);
		
		//Le groupe est au complet, ils vont donc prendre des chaussures
		salleChaussure.prendre(client);
		
		
		//client.getGroupe().beAllIn(client, Groupe.SALLE_CHAUSSURE);
	}
}
