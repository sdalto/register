package com.rilla.register.repository;

import java.math.BigDecimal;
import java.util.Date;

import com.rilla.register.repository.constants.EntryType;
import com.rilla.register.repository.model.Company;

public class AccountingEntry {

	private int id;
	private Date date;
	private String concept;
	private int quipusRut;
	private String account;
	private String clientName;
	private BigDecimal subtotalAmount = BigDecimal.ZERO;
	private BigDecimal ivaAmount = BigDecimal.ZERO;
	private BigDecimal totalAmount = BigDecimal.ZERO;
	private String currency;
	private EntryType type;
	private Date dueDate;
	private Company company;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public int getQuipusRut() {
		return quipusRut;
	}

	public void setQuipusRut(int quipusRut) {
		this.quipusRut = quipusRut;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public BigDecimal getSubtotalAmount() {
		return subtotalAmount;
	}

	public void setSubtotalAmount(BigDecimal subtotalAmount) {
		this.subtotalAmount = subtotalAmount;
	}

	public BigDecimal getIvaAmount() {
		return ivaAmount;
	}

	public void setIvaAmount(BigDecimal ivaAmount) {
		this.ivaAmount = ivaAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public EntryType getType() {
		return type;
	}

	public void setType(EntryType type) {
		this.type = type;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}

}
