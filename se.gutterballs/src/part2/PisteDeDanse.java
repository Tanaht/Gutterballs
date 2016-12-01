package part2;

import java.util.LinkedList;

public class PisteDeDanse {
	/**
	* true si aucune piste n'est libre, si non false
	*/
	private boolean pisteLibre;
	/**
	* groupe prioritaire pouvant sortir de la piste
	*/
	private Groupe prochainGroupeQuiJoue;
	/**
	* Fifo sur les groupes qui sont arrives sur la piste de danse
	*/
	private LinkedList<Groupe> fifo;
	/**
	* constructeur
	*/
	public PisteDeDanse() {
		this.pisteLibre = true;
		this.fifo = new LinkedList<>();
	}
	/**
	* fait danser le client tant qu'aucune piste n'est disponible, 
	* si le groupe du client est le plus ancien sur la piste il sera le premier a sortir
	* @param client allant danser
	*/
	public synchronized void echauffement(Client client) {
		if(!this.fifo.contains(client.getGroupe())) {
			System.out.println("ON EMPILE ----------> " + client.getGroupe());
			this.fifo.add(client.getGroupe());
		}
		
		while( (!pisteLibre && client.getGroupe() != this.prochainGroupeQuiJoue) || (pisteLibre && client.getGroupe() != this.fifo.getFirst()) ) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(this.pisteLibre) {
			Groupe groupe = this.fifo.removeFirst();
			
			if(groupe != client.getGroupe())
				System.err.println("ERREUR " + groupe + " != " + client.getGroupe());
			
			this.prochainGroupeQuiJoue = groupe;
			System.out.println("ON DEPILE ----------> " + groupe);
			this.pistesOccupee();
		}
		
		System.out.println(client + "" + client.getGroupe() + "[Fin Echauffement]");
		notifyAll();
	}
	/**
	* prmet de notifier la liberation d'une piste
	*/
	public synchronized void pisteLiberee() {
		System.out.println("[PisteDeDanse][Une piste est libre]");
		this.pisteLibre = true;
		notify();
	}
	/**
	* permet de prendre la piste venant d'etre liberee
	*/
	public synchronized void pistesOccupee() {
		System.out.println(Thread.currentThread().getName() + "[PisteDeDanse][les pistes sont occupees]");
		this.pisteLibre = false;
		notify();
	}
}
