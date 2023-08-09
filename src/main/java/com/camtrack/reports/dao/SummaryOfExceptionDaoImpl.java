//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("summaryofexceptiondao")
@PropertySource({ "classpath:sqlqueries.properties" })
public class SummaryOfExceptionDaoImpl implements SummaryOfExceptionDaoInterface {
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List showDetailedExceptionReport(final int parametertypeid, final int customerid, final int affiliateid,
			final int transporterid, final int vehicleid, final String startdate, final String enddate) {
		final String query = "select * from sp_detailedexceptionreport_v3(" + customerid + "," + affiliateid + ","
				+ transporterid + "," + vehicleid + "," + parametertypeid + ",ARRAY[1,2,3],'" + startdate + "','"
				+ enddate + "')";
		final List details = this.jdbcTemplate.queryForList(query);
		return details;
	}

	@Override
	public List showSummaryOfExceptionReport(final int customerid, final int affiliateid, final int transporterid,
			final int vehicleid, final String startdate, final String enddate) {
		final String paramtypequery = "SELECT parametertypeid,name,label FROM parametertype WHERE status = 1 order by parametertypeid asc";
		final List<Map<String, Object>> details = this.jdbcTemplate.queryForList(paramtypequery);
		for (final Map<String, Object> map : details) {
			final String recordingQry = "select exceptioncount from sp_summaryreport_withvehicle_v2(" + customerid + ","
					+ affiliateid + "," + transporterid + "," + vehicleid + "," + map.get("parametertypeid") + ",1,'"
					+ startdate + "','" + enddate + "')";
			final String alertQry = "select exceptioncount from sp_summaryreport_withvehicle_v2(" + customerid + ","
					+ affiliateid + "," + transporterid + "," + vehicleid + "," + map.get("parametertypeid") + ",2,'"
					+ startdate + "','" + enddate + "')";
			final String alarmQry = "select exceptioncount from sp_summaryreport_withvehicle_v2(" + customerid + ","
					+ affiliateid + "," + transporterid + "," + vehicleid + "," + map.get("parametertypeid") + ",3,'"
					+ startdate + "','" + enddate + "')";
			final long recordings = (int) this.jdbcTemplate.queryForObject(recordingQry, (Class) Integer.class);
			final long alerts = (int) this.jdbcTemplate.queryForObject(alertQry, (Class) Integer.class);
			final long alarms = (int) this.jdbcTemplate.queryForObject(alarmQry, (Class) Integer.class);
			map.put("recordings", recordings);
			map.put("alerts", alerts);
			map.put("alarms", alarms);
		}
		return details;
	}
}
