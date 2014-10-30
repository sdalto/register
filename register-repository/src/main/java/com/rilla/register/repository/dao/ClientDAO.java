package com.rilla.register.repository.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.db4o.ObjectContainer;
import com.rilla.register.repository.DB4OUtil;
import com.rilla.register.repository.model.Client;

@Component
public class ClientDAO {

	public void addClient(Client client) {
		client.setId(UUID.randomUUID());
		ObjectContainer db = DB4OUtil.open();
		DB4OUtil.saveDB4O(client, db);
		db.close();
	}
	
	public void delete(Client client) {
		ObjectContainer db = DB4OUtil.open();
		DB4OUtil.deleteDB4O(client, db);
		db.close();
	}
	
	public void update(Client client) {
		ObjectContainer db = DB4OUtil.open();
		Client example = new Client();
		example.setId(client.getId());
		DB4OUtil.updateDB4OClient(example, db, client);
		db.close();
	}

	public List<Client> getAllClients(UUID companyId) {
		ObjectContainer db = DB4OUtil.open();
		List<Client> list = DB4OUtil.readAll(db, Client.class);
		List<Client> result = new LinkedList<>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (Client client : list) {
				Client c = new Client();
				c.setId(client.getId());
				c.setName(client.getName());
				c.setCompanyId(client.getCompanyId());
				c.setNumber(client.getNumber());
				result.add(c);
			}
			return result;
		}
		db.close();
		return result;
	}
	
}
