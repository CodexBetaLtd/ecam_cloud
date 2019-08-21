package com.codex.ecam.config;

import java.util.Collections;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.codex.ecam.listeners.rootaware.RootAwareEventListenerIntegrator;
import com.codex.ecam.repository.FocusDataTablesRepositoryFactoryBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages = { "com.codex.ecam.dao" }, repositoryFactoryBeanClass = FocusDataTablesRepositoryFactoryBean.class)
@EnableTransactionManagement
@PropertySource({ "classpath:database.properties"})
public class DatabaseConfiguration {

	@Autowired
	private Environment environment;

	@Bean
	public DataSource getDataSource() {
		HikariConfig dataSourceConfig = new HikariConfig();
		dataSourceConfig.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
		dataSourceConfig.setJdbcUrl(getJDBCUrl());
		dataSourceConfig.setUsername(environment.getProperty("jdbc.user"));
		dataSourceConfig.setPassword(environment.getProperty("jdbc.password"));
		return new HikariDataSource(dataSourceConfig);
	}

	private String getJDBCUrl() {
		return environment.getProperty("jdbc.urlprefix") + environment.getProperty("jdbc.host") + ":"  + environment.getProperty("jdbc.port") + "/" + environment.getProperty("jdbc.db");
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("com.codex.ecam.model");
		entityManagerFactoryBean.setJpaProperties(getJpaProperties());
		return entityManagerFactoryBean;
	}

	private Properties getJpaProperties() {
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
		jpaProperties.put("hibernate.show_sql", "true");
		jpaProperties.put("hibernate.format_sql", "true");
		jpaProperties.put("hibernate.enable_lazy_load_no_trans", "true");
		jpaProperties.put("hibernate.integrator_provider", (IntegratorProvider) () -> Collections.singletonList( RootAwareEventListenerIntegrator.INSTANCE ));
		return jpaProperties;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}


}
