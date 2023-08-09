//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camtrack.reports.dao.DetailedExceptionDaoInterface;

@Service("detailedexceptionservice")
public class DetailedExceptionServiceImpl implements DetailedExceptionServiceInterface {
	@Autowired
	DetailedExceptionDaoInterface dao;

	@Override
	public Map fetchClientAffiliateDetails(final int transporterid, final int vehicleid) {
		return this.dao.fetchClientAffiliateDetails(transporterid, vehicleid);
	}

	@Override
	public List getAllExceptionLevels() {
		return this.dao.getAllExceptionLevels();
	}

	@Override
	public List showDetailedExceptionReport(final int customerid, final int affiliateid, final int transporterid,
			final int vehicleid, final String startdate, final String enddate, final int exceptiontype,
			final String exceptionlevel) {
		return this.dao.showDetailedExceptionReport(customerid, affiliateid, transporterid, vehicleid, startdate,
				enddate, exceptiontype, exceptionlevel);
	}

	@Override
	public List showDetailedInvalidatedExceptionReport(final int customerid, final int affiliateid,
			final int transporterid, final int vehicleid, final String startdate, final String enddate,
			final int exceptiontype, final String exceptionlevel) {
		return this.dao.showDetailedInvalidatedExceptionReport(customerid, affiliateid, transporterid, vehicleid,
				startdate, enddate, exceptiontype, exceptionlevel);
	}

}
