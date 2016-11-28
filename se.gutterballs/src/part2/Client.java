package part2;

public class Client implements Runnable{
	private Groupe groupe;
	private int chaussures;
	private Bowling bowling;
	
	public Client(Bowling b) {
		this.bowling = b;
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
		// TODO Auto-generated method stub
		return "[" + Thread.currentThread().getName() + "]";
	}
	
	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}
}
