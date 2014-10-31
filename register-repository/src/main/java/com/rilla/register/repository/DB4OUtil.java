package com.rilla.register.repository;

import java.util.List;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.foundation.StopWatch;
import com.rilla.register.repository.model.Client;
import com.rilla.register.repository.model.Company;

public class DB4OUtil {

	@SuppressWarnings("deprecation")
	public static ObjectContainer open() {
		ObjectContainer db = Db4o.openClient("localhost", 9999, "register_app",
				"register");
		return db;
	}

	public static long saveDB4O(List<Object> list, ObjectContainer db) {
		StopWatch watch = new StopWatch();
		watch.start();

		try {
			for (Object p : list) {
				db.store(p);
			}
			db.commit();

		} catch (Throwable th) {
			db.rollback();
			th.printStackTrace();
		}

		watch.stop();
		return watch.elapsed();
	}

	public static long saveDB4O(Object o, ObjectContainer db) {
		StopWatch watch = new StopWatch();
		watch.start();

		try {
			db.store(o);
			db.commit();
		} catch (Throwable th) {
			db.rollback();
			th.printStackTrace();
		}

		watch.stop();
		return watch.elapsed();
	}
	
	public static long deleteDB4O(Object o, ObjectContainer db) {
		StopWatch watch = new StopWatch();
		watch.start();

		try {
			ObjectSet<Object> queryByExample = db.queryByExample(o);
			db.delete(queryByExample.next());
			db.commit();
		} catch (Throwable th) {
			db.rollback();
			th.printStackTrace();
		}

		watch.stop();
		return watch.elapsed();
	}
	
	public static long updateDB4OCompany(Object o, ObjectContainer db, Company company) {
		StopWatch watch = new StopWatch();
		watch.start();

		try {
			ObjectSet<Object> queryByExample = db.queryByExample(o);
			
			Company updatedCompany = (Company) queryByExample.next();
			updatedCompany.setName(company.getName());
			updatedCompany.setIvaAccount(company.getIvaAccount());
			updatedCompany.setLegalName(company.getLegalName());
			updatedCompany.setSubtotalAccount(company.getSubtotalAccount());
			updatedCompany.setTotalAccount(company.getTotalAccount());
			
			db.store(updatedCompany);
			db.commit();
		} catch (Throwable th) {
			db.rollback();
			th.printStackTrace();
		}

		watch.stop();
		return watch.elapsed();
	}
	
	public static long updateDB4OClient(Object o, ObjectContainer db, Client client) {
		StopWatch watch = new StopWatch();
		watch.start();

		try {
			ObjectSet<Object> queryByExample = db.queryByExample(o);
			
			Client updatedClient = (Client) queryByExample.next();
			updatedClient.setCompanyId(client.getCompanyId());
			updatedClient.setName(client.getName());
			updatedClient.setNumber(client.getNumber());
			
			db.store(updatedClient);
			db.commit();
		} catch (Throwable th) {
			db.rollback();
			th.printStackTrace();
		}

		watch.stop();
		return watch.elapsed();
	}

	public static long readDB4O(ObjectContainer db, Class<?>... clazzes) {
		StopWatch watch = new StopWatch();

		try {
			watch.start();
			for (Class<?> clazz : clazzes) {
				ObjectSet<?> query = db.query(clazz);
				System.out.println(query.size());
			}

		} catch (Throwable th) {
			db.rollback();
			th.printStackTrace();
		}

		watch.stop();
		return watch.elapsed();
	}
	
	public static <T> List<T> readByExample(Object o,ObjectContainer db, Class<?> clazz) {
		StopWatch watch = new StopWatch();
		watch.start();

		try {
			List<T> query = db.queryByExample(o);
			watch.stop();
			return query;
			
		} catch (Throwable th) {
			db.rollback();
			th.printStackTrace();
			watch.stop();
			return null;
		}

	}

	public static <T> List<T> readAll(ObjectContainer db, Class<?> clazz) {
		StopWatch watch = new StopWatch();

		try {
			watch.start();

			List<T> query = db.queryByExample(clazz);
			watch.stop();
			return query;

		} catch (Throwable th) {
			db.rollback();
			th.printStackTrace();
			watch.stop();
			return null;
		}
	}
}
