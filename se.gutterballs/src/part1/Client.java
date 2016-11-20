package part1;

public class Client implements Runnable {
	private Bowling b;
	private Groupe g;
	private String nom;
	private boolean chaussureBowling;
	private boolean estSurPiste;
	private boolean jouer;
	
	public Client(Bowling b,String nom) {
		this.b = b;
		this.g = null;
		this.chaussureBowling = false;
		this.estSurPiste = false;
		this.jouer = false;
		this.nom = nom;
	}
	
	@Override
	public void run() {
		b.arriverClient(this);
	}
	
	public synchronized boolean aSesChaussureDebowling(){
		return chaussureBowling;
	}
	
	public synchronized void recevoirChaussuresBoowling(){
		this.chaussureBowling = true;
	}
	
	public synchronized void rendreChaussres(){
		this.chaussureBowling = false;
	}
	
	public synchronized void setGroupe(Groupe g) {
		this.g = g;
	}
	
	
	public synchronized boolean estSurLaPiste(){
		return estSurPiste;
	}
	
	public synchronized void entrerPiste(){
		estSurPiste = true;
	}
	
	public synchronized void quitterPiste(){
		estSurPiste = false;
	}
	
	public synchronized void jouer(){
		jouer = true;
	}
	
	public synchronized void finirjouer(){
		jouer = false;
	}
	
	public synchronized Groupe getGroupe() {
		return this.g;
	}

	public synchronized String getNom() {
		return nom;
	}

	public boolean isJouer() {
		return jouer;
	}

}
