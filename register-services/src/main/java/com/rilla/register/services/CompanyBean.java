package com.rilla.register.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rilla.register.repository.dao.CompanyDAO;
import com.rilla.register.repository.model.Company;

@Service
public class CompanyBean {

	@Autowired
	private CompanyDAO companyDao;
	
	public Company findByName(String companyName) {
		return new Company();
	}

	public void add(Company company) {
		companyDao.addCompany(company);
	}
	
	public void update(Company company) {
		companyDao.update(company);
	}

	public List<Company> getAll() {
		return companyDao.getAllCompanies();
	}
	
	public void deleteCompany(Company company) {
		companyDao.delete(company);
	}
}
