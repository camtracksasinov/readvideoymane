//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.service;

import java.util.List;

public interface ActivityLogServiceInterface {
	List getAllActivities(final int optionid, final String fromdate, final String todate, final int userroleid,
			final int userid) throws Exception;
}
