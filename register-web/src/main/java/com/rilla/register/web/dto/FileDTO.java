package com.rilla.register.web.dto;

import java.io.Serializable;

public class FileDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String path;
	private String providerName;
	private String companyName;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
