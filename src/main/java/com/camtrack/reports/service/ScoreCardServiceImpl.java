//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.camtrack.reports.dao.ScoreCardDoInterface;

@Service("scoreCardService")
@CacheConfig(cacheNames = "scorecaches")
public class ScoreCardServiceImpl implements ScoreCardServiceInterface {
	@Autowired
	ScoreCardDoInterface dao;

	@Override
	@Cacheable("getscoremap")
	public List getScoreParameterMap() {
		return this.dao.getScoreParameterMap();
	}

	@Override
	public List<Map<String, Object>> showScoreCardDetailedReport(final int customerid, final int affiliateid,
			final int transporterid, final int vehicleid, final int param, final String startDate,
			final String endDate) {
		return this.dao.showScoreCardDetailedReport(customerid, affiliateid, transporterid, vehicleid, param, startDate,
				endDate);
	}

	@Override
	public List<Map<String, Object>> showScoreCardReport(final int customerid, final int affiliateid,
			final int transporterid, final int vehicleid, final int param, final String startDate,
			final String endDate) {
		return this.dao.showScoreCardReport(customerid, affiliateid, transporterid, vehicleid, param, startDate,
				endDate);
	}
}
