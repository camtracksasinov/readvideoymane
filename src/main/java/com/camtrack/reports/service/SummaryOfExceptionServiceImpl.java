//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camtrack.reports.dao.SummaryOfExceptionDaoInterface;

@Service("summaryofexceptionservice")
public class SummaryOfExceptionServiceImpl implements SummaryOfExceptionServiceInterface {
	@Autowired
	SummaryOfExceptionDaoInterface dao;

	@Override
	public List showDetailedExceptionReport(final int parametertypeid, final int customerid, final int affiliateid,
			final int transporterid, final int vehicleid, final String startdate, final String enddate) {
		return this.dao.showDetailedExceptionReport(parametertypeid, customerid, affiliateid, transporterid, vehicleid,
				startdate, enddate);
	}

	@Override
	public List showSummaryOfExceptionReport(final int customerid, final int affiliateid, final int transporterid,
			final int vehicleid, final String startdate, final String enddate) {
		return this.dao.showSummaryOfExceptionReport(customerid, affiliateid, transporterid, vehicleid, startdate,
				enddate);
	}
}
