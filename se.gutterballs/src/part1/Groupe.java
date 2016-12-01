package part1;

import java.util.ArrayList;
import java.util.List;

public class Groupe {
	/**
	 * La capacité du groupe
	 */
	private final int capacite;
	/**
	* correspond au nombre de client faisant parti du groupe ayant pris leurs chaussures de bowling
	*/
	private int clientChausser;
	/**
	* correspond au nombre de client presents sur la piste de bowling que le groupe a reserve
	*/
	private int clientSurPiste;
	/**
	* num d'identification du groupe
	*/
	private int id;
	/**
	 * Les clients du groupe
	 */
	private List<Client> clients;
	
	/**
	* constructeur
	* @param id identifiant du groupe
	* param capacite taille maximale du groupe
	*/
	public Groupe(int id, int capacite) {
		this.capacite = capacite;
		this.id = id;
		this.clients = new ArrayList<>();
		this.clientChausser = 0;
		this.clientSurPiste = 0;
	}
	
	/**
	*
	* @return true si le nombre de client dans le groupe est egal a la capacite, si non false
	*/
	public synchronized boolean isComplete() {
		return this.clients.size() == capacite;
	}
	
	/**
	* ajoute un client au groupe
	* @param client a ajouter
	*/
	public synchronized void addClient(Client client) {
		this.clients.add(client);
		client.setGroupe(this);
		System.out.println(this + "[inscription]"+client);
		notify();//TODO: facultatif ?
	}
	
	/**
	* met en attente le groupe tant que celui-ci n'a pas atteint sa capacite max
	*/
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
	
	/**
	* permet d'incrementer clientChausser
	* @param client venant de prendre une paire de chaussure
	*/
	public synchronized void addClientChausser(Client client) {
		this.clientChausser++;
		System.out.println(this + "" + client + "[prend une paire]");
		notify();
	}
	
	/**
	* permet au client de prevenir quand il est arrive sur la piste de bowling
	* @param client etant arrive sur la piste de bowling
	*/
	public synchronized void addClientSurPiste(Client client) {
		this.clientSurPiste++;
		System.out.println(this + "" + client + "[arrive sur piste]");
		notify();
	}
	
	/**
	* met en attente le client tant que tous les membres du groupe n'ont pas pris leurs chaussures
	* @param client devant attendre
	*/
	public synchronized void waitAllChausser(Client client) {
		while(this.clientChausser != capacite) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(this + "" + client + "[Chaussée]");
		notify();
	}
	
	/**
	* met en attente le client tant qye tous les membres du groupe ne sont pas arrives sur la pistre de bowling
	* @param client devant attendre
	*/
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

	/**
	*
	* @return le nombre de client presents dans le groupe
	*/
	public synchronized int getSize() {
		return this.clients.size();
	}
}
