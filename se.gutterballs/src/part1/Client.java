package part1;

public class Client implements Runnable {
	private Bowling b;
	private Groupe g;
	private boolean chaussureBowling;
	private boolean estSurPiste;
	private boolean jouer;
	
	public Client(Bowling b) {
		this.b = b;
		this.g = null;
		this.chaussureBowling = false;
		this.estSurPiste = false;
		this.jouer = false;
	}
	
	@Override
	public void run() {
		b.arriverClient(this);
		
		//b.nouveauClient(this);
	}
	
	public boolean aSesChaussureDebowling(){
		return chaussureBowling;
	}
	
	public void recevoirChaussuresBoowling(){
		this.chaussureBowling = true;
	}
	
	public void rendreChaussures(){
		this.chaussureBowling = false;
	}
	
	public void setGroupe(Groupe g) {
		this.g = g;
	}
	
	
	public boolean estSurLaPiste(){
		return estSurPiste;
	}
	
	public void entrerPiste(){
		estSurPiste = true;
	}
	
	public void quitterPiste(){
		estSurPiste = false;
	}
	
	public void jouer(){
		jouer = true;
	}
	
	public void finirjouer(){
		jouer = false;
	}
	
	public Groupe getGroupe() {
		return this.g;
	}

	public String getNom() {
		return Thread.currentThread().getName();
	}
	
	public String toString() {
		return Thread.currentThread().getName();
	}

	public boolean isJouer() {
		return jouer;
	}

}
