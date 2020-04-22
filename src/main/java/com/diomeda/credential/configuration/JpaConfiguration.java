package com.diomeda.credential.configuration;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableJpaRepositories(basePackages = "com.diomeda.credential.repository",
		entityManagerFactoryRef = "entityManagerFactory",
		transactionManagerRef = "transactionManager")
@EnableTransactionManagement
@Slf4j
public class JpaConfiguration {

	private static final String MODEL_PACKAGE = "com.diomeda.credential.model";

	private static final String CREDENTIAL_SCHEMA = "credential";

	@Autowired
	private Environment environment;

	@Value("${datasource.maxPoolSize:10}")
	private int maxPoolSize;

	/*
	 * Populate SpringBoot DataSourceProperties object directly from application.properties 
	 * based on prefix. data is mapped out of the box with matching-name
	 * properties of DataSourceProperties object].
	 */
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "datasource")
	public DataSourceProperties dataSourceProperties(){
		return new DataSourceProperties();
	}

	/*
	 * Configure HikariCP pooled DataSource.
	 */
	@Bean
	public DataSource dataSource() {
		DataSourceProperties dataSourceProperties = dataSourceProperties();
			HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder
					.create(dataSourceProperties.getClassLoader())
					.driverClassName(dataSourceProperties.getDriverClassName())
					.url(dataSourceProperties.getUrl())
					.username(dataSourceProperties.getUsername())
					.password(dataSourceProperties.getPassword())
					.type(HikariDataSource.class)				
					.build();
			dataSource.setMaximumPoolSize(maxPoolSize);
			dataSource.setSchema(CREDENTIAL_SCHEMA);
			return dataSource;
	}

	/*
	 * Entity Manager Factory setup.
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException{
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan(new String[] { MODEL_PACKAGE });
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		factoryBean.setJpaProperties(jpaProperties());
		return factoryBean;

	}

	/*
	 * Provider specific adapter.
	 */
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		return hibernateJpaVendorAdapter;
	}

	/*
	 * Here you can specify any provider specific properties.
	 */
	private Properties jpaProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("datasource.hibernate.dialect"));
		properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("datasource.hibernate.hbm2ddl.method"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("datasource.hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("datasource.hibernate.format_sql"));
		properties.put("hibernate.default_schema", environment.getRequiredProperty("datasource.hibernate.defaultSchema"));
		properties.put("hibernate.physical_naming_strategy", environment.getRequiredProperty("datasource.hibernate.physical_naming_strategy"));
		log.info("Esta en properties de JPA");

		return properties;
		
	}

	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}


}
