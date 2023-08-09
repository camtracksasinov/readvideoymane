//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.dao;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.camtrack.bean.RewardRankingBean;
import com.camtrack.config.Utils;
import com.camtrack.user.repository.RemaningpoingsRepository;

@Repository("rewardrankingdao")
@PropertySource({ "classpath:sqlqueries.properties" })
public class RewardRankingDaoImpl implements RewardRankingDaoInterface {
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	RemaningpoingsRepository remainR;

	@Override
	public List<Map<String, Object>> getRewardRanking(final Integer[] clientid00, final Integer[] affiliateid00,
			final Integer[] transporterid00, final Integer[] driverid00, final int startyear, final int endyear,
			final int startmonth, final int endmonth) {
		final List<Map<String, Object>> ranklist = new ArrayList<>();
		final List<Map<String, Object>> transporters = new ArrayList<>();
		if (((!Objects.isNull(affiliateid00) && affiliateid00.length != 0)
				|| (!Objects.isNull(clientid00) && clientid00.length != 0))
				&& (Objects.isNull(transporterid00) || transporterid00.length == 0)
				&& (Objects.isNull(driverid00) || driverid00.length == 0)) {
			if (!Objects.isNull(affiliateid00) && affiliateid00.length != 0) {
				for (final Integer affiliateid : affiliateid00) {
					final String transsqry = "SELECT distinct dr.transporterid,dr.affiliateid,af.customerid  FROM transporter dr, customeraffiliate af  WHERE  dr.affiliateid=af.affiliateid and dr.status = '1' and dr.affiliateid ="
							+ affiliateid + " and date_part('month', age(concat(" + endyear + ",'-'," + endmonth
							+ ",'-'," + new Date(endyear, endmonth, 0).getDate()
							+ ")::timestamp,dr.createdon)) >= (select sc.minsenioritytransporter from scp sc where  sc.affiliateid="
							+ affiliateid + " and sc.status=1)";
					transporters.addAll(this.jdbcTemplate.queryForList(transsqry));
				}
			} else if (!Objects.isNull(clientid00) && clientid00.length != 0) {
				for (final Integer affiliateid : clientid00) {
					final String transsqry = "SELECT distinct dr.transporterid,dr.affiliateid,af.customerid  FROM transporter dr, customeraffiliate af  WHERE  dr.affiliateid=af.affiliateid and dr.status = '1' and af.customerid ="
							+ affiliateid + " and date_part('month', age(concat(" + endyear + ",'-'," + endmonth
							+ ",'-'," + new Date(endyear, endmonth, 0).getDate()
							+ ")::timestamp,dr.createdon)) >= (select sc.minsenioritytransporter from scp sc where  sc.clientid="
							+ affiliateid + " and sc.status=1)";
					transporters.addAll(this.jdbcTemplate.queryForList(transsqry));
				}
			}
			for (final Map<String, Object> transDet : transporters) {
				final int transId = Utils.castIntegerObject(transDet.get("transporterid"));
				final int affid = Utils.castIntegerObject(transDet.get("affiliateid"));
				final int customid = Utils.castIntegerObject(transDet.get("customerid"));
				final String queryforredcheck = "  select  count(mi.manualsubrecid) from manualaccicp mi   where mi.transporterid="
						+ transId
						+ "and mi.transportericp=3 and  date(concat( date_part('year', mi.dateofocurrence),'-', date_part('month', mi.dateofocurrence),'-1')) between date(concat("
						+ startyear + ",'-'," + startmonth + ",'-1')) and date(concat(" + endyear + ",'-'," + endmonth
						+ ",'-1')) ";
				final int redrate = this.jdbcTemplate.queryForObject(queryforredcheck, Integer.class);
				if (redrate == 0) {
					String query = "select clientid,clientname,affiliateid,affiliatename,transporterid,transportername,noofdriver,totalremainingpoints,totalsubstractedpoints,totaldistance,noofaccident,avgdriverpoins as avgdriverpoints,trasporterrate as transporterrate,transportericp,transporterredlimit,initialpoints,0 drivername,0 dateofocurrence from sp_reward_ranking_transporterbased("
							+ customid + "," + affid + "," + transId + "," + startmonth + "," + endmonth + ","
							+ startyear + "," + endyear + ") ";
					System.out.println(query);
					final List<Map<String, Object>> list = this.jdbcTemplate.queryForList(query);
					for (final Map<String, Object> map : list) {
						Double minpoints = 0.0;
						final Integer tranredlimit = Utils.castIntegerObject(map.get("transporterredlimit"));
						// final Float drivervalues = map.get("avgdriverpoins");
						if (map.get("avgdriverpoints") != null) {
							final String score = map.get("avgdriverpoints").toString();
							minpoints = Double.parseDouble(score);
						}
						final BigDecimal noofaccidents = Utils.castBigDecimalObject(map.get("noofaccident"));
						if (minpoints >= tranredlimit && noofaccidents.intValue() == 0) {
							map.put("colorcode", 0);
						} else if (minpoints < tranredlimit && noofaccidents.intValue() == 0) {
							map.put("colorcode", 1);
						} else if (minpoints < tranredlimit && noofaccidents.intValue() != 0) {
							map.put("colorcode", 2);
						} else if (minpoints >= tranredlimit && noofaccidents.intValue() != 0) {
							map.put("colorcode", 2);
						} else {
							map.put("colorcode", 0);
						}
						query = "select TRIM(concat(dr.name,'_[',dr.driverkeycode,']')) as drivername from manualaccicp mr join view_driver_trans_aff2 dr on dr.driverid=mr.driverid and dr.transporterid="
								+ transId + " and mr.status=1 and mr.transporterid=dr.transporterid and mr.affiliateid="
								+ affid + " and mr.noofaccident>0 and mr.clientid = " + customid
								+ " and date_part('month', mr.dateofocurrence) between " + startmonth + " and "
								+ endmonth + " and date_part('year', mr.dateofocurrence) between " + startyear + " and "
								+ endyear;
						map.put("drivername", this.jdbcTemplate.queryForList(query));
						query = "select concat( mr.dateofocurrence,' : ',mr.noofaccident) as dateofocurrence from manualaccicp mr join view_driver_trans_aff2 dr on dr.driverid=mr.driverid and dr.transporterid="
								+ transId
								+ "  and mr.status=1 and mr.transporterid=dr.transporterid and mr.affiliateid=" + affid
								+ " and mr.clientid=" + customid
								+ " and mr.noofaccident>0 and date_part('month', mr.dateofocurrence) between "
								+ startmonth + " and " + endmonth
								+ " and date_part('year', mr.dateofocurrence) between " + startyear + " and " + endyear;
						map.put("dateofocurrence", this.jdbcTemplate.queryForList(query));
						ranklist.add(map);
					}
				}
			}
			Collections.sort(ranklist, new Comparator<Map<String, Object>>() {
				@Override
				public int compare(final Map<String, Object> first, final Map<String, Object> second) {
					final BigDecimal firstValue = Utils.castBigDecimalObject(first.get("avgdriverpoints"));
					final BigDecimal secondValue = Utils.castBigDecimalObject(second.get("avgdriverpoints"));
					return secondValue.compareTo(firstValue);
				}
			});
			Collections.sort(ranklist, new Comparator<Map<String, Object>>() {
				@Override
				public int compare(final Map<String, Object> first, final Map<String, Object> second) {
					final BigDecimal firstValue = Utils.castBigDecimalObject(first.get("transporterrate"));
					final BigDecimal secondValue = Utils.castBigDecimalObject(second.get("transporterrate"));
					return secondValue.compareTo(firstValue);
				}
			});
			Collections.sort(ranklist, new Comparator<Map<String, Object>>() {
				@Override
				public int compare(final Map<String, Object> first, final Map<String, Object> second) {
					final BigDecimal firstValue = Utils.castBigDecimalObject(first.get("colorcode"));
					final BigDecimal secondValue = Utils.castBigDecimalObject(second.get("colorcode"));
					return secondValue.compareTo(firstValue);
				}
			});
		} else if (!Objects.isNull(transporterid00) || !Objects.isNull(driverid00)) {
			final Calendar startCalendar = new GregorianCalendar();
			startCalendar.setTime(new Date(startyear, startmonth, 1));
			final Calendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(new Date(endyear, endmonth, 1));
			final int diffYear = endCalendar.get(1) - startCalendar.get(1);
			final int diffMonth = diffYear * 12 + endCalendar.get(2) - startCalendar.get(2);
			final int[][] month = new int[diffMonth][diffMonth + 1];
			final int[][] Month = new int[diffMonth + 1][diffMonth + 2];
			if (endyear == startyear && endmonth == startmonth) {
				Month[0][0] = endyear;
				Month[0][1] = endmonth;
			} else {
				int i = 0;
				int starty = startyear;
				int startm = startmonth;
				for (i = 0; i < diffMonth; ++i) {
					month[i][0] = starty;
					month[i][1] = startm;
					if (++startm > 12) {
						++starty;
						startm = 1;
						if ((starty > endyear) || (startm > endmonth)) {
							month[i][0] = endyear;
							month[i][1] = endmonth;
							break;
						}
					}
				}
				for (i = 0; i < month.length; ++i) {
					Month[i] = month[i];
				}
				Month[i][0] = endyear;
				Month[i][1] = endmonth;
			}
			List<Map<String, Object>> list2 = new ArrayList<>();
			List<Map<String, Object>> driversandaffandcusid = new ArrayList<>();
			if (!Objects.isNull(driverid00) && driverid00.length > 0) {
				final String driver = RewardRankingBean.listToString(driverid00);
				final String allids = "select d.driverid, t.transporterid,c.affiliateid, cus.customerid from driver d, transporter t , customeraffiliate c , customer cus\nwhere d.transporterid = t.transporterid and t.affiliateid = c.affiliateid and c.customerid = cus.customerid and d.driverid in ("
						+ driver + ")";
				driversandaffandcusid = this.jdbcTemplate.queryForList(allids);
				for (final Map<String, Object> transDet2 : driversandaffandcusid) {
					final int transId = Utils.castIntegerObject(transDet2.get("transporterid"));
					final int affid = Utils.castIntegerObject(transDet2.get("affiliateid"));
					final int customid = Utils.castIntegerObject(transDet2.get("customerid"));
					final int driverid = Utils.castIntegerObject(transDet2.get("driverid"));
					final String driverquery = "SELECT count(dr.driverid)  FROM view_driver_trans_aff2 dr, customeraffiliate af  WHERE  dr.affiliateid=af.affiliateid and dr.status = '1' and dr.transporterid="
							+ transId + " and dr.affiliateid=" + affid + " and af.customerid=" + customid
							+ " and dr.driverid=" + driverid + " and date_part('month', age(concat(" + endyear + ",'-',"
							+ endmonth + ",'-'," + new Date(endyear, endmonth, 0).getDate()
							+ ")::timestamp,dr.createdon)) >= (select sc.minsenioritydriver from scp sc where sc.clientid="
							+ customid + " and sc.affiliateid=" + affid + " and sc.status=1)";
					final int Driverid = (int) this.jdbcTemplate.queryForObject(driverquery, (Class) Integer.class);
					if (Driverid > 0) {
						Double remainingpoint = this.remainR.findminremainpointsDriverOverYear(driverid, startyear,
								endyear);
						if (Objects.isNull(remainingpoint)) {
							remainingpoint = 0.0;
						}
						String query2 = "select clientid,clientname,affiliateid,affiliatename,transporterid,transportername,driverid,drivername,totalremainingpoints,totalsubstractedpoints,totaldistance,noofaccident,driverrate,driverredlimit,initialpoints ,"
								+ remainingpoint + " minpoints, 0 dateofocurrence from sp_reward_ranking_driver("
								+ customid + "," + affid + "," + transId + "," + driverid + "," + startmonth + ","
								+ endmonth + "," + startyear + "," + endyear + ")";
						System.out.println("->" + query2);
						list2 = this.jdbcTemplate.queryForList(query2);
						for (final Map<String, Object> map2 : list2) {
							final Integer driverredlimit = Utils.castIntegerObject(map2.get("driverredlimit"));
							final String score2 = map2.get("minpoints").toString();
							final Double minpoints2 = Double.parseDouble(score2);
							final BigDecimal noofaccidents2 = Utils.castBigDecimalObject(map2.get("noofaccident"));
							if (minpoints2 >= driverredlimit && noofaccidents2.intValue() == 0) {
								map2.put("colorcode", 0);
							} else if (minpoints2 < driverredlimit && noofaccidents2.intValue() == 0) {
								map2.put("colorcode", 1);
							} else if (minpoints2 < driverredlimit && noofaccidents2.intValue() != 0) {
								map2.put("colorcode", 2);
							} else if (minpoints2 >= driverredlimit && noofaccidents2.intValue() != 0) {
								map2.put("colorcode", 2);
							} else {
								map2.put("colorcode", 0);
							}
							query2 = "select concat( mr.dateofocurrence,' : ',mr.noofaccident) as dateofocurrence from manualaccicp mr join view_driver_trans_aff2 dr on dr.driverid=mr.driverid and (dr.driverid="
									+ driverid + " ) and dr.transporterid=" + transId
									+ " and mr.noofaccident>0 and date_part('month', age(concat(" + endyear + ",'-',"
									+ endmonth + ",'-'," + new Date(endyear, endmonth, 0).getDate()
									+ ")::timestamp,dr.createdon)) >= (select minsenioritydriver from scp sc where sc.clientid="
									+ customid + " and sc.affiliateid=" + affid
									+ " and sc.status=1) and mr.status=1 and mr.transporterid=dr.transporterid and mr.affiliateid=dr.affiliateid and mr.clientid="
									+ customid + " and date_part('month', mr.dateofocurrence) between " + startmonth
									+ " and " + endmonth + " and date_part('year', mr.dateofocurrence) between "
									+ startyear + " and " + endyear;
							map2.put("dateofocurrence", this.jdbcTemplate.queryForList(query2));
							if (!list2.isEmpty()) {
								ranklist.add(map2);
							}
						}
					}
				}
			} else {
				final String transporter = RewardRankingBean.listToString(transporterid00);
				final String allids = "select t.transporterid,c.affiliateid, cus.customerid from transporter t , customeraffiliate c , customer cus\nwhere t.affiliateid = c.affiliateid and c.customerid = cus.customerid and t.transporterid in ("
						+ transporter + ")";
				driversandaffandcusid = this.jdbcTemplate.queryForList(allids);
				for (final Map<String, Object> transDet2 : driversandaffandcusid) {
					final int transId = Utils.castIntegerObject(transDet2.get("transporterid"));
					final int affid = Utils.castIntegerObject(transDet2.get("affiliateid"));
					final int customid = Utils.castIntegerObject(transDet2.get("customerid"));
					String query3 = "select *,round((coalesce(initialpoints,0.0)::numeric + (coalesce(recoverypoint1,0.0)::numeric + coalesce(recoverypoint2,0.0)::numeric ) - (coalesce(subpoints,0.0)::NUMERIC + coalesce(excpnpoints,0.0)::NUMERIC))::numeric,5) as totalremainingpoints,round(coalesce(coalesce(subpoints,0.0)::NUMERIC + coalesce(excpnpoints,0.0)::NUMERIC,0.0)::numeric,5) as totalsubstractedpoints,round(coalesce(coalesce (coalesce(subpoints,0.0)::NUMERIC + coalesce(excpnpoints,0.0)::NUMERIC,0.0)::numeric / (coalesce(totaldistance,0.0)::NUMERIC),0.0)::numeric,5) as driverrate from sp_reward_ranking_driver_report("
							+ customid + "," + affid + "," + transId + "," + 0 + "," + startmonth + "," + endmonth + ","
							+ startyear + "," + endyear + ")";
					System.out.println(query3);
					list2 = this.jdbcTemplate.queryForList(query3);
					final Iterator itr3 = list2.iterator();
					int totaldistance = 0;
					while (itr3.hasNext()) {
						final Map<String, Object> map3 = (Map<String, Object>) itr3.next();
						final Integer driverId = Utils.castIntegerObject(map3.get("driverid"));
						final Double remainingpoint2 = this.remainR.findminremainpointsDriverOverYear(driverId,
								startyear, endyear);
						if (!Objects.isNull(remainingpoint2)) {
							map3.put("minpoints", new BigDecimal(remainingpoint2, MathContext.DECIMAL64));
						}
						if (map3.get("totaldistance") != null) {
							final BigDecimal test = Utils.castBigDecimalObject(map3.get("totaldistance"));
							totaldistance = test.intValue();
						}
						final Integer driverredlimit = Utils.castIntegerObject(map3.get("driverredlimit"));
						final String score2 = map3.get("minpoints").toString();
						System.out.println(score2);
						final Double minpoints2 = Double.parseDouble(score2);
						final BigDecimal noofaccidents2 = Utils.castBigDecimalObject(map3.get("noofaccident"));
						if (minpoints2 >= driverredlimit && noofaccidents2.intValue() == 0) {
							map3.put("colorcode", 0);
						} else if (minpoints2 < driverredlimit && noofaccidents2.intValue() == 0) {
							map3.put("colorcode", 1);
						} else if (minpoints2 < driverredlimit && noofaccidents2.intValue() != 0) {
							map3.put("colorcode", 2);
						} else if (minpoints2 >= driverredlimit && noofaccidents2.intValue() != 0) {
							map3.put("colorcode", 2);
						} else {
							map3.put("colorcode", 0);
						}
						query3 = "select concat( mr.dateofocurrence,' : ',mr.noofaccident) as dateofocurrence from manualaccicp mr join view_driver_trans_aff2 dr on dr.driverid=mr.driverid and dr.transporterid="
								+ transId + " and mr.noofaccident>0 and date_part('month', age(concat(" + endyear
								+ ",'-'," + endmonth + ",'-'," + new Date(endyear, endmonth, 0).getDate()
								+ ")::timestamp,dr.createdon)) >= (select minsenioritydriver from scp sc where sc.clientid="
								+ customid + " and sc.affiliateid=" + affid
								+ " and sc.status=1) and mr.status=1 and mr.transporterid=dr.transporterid and mr.affiliateid=dr.affiliateid and mr.clientid="
								+ customid + " and date_part('month', mr.dateofocurrence) between " + startmonth
								+ " and " + endmonth + " and date_part('year', mr.dateofocurrence) between " + startyear
								+ " and " + endyear;
						map3.put("dateofocurrence", this.jdbcTemplate.queryForList(query3));
						if (totaldistance != 0) {
							ranklist.add(map3);
						}
					}
				}
			}
			// Collections.sort(ranklist, (Comparator<? super Map<String, Object>>)new
			// RewardRankingDaoImpl.RewardRankingDaoImpl$6(this));
			Collections.sort(ranklist, new Comparator<Map<String, Object>>() {
				@Override
				public int compare(final Map<String, Object> first, final Map<String, Object> second) {
					final BigDecimal firstValue = Utils.castBigDecimalObject(first.get("driverrate"));
					final BigDecimal secondValue = Utils.castBigDecimalObject(second.get("driverrate"));
					return secondValue.compareTo(firstValue);
				}
			});
			Collections.sort(ranklist, new Comparator<Map<String, Object>>() {
				@Override
				public int compare(final Map<String, Object> first, final Map<String, Object> second) {
					final BigDecimal firstValue = Utils.castBigDecimalObject(first.get("noofaccident"));
					final BigDecimal secondValue = Utils.castBigDecimalObject(second.get("noofaccident"));
					return secondValue.compareTo(firstValue);
				}
			});
			Comparator<Map<String, Object>> myComparatorMin = new Comparator<Map<String, Object>>() {
				@Override
				public int compare(Map<String, Object> first, Map<String, Object> second) {
					/*
					 * BigDecimal firstValue = first.get("driverrate"); BigDecimal secondValue =
					 * second.get("driverrate");
					 *
					 * BigDecimal firstValueMin = first.get("minpoints"); BigDecimal secondValueMin
					 * = second.get("minpoints")
					 */

					BigDecimal firstValueRed = new BigDecimal(
							Integer.valueOf(Integer.parseInt("" + first.get("colorcode"))));
					BigDecimal secondValueRed = new BigDecimal(
							Integer.valueOf(Integer.parseInt("" + second.get("colorcode"))));

					return firstValueRed.compareTo(secondValueRed);
					// System.out.println(firstValueMin+","+firstValueRed+","+firstValueMin.compareTo(firstValueRed));
					// System.out.println(secondValueMin+","+secondValueRed+","+secondValueMin.compareTo(secondValueRed));
					/*
					 * if(firstValueMin.compareTo(firstValueRed) >=0 ||
					 * secondValueMin.compareTo(secondValueRed)>=0) return
					 * secondValueMin.compareTo(firstValueMin); else return 0;
					 */

				}
			};
			Collections.sort(ranklist, myComparatorMin);
		}
		return ranklist;
	}

	@Override
	public List getRewardRankingByDriver(final int clientid, final int affiliateid, final int transporterid,
			final int driverid, final int startyear, final int endyear, final int startmonth, final int endmonth) {
		final List ranklist = new ArrayList();
		final Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(new Date(startyear, startmonth, 1));
		final Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(new Date(endyear, endmonth, 1));
		final int diffYear = endCalendar.get(1) - startCalendar.get(1);
		final int diffMonth = diffYear * 12 + endCalendar.get(2) - startCalendar.get(2);
		final int[][] month = new int[diffMonth][diffMonth + 1];
		final int[][] Month = new int[diffMonth + 1][diffMonth + 2];
		if (endyear == startyear && endmonth == startmonth) {
			Month[0][0] = endyear;
			Month[0][1] = endmonth;
		} else {
			int i = 0;
			int starty = startyear;
			int startm = startmonth;
			for (i = 0; i < diffMonth; ++i) {
				month[i][0] = starty;
				month[i][1] = startm;
				if (++startm > 12) {
					++starty;
					startm = 1;
					if ((starty > endyear) || (startm > endmonth)) {
						month[i][0] = endyear;
						month[i][1] = endmonth;
						break;
					}
				}
			}
			for (i = 0; i < month.length; ++i) {
				Month[i] = month[i];
			}
			Month[i][0] = endyear;
			Month[i][1] = endmonth;
		}
		if (driverid > 0) {
			final String driverquery = "SELECT count( dr.driverid)  FROM driver dr, customeraffiliate af  WHERE  dr.affiliateid=af.affiliateid and dr.status = '1' and dr.transporterid="
					+ transporterid + " and dr.affiliateid=" + affiliateid + " and af.customerid=" + clientid
					+ " and dr.driverid=" + driverid + " and date_part('month', age(concat(" + endyear + ",'-',"
					+ endmonth + ",'-'," + new Date(endyear, endmonth, 0).getDate()
					+ ")::timestamp,dr.createdon)) >= (select sc.minsenioritydriver from scp sc where sc.clientid="
					+ clientid + " and sc.affiliateid=" + affiliateid + " and sc.status=1)";
			final int Driverid = (int) this.jdbcTemplate.queryForObject(driverquery, (Class) Integer.class);
			if (Driverid > 0) {
				final Double[] remainingpoint = new Double[Month.length];
				for (int j = 0; j < Month.length; ++j) {
					final String query = "select coalesce(SUM(totalremainingpoints),0) as totalremainingpoints from sp_reward_ranking_driver_minpoints("
							+ clientid + "," + affiliateid + "," + transporterid + "," + driverid + "," + Month[j][1]
							+ "," + Month[j][1] + "," + Month[j][0] + "," + Month[j][0] + ")";
					remainingpoint[j] = (Double) this.jdbcTemplate.queryForObject(query, (Class) Double.class);
				}
				Arrays.sort(remainingpoint);
				String query2 = "select clientid,clientname,affiliateid,affiliatename,transporterid,transportername,driverid,drivername,totalremainingpoints,totalsubstractedpoints,totaldistance,noofaccident,driverrate,driverredlimit,initialpoints ,"
						+ remainingpoint[0] + " minpoints, 0 dateofocurrence from sp_reward_ranking_driver(" + clientid
						+ "," + affiliateid + "," + transporterid + "," + driverid + "," + startmonth + "," + endmonth
						+ "," + startyear + "," + endyear + ")";
				final List<Map<String, Object>> list = this.jdbcTemplate.queryForList(query2);
				for (final Map map : list) {
					query2 = "select mr.dateofocurrence as dateofocurrence from manualaccicp mr join driver dr on dr.driverid=mr.driverid and (dr.driverid="
							+ driverid + " ) and dr.transporterid=" + transporterid
							+ " and date_part('month', age(concat(" + endyear + ",'-'," + endmonth + ",'-',"
							+ new Date(endyear, endmonth, 0).getDate()
							+ ")::timestamp,dr.createdon)) >= (select minsenioritydriver from scp sc where sc.clientid="
							+ clientid + " and sc.affiliateid=" + affiliateid
							+ " and sc.status=1) and mr.status=1 and mr.transporterid=dr.transporterid and mr.affiliateid=dr.affiliateid and mr.clientid="
							+ clientid + " and date_part('month', mr.dateofocurrence) between " + startmonth + " and "
							+ endmonth + " and date_part('year', mr.dateofocurrence) between " + startyear + " and "
							+ endyear;
					map.put("dateofocurrence", this.jdbcTemplate.queryForList(query2));
				}
				if (!list.isEmpty()) {
					ranklist.add(list);
				}
			}
		} else {
			final String driverquery = "SELECT distinct dr.driverid  FROM driver dr, customeraffiliate af  WHERE  dr.affiliateid=af.affiliateid and dr.status = '1' and dr.transporterid="
					+ transporterid + " and dr.affiliateid=" + affiliateid + " and af.customerid=" + clientid
					+ " and date_part('month', age(concat(" + endyear + ",'-'," + endmonth + ",'-',"
					+ new Date(endyear, endmonth, 0).getDate()
					+ ")::timestamp,dr.createdon)) >= (select sc.minsenioritydriver from scp sc where sc.clientid="
					+ clientid + " and sc.affiliateid=" + affiliateid + " and sc.status=1)";
			final List<Map<String, Object>> drivers = this.jdbcTemplate.queryForList(driverquery);
			for (final Map<String, Object> driverdet : drivers) {
				final int driverId = Utils.castIntegerObject(driverdet.get("driverid"));
				final Double[] remainingpoint2 = new Double[Month.length];
				for (int k = 0; k < Month.length; ++k) {
					final String query3 = "select coalesce(SUM(totalremainingpoints),0) as totalremainingpoints from sp_reward_ranking_driver_month("
							+ clientid + "," + affiliateid + "," + transporterid + "," + driverId + "," + Month[k][1]
							+ "," + Month[k][1] + "," + Month[k][0] + "," + Month[k][0] + ")";
					remainingpoint2[k] = (Double) this.jdbcTemplate.queryForObject(query3, (Class) Double.class);
				}
				Arrays.sort(remainingpoint2);
				String query4 = "select clientid,clientname,affiliateid,affiliatename,transporterid,transportername,driverid,drivername,totalremainingpoints,totalsubstractedpoints,totaldistance,noofaccident,driverrate,driverredlimit,initialpoints ,"
						+ remainingpoint2[0] + " minpoints,0 dateofocurrence from sp_reward_ranking_driver(" + clientid
						+ "," + affiliateid + "," + transporterid + "," + driverId + "," + startmonth + "," + endmonth
						+ "," + startyear + "," + endyear + ")";
				final List<Map<String, Object>> list2 = this.jdbcTemplate.queryForList(query4);
				for (final Map map2 : list2) {
					query4 = "select mr.dateofocurrence as dateofocurrence from manualaccicp mr join driver dr on dr.driverid=mr.driverid and (dr.driverid="
							+ driverId + " ) and dr.transporterid=" + transporterid
							+ " and date_part('month', age(concat(" + endyear + ",'-'," + endmonth + ",'-',"
							+ new Date(endyear, endmonth, 0).getDate()
							+ ")::timestamp,dr.createdon)) >= (select minsenioritydriver from scp sc where sc.clientid="
							+ clientid + " and sc.affiliateid=" + affiliateid
							+ " and sc.status=1) and mr.status=1 and mr.transporterid=dr.transporterid and mr.affiliateid=dr.affiliateid and mr.clientid="
							+ clientid + " and date_part('month', mr.dateofocurrence) between " + startmonth + " and "
							+ endmonth + " and date_part('year', mr.dateofocurrence) between " + startyear + " and "
							+ endyear;
					map2.put("dateofocurrence", this.jdbcTemplate.queryForList(query4));
				}
				if (!list2.isEmpty()) {
					ranklist.add(list2);
				}
			}
		}
		final Comparator<ArrayList<Map<String, BigDecimal>>> myComparator = new Comparator<ArrayList<Map<String, BigDecimal>>>() {
			@Override
			public int compare(final ArrayList<Map<String, BigDecimal>> first,
					final ArrayList<Map<String, BigDecimal>> second) {
				final BigDecimal firstValue = first.get(0).get("driverrate");
				final BigDecimal secondValue = second.get(0).get("driverrate");
				final BigDecimal avg1Value = new BigDecimal(
						Double.valueOf(Double.parseDouble("" + first.get(0).get("minpoints"))));
				final BigDecimal avg2Value = new BigDecimal(
						Integer.valueOf(Integer.parseInt("" + first.get(0).get("driverredlimit"))));
				if (avg1Value.compareTo(avg2Value) > 0) {
					return firstValue.compareTo(secondValue);
				}
				return secondValue.compareTo(firstValue);
			}
		};
		Collections.sort(ranklist, myComparator);
		return ranklist;
	}
}
