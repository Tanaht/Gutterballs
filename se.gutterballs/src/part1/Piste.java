package part1;

public class Piste {
	private boolean estLibre;
	private int numero;

	public Piste(int numero) {
		estLibre = true;
		this.numero = numero;
	}

	public synchronized void jouer(Client c) {
		c.jouer();
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		

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
	}

	public int getNumero() {
		return numero;
	}

}
