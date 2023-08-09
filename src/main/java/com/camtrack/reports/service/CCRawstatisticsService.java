//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.service;

import java.util.List;
import java.util.Map;

import com.camtrack.entities.DetailsRawStatictics;
import com.camtrack.entities.User;
import com.camtrack.reports.bean.RawStatistic;
import com.camtrack.reports.bean.RawstatisticsBean;

public interface CCRawstatisticsService {
	List<RawStatistic> generateReport(final RawstatisticsBean rawstat, User user);

	List viewRawstatisticReeport(final int clientid, final int affiliateid, final int transporterid, final int driverid,
			final int startyear, final int endyear, final int startmonth, final int endmonth, final String language)
			throws Exception;

	DetailsRawStatictics detailrawstatistic(int clientId, int affiliateId0, int transporterId_, int driverId,
			int startYear, int endYear, int startMonth, int endMonth) throws Exception;

	List<Map<String, Object>> recovid(int clientid, int affiliateid, int transporterid);

	List<Map<String, Object>> removid(int clientid, int affiliateid, int transporterid);
}
