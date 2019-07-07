package com.github.ppodgorsek.configur.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.ppodgorsek.configur.core.model.ConfigurationCategory;
import com.github.ppodgorsek.configur.core.model.ConfigurationProperty;
import com.github.ppodgorsek.configur.core.service.ConfigurationService;

/**
 * Tests for the {@link ConfigurationServiceDelegator}.
 *
 * @author Paul Podgorsek
 */
public class ConfigurationServiceDelegatorTest {

	private static final String KEY = "testKey";

	private ConfigurationServiceDelegator configurationServiceDelegator;

	@Mock
	private ConfigurationService delegate1;

	@Mock
	private ConfigurationService delegate2;

	private ConfigurationProperty property1;
	private ConfigurationProperty property2;
	private ConfigurationProperty property3;
	private ConfigurationProperty property4;
	private ConfigurationProperty property5;

	@BeforeEach
	public void setUp() {

		EasyMockSupport.injectMocks(this);

		final Collection<ConfigurationService> delegates = new ArrayList<>();
		delegates.add(delegate1);
		delegates.add(delegate2);

		configurationServiceDelegator = new ConfigurationServiceDelegator(delegates);

		property1 = new ConfigurationProperty() {

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
				return null;
			}

			@Override
			public String getDescription() {
				return null;
			}

			@Override
			public ConfigurationCategory getCategory() {
				return null;
			}
		};

		property2 = new ConfigurationProperty() {

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
				return null;
			}

			@Override
			public String getDescription() {
				return null;
			}

			@Override
			public ConfigurationCategory getCategory() {
				return null;
			}
		};

		property3 = new ConfigurationProperty() {

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
				return null;
			}

			@Override
			public String getDescription() {
				return null;
			}

			@Override
			public ConfigurationCategory getCategory() {
				return null;
			}
		};

		property4 = new ConfigurationProperty() {

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
				return null;
			}

			@Override
			public String getDescription() {
				return null;
			}

			@Override
			public ConfigurationCategory getCategory() {
				return null;
			}
		};

		property5 = new ConfigurationProperty() {

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
				return null;
			}

			@Override
			public String getDescription() {
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

		EasyMock.verify(delegate1);
		EasyMock.verify(delegate2);
	}

	@Test
	public void constructWithNullDelegates() {

		EasyMock.replay(delegate1);
		EasyMock.replay(delegate2);

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new ConfigurationServiceDelegator(null);
		});
	}

	@Test
	public void constructWithEmptyDelegates() {

		EasyMock.replay(delegate1);
		EasyMock.replay(delegate2);

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new ConfigurationServiceDelegator(new ArrayList<>());
		});
	}

	@Test
	public void deleteCategoryWithCorrectKey() {

		delegate1.deleteCategory(KEY);
		EasyMock.expectLastCall();

		delegate2.deleteCategory(KEY);
		EasyMock.expectLastCall();

		EasyMock.replay(delegate1);
		EasyMock.replay(delegate2);

		configurationServiceDelegator.deleteCategory(KEY);
	}

	@Test
	public void deleteCategoryWithNullKey() {

		delegate1.deleteCategory(null);
		EasyMock.expectLastCall();

		delegate2.deleteCategory(null);
		EasyMock.expectLastCall();

		EasyMock.replay(delegate1);
		EasyMock.replay(delegate2);

		configurationServiceDelegator.deleteCategory(null);
	}

	@Test
	public void deletePropertyWithCorrectKey() {

		delegate1.deleteProperty(KEY);
		EasyMock.expectLastCall();

		delegate2.deleteProperty(KEY);
		EasyMock.expectLastCall();

		EasyMock.replay(delegate1);
		EasyMock.replay(delegate2);

		configurationServiceDelegator.deleteProperty(KEY);
	}

	@Test
	public void deletePropertyWithNullKey() {

		delegate1.deleteProperty(null);
		EasyMock.expectLastCall();

		delegate2.deleteProperty(null);
		EasyMock.expectLastCall();

		EasyMock.replay(delegate1);
		EasyMock.replay(delegate2);

		configurationServiceDelegator.deleteProperty(null);
	}

	@Test
	public void getByCategory() {

		final List<ConfigurationProperty> delegate1Properties = new ArrayList<>();
		delegate1Properties.add(property1);
		delegate1Properties.add(property3);

		final List<ConfigurationProperty> delegate2Properties = new ArrayList<>();
		delegate2Properties.add(property2);
		delegate2Properties.add(property4);
		delegate2Properties.add(property5);

		final List<ConfigurationProperty> allProperties = new ArrayList<>();
		allProperties.add(property1);
		allProperties.add(property3);
		allProperties.add(property2);
		allProperties.add(property4);
		allProperties.add(property5);

		EasyMock.expect(delegate1.getByCategory(KEY)).andReturn(delegate1Properties);
		EasyMock.expect(delegate2.getByCategory(KEY)).andReturn(delegate2Properties);

		EasyMock.replay(delegate1);
		EasyMock.replay(delegate2);

		final List<ConfigurationProperty> results = configurationServiceDelegator
				.getByCategory(KEY);

		assertNotNull(results, "The results should not be null");
		assertEquals(allProperties, results, "Wrong list of results");
	}

	@Test
	public void getByPropertyFoundInDelegate1() {

		EasyMock.expect(delegate1.getByProperty(KEY)).andReturn(property1);

		EasyMock.replay(delegate1);
		EasyMock.replay(delegate2);

		final ConfigurationProperty result = configurationServiceDelegator.getByProperty(KEY);

		assertNotNull(result, "The result should not be null");
		assertEquals(property1, result, "Wrong result");
	}

	@Test
	public void getByPropertyFoundInDelegate2() {

		EasyMock.expect(delegate1.getByProperty(KEY)).andReturn(null);
		EasyMock.expect(delegate2.getByProperty(KEY)).andReturn(property1);

		EasyMock.replay(delegate1);
		EasyMock.replay(delegate2);

		final ConfigurationProperty result = configurationServiceDelegator.getByProperty(KEY);

		assertNotNull(result, "The result should not be null");
		assertEquals(property1, result, "Wrong result");
	}

	@Test
	public void getByPropertyNotFound() {

		EasyMock.expect(delegate1.getByProperty(KEY)).andReturn(null);
		EasyMock.expect(delegate2.getByProperty(KEY)).andReturn(null);

		EasyMock.replay(delegate1);
		EasyMock.replay(delegate2);

		final ConfigurationProperty result = configurationServiceDelegator.getByProperty(KEY);

		assertNull(result, "The result should be null");
	}

	@Test
	public void saveCategory() {

		final ConfigurationCategory category = new ConfigurationCategory() {

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
				return null;
			}

			@Override
			public String getDescription() {
				return null;
			}
		};

		EasyMock.expect(delegate1.save(category)).andReturn(category);

		EasyMock.replay(delegate1);
		EasyMock.replay(delegate2);

		final ConfigurationCategory result = configurationServiceDelegator.save(category);

		assertNotNull(result, "The result should be null");
		assertEquals(category, result, "Wrong persisted result");
	}

	@Test
	public void saveProperty() {

		EasyMock.expect(delegate1.save(property2)).andReturn(property2);

		EasyMock.replay(delegate1);
		EasyMock.replay(delegate2);

		final ConfigurationProperty result = configurationServiceDelegator.save(property2);

		assertNotNull(result, "The result should be null");
		assertEquals(property2, result, "Wrong persisted result");
	}

}
