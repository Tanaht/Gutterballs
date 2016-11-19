package part1;

public class Paiement {
	
	private boolean estLibre;
	private Groupe groupe;
	
	public Paiement() {
		this.estLibre = true;
		this.groupe = new Groupe();
	}
	
	
	public synchronized void inscription(Client c){
		
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(this.groupe.isComplete())
			this.groupe = new Groupe();
		
		this.groupe.addClient(c);
		
		//TODO: Mettre les clients au sein d'un groupe
		
		//Le client attend tant que g n'est pas complet.
		while(!c.getGroupe().isComplete()) {
			System.out.println("Le groupe n'est pas complet, on attend");
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		notifyAll();
		
	}
	
	public void paiement(Client c){
	}
	
	public synchronized boolean estLibre() {
		return this.estLibre;
	}
}
