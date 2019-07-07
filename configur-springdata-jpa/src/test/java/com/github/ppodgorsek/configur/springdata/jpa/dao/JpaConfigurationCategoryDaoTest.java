package com.github.ppodgorsek.configur.springdata.jpa.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.github.ppodgorsek.configur.springdata.jpa.model.JpaConfigurationCategory;

/**
 * Tests for the {@link JpaConfigurationCategoryDao}.
 *
 * @author Paul Podgorsek
 */
public class JpaConfigurationCategoryDaoTest extends AbstractDbUnitTest {

	@Resource
	private JpaConfigurationCategoryDao dao;

	@Test
	public void findByKeyWithCorrectKey() {

		final JpaConfigurationCategory category = dao.findByKey(category1.getKey());

		assertNotNull(category, "A category should be found");
		assertEquals(category1, category, "Wrong category");
	}

	@Test
	public void findByKeyWithNullKey() {

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			dao.findByKey(null);
		});
	}

	@Test
	public void findByKeyWithUnknownKey() {

		final JpaConfigurationCategory category = dao.findByKey(nonPersistedCategory.getKey());

		assertNull(category, "No category should be found");
	}

	@Test
	public void findByParentOrderByKeyWithCorrectCategoryAndCorrectPage() {

		final Page<JpaConfigurationCategory> children = dao.findByParentOrderByKey(category1,
				Pageable.unpaged());

		assertTrue(children.hasContent(), "There should be child categories");
		assertEquals(children.getContent().get(0), category3);
		assertEquals(children.getContent().get(1), category2);
	}

	@Test
	public void findByParentOrderByKeyWithNullCategoryAndCorrectPage() {

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			dao.findByParentOrderByKey(null, Pageable.unpaged());
		});
	}

	@Test
	public void findByParentOrderByKeyWithUnknownCategoryAndCorrectPage() {

		Assertions.assertThrows(IllegalStateException.class, () -> {
			dao.findByParentOrderByKey(nonPersistedCategory, Pageable.unpaged());
		});
	}

	@Test
	public void findByParentOrderByKeyWithKnownCategoryWithoutChildrenAndCorrectPage() {

		final Page<JpaConfigurationCategory> children = dao.findByParentOrderByKey(category7,
				Pageable.unpaged());

		assertFalse(children.hasContent(), "There should be no child categories");
	}

	@Test
	public void findByParentOrderByKeyWithCorrectCategoryAndNullPage() {

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			dao.findByParentOrderByKey(category1, null);
		});
	}

	@Test
	public void findByParentOrderByKeyWithCorrectCategoryAndLimitedPage() {

		final Page<JpaConfigurationCategory> children = dao.findByParentOrderByKey(category1,
				PageRequest.of(0, 1));

		assertTrue(children.hasContent(), "There should be child categories");
		assertEquals(children.getContent().get(0), category3);
	}

}
