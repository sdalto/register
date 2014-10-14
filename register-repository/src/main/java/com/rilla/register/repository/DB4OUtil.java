package com.rilla.register.repository;

import java.util.List;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.foundation.StopWatch;
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
			
			db.store(updatedCompany);
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

	public static <T> List<T> readAll(ObjectContainer db, Class<?> clazz) {
		StopWatch watch = new StopWatch();

		try {
			watch.start();

			List<T> query = db.queryByExample(clazz);
			System.out.println(query.size());
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
