package com.rilla.register.web.spring;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.rilla.register.services.ClientBean;
import com.rilla.register.services.CompanyBean;
import com.rilla.register.services.ProviderBean;
import com.rilla.register.services.generator.DBFGenerator;
import com.rilla.register.services.generator.ExcelGeneratorBean;
import com.rilla.register.services.reader.ExcelReaderBean;

public enum Services {
    FACADE;

    public ExcelGeneratorBean excelGeneratorBean;
    public ExcelReaderBean excelReaderBean;
    public CompanyBean companyBean;
    public ClientBean clientBean; 
    public DBFGenerator dbfGenerator;
    public ProviderBean providerBean;

    private Services() {
        ServletContext webContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(webContext);
        this.excelGeneratorBean = context.getBean(ExcelGeneratorBean.class);
        this.excelReaderBean = context.getBean(ExcelReaderBean.class);
        this.companyBean = context.getBean(CompanyBean.class);
        this.clientBean = context.getBean(ClientBean.class);
        this.dbfGenerator = context.getBean(DBFGenerator.class);
        this.providerBean = context.getBean(ProviderBean.class);
    }
}