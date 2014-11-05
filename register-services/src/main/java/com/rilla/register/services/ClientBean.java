package com.rilla.register.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rilla.register.repository.dao.ClientDAO;
import com.rilla.register.repository.model.Client;

@Service
public class ClientBean {
	
	@Autowired
	private ClientDAO clientDao;
	
	public Client findByName(String clientName) {
		return new Client();
	}

	public void add(Client client) {
		clientDao.addClient(client);
	}
	
	public void update(Client client) {
		clientDao.update(client);
	}

	public List<Client> getAll(UUID companyId) {
		return clientDao.getAllClients(companyId);
	}
	
	public Map<String, String> getMap(UUID companyId) {
		List<Client> clients = clientDao.getAllClients(companyId);
		Map<String, String> map = new HashMap<String, String>();
		if(CollectionUtils.isNotEmpty(clients)){
			for (Client client : clients) {
				map.put(client.getName(), client.getNumber());
			}
		}		
		return map;
	}
	
	public void deleteClient(Client client) {
		clientDao.delete(client);
	}
}
