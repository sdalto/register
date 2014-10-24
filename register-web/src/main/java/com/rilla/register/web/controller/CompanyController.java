package com.rilla.register.web.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;
import org.springframework.web.bind.annotation.RequestBody;

import com.rilla.register.repository.model.Client;
import com.rilla.register.repository.model.Company;
import com.rilla.register.web.spring.Services;

@ManagedBean
@SessionScoped
public class CompanyController {

	private List<Company> companies;
	private List<Company> filteredCompanies;

	private String name;
	private String legalName;
	private String subtotal;
	private String iva;
	private String total;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}

	public String getIva() {
		return iva;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	@PostConstruct
	public void init() {
		companies = getAllCompanies();
	}

	public void onCompanyRowEdit(RowEditEvent event) {
		Services.FACADE.companyBean.update((Company) event.getObject());
		FacesMessage msg = new FacesMessage("Se ha editado la empresa",
				((Company) event.getObject()).getId() + "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowSelect(RowEditEvent event) {
		Services.FACADE.companyBean.update((Company) event.getObject());
		FacesMessage msg = new FacesMessage("Se ha editado la empresa",
				((Company) event.getObject()).getId() + "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void remove(Company company) {
	    try {
	    	if (company != null) {
				Services.FACADE.companyBean.deleteCompany(company);
				companies.remove(company);
				FacesMessage msg = new FacesMessage("Se ha eliminado la empresa",
						company.getName());
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public void onCompanyRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage(
				"Se ha cancelado la edicion de la empresa",
				((Company) event.getObject()).getId() + "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onClientRowEdit(RowEditEvent event) {
		Services.FACADE.companyBean.update((Company) event.getObject());
		FacesMessage msg = new FacesMessage("Se ha editado la empresa",
				((Client) event.getObject()).getId() + "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onClientRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage(
				"Se ha cancelado la edicion del cliente",
				((Client) event.getObject()).getId() + "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void add() {
		Company newCompany = new Company();
		newCompany.setName(name);
		newCompany.setLegalName(legalName);
		newCompany.setSubtotalAccount(subtotal);
		newCompany.setIvaAccount(iva);
		newCompany.setTotalAccount(total);

		Services.FACADE.companyBean.add(newCompany);
		companies.add(newCompany);
		name = null;
		legalName = null;
		subtotal = null;
		iva = null;
		total = null;

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage("Se ha guardado la empresa "
						+ newCompany.getName()));
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

	public void addCompany(@RequestBody Company company) {
		Services.FACADE.companyBean.add(company);
	}

	public List<Company> getAllCompanies() {
		return Services.FACADE.companyBean.getAll();
	}

}
