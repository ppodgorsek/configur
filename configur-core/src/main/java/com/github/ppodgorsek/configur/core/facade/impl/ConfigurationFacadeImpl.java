package com.github.ppodgorsek.configur.core.facade.impl;

import java.util.Objects;
import java.util.Set;

import org.springframework.util.Assert;

import com.github.ppodgorsek.configur.core.facade.ConfigurationFacade;
import com.github.ppodgorsek.configur.core.model.ClusterNodeVariation;
import com.github.ppodgorsek.configur.core.model.ConfigurationProperty;
import com.github.ppodgorsek.configur.core.service.ConfigurationService;
import com.github.ppodgorsek.configur.core.strategy.ClusterNodeDeterminationStrategy;
import com.github.ppodgorsek.configur.core.strategy.impl.FixedClusterNodeDeterminationStrategy;

/**
 * Default implementation of the {@link ConfigurationFacade}.
 *
 * @author Paul Podgorsek
 */
public class ConfigurationFacadeImpl implements ConfigurationFacade {

	private final ClusterNodeDeterminationStrategy clusterNodeDeterminationStrategy;

	private final ConfigurationService configurationService;

	/**
	 * Default constructor.
	 *
	 * @param configurationService
	 *            The configuration service, to fetch properties.
	 */
	public ConfigurationFacadeImpl(final ConfigurationService configurationService) {
		this(configurationService, new FixedClusterNodeDeterminationStrategy());
	}

	/**
	 * Constructor allowing to define the cluster node determination strategy.
	 *
	 * @param configurationService
	 *            The configuration service, to fetch properties.
	 * @param clusterNodeDeterminationStrategy
	 *            The cluster node determination strategy.
	 */
	public ConfigurationFacadeImpl(final ConfigurationService configurationService,
			final ClusterNodeDeterminationStrategy clusterNodeDeterminationStrategy) {

		super();

		Assert.notNull(configurationService, "The configuration service is required");
		Assert.notNull(clusterNodeDeterminationStrategy,
				"The cluster node determination strategy is required");

		this.configurationService = configurationService;
		this.clusterNodeDeterminationStrategy = clusterNodeDeterminationStrategy;
	}

	@Override
	public Double getValueAsDouble(final String key) {

		final String value = getValueAsString(key);

		if (value == null) {
			return null;
		}
		else {
			return Double.valueOf(value);
		}
	}

	@Override
	public Integer getValueAsInteger(final String key) {

		final String value = getValueAsString(key);

		if (value == null) {
			return null;
		}
		else {
			return Integer.valueOf(value);
		}
	}

	@Override
	public Long getValueAsLong(final String key) {

		final String value = getValueAsString(key);

		if (value == null) {
			return null;
		}
		else {
			return Long.valueOf(value);
		}
	}

	@Override
	public String getValueAsString(final String key) {

		final ConfigurationProperty property = configurationService.getByProperty(key);
		final String clusterNodeProperty = getClusterNodeProperty(property);

		return clusterNodeProperty;
	}

	/**
	 * Fetches the value of a property for the current cluster node.
	 *
	 * @param property
	 *            The property for which the value must be fetched.
	 * @return The property value corresponding to the current cluster node, or the global value if
	 *         there is no cluster-specific value. Can be {@code null}.
	 */
	private String getClusterNodeProperty(final ConfigurationProperty property) {

		if (property == null) {
			return null;
		}

		final String nodeId = clusterNodeDeterminationStrategy.getNodeId();
		final Set<ClusterNodeVariation> clusterNodeVariations = property.getClusterNodeVariations();

		if (nodeId != null && clusterNodeVariations != null) {
			for (final ClusterNodeVariation clusterNodeVariation : clusterNodeVariations) {
				if (Objects.equals(nodeId, clusterNodeVariation.getNodeId())) {
					return clusterNodeVariation.getValue();
				}
			}
		}

		return property.getValue();
	}

}
