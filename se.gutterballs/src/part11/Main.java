package part11;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		//@mail: benjamin.rouxel@inria.fr
		
		int tailleGroupes = 4;
		int nbGroupes = 3;
		int nbClients = tailleGroupes * nbGroupes;
		
		Bowling bowling = new Bowling(tailleGroupes);
		ArrayList<Thread> clients = new ArrayList<Thread>();
		
		for(int i = 0 ; i < nbClients ; i++) {
			Client client = new Client(bowling);
			Thread th = new Thread(client);
			clients.add(th);
			th.start();
		}
		
		clients.forEach(client -> {
		try {
			client.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}});
		
		
		System.out.println("-------------- stats -------------");
	}
}
