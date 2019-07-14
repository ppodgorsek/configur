package com.github.ppodgorsek.configur.springdata.jpa.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Test configuration of the Spring Data JPA module.
 *
 * @author Paul Podgorsek
 */
@Configuration
@Import({ JpaConfiguration.class, JpaDbUnitTestConfiguration.class,
		JpaListenerTestConfiguration.class })
@PropertySource("classpath:properties/database.properties")
public class JpaTestConfiguration {

	@Value("${database.datasource.driverClassName}")
	private String datasourceDriverClassName;

	@Value("${database.datasource.url}")
	private String datasourceJdbcUrl;

	@Value("${database.datasource.user}")
	private String datasourceUsername;

	@Value("${database.datasource.password}")
	private String datasourcePassword;

	@Value("${database.hibernate.dialect}")
	private String databaseDialect;

	@Value("${database.hibernate.formatSql}")
	private String databaseFormatSql;

	@Value("${database.hibernate.hbm2ddl.import_files}")
	private String databaseImportFiles;

	@Value("${database.hibernate.showSql}")
	private String databaseShowSql;

	@Value("${database.hibernate.hbm2ddl.auto}")
	private String databaseGenerateDdl;

	@Bean
	public HikariConfig hikariConfig() {

		final HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setPoolName("springHikariCP");
		hikariConfig.setDriverClassName(datasourceDriverClassName);
		hikariConfig.setJdbcUrl(datasourceJdbcUrl);
		hikariConfig.setUsername(datasourceUsername);
		hikariConfig.setPassword(datasourcePassword);
		hikariConfig.setMaximumPoolSize(50);

		return hikariConfig;
	}

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		return new HikariDataSource(hikariConfig());
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {

		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(Boolean.parseBoolean(databaseGenerateDdl));
		vendorAdapter.setShowSql(Boolean.parseBoolean(databaseShowSql));

		final Properties jpaProperties = new Properties();
		jpaProperties.put(AvailableSettings.DIALECT, databaseDialect);
		jpaProperties.put(AvailableSettings.FORMAT_SQL, databaseFormatSql);
		jpaProperties.put(AvailableSettings.SHOW_SQL, databaseShowSql);
		jpaProperties.put(AvailableSettings.HBM2DDL_AUTO, databaseGenerateDdl);
		jpaProperties.put(AvailableSettings.HBM2DDL_IMPORT_FILES, databaseImportFiles);
		jpaProperties.put(AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS, true);

		final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(hibernatePackagesToScan());
		factory.setDataSource(dataSource());
		factory.setJpaProperties(jpaProperties);

		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {

		final JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());

		return txManager;
	}

	@Bean
	public String[] hibernatePackagesToScan() {

		final String[] hibernatePackagesToScan = new String[1];
		hibernatePackagesToScan[0] = "com.github.ppodgorsek.configur.springdata.jpa.model";

		return hibernatePackagesToScan;
	}

}
