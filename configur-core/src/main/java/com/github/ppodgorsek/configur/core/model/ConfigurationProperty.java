package com.github.ppodgorsek.configur.core.model;

/**
 * @author Paul Podgorsek
 */
public interface ConfigurationProperty {

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
