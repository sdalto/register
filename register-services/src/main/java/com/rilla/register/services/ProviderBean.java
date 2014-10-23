package com.rilla.register.services;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.rilla.register.repository.constants.AmountType;
import com.rilla.register.repository.constants.FinalFileType;
import com.rilla.register.repository.model.Metadata;
import com.rilla.register.repository.model.Provider;

@Service
public class ProviderBean {
	
	public List<Provider> providers;

	public List<Provider> getProviders() {
		return providers;
	}
	
	@PostConstruct
	public void setProviders() {
		Provider isa = createISAProvider();
		Provider scantech = createScanntechProvider();
		
		providers = new LinkedList<>();
		providers.add(isa);
		providers.add(scantech);
	}

	private Provider createISAProvider() {
		Metadata metadata = new Metadata();
		metadata.setAmountType(AmountType.UNIQUE_COLUMN_PLUS_MINUS);
		metadata.setFirstRow(8);
		metadata.setFinalFileType(FinalFileType.LAST_DATE);
		metadata.setDecimals(2);
		
		List<Integer> conceptColumns = new LinkedList<>();
		conceptColumns.add(7);
		
		metadata.setColumnDate(3);
		metadata.setColumnsConcept(conceptColumns);
		metadata.setColumnRut(18);
		metadata.setColumnAccount(13);
		metadata.setColumnClientName(15);
		metadata.setColumnIvaAmount(26);
		metadata.setColumnTotalAmount(30);
		
		Provider isa = new Provider();
		isa.setId(1);
		isa.setName("ISA");
		isa.setMetadata(metadata);
		return isa;
	}
	
	private static Provider createScanntechProvider() {
		Metadata metadata = new Metadata();
		metadata.setAmountType(AmountType.UNIQUE_COLUMN_PLUS_MINUS);
		metadata.setFirstRow(4);
		metadata.setFinalFileType(FinalFileType.LAST_DATE);
		metadata.setDecimals(2);
		
		List<Integer> conceptColumns = new LinkedList<>();
		conceptColumns.add(2);
		conceptColumns.add(3);
		conceptColumns.add(4);
		conceptColumns.add(5);
		
		metadata.setColumnDate(0);
		metadata.setColumnsConcept(conceptColumns);
		metadata.setColumnIvaAmount(6);
		metadata.setColumnTotalAmount(9);
		metadata.setColumnAccount(1);
		metadata.setColumnClientName(2);

		Provider scanntech = new Provider();
		scanntech.setId(2);
		scanntech.setName("Scanntech");
		scanntech.setMetadata(metadata);
		
		return scanntech;
	}
	
}
