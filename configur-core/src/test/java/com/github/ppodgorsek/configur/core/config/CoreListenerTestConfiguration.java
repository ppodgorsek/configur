package com.github.ppodgorsek.configur.core.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.ppodgorsek.configur.core.listener.ConfigurationPropertySourceApplicationListener;

/**
 * Listener test configuration for the core module.
 *
 * @author Paul Podgorsek
 */
@Configuration
public class CoreListenerTestConfiguration {

	@Bean
	public ConfigurationPropertySourceApplicationListener configurationDaoApplicationListener(
			final ApplicationContext applicationContext) {
		return new ConfigurationPropertySourceApplicationListener(applicationContext);
	}

}
