package com.rilla.register.repository.model;

import java.util.List;

import com.rilla.register.repository.constants.AmountType;
import com.rilla.register.repository.constants.FinalFileType;

public class Metadata {

	private AmountType amountType;
	private int firstRow;
	private FinalFileType finalFileType;
	private String finalValue;
	private int decimals;

	private Integer columnDate;
	private Integer columnConcept;
	private List<Integer> columnsConcept;
	private Integer columnRut;
	private Integer columnAccount;
	private Integer columnClientName;
	private Integer columnTotalAmount;
	private Integer columnIvaAmount;
	private Integer columnEntryType;

	public AmountType getAmountType() {
		return amountType;
	}

	public void setAmountType(AmountType amountType) {
		this.amountType = amountType;
	}

	public int getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	public FinalFileType getFinalFileType() {
		return finalFileType;
	}

	public void setFinalFileType(FinalFileType finalFileType) {
		this.finalFileType = finalFileType;
	}

	public String getFinalValue() {
		return finalValue;
	}

	public void setFinalValue(String finalValue) {
		this.finalValue = finalValue;
	}
	
	public int getDecimals() {
		return decimals;
	}
	
	public void setDecimals(int decimals) {
		this.decimals = decimals;
	}

	public Integer getColumnDate() {
		return columnDate;
	}

	public void setColumnDate(Integer columnDate) {
		this.columnDate = columnDate;
	}

	public Integer getColumnConcept() {
		return columnConcept;
	}

	public void setColumnConcept(Integer columnConcept) {
		this.columnConcept = columnConcept;
	}
	
	public List<Integer> getColumnsConcept() {
		return columnsConcept;
	}
	
	public void setColumnsConcept(List<Integer> columnsConcept) {
		this.columnsConcept = columnsConcept;
	}

	public Integer getColumnRut() {
		return columnRut;
	}

	public void setColumnRut(Integer columnRut) {
		this.columnRut = columnRut;
	}

	public Integer getColumnAccount() {
		return columnAccount;
	}

	public void setColumnAccount(Integer columnAccount) {
		this.columnAccount = columnAccount;
	}

	public Integer getColumnClientName() {
		return columnClientName;
	}

	public void setColumnClientName(Integer columnClientName) {
		this.columnClientName = columnClientName;
	}

	public Integer getColumnTotalAmount() {
		return columnTotalAmount;
	}
	
	public void setColumnTotalAmount(Integer columnTotalAmount) {
		this.columnTotalAmount = columnTotalAmount;
	}
	
	public Integer getColumnIvaAmount() {
		return columnIvaAmount;
	}
	
	public void setColumnIvaAmount(Integer columnIvaAmount) {
		this.columnIvaAmount = columnIvaAmount;
	}

	public Integer getColumnEntryType() {
		return columnEntryType;
	}

	public void setColumnEntryType(Integer columnEntryType) {
		this.columnEntryType = columnEntryType;
	}

}
