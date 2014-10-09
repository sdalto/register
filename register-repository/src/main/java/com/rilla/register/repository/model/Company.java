package com.rilla.register.repository.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Company implements Serializable{

	private static final long serialVersionUID = 1L;
		
	private UUID id;
	private String name;
	private List<Client> clients = new LinkedList<Client>();

	public UUID getId() {
		return id;
	}
	
	public void setId(UUID uuid) {
		this.id = uuid;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

}
