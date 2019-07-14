package com.github.ppodgorsek.configur.core.model;

import java.io.Serializable;

/**
 * Model allowing the categorisation of properties.
 *
 * @author Paul Podgorsek
 */
public interface ConfigurationCategory extends Serializable {

	/**
	 * Fetches the category key.
	 *
	 * @return The category key.
	 */
	String getKey();

	/**
	 * Sets the category key.
	 *
	 * @param key
	 *            The category key.
	 */
	void setKey(String key);

	/**
	 * Fetches the category name, containing a human-friendly name.
	 *
	 * @return The category name.
	 */
	String getName();

	/**
	 * Sets the category name, containing a human-friendly name.
	 *
	 * @param name
	 *            The category name.
	 */
	void setName(String name);

	/**
	 * Fetches the category description, containing an explanation about what this category
	 * regroups.
	 *
	 * @return The category description.
	 */
	String getDescription();

	/**
	 * Sets the category description, containing an explanation about what this category regroups.
	 *
	 * @param description
	 */
	void setDescription(String description);

	/**
	 * Fetches the parent category.
	 *
	 * @return The parent category.
	 */
	ConfigurationCategory getParent();

	/**
	 * Sets the parent category.
	 *
	 * @param parent
	 *            The parent category.
	 */
	void setParent(ConfigurationCategory parent);

}
