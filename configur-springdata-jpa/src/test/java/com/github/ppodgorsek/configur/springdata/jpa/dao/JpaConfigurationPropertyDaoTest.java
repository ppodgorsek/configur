package com.github.ppodgorsek.configur.springdata.jpa.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.github.ppodgorsek.configur.core.model.ClusterNodeVariation;
import com.github.ppodgorsek.configur.springdata.jpa.model.JpaConfigurationProperty;

/**
 * Tests for the {@link JpaConfigurationPropertyDao}.
 *
 * @author Paul Podgorsek
 */
public class JpaConfigurationPropertyDaoTest extends AbstractDbUnitTest {

	@Resource
	private JpaConfigurationPropertyDao dao;

	@Test
	public void deleteByKeyWithCorrectKey() {

		final long countBefore = dao.count();
		dao.deleteByKey(property01.getKey());
		final long countAfter = dao.count();

		assertEquals(countBefore - 1, countAfter, "There should be one record less");
	}

	@Test
	public void deleteByKeyWithNullKey() {

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			dao.deleteByKey(null);
		});
	}

	@Test
	public void deleteByKeyWithUnknownKey() {

		final long countBefore = dao.count();
		dao.deleteByKey("_" + property01.getKey() + "_");
		final long countAfter = dao.count();

		assertEquals(countBefore, countAfter, "There should be the same number of records");
	}

	@Test
	public void findByCategoryOrderByKeyWithCorrectCategory() {

		final Page<JpaConfigurationProperty> results = dao.findByCategoryOrderByKey(category6,
				Pageable.unpaged());

		assertNotNull(results, "The results mustn't be null");
		assertTrue(results.hasContent(), "The results should not be empty");
		assertEquals(property11, results.getContent().get(0), "Wrong result");
		assertEquals(property12, results.getContent().get(1), "Wrong result");
	}

	@Test
	public void findByCategoryOrderByKeyWithCorrectCategoryAndLimitedResults() {

		final Page<JpaConfigurationProperty> results = dao.findByCategoryOrderByKey(category6,
				PageRequest.of(1, 1));

		assertNotNull(results, "The results mustn't be null");
		assertTrue(results.hasContent(), "The results should not be empty");
		assertEquals(property12, results.getContent().get(0), "Wrong result");
	}

	@Test
	public void findByCategoryOrderByKeyWithNullCategory() {

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			dao.findByCategoryOrderByKey(null, Pageable.unpaged());
		});
	}

	@Test
	public void findByCategoryOrderByKeyWithNonPersistedCategory() {

		Assertions.assertThrows(IllegalStateException.class, () -> {
			dao.findByCategoryOrderByKey(nonPersistedCategory, Pageable.unpaged());
		});
	}

	@Test
	public void findByCategoryOrderByKeyWithCorrectCategoryHavingNoChildren() {

		final Page<JpaConfigurationProperty> results = dao.findByCategoryOrderByKey(category1,
				Pageable.unpaged());

		assertNotNull(results, "The results mustn't be null");
		assertFalse(results.hasContent(), "The results should be empty");
	}

	@Test
	public void findByKeyWithCorrectKey() {

		final JpaConfigurationProperty property = dao.findByKey(property01.getKey());

		assertNotNull(property, "The property should not be null");
		assertEquals(property01, property, "Wrong property");
	}

	@Test
	public void findByKeyWithCorrectKeyHavingClusterVariations() {

		final JpaConfigurationProperty property = dao.findByKey(property16.getKey());

		assertNotNull(property, "The property should not be null");
		assertEquals(property16, property, "Wrong property");

		final Set<ClusterNodeVariation> correctVariations = property16.getClusterNodeVariations();
		final Set<ClusterNodeVariation> variations = property.getClusterNodeVariations();

		assertEquals(correctVariations.size(), variations.size(), "Wrong number of variations");
		assertTrue(correctVariations.containsAll(variations), "Wrong variations");
	}

	@Test
	public void findByKeyWithNullKey() {

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			dao.findByKey(null);
		});
	}

	@Test
	public void findByKeyWithUnknownKey() {

		final JpaConfigurationProperty property = dao.findByKey(nonPersistedProperty.getKey());

		assertNull(property, "There should be no property having that key");
	}

}
