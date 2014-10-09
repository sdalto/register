package com.rilla.register.services;

import org.springframework.stereotype.Service;

import com.rilla.register.repository.model.Provider;

@Service
public class ProviderBean {

	public Provider findByName(String providerName) {
		return new Provider();
	}
	
}
