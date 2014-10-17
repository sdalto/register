package com.rilla.register.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.rilla.register.repository.AccountingEntry;
import com.rilla.register.web.spring.Services;

@ManagedBean
@SessionScoped
public class HomeController {

	private String provider;
	private String fileName;
	
	private List<String> providers;
	private List<AccountingEntry> movements; 

	// Getters and Setters
	
	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public List<String> getProviders() {
		return providers;
	}
	
	public void setProviders(List<String> providers) {
		this.providers = providers;
	}
	
	public List<AccountingEntry> getMovements() {
		return movements;
	}
	
	public void setMovements(List<AccountingEntry> movements) {
		this.movements = movements;
	}

	// Methods
	
	@PostConstruct
    public void init(){
		List<String> p = new LinkedList<String>();
		p.add("Zenga");
		setProviders(p);
		provider = providers.get(0);
    }
	
	public List<String> getProviders(String query) {
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
			fileName = event.getFile()
					.getFileName();
			movements = Services.FACADE.excelReaderBean
					.readFile(file, "provider", "company", fileName);
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error reading file" + e, null));
		}
	}
	
	public StreamedContent getDownloadFile(){
		InputStream stream = Services.FACADE.dbfGenerator.createFile("/home/sdalto/Descargas/IMPORTA.dbf", movements);
		return new DefaultStreamedContent(stream, "application/xls", "/home/sdalto/Descargas/IMPORTA.dbf");
	}
	
}