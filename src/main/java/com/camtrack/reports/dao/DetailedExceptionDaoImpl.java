//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("detailedexceptiondao")
@PropertySources({ @PropertySource({ "classpath:mail_labels.properties" }),
		@PropertySource({ "classpath:sqlqueries.properties" }) })
public class DetailedExceptionDaoImpl implements DetailedExceptionDaoInterface {
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	String mailBody;
	String mailSubject;
	String toEmail;

	public DetailedExceptionDaoImpl() {
		this.mailBody = null;
		this.mailSubject = null;
		this.toEmail = null;
	}

	@Override
	public List checkInvalidatedNotifications(final int exceptionid) {
		final String query = this.environment.getRequiredProperty("invalidatednotifications.getdata");
		return this.jdbcTemplate.queryForList(query, new Object[] { exceptionid });
	}

	@Override
	public List checkNotifications(final int exceptionid) {
		final String query = this.environment.getRequiredProperty("validatednotifications.getdata");
		return this.jdbcTemplate.queryForList(query, new Object[] { exceptionid });
	}

	@Override
	public Map fetchClientAffiliateDetails(final int transporterid, final int vehicleid) {
		String query = "";
		if (vehicleid > 0) {
			query = "SELECT t.transporterid,t.affiliateid,ca.customerid FROM vehicle as v,transporter as t,customeraffiliate ca  WHERE v.transporterid=t.transporterid and ca.affiliateid=t.affiliateid and v.vehicleid = "
					+ vehicleid;
		} else {
			query = "SELECT t.transporterid,t.affiliateid,ca.customerid FROM transporter as t,customeraffiliate ca  WHERE ca.affiliateid=t.affiliateid and t.transporterid = "
					+ transporterid;
		}
		return this.jdbcTemplate.queryForMap(query);
	}

	@Override
	public List getAllExceptionLevels() {
		return this.jdbcTemplate
				.queryForList("SELECT exceptionlevelid,name,label FROM exceptionlevel WHERE exceptionlevelid != '0'");
	}

	@Override
	public List showDetailedExceptionReport(final int customerid, final int affiliateid, final int transporterid,
			final int vehicleid, final String startdate, final String enddate, final int exceptiontype,
			String exceptionlevel) {
		if (exceptionlevel.equals("0")) {
			exceptionlevel = "1,2,3";
		}
		final String query = "select * from sp_detailedexceptionreport_v3(" + customerid + "," + affiliateid + ","
				+ transporterid + "," + vehicleid + "," + exceptiontype + ",ARRAY[" + exceptionlevel + "],'" + startdate
				+ "','" + enddate + "')";
		final List details = this.jdbcTemplate.queryForList(query);
		return details;
	}

	@Override
	public List showDetailedInvalidatedExceptionReport(final int customerid, final int affiliateid,
			final int transporterid, final int vehicleid, final String startdate, final String enddate,
			final int exceptiontype, String exceptionlevel) {
		if (exceptionlevel.equals("0")) {
			exceptionlevel = "1,2,3";
		}
		final String query = "select * from sp_detailedinvalidatedexceptionreport(" + customerid + "," + affiliateid
				+ "," + transporterid + "," + vehicleid + "," + exceptiontype + ",ARRAY[" + exceptionlevel + "],'"
				+ startdate + "','" + enddate + "')";
		return this.jdbcTemplate.queryForList(query);
	}

	private String translateMonth(String data, final String language, final boolean showTime) {
		try {
			if (!language.equals("English")) {
				final int defaultLen = 8;
				String translate = "";
				if (data.contains("Jan")) {
					translate = this.environment.getRequiredProperty(language + "_Jan");
					data = data.replace("Jan", translate);
				} else if (data.contains("Feb")) {
					translate = this.environment.getRequiredProperty(language + "_Feb");
					data = data.replace("Feb", translate);
				} else if (data.contains("Mar")) {
					translate = this.environment.getRequiredProperty(language + "_Mar");
					data = data.replace("Mar", translate);
				} else if (data.contains("Apr")) {
					translate = this.environment.getRequiredProperty(language + "_Apr");
					data = data.replace("Apr", translate);
				} else if (data.contains("May")) {
					translate = this.environment.getRequiredProperty(language + "_May");
					data = data.replace("May", translate);
				} else if (data.contains("Jun")) {
					translate = this.environment.getRequiredProperty(language + "_Jun");
					data = data.replace("Jun", translate);
				} else if (data.contains("Jul")) {
					translate = this.environment.getRequiredProperty(language + "_Jul");
					data = data.replace("Jul", translate);
				} else if (data.contains("Aug")) {
					translate = this.environment.getRequiredProperty(language + "_Aug");
					data = data.replace("Aug", translate);
				} else if (data.contains("Sep")) {
					translate = this.environment.getRequiredProperty(language + "_Sep");
					data = data.replace("Sep", translate);
				} else if (data.contains("Oct")) {
					translate = this.environment.getRequiredProperty(language + "_Oct");
					data = data.replace("Oct", translate);
				} else if (data.contains("Nov")) {
					translate = this.environment.getRequiredProperty(language + "_Nov");
					data = data.replace("Nov", translate);
				} else if (data.contains("Dec")) {
					translate = this.environment.getRequiredProperty(language + "_Dec");
					data = data.replace("Dec", translate);
				}
				if (!showTime) {
					final int len = defaultLen + translate.length();
					System.out.println("Translate: " + translate);
					System.out.println("len: " + len);
					data = data.substring(0, len);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

}
