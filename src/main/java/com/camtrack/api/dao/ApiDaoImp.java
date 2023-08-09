//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.api.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.apache.commons.lang.ArrayUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.camtrack.config.AppConstants;
import com.camtrack.config.CommonUtil;
import com.camtrack.config.PropertyLoader;
import com.camtrack.reports.bean.WeekDaysBean;

/**
 * @Author Jinshad P.T.
 * @created on 23/01/2018
 */
@Repository("apidao")
@PropertySource(value = { "classpath:message.properties" })
@PropertySource(value = { "classpath:sqlqueries.properties" })
public class ApiDaoImp implements ApiDao {
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List accelerationExceptionsPer100kms(String affiliateid, String transportername, String vehicledesc,
			String startime, String endtime) throws Exception {
		// TODO Auto-generated method stub

		String query = "SELECT transportername,exceptionper100km FROM \"accelerationexceptionsper100kms\" where 1=1 ";

		if (affiliateid != null && affiliateid.length() > 0) {
			query += " and " + "affiliateid='" + affiliateid + "'";
		}

		if (transportername != null && transportername.length() > 0) {
			query += " and " + "transportername='" + transportername + "'";
		}

		if (vehicledesc != null && vehicledesc.length() > 0) {
			query += " and vehiclename='" + vehicledesc + "'";
		}

		if (startime != null && startime.length() > 0) {
			query += " and startdatetime>='" + startime + "'";
		}

		if (endtime != null && endtime.length() > 0) {
			query += " and enddatetime<='" + endtime + "'";
		}
		List details = jdbcTemplate.queryForList(query);
		return details;

	}

	@Override
	public List AffTransVeh(int userid) throws Exception {
		List detailsList = null;

		String userQry = "select userroleid, customerid,affiliateid from \"user\" where userid=?";
		Map userDetails = jdbcTemplate.queryForMap(userQry, new Object[] { userid });

		if (userDetails != null && userDetails.size() > 0) {

			String query;
			if ((Integer) userDetails.get("userroleid") == AppConstants.ROLE_SUPER_ADMIN) {
				query = "SELECT transporterid,transportername,affiliateid,affiliatename ,vehicleid,vehicledesc FROM \"newafftransveh\"";
			} else if ((Integer) userDetails.get("userroleid") == AppConstants.ROLE_CUSTOMER_ADMIN) {
				query = "SELECT transporterid,transportername,affiliateid,affiliatename ,vehicleid,vehicledesc FROM \"newafftransvehall\" where customerid="
						+ userDetails.get("customerid");
			} else {
				query = "SELECT transporterid,transportername,affiliateid,affiliatename ,vehicleid,vehicledesc FROM \"newafftransvehall\" where affiliateid="
						+ userDetails.get("affiliateid");
			}

			detailsList = jdbcTemplate.queryForList(query);

		}
		return detailsList;
	}

	@Override
	public List behavoiurViolationTrend(String affiliateid, String transportername, String vehicledesc, String startime,
			String endtime) throws Exception {
		String query = "SELECT starttime,Acceleration,HarshBrake,Speeding FROM \"behaviourviolationtrend\" where 1=1";
		if (affiliateid != null && affiliateid.length() > 0) {
			query += " and " + "affiliateid='" + affiliateid + "'";
		}

		if (transportername != null && transportername.length() > 0) {
			query += " and " + "transportername='" + transportername + "'";
		}

		if (vehicledesc != null && vehicledesc.length() > 0) {
			query += " and vehiclename='" + vehicledesc + "'";
		}

		if (startime != null && startime.length() > 0) {
			query += " and starttime>='" + startime + "'";
		}

		if (endtime != null && endtime.length() > 0) {
			query += " and endtime<='" + endtime + "'";
		}
		return jdbcTemplate.queryForList(query);
	}

	@Override
	public List continuousDrivingViolations(String affiliateid, String transportername, String vehicledesc,
			String startime, String endtime) throws Exception {
		String query = "SELECT transportername,alert,alarm,recording FROM \"continuousdrivingviolations\" where 1=1";

		if (affiliateid != null && affiliateid.length() > 0) {
			query += " and " + "affiliateid='" + affiliateid + "'";
		}

		if (transportername != null && transportername.length() > 0) {
			query += " and " + "transportername='" + transportername + "'";
		}

		if (vehicledesc != null && vehicledesc.length() > 0) {
			query += " and vehiclename='" + vehicledesc + "'";
		}

		if (startime != null && startime.length() > 0) {
			query += " and startdatetime>='" + startime + "'";
		}

		if (endtime != null && endtime.length() > 0) {
			query += " and enddatetime<='" + endtime + "'";
		}
		return jdbcTemplate.queryForList(query);
	}

	@Override
	public List dailyDriveViolations(String affiliateid, String transportername, String vehicledesc, String startime,
			String endtime) throws Exception {
		String query = "SELECT transportername,alert,alarm,recording FROM \"dailydriveviolations\" where 1=1";
		if (affiliateid != null && affiliateid.length() > 0) {
			query += " and " + "affiliateid='" + affiliateid + "'";
		}

		if (transportername != null && transportername.length() > 0) {
			query += " and " + "transportername='" + transportername + "'";
		}

		if (vehicledesc != null && vehicledesc.length() > 0) {
			query += " and vehiclename='" + vehicledesc + "'";
		}

		if (startime != null && startime.length() > 0) {
			query += " and startdatetime>='" + startime + "'";
		}

		if (endtime != null && endtime.length() > 0) {
			query += " and enddatetime<='" + endtime + "'";
		}
		return jdbcTemplate.queryForList(query);
	}

	@Override
	public List dailyRestViolations(String affiliateid, String transportername, String vehicledesc, String startime,
			String endtime) throws Exception {
		String query = "SELECT transportername,alert,alarm,recording FROM \"dailyrestviolations\" where 1=1";
		if (affiliateid != null && affiliateid.length() > 0) {
			query += " and " + "affiliateid='" + affiliateid + "'";
		}

		if (transportername != null && transportername.length() > 0) {
			query += " and " + "transportername='" + transportername + "'";
		}

		if (vehicledesc != null && vehicledesc.length() > 0) {
			query += " and vehiclename='" + vehicledesc + "'";
		}

		if (startime != null && startime.length() > 0) {
			query += " and startdatetime>='" + startime + "'";
		}

		if (endtime != null && endtime.length() > 0) {
			query += " and enddatetime<='" + endtime + "'";
		}
		return jdbcTemplate.queryForList(query);
	}

	@Override
	public List datewiseDriverBehaviourViolations(String clientId, String affiliateid, String transporterid,
			String vehicleid, String startime, String endtime, String[] explevel, String language) throws Exception {
		// TODO Auto-generated method stub

		List details = new ArrayList<>();

		for (String exp : explevel) {

			if (affiliateid == null && affiliateid.length() == 0) {
				affiliateid = "0";
			}

			if (transporterid == null && transporterid.length() == 0) {
				transporterid = "0";
			}

			if (vehicleid == null && vehicleid.length() == 0) {
				vehicleid = "0";
			}

			if (startime == null && startime.length() == 0) {
				startime = null;
			}

			if (endtime == null && endtime.length() == 0) {
				endtime = null;
			}

			String query = "select * from \"sp_datewise_driverbehaviourviolations\"(" + clientId + "::int4,"
					+ affiliateid + "::int4," + transporterid + "::int4," + vehicleid + "::int4,'" + (startime)
					+ "'::date,'" + (endtime) + "'::date," + exp + "::int4);";

			List temp = jdbcTemplate.queryForList(query);

			/* added by arya on 15-02-2018 */
			Iterator itr = temp.iterator();
			while (itr.hasNext()) {
				Map map = (Map) itr.next();
				String edate = map.get("edate").toString();
				String exceptiontype = map.get("exceptiontype").toString();
				// exceptiontype=PropertyLoader.getLabel(language,exceptiontype);
				int vcount = parseInt(map.get("violationscount"));
				Iterator it = details.iterator();
				while (it.hasNext()) {
					Map dMap = (Map) it.next();
					String dedate = dMap.get("edate").toString();
					String dexceptiontype = dMap.get("exceptiontype").toString();
					// dexceptiontype=PropertyLoader.getLabel(language,dexceptiontype);
					int dvcount = parseInt(dMap.get("violationscount"));
					if (dedate.equals(edate) && dexceptiontype.equals(exceptiontype)) {
						dMap.put("violationscount", vcount + dvcount);
						itr.remove();
						break;
					}
				}
			}
			/* added by arya on 15-02-2018 */
			details.addAll(temp);
		}
		/* dates betwwen end date and startdate */
		java.util.Date begin = new Date(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(startime).getTime());
		java.util.LinkedList<Long> list = new java.util.LinkedList();
		list.add(begin.getTime());

		while (begin.compareTo(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(endtime)) < 0) {

			begin = new Date(begin.getTime() + 86400000);
			list.add(begin.getTime());
		}
		/* dates betwwen end date and startdate */

		List retData = new ArrayList<>();
		Map exceptionTypeIds = new Hashtable();
		List<String> keys = new ArrayList<>();
//		for (int i = 0; i < details.size(); i++) {
//
//			String s = details.get(i).toString();
//			s = s.replace("{", "").replace("}", "");
//			String[] pairs = s.split(",");
//			Map myMap = new HashMap<>();
//			for (int k = 0; k < pairs.length; k++) {
//				String pair = pairs[k];
//				pair = pair.trim();
//				String[] keyValue = pair.split("=");
//				myMap.put(keyValue[0], keyValue[1]);
//				if (keyValue[0].equals("exceptiontype")) {
//					String key = keyValue[0];
//
//					String a = PropertyLoader.getLabel(language, keyValue[1]);
//					if (!keys.contains(a)) {
//						keys.add(a);
//					}
//				}
//			}
//			JSONObject temp = new JSONObject(details.get(i).toString()
//					.replace("=", ":"));
//			exceptionTypeIds.put(
//					PropertyLoader.getLabel(language,
//							(String) temp.get("exceptiontype")),
//					temp.get("exceptiontypeid"));
//		}

		List<Object> keys1 = (List<Object>) details.stream().map((x) -> {
			String expType = ((Map) x).get("exceptiontype").toString();
			Integer expId = (Integer) ((Map) x).get("exceptiontypeid");
			exceptionTypeIds.put(PropertyLoader.getLabel(language, expType), expId);
			if (!keys.contains(PropertyLoader.getLabel(language, expType))) {
				keys.add(PropertyLoader.getLabel(language, expType));
			}
			return PropertyLoader.getLabel(language, expType);
		}).distinct().collect(Collectors.toList());

		Map jobToAdd = null;
		for (String keytoAdd : keys) {
			jobToAdd = new HashMap();
			jobToAdd.put("key", keytoAdd);
			jobToAdd.put("total", 0.0);
			jobToAdd.put("exceptionTypeIds", exceptionTypeIds);

			for (int i = 0; i < keys.size(); i++) {

				String s = details.get(i).toString();
				s = s.replace("{", "").replace("}", "");
				String[] pairs = s.split(",");
				Map myMap = new HashMap<>();
				for (String pair2 : pairs) {
					String pair = pair2;
					pair = pair.trim();
					String[] keyValue = pair.split("=");

					String a = PropertyLoader.getLabel(language, keyValue[1]);
					myMap.put(keyValue[0], a);
				}
				JSONObject job = new JSONObject(myMap);
				String key = job.getString("exceptiontype");
				if (key.equals(keytoAdd)) {
					List arr = null;
					if (jobToAdd.containsKey("values")) {
						arr = (ArrayList) jobToAdd.get("values");
					} else {
						/* add all dates betwwen end date and startdate */
						arr = new ArrayList();
						for (int k = 0; k < list.size(); k++) {
							Long d = list.get(k);
							List values = new ArrayList();
							values.add(new Timestamp(d));
							values.add("0.0");
							arr.add(k, values);
						}
						jobToAdd.put("values", arr);
						/* add all dates betwwen end date and startdate */
					}

					List values = new ArrayList();
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Date date = formatter.parse(job.getString("edate"));
					/* removing duplicate date which has value 0 */
					for (int k = 0; k < arr.size(); k++) {
						List l = (ArrayList) arr.get(k);
						Timestamp d = (Timestamp) l.get(0);
						if (d.compareTo(new Timestamp(date.getTime())) == 0) {
							arr.remove(k);
							break;
						}
					}
					/* removing duplicate date which has value 0 */

					values.add(new Timestamp(date.getTime()));
					values.add(parseDouble(job.get("violationscount")));

					jobToAdd.put("total", (Double) jobToAdd.get("total") + parseDouble(job.get("violationscount")));
					arr.add(values);
					Comparator<ArrayList<Object>> myComparator = new Comparator<ArrayList<Object>>() {
						@Override
						public int compare(ArrayList<Object> o1, ArrayList<Object> o2) {
							return ((Timestamp) o1.get(0)).compareTo((Timestamp) o2.get(0));
						}
					};
					Collections.sort(arr, myComparator);
					jobToAdd.put("values", arr);
				}
			}
			retData.add(jobToAdd);
		}

		return retData;

	}

	@Override
	public List datewiseDriverExceptionPer100Hrs(String clientid, String affiliateid, String transporterid,
			String vehicleid, String startime, String endtime, String[] explevel, String language) throws Exception {

		List details = new ArrayList<>();

		for (String exp : explevel) {

			if (affiliateid == null && affiliateid.length() == 0) {
				affiliateid = "0";
			}

			if (transporterid == null && transporterid.length() == 0) {
				transporterid = "0";
			}

			if (vehicleid == null && vehicleid.length() == 0) {
				vehicleid = "0";
			}

			if (startime == null && startime.length() == 0) {
				startime = null;
			}

			if (endtime == null && endtime.length() == 0) {
				endtime = null;
			}

			String query = "select * from \"sp_datewise_driverexceptionsper100hrs_new\"(" + clientid + "::int4,"
					+ affiliateid + "::int4," + transporterid + "::int4," + vehicleid + "::int4,'" + (startime)
					+ "'::date,'" + (endtime) + "'::date," + exp + "::int4);";

			List temp = jdbcTemplate.queryForList(query);

			/* added by arya on 15-02-2018 */
			Iterator itr = temp.iterator();
			while (itr.hasNext()) {
				Map map = (Map) itr.next();
				String edate = map.get("edate").toString();
				String exceptiontype = map.get("exceptiontype").toString();
				double vcount = parseDouble(map.get("driverexceptionper100hrs"));
				Iterator it = details.iterator();
				while (it.hasNext()) {
					Map dMap = (Map) it.next();
					String dedate = dMap.get("edate").toString();
					String dexceptiontype = dMap.get("exceptiontype").toString();
					double dvcount = parseDouble(dMap.get("driverexceptionper100hrs"));
					if (dedate.equals(edate) && dexceptiontype.equals(exceptiontype)) {
						dMap.put("driverexceptionper100hrs", vcount + dvcount);
						itr.remove();
						break;
					}
				}
			}
			/* added by arya on 15-02-2018 */

			details.addAll(temp);
		}
		/* dates between startdate and end date */
		java.util.Date begin = new Date(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(startime).getTime());
		java.util.LinkedList<Long> list = new java.util.LinkedList();
		list.add(begin.getTime());

		while (begin.compareTo(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(endtime)) < 0) {

			begin = new Date(begin.getTime() + 86400000);
			list.add(begin.getTime());
		}

		/* dates between startdate and end date */

		List retData = new ArrayList<>();
		Map exceptionTypeIds = new Hashtable();
		List<String> keys = new ArrayList<>();
		for (Object detail : details) {

			String s = detail.toString();
			s = s.replace("{", "").replace("}", "");
			String[] pairs = s.split(",");
			Map myMap = new HashMap<>();
			for (String pair2 : pairs) {
				String pair = pair2;
				pair = pair.trim();
				String[] keyValue = pair.split("=");
				myMap.put(keyValue[0], keyValue[1]);
				if (keyValue[0].equals("exceptiontype")) {
					String key = keyValue[0];

					String a = PropertyLoader.getLabel(language, keyValue[1]);
					if (!keys.contains(a)) {
						keys.add(a);
					}
				}
			}
			JSONObject temp = new JSONObject(detail.toString().replace("=", ":"));
			exceptionTypeIds.put(PropertyLoader.getLabel(language, (String) temp.get("exceptiontype")),
					temp.get("exceptiontypeid"));
		}
		exceptionTypeIds.put(PropertyLoader.getLabel(language, "menu_weekly_rest_time"), 8);
		exceptionTypeIds.put(PropertyLoader.getLabel(language, "menu_weekly_driving"), 9);

		Map jobToAdd = null;
		jobToAdd = new HashMap();
		jobToAdd.put("key", PropertyLoader.getLabel(language, "menu_weekly_driving"));
		jobToAdd.put("total", 0.0);
		jobToAdd.put("exceptionTypeIds", exceptionTypeIds);
		ArrayUtils.reverse(explevel);
		String exceptionlevels = String.join(",", explevel);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List arr = new ArrayList();

		/*
		 * values.add(PropertyLoader.getLabel(language, "menu_weekly_driving"));
		 */
		List weeklyList = getDateWiseWeeklyDrive100hrs(Integer.parseInt(clientid), Integer.parseInt(affiliateid),
				Integer.parseInt(transporterid), Integer.parseInt(vehicleid), exceptionlevels, startime, endtime,
				language);
		double exceptionvalue = 0.0;
		for (int k = 0; k < list.size(); k++) {
			Long d = list.get(k);
			List values1 = new ArrayList();
			values1.add(new Timestamp(d));
			values1.add("0.0");
			arr.add(k, values1);
		}
		for (int q = 0; q < weeklyList.size(); q++) {
			List values = new ArrayList();
			Map temp = (Map) weeklyList.get(q);
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formatter.parse(temp.get("edate").toString());
			/* remove duplicate date with value 0 */
			for (int k = 0; k < arr.size(); k++) {
				List l = (ArrayList) arr.get(k);
				Timestamp d = (Timestamp) l.get(0);
				if (d.compareTo(new Timestamp(date.getTime())) == 0) {
					arr.remove(k);
					break;
				}
			}
			exceptionvalue += Double.parseDouble(temp.get("exceptionvalue").toString());
			Date parsedDate = dateFormat.parse(temp.get("edate").toString());
			Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			values.add(timestamp);
			values.add(Double.parseDouble(temp.get("exceptionvalue").toString()));
			arr.add(values);

		}
		jobToAdd.put("total", (Double) jobToAdd.get("total") + exceptionvalue);

		if (!weeklyList.isEmpty()) {
			Comparator<ArrayList<Object>> myComparator = new Comparator<ArrayList<Object>>() {
				@Override
				public int compare(ArrayList<Object> o1, ArrayList<Object> o2) {
					// System.out.println(o1.get(0) + "," + o2.get(0));
					return ((Timestamp) o1.get(0)).compareTo((Timestamp) o2.get(0));
				}
			};
			Collections.sort(arr, myComparator);
		}

		// System.out.println("values->"+values.toString());
		jobToAdd.put("values", arr);
		retData.add(jobToAdd);

		/* WRT */
		jobToAdd = new HashMap();
		jobToAdd.put("key", PropertyLoader.getLabel(language, "menu_weekly_rest_time"));
		jobToAdd.put("total", 0.0);
		jobToAdd.put("exceptionTypeIds", exceptionTypeIds);

		arr = new ArrayList();

		List weeklyRestList = getDateWiseWeeklyRest100hrs(Integer.parseInt(clientid), Integer.parseInt(affiliateid),
				Integer.parseInt(transporterid), Integer.parseInt(vehicleid), exceptionlevels, startime, endtime,
				language);
		exceptionvalue = 0.0;
		for (int k = 0; k < list.size(); k++) {
			Long d = list.get(k);
			List values1 = new ArrayList();
			values1.add(new Timestamp(d));
			values1.add("0.0");
			arr.add(k, values1);
		}
		for (int q = 0; q < weeklyRestList.size(); q++) {
			List values = new ArrayList();
			Map temp = (Map) weeklyRestList.get(q);
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formatter.parse(temp.get("edate").toString());
			/* remove duplicate date with value 0 */
			for (int k = 0; k < arr.size(); k++) {
				List l = (ArrayList) arr.get(k);
				Timestamp d = (Timestamp) l.get(0);
				if (d.compareTo(new Timestamp(date.getTime())) == 0) {
					arr.remove(k);
					break;
				}
			}
			exceptionvalue += Double.parseDouble(temp.get("exceptionvalue").toString());
			Date parsedDate = dateFormat.parse(temp.get("edate").toString());
			Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			values.add(timestamp);
			values.add(Double.parseDouble(temp.get("exceptionvalue").toString()));
			arr.add(values);
		}
		jobToAdd.put("total", (Double) jobToAdd.get("total") + exceptionvalue);

		// jobToAdd.put("values", arr);
		if (!weeklyRestList.isEmpty()) {
			Comparator<ArrayList<Object>> myComparator = new Comparator<ArrayList<Object>>() {
				@Override
				public int compare(ArrayList<Object> o1, ArrayList<Object> o2) {
					// System.out.println(o1.get(0) + "," + o2.get(0));
					return ((Timestamp) o1.get(0)).compareTo((Timestamp) o2.get(0));
				}
			};

			Collections.sort(arr, myComparator);
		}

		jobToAdd.put("values", arr);
		retData.add(jobToAdd);

		// Map jobToAdd = null;
		for (String keytoAdd : keys) {
			jobToAdd = new HashMap();
			jobToAdd.put("key", keytoAdd);
			jobToAdd.put("total", 0.0);
			jobToAdd.put("exceptionTypeIds", exceptionTypeIds);

			for (int i = 0; i < details.size(); i++) {

				String s = details.get(i).toString();
				s = s.replace("{", "").replace("}", "");
				String[] pairs = s.split(",");
				Map myMap = new HashMap<>();
				for (String pair2 : pairs) {
					String pair = pair2;
					pair = pair.trim();
					String[] keyValue = pair.split("=");

					String a = PropertyLoader.getLabel(language, keyValue[1]);
					myMap.put(keyValue[0], a);
				}
				JSONObject job = new JSONObject(myMap);
				String key = job.getString("exceptiontype");
				if (key.equals(keytoAdd)) {
					arr = null;
					if (jobToAdd.containsKey("values")) {
						arr = (ArrayList) jobToAdd.get("values");
					} else {
						/* add all dates between startdate and end date */
						arr = new ArrayList();
						for (int k = 0; k < list.size(); k++) {
							Long d = list.get(k);
							List values1 = new ArrayList();
							values1.add(new Timestamp(d));
							values1.add("0.0");
							arr.add(k, values1);
						}
						jobToAdd.put("values", arr);
						/* add all dates between startdate and end date */
					}

					List values = new ArrayList();
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Date date = formatter.parse(job.getString("edate"));

					/* remove duplicate date with value 0 */
					for (int k = 0; k < arr.size(); k++) {
						List l = (ArrayList) arr.get(k);
						Timestamp d = (Timestamp) l.get(0);
						if (d.compareTo(new Timestamp(date.getTime())) == 0) {
							arr.remove(k);
							break;
						}
					}
					/* remove duplicate date with value 0 */
					values.add(new Timestamp(date.getTime()));
					values.add(parseDouble(job.get("driverexceptionper100hrs")));

					jobToAdd.put("total",
							(Double) jobToAdd.get("total") + parseDouble(job.get("driverexceptionper100hrs")));
					arr.add(values);
					Comparator<ArrayList<Object>> myComparator = new Comparator<ArrayList<Object>>() {
						@Override
						public int compare(ArrayList<Object> o1, ArrayList<Object> o2) {
							return ((Timestamp) o1.get(0)).compareTo((Timestamp) o2.get(0));
						}
					};
					Collections.sort(arr, myComparator);
					jobToAdd.put("values", arr);
				}
			}
			retData.add(jobToAdd);
		}

		return retData;

	}

	@Override
	public List datewiseDriverExceptionPer100Km(String clientid, String affiliateid, String transporterid,
			String vehicleid, String startime, String endtime, String[] explevel, String language) throws Exception {

		List details = new ArrayList<>();

		double recordingWeight = 1;
		double alertWeight = 1;
		double alarmWeight = 1;
		String exceptionlevels = String.join(",", explevel);

		if (explevel.length != 0) {
			ArrayUtils.reverse(explevel);
			if (explevel[0].equals("0")) {
				explevel = new String[3];
				explevel[0] = "3";
				explevel[1] = "2";
				explevel[2] = "1";
			}

			exceptionlevels = String.join(",", explevel);

			if (exceptionlevels.contains("1") && exceptionlevels.contains("2") && exceptionlevels.contains("3")) {
				recordingWeight = 0.15;
				alertWeight = 0.28;
				alarmWeight = 0.57;
			} else if (exceptionlevels.contains("2") && exceptionlevels.contains("3")) {
				recordingWeight = 0;
				alertWeight = 0.33;
				alarmWeight = 0.67;
			} else if (exceptionlevels.contains("1") && exceptionlevels.contains("3")) {
				recordingWeight = 0.21;
				alertWeight = 0;
				alarmWeight = 0.79;
			} else if (exceptionlevels.contains("1") && exceptionlevels.contains("2")) {
				recordingWeight = 0.35;
				alertWeight = 0.65;
				alarmWeight = 0;
			} else if (exceptionlevels.contains("1")) {
				recordingWeight = 1.0;
				alertWeight = 0.0;
				alarmWeight = 0.0;
			} else if (exceptionlevels.contains("2")) {
				recordingWeight = 0.0;
				alertWeight = 1.0;
				alarmWeight = 0.0;
			} else if (exceptionlevels.contains("3")) {
				recordingWeight = 0.0;
				alertWeight = 0.0;
				alarmWeight = 1.0;
			}
		}

		// for (String exp : explevel) {

		if (affiliateid == null && affiliateid.length() == 0) {
			affiliateid = "0";
		}

		if (transporterid == null && transporterid.length() == 0) {
			transporterid = "0";
		}

		if (vehicleid == null && vehicleid.length() == 0) {
			vehicleid = "0";
		}

		if (startime == null && startime.length() == 0) {
			startime = null;
		}

		if (endtime == null && endtime.length() == 0) {
			endtime = null;
		}

		String query = "select * from \"sp_datewise_driverexceptionsper100kms_weightage\"(" + clientid + "::int4,"
				+ affiliateid + "::int4," + transporterid + "::int4," + vehicleid + "::int4,'" + (startime)
				+ "'::date,'" + (endtime) + "'::date,array[" + exceptionlevels + "]," + recordingWeight + ","
				+ alertWeight + "," + alarmWeight + ");";

		List temp = jdbcTemplate.queryForList(query);

		/* added by arya on 15-02-2018 */
		/*
		 * Iterator itr = temp.iterator(); while (itr.hasNext()) { Map map = (Map)
		 * itr.next(); String edate = map.get("edate").toString(); String exceptiontype
		 * = map.get("exceptiontype").toString(); double vcount = 0; if
		 * (map.get("driverexceptionper100kms") != null) vcount =
		 * parseDouble(map.get("driverexceptionper100kms")); Iterator it =
		 * details.iterator(); while (it.hasNext()) { Map dMap = (Map) it.next(); String
		 * dedate = dMap.get("edate").toString(); String dexceptiontype =
		 * dMap.get("exceptiontype") .toString(); double dvcount = 0; if
		 * (dMap.get("driverexceptionper100kms") != null) { dvcount = parseDouble(dMap
		 * .get("driverexceptionper100kms")); } if (dedate.equals(edate) &&
		 * dexceptiontype.equals(exceptiontype)) { dMap.put("driverexceptionper100kms",
		 * vcount + dvcount); itr.remove(); break; } } }
		 */
		/* added by arya on 15-02-2018 */

		details.addAll(temp);
		// }

		/* dates between startdate and end date */
		java.util.Date begin = new Date(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(startime).getTime());
		java.util.LinkedList<Long> list = new java.util.LinkedList();
		list.add(begin.getTime());

		while (begin.compareTo(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(endtime)) < 0) {

			begin = new Date(begin.getTime() + 86400000);
			list.add(begin.getTime());
		}
		/* dates between startdate and end date */

		List retData = new ArrayList<>();
		Map exceptionTypeIds = new Hashtable();
		List<String> keys = new ArrayList<>();
		for (Object detail : details) {

			String s = detail.toString();
			s = s.replace("{", "").replace("}", "");
			String[] pairs = s.split(",");
			Map myMap = new HashMap<>();
			for (String pair2 : pairs) {
				String pair = pair2;
				pair = pair.trim();
				String[] keyValue = pair.split("=");
				myMap.put(keyValue[0], keyValue[1]);
				if (keyValue[0].equals("exceptiontype")) {
					String key = keyValue[0];

					String a = PropertyLoader.getLabel(language, keyValue[1]);
					if (!keys.contains(a)) {
						keys.add(a);
					}
				}
			}
			JSONObject temp1 = new JSONObject(detail.toString().replace("=", ":"));
			exceptionTypeIds.put(PropertyLoader.getLabel(language, (String) temp1.get("exceptiontype")),
					temp1.get("exceptiontypeid"));
		}

		Map jobToAdd = null;
		for (String keytoAdd : keys) {
			jobToAdd = new HashMap();
			jobToAdd.put("key", keytoAdd);
			jobToAdd.put("total", 0.0);
			jobToAdd.put("exceptionTypeIds", exceptionTypeIds);

			for (int i = 0; i < details.size(); i++) {

				String s = details.get(i).toString();
				s = s.replace("{", "").replace("}", "");
				String[] pairs = s.split(",");
				Map myMap = new HashMap<>();
				for (String pair2 : pairs) {
					String pair = pair2;
					pair = pair.trim();
					String[] keyValue = pair.split("=");

					String a = PropertyLoader.getLabel(language, keyValue[1]);
					myMap.put(keyValue[0], a);
				}
				JSONObject job = new JSONObject(myMap);
				String key = job.getString("exceptiontype");
				if (key.equals(keytoAdd)) {
					List arr = null;
					if (jobToAdd.containsKey("values")) {
						arr = (ArrayList) jobToAdd.get("values");
					} else {
						/* add all dates between startdate and end date */
						arr = new ArrayList();
						for (int k = 0; k < list.size(); k++) {
							Long d = list.get(k);
							List values = new ArrayList();
							values.add(new Timestamp(d));
							values.add("0.0");
							arr.add(k, values);
						}
						jobToAdd.put("values", arr);
						/* add all dates between startdate and end date */
					}

					List values = new ArrayList();
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Date date = formatter.parse(job.getString("edate"));
					/* remove duplicate date with value 0 */
					for (int k = 0; k < arr.size(); k++) {
						List l = (ArrayList) arr.get(k);
						Timestamp d = (Timestamp) l.get(0);
						if (d.compareTo(new Timestamp(date.getTime())) == 0) {
							arr.remove(k);
							break;
						}
					}
					/* remove duplicate date with value 0 */
					values.add(new Timestamp(date.getTime()));
					values.add(parseDouble(job.get("driverexceptionper100kms")));

					jobToAdd.put("total",
							(Double) jobToAdd.get("total") + parseDouble(job.get("driverexceptionper100kms")));
					arr.add(values);
					Comparator<ArrayList<Object>> myComparator = new Comparator<ArrayList<Object>>() {
						@Override
						public int compare(ArrayList<Object> o1, ArrayList<Object> o2) {
							return ((Timestamp) o1.get(0)).compareTo((Timestamp) o2.get(0));
						}
					};
					Collections.sort(arr, myComparator);
					jobToAdd.put("values", arr);
				}
			}
			retData.add(jobToAdd);
		}

		return retData;

	}

	@Override
	public List datewiseDriverTimeViolations(String clientid, String affiliateid, String transporterid,
			String vehicleid, String startime, String endtime, String[] explevel, String language) throws Exception {

		List details = new ArrayList<>();

		for (String exp : explevel) {

			if (affiliateid == null && affiliateid.length() == 0) {
				affiliateid = "0";
			}

			if (transporterid == null && transporterid.length() == 0) {
				transporterid = "0";
			}

			if (vehicleid == null && vehicleid.length() == 0) {
				vehicleid = "0";
			}

			if (startime == null && startime.length() == 0) {
				startime = null;
			}

			if (endtime == null && endtime.length() == 0) {
				endtime = null;
			}

			String query = "select * from \"sp_datewise_drivetimeviolations\"(" + clientid + "::int4," + affiliateid
					+ "::int4," + transporterid + "::int4," + vehicleid + "::int4,'" + (startime) + "'::date,'"
					+ (endtime) + "'::date," + exp + "::int4);";

			List temp = jdbcTemplate.queryForList(query);

			/* added by arya on 15-02-2018 */
			Iterator itr = temp.iterator();
			while (itr.hasNext()) {
				Map map = (Map) itr.next();
				String edate = map.get("edate").toString();
				String exceptiontype = map.get("exceptiontype").toString();
				int vcount = parseInt(map.get("violationscount"));
				Iterator it = details.iterator();
				while (it.hasNext()) {
					Map dMap = (Map) it.next();
					String dedate = dMap.get("edate").toString();
					String dexceptiontype = dMap.get("exceptiontype").toString();
					int dvcount = parseInt(dMap.get("violationscount"));
					if (dedate.equals(edate) && dexceptiontype.equals(exceptiontype)) {
						dMap.put("violationscount", vcount + dvcount);
						itr.remove();
						break;
					}
				}
			}
			/* added by arya on 15-02-2018 */

			details.addAll(temp);
		}
		/* dates betwwen end date and startdate */
		java.util.Date begin = new Date(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(startime).getTime());
		java.util.LinkedList<Long> list = new java.util.LinkedList();
		list.add(begin.getTime());

		while (begin.compareTo(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(endtime)) < 0) {

			begin = new Date(begin.getTime() + 86400000);
			list.add(begin.getTime());
		}
		/* dates betwwen end date and startdate */

		List retData = new ArrayList<>();
		Map exceptionTypeIds = new Hashtable();
		List<String> keys = new ArrayList<>();
		for (Object detail : details) {

			String s = detail.toString();
			s = s.replace("{", "").replace("}", "");
			String[] pairs = s.split(",");
			Map myMap = new HashMap<>();
			for (String pair2 : pairs) {
				String pair = pair2;
				pair = pair.trim();
				String[] keyValue = pair.split("=");
				myMap.put(keyValue[0], keyValue[1]);
				if (keyValue[0].equals("exceptiontype")) {
					String key = keyValue[0];

					String a = PropertyLoader.getLabel(language, keyValue[1]);
					if (!keys.contains(a)) {
						keys.add(a);
					}
				}
			}
			JSONObject temp = new JSONObject(detail.toString().replace("=", ":"));
			exceptionTypeIds.put(PropertyLoader.getLabel(language, (String) temp.get("exceptiontype")),
					temp.get("exceptiontypeid"));
		}

		exceptionTypeIds.put(PropertyLoader.getLabel(language, "menu_weekly_rest_time"), 8);
		exceptionTypeIds.put(PropertyLoader.getLabel(language, "menu_weekly_driving"), 9);

		Map jobToAdd = null;
		jobToAdd = new HashMap();
		jobToAdd.put("key", PropertyLoader.getLabel(language, "menu_weekly_driving"));
		jobToAdd.put("total", 0.0);
		jobToAdd.put("exceptionTypeIds", exceptionTypeIds);
		ArrayUtils.reverse(explevel);
		String exceptionlevels = String.join(",", explevel);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List arr = new ArrayList();

		/*
		 * values.add(PropertyLoader.getLabel(language, "menu_weekly_driving"));
		 */
		List weeklyList = null;
		if (affiliateid.equals("0"))
			weeklyList = getWeeklyDriveAffDriver(Integer.parseInt(clientid), Integer.parseInt(affiliateid),
					Integer.parseInt(transporterid), Integer.parseInt(vehicleid), exceptionlevels, startime, endtime,
					language);
		else if (transporterid.equals("0"))
			weeklyList = getWeeklyDriveTransporter(Integer.parseInt(clientid), Integer.parseInt(affiliateid),
					Integer.parseInt(transporterid), Integer.parseInt(vehicleid), exceptionlevels, startime, endtime,
					language);
		else
			weeklyList = getWeeklyDrive(Integer.parseInt(clientid), Integer.parseInt(affiliateid),
					Integer.parseInt(transporterid), Integer.parseInt(vehicleid), exceptionlevels, startime, endtime,
					language);
		Double alarmCount = 0.0, alertcount = 0.0, recordingcount = 0.0;
		for (int k = 0; k < list.size(); k++) {
			Long d = list.get(k);
			List values1 = new ArrayList();
			values1.add(new Timestamp(d));
			values1.add("0.0");
			arr.add(k, values1);
		}
		for (int q = 0; q < weeklyList.size(); q++) {
			List values = new ArrayList();
			Map temp = (Map) weeklyList.get(q);
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formatter.parse(temp.get("edate").toString());
			/* remove duplicate date with value 0 */
			for (int k = 0; k < arr.size(); k++) {
				List l = (ArrayList) arr.get(k);
				Timestamp d = (Timestamp) l.get(0);
				if (d.compareTo(new Timestamp(date.getTime())) == 0) {
					arr.remove(k);
					break;
				}
			}
			alarmCount += Integer.parseInt(temp.get("alarmcount").toString());
			alertcount += Integer.parseInt(temp.get("alertcount").toString());
			recordingcount += Integer.parseInt(temp.get("recordingcount").toString());
			Date parsedDate = dateFormat.parse(temp.get("edate").toString());
			Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			values.add(timestamp);
			values.add(Integer.parseInt(temp.get("alarmcount").toString())
					+ Integer.parseInt(temp.get("recordingcount").toString())
					+ Integer.parseInt(temp.get("alertcount").toString()));
			arr.add(values);

		}
		jobToAdd.put("total", (Double) jobToAdd.get("total") + alarmCount + alertcount + recordingcount);

		if (!weeklyList.isEmpty()) {
			Comparator<ArrayList<Object>> myComparator = new Comparator<ArrayList<Object>>() {
				@Override
				public int compare(ArrayList<Object> o1, ArrayList<Object> o2) {
					// System.out.println(o1.get(0) + "," + o2.get(0));
					return ((Timestamp) o1.get(0)).compareTo((Timestamp) o2.get(0));
				}
			};
			Collections.sort(arr, myComparator);
		}

		// System.out.println("values->"+values.toString());
		jobToAdd.put("values", arr);
		retData.add(jobToAdd);

		/* WRT */
		jobToAdd = new HashMap();
		jobToAdd.put("key", PropertyLoader.getLabel(language, "menu_weekly_rest_time"));
		jobToAdd.put("total", 0.0);
		jobToAdd.put("exceptionTypeIds", exceptionTypeIds);

		arr = new ArrayList();

		List weeklyRestList = null;
		if (affiliateid.equals("0"))
			weeklyRestList = getWeeklyRestAffDriver(Integer.parseInt(clientid), Integer.parseInt(affiliateid),
					Integer.parseInt(transporterid), Integer.parseInt(vehicleid), exceptionlevels, startime, endtime,
					language);
		else if (transporterid.equals("0"))
			weeklyRestList = getWeeklyRestTransporter(Integer.parseInt(clientid), Integer.parseInt(affiliateid),
					Integer.parseInt(transporterid), Integer.parseInt(vehicleid), exceptionlevels, startime, endtime,
					language);
		else
			weeklyRestList = getWeeklyRest(Integer.parseInt(clientid), Integer.parseInt(affiliateid),
					Integer.parseInt(transporterid), Integer.parseInt(vehicleid), exceptionlevels, startime, endtime,
					language);
		alarmCount = 0.0;
		alertcount = 0.0;
		recordingcount = 0.0;
		for (int k = 0; k < list.size(); k++) {
			Long d = list.get(k);
			List values1 = new ArrayList();
			values1.add(new Timestamp(d));
			values1.add("0.0");
			arr.add(k, values1);
		}
		for (int q = 0; q < weeklyRestList.size(); q++) {
			List values = new ArrayList();
			Map temp = (Map) weeklyRestList.get(q);
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formatter.parse(temp.get("edate").toString());
			/* remove duplicate date with value 0 */
			for (int k = 0; k < arr.size(); k++) {
				List l = (ArrayList) arr.get(k);
				Timestamp d = (Timestamp) l.get(0);
				if (d.compareTo(new Timestamp(date.getTime())) == 0) {
					arr.remove(k);
					break;
				}
			}
			alarmCount += Integer.parseInt(temp.get("alarmcount").toString());
			alertcount += Integer.parseInt(temp.get("alertcount").toString());
			recordingcount += Integer.parseInt(temp.get("recordingcount").toString());
			Date parsedDate = dateFormat.parse(temp.get("edate").toString());
			Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			values.add(timestamp);
			values.add(Integer.parseInt(temp.get("alarmcount").toString())
					+ Integer.parseInt(temp.get("recordingcount").toString())
					+ Integer.parseInt(temp.get("alertcount").toString()));
			arr.add(values);
		}
		jobToAdd.put("total", (Double) jobToAdd.get("total") + alarmCount + alertcount + recordingcount);

		// jobToAdd.put("values", arr);
		if (!weeklyRestList.isEmpty()) {
			Comparator<ArrayList<Object>> myComparator = new Comparator<ArrayList<Object>>() {
				@Override
				public int compare(ArrayList<Object> o1, ArrayList<Object> o2) {
					// System.out.println(o1.get(0) + "," + o2.get(0));
					return ((Timestamp) o1.get(0)).compareTo((Timestamp) o2.get(0));
				}
			};

			Collections.sort(arr, myComparator);
		}

		jobToAdd.put("values", arr);
		retData.add(jobToAdd);

		for (String keytoAdd : keys) {
			jobToAdd = new HashMap();
			jobToAdd.put("key", keytoAdd);
			jobToAdd.put("total", 0.0);
			jobToAdd.put("exceptionTypeIds", exceptionTypeIds);

			for (int i = 0; i < details.size(); i++) {

				String s = details.get(i).toString();
				s = s.replace("{", "").replace("}", "");
				String[] pairs = s.split(",");
				Map myMap = new HashMap<>();
				for (String pair2 : pairs) {
					String pair = pair2;
					pair = pair.trim();
					String[] keyValue = pair.split("=");

					String a = PropertyLoader.getLabel(language, keyValue[1]);
					myMap.put(keyValue[0], a);
				}
				JSONObject job = new JSONObject(myMap);
				String key = job.getString("exceptiontype");
				if (key.equals(keytoAdd)) {
					arr = null;
					if (jobToAdd.containsKey("values")) {
						arr = (ArrayList) jobToAdd.get("values");
					} else {
						/* add all dates betwwen end date and startdate */
						arr = new ArrayList();
						for (int k = 0; k < list.size(); k++) {
							Long d = list.get(k);
							List values1 = new ArrayList();
							values1.add(new Timestamp(d));
							values1.add("0.0");
							arr.add(k, values1);
						}
						jobToAdd.put("values", arr);
						/* add all dates betwwen end date and startdate */
					}

					List values1 = new ArrayList();
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Date date = formatter.parse(job.getString("edate"));
					/* remove duplicate date with value 0 */
					for (int k = 0; k < arr.size(); k++) {
						List l = (ArrayList) arr.get(k);
						Timestamp d = (Timestamp) l.get(0);
						if (d.compareTo(new Timestamp(date.getTime())) == 0) {
							arr.remove(k);
							break;
						}
					}
					/* remove duplicate date with value 0 */
					values1.add(new Timestamp(date.getTime()));
					values1.add(parseDouble(job.get("violationscount")));

					jobToAdd.put("total", (Double) jobToAdd.get("total") + parseDouble(job.get("violationscount")));
					arr.add(values1);
					Comparator<ArrayList<Object>> myComparator = new Comparator<ArrayList<Object>>() {
						@Override
						public int compare(ArrayList<Object> o1, ArrayList<Object> o2) {
							// System.out.println(o1.get(0) + "," + o2.get(0));
							return ((Timestamp) o1.get(0)).compareTo((Timestamp) o2.get(0));
						}
					};
					Collections.sort(arr, myComparator);
					jobToAdd.put("values", arr);
				}
			}
			retData.add(jobToAdd);
		}
		// System.out.println(retData.toString());
		return retData;

	}

	@Override
	public List driverBehaviourViolations(String clientid, String affiliateid, String transporterid, String vehicleid,
			String startime, String endtime, String[] explevel, String language) throws Exception {
		// TODO Auto-generated method stub

		List details = new ArrayList<Map<String, Object>>();
		ArrayUtils.reverse(explevel);

		for (String exp : explevel) {

			if (affiliateid == null && affiliateid.length() == 0) {
				affiliateid = "0";
			}

			if (transporterid == null && transporterid.length() == 0) {
				transporterid = "0";
			}

			if (vehicleid == null && vehicleid.length() == 0) {
				vehicleid = "0";
			}

			if (startime == null && startime.length() == 0) {
				startime = null;
			}

			if (endtime == null && endtime.length() == 0) {
				endtime = null;
			}

			String query = "select * from \"sp1_driverbehaviourviolations\"(" + clientid + "::int4," + affiliateid
					+ "::int4," + transporterid + "::int4," + vehicleid + "::int4,'" + (startime) + "','" + (endtime)
					+ "'," + exp + "::int4)";
			if (!exp.equals("0"))
				query += " where levelid=" + exp + "::int4";

			List temp = jdbcTemplate.queryForList(query);

			details.addAll(temp);
		}

		List retData = new ArrayList<>();

		List<String> keys = new ArrayList<>();
		Map exceptionTypeIds = new Hashtable();
		Map exceptionLevelIds = new Hashtable();
		for (Object detail : details) {

			String s = detail.toString();
			s = s.replace("{", "").replace("}", "");
			String[] pairs = s.split(",");
			Map myMap = new HashMap<>();
			for (String pair2 : pairs) {
				String pair = pair2;
				// pair = pair.replace(" ", "");
				pair = pair.trim();
				String[] keyValue = pair.split("=");
				myMap.put(keyValue[0], keyValue[1]);
				if (keyValue[0].equals("exceptionlevel")) {
					String key = keyValue[0];

					String a = PropertyLoader.getLabel(language, keyValue[1]);
					if (!keys.contains(a)) {
						keys.add(a);
					}
				}

			}

			JSONObject temp = new JSONObject(detail.toString().replace("=", ":"));
			exceptionLevelIds.put(PropertyLoader.getLabel(language, (String) temp.get("exceptionlevel")),
					temp.get("levelid"));
			exceptionTypeIds.put(PropertyLoader.getLabel(language, (String) temp.get("exceptiontype")),
					temp.get("exceptiontypeid"));
		}

		Map jobToAdd = null;
		for (String keytoAdd : keys) {
			jobToAdd = new HashMap();
			jobToAdd.put("key", keytoAdd);
			jobToAdd.put("total", 0);
			jobToAdd.put("exceptionLevelIds", exceptionLevelIds);
			jobToAdd.put("exceptionTypeIds", exceptionTypeIds);

			for (int i = 0; i < details.size(); i++) {

				String s = details.get(i).toString();
				JSONObject temp = new JSONObject(s.replace("=", ":"));
				s = s.replace("{", "").replace("}", "");
				String[] pairs = s.split(",");
				Map myMap = new HashMap<>();
				for (String pair2 : pairs) {
					String pair = pair2;
					pair = pair.trim();
					String[] keyValue = pair.split("=");

					String a = PropertyLoader.getLabel(language, keyValue[1]);

					myMap.put(keyValue[0], a);
				}
				JSONObject job = new JSONObject(myMap);
				String key = job.getString("exceptionlevel");
				if (key.equals(keytoAdd)) {
					List arr = null;
					if (jobToAdd.containsKey("values")) {
						arr = (ArrayList) jobToAdd.get("values");
					} else {
						arr = new ArrayList();
					}

					/*
					 * Map values=new HashMap();
					 *
					 * values.put("label", job.getString("exceptiontype")); values.put("value",
					 * job.get("violationscount"));
					 */

					List values = new ArrayList();
					values.add(job.getString("exceptiontype"));
					values.add(parseInt(job.get("violationscount")));

					jobToAdd.put("total", (Integer) jobToAdd.get("total") + parseInt(job.get("violationscount")));
					arr.add(values);
					jobToAdd.put("values", arr);
				}
			}
			retData.add(jobToAdd);
		}

		return retData;

	}

	@Override
	public List driverExceptionsPer100hrs(String clientid, String affiliateid, String transporterid, String vehicleid,
			String startime, String endtime, String[] explevel, String language) throws Exception {
		List details = new ArrayList<>();

		// long d1=System.currentTimeMillis();

		for (String exp : explevel) {

			if (affiliateid == null && affiliateid.length() == 0) {
				affiliateid = "0";
			}

			if (transporterid == null && transporterid.length() == 0) {
				transporterid = "0";
			}

			if (vehicleid == null && vehicleid.length() == 0) {
				vehicleid = "0";
			}

			if (startime == null && startime.length() == 0) {
				startime = null;
			}

			if (endtime == null && endtime.length() == 0) {
				endtime = null;
			}

			String query = "select * from \"sp_driverexceptionsper100hrs_new\"(" + clientid + "::int4," + affiliateid
					+ "::int4," + transporterid + "::int4," + vehicleid + "::int4,'" + (startime) + "'::date,'"
					+ (endtime) + "'::date," + exp + "::int4);";

			List temp = jdbcTemplate.queryForList(query);

			details.addAll(temp);
		}

		/*
		 * long d2 = System.currentTimeMillis();
		 *
		 * long diff = d2 - d1;
		 *
		 * long diffSeconds = diff / 1000 % 60;
		 *
		 * System.out.println("Query in seconds: "+diffSeconds);
		 *
		 * d1=System.currentTimeMillis();
		 */

		List retData = new ArrayList<>();

		List<String> keys = new ArrayList<>();
		Map exceptionTypeIds = new Hashtable();
		Map exceptionLevelIds = new Hashtable();
		for (Object detail : details) {

			String s = detail.toString();
			s = s.replace("{", "").replace("}", "");
			String[] pairs = s.split(",");
			Map myMap = new HashMap<>();
			for (String pair2 : pairs) {
				String pair = pair2;
				pair = pair.trim();
				String[] keyValue = pair.split("=");
				myMap.put(keyValue[0], keyValue[1]);
				if (keyValue[0].equals("exceptiontype")) {
					String key = keyValue[0];

					String a = PropertyLoader.getLabel(language, keyValue[1]);
					if (!keys.contains(a)) {
						keys.add(a);
					}
				}
			}
			JSONObject temp = new JSONObject(detail.toString().replace("=", ":"));
			exceptionLevelIds.put(PropertyLoader.getLabel(language, (String) temp.get("exceptionlevel")),
					temp.get("levelid"));
			exceptionTypeIds.put(PropertyLoader.getLabel(language, (String) temp.get("exceptiontype")),
					temp.get("exceptiontypeid"));
		}
		exceptionTypeIds.put(PropertyLoader.getLabel(language, "menu_weekly_rest_time"), 8);
		exceptionTypeIds.put(PropertyLoader.getLabel(language, "menu_weekly_driving"), 9);

		Map jobToAdd = null;
		List newList = new ArrayList();

		/* WDT */
		jobToAdd = new HashMap();
		jobToAdd.put(PropertyLoader.getLabel(language, "menu_weekly_driving"), 0.0);

		ArrayUtils.reverse(explevel);
		String exceptionlevels = String.join(",", explevel);

		List weeklyList = getWeeklyDrive100hrs(Integer.parseInt(clientid), Integer.parseInt(affiliateid),
				Integer.parseInt(transporterid), Integer.parseInt(vehicleid), exceptionlevels, startime, endtime,
				language);
		Double exceptionvalue = 0.0;
		for (Object element : weeklyList) {
			Map temp = (Map) element;
			exceptionvalue += Double.parseDouble(temp.get("exceptionvalue").toString());
		}

		jobToAdd.put(PropertyLoader.getLabel(language, "menu_weekly_driving"), exceptionvalue);

		newList.add(jobToAdd);

		/* WRT */
		jobToAdd = new HashMap();
		jobToAdd.put(PropertyLoader.getLabel(language, "menu_weekly_rest_time"), 0.0);

		List weeklyRestList = getWeeklyRest100hrs(Integer.parseInt(clientid), Integer.parseInt(affiliateid),
				Integer.parseInt(transporterid), Integer.parseInt(vehicleid), exceptionlevels, startime, endtime,
				language);

		for (Object element : weeklyRestList) {
			Map temp = (Map) element;

			exceptionvalue += Double.parseDouble(temp.get("exceptionvalue").toString());
		}

		jobToAdd.put(PropertyLoader.getLabel(language, "menu_weekly_rest_time"), exceptionvalue);

		newList.add(jobToAdd);
		for (String keytoAdd : keys) {
			if (!PropertyLoader.getLabel(language, "menu_weekly_driving").equals(keytoAdd)
					&& !PropertyLoader.getLabel(language, "menu_weekly_rest_time").equals(keytoAdd)) {
				jobToAdd = new HashMap();
				jobToAdd.put(keytoAdd, 0.0);

				for (int i = 0; i < details.size(); i++) {

					String s = details.get(i).toString();
					s = s.replace("{", "").replace("}", "");
					String[] pairs = s.split(",");
					Map myMap = new HashMap<>();
					for (String pair2 : pairs) {
						String pair = pair2;
						pair = pair.trim();
						String[] keyValue = pair.split("=");

						String a = PropertyLoader.getLabel(language, keyValue[1]);
						myMap.put(keyValue[0], a);
					}
					JSONObject job = new JSONObject(myMap);
					String key = job.getString("exceptiontype");
					if (key.equals(keytoAdd)) {
						jobToAdd.put(keytoAdd,
								(Double) jobToAdd.get(keytoAdd) + parseDouble(job.get("driverexceptionper100hrs")));
						newList.add(jobToAdd);
					}
				}
			}

		}

		for (int i = 0; i < newList.size(); i++) {
			Iterator it = ((Map) newList.get(i)).entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				Map newData = new HashMap();
				newData.put("key", pair.getKey());
				newData.put("y", pair.getValue());
				newData.put("exceptionLevelIds", exceptionLevelIds);
				newData.put("exceptionTypeIds", exceptionTypeIds);
				it.remove(); // avoids a ConcurrentModificationException
				retData.add(newData);
			}
		}

		/*
		 * d2 = System.currentTimeMillis();
		 *
		 * diff = d2 - d1;
		 *
		 * diffSeconds = diff / 1000 % 60;
		 *
		 * System.out.println("Process in seconds: "+diffSeconds);
		 */

		return retData;

	}

	@Override
	public List driverExceptionsPer100kms(String clientid, String affiliateid, String transporterid, String vehicleid,
			String startime, String endtime, String[] explevel, String language) throws Exception {
		List details = new ArrayList<>();

		/*
		 * System.out.println("driverExceptionsPer100kms");
		 *
		 * long d1 = System.currentTimeMillis();
		 *
		 * System.out.println("Query started at: " + Calendar.getInstance().getTime());
		 */

		for (String exp : explevel) {

			if (affiliateid == null && affiliateid.length() == 0) {
				affiliateid = "0";
			}

			if (transporterid == null && transporterid.length() == 0) {
				transporterid = "0";
			}

			if (vehicleid == null && vehicleid.length() == 0) {
				vehicleid = "0";
			}

			if (startime == null && startime.length() == 0) {
				startime = null;
			}

			if (endtime == null && endtime.length() == 0) {
				endtime = null;
			}

			String query = "select * from \"sp_driverexceptionsper100kms_new\"(" + clientid + "::int4," + affiliateid
					+ "::int4," + transporterid + "::int4," + vehicleid + "::int4,'" + (startime) + "'::date,'"
					+ (endtime) + "'::date," + exp + "::int4);";

			List temp = jdbcTemplate.queryForList(query);

			details.addAll(temp);

		}

		/*
		 * long d2 = System.currentTimeMillis();
		 *
		 * System.out.println("Query finished at: " + Calendar.getInstance().getTime());
		 *
		 * long diff = d2 - d1;
		 *
		 * long diffSeconds = diff / 1000 % 60;
		 *
		 * System.out.println("Query executed in milliseconds: " + diffSeconds);
		 *
		 *
		 * d1 = System.currentTimeMillis();
		 *
		 * System.out.println("Processing started at: " +
		 * Calendar.getInstance().getTime());
		 */

		List retData = new ArrayList<>();

		List<String> keys = new ArrayList<>();
		Map exceptionTypeIds = new Hashtable();
		Map exceptionLevelIds = new Hashtable();
		for (Object detail : details) {

			String s = detail.toString();
			s = s.replace("{", "").replace("}", "");
			String[] pairs = s.split(",");
			Map myMap = new HashMap<>();
			for (String pair2 : pairs) {
				String pair = pair2;
				pair = pair.trim();
				String[] keyValue = pair.split("=");
				myMap.put(keyValue[0], keyValue[1]);
				if (keyValue[0].equals("exceptiontype")) {
					String key = keyValue[0];

					String a = PropertyLoader.getLabel(language, keyValue[1]);
					if (!keys.contains(a)) {
						keys.add(a);
					}
				}
			}

			JSONObject temp = new JSONObject(detail.toString().replace("=", ":"));
			exceptionLevelIds.put(PropertyLoader.getLabel(language, (String) temp.get("exceptionlevel")),
					temp.get("levelid"));
			exceptionTypeIds.put(PropertyLoader.getLabel(language, (String) temp.get("exceptiontype")),
					temp.get("exceptiontypeid"));
		}

		Map jobToAdd = null;
		List newList = new ArrayList();
		for (String keytoAdd : keys) {
			jobToAdd = new HashMap();
			jobToAdd.put(keytoAdd, 0.0);

			for (int i = 0; i < details.size(); i++) {

				String s = details.get(i).toString();
				s = s.replace("{", "").replace("}", "");
				String[] pairs = s.split(",");
				Map myMap = new HashMap<>();
				for (String pair2 : pairs) {
					String pair = pair2;
					pair = pair.trim();
					String[] keyValue = pair.split("=");

					String a = PropertyLoader.getLabel(language, keyValue[1]);
					myMap.put(keyValue[0], a);
				}
				JSONObject job = new JSONObject(myMap);
				String key = job.getString("exceptiontype");
				if (key.equals(keytoAdd)) {
					jobToAdd.put(keytoAdd,
							(Double) jobToAdd.get(keytoAdd) + parseDouble(job.get("driverexceptionper100kms")));

					newList.add(jobToAdd);
				}
			}

		}

		for (int i = 0; i < newList.size(); i++) {
			Iterator it = ((Map) newList.get(i)).entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				Map newData = new HashMap();
				newData.put("key", pair.getKey());
				newData.put("y", pair.getValue());
				newData.put("exceptionLevelIds", exceptionLevelIds);
				newData.put("exceptionTypeIds", exceptionTypeIds);
				it.remove(); // avoids a ConcurrentModificationException
				retData.add(newData);
			}
		}

		/*
		 * d2 = System.currentTimeMillis();
		 *
		 * System.out.println("Processing finished at: " +
		 * Calendar.getInstance().getTime());
		 *
		 * diff = d2 - d1;
		 *
		 * diffSeconds = diff / 1000 % 60;
		 *
		 * System.out.println("Processing executed in milliseconds: " + diffSeconds);
		 */

		return retData;

	}

	@Override
	public List driveTimeViolations(String clientid, String affiliateid, String transporterid, String vehicleid,
			String startime, String endtime, String[] expLevel, String language) throws Exception {
		// TODO Auto-generated method stub

		List details = new ArrayList<>();
		ArrayUtils.reverse(expLevel);

		String exceptionlevel = "0";
		int count = 0;
		for (String exp : expLevel) {
			// System.out.println(exp);
			if (exp.equals("0")) {
				exceptionlevel = "1,2,3";
			} else {
				if (count == 0) {
					exceptionlevel = exp;
				} else {
					exceptionlevel = exceptionlevel + "," + exp;
				}

			}
			count++;

		}

		/*
		 * for (String exp : expLevel) {
		 *
		 * if (affiliateid == null && affiliateid.length() == 0) { affiliateid = "0"; }
		 *
		 * if (transporterid == null && transporterid.length() == 0) { transporterid =
		 * "0"; }
		 *
		 * if (vehicleid == null && vehicleid.length() == 0) { vehicleid = "0"; }
		 *
		 * if (startime == null && startime.length() == 0) { startime = null; }
		 *
		 * if (endtime == null && endtime.length() == 0) { endtime = null; }
		 *
		 * String query = "select * from \"sp1_drivetimeviolations\"(" + clientid +
		 * "::int4," + affiliateid + "::int4," + transporterid + "::int4," + vehicleid +
		 * "::int4,'" + (startime) + "'::date,'" + (endtime) + "'::date," + exp +
		 * "::int4)"; if (!exp.equals("0")) query += " where levelid=" + exp + "::int4";
		 * System.out.println(query); List temp = jdbcTemplate.queryForList(query);
		 *
		 * details.addAll(temp); }
		 */

		if (affiliateid == null && affiliateid.length() == 0) {
			affiliateid = "0";
		}

		if (transporterid == null && transporterid.length() == 0) {
			transporterid = "0";
		}

		if (vehicleid == null && vehicleid.length() == 0) {
			vehicleid = "0";
		}

		if (startime == null && startime.length() == 0) {
			startime = null;
		}

		if (endtime == null && endtime.length() == 0) {
			endtime = null;
		}

		String query = "select * from \"sp_new_drivetimeviolations\"(" + clientid + "::int4," + affiliateid + "::int4,"
				+ transporterid + "::int4," + vehicleid + "::int4,'" + (startime) + "'::date,'" + (endtime) + "'::date,"
				+ "ARRAY[" + exceptionlevel + "])";
		/*
		 * if (!exp.equals("0")) query += " where levelid=" + exp + "::int4";
		 */
		// System.out.println(query);
		List temp1 = jdbcTemplate.queryForList(query);

		details.addAll(temp1);

		List retData = new ArrayList<>();

		List<String> keys = new ArrayList<>();
		Map exceptionTypeIds = new Hashtable();
		Map exceptionLevelIds = new Hashtable();
		for (Object detail : details) {

			String s = detail.toString();
			s = s.replace("{", "").replace("}", "");
			String[] pairs = s.split(",");
			Map myMap = new HashMap<>();
			for (String pair2 : pairs) {
				String pair = pair2;
				pair = pair.trim();
				String[] keyValue = pair.split("=");
				myMap.put(keyValue[0], keyValue[1]);
				if (keyValue[0].equals("exceptionlevel")) {
					String key = keyValue[0];

					String a = PropertyLoader.getLabel(language, keyValue[1]);
					if (!keys.contains(a)) {
						keys.add(a);
					}
				}
			}
			JSONObject temp = new JSONObject(detail.toString().replace("=", ":"));
			exceptionLevelIds.put(PropertyLoader.getLabel(language, (String) temp.get("exceptionlevel")),
					temp.get("levelid"));
			exceptionTypeIds.put(PropertyLoader.getLabel(language, (String) temp.get("exceptiontype")),
					temp.get("exceptiontypeid"));
		}
		exceptionTypeIds.put(PropertyLoader.getLabel(language, "menu_weekly_rest_time"), 8);
		exceptionTypeIds.put(PropertyLoader.getLabel(language, "menu_weekly_driving"), 9);
		Map jobToAdd = null;
		for (String keytoAdd : keys) {
			jobToAdd = new HashMap();
			jobToAdd.put("key", keytoAdd);
			jobToAdd.put("total", 0.0);
			jobToAdd.put("exceptionLevelIds", exceptionLevelIds);
			jobToAdd.put("exceptionTypeIds", exceptionTypeIds);

			for (int i = 0; i < details.size(); i++) {

				String s = details.get(i).toString();
				s = s.replace("{", "").replace("}", "");
				String[] pairs = s.split(",");
				Map myMap = new HashMap<>();
				for (String pair2 : pairs) {
					String pair = pair2;
					pair = pair.trim();
					String[] keyValue = pair.split("=");

					String a = PropertyLoader.getLabel(language, keyValue[1]);
					myMap.put(keyValue[0], a);
				}
				JSONObject job = new JSONObject(myMap);
				String key = job.getString("exceptionlevel");
				if (key.equals(keytoAdd)) {
					List arr = null;
					if (jobToAdd.containsKey("values")) {
						arr = (ArrayList) jobToAdd.get("values");
					} else {
						arr = new ArrayList();
						ArrayUtils.reverse(expLevel);
						String exceptionlevels = String.join(",", expLevel);
						/* WDT */

						List values = new ArrayList();
						values.add(PropertyLoader.getLabel(language, "menu_weekly_driving"));
						List weeklyList = null;
						if (affiliateid.equals("0"))
							weeklyList = getWeeklyDriveAffDriver(Integer.parseInt(clientid),
									Integer.parseInt(affiliateid), Integer.parseInt(transporterid),
									Integer.parseInt(vehicleid), exceptionlevels, startime, endtime, language);
						else if (transporterid.equals("0"))
							weeklyList = getWeeklyDriveTransporter(Integer.parseInt(clientid),
									Integer.parseInt(affiliateid), Integer.parseInt(transporterid),
									Integer.parseInt(vehicleid), exceptionlevels, startime, endtime, language);
						else
							weeklyList = getWeeklyDrive(Integer.parseInt(clientid), Integer.parseInt(affiliateid),
									Integer.parseInt(transporterid), Integer.parseInt(vehicleid), exceptionlevels,
									startime, endtime, language);
						Double alarmCount = 0.0, alertcount = 0.0, recordingcount = 0.0;
						for (Object element : weeklyList) {
							Map temp = (Map) element;
							if (keytoAdd.equals(PropertyLoader.getLabel(language, "db_alarm")))
								alarmCount += Integer.parseInt(temp.get("alarmcount").toString());
							if (keytoAdd.equals(PropertyLoader.getLabel(language, "db_recording")))
								recordingcount += Integer.parseInt(temp.get("recordingcount").toString());
							if (keytoAdd.equals(PropertyLoader.getLabel(language, "db_alert")))
								alertcount += Integer.parseInt(temp.get("alertcount").toString());

						}
						if (keytoAdd.equals(PropertyLoader.getLabel(language, "db_alarm"))) {
							values.add(alarmCount);
							jobToAdd.put("total", (Double) jobToAdd.get("total") + alarmCount);
						}
						if (keytoAdd.equals(PropertyLoader.getLabel(language, "db_recording"))) {
							values.add(recordingcount);
							jobToAdd.put("total", (Double) jobToAdd.get("total") + recordingcount);
						}
						if (keytoAdd.equals(PropertyLoader.getLabel(language, "db_alert"))) {
							values.add(alertcount);
							jobToAdd.put("total", (Double) jobToAdd.get("total") + alertcount);
						}
						arr.add(values);

						/* WRT */
						values = new ArrayList();
						values.add(PropertyLoader.getLabel(language, "menu_weekly_rest_time"));

						List weeklyRestList = null;
						if (affiliateid.equals("0"))
							weeklyRestList = getWeeklyRestAffDriver(Integer.parseInt(clientid),
									Integer.parseInt(affiliateid), Integer.parseInt(transporterid),
									Integer.parseInt(vehicleid), exceptionlevels, startime, endtime, language);
						else if (transporterid.equals("0"))
							weeklyRestList = getWeeklyRestTransporter(Integer.parseInt(clientid),
									Integer.parseInt(affiliateid), Integer.parseInt(transporterid),
									Integer.parseInt(vehicleid), exceptionlevels, startime, endtime, language);
						else
							weeklyRestList = getWeeklyRest(Integer.parseInt(clientid), Integer.parseInt(affiliateid),
									Integer.parseInt(transporterid), Integer.parseInt(vehicleid), exceptionlevels,
									startime, endtime, language);
						alarmCount = 0.0;
						alertcount = 0.0;
						recordingcount = 0.0;
						for (Object element : weeklyRestList) {
							Map temp = (Map) element;
							if (keytoAdd.equals(PropertyLoader.getLabel(language, "db_alarm")))
								alarmCount += Integer.parseInt(temp.get("alarmcount").toString());
							if (keytoAdd.equals(PropertyLoader.getLabel(language, "db_recording")))
								recordingcount += Integer.parseInt(temp.get("recordingcount").toString());
							if (keytoAdd.equals(PropertyLoader.getLabel(language, "db_alert")))
								alertcount += Integer.parseInt(temp.get("alertcount").toString());
						}
						if (keytoAdd.equals(PropertyLoader.getLabel(language, "db_alarm"))) {
							values.add(alarmCount);
							jobToAdd.put("total", (Double) jobToAdd.get("total") + alarmCount);
						}
						if (keytoAdd.equals(PropertyLoader.getLabel(language, "db_recording"))) {
							values.add(recordingcount);
							jobToAdd.put("total", (Double) jobToAdd.get("total") + recordingcount);
						}
						if (keytoAdd.equals(PropertyLoader.getLabel(language, "db_alert"))) {
							values.add(alertcount);
							jobToAdd.put("total", (Double) jobToAdd.get("total") + alertcount);
						}

						arr.add(values);
					}
					/*
					 * Map values=new HashMap();
					 *
					 * values.put("label", job.getString("exceptiontype")); values.put("value",
					 * job.get("violationscount"));
					 */
					List values = new ArrayList();
					values.add(job.getString("exceptiontype"));
					values.add(parseDouble(job.get("violationscount")));

					jobToAdd.put("total", (Double) jobToAdd.get("total") + parseDouble(job.get("violationscount")));
					arr.add(values);
					jobToAdd.put("values", arr);
				}
			}
			retData.add(jobToAdd);
		}
		return retData;

	}

	@Override
	public String fetchAffiliateCountry(String affiliateid) throws Exception {

		String country = "";

		try {
			String query = environment.getRequiredProperty("affiliate.getcountry");

			Object[] id = new Object[] { new Integer(affiliateid) };

			Object CountryName = jdbcTemplate.queryForObject(query, id, String.class);

			country = CountryName.toString();

			// System.out.println("Country---->" + country);

			return country;

		} catch (Exception e) {
			// TODO: handle exception
			return e.toString();
		}

	}

	/* Methods to WeeklyDrive & Rest : created by Jinshad */

	// public List getWeeklyReports(String clientid, String affiliateid,
	// String transporterid, String vehicleid, String startime,
	// String endtime, String[] expLevel, String language)
	// throws Exception {
	//
	// String levels = "";
	//
	// if (expLevel.length == 1 && expLevel[0].contains("0")) {
	// levels = "1,2,3";
	// } else {
	// for (String str : expLevel) {
	// levels += str + ",";
	// }
	// }
	//
	// getWeeklyDrive(Integer.parseInt(clientid),
	// Integer.parseInt(affiliateid), Integer.parseInt(transporterid),
	// Integer.parseInt(vehicleid), levels, startime, endtime);
	//
	// return getWeeklyRest(Integer.parseInt(clientid),
	// Integer.parseInt(affiliateid), Integer.parseInt(transporterid),
	// Integer.parseInt(vehicleid), levels, startime, endtime);
	//
	// }

	@Override
	public List functionDriverBehaviourViolations(String userid, String affiliateid, String transporterid,
			String vehicleid, String startime, String endtime) throws Exception {
		String query = "select driver_beh_violations(";

		if (affiliateid != null && affiliateid.length() > 0) {
			query += affiliateid + ",";
		}

		if (transporterid != null && transporterid.length() > 0) {
			query += transporterid + ",";
		}

		// if (vehicleid != null && vehicleid.length() > 0) {
		// query += " and vehicleid='" + vehicleid + "'";
		// }

		if (startime != null && startime.length() > 0) {
			query += "'" + startime + "',";
		}

		if (endtime != null && endtime.length() > 0) {
			query += "'" + endtime + "',";
		}

		query = query.substring(0, query.length() - 1);
		query += ")";

		return jdbcTemplate.queryForList(query);
	}

	@Override
	public List getAllAffiliates(int userid) throws Exception {
		// TODO Auto-generated method stub

		List detailsList = null;

		String userQry = "select userroleid, customerid,affiliateid from \"user\" where userid=? and status=1";
		List userList = jdbcTemplate.queryForList(userQry, new Object[] { userid });

		if (userList.size() > 0) {
			Map userDetails = (Map) userList.get(0);

			String query;
			if ((Integer) userDetails.get("userroleid") == AppConstants.ROLE_SUPER_ADMIN) {
				query = "SELECT affiliateid,name as affiliatename FROM \"customeraffiliate\"";
			} else if ((Integer) userDetails.get("userroleid") == AppConstants.ROLE_CUSTOMER_ADMIN) {
				query = "SELECT affiliateid,name as affiliatename FROM \"customeraffiliate\" where  status=1 and customerid="
						+ userDetails.get("customerid");
			} else {
				query = "SELECT affiliateid,name as affiliatename FROM \"customeraffiliate\" where status=1 and affiliateid="
						+ userDetails.get("affiliateid");
			}

			detailsList = jdbcTemplate.queryForList(query);

		}
		return detailsList;
	}

	@Override
	public List getAllTransporters(int userid, int affiliateid) throws Exception {
		// TODO Auto-generated method stub

		String query = "SELECT transporterid,name as transportername FROM \"transporter\" where affiliateid="
				+ affiliateid + " and status=1";
		List detailsList = jdbcTemplate.queryForList(query);

		return detailsList;
	}

	@Override
	public List getAllVehicles(int userid, int transporterid) throws Exception {
		// TODO Auto-generated method stub

		String query = "SELECT vehicleid,vehicledesc FROM \"vehicle\" where transporterid=" + transporterid
				+ " and status=1";
		List detailsList = jdbcTemplate.queryForList(query);

		return detailsList;
	}

	public List getDateWiseWeeklyDrive100hrs(int customerid, int affiliateid, int transporterid, int vehicleid,
			String exceptionlevels, String startDate, String endDate, String language) throws JSONException, Exception {

		String query = "";
		if (exceptionlevels.equals("0"))
			exceptionlevels = "1,2,3";
		List weeklyList = new ArrayList();

		List<WeekDaysBean> dateRanges = CommonUtil.getWeekDaysBetweenDates(java.sql.Date.valueOf(startDate),
				java.sql.Date.valueOf(endDate));

		if (dateRanges.size() > 0) {

			String driversqry = "select v.* from view_driver_veh_trans_aff v join customeraffiliate c on c.affiliateid=v.affiliateid WHERE v.status = 1 ";
			if (vehicleid > 0) {
				driversqry += " and v.vehicleid=" + vehicleid;
			} else if (transporterid > 0) {
				driversqry += " and v.transporterid=" + transporterid;
			} else if (affiliateid > 0) {
				driversqry += " and v.affiliateid=" + affiliateid;
			} else if (customerid > 0) {
				driversqry += " and c.customerid=" + customerid;
			}

			List<Map<String, Object>> drivers = jdbcTemplate.queryForList(driversqry);

			// Taking Weekly Drive

			List<Map<String, Object>> tempList = new ArrayList();
			if (vehicleid == 0) {
				for (Map<String, Object> driverDet : drivers) {

					int vehId = (int) driverDet.get("vehicleid");
					int driId = (int) driverDet.get("driverid");
					int affId = (int) driverDet.get("affiliateid");
					int transId = (int) driverDet.get("transporterid");

					String thresholdQry = "SELECT thresholdlimit,recordingthreshold,alertthreshold,alarmthreshold FROM parameterconfig "
							+ " WHERE parametertypeid = ? AND status = '1' AND clientaffiliateid = ?";
					Map<String, Object> thresholdDet = null;

					try {

						thresholdDet = jdbcTemplate.queryForMap(thresholdQry, AppConstants.WEEKLYDRIVE, affId);

						if (thresholdDet == null) {
							continue;
						}
					} catch (Exception e) {
						continue;
					}

					double threshold = Double.parseDouble(thresholdDet.get("thresholdlimit").toString());

					double recordingThreshold = Double.parseDouble(thresholdDet.get("recordingthreshold").toString());
					double alertThreshold = Double.parseDouble(thresholdDet.get("alertthreshold").toString());
					double alarmThreshold = Double.parseDouble(thresholdDet.get("alarmthreshold").toString());

					for (WeekDaysBean weekDaysBean : dateRanges) {

						query = "select " + affId + " affiliateid," + transId + " transporterid," + driId + " driverid,"
								+ vehId
								+ " vehicleid,vehiclename as vehicledesc,date(enddatetime) as edate,* from sp_weeklydrivedetails("
								+ vehId + "," + driId + ",'" + weekDaysBean.getStartDate() + "','"
								+ weekDaysBean.getEndDate() + "')  order by startdatetime::timestamp asc";

						List<Map<String, Object>> restList = jdbcTemplate.queryForList(query);

						Map weeklyDrive = null;
						if (restList.size() > 0) {

							double totalduration = 0;

							for (Map det : restList) {

								totalduration += Double.parseDouble(det.get("totalduration").toString());

								weeklyDrive = det;
							}
							String totalddDurQuery = "select case when totdurn=0 then 0.000001 else totdurn end as totdurn from "
									+ "sp_get_totduration_exp_new('" + weekDaysBean.getStartDate() + "', '"
									+ weekDaysBean.getEndDate() + "', " + "" + vehId + ",0," + transId + " ," + affId
									+ ", 9,ARRAY[" + exceptionlevels + "] )";

							double totalD = jdbcTemplate.queryForObject(totalddDurQuery, Double.class);

							double exceededDuration = 0.0;
							double exceptionvalue = 0.0;

							if (totalD > 0.000001) {
								exceededDuration = totalD - threshold;
								exceptionvalue = exceededDuration * 100 / totalD;
							}

							weeklyDrive.put("totalD", totalD);
							weeklyDrive.put("exceededDurationNew", exceededDuration);
							weeklyDrive.put("totalDurSP", totalduration);

							if (exceededDuration >= alarmThreshold) {
								weeklyDrive.put("exceptionvalue", exceptionvalue);
								weeklyDrive.put("exceededDurationNew", exceededDuration);
								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("3")) {
									continue;
								}
							} else if (exceededDuration >= alertThreshold) {
								weeklyDrive.put("exceptionvalue", exceptionvalue);
								weeklyDrive.put("exceededDurationNew", exceededDuration);
								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("2")) {
									continue;
								}
							} else if (exceededDuration >= recordingThreshold) {
								weeklyDrive.put("exceptionvalue", exceptionvalue);
								weeklyDrive.put("exceededDurationNew", exceededDuration);
								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("1")) {
									continue;
								}
							} else {
								continue;
							}
							if (exceptionvalue > 0 && exceededDuration > 0) {

								tempList.add(weeklyDrive);
							}
						}
					}

				}

			} else {

				for (Map<String, Object> driverDet : drivers) {

					int vehId = (int) driverDet.get("vehicleid");
					int driId = (int) driverDet.get("driverid");
					int affId = (int) driverDet.get("affiliateid");
					int transId = (int) driverDet.get("transporterid");

					String thresholdQry = "SELECT thresholdlimit,recordingthreshold,alertthreshold,alarmthreshold FROM parameterconfig "
							+ " WHERE parametertypeid = ? AND status = '1' AND clientaffiliateid = ?";
					Map<String, Object> thresholdDet = null;

					try {

						thresholdDet = jdbcTemplate.queryForMap(thresholdQry, AppConstants.WEEKLYDRIVE, affId);

						if (thresholdDet == null) {
							continue;
						}
					} catch (Exception e) {
						continue;
					}

					double threshold = Double.parseDouble(thresholdDet.get("thresholdlimit").toString());

					double recordingThreshold = Double.parseDouble(thresholdDet.get("recordingthreshold").toString());
					double alertThreshold = Double.parseDouble(thresholdDet.get("alertthreshold").toString());
					double alarmThreshold = Double.parseDouble(thresholdDet.get("alarmthreshold").toString());

					for (WeekDaysBean weekDaysBean : dateRanges) {

						query = "select " + affId + " affiliateid," + transId + " transporterid," + driId + " driverid,"
								+ vehId
								+ " vehicleid,vehiclename as vehicledesc,date(enddatetime) as edate,* from sp_weeklydrivedetails("
								+ vehId + "," + driId + ",'" + weekDaysBean.getStartDate() + "','"
								+ weekDaysBean.getEndDate() + "')";

						List<Map<String, Object>> restList = jdbcTemplate.queryForList(query);

						Map weeklyDrive = null;
						if (restList.size() > 0) {

							int ct = 0;

							double totalduration = 0;

							for (Map det : restList) {

								totalduration += Double.parseDouble(det.get("totalduration").toString());

								weeklyDrive = det;
								ct++;

							}
							String totalddDurQuery = "select case when totdurn=0 then 0.000001 else totdurn end as totdurn from "
									+ "sp_get_totduration_exp_new('" + weekDaysBean.getStartDate() + "', '"
									+ weekDaysBean.getEndDate() + "', " + "" + vehId + ",0," + transId + " ," + affId
									+ ", 9,ARRAY[" + exceptionlevels + "] )";

							double totalD = jdbcTemplate.queryForObject(totalddDurQuery, Double.class);

							double timeexceeded = 0.0;
							double exceptionvalue = 0.0;

							if (totalD > 0.000001) {
								timeexceeded = totalD - threshold;
								exceptionvalue = timeexceeded * 100 / totalD;
							}
							weeklyDrive.put("totalD", totalD);
							weeklyDrive.put("exceededDurationNew", timeexceeded);

							if (timeexceeded >= alarmThreshold) {
								weeklyDrive.put("exceptionvalue", exceptionvalue);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("3")) {
									continue;
								}
							} else if (timeexceeded >= alertThreshold) {
								weeklyDrive.put("exceptionvalue", exceptionvalue);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("2")) {
									continue;
								}
							} else if (timeexceeded >= recordingThreshold) {
								weeklyDrive.put("exceptionvalue", exceptionvalue);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("1")) {
									continue;
								}
							} else {
								continue;
							}

							if (exceptionvalue > 0 && timeexceeded > 0) {

								tempList.add(weeklyDrive);
							}

						}
					}

				}
			}

			if (tempList.size() > 0) {
				Map<Integer, Map<String, Object>> detailsMap = new HashMap<>();
				if (vehicleid > 0 || transporterid > 0) {

					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("vehicleid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("vehicleid"));
							existing.put("exceptionvalue",
									(Double) existing.get("exceptionvalue") + (Double) map.get("exceptionvalue"));
						} else {
							detailsMap.put((int) map.get("vehicleid"), map);
						}
					}

				} else if (affiliateid > 0) {
					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("transporterid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("transporterid"));

							double exceededDurationNew = (double) existing.get("exceededDurationNew");
							double exceededDuration = (double) map.get("exceededDurationNew");
							double totalExceedTime = exceededDurationNew + exceededDuration;

							double totalDur = (double) existing.get("totalD");
							double TotalD = (double) map.get("totalD");
							double totaldurationsum = totalDur + TotalD;

							existing.put("totalD", totaldurationsum);
							existing.put("exceededDurationNew", totalExceedTime);
							existing.put("exceptionvalue", totalExceedTime * 100 / totaldurationsum);
							/*
							 * existing.put( "exceptionvalue", (Double) existing.get("exceptionvalue") +
							 * (Double) map .get("exceptionvalue"));
							 */
						} else {
							detailsMap.put((int) map.get("transporterid"), map);
						}
					}

				} else {
					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("affiliateid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("affiliateid"));
							/*
							 * existing.put( "exceptionvalue", (Double) existing.get("exceptionvalue") +
							 * (Double) map .get("exceptionvalue"));
							 */
							double exceededDurationNew = (double) existing.get("exceededDurationNew");
							double exceededDuration = (double) map.get("exceededDurationNew");
							double totalExceedTime = exceededDurationNew + exceededDuration;

							double totalDur = (double) existing.get("totalD");
							double TotalD = (double) map.get("totalD");
							double totaldurationsum = totalDur + TotalD;

							existing.put("totalD", totaldurationsum);
							existing.put("exceededDurationNew", totalExceedTime);
							existing.put("exceptionvalue", totalExceedTime * 100 / totaldurationsum);
						} else {
							detailsMap.put((int) map.get("affiliateid"), map);
						}
					}

				}

				weeklyList.addAll(detailsMap.values());

			}

		}
		// System.out.println("weeklyList->"+weeklyList.toString());
		return weeklyList;

	}

	public List getDateWiseWeeklyRest100hrs(int customerid, int affiliateid, int transporterid, int vehicleid,
			String exceptionlevels, String startDate, String endDate, String language) {

		List weeklyList = new ArrayList();

		String query = "";

		List<WeekDaysBean> dateRanges = CommonUtil.getWeekDaysBetweenDates(java.sql.Date.valueOf(startDate),
				java.sql.Date.valueOf(endDate));

		// Taking Weekly Rest

		if (dateRanges.size() > 0) {

			/*
			 * String driversqry =
			 * "select v.* from view_driver_veh_trans_aff v join customeraffiliate c on c.affiliateid=v.affiliateid WHERE v.status = 1 "
			 * ; if (vehicleid > 0) { driversqry += " and v.vehicleid=" + vehicleid; } else
			 * if (transporterid > 0) { driversqry += " and v.transporterid=" +
			 * transporterid; } else if (affiliateid > 0) { driversqry +=
			 * " and v.affiliateid=" + affiliateid; } else if (customerid > 0) { driversqry
			 * += " and c.customerid=" + customerid; }
			 */
			String driversqry = "select * from sp_get_vehdriver_ids_from_cd_of_wrt(" + vehicleid + "," + transporterid
					+ "," + affiliateid + "," + customerid + ",'" + startDate + "','" + endDate + "')";
			driversqry += " order by affiliateid,transporterid,vehicleid";

			List<Map<String, Object>> drivers = jdbcTemplate.queryForList(driversqry);

			List<Map<String, Object>> tempList = new ArrayList();

			if (exceptionlevels.equals("0"))
				exceptionlevels = "1,2,3";
			if (vehicleid == 0) {

				for (Map<String, Object> driverDet : drivers) {

					int vehId = (int) driverDet.get("vehicleid");
					int driId = (int) driverDet.get("driverid");
					int affId = (int) driverDet.get("affiliateid");
					int transId = (int) driverDet.get("transporterid");

					String thresholdQry = "SELECT thresholdlimit,recordingthreshold,alertthreshold,alarmthreshold FROM parameterconfig "
							+ " WHERE parametertypeid = ? AND status = '1' AND clientaffiliateid = ?";
					Map<String, Object> thresholdDet = null;

					try {

						thresholdDet = jdbcTemplate.queryForMap(thresholdQry, AppConstants.WEEKLYREST, affId);

						if (thresholdDet == null) {
							continue;
						}
					} catch (Exception e) {
						continue;
					}

					double threshold = Double.parseDouble(thresholdDet.get("thresholdlimit").toString());
					double recordingThreshold = Double.parseDouble(thresholdDet.get("recordingthreshold").toString());
					double alertThreshold = Double.parseDouble(thresholdDet.get("alertthreshold").toString());
					double alarmThreshold = Double.parseDouble(thresholdDet.get("alarmthreshold").toString());

					for (WeekDaysBean weekDaysBean : dateRanges) {

						query = "select " + affId + " affiliateid," + transId + " transporterid," + driId + " driverid,"
								+ vehId
								+ " vehicleid,vehiclename as vehicledesc,date(enddatetime) as edate,* from sp_weeklyrestdetails("
								+ vehId + "," + driId + ",'" + weekDaysBean.getStartDate() + "','"
								+ weekDaysBean.getEndDate() + "')";

						List<Map<String, Object>> restList = jdbcTemplate.queryForList(query);

						Map longestRest = null;
						if (restList.size() > 0) {

							double longestRestDur = 0;
							double totalduration = 0;
							for (Map det : restList) {

								if (longestRestDur < Double.parseDouble(det.get("totalduration").toString())) {
									longestRestDur = Double.parseDouble(det.get("totalduration").toString());
									longestRest = det;
								}
								totalduration += Double.parseDouble(det.get("totalduration").toString());
							}
							String totalddDurQuery = "select case when totdurn=0 then 0.000001 else totdurn end as totdurn from "
									+ "sp_get_totduration_exp_new('" + weekDaysBean.getStartDate() + "', '"
									+ weekDaysBean.getEndDate() + "', " + "" + vehId + ",0," + transId + " ," + affId
									+ ", 8,ARRAY[" + exceptionlevels + "] )";

							double totalD = jdbcTemplate.queryForObject(totalddDurQuery, Double.class);
							double exceededTim = 0.0;
							double exceptionvalue = 0.0;

							if (totalD > 0.000001) {
								exceededTim = threshold - longestRestDur;

								exceptionvalue = exceededTim * 100 / totalD;
							}

							if (exceededTim >= alarmThreshold) {
								longestRest.put("exceptionvalue", exceptionvalue);
								longestRest.put("exceededDurationNew", exceededTim);
								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("3")) {
									continue;
								}

							} else if (exceededTim >= alertThreshold) {
								longestRest.put("exceptionvalue", exceptionvalue);
								longestRest.put("exceededDurationNew", exceededTim);
								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("2")) {
									continue;
								}

							} else if (exceededTim >= recordingThreshold) {
								longestRest.put("exceptionvalue", exceptionvalue);
								longestRest.put("exceededDurationNew", exceededTim);
								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("1")) {
									continue;
								}

							} else {
								continue;
							}
							longestRest.put("exceededDurationNew", exceededTim);
							longestRest.put("totalD", totalD);
							longestRest.put("totalDurSP", totalduration);
							longestRest.put("exceedT", exceededTim);

							tempList.add(longestRest);

						}
					}

				}

			} else {
				for (Map<String, Object> driverDet : drivers) {

					int vehId = (int) driverDet.get("vehicleid");
					int driId = (int) driverDet.get("driverid");
					int affId = (int) driverDet.get("affiliateid");
					int transId = (int) driverDet.get("transporterid");

					String thresholdQry = "SELECT thresholdlimit,recordingthreshold,alertthreshold,alarmthreshold FROM parameterconfig "
							+ " WHERE parametertypeid = ? AND status = '1' AND clientaffiliateid = ?";
					Map<String, Object> thresholdDet = null;

					try {

						thresholdDet = jdbcTemplate.queryForMap(thresholdQry, AppConstants.WEEKLYREST, affId);

						if (thresholdDet == null) {
							continue;
						}
					} catch (Exception e) {
						continue;
					}

					double threshold = Double.parseDouble(thresholdDet.get("thresholdlimit").toString());

					double recordingThreshold = Double.parseDouble(thresholdDet.get("recordingthreshold").toString());
					double alertThreshold = Double.parseDouble(thresholdDet.get("alertthreshold").toString());
					double alarmThreshold = Double.parseDouble(thresholdDet.get("alarmthreshold").toString());

					for (WeekDaysBean weekDaysBean : dateRanges) {

						query = "select " + affId + " affiliateid," + transId + " transporterid," + driId + " driverid,"
								+ vehId
								+ " vehicleid,vehiclename as vehicledesc,date(enddatetime) as edate,* from sp_weeklyrestdetails("
								+ vehId + "," + driId + ",'" + weekDaysBean.getStartDate() + "','"
								+ weekDaysBean.getEndDate() + "')";

						List<Map<String, Object>> restList = jdbcTemplate.queryForList(query);

						Map longestRest = null;
						if (restList.size() > 0) {

							double longestRestDur = 0;
							double totalduration = 0;
							for (Map det : restList) {
								if (longestRestDur < Double.parseDouble(det.get("totalduration").toString())) {
									longestRestDur = Double.parseDouble(det.get("totalduration").toString());
									longestRest = det;
								}
								totalduration += Double.parseDouble(det.get("totalduration").toString());
							}

							String totalddDurQuery = "select case when totdurn=0 then 0.000001 else totdurn end as totdurn from "
									+ "sp_get_totduration_exp_new('" + weekDaysBean.getStartDate() + "', '"
									+ weekDaysBean.getEndDate() + "', " + "" + vehId + ",0," + transId + " ," + affId
									+ ", 8,ARRAY[" + exceptionlevels + "] )";

							double totalD = jdbcTemplate.queryForObject(totalddDurQuery, Double.class);

							double exceededTim = 0.0;
							double exceptionvalue = 0.0;

							if (totalD > 0.000001) {
								exceededTim = threshold - longestRestDur;

								exceptionvalue = exceededTim * 100 / totalD;
							}

							if (exceededTim >= alarmThreshold) {
								longestRest.put("exceptionvalue", exceptionvalue);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("3")) {
									continue;
								}

							} else if (exceededTim >= alertThreshold) {
								longestRest.put("exceptionvalue", exceptionvalue);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("2")) {
									continue;
								}

							} else if (exceededTim >= recordingThreshold) {
								longestRest.put("exceptionvalue", exceptionvalue);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("1")) {
									continue;
								}

							} else {
								continue;
							}

							longestRest.put("exceededDurationNew", exceededTim);
							longestRest.put("totalD", totalD);
							tempList.add(longestRest);

						}
					}

				}
			}

			if (tempList.size() > 0) {
				Map<Integer, Map<String, Object>> detailsMap = new HashMap<>();
				if (vehicleid > 0 || transporterid > 0) {

					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("vehicleid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("vehicleid"));
							existing.put("exceptionvalue",
									(Double) existing.get("exceptionvalue") + (Double) map.get("exceptionvalue"));
						} else {
							detailsMap.put((int) map.get("vehicleid"), map);
						}
					}

				} else if (affiliateid > 0) {
					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("transporterid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("transporterid"));

							double exceededDurationNew = (double) existing.get("exceededDurationNew");
							double exceededDuration = (double) map.get("exceededDurationNew");
							double totalExceedTime = exceededDurationNew + exceededDuration;

							double totalDur = (double) existing.get("totalD");
							double TotalD = (double) map.get("totalD");
							double totaldurationsum = totalDur + TotalD;

							existing.put("totalD", totaldurationsum);
							existing.put("exceededDurationNew", totalExceedTime);
							existing.put("exceptionvalue", totalExceedTime * 100 / totaldurationsum);

							/*
							 * existing.put( "exceptionvalue", (Double) existing.get("exceptionvalue") +
							 * (Double) map .get("exceptionvalue"));
							 */
						} else {
							detailsMap.put((int) map.get("transporterid"), map);
						}
					}

				} else {
					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("affiliateid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("affiliateid"));

							double exceededDurationNew = (double) existing.get("exceededDurationNew");
							double exceededDuration = (double) map.get("exceededDurationNew");
							double totalExceedTime = exceededDurationNew + exceededDuration;

							double totalDur = (double) existing.get("totalD");
							double TotalD = (double) map.get("totalD");
							double totaldurationsum = totalDur + TotalD;

							existing.put("totalD", totaldurationsum);
							existing.put("exceededDurationNew", totalExceedTime);
							existing.put("exceptionvalue", totalExceedTime * 100 / totaldurationsum);

							/*
							 * existing.put( "exceptionvalue", (Double) existing.get("exceptionvalue") +
							 * (Double) map .get("exceptionvalue"));
							 */

						} else {
							detailsMap.put((int) map.get("affiliateid"), map);
						}
					}

				}
				weeklyList.addAll(detailsMap.values());

			}

		}
		// System.out.println("weeklyRestList->"+weeklyList.toString());
		return weeklyList;

	}

	/* not in use */
	@Override
	public List getViewDetails(String view) throws Exception {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM \"" + view + "\"";
		List details = jdbcTemplate.queryForList(query);

		details.add(jdbcTemplate.queryForMap("show timezone;"));

		Calendar c = Calendar.getInstance();
		TimeZone tz = c.getTimeZone();

		details.add("Current TimeZone is : " + tz.getDisplayName());

		String query2 = "select * from \"sp_driverbehaviourviolations\"(" + 0 + "::int4," + 0 + "::int4," + 0
				+ "::int4," + 0 + "::int4,'2018-02-13','2018-02-13'::date," + 0 + "::int4);";

		List temp = jdbcTemplate.queryForList(query2);

		details.add(temp);

		return details;
	}

	public List getWeeklyDrive(int customerid, int affiliateid, int transporterid, int vehicleid,
			String exceptionlevels, String startDate, String endDate, String language) throws JSONException, Exception {

		String query = "";

		List weeklyList = new ArrayList();
		if (exceptionlevels.equals("0"))
			exceptionlevels = "1,2,3";

		List<WeekDaysBean> dateRanges = CommonUtil.getRangeBetweenDates(java.sql.Date.valueOf(startDate),
				java.sql.Date.valueOf(endDate));

		if (dateRanges.size() > 0) {

			String driversqry = "select v.* from view_driver_veh_trans_aff v join customeraffiliate c on c.affiliateid=v.affiliateid WHERE v.status = 1 ";
			if (vehicleid > 0) {
				driversqry += " and v.vehicleid=" + vehicleid;
			} else if (transporterid > 0) {
				driversqry += " and v.transporterid=" + transporterid;
			} else if (affiliateid > 0) {
				driversqry += " and v.affiliateid=" + affiliateid;
			} else if (customerid > 0) {
				driversqry += " and c.customerid=" + customerid;
			}

			List<Map<String, Object>> drivers = jdbcTemplate.queryForList(driversqry);

			// Taking Weekly Drive

			List<Map<String, Object>> tempList = new ArrayList();

			for (Map<String, Object> driverDet : drivers) {

				int vehId = (int) driverDet.get("vehicleid");
				int driId = (int) driverDet.get("driverid");
				int affId = (int) driverDet.get("affiliateid");
				int transId = (int) driverDet.get("transporterid");

				String thresholdQry = "SELECT thresholdlimit,recordingthreshold,alertthreshold,alarmthreshold FROM parameterconfig "
						+ " WHERE parametertypeid = ? AND status = '1' AND clientaffiliateid = ?";
				Map<String, Object> thresholdDet = null;

				try {

					thresholdDet = jdbcTemplate.queryForMap(thresholdQry, AppConstants.WEEKLYDRIVE, affId);

					if (thresholdDet == null) {
						continue;
					}
				} catch (Exception e) {
					continue;
				}

				double threshold = Double.parseDouble(thresholdDet.get("thresholdlimit").toString());

				double recordingThreshold = Double.parseDouble(thresholdDet.get("recordingthreshold").toString());
				double alertThreshold = Double.parseDouble(thresholdDet.get("alertthreshold").toString());
				double alarmThreshold = Double.parseDouble(thresholdDet.get("alarmthreshold").toString());

				for (WeekDaysBean weekDaysBean : dateRanges) {

					// query =
					// "SELECT e.exceptiontype as
					// exceptiontype,coalesce(e.totalduration,0) as
					// totalduration, cust.name as clientname,e.affiliateid as
					// affiliateid,c.name as affiliatename,e.transporterid as
					// transporterid,t.name as transportername,v.vehicleid as
					// vehicleid,v.vehicledesc as vehicledesc,d.driverid as
					// driverid, d.name as drivername"
					// +
					// " FROM \"continuousdrive\" as e, \"customer\" as
					// cust,\"customeraffiliate\" as c,\"transporter\" as
					// t,\"vehicle\" as v,\"driver\" as d,\"parametertype\" as p
					// "
					// +
					// "where c.customerid=cust.customerid and
					// e.affiliateid=c.affiliateid and v.vehicleid=e.vehicleid
					// and v.transporterid=t.transporterid and
					// d.driverid=e.driverid and
					// e.exceptiontype=concat('',p.parametertypeid)";
					//
					// query += " and e.vehicleid=" + vehId;
					// query += " and e.driverid=" + driId;
					//
					// query += " and e.exceptiontype='" +
					// AppConstants.WEEKLYDRIVE + "'";
					//
					// query += " and DATE(e.enddatetime::timestamp) between '"
					// + weekDaysBean.getStartDate() + "' and '"
					// + weekDaysBean.getEndDate() + "'";
					//
					// query +=
					// " and DATE(e.startdatetime::timestamp) between '" +
					// weekDaysBean.getStartDate() + "' and '"
					// + weekDaysBean.getEndDate() + "'";
					//
					// query += " order by e.startdatetime::timestamp asc";

					query = "select " + affId + " affiliateid," + transId + " transporterid," + driId + " driverid,"
							+ vehId
							+ " vehicleid,vehiclename as vehicledesc,date(enddatetime) as edate,* from sp_weeklydrivedetails("
							+ vehId + "," + driId + ",'" + weekDaysBean.getStartDate() + "','"
							+ weekDaysBean.getEndDate() + "')";

					List<Map<String, Object>> restList = jdbcTemplate.queryForList(query);

					Map weeklyDrive = null;
					if (restList.size() > 0) {

						int ct = 0;

						double totalduration = 0;

						for (Map det : restList) {

							totalduration += Double.parseDouble(det.get("totalduration").toString());

							weeklyDrive = det;
							ct++;

						}

						weeklyDrive.put("alarmcount", 0);
						weeklyDrive.put("alertcount", 0);
						weeklyDrive.put("recordingcount", 0);

						String totalddDurQuery = "select case when totdurn=0 then 0.000001 else totdurn end as totdurn from "
								+ "sp_get_totduration_exp_new('" + weekDaysBean.getStartDate() + "', '"
								+ weekDaysBean.getEndDate() + "', " + "" + vehId + ",0," + transId + " ," + affId
								+ ", 9,ARRAY[" + exceptionlevels + "] )";

						double totalD = jdbcTemplate.queryForObject(totalddDurQuery, Double.class);

						double timeexceeded = 0.0;

						if (totalD > 0.000001) {

							timeexceeded = totalD - threshold;
						}
						weeklyDrive.put("totalD", totalD);
						weeklyDrive.put("totalDurSP", totalduration);
						weeklyDrive.put("exceedT", timeexceeded);

						// double timeexceeded = totalduration - threshold;

						if (timeexceeded >= alarmThreshold) {
							weeklyDrive.put("alarmcount", 1);

							if (exceptionlevels != null && !exceptionlevels.equals("0")
									&& !exceptionlevels.contains("3")) {
								continue;
							}
						} else if (timeexceeded >= alertThreshold) {
							weeklyDrive.put("alertcount", 1);

							if (exceptionlevels != null && !exceptionlevels.equals("0")
									&& !exceptionlevels.contains("2")) {
								continue;
							}
						} else if (timeexceeded >= recordingThreshold) {
							weeklyDrive.put("recordingcount", 1);

							if (exceptionlevels != null && !exceptionlevels.equals("0")
									&& !exceptionlevels.contains("1")) {
								continue;
							}
						} else {
							continue;
						}

						tempList.add(weeklyDrive);

					}
				}

			}

			if (tempList.size() > 0) {
				Map<Integer, Map<String, Object>> detailsMap = new HashMap<>();
				if (vehicleid > 0 || transporterid > 0) {

					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("vehicleid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("vehicleid"));
							existing.put("alarmcount", (int) existing.get("alarmcount") + (int) map.get("alarmcount"));
							existing.put("alertcount", (int) existing.get("alertcount") + (int) map.get("alertcount"));
							existing.put("recordingcount",
									(int) existing.get("recordingcount") + (int) map.get("recordingcount"));
						} else {
							detailsMap.put((int) map.get("vehicleid"), map);
						}
					}

				} else if (affiliateid > 0) {
					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("transporterid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("transporterid"));
							existing.put("alarmcount", (int) existing.get("alarmcount") + (int) map.get("alarmcount"));
							existing.put("alertcount", (int) existing.get("alertcount") + (int) map.get("alertcount"));
							existing.put("recordingcount",
									(int) existing.get("recordingcount") + (int) map.get("recordingcount"));
						} else {
							detailsMap.put((int) map.get("transporterid"), map);
						}
					}

				} else {
					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("affiliateid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("affiliateid"));
							existing.put("alarmcount", (int) existing.get("alarmcount") + (int) map.get("alarmcount"));
							existing.put("alertcount", (int) existing.get("alertcount") + (int) map.get("alertcount"));
							existing.put("recordingcount",
									(int) existing.get("recordingcount") + (int) map.get("recordingcount"));
						} else {
							detailsMap.put((int) map.get("affiliateid"), map);
						}
					}

				}

				weeklyList.addAll(detailsMap.values());

			}

		}
		return weeklyList;

	}

	public List getWeeklyDrive100hrs(int customerid, int affiliateid, int transporterid, int vehicleid,
			String exceptionlevels, String startDate, String endDate, String language) throws JSONException, Exception {

		String query = "";
		if (exceptionlevels.equals("0"))
			exceptionlevels = "1,2,3";
		List weeklyList = new ArrayList();

		List<WeekDaysBean> dateRanges = CommonUtil.getWeekDaysBetweenDates(java.sql.Date.valueOf(startDate),
				java.sql.Date.valueOf(endDate));

		if (dateRanges.size() > 0) {

			String driversqry = "select v.* from view_driver_veh_trans_aff v join customeraffiliate c on c.affiliateid=v.affiliateid WHERE v.status = 1 ";
			if (vehicleid > 0) {
				driversqry += " and v.vehicleid=" + vehicleid;
			} else if (transporterid > 0) {
				driversqry += " and v.transporterid=" + transporterid;
			} else if (affiliateid > 0) {
				driversqry += " and v.affiliateid=" + affiliateid;
			} else if (customerid > 0) {
				driversqry += " and c.customerid=" + customerid;
			}
			List<Map<String, Object>> drivers = jdbcTemplate.queryForList(driversqry);

			// Taking Weekly Drive

			List<Map<String, Object>> tempList = new ArrayList();
			if (transporterid == 0) {
				for (Map<String, Object> driverDet : drivers) {

					int vehId = (int) driverDet.get("vehicleid");
					int driId = (int) driverDet.get("driverid");
					int affId = (int) driverDet.get("affiliateid");
					int transId = (int) driverDet.get("transporterid");

					String thresholdQry = "SELECT thresholdlimit,recordingthreshold,alertthreshold,alarmthreshold FROM parameterconfig "
							+ " WHERE parametertypeid = ? AND status = '1' AND clientaffiliateid = ?";
					Map<String, Object> thresholdDet = null;

					try {

						thresholdDet = jdbcTemplate.queryForMap(thresholdQry, AppConstants.WEEKLYDRIVE, affId);

						if (thresholdDet == null) {
							continue;
						}
					} catch (Exception e) {
						continue;
					}

					double threshold = Double.parseDouble(thresholdDet.get("thresholdlimit").toString());

					double recordingThreshold = Double.parseDouble(thresholdDet.get("recordingthreshold").toString());
					double alertThreshold = Double.parseDouble(thresholdDet.get("alertthreshold").toString());
					double alarmThreshold = Double.parseDouble(thresholdDet.get("alarmthreshold").toString());

					for (WeekDaysBean weekDaysBean : dateRanges) {

						query = "select " + affId + " affiliateid," + transId + " transporterid," + driId
								+ " driverid,vehiclename as vehicledesc,date(enddatetime) as edate,* from sp_weeklydrivedetails("
								+ vehId + "," + driId + ",'" + weekDaysBean.getStartDate() + "','"
								+ weekDaysBean.getEndDate() + "')  order by startdatetime::timestamp asc";

						List<Map<String, Object>> restList = jdbcTemplate.queryForList(query);

						Map weeklyDrive = null;
						if (restList.size() > 0) {

							double totalduration = 0;

							for (Map det : restList) {

								totalduration += Double.parseDouble(det.get("totalduration").toString());

								weeklyDrive = det;
							}
							String totalddDurQuery = "select case when totdurn=0 then 0.000001 else totdurn end as totdurn from "
									+ "sp_get_totduration_exp_new('" + weekDaysBean.getStartDate() + "', '"
									+ weekDaysBean.getEndDate() + "', " + "" + vehId + ",0," + transId + " ," + affId
									+ ", 9,ARRAY[" + exceptionlevels + "] )";

							double totalD = jdbcTemplate.queryForObject(totalddDurQuery, Double.class);

							double exceededDuration = 0.0;
							double exceptionvalue = 0.0;

							if (totalD > 0.000001) {
								exceededDuration = totalD - threshold;
								exceptionvalue = exceededDuration * 100 / totalD;
							}

							weeklyDrive.put("totalD", totalD);
							weeklyDrive.put("exceededDurationNew", exceededDuration);
							weeklyDrive.put("totalDurSP", totalduration);

							if (exceededDuration >= alarmThreshold) {
								weeklyDrive.put("exceptionvalue", exceptionvalue);
								weeklyDrive.put("exceededDurationNew", exceededDuration);
								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("3")) {
									continue;
								}
							} else if (exceededDuration >= alertThreshold) {
								weeklyDrive.put("exceptionvalue", exceptionvalue);
								weeklyDrive.put("exceededDurationNew", exceededDuration);
								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("2")) {
									continue;
								}
							} else if (exceededDuration >= recordingThreshold) {
								weeklyDrive.put("exceptionvalue", exceptionvalue);
								weeklyDrive.put("exceededDurationNew", exceededDuration);
								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("1")) {
									continue;
								}
							} else {
								continue;
							}
							if (exceptionvalue > 0 && exceededDuration > 0) {

								tempList.add(weeklyDrive);
							}
						}
					}

				}

			} else {

				for (Map<String, Object> driverDet : drivers) {

					int vehId = (int) driverDet.get("vehicleid");
					int driId = (int) driverDet.get("driverid");
					int affId = (int) driverDet.get("affiliateid");
					int transId = (int) driverDet.get("transporterid");

					String thresholdQry = "SELECT thresholdlimit,recordingthreshold,alertthreshold,alarmthreshold FROM parameterconfig "
							+ " WHERE parametertypeid = ? AND status = '1' AND clientaffiliateid = ?";
					Map<String, Object> thresholdDet = null;

					try {

						thresholdDet = jdbcTemplate.queryForMap(thresholdQry, AppConstants.WEEKLYDRIVE, affId);

						if (thresholdDet == null) {
							continue;
						}
					} catch (Exception e) {
						continue;
					}

					double threshold = Double.parseDouble(thresholdDet.get("thresholdlimit").toString());

					double recordingThreshold = Double.parseDouble(thresholdDet.get("recordingthreshold").toString());
					double alertThreshold = Double.parseDouble(thresholdDet.get("alertthreshold").toString());
					double alarmThreshold = Double.parseDouble(thresholdDet.get("alarmthreshold").toString());

					for (WeekDaysBean weekDaysBean : dateRanges) {

						query = "select " + affId + " affiliateid," + transId + " transporterid," + driId + " driverid,"
								+ vehId
								+ " vehicleid,vehiclename as vehicledesc,date(enddatetime) as edate,* from sp_weeklydrivedetails("
								+ vehId + "," + driId + ",'" + weekDaysBean.getStartDate() + "','"
								+ weekDaysBean.getEndDate() + "')";

						List<Map<String, Object>> restList = jdbcTemplate.queryForList(query);

						Map weeklyDrive = null;
						if (restList.size() > 0) {

							int ct = 0;

							double totalduration = 0;

							for (Map det : restList) {

								totalduration += Double.parseDouble(det.get("totalduration").toString());

								weeklyDrive = det;
								ct++;

							}
							String totalddDurQuery = "select case when totdurn=0 then 0.000001 else totdurn end as totdurn from "
									+ "sp_get_totduration_exp_new('" + weekDaysBean.getStartDate() + "', '"
									+ weekDaysBean.getEndDate() + "', " + "" + vehId + ",0," + transId + " ," + affId
									+ ", 9,ARRAY[" + exceptionlevels + "] )";

							double totalD = jdbcTemplate.queryForObject(totalddDurQuery, Double.class);

							double timeexceeded = 0.0;
							double exceptionvalue = 0.0;

							if (totalD > 0.000001) {
								timeexceeded = totalD - threshold;
								exceptionvalue = timeexceeded * 100 / totalD;
							}
							weeklyDrive.put("totalD", totalD);
							weeklyDrive.put("exceededDurationNew", timeexceeded);

							if (timeexceeded >= alarmThreshold) {
								weeklyDrive.put("exceptionvalue", exceptionvalue);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("3")) {
									continue;
								}
							} else if (timeexceeded >= alertThreshold) {
								weeklyDrive.put("exceptionvalue", exceptionvalue);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("2")) {
									continue;
								}
							} else if (timeexceeded >= recordingThreshold) {
								weeklyDrive.put("exceptionvalue", exceptionvalue);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("1")) {
									continue;
								}
							} else {
								continue;
							}

							if (exceptionvalue > 0 && timeexceeded > 0) {

								tempList.add(weeklyDrive);
							}

						}
					}

				}
			}

			if (tempList.size() > 0) {
				Map<Integer, Map<String, Object>> detailsMap = new HashMap<>();
				if (vehicleid > 0) {

					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("vehicleid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("vehicleid"));
							existing.put("exceptionvalue",
									(Double) existing.get("exceptionvalue") + (Double) map.get("exceptionvalue"));
						} else {
							detailsMap.put((int) map.get("vehicleid"), map);
						}
					}

				} else if (affiliateid > 0 || transporterid > 0) {
					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("transporterid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("transporterid"));

							double exceededDurationNew = (double) existing.get("exceededDurationNew");
							double exceededDuration = (double) map.get("exceededDurationNew");
							double totalExceedTime = exceededDurationNew + exceededDuration;

							double totalDur = (double) existing.get("totalD");
							double TotalD = (double) map.get("totalD");
							double totaldurationsum = totalDur + TotalD;

							existing.put("totalD", totaldurationsum);
							existing.put("exceededDurationNew", totalExceedTime);
							existing.put("exceptionvalue", totalExceedTime * 100 / totaldurationsum);
							/*
							 * existing.put( "exceptionvalue", (Double) existing.get("exceptionvalue") +
							 * (Double) map .get("exceptionvalue"));
							 */
						} else {
							detailsMap.put((int) map.get("transporterid"), map);
						}
					}

				} else {
					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("affiliateid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("affiliateid"));
							/*
							 * existing.put( "exceptionvalue", (Double) existing.get("exceptionvalue") +
							 * (Double) map .get("exceptionvalue"));
							 */
							double exceededDurationNew = (double) existing.get("exceededDurationNew");
							double exceededDuration = (double) map.get("exceededDurationNew");
							double totalExceedTime = exceededDurationNew + exceededDuration;

							double totalDur = (double) existing.get("totalD");
							double TotalD = (double) map.get("totalD");
							double totaldurationsum = totalDur + TotalD;

							existing.put("totalD", totaldurationsum);
							existing.put("exceededDurationNew", totalExceedTime);
							existing.put("exceptionvalue", totalExceedTime * 100 / totaldurationsum);
						} else {
							detailsMap.put((int) map.get("affiliateid"), map);
						}
					}

				}

				weeklyList.addAll(detailsMap.values());

			}

		}
		// System.out.println("weeklyList->"+weeklyList.toString());
		return weeklyList;

	}

	public List getWeeklyDriveAffDriver(int customerid, int affiliateid, int transporterid, int vehicleid,
			String exceptionlevels, String startDate, String endDate, String language) throws JSONException, Exception {

		String query = "";

		List weeklyList = new ArrayList();
		if (exceptionlevels.equals("0"))
			exceptionlevels = "1,2,3";

		List<WeekDaysBean> dateRanges = CommonUtil.getWeekDaysBetweenDates(java.sql.Date.valueOf(startDate),
				java.sql.Date.valueOf(endDate));

		if (dateRanges.size() > 0) { // bypassing check because wr&wd is now inserted for all days, not weekly

			String driversqry = "select * from sp_get_driver_ids_from_cd_of_wdt(" + vehicleid + "," + transporterid
					+ "," + affiliateid + "," + customerid + ",'" + startDate + "','" + endDate + "')";
			driversqry += " order by affiliateid,driverid";

			List<Map<String, Object>> drivers = jdbcTemplate.queryForList(driversqry);

			// Taking Weekly Drive

			List<Map<String, Object>> tempList = new ArrayList();

			for (Map<String, Object> driverDet : drivers) {

				// int vehId = (int) driverDet.get("vehicleid");
				int driId = (int) driverDet.get("driverid");
				int affId = (int) driverDet.get("affiliateid");
				// int transId = (int) driverDet.get("transporterid");

				String thresholdQry = "SELECT thresholdlimit,recordingthreshold,alertthreshold,alarmthreshold FROM parameterconfig "
						+ " WHERE parametertypeid = ? AND status = '1' AND clientaffiliateid = ?";
				Map<String, Object> thresholdDet = null;

				try {

					thresholdDet = jdbcTemplate.queryForMap(thresholdQry, AppConstants.WEEKLYDRIVE, affId);

					if (thresholdDet == null) {
						continue;
					}
				} catch (Exception e) {
					continue;
				}

				double threshold = Double.parseDouble(thresholdDet.get("thresholdlimit").toString());

				double recordingThreshold = Double.parseDouble(thresholdDet.get("recordingthreshold").toString());
				double alertThreshold = Double.parseDouble(thresholdDet.get("alertthreshold").toString());
				double alarmThreshold = Double.parseDouble(thresholdDet.get("alarmthreshold").toString());

				for (WeekDaysBean weekDaysBean : dateRanges) {

					// query =
					// "SELECT e.exceptiontype as
					// exceptiontype,coalesce(e.totalduration,0) as
					// totalduration, cust.name as clientname,e.affiliateid as
					// affiliateid,c.name as affiliatename,e.transporterid as
					// transporterid,t.name as transportername,v.vehicleid as
					// vehicleid,v.vehicledesc as vehicledesc,d.driverid as
					// driverid, d.name as drivername"
					// +
					// " FROM \"continuousdrive\" as e, \"customer\" as
					// cust,\"customeraffiliate\" as c,\"transporter\" as
					// t,\"vehicle\" as v,\"driver\" as d,\"parametertype\" as p
					// "
					// +
					// "where c.customerid=cust.customerid and
					// e.affiliateid=c.affiliateid and v.vehicleid=e.vehicleid
					// and v.transporterid=t.transporterid and
					// d.driverid=e.driverid and
					// e.exceptiontype=concat('',p.parametertypeid)";
					//
					// query += " and e.vehicleid=" + vehId;
					// query += " and e.driverid=" + driId;
					//
					// query += " and e.exceptiontype='" +
					// AppConstants.WEEKLYDRIVE + "'";
					//
					// query += " and DATE(e.enddatetime::timestamp) between '"
					// + weekDaysBean.getStartDate() + "' and '"
					// + weekDaysBean.getEndDate() + "'";
					//
					// query +=
					// " and DATE(e.startdatetime::timestamp) between '" +
					// weekDaysBean.getStartDate() + "' and '"
					// + weekDaysBean.getEndDate() + "'";
					//
					// query += " order by e.startdatetime::timestamp asc";

					/*
					 * query = "select " + affId + " affiliateid," + transId + " transporterid," +
					 * driId + " driverid," + vehId +
					 * " vehicleid,vehiclename as vehicledesc,date(enddatetime) as edate,* from sp_weeklydrivedetails("
					 * + vehId + "," + driId + ",'" + weekDaysBean.getStartDate() + "','" +
					 * weekDaysBean.getEndDate() + "')";
					 */

					query = "select " + affId
							+ " affiliateid,date(enddatetime) as edate,* from sp_weeklydrivedetails_driverbased("
							+ affId + "," + driId + ",'" + weekDaysBean.getStartDate() + "','"
							+ weekDaysBean.getEndDate() + "')";

					List<Map<String, Object>> restList = jdbcTemplate.queryForList(query);

					Map weeklyDrive = null;
					if (restList.size() > 0) {

						int ct = 0;

						double totalduration = 0;

						for (Map det : restList) {

							totalduration += Double.parseDouble(det.get("totalduration").toString());

							weeklyDrive = det;
							ct++;

						}

						weeklyDrive.put("alarmcount", 0);
						weeklyDrive.put("alertcount", 0);
						weeklyDrive.put("recordingcount", 0);

						/*
						 * String totalddDurQuery =
						 * "select case when totdurn=0 then 0.000001 else totdurn end as totdurn from "
						 * + "sp_get_totduration_exp_new('" + weekDaysBean.getStartDate() + "', '" +
						 * weekDaysBean.getEndDate() + "', " + "" + vehId + ",0," + transId + " ," +
						 * affId + ", 9,ARRAY[" + exceptionlevels + "] )";
						 *
						 * double totalD = jdbcTemplate.queryForObject( totalddDurQuery, Double.class);
						 */

						double timeexceeded = 0.0;

						if (totalduration > 0.000001) {

							timeexceeded = totalduration - threshold;
						}
						weeklyDrive.put("totalD", totalduration);
						weeklyDrive.put("totalDurSP", totalduration);
						weeklyDrive.put("exceedT", timeexceeded);

						// double timeexceeded = totalduration - threshold;

						if (timeexceeded >= alarmThreshold) {
							weeklyDrive.put("alarmcount", 1);

							if (exceptionlevels != null && !exceptionlevels.equals("0")
									&& !exceptionlevels.contains("3")) {
								continue;
							}
						} else if (timeexceeded >= alertThreshold) {
							weeklyDrive.put("alertcount", 1);

							if (exceptionlevels != null && !exceptionlevels.equals("0")
									&& !exceptionlevels.contains("2")) {
								continue;
							}
						} else if (timeexceeded >= recordingThreshold) {
							weeklyDrive.put("recordingcount", 1);

							if (exceptionlevels != null && !exceptionlevels.equals("0")
									&& !exceptionlevels.contains("1")) {
								continue;
							}
						} else {
							continue;
						}

						tempList.add(weeklyDrive);

					}
				}

			}

			if (tempList.size() > 0) {
				Map<Integer, Map<String, Object>> detailsMap = new HashMap<>();
				if (vehicleid > 0 || transporterid > 0) {

					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("vehicleid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("vehicleid"));
							existing.put("alarmcount", (int) existing.get("alarmcount") + (int) map.get("alarmcount"));
							existing.put("alertcount", (int) existing.get("alertcount") + (int) map.get("alertcount"));
							existing.put("recordingcount",
									(int) existing.get("recordingcount") + (int) map.get("recordingcount"));
						} else {
							detailsMap.put((int) map.get("vehicleid"), map);
						}
					}

				} else if (affiliateid > 0) {
					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("transporterid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("transporterid"));
							existing.put("alarmcount", (int) existing.get("alarmcount") + (int) map.get("alarmcount"));
							existing.put("alertcount", (int) existing.get("alertcount") + (int) map.get("alertcount"));
							existing.put("recordingcount",
									(int) existing.get("recordingcount") + (int) map.get("recordingcount"));
						} else {
							detailsMap.put((int) map.get("transporterid"), map);
						}
					}

				} else {
					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("affiliateid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("affiliateid"));
							existing.put("alarmcount", (int) existing.get("alarmcount") + (int) map.get("alarmcount"));
							existing.put("alertcount", (int) existing.get("alertcount") + (int) map.get("alertcount"));
							existing.put("recordingcount",
									(int) existing.get("recordingcount") + (int) map.get("recordingcount"));
						} else {
							detailsMap.put((int) map.get("affiliateid"), map);
						}
					}

				}

				weeklyList.addAll(detailsMap.values());

			}

		}
		return weeklyList;

	}

	public List getWeeklyDriveTransporter(int customerid, int affiliateid, int transporterid, int vehicleid,
			String exceptionlevels, String startDate, String endDate, String language) throws JSONException, Exception {

		String query = "";

		List weeklyList = new ArrayList();
		if (exceptionlevels.equals("0"))
			exceptionlevels = "1,2,3";

		List<WeekDaysBean> dateRanges = CommonUtil.getWeekDaysBetweenDates(java.sql.Date.valueOf(startDate),
				java.sql.Date.valueOf(endDate));

		if (dateRanges.size() > 0) {// bypassing check because wr&wd is now inserted for all days, not weekly

			String driversqry = "select * from sp_get_driver_ids_from_cd_of_wdt_forgraph(" + vehicleid + ","
					+ transporterid + "," + affiliateid + "," + customerid + ",'" + startDate + "','" + endDate + "')";
			driversqry += " order by affiliateid,transporterid,driverid";

			List<Map<String, Object>> drivers = jdbcTemplate.queryForList(driversqry);

			// Taking Weekly Drive

			List<Map<String, Object>> tempList = new ArrayList();

			for (Map<String, Object> driverDet : drivers) {

				// int vehId = (int) driverDet.get("vehicleid");
				int driId = (int) driverDet.get("driverid");
				int affId = (int) driverDet.get("affiliateid");
				int transId = (int) driverDet.get("transporterid");

				String thresholdQry = "SELECT thresholdlimit,recordingthreshold,alertthreshold,alarmthreshold FROM parameterconfig "
						+ " WHERE parametertypeid = ? AND status = '1' AND clientaffiliateid = ?";
				Map<String, Object> thresholdDet = null;

				try {

					thresholdDet = jdbcTemplate.queryForMap(thresholdQry, AppConstants.WEEKLYDRIVE, affId);

					if (thresholdDet == null) {
						continue;
					}
				} catch (Exception e) {
					continue;
				}

				double threshold = Double.parseDouble(thresholdDet.get("thresholdlimit").toString());

				double recordingThreshold = Double.parseDouble(thresholdDet.get("recordingthreshold").toString());
				double alertThreshold = Double.parseDouble(thresholdDet.get("alertthreshold").toString());
				double alarmThreshold = Double.parseDouble(thresholdDet.get("alarmthreshold").toString());

				for (WeekDaysBean weekDaysBean : dateRanges) {

					// query =
					// "SELECT e.exceptiontype as
					// exceptiontype,coalesce(e.totalduration,0) as
					// totalduration, cust.name as clientname,e.affiliateid as
					// affiliateid,c.name as affiliatename,e.transporterid as
					// transporterid,t.name as transportername,v.vehicleid as
					// vehicleid,v.vehicledesc as vehicledesc,d.driverid as
					// driverid, d.name as drivername"
					// +
					// " FROM \"continuousdrive\" as e, \"customer\" as
					// cust,\"customeraffiliate\" as c,\"transporter\" as
					// t,\"vehicle\" as v,\"driver\" as d,\"parametertype\" as p
					// "
					// +
					// "where c.customerid=cust.customerid and
					// e.affiliateid=c.affiliateid and v.vehicleid=e.vehicleid
					// and v.transporterid=t.transporterid and
					// d.driverid=e.driverid and
					// e.exceptiontype=concat('',p.parametertypeid)";
					//
					// query += " and e.vehicleid=" + vehId;
					// query += " and e.driverid=" + driId;
					//
					// query += " and e.exceptiontype='" +
					// AppConstants.WEEKLYDRIVE + "'";
					//
					// query += " and DATE(e.enddatetime::timestamp) between '"
					// + weekDaysBean.getStartDate() + "' and '"
					// + weekDaysBean.getEndDate() + "'";
					//
					// query +=
					// " and DATE(e.startdatetime::timestamp) between '" +
					// weekDaysBean.getStartDate() + "' and '"
					// + weekDaysBean.getEndDate() + "'";
					//
					// query += " order by e.startdatetime::timestamp asc";

					/*
					 * query = "select " + affId + " affiliateid," + transId + " transporterid," +
					 * driId + " driverid," + vehId +
					 * " vehicleid,vehiclename as vehicledesc,date(enddatetime) as edate,* from sp_weeklydrivedetails("
					 * + vehId + "," + driId + ",'" + weekDaysBean.getStartDate() + "','" +
					 * weekDaysBean.getEndDate() + "')";
					 */

					query = "select " + affId + " affiliateid," + transId
							+ " transporterid,date(enddatetime) as edate,* from sp_weeklydrivedetails_driverbased_forgraph("
							+ affId + ",0," + driId + ",'" + weekDaysBean.getStartDate() + "','"
							+ weekDaysBean.getEndDate() + "')";

					List<Map<String, Object>> restList = jdbcTemplate.queryForList(query);

					Map weeklyDrive = null;
					if (restList.size() > 0) {

						int ct = 0;

						double totalduration = 0;

						for (Map det : restList) {

							totalduration += Double.parseDouble(det.get("totalduration").toString());

							weeklyDrive = det;
							ct++;

						}

						weeklyDrive.put("alarmcount", 0);
						weeklyDrive.put("alertcount", 0);
						weeklyDrive.put("recordingcount", 0);

						/*
						 * String totalddDurQuery =
						 * "select case when totdurn=0 then 0.000001 else totdurn end as totdurn from "
						 * + "sp_get_totduration_exp_new('" + weekDaysBean.getStartDate() + "', '" +
						 * weekDaysBean.getEndDate() + "', " + "" + vehId + ",0," + transId + " ," +
						 * affId + ", 9,ARRAY[" + exceptionlevels + "] )";
						 *
						 * double totalD = jdbcTemplate.queryForObject( totalddDurQuery, Double.class);
						 */

						double timeexceeded = 0.0;

						if (totalduration > 0.000001) {

							timeexceeded = totalduration - threshold;
						}
						weeklyDrive.put("totalD", totalduration);
						weeklyDrive.put("totalDurSP", totalduration);
						weeklyDrive.put("exceedT", timeexceeded);

						// double timeexceeded = totalduration - threshold;

						if (timeexceeded >= alarmThreshold) {
							weeklyDrive.put("alarmcount", 1);

							if (exceptionlevels != null && !exceptionlevels.equals("0")
									&& !exceptionlevels.contains("3")) {
								continue;
							}
						} else if (timeexceeded >= alertThreshold) {
							weeklyDrive.put("alertcount", 1);

							if (exceptionlevels != null && !exceptionlevels.equals("0")
									&& !exceptionlevels.contains("2")) {
								continue;
							}
						} else if (timeexceeded >= recordingThreshold) {
							weeklyDrive.put("recordingcount", 1);

							if (exceptionlevels != null && !exceptionlevels.equals("0")
									&& !exceptionlevels.contains("1")) {
								continue;
							}
						} else {
							continue;
						}

						tempList.add(weeklyDrive);

					}
				}

			}

			if (tempList.size() > 0) {
				Map<Integer, Map<String, Object>> detailsMap = new HashMap<>();
				if (vehicleid > 0 || transporterid > 0) {

					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("vehicleid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("vehicleid"));
							existing.put("alarmcount", (int) existing.get("alarmcount") + (int) map.get("alarmcount"));
							existing.put("alertcount", (int) existing.get("alertcount") + (int) map.get("alertcount"));
							existing.put("recordingcount",
									(int) existing.get("recordingcount") + (int) map.get("recordingcount"));
						} else {
							detailsMap.put((int) map.get("vehicleid"), map);
						}
					}

				} else if (affiliateid > 0) {
					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("transporterid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("transporterid"));
							existing.put("alarmcount", (int) existing.get("alarmcount") + (int) map.get("alarmcount"));
							existing.put("alertcount", (int) existing.get("alertcount") + (int) map.get("alertcount"));
							existing.put("recordingcount",
									(int) existing.get("recordingcount") + (int) map.get("recordingcount"));
						} else {
							detailsMap.put((int) map.get("transporterid"), map);
						}
					}

				} else {
					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("affiliateid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("affiliateid"));
							existing.put("alarmcount", (int) existing.get("alarmcount") + (int) map.get("alarmcount"));
							existing.put("alertcount", (int) existing.get("alertcount") + (int) map.get("alertcount"));
							existing.put("recordingcount",
									(int) existing.get("recordingcount") + (int) map.get("recordingcount"));
						} else {
							detailsMap.put((int) map.get("affiliateid"), map);
						}
					}

				}

				weeklyList.addAll(detailsMap.values());

			}

		}
		return weeklyList;

	}

	public List getWeeklyRest(int customerid, int affiliateid, int transporterid, int vehicleid, String exceptionlevels,
			String startDate, String endDate, String language) {

		List weeklyList = new ArrayList();

		String query = "";

		if (exceptionlevels.equals("0"))
			exceptionlevels = "1,2,3";
		List<WeekDaysBean> dateRanges = CommonUtil.getRangeBetweenDates(java.sql.Date.valueOf(startDate),
				java.sql.Date.valueOf(endDate));

		// Taking Weekly Rest

		if (dateRanges.size() > 0) {

			/*
			 * String driversqry =
			 * "select v.* from view_driver_veh_trans_aff v join customeraffiliate c on c.affiliateid=v.affiliateid WHERE v.status = 1 "
			 * ; if (vehicleid > 0) { driversqry += " and v.vehicleid=" + vehicleid; } else
			 * if (transporterid > 0) { driversqry += " and v.transporterid=" +
			 * transporterid; } else if (affiliateid > 0) { driversqry +=
			 * " and v.affiliateid=" + affiliateid; } else if (customerid > 0) { driversqry
			 * += " and c.customerid=" + customerid; }
			 */
			String driversqry = "select * from sp_get_vehdriver_ids_from_cd_of_wrt(" + vehicleid + "," + transporterid
					+ "," + affiliateid + "," + customerid + ",'" + startDate + "','" + endDate + "')";
			driversqry += " order by affiliateid,transporterid,vehicleid";

			// System.out.println(driversqry);

			List<Map<String, Object>> drivers = jdbcTemplate.queryForList(driversqry);

			List<Map<String, Object>> tempList = new ArrayList();

			for (Map<String, Object> driverDet : drivers) {

				int vehId = (int) driverDet.get("vehicleid");
				int driId = (int) driverDet.get("driverid");
				int affId = (int) driverDet.get("affiliateid");
				int transId = (int) driverDet.get("transporterid");

				String thresholdQry = "SELECT thresholdlimit,recordingthreshold,alertthreshold,alarmthreshold FROM parameterconfig "
						+ " WHERE parametertypeid = ? AND status = '1' AND clientaffiliateid = ?";
				Map<String, Object> thresholdDet = null;

				try {
					// System.out.println(thresholdQry);
					thresholdDet = jdbcTemplate.queryForMap(thresholdQry, AppConstants.WEEKLYREST, affId);

					if (thresholdDet == null) {
						continue;
					}
				} catch (Exception e) {
					continue;
				}

				double threshold = Double.parseDouble(thresholdDet.get("thresholdlimit").toString());

				double recordingThreshold = Double.parseDouble(thresholdDet.get("recordingthreshold").toString());
				double alertThreshold = Double.parseDouble(thresholdDet.get("alertthreshold").toString());
				double alarmThreshold = Double.parseDouble(thresholdDet.get("alarmthreshold").toString());

				for (WeekDaysBean weekDaysBean : dateRanges) {

					// query =
					// "SELECT e.exceptiontype as
					// exceptiontype,coalesce(e.totalduration,0) as
					// totalduration, cust.name as clientname,e.affiliateid as
					// affiliateid,c.name as affiliatename,e.transporterid as
					// transporterid,t.name as transportername,v.vehicleid as
					// vehicleid,v.vehicledesc as vehicledesc,d.driverid as
					// driverid, d.name as drivername"
					// +
					// " FROM \"continuousdrive\" as e, \"customer\" as
					// cust,\"customeraffiliate\" as c,\"transporter\" as
					// t,\"vehicle\" as v,\"driver\" as d,\"parametertype\" as p
					// "
					// +
					// "where c.customerid=cust.customerid and
					// e.affiliateid=c.affiliateid and v.vehicleid=e.vehicleid
					// and v.transporterid=t.transporterid and
					// d.driverid=e.driverid and
					// e.exceptiontype=concat('',p.parametertypeid)";
					//
					// query += " and e.vehicleid=" + vehId;
					// query += " and e.driverid=" + driId;
					//
					// query += " and e.exceptiontype='" +
					// AppConstants.WEEKLYREST + "'";
					//
					// query += " and DATE(e.enddatetime::timestamp) between '"
					// + weekDaysBean.getStartDate() + "' and '"
					// + weekDaysBean.getEndDate() + "'";
					//
					// query +=
					// " and DATE(e.startdatetime::timestamp) between '" +
					// weekDaysBean.getStartDate() + "' and '"
					// + weekDaysBean.getEndDate() + "'";
					//
					// query += " order by e.startdatetime::timestamp asc";

					query = "select " + affId + " affiliateid," + transId + " transporterid," + driId + " driverid,"
							+ vehId
							+ " vehicleid,vehiclename as vehicledesc,date(enddatetime) as edate,* from sp_weeklyrestdetails("
							+ vehId + "," + driId + ",'" + weekDaysBean.getStartDate() + "','"
							+ weekDaysBean.getEndDate() + "')";

					// System.out.println(query);

					List<Map<String, Object>> restList = jdbcTemplate.queryForList(query);

					Map longestRest = null;
					if (restList.size() > 0) {

						double longestRestDur = 0;
						for (Map det : restList) {
							if (longestRestDur < Double.parseDouble(det.get("totalduration").toString())) {
								longestRestDur = Double.parseDouble(det.get("totalduration").toString());
								longestRest = det;
							}
						}
						if (longestRest != null) {
							longestRest.put("alarmcount", 0);
							longestRest.put("alertcount", 0);
							longestRest.put("recordingcount", 0);

							double exceededTim = threshold - longestRestDur;

							if (exceededTim >= alarmThreshold) {
								longestRest.put("alarmcount", 1);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("3")) {
									continue;
								}

							} else if (exceededTim >= alertThreshold) {
								longestRest.put("alertcount", 1);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("2")) {
									continue;
								}

							} else if (exceededTim >= recordingThreshold) {
								longestRest.put("recordingcount", 1);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("1")) {
									continue;
								}

							} else {
								continue;
							}

							tempList.add(longestRest);
						}

					}
				}

			}

			if (tempList.size() > 0) {
				Map<Integer, Map<String, Object>> detailsMap = new HashMap<>();
				if (vehicleid > 0 || transporterid > 0) {

					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("vehicleid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("vehicleid"));
							existing.put("alarmcount", (int) existing.get("alarmcount") + (int) map.get("alarmcount"));
							existing.put("alertcount", (int) existing.get("alertcount") + (int) map.get("alertcount"));
							existing.put("recordingcount",
									(int) existing.get("recordingcount") + (int) map.get("recordingcount"));
						} else {
							detailsMap.put((int) map.get("vehicleid"), map);
						}
					}

				} else if (affiliateid > 0) { /*
												 * in dashboard vehicle and transporter wise listing is enough
												 */
					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("transporterid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("transporterid"));

							existing.put("alarmcount", (int) existing.get("alarmcount") + (int) map.get("alarmcount"));
							existing.put("alertcount", (int) existing.get("alertcount") + (int) map.get("alertcount"));
							existing.put("recordingcount",
									(int) existing.get("recordingcount") + (int) map.get("recordingcount"));

						} else {
							detailsMap.put((int) map.get("transporterid"), map);
						}
					}

				} else {
					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("affiliateid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("affiliateid"));

							existing.put("alarmcount", (int) existing.get("alarmcount") + (int) map.get("alarmcount"));
							existing.put("alertcount", (int) existing.get("alertcount") + (int) map.get("alertcount"));
							existing.put("recordingcount",
									(int) existing.get("recordingcount") + (int) map.get("recordingcount"));

						} else {
							detailsMap.put((int) map.get("affiliateid"), map);
						}
					}

				}
				weeklyList.addAll(detailsMap.values());

			}

		}

		return weeklyList;

	}

	public List getWeeklyRest100hrs(int customerid, int affiliateid, int transporterid, int vehicleid,
			String exceptionlevels, String startDate, String endDate, String language) {

		List weeklyList = new ArrayList();

		String query = "";

		List<WeekDaysBean> dateRanges = CommonUtil.getWeekDaysBetweenDates(java.sql.Date.valueOf(startDate),
				java.sql.Date.valueOf(endDate));

		// Taking Weekly Rest

		if (dateRanges.size() > 0) {

			/*
			 * String driversqry =
			 * "select v.* from view_driver_veh_trans_aff v join customeraffiliate c on c.affiliateid=v.affiliateid WHERE v.status = 1 "
			 * ; if (vehicleid > 0) { driversqry += " and v.vehicleid=" + vehicleid; } else
			 * if (transporterid > 0) { driversqry += " and v.transporterid=" +
			 * transporterid; } else if (affiliateid > 0) { driversqry +=
			 * " and v.affiliateid=" + affiliateid; } else if (customerid > 0) { driversqry
			 * += " and c.customerid=" + customerid; }
			 */
			String driversqry = "select * from sp_get_vehdriver_ids_from_cd_of_wrt(" + vehicleid + "," + transporterid
					+ "," + affiliateid + "," + customerid + ",'" + startDate + "','" + endDate + "')";
			driversqry += " order by affiliateid,transporterid,vehicleid";
			List<Map<String, Object>> drivers = jdbcTemplate.queryForList(driversqry);

			List<Map<String, Object>> tempList = new ArrayList();

			if (exceptionlevels.equals("0"))
				exceptionlevels = "1,2,3";
			if (transporterid == 0) {

				for (Map<String, Object> driverDet : drivers) {

					int vehId = (int) driverDet.get("vehicleid");
					int driId = (int) driverDet.get("driverid");
					int affId = (int) driverDet.get("affiliateid");
					int transId = (int) driverDet.get("transporterid");

					String thresholdQry = "SELECT thresholdlimit,recordingthreshold,alertthreshold,alarmthreshold FROM parameterconfig "
							+ " WHERE parametertypeid = ? AND status = '1' AND clientaffiliateid = ?";
					Map<String, Object> thresholdDet = null;

					try {

						thresholdDet = jdbcTemplate.queryForMap(thresholdQry, AppConstants.WEEKLYREST, affId);

						if (thresholdDet == null) {
							continue;
						}
					} catch (Exception e) {
						continue;
					}

					double threshold = Double.parseDouble(thresholdDet.get("thresholdlimit").toString());
					double recordingThreshold = Double.parseDouble(thresholdDet.get("recordingthreshold").toString());
					double alertThreshold = Double.parseDouble(thresholdDet.get("alertthreshold").toString());
					double alarmThreshold = Double.parseDouble(thresholdDet.get("alarmthreshold").toString());

					for (WeekDaysBean weekDaysBean : dateRanges) {

						query = "select " + affId + " affiliateid," + transId + " transporterid," + driId
								+ " driverid,vehiclename as vehicledesc,date(enddatetime) as edate,* from sp_weeklyrestdetails("
								+ vehId + "," + driId + ",'" + weekDaysBean.getStartDate() + "','"
								+ weekDaysBean.getEndDate() + "')";

						List<Map<String, Object>> restList = jdbcTemplate.queryForList(query);

						Map longestRest = null;
						if (restList.size() > 0) {

							double longestRestDur = 0;
							double totalduration = 0;
							for (Map det : restList) {

								if (longestRestDur < Double.parseDouble(det.get("totalduration").toString())) {
									longestRestDur = Double.parseDouble(det.get("totalduration").toString());
									longestRest = det;
								}
								totalduration += Double.parseDouble(det.get("totalduration").toString());
							}
							String totalddDurQuery = "select case when totdurn=0 then 0.000001 else totdurn end as totdurn from "
									+ "sp_get_totduration_exp_new('" + weekDaysBean.getStartDate() + "', '"
									+ weekDaysBean.getEndDate() + "', " + "" + vehId + ",0," + transId + " ," + affId
									+ ", 8,ARRAY[" + exceptionlevels + "] )";

							double totalD = jdbcTemplate.queryForObject(totalddDurQuery, Double.class);
							double exceededTim = 0.0;
							double exceptionvalue = 0.0;

							if (totalD > 0.000001) {
								exceededTim = threshold - longestRestDur;

								exceptionvalue = exceededTim * 100 / totalD;
							}

							if (exceededTim >= alarmThreshold) {
								longestRest.put("exceptionvalue", exceptionvalue);
								longestRest.put("exceededDurationNew", exceededTim);
								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("3")) {
									continue;
								}

							} else if (exceededTim >= alertThreshold) {
								longestRest.put("exceptionvalue", exceptionvalue);
								longestRest.put("exceededDurationNew", exceededTim);
								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("2")) {
									continue;
								}

							} else if (exceededTim >= recordingThreshold) {
								longestRest.put("exceptionvalue", exceptionvalue);
								longestRest.put("exceededDurationNew", exceededTim);
								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("1")) {
									continue;
								}

							} else {
								continue;
							}
							longestRest.put("exceededDurationNew", exceededTim);
							longestRest.put("totalD", totalD);
							longestRest.put("totalDurSP", totalduration);
							longestRest.put("exceedT", exceededTim);

							tempList.add(longestRest);

						}
					}

				}

			} else {
				for (Map<String, Object> driverDet : drivers) {

					int vehId = (int) driverDet.get("vehicleid");
					int driId = (int) driverDet.get("driverid");
					int affId = (int) driverDet.get("affiliateid");
					int transId = (int) driverDet.get("transporterid");

					String thresholdQry = "SELECT thresholdlimit,recordingthreshold,alertthreshold,alarmthreshold FROM parameterconfig "
							+ " WHERE parametertypeid = ? AND status = '1' AND clientaffiliateid = ?";
					Map<String, Object> thresholdDet = null;

					try {

						thresholdDet = jdbcTemplate.queryForMap(thresholdQry, AppConstants.WEEKLYREST, affId);

						if (thresholdDet == null) {
							continue;
						}
					} catch (Exception e) {
						continue;
					}

					double threshold = Double.parseDouble(thresholdDet.get("thresholdlimit").toString());

					double recordingThreshold = Double.parseDouble(thresholdDet.get("recordingthreshold").toString());
					double alertThreshold = Double.parseDouble(thresholdDet.get("alertthreshold").toString());
					double alarmThreshold = Double.parseDouble(thresholdDet.get("alarmthreshold").toString());

					for (WeekDaysBean weekDaysBean : dateRanges) {

						query = "select " + affId + " affiliateid," + transId + " transporterid," + driId + " driverid,"
								+ vehId
								+ " vehicleid,vehiclename as vehicledesc,date(enddatetime) as edate,* from sp_weeklyrestdetails("
								+ vehId + "," + driId + ",'" + weekDaysBean.getStartDate() + "','"
								+ weekDaysBean.getEndDate() + "')";

						List<Map<String, Object>> restList = jdbcTemplate.queryForList(query);

						Map longestRest = null;
						if (restList.size() > 0) {

							double longestRestDur = 0;
							double totalduration = 0;
							for (Map det : restList) {
								if (longestRestDur < Double.parseDouble(det.get("totalduration").toString())) {
									longestRestDur = Double.parseDouble(det.get("totalduration").toString());
									longestRest = det;
								}
								totalduration += Double.parseDouble(det.get("totalduration").toString());
							}

							String totalddDurQuery = "select case when totdurn=0 then 0.000001 else totdurn end as totdurn from "
									+ "sp_get_totduration_exp_new('" + weekDaysBean.getStartDate() + "', '"
									+ weekDaysBean.getEndDate() + "', " + "" + vehId + ",0," + transId + " ," + affId
									+ ", 8,ARRAY[" + exceptionlevels + "] )";

							double totalD = jdbcTemplate.queryForObject(totalddDurQuery, Double.class);

							double exceededTim = 0.0;
							double exceptionvalue = 0.0;

							if (totalD > 0.000001) {
								exceededTim = threshold - longestRestDur;

								exceptionvalue = exceededTim * 100 / totalD;
							}

							if (exceededTim >= alarmThreshold) {
								longestRest.put("exceptionvalue", exceptionvalue);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("3")) {
									continue;
								}

							} else if (exceededTim >= alertThreshold) {
								longestRest.put("exceptionvalue", exceptionvalue);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("2")) {
									continue;
								}

							} else if (exceededTim >= recordingThreshold) {
								longestRest.put("exceptionvalue", exceptionvalue);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("1")) {
									continue;
								}

							} else {
								continue;
							}

							longestRest.put("exceededDurationNew", exceededTim);
							longestRest.put("totalD", totalD);
							tempList.add(longestRest);

						}
					}

				}
			}

			if (tempList.size() > 0) {
				Map<Integer, Map<String, Object>> detailsMap = new HashMap<>();
				if (vehicleid > 0) {

					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("vehicleid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("vehicleid"));
							existing.put("exceptionvalue",
									(Double) existing.get("exceptionvalue") + (Double) map.get("exceptionvalue"));
						} else {
							detailsMap.put((int) map.get("vehicleid"), map);
						}
					}

				} else if (affiliateid > 0 || transporterid > 0) {
					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("transporterid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("transporterid"));

							double exceededDurationNew = (double) existing.get("exceededDurationNew");
							double exceededDuration = (double) map.get("exceededDurationNew");
							double totalExceedTime = exceededDurationNew + exceededDuration;

							double totalDur = (double) existing.get("totalD");
							double TotalD = (double) map.get("totalD");
							double totaldurationsum = totalDur + TotalD;

							existing.put("totalD", totaldurationsum);
							existing.put("exceededDurationNew", totalExceedTime);
							existing.put("exceptionvalue", totalExceedTime * 100 / totaldurationsum);

							/*
							 * existing.put( "exceptionvalue", (Double) existing.get("exceptionvalue") +
							 * (Double) map .get("exceptionvalue"));
							 */
						} else {
							detailsMap.put((int) map.get("transporterid"), map);
						}
					}

				} else {
					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("affiliateid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("affiliateid"));

							double exceededDurationNew = (double) existing.get("exceededDurationNew");
							double exceededDuration = (double) map.get("exceededDurationNew");
							double totalExceedTime = exceededDurationNew + exceededDuration;

							double totalDur = (double) existing.get("totalD");
							double TotalD = (double) map.get("totalD");
							double totaldurationsum = totalDur + TotalD;

							existing.put("totalD", totaldurationsum);
							existing.put("exceededDurationNew", totalExceedTime);
							existing.put("exceptionvalue", totalExceedTime * 100 / totaldurationsum);

							/*
							 * existing.put( "exceptionvalue", (Double) existing.get("exceptionvalue") +
							 * (Double) map .get("exceptionvalue"));
							 */

						} else {
							detailsMap.put((int) map.get("affiliateid"), map);
						}
					}

				}
				weeklyList.addAll(detailsMap.values());

			}

		}
		// System.out.println("weeklyRestList->"+weeklyList.toString());
		return weeklyList;

	}

	public List getWeeklyRestAffDriver(int customerid, int affiliateid, int transporterid, int vehicleid,
			String exceptionlevels, String startDate, String endDate, String language) {

		List weeklyList = new ArrayList();

		String query = "";

		if (exceptionlevels.equals("0"))
			exceptionlevels = "1,2,3";
		List<WeekDaysBean> dateRanges = CommonUtil.getWeekDaysBetweenDates(java.sql.Date.valueOf(startDate),
				java.sql.Date.valueOf(endDate));

		// Taking Weekly Rest

		if (dateRanges.size() > 0) {// bypassing check because wr&wd is now inserted for all days, not weekly

			String driversqry = "select * from sp_get_driver_ids_from_cd_of_wrt(" + vehicleid + "," + transporterid
					+ "," + affiliateid + "," + customerid + ",'" + startDate + "','" + endDate + "')";
			driversqry += " order by affiliateid,driverid";

			List<Map<String, Object>> drivers = jdbcTemplate.queryForList(driversqry);

			List<Map<String, Object>> tempList = new ArrayList();

			for (Map<String, Object> driverDet : drivers) {

				// int vehId = (int) driverDet.get("vehicleid");
				int driId = (int) driverDet.get("driverid");
				int affId = (int) driverDet.get("affiliateid");
				// int transId = (int) driverDet.get("transporterid");

				String thresholdQry = "SELECT thresholdlimit,recordingthreshold,alertthreshold,alarmthreshold FROM parameterconfig "
						+ " WHERE parametertypeid = ? AND status = '1' AND clientaffiliateid = ?";
				Map<String, Object> thresholdDet = null;

				try {

					thresholdDet = jdbcTemplate.queryForMap(thresholdQry, AppConstants.WEEKLYREST, affId);

					if (thresholdDet == null) {
						continue;
					}
				} catch (Exception e) {
					continue;
				}

				double threshold = Double.parseDouble(thresholdDet.get("thresholdlimit").toString());

				double recordingThreshold = Double.parseDouble(thresholdDet.get("recordingthreshold").toString());
				double alertThreshold = Double.parseDouble(thresholdDet.get("alertthreshold").toString());
				double alarmThreshold = Double.parseDouble(thresholdDet.get("alarmthreshold").toString());

				for (WeekDaysBean weekDaysBean : dateRanges) {

					// query =
					// "SELECT e.exceptiontype as
					// exceptiontype,coalesce(e.totalduration,0) as
					// totalduration, cust.name as clientname,e.affiliateid as
					// affiliateid,c.name as affiliatename,e.transporterid as
					// transporterid,t.name as transportername,v.vehicleid as
					// vehicleid,v.vehicledesc as vehicledesc,d.driverid as
					// driverid, d.name as drivername"
					// +
					// " FROM \"continuousdrive\" as e, \"customer\" as
					// cust,\"customeraffiliate\" as c,\"transporter\" as
					// t,\"vehicle\" as v,\"driver\" as d,\"parametertype\" as p
					// "
					// +
					// "where c.customerid=cust.customerid and
					// e.affiliateid=c.affiliateid and v.vehicleid=e.vehicleid
					// and v.transporterid=t.transporterid and
					// d.driverid=e.driverid and
					// e.exceptiontype=concat('',p.parametertypeid)";
					//
					// query += " and e.vehicleid=" + vehId;
					// query += " and e.driverid=" + driId;
					//
					// query += " and e.exceptiontype='" +
					// AppConstants.WEEKLYREST + "'";
					//
					// query += " and DATE(e.enddatetime::timestamp) between '"
					// + weekDaysBean.getStartDate() + "' and '"
					// + weekDaysBean.getEndDate() + "'";
					//
					// query +=
					// " and DATE(e.startdatetime::timestamp) between '" +
					// weekDaysBean.getStartDate() + "' and '"
					// + weekDaysBean.getEndDate() + "'";
					//
					// query += " order by e.startdatetime::timestamp asc";

					/*
					 * query = "select " + affId + " affiliateid," + transId + " transporterid," +
					 * driId + " driverid," + vehId +
					 * " vehicleid,vehiclename as vehicledesc,date(enddatetime) as edate,* from sp_weeklyrestdetails("
					 * + vehId + "," + driId + ",'" + weekDaysBean.getStartDate() + "','" +
					 * weekDaysBean.getEndDate() + "')";
					 */

					query = "select " + affId + " affiliateid"
							+ ",date(enddatetime) as edate,* from sp_weeklyrestdetails_driverbased(" + affId + ","
							+ driId + ",'" + weekDaysBean.getStartDate() + "','" + weekDaysBean.getEndDate()
							+ "') where totalduration in (select max(totalduration) from  sp_weeklyrestdetails_driverbased("
							+ affId + "," + driId + ",'" + weekDaysBean.getStartDate() + "','"
							+ weekDaysBean.getEndDate() + "'))";

					System.out.println(query);

					List<Map<String, Object>> restList = jdbcTemplate.queryForList(query);

					Map longestRest = null;
					if (restList.size() > 0) {

						double longestRestDur = 0;
						for (Map det : restList) {
							if (longestRestDur < Double.parseDouble(det.get("totalduration").toString())) {
								longestRestDur = Double.parseDouble(det.get("totalduration").toString());
								longestRest = det;
							}
						}
						if (longestRest != null) {
							longestRest.put("alarmcount", 0);
							longestRest.put("alertcount", 0);
							longestRest.put("recordingcount", 0);

							double exceededTim = threshold - longestRestDur;

							if (exceededTim >= alarmThreshold) {
								longestRest.put("alarmcount", 1);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("3")) {
									continue;
								}

							} else if (exceededTim >= alertThreshold) {
								longestRest.put("alertcount", 1);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("2")) {
									continue;
								}

							} else if (exceededTim >= recordingThreshold) {
								longestRest.put("recordingcount", 1);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("1")) {
									continue;
								}

							} else {
								continue;
							}

							tempList.add(longestRest);
						}

					}
				}

			}

			if (tempList.size() > 0) {
				Map<Integer, Map<String, Object>> detailsMap = new HashMap<>();
				if (vehicleid > 0 || transporterid > 0) {

					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("vehicleid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("vehicleid"));
							existing.put("alarmcount", (int) existing.get("alarmcount") + (int) map.get("alarmcount"));
							existing.put("alertcount", (int) existing.get("alertcount") + (int) map.get("alertcount"));
							existing.put("recordingcount",
									(int) existing.get("recordingcount") + (int) map.get("recordingcount"));
						} else {
							detailsMap.put((int) map.get("vehicleid"), map);
						}
					}

				} else if (affiliateid > 0) { /*
												 * in dashboard vehicle and transporter wise listing is enough
												 */
					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("transporterid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("transporterid"));

							existing.put("alarmcount", (int) existing.get("alarmcount") + (int) map.get("alarmcount"));
							existing.put("alertcount", (int) existing.get("alertcount") + (int) map.get("alertcount"));
							existing.put("recordingcount",
									(int) existing.get("recordingcount") + (int) map.get("recordingcount"));

						} else {
							detailsMap.put((int) map.get("transporterid"), map);
						}
					}

				} else {
					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("affiliateid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("affiliateid"));

							existing.put("alarmcount", (int) existing.get("alarmcount") + (int) map.get("alarmcount"));
							existing.put("alertcount", (int) existing.get("alertcount") + (int) map.get("alertcount"));
							existing.put("recordingcount",
									(int) existing.get("recordingcount") + (int) map.get("recordingcount"));

						} else {
							detailsMap.put((int) map.get("affiliateid"), map);
						}
					}

				}
				weeklyList.addAll(detailsMap.values());

			}

		}

		return weeklyList;

	}

	public List getWeeklyRestTransporter(int customerid, int affiliateid, int transporterid, int vehicleid,
			String exceptionlevels, String startDate, String endDate, String language) {

		List weeklyList = new ArrayList();

		String query = "";

		if (exceptionlevels.equals("0"))
			exceptionlevels = "1,2,3";
		List<WeekDaysBean> dateRanges = CommonUtil.getWeekDaysBetweenDates(java.sql.Date.valueOf(startDate),
				java.sql.Date.valueOf(endDate));

		// Taking Weekly Rest

		if (dateRanges.size() > 0) {// bypassing check because wr&wd is now inserted for all days, not weekly

			String driversqry = "select * from sp_get_driver_ids_from_cd_of_wrt_forgraph(" + vehicleid + ","
					+ transporterid + "," + affiliateid + "," + customerid + ",'" + startDate + "','" + endDate + "')";
			driversqry += " order by affiliateid,transporterid,driverid";

			List<Map<String, Object>> drivers = jdbcTemplate.queryForList(driversqry);

			List<Map<String, Object>> tempList = new ArrayList();

			for (Map<String, Object> driverDet : drivers) {

				// int vehId = (int) driverDet.get("vehicleid");
				int driId = (int) driverDet.get("driverid");
				int affId = (int) driverDet.get("affiliateid");
				int transId = (int) driverDet.get("transporterid");

				String thresholdQry = "SELECT thresholdlimit,recordingthreshold,alertthreshold,alarmthreshold FROM parameterconfig "
						+ " WHERE parametertypeid = ? AND status = '1' AND clientaffiliateid = ?";
				Map<String, Object> thresholdDet = null;

				try {

					thresholdDet = jdbcTemplate.queryForMap(thresholdQry, AppConstants.WEEKLYREST, affId);

					if (thresholdDet == null) {
						continue;
					}
				} catch (Exception e) {
					continue;
				}

				double threshold = Double.parseDouble(thresholdDet.get("thresholdlimit").toString());

				double recordingThreshold = Double.parseDouble(thresholdDet.get("recordingthreshold").toString());
				double alertThreshold = Double.parseDouble(thresholdDet.get("alertthreshold").toString());
				double alarmThreshold = Double.parseDouble(thresholdDet.get("alarmthreshold").toString());

				for (WeekDaysBean weekDaysBean : dateRanges) {

					// query =
					// "SELECT e.exceptiontype as
					// exceptiontype,coalesce(e.totalduration,0) as
					// totalduration, cust.name as clientname,e.affiliateid as
					// affiliateid,c.name as affiliatename,e.transporterid as
					// transporterid,t.name as transportername,v.vehicleid as
					// vehicleid,v.vehicledesc as vehicledesc,d.driverid as
					// driverid, d.name as drivername"
					// +
					// " FROM \"continuousdrive\" as e, \"customer\" as
					// cust,\"customeraffiliate\" as c,\"transporter\" as
					// t,\"vehicle\" as v,\"driver\" as d,\"parametertype\" as p
					// "
					// +
					// "where c.customerid=cust.customerid and
					// e.affiliateid=c.affiliateid and v.vehicleid=e.vehicleid
					// and v.transporterid=t.transporterid and
					// d.driverid=e.driverid and
					// e.exceptiontype=concat('',p.parametertypeid)";
					//
					// query += " and e.vehicleid=" + vehId;
					// query += " and e.driverid=" + driId;
					//
					// query += " and e.exceptiontype='" +
					// AppConstants.WEEKLYREST + "'";
					//
					// query += " and DATE(e.enddatetime::timestamp) between '"
					// + weekDaysBean.getStartDate() + "' and '"
					// + weekDaysBean.getEndDate() + "'";
					//
					// query +=
					// " and DATE(e.startdatetime::timestamp) between '" +
					// weekDaysBean.getStartDate() + "' and '"
					// + weekDaysBean.getEndDate() + "'";
					//
					// query += " order by e.startdatetime::timestamp asc";

					/*
					 * query = "select " + affId + " affiliateid," + transId + " transporterid," +
					 * driId + " driverid," + vehId +
					 * " vehicleid,vehiclename as vehicledesc,date(enddatetime) as edate,* from sp_weeklyrestdetails("
					 * + vehId + "," + driId + ",'" + weekDaysBean.getStartDate() + "','" +
					 * weekDaysBean.getEndDate() + "')";
					 */

					query = "select " + affId + " affiliateid," + transId
							+ " transporterid,date(enddatetime) as edate,* from sp_weeklyrestdetails_driverbased_forgraph("
							+ affId + ",0," + driId + ",'" + weekDaysBean.getStartDate() + "','"
							+ weekDaysBean.getEndDate()
							+ "') where totalduration in (select max(totalduration) from  sp_weeklyrestdetails_driverbased_forgraph("
							+ affId + ",0," + driId + ",'" + weekDaysBean.getStartDate() + "','"
							+ weekDaysBean.getEndDate() + "'))";

					List<Map<String, Object>> restList = jdbcTemplate.queryForList(query);

					Map longestRest = null;
					if (restList.size() > 0) {

						double longestRestDur = 0;
						for (Map det : restList) {
							if (longestRestDur < Double.parseDouble(det.get("totalduration").toString())) {
								longestRestDur = Double.parseDouble(det.get("totalduration").toString());
								longestRest = det;
							}
						}
						if (longestRest != null) {
							longestRest.put("alarmcount", 0);
							longestRest.put("alertcount", 0);
							longestRest.put("recordingcount", 0);

							double exceededTim = threshold - longestRestDur;

							if (exceededTim >= alarmThreshold) {
								longestRest.put("alarmcount", 1);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("3")) {
									continue;
								}

							} else if (exceededTim >= alertThreshold) {
								longestRest.put("alertcount", 1);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("2")) {
									continue;
								}

							} else if (exceededTim >= recordingThreshold) {
								longestRest.put("recordingcount", 1);

								if (exceptionlevels != null && !exceptionlevels.equals("0")
										&& !exceptionlevels.contains("1")) {
									continue;
								}

							} else {
								continue;
							}

							tempList.add(longestRest);
						}

					}
				}

			}

			if (tempList.size() > 0) {
				Map<Integer, Map<String, Object>> detailsMap = new HashMap<>();
				if (vehicleid > 0 || transporterid > 0) {

					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("vehicleid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("vehicleid"));
							existing.put("alarmcount", (int) existing.get("alarmcount") + (int) map.get("alarmcount"));
							existing.put("alertcount", (int) existing.get("alertcount") + (int) map.get("alertcount"));
							existing.put("recordingcount",
									(int) existing.get("recordingcount") + (int) map.get("recordingcount"));
						} else {
							detailsMap.put((int) map.get("vehicleid"), map);
						}
					}

				} else if (affiliateid > 0) { /*
												 * in dashboard vehicle and transporter wise listing is enough
												 */
					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("transporterid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("transporterid"));

							existing.put("alarmcount", (int) existing.get("alarmcount") + (int) map.get("alarmcount"));
							existing.put("alertcount", (int) existing.get("alertcount") + (int) map.get("alertcount"));
							existing.put("recordingcount",
									(int) existing.get("recordingcount") + (int) map.get("recordingcount"));

						} else {
							detailsMap.put((int) map.get("transporterid"), map);
						}
					}

				} else {
					for (Map<String, Object> map : tempList) {

						if (detailsMap.get((int) map.get("affiliateid")) != null) {
							Map<String, Object> existing = detailsMap.get((int) map.get("affiliateid"));

							existing.put("alarmcount", (int) existing.get("alarmcount") + (int) map.get("alarmcount"));
							existing.put("alertcount", (int) existing.get("alertcount") + (int) map.get("alertcount"));
							existing.put("recordingcount",
									(int) existing.get("recordingcount") + (int) map.get("recordingcount"));

						} else {
							detailsMap.put((int) map.get("affiliateid"), map);
						}
					}

				}
				weeklyList.addAll(detailsMap.values());

			}

		}

		return weeklyList;

	}

	@Override
	public List harshBreakingExceptionsPer100kms(String affiliateid, String transportername, String vehicledesc,
			String startime, String endtime) throws Exception {
		// TODO Auto-generated method stub

		String query = "SELECT transportername,exceptionper100km FROM \"harshbreakingexceptionsper100kms\" where 1=1 ";

		if (affiliateid != null && affiliateid.length() > 0) {
			query += " and " + "affiliateid='" + affiliateid + "'";
		}

		if (transportername != null && transportername.length() > 0) {
			query += " and " + "transportername='" + transportername + "'";
		}

		if (vehicledesc != null && vehicledesc.length() > 0) {
			query += " and vehiclename='" + vehicledesc + "'";
		}

		if (startime != null && startime.length() > 0) {
			query += " and startdatetime>='" + startime + "'";
		}

		if (endtime != null && endtime.length() > 0) {
			query += " and enddatetime<='" + endtime + "'";
		}
		List details = jdbcTemplate.queryForList(query);
		return details;

	}

	@Override
	public List harshBreakingViolations(String affiliateid, String transportername, String vehicledesc, String startime,
			String endtime) throws Exception {
		// TODO Auto-generated method stub

		String query = "SELECT transportername,alert,alarm,recording FROM \"harshbreakingviolations\" where 1=1 ";

		if (affiliateid != null && affiliateid.length() > 0) {
			query += " and " + "affiliateid='" + affiliateid + "'";
		}

		if (transportername != null && transportername.length() > 0) {
			query += " and " + "transportername='" + transportername + "'";
		}

		if (vehicledesc != null && vehicledesc.length() > 0) {
			query += " and vehiclename='" + vehicledesc + "'";
		}

		if (startime != null && startime.length() > 0) {
			query += " and startdatetime>='" + startime + "'";
		}

		if (endtime != null && endtime.length() > 0) {
			query += " and enddatetime<='" + endtime + "'";
		}
		List details = jdbcTemplate.queryForList(query);
		return details;

	}

	@Override
	public List heatMapFetchData(String clientid, String affiliateid, String transporterid, String vehicleid,
			String startime, String endtime, String explevel, String exceptionType, String language) throws Exception {
		// TODO Auto-generated method stub
		String query = "select * from sp_roadriskanalysis(";

		if (clientid != null && clientid.length() > 0) {
			query += clientid + ",";
		} else {
			query += "0,";
		}

		if (affiliateid != null && affiliateid.length() > 0) {
			query += affiliateid + ",";
		} else {
			query += "0,";
		}

		if (transporterid != null && transporterid.length() > 0) {
			query += transporterid + ",";
		} else {
			query += "0,";
		}

		if (vehicleid != null && vehicleid.length() > 0) {
			query += vehicleid + ",";
		} else {
			query += "0,";
		}

		if (startime != null && startime.length() > 0) {
			query += "'" + startime + "',";
		} else {
			query += "null,";
		}

		if (endtime != null && endtime.length() > 0) {
			query += "'" + endtime + "',";
		} else {
			query += "null,";
		}

		if (explevel.equals("0")) {
			query += "Array[1,2,3],";

		} else {
			query += "Array[" + explevel + "]" + ",";
		}

		if (exceptionType != null && exceptionType.length() > 0) {
			query += exceptionType;
		} else {
			query += "0";
		}

		query += ")";
		List result = jdbcTemplate.queryForList(query);
		return jdbcTemplate.queryForList(query);
	}

	@Override
	public List newDriverBehaviourViolations(String userid, String affiliateid, String transporterid, String vehicleid,
			String startime, String endtime) throws Exception {
		String query = "SELECT exceptiontype,alert,alarm,recording FROM \"newdriverbehaviourviolations\" where 1=1";

		if (affiliateid != null && affiliateid.length() > 0 && !affiliateid.equals("0")) {
			query += " and " + "affiliateid='" + affiliateid + "'";
		}

		if (transporterid != null && transporterid.length() > 0 && !transporterid.equals("0")) {
			query += " and " + "transporterid='" + transporterid + "'";
		}

		if (vehicleid != null && vehicleid.length() > 0 && !vehicleid.equals("0")) {
			query += " and vehicleid='" + vehicleid + "'";
		}

		if (startime != null && startime.length() > 0) {
			query += " and startdate>='" + startime + "'";
		}

		if (endtime != null && endtime.length() > 0) {
			query += " and enddatetime<='" + endtime + "'";
		}

		return jdbcTemplate.queryForList(query);
	}

	@Override
	public List nightTimeDriveViolations(String affiliateid, String transportername, String vehicledesc,
			String startime, String endtime) throws Exception {
		String query = "SELECT transportername,alert,alarm,recording FROM \"nighttimedriveviolations\" where 1=1";
		if (affiliateid != null && affiliateid.length() > 0) {
			query += " and " + "affiliateid='" + affiliateid + "'";
		}

		if (transportername != null && transportername.length() > 0) {
			query += " and " + "transportername='" + transportername + "'";
		}

		if (vehicledesc != null && vehicledesc.length() > 0) {
			query += " and vehiclename='" + vehicledesc + "'";
		}

		if (startime != null && startime.length() > 0) {
			query += " and startdatetime>='" + startime + "'";
		}

		if (endtime != null && endtime.length() > 0) {
			query += " and enddatetime<='" + endtime + "'";
		}
		return jdbcTemplate.queryForList(query);
	}

	/* created by Arya */

	@Override
	public Double parseDouble(Object value) throws Exception {
		try {
			if (value != null) {
				return Double.parseDouble(value.toString());
			}
			return 0d;
		} catch (Exception e) {
			return 0d;
		}
	}

	@Override
	public int parseInt(Object value) throws Exception {
		try {
			if (value != null) {
				return Integer.parseInt(value.toString());
			}
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public List reports(String affiliateid, String transportername, String vehicledesc, String startime, String endtime)
			throws Exception {
		String query = "SELECT exceptiontype,alert,alarm,recording,total FROM \"report\" where 1=1";
		if (affiliateid != null && affiliateid.length() > 0) {
			query += " and " + "affiliateid='" + affiliateid + "'";
		}

		if (transportername != null && transportername.length() > 0) {
			query += " and " + "transportername='" + transportername + "'";
		}

		if (vehicledesc != null && vehicledesc.length() > 0) {
			query += " and vehiclename='" + vehicledesc + "'";
		}

		if (startime != null && startime.length() > 0) {
			query += " and startdatetime>='" + startime + "'";
		}

		if (endtime != null && endtime.length() > 0) {
			query += " and enddatetime<='" + endtime + "'";
		}
		return jdbcTemplate.queryForList(query);
	}

	@Override
	public List speedingExceptionsPer100kms(String affiliateid, String transportername, String vehicledesc,
			String startime, String endtime) throws Exception {
		String query = "SELECT transportername,exceptionper100km FROM \"speedingexceptionsper100kms\" where 1=1";

		if (affiliateid != null && affiliateid.length() > 0) {
			query += " and " + "affiliateid='" + affiliateid + "'";
		}

		if (transportername != null && transportername.length() > 0) {
			query += " and " + "transportername='" + transportername + "'";
		}

		if (vehicledesc != null && vehicledesc.length() > 0) {
			query += " and vehiclename='" + vehicledesc + "'";
		}

		if (startime != null && startime.length() > 0) {
			query += " and startdatetime>='" + startime + "'";
		}

		if (endtime != null && endtime.length() > 0) {
			query += " and enddatetime<='" + endtime + "'";
		}

		return jdbcTemplate.queryForList(query);
	}

	@Override
	public List speedingViolations(String affiliateid, String transportername, String vehicledesc, String startime,
			String endtime) throws Exception {
		// TODO Auto-generated method stub

		String query = "SELECT transportername,alert,alarm,recording FROM \"speedingviolations\" where 1=1 ";

		if (affiliateid != null && affiliateid.length() > 0) {
			query += " and " + "affiliateid='" + affiliateid + "'";
		}

		if (transportername != null && transportername.length() > 0) {
			query += " and " + "transportername='" + transportername + "'";
		}

		if (vehicledesc != null && vehicledesc.length() > 0) {
			query += " and vehiclename='" + vehicledesc + "'";
		}

		if (startime != null && startime.length() > 0) {
			query += " and startdatetime>='" + startime + "'";
		}

		if (endtime != null && endtime.length() > 0) {
			query += " and enddatetime<='" + endtime + "'";
		}
		List details = jdbcTemplate.queryForList(query);
		return details;

	}

	@Override
	public List testDriverBehaviourViolations(String userid, String affiliateid, String transporterid, String vehicleid,
			String startime, String endtime, int level) throws Exception {
		String query = "select exceptiontype,exceptionname as exceptionlevel,violationscount as count from test_driverbehaviourviolation(";

		if (userid != null && userid.length() > 0) {
			query += userid + ",";
		} else {
			query += "0,";
		}

		if (affiliateid != null && affiliateid.length() > 0) {
			query += affiliateid + ",";
		} else {
			query += "0,";
		}

		if (transporterid != null && transporterid.length() > 0) {
			query += transporterid + ",";
		} else {
			query += "0,";
		}

		if (vehicleid != null && vehicleid.length() > 0) {
			query += vehicleid + ",";
		} else {
			query += "0,";
		}

		if (startime != null && startime.length() > 0) {
			query += "'" + startime + "',";
		} else {
			query += "null,";
		}

		if (endtime != null && endtime.length() > 0) {
			query += "'" + endtime + "',";
		} else {
			query += "null,";
		}

		if (level >= 0) {
			query += level;
		} else {
			query += "0";
		}

		query += ") order by exceptiontype";

		return jdbcTemplate.queryForList(query);
	}

	@Override
	public List transwiseDriverBehaviourViolations(String clientid, String affiliateid, String transporterid,
			String vehicleid, String startime, String endtime, String exptype, String[] explevel, String language)
			throws Exception {
		int expt = Integer.parseInt(exptype);
		ArrayUtils.reverse(explevel);
		String exceptionlevels = String.join(",", explevel);
		if (expt <= 7) {
			List<Map<String, Object>> details = new ArrayList<>();

			int len = 0;
			for (String exp : explevel) {

				if (affiliateid == null && affiliateid.length() == 0) {
					affiliateid = "0";
				}

				if (transporterid == null && transporterid.length() == 0) {
					transporterid = "0";
				}

				if (vehicleid == null && vehicleid.length() == 0) {
					vehicleid = "0";
				}

				if (startime == null && startime.length() == 0) {
					startime = null;
				}

				if (endtime == null && endtime.length() == 0) {
					endtime = null;
				}

				String query = "select * from \"sp1_transwise_driverbehaviourviolations\"(" + clientid + "::int4,"
						+ affiliateid + "::int4," + transporterid + "::int4," + vehicleid + "::int4,'" + (startime)
						+ "'::date,'" + (endtime) + "'::date," + exptype + "::int4,0::int4) where exceptiontypeid="
						+ exptype + "::int4 ";

				if (!exp.equals("0")) {
					query += " and levelid=" + exp + "::int4";
					if (explevel.length == 1)
						query += " and violationscount>0";
				}

				query += " order by transportername asc";

				List<Map<String, Object>> temp = jdbcTemplate.queryForList(query);
				details.addAll(temp);
				len = temp.size();
			}

			List<Map<String, Object>> details_altered = new ArrayList<>();
			if (explevel.length == 1 && explevel[0].equals("0")) {
				for (int i = 0; i < details.size(); i = i + 3) {
					if (i + 2 < details.size()) {
						Map map1 = details.get(i);
						Map map2 = details.get(i + 1);
						Map map3 = details.get(i + 2);

						long vcount1 = (long) (map1.get("violationscount"));
						long vcount2 = (long) (map2.get("violationscount"));
						long vcount3 = (long) (map3.get("violationscount"));
						if ((vcount1 == 0 && vcount2 == 0 && vcount3 == 0)) {
							continue;
						} else {
							details_altered.add(map1);
							details_altered.add(map2);
							details_altered.add(map3);
						}
					}
				}
			} else if (explevel.length == 2) {
				for (int i = 0; i < len; i++) {
					Map map1 = details.get(i);
					Map map2 = details.get(len + i);

					long vcount1 = (long) (map1.get("violationscount"));
					long vcount2 = (long) (map2.get("violationscount"));
					if (vcount1 == 0 && vcount2 == 0) {
						continue;
					} else {
						details_altered.add(map1);
						details_altered.add(map2);

					}
				}
			} else
				details_altered = details;

			List retData = new ArrayList<>();

			List<Object> keys = details_altered.stream().map(x -> x.get("exceptionlevel")).distinct()
					.collect(Collectors.toList());

			Map jobToAdd = null;
			Map exceptionTypeIds = new Hashtable();
			Map exceptionLevelIds = new Hashtable();
			Map transporterIds = new Hashtable();

			for (Object keytoAdd : keys) {
				String keyInLanguage = PropertyLoader.getLabel(language, keytoAdd.toString());

				jobToAdd = new HashMap();
				jobToAdd.put("key", keyInLanguage);
				jobToAdd.put("total", 0L);
				jobToAdd.put("exceptionLevelIds", exceptionLevelIds);
				jobToAdd.put("exceptionTypeIds", exceptionTypeIds);
				jobToAdd.put("transporterIds", transporterIds);

				for (Map<String, Object> job : details_altered) {

					String key = (String) job.get("exceptionlevel");
					if (key.equals(keytoAdd)) {
						List arr = null;
						if (jobToAdd.containsKey("values")) {
							arr = (ArrayList) jobToAdd.get("values");
						} else {
							arr = new ArrayList();
						}

						List values = new ArrayList();
						values.add(job.get("transportername"));
						values.add((job.get("violationscount")));

						jobToAdd.put("total", (long) jobToAdd.get("total") + (long) (job.get("violationscount")));
						arr.add(values);
						jobToAdd.put("values", arr);
					}

					exceptionLevelIds.put(PropertyLoader.getLabel(language, key), job.get("levelid"));
					exceptionTypeIds.put(PropertyLoader.getLabel(language, (String) job.get("exceptiontype")),
							job.get("exceptiontypeid"));
					transporterIds.put(job.get("transportername"), job.get("transporterid"));
				}
				retData.add(jobToAdd);
			}

			return retData;
		} else if (expt == 9) {
			List weeklyList = null;
			if (affiliateid.equals("0"))
				weeklyList = getWeeklyDriveAffDriver(Integer.parseInt(clientid), Integer.parseInt(affiliateid),
						Integer.parseInt(transporterid), Integer.parseInt(vehicleid), exceptionlevels, startime,
						endtime, language);
			else
				weeklyList = getWeeklyDriveTransporter(Integer.parseInt(clientid), Integer.parseInt(affiliateid),
						Integer.parseInt(transporterid), Integer.parseInt(vehicleid), exceptionlevels, startime,
						endtime, language);
			if (!weeklyList.isEmpty()) {
				List retData = new ArrayList<>();
				Map exceptionTypeIds = new Hashtable();
				Map exceptionLevelIds = new Hashtable();
				// Map vehicleIds = new Hashtable();
				Map transporterIds = new Hashtable();
				List<String> keys = new ArrayList<>();

				if (explevel.length != 0) {
					// ArrayUtils.reverse(explevel);
					if (explevel[0].equals("0")) {
						explevel = new String[3];
						explevel[0] = "3";
						explevel[1] = "2";
						explevel[2] = "1";
					}
					for (String exp : explevel) {
						if (exp.equals("3"))
							keys.add("db_alarm");
						if (exp.equals("2"))
							keys.add("db_alert");
						if (exp.equals("1"))
							keys.add("db_recording");
					}
					exceptionTypeIds.put(PropertyLoader.getLabel(language, "menu_weekly_driving"), 9);

					exceptionLevelIds.put(PropertyLoader.getLabel(language, "db_alarm"), 3);
					exceptionLevelIds.put(PropertyLoader.getLabel(language, "db_alert"), 2);
					exceptionLevelIds.put(PropertyLoader.getLabel(language, "db_recording"), 1);

					for (Object element : weeklyList) {
						Map temp = (Map) element;
						if (!affiliateid.equals("0"))
							transporterIds.put(temp.get("transportername"), temp.get("transporterid"));
						else
							transporterIds.put(temp.get("affiliatename"), temp.get("affiliateid"));
					}

					Map jobToAdd = null;
					for (Object keytoAdd : keys) {
						String keyInLanguage = PropertyLoader.getLabel(language, keytoAdd.toString());

						jobToAdd = new HashMap();
						jobToAdd.put("key", keyInLanguage);
						jobToAdd.put("total", 0L);
						jobToAdd.put("exceptionLevelIds", exceptionLevelIds);
						jobToAdd.put("exceptionTypeIds", exceptionTypeIds);
						jobToAdd.put("transporterIds", transporterIds);

						for (int i = 0; i < weeklyList.size(); i++) {

							Map temp = (Map) weeklyList.get(i);

							List arr = null;
							if (jobToAdd.containsKey("values")) {
								arr = (ArrayList) jobToAdd.get("values");
							} else {
								arr = new ArrayList();
							}

							List values = new ArrayList();
							if (!affiliateid.equals("0"))
								values.add(temp.get("transportername"));
							else
								values.add(temp.get("affiliatename"));
							if (keytoAdd.equals("db_alarm")) {
								values.add((temp.get("alarmcount")));

								jobToAdd.put("total", Integer.parseInt(jobToAdd.get("total").toString())
										+ Integer.parseInt(temp.get("alarmcount").toString()));
							} else if (keytoAdd.equals("db_alert")) {
								values.add((temp.get("alertcount")));
								jobToAdd.put("total", Integer.parseInt(jobToAdd.get("total").toString())
										+ Integer.parseInt(temp.get("alertcount").toString()));

							} else {
								values.add((temp.get("recordingcount")));
								jobToAdd.put("total", Integer.parseInt(jobToAdd.get("total").toString())
										+ Integer.parseInt(temp.get("recordingcount").toString()));

							}
							arr.add(values);
							jobToAdd.put("values", arr);

						}
						retData.add(jobToAdd);
					}
				}
				if (!weeklyList.isEmpty())
					return retData;
				else
					return weeklyList;
			} else
				return null;
		} else if (expt == 8) {
			List weeklyRestList = null;
			if (affiliateid.equals("0"))
				weeklyRestList = getWeeklyRestAffDriver(Integer.parseInt(clientid), Integer.parseInt(affiliateid),
						Integer.parseInt(transporterid), Integer.parseInt(vehicleid), exceptionlevels, startime,
						endtime, language);
			else
				weeklyRestList = getWeeklyRestTransporter(Integer.parseInt(clientid), Integer.parseInt(affiliateid),
						Integer.parseInt(transporterid), Integer.parseInt(vehicleid), exceptionlevels, startime,
						endtime, language);

			if (!weeklyRestList.isEmpty()) {
				List retData = new ArrayList<>();
				Map exceptionTypeIds = new Hashtable();
				Map exceptionLevelIds = new Hashtable();
				// Map vehicleIds = new Hashtable();
				Map transporterIds = new Hashtable();
				List<String> keys = new ArrayList<>();

				if (explevel.length != 0) {
					// ArrayUtils.reverse(explevel);
					if (explevel[0].equals("0")) {
						explevel = new String[3];
						explevel[0] = "3";
						explevel[1] = "2";
						explevel[2] = "1";
					}
					for (String exp : explevel) {
						if (exp.equals("3"))
							keys.add("db_alarm");
						if (exp.equals("2"))
							keys.add("db_alert");
						if (exp.equals("1"))
							keys.add("db_recording");
					}
					exceptionTypeIds.put(PropertyLoader.getLabel(language, "menu_weekly_rest_time"), 8);

					exceptionLevelIds.put(PropertyLoader.getLabel(language, "db_alarm"), 3);
					exceptionLevelIds.put(PropertyLoader.getLabel(language, "db_alert"), 2);
					exceptionLevelIds.put(PropertyLoader.getLabel(language, "db_recording"), 1);

					for (Object element : weeklyRestList) {
						Map temp = (Map) element;
						if (!affiliateid.equals("0"))
							transporterIds.put(temp.get("transportername"), temp.get("transporterid"));
						else
							transporterIds.put(temp.get("affiliatename"), temp.get("affiliateid"));
					}

					Map jobToAdd = null;
					for (Object keytoAdd : keys) {
						String keyInLanguage = PropertyLoader.getLabel(language, keytoAdd.toString());

						jobToAdd = new HashMap();
						jobToAdd.put("key", keyInLanguage);
						jobToAdd.put("total", 0L);
						jobToAdd.put("exceptionLevelIds", exceptionLevelIds);
						jobToAdd.put("exceptionTypeIds", exceptionTypeIds);
						jobToAdd.put("transporterIds", transporterIds);
						// jobToAdd.put("vehicleIds", vehicleIds);

						for (int i = 0; i < weeklyRestList.size(); i++) {
							Map temp = (Map) weeklyRestList.get(i);

							List arr = null;
							if (jobToAdd.containsKey("values")) {
								arr = (ArrayList) jobToAdd.get("values");
							} else {
								arr = new ArrayList();
							}

							List values = new ArrayList();
							if (!affiliateid.equals("0"))
								values.add(temp.get("transportername"));
							else
								values.add(temp.get("affiliatename"));
							if (keytoAdd.equals("db_alarm")) {
								values.add((temp.get("alarmcount")));

								jobToAdd.put("total", Integer.parseInt(jobToAdd.get("total").toString())
										+ Integer.parseInt(temp.get("alarmcount").toString()));
							} else if (keytoAdd.equals("db_alert")) {
								values.add((temp.get("alertcount")));
								jobToAdd.put("total", Integer.parseInt(jobToAdd.get("total").toString())
										+ Integer.parseInt(temp.get("alertcount").toString()));

							} else {
								values.add((temp.get("recordingcount")));
								jobToAdd.put("total", Integer.parseInt(jobToAdd.get("total").toString())
										+ Integer.parseInt(temp.get("recordingcount").toString()));

							}
							arr.add(values);
							jobToAdd.put("values", arr);

						}
						retData.add(jobToAdd);
					}
				}
				if (!weeklyRestList.isEmpty())
					return retData;
				else
					return weeklyRestList;
			} else
				return null;

		} else
			return null;

	}

	public List transwiseDriverBehaviourViolations_Old(String clientid, String affiliateid, String transporterid,
			String vehicleid, String startime, String endtime, String exptype, String[] explevel, String language)
			throws Exception {
		/* List details = new ArrayList<>(); */
		List details = new ArrayList<>();

		long d1 = System.currentTimeMillis();

		for (String exp : explevel) {

			if (affiliateid == null && affiliateid.length() == 0) {
				affiliateid = "0";
			}

			if (transporterid == null && transporterid.length() == 0) {
				transporterid = "0";
			}

			if (vehicleid == null && vehicleid.length() == 0) {
				vehicleid = "0";
			}

			if (startime == null && startime.length() == 0) {
				startime = null;
			}

			if (endtime == null && endtime.length() == 0) {
				endtime = null;
			}

			String query = "select * from \"sp1_transwise_driverbehaviourviolations\"(" + clientid + "::int4,"
					+ affiliateid + "::int4," + transporterid + "::int4," + vehicleid + "::int4,'" + (startime)
					+ "'::date,'" + (endtime) + "'::date," + exptype + "::int4," + exp + "::int4);";

			List temp = jdbcTemplate.queryForList(query);

			long d2 = System.currentTimeMillis();
			long diff = d2 - d1;
			long diffSeconds = diff /* / 1000 % 60 */;
			// System.out.println("Query string: " + query);
			// System.out
			// .println("Query executed in milliseconds: " + diffSeconds);

			details.addAll(temp);
		}

		// return details;

		d1 = System.currentTimeMillis();

		List retData = new ArrayList<>();

		List<String> keys = new ArrayList<>();
		Map exceptionTypeIds = new Hashtable();
		Map exceptionLevelIds = new Hashtable();
		Map transporterIds = new Hashtable();
		for (Object detail : details) {

			String s = detail.toString();
			s = s.replace("{", "").replace("}", "");
			String[] pairs = s.split(",");
			Map myMap = new HashMap<>();
			for (String pair2 : pairs) {
				String pair = pair2;
				pair = pair.trim();
				String[] keyValue = pair.split("=");
				myMap.put(keyValue[0], keyValue[1]);
				if (keyValue[0].equals("exceptionlevel")) {
					String key = keyValue[0];

					String a = PropertyLoader.getLabel(language, keyValue[1]);
					if (!keys.contains(a)) {
						keys.add(a);
					}
				}
			}
			JSONObject temp = new JSONObject(detail.toString().replace("=", ":"));
			exceptionLevelIds.put(PropertyLoader.getLabel(language, (String) temp.get("exceptionlevel")),
					temp.get("levelid"));
			exceptionTypeIds.put(PropertyLoader.getLabel(language, (String) temp.get("exceptiontype")),
					temp.get("exceptiontypeid"));
			transporterIds.put(temp.get("transportername"), temp.get("transporterid"));
		}

		Map jobToAdd = null;
		for (String keytoAdd : keys) {
			jobToAdd = new HashMap();
			jobToAdd.put("key", keytoAdd);
			jobToAdd.put("total", 0.0);
			jobToAdd.put("exceptionLevelIds", exceptionLevelIds);
			jobToAdd.put("exceptionTypeIds", exceptionTypeIds);
			jobToAdd.put("transporterIds", transporterIds);

			for (int i = 0; i < details.size(); i++) {

				String s = details.get(i).toString();
				s = s.replace("{", "").replace("}", "");
				String[] pairs = s.split(",");
				Map myMap = new HashMap<>();
				for (String pair2 : pairs) {
					String pair = pair2;
					pair = pair.trim();
					String[] keyValue = pair.split("=");

					String a = PropertyLoader.getLabel(language, keyValue[1]);
					myMap.put(keyValue[0], a);
				}
				JSONObject job = new JSONObject(myMap);
				String key = job.getString("exceptionlevel");
				if (key.equals(keytoAdd)) {
					List arr = null;
					if (jobToAdd.containsKey("values")) {
						arr = (ArrayList) jobToAdd.get("values");
					} else {
						arr = new ArrayList();
					}
					/*
					 * Map values=new HashMap();
					 *
					 * values.put("label", job.getString("exceptiontype")); values.put("value",
					 * job.get("violationscount"));
					 */
					List values = new ArrayList();
					values.add(job.getString("transportername"));
					values.add(parseDouble(job.get("violationscount")));

					jobToAdd.put("total", (Double) jobToAdd.get("total") + parseDouble(job.get("violationscount")));
					arr.add(values);
					jobToAdd.put("values", arr);
				}
			}
			retData.add(jobToAdd);
		}

		long d2 = System.currentTimeMillis();
		long diff = d2 - d1;
		long diffSeconds = diff /* / 1000 % 60 */;

		// System.out.println("Data Process executed in milliseconds: "
		// + diffSeconds);

		return retData;

	}

	@Override
	public List transwiseDriverBehaviourViolationsPer100Km(String clientid, String affiliateid, String transporterid,
			String vehicleid, String startime, String endtime, String exptype, String[] explevel, String language)
			throws Exception {
		List<Map<String, Object>> details = new ArrayList<>();
		double recordingWeight = 1;
		double alertWeight = 1;
		double alarmWeight = 1;

		if (explevel.length != 0) {
			ArrayUtils.reverse(explevel);
			if (explevel[0].equals("0")) {
				explevel = new String[3];
				explevel[0] = "3";
				explevel[1] = "2";
				explevel[2] = "1";
			}

			String exceptionlevels = String.join(",", explevel);

			if (exceptionlevels.contains("1") && exceptionlevels.contains("2") && exceptionlevels.contains("3")) {
				recordingWeight = 0.15;
				alertWeight = 0.28;
				alarmWeight = 0.57;
			} else if (exceptionlevels.contains("2") && exceptionlevels.contains("3")) {
				recordingWeight = 0;
				alertWeight = 0.33;
				alarmWeight = 0.67;
			} else if (exceptionlevels.contains("1") && exceptionlevels.contains("3")) {
				recordingWeight = 0.21;
				alertWeight = 0;
				alarmWeight = 0.79;
			} else if (exceptionlevels.contains("1") && exceptionlevels.contains("2")) {
				recordingWeight = 0.35;
				alertWeight = 0.65;
				alarmWeight = 0;
			} else if (exceptionlevels.contains("1")) {
				recordingWeight = 1.0;
				alertWeight = 0.0;
				alarmWeight = 0.0;
			} else if (exceptionlevels.contains("2")) {
				recordingWeight = 0.0;
				alertWeight = 1.0;
				alarmWeight = 0.0;
			} else if (exceptionlevels.contains("3")) {
				recordingWeight = 0.0;
				alertWeight = 0.0;
				alarmWeight = 1.0;
			}
		}
		int len = 0;

		long start = System.currentTimeMillis();

		for (String exp : explevel) {

			if (affiliateid == null && affiliateid.length() == 0) {
				affiliateid = "0";
			}

			if (transporterid == null && transporterid.length() == 0) {
				transporterid = "0";
			}

			if (vehicleid == null && vehicleid.length() == 0) {
				vehicleid = "0";
			}

			if (startime == null && startime.length() == 0) {
				startime = null;
			}

			if (endtime == null && endtime.length() == 0) {
				endtime = null;
			}

			String query = "select * from \"sp_transwise_driverbehaviourviolations_per100km_dashboard\"(" + clientid
					+ "::int4," + affiliateid + "::int4," + transporterid + "::int4," + vehicleid + "::int4,'"
					+ (startime) + "'::date,'" + (endtime) + "'::date," + exptype + "::int4," + exp + "::int4,";
			if (exp.equals("1"))
				query += recordingWeight + ")";

			if (exp.equals("2"))
				query += alertWeight + ")";

			if (exp.equals("3"))
				query += alarmWeight + ")";

			query += " where exceptiontypeid=" + exptype + "::int4 ";

			query += " and levelid=" + exp + "::int4";

			if (explevel.length == 1)
				query += " and driverexceptionper100kms>0";

			query += " order by transportername asc";

			List<Map<String, Object>> temp = jdbcTemplate.queryForList(query);

			details.addAll(temp);
			len = temp.size();
		}

		long end = System.currentTimeMillis();

		System.out.println("*****Driver behaviour violation per 100 km**** ");
		System.out.println("Query execution: " + (end - start));

		start = System.currentTimeMillis();
		// return details;
		List<Map<String, Object>> details_altered = new ArrayList<>();
		if (explevel.length == 3) {
			for (int i = 0; i < len; i++) {
				Map map1 = details.get(i);
				Map map2 = details.get(len + i);
				Map map3 = details.get((len * 2) + i);

				BigDecimal vcount1 = (BigDecimal) map1.get("driverexceptionper100kms");
				BigDecimal vcount2 = (BigDecimal) map2.get("driverexceptionper100kms");
				BigDecimal vcount3 = (BigDecimal) map3.get("driverexceptionper100kms");

				if (vcount1.compareTo(BigDecimal.ZERO) < 0) {
					map1.put("driverexceptionper100kms", BigDecimal.ZERO);
					vcount1 = BigDecimal.ZERO;
				}

				if (vcount2.compareTo(BigDecimal.ZERO) < 0) {
					map2.put("driverexceptionper100kms", BigDecimal.ZERO);
					vcount2 = BigDecimal.ZERO;
				}

				if (vcount3.compareTo(BigDecimal.ZERO) < 0) {
					map3.put("driverexceptionper100kms", BigDecimal.ZERO);
					vcount3 = BigDecimal.ZERO;
				}

				if ((vcount1 != null && vcount2 != null && vcount3 != null) && (vcount1.compareTo(BigDecimal.ZERO) == 0
						&& vcount2.compareTo(BigDecimal.ZERO) == 0 && vcount3.compareTo(BigDecimal.ZERO) == 0)) {
					continue;
				} else {
					details_altered.add(map1);
					details_altered.add(map2);
					details_altered.add(map3);
				}

			}
		} else if (explevel.length == 2) {
			for (int i = 0; i < len; i++) {
				Map map1 = details.get(i);
				Map map2 = details.get(len + i);

				BigDecimal vcount1 = (BigDecimal) map1.get("driverexceptionper100kms");
				BigDecimal vcount2 = (BigDecimal) map2.get("driverexceptionper100kms");

				if (vcount1.compareTo(BigDecimal.ZERO) < 0) {
					map1.put("driverexceptionper100kms", BigDecimal.ZERO);
					vcount1 = BigDecimal.ZERO;
				}

				if (vcount2.compareTo(BigDecimal.ZERO) < 0) {
					map2.put("driverexceptionper100kms", BigDecimal.ZERO);
					vcount2 = BigDecimal.ZERO;
				}

				if ((vcount1 != null && vcount2 != null)
						&& (vcount1.compareTo(BigDecimal.ZERO) == 0 && vcount2.compareTo(BigDecimal.ZERO) == 0)) {
					continue;
				} else {
					details_altered.add(map1);
					details_altered.add(map2);
				}

			}
		} else
			details_altered = details;

		List retData = new ArrayList<>();

		List<Object> keys = details_altered.stream().map(x -> x.get("exceptionlevel")).distinct()
				.collect(Collectors.toList());

		Map jobToAdd = null;
		Map exceptionTypeIds = new Hashtable();
		Map exceptionLevelIds = new Hashtable();
		Map transporterIds = new Hashtable();

		for (Object keytoAdd : keys) {
			String keyInLanguage = PropertyLoader.getLabel(language, keytoAdd.toString());

			jobToAdd = new HashMap();
			jobToAdd.put("key", keyInLanguage);
			jobToAdd.put("total", 0D);
			jobToAdd.put("exceptionLevelIds", exceptionLevelIds);
			jobToAdd.put("exceptionTypeIds", exceptionTypeIds);
			jobToAdd.put("transporterIds", transporterIds);

			for (Map<String, Object> job : details_altered) {

				String key = (String) job.get("exceptionlevel");

				if (key.equals(keytoAdd)) {
					List arr = null;
					if (jobToAdd.containsKey("values")) {
						arr = (ArrayList) jobToAdd.get("values");
					} else {
						arr = new ArrayList();
					}

					List values = new ArrayList();
					values.add(job.get("transportername"));
					BigDecimal val = (BigDecimal) job.get("driverexceptionper100kms");

					Double total = (Double) jobToAdd.get("total");

					if (val != null) {
						values.add(val);
						total = total + val.doubleValue();
					}

					jobToAdd.put("total", total);
					arr.add(values);
					jobToAdd.put("values", arr);
				}

				exceptionLevelIds.put(PropertyLoader.getLabel(language, (String) job.get("exceptionlevel")),
						job.get("levelid"));
				exceptionTypeIds.put(PropertyLoader.getLabel(language, (String) job.get("exceptiontype")),
						job.get("exceptiontypeid"));
				transporterIds.put(job.get("transportername"), job.get("transporterid"));

			}
			retData.add(jobToAdd);
		}

		end = System.currentTimeMillis();

		System.out.println("Data Processing: " + (end - start));

		return retData;

	}

	public List transwiseDriverBehaviourViolationsPer100Km_old(String clientid, String affiliateid,
			String transporterid, String vehicleid, String startime, String endtime, String exptype, String[] explevel,
			String language) throws Exception {
		List details = new ArrayList<>();

		for (String exp : explevel) {

			if (affiliateid == null && affiliateid.length() == 0) {
				affiliateid = "0";
			}

			if (transporterid == null && transporterid.length() == 0) {
				transporterid = "0";
			}

			if (vehicleid == null && vehicleid.length() == 0) {
				vehicleid = "0";
			}

			if (startime == null && startime.length() == 0) {
				startime = null;
			}

			if (endtime == null && endtime.length() == 0) {
				endtime = null;
			}

			String query = "select * from \"sp_transwise_driverbehaviourviolations_per100km_new\"(" + clientid
					+ "::int4," + affiliateid + "::int4," + transporterid + "::int4," + vehicleid + "::int4,'"
					+ (startime) + "'::date,'" + (endtime) + "'::date," + exptype + "::int4," + exp + "::int4);";

			List temp = jdbcTemplate.queryForList(query);

			details.addAll(temp);
		}

		// return details;

		List retData = new ArrayList<>();

		List<String> keys = new ArrayList<>();
		Map exceptionTypeIds = new Hashtable();
		Map exceptionLevelIds = new Hashtable();
		Map transporterIds = new Hashtable();
		for (Object detail : details) {

			String s = detail.toString();
			s = s.replace("{", "").replace("}", "");
			String[] pairs = s.split(",");
			Map myMap = new HashMap<>();
			for (String pair2 : pairs) {
				String pair = pair2;
				pair = pair.trim();
				String[] keyValue = pair.split("=");
				myMap.put(keyValue[0], keyValue[1]);
				if (keyValue[0].equals("exceptionlevel")) {
					String key = keyValue[0];

					String a = PropertyLoader.getLabel(language, keyValue[1]);
					if (!keys.contains(a)) {
						keys.add(a);
					}
				}
			}
			JSONObject temp = new JSONObject(detail.toString().replace("=", ":"));
			exceptionLevelIds.put(PropertyLoader.getLabel(language, (String) temp.get("exceptionlevel")),
					temp.get("levelid"));
			exceptionTypeIds.put(PropertyLoader.getLabel(language, (String) temp.get("exceptiontype")),
					temp.get("exceptiontypeid"));
			transporterIds.put(temp.get("transportername"), temp.get("transporterid"));
		}

		Map jobToAdd = null;
		for (String keytoAdd : keys) {
			jobToAdd = new HashMap();
			jobToAdd.put("key", keytoAdd);
			jobToAdd.put("total", 0.0);
			jobToAdd.put("exceptionLevelIds", exceptionLevelIds);
			jobToAdd.put("exceptionTypeIds", exceptionTypeIds);
			jobToAdd.put("transporterIds", transporterIds);

			for (int i = 0; i < details.size(); i++) {

				String s = details.get(i).toString();
				s = s.replace("{", "").replace("}", "");
				String[] pairs = s.split(",");
				Map myMap = new HashMap<>();
				for (String pair2 : pairs) {
					String pair = pair2;
					pair = pair.trim();
					String[] keyValue = pair.split("=");

					String a = PropertyLoader.getLabel(language, keyValue[1]);
					myMap.put(keyValue[0], a);
				}
				JSONObject job = new JSONObject(myMap);
				String key = job.getString("exceptionlevel");
				if (key.equals(keytoAdd)) {
					List arr = null;
					if (jobToAdd.containsKey("values")) {
						arr = (ArrayList) jobToAdd.get("values");
					} else {
						arr = new ArrayList();
					}
					/*
					 * Map values=new HashMap();
					 *
					 * values.put("label", job.getString("exceptiontype")); values.put("value",
					 * job.get("violationscount"));
					 */
					List values = new ArrayList();
					values.add(job.getString("transportername"));
					values.add(parseDouble(job.get("driverexceptionper100kms")));

					jobToAdd.put("total",
							(Double) jobToAdd.get("total") + parseDouble(job.get("driverexceptionper100kms")));
					arr.add(values);
					jobToAdd.put("values", arr);
				}
			}
			retData.add(jobToAdd);
		}

		return retData;

	}

	@Override
	public List vehiclewiseDriverBehaviourViolations(String clientid, String affiliateid, String transporterid,
			String vehicleid, String startime, String endtime, String exptype, String[] explevel, String language)
			throws Exception {

		List details = new ArrayList<>();
		ArrayUtils.reverse(explevel);
		int expt = Integer.parseInt(exptype);
		String exceptionlevels = String.join(",", explevel);
		if (expt <= 7) {
			int len = 0;
			for (String exp : explevel) {

				if (affiliateid == null && affiliateid.length() == 0) {
					affiliateid = "0";
				}

				if (transporterid == null && transporterid.length() == 0) {
					transporterid = "0";
				}

				if (vehicleid == null && vehicleid.length() == 0) {
					vehicleid = "0";
				}

				if (startime == null && startime.length() == 0) {
					startime = null;
				}

				if (endtime == null && endtime.length() == 0) {
					endtime = null;
				}

				String query = "select * from \"sp1_vehiclewise_driverbehaviourviolations\"(" + clientid + "::int4,"
						+ affiliateid + "::int4," + transporterid + "::int4," + vehicleid + "::int4,'" + (startime)
						+ "'::date,'" + (endtime) + "'::date," + exptype + "::int4,0::int4) where exceptiontypeid="
						+ exptype + "::int4 ";

				if (!exp.equals("0")) {
					query += " and levelid=" + exp + "::int4";

					if (explevel.length == 1)
						query += " and violationscount>0";
				}

				query += " order by vehiclename asc";

				List temp = jdbcTemplate.queryForList(query);

				details.addAll(temp);
				len = temp.size();
			}
			List<Map<String, Object>> details_temp = details;

			List<Map<String, Object>> details_altered = new ArrayList<>();
			if (explevel.length == 1 && explevel[0].equals("0")) {
				for (int i = 0; i < details_temp.size(); i = i + 3) {
					if (i + 2 < details_temp.size()) {
						Map map1 = details_temp.get(i);
						Map map2 = details_temp.get(i + 1);
						Map map3 = details_temp.get(i + 2);

						long vcount1 = (long) (map1.get("violationscount"));
						long vcount2 = (long) (map2.get("violationscount"));
						long vcount3 = (long) (map3.get("violationscount"));
						if ((vcount1 == 0 && vcount2 == 0 && vcount3 == 0)) {
							continue;
						} else {
							details_altered.add(map1);
							details_altered.add(map2);
							details_altered.add(map3);
						}
					}

				}
			} else if (explevel.length == 2) {
				for (int i = 0; i < len; i++) {
					Map map1 = details_temp.get(i);
					Map map2 = details_temp.get(len + i);

					long vcount1 = (long) (map1.get("violationscount"));
					long vcount2 = (long) (map2.get("violationscount"));
					if (vcount1 == 0 && vcount2 == 0) {
						continue;
					} else {
						details_altered.add(map1);
						details_altered.add(map2);

					}
				}
			} else
				details_altered = details_temp;

			// return details;

			List retData = new ArrayList<>();
			Map exceptionTypeIds = new Hashtable();
			Map exceptionLevelIds = new Hashtable();
			Map vehicleIds = new Hashtable();
			List<String> keys = new ArrayList<>();
			for (int i = 0; i < details_altered.size(); i++) {

				String s = details_altered.get(i).toString();
				s = s.replace("{", "").replace("}", "");
				String[] pairs = s.split(",");
				Map myMap = new HashMap<>();
				for (String pair2 : pairs) {
					String pair = pair2;
					pair = pair.trim();
					String[] keyValue = pair.split("=");
					myMap.put(keyValue[0], keyValue[1]);
					if (keyValue[0].equals("exceptionlevel")) {
						String key = keyValue[0];

						String a = PropertyLoader.getLabel(language, keyValue[1]);
						if (!keys.contains(a)) {
							keys.add(a);
						}
					}
				}
				Map temp = details_altered.get(i);

				exceptionLevelIds.put(PropertyLoader.getLabel(language, (String) temp.get("exceptionlevel")),
						temp.get("levelid"));
				exceptionTypeIds.put(PropertyLoader.getLabel(language, (String) temp.get("exceptiontype")),
						temp.get("exceptiontypeid"));
				vehicleIds.put(temp.get("vehiclename").toString().replace("@", "/"), temp.get("vehicleid"));
			}

			Map jobToAdd = null;
			for (String keytoAdd : keys) {
				jobToAdd = new HashMap();
				jobToAdd.put("key", keytoAdd);
				jobToAdd.put("total", 0.0);
				jobToAdd.put("exceptionLevelIds", exceptionLevelIds);
				jobToAdd.put("exceptionTypeIds", exceptionTypeIds);
				jobToAdd.put("vehicleIds", vehicleIds);

				for (Map<String, Object> element : details_altered) {

					String s = element.toString();
					s = s.replace("{", "").replace("}", "");
					String[] pairs = s.split(",");
					Map myMap = new HashMap<>();
					for (String pair2 : pairs) {
						String pair = pair2;
						pair = pair.trim();
						String[] keyValue = pair.split("=");

						String a = PropertyLoader.getLabel(language, keyValue[1]);
						myMap.put(keyValue[0], a);
					}
					JSONObject job = new JSONObject(myMap);
					String key = job.getString("exceptionlevel");
					if (key.equals(keytoAdd)) {
						List arr = null;
						if (jobToAdd.containsKey("values")) {
							arr = (ArrayList) jobToAdd.get("values");
						} else {
							arr = new ArrayList();
						}
						/*
						 * Map values=new HashMap();
						 *
						 * values.put("label", job.getString("exceptiontype")); values.put("value",
						 * job.get("violationscount"));
						 */
						List values = new ArrayList();
						values.add(job.getString("vehiclename"));
						values.add(parseDouble(job.get("violationscount")));

						jobToAdd.put("total", (Double) jobToAdd.get("total") + parseDouble(job.get("violationscount")));
						arr.add(values);
						jobToAdd.put("values", arr);
					}
				}
				retData.add(jobToAdd);
			}

			return retData;
		} else if (expt == 9) {
			List weeklyList = getWeeklyDrive(Integer.parseInt(clientid), Integer.parseInt(affiliateid),
					Integer.parseInt(transporterid), Integer.parseInt(vehicleid), exceptionlevels, startime, endtime,
					language);
			if (!weeklyList.isEmpty()) {
				List retData = new ArrayList<>();
				Map exceptionTypeIds = new Hashtable();
				Map exceptionLevelIds = new Hashtable();
				Map vehicleIds = new Hashtable();
				// Map transporterIds = new Hashtable();
				List<String> keys = new ArrayList<>();

				if (explevel.length != 0) {
					// ArrayUtils.reverse(explevel);
					if (explevel[0].equals("0")) {
						explevel = new String[3];
						explevel[0] = "3";
						explevel[1] = "2";
						explevel[2] = "1";
					}
					for (String exp : explevel) {
						if (exp.equals("3"))
							keys.add("db_alarm");
						if (exp.equals("2"))
							keys.add("db_alert");
						if (exp.equals("1"))
							keys.add("db_recording");
					}
					exceptionTypeIds.put(PropertyLoader.getLabel(language, "menu_weekly_driving"), 9);

					exceptionLevelIds.put(PropertyLoader.getLabel(language, "db_alarm"), 3);
					exceptionLevelIds.put(PropertyLoader.getLabel(language, "db_alert"), 2);
					exceptionLevelIds.put(PropertyLoader.getLabel(language, "db_recording"), 1);

					for (Object element : weeklyList) {

						Map temp = (Map) element;

						vehicleIds.put(temp.get("vehicledesc"), temp.get("vehicleid"));
					}

					Map jobToAdd = null;
					for (Object keytoAdd : keys) {
						String keyInLanguage = PropertyLoader.getLabel(language, keytoAdd.toString());

						jobToAdd = new HashMap();
						jobToAdd.put("key", keyInLanguage);
						jobToAdd.put("total", 0L);
						jobToAdd.put("exceptionLevelIds", exceptionLevelIds);
						jobToAdd.put("exceptionTypeIds", exceptionTypeIds);
						jobToAdd.put("vehicleIds", vehicleIds);

						for (int i = 0; i < weeklyList.size(); i++) {

							Map temp = (Map) weeklyList.get(i);

							List arr = null;
							if (jobToAdd.containsKey("values")) {
								arr = (ArrayList) jobToAdd.get("values");
							} else {
								arr = new ArrayList();
							}

							List values = new ArrayList();
							values.add(temp.get("vehicledesc"));
							if (keytoAdd.equals("db_alarm")) {
								values.add((temp.get("alarmcount")));

								jobToAdd.put("total", Integer.parseInt(jobToAdd.get("total").toString())
										+ Integer.parseInt(temp.get("alarmcount").toString()));
							} else if (keytoAdd.equals("db_alert")) {
								values.add((temp.get("alertcount")));
								jobToAdd.put("total", Integer.parseInt(jobToAdd.get("total").toString())
										+ Integer.parseInt(temp.get("alertcount").toString()));

							} else {
								values.add((temp.get("recordingcount")));
								jobToAdd.put("total", Integer.parseInt(jobToAdd.get("total").toString())
										+ Integer.parseInt(temp.get("recordingcount").toString()));

							}
							arr.add(values);
							jobToAdd.put("values", arr);

						}
						retData.add(jobToAdd);
					}
				}
				if (!weeklyList.isEmpty())
					return retData;
				else
					return weeklyList;
			} else
				return null;
		} else if (expt == 8) {
			List weeklyRestList = getWeeklyRest(Integer.parseInt(clientid), Integer.parseInt(affiliateid),
					Integer.parseInt(transporterid), Integer.parseInt(vehicleid), exceptionlevels, startime, endtime,
					language);
			if (!weeklyRestList.isEmpty()) {
				List retData = new ArrayList<>();
				Map exceptionTypeIds = new Hashtable();
				Map exceptionLevelIds = new Hashtable();
				Map vehicleIds = new Hashtable();
				// Map transporterIds = new Hashtable();
				List<String> keys = new ArrayList<>();

				if (explevel.length != 0) {
					// ArrayUtils.reverse(explevel);
					if (explevel[0].equals("0")) {
						explevel = new String[3];
						explevel[0] = "3";
						explevel[1] = "2";
						explevel[2] = "1";
					}
					for (String exp : explevel) {
						if (exp.equals("3"))
							keys.add("db_alarm");
						if (exp.equals("2"))
							keys.add("db_alert");
						if (exp.equals("1"))
							keys.add("db_recording");
					}
					exceptionTypeIds.put(PropertyLoader.getLabel(language, "menu_weekly_rest_time"), 8);

					exceptionLevelIds.put(PropertyLoader.getLabel(language, "db_alarm"), 3);
					exceptionLevelIds.put(PropertyLoader.getLabel(language, "db_alert"), 2);
					exceptionLevelIds.put(PropertyLoader.getLabel(language, "db_recording"), 1);

					for (Object element : weeklyRestList) {

						// System.out.println(weeklyRestList.get(i));

						Map temp = (Map) element;
						// System.out.println(temp.get("vehicledesc"));
						vehicleIds.put(temp.get("vehicledesc"), temp.get("vehicleid"));
					}

					Map jobToAdd = null;
					for (Object keytoAdd : keys) {
						String keyInLanguage = PropertyLoader.getLabel(language, keytoAdd.toString());

						jobToAdd = new HashMap();
						jobToAdd.put("key", keyInLanguage);
						jobToAdd.put("total", 0L);
						jobToAdd.put("exceptionLevelIds", exceptionLevelIds);
						jobToAdd.put("exceptionTypeIds", exceptionTypeIds);
						jobToAdd.put("vehicleIds", vehicleIds);

						for (int i = 0; i < weeklyRestList.size(); i++) {
							Map temp = (Map) weeklyRestList.get(i);

							List arr = null;
							if (jobToAdd.containsKey("values")) {
								arr = (ArrayList) jobToAdd.get("values");
							} else {
								arr = new ArrayList();
							}

							List values = new ArrayList();
							values.add(temp.get("vehicledesc"));
							if (keytoAdd.equals("db_alarm")) {
								values.add((temp.get("alarmcount")));

								jobToAdd.put("total", Integer.parseInt(jobToAdd.get("total").toString())
										+ Integer.parseInt(temp.get("alarmcount").toString()));
							} else if (keytoAdd.equals("db_alert")) {
								values.add((temp.get("alertcount")));
								jobToAdd.put("total", Integer.parseInt(jobToAdd.get("total").toString())
										+ Integer.parseInt(temp.get("alertcount").toString()));

							} else {
								values.add((temp.get("recordingcount")));
								jobToAdd.put("total", Integer.parseInt(jobToAdd.get("total").toString())
										+ Integer.parseInt(temp.get("recordingcount").toString()));

							}
							arr.add(values);
							jobToAdd.put("values", arr);

						}
						retData.add(jobToAdd);
					}
				}
				if (!weeklyRestList.isEmpty())
					return retData;
				else
					return weeklyRestList;
			} else
				return null;

		} else
			return null;

	}

	@Override
	public List vehiclewiseDriverBehaviourViolationsPer100Km(String clientid, String affiliateid, String transporterid,
			String vehicleid, String startime, String endtime, String exptype, String[] explevel, String language)
			throws Exception {
		List details = new ArrayList<>();
		double recordingWeight = 1;
		double alertWeight = 1;
		double alarmWeight = 1;
		if (explevel.length != 0) {
			ArrayUtils.reverse(explevel);
			if (explevel[0].equals("0")) {
				explevel = new String[3];
				explevel[0] = "3";
				explevel[1] = "2";
				explevel[2] = "1";
			}

			String exceptionlevels = String.join(",", explevel);

			if (exceptionlevels.contains("1") && exceptionlevels.contains("2") && exceptionlevels.contains("3")) {
				recordingWeight = 0.15;
				alertWeight = 0.28;
				alarmWeight = 0.57;
			} else if (exceptionlevels.contains("2") && exceptionlevels.contains("3")) {
				recordingWeight = 0;
				alertWeight = 0.33;
				alarmWeight = 0.67;
			} else if (exceptionlevels.contains("1") && exceptionlevels.contains("3")) {
				recordingWeight = 0.21;
				alertWeight = 0;
				alarmWeight = 0.79;
			} else if (exceptionlevels.contains("1") && exceptionlevels.contains("2")) {
				recordingWeight = 0.35;
				alertWeight = 0.65;
				alarmWeight = 0;
			} else if (exceptionlevels.contains("1")) {
				recordingWeight = 1.0;
				alertWeight = 0.0;
				alarmWeight = 0.0;
			} else if (exceptionlevels.contains("2")) {
				recordingWeight = 0.0;
				alertWeight = 1.0;
				alarmWeight = 0.0;
			} else if (exceptionlevels.contains("3")) {
				recordingWeight = 0.0;
				alertWeight = 0.0;
				alarmWeight = 1.0;
			}
		}
		int len = 0;

		for (String exp : explevel) {

			if (affiliateid == null && affiliateid.length() == 0) {
				affiliateid = "0";
			}

			if (transporterid == null && transporterid.length() == 0) {
				transporterid = "0";
			}

			if (vehicleid == null && vehicleid.length() == 0) {
				vehicleid = "0";
			}

			if (startime == null && startime.length() == 0) {
				startime = null;
			}

			if (endtime == null && endtime.length() == 0) {
				endtime = null;
			}

			String query = "select * from \"sp_vehiclewise_driverbehaviourviolations_per100km_dashboard\"(" + clientid
					+ "::int4," + affiliateid + "::int4," + transporterid + "::int4," + vehicleid + "::int4,'"
					+ (startime) + "'::date,'" + (endtime) + "'::date," + exptype + "::int4,0::int4,";
			if (exp.equals("1"))
				query += recordingWeight + ")";

			if (exp.equals("2"))
				query += alertWeight + ")";

			if (exp.equals("3"))
				query += alarmWeight + ")";

			query += " where exceptiontypeid=" + exptype + "::int4 ";

			query += " and levelid=" + exp + "::int4";

			if (explevel.length == 1)
				query += " and driverexceptionper100kms>0";

			query += " order by vehicledesc asc";

			List temp = jdbcTemplate.queryForList(query);

			details.addAll(temp);
			len = temp.size();
		}
		List<Map<String, Object>> details_temp = details;
		List<Map<String, Object>> details_altered = new ArrayList<>();
		if (explevel.length == 3) {
			for (int i = 0; i < len; i++) {
				Map map1 = details_temp.get(i);
				Map map2 = details_temp.get(len + i);
				Map map3 = details_temp.get((len * 2) + i);

				double vcount1 = parseDouble(map1.get("driverexceptionper100kms"));
				double vcount2 = parseDouble(map2.get("driverexceptionper100kms"));
				double vcount3 = parseDouble(map3.get("driverexceptionper100kms"));

				if (vcount1 < 0) {
					map1.put("driverexceptionper100kms", 0);
					vcount1 = 0;
				}

				if (vcount2 < 0) {
					map2.put("driverexceptionper100kms", 0);
					vcount2 = 0;
				}

				if (vcount3 < 0) {
					map3.put("driverexceptionper100kms", 0);
					vcount3 = 0;
				}

				if ((vcount1 == 0 && vcount2 == 0 && vcount3 == 0)) {
					continue;
				} else {
					details_altered.add(map1);
					details_altered.add(map2);
					details_altered.add(map3);
				}

			}
		} else if (explevel.length == 2) {
			for (int i = 0; i < len; i++) {
				Map map1 = details_temp.get(i);
				Map map2 = details_temp.get(len + i);

				double vcount1 = parseDouble(map1.get("driverexceptionper100kms"));
				double vcount2 = parseDouble(map2.get("driverexceptionper100kms"));

				if (vcount1 < 0) {
					map1.put("driverexceptionper100kms", 0);
					vcount1 = 0;
				}

				if (vcount2 < 0) {
					map2.put("driverexceptionper100kms", 0);
					vcount2 = 0;
				}

				if (vcount1 == 0 && vcount2 == 0) {
					continue;
				} else {
					details_altered.add(map1);
					details_altered.add(map2);
				}

			}
		} else
			details_altered = details_temp;
		// return details;

		List retData = new ArrayList<>();

		List<String> keys = new ArrayList<>();
		Map exceptionTypeIds = new Hashtable();
		Map exceptionLevelIds = new Hashtable();
		Map vehicleIds = new Hashtable();
		for (int i = 0; i < details_altered.size(); i++) {

			String s = details_altered.get(i).toString();
			s = s.replace("{", "").replace("}", "");
			String[] pairs = s.split(",");
			Map myMap = new HashMap<>();
			for (String pair2 : pairs) {
				String pair = pair2;
				pair = pair.trim();
				String[] keyValue = pair.split("=");
				myMap.put(keyValue[0], keyValue[1]);
				if (keyValue[0].equals("exceptionlevel")) {
					String key = keyValue[0];

					String a = PropertyLoader.getLabel(language, keyValue[1]);
					if (!keys.contains(a)) {
						keys.add(a);
					}
				}
			}
			String str = details_altered.get(i).toString().replace("/", "@");
			JSONObject temp = new JSONObject(str.replace("=", ":"));
			exceptionLevelIds.put(PropertyLoader.getLabel(language, (String) temp.get("exceptionlevel")),
					temp.get("levelid"));
			exceptionTypeIds.put(PropertyLoader.getLabel(language, (String) temp.get("exceptiontype")),
					temp.get("exceptiontypeid"));
			vehicleIds.put(temp.get("vehicledesc").toString().replace("@", "/"), temp.get("vehicleid"));
		}

		Map jobToAdd = null;
		for (String keytoAdd : keys) {
			jobToAdd = new HashMap();
			jobToAdd.put("key", keytoAdd);
			jobToAdd.put("total", 0.0);
			jobToAdd.put("exceptionLevelIds", exceptionLevelIds);
			jobToAdd.put("exceptionTypeIds", exceptionTypeIds);
			jobToAdd.put("vehicleIds", vehicleIds);

			for (Map<String, Object> element : details_altered) {

				String s = element.toString();
				s = s.replace("{", "").replace("}", "");
				String[] pairs = s.split(",");
				Map myMap = new HashMap<>();
				for (String pair2 : pairs) {
					String pair = pair2;
					pair = pair.trim();
					String[] keyValue = pair.split("=");

					String a = PropertyLoader.getLabel(language, keyValue[1]);
					myMap.put(keyValue[0], a);
				}
				JSONObject job = new JSONObject(myMap);
				String key = job.getString("exceptionlevel");
				if (key.equals(keytoAdd)) {
					List arr = null;
					if (jobToAdd.containsKey("values")) {
						arr = (ArrayList) jobToAdd.get("values");
					} else {
						arr = new ArrayList();
					}
					/*
					 * Map values=new HashMap();
					 *
					 * values.put("label", job.getString("exceptiontype")); values.put("value",
					 * job.get("violationscount"));
					 */
					List values = new ArrayList();
					values.add(job.getString("vehicledesc"));
					values.add(parseDouble(job.get("driverexceptionper100kms")));

					jobToAdd.put("total",
							(Double) jobToAdd.get("total") + parseDouble(job.get("driverexceptionper100kms")));
					arr.add(values);
					jobToAdd.put("values", arr);
				}
			}
			retData.add(jobToAdd);
		}

		return retData;

	}

	@Override
	public List weeklyRestViolations(String affiliateid, String transportername, String vehicledesc, String startime,
			String endtime) throws Exception {
		String query = "SELECT transportername,alert,alarm,recording FROM \"weeklyrestviolations\" where 1=1";
		if (affiliateid != null && affiliateid.length() > 0) {
			query += " and " + "affiliateid='" + affiliateid + "'";
		}

		if (transportername != null && transportername.length() > 0) {
			query += " and " + "transportername='" + transportername + "'";
		}

		if (vehicledesc != null && vehicledesc.length() > 0) {
			query += " and vehiclename='" + vehicledesc + "'";
		}

		if (startime != null && startime.length() > 0) {
			query += " and startdatetime>='" + startime + "'";
		}

		if (endtime != null && endtime.length() > 0) {
			query += " and enddatetime<='" + endtime + "'";
		}
		return jdbcTemplate.queryForList(query);
	}

	/* created by Arya */

	/* not in use */

}
