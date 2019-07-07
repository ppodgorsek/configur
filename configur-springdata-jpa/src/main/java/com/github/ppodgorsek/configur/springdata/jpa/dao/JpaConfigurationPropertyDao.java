package com.github.ppodgorsek.configur.springdata.jpa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.github.ppodgorsek.configur.springdata.jpa.model.JpaConfigurationCategory;
import com.github.ppodgorsek.configur.springdata.jpa.model.JpaConfigurationProperty;

/**
 * DAO for {@link JpaConfigurationProperty}s.
 *
 * @author Paul Podgorsek
 */
public interface JpaConfigurationPropertyDao
		extends PagingAndSortingRepository<JpaConfigurationProperty, String> {

	/**
	 * Deletes the entity with the given key.
	 *
	 * @param key
	 *            The property key, must not be {@code null}.
	 * @throws IllegalArgumentException
	 *             In case the given key is {@code null}
	 */
	@Nullable
	void deleteByKey(@NonNull String key);

	/**
	 * Fetches the child categories of a given category.
	 * 
	 * @param category
	 *            The parent category.
	 * @param pageable
	 *            The result limits.
	 * @return The list of child categories of the provided category, or an empty result set if
	 *         there are none.
	 */
	Page<JpaConfigurationProperty> findByCategoryOrderByKey(
			@NonNull JpaConfigurationCategory category, @NonNull Pageable pageable);

	/**
	 * Fetches a category by its key.
	 * 
	 * @param key
	 *            The category key.
	 * @return The category which has the provided key, or {@code null} if there isn't one.
	 */
	@Nullable
	JpaConfigurationProperty findByKey(@NonNull String key);

}
