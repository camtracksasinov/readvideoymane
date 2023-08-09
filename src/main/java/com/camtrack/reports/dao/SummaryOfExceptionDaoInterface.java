//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.dao;

import java.util.List;

public interface SummaryOfExceptionDaoInterface {
	List showDetailedExceptionReport(final int parametertypeid, final int customerid, final int affiliateid,
			final int transporterid, final int vehicleid, final String startdate, final String enddate);

	List showSummaryOfExceptionReport(final int customerid, final int affiliateid, final int transporterid,
			final int vehicleid, final String startdate, final String enddate);
}
