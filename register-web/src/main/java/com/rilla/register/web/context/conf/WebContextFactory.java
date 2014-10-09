package com.rilla.register.web.context.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;
import org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;

@Configuration
public class WebContextFactory {
	@Bean
	public DefaultAnnotationHandlerMapping getDefaultAnnotationHandlerMapping() {
		DefaultAnnotationHandlerMapping instance = new DefaultAnnotationHandlerMapping();
		instance.setUseDefaultSuffixPattern(false);
		return instance;
	}

	@Bean
	public BeanNameUrlHandlerMapping getBeanNameUrlHandlerMapping() {
		BeanNameUrlHandlerMapping instance = new BeanNameUrlHandlerMapping();
		return instance;
	}

	@Bean
	public SimpleControllerHandlerAdapter getSimpleControllerHandlerAdapter() {
		SimpleControllerHandlerAdapter instance = new SimpleControllerHandlerAdapter();
		return instance;
	}

	@Bean
	public HttpRequestHandlerAdapter getHttpRequestHandlerAdapter() {
		HttpRequestHandlerAdapter instance = new HttpRequestHandlerAdapter();
		return instance;
	}

	@Bean
	public AnnotationMethodHandlerAdapter getAnnotationMethodHandlerAdapter() {
		AnnotationMethodHandlerAdapter instance = new AnnotationMethodHandlerAdapter();
		return instance;
	}
}
