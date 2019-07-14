package com.github.ppodgorsek.configur.core.config;

import java.util.ArrayList;
import java.util.Collection;

import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.ppodgorsek.configur.core.service.ConfigurationService;
import com.github.ppodgorsek.configur.core.service.impl.ConfigurationServiceDelegator;

/**
 * Service test configuration for the core module.
 *
 * @author Paul Podgorsek
 */
@Configuration
public class CoreServiceTestConfiguration {

	@Mock
	private ConfigurationService delegate1;

	@Mock
	private ConfigurationService delegate2;

	@Bean
	public ConfigurationServiceDelegator configurationServiceDelegator() {

		EasyMockSupport.injectMocks(this);

		final Collection<ConfigurationService> delegates = new ArrayList<>();
		delegates.add(delegate1);
		delegates.add(delegate2);

		return new ConfigurationServiceDelegator(delegates);
	}

}
