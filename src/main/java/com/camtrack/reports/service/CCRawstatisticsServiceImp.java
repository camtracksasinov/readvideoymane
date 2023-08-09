package com.camtrack.reports.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.camtrack.config.Utils;
import com.camtrack.configuartion.repository.RecoveryRepository;
import com.camtrack.entities.DetailsRawStatictics;
import com.camtrack.entities.Manualrecovery;
import com.camtrack.entities.Manualsubstraction;
import com.camtrack.entities.ObjectDrivers;
import com.camtrack.entities.Scp;
import com.camtrack.entities.SmallRaw;
import com.camtrack.entities.User;
import com.camtrack.entities.ViewDriverTransAff;
import com.camtrack.entities.ViewDriverwiseExceptions;
import com.camtrack.reports.bean.Customeraffiliate;
import com.camtrack.reports.bean.Driver;
import com.camtrack.reports.bean.RawStatistic;
import com.camtrack.reports.bean.RawstatisticsBean;
import com.camtrack.reports.bean.Transporter;
import com.camtrack.reports.dao.CCRawstatisticsDao;
import com.camtrack.user.repository.DriverRepository;
import com.camtrack.user.repository.VehicleactivitysummaryRepository;

@Service("ccrawstatisticsservice")
@PropertySource({ "classpath:sqlqueries.properties" })
public class CCRawstatisticsServiceImp implements CCRawstatisticsService {
	@Autowired
	CCRawstatisticsDao dao;
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Value("${userrrole.transporterid}")
	private Integer transporterid;
	@Autowired
	VehicleactivitysummaryRepository driversumR;
	@Autowired
	DriverRepository driverR;
	@Autowired
	RecoveryRepository recovR;

	@Override
	public List<RawStatistic> generateReport(RawstatisticsBean rawstat, User user) {
		List<Integer> lis = null;
		lis = null;
		Integer[] listid = rawstat.getClientid();
		if (!Objects.isNull(listid) && listid.length > 0) {
			lis = Arrays.stream(listid).collect(Collectors.toList());
		}
		List<Integer> clientId0 = lis;
		lis = null;
		listid = rawstat.getAffiliateId();
		if (!Objects.isNull(listid) && listid.length > 0) {
			lis = Arrays.stream(listid).collect(Collectors.toList());
		}
		List<Integer> affiliateId00 = lis;

		lis = null;
		listid = rawstat.getTransporterid();
		if (!Objects.isNull(listid) && listid.length > 0) {
			lis = Arrays.stream(listid).collect(Collectors.toList());
		}
		List<Integer> transporterId = lis;
		List<Integer> transporterId2 = lis;
		lis = null;
		listid = rawstat.getDriverid();
		if (!Objects.isNull(listid) && listid.length > 0) {
			lis = Arrays.stream(listid).collect(Collectors.toList());
		}

		List<Integer> driverId = lis;
		int startYear = rawstat.getStartyear();
		int endYear = rawstat.getEndyear();
		int startMonth = rawstat.getStartmonth();
		int endMonth = rawstat.getEndmonth();
		Integer affiliateId0 = null;
		Integer clientId = null;
		List<Integer> listaffiliateid = null;

		List<RawStatistic> results = new ArrayList<>();
		Integer level = rawstat.getLevel();
		if (Objects.isNull(level)) {
			level = 3;
		}
		/*
		 * if (!Objects.isNull(driverId) && !driverId.isEmpty()) { try { int small =
		 * driverId.get(0).intValue(); String querydriver =
		 * "select t.affiliateid from transporter t where t.transporterid = (select d.transporterid from driver d where d.driverid = "
		 * + small + ")";
		 * 
		 * Integer affiliateId0 = this.jdbcTemplate.queryForObject(querydriver,
		 * Integer.class); querydriver =
		 * "select c.customerid from customeraffiliate c where c.affiliateid = " +
		 * affiliateId0; Integer clientId =
		 * this.jdbcTemplate.queryForObject(querydriver, Integer.class); return
		 * minorgenerateReport(clientId, affiliateId0, transporterId, driverId,
		 * startYear, endYear, startMonth, endMonth,level); } catch (Exception ex) {
		 * ex.printStackTrace(); } } else if (!Objects.isNull(transporterId) &&
		 * !transporterId.isEmpty()) { try { int small =
		 * transporterId.get(0).intValue(); String querydriver =
		 * "select t.affiliateid from transporter t where t.transporterid = " + small +
		 * ""; Integer affiliateId0 = this.jdbcTemplate.queryForObject(querydriver,
		 * Integer.class); querydriver =
		 * "select c.customerid from customeraffiliate c where c.affiliateid = " +
		 * affiliateId0; Integer clientId =
		 * this.jdbcTemplate.queryForObject(querydriver, Integer.class); return
		 * minorgenerateReport(clientId, affiliateId0, transporterId, driverId,
		 * startYear, endYear, startMonth, endMonth,level); } catch (Exception ex) {
		 * ex.printStackTrace(); } } else if (!Objects.isNull(affiliateId00) &&
		 * !affiliateId00.isEmpty()) { for (Integer affiliateId0s : affiliateId00) {
		 * String querydriver =
		 * "select c.customerid from customeraffiliate c where c.affiliateid = " +
		 * affiliateId0s; try { Integer clientId =
		 * this.jdbcTemplate.queryForObject(querydriver, Integer.class);
		 * results.addAll(minorgenerateReport(clientId, affiliateId0s, null, null,
		 * startYear, endYear, startMonth, endMonth,level)); } catch (Exception ex) {
		 * ex.printStackTrace(); } } } else if (!Objects.isNull(clientId0) &&
		 * !clientId0.isEmpty()) { for (Integer clientId00 : clientId0) {
		 * results.addAll( minorgenerateReport(clientId00, null, null, null, startYear,
		 * endYear, startMonth, endMonth,level)); } }
		 */
		String ids = "";
		int k = 0;
		if (Objects.isNull(level) || level == 3) {
			if (Objects.isNull(driverId) || driverId.isEmpty()) {
				if (!Objects.isNull(transporterId) && !transporterId.isEmpty()) {
					for (Integer ints : transporterId) {
						if (k == 0) {
							ids = ints + "";
						} else {
							ids = ids + "," + ints;
						}
						k++;
					}
					String sqlin = this.environment.getRequiredProperty("listdriveridid.transporter");
					sqlin = sqlin.replaceFirst("LISTRANS", ids);
					driverId = this.jdbcTemplate.queryForList(sqlin, Integer.class);
				} else if (!Objects.isNull(affiliateId00) && !affiliateId00.isEmpty()) {
					for (Integer ints : affiliateId00) {
						if (k == 0) {
							ids = ints + "";
						} else {
							ids = ids + "," + ints;
						}
						k++;
					}
					String sqlin = this.environment.getRequiredProperty("listdriveridid.affiliate");
					sqlin = sqlin.replaceFirst("LISTAFF", ids);
					driverId = this.jdbcTemplate.queryForList(sqlin, Integer.class);
				} else if (!Objects.isNull(clientId0) && !clientId0.isEmpty()) {
					String sqlin;
					int role = -1;
					try {
						role = user.getReelrole().getIdtyperole().getUserroleid();
					} catch (Exception ex) {

					}
					if ((clientId0.size() == 1) && (clientId0.get(0) == 0) && (role == 1)) {
						sqlin = this.environment.getRequiredProperty("listdriveridid.allclinet");
					} else {
						for (Integer ints : clientId0) {
							if (k == 0) {
								ids = ints + "";
							} else {
								ids = ids + "," + ints;
							}
							k++;
						}
						sqlin = this.environment.getRequiredProperty("listdriveridid.clinet");
						sqlin = sqlin.replaceFirst("LISTCLIENT", ids);
					}
					driverId = this.jdbcTemplate.queryForList(sqlin, Integer.class);
				}
			}

			if (!Objects.isNull(driverId) && !driverId.isEmpty()) {
				try {
					ids = "";
					k = 0;
					for (Integer ints : driverId) {
						if (k == 0) {
							ids = ints + "";
						} else {
							ids = ids + "," + ints;
						}
						k++;
					}
					String querydriver = "select transporterid,string_agg(driverid::text,',') as driverids from driver where status = 1 and  (driverkeycode != '0' and driverkeycode not like '%Unknow Driver%') and driverid in ("
							+ ids + ") group by transporterid ";
					List<ObjectDrivers> drivers = this.jdbcTemplate.query(querydriver, new RowMapper<ObjectDrivers>() {

						@Override
						public ObjectDrivers mapRow(ResultSet rs, int rowNum) throws SQLException {
							ObjectDrivers scp = new ObjectDrivers();
							scp.setDriverids(rs.getString("driverids"));
							scp.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
							return scp;
						}
					});
					for (ObjectDrivers rewardRanking : drivers) {
						try {
							querydriver = "select t.affiliateid from transporter t where t.status = 1 and t.transporterid = "
									+ rewardRanking.getTransporterid();

							affiliateId0 = this.jdbcTemplate.queryForObject(querydriver, Integer.class);
							querydriver = "select c.customerid from customeraffiliate c where  c.status = 1 and c.affiliateid = "
									+ affiliateId0;
							clientId = this.jdbcTemplate.queryForObject(querydriver, Integer.class);
							transporterId = new ArrayList<>();
							transporterId.add(rewardRanking.getTransporterid());
							results.addAll(minorgenerateReport(clientId, affiliateId0, transporterId,
									ObjectDrivers.getListFomString(rewardRanking.getDriverids()), startYear, endYear,
									startMonth, endMonth, level));
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		} else if (!Objects.isNull(driverId) && !driverId.isEmpty()) {
			try {

				ids = "";
				k = 0;
				for (Integer ints : driverId) {
					if (k == 0) {
						ids = ints + "";
					} else {
						ids = ids + "," + ints;
					}
					k++;
				}
				String querydriver = "select transporterid,string_agg(driverid::text,',') as driverids from driver where status = 1 and  (driverkeycode != '0' and driverkeycode not like '%Unknow Driver%') and driverid in ("
						+ ids + ") group by transporterid ";
				List<ObjectDrivers> drivers = this.jdbcTemplate.query(querydriver, new RowMapper<ObjectDrivers>() {

					@Override
					public ObjectDrivers mapRow(ResultSet rs, int rowNum) throws SQLException {
						ObjectDrivers scp = new ObjectDrivers();
						scp.setDriverids(rs.getString("driverids"));
						scp.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
						return scp;
					}
				});
				for (ObjectDrivers rewardRanking : drivers) {
					querydriver = "select t.affiliateid from transporter t where  status = 1 and t.transporterid = "
							+ rewardRanking.getTransporterid();

					affiliateId0 = this.jdbcTemplate.queryForObject(querydriver, Integer.class);
					querydriver = "select c.customerid from customeraffiliate c where  c.status = 1 and c.affiliateid = "
							+ affiliateId0;
					clientId = this.jdbcTemplate.queryForObject(querydriver, Integer.class);
					transporterId = new ArrayList<>();
					transporterId.add(rewardRanking.getTransporterid());
					results.addAll(minorgenerateReport(clientId, affiliateId0, transporterId,
							ObjectDrivers.getListFomString(rewardRanking.getDriverids()), startYear, endYear,
							startMonth, endMonth, level));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (!Objects.isNull(transporterId) && !transporterId.isEmpty()) {
			try {
				if (transporterId.size() > 0) {
					for (Integer small : transporterId) {
						String querydriver = "select t.affiliateid from transporter t where  t.status = 1 and t.transporterid = "
								+ small + "";
						affiliateId0 = this.jdbcTemplate.queryForObject(querydriver, Integer.class);
						transporterId2 = new ArrayList<>();
						transporterId2.add(small);
						querydriver = "select c.customerid from customeraffiliate c where  c.status = 1 and c.affiliateid = "
								+ affiliateId0;
						clientId = this.jdbcTemplate.queryForObject(querydriver, Integer.class);
						results.addAll(minorgenerateReport(clientId, affiliateId0, transporterId2, null, startYear,
								endYear, startMonth, endMonth, level));
					}
				} else {
					int small = transporterId.get(0).intValue();
					String querydriver = "select t.affiliateid from transporter t where  t.status = 1 and  t.transporterid = "
							+ small + "";
					affiliateId0 = this.jdbcTemplate.queryForObject(querydriver, Integer.class);
					querydriver = "select c.customerid from customeraffiliate c where c.affiliateid = " + affiliateId0;
					clientId = this.jdbcTemplate.queryForObject(querydriver, Integer.class);
					return minorgenerateReport(clientId, affiliateId0, transporterId, null, startYear, endYear,
							startMonth, endMonth, level);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (!Objects.isNull(affiliateId00) && !affiliateId00.isEmpty()) {
			for (Integer affiliateId0s : affiliateId00) {
				String querydriver = "select c.customerid from customeraffiliate c where  c.status = 1 and  c.affiliateid = "
						+ affiliateId0s;
				try {
					clientId = this.jdbcTemplate.queryForObject(querydriver, Integer.class);
					results.addAll(minorgenerateReport(clientId, affiliateId0s, null, null, startYear, endYear,
							startMonth, endMonth, level));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				/**
				 * String querydriver = "select t.transporterid from transporter t where
				 * t.affiliateid = " + affiliateId0s + ""; List<Integer> transporterids =
				 * jdbcTemplate.queryForList(querydriver,Integer.class); for (Integer small :
				 * transporterids) { //querydriver = "select t.affiliateid from transporter t
				 * where t.transporterid = " + small + ""; //affiliateId0 =
				 * this.jdbcTemplate.queryForObject(querydriver, Integer.class); transporterId2
				 * = new ArrayList<>(); transporterId2.add(small); querydriver = "select
				 * c.customerid from customeraffiliate c where c.affiliateid = " +
				 * affiliateId0s; clientId = this.jdbcTemplate.queryForObject(querydriver,
				 * Integer.class); results.addAll(minorgenerateReport(clientId, affiliateId0s,
				 * transporterId2, null, startYear, endYear, startMonth, endMonth,level)); }
				 */

				/**
				 * String querydriver = "select c.customerid from customeraffiliate c where
				 * c.affiliateid = " + affiliateId0s; try { clientId =
				 * this.jdbcTemplate.queryForObject(querydriver, Integer.class);
				 * results.addAll(minorgenerateReport(clientId, affiliateId0s, null, null,
				 * startYear, endYear, startMonth, endMonth,level)); } catch (Exception ex) {
				 * ex.printStackTrace(); }
				 */
			}
		} else if (!Objects.isNull(clientId0) && !clientId0.isEmpty()) {
			int role = -1;
			try {
				role = user.getReelrole().getIdtyperole().getUserroleid();
			} catch (Exception ex) {

			}
			if ((clientId0.size() == 1) && (clientId0.get(0) == 0) && (role == 1)) {
				String sqlin = this.environment.getRequiredProperty("listdriveridid.allclientid");
				List<Integer> clients = this.jdbcTemplate.queryForList(sqlin, Integer.class);
				for (Integer clientId00 : clients) {
					results.addAll(minorgenerateReport(clientId00, null, null, null, startYear, endYear, startMonth,
							endMonth, level));
				}
			} else {
				for (Integer clientId00 : clientId0) {
					results.addAll(minorgenerateReport(clientId00, null, null, null, startYear, endYear, startMonth,
							endMonth, level));
				}
			}
		}
		return results;
	}

	public Map<String, Map<Integer, Object>> getInitialPoints(int clientId, int affiliateId, int transporterid,
			int startMonth, int startYear, List<Integer> driverIds, Scp scp, YearMonth now) {
		if (scp != null) {
			HashMap<Integer, Object> defaultPoints = new HashMap<>();
			HashMap<Integer, Object> driverDistances = new HashMap<>();
			SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
			Date end0 = null, start = null;
			int lastDay = 0;
			// YearMonth now = YearMonth.of(endYear, endMonth);
			try {
				start = fm.parse(String.valueOf(startYear) + "-" + String.valueOf(startMonth) + "-01");

				Date yearStart = null;
				Date endBeforeSelectedPeriod = null;
				try {
					yearStart = fm.parse(String.valueOf(startYear) + "-01-01");
					Calendar c1 = Calendar.getInstance();
					c1.setTime(start);

					endBeforeSelectedPeriod = c1.getTime();
					System.out.println("end before selected period: " + endBeforeSelectedPeriod);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(start);
				lastDay = calendar.getActualMaximum(5);
				/**
				 * end = fm.parse( String.valueOf(endYear) + "-" + String.valueOf(endMonth) +
				 * "-" + String.valueOf(lastDay));
				 */
				String ids = "(";
				for (Integer driverId : driverIds) {
					ids = ids + driverId + ",";
				}
				ids = ids.substring(0, ids.length() - 1) + ")";

				int minority = scp.getMinsenioritydriver().intValue();
				String month = "";
				if (minority == 1) {
					month = " 1 month";
				} else {
					month = minority + " months";
				}
				String activeDriversQuery = "select driverid, driverkeycode, \"name\", transporterid, affiliateid, createdon, sum(totaldistance) as totaldistance from (SELECT DISTINCT driver.driverid,\n\t    driver.name,\n\t    driver.createdon,\n\t    cd.transporterid,\n\t    cd.affiliateid,\n\t    driver.driverkeycode,\n\t    driver.status,\n\t    cd.totaldistance,\n\t    cd.startdatetime\n\t   FROM \"exception\" cd,\n\t    driver driver\n\t  WHERE driver.driverid = cd.driverid AND driver.transporterid = cd.transporterid  AND driver.affiliateid = cd.affiliateid AND driver.status = 1 AND cast(driver.name as text) !~~ cast('UNKNOWN%' as text) and cd.startdatetime >= ? and cd.startdatetime < ? and driver.driverid in "
						+ ids + " ) as activedrivers where  (date(?) - (interval '" + month
						+ "' )) >= createdon group by driverid, transporterid, affiliateid, \"name\", createdon, driverkeycode";

				List<ViewDriverTransAff> drivers = this.jdbcTemplate.query(activeDriversQuery,
						new RowMapper<ViewDriverTransAff>() {

							@Override
							public ViewDriverTransAff mapRow(ResultSet rs, int rowNum) throws SQLException {
								System.out.println("result set at row " + rowNum + ": " + rs);

								ViewDriverTransAff d = new ViewDriverTransAff();
								d.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
								d.setCreatedon(rs.getDate("createdon"));
								d.setTotaldistance(rs.getBigDecimal("totaldistance"));
								d.setDriverid(Integer.valueOf(rs.getInt("driverid")));
								d.setDriverkeycode("driverkeycode");
								d.setName(rs.getString("name"));
								d.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));

								return d;
							}
						}, new Object[] { yearStart, endBeforeSelectedPeriod, endBeforeSelectedPeriod });
				for (ViewDriverTransAff driver : drivers) {

					try {
						if ((1000.0D * driver.getTotaldistance().doubleValue()) >= scp.getMindistance().doubleValue()) {
							defaultPoints.put(driver.getDriverid(), scp.getInitialpoint());
							driverDistances.put(driver.getDriverid(),
									Double.valueOf(driver.getTotaldistance().doubleValue()));
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				if (defaultPoints.size() > 0) {
					if (startMonth == 1) {
						Map<String, Map<Integer, Object>> results = new HashMap<>();
						results.put("initialpoints", defaultPoints);
						results.put("additionalpoints", new HashMap());
						results.put("distances", driverDistances);
						return results;
					}

					String allwhere = "((mr.clientid = " + clientId + " and  mr.affiliateid =" + affiliateId
							+ " and  mr.transporterid =" + transporterid + " and mr.driverid in " + ids + ")";
					allwhere = allwhere + " or (mr.clientid = " + clientId + " and  mr.affiliateid =" + affiliateId
							+ " and  mr.transporterid =" + transporterid + " and mr.driverid = 0 )";
					allwhere = allwhere + " or (mr.clientid = " + clientId + " and  mr.affiliateid =" + affiliateId
							+ " and  mr.transporterid =0  and mr.driverid = 0 )";
					allwhere = allwhere + " or (mr.clientid = " + clientId
							+ " and  mr.affiliateid =0  and  mr.transporterid =0 and mr.driverid = 0))";

					String manualRecoveryPointsQuery = "select max(mr.manualrecid) as manualrecid, mr.clientid, mr.affiliateid, mr.transporterid, mr.driverid, max(mr.status) as status, max(mr.dateofocurrence) as dateofocurrence, max(mr.recoveryid) as recoveryid, sum(mr.points*s.points) as points from scprecovery s join manualrecovery mr on (s.affiliateid = mr.affiliateid and s.recoveryid=mr.recoveryid) where  "
							+ " mr.dateofocurrence >= ? and mr.dateofocurrence < ? and " + allwhere
							+ " and mr.status = 1 group by mr.driverid, mr.transporterid, mr.affiliateid, mr.clientid";

					List<Manualrecovery> additionalPoints1 = this.jdbcTemplate.query(manualRecoveryPointsQuery,
							new RowMapper<Manualrecovery>() {

								@Override
								public Manualrecovery mapRow(ResultSet rs, int rowNum) throws SQLException {
									Manualrecovery mr = new Manualrecovery();
									mr.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
									mr.setClientid(Integer.valueOf(rs.getInt("clientid")));
									mr.setDateofocurrence(rs.getDate("dateofocurrence"));
									mr.setDriverid(Integer.valueOf(rs.getInt("driverid")));
									mr.setManualrecid(Integer.valueOf(rs.getInt("manualrecid")));
									mr.setPoints(Integer.valueOf(rs.getInt("points")));
									mr.setRecoveryid(Integer.valueOf(rs.getInt("recoveryid")));
									mr.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
									return mr;
								}
							}, new Object[] { yearStart, endBeforeSelectedPeriod });

					HashMap<Integer, Integer> points1 = new HashMap<>();
					for (Manualrecovery recovery : additionalPoints1) {
						points1.put(recovery.getDriverid(), recovery.getPoints());
					}

					// HashMap<Integer, Object> points2 = new HashMap<>();
					// List<Integer> driversWithNoViolation = new ArrayList<>();
					/**
					 * String scpRecoveryQuery = "select * from scprecovery where affiliateid=? and
					 * clientid=? and transporterid = ? and status=1"; List<Scprecovery>
					 * listrecovery = new ArrayList<>(); // Scprecovery recovery = null; try {
					 * listrecovery = this.jdbcTemplate.query(scpRecoveryQuery, new
					 * RowMapper<Scprecovery>() {
					 * 
					 * @Override public Scprecovery mapRow(ResultSet rs, int rowNum) throws
					 *           SQLException { Scprecovery scpRecovery = new Scprecovery();
					 *           scpRecovery.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
					 *           scpRecovery.setClientid(Integer.valueOf(rs.getInt("clientid")));
					 *           scpRecovery.setPoints(Integer.valueOf(rs.getInt("points"))); return
					 *           scpRecovery; } }, new Object[] { Integer.valueOf(affiliateId),
					 *           Integer.valueOf(clientId), Integer.valueOf(transporterid) }); }
					 *           catch (IncorrectResultSizeDataAccessException e) {
					 *           e.printStackTrace(); } for (Scprecovery recovery : listrecovery) {
					 *           re }
					 */

					HashMap<Integer, Integer> lostPoints = new HashMap<>();

					ids = "(";
					for (Integer driverId : driverIds) {
						ids = ids + driverId + ",";
					}
					ids = ids.substring(0, ids.length() - 1) + ")";
					allwhere = "((ms.clientid = " + clientId + " and  ms.affiliateid =" + affiliateId
							+ " and  ms.transporterid =" + transporterid + " and ms.driverid in " + ids + ")";
					allwhere = allwhere + " or (ms.clientid = " + clientId + " and  ms.affiliateid =" + affiliateId
							+ " and  ms.transporterid =" + transporterid + " and ms.driverid = 0 )";
					allwhere = allwhere + " or (ms.clientid = " + clientId + " and  ms.affiliateid =" + affiliateId
							+ " and  ms.transporterid =0  and ms.driverid = 0 )";
					allwhere = allwhere + " or (ms.clientid = " + clientId
							+ " and  ms.affiliateid =0  and  ms.transporterid =0 and ms.driverid = 0))";

					String manualSubtractionQuery = "select max(ms.manualsubid) as manualsubid, ms.affiliateid, ms.transporterid, ms.driverid,sum(ms.points*s.points) as points, ms.clientid, max(ms.status) as status, max(ms.visualparamid) as visualparamid,max(ms.dateofocurrence) as dateofocurrence from scpvisualparameter s join manualsubstraction ms on (s.affiliateid = ms.affiliateid and s.visualparamid=ms.visualparamid) where  ms.dateofocurrence >= ? and ms.dateofocurrence < ? and "
							+ allwhere
							+ " and ms.status = 1 group by ms.driverid, ms.transporterid, ms.affiliateid, ms.clientid";

					List<Manualsubstraction> manualLostPoints = this.jdbcTemplate.query(manualSubtractionQuery,
							new RowMapper<Manualsubstraction>() {

								@Override
								public Manualsubstraction mapRow(ResultSet rs, int rowNum) throws SQLException {
									Manualsubstraction ms = new Manualsubstraction();
									ms.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
									ms.setClientid(Integer.valueOf(rs.getInt("clientid")));
									ms.setDateofocurrence(rs.getDate("dateofocurrence"));
									ms.setDriverid(Integer.valueOf(rs.getInt("driverid")));

									ms.setPoints(Integer.valueOf(rs.getInt("points")));

									ms.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
									return ms;
								}
							}, new Object[] { yearStart, endBeforeSelectedPeriod });

					for (Manualsubstraction subtraction : manualLostPoints) {
						System.out.println("manual subtraction points: " + subtraction);
						lostPoints.put(subtraction.getDriverid(), subtraction.getPoints());
					}
					HashMap<Integer, Integer> lostExcepPoints = new HashMap<>();
					List<Integer> months = new ArrayList<>();
					String monthIn = "(";
					for (int i = 1; i < startMonth; i++) {
						months.add(Integer.valueOf(i));
						monthIn = monthIn + i + ",";
					}
					monthIn = monthIn.substring(0, monthIn.length() - 1) + ")";

					String driverExpsQuery = this.environment.getRequiredProperty("rawstatisticin");
					driverExpsQuery = driverExpsQuery.replaceFirst("XXXXXXXXXXXXXXXXXXXX", " and e.driverid in " + ids);

					driverExpsQuery = driverExpsQuery.replaceFirst("YYYYYYYYYY", " T.dmonth in " + monthIn + " and ");

					driverExpsQuery = driverExpsQuery.replaceFirst("YEARTDID", startYear + "");

					List<ViewDriverwiseExceptions> driverExceptions = this.jdbcTemplate.query(driverExpsQuery,
							new RowMapper<ViewDriverwiseExceptions>() {

								@Override
								public ViewDriverwiseExceptions mapRow(ResultSet rs, int rowNum) throws SQLException {
									ViewDriverwiseExceptions exceptions = new ViewDriverwiseExceptions();
									exceptions.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
									exceptions.setDriverid(Integer.valueOf(rs.getInt("driverid")));
									exceptions.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
									exceptions.setAlarmscore(Integer.valueOf(rs.getInt("alarmscore")));
									exceptions.setAlertscore(Integer.valueOf(rs.getInt("alertscore")));
									exceptions.setRecordscore(Integer.valueOf(rs.getInt("recordscore")));
									return exceptions;
								}
							});

					for (ViewDriverwiseExceptions exception : driverExceptions) {

						int points = exception.getAlarmscore().intValue() + exception.getAlertscore().intValue()
								+ exception.getRecordscore().intValue();
						Integer prevPoints = lostExcepPoints.get(exception.getDriverid());
						if (prevPoints != null) {
							lostExcepPoints.put(exception.getDriverid(),
									Integer.valueOf(prevPoints.intValue() + points));
							continue;
						}
						lostExcepPoints.put(exception.getDriverid(), Integer.valueOf(points));
					}

					System.out.println("driver lost exceptions for past months: " + lostExcepPoints);
					Double distance;
					HashMap<Integer, Object> initialPoints = new HashMap<>();
					HashMap<Integer, Object> initialsubs = new HashMap<>();
					HashMap<Integer, Object> expointss = new HashMap<>();
					HashMap<Integer, Object> additionals = new HashMap<>();

					Long nbracctotal = 0L;
					if (startMonth > 1) {
						String query = "select sum(mr.noofaccident) as accs from manualaccicp mr join driver dr on dr.driverid=mr.driverid and dr.driverid  in "
								+ ids
								+ " and mr.noofaccident>0 and mr.status=1 and mr.transporterid=dr.transporterid and mr.affiliateid=dr.affiliateid  and date_part('month', mr.dateofocurrence) between "
								+ 1 + " and " + (startMonth - 1) + " and date_part('year', mr.dateofocurrence) between "
								+ startYear + " and " + startYear;
						nbracctotal = this.jdbcTemplate.queryForObject(query, Long.class);
						if (Objects.isNull(nbracctotal)) {
							nbracctotal = 0L;
						}
					}

					for (Map.Entry<Integer, Object> driverPoints : defaultPoints.entrySet()) {
						com.camtrack.entities.Driver driver = driverR.findById(driverPoints.getKey()).orElse(null);
						YearMonth driverCreatedate = YearMonth.from(LocalDate.parse(driver.getCreatedon().toString()));
						Integer mindv = (int) ChronoUnit.MONTHS.between(driverCreatedate, now);
						Integer seniority = scp.getMinsenioritydriver().intValue();
						BigDecimal dist = this.driversumR.sumdistanceperperiodfordriver2(yearStart,
								endBeforeSelectedPeriod, driver.getDriverid());
						if (!Objects.isNull(dist)) {
							distance = Double.valueOf(dist.doubleValue());
						} else {
							distance = Double.valueOf(0.0D);
						}
						int dist2 = 1000 * distance.intValue();
						int mindist = scp.getMindistance().intValue();

						if ((dist2 > mindist) && (mindv > seniority)) {
							Integer points1ToAdd = Utils.IgnoreNull(points1.get(driverPoints.getKey()))
									+ Utils.IgnoreNull(points1.get(0));
							/**
							 * +Utils.IgnoreNull(Utils.castIntegerObject(points2.get(driverPoints.getKey())))
							 * + Utils.IgnoreNull(Utils.castIntegerObject(points2.get(0)));
							 */
							Integer manualLost = null;
							try {
								manualLost = Utils.IgnoreNull(lostPoints.get(driverPoints.getKey()))
										+ Utils.IgnoreNull(lostPoints.get(0));// ALL AUTHER POINTS
							} catch (Exception ex) {

							}
							if (manualLost == null)
								manualLost = Integer.valueOf(0);
							Integer excpoints = Utils.IgnoreNull(lostExcepPoints.get(driverPoints.getKey()))
									+ Utils.IgnoreNull(lostExcepPoints.get(0));
							initialPoints.put(driverPoints.getKey(),
									Integer.valueOf(
											((Integer) driverPoints.getValue()).intValue() + points1ToAdd.intValue()
													- (Math.abs(manualLost.intValue())
															+ Math.abs((nbracctotal.intValue()) * scp.getAccpoints())
															+ Math.abs(excpoints.intValue()))));
							initialsubs.put(driverPoints.getKey(), Math.abs(manualLost.intValue()));
							expointss.put(driverPoints.getKey(), Math.abs(excpoints.intValue()));
							additionals.put(driverPoints.getKey(), points1ToAdd.intValue());
						}
					}
					Map<String, Map<Integer, Object>> results = new HashMap<>();
					results.put("initialpoints", initialPoints);
					results.put("additionalpoints", additionals);
					results.put("manuallost", initialsubs);
					results.put("expoints", expointss);

					results.put("distances", driverDistances);
					return results;
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private List<RawStatistic> minorgenerateReport(Integer clientId, Integer affiliateId0, List<Integer> transporterId_,
			List<Integer> driverId, int startYear, int endYear, int startMonth, int endMonth, Integer level) {
		String query;
		List<RawStatistic> results = new ArrayList<>();

		List<Integer> listaffiliateid = null;
		Integer transid = 0;
		if (!Objects.isNull(transporterId_) && transporterId_.size() > 0) {
			transid = transporterId_.get(0);
		}

		boolean trouve = false;
		boolean transportertrouve = false;
		boolean affiliatetrouve = false;
		boolean clienttrouve = false;
		Scp scp = null;
		YearMonth now = YearMonth.of(endYear, endMonth);
		while (!trouve) {
			if ((transid != 0 && level == 3 && !transportertrouve)
					|| (!Objects.isNull(transporterId_)) && transporterId_.size() == 1) {
				query = "select * from scp where transporterid=" + transid + " and affiliateid=" + affiliateId0
						+ " and clientid=" + clientId; // and (transporterid is null or transporterid = 0)";
				listaffiliateid = new ArrayList<>();
				listaffiliateid.add(affiliateId0);
				transportertrouve = true;
			} else if (!Objects.isNull(affiliateId0) && !affiliatetrouve) {
				query = "select * from scp where affiliateid=" + affiliateId0
						+ " and (transporterid is null or transporterid = 0)  and clientid=" + clientId;
				listaffiliateid = new ArrayList<>();
				listaffiliateid.add(affiliateId0);
				affiliatetrouve = true;
			} else {
				if (Objects.isNull(clientId)) {
					return new ArrayList();
				}
				query = "select * from scp where clientid=" + clientId
						+ " and (affiliateid is null or affiliateid  = 0) and (transporterid is null or transporterid = 0) ";
				if ((Objects.isNull(driverId) || driverId.isEmpty())
						&& (Objects.isNull(transporterId_) || transporterId_.isEmpty())
						&& (Objects.isNull(affiliateId0))) {
					String query2 = "select affiliateid from customeraffiliate where status=1 and customerid= "
							+ clientId;
					listaffiliateid = this.jdbcTemplate.query(query2, new RowMapper<Integer>() {
						@Override
						public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
							return Integer.valueOf(rs.getInt("affiliateid"));
						}
					});
				} else {
					listaffiliateid = new ArrayList<>();
					listaffiliateid.add(affiliateId0);
				}

				clienttrouve = true;
			}

			/**
			 * List<Integer> listaffiliateid = null; if (!Objects.isNull(affiliateId0)) {
			 * query = "select * from scp where affiliateid=" + affiliateId0; } else { if
			 * (Objects.isNull(clientId)) { return new ArrayList(); } query = "select * from
			 * scp where clientid=" + clientId + " and (affiliateid = 0 or affiliateid is
			 * null)"; String query2 = "select affiliateid from customeraffiliate where
			 * status=1 and customerid= " + clientId; listaffiliateid =
			 * this.jdbcTemplate.query(query2, new RowMapper<Integer>() {
			 * 
			 * @Override public Integer mapRow(ResultSet rs, int rowNum) throws SQLException
			 *           { return Integer.valueOf(rs.getInt("affiliateid")); } }); }
			 */

			try {
				scp = this.jdbcTemplate.queryForObject(query, new RowMapper<Scp>() {

					@Override
					public Scp mapRow(ResultSet rs, int rowNum) throws SQLException {
						Scp scp = new Scp();
						scp.setScpid(Integer.valueOf(rs.getInt("scpid")));
						scp.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")), "", true);
						scp.setClientid(Integer.valueOf(rs.getInt("clientid")), "", true);
						scp.setDriverdisqualifiedlimit(Integer.valueOf(rs.getInt("driverdisqualifiedlimit")), true);
						scp.setDrivergreenlimit(Integer.valueOf(rs.getInt("drivergreenlimit")), true);
						scp.setDriverredlimit(Integer.valueOf(rs.getInt("driverredlimit")), true);
						scp.setDriveryellowlimit(Integer.valueOf(rs.getInt("driveryellowlimit")), true);
						scp.setInitialpoint(Integer.valueOf(rs.getInt("initialpoint")), true);
						scp.setMindistance(rs.getBigDecimal("mindistance"), true);
						scp.setMinsenioritydriver(Integer.valueOf(rs.getInt("minsenioritydriver")), true);
						scp.setMinsenioritytransporter(Integer.valueOf(rs.getInt("minsenioritytransporter")), true);
						scp.setStatus(Integer.valueOf(rs.getInt("status")));
						scp.setTransporterredlimit(Integer.valueOf(rs.getInt("transporterredlimit")), true);
						scp.setTransporterid(Integer.valueOf(rs.getInt("transporterid")), "", true);
						scp.setAccpoints(Integer.valueOf(rs.getInt("accpoints")), "", true);
						return scp;
					}
				});
			} catch (IncorrectResultSizeDataAccessException e) {
				//e.printStackTrace();
			}

			if (!Objects.isNull(scp) || (transportertrouve && affiliatetrouve && clienttrouve)) {
				trouve = true;
			}
		}

		if (!Objects.isNull(scp)) {

			HashMap<Integer, Driver> driverIdMapping = new HashMap<>();
			HashMap<Integer, Transporter> driverTransporterMapping = new HashMap<>();

			Map<String, Map<Integer, Object>> initialPoints = new HashMap<>();
			Map<Integer, Integer> finalPoints = new HashMap<>();
			if (Objects.isNull(listaffiliateid)) {
				listaffiliateid = new ArrayList<>();
				listaffiliateid.add(affiliateId0);
			}

			for (Integer affiliateId : listaffiliateid) {

				driverIdMapping = new HashMap<>();
				driverTransporterMapping = new HashMap<>();

				initialPoints = new HashMap<>();
				finalPoints = new HashMap<>();
				String tranporterByAffiliateQuery = "select * from transporter where status = 1 and  affiliateid = ?";
				if (affiliateId.intValue() != 0) {
					List<Integer> driverIds;
					if (!Objects.isNull(driverId) && !driverId.isEmpty()) {
						driverIds = driverId;
						String ids = "(";
						for (Integer driverId2 : driverIds) {
							ids = ids + driverId2 + ",";
						}
						ids = ids.substring(0, ids.length() - 1) + ")";
						String driverQuery = "select * from driver where status = 1 and  (driverkeycode != '0' and driverkeycode not like '%Unknow Driver%') and  driverid in "
								+ ids + "";

						List<Driver> transDrivers = this.jdbcTemplate.query(driverQuery, new RowMapper<Driver>() {
							@Override
							public Driver mapRow(ResultSet rs, int rowNum) throws SQLException {
								Driver d = new Driver();
								d.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
								d.setDriverid(Integer.valueOf(rs.getInt("driverid")));
								d.setName(rs.getString("name"));
								d.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
								d.setVehicleid(Integer.valueOf(rs.getInt("vehicleid")));
								d.setCreatedon(rs.getDate("createdon"));
								return d;
							}
						});
						driverIds.addAll(transDrivers.stream().map(d -> d.getDriverid()).distinct()
								.collect(Collectors.toList()));

						for (Driver driver : transDrivers) {

							String transporterQuery = "select * from transporter where status = 1 and transporterid = "
									+ driver.getTransporterid();
							List<Transporter> transporters = this.jdbcTemplate.query(transporterQuery,
									new RowMapper<Transporter>() {

										@Override
										public Transporter mapRow(ResultSet rs, int rowNum) throws SQLException {
											Transporter t = new Transporter();
											t.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
											t.setName(rs.getString("name"));
											t.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
											return t;
										}
									});

							try {
								driverTransporterMapping.put(driver.getDriverid(), transporters.get(0));
								driverIdMapping.put(driver.getDriverid(), driver);
							} catch (Exception exception1) {
							}
						}
					} else {
						List<Transporter> transporters;

						driverIds = new ArrayList<>();

						if (Objects.isNull(transporterId_)) {

							transporters = this.jdbcTemplate.query(tranporterByAffiliateQuery,
									new RowMapper<Transporter>() {
										@Override
										public Transporter mapRow(ResultSet rs, int rowNum) throws SQLException {
											Transporter t = new Transporter();
											t.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
											t.setName(rs.getString("name"));
											t.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
											return t;
										}
									}, new Object[] { affiliateId });
						} else {
							transporters = new ArrayList<>();

							String ids = "";
							int k = 0;
							for (Integer transporter : transporterId_) {
								if (k == 0) {
									ids = transporter + "";
								} else {
									ids = ids + "," + transporter;
								}
								k++;
							}
							if (k > 0) {
								String transporterQuery = "select * from transporter where status = 1 and  transporterid in ("
										+ ids + ")";

								transporters = this.jdbcTemplate.query(transporterQuery, new RowMapper<Transporter>() {
									@Override
									public Transporter mapRow(ResultSet rs, int rowNum) throws SQLException {
										Transporter t = new Transporter();
										t.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
										t.setName(rs.getString("name"));
										t.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
										return t;
									}
								});
							}
						}

						for (Transporter transporter : transporters) {

							String driverQuery = "select * from driver where status = 1 and  (driverkeycode != '0' and driverkeycode not like '%Unknow Driver%') and transporterid="
									+ transporter.getTransporterid();

							List<Driver> transDrivers = this.jdbcTemplate.query(driverQuery, new RowMapper<Driver>() {
								@Override
								public Driver mapRow(ResultSet rs, int rowNum) throws SQLException {
									Driver d = new Driver();
									d.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
									d.setDriverid(Integer.valueOf(rs.getInt("driverid")));
									d.setName(rs.getString("name"));
									d.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
									d.setVehicleid(Integer.valueOf(rs.getInt("vehicleid")));
									d.setCreatedon(rs.getDate("createdon"));
									return d;
								}
							});

							driverIds.addAll(transDrivers.stream().map(d -> d.getDriverid()).distinct()
									.collect(Collectors.toList()));

							for (Driver driver : transDrivers) {
								driverTransporterMapping.put(driver.getDriverid(), transporter);
								driverIdMapping.put(driver.getDriverid(), driver);
							}
						}
					}
					driverIds = driverIds.stream().distinct().collect(Collectors.toList());
					if (driverIds.size() > 0) {
						if (level == 3) {
							initialPoints = getInitialPoints(clientId.intValue(), affiliateId.intValue(), transid,
									startMonth, startYear, driverIds, scp, now);
						} else if (level == 4) {
							initialPoints = getInitialPoints(clientId.intValue(), affiliateId.intValue(), 0, startMonth,
									startYear, driverIds, scp, now);
						} else {
							initialPoints = getInitialPoints(clientId.intValue(), affiliateId.intValue(), 0, startMonth,
									startYear, driverIds, scp, now);
						}

						if (initialPoints != null) {
							System.out.println("initial points: " + initialPoints);
							SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");

							try {
								Date start = fm
										.parse(String.valueOf(startYear) + "-" + String.valueOf(startMonth) + "-01");

								Date endperiod = fm
										.parse(String.valueOf(endYear) + "-" + String.valueOf(endMonth) + "-01");
								System.out.println("start date: " + start);
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(endperiod);
								calendar.set(5, calendar.getActualMaximum(5));
								calendar.add(5, 1);
								Date end = calendar.getTime();

								HashMap<Integer, Integer> lostPoints = new HashMap<>();

								String ids = "(";
								for (Integer driverId2 : driverIds) {
									ids = ids + driverId2 + ",";
								}
								ids = ids.substring(0, ids.length() - 1) + ")";
								String allwhere;
								if (level == 3) {
									allwhere = "((ms.clientid = " + clientId + " and  ms.affiliateid =" + affiliateId
											+ " and  ms.transporterid =" + transid + " and ms.driverid in " + ids + ")";
									allwhere = allwhere + " or (ms.clientid = " + clientId + " and  ms.affiliateid ="
											+ affiliateId + " and  ms.transporterid =" + transid
											+ " and ms.driverid = 0 )";
								} else {
									String idss;

									idss = "(";
									for (Integer driverId2 : transporterId_) {
										idss = idss + driverId2 + ",";
									}
									idss = idss + ")";

									allwhere = "((ms.clientid = " + clientId + " and  ms.affiliateid =" + affiliateId
											+ " and ms.driverid in " + ids + ")";
									allwhere = allwhere + " or (ms.clientid = " + clientId + " and  ms.affiliateid ="
											+ affiliateId + " and  ms.transporterid in " + idss
											+ " and ms.driverid = 0 )";
								}

								allwhere = allwhere + " or (ms.clientid = " + clientId + " and  ms.affiliateid ="
										+ affiliateId + " and  ms.transporterid =0  and ms.driverid = 0 )";
								allwhere = allwhere + " or (ms.clientid = " + clientId
										+ " and  ms.affiliateid =0  and  ms.transporterid =0 and ms.driverid = 0))";

								String manualSubtractionQuery = "select max(ms.manualsubid) as manualsubid, ms.affiliateid, ms.transporterid, ms.driverid,sum(ms.points*s.points) as points, ms.clientid, max(ms.status) as status, max(ms.visualparamid) as visualparamid,max(ms.dateofocurrence) as dateofocurrence from scpvisualparameter s join manualsubstraction ms on (s.affiliateid = ms.affiliateid and s.visualparamid=ms.visualparamid) where ms.dateofocurrence  >=? and ms.dateofocurrence < ? and "
										+ allwhere
										+ " and ms.status = 1 group by ms.driverid, ms.transporterid, ms.affiliateid, ms.clientid";

								List<Manualsubstraction> manualLostPoints = this.jdbcTemplate
										.query(manualSubtractionQuery, new RowMapper<Manualsubstraction>() {

											@Override
											public Manualsubstraction mapRow(ResultSet rs, int rowNum)
													throws SQLException {
												Manualsubstraction ms = new Manualsubstraction();
												ms.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
												ms.setClientid(Integer.valueOf(rs.getInt("clientid")));
												ms.setDateofocurrence(rs.getDate("dateofocurrence"));
												ms.setDriverid(Integer.valueOf(rs.getInt("driverid")));

												ms.setPoints(Integer.valueOf(rs.getInt("points")));

												ms.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
												return ms;
											}
										}, new Object[] { start, end });

								for (Manualsubstraction subtraction : manualLostPoints) {
									System.out.println("lost points for current month: " + subtraction);
									lostPoints.put(subtraction.getDriverid(), subtraction.getPoints());
								}

								HashMap<Integer, Integer> lostExcepPoints = new HashMap<>();

								/**
								 * String driverExpsQuery =
								 * this.environment.getRequiredProperty("rawstatistic"); driverExpsQuery =
								 * driverExpsQuery.replaceFirst("XXXXXXXXXXXXXXXXXXXX", " and e.driverid in " +
								 * ids);
								 * 
								 * driverExpsQuery = driverExpsQuery.replaceFirst("MONTHTDID", endMonth + "");
								 * driverExpsQuery = driverExpsQuery.replaceFirst("YEARTDID", endYear + "");
								 */

								String monthIns = "(";
								for (int i = startMonth; i <= endMonth; i++) {
									// months.add(Integer.valueOf(i));
									monthIns = monthIns + i + ",";
								}
								monthIns = monthIns.substring(0, monthIns.length() - 1) + ")";

								String driverExpsQuery = this.environment.getRequiredProperty("rawstatisticin");
								driverExpsQuery = driverExpsQuery.replaceFirst("XXXXXXXXXXXXXXXXXXXX",
										" and e.driverid in " + ids);

								driverExpsQuery = driverExpsQuery.replaceFirst("YYYYYYYYYY",
										" T.dmonth in " + monthIns + " and ");

								driverExpsQuery = driverExpsQuery.replaceFirst("YEARTDID", endYear + "");
								/**
								 * driverExpsQuery = driverExpsQuery.replaceFirst("MO0NTHTDID0", startMonth +
								 * ""); driverExpsQuery = driverExpsQuery.replaceFirst("YE0ARTDID0", startYear +
								 * "");
								 */

								List<ViewDriverwiseExceptions> driverExceptions = this.jdbcTemplate
										.query(driverExpsQuery, new RowMapper<ViewDriverwiseExceptions>() {

											@Override
											public ViewDriverwiseExceptions mapRow(ResultSet rs, int rowNum)
													throws SQLException {
												ViewDriverwiseExceptions exceptions = new ViewDriverwiseExceptions();
												exceptions.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
												exceptions.setDriverid(Integer.valueOf(rs.getInt("driverid")));
												exceptions
														.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
												exceptions.setAlarmscore(Integer.valueOf(rs.getInt("alarmscore")));
												exceptions.setAlertscore(Integer.valueOf(rs.getInt("alertscore")));
												exceptions.setRecordscore(Integer.valueOf(rs.getInt("recordscore")));
												return exceptions;
											}
										});

								for (ViewDriverwiseExceptions exception : driverExceptions) {

									int points = exception.getAlarmscore().intValue()
											+ exception.getAlertscore().intValue()
											+ exception.getRecordscore().intValue();
									Integer prevPoints = lostExcepPoints.get(exception.getDriverid());
									if (prevPoints != null) {
										lostExcepPoints.put(exception.getDriverid(),
												Integer.valueOf(prevPoints.intValue() + points));
										continue;
									}
									lostExcepPoints.put(exception.getDriverid(), Integer.valueOf(points));
								}

								System.out.println("driver lost exceptions for current month: " + lostExcepPoints);
								if (level == 3) {
									allwhere = "((mr.clientid = " + clientId + " and  mr.affiliateid =" + affiliateId
											+ " and  mr.transporterid =" + transid + " and mr.driverid in " + ids + ")";
									allwhere = allwhere + " or (mr.clientid = " + clientId + " and  mr.affiliateid ="
											+ affiliateId + " and  mr.transporterid =" + transid
											+ " and mr.driverid = 0 )";
								} else {
									String idss;

									idss = "(";
									for (Integer driverId2 : transporterId_) {
										idss = idss + driverId2 + ",";
									}
									idss = idss + ")";

									allwhere = "((mr.clientid = " + clientId + " and  mr.affiliateid =" + affiliateId
											+ " and mr.driverid in " + ids + ")";
									allwhere = allwhere + " or (mr.clientid = " + clientId + " and  mr.affiliateid ="
											+ affiliateId + " and  mr.transporterid in " + idss
											+ " and mr.driverid = 0 )";
								}

								allwhere = allwhere + " or (mr.clientid = " + clientId + " and  mr.affiliateid ="
										+ affiliateId + " and  mr.transporterid =0  and mr.driverid = 0 )";
								allwhere = allwhere + " or (mr.clientid = " + clientId
										+ " and  mr.affiliateid =0  and  mr.transporterid =0 and mr.driverid = 0))";

								String manualRecoveryPointsQuery = "select max(mr.manualrecid) as manualrecid, mr.clientid, mr.affiliateid, mr.transporterid, mr.driverid, max(mr.status) as status, max(mr.dateofocurrence) as dateofocurrence, max(mr.recoveryid) as recoveryid, sum(mr.points*s.points) as points from scprecovery s join manualrecovery mr on (s.affiliateid = mr.affiliateid and s.recoveryid=mr.recoveryid) where mr.recoveryid != "
										+ Utils.recoveryIdInTabtaBaseByFrequenceId() + " and s.recoveryid !="
										+ Utils.recoveryIdInTabtaBaseByFrequenceId()
										+ " and mr.dateofocurrence >= ? and mr.dateofocurrence < ? and " + allwhere
										+ " and mr.status = 1 group by mr.driverid, mr.transporterid, mr.affiliateid, mr.clientid";

								List<Manualrecovery> additionalPoints1 = this.jdbcTemplate
										.query(manualRecoveryPointsQuery, new RowMapper<Manualrecovery>() {

											@Override
											public Manualrecovery mapRow(ResultSet rs, int rowNum) throws SQLException {
												Manualrecovery mr = new Manualrecovery();
												mr.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
												mr.setClientid(Integer.valueOf(rs.getInt("clientid")));
												mr.setDateofocurrence(rs.getDate("dateofocurrence"));
												mr.setDriverid(Integer.valueOf(rs.getInt("driverid")));
												mr.setManualrecid(Integer.valueOf(rs.getInt("manualrecid")));
												mr.setPoints(Integer.valueOf(rs.getInt("points")));
												mr.setRecoveryid(Integer.valueOf(rs.getInt("recoveryid")));
												mr.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
												return mr;
											}
										}, new Object[] { start, end });

								HashMap<Integer, Integer> points1 = new HashMap<>();
								for (Manualrecovery recovery : additionalPoints1) {
									System.out.println("recovery points for month: " + recovery);
									points1.put(recovery.getDriverid(), recovery.getPoints());
								}

								/**
								 * for (Map.Entry<Integer, Object> driverPoints :
								 * (initialPoints.get("initialpoints")) .entrySet()) { Integer points1ToAdd =
								 * Utils.nullToZero(points1.get(driverPoints.getKey())) +
								 * Utils.nullToZero(points1.get(0));
								 * 
								 * Integer points2 = Utils .nullToZero((Integer) ((Map)
								 * initialPoints.get("additionalpoints")) .get(driverPoints.getKey())) +
								 * Utils.nullToZero( (Integer) ((Map)
								 * initialPoints.get("additionalpoints")).get(0)); Integer manualLost =
								 * Utils.nullToZero(lostPoints.get(driverPoints.getKey())) +
								 * Utils.nullToZero(lostPoints.get(0)); Integer excpoints =
								 * Utils.nullToZero(lostExcepPoints.get(driverPoints.getKey())) +
								 * Utils.nullToZero(lostExcepPoints.get(0));
								 * finalPoints.put(driverPoints.getKey(), Integer.valueOf(((Integer)
								 * driverPoints.getValue()).intValue() + points1ToAdd.intValue() +
								 * points2.intValue() - manualLost.intValue() - excpoints.intValue())); }
								 */
								String affilQuery = "select * from customeraffiliate where status = 1 and affiliateid="
										+ affiliateId;
								Customeraffiliate aff = this.jdbcTemplate.queryForObject(affilQuery,
										new RowMapper<Customeraffiliate>() {

											@Override
											public Customeraffiliate mapRow(ResultSet rs, int rowNum)
													throws SQLException {
												Customeraffiliate affil = new Customeraffiliate();
												affil.setCustomerid(Integer.valueOf(rs.getInt("customerid")));
												affil.setName(rs.getString("name"));
												affil.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
												return affil;
											}
										});

								String clientNameQuery = "select name from customer where status = 1 and  customerid="
										+ clientId;
								String clientName = this.jdbcTemplate.queryForObject(clientNameQuery, String.class);

								Double distance;
								Driver driver;
								Integer allotherpoints;
								Integer expoints0;
								Integer manualLost0;
								Integer nbracctotal = 0;
								for (Map.Entry<Integer, Object> entry : (initialPoints.get("initialpoints"))
										.entrySet()) {
									driver = driverIdMapping.get(entry.getKey());
									BigDecimal dist = this.driversumR.sumdistanceperperiodfordriver2(start, end,
											driver.getDriverid());
									if (!Objects.isNull(dist)) {
										distance = Double.valueOf(dist.doubleValue());
										// row.setTotdist(distance.doubleValue());
									} else {
										distance = Double.valueOf(0.0D);
										// row.setTotdist(0.0D);
									}

									int mindist = scp.getMindistance().intValue();
									Integer seniority = scp.getMinsenioritydriver().intValue();
									YearMonth driverCreatedate = YearMonth
											.from(LocalDate.parse(driver.getCreatedon().toString()));
									Integer mindv = (int) ChronoUnit.MONTHS.between(driverCreatedate, now);
									int dist2 = 1000 * distance.intValue();
									if ((dist2 > mindist) && (mindv > seniority)) {

										query = "select mr.dateofocurrence,mr.noofaccident from manualaccicp mr join driver dr on dr.driverid=mr.driverid and (dr.driverid="
												+ driver.getDriverid() + " ) and dr.transporterid="
												+ driver.getTransporterid()
												+ " and mr.noofaccident>0 and mr.status=1 and mr.transporterid=dr.transporterid and mr.affiliateid=dr.affiliateid  and date_part('month', mr.dateofocurrence) between "
												+ startMonth + " and " + endMonth
												+ " and date_part('year', mr.dateofocurrence) between " + startYear
												+ " and " + endYear;

										List<Map<String, Object>> cars = this.jdbcTemplate.queryForList(query);
										String result = "";
										JSONArray array = new JSONArray();

										for (Map data : cars) {
											JSONObject value = new JSONObject();
											value.put("drivid", driver.getDriverid());
											nbracctotal = nbracctotal
													+ Utils.castIntegerObject(data.get("noofaccident"));
											value.put("nbacc", Utils.castIntegerObject(data.get("noofaccident")));
											value.put("occdat", String.valueOf(data.get("dateofocurrence")));
											array.put(value);
										}

										RawStatistic row = new RawStatistic();
										row.setAcc(array.toString());
										row.setAffiliateid(affiliateId);
										row.setAffiliatename(aff.getName());
										row.setClientid(clientId);
										row.setClientname(clientName);
										row.setTransporterid(
												driverTransporterMapping.get(entry.getKey()).getTransporterid());
										row.setTransportername(driverTransporterMapping.get(entry.getKey()).getName());
										row.setDriverid(entry.getKey());
										// Driver driver = driverIdMapping.get(entry.getKey());
										row.setDrivername(driver.getName());
										row.setInitialpoint(Integer.valueOf(((Integer) entry.getValue()).intValue()));
										Integer points1ToAdd = Utils.nullToZero(points1.get(entry.getKey()))
												+ Utils.nullToZero(points1.get(0));

										/**
										 * Integer points2 = Utils .nullToZero((Integer) ((Map)
										 * initialPoints.get("additionalpoints")) .get(entry.getKey())) +
										 * Utils.nullToZero( (Integer) ((Map)
										 * initialPoints.get("additionalpoints")).get(0));
										 */
										/**
										 * results.put("manuallost", initialsubs); results.put("expoints", expointss);
										 */
										/**
										 * expoints0 = Utils .nullToZero((Integer) ((Map) initialPoints.get("expoints"))
										 * .get(entry.getKey())) + Utils.nullToZero( (Integer) ((Map)
										 * initialPoints.get("expoints")).get(0)); manualLost0 = Utils
										 * .nullToZero((Integer) ((Map) initialPoints.get("manuallost"))
										 * .get(entry.getKey())) + Utils.nullToZero( (Integer) ((Map)
										 * initialPoints.get("manuallost")).get(0));
										 */
										Integer manualLost = Utils.nullToZero(lostPoints.get(entry.getKey()))
												+ Utils.nullToZero(lostPoints.get(0));
										Integer excpoints = Utils.nullToZero(lostExcepPoints.get(entry.getKey()))
												+ Utils.nullToZero(lostExcepPoints.get(0));// lostExcepPoints.get(entry.getKey());

										// double distance = ((Double) ((Map) initialPoints.get("distances"))
										// .get(entry.getKey())).doubleValue();
										row.setTotdist(distance);

										row.setRecvrypoints(Integer.valueOf(points1ToAdd.intValue()));
										row.setRecoverypoint1(points1ToAdd);
										row.setRecoverypoint2(0);

										row.setTotacc(-Math.abs(nbracctotal * scp.getAccpoints()));
										row.setExcpnpoints(-(Math.abs(excpoints)));
										row.setSubpoints(-(Math.abs(manualLost.intValue())));
										row.setTotalremainingpoints(Integer.valueOf(
												((Integer) entry.getValue()).intValue() + points1ToAdd.intValue()
														- (Math.abs(manualLost.intValue())
																+ Math.abs(nbracctotal * scp.getAccpoints())
																+ Math.abs(excpoints.intValue()))));

										row.setTotalsubstractedpoints(-(Integer.valueOf(Math.abs(manualLost.intValue())
												+ Math.abs(nbracctotal * scp.getAccpoints())
												+ Math.abs(excpoints.intValue()))));

										int colorzone = 0;
										if (row.getTotalremainingpoints().intValue() >= scp.getDrivergreenlimit()
												.intValue()) {
											colorzone = 4;
										} else if (row.getTotalremainingpoints().intValue() >= scp
												.getDriveryellowlimit().intValue()
												&& row.getTotalremainingpoints().intValue() < scp.getDrivergreenlimit()
														.intValue()) {
											colorzone = 3;
										} else if (row.getTotalremainingpoints().intValue() >= scp.getDriverredlimit()
												.intValue()
												&& row.getTotalremainingpoints().intValue() < scp.getDriveryellowlimit()
														.intValue()) {
											colorzone = 1;
										} else if (row.getTotalremainingpoints().intValue() >= scp
												.getDriverdisqualifiedlimit().intValue()
												&& row.getTotalremainingpoints().intValue() < scp.getDriverredlimit()
														.intValue()) {
											colorzone = 2;
										}
										row.setColorzone(Integer.valueOf(colorzone));
										row.setDriverdisqualifiedlimit(scp.getDriverdisqualifiedlimit());
										row.setDrivergreenlimit(scp.getDrivergreenlimit());
										row.setDriverredlimit(scp.getDriverredlimit());
										row.setDriveryellowlimit(scp.getDriveryellowlimit());
										row.setMinsenioritydriver(Integer
												.valueOf((int) ChronoUnit.MONTHS.between(driverCreatedate, now)));
										results.add(row);
									}
								}

							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			return results;
		}
		return new ArrayList();
	}

	@Override
	public DetailsRawStatictics detailrawstatistic(int clientId, int affiliateId0, int transporterId_, int driverId,
			int startYear, int endYear, int startMonth, int endMonth) {
		DetailsRawStatictics response = new DetailsRawStatictics();
		String query;
		List<RawStatistic> results = new ArrayList<>();

		List<Integer> listaffiliateid = null;

		boolean trouve = false;
		boolean transportertrouve = false;
		boolean affiliatetrouve = false;
		boolean clienttrouve = false;
		Scp scp = null;
		YearMonth now = YearMonth.of(endYear, endMonth);
		while (!trouve) {
			if (transporterId_ != 0 && !transportertrouve) {
				query = "select * from scp where transporterid=" + transporterId_ + " and affiliateid=" + affiliateId0
						+ " and clientid=" + clientId; // and (transporterid is null or transporterid = 0)";
				listaffiliateid = new ArrayList<>();
				listaffiliateid.add(affiliateId0);
				transportertrouve = true;
			} else if (!affiliatetrouve) {
				query = "select * from scp where affiliateid=" + affiliateId0
						+ " and (transporterid is null or transporterid = 0)  and clientid=" + clientId;
				listaffiliateid = new ArrayList<>();
				listaffiliateid.add(affiliateId0);
				affiliatetrouve = true;
			} else {
				if (Objects.isNull(clientId)) {
					return response;
				}
				query = "select * from scp where clientid=" + clientId
						+ " and (affiliateid is null or affiliateid  = 0) and (transporterid is null or transporterid = 0) ";
				String query2 = "select affiliateid from customeraffiliate where status=1 and customerid= " + clientId;
				listaffiliateid = this.jdbcTemplate.query(query2, new RowMapper<Integer>() {
					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						return Integer.valueOf(rs.getInt("affiliateid"));
					}
				});

				clienttrouve = true;
			}

			/**
			 * List<Integer> listaffiliateid = null; if (!Objects.isNull(affiliateId0)) {
			 * query = "select * from scp where affiliateid=" + affiliateId0; } else { if
			 * (Objects.isNull(clientId)) { return new ArrayList(); } query = "select * from
			 * scp where clientid=" + clientId + " and (affiliateid = 0 or affiliateid is
			 * null)"; String query2 = "select affiliateid from customeraffiliate where
			 * status=1 and customerid= " + clientId; listaffiliateid =
			 * this.jdbcTemplate.query(query2, new RowMapper<Integer>() {
			 * 
			 * @Override public Integer mapRow(ResultSet rs, int rowNum) throws SQLException
			 *           { return Integer.valueOf(rs.getInt("affiliateid")); } }); }
			 */

			try {
				scp = this.jdbcTemplate.queryForObject(query, new RowMapper<Scp>() {

					@Override
					public Scp mapRow(ResultSet rs, int rowNum) throws SQLException {
						Scp scp = new Scp();
						scp.setScpid(Integer.valueOf(rs.getInt("scpid")));
						scp.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")), "", true);
						scp.setClientid(Integer.valueOf(rs.getInt("clientid")), "", true);
						scp.setDriverdisqualifiedlimit(Integer.valueOf(rs.getInt("driverdisqualifiedlimit")), true);
						scp.setDrivergreenlimit(Integer.valueOf(rs.getInt("drivergreenlimit")), true);
						scp.setDriverredlimit(Integer.valueOf(rs.getInt("driverredlimit")), true);
						scp.setDriveryellowlimit(Integer.valueOf(rs.getInt("driveryellowlimit")), true);
						scp.setInitialpoint(Integer.valueOf(rs.getInt("initialpoint")), true);
						scp.setMindistance(rs.getBigDecimal("mindistance"), true);
						scp.setMinsenioritydriver(Integer.valueOf(rs.getInt("minsenioritydriver")), true);
						scp.setMinsenioritytransporter(Integer.valueOf(rs.getInt("minsenioritytransporter")), true);
						scp.setStatus(Integer.valueOf(rs.getInt("status")));
						scp.setTransporterredlimit(Integer.valueOf(rs.getInt("transporterredlimit")), true);
						scp.setTransporterid(Integer.valueOf(rs.getInt("transporterid")), "", true);
						scp.setAccpoints(Integer.valueOf(rs.getInt("accpoints")), "", true);
						return scp;
					}
				});
			} catch (IncorrectResultSizeDataAccessException e) {
				e.printStackTrace();
			}

			if (!Objects.isNull(scp) || (transportertrouve && affiliatetrouve && clienttrouve)) {
				trouve = true;
			}
		}

		if (!Objects.isNull(scp)) {

			HashMap<Integer, Driver> driverIdMapping = new HashMap<>();
			HashMap<Integer, Transporter> driverTransporterMapping = new HashMap<>();

			Map<String, Map<Integer, Object>> initialPoints = new HashMap<>();
			Map<Integer, Integer> finalPoints = new HashMap<>();
			if (Objects.isNull(listaffiliateid)) {
				listaffiliateid = new ArrayList<>();
				listaffiliateid.add(affiliateId0);
			}

			for (Integer affiliateId : listaffiliateid) {

				driverIdMapping = new HashMap<>();
				driverTransporterMapping = new HashMap<>();

				initialPoints = new HashMap<>();
				finalPoints = new HashMap<>();
				// String tranporterByAffiliateQuery = "select * from transporter where status =
				// 1 and affiliateid = ?";
				if (affiliateId != 0) {
					List<Integer> driverIds = new ArrayList<>();
					// if (!Objects.isNull(driverId) && !driverId.isEmpty()) {
					String driverQuery = "select * from driver where status = 1 and  (driverkeycode != '0' and driverkeycode not like '%Unknow Driver%') and  driverid = "
							+ driverId + "";

					List<Driver> transDrivers = this.jdbcTemplate.query(driverQuery, new RowMapper<Driver>() {
						@Override
						public Driver mapRow(ResultSet rs, int rowNum) throws SQLException {
							Driver d = new Driver();
							d.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
							d.setDriverid(Integer.valueOf(rs.getInt("driverid")));
							d.setName(rs.getString("name"));
							d.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
							d.setVehicleid(Integer.valueOf(rs.getInt("vehicleid")));
							d.setCreatedon(rs.getDate("createdon"));
							return d;
						}
					});
					driverIds.addAll(
							transDrivers.stream().map(d -> d.getDriverid()).distinct().collect(Collectors.toList()));

					for (Driver driver : transDrivers) {

						String transporterQuery = "select * from transporter where status = 1 and transporterid = "
								+ driver.getTransporterid();
						List<Transporter> transporters = this.jdbcTemplate.query(transporterQuery,
								new RowMapper<Transporter>() {

									@Override
									public Transporter mapRow(ResultSet rs, int rowNum) throws SQLException {
										Transporter t = new Transporter();
										t.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
										t.setName(rs.getString("name"));
										t.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
										return t;
									}
								});

						try {
							driverTransporterMapping.put(driver.getDriverid(), transporters.get(0));
							driverIdMapping.put(driver.getDriverid(), driver);
						} catch (Exception exception1) {
						}
					}

					driverIds = driverIds.stream().distinct().collect(Collectors.toList());
					// if (driverIds.size() > 0) {
					initialPoints = getInitialPoints(clientId, affiliateId, transporterId_, startMonth, startYear,
							driverIds, scp, now);

					if (initialPoints != null) {
						System.out.println("initial points: " + initialPoints);
						SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");

						try {
							Date start = fm.parse(String.valueOf(startYear) + "-" + String.valueOf(startMonth) + "-01");

							Date endperiod = fm.parse(String.valueOf(endYear) + "-" + String.valueOf(endMonth) + "-01");
							System.out.println("start date: " + start);
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(endperiod);
							calendar.set(5, calendar.getActualMaximum(5));
							calendar.add(5, 1);
							Date end = calendar.getTime();

							HashMap<Integer, Integer> lostPoints = new HashMap<>();

							String allwhere;
							allwhere = "((ms.clientid = " + clientId + " and  ms.affiliateid =" + affiliateId
									+ " and  ms.transporterid =" + transporterId_ + " and ms.driverid = " + driverId
									+ ")";
							allwhere = allwhere + " or (ms.clientid = " + clientId + " and  ms.affiliateid ="
									+ affiliateId + " and  ms.transporterid =" + transporterId_
									+ " and ms.driverid = 0 )";

							allwhere = allwhere + " or (ms.clientid = " + clientId + " and  ms.affiliateid ="
									+ affiliateId + " and  ms.transporterid =0  and ms.driverid = 0 )";
							allwhere = allwhere + " or (ms.clientid = " + clientId
									+ " and  ms.affiliateid =0  and  ms.transporterid =0 and ms.driverid = 0))";

							String manualSubtractionQuery = "select max(ms.manualsubid) as manualsubid, ms.affiliateid, ms.transporterid, ms.driverid,sum(ms.points*s.points) as points, ms.clientid, max(ms.status) as status, max(ms.visualparamid) as visualparamid,max(ms.dateofocurrence) as dateofocurrence from scpvisualparameter s join manualsubstraction ms on (s.affiliateid = ms.affiliateid and s.visualparamid=ms.visualparamid) where ms.dateofocurrence  >=? and ms.dateofocurrence < ? and "
									+ allwhere
									+ " and ms.status = 1 group by ms.driverid, ms.transporterid, ms.affiliateid, ms.clientid";

							List<Manualsubstraction> manualLostPoints = this.jdbcTemplate.query(manualSubtractionQuery,
									new RowMapper<Manualsubstraction>() {

										@Override
										public Manualsubstraction mapRow(ResultSet rs, int rowNum) throws SQLException {
											Manualsubstraction ms = new Manualsubstraction();
											ms.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
											ms.setClientid(Integer.valueOf(rs.getInt("clientid")));
											ms.setDateofocurrence(rs.getDate("dateofocurrence"));
											ms.setDriverid(Integer.valueOf(rs.getInt("driverid")));

											ms.setPoints(Integer.valueOf(rs.getInt("points")));

											ms.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
											ms.setVisualparamid(Integer.valueOf(rs.getInt("visualparamid")));

											return ms;
										}
									}, new Object[] { start, end });

							// Manaul Subscription END

							// DETAIL RAW STATISTIC

							Integer initp = Utils
									.nullToZero((Integer) ((Map) initialPoints.get("initialpoints")).get(driverId));
							response.setInitp(initp);

							SmallRaw substraction;
							SmallRaw infraction;
							SmallRaw recoveryfaction;
							List<SmallRaw> listsubstraction = new ArrayList<>();
							List<SmallRaw> listinfraction = new ArrayList<>();
							List<SmallRaw> listrevovery = new ArrayList<>();
							for (Manualsubstraction subtraction : manualLostPoints) {
								System.out.println("lost points for current month: " + subtraction);
								lostPoints.put(subtraction.getDriverid(), subtraction.getPoints());
								substraction = new SmallRaw();
								substraction.setIdtype(-1);
								substraction.setIds(subtraction.getVisualparamid());
								substraction.setPts(subtraction.getPoints());
								listsubstraction.add(substraction);
							}

							String monthIns = "(";
							for (int i = startMonth; i <= endMonth; i++) {
								// months.add(Integer.valueOf(i));
								monthIns = monthIns + i + ",";
							}
							monthIns = monthIns.substring(0, monthIns.length() - 1) + ")";

							String driverExpsQuery = this.environment.getRequiredProperty("rawstatisticin");
							driverExpsQuery = driverExpsQuery.replaceFirst("XXXXXXXXXXXXXXXXXXXX",
									" and e.driverid = " + driverId);

							driverExpsQuery = driverExpsQuery.replaceFirst("YYYYYYYYYY",
									" T.dmonth in " + monthIns + " and ");

							driverExpsQuery = driverExpsQuery.replaceFirst("YEARTDID", endYear + "");
							/**
							 * driverExpsQuery = driverExpsQuery.replaceFirst("MO0NTHTDID0", startMonth +
							 * ""); driverExpsQuery = driverExpsQuery.replaceFirst("YE0ARTDID0", startYear +
							 * "");
							 */

							List<ViewDriverwiseExceptions> driverExceptions = this.jdbcTemplate.query(driverExpsQuery,
									new RowMapper<ViewDriverwiseExceptions>() {

										@Override
										public ViewDriverwiseExceptions mapRow(ResultSet rs, int rowNum)
												throws SQLException {
											ViewDriverwiseExceptions exceptions = new ViewDriverwiseExceptions();
											exceptions.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
											exceptions.setDriverid(Integer.valueOf(rs.getInt("driverid")));
											exceptions.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
											exceptions.setAlarmscore(Integer.valueOf(rs.getInt("alarmscore")));
											exceptions.setAlertscore(Integer.valueOf(rs.getInt("alertscore")));
											exceptions.setRecordscore(Integer.valueOf(rs.getInt("recordscore")));
											exceptions.setExceptiontype(Integer.valueOf(rs.getInt("exceptiontype")));
											return exceptions;
										}
									});

							// LOST EXCEPTION
							HashMap<Integer, Integer> lostExcepPoints = new HashMap<>();
							Integer prevID = 0;
							int k = 0;
							Integer id1 = 0, id2 = 0;
							for (ViewDriverwiseExceptions exception : driverExceptions) {
								if (k != 0) {
									if (prevID != exception.getDriverid()) {
										infraction = new SmallRaw();
										infraction.setIdtype(exception.getExceptiontype());
										infraction.setIds(exception.getObcparamid());
										infraction.setPts(Integer.valueOf(lostExcepPoints.get(prevID)));
										listinfraction.add(infraction);
									}
								}
								int points = exception.getAlarmscore().intValue() + exception.getAlertscore().intValue()
										+ exception.getRecordscore().intValue();

								Integer prevPoints = lostExcepPoints.get(exception.getDriverid());
								if (prevPoints != null) {
									lostExcepPoints.put(exception.getDriverid(),
											Integer.valueOf(prevPoints.intValue() + points));
									continue;
								}
								lostExcepPoints.put(exception.getDriverid(), Integer.valueOf(points));
								prevID = exception.getDriverid();
								id1 = exception.getExceptiontype();
								id2 = exception.getObcparamid();
								k++;

							}

							infraction = new SmallRaw();
							infraction.setIdtype(id1);
							infraction.setIds(id2);
							infraction.setPts(Integer.valueOf(lostExcepPoints.get(prevID)));
							listinfraction.add(infraction);

							allwhere = "((mr.clientid = " + clientId + " and  mr.affiliateid =" + affiliateId
									+ " and  mr.transporterid =" + transporterId_ + " and mr.driverid = " + driverId
									+ ")";
							allwhere = allwhere + " or (mr.clientid = " + clientId + " and  mr.affiliateid ="
									+ affiliateId + " and  mr.transporterid =" + transporterId_
									+ " and mr.driverid = 0 )";

							allwhere = allwhere + " or (mr.clientid = " + clientId + " and  mr.affiliateid ="
									+ affiliateId + " and  mr.transporterid =0  and mr.driverid = 0 )";
							allwhere = allwhere + " or (mr.clientid = " + clientId
									+ " and  mr.affiliateid =0  and  mr.transporterid =0 and mr.driverid = 0))";

							String manualRecoveryPointsQuery = "select max(mr.manualrecid) as manualrecid, mr.clientid, mr.affiliateid, mr.transporterid, mr.driverid, max(mr.status) as status, max(mr.dateofocurrence) as dateofocurrence, max(mr.recoveryid) as recoveryid, sum(mr.points*s.points) as points from scprecovery s join manualrecovery mr on (s.affiliateid = mr.affiliateid and s.recoveryid=mr.recoveryid) where mr.recoveryid != "
									+ Utils.recoveryIdInTabtaBaseByFrequenceId() + " and s.recoveryid !="
									+ Utils.recoveryIdInTabtaBaseByFrequenceId()
									+ " and mr.dateofocurrence >= ? and mr.dateofocurrence < ? and " + allwhere
									+ " and mr.status = 1 group by mr.driverid, mr.transporterid, mr.affiliateid, mr.clientid";

							List<Manualrecovery> additionalPoints1 = this.jdbcTemplate.query(manualRecoveryPointsQuery,
									new RowMapper<Manualrecovery>() {

										@Override
										public Manualrecovery mapRow(ResultSet rs, int rowNum) throws SQLException {
											Manualrecovery mr = new Manualrecovery();
											mr.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
											mr.setClientid(Integer.valueOf(rs.getInt("clientid")));
											mr.setDateofocurrence(rs.getDate("dateofocurrence"));
											mr.setDriverid(Integer.valueOf(rs.getInt("driverid")));
											mr.setManualrecid(Integer.valueOf(rs.getInt("manualrecid")));
											mr.setPoints(Integer.valueOf(rs.getInt("points")));
											mr.setRecoveryid(Integer.valueOf(rs.getInt("recoveryid")));
											mr.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));
											return mr;
										}
									}, new Object[] { start, end });

							// Manual Recovery
							// Recovery
							HashMap<Integer, Integer> points1 = new HashMap<>();
							for (Manualrecovery recovery : additionalPoints1) {
								// System.out.println("recovery points for month: " + recovery);
								points1.put(recovery.getDriverid(), recovery.getPoints());
								recoveryfaction = new SmallRaw();
								recoveryfaction.setIdtype(0);
								recoveryfaction.setIds(recovery.getRecoveryid());
								recoveryfaction.setPts(recovery.getPoints());
								listrevovery.add(recoveryfaction);
							}

							response.setRecov(listrevovery);
							response.setRemov(listsubstraction);
							response.setInfraction(listinfraction);
							Double distance;
							Driver driver;
							Integer allotherpoints;
							Integer expoints0;
							Integer manualLost0;

							Integer remainpoints = 0;
							Integer nbracctotal = 0;
							JSONArray array = new JSONArray();
							for (Map.Entry<Integer, Object> entry : (initialPoints.get("initialpoints")).entrySet()) {
								driver = driverIdMapping.get(entry.getKey());
								nbracctotal = 0;
								query = "select mr.dateofocurrence,mr.noofaccident from manualaccicp mr join driver dr on dr.driverid=mr.driverid and (dr.driverid="
										+ driver.getDriverid() + " ) and dr.transporterid=" + driver.getTransporterid()
										+ " and mr.noofaccident>0 and mr.status=1 and mr.transporterid=dr.transporterid and mr.affiliateid=dr.affiliateid  and date_part('month', mr.dateofocurrence) between "
										+ startMonth + " and " + endMonth
										+ " and date_part('year', mr.dateofocurrence) between " + startYear + " and "
										+ endYear;

								List<Map<String, Object>> cars = this.jdbcTemplate.queryForList(query);
								String result = "";

								for (Map data : cars) {
									JSONObject value = new JSONObject();
									value.put("drivid", driver.getDriverid());
									nbracctotal = nbracctotal + Utils.castIntegerObject(data.get("noofaccident"));
									value.put("nbacc", Utils.castIntegerObject(data.get("noofaccident")));
									value.put("occdat", String.valueOf(data.get("dateofocurrence")));
									array.put(value);
								}

								BigDecimal dist = this.driversumR.sumdistanceperperiodfordriver2(start, end,
										driver.getDriverid());
								if (!Objects.isNull(dist)) {
									distance = Double.valueOf(dist.doubleValue());
									// row.setTotdist(distance.doubleValue());
								} else {
									distance = Double.valueOf(0.0D);
									// row.setTotdist(0.0D);
								}

								int mindist = scp.getMindistance().intValue();
								Integer seniority = scp.getMinsenioritydriver().intValue();
								YearMonth driverCreatedate = YearMonth
										.from(LocalDate.parse(driver.getCreatedon().toString()));
								Integer mindv = (int) ChronoUnit.MONTHS.between(driverCreatedate, now);
								int dist2 = 1000 * distance.intValue();
								if ((dist2 > mindist) && (mindv > seniority)) {

									// row.setInitialpoint(Integer.valueOf(((Integer)
									// entry.getValue()).intValue()));
									Integer points1ToAdd = Utils.nullToZero(points1.get(entry.getKey()))
											+ Utils.nullToZero(points1.get(0));

									/**
									 * Integer points2 = Utils .nullToZero((Integer) ((Map)
									 * initialPoints.get("additionalpoints")) .get(entry.getKey())) +
									 * Utils.nullToZero( (Integer) ((Map)
									 * initialPoints.get("additionalpoints")).get(0));
									 */
									/**
									 * results.put("manuallost", initialsubs); results.put("expoints", expointss);
									 */
									/**
									 * expoints0 = Utils .nullToZero((Integer) ((Map) initialPoints.get("expoints"))
									 * .get(entry.getKey())) + Utils.nullToZero( (Integer) ((Map)
									 * initialPoints.get("expoints")).get(0)); manualLost0 = Utils
									 * .nullToZero((Integer) ((Map) initialPoints.get("manuallost"))
									 * .get(entry.getKey())) + Utils.nullToZero( (Integer) ((Map)
									 * initialPoints.get("manuallost")).get(0));
									 */
									Integer manualLost = Utils.nullToZero(lostPoints.get(entry.getKey()))
											+ Utils.nullToZero(lostPoints.get(0));
									Integer excpoints = Utils.nullToZero(lostExcepPoints.get(entry.getKey()))
											+ Utils.nullToZero(lostExcepPoints.get(0));// lostExcepPoints.get(entry.getKey());

									remainpoints = remainpoints + Integer
											.valueOf(((Integer) entry.getValue()).intValue() + points1ToAdd.intValue()
													- (Math.abs(manualLost.intValue())
															+ (nbracctotal * scp.getAccpoints())
															+ Math.abs(excpoints.intValue())));
								}
							}

							response.setRemainp(remainpoints);
							response.setAcc(array.toString());
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					// }
				}
			}

		}
		return response;
	}

	@Override
	public List viewRawstatisticReeport(int clientid, int affiliateid, int transporterid, int driverid, int startyear,
			int endyear, int startmonth, int endmonth, String language) throws Exception {
		return this.dao.viewRawstatisticReeport(clientid, affiliateid, transporterid, driverid, startyear, endyear,
				startmonth, endmonth, language);
	}

	@Override
	public List<Map<String, Object>> recovid(int clientid, int affiliateid, int transporterid) {
		String query = this.environment.getRequiredProperty("rawstatistic.recovid");
		query = query.replaceFirst("CUSIDMER", clientid + "");
		query = query.replaceFirst("AFFIDLIATE", affiliateid + "");
		query = query.replaceFirst("TRANSIPPORTER", transporterid + "");
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public List<Map<String, Object>> removid(int clientid, int affiliateid, int transporterid) {
		String query = this.environment.getRequiredProperty("rawstatistic.removid");
		query = query.replaceFirst("CUSIDMER", clientid + "");
		query = query.replaceFirst("AFFIDLIATE", affiliateid + "");
		query = query.replaceFirst("TRANSIPPORTER", transporterid + "");
		return this.jdbcTemplate.queryForList(query);
	}

}
