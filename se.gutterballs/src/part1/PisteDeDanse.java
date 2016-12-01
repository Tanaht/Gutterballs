package part1;

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
	* constructeur
	*/
	public PisteDeDanse() {
		this.pisteLibre = true;
	}
	/**
	* fait dans le client tant qu'aucune piste n'est disponible
	* @param client allant danser
	*/
	public synchronized void echauffement(Client client) {
		while(!pisteLibre && client.getGroupe() != this.prochainGroupeQuiJoue) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(this.pisteLibre) {
			this.prochainGroupeQuiJoue = client.getGroupe();
			this.pistesOccupee();
		}
		notifyAll();
	}
	
	/**
	* prmet de notifier la liberation d'une piste
	*/
	public synchronized void pisteLiberee() {
		System.out.println("[PisteDeDanse][Une piste est libérée]");
		this.pisteLibre = true;
		notify();
	}
	/**
	* permet de prendre la piste venant d'etre liberee
	*/
	public synchronized void pistesOccupee() {
		System.out.println(Thread.currentThread().getName() + "[PisteDeDanse][les pistes sont occupée]");
		this.pisteLibre = false;
		notify();
	}
}
