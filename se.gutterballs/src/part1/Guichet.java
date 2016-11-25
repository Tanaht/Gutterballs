package part1;

public class Guichet {
	
	private boolean estLibre;
	private Groupe groupe;
	private int k;
	private int capaciteGroupe;
	
	private boolean occupe;
	
	public Guichet(int capaciteGroupe) {
		this.occupe = false;
		this.estLibre = true;
		this.k=0;
		this.capaciteGroupe = capaciteGroupe;
		this.groupe = new Groupe("G"+k, capaciteGroupe);
	}
	
	
	public synchronized void inscription(Client c){
		
		System.out.println("Le guichet procède à l'inscription d'un client, il est occupé");
		if(this.groupe.isComplete()) {
			this.groupe = new Groupe("G" + (++k), capaciteGroupe);
		}
		
		this.groupe.addClient(c);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Le guichet à finis de s'occuper d'un client, il se libère");
		
		while(!c.getGroupe().isComplete()) {
			try {
				System.out.println("[" + c + "] attend");
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("[" + c + "] se réveille et s'en va");
		notify();
		
		/*
		 * L'inscription du groupe dans le système informatique prend un certain temps, 
		 * on suppose que le système est mis à jour (le sleep) 
		 * une fois que tout les clients pour former un groupe sont là.
		 */
		
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
