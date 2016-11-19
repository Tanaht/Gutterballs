package part1;

import java.util.ArrayList;
import java.util.List;

public class Bowling {
	private List<Piste> pistes;//x10
	private Danse danse;
	private SalleChaussure salleChaussure;
	private Paiement paiement;
	
	/**
	 * Initialisation du Bowling,
	 * Il y a 10 pistes de Jeu.
	 */
	public Bowling() {
		this.danse = new Danse();
		this.salleChaussure = new SalleChaussure();
		this.paiement = new Paiement();
		
		this.pistes = new ArrayList<>();
		
		for(int i = 0 ; i < 10 ; i++) {
			this.pistes.add(i, new Piste());
		}
	}
	
	public void arriverClient(Client c) {
		paiement.inscription(c);
		System.out.println("Au complet, on va jouer !!!");
		
	}
	
	public int pisteLibre() {
		for(int i = 0 ; i < this.pistes.size() ; i++) {
			if(this.pistes.get(i).estLibre())
				return i;
		}
		return -1;
		
	}
}
