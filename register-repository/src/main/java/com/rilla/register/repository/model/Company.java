package com.rilla.register.repository.model;

import java.io.Serializable;
import java.util.UUID;

public class Company implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;
	private String name;
	private String subtotalAccount = "1234";
	private String ivaAccount = "4321";
	private String totalAccount = "6789";

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

	public String getSubtotalAccount() {
		return subtotalAccount;
	}

	public void setSubtotalAccount(String subtotalAccount) {
		this.subtotalAccount = subtotalAccount;
	}

	public String getIvaAccount() {
		return ivaAccount;
	}

	public void setIvaAccount(String ivaAccount) {
		this.ivaAccount = ivaAccount;
	}

	public String getTotalAccount() {
		return totalAccount;
	}

	public void setTotalAccount(String totalAccount) {
		this.totalAccount = totalAccount;
	}

}
