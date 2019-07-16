package com.github.ppodgorsek.configur.cache.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

/**
 * Test configuration of the Cache module.
 *
 * @author Paul Podgorsek
 */
@Configuration
@Import({ CacheConfiguration.class, CacheServiceTestConfiguration.class })
public class CacheTestConfiguration {

	@Bean
	public CacheManager cacheManager(final net.sf.ehcache.CacheManager ehcacheManager) {
		return new EhCacheCacheManager(ehcacheManager);
	}

	@Bean(destroyMethod = "destroy")
	public EhCacheManagerFactoryBean ehcacheManager() {

		final EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
		factoryBean.setCacheManagerName("testCacheManager");
		factoryBean.setConfigLocation(new ClassPathResource("context/ehcache.xml"));
		factoryBean.setShared(true);

		return factoryBean;
	}

}
