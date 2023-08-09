//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("activityLogDao")
@PropertySource({ "classpath:sqlqueries.properties" })
public class ActivityLogDaoImpl implements ActivityLogDaoInterface {
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List getAllActivities(final int optionid, final String fromdate, final String todate, final int userroleid,
			final int userid) throws Exception {
		List historyList = null;
		List scorecardList = null;
		List scpList = null;
		List scpobcparmList = null;
		List visulparmList = null;
		List recoveryList = null;
		List scpvisualparameterList = null;
		List scprecoveryList = null;
		List driverratingcolorcodeList = null;
		List manualrecoveryList = null;
		List manualsubstractionList = null;
		String andaffliateid = "";
		String andcustomerid = "";
		int affiliateid = 0;
		int customerid = 0;
		if (userroleid > 3) {
			final String gueryforaffiliateid = "select \"user\".affiliateid from \"user\" where \"user\".userid="
					+ userid + "";
			affiliateid = (int) this.jdbcTemplate.queryForObject(gueryforaffiliateid, (Class) Integer.class);
			andaffliateid = " and au.affiliateid =" + affiliateid + "";
			final String gueryforcustomerid = "select \"user\".customerid from \"user\" where \"user\".userid=" + userid
					+ " ";
			customerid = (int) this.jdbcTemplate.queryForObject(gueryforcustomerid, (Class) Integer.class);
			andcustomerid = "and au.clientid=" + customerid + " ";
		}
		if (userroleid > 1 && userroleid <= 3) {
			final String gueryforcustomerid2 = "select \"user\".customerid from \"user\" where \"user\".userid="
					+ userid + " ";
			customerid = (int) this.jdbcTemplate.queryForObject(gueryforcustomerid2, (Class) Integer.class);
			andcustomerid = "and au.clientid=" + customerid + " ";
		}
		String criteria = "";
		if (fromdate != null && fromdate.length() == 10 && todate != null && todate.length() == 10) {
			criteria = criteria + " and date(au.updatedon) between '" + fromdate + "' and '" + todate + "'";
		} else if (fromdate != null && fromdate.length() == 10) {
			criteria = criteria + " and date(au.updatedon) >= '" + fromdate + "'";
		} else if (todate != null && todate.length() == 10) {
			criteria = criteria + " and date(au.updatedon) <= '" + todate + "'";
		}
		String orderby = "";
		String tablename = "";
		final String timezoneselect = "  case when u.userroleid >3 then au.updatedon::timestamp at time zone (select  af.afftimezone from customeraffiliate af where af.affiliateid=u.affiliateid) else au.updatedon::timestamp  end as updatedon ";
		final String affiliatejoin = "";
		if (optionid == 0) {
			tablename = "audituser";
			orderby = " order by au.audituserid desc";
			String query1 = "";
			if (userroleid == 1) {
				query1 = "SELECT au.comments," + timezoneselect + ",au.flag,u.name as updatedbyuser FROM \"" + tablename
						+ "\" au join \"user\" u on u.userid=au.updatedby " + affiliatejoin + " where 1=1 " + criteria
						+ orderby;
			} else if (userroleid > 1 && userroleid <= 3) {
				query1 = "SELECT au.comments," + timezoneselect + ",au.flag,u.name as updatedbyuser FROM \"" + tablename
						+ "\" au join \"user\" u on u.userid=au.updatedby " + affiliatejoin
						+ " where 1=1 and au.customerid=" + customerid + "  " + criteria + orderby;
			} else if (userroleid > 3) {
				query1 = "SELECT au.comments," + timezoneselect + ",au.flag,u.name as updatedbyuser FROM \"" + tablename
						+ "\" au join \"user\" u on u.userid=au.updatedby " + affiliatejoin
						+ " where 1=1 and au.customerid=" + customerid + " and au.affiliateid=" + affiliateid + "  "
						+ criteria + orderby;
			}
			String query2 = "";
			if (userroleid <= 3) {
				tablename = "auditcustomer";
				orderby = " order by au.auditcustomerid desc";
				query2 = "  SELECT au.comments," + timezoneselect + ",au.flag,u.name as updatedbyuser FROM \""
						+ tablename + "\" au join \"user\" u on u.userid=au.updatedby " + affiliatejoin + " where 1=1  "
						+ criteria + orderby;
			}
			String query3 = "";
			if (userroleid == 1) {
				tablename = "auditcustomeraffiliate";
				orderby = " order by au.auditaffiliateid desc";
				query3 = " SELECT au.comments," + timezoneselect + ",au.flag,u.name as updatedbyuser FROM \""
						+ tablename + "\" au join \"user\" u on u.userid=au.updatedby  " + affiliatejoin + " where 1=1 "
						+ criteria + orderby;
			} else if (userroleid > 1 && userroleid <= 3) {
				tablename = "auditcustomeraffiliate";
				orderby = " order by au.auditaffiliateid desc";
				query3 = "  SELECT au.comments," + timezoneselect + ",au.flag,u.name as updatedbyuser FROM \""
						+ tablename + "\" au join \"user\" u on u.userid=au.updatedby  " + affiliatejoin
						+ " where 1=1 and au.customerid=" + customerid + "" + criteria + orderby;
			}
			tablename = "auditparameterconfig";
			orderby = " order by au.auditparameterconfigid desc";
			String query4 = "";
			if (userroleid == 1) {
				query4 = " SELECT au.comments," + timezoneselect + ",au.flag,u.name as updatedbyuser FROM \""
						+ tablename + "\" au join \"user\" u on u.userid=au.updatedby " + affiliatejoin + "  where 1=1 "
						+ criteria + orderby;
			} else if (userroleid > 1 && userroleid <= 3) {
				query4 = " SELECT au.comments," + timezoneselect + ",au.flag,u.name as updatedbyuser FROM \""
						+ tablename + "\" au join \"user\" u on u.userid=au.updatedby " + affiliatejoin
						+ "  where 1=1 and au.customerid=" + customerid + " " + criteria + orderby;
			} else if (userroleid > 3) {
				query4 = " SELECT au.comments," + timezoneselect + ",au.flag,u.name as updatedbyuser FROM \""
						+ tablename + "\" au join \"user\" u on u.userid=au.updatedby " + affiliatejoin
						+ "  where 1=1 and au.customerid=" + customerid + " and au.clientaffiliateid=" + affiliateid
						+ " " + criteria + orderby;
			}
			String query5 = "";
			if (userroleid == 1) {
				tablename = "auditemailconfig";
				orderby = " order by au.auditconfigid desc";
				query5 = "SELECT au.comments," + timezoneselect + ",au.flag,u.name as updatedbyuser FROM \"" + tablename
						+ "\" au join \"user\" u on u.userid=au.updatedby " + affiliatejoin + "  where 1=1 " + criteria
						+ orderby;
			}
			tablename = "auditmanualaccicp";
			orderby = " order by au.auditmanualaccicpid desc";
			final String query6 = "SELECT au.comments," + timezoneselect + ",au.flag,u.name as updatedbyuser FROM \""
					+ tablename + "\" au join \"user\" u on u.userid=au.updatedby " + affiliatejoin + " where 1=1 "
					+ andcustomerid + " " + andaffliateid + " " + criteria + orderby;
			tablename = "auditinvalidatedexception";
			orderby = " order by au.auditid desc";
			final String query7 = "SELECT au.triggercomment as comments," + timezoneselect
					+ ",au.flag,u.name as updatedbyuser FROM \"" + tablename
					+ "\" au join \"user\" u on u.userid=au.updatedby " + affiliatejoin + " where 1=1 " + andcustomerid
					+ " " + andaffliateid + "  " + criteria + orderby;
			String allQuery = "";
			if (userroleid == 1) {
				allQuery = "(" + query1 + ") UNION (" + query2 + ") UNION (" + query3 + ") UNION (" + query4
						+ ") UNION (" + query5 + ") UNION (" + query6 + ")  UNION (" + query7
						+ ") order by updatedon desc";
			} else if (userroleid <= 3 && userroleid > 1) {
				allQuery = "(" + query1 + ") UNION (" + query2 + ") UNION (" + query3 + ") UNION (" + query4
						+ ")  UNION (" + query6 + ")  order by updatedon desc";
			} else {
				allQuery = "(" + query1 + ")   UNION (" + query4 + ")  UNION (" + query6 + ")  order by updatedon desc";
			}
			historyList = this.jdbcTemplate.queryForList(allQuery);
			if (userroleid == 1) {
				final String queryforscorecard = "SELECT au.comments," + timezoneselect
						+ ",au.flag,u.name as updatedbyuser FROM \"auditscorecardparam\" au join \"user\" u on u.userid=au.updatedby "
						+ affiliatejoin + "  where 1=1 " + criteria + "order by au.auditscparamid desc";
				scorecardList = this.jdbcTemplate.queryForList(queryforscorecard);
			} else if (userroleid > 1 && userroleid <= 3) {
				final String queryforscorecard = "SELECT au.comments," + timezoneselect
						+ ",au.flag,u.name as updatedbyuser FROM \"auditscorecardparam\" au join \"user\" u on u.userid=au.updatedby "
						+ affiliatejoin + "  where 1=1  and au.client_id=" + customerid + "" + criteria
						+ "order by au.auditscparamid desc";
				scorecardList = this.jdbcTemplate.queryForList(queryforscorecard);
			} else if (userroleid > 3) {
				final String queryforscorecard = "SELECT au.comments," + timezoneselect
						+ ",au.flag,u.name as updatedbyuser FROM \"auditscorecardparam\" au join \"user\" u on u.userid=au.updatedby "
						+ affiliatejoin + "  where 1=1 and au.affiliate_id=" + affiliateid + " and au.client_id="
						+ customerid + "" + criteria + "order by au.auditscparamid desc";
				scorecardList = this.jdbcTemplate.queryForList(queryforscorecard);
			}
			final String queryforscp = "SELECT au.comments," + timezoneselect
					+ ",au.flag,u.name as updatedbyuser FROM \"auditscp\" au join \"user\" u on u.userid=au.updatedby "
					+ affiliatejoin + "  where 1=1 " + andcustomerid + " " + andaffliateid + " " + criteria
					+ "order by au.auditscpid desc";
			scpList = this.jdbcTemplate.queryForList(queryforscp);
			System.out.println(queryforscp);
			final String queryforscpobcparm = "SELECT au.comments," + timezoneselect
					+ ",au.flag,u.name as updatedbyuser FROM \"auditscpobcparameter\" au join \"user\" u on u.userid=au.updatedby "
					+ affiliatejoin + "  where 1=1 " + andcustomerid + " " + andaffliateid + " " + criteria
					+ "order by au.auditobcscpparamid desc";
			scpobcparmList = this.jdbcTemplate.queryForList(queryforscpobcparm);
			final String queryforvisulparm = "SELECT au.comments," + timezoneselect
					+ ",au.flag,u.name as updatedbyuser FROM \"auditvisualparameter\" au join \"user\" u on u.userid=au.updatedby "
					+ affiliatejoin + "  where 1=1 " + andcustomerid + " " + andaffliateid + " " + criteria
					+ "order by au.auditvisualparameterid desc";
			visulparmList = this.jdbcTemplate.queryForList(queryforvisulparm);
			final String queryforrecovery = "SELECT au.comments," + timezoneselect
					+ ",au.flag,u.name as updatedbyuser FROM \"auditrecovery\" au join \"user\" u on u.userid=au.updatedby "
					+ affiliatejoin + "  where 1=1 " + andcustomerid + " " + andaffliateid + " " + criteria
					+ "order by au.auditrecoveryid desc";
			recoveryList = this.jdbcTemplate.queryForList(queryforrecovery);
			final String queryforscpvisualparameter = "SELECT au.comments," + timezoneselect
					+ ",au.flag,u.name as updatedbyuser FROM \"auditscpvisualparameter\" au join \"user\" u on u.userid=au.updatedby "
					+ affiliatejoin + "  where 1=1 " + andcustomerid + " " + andaffliateid + " " + criteria
					+ "order by au.auditscpvisualparameterid desc";
			scpvisualparameterList = this.jdbcTemplate.queryForList(queryforscpvisualparameter);
			final String queryforscprecovery = "SELECT au.comments," + timezoneselect
					+ ",au.flag,u.name as updatedbyuser FROM \"auditscprecovery\" au join \"user\" u on u.userid=au.updatedby "
					+ affiliatejoin + "  where 1=1 " + andcustomerid + " " + andaffliateid + " " + criteria
					+ "order by au.auditscprecoveryid desc";
			scprecoveryList = this.jdbcTemplate.queryForList(queryforscprecovery);
			if (userroleid == 1) {
				final String queryfordriverratingcolorcode = "SELECT au.comments," + timezoneselect
						+ ",au.flag,u.name as updatedbyuser FROM \"auditdriverratingcolorcode\" au join \"user\" u on u.userid=au.updatedby "
						+ affiliatejoin + "  where 1=1  " + criteria + "order by au.auditdrcolorcodeid desc";
				driverratingcolorcodeList = this.jdbcTemplate.queryForList(queryfordriverratingcolorcode);
			} else if (userroleid > 1 && userroleid <= 3) {
				final String queryfordriverratingcolorcode = "SELECT au.comments," + timezoneselect
						+ ",au.flag,u.name as updatedbyuser FROM \"auditdriverratingcolorcode\" au join \"user\" u on u.userid=au.updatedby "
						+ affiliatejoin + "  where 1=1  and au.client_id=" + customerid + " " + criteria
						+ "order by au.auditdrcolorcodeid desc";
				driverratingcolorcodeList = this.jdbcTemplate.queryForList(queryfordriverratingcolorcode);
			} else if (userroleid > 3) {
				final String queryfordriverratingcolorcode = "SELECT au.comments," + timezoneselect
						+ ",au.flag,u.name as updatedbyuser FROM \"auditdriverratingcolorcode\" au join \"user\" u on u.userid=au.updatedby "
						+ affiliatejoin + "  where 1=1 and au.affiliate_id=" + affiliateid + " and au.client_id="
						+ customerid + " " + criteria + "order by au.auditdrcolorcodeid desc";
				driverratingcolorcodeList = this.jdbcTemplate.queryForList(queryfordriverratingcolorcode);
			}
			final String queryformanualrecovery = "SELECT au.comments," + timezoneselect
					+ ",au.flag,u.name as updatedbyuser FROM \"auditmanualrecovery\" au join \"user\" u on u.userid=au.updatedby "
					+ affiliatejoin + "  where 1=1 " + andcustomerid + " " + andaffliateid + " " + criteria
					+ "order by au.auditmanualrecoveryid desc";
			manualrecoveryList = this.jdbcTemplate.queryForList(queryformanualrecovery);
			final String queryformanualsubstraction = "SELECT au.comments," + timezoneselect
					+ ",au.flag,u.name as updatedbyuser FROM \"auditmanualsubstraction\" au join \"user\" u on u.userid=au.updatedby "
					+ affiliatejoin + "  where 1=1 " + andcustomerid + " " + andaffliateid + " " + criteria
					+ "order by au.auditmanualsubstractionid desc";
			manualsubstractionList = this.jdbcTemplate.queryForList(queryformanualsubstraction);
			if (scorecardList != null) {
				historyList.addAll(scorecardList);
			}
			if (scpList != null) {
				historyList.addAll(scpList);
			}
			if (scpobcparmList != null) {
				historyList.addAll(scpobcparmList);
			}
			if (visulparmList != null) {
				historyList.addAll(visulparmList);
			}
			if (recoveryList != null) {
				historyList.addAll(recoveryList);
			}
			if (scpvisualparameterList != null) {
				historyList.addAll(scpvisualparameterList);
			}
			if (scprecoveryList != null) {
				historyList.addAll(scprecoveryList);
			}
			if (driverratingcolorcodeList != null) {
				historyList.addAll(driverratingcolorcodeList);
			}
			if (manualrecoveryList != null) {
				historyList.addAll(manualrecoveryList);
			}
			if (manualsubstractionList != null) {
				historyList.addAll(manualsubstractionList);
			}
			return historyList;
		}
		if (optionid == 1) {
			tablename = "audituser";
			orderby = " order by au.audituserid desc";
			final String query8 = "SELECT au.comments," + timezoneselect + ",au.flag,u.name as updatedbyuser FROM \""
					+ tablename + "\" au join \"user\" u on u.userid=au.updatedby " + affiliatejoin + " where 1=1 "
					+ criteria + orderby;
			historyList = this.jdbcTemplate.queryForList(query8);
		} else if (optionid == 2) {
			if (userroleid <= 3) {
				tablename = "auditcustomer";
				orderby = " order by au.auditcustomerid desc";
				final String query8 = "SELECT au.comments," + timezoneselect
						+ ",au.flag,u.name as updatedbyuser FROM \"" + tablename
						+ "\" au join \"user\" u on u.userid=au.updatedby " + affiliatejoin + " where 1=1 " + criteria
						+ orderby;
				historyList = this.jdbcTemplate.queryForList(query8);
			}
		} else if (optionid == 3) {
			if (userroleid == 1) {
				tablename = "auditcustomeraffiliate";
				orderby = " order by au.auditaffiliateid desc";
				final String query8 = "SELECT au.comments," + timezoneselect
						+ ",au.flag,u.name as updatedbyuser FROM \"" + tablename
						+ "\" au join \"user\" u on u.userid=au.updatedby " + affiliatejoin + " where 1=1 " + criteria
						+ orderby;
				historyList = this.jdbcTemplate.queryForList(query8);
			} else if (userroleid > 1 && userroleid <= 5) {
				tablename = "auditcustomeraffiliate";
				orderby = " order by au.auditaffiliateid desc";
				final String query8 = "SELECT au.comments," + timezoneselect
						+ ",au.flag,u.name as updatedbyuser FROM \"" + tablename
						+ "\" au join \"user\" u on u.userid=au.updatedby " + affiliatejoin
						+ " where 1=1 and au.customerid=" + customerid + "" + criteria + orderby;
				historyList = this.jdbcTemplate.queryForList(query8);
			}
		} else if (optionid == 4) {
			if (userroleid > 1 && userroleid <= 3) {
				final String queryforscorecard2 = "SELECT au.comments," + timezoneselect
						+ ",au.flag,u.name as updatedbyuser FROM \"auditscorecardparam\" au join \"user\" u on u.userid=au.updatedby "
						+ affiliatejoin + "  where 1=1  and au.client_id=" + customerid + "" + criteria
						+ "order by au.auditscparamid desc";
				scorecardList = this.jdbcTemplate.queryForList(queryforscorecard2);
			} else if (userroleid > 3) {
				final String queryforscorecard2 = "SELECT au.comments," + timezoneselect
						+ ",au.flag,u.name as updatedbyuser FROM \"auditscorecardparam\" au join \"user\" u on u.userid=au.updatedby "
						+ affiliatejoin + "  where 1=1 and au.affiliate_id=" + affiliateid + " and au.client_id="
						+ customerid + "" + criteria + "order by au.auditscparamid desc";
				scorecardList = this.jdbcTemplate.queryForList(queryforscorecard2);
			} else {
				final String queryforscorecard2 = "SELECT au.comments," + timezoneselect
						+ ",au.flag,u.name as updatedbyuser FROM \"auditscorecardparam\" au join \"user\" u on u.userid=au.updatedby "
						+ affiliatejoin + "  where 1=1 " + criteria + "order by au.auditscparamid desc";
				scorecardList = this.jdbcTemplate.queryForList(queryforscorecard2);
			}
			final String queryforscp2 = "SELECT au.comments," + timezoneselect
					+ ",au.flag,u.name as updatedbyuser FROM \"auditscp\" au join \"user\" u on u.userid=au.updatedby  "
					+ affiliatejoin + "   where 1=1 " + andcustomerid + " " + andaffliateid + " " + criteria
					+ "order by au.auditscpid desc";
			scpList = this.jdbcTemplate.queryForList(queryforscp2);
			System.out.println(queryforscp2);
			final String queryforscpobcparm2 = "SELECT au.comments," + timezoneselect
					+ ",au.flag,u.name as updatedbyuser FROM \"auditscpobcparameter\" au join \"user\" u on u.userid=au.updatedby "
					+ affiliatejoin + "  where 1=1 " + andcustomerid + " " + andaffliateid + " " + criteria
					+ "order by au.auditobcscpparamid desc";
			scpobcparmList = this.jdbcTemplate.queryForList(queryforscpobcparm2);
			final String queryforvisulparm2 = "SELECT au.comments," + timezoneselect
					+ ",au.flag,u.name as updatedbyuser FROM \"auditvisualparameter\" au join \"user\" u on u.userid=au.updatedby "
					+ affiliatejoin + "   where 1=1 " + andcustomerid + " " + andaffliateid + " " + criteria
					+ "order by au.auditvisualparameterid desc";
			visulparmList = this.jdbcTemplate.queryForList(queryforvisulparm2);
			final String queryforrecovery2 = "SELECT au.comments," + timezoneselect
					+ ",au.flag,u.name as updatedbyuser FROM \"auditrecovery\" au join \"user\" u on u.userid=au.updatedby "
					+ affiliatejoin + "  where 1=1  " + andcustomerid + " " + andaffliateid + " " + criteria
					+ "order by au.auditrecoveryid desc";
			recoveryList = this.jdbcTemplate.queryForList(queryforrecovery2);
			final String queryforscpvisualparameter2 = "SELECT au.comments," + timezoneselect
					+ ",au.flag,u.name as updatedbyuser FROM \"auditscpvisualparameter\" au join \"user\" u on u.userid=au.updatedby "
					+ affiliatejoin + "  where 1=1 " + andcustomerid + " " + andaffliateid + " " + criteria
					+ "order by au.auditscpvisualparameterid desc";
			scpvisualparameterList = this.jdbcTemplate.queryForList(queryforscpvisualparameter2);
			final String queryforscprecovery2 = "SELECT au.comments," + timezoneselect
					+ ",au.flag,u.name as updatedbyuser FROM \"auditscprecovery\" au join \"user\" u on u.userid=au.updatedby "
					+ affiliatejoin + "  where 1=1 " + andcustomerid + " " + andaffliateid + " " + criteria
					+ "order by au.auditscprecoveryid desc";
			scprecoveryList = this.jdbcTemplate.queryForList(queryforscprecovery2);
			if (userroleid > 1 && userroleid <= 3) {
				final String queryfordriverratingcolorcode2 = "SELECT au.comments," + timezoneselect
						+ ",au.flag,u.name as updatedbyuser FROM \"auditdriverratingcolorcode\" au join \"user\" u on u.userid=au.updatedby "
						+ affiliatejoin + "  where 1=1  and au.client_id=" + customerid + " " + criteria
						+ "order by au.auditdrcolorcodeid desc";
				driverratingcolorcodeList = this.jdbcTemplate.queryForList(queryfordriverratingcolorcode2);
			} else if (userroleid > 3) {
				final String queryfordriverratingcolorcode2 = "SELECT au.comments," + timezoneselect
						+ ",au.flag,u.name as updatedbyuser FROM \"auditdriverratingcolorcode\" au join \"user\" u on u.userid=au.updatedby "
						+ affiliatejoin + "  where 1=1 and au.affiliate_id=" + affiliateid + " and au.client_id="
						+ customerid + " " + criteria + "order by au.auditdrcolorcodeid desc";
				driverratingcolorcodeList = this.jdbcTemplate.queryForList(queryfordriverratingcolorcode2);
			} else {
				final String queryfordriverratingcolorcode2 = "SELECT au.comments," + timezoneselect
						+ ",au.flag,u.name as updatedbyuser FROM \"auditdriverratingcolorcode\" au join \"user\" u on u.userid=au.updatedby "
						+ affiliatejoin + "  where 1=1  " + criteria + "order by au.auditdrcolorcodeid desc";
				driverratingcolorcodeList = this.jdbcTemplate.queryForList(queryfordriverratingcolorcode2);
			}
			tablename = "auditparameterconfig";
			orderby = " order by au.auditparameterconfigid desc";
			if (userroleid == 1) {
				final String query9 = " SELECT au.comments," + timezoneselect
						+ ",au.flag,u.name as updatedbyuser FROM \"" + tablename
						+ "\" au join \"user\" u on u.userid=au.updatedby " + affiliatejoin + "  where 1=1 " + criteria
						+ orderby;
				historyList = this.jdbcTemplate.queryForList(query9);
			} else if (userroleid > 1 && userroleid <= 3) {
				final String query9 = " SELECT au.comments," + timezoneselect
						+ ",au.flag,u.name as updatedbyuser FROM \"" + tablename
						+ "\" au join \"user\" u on u.userid=au.updatedby " + affiliatejoin
						+ "  where 1=1 and au.customerid=" + customerid + " " + criteria + orderby;
				historyList = this.jdbcTemplate.queryForList(query9);
			} else if (userroleid > 3) {
				final String query9 = " SELECT au.comments," + timezoneselect
						+ ",au.flag,u.name as updatedbyuser FROM \"" + tablename
						+ "\" au join \"user\" u on u.userid=au.updatedby " + affiliatejoin
						+ "  where 1=1 and au.customerid=" + customerid + " and au.clientaffiliateid=" + affiliateid
						+ " " + criteria + orderby;
				historyList = this.jdbcTemplate.queryForList(query9);
			}
		} else if (optionid == 5) {
			tablename = "auditemailconfig";
			orderby = " order by au.auditconfigid desc";
			if (userroleid == 1) {
				final String query8 = "SELECT au.comments," + timezoneselect
						+ ",au.flag,u.name as updatedbyuser FROM \"" + tablename
						+ "\" au join \"user\" u on u.userid=au.updatedby " + affiliatejoin + " where 1=1 "
						+ andcustomerid + " " + andaffliateid + "  " + criteria + orderby;
				historyList = this.jdbcTemplate.queryForList(query8);
			}
		} else if (optionid == 6) {
			final String queryformanualrecovery2 = "SELECT au.comments," + timezoneselect
					+ ",au.flag,u.name as updatedbyuser FROM \"auditmanualrecovery\" au join \"user\" u on u.userid=au.updatedby "
					+ affiliatejoin + "  where 1=1 " + andcustomerid + " " + andaffliateid + " " + criteria
					+ "order by au.auditmanualrecoveryid desc";
			manualrecoveryList = this.jdbcTemplate.queryForList(queryformanualrecovery2);
			final String queryformanualsubstraction2 = "SELECT au.comments," + timezoneselect
					+ ",au.flag,u.name as updatedbyuser FROM \"auditmanualsubstraction\" au join \"user\" u on u.userid=au.updatedby "
					+ affiliatejoin + "  where 1=1 " + andcustomerid + " " + andaffliateid + " " + criteria
					+ "order by au.auditmanualsubstractionid desc";
			manualsubstractionList = this.jdbcTemplate.queryForList(queryformanualsubstraction2);
			tablename = "auditmanualaccicp";
			orderby = " order by au.auditmanualaccicpid desc";
			final String query10 = "SELECT au.comments," + timezoneselect + ",au.flag,u.name as updatedbyuser FROM \""
					+ tablename + "\" au join \"user\" u on u.userid=au.updatedby " + affiliatejoin + " where 1=1 "
					+ andcustomerid + " " + andaffliateid + "  " + criteria + orderby;
			historyList = this.jdbcTemplate.queryForList(query10);
		} else if (optionid == 7) {
			tablename = "auditinvalidatedexception";
			orderby = " order by au.auditid desc";
			if (userroleid == 1) {
				final String query8 = "SELECT au.triggercomment as comments," + timezoneselect
						+ ",au.flag,u.name as updatedbyuser FROM \"" + tablename
						+ "\" au join \"user\" u on u.userid=au.updatedby " + affiliatejoin + " where 1=1 "
						+ andcustomerid + " " + andaffliateid + "  " + criteria + orderby;
				historyList = this.jdbcTemplate.queryForList(query8);
			}
		} else if (optionid == 8) {
		}
		if (scorecardList != null) {
			historyList.addAll(scorecardList);
		}
		if (scpList != null) {
			historyList.addAll(scpList);
		}
		if (scpobcparmList != null) {
			historyList.addAll(scpobcparmList);
		}
		if (visulparmList != null) {
			historyList.addAll(visulparmList);
		}
		if (recoveryList != null) {
			historyList.addAll(recoveryList);
		}
		if (scpvisualparameterList != null) {
			historyList.addAll(scpvisualparameterList);
		}
		if (scprecoveryList != null) {
			historyList.addAll(scprecoveryList);
		}
		if (driverratingcolorcodeList != null) {
			historyList.addAll(driverratingcolorcodeList);
		}
		if (manualrecoveryList != null) {
			historyList.addAll(manualrecoveryList);
		}
		if (manualsubstractionList != null) {
			historyList.addAll(manualsubstractionList);
		}
		if (visulparmList != null) {
			historyList.addAll(visulparmList);
		}
		return historyList;
	}
}
