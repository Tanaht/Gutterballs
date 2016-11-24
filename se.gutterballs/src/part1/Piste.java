package part1;

public class Piste {
	private boolean estLibre;
	private int numero;

	public Piste(int numero) {
		estLibre = true;
		this.numero = numero;
	}

	public synchronized void jouer(Client c) {
		
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		c.jouer();
		while(!c.getGroupe().tousjouer()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notify();

	}

	public synchronized boolean estLibre() {
		return this.estLibre;
	}

	public synchronized void entrerPiste(Client c) {
		estLibre = false;
		c.entrerPiste();
	}

	public synchronized void quitter(Client c) {

		c.quitterPiste();
		if (c.getGroupe().personneSurPiste())
			estLibre = true;
		
		if(estLibre()){
			System.out.println("Le groupe "+c.getGroupe().getNom()+" vient de finir de jouer sur la piste "+getNumero());
			notifyAll();
		
		}
	}

	public int getNumero() {
		return numero;
	}

}
