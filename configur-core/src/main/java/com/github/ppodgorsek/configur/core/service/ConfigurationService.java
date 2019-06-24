package com.github.ppodgorsek.configur.core.service;

import com.github.ppodgorsek.configur.core.model.ConfigurationCategory;
import com.github.ppodgorsek.configur.core.model.ConfigurationProperty;

/**
 * Service to fetch and persist {@link ConfigurationProperty}s.
 *
 * @author Paul Podgorsek
 */
public interface ConfigurationService {

	/**
	 * Fetches a configuration by its key, in the default category.
	 *
	 * @param key
	 *            The configuration's key.
	 * @return The configuration having the provided key, or {@code null} if there isn't one.
	 */
	ConfigurationProperty getByKey(String key);

	/**
	 * Fetches a configuration by its key.
	 *
	 * @param key
	 *            The configuration's key.
	 * @param category
	 *            The configuration category in which the property is located.
	 * @return The configuration having the provided key in the provided category, or {@code null}
	 *         if there isn't one.
	 */
	ConfigurationProperty getByKey(String key, ConfigurationCategory category);

}
