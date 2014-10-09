package com.rilla.register.services.context.scan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.rilla.register.repository.context.scan.RepositoryContext;

@Configuration
@Import({RepositoryContext.class})
@ComponentScan(basePackages = {"com.rilla.register.services"})
public class ServiceContext {

}
