package com.github.ppodgorsek.configur.core.model;

import java.io.Serializable;
import java.util.Set;

/**
 * Model for configuration properties.
 *
 * @author Paul Podgorsek
 */
public interface ConfigurationProperty extends Serializable {

	/**
	 * Fetches the property key.
	 *
	 * @return The property key.
	 */
	String getKey();

	/**
	 * Sets the property key.
	 *
	 * @param key
	 *            The property key.
	 */
	void setKey(String key);

	/**
	 * Fetches the property's value.
	 *
	 * @return The property's value.
	 */
	String getValue();

	/**
	 * Sets the property's value.
	 *
	 * @param value
	 *            The property's value.
	 */
	void setValue(String value);

	/**
	 * Fetches the property's name, containing a human-friendly name.
	 *
	 * @return The property's name.
	 */
	String getName();

	/**
	 * Sets the property's name, containing a human-friendly name.
	 *
	 * @param name
	 *            The property's name.
	 */
	void setName(String name);

	/**
	 * Fetches the property's description, containing an explanation about what this property is
	 * used for.
	 *
	 * @return The property description.
	 */
	String getDescription();

	/**
	 * Sets the property's description, containing an explanation about what this property is used
	 * for.
	 *
	 * @param description
	 *            The property's description.
	 */
	void setDescription(String description);

	/**
	 * Fetches the category to which this property belongs.
	 *
	 * @return The category.
	 */
	ConfigurationCategory getCategory();

	/**
	 * Sets the category to which this property should be attached.
	 *
	 * @param category
	 *            The category.
	 */
	void setCategory(ConfigurationCategory category);

	/**
	 * Fetches the cluster node variations, which allow to define node-specific values.
	 *
	 * @return The cluster node variations.
	 */
	Set<ClusterNodeVariation> getClusterNodeVariations();

	/**
	 * Sets the cluster node variations, which allow to define node-specific values.
	 *
	 * @param clusterNodeVariations
	 *            The cluster node variations.
	 */
	void setClusterNodeVariations(Set<ClusterNodeVariation> clusterNodeVariations);

}
