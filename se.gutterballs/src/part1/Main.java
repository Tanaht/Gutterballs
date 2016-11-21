package part1;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		//@mail: benjamin.rouxel@inria.fr
		
		int nbPistes = 3;
		int tailleGroupes = 8;
		int nbGroupes = 6;
		int nbClients = tailleGroupes * nbGroupes;
		
		Bowling b = new Bowling(nbPistes, tailleGroupes);
		ArrayList<Thread> clients = new ArrayList<Thread>();
		
		for(int i = 0 ; i < nbClients ; i++) {
			Client c = new Client(b,"c"+i);
			Thread th = new Thread(c);
			clients.add(th);
			th.start();
		}
		
		clients.forEach(c-> {
		try {
			c.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}});
		
		
		System.out.println("-------------- stats -------------");
	}
}
