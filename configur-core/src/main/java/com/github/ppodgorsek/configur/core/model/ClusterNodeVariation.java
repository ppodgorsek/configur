package com.github.ppodgorsek.configur.core.model;

import java.io.Serializable;

/**
 * Model allowing the categorisation of property variations for cluster nodes.
 *
 * @author Paul Podgorsek
 */
public interface ClusterNodeVariation extends Serializable {

	/**
	 * Fetches the cluster node ID.
	 *
	 * @return The node ID.
	 */
	String getNodeId();

	/**
	 * Sets the cluster node ID.
	 *
	 * @param nodeId
	 *            The node ID.
	 */
	void setNodeId(String nodeId);

	/**
	 * Fetches the property value defined for the cluster node.
	 *
	 * @return The cluster node's value.
	 */
	String getValue();

	/**
	 * Set the property value for the cluster node.
	 *
	 * @param value
	 *            The cluster node's value.
	 */
	void setValue(String value);

}
