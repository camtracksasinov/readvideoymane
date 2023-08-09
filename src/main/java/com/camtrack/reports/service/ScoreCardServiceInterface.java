//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.service;

import java.util.List;
import java.util.Map;

public interface ScoreCardServiceInterface {
	List getScoreParameterMap();

	List<Map<String, Object>> showScoreCardDetailedReport(final int customerid, final int affiliateid,
			final int transporterid, final int vehicleid, final int param, final String startDate,
			final String endDate);

	List<Map<String, Object>> showScoreCardReport(final int customerid, final int affiliateid, final int transporterid,
			final int vehicleid, final int param, final String startDate, final String endDate);
}
