package part1;

public class Guichet {
	
	private boolean estLibre;
	private Groupe groupe;
	private int k;
	
	public Guichet() {
		this.estLibre = true;
		this.k=0;
		this.groupe = new Groupe("G"+k);
	}
	
	
	public synchronized void inscription(Client c){
		System.out.println("Le client "+c.getNom() +" s'inscrit");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Le client "+c.getNom()+ " rejoind le groupe "+this.groupe.getNom());
		this.groupe.addClient(c);
		if(this.groupe.isComplete()){
			System.out.println("Le groupe "+this.groupe.getNom()+ " est plein");
			this.groupe = new Groupe("G"+(++k));
		}
		
		//Le client attend tant que g n'est pas complet.
		while(!c.getGroupe().isComplete()) {
		
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		notifyAll();
		
	}
	
	public synchronized void paiement(Client c){
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Le client "+c.getNom() +"du groupe " +c.getGroupe().getNom()+ " paye et s'en va");
	}
	
	public synchronized boolean estLibre() {
		return this.estLibre;
	}
}
