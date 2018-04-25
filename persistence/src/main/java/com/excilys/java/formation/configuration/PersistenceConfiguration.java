package com.excilys.java.formation.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan(basePackages = "com.excilys.java.formation")
@Profile("!interface")
public class PersistenceConfiguration {
	
	@Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/computer-database-db?useSSL=false&serverTimezone=CET");
        dataSource.setUsername("admincdb");
        dataSource.setPassword("qwerty1234");	
        return dataSource;
    }

}
