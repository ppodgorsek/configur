package com.github.ppodgorsek.configur.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.util.Assert;

import com.github.ppodgorsek.configur.core.service.ConfigurationService;

/**
 * Delegator which uses a list of {@link ConfigurationService}s to fetch
 * properties. It fetches properties from the delegates one by one and stops as
 * soon as one of them returns that property. It stores new properties in the
 * first delegate in the list.
 *
 * @author Paul Podgorsek
 */
public class ConfigurationServiceDelegator implements ConfigurationService {

	private final List<ConfigurationService> delegates;

	/**
	 * Default constructor.
	 *
	 * @param delegates The list of delegate configuration services.
	 */
	public ConfigurationServiceDelegator(final Collection<ConfigurationService> delegates) {

		super();

		Assert.notNull(delegates, "The list of delegates is required");
		Assert.isTrue(!delegates.isEmpty(), "The list of delegates should not be empty");

		this.delegates = Collections.unmodifiableList(new ArrayList<>(delegates));
	}

	protected List<ConfigurationService> getDelegates() {
		return delegates;
	}

}
