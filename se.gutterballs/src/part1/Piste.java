package part1;

import java.util.Random;
/**
* Fait jouer les clients 
*/
public class Piste {
	/**
	* numero d'identification de la piste
	*/
	private int id;
	
	/**
	* constructeur
	* @param id identificateur de la piste
	*/
	public Piste(int id) {
		this.id = id;
	}
	
	/**
	* fait venir le client sur la piste et le fait attendre les autres membre de son groupe
	* une fois que tout le groupe est sur la piste, il joue
	* @param client
	*/
	public void utiliser(Client client) {
		client.getGroupe().addClientSurPiste(client);
		client.getGroupe().waitAllSurPiste(client);
		this.jouer();
	}
	
	/**
	* methode simulant le fait de jouer une partie, cela prend un certain temps
	*/
	public void jouer() {
		try {
			//Il faut qu'une partie dure plus longtemps que le temps que va mettre le groupe suivant pour s'inscrire et prendre leurs chaussures.
			Thread.sleep(9000 + new Random().nextInt(2000));//On part du principe que certaine partie sont plus rapide que d'autres
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[Piste-" + this.id + "]";
	}
}
