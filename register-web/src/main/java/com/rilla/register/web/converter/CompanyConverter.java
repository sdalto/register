package com.rilla.register.web.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.rilla.register.repository.model.Company;
import com.rilla.register.repository.model.Provider;
import com.rilla.register.web.spring.Services;

@FacesConverter("companyConverter")
public class CompanyConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				for (Company c : Services.FACADE.companyBean.getAll()) {
					if (c.getName().equals(value)) {
						return c;
					}
				}
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error",
						"La empresa seleccionada no es valida."));
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((Company) object).getName());
		} else {
			return null;
		}
	}
	
}
