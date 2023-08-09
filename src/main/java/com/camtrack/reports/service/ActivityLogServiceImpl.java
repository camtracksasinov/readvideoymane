//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camtrack.reports.dao.ActivityLogDaoInterface;

@Service("activityLogService")
public class ActivityLogServiceImpl implements ActivityLogServiceInterface {
	@Autowired
	ActivityLogDaoInterface dao;

	@Override
	public List getAllActivities(final int optionid, final String fromdate, final String todate, final int userroleid,
			final int userid) throws Exception {
		return this.dao.getAllActivities(optionid, fromdate, todate, userroleid, userid);
	}
}
