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
import com.github.ppodgorsek.configur.springdata.jpa.JpaConfigurationMetadata;

/**
 * {@link ConfigurationCategory} model for Spring Data JPA.
 *
 * @author Paul Podgorsek
 */
@Entity
@Table(name = JpaConfigurationMetadata.Table.CONFIGURATION_CATEGORY)
public class JpaConfigurationCategory implements ConfigurationCategory {

	private static final long serialVersionUID = 8928712393065528448L;

	@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
			@Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy") })
	@Column(name = JpaConfigurationMetadata.Column.ID, length = JpaConfigurationMetadata.Constraint.ID_LENGTH, updatable = false, nullable = false)
	private String id;

	@Column(name = JpaConfigurationMetadata.Column.KEY, length = JpaConfigurationMetadata.Constraint.KEY_LENGTH, nullable = false, unique = true)
	private String key;

	@Column(name = JpaConfigurationMetadata.Column.NAME, length = JpaConfigurationMetadata.Constraint.NAME_LENGTH, nullable = true)
	private String name;

	@Column(name = JpaConfigurationMetadata.Column.DESCRIPTION, length = JpaConfigurationMetadata.Constraint.DESCRIPTION_LENGTH, nullable = true)
	private String description;

	@ManyToOne(optional = true)
	@JoinColumn(name = JpaConfigurationMetadata.Column.PARENT, nullable = true, unique = false)
	private JpaConfigurationCategory parent;

	/**
	 * Default constructor.
	 */
	public JpaConfigurationCategory() {
		super();
	}

	/**
	 * Constructor allowing to create a new instance using values from an existing category.
	 *
	 * @param category
	 *            The category from which the values must be copied.
	 */
	public JpaConfigurationCategory(final ConfigurationCategory category) {

		this();

		if (category != null) {

			setKey(category.getKey());
			setName(category.getName());
			setDescription(category.getDescription());
			setParent(category.getParent());
		}
	}

	@Override
	public boolean equals(final Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj instanceof JpaConfigurationCategory) {

			final JpaConfigurationCategory category = (JpaConfigurationCategory) obj;

			return Objects.equals(id, category.getId()) && Objects.equals(key, category.getKey())
					&& Objects.equals(parent, category.getParent());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, key, parent);
	}

	@Override
	public String toString() {

		final StringBuilder sbld = new StringBuilder(getClass().getSimpleName());
		sbld.append("[id=").append(id);
		sbld.append(",key=").append(key);
		sbld.append(",parent=").append(parent);
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
	public ConfigurationCategory getParent() {
		return parent;
	}

	@Override
	public void setParent(final ConfigurationCategory parent) {

		if (parent == null || parent instanceof JpaConfigurationCategory) {
			this.parent = (JpaConfigurationCategory) parent;
		}
		else {
			this.parent = new JpaConfigurationCategory(parent);
		}
	}

}
