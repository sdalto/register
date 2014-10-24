package com.rilla.register.repository.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.db4o.ObjectContainer;
import com.rilla.register.repository.DB4OUtil;
import com.rilla.register.repository.model.Client;
import com.rilla.register.repository.model.Company;

@Component
public class CompanyDAO {

	public void addCompany(Company company) {
		company.setId(UUID.randomUUID());
		ObjectContainer db = DB4OUtil.open();
		DB4OUtil.saveDB4O(company, db);
		db.close();
	}
	
	public void delete(Company company) {
		ObjectContainer db = DB4OUtil.open();
		DB4OUtil.deleteDB4O(company, db);
		db.close();
	}
	
	public void update(Company company) {
		ObjectContainer db = DB4OUtil.open();
		Company example = new Company();
		example.setId(company.getId());
		DB4OUtil.updateDB4OCompany(example, db, company);
		db.close();
	}

	public List<Company> getAllCompanies() {
		ObjectContainer db = DB4OUtil.open();
		List<Company> list = DB4OUtil.readAll(db, Company.class);
		List<Company> result = new LinkedList<>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (Company company : list) {
				Company c = new Company();
				c.setId(company.getId());
				c.setName(company.getName());
				c.setLegalName(company.getLegalName());
				c.setSubtotalAccount(company.getSubtotalAccount());
				c.setIvaAccount(company.getIvaAccount());
				c.setTotalAccount(company.getTotalAccount());
				result.add(c);
			}
			return result;
		}
		db.close();
		return result;
	}

//	private List<Client> getClients(Company company) {
//		if (CollectionUtils.isNotEmpty(company.getClients())) {
//			List<Client> clients = new LinkedList<>();
//			for (Client client : company.getClients()) {
//				Client cl = new Client();
//
//				cl.setDescription(client.getDescription());
//				cl.setNumber(client.getNumber());
//				clients.add(cl);
//			}
//			return clients;
//		}
//		return null;
//	}

}
