package com.camtrack.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {
	@Bean
	@Override
	public CacheManager cacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager("fetchalldrivers", "alllisttypeconfig",
				"getscoremap", "safetyranking", "fetchallmenus", "listtyperole", "status", "listformatrepor",
				"listfrequence", "listimerange", "lisreportname", "scpobcparams", "scprecovery", "visualparameter",
				"detailexception", "groupinvalidation", "invalidatedexception", "listexceptlevel", "listexcepttype",
				"resumedriverandvehicle", "resumeworktime", "resumeexception", "resume100km", "trendistance",
				"trendresume", "trendresume100km", "worktime", "fechclients", "fetchallcountries", "fetchalllangues",
				"activedriverandvehicle", "activeordriverpersearch", "resumeactivedrive", "toprankingcaches",
				"dashboardcaches", "exceptioncaches", "clientcache", "manualsubstraction", "scorecaches",
				"rolemenucache", "transportercache", "usercaches", "listreelexception");
		cacheManager.setCaffeine(caffeineCacheBuilder());
		return cacheManager;
	}

	Caffeine<Object, Object> caffeineCacheBuilder() {
		return Caffeine.newBuilder().initialCapacity(100).maximumSize(2000).expireAfterWrite(60, TimeUnit.SECONDS)
				.recordStats();
	}
}
