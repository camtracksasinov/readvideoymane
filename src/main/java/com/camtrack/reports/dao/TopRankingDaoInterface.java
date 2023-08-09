//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.dao;

import java.util.List;

public interface TopRankingDaoInterface {
	List getVehicles(final int transporterid);

	List showTopRankingReport(final int customerid, final int affiliateid, final int transporterid, final int vehicleid,
			final int param, final String exceptionlevels, final String startDate, final String endDate);
}
