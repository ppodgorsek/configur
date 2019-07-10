package com.github.ppodgorsek.configur.springdata.jpa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.github.ppodgorsek.configur.core.model.ClusterNodeVariation;
import com.github.ppodgorsek.configur.core.model.ConfigurationCategory;
import com.github.ppodgorsek.configur.core.model.ConfigurationProperty;
import com.github.ppodgorsek.configur.springdata.jpa.JpaConfigurationMetadata;
import com.github.ppodgorsek.configur.springdata.jpa.dao.AbstractDbUnitTest;
import com.github.ppodgorsek.configur.springdata.jpa.dao.JpaConfigurationCategoryDao;
import com.github.ppodgorsek.configur.springdata.jpa.dao.JpaConfigurationPropertyDao;
import com.github.ppodgorsek.configur.springdata.jpa.model.JpaConfigurationCategory;
import com.github.ppodgorsek.configur.springdata.jpa.model.JpaConfigurationProperty;

/**
 * Tests for the {@link JpaConfigurationService}.
 *
 * @author Paul Podgorsek
 */
public class JpaConfigurationServiceTest extends AbstractDbUnitTest {

	private static final String KEY = "testKey";

	private JpaConfigurationService service;

	@Mock
	private JpaConfigurationCategoryDao jpaConfigurationCategoryDao;

	@Mock
	private JpaConfigurationPropertyDao jpaConfigurationPropertyDao;

	@BeforeEach
	public void setUp() {

		EasyMockSupport.injectMocks(this);

		service = new JpaConfigurationService(jpaConfigurationCategoryDao,
				jpaConfigurationPropertyDao);
	}

	@AfterEach
	public void tearDown() {

		EasyMock.verify(jpaConfigurationCategoryDao);
		EasyMock.verify(jpaConfigurationPropertyDao);
	}

	@Test
	public void deleteCategoryWithCorrectCategoryHavingNoChildCategoriesAndNoProperties() {

		final Pageable pageable = PageRequest.of(0, JpaConfigurationMetadata.MAX_RESULTS);

		EasyMock.expect(jpaConfigurationCategoryDao.findByKey(KEY)).andReturn(category1);
		EasyMock.expect(jpaConfigurationCategoryDao.findByParentOrderByKey(category1, pageable))
				.andReturn(new PageImpl<>(new ArrayList<>()));

		jpaConfigurationCategoryDao.delete(category1);
		EasyMock.expectLastCall();

		EasyMock.expect(jpaConfigurationPropertyDao.findByCategoryOrderByKey(category1, pageable))
				.andReturn(new PageImpl<>(new ArrayList<>()));

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		service.deleteCategory(KEY);
	}

	@Test
	public void deleteCategoryWithCorrectCategoryHavingChildCategoriesAndNoProperties() {

		final List<JpaConfigurationCategory> childCategories = new ArrayList<>();
		childCategories.add(category2);

		final Pageable pageable = PageRequest.of(0, JpaConfigurationMetadata.MAX_RESULTS);

		EasyMock.expect(jpaConfigurationCategoryDao.findByKey(KEY)).andReturn(category1);
		EasyMock.expect(jpaConfigurationCategoryDao.findByParentOrderByKey(category1, pageable))
				.andReturn(new PageImpl<>(childCategories));
		EasyMock.expect(jpaConfigurationCategoryDao.findByParentOrderByKey(category1, pageable))
				.andReturn(new PageImpl<>(new ArrayList<>()));

		EasyMock.expect(jpaConfigurationCategoryDao.findByParentOrderByKey(category2, pageable))
				.andReturn(new PageImpl<>(new ArrayList<>()));

		jpaConfigurationCategoryDao.delete(category2);
		EasyMock.expectLastCall();
		jpaConfigurationCategoryDao.delete(category1);
		EasyMock.expectLastCall();

		EasyMock.expect(jpaConfigurationPropertyDao.findByCategoryOrderByKey(category2, pageable))
				.andReturn(new PageImpl<>(new ArrayList<>()));
		EasyMock.expect(jpaConfigurationPropertyDao.findByCategoryOrderByKey(category1, pageable))
				.andReturn(new PageImpl<>(new ArrayList<>()));

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		service.deleteCategory(KEY);
	}

	@Test
	public void deleteCategoryWithCorrectCategoryHavingNoChildCategoriesAndProperties() {

		final List<JpaConfigurationProperty> properties = new ArrayList<>();
		properties.add(property01);
		properties.add(property02);
		properties.add(property05);

		final Pageable pageable = PageRequest.of(0, JpaConfigurationMetadata.MAX_RESULTS);

		EasyMock.expect(jpaConfigurationCategoryDao.findByKey(KEY)).andReturn(category2);
		EasyMock.expect(jpaConfigurationCategoryDao.findByParentOrderByKey(category2, pageable))
				.andReturn(new PageImpl<>(new ArrayList<>()));

		jpaConfigurationCategoryDao.delete(category2);
		EasyMock.expectLastCall();

		EasyMock.expect(jpaConfigurationPropertyDao.findByCategoryOrderByKey(category2, pageable))
				.andReturn(new PageImpl<>(properties));
		EasyMock.expect(jpaConfigurationPropertyDao.findByCategoryOrderByKey(category2, pageable))
				.andReturn(new PageImpl<>(new ArrayList<>()));

		jpaConfigurationPropertyDao.deleteByKey(property01.getKey());
		EasyMock.expectLastCall();
		jpaConfigurationPropertyDao.deleteByKey(property02.getKey());
		EasyMock.expectLastCall();
		jpaConfigurationPropertyDao.deleteByKey(property05.getKey());
		EasyMock.expectLastCall();

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		service.deleteCategory(KEY);
	}

	@Test
	public void deleteCategoryWithNullCategory() {

		EasyMock.expect(jpaConfigurationCategoryDao.findByKey(null))
				.andThrow(new IllegalArgumentException());

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.deleteCategory(null);
		});
	}

	@Test
	public void deleteCategoryWithUnknownCategory() {

		EasyMock.expect(jpaConfigurationCategoryDao.findByKey(KEY)).andReturn(null);

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		service.deleteCategory(KEY);
	}

	@Test
	public void deletePropertyWithCorrectKey() {

		jpaConfigurationPropertyDao.deleteByKey(KEY);
		EasyMock.expectLastCall();

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		service.deleteProperty(KEY);
	}

	@Test
	public void deletePropertyWithNullKey() {

		jpaConfigurationPropertyDao.deleteByKey(null);
		EasyMock.expectLastCall().andThrow(new IllegalArgumentException());

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.deleteProperty(null);
		});
	}

	@Test
	public void getByCategoryWithCorrectCategoryHavingProperties() {

		final List<JpaConfigurationProperty> properties = new ArrayList<>();
		properties.add(property01);
		properties.add(property02);
		properties.add(property05);

		final Pageable pageable = Pageable.unpaged();

		EasyMock.expect(jpaConfigurationCategoryDao.findByKey(category1.getKey()))
				.andReturn(category1);

		EasyMock.expect(jpaConfigurationPropertyDao.findByCategoryOrderByKey(category1, pageable))
				.andReturn(new PageImpl<>(properties));

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		final List<ConfigurationProperty> results = service.getByCategory(category1.getKey());

		assertNotNull(results, "The results must not be null");
		assertFalse(results.isEmpty(), "The results should not be empty");
		assertEquals(properties, results, "Wrong results");
	}

	@Test
	public void getByCategoryWithCorrectCategoryHavingNoProperties() {

		final Pageable pageable = Pageable.unpaged();

		EasyMock.expect(jpaConfigurationCategoryDao.findByKey(category1.getKey()))
				.andReturn(category1);

		EasyMock.expect(jpaConfigurationPropertyDao.findByCategoryOrderByKey(category1, pageable))
				.andReturn(new PageImpl<>(new ArrayList<>()));

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		final List<ConfigurationProperty> results = service.getByCategory(category1.getKey());

		assertNotNull(results, "The results must not be null");
		assertTrue(results.isEmpty(), "The results should not be empty");
	}

	@Test
	public void getByCategoryWithNullCategory() {

		EasyMock.expect(jpaConfigurationCategoryDao.findByKey(null))
				.andThrow(new IllegalArgumentException());

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.getByCategory(null);
		});
	}

	@Test
	public void getByCategoryWithUnknownCategory() {

		EasyMock.expect(jpaConfigurationCategoryDao.findByKey(nonPersistedCategory.getKey()))
				.andReturn(null);

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		final List<ConfigurationProperty> results = service
				.getByCategory(nonPersistedCategory.getKey());

		assertNotNull(results, "The results must not be null");
		assertTrue(results.isEmpty(), "The results should not be empty");
	}

	@Test
	public void getByProperty() {

		EasyMock.expect(jpaConfigurationPropertyDao.findByKey(property01.getKey()))
				.andReturn(property01);

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		final ConfigurationProperty result = service.getByProperty(property01.getKey());

		assertNotNull(result, "The result must not be null");
		assertEquals(property01, result, "Wrong result");
	}

	@Test
	public void saveCategoryWithNewCategoryHavingNoParents() {

		final ConfigurationCategory category = new ConfigurationCategory() {

			private static final long serialVersionUID = 1L;

			@Override
			public void setParent(final ConfigurationCategory parent) {
				// Nothing specific here.
			}

			@Override
			public void setName(final String name) {
				// Nothing specific here.
			}

			@Override
			public void setKey(final String key) {
				// Nothing specific here.
			}

			@Override
			public void setDescription(final String description) {
				// Nothing specific here.
			}

			@Override
			public ConfigurationCategory getParent() {
				return null;
			}

			@Override
			public String getName() {
				return null;
			}

			@Override
			public String getKey() {
				return KEY;
			}

			@Override
			public String getDescription() {
				return null;
			}
		};

		EasyMock.expect(jpaConfigurationCategoryDao.findByKey(category.getKey())).andReturn(null);
		EasyMock.expect(jpaConfigurationCategoryDao
				.save(EasyMock.anyObject(JpaConfigurationCategory.class)))
				.andReturn(nonPersistedCategory);

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		final ConfigurationCategory result = service.save(category);

		assertNotNull(result, "The result should not be null");
		assertEquals(nonPersistedCategory, result, "Wrong result");
	}

	@Test
	public void saveCategoryWithNewJpaCategoryHavingNoParents() {

		EasyMock.expect(jpaConfigurationCategoryDao.save(nonPersistedCategory))
				.andReturn(nonPersistedCategory);

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		final ConfigurationCategory result = service.save(nonPersistedCategory);

		assertNotNull(result, "The result should not be null");
		assertEquals(nonPersistedCategory, result, "Wrong result");
	}

	@Test
	public void saveCategoryWithNewJpaCategoryHavingParents() {

		final JpaConfigurationCategory parentCategory = new JpaConfigurationCategory();
		parentCategory.setDescription(null);
		parentCategory.setId(null);
		parentCategory.setKey(KEY);
		parentCategory.setName(null);
		parentCategory.setParent(null);

		nonPersistedCategory.setParent(parentCategory);

		EasyMock.expect(jpaConfigurationCategoryDao.save(parentCategory)).andReturn(parentCategory);

		EasyMock.expect(jpaConfigurationCategoryDao.save(nonPersistedCategory))
				.andReturn(nonPersistedCategory);

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		final ConfigurationCategory result = service.save(nonPersistedCategory);

		assertNotNull(result, "The result should not be null");
		assertEquals(nonPersistedCategory, result, "Wrong result");
	}

	@Test
	public void saveCategoryWithNewCategoryHavingParentLoop() {

		final JpaConfigurationCategory parentCategory = new JpaConfigurationCategory();
		parentCategory.setDescription(null);
		parentCategory.setId(null);
		parentCategory.setKey(KEY);
		parentCategory.setName(null);
		parentCategory.setParent(nonPersistedCategory);

		nonPersistedCategory.setParent(parentCategory);

		EasyMock.expect(jpaConfigurationCategoryDao.save(parentCategory)).andReturn(parentCategory);

		EasyMock.expect(jpaConfigurationCategoryDao.save(nonPersistedCategory))
				.andReturn(nonPersistedCategory);

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		final ConfigurationCategory result = service.save(nonPersistedCategory);

		assertNotNull(result, "The result should not be null");
		assertEquals(nonPersistedCategory, result, "Wrong result");
	}

	@Test
	public void saveCategoryWithNullCategory() {

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		final ConfigurationCategory result = service.save((ConfigurationCategory) null);

		assertNull(result, "No category should have been saved");
	}

	@Test
	public void saveCategoryWithExistingCategory() {

		final ConfigurationCategory category = new ConfigurationCategory() {

			private static final long serialVersionUID = 1L;

			@Override
			public void setParent(final ConfigurationCategory parent) {
				// Nothing specific here.
			}

			@Override
			public void setName(final String name) {
				// Nothing specific here.
			}

			@Override
			public void setKey(final String key) {
				// Nothing specific here.
			}

			@Override
			public void setDescription(final String description) {
				// Nothing specific here.
			}

			@Override
			public ConfigurationCategory getParent() {
				return null;
			}

			@Override
			public String getName() {
				return null;
			}

			@Override
			public String getKey() {
				return KEY;
			}

			@Override
			public String getDescription() {
				return null;
			}
		};

		EasyMock.expect(jpaConfigurationCategoryDao.findByKey(category.getKey()))
				.andReturn(category1);
		EasyMock.expect(jpaConfigurationCategoryDao.save(category1)).andReturn(category1);

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		final ConfigurationCategory result = service.save(category);

		assertNotNull(result, "The result should not be null");
		assertEquals(category1, result, "Wrong result");
	}

	@Test
	public void savePropertyWithNewPropertyAndNullCategory() {

		final ConfigurationProperty property = new ConfigurationProperty() {

			private static final long serialVersionUID = 1L;

			@Override
			public void setValue(final String value) {
				// Nothing specific here.
			}

			@Override
			public void setName(final String name) {
				// Nothing specific here.
			}

			@Override
			public void setKey(final String key) {
				// Nothing specific here.
			}

			@Override
			public void setDescription(final String description) {
				// Nothing specific here.
			}

			@Override
			public void setCategory(final ConfigurationCategory category) {
				// Nothing specific here.
			}

			@Override
			public String getValue() {
				return null;
			}

			@Override
			public String getName() {
				return null;
			}

			@Override
			public String getKey() {
				return KEY;
			}

			@Override
			public String getDescription() {
				return null;
			}

			@Override
			public ConfigurationCategory getCategory() {
				return null;
			}

			@Override
			public Set<ClusterNodeVariation> getClusterNodeVariations() {
				return null;
			}

			@Override
			public void setClusterNodeVariations(
					final Set<ClusterNodeVariation> clusterNodeVariations) {
				// Nothing specific here.
			}
		};

		EasyMock.expect(jpaConfigurationPropertyDao
				.save(EasyMock.anyObject(JpaConfigurationProperty.class)))
				.andReturn(new JpaConfigurationProperty(property));

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		final ConfigurationProperty result = service.save(property);

		assertNotNull(result, "The result should not be null");
		assertNotSame(property, result, "Wrong result");
	}

	@Test
	public void savePropertyWithNewJpaPropertyAndNullCategory() {

		EasyMock.expect(jpaConfigurationPropertyDao.save(nonPersistedProperty))
				.andReturn(nonPersistedProperty);

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		final ConfigurationProperty result = service.save(nonPersistedProperty);

		assertNotNull(result, "The result should not be null");
		assertEquals(nonPersistedProperty, result, "Wrong result");
	}

	@Test
	public void savePropertyWithNullProperty() {

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		final ConfigurationProperty result = service.save((ConfigurationProperty) null);

		assertNull(result, "The result should be null");
	}

	@Test
	public void savePropertyWithExistingPropertyAndNullCategory() {

		EasyMock.expect(jpaConfigurationPropertyDao.save(property15)).andReturn(property15);

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		final ConfigurationProperty result = service.save(property15);

		assertNotNull(result, "The result should not be null");
		assertEquals(property15, result, "Wrong result");
	}

	@Test
	public void savePropertyWithExistingPropertyAndNewCategory() {

		EasyMock.expect(jpaConfigurationCategoryDao
				.save((JpaConfigurationCategory) property11.getCategory()))
				.andReturn((JpaConfigurationCategory) property11.getCategory());

		EasyMock.expect(jpaConfigurationPropertyDao.save(property11)).andReturn(property11);

		EasyMock.replay(jpaConfigurationCategoryDao);
		EasyMock.replay(jpaConfigurationPropertyDao);

		final ConfigurationProperty result = service.save(property11);

		assertNotNull(result, "The result should not be null");
		assertEquals(property11, result, "Wrong result");
	}

}
