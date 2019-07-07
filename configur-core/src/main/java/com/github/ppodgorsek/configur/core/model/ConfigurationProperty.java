package com.github.ppodgorsek.configur.core.model;

import java.io.Serializable;

/**
 * @author Paul Podgorsek
 */
public interface ConfigurationProperty extends Serializable {

	String getKey();

	void setKey(String key);

	String getValue();

	void setValue(String value);

	String getName();

	void setName(String name);

	String getDescription();

	void setDescription(String description);

	ConfigurationCategory getCategory();

	void setCategory(ConfigurationCategory category);

}
