package com.camtrack.manualsubtractionandrecovery.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.camtrack.config.Utils;
import com.camtrack.entities.User;
import com.camtrack.manualsubtractionandrecovery.bean.ManualaccicpBean;
import com.camtrack.manualsubtractionandrecovery.bean.Manualsubnrec;
import com.camtrack.manualsubtractionandrecovery.bean.Manualsubnreclist;
import com.camtrack.manualsubtractionandrecovery.bean.RecoveryBean;
import com.camtrack.manualsubtractionandrecovery.bean.SubstractionRecovBean;

@Repository("ManualRecoveryDao")
@PropertySource({ "classpath:sqlqueries.properties" })
public class ManualSubtractionAndRecoveryDaoImpl implements ManualSubtractionAndRecoveryDaoInterface {
	@Value("${userrrole.affiliateid}")
	private Integer affiliateid;
	@Value("${userrrole.clientid}")
	private Integer clientid;
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Value("${userrrole.superadminid}")
	private Integer superadminid;
	@Value("${userrrole.transporterid}")
	private Integer transporterid;

	@Override
	public int deactivateRecord(int customerid, int affid, int transid, int driverid, String occdate)
			throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date = format.parse(occdate);

		int count = 0;

		String query = this.environment.getRequiredProperty("maccip.delete");
		count = this.jdbcTemplate.update(query, new Object[] { Integer.valueOf(customerid), Integer.valueOf(affid),
				Integer.valueOf(transid), Integer.valueOf(driverid), date });

		String query1 = this.environment.getRequiredProperty("manualrec.delete");
		count = this.jdbcTemplate.update(query, new Object[] { Integer.valueOf(customerid), Integer.valueOf(affid),
				Integer.valueOf(transid), Integer.valueOf(driverid), date });

		String query2 = this.environment.getRequiredProperty("manualsub.delete");
		return this.jdbcTemplate.update(query, new Object[] { Integer.valueOf(customerid), Integer.valueOf(affid),
				Integer.valueOf(transid), Integer.valueOf(driverid), date });
	}

	@Override
	public List fetchDrivers() throws Exception {
		String query = this.environment.getRequiredProperty("manualrecovery.alldriverslist");
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public List fetchDrivers(int clientaffiliateid, int transporterid) throws Exception {
		String query = this.environment.getRequiredProperty("manualrecovery.driverslist");
		return this.jdbcTemplate.queryForList(query,
				new Object[] { Integer.valueOf(clientaffiliateid), Integer.valueOf(transporterid) });
	}

	@Override
	public List fetchDrivers(int clientid, int clientaffiliateid, int transporterid) throws Exception {
		String query = this.environment.getRequiredProperty("manualrecovery.driverslist");
		return this.jdbcTemplate.queryForList(query,
				new Object[] { Integer.valueOf(clientaffiliateid), Integer.valueOf(transporterid) });
	}

	@Override
	public List fetchDriversAffiliates(int clientaffiliateid) throws Exception {
		String query = this.environment.getRequiredProperty("manualrecovery.driverslistbyaffiliate");
		return this.jdbcTemplate.queryForList(query, new Object[] { Integer.valueOf(clientaffiliateid) });
	}

	@Override
	public List fetchDriversTransporters(int transporterid) throws Exception {
		String query = this.environment.getRequiredProperty("manualrecovery.driverslistbytransporter");
		return this.jdbcTemplate.queryForList(query, new Object[] { Integer.valueOf(transporterid) });
	}

	@Override
	public List<RecoveryBean> fetchMyList(User user, String codeclient, String codeafiliate, String transporter,
			String codedriver, String fromdate, String todate) throws Exception {
		String addString, sqlin = "";
		String andsql = "";
		String completeRequest = "";
		String allconstructsqlin = "";
		Date date01 = Utils.StringToDate("", fromdate, "yyyy-MM-dd");
		Date date02 = Utils.StringToDate("", todate, "yyyy-MM-dd");

		if (Objects.isNull(date02) || Objects.isNull(date01)) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date02);
		calendar.add(5, 1);

		date02 = calendar.getTime();
		Integer typeInfos = Integer.valueOf(-1);
		if (Objects.isNull(codedriver) || codedriver.equalsIgnoreCase("")) {
			if (Objects.isNull(transporter) || transporter.equalsIgnoreCase("")) {
				if (Objects.isNull(codeafiliate) || codeafiliate.equalsIgnoreCase("")) {
					if (Objects.isNull(codeclient) || codeclient.equalsIgnoreCase("")) {

						if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {

							sqlin = this.environment.getRequiredProperty("clientid.listidclient");
						} else {
							sqlin = this.environment.getRequiredProperty("clientid.listidfromuserightsbyuserid");
							sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
						}

						typeInfos = Integer.valueOf(1);

					} else if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid
							|| user.getReelrole().getIdtyperole().getUserroleid() == this.clientid) {
						try {
							if (codeclient.equalsIgnoreCase("0")) {

								if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {

									sqlin = this.environment.getRequiredProperty("clientid.listidclient");
								} else {
									sqlin = this.environment
											.getRequiredProperty("clientid.listidfromuserightsbyuserid");
									sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
								}

							} else {

								sqlin = codeclient;
							}
							typeInfos = Integer.valueOf(1);
						} catch (Exception ex) {
							ex.printStackTrace();

						}

					}

				} else if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid
						|| user.getReelrole().getIdtyperole().getUserroleid() == this.clientid
						|| user.getReelrole().getIdtyperole().getUserroleid() == this.affiliateid) {

					try {
						if (codeafiliate.equalsIgnoreCase("0")) {

							if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {

								sqlin = this.environment.getRequiredProperty("affiliateid.listidaffiliate");
							} else {
								sqlin = this.environment.getRequiredProperty("affiliateid.listidfromuserightsbyuserid");
								sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
							}

						} else {

							sqlin = codeafiliate;
						}
						typeInfos = Integer.valueOf(2);
					} catch (Exception ex) {
						ex.printStackTrace();

					}

				}

			} else if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid
					|| user.getReelrole().getIdtyperole().getUserroleid() == this.clientid
					|| user.getReelrole().getIdtyperole().getUserroleid() == this.affiliateid
					|| user.getReelrole().getIdtyperole().getUserroleid() == this.transporterid) {
				try {
					if (transporter.equalsIgnoreCase("0")) {

						if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {

							sqlin = this.environment.getRequiredProperty("transporterid.listidtransporter");
						} else {
							sqlin = this.environment.getRequiredProperty("transporterid.listidfromuserightsbyuserid");
							sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
						}

					} else {

						sqlin = transporter;
					}

					typeInfos = Integer.valueOf(3);
				} catch (Exception ex) {
					ex.printStackTrace();

				}

			}

		} else if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid
				|| user.getReelrole().getIdtyperole().getUserroleid() == this.clientid
				|| user.getReelrole().getIdtyperole().getUserroleid() == this.affiliateid
				|| user.getReelrole().getIdtyperole().getUserroleid() == this.transporterid) {

			try {
				if (codedriver.equalsIgnoreCase("0")) {

					if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {

						sqlin = this.environment.getRequiredProperty("driveridid.listidriver");
					} else {
						sqlin = this.environment.getRequiredProperty("driveridid.listidfromuserightsbyuserid");
						sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
					}

				} else {

					sqlin = codedriver;
				}
				typeInfos = Integer.valueOf(4);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		List<RecoveryBean> listbean = new ArrayList<>();
		/**
		 * if (Utils.scpdescandtoTransporter()) { addString = " and m.transporterid =
		 * s.transporterid"; } else { addString = " and s.transporterid=0"; }
		 */
		addString = " and m.transporterid = s.transporterid ";
		String query = "select dateofocurrence,clientid,affiliateid,transporterid,driverid, sum(points) as points from (select m.dateofocurrence,m.clientid,m.affiliateid,m.transporterid,m.driverid,m.recoveryid,m.points*(select s.points from scprecovery s where m.recoveryid = s.recoveryid and m.clientid = s.clientid and m.affiliateid = s.affiliateid "
				+ addString + ") as points  from manualrecovery m  where m.dateofocurrence >= '" + fromdate
				+ "' and m.dateofocurrence < '" + Utils.DateFormat(date02, "yyyy-MM-dd")
				+ "' ZZZZZZ)T group by dateofocurrence,clientid,affiliateid,transporterid,driverid";

		if (typeInfos.intValue() == 4) {
			query = query.replaceAll("ZZZZZZ", " and (m.driverid in (" + sqlin + "))");
		} else if (typeInfos.intValue() == 3) {
			query = query.replaceAll("ZZZZZZ", " and (m.transporterid in (" + sqlin + "))");
		} else if (typeInfos.intValue() == 2) {
			query = query.replaceAll("ZZZZZZ", " and (m.affiliateid in (" + sqlin + "))");
		} else if (typeInfos.intValue() == 1) {
			query = query.replaceAll("ZZZZZZ", " and (m.clientid in (" + sqlin + "))");
		} else {
			return listbean;
		}

		final HashMap<String, SubstractionRecovBean> recovery = new HashMap<>();
		List<SubstractionRecovBean> recoveryBean = this.jdbcTemplate.query(query,
				new RowMapper<SubstractionRecovBean>() {
					@Override
					public SubstractionRecovBean mapRow(ResultSet rs, int rowNum) throws SQLException {
						SubstractionRecovBean d = new SubstractionRecovBean();
						d.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
						d.setDriverid(Integer.valueOf(rs.getInt("driverid")));
						d.setClientid(Integer.valueOf(rs.getInt("clientid")));
						d.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
						d.setDateofocurrence(rs.getDate("dateofocurrence"));
						d.setPoints(Integer.valueOf(rs.getInt("points")));
						recovery.put(
								d.getClientid() + "" + d.getAffiliateid() + "" + d.getTransporterid() + ""
										+ d.getDriverid() + "" + Utils.DateFormat(d.getDateofocurrence(), "yyyyMMdd"),
								d);
						return d;
					}
				});

		query = "select dateofocurrence,clientid,affiliateid,transporterid,driverid, sum(points) as points from (select m.dateofocurrence,m.clientid,m.affiliateid,m.transporterid,m.driverid,m.visualparamid,m.points*(select s.points from scpvisualparameter s where m.visualparamid = s.visualparamid and m.clientid = s.clientid and m.affiliateid = s.affiliateid "
				+ addString + ") as points from manualsubstraction m  where m.dateofocurrence >= '" + fromdate
				+ "' and m.dateofocurrence < '" + Utils.DateFormat(date02, "yyyy-MM-dd")
				+ "' ZZZZZZ)T group by dateofocurrence,clientid,affiliateid,transporterid,driverid";

		if (typeInfos.intValue() == 4) {
			query = query.replaceAll("ZZZZZZ", " and (m.driverid in (" + sqlin + "))");
		} else if (typeInfos.intValue() == 3) {
			query = query.replaceAll("ZZZZZZ", " and (m.transporterid in (" + sqlin + "))");
		} else if (typeInfos.intValue() == 2) {
			query = query.replaceAll("ZZZZZZ", " and (m.affiliateid in (" + sqlin + "))");
		} else if (typeInfos.intValue() == 1) {
			query = query.replaceAll("ZZZZZZ", " and (m.clientid in (" + sqlin + "))");
		}
		final HashMap<String, SubstractionRecovBean> substraction = new HashMap<>();
		List<SubstractionRecovBean> substractionBean = this.jdbcTemplate.query(query,
				new RowMapper<SubstractionRecovBean>() {

					@Override
					public SubstractionRecovBean mapRow(ResultSet rs, int rowNum) throws SQLException {
						SubstractionRecovBean d = new SubstractionRecovBean();
						d.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
						d.setDriverid(Integer.valueOf(rs.getInt("driverid")));
						d.setClientid(Integer.valueOf(rs.getInt("clientid")));
						d.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
						d.setDateofocurrence(rs.getDate("dateofocurrence"));
						d.setPoints(Integer.valueOf(rs.getInt("points")));
						substraction.put(
								d.getClientid() + "" + d.getAffiliateid() + "" + d.getTransporterid() + ""
										+ d.getDriverid() + "" + Utils.DateFormat(d.getDateofocurrence(), "yyyyMMdd"),
								d);

						return d;
					}
				});

		query = "select m.dateofocurrence,m.clientid,m.affiliateid,m.transporterid,m.driverid,m.noofaccident,m.transportericp from manualaccicp m  where m.dateofocurrence >= '"
				+ fromdate + "' and m.dateofocurrence < '" + Utils.DateFormat(date02, "yyyy-MM-dd") + "' ZZZZZZ";

		if (typeInfos.intValue() == 4) {
			query = query.replaceAll("ZZZZZZ", " and (m.driverid in (" + sqlin + "))");
		} else if (typeInfos.intValue() == 3) {
			query = query.replaceAll("ZZZZZZ", " and (m.transporterid in (" + sqlin + "))");
		} else if (typeInfos.intValue() == 2) {
			query = query.replaceAll("ZZZZZZ", " and (m.affiliateid in (" + sqlin + "))");
		} else if (typeInfos.intValue() == 1) {
			query = query.replaceAll("ZZZZZZ", " and (m.clientid in (" + sqlin + "))");
		}
		final HashMap<String, ManualaccicpBean> manualacc = new HashMap<>();
		List<ManualaccicpBean> manualaccicpBean = this.jdbcTemplate.query(query, new RowMapper<ManualaccicpBean>() {
			@Override
			public ManualaccicpBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				ManualaccicpBean d = new ManualaccicpBean();
				d.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
				d.setDriverid(Integer.valueOf(rs.getInt("driverid")));
				d.setClientid(Integer.valueOf(rs.getInt("clientid")));
				d.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
				d.setNoofaccident(Integer.valueOf(rs.getInt("noofaccident")));
				d.setDateofocurrence(rs.getDate("dateofocurrence"));
				d.setTransportericp(Integer.valueOf(rs.getInt("transportericp")));
				manualacc.put(d.getClientid() + "" + d.getAffiliateid() + "" + d.getTransporterid() + ""
						+ d.getDriverid() + "" + Utils.DateFormat(d.getDateofocurrence(), "yyyyMMdd"), d);

				return d;
			}
		});

		for (Map.Entry entry : substraction.entrySet()) {
			String key = (String) entry.getKey();
			SubstractionRecovBean subsbean = (SubstractionRecovBean) entry.getValue();
			SubstractionRecovBean recvbean = recovery.get(key);
			ManualaccicpBean acciden = manualacc.get(key);
			RecoveryBean smallbean = new RecoveryBean();
			smallbean.setAffid(subsbean.getAffiliateid());
			smallbean.setCust(subsbean.getClientid());
			smallbean.setDat(subsbean.getDateofocurrence());
			smallbean.setDrivid(subsbean.getDriverid());
			smallbean.setTrpid(subsbean.getTransporterid());
			smallbean.setSubp(subsbean.getPoints());
			if (!Objects.isNull(recvbean)) {
				smallbean.setRcvp(recvbean.getPoints());
			} else {
				smallbean.setRcvp(Integer.valueOf(0));
			}

			if (!Objects.isNull(acciden)) {
				smallbean.setNbacc(acciden.getNoofaccident());
				smallbean.setTcip(acciden.getTransportericp());
			} else {
				smallbean.setNbacc(Integer.valueOf(0));
				smallbean.setTcip(Integer.valueOf(0));
			}

			listbean.add(smallbean);
			recovery.remove(key);
			manualacc.remove(key);
		}

		if (!recovery.isEmpty()) {
			for (Map.Entry entry : recovery.entrySet()) {
				String key = (String) entry.getKey();
				SubstractionRecovBean subsbean = (SubstractionRecovBean) entry.getValue();
				ManualaccicpBean acciden = manualacc.get(key);
				RecoveryBean smallbean = new RecoveryBean();
				smallbean.setAffid(subsbean.getAffiliateid());
				smallbean.setCust(subsbean.getClientid());
				smallbean.setDat(subsbean.getDateofocurrence());
				smallbean.setDrivid(subsbean.getDriverid());
				smallbean.setTrpid(subsbean.getTransporterid());
				smallbean.setSubp(Integer.valueOf(0));
				smallbean.setRcvp(subsbean.getPoints());

				if (!Objects.isNull(acciden)) {
					smallbean.setNbacc(acciden.getNoofaccident());
					smallbean.setTcip(acciden.getTransportericp());
				} else {
					smallbean.setNbacc(Integer.valueOf(0));
					smallbean.setTcip(Integer.valueOf(0));
				}

				listbean.add(smallbean);
				manualacc.remove(key);
			}
		}

		if (!manualacc.isEmpty()) {
			for (Map.Entry entry : manualacc.entrySet()) {
				String key = (String) entry.getKey();
				ManualaccicpBean acciden = (ManualaccicpBean) entry.getValue();
				RecoveryBean smallbean = new RecoveryBean();
				smallbean.setAffid(acciden.getAffiliateid());
				smallbean.setCust(acciden.getClientid());
				smallbean.setDat(acciden.getDateofocurrence());
				smallbean.setDrivid(acciden.getDriverid());
				smallbean.setTrpid(acciden.getTransporterid());
				smallbean.setSubp(Integer.valueOf(0));
				smallbean.setRcvp(Integer.valueOf(0));
				smallbean.setNbacc(acciden.getNoofaccident());
				smallbean.setTcip(acciden.getTransportericp());

				listbean.add(smallbean);
			}
		}

		return listbean;
	}

	@Override
	public int fetchresetfreqbyaffiliateid(int affiliateid) throws Exception {
		String queryforfre = "select noofcalendaryear from scp where  affiliateid=" + affiliateid + "";
		int freq = 1;
		try {
			freq = this.jdbcTemplate.queryForObject(queryforfre, Integer.class).intValue();
		} catch (Exception e) {
			freq = 1;
		}

		return freq;
	}

	@Override
	public List getRawStaticList(int clientid, int affiliateid, int transporterid, int driverid, int startyear,
			int endyear, int startmonth, int endmonth) throws Exception {
		System.out.println("s y andey" + endyear + "st" + startyear);
		int yeardiffernce = endyear - startyear;
		System.out.println("year differnce is--->" + yeardiffernce);

		String queryforfre = "select noofcalendaryear from scp where affiliateid=" + affiliateid + "";
		int freq = this.jdbcTemplate.queryForObject(queryforfre, Integer.class).intValue();

		System.out.println("freq is--->" + freq);

		if (freq <= yeardiffernce) {

			do {
				startmonth = 1;
				startyear += freq;
				System.out.println("now start year is-->" + startyear + "--->start month-->" + startmonth);
			} while (freq <= endyear - startyear);
		}

		// ranklist = new ArrayList();

		String query = "select   *,((initialpoint)::numeric + coalesce( recoverypoint1,0.0)::numeric +   coalesce( recoverypoint2,0.0)::numeric - (coalesce(subpoints,0.0)::NUMERIC + coalesce(excpnpoints,0.0)::NUMERIC)) as totalremainingpoints ,coalesce(coalesce(subpoints,0.0)::NUMERIC + coalesce(excpnpoints,0.0)::NUMERIC,0.0)::NUMERIC as totalsubstractedpoints,  coalesce( recoverypoint1,0.0)::numeric +   coalesce( recoverypoint2,0.0)::numeric  as recvrypoints,case when ((initialpoint)::numeric + coalesce( recoverypoint1,0.0)::numeric +   coalesce( recoverypoint2,0.0)::numeric - (coalesce(subpoints,0.0)::NUMERIC + coalesce(excpnpoints,0.0)::NUMERIC))<= driverdisqualifiedlimit then 0  when ((initialpoint)::numeric +   coalesce( recoverypoint1,0.0)::numeric +   coalesce( recoverypoint2,0.0)::numeric - (coalesce(subpoints,0.0)::NUMERIC+coalesce(excpnpoints,0.0)::NUMERIC)) < driverredlimit  and  ((initialpoint)::numeric  +   coalesce( recoverypoint1,0.0)::numeric +   coalesce( recoverypoint2,0.0)::numeric - (coalesce(subpoints,0.0)::NUMERIC+coalesce(excpnpoints,0.0)::NUMERIC)) > driverdisqualifiedlimit then 1 when ((initialpoint)::numeric +   coalesce( recoverypoint1,0.0)::numeric +   coalesce( recoverypoint2,0.0)::numeric - (coalesce(subpoints,0.0)::NUMERIC+coalesce(excpnpoints,0.0)::NUMERIC)) < driveryellowlimit and ((initialpoint)::numeric+   coalesce( recoverypoint1,0.0)::numeric +   coalesce( recoverypoint2,0.0)::numeric - (coalesce(subpoints,0.0)::NUMERIC + coalesce(excpnpoints,0.0)::NUMERIC)) >= driverredlimit then 2 when ((initialpoint)::numeric +   coalesce( recoverypoint1,0.0)::numeric +   coalesce( recoverypoint2,0.0)::numeric - (coalesce(subpoints,0.0)::NUMERIC + coalesce(excpnpoints,0.0)::NUMERIC)) < drivergreenlimit and ((initialpoint)::numeric +  coalesce( recoverypoint1,0.0)::numeric +   coalesce( recoverypoint2,0.0)::numeric  - (coalesce(subpoints,0.0)::NUMERIC + coalesce(excpnpoints,0.0)::NUMERIC)) >= driveryellowlimit then 3 when ((initialpoint)::numeric +   coalesce( recoverypoint1,0.0)::numeric +   coalesce( recoverypoint2,0.0)::numeric  - (coalesce(subpoints,0.0)::NUMERIC + coalesce(excpnpoints,0.0)::NUMERIC)) >= drivergreenlimit then 4 end as colorzone,case when minsenioritydriver <= 0 then 0 else minsenioritydriver  end as minsenioritydriver from sp_raw_static_Report("
				+ clientid + "," + affiliateid + "," + transporterid + "," + driverid + "," + startmonth + ","
				+ endmonth + "," + startyear + "," + endyear + ")";
		System.out.println(query);

		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public Map recoveryParamMap(int clientid, int affiliateid, int transid, int driverid, String occdate)
			throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date = format.parse(occdate);

		Map Rmap = new HashMap();

		String query = this.environment.getRequiredProperty("manualsubnrec.recoveryparamlisttrans");
		List visualparams = this.jdbcTemplate.queryForList(query,
				new Object[] { Integer.valueOf(clientid), Integer.valueOf(affiliateid), Integer.valueOf(transid) });

		if (Objects.isNull(visualparams) || visualparams.isEmpty()) {
			if (transid != 0) {
				// transid = 0;
				visualparams = this.jdbcTemplate.queryForList(query,
						new Object[] { Integer.valueOf(clientid), Integer.valueOf(affiliateid), Integer.valueOf(0) });

				if (Objects.isNull(visualparams) || visualparams.isEmpty()) {
					if (affiliateid != 0) {
						// affiliateid =0;
						visualparams = this.jdbcTemplate.queryForList(query,
								new Object[] { Integer.valueOf(clientid), Integer.valueOf(0), Integer.valueOf(0) });
					}
				}

			} else if (affiliateid != 0) {
				visualparams = this.jdbcTemplate.queryForList(query,
						new Object[] { Integer.valueOf(clientid), Integer.valueOf(0), Integer.valueOf(0) });
			}
		}

		String query1 = this.environment.getRequiredProperty("manualrec.recoverypointslist");
		List visualparamlist = this.jdbcTemplate.queryForList(query1, new Object[] { Integer.valueOf(clientid),
				Integer.valueOf(affiliateid), Integer.valueOf(transid), Integer.valueOf(driverid), date });

		if (Objects.isNull(visualparamlist) || visualparamlist.isEmpty()) {
			if (driverid != 0) {
				// transid = 0;
				visualparamlist = this.jdbcTemplate.queryForList(query1, new Object[] { Integer.valueOf(clientid),
						Integer.valueOf(affiliateid), Integer.valueOf(transid), Integer.valueOf(0), date });

				if (Objects.isNull(visualparamlist) || visualparamlist.isEmpty()) {
					if (transid != 0) {
						// affiliateid =0;
						visualparamlist = this.jdbcTemplate.queryForList(query1,
								new Object[] { Integer.valueOf(clientid), Integer.valueOf(affiliateid),
										Integer.valueOf(0), Integer.valueOf(0), date });

						if (Objects.isNull(visualparamlist) || visualparamlist.isEmpty()) {
							if (affiliateid != 0) {
								// affiliateid =0;
								visualparamlist = this.jdbcTemplate.queryForList(query1,
										new Object[] { Integer.valueOf(clientid), Integer.valueOf(0),
												Integer.valueOf(0), Integer.valueOf(0), date });
							}
						}
					}
				}
			} else if (transid != 0) {
				// transid = 0;
				visualparamlist = this.jdbcTemplate.queryForList(query1, new Object[] { Integer.valueOf(clientid),
						Integer.valueOf(affiliateid), Integer.valueOf(0), Integer.valueOf(0), date });

				if (Objects.isNull(visualparamlist) || visualparamlist.isEmpty()) {
					if (affiliateid != 0) {
						// affiliateid =0;
						visualparamlist = this.jdbcTemplate.queryForList(query1,
								new Object[] { Integer.valueOf(clientid), Integer.valueOf(0), Integer.valueOf(0),
										Integer.valueOf(0), date });
					}
				}

			} else if (affiliateid != 0) {
				visualparamlist = this.jdbcTemplate.queryForList(query1, new Object[] { Integer.valueOf(clientid),
						Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), date });
			}
		}

		String query2 = this.environment.getRequiredProperty("manualrec.manualacip");
		List manualacip = this.jdbcTemplate.queryForList(query2, new Object[] { Integer.valueOf(clientid),
				Integer.valueOf(affiliateid), Integer.valueOf(transid), Integer.valueOf(driverid), date });

		if (Objects.isNull(manualacip) || manualacip.isEmpty()) {
			if (driverid != 0) {
				// transid = 0;
				manualacip = this.jdbcTemplate.queryForList(query2, new Object[] { Integer.valueOf(clientid),
						Integer.valueOf(affiliateid), Integer.valueOf(transid), Integer.valueOf(0), date });

				if (Objects.isNull(manualacip) || manualacip.isEmpty()) {
					if (transid != 0) {
						// affiliateid =0;
						manualacip = this.jdbcTemplate.queryForList(query2, new Object[] { Integer.valueOf(clientid),
								Integer.valueOf(affiliateid), Integer.valueOf(0), Integer.valueOf(0), date });

						if (Objects.isNull(manualacip) || manualacip.isEmpty()) {
							if (affiliateid != 0) {
								// affiliateid =0;
								manualacip = this.jdbcTemplate.queryForList(query2,
										new Object[] { Integer.valueOf(clientid), Integer.valueOf(0),
												Integer.valueOf(0), Integer.valueOf(0), date });
							}
						}
					}
				}
			} else if (transid != 0) {
				// transid = 0;
				manualacip = this.jdbcTemplate.queryForList(query2, new Object[] { Integer.valueOf(clientid),
						Integer.valueOf(affiliateid), Integer.valueOf(0), Integer.valueOf(0), date });

				if (Objects.isNull(manualacip) || manualacip.isEmpty()) {
					if (affiliateid != 0) {
						// affiliateid =0;
						manualacip = this.jdbcTemplate.queryForList(query2, new Object[] { Integer.valueOf(clientid),
								Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), date });
					}
				}

			} else if (affiliateid != 0) {
				manualacip = this.jdbcTemplate.queryForList(query2, new Object[] { Integer.valueOf(clientid),
						Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), date });
			}
		}

		int[] recparamid = new int[visualparams.size()];
		String[] reclabels = new String[visualparams.size()];
		int[] recpoints = new int[visualparams.size()];
		int[] manualrecid = new int[visualparams.size()];

		Iterator itr = visualparams.iterator();
		int j = 0;

		while (itr.hasNext()) {

			Map map = (Map) itr.next();
			recparamid[j] = Integer.parseInt(map.get("recoveryid").toString());

			reclabels[j] = map.get("recoverylabel").toString();

			if (visualparamlist.size() > 0) {

				Iterator itrscp = visualparamlist.iterator();
				while (itrscp.hasNext()) {
					Map mapscp = (Map) itrscp.next();
					int id = Integer.parseInt(mapscp.get("recoveryid").toString());
					if (id == recparamid[j]) {
						recpoints[j] = Integer.parseInt(mapscp.get("points").toString());
						manualrecid[j] = Integer.parseInt(mapscp.get("manualrecid").toString());
						break;
					}
					recpoints[j] = 0;
					manualrecid[j] = 0;
				}

				j++;
				continue;
			} else {
				try {
					recpoints[j] = Integer.parseInt(map.get("points").toString());
					j++;
					continue;
				} catch (Exception ex) {
					recpoints[j] = 0;

					manualrecid[j] = 0;

					j++;
				}
			}
			// recpoints[j] = 0;

			// manualrecid[j] = 0;

			// j++;
		}

		Rmap.put("recparamid", recparamid);
		Rmap.put("reclabels", reclabels);
		Rmap.put("recpoints", recpoints);
		Rmap.put("manualrecid", manualrecid);

		if (!manualacip.isEmpty()) {
			Rmap.put("manualacip", manualacip);
		} else {
			manualacip.add(Integer.valueOf(0));
			manualacip.add(Integer.valueOf(0));
			manualacip.add(Integer.valueOf(0));
			Rmap.put("manualacip", manualacip);
		}

		return Rmap;
	}

	@Override
	public int saveManualSubtractionAndRecovery(User user, Manualsubnrec manualsubnrecBean) throws Exception {
		try {
			String occDate = manualsubnrecBean.getOccurancedate();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date date = format.parse(occDate);

			Integer manualsubrecid = manualsubnrecBean.getManualsubrecid();
			Date dates = new Date();
			String query = this.environment.getRequiredProperty("manualaccip.insert");
			String qry = this.environment.getRequiredProperty("manualaccip.update");
			String selectid = this.environment.getRequiredProperty("manualaccip.selectid");
			Integer tcip = Integer.valueOf(0);
			Integer driverid = Integer.valueOf(0);
			if (!Objects.isNull(manualsubnrecBean.getTcip())) {
				tcip = manualsubnrecBean.getTcip();
			}

			if (!Objects.isNull(manualsubnrecBean.getDriverid())) {
				driverid = manualsubnrecBean.getDriverid();
			}

			if (Objects.isNull(manualsubrecid) || manualsubrecid.intValue() == 0) {
				try {
					manualsubrecid = this.jdbcTemplate.queryForObject(selectid,
							new Object[] { manualsubnrecBean.getCustomerid(), manualsubnrecBean.getClientaffiliateid(),
									manualsubnrecBean.getTransporterid(), driverid, date },
							Integer.class);
				} catch (EmptyResultDataAccessException e) {
					manualsubrecid = Integer.valueOf(0);
				}
			}

			if (Objects.isNull(manualsubrecid) || manualsubrecid.intValue() == 0) {

				try {
					this.jdbcTemplate.update(query,
							new Object[] { manualsubnrecBean.getCustomerid(), manualsubnrecBean.getClientaffiliateid(),
									manualsubnrecBean.getTransporterid(), driverid,
									manualsubnrecBean.getAccidentpoints(), tcip, user.getUserid(), dates,
									user.getUserid(), dates, date });
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else if (manualsubrecid.intValue() > 0) {

				this.jdbcTemplate.update(qry, new Object[] { manualsubnrecBean.getAccidentpoints(), tcip,
						user.getUserid(), new Date(), manualsubrecid });
			}

			// if (driverid.intValue() > 0) {

			String query1 = this.environment.getRequiredProperty("manualsub.insert");
			String query2 = this.environment.getRequiredProperty("manualsub.update");
			selectid = this.environment.getRequiredProperty("manualsub.selectid");
			Integer[] vparamid = manualsubnrecBean.getVparamid();
			Integer[] vparampoints = manualsubnrecBean.getVpoints();
			Integer[] manuasubid = manualsubnrecBean.getManualsubid();

			if (!Objects.isNull(vparamid) && !Objects.isNull(vparampoints) && !Objects.isNull(manuasubid)) {
				int lengths = vparamid.length;
				if (lengths == vparampoints.length && lengths == manuasubid.length) {
					for (int i = 0; i < vparamid.length; i++) {
						Integer manualid;
						try {
							manualid = this.jdbcTemplate.queryForObject(selectid,
									new Object[] { manualsubnrecBean.getCustomerid(),
											manualsubnrecBean.getClientaffiliateid(),
											manualsubnrecBean.getTransporterid(), driverid, date, vparamid[i] },
									Integer.class);
						} catch (EmptyResultDataAccessException e) {
							manualid = Integer.valueOf(0);
						}
						if (manuasubid[i].intValue() == 0 && manualid.intValue() == 0) {

							this.jdbcTemplate.update(query1,
									new Object[] { manualsubnrecBean.getCustomerid(),
											manualsubnrecBean.getClientaffiliateid(),
											manualsubnrecBean.getTransporterid(), driverid, vparamid[i],
											vparampoints[i], user.getUserid(), dates, user.getUserid(), dates, date });
						} else if (manuasubid[i].intValue() > 0 || manualid.intValue() > 0) {
							if (manuasubid[i].intValue() > 0) {
								this.jdbcTemplate.update(query2,
										new Object[] { vparampoints[i], user.getUserid(), dates, manuasubid[i] });
							} else {

								this.jdbcTemplate.update(query2,
										new Object[] { vparampoints[i], user.getUserid(), dates, manualid });
							}
						}
					}
				}
			}
			// }

			String query3 = this.environment.getRequiredProperty("manualrecovery.insert");
			String query4 = this.environment.getRequiredProperty("manualrecovery.update");
			selectid = this.environment.getRequiredProperty("manualrecovery.selectid");
			Integer[] recparamid = manualsubnrecBean.getRecoveryid();
			Integer[] recparampoints = manualsubnrecBean.getRecpoints();
			Integer[] manualrecid = manualsubnrecBean.getManualrecid();

			if (!Objects.isNull(recparamid) && !Objects.isNull(recparampoints) && !Objects.isNull(manualrecid)) {
				int lengths = recparamid.length;
				if (lengths == recparampoints.length && lengths == manualrecid.length) {
					for (int i = 0; i < recparamid.length; i++) {
						Integer manualid;
						try {
							manualid = this.jdbcTemplate.queryForObject(selectid,
									new Object[] { manualsubnrecBean.getCustomerid(),
											manualsubnrecBean.getClientaffiliateid(),
											manualsubnrecBean.getTransporterid(), driverid, date, recparamid[i] },
									Integer.class);
						} catch (EmptyResultDataAccessException e) {
							manualid = Integer.valueOf(0);
						}
						if ((Objects.isNull(manualrecid[i]) || manualrecid[i].intValue() == 0)
								&& manualid.intValue() == 0) {

							this.jdbcTemplate.update(query3, new Object[] { manualsubnrecBean.getCustomerid(),
									manualsubnrecBean.getClientaffiliateid(), manualsubnrecBean.getTransporterid(),
									driverid, recparamid[i], recparampoints[i], user.getUserid(), dates,
									user.getUserid(), dates, date });
						} else if (manualrecid[i].intValue() > 0 || manualid.intValue() > 0) {
							if (manualrecid[i].intValue() > 0) {
								this.jdbcTemplate.update(query4,
										new Object[] { recparampoints[i], user.getUserid(), dates, manualrecid[i] });
							} else {
								this.jdbcTemplate.update(query4,
										new Object[] { recparampoints[i], user.getUserid(), dates, manualid });
							}
						}
					}

					return 1;
				}
				return -1;
			}

			return -2;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List viewRawstatisticReeport(int clientid, int affiliateid, int transporterid, int driverid, int startyear,
			int endyear, int startmonth, int endmonth) throws Exception {
		String query = "select rs.recoverylabel as label,rs.recoveryid as parmid,case when a.paramid !=rs.recoveryid then 0 else sum(a.points)  end as points,4 as tableorder from recovery rs join scprecovery scp on rs.recoveryid=scp.recoveryid and scp.affiliateid="
				+ affiliateid + " and scp.clientid =" + clientid
				+ "   left join (select * from sp_raw_static_driverbased_detailedview(" + clientid + "," + affiliateid
				+ "," + transporterid + "," + driverid + "," + startmonth + "," + endmonth + "," + startyear + ","
				+ endyear
				+ ") ) as a on a.paramid=rs.recoveryid and a.tableorder=4 where rs.status=1 and rs.recoveryid != 3 and scp.affiliateid="
				+ affiliateid + " and scp.clientid =" + clientid
				+ " group by rs.recoverylabel ,rs.recoveryid,a.paramid,a.tableorder union select vp.visuallabel as label, vp.visualparamid as parmid,case when a.paramid !=vp.visualparamid then 0 else sum(a.points)  end as points,2 as tableorder from visualparameter vp join scpvisualparameter scp on vp.visualparamid= scp.visualparamid and scp.affiliateid="
				+ affiliateid + " and scp.clientid =" + clientid
				+ " left join (select * from sp_raw_static_driverbased_detailedview(" + clientid + "," + affiliateid
				+ "," + transporterid + "," + driverid + "," + startmonth + "," + endmonth + "," + startyear + ","
				+ endyear + ") ) as a on a.paramid=vp.visualparamid and a.tableorder=2 and scp.affiliateid="
				+ affiliateid + " and scp.clientid =" + clientid
				+ " group by vp.visuallabel ,vp.visualparamid,a.paramid,a.tableorder union select obc.obclabel as label, obc.obcparamid as parmid,case when a.paramid !=obc.obcparamid then 0 else sum(a.points)  end as points,1 as tableorder from obcparameter obc join scpobcparameter scp on obc.obcparamid =scp.obcparamid and scp.affiliateid="
				+ affiliateid + " and scp.clientid =" + clientid
				+ " left join (select * from sp_raw_static_driverbased_detailedview(" + clientid + "," + affiliateid
				+ "," + transporterid + "," + driverid + "," + startmonth + "," + endmonth + "," + startyear + ","
				+ endyear + ") ) as a on a.paramid=obc.obcparamid and a.tableorder=1 and scp.affiliateid=" + affiliateid
				+ " and scp.clientid =" + clientid
				+ " group by obc.obclabel ,obc.obcparamid,a.paramid,a.tableorder union select null as label,null  as parmid,0 as points,3 as tableorder order by tableorder";

		List<Map<String, Object>> tempList = this.jdbcTemplate.queryForList(query);

		Iterator itr = tempList.iterator();
		while (itr.hasNext()) {
			Map map = (Map) itr.next();

			int tableorder = ((Integer) map.get("tableorder")).intValue();

			if (tableorder == 2) {
				int paramid = ((Integer) map.get("parmid")).intValue();
				query = "select case when points!=0 then concat(dateofocurrence,' : ',points) else null end as dateofocurrence from manualsubstraction where visualparamid="
						+ paramid + " and clientid=" + clientid + " and affiliateid=" + affiliateid
						+ " and transporterid=" + transporterid + " and driverid in (0," + driverid
						+ ") and date_part('month',dateofocurrence) between " + startmonth + " and " + endmonth
						+ " and date_part('year',dateofocurrence) between " + startyear + " and " + endyear;

				map.put("datelist", this.jdbcTemplate.queryForList(query));
				continue;
			}
			if (tableorder == 4) {
				int paramid = ((Integer) map.get("parmid")).intValue();
				query = "select case when points!=0 then concat(dateofocurrence,' : ',points) else null end as dateofocurrence from manualrecovery where recoveryid="
						+ paramid + " and clientid=" + clientid + " and affiliateid=" + affiliateid
						+ " and transporterid=" + transporterid + " and driverid in(0," + driverid
						+ ") and date_part('month',dateofocurrence) between " + startmonth + " and " + endmonth
						+ " and date_part('year',dateofocurrence) between " + startyear + " and " + endyear;

				map.put("datelist", this.jdbcTemplate.queryForList(query));
			}
		}

		return tempList;
	}

	@Override
	public Map visualParamMap(int clientid, int affiliateid, int transid, int driverid, String occdate)
			throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date = format.parse(occdate);

		Map Lmap = new HashMap();

		String query = this.environment.getRequiredProperty("manualsubnrec.visualparamlist");
		List visualparams = this.jdbcTemplate.queryForList(query,
				new Object[] { Integer.valueOf(clientid), Integer.valueOf(affiliateid), Integer.valueOf(transid) });
		if (Objects.isNull(visualparams) || visualparams.isEmpty()) {
			if (transid != 0) {
				// transid = 0;
				visualparams = this.jdbcTemplate.queryForList(query,
						new Object[] { Integer.valueOf(clientid), Integer.valueOf(affiliateid), Integer.valueOf(0) });

				if (Objects.isNull(visualparams) || visualparams.isEmpty()) {
					if (affiliateid != 0) {
						// affiliateid =0;
						visualparams = this.jdbcTemplate.queryForList(query,
								new Object[] { Integer.valueOf(clientid), Integer.valueOf(0), Integer.valueOf(0) });
					}
				}

			} else if (affiliateid != 0) {
				visualparams = this.jdbcTemplate.queryForList(query,
						new Object[] { Integer.valueOf(clientid), Integer.valueOf(0), Integer.valueOf(0) });
			}
		}
		String query1 = this.environment.getRequiredProperty("manualrec.pointslist");
		List visualparamlist = this.jdbcTemplate.queryForList(query1, new Object[] { Integer.valueOf(clientid),
				Integer.valueOf(affiliateid), Integer.valueOf(transid), Integer.valueOf(driverid), date });
		if (Objects.isNull(visualparamlist) || visualparamlist.isEmpty()) {
			if (driverid != 0) {
				// transid = 0;
				visualparamlist = this.jdbcTemplate.queryForList(query1, new Object[] { Integer.valueOf(clientid),
						Integer.valueOf(affiliateid), Integer.valueOf(transid), Integer.valueOf(0), date });

				if (Objects.isNull(visualparamlist) || visualparamlist.isEmpty()) {
					if (transid != 0) {
						// affiliateid =0;
						visualparamlist = this.jdbcTemplate.queryForList(query1,
								new Object[] { Integer.valueOf(clientid), Integer.valueOf(affiliateid),
										Integer.valueOf(0), Integer.valueOf(0), date });

						if (Objects.isNull(visualparamlist) || visualparamlist.isEmpty()) {
							if (affiliateid != 0) {
								// affiliateid =0;
								visualparamlist = this.jdbcTemplate.queryForList(query1,
										new Object[] { Integer.valueOf(clientid), Integer.valueOf(0),
												Integer.valueOf(0), Integer.valueOf(0), date });
							}
						}
					}
				}
			} else if (transid != 0) {
				// transid = 0;
				visualparamlist = this.jdbcTemplate.queryForList(query1, new Object[] { Integer.valueOf(clientid),
						Integer.valueOf(affiliateid), Integer.valueOf(0), Integer.valueOf(0), date });

				if (Objects.isNull(visualparamlist) || visualparamlist.isEmpty()) {
					if (affiliateid != 0) {
						// affiliateid =0;
						visualparamlist = this.jdbcTemplate.queryForList(query1,
								new Object[] { Integer.valueOf(clientid), Integer.valueOf(0), Integer.valueOf(0),
										Integer.valueOf(0), date });
					}
				}

			} else if (affiliateid != 0) {
				visualparamlist = this.jdbcTemplate.queryForList(query1, new Object[] { Integer.valueOf(clientid),
						Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), date });
			}
		}
		int[] vparamid = new int[visualparams.size()];
		String[] vlabels = new String[visualparams.size()];
		int[] vpoints = new int[visualparams.size()];
		int[] manualsubid = new int[visualparams.size()];

		Iterator itr = visualparams.iterator();
		int j = 0;

		while (itr.hasNext()) {

			Map map = (Map) itr.next();
			vparamid[j] = Integer.parseInt(map.get("visualparamid").toString());

			vlabels[j] = Utils.StringEscape(map.get("visuallabel").toString());

			if (visualparamlist.size() > 0) {

				Iterator itrscp = visualparamlist.iterator();
				while (itrscp.hasNext()) {
					Map mapscp = (Map) itrscp.next();
					int id = Integer.parseInt(mapscp.get("visualparamid").toString());
					if (id == vparamid[j]) {
						vpoints[j] = Integer.parseInt(mapscp.get("points").toString());
						manualsubid[j] = Integer.parseInt(mapscp.get("manualsubid").toString());
						break;
					}
					vpoints[j] = 0;
					manualsubid[j] = 0;
				}

				j++;
				continue;
			} else {
				try {
					vpoints[j] = Integer.parseInt(map.get("points").toString());
					j++;
					continue;
				} catch (Exception ex) {
					vpoints[j] = 0;

					manualsubid[j] = 0;

					j++;
				}
			}
			// vpoints[j] = 0;

			// manualsubid[j] = 0;

			// j++;
		}

		Lmap.put("vparamid", vparamid);
		Lmap.put("vparamlabel", vlabels);
		Lmap.put("vpoints", vpoints);
		Lmap.put("manualsubid", manualsubid);

		return Lmap;
	}

	@Override
	public int saveManualSubtractionAndRecoverylist(User user, Manualsubnreclist manualsubnrecBean) {
		try {
			String occDate = manualsubnrecBean.getOccurancedate();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date date = format.parse(occDate);

			Integer manualsubrecid = manualsubnrecBean.getManualsubrecid();
			Date dates = new Date();
			String query = this.environment.getRequiredProperty("manualaccip.insert");
			String qry = this.environment.getRequiredProperty("manualaccip.update");
			String selectid = this.environment.getRequiredProperty("manualaccip.selectid");
			Integer tcip = Integer.valueOf(0);
			Integer[] listdriverid;
			if (!Objects.isNull(manualsubnrecBean.getDriverid())) {
				listdriverid = manualsubnrecBean.getDriverid();
			}
			else
			{
				listdriverid = new Integer[] {0};
			}
			//Integer[] listdriverid = manualsubnrecBean.getDriverid();
			//Integer driverid;
			for (Integer driverid : listdriverid) {
				try {
				
				if (!Objects.isNull(manualsubnrecBean.getTcip())) {
					tcip = manualsubnrecBean.getTcip();
				}
				//driverid = integerdriverid;
				
				if (Objects.isNull(manualsubrecid) || manualsubrecid.intValue() == 0) {
					try {
						manualsubrecid = this.jdbcTemplate.queryForObject(selectid,
								new Object[] { manualsubnrecBean.getCustomerid(),
										manualsubnrecBean.getClientaffiliateid(), manualsubnrecBean.getTransporterid(),
										driverid, date },
								Integer.class);
					} catch (EmptyResultDataAccessException e) {
						manualsubrecid = Integer.valueOf(0);
					}
				}

				if (Objects.isNull(manualsubrecid) || manualsubrecid.intValue() == 0) {

					try {
						this.jdbcTemplate.update(query,
								new Object[] { manualsubnrecBean.getCustomerid(),
										manualsubnrecBean.getClientaffiliateid(), manualsubnrecBean.getTransporterid(),
										driverid, manualsubnrecBean.getAccidentpoints(), tcip, user.getUserid(), dates,
										user.getUserid(), dates, date });
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else if (manualsubrecid.intValue() > 0) {

					this.jdbcTemplate.update(qry, new Object[] { manualsubnrecBean.getAccidentpoints(), tcip,
							user.getUserid(), new Date(), manualsubrecid });
				}

				// if (driverid.intValue() > 0) {

				String query1 = this.environment.getRequiredProperty("manualsub.insert");
				String query2 = this.environment.getRequiredProperty("manualsub.update");
				selectid = this.environment.getRequiredProperty("manualsub.selectid");
				Integer[] vparamid = manualsubnrecBean.getVparamid();
				Integer[] vparampoints = manualsubnrecBean.getVpoints();
				Integer[] manuasubid = manualsubnrecBean.getManualsubid();

				if (!Objects.isNull(vparamid) && !Objects.isNull(vparampoints) && !Objects.isNull(manuasubid)) {
					int lengths = vparamid.length;
					if (lengths == vparampoints.length && lengths == manuasubid.length) {
						for (int i = 0; i < vparamid.length; i++) {
							Integer manualid;
							try {
								manualid = this.jdbcTemplate.queryForObject(selectid,
										new Object[] { manualsubnrecBean.getCustomerid(),
												manualsubnrecBean.getClientaffiliateid(),
												manualsubnrecBean.getTransporterid(), driverid, date, vparamid[i] },
										Integer.class);
							} catch (EmptyResultDataAccessException e) {
								manualid = Integer.valueOf(0);
							}
							if (manuasubid[i].intValue() == 0 && manualid.intValue() == 0) {

								this.jdbcTemplate.update(query1, new Object[] { manualsubnrecBean.getCustomerid(),
										manualsubnrecBean.getClientaffiliateid(), manualsubnrecBean.getTransporterid(),
										driverid, vparamid[i], vparampoints[i], user.getUserid(), dates,
										user.getUserid(), dates, date });
							} else if (manuasubid[i].intValue() > 0 || manualid.intValue() > 0) {
								if (manuasubid[i].intValue() > 0) {
									this.jdbcTemplate.update(query2,
											new Object[] { vparampoints[i], user.getUserid(), dates, manuasubid[i] });
								} else {

									this.jdbcTemplate.update(query2,
											new Object[] { vparampoints[i], user.getUserid(), dates, manualid });
								}
							}
						}
					}
				}
				// }

				String query3 = this.environment.getRequiredProperty("manualrecovery.insert");
				String query4 = this.environment.getRequiredProperty("manualrecovery.update");
				selectid = this.environment.getRequiredProperty("manualrecovery.selectid");
				Integer[] recparamid = manualsubnrecBean.getRecoveryid();
				Integer[] recparampoints = manualsubnrecBean.getRecpoints();
				Integer[] manualrecid = manualsubnrecBean.getManualrecid();

				if (!Objects.isNull(recparamid) && !Objects.isNull(recparampoints) && !Objects.isNull(manualrecid)) {
					int lengths = recparamid.length;
					if (lengths == recparampoints.length && lengths == manualrecid.length) {
						for (int i = 0; i < recparamid.length; i++) {
							Integer manualid;
							try {
								manualid = this.jdbcTemplate.queryForObject(selectid,
										new Object[] { manualsubnrecBean.getCustomerid(),
												manualsubnrecBean.getClientaffiliateid(),
												manualsubnrecBean.getTransporterid(), driverid, date, recparamid[i] },
										Integer.class);
							} catch (EmptyResultDataAccessException e) {
								manualid = Integer.valueOf(0);
							}
							if ((Objects.isNull(manualrecid[i]) || manualrecid[i].intValue() == 0)
									&& manualid.intValue() == 0) {

								this.jdbcTemplate.update(query3, new Object[] { manualsubnrecBean.getCustomerid(),
										manualsubnrecBean.getClientaffiliateid(), manualsubnrecBean.getTransporterid(),
										driverid, recparamid[i], recparampoints[i], user.getUserid(), dates,
										user.getUserid(), dates, date });
							} else if (manualrecid[i].intValue() > 0 || manualid.intValue() > 0) {
								if (manualrecid[i].intValue() > 0) {
									this.jdbcTemplate.update(query4, new Object[] { recparampoints[i], user.getUserid(),
											dates, manualrecid[i] });
								} else {
									this.jdbcTemplate.update(query4,
											new Object[] { recparampoints[i], user.getUserid(), dates, manualid });
								}
							}
						}

						// return 1;
					}
					// return -1;
				}
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			}
			return 1;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
