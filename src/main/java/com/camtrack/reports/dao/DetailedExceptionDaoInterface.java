//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.dao;

import java.util.List;
import java.util.Map;

public interface DetailedExceptionDaoInterface {
	List checkInvalidatedNotifications(final int exceptionid);

	List checkNotifications(final int exceptionid);

	Map fetchClientAffiliateDetails(final int transporterid, final int vehicleid);

	List getAllExceptionLevels();

	List showDetailedExceptionReport(final int customerid, final int affiliateid, final int transporterid,
			final int vehicleid, final String startdate, final String enddate, final int exceptiontype,
			final String exceptionlevel);

	List showDetailedInvalidatedExceptionReport(final int customerid, final int affiliateid, final int transporterid,
			final int vehicleid, final String startdate, final String enddate, final int exceptiontype,
			final String exceptionlevel);
}
