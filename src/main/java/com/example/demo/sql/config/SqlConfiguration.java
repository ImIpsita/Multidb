package com.example.demo.sql.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactroryBean", basePackages = {
		"com.example.demo.sql.repo" }, transactionManagerRef = "transcationmanager")
public class SqlConfiguration {
	@ConfigurationProperties("spring.datasource.mysql")
	@Bean
	public DataSourceProperties mysqlproperties() {
		return new DataSourceProperties();
		
	}
	// datasource
	@Primary
	@Bean(name="firstdatasource")
	public DataSource datasource() {
		DriverManagerDataSource mysqlDataSource = new DriverManagerDataSource();
		mysqlDataSource.setUrl(mysqlproperties().getUrl());
		mysqlDataSource.setUsername(mysqlproperties().getUsername());
		mysqlDataSource.setPassword(mysqlproperties().getPassword());
		mysqlDataSource.setDriverClassName(mysqlproperties().getDriverClassName());
		return mysqlDataSource;
	}

	@Primary
	@Bean(name="entityManagerFactroryBean")
	public LocalContainerEntityManagerFactoryBean entitymnagerfactory() {

		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(datasource());

		emf.setJpaProperties(hibernateProperty());

		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		emf.setPackagesToScan("com.example.demo.sql.entity");


		return emf;

	}

	public Properties hibernateProperty() {

		Properties prop = new Properties();
		prop.setProperty("hibernate.show_sql", "true");
		prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		prop.setProperty("hibernate.hbm2ddl.auto", "update");
		return prop;

	}

	@Primary
	@Bean(name="transcationmanager")
	public PlatformTransactionManager transcationManager() {

		JpaTransactionManager transcationManager = new JpaTransactionManager();
		transcationManager.setEntityManagerFactory(entitymnagerfactory().getObject());
		transcationManager.setDataSource(datasource());

		return transcationManager;

	}
}
