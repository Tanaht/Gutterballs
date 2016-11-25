package part11;


public class Bowling {
	private Guichet guichet;

	public Bowling(int capaciteGroupe) {
		this.guichet = new Guichet(capaciteGroupe);
	}

	public void nouveauClient(Client client) {
		//Inscription des clients dans un groupe, ils vont dans la position: GUICHET
		guichet.inscription(client);	
		
		//Bloque les clients du groupe tant qu'ils ne sont pas dans le GUICHET
		client.getGroupe().beAllIn(client, Groupe.GUICHET);
	}
}
