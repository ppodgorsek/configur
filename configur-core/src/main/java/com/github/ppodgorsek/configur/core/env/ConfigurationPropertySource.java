package com.github.ppodgorsek.configur.core.env;

import org.springframework.core.env.PropertySource;

import com.github.ppodgorsek.configur.core.service.ConfigurationService;

/**
 * A property source which uses a configuration service to fetch values.
 *
 * @author Paul Podgorsek
 */
public class ConfigurationPropertySource extends PropertySource<ConfigurationService> {

	/**
	 * Create a new PropertySource with the given name and source object.
	 *
	 * @param name
	 *            The name of the property source.
	 * @param service
	 *            The configuration service which will be used to retrieve the properties.
	 */
	public ConfigurationPropertySource(final String name, final ConfigurationService service) {
		super(name, service);
	}

	@Override
	public Object getProperty(final String name) {
		return getSource().getPropertyValue(name);
	}

}
