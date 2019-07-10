package com.github.ppodgorsek.configur.springdata.jpa.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.github.ppodgorsek.configur.core.model.ClusterNodeVariation;
import com.github.ppodgorsek.configur.springdata.jpa.JpaConfigurationMetadata;

/**
 * {@link ClusterNodeVariation} model for Spring Data JPA.
 *
 * @author Paul Podgorsek
 */
@Entity
@Table(name = JpaConfigurationMetadata.Table.CLUSTER_NODE_VARIATION)
public class JpaClusterNodeVariation implements ClusterNodeVariation {

	private static final long serialVersionUID = 2544052597196704969L;

	@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
			@Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy") })
	@Column(name = JpaConfigurationMetadata.Column.ID, length = JpaConfigurationMetadata.Constraint.ID_LENGTH, nullable = false, updatable = false)
	private String id;

	@Column(name = JpaConfigurationMetadata.Column.NODE_ID, length = JpaConfigurationMetadata.Constraint.NODE_ID_LENGTH, nullable = false, unique = false)
	private String nodeId;

	@Column(name = JpaConfigurationMetadata.Column.VALUE, length = JpaConfigurationMetadata.Constraint.VALUE_LENGTH, nullable = true)
	private String value;

	@ManyToOne
	@JoinColumn(name = JpaConfigurationMetadata.Column.PROPERTY_ID, nullable = false, unique = false)
	private JpaConfigurationProperty property;

	/**
	 * Default constructor.
	 */
	public JpaClusterNodeVariation() {
		super();
	}

	/**
	 * Constructor allowing to create a new instance using values from an existing variation. Only
	 * the ID and property will not be copied.
	 *
	 * @param variation
	 *            The variation from which the attributes must be copied.
	 */
	public JpaClusterNodeVariation(final ClusterNodeVariation variation) {

		this();

		if (variation != null) {
			setNodeId(variation.getNodeId());
			setValue(variation.getValue());
		}
	}

	@Override
	public boolean equals(final Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj instanceof JpaClusterNodeVariation) {

			final JpaClusterNodeVariation variation = (JpaClusterNodeVariation) obj;

			return Objects.equals(id, variation.getId())
					&& Objects.equals(nodeId, variation.getNodeId())
					&& Objects.equals(property, variation.getProperty());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nodeId, property);
	}

	@Override
	public String toString() {

		final StringBuilder sbld = new StringBuilder(getClass().getSimpleName());
		sbld.append("[id=").append(id);
		sbld.append(",property=").append(property);
		sbld.append(",nodeId=").append(nodeId);
		sbld.append(",value=").append(value);
		sbld.append("]");

		return sbld.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	@Override
	public String getNodeId() {
		return nodeId;
	}

	@Override
	public void setNodeId(final String nodeId) {
		this.nodeId = nodeId;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(final String value) {
		this.value = value;
	}

	public JpaConfigurationProperty getProperty() {
		return property;
	}

	public void setProperty(final JpaConfigurationProperty property) {
		this.property = property;
	}

}
