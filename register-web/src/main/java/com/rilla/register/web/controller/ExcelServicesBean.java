package com.rilla.register.web.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.rilla.register.repository.AccountingEntry;
import com.rilla.register.web.spring.Services;

@ManagedBean(name = "excelServicesBean")
@ViewScoped
public class ExcelServicesBean {

//	public List<AccountingEntry> readFile(String path, String providerName,
//			String companyName) {
//		return Services.FACADE.excelReaderBean.readFile(path, providerName, companyName);
//	}
	
	public void createFile(String path, List<AccountingEntry> entries) {
		Services.FACADE.excelGeneratorBean.createFile(path, entries);
	}
	
}
