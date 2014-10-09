package com.rilla.register.web.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;
import org.springframework.web.bind.annotation.RequestBody;

import com.rilla.register.repository.model.Company;
import com.rilla.register.web.spring.Services;

@ManagedBean
@SessionScoped
public class CompanyController {

	private List<Company> companies;

	private List<Company> filteredCompanies;

	private String newCompanyName;
	
	private Company selectedCompany;

	@PostConstruct
	public void init() {
		companies = getAllCompanies();
	}

	public void onRowEdit(RowEditEvent event) {
		Services.FACADE.companyBean.update((Company) event.getObject());
		FacesMessage msg = new FacesMessage("Se ha editado el cliente",
				((Company) event.getObject()).getId() + "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage(
				"Se ha cancelado la edicion del cliente",
				((Company) event.getObject()).getId() + "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void add() {
		Company newCompany = new Company();
		newCompany.setName(newCompanyName);
		
		Services.FACADE.companyBean.add(newCompany);
		companies.add(newCompany);
		
		newCompanyName = null;
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Se ha guardado la empresa " + newCompany.getName()));
	}

	public void delete() {
		Services.FACADE.companyBean.deleteCompany(selectedCompany);
		companies.remove(selectedCompany);
		selectedCompany = new Company();
		FacesMessage msg = new FacesMessage(
				"Se ha cancelado la edicion del cliente", selectedCompany.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public List<Company> getFilteredCompanies() {
		return filteredCompanies;
	}

	public void setFilteredCompanies(List<Company> filteredCompanies) {
		this.filteredCompanies = filteredCompanies;
	}

	public String getNewCompanyName() {
		return newCompanyName;
	}

	public void setNewCompanyName(String newCompanyName) {
		this.newCompanyName = newCompanyName;
	}

	public void addCompany(@RequestBody Company company) {
		Services.FACADE.companyBean.add(company);
	}

	public List<Company> getAllCompanies() {
		return Services.FACADE.companyBean.getAll();
	}

	public Company getSelectedCompany() {
		return selectedCompany;
	}
	
	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}
}
