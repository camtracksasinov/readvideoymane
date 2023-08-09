//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.camtrack.affiliate.repository.CustomeraffiliateRepository;
import com.camtrack.config.Utils;
import com.camtrack.entities.User;
import com.camtrack.reports.bean.SafetyRanking;
import com.camtrack.reports.dao.TopRankingDaoInterface;
import com.camtrack.user.repository.UserightsRepository;
import com.camtrack.user.repository.UsersRepository;

@Service("toprankingservice")
@PropertySource({ "classpath:sqlqueries.properties" })
@CacheConfig(cacheNames = "toprankingcaches")
public class TopRankingServiceImpl implements TopRankingServiceInterface {
	@Value("${userrrole.affiliateid}")
	private Integer affiliateid;
	@Value("${userrrole.clientid}")
	private Integer clientid;
	@Autowired
	CustomeraffiliateRepository cusaffR;
	@Autowired
	TopRankingDaoInterface dao;
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Value("${userrrole.superadminid}")
	private Integer superadminid;
	@Value("${userrrole.transporterid}")
	private Integer transporterid;
	@Autowired
	UserightsRepository userRR;
	@Autowired
	UsersRepository usersR;

	@Override
	@Cacheable(value = "safetyranking", key = "new org.springframework.cache.interceptor.SimpleKey(#user.userid, #codeclient,#codeafiliate,#transporter,#codevehicle,#codedriver,#datedebut,#datefin,#listidtypeexception,#record,#alert,#alarm)")
	public SafetyRanking safetyranking(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codevehicle, final String codedriver, final String datedebut,
			final String datefin, final String listidtypeexception, Boolean record, Boolean alert, Boolean alarm) {
		double recordingWeight = 1.0;
		double alertWeight = 1.0;
		double alarmWeight = 1.0;
		final int k = 0;
		String recordalertalarm = "";
		final List<Integer> list = new ArrayList<>();
		if (!Objects.isNull(record) && record) {
			list.add(1);
		} else {
			record = false;
		}
		if (!Objects.isNull(alert) && alert) {
			list.add(2);
		} else {
			alert = false;
		}
		if (!Objects.isNull(alarm) && alarm) {
			list.add(3);
		} else {
			alarm = false;
		}
		if (list.isEmpty()) {
			return new SafetyRanking(-1, (List) null);
		}
		recordalertalarm = list.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(",", "(", ")"));
		final List<Integer> listypexception = new ArrayList<>();
		String sqlin2 = "";
		String andsql = "";
		String andsql2 = "";
		String andsql3 = "";
		String completeRequest = "";
		String allconstructsqlin = "";
		List<Integer> lists = new ArrayList<>();
		andsql2 = null;
		andsql3 = null;
		if (Objects.isNull(listidtypeexception) || listidtypeexception.equalsIgnoreCase("0")
				|| listidtypeexception.isEmpty()) {
			if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
				andsql = this.environment.getRequiredProperty("exceptiontypeid.listexceptiontype");
				andsql2 = "1,2,3";
				andsql3 = "4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40";
			} else {
				andsql = this.environment.getRequiredProperty("exceptiontypeid.listidfromuserightsbyuserid");
				andsql = andsql.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
				lists = this.userRR.findAllParametertypeIdOfUserId(user.getUserid());
			}
		} else {
			andsql = listidtypeexception;
			lists = Arrays.stream(listidtypeexception.split(",")).map(Integer::parseInt).collect(Collectors.toList());
		}
		int k2 = 0;
		int k3 = 0;
		for (final Integer integer : lists) {
			if (integer == 1 || integer == 2 || integer == 3) {
				if (k2 == 0) {
					andsql2 = "" + integer;
				} else {
					andsql2 = andsql2 + "," + integer;
				}
				++k2;
			} else {
				if (integer <= 3) {
					continue;
				}
				if (k3 == 0) {
					andsql3 = "" + integer;
				} else {
					andsql3 = andsql3 + "," + integer;
				}
				++k3;
			}
		}
		final Date date01 = Utils.StringToDate("", datedebut, "yyyy-MM-dd");
		Date date2 = Utils.StringToDate("", datefin, "yyyy-MM-dd");
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date2);
		calendar.add(5, 1);
		date2 = calendar.getTime();
		if (Objects.isNull(date2) || Objects.isNull(date01)) {
			return new SafetyRanking(-2, (List) null);
		}
		final String minisql = " bigexception.level in " + recordalertalarm + " and bigexception.exceptiontype in ("
				+ andsql + ") and bigexception.startdatetime >= '" + Utils.DateFormat(date01, "yyyy-MM-dd")
				+ "' and bigexception.startdatetime < '" + Utils.DateFormat(date2, "yyyy-MM-dd") + "'";
		String minisql2 = null;
		if (!Objects.isNull(andsql2)) {
			minisql2 = " bigexception.level in " + recordalertalarm + " and bigexception.exceptiontype in (" + andsql2
					+ ") and bigexception.startdatetime >= '" + Utils.DateFormat(date01, "yyyy-MM-dd")
					+ "' and bigexception.startdatetime < '" + Utils.DateFormat(date2, "yyyy-MM-dd") + "'";
		}
		String minisql3 = null;
		if (!Objects.isNull(andsql3)) {
			minisql3 = " bigexception.level in " + recordalertalarm + " and bigexception.exceptiontype in (" + andsql3
					+ ") and bigexception.startdatetime >= '" + Utils.DateFormat(date01, "yyyy-MM-dd")
					+ "' and bigexception.startdatetime < '" + Utils.DateFormat(date2, "yyyy-MM-dd") + "'";
		}
		if (record && alert && alarm) {
			recordingWeight = 0.15;
			alertWeight = 0.28;
			alarmWeight = 0.57;
		} else if (alert && alarm) {
			recordingWeight = 0.0;
			alertWeight = 0.33;
			alarmWeight = 0.67;
		} else if (record && alarm) {
			recordingWeight = 0.21;
			alertWeight = 0.0;
			alarmWeight = 0.79;
		} else if (record && alert) {
			recordingWeight = 0.35;
			alertWeight = 0.65;
			alarmWeight = 0.0;
		} else if (record) {
			recordingWeight = 1.0;
			alertWeight = 0.0;
			alarmWeight = 0.0;
		} else if (alert) {
			recordingWeight = 0.0;
			alertWeight = 1.0;
			alarmWeight = 0.0;
		} else if (alarm) {
			recordingWeight = 0.0;
			alertWeight = 0.0;
			alarmWeight = 1.0;
		}
		String completeRequest2 = "";
		String completeRequest3 = "";
		String completeRequest4 = "";
		String completeRequest5 = "";
		final List<Map<String, Object>> allresukt2 = new ArrayList<>();
		String allconstructsqlin2 = "";
		List<Map<String, Object>> allresukt3 = null;
		List<Map<String, Object>> allresukt4 = null;
		List<Map<String, Object>> allresukt5 = null;
		List<Map<String, Object>> allresukt6 = null;
		List<Map<String, Object>> allresukt7 = null;
		List<Map<String, Object>> resultlist = null;
		final List<Integer> listdriver = new ArrayList<>();
		final List<Integer> listvehicle = new ArrayList<>();
		final List<Integer> listransporter = new ArrayList<>();
		final List<Integer> listafiliate = new ArrayList<>();
		List<Integer> listclient = new ArrayList<>();
		final List<Integer> result = new ArrayList<>();
		Integer typeInfos = -1;
		try {
			Label_2603: {
				if (Objects.isNull(codedriver) || codedriver.equalsIgnoreCase("")) {
					if (Objects.isNull(codevehicle) || codevehicle.equalsIgnoreCase("")) {
						if (Objects.isNull(transporter) || transporter.equalsIgnoreCase("")) {
							if (Objects.isNull(codeafiliate) || codeafiliate.equalsIgnoreCase("")) {
								if (Objects.isNull(codeclient) || codeclient.equalsIgnoreCase("")) {
									if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
										listclient = this.usersR.findAllActiveCustomerId();
										sqlin2 = this.environment.getRequiredProperty("clientid.listidclient");
									} else {
										try {
											final List<Integer> listcostomerid = this.userRR
													.findAllCustomerIdOfUserId(user.getCustomerid());
											if (!Objects.isNull(listcostomerid) && !listcostomerid.isEmpty()) {
												listclient.addAll(listcostomerid);
											}
											sqlin2 = this.environment
													.getRequiredProperty("clientid.listidfromuserightsbyuserid");
											sqlin2 = sqlin2.replaceFirst(Utils.UserIDSTRINGToREPLACE,
													user.getUserid() + "");
										} catch (Exception ex) {
										}
									}
									typeInfos = 1;
								} else {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid) {
										if (user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
											break Label_2603;
										}
									}
									try {
										if (codeclient.equalsIgnoreCase("0")) {
											listclient = new ArrayList<>();
											if (user.getReelrole().getIdtyperole()
													.getUserroleid() == this.superadminid) {
												listclient = this.usersR.findAllActiveCustomerId();
												sqlin2 = this.environment.getRequiredProperty("clientid.listidclient");
											} else {
												listclient = this.userRR
														.findAllReelCustomerIdOfUserId(user.getUserid());
												sqlin2 = this.environment
														.getRequiredProperty("clientid.listidfromuserightsbyuserid");
												sqlin2 = sqlin2.replaceFirst(Utils.UserIDSTRINGToREPLACE,
														user.getUserid() + "");
											}
										} else {
											listclient = Stream.of(codeclient.split(",")).map(String::trim)
													.map(Integer::valueOf).collect(Collectors.toList());
											sqlin2 = codeclient;
										}
										typeInfos = 1;
									} catch (Exception ex2) {
									}
								}
							} else {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
										&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
										break Label_2603;
									}
								}
								try {
									if (codeafiliate.equalsIgnoreCase("0")) {
										if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
											sqlin2 = this.environment
													.getRequiredProperty("affiliateid.listidaffiliate");
										} else {
											final Integer userroleid = user.getReelrole().getIdtyperole()
													.getUserroleid();
											if (userroleid == this.clientid) {
												sqlin2 = this.environment.getRequiredProperty(
														"affiliateid.cuslistidfromuserightsbyuserid");
											} else if (userroleid == this.affiliateid) {
												sqlin2 = this.environment
														.getRequiredProperty("affiliateid.listidfromuserightsbyuserid");
											}
											sqlin2 = sqlin2.replaceFirst(Utils.UserIDSTRINGToREPLACE,
													user.getUserid() + "");
										}
									} else {
										sqlin2 = codeafiliate;
									}
									typeInfos = 2;
								} catch (Exception ex3) {
								}
							}
						} else {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
									break Label_2603;
								}
							}
							try {
								if (transporter.equalsIgnoreCase("0")) {
									if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
										sqlin2 = this.environment
												.getRequiredProperty("transporterid.listidtransporter");
									} else {
										final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
										if (userroleid == this.clientid) {
											sqlin2 = this.environment.getRequiredProperty(
													"transporterid.cuslistidfromuserightsbyuserid");
										} else if (userroleid == this.affiliateid) {
											sqlin2 = this.environment.getRequiredProperty(
													"transporterid.afflistidfromuserightsbyuserid");
										} else if (userroleid == this.transporterid) {
											sqlin2 = this.environment
													.getRequiredProperty("transporterid.listidfromuserightsbyuserid");
										}
										sqlin2 = sqlin2.replaceFirst(Utils.UserIDSTRINGToREPLACE,
												user.getUserid() + "");
									}
								} else {
									sqlin2 = transporter;
								}
								typeInfos = 3;
							} catch (Exception ex4) {
							}
						}
					} else {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
								break Label_2603;
							}
						}
						try {
							if (codevehicle.equalsIgnoreCase("0")) {
								if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
									sqlin2 = this.environment.getRequiredProperty("vehicleid.listidvehicle");
								} else {
									final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
									if (userroleid == this.clientid) {
										sqlin2 = this.environment
												.getRequiredProperty("vehicleid.cuslistidfromuserightsbyuserid");
									} else if (userroleid == this.affiliateid) {
										sqlin2 = this.environment
												.getRequiredProperty("vehicleid.afflistidfromuserightsbyuserid");
									} else if (userroleid == this.transporterid) {
										sqlin2 = this.environment
												.getRequiredProperty("vehicleid.listidfromuserightsbyuserid");
									}
									sqlin2 = sqlin2.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
								}
							} else {
								sqlin2 = codevehicle;
							}
							typeInfos = 4;
						} catch (Exception ex5) {
						}
					}
				} else {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
							break Label_2603;
						}
					}
					try {
						if (codedriver.equalsIgnoreCase("0")) {
							if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
								sqlin2 = this.environment.getRequiredProperty("driveridid.listidriver");
							} else {
								final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
								if (userroleid == this.clientid) {
									sqlin2 = this.environment
											.getRequiredProperty("driveridid.cuslistidfromuserightsbyuserid");
								} else if (userroleid == this.affiliateid) {
									sqlin2 = this.environment
											.getRequiredProperty("driveridid.afflistidfromuserightsbyuserid");
								} else if (userroleid == this.transporterid) {
									sqlin2 = this.environment
											.getRequiredProperty("driveridid.listidfromuserightsbyuserid");
								}
								sqlin2 = sqlin2.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
							}
						} else {
							sqlin2 = codedriver;
						}
						typeInfos = 5;
					} catch (Exception ex6) {
					}
				}
			}
		} catch (Exception ex7) {
		}
		
		
		String date001 = Utils.DateFormat(date01, "yyyy-MM-dd");
		String date002 = Utils.DateFormat(date2, "yyyy-MM-dd") ;
		
		final String minisql4 = " bigexception.activitydate >= '" + date001
				+ "' and bigexception.activitydate < '" +date002+ "'";
		allresukt3 = new ArrayList<>();
		completeRequest2 = "";
		completeRequest4 = "";
		completeRequest3 = "";
		completeRequest5 = "";
		allresukt7 = new ArrayList<>();
		allresukt5 = new ArrayList<>();
		if (!Objects.isNull(minisql2) && !Objects.isNull(minisql4)) {
			if (typeInfos == 1) {
				allconstructsqlin2 = " bigexception.clientid in (" + sqlin2 + ") and " + minisql4;
				completeRequest2 = this.environment.getRequiredProperty("totaldistance.client");
				completeRequest2 = completeRequest2.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
				for (final Integer clientid : listclient) {
					String sqlin3 = this.environment.getRequiredProperty("affiliateidperclientid.listidaffiliate");
					sqlin3 = sqlin3.replaceFirst("CLIENT", clientid + "");
					allconstructsqlin = " bigexception.affiliateid in (" + sqlin3 + ") and " + minisql2;
					completeRequest = this.environment.getRequiredProperty("safetyrankingcount.client");
					completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
					completeRequest = completeRequest.replaceFirst("CLIENT", clientid + "");
					completeRequest = completeRequest.replaceFirst("SQLIN", sqlin2 + "");
					completeRequest = completeRequest.replaceFirst("DATE1", date001 + "");
					completeRequest = completeRequest.replaceFirst("DATE2", date002 + "");
					resultlist = this.jdbcTemplate.queryForList(completeRequest);
					allresukt5.addAll(resultlist);
				}
			} else if (typeInfos == 2) {
				allconstructsqlin2 = " bigexception.affiliateid in (" + sqlin2 + ") and " + minisql4;
				completeRequest2 = this.environment.getRequiredProperty("totaldistance.affiliate");
				completeRequest2 = completeRequest2.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
				allconstructsqlin2 = " bigexception.affiliateid in (" + sqlin2 + ") and " + minisql2;
				completeRequest3 = this.environment.getRequiredProperty("safetyrankingcount.affiliate");
				completeRequest3 = completeRequest3.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
			} else if (typeInfos == 3) {
				allconstructsqlin2 = " bigexception.transporterid in (" + sqlin2 + ") and " + minisql4;
				completeRequest2 = this.environment.getRequiredProperty("totaldistance.transporter");
				completeRequest2 = completeRequest2.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
				allconstructsqlin2 = " bigexception.transporterid in (" + sqlin2 + ") and " + minisql2;
				completeRequest3 = this.environment.getRequiredProperty("safetyrankingcount.transporter");
				completeRequest3 = completeRequest3.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
			} else if (typeInfos == 4) {
				allconstructsqlin2 = " bigexception.vehicleid in (" + sqlin2 + ") and " + minisql4;
				completeRequest2 = this.environment.getRequiredProperty("totaldistance.vehicle");
				completeRequest2 = completeRequest2.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
				allconstructsqlin2 = " bigexception.vehicleid in (" + sqlin2 + ") and " + minisql2;
				completeRequest3 = this.environment.getRequiredProperty("safetyrankingcount.vehicle");
				completeRequest3 = completeRequest3.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
			} else if (typeInfos == 5) {
				allconstructsqlin2 = " bigexception.driverid in (" + sqlin2 + ") and " + minisql4;
				completeRequest2 = this.environment.getRequiredProperty("totaldistance.driver");
				completeRequest2 = completeRequest2.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
				allconstructsqlin2 = " bigexception.driverid in (" + sqlin2 + ") and " + minisql2;
				completeRequest3 = this.environment.getRequiredProperty("safetyrankingcount.driver");
				completeRequest3 = completeRequest3.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
			}
		}
		if (!Objects.isNull(minisql3) && !Objects.isNull(minisql4)) {
			if (typeInfos == 1) {
				allconstructsqlin2 = " bigexception.clientid in (" + sqlin2 + ") and " + minisql4;
				completeRequest4 = this.environment.getRequiredProperty("totaldriving.client");
				completeRequest4 = completeRequest4.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
				for (final Integer clientid : listclient) {
					String sqlin3 = this.environment.getRequiredProperty("affiliateidperclientid.listidaffiliate");
					sqlin3 = sqlin3.replaceFirst("CLIENT", clientid + "");
					allconstructsqlin = " bigexception.affiliateid in (" + sqlin3 + ") and " + minisql3;
					completeRequest = this.environment.getRequiredProperty("safetyrankingdriving.client");
					completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
					completeRequest = completeRequest.replaceFirst("CLIENT", clientid + "");
					completeRequest = completeRequest.replaceFirst("SQLIN", sqlin2 + "");
					completeRequest = completeRequest.replaceFirst("DATE1", date001 + "");
					completeRequest = completeRequest.replaceFirst("DATE2", date002 + "");
					resultlist = this.jdbcTemplate.queryForList(completeRequest);
					allresukt7.addAll(resultlist);
				}
			} else if (typeInfos == 2) {
				allconstructsqlin2 = " bigexception.affiliateid in (" + sqlin2 + ") and " + minisql4;
				completeRequest4 = this.environment.getRequiredProperty("totaldriving.affiliate");
				completeRequest4 = completeRequest4.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
				allconstructsqlin2 = " bigexception.affiliateid in (" + sqlin2 + ") and " + minisql3;
				completeRequest5 = this.environment.getRequiredProperty("safetyrankingdriving.affiliate");
				completeRequest5 = completeRequest5.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
			} else if (typeInfos == 3) {
				allconstructsqlin2 = " bigexception.transporterid in (" + sqlin2 + ") and " + minisql4;
				completeRequest4 = this.environment.getRequiredProperty("totaldriving.transporter");
				completeRequest4 = completeRequest4.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
				allconstructsqlin2 = " bigexception.transporterid in (" + sqlin2 + ") and " + minisql3;
				completeRequest5 = this.environment.getRequiredProperty("safetyrankingdriving.transporter");
				completeRequest5 = completeRequest5.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
			} else if (typeInfos == 4) {
				allconstructsqlin2 = " bigexception.vehicleid in (" + sqlin2 + ") and " + minisql4;
				completeRequest4 = this.environment.getRequiredProperty("totaldriving.vehicle");
				completeRequest4 = completeRequest4.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
				allconstructsqlin2 = " bigexception.vehicleid in (" + sqlin2 + ") and " + minisql3;
				completeRequest5 = this.environment.getRequiredProperty("safetyrankingdriving.vehicle");
				completeRequest5 = completeRequest5.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
			} else if (typeInfos == 5) {
				allconstructsqlin2 = " bigexception.driverid in (" + sqlin2 + ") and " + minisql4;
				completeRequest4 = this.environment.getRequiredProperty("totaldriving.driver");
				completeRequest4 = completeRequest4.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
				allconstructsqlin2 = " bigexception.driverid in (" + sqlin2 + ") and " + minisql3;
				completeRequest5 = this.environment.getRequiredProperty("safetyrankingdriving.driver");
				completeRequest5 = completeRequest5.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
			}
		}
		
		
		completeRequest = completeRequest.replaceFirst("SQLIN", sqlin2 + "");
		completeRequest = completeRequest.replaceFirst("DATE1", date001 + "");
		completeRequest = completeRequest.replaceFirst("DATE2", date002 + "");
		
		
		completeRequest2 = completeRequest2.replaceFirst("SQLIN", sqlin2 + "");
		completeRequest2 = completeRequest2.replaceFirst("DATE1", date001 + "");
		completeRequest2 = completeRequest2.replaceFirst("DATE2", date002 + "");
		
		
		completeRequest3 = completeRequest3.replaceFirst("SQLIN", sqlin2 + "");
		completeRequest3 = completeRequest3.replaceFirst("DATE1", date001 + "");
		completeRequest3 = completeRequest3.replaceFirst("DATE2", date002 + "");
		
		
		completeRequest4 = completeRequest4.replaceFirst("SQLIN", sqlin2 + "");
		completeRequest4 = completeRequest4.replaceFirst("DATE1", date001 + "");
		completeRequest4 = completeRequest4.replaceFirst("DATE2", date002 + "");
		
		
		completeRequest5 = completeRequest5.replaceFirst("SQLIN", sqlin2 + "");
		completeRequest5 = completeRequest5.replaceFirst("DATE1", date001 + "");
		completeRequest5 = completeRequest5.replaceFirst("DATE2", date002 + "");
		
		if (typeInfos == 1) {
			if (!Objects.isNull(minisql2) && !Objects.isNull(minisql4)) {
				allresukt4 = this.jdbcTemplate.queryForList(completeRequest2);
			}
			if (!Objects.isNull(minisql3) && !Objects.isNull(minisql4)) {
				allresukt6 = this.jdbcTemplate.queryForList(completeRequest4);
			}
		} else {
			if (!Objects.isNull(minisql2) && !Objects.isNull(minisql4)) {
				allresukt4 = this.jdbcTemplate.queryForList(completeRequest2);
				allresukt5 = this.jdbcTemplate.queryForList(completeRequest3);
			}
			if (!Objects.isNull(minisql3) && !Objects.isNull(minisql4)) {
				allresukt6 = this.jdbcTemplate.queryForList(completeRequest4);
				allresukt7 = this.jdbcTemplate.queryForList(completeRequest5);
			}
		}
		final Map<String, Double> distanceinfos = new HashMap<>();
		final Map<String, Double> distancevalue = new HashMap<>();
		final Map<String, Double> timeinfos = new HashMap<>();
		final Map<String, Double> timevalue = new HashMap<>();
		if (!Objects.isNull(allresukt5) && !allresukt5.isEmpty()) {
			for (final Map<String, Object> datadb : allresukt5) {
				distanceinfos.put(datadb.get("entitiesid") + "_" + datadb.get("level"),
						Utils.castDoubleObject(datadb.get("count")));
			}
		}
		if (!Objects.isNull(allresukt4) && !allresukt4.isEmpty()) {
			for (final Map<String, Object> datadb : allresukt4) {
				final Double values = Utils.castDoubleObject(datadb.get("sum"));
				if (values > 1.0E-6) {
					Double values2 = distanceinfos.get(datadb.get("entitiesid") + "_1");
					if (Objects.isNull(values2)) {
						values2 = 0.0;
					}
					Double values3 = distanceinfos.get(datadb.get("entitiesid") + "_2");
					if (Objects.isNull(values3)) {
						values3 = 0.0;
					}
					Double values4 = distanceinfos.get(datadb.get("entitiesid") + "_3");
					if (Objects.isNull(values4)) {
						values4 = 0.0;
					}
					if (values2 <= 1.0E-6 && values3 <= 1.0E-6 && values4 <= 1.0E-6) {
						continue;
					}
					distancevalue.put(String.valueOf(datadb.get("entitiesid")),
							(values2 * recordingWeight + values3 * alertWeight + values4 * alarmWeight) * 100.0
									/ values);
				}
			}
		}
		if (!Objects.isNull(allresukt7) && !allresukt7.isEmpty()) {
			for (final Map<String, Object> datadb : allresukt7) {
				timeinfos.put(datadb.get("entitiesid") + "_" + datadb.get("level"),
						Utils.castDoubleObject(datadb.get("count")));
			}
		}
		if (!Objects.isNull(allresukt6) && !allresukt6.isEmpty()) {
			for (final Map<String, Object> datadb : allresukt6) {
				final Double values = Utils.castDoubleObject(datadb.get("sum"));
				if (values > 1.0E-6) {
					Double values2 = timeinfos.get(datadb.get("entitiesid") + "_1");
					if (Objects.isNull(values2)) {
						values2 = 0.0;
					}
					Double values3 = timeinfos.get(datadb.get("entitiesid") + "_2");
					if (Objects.isNull(values3)) {
						values3 = 0.0;
					}
					Double values4 = timeinfos.get(datadb.get("entitiesid") + "_3");
					if (Objects.isNull(values4)) {
						values4 = 0.0;
					}
					if (values2 <= 1.0E-6 && values3 <= 1.0E-6 && values4 <= 1.0E-6) {
						continue;
					}
					timevalue.put(String.valueOf(datadb.get("entitiesid")),
							(values2 + values3 + values4) * 100.0 / values);
				}
			}
		}
		final List<Map<String, Double>> finalresult = new ArrayList<>();
		for (final String key : distancevalue.keySet()) {
			final Map<String, Double> maps = new HashMap<>();
			maps.put("entitiesid", Utils.castDoubleObject(key));
			if (timevalue.containsKey(key)) {
				maps.put("sum", distancevalue.get(key) + timevalue.get(key));
				timevalue.remove(key);
			} else {
				maps.put("sum", distancevalue.get(key));
			}
			finalresult.add(maps);
		}
		for (final String key : timevalue.keySet()) {
			final Map<String, Double> maps = new HashMap<>();
			maps.put("entitiesid", Utils.castDoubleObject(key));
			maps.put("sum", timevalue.get(key));
			finalresult.add(maps);
		}
		finalresult.sort(Comparator.comparing(m -> m.get("sum"), Comparator.nullsLast(Comparator.naturalOrder())));
		return new SafetyRanking(typeInfos, finalresult);
	}

	@Override
	public List showTopRankingReport(final int customerid, final int affiliateid, final int transporterid,
			final int vehicleid, final int param, final String exceptionlevels, final String startDate,
			final String endDate) {
		return this.dao.showTopRankingReport(customerid, affiliateid, transporterid, vehicleid, param, exceptionlevels,
				startDate, endDate);
	}
}
