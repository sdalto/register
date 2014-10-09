package com.rilla.register.web.context.scan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.rilla.register.services.context.scan.ServiceContext;

@Configuration
@EnableWebMvc
@Import({ ServiceContext.class })
@ComponentScan({ "com.rilla.register.web.context.conf",
		"com.rilla.register.web.spring" })
public class WebContext extends WebMvcConfigurerAdapter {

	// @Override
	// public void configureMessageConverters(List<HttpMessageConverter<?>>
	// converters) {
	// super.configureMessageConverters(converters);
	//
	// MappingJackson2HttpMessageConverter converter = new
	// MappingJackson2HttpMessageConverter();
	//
	// ObjectMapper objectMapper = new ObjectMapper();
	// objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
	// false);
	// objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
	// objectMapper.setSerializationInclusion(Include.NON_NULL);
	// converter.setObjectMapper(objectMapper);
	//
	// converters.add(converter);
	// }
	//
}
