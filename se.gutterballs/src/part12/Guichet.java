package part12;

public class Guichet {
	
	private Groupe groupe;
	private int nbGroupe;
	private final int capaciteGroupe;
	
	public Guichet(int capaciteGroupe) {
		this.nbGroupe = 0;
		this.capaciteGroupe = capaciteGroupe;
		this.groupe = new Groupe("[G" + nbGroupe + "]", capaciteGroupe);
	}
	
	
	/**
	 * Inscription d'un client dans un groupe (200ms)
	 * @param c
	 */
	public synchronized void inscription(Client c){
		if(this.groupe.isComplete()) {
			this.groupe = new Groupe("[G" + (++nbGroupe) + "]", capaciteGroupe);
		}
		
		this.groupe.addClient(c);
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
