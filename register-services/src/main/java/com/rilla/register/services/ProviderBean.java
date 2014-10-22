package com.rilla.register.services;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
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
		Provider zenga = createZENGAProvider();
		
		providers = new LinkedList<>();
		providers.add(isa);
		providers.add(zenga);
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
		metadata.setColumnAmount(30);
		metadata.setColumnCurrency(29);
		
		Provider isa = new Provider();
		isa.setId(1);
		isa.setName("ISA");
		isa.setMetadata(metadata);
		return isa;
	}
	
	private static Provider createZENGAProvider() {
		Metadata metadata = new Metadata();
		metadata.setAmountType(AmountType.UNIQUE_COLUMN_PLUS_MINUS);
		metadata.setFirstRow(4);
		metadata.setFinalFileType(FinalFileType.LAST_DATE);
		metadata.setDecimals(2);
		
		List<Integer> conceptColumns = new LinkedList<>();
		conceptColumns.add(3);
		conceptColumns.add(4);
		conceptColumns.add(5);
		conceptColumns.add(2);
		
		metadata.setColumnDate(0);
		metadata.setColumnsConcept(conceptColumns);
		metadata.setColumnAmount(9);
		metadata.setColumnAccount(1);
		metadata.setColumnClientName(2);

		Provider zenga = new Provider();
		zenga.setId(2);
		zenga.setName("ZENGA");
		zenga.setMetadata(metadata);
		
		return zenga;
	}
	
}
