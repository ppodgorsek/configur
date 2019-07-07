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

import com.github.ppodgorsek.configur.core.model.ConfigurationCategory;
import com.github.ppodgorsek.configur.core.model.ConfigurationProperty;
import com.github.ppodgorsek.configur.springdata.jpa.JpaConfigurationMetadata;

/**
 * {@link ConfigurationProperty} model for Spring Data JPA.
 *
 * @author Paul Podgorsek
 */
@Entity
@Table(name = JpaConfigurationMetadata.Table.CONFIGURATION_PROPERTY)
public class JpaConfigurationProperty implements ConfigurationProperty {

	private static final long serialVersionUID = 1560438187841290956L;

	@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
			@Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy") })
	@Column(name = JpaConfigurationMetadata.Column.ID, length = JpaConfigurationMetadata.Constraint.ID_LENGTH, nullable = false, updatable = false)
	private String id;

	@Column(name = JpaConfigurationMetadata.Column.KEY, length = JpaConfigurationMetadata.Constraint.KEY_LENGTH, nullable = false, unique = true)
	private String key;

	@Column(name = JpaConfigurationMetadata.Column.VALUE, length = JpaConfigurationMetadata.Constraint.VALUE_LENGTH, nullable = true)
	private String value;

	@Column(name = JpaConfigurationMetadata.Column.NAME, length = JpaConfigurationMetadata.Constraint.NAME_LENGTH, nullable = true)
	private String name;

	@Column(name = JpaConfigurationMetadata.Column.DESCRIPTION, length = JpaConfigurationMetadata.Constraint.DESCRIPTION_LENGTH, nullable = true)
	private String description;

	@ManyToOne(optional = true)
	@JoinColumn(name = JpaConfigurationMetadata.Column.PARENT, nullable = true)
	private JpaConfigurationCategory category;

	/**
	 * Default constructor.
	 */
	public JpaConfigurationProperty() {
		super();
	}

	/**
	 * Constructor allowing to create a new instance using values from an existing property. Only
	 * the ID will not be copied.
	 *
	 * @param property
	 *            The property from which the attributes must be copied.
	 */
	public JpaConfigurationProperty(final ConfigurationProperty property) {

		this();

		if (property != null) {

			setKey(property.getKey());
			setValue(property.getValue());
			setName(property.getName());
			setDescription(property.getDescription());
			setCategory(property.getCategory());
		}
	}

	@Override
	public boolean equals(final Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj instanceof JpaConfigurationProperty) {

			final JpaConfigurationProperty property = (JpaConfigurationProperty) obj;

			return Objects.equals(id, property.getId()) && Objects.equals(key, property.getKey())
					&& Objects.equals(category, property.getCategory());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, key, category);
	}

	@Override
	public String toString() {

		final StringBuilder sbld = new StringBuilder(getClass().getSimpleName());
		sbld.append("[id=").append(id);
		sbld.append(",key=").append(key);
		sbld.append(",value=").append(value);
		sbld.append(",category=").append(category);
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
	public String getKey() {
		return key;
	}

	@Override
	public void setKey(final String key) {
		this.key = key;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(final String value) {
		this.value = value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public ConfigurationCategory getCategory() {
		return category;
	}

	@Override
	public void setCategory(final ConfigurationCategory configurationCategory) {

		if (configurationCategory == null
				|| configurationCategory instanceof JpaConfigurationCategory) {
			category = (JpaConfigurationCategory) configurationCategory;
		}
		else {
			category = new JpaConfigurationCategory(configurationCategory);
		}
	}

}
