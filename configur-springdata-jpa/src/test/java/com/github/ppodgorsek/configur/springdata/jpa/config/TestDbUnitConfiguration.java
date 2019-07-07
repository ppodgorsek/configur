package com.github.ppodgorsek.configur.springdata.jpa.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;

/**
 * Test database configuration.
 *
 * @author Paul Podgorsek
 */
@Configuration
public class TestDbUnitConfiguration {

	@Resource
	private DataSource dataSource;

	@Value("${database.dbunit.dataTypeFactory}")
	private String dbUnitDataTypeFactory;

	@Bean
	public IDataTypeFactory dataTypeFactory()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return (IDataTypeFactory) Class.forName(dbUnitDataTypeFactory).newInstance();
	}

	@Bean
	public DatabaseConfigBean databaseConfig()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		final DatabaseConfigBean databaseConfig = new DatabaseConfigBean();
		databaseConfig.setDatatypeFactory(dataTypeFactory());
		databaseConfig.setAllowEmptyFields(Boolean.FALSE);

		return databaseConfig;
	}

	@Bean
	public DatabaseDataSourceConnectionFactoryBean databaseDataSourceConnectionFactory()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		final DatabaseDataSourceConnectionFactoryBean databaseDataSourceConnectionFactory = new DatabaseDataSourceConnectionFactoryBean(
				dataSource);
		databaseDataSourceConnectionFactory.setDatabaseConfig(databaseConfig());

		return databaseDataSourceConnectionFactory;
	}

}
