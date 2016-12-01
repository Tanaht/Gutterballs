package part2;

public class Client implements Runnable{
	private Groupe groupe;
	private int chaussures;
	private Bowling bowling;
	private int nom;
	
	public Client(Bowling b, int nom) {
		this.bowling = b;
		this.nom = nom;
		this.chaussures = 0;
	}

	@Override
	public void run() {
		System.out.println(this + "[entre dans le bowling]");
		this.bowling.entrer(this);
		System.out.println(this + "[quitte le bowling]");
	}
	
	public void putChaussures() {
		this.chaussures = 1;
		this.getGroupe().addClientChausser(this);
	}
	
	public void removeChaussures() {
		this.chaussures = 0;
		System.out.println(this + "[rend ses chaussures]");
	}
	
	@Override
	public String toString() {
		return "[Client-" + nom + "]";
	}
	
	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}
}
