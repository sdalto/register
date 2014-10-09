package com.rilla.register.repository.model;

import com.rilla.register.repository.constants.AmountType;
import com.rilla.register.repository.constants.FinalFileType;

public class Metadata {

	private AmountType amountType = AmountType.UNIQUE_COLUMN_PLUS_MINUS;
	private int firstRow = 8;
	private FinalFileType finalFileType = FinalFileType.LAST_DATE;
	private String finalValue;

	private Integer columnDate = 3;
	private Integer columnConcept = 7;
	private Integer columnRut = 18;
	private Integer columnAccount = 13;
	private Integer columnClientName = 15;
	private Integer columnAmount = 30;
	private Integer columnCurrency = 29;
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

	public Integer getColumnAmount() {
		return columnAmount;
	}

	public void setColumnAmount(Integer columnAmount) {
		this.columnAmount = columnAmount;
	}

	public Integer getColumnCurrency() {
		return columnCurrency;
	}

	public void setColumnCurrency(Integer columnCurrency) {
		this.columnCurrency = columnCurrency;
	}

	public Integer getColumnEntryType() {
		return columnEntryType;
	}

	public void setColumnEntryType(Integer columnEntryType) {
		this.columnEntryType = columnEntryType;
	}

}
