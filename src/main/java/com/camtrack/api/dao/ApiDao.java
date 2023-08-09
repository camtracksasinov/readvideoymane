//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.api.dao;

import java.util.List;

public interface ApiDao {
	List accelerationExceptionsPer100kms(final String affiliateid, final String transportername,
			final String vehicledesc, final String startime, final String endtime) throws Exception;

	List AffTransVeh(final int userid) throws Exception;

	List behavoiurViolationTrend(final String affiliateid, final String transportername, final String vehicledesc,
			final String startime, final String endtime) throws Exception;

	List continuousDrivingViolations(final String affiliateid, final String transportername, final String vehicledesc,
			final String startime, final String endtime) throws Exception;

	List dailyDriveViolations(final String affiliateid, final String transportername, final String vehicledesc,
			final String startime, final String endtime) throws Exception;

	List dailyRestViolations(final String affiliateid, final String transportername, final String vehicledesc,
			final String startime, final String endtime) throws Exception;

	List datewiseDriverBehaviourViolations(final String clientId, final String affiliateid,
			final String transportername, final String vehicledesc, final String startime, final String endtime,
			final String[] explevel, final String language) throws Exception;

	List datewiseDriverExceptionPer100Hrs(final String clientid, final String affiliateid, final String transportername,
			final String vehicledesc, final String startime, final String endtime, final String[] explevel,
			final String language) throws Exception;

	List datewiseDriverExceptionPer100Km(final String clientid, final String affiliateid, final String transportername,
			final String vehicledesc, final String startime, final String endtime, final String[] explevel,
			final String language) throws Exception;

	List datewiseDriverTimeViolations(final String clientid, final String affiliateid, final String transportername,
			final String vehicledesc, final String startime, final String endtime, final String[] explevel,
			final String language) throws Exception;

	List driverBehaviourViolations(final String clientid, final String affiliateid, final String transportername,
			final String vehicledesc, final String startime, final String endtime, final String[] explevel,
			final String language) throws Exception;

	List driverExceptionsPer100hrs(final String clientid, final String affiliateid, final String transporterid,
			final String vehicleid, final String startime, final String endtime, final String[] explevel,
			final String language) throws Exception;

	List driverExceptionsPer100kms(final String clientid, final String affiliateid, final String transporterid,
			final String vehicleid, final String startime, final String endtime, final String[] expLevel,
			final String language) throws Exception;

	List driveTimeViolations(final String clientid, final String affiliateid, final String transporterid,
			final String vehicleid, final String startime, final String endtime, final String[] expLevel,
			final String language) throws Exception;

	String fetchAffiliateCountry(final String affiliateid) throws Exception;

	List functionDriverBehaviourViolations(final String userid, final String affiliateid, final String transporterid,
			final String vehicleid, final String startime, final String endtime) throws Exception;

	List getAllAffiliates(final int userid) throws Exception;

	List getAllTransporters(final int userid, final int affiliateid) throws Exception;

	List getAllVehicles(final int userid, final int transporterid) throws Exception;

	List getViewDetails(final String view) throws Exception;

	List harshBreakingExceptionsPer100kms(final String affiliateid, final String transportername,
			final String vehicledesc, final String startime, final String endtime) throws Exception;

	List harshBreakingViolations(final String affiliateid, final String transportername, final String vehicledesc,
			final String startime, final String endtime) throws Exception;

	List heatMapFetchData(final String clientid, final String affiliateid, final String transporterid,
			final String vehicleid, final String startime, final String endtime, final String explevel,
			final String exceptionType, final String language) throws Exception;

	List newDriverBehaviourViolations(final String userid, final String affiliateid, final String transporterid,
			final String vehicleid, final String startime, final String endtime) throws Exception;

	List nightTimeDriveViolations(final String affiliateid, final String transportername, final String vehicledesc,
			final String startime, final String endtime) throws Exception;

	Double parseDouble(final Object value) throws Exception;

	int parseInt(final Object value) throws Exception;

	List reports(final String affiliateid, final String transportername, final String vehicledesc,
			final String startime, final String endtime) throws Exception;

	List speedingExceptionsPer100kms(final String affiliateid, final String transportername, final String vehicledesc,
			final String startime, final String endtime) throws Exception;

	List speedingViolations(final String affiliateid, final String transportername, final String vehicledesc,
			final String startime, final String endtime) throws Exception;

	List testDriverBehaviourViolations(final String userid, final String affiliatename, final String transportername,
			final String vehicledesc, final String startime, final String endtime, final int level) throws Exception;

	List transwiseDriverBehaviourViolations(final String clientid, final String affiliateid, final String transporterid,
			final String vehicleid, final String startime, final String endtime, final String exptype,
			final String[] explevel, final String language) throws Exception;

	List transwiseDriverBehaviourViolationsPer100Km(final String clientid, final String affiliateid,
			final String transporterid, final String vehicleid, final String startime, final String endtime,
			final String exptype, final String[] explevel, final String language) throws Exception;

	List vehiclewiseDriverBehaviourViolations(final String clientid, final String affiliateid,
			final String transporterid, final String vehicleid, final String startime, final String endtime,
			final String exptype, final String[] explevel, final String language) throws Exception;

	List vehiclewiseDriverBehaviourViolationsPer100Km(final String clientid, final String affiliateid,
			final String transporterid, final String vehicleid, final String startime, final String endtime,
			final String exptype, final String[] explevel, final String language) throws Exception;

	List weeklyRestViolations(final String affiliateid, final String transportername, final String vehicledesc,
			final String startime, final String endtime) throws Exception;
}
