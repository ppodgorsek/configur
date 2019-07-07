package com.github.ppodgorsek.configur.springdata.jpa.dao;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.ppodgorsek.configur.springdata.jpa.config.JpaTestConfiguration;
import com.github.ppodgorsek.configur.springdata.jpa.model.JpaConfigurationCategory;
import com.github.ppodgorsek.configur.springdata.jpa.model.JpaConfigurationProperty;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.CsvUrlDataSetLoader;

/**
 * Abstract test class initialising the DbUnit test data.
 *
 * @author Paul Podgorsek
 */
@SpringJUnitConfig(JpaTestConfiguration.class)
@Transactional
@Rollback
@DbUnitConfiguration(databaseConnection = "databaseDataSourceConnectionFactory", dataSetLoader = CsvUrlDataSetLoader.class)
@DatabaseSetup({ "/datasets/" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class })
public abstract class AbstractDbUnitTest {

	protected JpaConfigurationCategory category1;
	protected JpaConfigurationCategory category2;
	protected JpaConfigurationCategory category3;
	protected JpaConfigurationCategory category4;
	protected JpaConfigurationCategory category5;
	protected JpaConfigurationCategory category6;
	protected JpaConfigurationCategory category7;
	protected JpaConfigurationCategory nonPersistedCategory;

	protected JpaConfigurationProperty property01;
	protected JpaConfigurationProperty property02;
	protected JpaConfigurationProperty property03;
	protected JpaConfigurationProperty property04;
	protected JpaConfigurationProperty property05;
	protected JpaConfigurationProperty property06;
	protected JpaConfigurationProperty property07;
	protected JpaConfigurationProperty property08;
	protected JpaConfigurationProperty property09;
	protected JpaConfigurationProperty property10;
	protected JpaConfigurationProperty property11;
	protected JpaConfigurationProperty property12;
	protected JpaConfigurationProperty property13;
	protected JpaConfigurationProperty property14;
	protected JpaConfigurationProperty property15;
	protected JpaConfigurationProperty nonPersistedProperty;

	@BeforeEach
	public void commonSetUp() {

		category1 = new JpaConfigurationCategory();
		category1.setDescription("This category regroups database properties.");
		category1.setId("category1");
		category1.setKey("database");
		category1.setName("Database");
		category1.setParent(null);

		category2 = new JpaConfigurationCategory();
		category2.setDescription("This category regroups Hibernate properties.");
		category2.setId("category2");
		category2.setKey("database.hibernate");
		category2.setName("Hibernate");
		category2.setParent(category1);

		category3 = new JpaConfigurationCategory();
		category3.setDescription("This category regroups data source properties.");
		category3.setId("category3");
		category3.setKey("database.datasource");
		category3.setName("Data source");
		category3.setParent(category1);

		category4 = new JpaConfigurationCategory();
		category4.setDescription(null);
		category4.setId("category4");
		category4.setKey("test");
		category4.setName(null);
		category4.setParent(null);

		category5 = new JpaConfigurationCategory();
		category5.setDescription("DbUnit properties");
		category5.setId("category5");
		category5.setKey("test.dbunit");
		category5.setName("DbUnit");
		category5.setParent(category4);

		category6 = new JpaConfigurationCategory();
		category6.setDescription(null);
		category6.setId("category6");
		category6.setKey("logging");
		category6.setName(null);
		category6.setParent(null);

		category7 = new JpaConfigurationCategory();
		category7.setDescription("Regroups properties related to social networks.");
		category7.setId("category7");
		category7.setKey("social");
		category7.setName("Social Networks");
		category7.setParent(null);

		nonPersistedCategory = new JpaConfigurationCategory();
		nonPersistedCategory.setDescription(null);
		nonPersistedCategory.setKey("non persisted");
		nonPersistedCategory.setName(null);
		nonPersistedCategory.setParent(null);

		property01 = new JpaConfigurationProperty();
		property01.setCategory(category3);
		property01.setDescription("The data source URL");
		property01.setId("property01");
		property01.setKey("database.datasource.url");
		property01.setName("URL");
		property01
				.setValue("jdbc:hsqldb:mem:configurjpatest;hsqldb.write_delay=false;shutdown=true");

		property02 = new JpaConfigurationProperty();
		property02.setCategory(category3);
		property02.setDescription("The data source driver");
		property02.setId("property02");
		property02.setKey("database.datasource.driverClassName");
		property02.setName("Driver class name");
		property02.setValue("org.hsqldb.jdbc.JDBCDriver");

		property03 = new JpaConfigurationProperty();
		property03.setCategory(category3);
		property03.setDescription("The data source username");
		property03.setId("property03");
		property03.setKey("database.datasource.user");
		property03.setName("User");
		property03.setValue("sa");

		property04 = new JpaConfigurationProperty();
		property04.setCategory(category3);
		property04.setDescription("The data source password");
		property04.setId("property04");
		property04.setKey("database.datasource.password");
		property04.setName("Password");
		property04.setValue(null);

		property05 = new JpaConfigurationProperty();
		property05.setCategory(category5);
		property05.setDescription("The DbUnit data type factory");
		property05.setId("property05");
		property05.setKey("database.dbunit.dataTypeFactory");
		property05.setName("Data type factory");
		property05.setValue("org.dbunit.ext.hsqldb.HsqldbDataTypeFactory");

		property06 = new JpaConfigurationProperty();
		property06.setCategory(category2);
		property06.setDescription("The Hibernate dialect");
		property06.setId("property06");
		property06.setKey("database.hibernate.dialect");
		property06.setName("Dialect");
		property06.setValue("org.hibernate.dialect.HSQLDialect");

		property07 = new JpaConfigurationProperty();
		property07.setCategory(category2);
		property07.setDescription("Indicates whether or not the SQL statements must be formatted");
		property07.setId("property07");
		property07.setKey("database.hibernate.formatSql");
		property07.setName("Format SQL");
		property07.setValue("false");

		property08 = new JpaConfigurationProperty();
		property08.setCategory(category2);
		property08.setDescription(
				"Indicates whether or not the SQL statements must be shown in the logs");
		property08.setId("property08");
		property08.setKey("database.hibernate.showSql");
		property08.setName("Show SQL");
		property08.setValue("false");

		property09 = new JpaConfigurationProperty();
		property09.setCategory(category2);
		property09.setDescription(
				"Strategy for the creation of the DDL using the Hibernate configuration");
		property09.setId("property09");
		property09.setKey("database.hibernate.hbm2ddl.auto");
		property09.setName("Hbm2Ddl");
		property09.setValue("create");

		property10 = new JpaConfigurationProperty();
		property10.setCategory(category2);
		property10.setDescription("List of files to import on Hibernate startup");
		property10.setId("property10");
		property10.setKey("database.hibernate.hbm2ddl.import_files");
		property10.setName("Import files");
		property10.setValue(null);

		property11 = new JpaConfigurationProperty();
		property11.setCategory(category6);
		property11.setDescription("Log level for the com.github package");
		property11.setId("property11");
		property11.setKey("logging.level.com.github");
		property11.setName("com.github");
		property11.setValue("DEBUG");

		property12 = new JpaConfigurationProperty();
		property12.setCategory(category6);
		property12.setDescription("Log level for the org.springframework package");
		property12.setId("property12");
		property12.setKey("logging.level.org.springframework");
		property12.setName("org.springframework");
		property12.setValue("WARN");

		property13 = new JpaConfigurationProperty();
		property13.setCategory(category7);
		property13.setDescription("ID to connect to the Facebook API");
		property13.setId("property13");
		property13.setKey("social.facebook.id");
		property13.setName("Facebook ID");
		property13.setValue("facebookId");

		property14 = new JpaConfigurationProperty();
		property14.setCategory(category7);
		property14.setDescription("ID to connect to the Twitter API");
		property14.setId("property14");
		property14.setKey("social.twitter.id");
		property14.setName("Twitter ID");
		property14.setValue("twitterId");

		property15 = new JpaConfigurationProperty();
		property15.setCategory(null);
		property15.setDescription(null);
		property15.setId("property15");
		property15.setKey("project.version");
		property15.setName("Project version");
		property15.setValue("SNAPSHOT");

		nonPersistedProperty = new JpaConfigurationProperty();
		nonPersistedProperty.setCategory(null);
		nonPersistedProperty.setDescription(null);
		nonPersistedProperty.setId(null);
		nonPersistedProperty.setKey("unknownKey");
		nonPersistedProperty.setName(null);
		nonPersistedProperty.setValue("unknownValue");
	}

}
