package part1;

import java.util.ArrayList;
import java.util.List;

public class Bowling {
	private List<Piste> pistes;//x10
	private Danse danse;
	private SalleChaussure salleChaussure;
	private Guichet guichet;
	
	/**
	 * Initialisation du Bowling,
	 * Il y a 10 pistes de Jeu.
	 */
	public Bowling() {
		this.danse = new Danse();
		this.salleChaussure = new SalleChaussure();
		this.guichet = new Guichet();
		
		this.pistes = new ArrayList<>();
		
		for(int i = 0 ; i < 3 ; i++) {
			this.pistes.add(i, new Piste(i));
		}
	}
	
	public  void arriverClient(Client c) {
		guichet.inscription(c);
		
		salleChaussure.prendre(c);
		
		while(!pisteLibre() && c.getGroupe().pisteReservee() == -1){
			this.danser(c);
		}
		
		
		
		if(c.getGroupe().pisteReservee() == -1){
			int pisteLibre = laquelleLibre();
			System.out.println("La piste "+pisteLibre+" est libre , le client "+c.getNom()+"du groupe "+  c.getGroupe().getNom()+" occupe cette piste");
			
			prendrePiste(c, pisteLibre);
		}
		else{
			
			System.out.println("Le client "+c.getNom()+" rejoint son groupe "+c.getGroupe().getNom()+" sur la piste "+c.getGroupe().pisteReservee());
			prendrePiste(c, c.getGroupe().pisteReservee());
		}
		
		
		salleChaussure.restituer(c);
		guichet.paiement(c);
		
	}
	
	public synchronized boolean pisteLibre(){
		boolean res = false;
		for(Piste p: pistes){
			res = res || p.estLibre();
		}
		return res;
	}
	public synchronized void danser(Client c) {
		System.out.println(c.getNom()+ "danse du groupe "+c.getGroupe().getNom());
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public synchronized void prendrePiste(Client c, int numPiste){
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

		System.out.println("Le client "+c.getNom()+" du groupe "+c.getGroupe().getNom()+" joue enfin au bowling");

		p.jouer(c);
		
		notifyAll();
		while(!c.getGroupe().tousjouer()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Le client "+c.getNom()+" du groupe "+c.getGroupe().getNom()+" a fini du jouer au bowling");
		p.quitter(c);
		
		if(p.estLibre()){
			System.out.println("Le groupe "+c.getGroupe().getNom()+" vient de finir de jouer sur la piste "+p.getNumero());
			notifyAll();
		
		}
		
	}
	
	
	public synchronized int laquelleLibre() {
		for(int i = 0 ; i < this.pistes.size() ; i++) {
			if(this.pistes.get(i).estLibre())
				return i;
		}
		return -1;
		
	}
}
