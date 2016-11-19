package part1;

import java.util.ArrayList;
import java.util.List;

public class Groupe {
	//extends ThreadGroup ?
	//groupe de 8
	
	private List<Client> clients;//Client or Thread
	
	public Groupe() {
		this.clients = new ArrayList<>();
	}
	
	public synchronized boolean isComplete() {
		return this.clients.size() == 8;
	}
	
	public synchronized void addClient(Client c) {
		if(!isComplete()) {
			this.clients.add(c);
			c.setGroupe(this);
		}
	}
}
