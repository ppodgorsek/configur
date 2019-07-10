package com.github.ppodgorsek.configur.core.facade;

/**
 * Facade allowing to fetch property values according to keys. As most configuration interactions in
 * an application are read-only, this class should ease the project's usage. For clustered
 * environments, those values will be node-specific, if it applies.
 *
 * @author Paul Podgorsek
 */
public interface ConfigurationFacade {

	/**
	 * Fetches a property and converts its value to a Double.
	 * 
	 * @param key
	 *            The property key.
	 * @return The Double representation of the property having the provided key, or {@code null} if
	 *         there isn't one.
	 */
	Double getValueAsDouble(String key);

	/**
	 * Fetches a property and converts its value to an Integer.
	 * 
	 * @param key
	 *            The property key.
	 * @return The Integer representation of the property having the provided key, or {@code null}
	 *         if there isn't one.
	 */
	Integer getValueAsInteger(String key);

	/**
	 * Fetches a property and converts its value to a Long.
	 * 
	 * @param key
	 *            The property key.
	 * @return The Long representation of the property having the provided key, or {@code null} if
	 *         there isn't one.
	 */
	Long getValueAsLong(String key);

	/**
	 * Fetches a property.
	 * 
	 * @param key
	 *            The property key.
	 * @return The String representation of the property having the provided key, or {@code null} if
	 *         there isn't one.
	 */
	String getValueAsString(String key);

}
