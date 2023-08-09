//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.camtrack.config.Utils;

@Repository("scoreCardDao")
@PropertySource({ "classpath:sqlqueries.properties" })
public class ScoreCardDaoImpl implements ScoreCardDoInterface {
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List getScoreParameterMap() {
		final String query = this.environment.getRequiredProperty("scorecardmainparm.getmap");
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public List<Map<String, Object>> showScoreCardDetailedReport(final int customerid, final int affiliateid,
			final int transporterid, final int vehicleid, int param, final String startDate, final String endDate) {
		final List<Map<String, Object>> tempList = new ArrayList<>();
		final String colorratequery = "select drcolorname,drstartvalueincl,drendvalueincl from driverratingcolorcode where affiliate_id = "
				+ affiliateid + " AND client_id = " + customerid;
		final List<Map<String, Object>> colorRating = this.jdbcTemplate.queryForList(colorratequery);
		System.out.println("colrcode legnth" + colorRating.size());
		if (param != 0) {
			final String parmquery = "SELECT distinct sm.paramtypeid   FROM scorecardmainparam  sm  WHERE  sm.scmainparamid = "
					+ param;
			param = (int) this.jdbcTemplate.queryForObject(parmquery, (Class) Integer.class);
		}
		System.out.println("parm is" + param);
		final String query = "select * from sp_driverscorcard_new(" + customerid + "," + affiliateid + ","
				+ transporterid + "," + vehicleid + "," + param + ",'" + startDate + "','" + endDate + "')";
		System.out.println(query);
		final List<Map<String, Object>> reportlist = this.jdbcTemplate.queryForList(query);
		for (final Map<String, Object> scrorcardsreport : reportlist) {
			final String scorepoint = scrorcardsreport.get("score").toString();
			final Double score = Double.parseDouble(scorepoint);
			for (final Map<String, Object> colors : colorRating) {
				final String colorname = String.valueOf(colors.get("drcolorname"));
				final int colorstrtvalue1 = Utils.castIntegerObject(colors.get("drstartvalueincl"));
				final int colorendvalue2 = Utils.castIntegerObject(colors.get("drendvalueincl"));
				scrorcardsreport.put("colors", colorRating);
				if (score != null && score >= colorstrtvalue1 && score <= colorendvalue2) {
					scrorcardsreport.put("colorcode", colorname);
				}
			}
		}
		tempList.addAll(reportlist);
		return tempList;
	}

	@Override
	public List showScoreCardReport(final int customerid, final int affiliateid, final int transporterid,
			final int vehicleid, int param, final String startDate, final String endDate) {
		final List<Map<String, Object>> tempList = new ArrayList<>();
		String colorratequery = "";
		if (customerid > 0 || affiliateid > 0) {
			colorratequery = "select drcolorname,drstartvalueincl,drendvalueincl,affiliate_id,client_id from driverratingcolorcode where affiliate_id = "
					+ affiliateid + " OR client_id = " + customerid;
		} else {
			colorratequery = "select drcolorname,drstartvalueincl,drendvalueincl,affiliate_id,client_id from driverratingcolorcode";
		}
		final List<Map<String, Object>> colorRating = this.jdbcTemplate.queryForList(colorratequery);
		if (param != 0) {
			final String parmquery = "SELECT distinct sm.paramtypeid   FROM scorecardmainparam  sm  WHERE  sm.scmainparamid = "
					+ param;
			param = (int) this.jdbcTemplate.queryForObject(parmquery, (Class) Integer.class);
		}
		System.out.println("parm is" + param);
		String query = "";
		if (customerid == 0 && affiliateid == 0 && transporterid == 0 && vehicleid == 0) {
			query = "select sum(a.score) as exceptioncount,sum(a.totaldistance) as totaldistance, a.vehicleid,a.vehicledesc,a.driverid,a.drivername,a.affiliateid,a.affiliatename,a.transporterid,a.transportername,a.customername as customername,1 as asd  from sp_driverscorcard_new("
					+ customerid + "," + affiliateid + "," + transporterid + "," + vehicleid + "," + param + ",'"
					+ startDate + "','" + endDate
					+ "') a group by asd,a.customername,a.affiliateid,a.affiliatename,a.transporterid,a.transportername,a.vehicleid,a.vehicledesc,a.driverid,a.drivername order by exceptioncount DESC ";
		}
		if (customerid > 0 && affiliateid == 0 && transporterid == 0 && vehicleid == 0) {
			query = "select sum(a.score) as exceptioncount,sum(a.totaldistance) as totaldistance, a.vehicleid,a.vehicledesc,a.driverid,a.drivername,a.affiliateid,a.affiliatename,a.transporterid,a.transportername,a.customername as customername ,1 as asd  from sp_driverscorcard_new("
					+ customerid + "," + affiliateid + "," + transporterid + "," + vehicleid + "," + param + ",'"
					+ startDate + "','" + endDate
					+ "') a group by asd,a.customername,a.affiliateid,a.affiliatename,a.transporterid,a.transportername,a.vehicleid,a.vehicledesc,a.driverid,a.drivername order by exceptioncount DESC";
		}
		if (customerid > 0 && affiliateid > 0 && transporterid == 0 && vehicleid == 0) {
			query = "select sum(a.score) as exceptioncount,sum(a.totaldistance) as totaldistance, a.vehicleid,a.vehicledesc,a.driverid,a.drivername,a.affiliateid,a.affiliatename,a.transporterid,a.transportername,a.customername as customername,1 as asd  from sp_driverscorcard_new("
					+ customerid + "," + affiliateid + "," + transporterid + "," + vehicleid + "," + param + ",'"
					+ startDate + "','" + endDate
					+ "') a group by asd,a.customername,a.affiliateid,a.affiliatename,a.transporterid,a.transportername,a.vehicleid,a.vehicledesc,a.driverid,a.drivername order by exceptioncount DESC ";
		}
		if (customerid > 0 && affiliateid > 0 && transporterid > 0 && vehicleid == 0) {
			query = "select sum(a.score) as exceptioncount,sum(a.totaldistance) as totaldistance, a.vehicleid,a.vehicledesc,a.driverid,a.drivername,a.affiliateid,a.affiliatename,a.transporterid,a.transportername,a.customername as customername,1 as asd  from sp_driverscorcard_new("
					+ customerid + "," + affiliateid + "," + transporterid + "," + vehicleid + "," + param + ",'"
					+ startDate + "','" + endDate
					+ "') a group by asd,a.customername,a.affiliateid,a.affiliatename,a.transporterid,a.transportername,a.vehicleid,a.vehicledesc,a.driverid,a.drivername order by exceptioncount  DESC";
		}
		if (customerid > 0 && affiliateid > 0 && transporterid > 0 && vehicleid > 0) {
			query = "select sum(a.score) as exceptioncount,sum(a.totaldistance) as totaldistance, a.vehicleid,a.vehicledesc,a.driverid,a.drivername,a.affiliateid,a.affiliatename,a.transporterid,a.transportername,a.customername as customername ,1 as asd  from sp_driverscorcard_new("
					+ customerid + "," + affiliateid + "," + transporterid + "," + vehicleid + "," + param + ",'"
					+ startDate + "','" + endDate
					+ "') a group by asd,a.customername,a.affiliateid,a.affiliatename,a.transporterid,a.transportername,a.vehicleid,a.vehicledesc,a.driverid,a.drivername order by exceptioncount DESC ";
		}
		System.out.println(query);
		final List<Map<String, Object>> reportlist = this.jdbcTemplate.queryForList(query);
		for (final Map<String, Object> scrorcardsreport : reportlist) {
			final String scorepoint = scrorcardsreport.get("exceptioncount").toString();
			final Double score = Double.parseDouble(scorepoint);
			final int affililateId = Utils.castIntegerObject(scrorcardsreport.get("affiliateid"));
			scrorcardsreport.put("colors", colorRating);
			for (final Map<String, Object> colors : colorRating) {
				final String colorname = String.valueOf(colors.get("drcolorname"));
				final int colorstrtvalue1 = Utils.castIntegerObject(colors.get("drstartvalueincl"));
				final int colorendvalue2 = Utils.castIntegerObject(colors.get("drendvalueincl"));
				final int affilid_forcolr = Utils.castIntegerObject(colors.get("affiliate_id"));
				if (affililateId == affilid_forcolr) {
					if (colorname.equals("lbl_green")) {
						if (score == null || score < colorstrtvalue1 || score > colorendvalue2) {
							continue;
						}
						scrorcardsreport.put("colorcode", 0);
					} else if (colorname.equals("lbl_yellow")) {
						if (score == null || score <= colorstrtvalue1 || score > colorendvalue2) {
							continue;
						}
						scrorcardsreport.put("colorcode", 1);
					} else {
						if (!colorname.equals("lbl_red") || score == null
								|| (score <= colorstrtvalue1 && score <= colorendvalue2)) {
							continue;
						}
						scrorcardsreport.put("colorcode", 2);
					}
				}
			}
		}
		tempList.addAll(reportlist);
		return tempList;
	}
}
