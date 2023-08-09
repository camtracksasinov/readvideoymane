//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.camtrack.config.AppConstants;
import com.camtrack.config.CommonUtil;
import com.camtrack.config.Utils;
import com.camtrack.reports.bean.WeekDaysBean;

@Repository("toprankingdao")
@PropertySource({ "classpath:sqlqueries.properties" })
public class TopRankingDaoImpl implements TopRankingDaoInterface {
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List getVehicles(final int transporterid) {
		final String query = this.environment.getRequiredProperty("transporter.getvehiclesundertransporter");
		return this.jdbcTemplate.queryForList(query, new Object[] { transporterid });
	}

	@Override
	public List showTopRankingReport(final int customerid, final int affiliateid, final int transporterid,
			final int vehicleid, final int param, final String exceptionlevels, final String startDate,
			final String endDate) {
		double recordingWeight = 1.0;
		double alertWeight = 1.0;
		double alarmWeight = 1.0;
		if (exceptionlevels.contains("1") && exceptionlevels.contains("2") && exceptionlevels.contains("3")) {
			recordingWeight = 0.15;
			alertWeight = 0.28;
			alarmWeight = 0.57;
		} else if (exceptionlevels.contains("2") && exceptionlevels.contains("3")) {
			recordingWeight = 0.0;
			alertWeight = 0.33;
			alarmWeight = 0.67;
		} else if (exceptionlevels.contains("1") && exceptionlevels.contains("3")) {
			recordingWeight = 0.21;
			alertWeight = 0.0;
			alarmWeight = 0.79;
		} else if (exceptionlevels.contains("1") && exceptionlevels.contains("2")) {
			recordingWeight = 0.35;
			alertWeight = 0.65;
			alarmWeight = 0.0;
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
		String query = "";
		if (param == 1) {
			query = "select * from sp_toprankingreport100kms_latest(" + customerid + "," + affiliateid + ","
					+ transporterid + "," + vehicleid + ",ARRAY[1],ARRAY[" + exceptionlevels + "],'" + startDate + "','"
					+ endDate + "'," + recordingWeight + "," + alertWeight + "," + alarmWeight + ")";
		} else if (param == 2) {
			query = "select * from sp_toprankingreport100kms_latest(" + customerid + "," + affiliateid + ","
					+ transporterid + "," + vehicleid + ",ARRAY[2,3],ARRAY[" + exceptionlevels + "],'" + startDate
					+ "','" + endDate + "'," + recordingWeight + "," + alertWeight + "," + alarmWeight + ")";
		} else if (param == 3) {
			query = "select * from sp_toprankingreport100hrs_new(" + customerid + "," + affiliateid + ","
					+ transporterid + "," + vehicleid + ",5,ARRAY[" + exceptionlevels + "],'" + startDate + "','"
					+ endDate + "')";
		} else if (param == 4) {
			query = "select * from sp_toprankingreport100hrs_new(" + customerid + "," + affiliateid + ","
					+ transporterid + "," + vehicleid + ",4,ARRAY[" + exceptionlevels + "],'" + startDate + "','"
					+ endDate + "')";
		} else if (param == 5) {
			query = "select * from sp_toprankingreport100hrs_new(" + customerid + "," + affiliateid + ","
					+ transporterid + "," + vehicleid + ",6,ARRAY[" + exceptionlevels + "],'" + startDate + "','"
					+ endDate + "')";
		} else if (param == 6) {
			query = "select * from sp_toprankingreport100hrs_new(" + customerid + "," + affiliateid + ","
					+ transporterid + "," + vehicleid + ",7,ARRAY[" + exceptionlevels + "],'" + startDate + "','"
					+ endDate + "')";
		} else if (param == 7 || param == 8) {
			final List weeklyList = new ArrayList();
			final List<WeekDaysBean> dateRanges = CommonUtil.getWeekDaysBetweenDates(Date.valueOf(startDate),
					Date.valueOf(endDate));
			if (dateRanges.size() > 0) {
				if (param == 8) {
					final List<Map<String, Object>> tempList = new ArrayList<>();
					if (vehicleid == 0) {
						for (final WeekDaysBean weekDaysBean : dateRanges) {
							String driversqry = "select * from sp_get_driver_ids_from_cd_of_wrt_for_topranking("
									+ vehicleid + "," + transporterid + "," + affiliateid + "," + customerid + ",'"
									+ weekDaysBean.getStartDate() + "','" + weekDaysBean.getEndDate() + "')";
							driversqry += " order by affiliateid,driverid";
							System.out.println(driversqry);
							final List<Map<String, Object>> drivers = this.jdbcTemplate.queryForList(driversqry);
							for (final Map<String, Object> driverDet : drivers) {
								final int vehId = Utils.castIntegerObject(driverDet.get("vehicleid"));
								final int driId = Utils.castIntegerObject(driverDet.get("driverid"));
								final int affId = Utils.castIntegerObject(driverDet.get("affiliateid"));
								final int transId = Utils.castIntegerObject(driverDet.get("transporterid"));
								final String thresholdQry = "SELECT thresholdlimit,recordingthreshold,alertthreshold,alarmthreshold FROM parameterconfig  WHERE parametertypeid = ? AND status = '1' AND clientaffiliateid = ?";
								System.out.println(thresholdQry);
								Map<String, Object> thresholdDet = null;
								try {
									thresholdDet = this.jdbcTemplate.queryForMap(thresholdQry,
											new Object[] { AppConstants.WEEKLYREST, affId });
									if (thresholdDet == null) {
										continue;
									}
								} catch (Exception e) {
									continue;
								}
								final double threshold = Double
										.parseDouble(thresholdDet.get("thresholdlimit").toString());
								final double recordingThreshold = Double
										.parseDouble(thresholdDet.get("recordingthreshold").toString());
								final double alertThreshold = Double
										.parseDouble(thresholdDet.get("alertthreshold").toString());
								final double alarmThreshold = Double
										.parseDouble(thresholdDet.get("alarmthreshold").toString());
								query = "select " + affId + " affiliateid," + transId + " transporterid," + driId
										+ " driverid,vehiclename as vehicledesc,* from sp_weeklyrestdetails(" + vehId
										+ "," + driId + ",'" + weekDaysBean.getStartDate() + "','"
										+ weekDaysBean.getEndDate() + "')";
								System.out.println(query);
								final List<Map<String, Object>> restList = this.jdbcTemplate.queryForList(query);
								Map longestRest = null;
								if (restList.size() > 0) {
									double longestRestDur = 0.0;
									double totalduration = 0.0;
									for (final Map det : restList) {
										if (longestRestDur < Double.parseDouble(det.get("totalduration").toString())) {
											longestRestDur = Double.parseDouble(det.get("totalduration").toString());
											longestRest = det;
										}
										totalduration += Double.parseDouble(det.get("totalduration").toString());
									}
									final String totalddDurQuery = "select case when totdurn=0 then 0.000001 else totdurn end as totdurn from sp_get_totduration_exp_new('"
											+ weekDaysBean.getStartDate() + "', '" + weekDaysBean.getEndDate() + "', "
											+ vehId + ",0," + transId + " ," + affId + ", 8,ARRAY[" + exceptionlevels
											+ "] )";
									System.out.println(totalddDurQuery);
									final double totalD = (double) this.jdbcTemplate.queryForObject(totalddDurQuery,
											(Class) Double.class);
									double exceededTim = 0.0;
									double exceptionvalue = 0.0;
									if (totalD > 1.0E-6) {
										exceededTim = threshold - longestRestDur;
										exceptionvalue = exceededTim * 100.0 / totalD;
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
									} else {
										if (exceededTim < recordingThreshold) {
											continue;
										}
										longestRest.put("exceptionvalue", exceptionvalue);
										longestRest.put("exceededDurationNew", exceededTim);
										if (exceptionlevels != null && !exceptionlevels.equals("0")
												&& !exceptionlevels.contains("1")) {
											continue;
										}
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
						for (final WeekDaysBean weekDaysBean : dateRanges) {
							String driversqry = "select * from sp_get_driver_ids_from_cd_of_wrt_for_topranking("
									+ vehicleid + "," + transporterid + "," + affiliateid + "," + customerid + ",'"
									+ weekDaysBean.getStartDate() + "','" + weekDaysBean.getEndDate() + "')";
							driversqry += " order by affiliateid,driverid";
							System.out.println(driversqry);
							final List<Map<String, Object>> drivers = this.jdbcTemplate.queryForList(driversqry);
							for (final Map<String, Object> driverDet : drivers) {
								final int vehId = Utils.castIntegerObject(driverDet.get("vehicleid"));
								final int driId = Utils.castIntegerObject(driverDet.get("driverid"));
								final int affId = Utils.castIntegerObject(driverDet.get("affiliateid"));
								final int transId = Utils.castIntegerObject(driverDet.get("transporterid"));
								final String thresholdQry = "SELECT thresholdlimit,recordingthreshold,alertthreshold,alarmthreshold FROM parameterconfig  WHERE parametertypeid = ? AND status = '1' AND clientaffiliateid = ?";
								Map<String, Object> thresholdDet = null;
								try {
									thresholdDet = this.jdbcTemplate.queryForMap(thresholdQry,
											new Object[] { AppConstants.WEEKLYREST, affId });
									if (thresholdDet == null) {
										continue;
									}
								} catch (Exception e) {
									continue;
								}
								final double threshold = Double
										.parseDouble(thresholdDet.get("thresholdlimit").toString());
								final double recordingThreshold = Double
										.parseDouble(thresholdDet.get("recordingthreshold").toString());
								final double alertThreshold = Double
										.parseDouble(thresholdDet.get("alertthreshold").toString());
								final double alarmThreshold = Double
										.parseDouble(thresholdDet.get("alarmthreshold").toString());
								query = "select " + affId + " affiliateid," + transId + " transporterid," + driId
										+ " driverid,vehiclename as vehicledesc,* from sp_weeklyrestdetails(" + vehId
										+ "," + driId + ",'" + weekDaysBean.getStartDate() + "','"
										+ weekDaysBean.getEndDate() + "')";
								final List<Map<String, Object>> restList = this.jdbcTemplate.queryForList(query);
								Map longestRest = null;
								if (restList.size() > 0) {
									double longestRestDur = 0.0;
									double totalduration = 0.0;
									for (final Map det : restList) {
										if (longestRestDur < Double.parseDouble(det.get("totalduration").toString())) {
											longestRestDur = Double.parseDouble(det.get("totalduration").toString());
											longestRest = det;
										}
										totalduration += Double.parseDouble(det.get("totalduration").toString());
									}
									final String totalddDurQuery = "select case when totdurn=0 then 0.000001 else totdurn end as totdurn from sp_get_totduration_exp_new('"
											+ weekDaysBean.getStartDate() + "', '" + weekDaysBean.getEndDate() + "', "
											+ vehId + ",0," + transId + " ," + affId + ", 8,ARRAY[" + exceptionlevels
											+ "] )";
									final double totalD = (double) this.jdbcTemplate.queryForObject(totalddDurQuery,
											(Class) Double.class);
									double exceededTim = 0.0;
									double exceptionvalue = 0.0;
									if (totalD > 1.0E-6) {
										exceededTim = threshold - longestRestDur;
										exceptionvalue = exceededTim * 100.0 / totalD;
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
									} else {
										if (exceededTim < recordingThreshold) {
											continue;
										}
										longestRest.put("exceptionvalue", exceptionvalue);
										if (exceptionlevels != null && !exceptionlevels.equals("0")
												&& !exceptionlevels.contains("1")) {
											continue;
										}
									}
									longestRest.put("exceededDurationNew", exceededTim);
									longestRest.put("totalD", totalD);
									longestRest.put("totalDurSP", totalduration);
									longestRest.put("exceedT", exceededTim);
									tempList.add(longestRest);
								}
							}
						}
					}
					if (tempList.size() > 0) {
						final Map<Integer, Map<String, Object>> detailsMap = new HashMap<>();
						if (vehicleid > 0 || transporterid > 0) {
							for (final Map<String, Object> map : tempList) {
								if (detailsMap.get((int) map.get("driverid")) != null) {
									final Map<String, Object> existing = detailsMap.get((int) map.get("driverid"));
									final double total = Utils.castDoubleObject(existing.get("exceptionvalue"));
									final double driverwiseValue = Utils.castDoubleObject(map.get("exceptionvalue"));
									existing.put("exceptionvalue", total + driverwiseValue);
								} else {
									detailsMap.put((int) map.get("driverid"), map);
								}
							}
						} else if (affiliateid > 0) {
							for (final Map<String, Object> map : tempList) {
								if (detailsMap.get((int) map.get("transporterid")) != null) {
									final Map<String, Object> existing = detailsMap.get((int) map.get("transporterid"));
									final double exceededDurationNew = Utils
											.castDoubleObject(existing.get("exceededDurationNew"));
									final double exceededDuration = Utils
											.castDoubleObject(map.get("exceededDurationNew"));
									final double totalExceedTime = Utils
											.castDoubleObject(exceededDurationNew + exceededDuration);
									final double totalDur = Utils.castDoubleObject(existing.get("totalD"));
									final double TotalD = Utils.castDoubleObject(map.get("totalD"));
									final double totaldurationsum = totalDur + TotalD;
									existing.put("totalD", totaldurationsum);
									existing.put("exceededDurationNew", totalExceedTime);
									existing.put("exceptionvalue", totalExceedTime * 100.0 / totaldurationsum);
								} else {
									detailsMap.put((int) map.get("transporterid"), map);
								}
							}
						} else {
							for (final Map<String, Object> map : tempList) {
								if (detailsMap.get((int) map.get("affiliateid")) != null) {
									final Map<String, Object> existing = detailsMap.get((int) map.get("affiliateid"));
									final double exceededDurationNew = Utils
											.castDoubleObject(existing.get("exceededDurationNew"));
									final double exceededDuration = Utils
											.castDoubleObject(map.get("exceededDurationNew"));
									final double totalExceedTime = exceededDurationNew + exceededDuration;
									final double totalDur = Utils.castDoubleObject(existing.get("totalD"));
									final double TotalD = Utils.castDoubleObject(map.get("totalD"));
									final double totaldurationsum = totalDur + TotalD;
									existing.put("totalD", totaldurationsum);
									existing.put("exceededDurationNew", totalExceedTime);
									existing.put("exceptionvalue", totalExceedTime * 100.0 / totaldurationsum);
								} else {
									detailsMap.put((int) map.get("affiliateid"), map);
								}
							}
						}
						weeklyList.addAll(detailsMap.values());
						Collections.sort(weeklyList, new Comparator<Map<String, Object>>() {
							@Override
							public int compare(Map<String, Object> e1, Map<String, Object> e2) {
								return ((Double) e2.get("exceptionvalue")).compareTo((Double) e1.get("exceptionvalue"));
							}
						});
					}
				}
				if (param == 7) {
					final List<Map<String, Object>> tempList = new ArrayList<>();
					for (final WeekDaysBean weekDaysBean : dateRanges) {
						String driversqry = "select * from sp_get_driver_ids_from_cd_of_wrt_for_topranking(" + vehicleid
								+ "," + transporterid + "," + affiliateid + "," + customerid + ",'" + startDate + "','"
								+ endDate + "')";
						driversqry += " order by affiliateid,driverid";
						System.out.println(driversqry);
						final List<Map<String, Object>> drivers = this.jdbcTemplate.queryForList(driversqry);
						if (vehicleid == 0) {
							for (final Map<String, Object> driverDet : drivers) {
								final int vehId = Utils.castIntegerObject(driverDet.get("vehicleid"));
								final int driId = Utils.castIntegerObject(driverDet.get("driverid"));
								final int affId = Utils.castIntegerObject(driverDet.get("affiliateid"));
								final int transId = Utils.castIntegerObject(driverDet.get("transporterid"));
								final String thresholdQry = "SELECT thresholdlimit,recordingthreshold,alertthreshold,alarmthreshold FROM parameterconfig  WHERE parametertypeid = ? AND status = '1' AND clientaffiliateid = ?";
								Map<String, Object> thresholdDet = null;
								try {
									thresholdDet = this.jdbcTemplate.queryForMap(thresholdQry,
											new Object[] { AppConstants.WEEKLYDRIVE, affId });
									if (thresholdDet == null) {
										continue;
									}
								} catch (Exception e) {
									continue;
								}
								final double threshold = Double
										.parseDouble(thresholdDet.get("thresholdlimit").toString());
								final double recordingThreshold = Double
										.parseDouble(thresholdDet.get("recordingthreshold").toString());
								final double alertThreshold = Double
										.parseDouble(thresholdDet.get("alertthreshold").toString());
								final double alarmThreshold = Double
										.parseDouble(thresholdDet.get("alarmthreshold").toString());
								query = "select " + affId + " affiliateid," + transId + " transporterid," + driId
										+ " driverid,vehiclename as vehicledesc,* from sp_weeklydrivedetails(" + vehId
										+ "," + driId + ",'" + weekDaysBean.getStartDate() + "','"
										+ weekDaysBean.getEndDate() + "')  order by startdatetime::timestamp asc";
								final List<Map<String, Object>> restList = this.jdbcTemplate.queryForList(query);
								Map weeklyDrive = null;
								if (restList.size() > 0) {
									double totalduration2 = 0.0;
									for (final Map det2 : restList) {
										totalduration2 += Double.parseDouble(det2.get("totalduration").toString());
										weeklyDrive = det2;
									}
									final String totalddDurQuery2 = "select case when totdurn=0 then 0.000001 else totdurn end as totdurn from sp_get_totduration_exp_new('"
											+ weekDaysBean.getStartDate() + "', '" + weekDaysBean.getEndDate() + "', "
											+ vehId + ",0," + transId + " ," + affId + ", 9,ARRAY[" + exceptionlevels
											+ "] )";
									final double totalD2 = (double) this.jdbcTemplate.queryForObject(totalddDurQuery2,
											(Class) Double.class);
									double exceededDuration2 = 0.0;
									double exceptionvalue2 = 0.0;
									if (totalD2 > 1.0E-6) {
										exceededDuration2 = totalD2 - threshold;
										exceptionvalue2 = exceededDuration2 * 100.0 / totalD2;
									}
									weeklyDrive.put("totalD", totalD2);
									weeklyDrive.put("totalDurSP", totalduration2);
									weeklyDrive.put("exceededDurationNew", exceededDuration2);
									if (exceededDuration2 >= alarmThreshold) {
										weeklyDrive.put("exceptionvalue", exceptionvalue2);
										weeklyDrive.put("exceededDurationNew", exceededDuration2);
										if (exceptionlevels != null && !exceptionlevels.equals("0")
												&& !exceptionlevels.contains("3")) {
											continue;
										}
									} else if (exceededDuration2 >= alertThreshold) {
										weeklyDrive.put("exceptionvalue", exceptionvalue2);
										weeklyDrive.put("exceededDurationNew", exceededDuration2);
										if (exceptionlevels != null && !exceptionlevels.equals("0")
												&& !exceptionlevels.contains("2")) {
											continue;
										}
									} else {
										if (exceededDuration2 < recordingThreshold) {
											continue;
										}
										weeklyDrive.put("exceptionvalue", exceptionvalue2);
										weeklyDrive.put("exceededDurationNew", exceededDuration2);
										if (exceptionlevels != null && !exceptionlevels.equals("0")
												&& !exceptionlevels.contains("1")) {
											continue;
										}
									}
									if (exceptionvalue2 <= 0.0 || exceededDuration2 <= 0.0) {
										continue;
									}
									tempList.add(weeklyDrive);
								}
							}
						} else {
							for (final Map<String, Object> driverDet : drivers) {
								final int vehId = Utils.castIntegerObject(driverDet.get("vehicleid"));
								final int driId = Utils.castIntegerObject(driverDet.get("driverid"));
								final int affId = Utils.castIntegerObject(driverDet.get("affiliateid"));
								final int transId = Utils.castIntegerObject(driverDet.get("transporterid"));
								final String thresholdQry = "SELECT thresholdlimit,recordingthreshold,alertthreshold,alarmthreshold FROM parameterconfig  WHERE parametertypeid = ? AND status = '1' AND clientaffiliateid = ?";
								Map<String, Object> thresholdDet = null;
								try {
									thresholdDet = this.jdbcTemplate.queryForMap(thresholdQry,
											new Object[] { AppConstants.WEEKLYDRIVE, affId });
									if (thresholdDet == null) {
										continue;
									}
								} catch (Exception e) {
									continue;
								}
								final double threshold = Double
										.parseDouble(thresholdDet.get("thresholdlimit").toString());
								final double recordingThreshold = Double
										.parseDouble(thresholdDet.get("recordingthreshold").toString());
								final double alertThreshold = Double
										.parseDouble(thresholdDet.get("alertthreshold").toString());
								final double alarmThreshold = Double
										.parseDouble(thresholdDet.get("alarmthreshold").toString());
								query = "select " + affId + " affiliateid," + transId + " transporterid," + driId
										+ " driverid,vehiclename as vehicledesc,* from sp_weeklydrivedetails(" + vehId
										+ "," + driId + ",'" + weekDaysBean.getStartDate() + "','"
										+ weekDaysBean.getEndDate() + "')  order by startdatetime::timestamp asc";
								final List<Map<String, Object>> restList = this.jdbcTemplate.queryForList(query);
								Map weeklyDrive = null;
								if (restList.size() > 0) {
									double totalduration2 = 0.0;
									for (final Map det2 : restList) {
										totalduration2 += Double.parseDouble(det2.get("totalduration").toString());
										weeklyDrive = det2;
									}
									final String totalddDurQuery2 = "select case when totdurn=0 then 0.000001 else totdurn end as totdurn from sp_get_totduration_exp_new('"
											+ weekDaysBean.getStartDate() + "', '" + weekDaysBean.getEndDate() + "', "
											+ vehId + ",0," + transId + " ," + affId + ", 9,ARRAY[" + exceptionlevels
											+ "] )";
									final double totalD2 = (double) this.jdbcTemplate.queryForObject(totalddDurQuery2,
											(Class) Double.class);
									double exceededDuration2 = 0.0;
									double exceptionvalue2 = 0.0;
									if (totalD2 > 1.0E-6) {
										exceededDuration2 = totalD2 - threshold;
										exceptionvalue2 = exceededDuration2 * 100.0 / totalD2;
									}
									weeklyDrive.put("totalD", totalD2);
									weeklyDrive.put("exceededDurationNew", exceededDuration2);
									weeklyDrive.put("totalDurSP", totalduration2);
									if (exceededDuration2 >= alarmThreshold) {
										weeklyDrive.put("exceptionvalue", exceptionvalue2);
										if (exceptionlevels != null && !exceptionlevels.equals("0")
												&& !exceptionlevels.contains("3")) {
											continue;
										}
									} else if (exceededDuration2 >= alertThreshold) {
										weeklyDrive.put("exceptionvalue", exceptionvalue2);
										if (exceptionlevels != null && !exceptionlevels.equals("0")
												&& !exceptionlevels.contains("2")) {
											continue;
										}
									} else {
										if (exceededDuration2 < recordingThreshold) {
											continue;
										}
										weeklyDrive.put("exceptionvalue", exceptionvalue2);
										if (exceptionlevels != null && !exceptionlevels.equals("0")
												&& !exceptionlevels.contains("1")) {
											continue;
										}
									}
									if (exceptionvalue2 <= 0.0 || exceededDuration2 <= 0.0) {
										continue;
									}
									tempList.add(weeklyDrive);
								}
							}
						}
					}
					if (tempList.size() > 0) {
						final Map<Integer, Map<String, Object>> detailsMap = new HashMap<>();
						if (vehicleid > 0 || transporterid > 0) {
							for (final Map<String, Object> map : tempList) {
								if (detailsMap.get((int) map.get("driverid")) != null) {
									final Map<String, Object> existing = detailsMap.get((int) map.get("driverid"));
									final double total = Utils.castDoubleObject(existing.get("exceptionvalue"));
									final double driverwiseValue = Utils.castDoubleObject(map.get("exceptionvalue"));
									existing.put("exceptionvalue", total + driverwiseValue);
								} else {
									detailsMap.put((int) map.get("driverid"), map);
								}
							}
						} else if (affiliateid > 0) {
							for (final Map<String, Object> map : tempList) {
								if (detailsMap.get((int) map.get("transporterid")) != null) {
									final Map<String, Object> existing = detailsMap.get((int) map.get("transporterid"));
									final double exceededDurationNew = Utils
											.castDoubleObject(existing.get("exceededDurationNew"));
									final double exceededDuration = Utils
											.castDoubleObject(map.get("exceededDurationNew"));
									final double totalExceedTime = exceededDurationNew + exceededDuration;
									final double totalDur = Utils.castDoubleObject(existing.get("totalD"));
									final double TotalD = Utils.castDoubleObject(map.get("totalD"));
									final double totaldurationsum = totalDur + TotalD;
									existing.put("totalD", totaldurationsum);
									existing.put("exceededDurationNew", totalExceedTime);
									existing.put("exceptionvalue", totalExceedTime * 100.0 / totaldurationsum);
								} else {
									detailsMap.put((int) map.get("transporterid"), map);
								}
							}
						} else {
							for (final Map<String, Object> map : tempList) {
								if (detailsMap.get((int) map.get("affiliateid")) != null) {
									final Map<String, Object> existing = detailsMap.get((int) map.get("affiliateid"));
									final double exceededDurationNew = Utils
											.castDoubleObject(existing.get("exceededDurationNew"));
									final double exceededDuration = Utils
											.castDoubleObject(map.get("exceededDurationNew"));
									final double totalExceedTime = exceededDurationNew + exceededDuration;
									final double totalDur = Utils.castDoubleObject(existing.get("totalD"));
									final double TotalD = Utils.castDoubleObject(map.get("totalD"));
									final double totaldurationsum = totalDur + TotalD;
									existing.put("totalD", totaldurationsum);
									existing.put("exceededDurationNew", totalExceedTime);
									existing.put("exceptionvalue", totalExceedTime * 100.0 / totaldurationsum);
								} else {
									detailsMap.put((int) map.get("affiliateid"), map);
								}
							}
						}
						weeklyList.addAll(detailsMap.values());
						Collections.sort(weeklyList, new Comparator<Map<String, Object>>() {
							@Override
							public int compare(Map<String, Object> e1, Map<String, Object> e2) {
								return ((Double) e2.get("exceptionvalue")).compareTo((Double) e1.get("exceptionvalue"));
							}
						});
					}
				}
			}
			return weeklyList;
		}
		query += " where exceptionvalue>0 order by exceptionvalue desc";
		return this.jdbcTemplate.queryForList(query);
	}
}
