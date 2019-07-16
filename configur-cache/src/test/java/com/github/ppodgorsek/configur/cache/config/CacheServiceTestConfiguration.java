package com.github.ppodgorsek.configur.cache.config;

import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.ppodgorsek.configur.cache.service.CachedConfigurationService;
import com.github.ppodgorsek.configur.core.service.ConfigurationService;

/**
 * Service test configuration for the Cache module.
 *
 * @author Paul Podgorsek
 */
@Configuration
public class CacheServiceTestConfiguration {

	@Bean
	public ConfigurationService mockConfigurationService() {
		return EasyMock.createMock(ConfigurationService.class);
	}

	@Bean
	public CachedConfigurationService cachedConfigurationService() {
		return new CachedConfigurationService(mockConfigurationService());
	}

}
