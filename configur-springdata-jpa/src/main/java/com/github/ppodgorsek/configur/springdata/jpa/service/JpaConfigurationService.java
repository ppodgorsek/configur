package com.github.ppodgorsek.configur.springdata.jpa.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.github.ppodgorsek.configur.core.model.ConfigurationCategory;
import com.github.ppodgorsek.configur.core.model.ConfigurationProperty;
import com.github.ppodgorsek.configur.core.service.ConfigurationService;
import com.github.ppodgorsek.configur.springdata.jpa.JpaConfigurationMetadata;
import com.github.ppodgorsek.configur.springdata.jpa.dao.JpaConfigurationCategoryDao;
import com.github.ppodgorsek.configur.springdata.jpa.dao.JpaConfigurationPropertyDao;
import com.github.ppodgorsek.configur.springdata.jpa.model.JpaConfigurationCategory;
import com.github.ppodgorsek.configur.springdata.jpa.model.JpaConfigurationProperty;

/**
 * Spring Data JPA implementation of the {@link ConfigurationService} interface.
 *
 * @author Paul Podgorsek
 */
public class JpaConfigurationService implements ConfigurationService {

	private final JpaConfigurationCategoryDao jpaConfigurationCategoryDao;
	private final JpaConfigurationPropertyDao jpaConfigurationPropertyDao;

	/**
	 * Default constructor.
	 *
	 * @param jpaConfigurationCategoryDao
	 *            The configuration category DAO.
	 * @param jpaConfigurationPropertyDao
	 *            The configuration property DAO.
	 */
	public JpaConfigurationService(final JpaConfigurationCategoryDao jpaConfigurationCategoryDao,
			final JpaConfigurationPropertyDao jpaConfigurationPropertyDao) {

		super();

		Assert.notNull(jpaConfigurationCategoryDao, "The configuration category DAO is required");
		Assert.notNull(jpaConfigurationPropertyDao, "The configuration property DAO is required");

		this.jpaConfigurationCategoryDao = jpaConfigurationCategoryDao;
		this.jpaConfigurationPropertyDao = jpaConfigurationPropertyDao;
	}

	@Override
	@Transactional
	public void deleteCategory(final String key) {

		final JpaConfigurationCategory category = jpaConfigurationCategoryDao.findByKey(key);

		delete(category, new HashSet<>());
	}

	@Override
	@Transactional
	public void deleteProperty(final String key) {
		jpaConfigurationPropertyDao.deleteByKey(key);
	}

	@Override
	@Transactional(readOnly = true)
	public ConfigurationProperty getByProperty(final String key) {
		return jpaConfigurationPropertyDao.findByKey(key);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ConfigurationProperty> getByCategory(final String key) {

		final JpaConfigurationCategory category = jpaConfigurationCategoryDao.findByKey(key);

		if (category == null) {
			return new ArrayList<>();
		}
		else {
			return new ArrayList<>(jpaConfigurationPropertyDao
					.findByCategoryOrderByKey(category, Pageable.unpaged()).getContent());
		}
	}

	@Override
	@Transactional
	public ConfigurationCategory save(final ConfigurationCategory category) {
		return save(category, new HashSet<>());
	}

	@Override
	@Transactional
	public ConfigurationProperty save(final ConfigurationProperty property) {

		if (property == null) {
			return null;
		}

		JpaConfigurationProperty jpaProperty;

		if (property instanceof JpaConfigurationProperty) {
			jpaProperty = (JpaConfigurationProperty) property;
		}
		else {
			jpaProperty = new JpaConfigurationProperty(property);
		}

		ConfigurationCategory category = jpaProperty.getCategory();
		category = save(category);
		jpaProperty.setCategory(category);

		return jpaConfigurationPropertyDao.save(jpaProperty);
	}

	/**
	 * Deletes a category by taking into account its parent categories to avoid loops.
	 *
	 * @param category
	 *            The category which must be deleted.
	 * @param parentCategories
	 *            The parent categories which have been traversed.
	 */
	private void delete(final JpaConfigurationCategory category,
			final Set<String> parentCategories) {

		if (category == null) {
			return;
		}

		parentCategories.add(category.getKey());

		final Pageable pageable = PageRequest.of(0, JpaConfigurationMetadata.MAX_RESULTS);
		Page<JpaConfigurationCategory> childCategories = jpaConfigurationCategoryDao
				.findByParentOrderByKey(category, pageable);

		while (!childCategories.isEmpty()) {
			for (final JpaConfigurationCategory childCategory : childCategories) {
				if (!parentCategories.contains(childCategory.getKey())) {
					delete(childCategory, parentCategories);
				}
			}

			childCategories = jpaConfigurationCategoryDao.findByParentOrderByKey(category,
					pageable);
		}

		Page<JpaConfigurationProperty> childProperties = jpaConfigurationPropertyDao
				.findByCategoryOrderByKey(category, pageable);

		while (!childProperties.isEmpty()) {
			for (final JpaConfigurationProperty childProperty : childProperties) {
				deleteProperty(childProperty.getKey());
			}

			childProperties = jpaConfigurationPropertyDao.findByCategoryOrderByKey(category,
					pageable);
		}

		jpaConfigurationCategoryDao.delete(category);
	}

	/**
	 * Saves a category by taking into account its parent categories to avoid loops.
	 *
	 * @param category
	 *            The category which must be saved.
	 * @param parentCategories
	 *            The parent categories which have been traversed.
	 */
	private ConfigurationCategory save(final ConfigurationCategory category,
			final Set<String> parentCategories) {

		if (category == null) {
			return null;
		}

		if (parentCategories.contains(category.getKey())) {
			return category;
		}
		else {
			parentCategories.add(category.getKey());
		}

		ConfigurationCategory parentCategory = category.getParent();
		parentCategory = save(parentCategory, parentCategories);

		JpaConfigurationCategory jpaCategory;

		if (category instanceof JpaConfigurationCategory) {
			jpaCategory = (JpaConfigurationCategory) category;
		}
		else {

			jpaCategory = jpaConfigurationCategoryDao.findByKey(category.getKey());

			if (jpaCategory == null) {
				jpaCategory = new JpaConfigurationCategory(category);
			}
			else {
				jpaCategory.setDescription(category.getDescription());
				jpaCategory.setName(category.getName());
				jpaCategory.setParent(parentCategory);
			}
		}

		return jpaConfigurationCategoryDao.save(jpaCategory);
	}

}
