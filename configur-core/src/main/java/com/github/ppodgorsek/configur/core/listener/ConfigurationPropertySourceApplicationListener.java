package com.github.ppodgorsek.configur.core.listener;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.github.ppodgorsek.configur.core.env.ConfigurationPropertySource;
import com.github.ppodgorsek.configur.core.service.ConfigurationService;

/**
 * Application listener which registers a new {@link org.springframework.core.env.PropertySource
 * PropertySource} for configuration properties, once the application context has been loaded.
 *
 * @author Paul Podgorsek
 */
public class ConfigurationPropertySourceApplicationListener implements GenericApplicationListener {

	private final ApplicationContext applicationContext;

	private String configurationServiceBeanName;

	/**
	 * Default constructor.
	 *
	 * @param applicationContext
	 *            The application context.
	 */
	public ConfigurationPropertySourceApplicationListener(
			final ApplicationContext applicationContext) {

		super();

		Assert.notNull(applicationContext, "The application context is required");

		this.applicationContext = applicationContext;
	}

	/**
	 * Constructor allowing to set the name of the {@link ConfigurationService} bean which should be
	 * used in the {@link org.springframework.core.env.PropertySource PropertySource}.
	 *
	 * @param applicationContext
	 *            The application context.
	 * @param configurationServiceBeanName
	 *            The name of the {@link ConfigurationService} bean.
	 */
	public ConfigurationPropertySourceApplicationListener(
			final ApplicationContext applicationContext,
			final String configurationServiceBeanName) {

		this(applicationContext);

		this.configurationServiceBeanName = configurationServiceBeanName;
	}

	@Override
	public void onApplicationEvent(final ApplicationEvent event) {

		final Environment environment = applicationContext.getEnvironment();

		Assert.isInstanceOf(ConfigurableEnvironment.class, environment,
				"Impossible to add the JpaConfigurationService to the list of property sources");

		final ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
		final MutablePropertySources propertySources = configurableEnvironment.getPropertySources();

		if (StringUtils.hasText(configurationServiceBeanName)) {

			final ConfigurationService jpaConfigurationService = applicationContext
					.getBean(configurationServiceBeanName, ConfigurationService.class);

			propertySources.addFirst(new ConfigurationPropertySource(configurationServiceBeanName,
					jpaConfigurationService));
		}
		else {
			final Map<String, ConfigurationService> configurationServices = applicationContext
					.getBeansOfType(ConfigurationService.class);

			for (final Entry<String, ConfigurationService> configurationService : configurationServices
					.entrySet()) {
				propertySources.addFirst(new ConfigurationPropertySource(
						configurationService.getKey(), configurationService.getValue()));
			}
		}
	}

	@Override
	public boolean supportsEventType(final ResolvableType eventType) {
		return eventType.isAssignableFrom(ContextRefreshedEvent.class);
	}

}
