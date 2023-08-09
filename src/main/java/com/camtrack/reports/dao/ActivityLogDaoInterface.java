//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.dao;

import java.util.List;

public interface ActivityLogDaoInterface {
	List getAllActivities(final int optionid, final String fromdate, final String todate, final int userroleid,
			final int userid) throws Exception;
}
