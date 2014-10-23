package com.rilla.register.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.collections.CollectionUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.rilla.register.repository.AccountingEntry;
import com.rilla.register.repository.model.Company;
import com.rilla.register.repository.model.Provider;
import com.rilla.register.web.spring.Services;

@ManagedBean
@SessionScoped
public class HomeController {

	private Provider selectedProvider;
	private Company selectedCompany;
	private String fileName;
	private String currency = "$";

	private List<Provider> providers;
	private List<Company> companies;
	private List<AccountingEntry> movements;

	// Getters and Setters

	public Provider getSelectedProvider() {
		return selectedProvider;
	}

	public void setSelectedProvider(Provider selectedProvider) {
		this.selectedProvider = selectedProvider;
	}

	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public List<Provider> getProviders() {
		return providers;
	}

	public void setProviders(List<Provider> providers) {
		this.providers = providers;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public List<AccountingEntry> getMovements() {
		return movements;
	}

	public void setMovements(List<AccountingEntry> movements) {
		this.movements = movements;
	}

	// Methods

	@PostConstruct
	public void init() {
		this.providers = Services.FACADE.providerBean.getProviders();
		selectedProvider = providers.get(0);

		this.companies = Services.FACADE.companyBean.getAll();
		if (CollectionUtils.isNotEmpty(companies)) {
			selectedCompany = companies.get(0);
		}
	}

	public List<Provider> getProviders(String query) {
		return providers;
	}

	public void clear(ActionEvent actionEvent) {
		this.movements = null;
		this.fileName = null;
		addMessage("Termina...");
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void onProviderItemSelect(SelectEvent event) {
	}

	public void onCurrencyItemSelect(SelectEvent event) {
	}
	
	public void onCompanyItemSelect(SelectEvent event) {
	}

	public void handleFileUpload(FileUploadEvent event) {
		if (event.getFile().equals(null)) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"File is null", null));
		}
		InputStream file;
		try {
			file = event.getFile().getInputstream();
			fileName = event.getFile().getFileName();
			movements = Services.FACADE.excelReaderBean.readFile(file,
					selectedProvider, selectedCompany, fileName, currency);
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error reading file" + e, null));
		}
	}

	public StreamedContent getDownloadFile() {
		InputStream stream = Services.FACADE.dbfGenerator.createFile(
				"/home/sdalto/Descargas/IMPORTA.dbf", movements);
		return new DefaultStreamedContent(stream, "application/xls",
				"/home/sdalto/Descargas/IMPORTA.dbf");
	}

}