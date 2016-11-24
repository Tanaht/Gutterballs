package part1;

import java.util.ArrayList;
import java.util.List;

public class Bowling {
	private List<Piste> pistes;
	private SalleChaussure salleChaussure;
	private Guichet guichet;
	protected static final int PISTE_INDISPONIBLE = -1;

	public Bowling(int nbPistes, int capaciteGroupe) {
		this.salleChaussure = new SalleChaussure();
		this.guichet = new Guichet(capaciteGroupe);
		this.pistes = new ArrayList<Piste>();
		
		for(int i = 0 ; i < nbPistes ; i++) {
			this.pistes.add(i, new Piste(i));
		}
	}
	
	public void arriverClient(Client c) {
		guichet.inscription(c);
		System.out.println(c + " va prendre ses chaussures");
		salleChaussure.prendre(c);
		
		while(!pisteLibre() && c.getGroupe().pisteReservee() == PISTE_INDISPONIBLE){
			this.danser(c);
		}
		
		if(c.getGroupe().pisteReservee() == PISTE_INDISPONIBLE){
			int pisteLibre = laquelleLibre();
			System.out.println("La piste "+pisteLibre+" est libre , le client "+c.getNom()+" du groupe "+  c.getGroupe().getNom()+" occupe cette piste");
			prendrePiste(c, pisteLibre);
		}
		else{
			
			System.out.println("Le client "+c.getNom()+" rejoint son groupe "+c.getGroupe().getNom()+" sur la piste "+c.getGroupe().pisteReservee());
			prendrePiste(c, c.getGroupe().pisteReservee());
		}
		
		Piste p = pistes.get(c.getGroupe().pisteReservee());
		p.jouer(c);
		
		System.out.println("Le client "+c.getNom()+" du groupe "+c.getGroupe().getNom()+" a fini de jouer au bowling");
		p.quitter(c);
		
		guichet.paiement(c);
		System.out.println("Le client "+c.getNom() +" du groupe " +c.getGroupe().getNom()+ " a fini de payer");
		salleChaussure.restituer(c);
		System.out.println("Le client "+c.getNom() +" du groupe " +c.getGroupe().getNom()+ " a rendu ses chaussures et a quitte le bowling");
	}
	
	public synchronized boolean pisteLibre(){
		boolean res = false;
		for(Piste p: pistes){
			res = res || p.estLibre();
		}
		return res;
	}
	
	private synchronized void danser(Client c) {
		System.out.println(c.getNom()+ " danse du groupe "+c.getGroupe().getNom());
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void prendrePiste(Client c, int numPiste){
		Piste p = pistes.get(numPiste);
		p.entrerPiste(c);
		c.getGroupe().setNumPiste(numPiste);
		
		notifyAll();
		
		while(!c.getGroupe().tousSurPiste()){
			
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
	}
	
	
	public synchronized int laquelleLibre() {
		for(int i = 0 ; i < this.pistes.size() ; i++) {
			if(this.pistes.get(i).estLibre())
				return i;
		}
		return PISTE_INDISPONIBLE;
		
	}

	public void nouveauClient(Client client) {
		guichet.inscription(client);
		System.out.println("Le client " + " " + Thread.currentThread().getName() + " appartient au groupe: " + client.getGroupe());
	}
}
