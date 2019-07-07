package com.github.ppodgorsek.configur.core.model;

import java.io.Serializable;

/**
 * Model allowing the categorisation of properties.
 *
 * @author Paul Podgorsek
 */
public interface ConfigurationCategory extends Serializable {

	String getKey();

	void setKey(String key);

	String getName();

	void setName(String name);

	String getDescription();

	void setDescription(String description);

	ConfigurationCategory getParent();

	void setParent(ConfigurationCategory parent);

}
