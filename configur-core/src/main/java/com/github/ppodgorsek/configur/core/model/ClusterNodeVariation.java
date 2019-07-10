package com.github.ppodgorsek.configur.core.model;

import java.io.Serializable;

/**
 * @author Paul Podgorsek
 */
public interface ClusterNodeVariation extends Serializable {

	String getNodeId();

	void setNodeId(String nodeId);

	String getValue();

	void setValue(String value);

}
