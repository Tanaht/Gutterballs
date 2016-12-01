package part2;

public class Client implements Runnable{
	/**
	* groupe auquel appartient le client, peut être null
	*/
	private Groupe groupe;
	/**
	* nombre de paire de chaussure de bowling que le client a en sa possession
	* initialise a zero
	*/
	private int chaussures;
	/**
	* Bowling dans lequel va jouer le client
	*/
	private Bowling bowling;
	/**
	* nom permettant d'identifier le client
	*/
	private int nom;
	/**
	* constructeur
	* @param b bowling dans lequel va jouer le client
	* @param nom du client
	*/
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
	/**
	* fait prendre une paire de chaussure au client et prévient son groupe
	* cette methode est appelee a un stade d'execution ou le client a un groupe.
	*/
	public void putChaussures() {
		this.chaussures = 1;
		this.getGroupe().addClientChausser(this);
	}
	/**
	* fait rendre sa paire de chaussure au client 
	*/
	public void removeChaussures() {
		this.chaussures = 0;
		System.out.println(this + "[rend ses chaussures]");
	}
	
	@Override
	public String toString() {
		return "[Client-" + nom + "]";
	}
	/**
	* accesseur sur l'attribut groupe du client
	*/
	public Groupe getGroupe() {
		return groupe;
	}
	/**
	* permet d'attribuer un groupe au client
	* @param groupe ququel le client va appartenir
	*/
	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}
}
