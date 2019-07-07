package com.github.ppodgorsek.configur.core.service;

import java.util.List;

import com.github.ppodgorsek.configur.core.model.ConfigurationCategory;
import com.github.ppodgorsek.configur.core.model.ConfigurationProperty;

/**
 * Service to fetch and persist {@link ConfigurationProperty}s.
 *
 * @author Paul Podgorsek
 */
public interface ConfigurationService {

	/**
	 * Deletes a category. All properties attached to this category will also be removed.
	 *
	 * @param key
	 *            The category to delete.
	 */
	void deleteCategory(String key);

	/**
	 * Deletes a property.
	 *
	 * @param key
	 *            The property to delete.
	 */
	void deleteProperty(String key);

	/**
	 * Fetches the configuration properties belonging to a category.
	 *
	 * @param category
	 *            The category's key in which the properties are located.
	 * @return The configuration properties belonging to the provided category, or an empty list if
	 *         there are none.
	 */
	List<ConfigurationProperty> getByCategory(String category);

	/**
	 * Fetches a configuration by its key, in the default category.
	 *
	 * @param property
	 *            The configuration's key.
	 * @return The configuration having the provided key, or {@code null} if there isn't one.
	 */
	ConfigurationProperty getByProperty(String property);

	/**
	 * Saves a category. If the category has parents, they will be persisted too.
	 *
	 * @param category
	 *            The category which must be persisted.
	 * @return The persisted category.
	 */
	ConfigurationCategory save(ConfigurationCategory category);

	/**
	 * Saves a property. If the property has a parent category, it will be persisted too.
	 *
	 * @param property
	 *            The property which must be persisted.
	 * @return The persisted property.
	 */
	ConfigurationProperty save(ConfigurationProperty property);

}
