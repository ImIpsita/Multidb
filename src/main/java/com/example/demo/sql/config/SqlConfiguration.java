package com.example.demo.sql.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
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

	@Autowired
	Environment env;

	// datasource
	@Bean
	@Primary
	public DataSource datasource() {

		DriverManagerDataSource sqlDataSource = new DriverManagerDataSource();
		sqlDataSource.setUrl(env.getProperty("spring.datasource.url"));
		sqlDataSource.setUsername(env.getProperty("spring.datasource.username"));
		sqlDataSource.setPassword(env.getProperty("spring.datasource.password"));
		sqlDataSource.setDriverClassName(env.getProperty("spring.datasource.driver-classname"));

		return sqlDataSource;

	}

	// entitymanager
	@Bean(name="entityManagerFactroryBean")
	@Primary
	public LocalContainerEntityManagerFactoryBean entitymnagerfactory() {

		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(datasource());

		emf.setJpaProperties(hibernateProperty());

		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		emf.setPackagesToScan("com.example.demo.sql.entity");


		return emf;

	}

	@Bean
	public Properties hibernateProperty() {

		Properties prop = new Properties();
		prop.setProperty("hibernate.show_sql", "true");
		prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		prop.setProperty("hibernate.hdm2ddl.auto","update");
		return prop;

	}

	// Transcationmanager
	@Bean(name="transcationmanager")
	@Primary
	public PlatformTransactionManager transcationManager() {

		JpaTransactionManager transcationManager = new JpaTransactionManager();
		transcationManager.setEntityManagerFactory(entitymnagerfactory().getObject());
		transcationManager.setDataSource(datasource());

		return transcationManager;

	}

}
