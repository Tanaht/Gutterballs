package part12;

public class Client implements Runnable {
	private Bowling b;
	private Groupe g;
	private int chaussures = 0;
	
	/**
	 * Constructeur d'un Client
	 * @param b
	 */
	public Client(Bowling b) {
		this.b = b;
	}
	
	@Override
	public void run() {
		//un client arrive dans le bowling par l'intermédiaire de la méthode Bowling.nouveauClient();
		b.nouveauClient(this);
	}
	
	/**
	 * Setter du groupe d'un client
	 * @param g
	 */
	public void setGroupe(Groupe g) {
		this.g = g;
	}
	
	/**
	 * Getter du groupe d'un client
	 * @return
	 */
	public Groupe getGroupe() {
		return this.g;
	}
	
	/**
	 * Affiche le nom du thread correspondant au client
	 */
	public String toString() {
		return "[" + Thread.currentThread().getName() + "]";
	}

	public void setChaussures() {
		this.chaussures = 1;
	}
	
	public int getChaussures() {
		return this.chaussures;
	}
}
