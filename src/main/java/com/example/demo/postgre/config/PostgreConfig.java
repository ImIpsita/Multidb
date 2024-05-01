package  com.example.demo.postgre.config;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "secondentityManagerFactroryBean", basePackages = {
		"com.example.demo.postgre.repo" }, transactionManagerRef = "secondtranscationmanager")
public class PostgreConfig {
	
	@ConfigurationProperties("spring.datasource.pg")
	@Bean("seconfpgproperties")
	public DataSourceProperties postgresqlproperties() {
		return new DataSourceProperties();
		
	}
	// datasource
	@Bean(name="seconddatasource")
	public DataSource datasource() {
		DriverManagerDataSource postDataSource = new DriverManagerDataSource();
		postDataSource.setUrl(postgresqlproperties().getUrl());
		postDataSource.setUsername(postgresqlproperties().getUsername());
		postDataSource.setPassword(postgresqlproperties().getPassword());
		postDataSource.setDriverClassName(postgresqlproperties().getDriverClassName());
		return postDataSource;
	}
	
	@Bean(name="secondentityManagerFactroryBean")
	public LocalContainerEntityManagerFactoryBean entitymnagerfactory() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(datasource());
		emf.setJpaProperties(hibernateProperty());
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		emf.setPackagesToScan("com.example.demo.postgre.entity");
		return emf;
	}



	public Properties hibernateProperty() {
		Properties prop = new Properties();
		prop.setProperty("hibernate.show_sql", "true");
		prop.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		prop.setProperty("hibernate.hbm2ddl.auto", "update");
		return prop;
	}

	// Transcationmanager
	@Bean(name="secondtranscationmanager")
        public PlatformTransactionManager transcationManager() {
		JpaTransactionManager transcationManager = new JpaTransactionManager();
		transcationManager.setEntityManagerFactory(entitymnagerfactory().getObject());
	//	transcationManager.setDataSource(datasource());
		return transcationManager;
	}
	
}
