//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camtrack.api.dao.ApiDao;

@Service("apiservice")
public class ApiServiceImp implements ApiService {
	@Autowired
	ApiDao dao;

	@Override
	public List accelerationExceptionsPer100kms(final String affiliateid, final String transportername,
			final String vehicledesc, final String startime, final String endtime) throws Exception {
		return this.dao.accelerationExceptionsPer100kms(affiliateid, transportername, vehicledesc, startime, endtime);
	}

	@Override
	public List AffTransVeh(final int userid) throws Exception {
		return this.dao.AffTransVeh(userid);
	}

	@Override
	public List behavoiurViolationTrend(final String affiliateid, final String transportername,
			final String vehicledesc, final String startime, final String endtime) throws Exception {
		return this.dao.behavoiurViolationTrend(affiliateid, transportername, vehicledesc, startime, endtime);
	}

	@Override
	public List continuousDrivingViolations(final String affiliateid, final String transportername,
			final String vehicledesc, final String startime, final String endtime) throws Exception {
		return this.dao.continuousDrivingViolations(affiliateid, transportername, vehicledesc, startime, endtime);
	}

	@Override
	public List dailyDriveViolations(final String affiliateid, final String transportername, final String vehicledesc,
			final String startime, final String endtime) throws Exception {
		return this.dao.dailyDriveViolations(affiliateid, transportername, vehicledesc, startime, endtime);
	}

	@Override
	public List dailyRestViolations(final String affiliateid, final String transportername, final String vehicledesc,
			final String startime, final String endtime) throws Exception {
		return this.dao.dailyRestViolations(affiliateid, transportername, vehicledesc, startime, endtime);
	}

	@Override
	public List datewiseDriverBehaviourViolations(final String clientId, final String affiliateid,
			final String transporterid, final String vehicleid, final String startime, final String endtime,
			final String[] explevel, final String language) throws Exception {
		return this.dao.datewiseDriverBehaviourViolations(clientId, affiliateid, transporterid, vehicleid, startime,
				endtime, explevel, language);
	}

	@Override
	public List datewiseDriverExceptionPer100Hrs(final String clientid, final String affiliateid,
			final String transporterid, final String vehicleid, final String startime, final String endtime,
			final String[] explevel, final String language) throws Exception {
		return this.dao.datewiseDriverExceptionPer100Hrs(clientid, affiliateid, transporterid, vehicleid, startime,
				endtime, explevel, language);
	}

	@Override
	public List datewiseDriverExceptionPer100Km(final String clientid, final String affiliateid,
			final String transporterid, final String vehicleid, final String startime, final String endtime,
			final String[] explevel, final String language) throws Exception {
		return this.dao.datewiseDriverExceptionPer100Km(clientid, affiliateid, transporterid, vehicleid, startime,
				endtime, explevel, language);
	}

	@Override
	public List datewiseDriverTimeViolations(final String clientid, final String affiliateid,
			final String transporterid, final String vehicleid, final String startime, final String endtime,
			final String[] explevel, final String language) throws Exception {
		return this.dao.datewiseDriverTimeViolations(clientid, affiliateid, transporterid, vehicleid, startime, endtime,
				explevel, language);
	}

	@Override
	public List driverBehaviourViolations(final String clientid, final String affiliateid, final String transportername,
			final String vehicledesc, final String startime, final String endtime, final String[] explevel,
			final String language) throws Exception {
		return this.dao.driverBehaviourViolations(clientid, affiliateid, transportername, vehicledesc, startime,
				endtime, explevel, language);
	}

	@Override
	public List driverExceptionsPer100hrs(final String clientid, final String affiliateid, final String transporterid,
			final String vehicleid, final String startime, final String endtime, final String[] explevel,
			final String language) throws Exception {
		return this.dao.driverExceptionsPer100hrs(clientid, affiliateid, transporterid, vehicleid, startime, endtime,
				explevel, language);
	}

	@Override
	public List driverExceptionsPer100kms(final String clientid, final String affiliateid, final String transporterid,
			final String vehicleid, final String startime, final String endtime, final String[] explevel,
			final String language) throws Exception {
		return this.dao.driverExceptionsPer100kms(clientid, affiliateid, transporterid, vehicleid, startime, endtime,
				explevel, language);
	}

	@Override
	public List driveTimeViolations(final String clientid, final String affiliateid, final String transporterid,
			final String vehicleid, final String startime, final String endtime, final String[] expLevel,
			final String language) throws Exception {
		return this.dao.driveTimeViolations(clientid, affiliateid, transporterid, vehicleid, startime, endtime,
				expLevel, language);
	}

	@Override
	public String fetchAffiliateCountry(final String affiliateid) throws Exception {
		return this.dao.fetchAffiliateCountry(affiliateid);
	}

	@Override
	public List functionDriverBehaviourViolations(final String userid, final String affiliateid,
			final String transporterid, final String vehicleid, final String startime, final String endtime)
			throws Exception {
		return this.dao.functionDriverBehaviourViolations(userid, affiliateid, transporterid, vehicleid, startime,
				endtime);
	}

	@Override
	public List getAllAffiliates(final int userid) throws Exception {
		return this.dao.getAllAffiliates(userid);
	}

	@Override
	public List getAllTransporters(final int userid, final int affiliateid) throws Exception {
		return this.dao.getAllTransporters(userid, affiliateid);
	}

	@Override
	public List getAllVehicles(final int userid, final int transporterid) throws Exception {
		return this.dao.getAllVehicles(userid, transporterid);
	}

	@Override
	public List getViewDetails(final String view) throws Exception {
		return this.dao.getViewDetails(view);
	}

	@Override
	public List harshBreakingExceptionsPer100kms(final String affiliateid, final String transportername,
			final String vehicledesc, final String startime, final String endtime) throws Exception {
		return this.dao.harshBreakingExceptionsPer100kms(affiliateid, transportername, vehicledesc, startime, endtime);
	}

	@Override
	public List harshBreakingViolations(final String affiliateid, final String transportername,
			final String vehicledesc, final String startime, final String endtime) throws Exception {
		return this.dao.harshBreakingViolations(affiliateid, transportername, vehicledesc, startime, endtime);
	}

	@Override
	public List heatMapFetchData(final String clientid, final String affiliateid, final String transporterid,
			final String vehicleid, final String startime, final String endtime, final String explevel,
			final String exceptionType, final String language) throws Exception {
		return this.dao.heatMapFetchData(clientid, affiliateid, transporterid, vehicleid, startime, endtime, explevel,
				exceptionType, language);
	}

	@Override
	public List newDriverBehaviourViolations(final String userid, final String affiliateid, final String transporterid,
			final String vehicleid, final String startime, final String endtime) throws Exception {
		return this.dao.newDriverBehaviourViolations(userid, affiliateid, transporterid, vehicleid, startime, endtime);
	}

	@Override
	public List nightTimeDriveViolations(final String affiliateid, final String transportername,
			final String vehicledesc, final String startime, final String endtime) throws Exception {
		return this.dao.nightTimeDriveViolations(affiliateid, transportername, vehicledesc, startime, endtime);
	}

	@Override
	public List reports(final String affiliateid, final String transportername, final String vehicledesc,
			final String startime, final String endtime) throws Exception {
		return this.dao.reports(affiliateid, transportername, vehicledesc, startime, endtime);
	}

	@Override
	public List speedingExceptionsPer100kms(final String affiliateid, final String transportername,
			final String vehicledesc, final String startime, final String endtime) throws Exception {
		return this.dao.speedingExceptionsPer100kms(affiliateid, transportername, vehicledesc, startime, endtime);
	}

	@Override
	public List speedingViolations(final String affiliateid, final String transportername, final String vehicledesc,
			final String startime, final String endtime) throws Exception {
		return this.dao.speedingViolations(affiliateid, transportername, vehicledesc, startime, endtime);
	}

	@Override
	public List testDriverBehaviourViolations(final String userid, final String affiliatename,
			final String transportername, final String vehicledesc, final String startime, final String endtime,
			final int level) throws Exception {
		return this.dao.testDriverBehaviourViolations(userid, affiliatename, transportername, vehicledesc, startime,
				endtime, level);
	}

	@Override
	public List transwiseDriverBehaviourViolations(final String clientid, final String affiliateid,
			final String transporterid, final String vehicleid, final String startime, final String endtime,
			final String exptype, final String[] explevel, final String language) throws Exception {
		return this.dao.transwiseDriverBehaviourViolations(clientid, affiliateid, transporterid, vehicleid, startime,
				endtime, exptype, explevel, language);
	}

	@Override
	public List transwiseDriverBehaviourViolationsPer100Km(final String clientid, final String affiliateid,
			final String transporterid, final String vehicleid, final String startime, final String endtime,
			final String exptype, final String[] explevel, final String language) throws Exception {
		return this.dao.transwiseDriverBehaviourViolationsPer100Km(clientid, affiliateid, transporterid, vehicleid,
				startime, endtime, exptype, explevel, language);
	}

	@Override
	public List vehiclewiseDriverBehaviourViolations(final String clientid, final String affiliateid,
			final String transporterid, final String vehicleid, final String startime, final String endtime,
			final String exptype, final String[] explevel, final String language) throws Exception {
		return this.dao.vehiclewiseDriverBehaviourViolations(clientid, affiliateid, transporterid, vehicleid, startime,
				endtime, exptype, explevel, language);
	}

	@Override
	public List vehiclewiseDriverBehaviourViolationsPer100Km(final String clientid, final String affiliateid,
			final String transporterid, final String vehicleid, final String startime, final String endtime,
			final String exptype, final String[] explevel, final String language) throws Exception {
		return this.dao.vehiclewiseDriverBehaviourViolationsPer100Km(clientid, affiliateid, transporterid, vehicleid,
				startime, endtime, exptype, explevel, language);
	}

	@Override
	public List weeklyRestViolations(final String affiliateid, final String transportername, final String vehicledesc,
			final String startime, final String endtime) throws Exception {
		return this.dao.weeklyRestViolations(affiliateid, transportername, vehicledesc, startime, endtime);
	}
}
