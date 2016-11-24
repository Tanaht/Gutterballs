package part1;

public class Guichet {
	
	private boolean estLibre;
	private Groupe groupe;
	private int k;
	private int capaciteGroupe;
	
	public Guichet(int capaciteGroupe) {
		this.estLibre = true;
		this.k=0;
		this.capaciteGroupe = capaciteGroupe;
		this.groupe = new Groupe("G"+k, capaciteGroupe);
	}
	
	
	public synchronized void inscription(Client c){
		
		if(this.groupe.isComplete()) {
			this.groupe = new Groupe("G" + (++k), capaciteGroupe);
		}
		
		this.groupe.addClient(c);
		
		while(!c.getGroupe().isComplete()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		notify();
		
		/*
		 * L'inscription du groupe dans le système informatique prend un certain temps, 
		 * on suppose que le système est mis à jour (le sleep) 
		 * une fois que tout les clients pour former un groupe sont là.
		 */
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public synchronized void paiement(Client c){
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized boolean estLibre() {
		return this.estLibre;
	}
}
