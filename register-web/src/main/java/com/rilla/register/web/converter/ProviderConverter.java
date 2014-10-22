package com.rilla.register.web.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.rilla.register.repository.model.Provider;
import com.rilla.register.web.spring.Services;

@FacesConverter("providerConverter")
public class ProviderConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				for (Provider p : Services.FACADE.providerBean.getProviders()) {
					if (p.getName().equals(value)) {
						return p;
					}
				}
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error",
						"El proveedor seleccionado no es valido."));
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((Provider) object).getName());
		} else {
			return null;
		}
	}

	

}
