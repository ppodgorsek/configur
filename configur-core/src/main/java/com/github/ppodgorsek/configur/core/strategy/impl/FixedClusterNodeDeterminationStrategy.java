package com.github.ppodgorsek.configur.core.strategy.impl;

import com.github.ppodgorsek.configur.core.strategy.ClusterNodeDeterminationStrategy;

/**
 * Default implementation of the {@link ClusterNodeDeterminationStrategy} which uses a fixed node
 * ID, {@code null} by default.
 *
 * @author Paul Podgorsek
 */
public class FixedClusterNodeDeterminationStrategy implements ClusterNodeDeterminationStrategy {

	private final String nodeId;

	/**
	 * Default constructor.
	 */
	public FixedClusterNodeDeterminationStrategy() {
		this(null);
	}

	/**
	 * Constructor allowing to set the node ID.
	 *
	 * @param nodeId
	 *            The fixed node ID.
	 */
	public FixedClusterNodeDeterminationStrategy(final String nodeId) {

		super();

		this.nodeId = nodeId;
	}

	@Override
	public String getNodeId() {
		return nodeId;
	}

}
