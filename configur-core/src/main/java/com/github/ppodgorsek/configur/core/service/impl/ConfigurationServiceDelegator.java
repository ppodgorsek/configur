package com.github.ppodgorsek.configur.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.github.ppodgorsek.configur.core.model.ConfigurationCategory;
import com.github.ppodgorsek.configur.core.model.ConfigurationProperty;
import com.github.ppodgorsek.configur.core.service.ConfigurationService;

/**
 * Delegator which uses a list of {@link ConfigurationService}s to fetch properties. It fetches
 * properties from the delegates one by one and stops as soon as one of them returns that property.
 * It persists properties using the first delegate in the list. It deletes properties from all
 * delegates to make sure they are not reloaded upon restart.
 *
 * @author Paul Podgorsek
 */
public class ConfigurationServiceDelegator implements ConfigurationService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfigurationServiceDelegator.class);

	private final List<ConfigurationService> delegates;

	/**
	 * Default constructor.
	 *
	 * @param delegates
	 *            The list of delegate configuration services.
	 */
	public ConfigurationServiceDelegator(final Collection<ConfigurationService> delegates) {

		super();

		Assert.notNull(delegates, "The list of delegates is required");
		Assert.isTrue(!delegates.isEmpty(), "The list of delegates should not be empty");

		this.delegates = Collections.unmodifiableList(new ArrayList<>(delegates));
	}

	@Override
	public void deleteCategory(final String key) {

		LOGGER.debug("Deleting category {}", key);

		for (final ConfigurationService delegate : delegates) {
			delegate.deleteCategory(key);
		}
	}

	@Override
	public void deleteProperty(final String key) {

		LOGGER.debug("Deleting property {}", key);

		for (final ConfigurationService delegate : delegates) {
			delegate.deleteProperty(key);
		}
	}

	@Override
	public List<ConfigurationProperty> getByCategory(final String category) {

		final List<ConfigurationProperty> configurationProperties = new ArrayList<>();

		for (final ConfigurationService delegate : delegates) {
			configurationProperties.addAll(delegate.getByCategory(category));
		}

		return configurationProperties;
	}

	@Override
	public ConfigurationProperty getByProperty(final String key) {

		ConfigurationProperty configurationProperty = null;

		for (final ConfigurationService delegate : delegates) {
			configurationProperty = delegate.getByProperty(key);

			if (configurationProperty != null) {
				break;
			}
		}

		return configurationProperty;
	}

	@Override
	public ConfigurationCategory save(final ConfigurationCategory category) {
		return delegates.get(0).save(category);
	}

	@Override
	public ConfigurationProperty save(final ConfigurationProperty property) {
		return delegates.get(0).save(property);
	}

	protected List<ConfigurationService> getDelegates() {
		return delegates;
	}

}
