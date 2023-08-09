package com.camtrack.reports.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
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
import org.springframework.core.env.Environment;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.camtrack.bean.DriverBean;
import com.camtrack.config.Utils;
import com.camtrack.entities.Driver;
import com.camtrack.entities.Manualrecovery;
import com.camtrack.entities.Manualsubstraction;
import com.camtrack.entities.ObjectDrivers;
import com.camtrack.entities.Scp;
import com.camtrack.entities.User;
import com.camtrack.entities.ViewDriverTransAff;
import com.camtrack.entities.ViewDriverwiseExceptions;
import com.camtrack.reports.bean.Customeraffiliate;
import com.camtrack.reports.bean.RawstatisticsBean;
import com.camtrack.reports.bean.RewardRanking;
import com.camtrack.reports.dao.CCRawstatisticsDao;
import com.camtrack.user.repository.DriverRepository;
import com.camtrack.user.repository.VehicleactivitysummaryRepository;

@Service("rewardrankingservice")
public class RewardRankingServiceImpl implements RewardRankingServiceInterface {
	@Autowired
	CCRawstatisticsDao dao;
	// @Autowired
	// DriveractivitysummaryRepository driversumR;
	@Autowired
	VehicleactivitysummaryRepository driversumR;

	@Autowired
	DriverRepository driverR;

	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Map<String, Map<Integer, Object>> getInitialPoints(int clientId, int affiliateId, int transporterid,
			int startMonth, int startYear, List<Integer> driverIds, Scp scp, YearMonth now) {
		if (scp != null) {
			HashMap<Integer, Object> defaultPoints = new HashMap<>();
			HashMap<Integer, Object> driverDistances = new HashMap<>();
			SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
			Date start = null;
			// int lastDay = 0;
			try {

				Date yearStart = null;
				Date endBeforeSelectedPeriod = null;

				start = fm.parse(String.valueOf(startYear) + "-" + String.valueOf(startMonth) + "-01");
				try {
					yearStart = fm.parse(String.valueOf(startYear) + "-01-01");
					Calendar c1 = Calendar.getInstance();
					c1.setTime(start);

					endBeforeSelectedPeriod = c1.getTime();
					System.out.println("end before selected period: " + endBeforeSelectedPeriod);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				// Calendar calendar = Calendar.getInstance();
				// calendar.setTime(start);
				// lastDay = calendar.getActualMaximum(5);
				/**
				 * end0 = fm.parse( String.valueOf(endYear) + "-" + String.valueOf(endMonth) +
				 * "-" + String.valueOf(lastDay));
				 */
				String ids = "(";
				// List<Integer> listremove=new ArrayList<>();

				for (Integer driverId : driverIds) {
					ids = ids + driverId + ",";
					// listremove.add(driverId);
				}
				ids = ids.substring(0, ids.length() - 1) + ")";

				String activeDriversQuery = "select driverid, driverkeycode, \"name\", transporterid, affiliateid, createdon, sum(totaldistance) as totaldistance from (SELECT DISTINCT driver.driverid,\n\t    driver.name,\n\t    driver.createdon,\n\t    cd.transporterid,\n\t    cd.affiliateid,\n\t    driver.driverkeycode,\n\t    driver.status,\n\t    cd.totaldistance,\n\t    cd.startdatetime\n\t   FROM \"exception\" cd,\n\t    driver driver\n\t  WHERE driver.driverid = cd.driverid AND driver.transporterid = cd.transporterid AND driver.affiliateid = cd.affiliateid AND driver.status = 1 AND cast(driver.name as text) !~~ cast('UNKNOWN%' as text) and cd.startdatetime >= ? and cd.startdatetime < ?) as activedrivers where  (date(?) - (interval '"
						+ String.valueOf(scp.getMinsenioritydriver()) + " month')) >= createdon and driverid in " + ids
						+ " group by driverid, transporterid, affiliateid, \"name\", createdon, driverkeycode";

				List<ViewDriverTransAff> drivers = this.jdbcTemplate.query(activeDriversQuery,
						new RowMapper<ViewDriverTransAff>() {

							@Override
							public ViewDriverTransAff mapRow(ResultSet rs, int rowNum) throws SQLException {
								System.out.println("result set at row " + rowNum + ": " + rs);

								ViewDriverTransAff d = new ViewDriverTransAff();
								d.setId(Integer.valueOf(rowNum));
								d.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
								d.setCreatedon(rs.getDate("createdon"));
								d.setTotaldistance(rs.getBigDecimal("totaldistance"));
								d.setDriverid(Integer.valueOf(rs.getInt("driverid")));
								d.setDriverkeycode("driverkeycode");
								d.setName(rs.getString("name"));
								d.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));

								return d;
							}
						}, new Object[] { yearStart, endBeforeSelectedPeriod, start });

				for (ViewDriverTransAff driver : drivers) {

					try {
						if (driver.getTotaldistance().doubleValue() >= scp.getMindistance().doubleValue() / 1000.0D) {
							defaultPoints.put(driver.getDriverid(), scp.getInitialpoint());
							driverDistances.put(driver.getDriverid(),
									Double.valueOf(driver.getTotaldistance().doubleValue()));
							// listremove.remove(driver.getDriverid());
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				/**
				 * for (Integer driverids : listremove) { defaultPoints.put(driverids,
				 * scp.getInitialpoint()); driverDistances.put(driverids,0L); }
				 */
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
					String manualRecoveryPointsQuery = "select max(mr.manualrecid) as manualrecid, mr.clientid, mr.affiliateid, mr.transporterid, mr.driverid, max(mr.status) as status, max(mr.dateofocurrence) as dateofocurrence, max(mr.recoveryid) as recoveryid, sum(mr.points*s.points) as points from scprecovery s join manualrecovery mr on (s.recoveryid=mr.recoveryid) where "
							+ " mr.dateofocurrence >= ? and mr.dateofocurrence < ?  and " + allwhere
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

					HashMap<Integer, Object> points2 = new HashMap<>();
					List<Integer> driversWithNoViolation = new ArrayList<>();

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
					String manualSubtractionQuery = "select max(ms.manualsubid) as manualsubid, ms.affiliateid, ms.transporterid, ms.driverid,sum(ms.points*s.points) as points, ms.clientid, max(ms.status) as status, max(ms.visualparamid) as visualparamid,max(ms.dateofocurrence) as dateofocurrence from scpvisualparameter s join manualsubstraction ms on (s.visualparamid=ms.visualparamid) where ms.dateofocurrence >= ? and ms.dateofocurrence < ? "
							+ " and  " + allwhere
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

					/**
					 * for (Map data : cars) { value.put("drivid",
					 * Utils.castIntegerObject(data.get("driverid"))); nbracctotal = nbracctotal +
					 * Utils.castIntegerObject(data.get("noofaccident")); value.put("nbacc",
					 * Utils.castIntegerObject(data.get("noofaccident")));
					 * value.put("occdat",String.valueOf(data.get("dateofocurrence")));
					 * 
					 * array.put(value); }
					 */

					for (Map.Entry<Integer, Object> driverPoints : defaultPoints.entrySet()) {

						Driver driver = driverR.findById(driverPoints.getKey()).orElse(null);
						YearMonth driverCreatedate = YearMonth.from(LocalDate.parse(driver.getCreatedon().toString()));
						Integer mindv = (int) ChronoUnit.MONTHS.between(driverCreatedate, now);
						Integer seniority = scp.getMinsenioritydriver().intValue();
						BigDecimal dist = this.driversumR.sumdistanceperperiodfordriver2(yearStart, start,
								driver.getDriverid());
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
									(Utils.IgnoreNull((Integer) driverPoints.getValue())).intValue()
											+ points1ToAdd.intValue() - Math.abs(manualLost.intValue())
											- Math.abs(excpoints.intValue())
											- Math.abs((nbracctotal.intValue()) * scp.getAccpoints()));

						}
					}
					Map<String, Map<Integer, Object>> results = new HashMap<>();
					results.put("initialpoints", initialPoints);
					results.put("additionalpoints", points2);
					results.put("distances", driverDistances);
					return results;
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	@Override
	public List<RewardRanking> getRewardranking(RawstatisticsBean rawstat, User user) {
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
		Integer affiliateId0 = null;
		Integer clientId = null;
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

		List<Integer> listaffiliateid = null;

		List<RewardRanking> results = new ArrayList<>();
		Integer level = rawstat.getLevel();
		if (Objects.isNull(level)) {
			level = 3;
		}
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

	private List<RewardRanking> minorgenerateReport(Integer clientId, Integer affiliateId0, List<Integer> transporterId,
			List<Integer> driverId, int startYear, int endYear, int startMonth, int endMonth, Integer level) {
		String query;
		List<RewardRanking> results = new ArrayList<>();

		List<Integer> listaffiliateid = null;
		Integer transid = 0;
		if (!Objects.isNull(transporterId) && transporterId.size() > 0) {
			transid = transporterId.get(0);
		}

		boolean trouve = false;
		boolean transportertrouve = false;
		boolean affiliatetrouve = false;
		boolean clienttrouve = false;
		Scp scp = null;
		while (!trouve) {
			if ((transid != 0 && level == 3 && !transportertrouve)
					|| (!Objects.isNull(transporterId)) && transporterId.size() == 1) {
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
						&& (Objects.isNull(transporterId) || transporterId.isEmpty())
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

			HashMap<Integer, DriverBean> driverIdMapping = new HashMap<>();

			Map<String, Map<Integer, Object>> initialPoints = new HashMap<>();
			Map<Integer, Integer> finalPoints = new HashMap<>();

			YearMonth now = YearMonth.of(endYear, endMonth);
			for (Integer affiliateId : listaffiliateid) {

				driverIdMapping = new HashMap<>();

				initialPoints = new HashMap<>();
				finalPoints = new HashMap<>();
				String tranporterByAffiliateQuery = "select transporterid from transporter where status = 1 and affiliateid = "
						+ affiliateId;

				if (affiliateId.intValue() != 0) {
					if (level == 3) {
						if (!Objects.isNull(driverId) && !driverId.isEmpty()) {
							List<Integer> driverIds = driverId;
							String ids = "(";
							for (Integer driverId2 : driverIds) {
								ids = ids + driverId2 + ",";
							}
							ids = ids.substring(0, ids.length() - 1) + ")";
							String driverQuery = "select driverid,transporterid,affiliateid,createdon from driver where (driverkeycode != '0' and driverkeycode not like '%Unknow Driver%') and driverid in "
									+ ids + " and status = 1";

							List<DriverBean> transDrivers = this.jdbcTemplate.query(driverQuery,
									new RowMapper<DriverBean>() {
										@Override
										public DriverBean mapRow(ResultSet rs, int rowNum) throws SQLException {
											DriverBean d = new DriverBean();

											d.setDriverid(Integer.valueOf(rs.getInt("driverid")));

											d.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));

											d.setCreatedon(rs.getDate("createdon"));
											return d;
										}
									});
							driverIds.addAll(transDrivers.stream().map(d -> d.getDriverid()).distinct()
									.collect(Collectors.toList()));

							for (DriverBean driver : transDrivers) {
								try {
									driverIdMapping.put(driver.getDriverid(), driver);
								} catch (Exception exception1) {
								}
							}
							driverIds = driverIds.stream().distinct().collect(Collectors.toList());
							if (driverIds.size() > 0) {
								/**
								 * List<MonthlyRecord> listmonths = Utils.monhtlyfromperiod(startYear0,
								 * endYear0, startMonth0, endMonth0); for (MonthlyRecord integer : listmonths) {
								 * 
								 * int startYear = integer.getYear(); int endYear = integer.getYear(); int
								 * startMonth = integer.getMonth(); int endMonth = integer.getMonth(); YearMonth
								 * now = YearMonth.of(endYear, endMonth);
								 */
								if (level == 3) {
									initialPoints = getInitialPoints(clientId.intValue(), affiliateId.intValue(),
											transid, startMonth, startYear, driverIds, scp, now);
								} else if (level == 4) {
									initialPoints = getInitialPoints(clientId.intValue(), affiliateId.intValue(), 0,
											startMonth, startYear, driverIds, scp, now);
								} else {
									initialPoints = getInitialPoints(clientId.intValue(), affiliateId.intValue(), 0,
											startMonth, startYear, driverIds, scp, now);
								}
								if (initialPoints != null) {
									System.out.println("initial points: " + initialPoints);
									SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");

									try {
										Date start = fm.parse(
												String.valueOf(startYear) + "-" + String.valueOf(startMonth) + "-01");

										Date endperiod = fm.parse(
												String.valueOf(endYear) + "-" + String.valueOf(endMonth) + "-01");
										System.out.println("start date: " + start);
										Calendar calendar = Calendar.getInstance();
										calendar.setTime(endperiod);
										calendar.set(5, calendar.getActualMaximum(5));
										calendar.add(5, 1);
										Date end = calendar.getTime();

										HashMap<Integer, Integer> lostPoints = new HashMap<>();

										ids = "(";
										for (Integer driverId2 : driverIds) {
											ids = ids + driverId2 + ",";
										}
										ids = ids.substring(0, ids.length() - 1) + ")";
										String allwhere;
										if (level == 3) {
											allwhere = "((ms.clientid = " + clientId + " and  ms.affiliateid ="
													+ affiliateId + " and  ms.transporterid =" + transid
													+ " and ms.driverid in " + ids + ")";
											allwhere = allwhere + " or (ms.clientid = " + clientId
													+ " and  ms.affiliateid =" + affiliateId
													+ " and  ms.transporterid =" + transid + " and ms.driverid = 0 )";
										} else {
											String idss;

											idss = "(";
											for (Integer driverId2 : transporterId) {
												idss = idss + driverId2 + ",";
											}
											idss = idss + ")";

											allwhere = "((ms.clientid = " + clientId + " and  ms.affiliateid ="
													+ affiliateId + " and ms.driverid in " + ids + ")";
											allwhere = allwhere + " or (ms.clientid = " + clientId
													+ " and  ms.affiliateid =" + affiliateId
													+ " and  ms.transporterid in " + idss + " and ms.driverid = 0 )";
										}

										allwhere = allwhere + " or (ms.clientid = " + clientId
												+ " and  ms.affiliateid =" + affiliateId
												+ " and  ms.transporterid =0  and ms.driverid = 0 )";
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

														ms.setTransporterid(
																Integer.valueOf(rs.getInt("transporterid")));
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
														exceptions.setAffiliateid(
																Integer.valueOf(rs.getInt("affiliateid")));
														exceptions.setDriverid(Integer.valueOf(rs.getInt("driverid")));
														exceptions.setTransporterid(
																Integer.valueOf(rs.getInt("transporterid")));
														exceptions.setAlarmscore(
																Integer.valueOf(rs.getInt("alarmscore")));
														exceptions.setAlertscore(
																Integer.valueOf(rs.getInt("alertscore")));
														exceptions.setRecordscore(
																Integer.valueOf(rs.getInt("recordscore")));
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

										System.out.println(
												"driver lost exceptions for current month: " + lostExcepPoints);
										if (level == 3) {
											allwhere = "((mr.clientid = " + clientId + " and  mr.affiliateid ="
													+ affiliateId + " and  mr.transporterid =" + transid
													+ " and mr.driverid in " + ids + ")";
											allwhere = allwhere + " or (mr.clientid = " + clientId
													+ " and  mr.affiliateid =" + affiliateId
													+ " and  mr.transporterid =" + transid + " and mr.driverid = 0 )";
										} else {
											String idss;

											idss = "(";
											for (Integer driverId2 : transporterId) {
												idss = idss + driverId2 + ",";
											}
											idss = idss + ")";

											allwhere = "((mr.clientid = " + clientId + " and  mr.affiliateid ="
													+ affiliateId + " and mr.driverid in " + ids + ")";
											allwhere = allwhere + " or (mr.clientid = " + clientId
													+ " and  mr.affiliateid =" + affiliateId
													+ " and  mr.transporterid in " + idss + " and mr.driverid = 0 )";
										}

										allwhere = allwhere + " or (mr.clientid = " + clientId
												+ " and  mr.affiliateid =" + affiliateId
												+ " and  mr.transporterid =0  and mr.driverid = 0 )";
										allwhere = allwhere + " or (mr.clientid = " + clientId
												+ " and  mr.affiliateid =0  and  mr.transporterid =0 and mr.driverid = 0))";

										String manualRecoveryPointsQuery = "select max(mr.manualrecid) as manualrecid, mr.clientid, mr.affiliateid, mr.transporterid, mr.driverid, max(mr.status) as status, max(mr.dateofocurrence) as dateofocurrence, max(mr.recoveryid) as recoveryid, sum(mr.points*s.points) as points from scprecovery s join manualrecovery mr on (s.affiliateid = mr.affiliateid and s.recoveryid=mr.recoveryid) where mr.recoveryid != "
												+ Utils.recoveryIdInTabtaBaseByFrequenceId() + " and s.recoveryid !="
												+ Utils.recoveryIdInTabtaBaseByFrequenceId()
												+ " and mr.dateofocurrence >= ? and mr.dateofocurrence < ? and "
												+ allwhere
												+ " and mr.status = 1 group by mr.driverid, mr.transporterid, mr.affiliateid, mr.clientid";

										List<Manualrecovery> additionalPoints1 = this.jdbcTemplate
												.query(manualRecoveryPointsQuery, new RowMapper<Manualrecovery>() {

													@Override
													public Manualrecovery mapRow(ResultSet rs, int rowNum)
															throws SQLException {
														Manualrecovery mr = new Manualrecovery();
														mr.setAffiliateid(Integer.valueOf(rs.getInt("affiliateid")));
														mr.setClientid(Integer.valueOf(rs.getInt("clientid")));
														mr.setDateofocurrence(rs.getDate("dateofocurrence"));
														mr.setDriverid(Integer.valueOf(rs.getInt("driverid")));
														mr.setManualrecid(Integer.valueOf(rs.getInt("manualrecid")));
														mr.setPoints(Integer.valueOf(rs.getInt("points")));
														mr.setRecoveryid(Integer.valueOf(rs.getInt("recoveryid")));
														mr.setTransporterid(
																Integer.valueOf(rs.getInt("transporterid")));
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
										String clientName = this.jdbcTemplate.queryForObject(clientNameQuery,
												String.class);

										Double distance;
										// Driver driver;
										Integer allotherpoints;
										Integer expoints0;
										Integer manualLost0;

										String accident = "{}";
										Integer totallost;
										Integer nbracctotal = 0;
										for (Map.Entry<Integer, Object> entry : (initialPoints.get("initialpoints"))
												.entrySet()) {
											try {
												// Double distance;
												RewardRanking row = new RewardRanking();
												row.setAffid(affiliateId);

												row.setCusid(clientId);

												DriverBean driver = driverIdMapping.get(entry.getKey());
												row.setDrivid(entry.getKey());
												BigDecimal dist = this.driversumR.sumdistanceperperiodfordriver2(start,
														end, driver.getDriverid());
												if (!Objects.isNull(dist)) {
													distance = Double.valueOf(dist.doubleValue());
													row.setTotdist(distance.doubleValue());
												} else {
													distance = Double.valueOf(0.0D);
													row.setTotdist(0.0D);
												}

												YearMonth driverCreatedate = YearMonth
														.from(LocalDate.parse(driver.getCreatedon().toString()));
												Integer mindv = (int) ChronoUnit.MONTHS.between(driverCreatedate, now);
												Integer seniority = scp.getMinsenioritydriver().intValue();

												int dist2 = 1000 * distance.intValue();
												int mindist = scp.getMindistance().intValue();
												if ((dist2 > mindist) && (mindv > seniority)) {

													query = "select mr.dateofocurrence,mr.noofaccident from manualaccicp mr join driver dr on dr.driverid=mr.driverid and (dr.driverid="
															+ driver.getDriverid() + " ) and dr.transporterid="
															+ driver.getTransporterid()
															+ " and mr.noofaccident>0 and mr.status=1 and mr.transporterid=dr.transporterid and mr.affiliateid=dr.affiliateid  and date_part('month', mr.dateofocurrence) between "
															+ startMonth + " and " + endMonth
															+ " and date_part('year', mr.dateofocurrence) between "
															+ startYear + " and " + endYear;

													List<Map<String, Object>> cars = this.jdbcTemplate
															.queryForList(query);
													String result = "";
													JSONArray array = new JSONArray();

													for (Map data : cars) {
														JSONObject value = new JSONObject();
														value.put("drivid", driver.getDriverid());
														nbracctotal = nbracctotal
																+ Utils.castIntegerObject(data.get("noofaccident"));
														value.put("nbacc",
																Utils.castIntegerObject(data.get("noofaccident")));
														value.put("occdat",
																String.valueOf(data.get("dateofocurrence")));
														array.put(value);
													}
													row.setAcci(array.toString());
													row.setTrpid(driver.getTransporterid());

													row.setInitp(
															Integer.valueOf(((Integer) entry.getValue()).intValue()));
													Integer points1ToAdd = Utils.nullToZero(points1.get(entry.getKey()))
															+ Utils.nullToZero(points1.get(0));

													/**
													 * Integer points2 = Utils.nullToZero( (Integer) ((Map)
													 * initialPoints.get("additionalpoints")) .get(entry.getKey())) +
													 * Utils.nullToZero((Integer) ((Map) initialPoints
													 * .get("additionalpoints")).get(0));
													 */

													Integer manualLost = Utils
															.nullToZero(lostPoints.get(entry.getKey()))
															+ Utils.nullToZero(lostPoints.get(0));
													Integer excpoints = Utils
															.nullToZero(lostExcepPoints.get(entry.getKey()))
															+ Utils.nullToZero(lostExcepPoints.get(0));// lostExcepPoints.get(entry.getKey());

													row.setRecvp(Integer.valueOf(points1ToAdd.intValue()));
													row.setRecovp1(points1ToAdd);
													// row.setRecovp2(points2);
													row.setTotrp(Integer.valueOf(((Integer) entry.getValue()).intValue()
															+ points1ToAdd.intValue() - Math.abs(manualLost.intValue())
															- Math.abs(excpoints.intValue()))
															- Math.abs(nbracctotal * scp.getAccpoints()));
													row.setSubp(-Math.abs(manualLost));
													totallost = Math.abs(manualLost.intValue())
															+ Math.abs(excpoints.intValue())
															+ Math.abs(nbracctotal * scp.getAccpoints());
													row.setTotsubp(-totallost);
													row.setExcp(-Math.abs(excpoints));
													row.setTotacc(-Math.abs(nbracctotal * scp.getAccpoints()));
													row.setNbracc(nbracctotal);
													row.setMinpach(Integer.valueOf(points1ToAdd.intValue()));

													if (distance.compareTo(Double.valueOf(1.0E-8D)) < 0) {
														row.setDrivr(totallost / 1D);
														distance = 1D;
													} else {
														row.setDrivr(totallost / distance.doubleValue());
													}

													int colorzone = 0;

													if (array.length() > 0) {
														colorzone = 3;
													} else if (row.getMinpach().intValue() >= scp.getDriverredlimit()
															.intValue()) {
														colorzone = 1;
													} else if (row.getMinpach().intValue() < scp.getDriverredlimit()
															.intValue()) {
														colorzone = 2;
													}
													row.setMinsdrv(Integer.valueOf(mindv));
													row.setColor(Integer.valueOf(colorzone));
													row.setDrivdist(scp.getDriverdisqualifiedlimit());
													row.setDrivgl(scp.getDrivergreenlimit());
													row.setDrivrl(scp.getDriverredlimit());
													row.setDrivyl(scp.getDriveryellowlimit());
													row.setTrpr(null);
													row.setAvgdrvp(null);
													// row.setTotrp(null);
													// row.setTotsubp(null);
													row.setLevel(level);
													results.add(row);
												}
											} catch (Exception exception1) {
												exception1.printStackTrace();
											}
										}

										// continue;
									} catch (ParseException e) {
										e.printStackTrace();
										// continue;
									}
								}

							}
							// continue;
						}
					} else {
						List<Integer> driverIds = new ArrayList<>();
						if (Objects.isNull(transporterId) || transporterId.isEmpty()) {

							transporterId = this.jdbcTemplate.query(tranporterByAffiliateQuery,
									new RowMapper<Integer>() {
										@Override
										public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
											return Integer.valueOf(rs.getInt("transporterid"));
										}
									});
						}

						Integer totaldrivers = Integer.valueOf(0);
						/**
						 * List<MonthlyRecord> listmonths = Utils.monhtlyfromperiod(startYear0,
						 * endYear0, startMonth0, endMonth0);
						 */
						for (Integer transporterid : transporterId) {
							try {
								String driverQuery = "select createdon from transporter where transporterid = "
										+ transporterid;

								Date createon = this.jdbcTemplate.queryForObject(driverQuery, Date.class);

								YearMonth driverCreatedate = YearMonth
										.from(createon.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

								/**
								 * for (MonthlyRecord integer : listmonths) {
								 * 
								 * int startYear = integer.getYear(); int endYear = integer.getYear(); int
								 * startMonth = integer.getMonth(); int endMonth = integer.getMonth(); YearMonth
								 * now = YearMonth.of(endYear, endMonth);
								 */
								int severity = (int) ChronoUnit.MONTHS.between(driverCreatedate, now);
								SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
								Integer nbracctotal = 0;
								Date start = fm
										.parse(String.valueOf(startYear) + "-" + String.valueOf(startMonth) + "-01");

								System.out.println("start date: " + start);
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(start);
								calendar.set(5, calendar.getActualMaximum(5));
								calendar.add(5, 1);
								Date end = calendar.getTime();

								driverQuery = "select count(*) from manualaccicp where transporterid = " + transporterid
										+ " and createdon >= '" + String.valueOf(startYear) + "-"
										+ String.valueOf(startMonth) + "-01" + "' and createdon < '"
										+ Utils.DateFormat(end, "yyyy-MM-dd") + "'";
								Long counticp = this.jdbcTemplate.queryForObject(driverQuery, Long.class);
								if (Objects.isNull(counticp)) {
									counticp = Long.valueOf(0L);
								}
								if (severity > scp.getMinsenioritytransporter().intValue()) {
									driverIds = new ArrayList<>();
									driverQuery = "select driverid,transporterid,affiliateid,createdon from driver where  status = 1 and  (driverkeycode != '0' and driverkeycode not like '%Unknow Driver%') and transporterid="
											+ transporterid;

									List<DriverBean> transDrivers = this.jdbcTemplate.query(driverQuery,
											new RowMapper<DriverBean>() {

												@Override
												public DriverBean mapRow(ResultSet rs, int rowNum) throws SQLException {
													DriverBean d = new DriverBean();

													d.setDriverid(Integer.valueOf(rs.getInt("driverid")));

													d.setTransporterid(Integer.valueOf(rs.getInt("transporterid")));

													d.setCreatedon(rs.getDate("createdon"));
													return d;
												}
											});

									driverIds.addAll(transDrivers.stream().map(d -> d.getDriverid()).distinct()
											.collect(Collectors.toList()));

									for (DriverBean driver : transDrivers) {
										driverIdMapping.put(driver.getDriverid(), driver);
									}
									totaldrivers = Integer.valueOf(driverIds.size());
									if (totaldrivers.intValue() > 0) {
										initialPoints = getInitialPoints(clientId.intValue(), affiliateId.intValue(),
												transporterid, startMonth, startYear, driverIds, scp, now);

										if (initialPoints != null) {
											System.out.println("initial points: " + initialPoints);

											try {
												HashMap<Integer, Integer> lostPoints = new HashMap<>();

												String ids = "(";
												for (Integer driverId2 : driverIds) {
													ids = ids + driverId2 + ",";
												}
												ids = ids.substring(0, ids.length() - 1) + ")";
												String allwhere;
												allwhere = "((ms.clientid = " + clientId + " and  ms.affiliateid ="
														+ affiliateId + " and  ms.transporterid =" + transporterid
														+ " and ms.driverid in " + ids + ")";
												allwhere = allwhere + " or (ms.clientid = " + clientId
														+ " and  ms.affiliateid =" + affiliateId
														+ " and  ms.transporterid =" + transporterid
														+ " and ms.driverid = 0 )";
												allwhere = allwhere + " or (ms.clientid = " + clientId
														+ " and  ms.affiliateid =" + affiliateId
														+ " and  ms.transporterid =0  and ms.driverid = 0 )";
												allwhere = allwhere + " or (ms.clientid = " + clientId
														+ " and  ms.affiliateid =0  and  ms.transporterid =0 and ms.driverid = 0))";

												String manualSubtractionQuery = "select max(ms.manualsubid) as manualsubid, ms.affiliateid, ms.transporterid, ms.driverid,sum(ms.points*s.points) as points, ms.clientid, max(ms.status) as status, max(ms.visualparamid) as visualparamid,max(ms.dateofocurrence) as dateofocurrence from scpvisualparameter s join manualsubstraction ms on (s.affiliateid = ms.affiliateid and s.visualparamid=ms.visualparamid) where ms.dateofocurrence  >=? and ms.dateofocurrence < ? and "
														+ allwhere
														+ " and ms.status = 1 group by ms.driverid, ms.transporterid, ms.affiliateid, ms.clientid";

												List<Manualsubstraction> manualLostPoints = this.jdbcTemplate.query(
														manualSubtractionQuery, new RowMapper<Manualsubstraction>() {

															@Override
															public Manualsubstraction mapRow(ResultSet rs, int rowNum)
																	throws SQLException {
																Manualsubstraction ms = new Manualsubstraction();
																ms.setAffiliateid(
																		Integer.valueOf(rs.getInt("affiliateid")));
																ms.setClientid(Integer.valueOf(rs.getInt("clientid")));
																ms.setDateofocurrence(rs.getDate("dateofocurrence"));
																ms.setDriverid(Integer.valueOf(rs.getInt("driverid")));

																ms.setPoints(Integer.valueOf(rs.getInt("points")));

																ms.setTransporterid(
																		Integer.valueOf(rs.getInt("transporterid")));
																return ms;
															}
														}, new Object[] { start, end });

												for (Manualsubstraction subtraction : manualLostPoints) {
													System.out.println("lost points for current month: " + subtraction);
													lostPoints.put(subtraction.getDriverid(), subtraction.getPoints());
												}

												HashMap<Integer, Integer> lostExcepPoints = new HashMap<>();

												String monthIn = "(";
												for (int i = startMonth; i <= endMonth; i++) {
													// months.add(Integer.valueOf(i));
													monthIn = monthIn + i + ",";
												}
												monthIn = monthIn.substring(0, monthIn.length() - 1) + ")";

												String driverExpsQuery = this.environment
														.getRequiredProperty("rawstatisticin");
												driverExpsQuery = driverExpsQuery.replaceFirst("XXXXXXXXXXXXXXXXXXXX",
														" and e.driverid in " + ids);

												driverExpsQuery = driverExpsQuery.replaceFirst("YYYYYYYYYY",
														" T.dmonth in " + monthIn + " and ");

												driverExpsQuery = driverExpsQuery.replaceFirst("YEARTDID",
														endYear + "");

												List<ViewDriverwiseExceptions> driverExceptions = this.jdbcTemplate
														.query(driverExpsQuery,
																new RowMapper<ViewDriverwiseExceptions>() {

																	@Override
																	public ViewDriverwiseExceptions mapRow(ResultSet rs,
																			int rowNum) throws SQLException {
																		ViewDriverwiseExceptions exceptions = new ViewDriverwiseExceptions();
																		exceptions.setAffiliateid(Integer
																				.valueOf(rs.getInt("affiliateid")));
																		exceptions.setDriverid(
																				Integer.valueOf(rs.getInt("driverid")));
																		exceptions.setTransporterid(Integer
																				.valueOf(rs.getInt("transporterid")));
																		exceptions.setAlarmscore(Integer
																				.valueOf(rs.getInt("alarmscore")));
																		exceptions.setAlertscore(Integer
																				.valueOf(rs.getInt("alertscore")));
																		exceptions.setRecordscore(Integer
																				.valueOf(rs.getInt("recordscore")));
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
													lostExcepPoints.put(exception.getDriverid(),
															Integer.valueOf(points));
												}

												System.out.println(
														"driver lost exceptions for current month: " + lostExcepPoints);
												allwhere = "((mr.clientid = " + clientId + " and  mr.affiliateid ="
														+ affiliateId + " and  mr.transporterid =" + transporterid
														+ " and mr.driverid in " + ids + ")";
												allwhere = allwhere + " or (mr.clientid = " + clientId
														+ " and  mr.affiliateid =" + affiliateId
														+ " and  mr.transporterid =" + transporterid
														+ " and mr.driverid = 0 )";
												allwhere = allwhere + " or (mr.clientid = " + clientId
														+ " and  mr.affiliateid =" + affiliateId
														+ " and  mr.transporterid =0  and mr.driverid = 0 )";
												allwhere = allwhere + " or (mr.clientid = " + clientId
														+ " and  mr.affiliateid =0  and  mr.transporterid =0 and mr.driverid = 0))";
												String manualRecoveryPointsQuery = "select max(mr.manualrecid) as manualrecid, mr.clientid, mr.affiliateid, mr.transporterid, mr.driverid, max(mr.status) as status, max(mr.dateofocurrence) as dateofocurrence, max(mr.recoveryid) as recoveryid, sum(mr.points*s.points) as points from scprecovery s join manualrecovery mr on (s.affiliateid = mr.affiliateid and s.recoveryid=mr.recoveryid) where mr.recoveryid != "
														+ Utils.recoveryIdInTabtaBaseByFrequenceId()
														+ " and s.recoveryid !="
														+ Utils.recoveryIdInTabtaBaseByFrequenceId()
														+ " and mr.dateofocurrence >= ? and mr.dateofocurrence < ? and "
														+ allwhere
														+ " and mr.status = 1 group by mr.driverid, mr.transporterid, mr.affiliateid, mr.clientid";

												List<Manualrecovery> additionalPoints1 = this.jdbcTemplate.query(
														manualRecoveryPointsQuery, new RowMapper<Manualrecovery>() {

															@Override
															public Manualrecovery mapRow(ResultSet rs, int rowNum)
																	throws SQLException {
																Manualrecovery mr = new Manualrecovery();
																mr.setAffiliateid(
																		Integer.valueOf(rs.getInt("affiliateid")));
																mr.setClientid(Integer.valueOf(rs.getInt("clientid")));
																mr.setDateofocurrence(rs.getDate("dateofocurrence"));
																mr.setDriverid(Integer.valueOf(rs.getInt("driverid")));
																mr.setManualrecid(
																		Integer.valueOf(rs.getInt("manualrecid")));
																mr.setPoints(Integer.valueOf(rs.getInt("points")));
																mr.setRecoveryid(
																		Integer.valueOf(rs.getInt("recoveryid")));
																mr.setTransporterid(
																		Integer.valueOf(rs.getInt("transporterid")));
																return mr;
															}
														}, new Object[] { start, end });

												HashMap<Integer, Integer> points1 = new HashMap<>();
												for (Manualrecovery recovery : additionalPoints1) {
													System.out.println("recovery points for month: " + recovery);
													points1.put(recovery.getDriverid(), recovery.getPoints());
												}

												/**
												 * for (Map.Entry<Integer, Object> driverPoints : (initialPoints
												 * .get("initialpoints")).entrySet()) {
												 * 
												 * Integer points1ToAdd = Utils
												 * .nullToZero(points1.get(driverPoints.getKey())) +
												 * Utils.nullToZero(points1.get(0));
												 * 
												 * Integer points2 = Utils.nullToZero( (Integer) ((Map)
												 * initialPoints.get("additionalpoints")) .get(driverPoints.getKey())) +
												 * Utils.nullToZero((Integer) ((Map) initialPoints
												 * .get("additionalpoints")).get(0)); Integer manualLost = Utils
												 * .nullToZero(lostPoints.get(driverPoints.getKey())) +
												 * Utils.nullToZero(lostPoints.get(0)); Integer excpoints = Utils
												 * .nullToZero(lostExcepPoints.get(driverPoints.getKey())) +
												 * Utils.nullToZero(lostExcepPoints.get(0));
												 * finalPoints.put(driverPoints.getKey(), Integer .valueOf(((Integer)
												 * driverPoints.getValue()).intValue() + points1ToAdd.intValue() +
												 * points2.intValue() - manualLost.intValue() - excpoints.intValue()));
												 * }
												 */

												Double distance = Double.valueOf(0.0D);

												String accident = "{}";

												JSONArray array = new JSONArray();

												JSONObject value = new JSONObject();
												Integer points1ToAdd = Integer.valueOf(0);
												Integer points2 = Integer.valueOf(0);
												Integer manualLost = Integer.valueOf(0);
												Integer excpoints = Integer.valueOf(0);
												Integer totalremainingspoints = Integer.valueOf(0);
												Integer totalinitpoints = Integer.valueOf(0);
												Double transporterrate = 0D;
												Double distance0;
												for (Map.Entry<Integer, Object> entry : (initialPoints
														.get("initialpoints")).entrySet()) {

													DriverBean driver = driverIdMapping.get(entry.getKey());
													BigDecimal dist = this.driversumR.sumdistanceperperiodfordriver2(
															start, end, driver.getDriverid());
													if (!Objects.isNull(dist)) {
														distance0 = Double.valueOf(dist.doubleValue());
														distance = Double.valueOf(
																distance.doubleValue() + distance0.doubleValue());
														// row.setTotdist(distance.doubleValue());
													} else {
														distance0 = Double.valueOf(0.0D);
														// row.setTotdist(0.0D);
													}
													int dist2 = (distance0.intValue()) * 1000;
													int mindist = scp.getMindistance().intValue();

													YearMonth driverCreatedate2 = YearMonth
															.from(LocalDate.parse(driver.getCreatedon().toString()));
													Integer mindv = (int) ChronoUnit.MONTHS.between(driverCreatedate2,
															now);
													Integer seniority2 = scp.getMinsenioritydriver().intValue();
													if ((dist2 > mindist) && (mindv > seniority2)) {

														/**
														 * query = "select mr.dateofocurrence,mr.noofaccident from
														 * manualaccicp mr join view_driver_trans_aff dr on
														 * dr.driverid=mr.driverid and (dr.driverid=" +
														 * driver.getDriverid() + " ) and dr.transporterid=" +
														 * driver.getTransporterid() + " and mr.noofaccident>0 and
														 * date_part('month', age(concat(" + endYear + ",'-'," +
														 * endMonth + ",'-'," + (new Date(endYear, endMonth,
														 * 0)).getDate() + ")::timestamp,dr.createdon)) >= (select
														 * minsenioritydriver from scp sc where sc.affiliateid=" +
														 * affiliateId + " and sc.status=1) and mr.status=1 and
														 * mr.transporterid=dr.transporterid and
														 * mr.affiliateid=dr.affiliateid and date_part('month',
														 * mr.dateofocurrence) between " + startMonth + " and " +
														 * endMonth + " and date_part('year', mr.dateofocurrence)
														 * between " + startYear + " and " + endYear;
														 */

														query = "select mr.dateofocurrence,mr.noofaccident,mr.driverid from manualaccicp mr join driver dr on dr.driverid=mr.driverid and dr.transporterid="
																+ driver.getTransporterid()
																+ " and mr.noofaccident>0 and mr.status=1 and mr.transporterid=dr.transporterid and mr.affiliateid=dr.affiliateid  and date_part('month', mr.dateofocurrence) between "
																+ startMonth + " and " + endMonth
																+ " and date_part('year', mr.dateofocurrence) between "
																+ startYear + " and " + endYear;
														List<Map<String, Object>> cars = this.jdbcTemplate
																.queryForList(query);
														for (Map data : cars) {
															value.put("drivid",
																	Utils.castIntegerObject(data.get("driverid")));
															nbracctotal = nbracctotal
																	+ Utils.castIntegerObject(data.get("noofaccident"));
															value.put("nbacc",
																	Utils.castIntegerObject(data.get("noofaccident")));
															value.put("occdat",
																	String.valueOf(data.get("dateofocurrence")));

															array.put(value);
														}

														points1ToAdd = Integer.valueOf(points1ToAdd.intValue())
																+ Utils.nullToZero(points1.get(entry.getKey()))
																+ Utils.nullToZero(points1.get(0));

														/*
														 * points2 = Integer.valueOf(points2.intValue()) +
														 * Utils.nullToZero((Integer) ((Map) initialPoints
														 * .get("additionalpoints")).get(entry.getKey())) +
														 * Utils.nullToZero((Integer) ((Map) initialPoints
														 * .get("additionalpoints")).get(0));
														 */
														manualLost = manualLost
																+ Utils.nullToZero(lostPoints.get(entry.getKey()))
																+ Utils.nullToZero(lostPoints.get(0));
														excpoints = excpoints
																+ Utils.nullToZero(lostExcepPoints.get(entry.getKey()))
																+ Utils.nullToZero(lostExcepPoints.get(0));// lostExcepPoints.get(entry.getKey());

														totalinitpoints = totalinitpoints
																+ ((Integer) entry.getValue()).intValue();
													}

												}

												totalremainingspoints = totalinitpoints + points1ToAdd.intValue()
														- Math.abs(manualLost.intValue())
														- Math.abs(excpoints.intValue())
														- Math.abs(nbracctotal * scp.getAccpoints());

												RewardRanking row = new RewardRanking();
												row.setAffid(affiliateId);
												row.setNbrchaff(totaldrivers);
												row.setCusid(clientId);

												row.setDrivid(null);

												row.setAcci(array.toString());
												row.setTrpid(transporterid);
												row.setNbracc(counticp.intValue());
												row.setInitp(totalinitpoints);
												// row.setTotdist(Utils.truncate(distance).doubleValue());
												row.setTotdist(distance);
												row.setRecvp(Integer.valueOf(points1ToAdd.intValue()));
												row.setRecovp1(points1ToAdd);
												row.setRecovp2(0);
												row.setTotrp(totalremainingspoints);
												row.setTotacc(-Math.abs(nbracctotal * scp.getAccpoints()));
												row.setSubp(-Math.abs(manualLost));
												row.setTotsubp(-Math.abs(manualLost.intValue())
														- Math.abs(excpoints.intValue())
														- Math.abs(nbracctotal * scp.getAccpoints()));
												row.setExcp(-Math.abs(excpoints));
												row.setMinpach(0);
												row.setNbrchaff(totaldrivers);
												if (distance.compareTo(Double.valueOf(1.0E-8D)) < 0) {
													row.setTrpr(((Math.abs(manualLost) + Math.abs(excpoints)) * 1D));
												} else {
													row.setTrpr(
															(Math.abs(manualLost) + Math.abs(excpoints)) / distance);
												}

												int colorzone = 0;
												row.setColor(Integer.valueOf(colorzone));
												row.setNbracc(nbracctotal);
												/**
												 * row.setDrivdist(scp.getDriverdisqualifiedlimit());
												 * row.setDrivgl(scp.getDrivergreenlimit());
												 * row.setDrivrl(scp.getDriverredlimit());
												 * row.setDrivyl(scp.getDriveryellowlimit());
												 */

												if (totaldrivers.intValue() > 0) {
													row.setAvgdrvp(Utils.truncate(Double.valueOf(
															row.getTotrp().intValue() / totaldrivers.intValue())));
												} else {
													row.setAvgdrvp(
															Utils.truncate(Double.valueOf(row.getTotrp().intValue())));
												}

												if (array.length() > 0) {
													colorzone = 3;
												} else if (row.getTotrp().intValue() >= scp.getTransporterredlimit()
														.intValue()) {
													colorzone = 1;
												} else if (row.getTotrp().intValue() < scp.getTransporterredlimit()
														.intValue()) {
													colorzone = 2;
												}
												row.setDrivdist(null);
												row.setDrivr(null);
												row.setDrivrl(null);
												row.setDrivgl(null);
												row.setDrivid(null);
												row.setDrivyl(null);
												row.setLevel(level);
												results.add(row);
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									}
								}

							} catch (Exception ex) {
								ex.printStackTrace();
							}

						}

						transporterId = new ArrayList<>();
					}
				}
			}

			return results;
		}
		return new ArrayList();
	}
}
