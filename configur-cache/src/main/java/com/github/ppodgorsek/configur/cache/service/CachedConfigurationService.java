package com.github.ppodgorsek.configur.cache.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.util.Assert;

import com.github.ppodgorsek.configur.cache.CacheConfigurationMetadata;
import com.github.ppodgorsek.configur.core.model.ConfigurationCategory;
import com.github.ppodgorsek.configur.core.model.ConfigurationProperty;
import com.github.ppodgorsek.configur.core.service.ConfigurationService;

/**
 * Adds a layer of cache on top of an existing {@link ConfigurationService}. Only properties are
 * cached, not their categories; this avoids complex invalidation cases.
 *
 * @author Paul Podgorsek
 */
public class CachedConfigurationService implements ConfigurationService {

	private final ConfigurationService delegate;

	/**
	 * Default constructor.
	 *
	 * @param configurationService
	 *            The configuration service which must be cached.
	 */
	public CachedConfigurationService(final ConfigurationService configurationService) {

		super();

		Assert.notNull(configurationService, "The configuration service is required");

		delegate = configurationService;
	}

	@Override
	public void deleteCategory(final String key) {
		delegate.deleteCategory(key);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = CacheConfigurationMetadata.CACHE_REGION, key = "#key + '#getProperty()'", condition = "#key!=null"),
			@CacheEvict(value = CacheConfigurationMetadata.CACHE_REGION, key = "#key + '#getPropertyValue()'", condition = "#key!=null") })
	public void deleteProperty(final String key) {
		delegate.deleteProperty(key);
	}

	@Override
	public List<ConfigurationProperty> getByCategory(final String category) {
		return delegate.getByCategory(category);
	}

	@Override
	@Cacheable(value = CacheConfigurationMetadata.CACHE_REGION, key = "#key + '#getProperty()'", condition = "#key!=null")
	public ConfigurationProperty getProperty(final String key) {
		return delegate.getProperty(key);
	}

	@Override
	@Cacheable(value = CacheConfigurationMetadata.CACHE_REGION, key = "#key + '#getPropertyValue()'", condition = "#key!=null")
	public String getPropertyValue(final String key) {
		return delegate.getPropertyValue(key);
	}

	@Override
	public ConfigurationCategory save(final ConfigurationCategory category) {
		return delegate.save(category);
	}

	@Override
	@Caching(evict = @CacheEvict(value = CacheConfigurationMetadata.CACHE_REGION, key = "#property.key + '#getPropertyValue()'", condition = "#property!=null && #property.key!=null"), put = @CachePut(value = CacheConfigurationMetadata.CACHE_REGION, key = "#property.key + '#getProperty()'", condition = "#property!=null && #property.key!=null"))
	public ConfigurationProperty save(final ConfigurationProperty property) {
		return delegate.save(property);
	}

}
