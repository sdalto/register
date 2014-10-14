package com.rilla.register.repository.model;

import java.io.Serializable;
import java.util.UUID;

public class Client implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private UUID companyId;
	private String name;
	private String number;

	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public UUID getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(UUID companyId) {
		this.companyId = companyId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
