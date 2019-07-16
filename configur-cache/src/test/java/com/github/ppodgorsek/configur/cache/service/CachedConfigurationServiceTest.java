package com.github.ppodgorsek.configur.cache.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.github.ppodgorsek.configur.cache.CacheConfigurationMetadata;
import com.github.ppodgorsek.configur.cache.config.CacheTestConfiguration;
import com.github.ppodgorsek.configur.core.model.ClusterNodeVariation;
import com.github.ppodgorsek.configur.core.model.ConfigurationCategory;
import com.github.ppodgorsek.configur.core.model.ConfigurationProperty;
import com.github.ppodgorsek.configur.core.service.ConfigurationService;

/**
 * Tests for the {@link CachedConfigurationService}.
 *
 * @author Paul Podgorsek
 */
@SpringJUnitConfig(CacheTestConfiguration.class)
public class CachedConfigurationServiceTest {

	private static final String KEY = "testKey";
	private static final String VALUE = "testValue";

	@Resource
	private CacheManager cacheManager;

	@Resource
	private ConfigurationService cachedConfigurationService;

	@Resource
	private ConfigurationService mockConfigurationService;

	private ConfigurationCategory category;
	private ConfigurationProperty property;

	@BeforeEach
	public void setUp() {

		cacheManager.getCache(CacheConfigurationMetadata.CACHE_REGION).clear();

		category = new ConfigurationCategory() {

			private static final long serialVersionUID = 1L;

			@Override
			public void setParent(final ConfigurationCategory parent) {
				// Nothing specific here.
			}

			@Override
			public void setName(final String name) {
				// Nothing specific here.
			}

			@Override
			public void setKey(final String key) {
				// Nothing specific here.
			}

			@Override
			public void setDescription(final String description) {
				// Nothing specific here.
			}

			@Override
			public ConfigurationCategory getParent() {
				return null;
			}

			@Override
			public String getName() {
				return null;
			}

			@Override
			public String getKey() {
				return KEY;
			}

			@Override
			public String getDescription() {
				return null;
			}
		};

		property = new ConfigurationProperty() {

			private static final long serialVersionUID = 1L;

			@Override
			public void setValue(final String value) {
				// Nothing specific here.
			}

			@Override
			public void setName(final String name) {
				// Nothing specific here.
			}

			@Override
			public void setKey(final String key) {
				// Nothing specific here.
			}

			@Override
			public void setDescription(final String description) {
				// Nothing specific here.
			}

			@Override
			public void setClusterNodeVariations(
					final Set<ClusterNodeVariation> clusterNodeVariations) {
				// Nothing specific here.
			}

			@Override
			public void setCategory(final ConfigurationCategory category) {
				// Nothing specific here.
			}

			@Override
			public String getValue() {
				return VALUE;
			}

			@Override
			public String getName() {
				return null;
			}

			@Override
			public String getKey() {
				return KEY;
			}

			@Override
			public String getDescription() {
				return null;
			}

			@Override
			public Set<ClusterNodeVariation> getClusterNodeVariations() {
				return null;
			}

			@Override
			public ConfigurationCategory getCategory() {
				return null;
			}
		};

		EasyMock.reset(mockConfigurationService);

		EasyMock.expect(mockConfigurationService.getProperty(KEY)).andReturn(property);
		EasyMock.expect(mockConfigurationService.getPropertyValue(KEY)).andReturn(VALUE);
	}

	@AfterEach
	public void tearDown() {
		EasyMock.verify(mockConfigurationService);
	}

	@Test
	public void deleteCategory() {

		mockConfigurationService.deleteCategory(KEY);
		EasyMock.expectLastCall();

		EasyMock.replay(mockConfigurationService);

		cachedConfigurationService.getProperty(KEY);
		cachedConfigurationService.getPropertyValue(KEY);

		cachedConfigurationService.deleteCategory(KEY);
	}

	@Test
	public void deleteProperty() {

		mockConfigurationService.deleteProperty(KEY);
		EasyMock.expectLastCall();

		EasyMock.expect(mockConfigurationService.getProperty(KEY)).andReturn(property);

		EasyMock.replay(mockConfigurationService);

		cachedConfigurationService.getProperty(KEY);
		cachedConfigurationService.getPropertyValue(KEY);

		cachedConfigurationService.getProperty(KEY);
		cachedConfigurationService.deleteProperty(KEY);
		cachedConfigurationService.getProperty(KEY);
	}

	@Test
	public void getByCategory() {

		final List<ConfigurationProperty> properties = Collections.singletonList(property);

		EasyMock.expect(mockConfigurationService.getByCategory(KEY)).andReturn(properties);

		EasyMock.replay(mockConfigurationService);

		cachedConfigurationService.getProperty(KEY);
		cachedConfigurationService.getPropertyValue(KEY);

		final List<ConfigurationProperty> results = cachedConfigurationService
				.getByCategory(category.getKey());

		assertNotNull(results, "The results are required");
		assertEquals(properties, results, "Wrong list of results");
	}

	@Test
	public void getProperty() {

		EasyMock.replay(mockConfigurationService);

		cachedConfigurationService.getProperty(KEY);
		cachedConfigurationService.getPropertyValue(KEY);

		ConfigurationProperty result = cachedConfigurationService.getProperty(KEY);

		assertNotNull(result, "The result is required");
		assertEquals(property, result, "Wrong result");

		result = cachedConfigurationService.getProperty(KEY);

		assertNotNull(result, "The result is required");
		assertEquals(property, result, "Wrong result");
	}

	@Test
	public void getPropertyValue() {

		EasyMock.replay(mockConfigurationService);

		cachedConfigurationService.getProperty(KEY);
		cachedConfigurationService.getPropertyValue(KEY);

		final String result = cachedConfigurationService.getPropertyValue(KEY);

		assertNotNull(result, "The result is required");
		assertEquals(property.getValue(), result, "Wrong result");
	}

	@Test
	public void saveCategory() {

		EasyMock.expect(mockConfigurationService.save(category)).andReturn(category);

		EasyMock.replay(mockConfigurationService);

		cachedConfigurationService.getProperty(KEY);
		cachedConfigurationService.getPropertyValue(KEY);

		cachedConfigurationService.save(category);
	}

	@Test
	public void saveProperty() {

		EasyMock.expect(mockConfigurationService.save(property)).andReturn(property);

		EasyMock.expect(mockConfigurationService.getPropertyValue(KEY)).andReturn(VALUE);

		EasyMock.replay(mockConfigurationService);

		cachedConfigurationService.getProperty(KEY);
		cachedConfigurationService.getPropertyValue(KEY);

		cachedConfigurationService.getProperty(KEY);
		cachedConfigurationService.getPropertyValue(KEY);
		cachedConfigurationService.save(property);
		cachedConfigurationService.getProperty(KEY);
		cachedConfigurationService.getPropertyValue(KEY);
	}

}
