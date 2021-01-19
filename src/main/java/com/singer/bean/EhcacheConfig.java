package com.singer.bean;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
public class EhcacheConfig {

	@Bean
	public EhCacheManagerFactoryBean ehCahe() {
		EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
		factoryBean.setConfigLocation(new ClassPathResource("conf/ehcache.xml"));
		factoryBean.setShared(true);
		return factoryBean;
	}

	@Bean
	public CacheManager ehCacheManager(EhCacheManagerFactoryBean ehCache) {
		return new EhCacheCacheManager(ehCache.getObject());
	}
}
