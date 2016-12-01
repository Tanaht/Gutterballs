package part2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

public class Main {
	public static void main(String[] args) {
		int nbGroupe = 10, capaciteGroupe = 8, nbPistes = 5;
		
		Bowling bowling = new Bowling(nbPistes, capaciteGroupe);
		ArrayList<Thread> clients = new ArrayList<Thread>();
		
		for(int i = 0 ; i < nbGroupe * capaciteGroupe ; i++) {
			Client client = new Client(bowling, i);
			Thread th = new Thread(client);
			clients.add(th);
			th.start();
		}
		
		for(Thread client : clients) {
			try {
				client.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("==============STATS=============");
		System.out.println("Nombre de parties jouees: " + bowling.getTotalParties());
		System.out.println("Parties par pistes:");
		
		for (Iterator<Entry<Piste, Integer>> iterator =  bowling.getPartiesParPiste().entrySet().iterator(); iterator.hasNext();) {
			Entry<Piste, Integer>  entry = iterator.next();
			System.out.println(entry.getKey() + "[" + entry.getValue() + " parties]");
			
		}
	}
}
