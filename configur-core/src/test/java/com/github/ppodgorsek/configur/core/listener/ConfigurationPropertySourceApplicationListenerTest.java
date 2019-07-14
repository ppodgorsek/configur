package com.github.ppodgorsek.configur.core.listener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.github.ppodgorsek.configur.core.config.CoreTestConfiguration;
import com.github.ppodgorsek.configur.core.model.ClusterNodeVariation;
import com.github.ppodgorsek.configur.core.model.ConfigurationCategory;
import com.github.ppodgorsek.configur.core.model.ConfigurationProperty;
import com.github.ppodgorsek.configur.core.service.ConfigurationService;
import com.github.ppodgorsek.configur.core.service.impl.ConfigurationServiceDelegator;

/**
 * Tests for {@link ConfigurationPropertySourceApplicationListener}.
 *
 * @author Paul Podgorsek
 */
@SpringJUnitConfig(CoreTestConfiguration.class)
public class ConfigurationPropertySourceApplicationListenerTest {

	private static final String KEY = "testKey";
	private static final String VALUE = "testValue";

	@Resource
	private ConfigurationServiceDelegator configurationServiceDelegator;

	@Resource
	private ConfigurableEnvironment environment;

	private ConfigurationService delegate1;
	private ConfigurationService delegate2;

	private ConfigurationProperty property;

	@BeforeEach
	public void setUp() {

		final List<ConfigurationService> delegates = configurationServiceDelegator.getDelegates();
		delegate1 = delegates.get(0);
		delegate2 = delegates.get(1);

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
	}

	@AfterEach
	public void tearDown() {

		EasyMock.verify(delegate1);
		EasyMock.verify(delegate2);
	}

	@Test
	public void getPropertyFromEnvironmentWithCorrectKey() {

		EasyMock.reset(delegate1);
		EasyMock.reset(delegate2);

		EasyMock.expect(delegate1.getProperty(KEY)).andReturn(null);
		EasyMock.expect(delegate2.getProperty(KEY)).andReturn(property);

		EasyMock.replay(delegate1);
		EasyMock.replay(delegate2);

		final String result = environment.getProperty(KEY);

		assertNotNull(result, "The result should not be null");
		assertEquals(VALUE, result, "Wrong result");
	}

	@Test
	public void getPropertyFromEnvironmentWithNullKey() {

		EasyMock.reset(delegate1);
		EasyMock.reset(delegate2);

		EasyMock.expect(delegate1.getProperty(null)).andReturn(null);
		EasyMock.expect(delegate2.getProperty(null)).andReturn(null);

		EasyMock.replay(delegate1);
		EasyMock.replay(delegate2);

		Assertions.assertThrows(NullPointerException.class, () -> {
			environment.getProperty(null);
		});
	}

	@Test
	public void getPropertyFromEnvironmentWithUnknownKey() {

		EasyMock.reset(delegate1);
		EasyMock.reset(delegate2);

		EasyMock.expect(delegate1.getProperty(KEY)).andReturn(null);
		EasyMock.expect(delegate2.getProperty(KEY)).andReturn(null);

		EasyMock.replay(delegate1);
		EasyMock.replay(delegate2);

		final String result = environment.getProperty(KEY);

		assertNull(result, "The result should  be null");
	}

}
