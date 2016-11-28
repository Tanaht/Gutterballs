package part1;

public class PisteDeDanse {
	private boolean pisteLibre;
	private Groupe prochainGroupeQuiJoue;
	
	public PisteDeDanse() {
		this.pisteLibre = true;
	}
	
	public synchronized void echauffement(Client client) {
		while(!pisteLibre && client.getGroupe() != this.prochainGroupeQuiJoue) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(this.pisteLibre) {
			this.prochainGroupeQuiJoue = client.getGroupe();
			this.pistesOccupee();
		}
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
