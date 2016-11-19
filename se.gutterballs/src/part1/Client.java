package part1;

public class Client implements Runnable {
	private Bowling b;
	private Groupe g;
	
	public Client(Bowling b) {
		this.b = b;
		this.g = null;
	}
	
	@Override
	public void run() {
		b.arriverClient(this);
	}
	
	public void setGroupe(Groupe g) {
		this.g = g;
	}
	
	public Groupe getGroupe() {
		return this.g;
	}

}
