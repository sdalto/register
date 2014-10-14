package com.rilla.register.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessControlException;
import java.util.EnumSet;
import java.util.Map.Entry;
import java.util.Properties;

import javax.el.ELException;
import javax.faces.application.ViewExpiredException;
import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.WebAppContext;
import org.primefaces.webapp.filter.FileUploadFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.rilla.register.web.context.scan.WebContext;
import com.rilla.register.web.utils.JulInstallationListener;
import com.sun.faces.config.ConfigureListener;
import com.sun.faces.context.FacesFileNotFoundException;

public class RegisterWebServer {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RegisterWebServer.class);

	private static final String WEB_PARAMS_PATH = "conf/web-params.properties";
	private static final int MAX_CONTENT_SIZE = 5242880; // 5 MB
	private static final String DISPLAY_NAME = "register";
	private static final String JSF_CONTEXT_NAME = "/register-front";
	private static final int DEFAULT_PORT = 9010;

	private final Server server;

	public RegisterWebServer(String[] args) {
		this.server = this.createNewServer(args);
	}

	public static void main(String[] args) throws Exception {
		RegisterWebServer server = new RegisterWebServer(args);
		server.run();
	}

	private void run() throws Exception {
		this.server.start();
		LOGGER.info("Server Started");
		this.server.join();
	}

	private Server createNewServer(String[] args) {
		Server server = new Server(DEFAULT_PORT);

		WebAppContext handler = this.buildWebAppContext(args);
		server.setHandler(handler);
		server.setStopAtShutdown(true);

		// Set maxFormContentSize
		server.setAttribute(
				"org.eclipse.jetty.server.Request.maxFormContentSize",
				MAX_CONTENT_SIZE);

		return server;
	}

	private WebAppContext buildWebAppContext(String[] args) {
		boolean dev = args.length > 0 ? args[0].equals("dev") : false;

		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(WebContext.class);

		LOGGER.info("Init WebAppContext");
		WebAppContext handler = new WebAppContext();
		handler.setContextPath(JSF_CONTEXT_NAME);
		handler.setWelcomeFiles(new String[] { "views/home.xhtml" });
		handler.setDisplayName(DISPLAY_NAME);

		String[] resources = null;
		if (dev) {
			resources = new String[] { "./src/main/webapp", "./target" };
		} else {
			resources = new String[] { "./webapp" };
		}
		handler.setBaseResource(new ResourceCollection(resources));
		handler.setResourceAlias("/WEB-INF/classes/", "/classes/");

		this.appendListeners(applicationContext, handler);
		this.appendInitParamenters(handler);
		this.appendErrorHandler(handler);
		this.appendSpringDispatcherServlet(applicationContext, handler);
		this.appendFilters(handler);
		return handler;
	}

	private void appendListeners(
			AnnotationConfigWebApplicationContext applicationContext,
			WebAppContext handler) {
		// Para que ande JSF & Primefaces
		handler.addEventListener(new ConfigureListener());

		// Para que funcione Spring con su contexto
		handler.addEventListener(new ContextLoaderListener(applicationContext));

		// Logging
		handler.addEventListener(new JulInstallationListener());
	}

	private void appendErrorHandler(WebAppContext handler) {
		ErrorPageErrorHandler errorHandler = new ErrorPageErrorHandler();
		errorHandler.addErrorPage(500, "/views/error/error.xhtml");
		errorHandler.addErrorPage(404, "/views/error/urlError.xhtml");
		errorHandler.addErrorPage(FacesFileNotFoundException.class,
				"/views/error/urlError.xhtml");
		errorHandler.addErrorPage(IllegalArgumentException.class,
				"/views/error/urlError.xhtml");
		handler.setErrorHandler(errorHandler);
	}

	private void appendSpringDispatcherServlet(
			AnnotationConfigWebApplicationContext applicationContext,
			WebAppContext handler) {
		// LOGGER.info("Init Spring DispatcherServlet");
		// DispatcherServlet dispatcherServlet = new DispatcherServlet(
		// applicationContext);
		// ServletHolder servletHolder = new ServletHolder(dispatcherServlet);
		// servletHolder.setName("spring");
		// servletHolder.setInitOrder(1);
		// handler.addServlet(servletHolder, "/health/*");
		// handler.addServlet(servletHolder, "/atp/*");
	}

	private void appendFilters(WebAppContext handler) {

		LOGGER.info("Init Encoding Filter");

		// Update File Filter
		FileUploadFilter fileUploadFilter = new FileUploadFilter();
		FilterHolder fileUploadFilterHolder = new FilterHolder(fileUploadFilter);
		handler.addFilter(fileUploadFilterHolder, "/*",
				EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR));

		// Encoding Filter
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		FilterHolder characterEncodingFilterHolder = new FilterHolder(
				characterEncodingFilter);
		handler.addFilter(characterEncodingFilterHolder, "/*",
				EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR));
	}

	private void appendInitParamenters(WebAppContext handler) {
		Properties webParams = new Properties();
		try {
			InputStream stream = this.getClass().getClassLoader()
					.getResourceAsStream(WEB_PARAMS_PATH);
			webParams.load(stream);
		} catch (FileNotFoundException e) {
			LOGGER.error(WEB_PARAMS_PATH + " not found!!");
		} catch (IOException e) {
			LOGGER.error("Error loading " + WEB_PARAMS_PATH, e);
		}

		for (Entry<Object, Object> entry : webParams.entrySet()) {
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			handler.setInitParameter(key, value);
		}
	}

}
