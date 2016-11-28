package part2;

import java.util.LinkedList;

public class PisteDeDanse {
	private boolean pisteLibre;
	private Groupe prochainGroupeQuiJoue;
	private LinkedList<Groupe> fifo;
	
	public PisteDeDanse() {
		this.pisteLibre = true;
		this.fifo = new LinkedList<>();
	}
	
	public synchronized void echauffement(Client client) {
		if(!this.fifo.contains(client.getGroupe())) {
			System.out.println("ON EMPILE ----------> " + client.getGroupe());
			this.fifo.add(client.getGroupe());
		}
		
		while( (!pisteLibre && client.getGroupe() != this.prochainGroupeQuiJoue) || (pisteLibre && client.getGroupe() != this.fifo.getFirst()) ) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
	
	public synchronized void pisteLiberee() {
		System.out.println("[PisteDeDanse][Une piste est libérée]");
		this.pisteLibre = true;
		notify();
	}
	
	public synchronized void pistesOccupee() {
		System.out.println(Thread.currentThread().getName() + "[PisteDeDanse][les pistes sont occupée]");
		this.pisteLibre = false;
		notify();
	}
}
