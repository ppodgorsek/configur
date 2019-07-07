package com.github.ppodgorsek.configur.springdata.jpa.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.ppodgorsek.configur.springdata.jpa.dao.JpaConfigurationCategoryDao;
import com.github.ppodgorsek.configur.springdata.jpa.dao.JpaConfigurationPropertyDao;
import com.github.ppodgorsek.configur.springdata.jpa.service.JpaConfigurationService;

/**
 * Configuration of the Spring Data JPA module.
 *
 * @author Paul Podgorsek
 */
@Configuration
@EnableJpaRepositories({ "com.github.ppodgorsek.configur.springdata.jpa.dao" })
@EnableTransactionManagement
public class JpaConfiguration {

	@Resource
	private JpaConfigurationCategoryDao jpaConfigurationCategoryDao;

	@Resource
	private JpaConfigurationPropertyDao jpaConfigurationPropertyDao;

	@Bean
	public JpaConfigurationService personService() {
		return new JpaConfigurationService(jpaConfigurationCategoryDao,
				jpaConfigurationPropertyDao);
	}

}
