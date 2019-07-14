package com.github.ppodgorsek.configur.springdata.jpa.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.ppodgorsek.configur.core.listener.ConfigurationPropertySourceApplicationListener;

/**
 * Listener test configuration for the Spring Data JPA module.
 *
 * @author Paul Podgorsek
 */
@Configuration
public class JpaListenerTestConfiguration {

	@Bean
	public ConfigurationPropertySourceApplicationListener jpaConfigurationDaoApplicationListener(
			final ApplicationContext applicationContext) {
		return new ConfigurationPropertySourceApplicationListener(applicationContext,
				"jpaConfigurationService");
	}

}
