package part2;

import java.util.ArrayList;
import java.util.List;

public class Groupe {
	/**
	 * La capacité du groupe
	 */
	private final int capacite;
	private int clientChausser;
	private int clientSurPiste;
	private int id;
	/**
	 * Les clients du groupe
	 */
	private List<Client> clients;
	
	public Groupe(int id, int capacite) {
		this.capacite = capacite;
		this.id = id;
		this.clients = new ArrayList<>();
		this.clientChausser = 0;
		this.clientSurPiste = 0;
	}
	
	public synchronized boolean isComplete() {
		return this.clients.size() == capacite;
	}
	
	public synchronized void addClient(Client client) {
		this.clients.add(client);
		client.setGroupe(this);
		System.out.println(this + "[inscription]"+client);
		notify();//TODO: facultatif ?
	}
	
	public synchronized void waitUntilComplete() {
		while(!this.isComplete()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println(this + "[" + Thread.currentThread().getName() + "][Complet]");
		notify();
	}
	
	public synchronized void addClientChausser(Client client) {
		this.clientChausser++;
		System.out.println(this + "" + client + "[prend une paire]");
		notify();
	}
	
	public synchronized void addClientSurPiste(Client client) {
		this.clientSurPiste++;
		System.out.println(this + "" + client + "[arrive sur piste]");
		notify();
	}
	
	public synchronized void waitAllChausser(Client client) {
		while(this.clientChausser != capacite) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println(this + "" + client + "[Chaussée]");
		notify();
	}
	
	public synchronized void waitAllSurPiste(Client client) {
		while(this.clientSurPiste != capacite) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(this + "" + client + "[sur piste]");
		notify();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[G-" + id + "]";
	}

	public synchronized int getSize() {
		return this.clients.size();
	}
}
