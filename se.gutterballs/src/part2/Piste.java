package part2;

import java.util.Random;

public class Piste {
	private int id;
	
	public Piste(int id) {
		this.id = id;
	}
	
	public void utiliser(Client client) {
		client.getGroupe().addClientSurPiste(client);
		client.getGroupe().waitAllSurPiste(client);
		this.jouer();
	}
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
