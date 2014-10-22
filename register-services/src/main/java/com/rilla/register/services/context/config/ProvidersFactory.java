package com.rilla.register.services.context.config;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.rilla.register.repository.constants.AmountType;
import com.rilla.register.repository.constants.FinalFileType;
import com.rilla.register.repository.model.Metadata;
import com.rilla.register.repository.model.Provider;

@Service
public class ProvidersFactory {

	private static List<Provider> providers;

	@PostConstruct
	public static void setProviders() {
		Provider isa = createISAProvider();
		Provider zenga = createZENGAProvider();
		
		providers = new LinkedList<>();
		providers.add(isa);
		providers.add(zenga);
	}

	private static Provider createISAProvider() {
		Metadata isaMetadata = new Metadata();
		isaMetadata.setAmountType(AmountType.UNIQUE_COLUMN_PLUS_MINUS);
		isaMetadata.setFirstRow(8);
		isaMetadata.setFinalFileType(FinalFileType.LAST_DATE);
		
		isaMetadata.setColumnDate(3);
		isaMetadata.setColumnConcept(7);
		isaMetadata.setColumnRut(18);
		isaMetadata.setColumnAccount(13);
		isaMetadata.setColumnClientName(15);
		isaMetadata.setColumnAmount(30);
		isaMetadata.setColumnCurrency(29);
		
		Provider isa = new Provider();
		isa.setId(1);
		isa.setName("ISA");
		isa.setMetadata(isaMetadata);
		return isa;
	}
	
	private static Provider createZENGAProvider() {
		Provider zenga = new Provider();

		return zenga;
	}

}
