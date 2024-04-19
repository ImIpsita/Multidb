package  com.example.demo.postgre.config;
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
@EnableJpaRepositories(entityManagerFactoryRef = "secondentityManagerFactroryBean", basePackages = {
		"com.example.demo.postgre.repo" }, transactionManagerRef = "secondtranscationmanager")
public class PostgreConfig {
	
	@Autowired
	Environment env;

	// datasource
	@Bean(name="seconddatasource")
	@Primary
	public DataSource datasource() {
		DriverManagerDataSource postDataSource = new DriverManagerDataSource();
		postDataSource.setUrl(env.getProperty("second.datasource.url"));
		postDataSource.setUsername(env.getProperty("second.datasource.username"));
		postDataSource.setPassword(env.getProperty("second.datasource.password"));
		postDataSource.setDriverClassName(env.getProperty("second.datasource.driver-classname"));
		return postDataSource;
	}

	// entitymanager
	@Bean(name="secondentityManagerFactroryBean")
	@Primary
	public LocalContainerEntityManagerFactoryBean entitymnagerfactory() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(datasource());
		emf.setJpaProperties(hibernateProperty());
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		emf.setPackagesToScan("com.example.demo.postgre.entity");
		return emf;
	}

	@Bean(name = "postgresqlHibernate")
	public Properties hibernateProperty() {
		Properties prop = new Properties();
		prop.setProperty("hibernate.show_sql", "true");
		prop.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		prop.setProperty("hibernate.hbm2ddl.auto", "update");
		return prop;
	}

	// Transcationmanager
	@Bean(name="secondtranscationmanager")
	@Primary
	public PlatformTransactionManager transcationManager() {
		JpaTransactionManager transcationManager = new JpaTransactionManager();
		transcationManager.setEntityManagerFactory(entitymnagerfactory().getObject());
	//	transcationManager.setDataSource(datasource());
		return transcationManager;
	}
}
