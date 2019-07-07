package com.github.ppodgorsek.configur.springdata.jpa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.github.ppodgorsek.configur.springdata.jpa.model.JpaConfigurationCategory;

/**
 * DAO for {@link JpaConfigurationCategory}s.
 *
 * @author Paul Podgorsek
 */
public interface JpaConfigurationCategoryDao
		extends PagingAndSortingRepository<JpaConfigurationCategory, String> {

	/**
	 * Fetches a configuration category by its key.
	 *
	 * @param key
	 *            The category key.
	 * @return The category having the provided key, or {@code null} if there isn't one.
	 */
	@Nullable
	JpaConfigurationCategory findByKey(@NonNull String key);

	/**
	 * Fetches all categories which have a given parent, ordered by key.
	 *
	 * @param category
	 *            The parent category.
	 * @param pageable
	 *            The result limits.
	 * @return The categories having the provided parent, between the provided limits, or an empty
	 *         result set if there are none.
	 */
	Page<JpaConfigurationCategory> findByParentOrderByKey(
			@NonNull JpaConfigurationCategory category, @NonNull Pageable pageable);

}
