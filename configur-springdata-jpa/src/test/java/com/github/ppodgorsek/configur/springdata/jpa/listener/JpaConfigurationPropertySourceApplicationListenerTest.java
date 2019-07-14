package com.github.ppodgorsek.configur.springdata.jpa.listener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.ConfigurableEnvironment;

import com.github.ppodgorsek.configur.core.listener.ConfigurationPropertySourceApplicationListener;
import com.github.ppodgorsek.configur.springdata.jpa.dao.AbstractDbUnitTest;

/**
 * Tests for {@link ConfigurationPropertySourceApplicationListener} using a JPA repository.
 *
 * @author Paul Podgorsek
 */
public class JpaConfigurationPropertySourceApplicationListenerTest extends AbstractDbUnitTest {

	@Resource
	private ConfigurableEnvironment environment;

	@Test
	public void getPropertyFromEnvironmentWithCorrectKey() {

		final String result = environment.getProperty(property15.getKey());

		assertNotNull(result, "The result should not be null");
		assertEquals(property15.getValue(), result, "Wrong result");
	}

	@Test
	public void getPropertyFromEnvironmentWithNullKey() {

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			environment.getProperty(null);
		});
	}

	@Test
	public void getPropertyFromEnvironmentWithUnknownKey() {

		final String result = environment.getProperty(nonPersistedProperty.getKey());

		assertNull(result, "The result should be null");
	}

}
