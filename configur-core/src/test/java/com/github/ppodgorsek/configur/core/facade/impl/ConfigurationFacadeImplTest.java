package com.github.ppodgorsek.configur.core.facade.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashSet;
import java.util.Set;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.ppodgorsek.configur.core.model.ClusterNodeVariation;
import com.github.ppodgorsek.configur.core.model.ConfigurationCategory;
import com.github.ppodgorsek.configur.core.model.ConfigurationProperty;
import com.github.ppodgorsek.configur.core.service.ConfigurationService;
import com.github.ppodgorsek.configur.core.strategy.impl.FixedClusterNodeDeterminationStrategy;

/**
 * Tests for the {@link ConfigurationFacadeImpl}.
 *
 * @author Paul Podgorsek
 */
public class ConfigurationFacadeImplTest {

	private static final String KEY = "testKey";
	private static final String NODE_ID = "testNode";
	private static final String VALUE = "1";

	private ConfigurationFacadeImpl facade;

	@Mock
	private ConfigurationService configurationService;

	private ConfigurationProperty propertyWithoutValue;
	private ConfigurationProperty propertyWithValueAndVariations;
	private ConfigurationProperty propertyWithValueButNoVariations;

	@BeforeEach
	public void setUp() {

		EasyMockSupport.injectMocks(this);

		facade = new ConfigurationFacadeImpl(configurationService);

		propertyWithoutValue = new ConfigurationProperty() {

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

			@Override
			public Set<ClusterNodeVariation> getClusterNodeVariations() {
				return null;
			}

			@Override
			public ConfigurationCategory getCategory() {
				return null;
			}
		};

		propertyWithValueAndVariations = new ConfigurationProperty() {

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

				final ClusterNodeVariation variation = new ClusterNodeVariation() {

					private static final long serialVersionUID = 1L;

					@Override
					public void setValue(final String value) {
						// Nothing specific here.
					}

					@Override
					public void setNodeId(final String nodeId) {
						// Nothing specific here.
					}

					@Override
					public String getValue() {
						return "2";
					}

					@Override
					public String getNodeId() {
						return NODE_ID;
					}
				};

				final Set<ClusterNodeVariation> variations = new HashSet<>();
				variations.add(variation);

				return variations;
			}

			@Override
			public ConfigurationCategory getCategory() {
				return null;
			}
		};

		propertyWithValueButNoVariations = new ConfigurationProperty() {

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
	}

	@AfterEach
	public void tearDown() {

		EasyMock.verify(configurationService);
	}

	@Test
	public void getValueAsDoubleWithCorrectKeyAndNoNodeVariation() {

		EasyMock.expect(configurationService.getByProperty(KEY))
				.andReturn(propertyWithValueButNoVariations);
		EasyMock.replay(configurationService);

		final Double result = facade.getValueAsDouble(KEY);

		assertNotNull(result, "The result should not be null");
		assertEquals(1D, result, "Wrong result");
	}

	@Test
	public void getValueAsDoubleWithCorrectKeyAndNoNodeVariationButNoResult() {

		EasyMock.expect(configurationService.getByProperty(KEY)).andReturn(propertyWithoutValue);
		EasyMock.replay(configurationService);

		final Double result = facade.getValueAsDouble(KEY);

		assertNull(result, "The result should be null");
	}

	@Test
	public void getValueAsDoubleWithCorrectKeyAndNodeVariation() {

		facade = new ConfigurationFacadeImpl(configurationService,
				new FixedClusterNodeDeterminationStrategy(NODE_ID));

		EasyMock.expect(configurationService.getByProperty(KEY))
				.andReturn(propertyWithValueAndVariations);
		EasyMock.replay(configurationService);

		final Double result = facade.getValueAsDouble(KEY);

		assertNotNull(result, "The result should not be null");
		assertEquals(2D, result, "Wrong result");
	}

	@Test
	public void getValueAsDoubleWithNullKeyAndNoNodeVariation() {

		EasyMock.expect(configurationService.getByProperty(null)).andReturn(null);
		EasyMock.replay(configurationService);

		final Double result = facade.getValueAsDouble(null);

		assertNull(result, "The result should be null");
	}

	@Test
	public void getValueAsIntegerWithCorrectKeyAndNoNodeVariation() {

		EasyMock.expect(configurationService.getByProperty(KEY))
				.andReturn(propertyWithValueButNoVariations);
		EasyMock.replay(configurationService);

		final Integer result = facade.getValueAsInteger(KEY);

		assertNotNull(result, "The result should not be null");
		assertEquals(1, result, "Wrong result");
	}

	@Test
	public void getValueAsIntegerWithCorrectKeyAndNoNodeVariationButNoResult() {

		EasyMock.expect(configurationService.getByProperty(KEY)).andReturn(propertyWithoutValue);
		EasyMock.replay(configurationService);

		final Integer result = facade.getValueAsInteger(KEY);

		assertNull(result, "The result should be null");
	}

	@Test
	public void getValueAsIntegerWithCorrectKeyAndNodeVariation() {

		facade = new ConfigurationFacadeImpl(configurationService,
				new FixedClusterNodeDeterminationStrategy(NODE_ID));

		EasyMock.expect(configurationService.getByProperty(KEY))
				.andReturn(propertyWithValueAndVariations);
		EasyMock.replay(configurationService);

		final Integer result = facade.getValueAsInteger(KEY);

		assertNotNull(result, "The result should not be null");
		assertEquals(2, result, "Wrong result");
	}

	@Test
	public void getValueAsIntegerWithNullKeyAndNoNodeVariation() {

		EasyMock.expect(configurationService.getByProperty(null)).andReturn(null);
		EasyMock.replay(configurationService);

		final Integer result = facade.getValueAsInteger(null);

		assertNull(result, "The result should be null");
	}

	@Test
	public void getValueAsLongWithCorrectKeyAndNoNodeVariation() {

		EasyMock.expect(configurationService.getByProperty(KEY))
				.andReturn(propertyWithValueButNoVariations);
		EasyMock.replay(configurationService);

		final Long result = facade.getValueAsLong(KEY);

		assertNotNull(result, "The result should not be null");
		assertEquals(1L, result, "Wrong result");
	}

	@Test
	public void getValueAsLongWithCorrectKeyAndNoNodeVariationButNoResult() {

		EasyMock.expect(configurationService.getByProperty(KEY)).andReturn(propertyWithoutValue);
		EasyMock.replay(configurationService);

		final Long result = facade.getValueAsLong(KEY);

		assertNull(result, "The result should be null");
	}

	@Test
	public void getValueAsLongWithCorrectKeyAndNodeVariation() {

		facade = new ConfigurationFacadeImpl(configurationService,
				new FixedClusterNodeDeterminationStrategy(NODE_ID));

		EasyMock.expect(configurationService.getByProperty(KEY))
				.andReturn(propertyWithValueAndVariations);
		EasyMock.replay(configurationService);

		final Long result = facade.getValueAsLong(KEY);

		assertNotNull(result, "The result should not be null");
		assertEquals(2L, result, "Wrong result");
	}

	@Test
	public void getValueAsLongWithNullKeyAndNoNodeVariation() {

		EasyMock.expect(configurationService.getByProperty(null)).andReturn(null);
		EasyMock.replay(configurationService);

		final Long result = facade.getValueAsLong(null);

		assertNull(result, "The result should be null");
	}

	@Test
	public void getValueAsStringWithCorrectKeyAndNoNodeVariation() {

		EasyMock.expect(configurationService.getByProperty(KEY))
				.andReturn(propertyWithValueButNoVariations);
		EasyMock.replay(configurationService);

		final String result = facade.getValueAsString(KEY);

		assertNotNull(result, "The result should not be null");
		assertEquals("1", result, "Wrong result");
	}

	@Test
	public void getValueAsStringWithCorrectKeyAndNoNodeVariationButNoResult() {

		EasyMock.expect(configurationService.getByProperty(KEY)).andReturn(propertyWithoutValue);
		EasyMock.replay(configurationService);

		final String result = facade.getValueAsString(KEY);

		assertNull(result, "The result should be null");
	}

	@Test
	public void getValueAsStringWithCorrectKeyAndNodeVariation() {

		facade = new ConfigurationFacadeImpl(configurationService,
				new FixedClusterNodeDeterminationStrategy(NODE_ID));

		EasyMock.expect(configurationService.getByProperty(KEY))
				.andReturn(propertyWithValueAndVariations);
		EasyMock.replay(configurationService);

		final String result = facade.getValueAsString(KEY);

		assertNotNull(result, "The result should not be null");
		assertEquals("2", result, "Wrong result");
	}

	@Test
	public void getValueAsStringWithNullKeyAndNoNodeVariation() {

		EasyMock.expect(configurationService.getByProperty(null)).andReturn(null);
		EasyMock.replay(configurationService);

		final String result = facade.getValueAsString(null);

		assertNull(result, "The result should be null");
	}

}
