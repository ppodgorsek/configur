package com.github.ppodgorsek.configur.core.service.impl;

import java.util.Objects;
import java.util.Set;

import org.springframework.util.Assert;

import com.github.ppodgorsek.configur.core.model.ClusterNodeVariation;
import com.github.ppodgorsek.configur.core.model.ConfigurationProperty;
import com.github.ppodgorsek.configur.core.service.ConfigurationService;
import com.github.ppodgorsek.configur.core.strategy.ClusterNodeDeterminationStrategy;
import com.github.ppodgorsek.configur.core.strategy.impl.FixedClusterNodeDeterminationStrategy;

/**
 * Abstract parent configuration service allowing to fetch property values according to keys. For
 * clustered environments, those values will be node-specific, if it applies.
 *
 * @author Paul Podgorsek
 */
public abstract class AbstractConfigurationService implements ConfigurationService {

	private final ClusterNodeDeterminationStrategy clusterNodeDeterminationStrategy;

	/**
	 * Constructor which uses a {@link FixedClusterNodeDeterminationStrategy} by default.
	 */
	public AbstractConfigurationService() {
		this(new FixedClusterNodeDeterminationStrategy());
	}

	/**
	 * Constructor allowing to define the cluster node determination strategy.
	 *
	 * @param clusterNodeDeterminationStrategy
	 *            The cluster node determination strategy.
	 */
	public AbstractConfigurationService(
			final ClusterNodeDeterminationStrategy clusterNodeDeterminationStrategy) {

		super();

		Assert.notNull(clusterNodeDeterminationStrategy,
				"The cluster node determination strategy is required");

		this.clusterNodeDeterminationStrategy = clusterNodeDeterminationStrategy;
	}

	/**
	 * Fetches the value of a property for the current cluster node.
	 *
	 * @param property
	 *            The property for which the value must be fetched.
	 * @return The property value corresponding to the current cluster node, or the global value if
	 *         there is no cluster-specific value. Can be {@code null}.
	 */
	protected String getClusterNodeProperty(final ConfigurationProperty property) {

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

	@Override
	public String getPropertyValue(final String key) {

		final ConfigurationProperty property = getProperty(key);
		final String clusterNodeProperty = getClusterNodeProperty(property);

		return clusterNodeProperty;
	}

}
