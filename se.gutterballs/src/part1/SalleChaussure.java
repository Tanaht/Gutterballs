package part1;

public class SalleChaussure {
	
	public synchronized void prendre(Client c) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Le client "+c.getNom()+" prend ses chaussures");
		c.recevoirChaussuresBoowling();
		notifyAll();
		while(!c.getGroupe().toutesChaussuresBoowling()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
	}
	
	public synchronized void restituer(Client c) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Le client "+c.getNom()+" rend ses chaussures");
		c.rendreChaussres();
		notifyAll();
	}
}
