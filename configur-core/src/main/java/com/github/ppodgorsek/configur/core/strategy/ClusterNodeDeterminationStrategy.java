package com.github.ppodgorsek.configur.core.strategy;

/**
 * Strategy allowing to determine the current node ID inside a cluster.
 *
 * @author Paul Podgorsek
 */
public interface ClusterNodeDeterminationStrategy {

	/**
	 * Fetches the ID of the current cluster node.
	 * 
	 * @return The current cluster node ID, or {@code null} if it is undefined or if the application
	 *         does not run in a clustered environment.
	 */
	String getNodeId();

}
