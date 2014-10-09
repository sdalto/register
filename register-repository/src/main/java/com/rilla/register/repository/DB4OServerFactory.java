package com.rilla.register.repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import com.db4o.Db4o;
import com.db4o.ObjectServer;

@Service
public class DB4OServerFactory {

	private static ObjectServer server;
	
	@SuppressWarnings("deprecation")
	@PostConstruct
	public static void startServer() {
		ObjectServer server = Db4o.openServer("register.db", 9999);
		server.grantAccess("register_app", "register");
		System.out.println("Server is ruuning!!");
		DB4OServerFactory.server = server;
	}
	
	@PreDestroy
	public static void stopServer() {
		server.close();
	}
	
	
}
