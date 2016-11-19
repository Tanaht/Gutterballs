package part1;

public class Piste {
	private boolean estLibre;
	
	public synchronized void jouer() {
		//TODO: c'est un groupe qui joue
		//TODO: à la fin de la partie, une personne du groupe notifie le guichet, puis le groupe de disloque. 
	}
	
	public synchronized boolean estLibre() {
		return this.estLibre;
	}
}
