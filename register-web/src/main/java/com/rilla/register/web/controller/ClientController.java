package com.rilla.register.web.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.collections.CollectionUtils;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.web.bind.annotation.RequestBody;

import com.rilla.register.repository.model.Client;
import com.rilla.register.repository.model.Company;
import com.rilla.register.web.spring.Services;

@ManagedBean
@SessionScoped
public class ClientController {

	private List<Company> companies = new LinkedList<>();
	private List<Client> clients = new LinkedList<>();

	private UUID companyId;
	private Company company;
	private String name;
	private String number;

	public List<Company> getCompanies() {
		return companies;
	}

	public List<Company> getCompanies(String query) {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	public UUID getCompanyId() {
		return companyId;
	}

	public void setCompanyId(UUID companyId) {
		this.companyId = companyId;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	// Methods

	@PostConstruct
	public void init() {
		this.companies = Services.FACADE.companyBean.getAll();
		if (CollectionUtils.isNotEmpty(companies)) {
			company = companies.get(0);
		}
		if(company != null){
			clients = Services.FACADE.clientBean.getAll(company.getId());
		}
		
	}
	
	public String getLoginStatus() {
		this.companies = Services.FACADE.companyBean.getAll();
		if (CollectionUtils.isNotEmpty(companies) && company == null) {
			company = companies.get(0);
		}
		if(company != null){
			clients = Services.FACADE.clientBean.getAll(company.getId());
		}
		return "";
	}

	public void onClientRowEdit(RowEditEvent event) {
		Services.FACADE.clientBean.update((Client) event.getObject());
		FacesMessage msg = new FacesMessage("Se ha editado el cliente",
				((Client) event.getObject()).getId() + "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowSelect(RowEditEvent event) {
		Services.FACADE.clientBean.update((Client) event.getObject());
		FacesMessage msg = new FacesMessage("Se ha editado el cliente",
				((Client) event.getObject()).getId() + "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void remove(Client client) {
		try {
			if (client != null) {
				Services.FACADE.clientBean.deleteClient(client);
				clients.remove(client);
				FacesMessage msg = new FacesMessage(
						"Se ha eliminado el cliente", client.getName());
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
								"Por favor, seleccione un cliente."));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onClientRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage(
				"Se ha cancelado la edicion del cliente",
				((Client) event.getObject()).getId() + "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void add() {
		Client newClient = new Client();
		newClient.setName(name);
		newClient.setCompanyId(company.getId());
		newClient.setNumber(number);

		Services.FACADE.clientBean.add(newClient);
		clients.add(newClient);
		name = null;
		number = null;

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage("Se ha guardado el cliente "
						+ newClient.getName()));
	}

	public void addClient(@RequestBody Client company) {
		Services.FACADE.clientBean.add(company);
	}

	public List<Client> getAllClients() {
		return Services.FACADE.clientBean.getAll(companyId);
	}

	// Auxiliares
	public void onCompanyItemSelect(SelectEvent event) {
		clients = Services.FACADE.clientBean.getAll(company.getId());
	}

	public void setLoginStatus(String loginStatus) {

	}
}
