package com.excilys.java.formation.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;

public class HibernateUtil {

	static SessionFactory sessionFactory ;
	static {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass (Computer.class);
		configuration.addAnnotatedClass (Company.class);
		configuration.setProperty("connection.driver_class","com.mysql.cj.jdbc.Driver");
		configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/computer-database-db?useSSL=false&serverTimezone=CET");                                
		configuration.setProperty("hibernate.connection.username", "admincdb");     
		configuration.setProperty("hibernate.connection.password", "qwerty1234");
		configuration.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
		configuration.setProperty("hibernate.hbm2ddl.auto", "update");
		configuration.setProperty("hibernate.show_sql", "true");
		configuration.setProperty(" hibernate.connection.pool_size", "10");
		configuration.setProperty(" hibernate.cache.use_second_level_cache", "true");
		configuration.setProperty(" hibernate.cache.use_query_cache", "true");
		configuration.setProperty(" cache.provider_class", "org.hibernate.cache.EhCacheProvider");
		configuration.setProperty("hibernate.cache.region.factory_class" ,"org.hibernate.cache.ehcache.EhCacheRegionFactory");

		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		sessionFactory = configuration.buildSessionFactory(builder.build());
	}
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
} 
