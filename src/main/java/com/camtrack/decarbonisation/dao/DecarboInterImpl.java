//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.decarbonisation.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.camtrack.affiliate.repository.CustomeraffiliateRepository;
import com.camtrack.bean.ResumeResults;
import com.camtrack.config.Utils;
import com.camtrack.dashboard.dao.DashboardDaoInterface;
import com.camtrack.entities.Reelroleusers;
import com.camtrack.entities.Resumexceptionaffiliate;
import com.camtrack.entities.Resumexceptionclient;
import com.camtrack.entities.Resumexceptiondriver;
import com.camtrack.entities.Resumexceptiontransporter;
import com.camtrack.entities.Resumexceptionvehicle;
import com.camtrack.entities.User;
import com.camtrack.entities.Userrole;
import com.camtrack.transporter.repository.TransporterRepository;
import com.camtrack.user.repository.CustomerRepository;
import com.camtrack.user.repository.DriverRepository;
import com.camtrack.user.repository.DriveractivitysummaryRepository;
import com.camtrack.user.repository.DrivertripsRepository;
import com.camtrack.user.repository.ExceptionRepository;
import com.camtrack.user.repository.InvalidateexceptionRepository;
import com.camtrack.user.repository.LogusersRepository;
import com.camtrack.user.repository.ParametertypeRepository;
import com.camtrack.user.repository.UserightsRepository;
import com.camtrack.user.repository.UsersRepository;
import com.camtrack.user.repository.VehicleRepository;
import com.camtrack.user.repository.VehicleactivitysummaryRepository;

@Repository("decarboDao")
@PropertySource({ "classpath:sqlqueries.properties" })
public class DecarboInterImpl implements DecarboInterface {
	@Value("${userrrole.affiliateid}")
	private Integer affiliateid;
	@Value("${userrrole.clientid}")
	private Integer clientid;
	@Autowired
	CustomeraffiliateRepository cusaffR;
	@Autowired
	CustomerRepository customR;
	@Autowired
	DashboardDaoInterface dashboardDaoInterface;
	@Autowired
	DriveractivitysummaryRepository driveractsum;
	@Autowired
	DriverRepository driverR;
	@Autowired
	Environment environment;
	@Autowired
	ExceptionRepository excepR;
	@Autowired
	InvalidateexceptionRepository invalidR;
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	LogusersRepository logRepo;
	@Autowired
	ParametertypeRepository paramertypeR;
	@Value("${userrrole.superadminid}")
	private Integer superadminid;
	@Value("${userrrole.transporterid}")
	private Integer transporterid;
	@Autowired
	TransporterRepository transR;
	@Autowired
	DrivertripsRepository tripsR;
	@Autowired
	UserightsRepository userRR;
	@Autowired
	UsersRepository usersR;
	@Autowired
	VehicleactivitysummaryRepository vehactsum;
	@Autowired
	VehicleRepository vehR;

	@Override
	public List<Map<String, Object>> detaildecarbonisation(final User user, final String codeclient,
			final String codeafiliate, final String transporter, final String codevehicle, final String codedriver,
			final String datedebut, final String datefin) {
		final List<Integer> listypexception = new ArrayList<>();
		String sqlin = "";
		final String andsql = "";
		String completeRequest = "";
		String allconstructsqlin = "";
		final Date date01 = Utils.StringToDate("", datedebut, "yyyy-MM-dd");
		Date date2 = Utils.StringToDate("", datefin, "yyyy-MM-dd");
		if (Objects.isNull(date2) || Objects.isNull(date01)) {
			return null;
		}
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date2);
		calendar.add(5, 1);
		date2 = calendar.getTime();
		final String minisql = " bigexception.startdatetime >= '" + datedebut + "' and bigexception.startdatetime < '"
				+ Utils.DateFormat(date2, "yyyy-MM-dd") + "'";
		final List<Integer> listdriver = new ArrayList<>();
		final List<Integer> listvehicle = new ArrayList<>();
		final List<Integer> listransporter = new ArrayList<>();
		final List<Integer> listafiliate = new ArrayList<>();
		final List<Integer> listclient = new ArrayList<>();
		final List<Integer> result = new ArrayList<>();
		final List<Resumexceptionclient> resumclient = new ArrayList<>();
		final List<Resumexceptionaffiliate> resumaff = new ArrayList<>();
		final List<Resumexceptiontransporter> resumtrans = new ArrayList<>();
		final List<Resumexceptionvehicle> resumveh = new ArrayList<>();
		final List<Resumexceptiondriver> resumdriver = new ArrayList<>();
		final List<Map<String, Object>> resultlist = null;
		List<Map<String, Object>> allresukt = new ArrayList<>();
		try {
			if (Objects.isNull(codedriver) || codedriver.equalsIgnoreCase("")) {
				if (Objects.isNull(codevehicle) || codevehicle.equalsIgnoreCase("")) {
					if (Objects.isNull(transporter) || transporter.equalsIgnoreCase("")) {
						if (Objects.isNull(codeafiliate) || codeafiliate.equalsIgnoreCase("")) {
							if (Objects.isNull(codeclient) || codeclient.equalsIgnoreCase("")) {
								if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
									sqlin = this.environment.getRequiredProperty("affiliateid.listidaffiliate");
								} else {
									sqlin = this.environment
											.getRequiredProperty("affiliateidfromclientid.listidfromuserightsbyuserid");
									sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
								}
								allconstructsqlin = " bigexception.affiliateid in (" + sqlin + ") and " + minisql;
								completeRequest = this.environment
										.getRequiredProperty("selectdecarbonisationdetailexception");
								completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
								allresukt = this.jdbcTemplate.queryForList(completeRequest);
							} else {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid) {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
										return allresukt;
									}
								}
								try {
									if (codeclient.equalsIgnoreCase("0")) {
										if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
											sqlin = this.environment.getRequiredProperty("affiliateid.listidaffiliate");
										} else {
											sqlin = this.environment.getRequiredProperty(
													"affiliateidfromclientid.listidfromuserightsbyuserid");
											sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE,
													user.getUserid() + "");
										}
									} else {
										sqlin = this.environment.getRequiredProperty(
												"affiliateidwithlistclientid.listidfromuserightsbyuserid");
										sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, codeclient);
									}
									allconstructsqlin = " bigexception.affiliateid in (" + sqlin + ") and " + minisql;
									completeRequest = this.environment
											.getRequiredProperty("selectdecarbonisationdetailexception");
									completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
									allresukt = this.jdbcTemplate.queryForList(completeRequest);
								} catch (Exception ex) {
								}
							}
						} else {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
									return allresukt;
								}
							}
							try {
								if (codeafiliate.equalsIgnoreCase("0")) {
									if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
										sqlin = this.environment.getRequiredProperty("affiliateid.listidaffiliate");
									} else {
										final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
										if (userroleid == this.clientid) {
											sqlin = this.environment
													.getRequiredProperty("affiliateid.cuslistidfromuserightsbyuserid");
										} else if (userroleid == this.affiliateid) {
											sqlin = this.environment
													.getRequiredProperty("affiliateid.listidfromuserightsbyuserid");
										}
										sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
									}
								} else {
									sqlin = codeafiliate;
								}
								allconstructsqlin = " bigexception.affiliateid in (" + sqlin + ") and " + minisql;
								completeRequest = this.environment
										.getRequiredProperty("selectdecarbonisationdetailexception");
								completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
								allresukt = this.jdbcTemplate.queryForList(completeRequest);
							} catch (Exception ex2) {
							}
						}
					} else {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
								return allresukt;
							}
						}
						try {
							if (transporter.equalsIgnoreCase("0")) {
								if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
									sqlin = this.environment.getRequiredProperty("transporterid.listidtransporter");
								} else {
									final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
									if (userroleid == this.clientid) {
										sqlin = this.environment
												.getRequiredProperty("transporterid.cuslistidfromuserightsbyuserid");
									} else if (userroleid == this.affiliateid) {
										sqlin = this.environment
												.getRequiredProperty("transporterid.afflistidfromuserightsbyuserid");
									} else if (userroleid == this.transporterid) {
										sqlin = this.environment
												.getRequiredProperty("transporterid.listidfromuserightsbyuserid");
									}
									sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
								}
							} else {
								sqlin = transporter;
							}
							allconstructsqlin = " bigexception.transporterid in (" + sqlin + ") and " + minisql;
							completeRequest = this.environment
									.getRequiredProperty("selectdecarbonisationdetailexception");
							completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
							allresukt = this.jdbcTemplate.queryForList(completeRequest);
						} catch (Exception ex3) {
						}
					}
				} else {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
							return allresukt;
						}
					}
					try {
						if (codevehicle.equalsIgnoreCase("0")) {
							if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
								sqlin = this.environment.getRequiredProperty("vehicleid.listidvehicle");
							} else {
								final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
								if (userroleid == this.clientid) {
									sqlin = this.environment
											.getRequiredProperty("vehicleid.cuslistidfromuserightsbyuserid");
								} else if (userroleid == this.affiliateid) {
									sqlin = this.environment
											.getRequiredProperty("vehicleid.afflistidfromuserightsbyuserid");
								} else if (userroleid == this.transporterid) {
									sqlin = this.environment
											.getRequiredProperty("vehicleid.listidfromuserightsbyuserid");
								}
								sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
							}
						} else {
							sqlin = codevehicle;
						}
						allconstructsqlin = " bigexception.vehicleid in (" + sqlin + ") and " + minisql;
						completeRequest = this.environment.getRequiredProperty("selectdecarbonisationdetailexception");
						completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
						allresukt = this.jdbcTemplate.queryForList(completeRequest);
					} catch (Exception ex4) {
					}
				}
			} else {
				if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
						&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
						&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
						return allresukt;
					}
				}
				try {
					if (codedriver.equalsIgnoreCase("0")) {
						if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
							sqlin = this.environment.getRequiredProperty("driveridid.listidriverdecar");
						} else {
							final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
							if (userroleid == this.clientid) {
								sqlin = this.environment
										.getRequiredProperty("driveridid.cuslistidfromuserightsbyuserid");
							} else if (userroleid == this.affiliateid) {
								sqlin = this.environment
										.getRequiredProperty("driveridid.afflistidfromuserightsbyuserid");
							} else if (userroleid == this.transporterid) {
								sqlin = this.environment.getRequiredProperty("driveridid.listidfromuserightsbyuserid");
							}
							sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
						}
					} else {
						sqlin = codedriver;
					}
					allconstructsqlin = " bigexception.driverid in (" + sqlin + ") and " + minisql;
					completeRequest = this.environment.getRequiredProperty("selectdecarbonisationdetailexception");
					completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
					allresukt = this.jdbcTemplate.queryForList(completeRequest);
				} catch (Exception ex5) {
				}
			}
		} catch (Exception ex6) {
		}
		return allresukt;
	}

	@Override
	public List<Map<String, Object>> fuelconsum(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codevehicle, final String codedriver, final String datedebut,
			final String datefin, final Integer periodicite) {
		final List<Integer> listypexception = new ArrayList<>();
		String sqlin = "";
		final String andsql = "";
		String completeRequest = "";
		final String allconstructsqlin = "";
		final Date date01 = Utils.StringToDate("", datedebut, "yyyy-MM-dd");
		Date date2 = Utils.StringToDate("", datefin, "yyyy-MM-dd");
		if (Objects.isNull(date2) || Objects.isNull(date01)) {
			return null;
		}
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date2);
		calendar.add(5, 1);
		date2 = calendar.getTime();
		final List<Integer> listdriver = new ArrayList<>();
		final List<Integer> listvehicle = new ArrayList<>();
		final List<Integer> listransporter = new ArrayList<>();
		final List<Integer> listafiliate = new ArrayList<>();
		final List<Integer> listclient = new ArrayList<>();
		final List<Integer> result = new ArrayList<>();
		final List<Resumexceptionclient> resumclient = new ArrayList<>();
		final List<Resumexceptionaffiliate> resumaff = new ArrayList<>();
		final List<Resumexceptiontransporter> resumtrans = new ArrayList<>();
		final List<Resumexceptionvehicle> resumveh = new ArrayList<>();
		final List<Resumexceptiondriver> resumdriver = new ArrayList<>();
		final List<Map<String, Object>> resultlist = null;
		List<Map<String, Object>> allresukt = new ArrayList<>();
		try {
			if (Objects.isNull(codedriver) || codedriver.equalsIgnoreCase("")) {
				if (Objects.isNull(codevehicle) || codevehicle.equalsIgnoreCase("")) {
					if (Objects.isNull(transporter) || transporter.equalsIgnoreCase("")) {
						if (Objects.isNull(codeafiliate) || codeafiliate.equalsIgnoreCase("")) {
							if (Objects.isNull(codeclient) || codeclient.equalsIgnoreCase("")) {
								if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
									sqlin = this.environment.getRequiredProperty("clientid.listidclient");
								} else {
									sqlin = this.environment
											.getRequiredProperty("clientid.listidfromuserightsbyuserid");
									sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
								}
								if (periodicite == 1) {
									completeRequest = this.environment.getRequiredProperty(
											"sumalldailyfueldecarbonisationperiodforclientgroupby");
								} else if (periodicite == 2) {
									completeRequest = this.environment.getRequiredProperty(
											"sumallweeklyfueldecarbonisationperiodforclientgroupby");
								} else {
									completeRequest = this.environment.getRequiredProperty(
											"sumallmonthlyfueldecarbonisationperiodforclientgroupby");
								}
								// completeRequest =
								// this.environment.getRequiredProperty("selectdecarbonisationdetailexception");
								completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
								completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
								completeRequest = completeRequest.replaceFirst("DATTTES2",
										Utils.DateFormat(date2, "yyyy-MM-dd") + "");
								allresukt = this.jdbcTemplate.queryForList(completeRequest);
							} else {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid) {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
										return allresukt;
									}
								}
								try {
									if (codeclient.equalsIgnoreCase("0")) {
										if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
											sqlin = this.environment.getRequiredProperty("clientid.listidclient");
										} else {
											sqlin = this.environment
													.getRequiredProperty("clientid.listidfromuserightsbyuserid");
											sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE,
													user.getUserid() + "");
										}
									} else {
										sqlin = codeclient;
									}
									if (periodicite == 1) {
										completeRequest = this.environment.getRequiredProperty(
												"sumalldailyfueldecarbonisationperiodforclientgroupby");
									} else if (periodicite == 2) {
										completeRequest = this.environment.getRequiredProperty(
												"sumallweeklyfueldecarbonisationperiodforclientgroupby");
									} else {
										completeRequest = this.environment.getRequiredProperty(
												"sumallmonthlyfueldecarbonisationperiodforclientgroupby");
									}
									completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
									completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
									completeRequest = completeRequest.replaceFirst("DATTTES2",
											Utils.DateFormat(date2, "yyyy-MM-dd") + "");
									allresukt = this.jdbcTemplate.queryForList(completeRequest);
								} catch (Exception ex) {
								}
							}
						} else {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
									return allresukt;
								}
							}
							try {
								if (codeafiliate.equalsIgnoreCase("0")) {
									if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
										sqlin = this.environment.getRequiredProperty("affiliateid.listidaffiliate");
									} else {
										final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
										if (userroleid == this.clientid) {
											sqlin = this.environment
													.getRequiredProperty("affiliateid.cuslistidfromuserightsbyuserid");
										} else if (userroleid == this.affiliateid) {
											sqlin = this.environment
													.getRequiredProperty("affiliateid.listidfromuserightsbyuserid");
										}
										sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
									}
								} else {
									sqlin = codeafiliate;
								}
								if (periodicite == 1) {
									completeRequest = this.environment.getRequiredProperty(
											"sumalldailyfueldecarbonisationperiodforaffiliategroupby");
								} else if (periodicite == 2) {
									completeRequest = this.environment.getRequiredProperty(
											"sumallweeklyfueldecarbonisationperiodforaffiliategroupby");
								} else {
									completeRequest = this.environment.getRequiredProperty(
											"sumallmonthlyfueldecarbonisationperiodforaffiliategroupby");
								}
								completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
								completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
								completeRequest = completeRequest.replaceFirst("DATTTES2",
										Utils.DateFormat(date2, "yyyy-MM-dd") + "");
								allresukt = this.jdbcTemplate.queryForList(completeRequest);
							} catch (Exception ex2) {
							}
						}
					} else {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
								return allresukt;
							}
						}
						try {
							if (transporter.equalsIgnoreCase("0")) {
								if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
									sqlin = this.environment.getRequiredProperty("transporterid.listidtransporter");
								} else {
									final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
									if (userroleid == this.clientid) {
										sqlin = this.environment
												.getRequiredProperty("transporterid.cuslistidfromuserightsbyuserid");
									} else if (userroleid == this.affiliateid) {
										sqlin = this.environment
												.getRequiredProperty("transporterid.afflistidfromuserightsbyuserid");
									} else if (userroleid == this.transporterid) {
										sqlin = this.environment
												.getRequiredProperty("transporterid.listidfromuserightsbyuserid");
									}
									sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
								}
							} else {
								sqlin = transporter;
							}
							if (periodicite == 1) {
								completeRequest = this.environment.getRequiredProperty(
										"sumalldailyfueldecarbonisationperiodfortransportergroupby");
							} else if (periodicite == 2) {
								completeRequest = this.environment.getRequiredProperty(
										"sumallweeklyfueldecarbonisationperiodfortransportergroupby");
							} else {
								completeRequest = this.environment.getRequiredProperty(
										"sumallmonthlyfueldecarbonisationperiodfortransportergroupby");
							}
							completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
							completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
							completeRequest = completeRequest.replaceFirst("DATTTES2",
									Utils.DateFormat(date2, "yyyy-MM-dd") + "");
							allresukt = this.jdbcTemplate.queryForList(completeRequest);
						} catch (Exception ex3) {
						}
					}
				} else {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
							return allresukt;
						}
					}
					try {
						if (codevehicle.equalsIgnoreCase("0")) {
							if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
								sqlin = this.environment.getRequiredProperty("vehicleid.listidvehicle");
							} else {
								final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
								if (userroleid == this.clientid) {
									sqlin = this.environment
											.getRequiredProperty("vehicleid.cuslistidfromuserightsbyuserid");
								} else if (userroleid == this.affiliateid) {
									sqlin = this.environment
											.getRequiredProperty("vehicleid.afflistidfromuserightsbyuserid");
								} else if (userroleid == this.transporterid) {
									sqlin = this.environment
											.getRequiredProperty("vehicleid.listidfromuserightsbyuserid");
								}
								sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
							}
						} else {
							sqlin = codevehicle;
						}
						if (periodicite == 1) {
							completeRequest = this.environment
									.getRequiredProperty("sumalldailyfueldecarbonisationperiodforvehiclegroupby");
						} else if (periodicite == 2) {
							completeRequest = this.environment
									.getRequiredProperty("sumallweeklyfueldecarbonisationperiodforvehiclegroupby");
						} else {
							completeRequest = this.environment
									.getRequiredProperty("sumallmonthlyfueldecarbonisationperiodforvehiclegroupby");
						}
						completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
						completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
						completeRequest = completeRequest.replaceFirst("DATTTES2",
								Utils.DateFormat(date2, "yyyy-MM-dd") + "");
						allresukt = this.jdbcTemplate.queryForList(completeRequest);
					} catch (Exception ex4) {
					}
				}
			} else {
				if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
						&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
						&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
						return allresukt;
					}
				}
				try {
					if (codedriver.equalsIgnoreCase("0")) {
						if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
							sqlin = this.environment.getRequiredProperty("driveridid.listidriverdecar");
						} else {
							final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
							if (userroleid == this.clientid) {
								sqlin = this.environment
										.getRequiredProperty("driveridid.cuslistidfromuserightsbyuserid");
							} else if (userroleid == this.affiliateid) {
								sqlin = this.environment
										.getRequiredProperty("driveridid.afflistidfromuserightsbyuserid");
							} else if (userroleid == this.transporterid) {
								sqlin = this.environment.getRequiredProperty("driveridid.listidfromuserightsbyuserid");
							}
							sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
						}
					} else {
						sqlin = codedriver;
					}
					if (periodicite == 1) {
						completeRequest = this.environment
								.getRequiredProperty("sumalldailyfueldecarbonisationperiodfordrivergroupby");
					} else if (periodicite == 2) {
						completeRequest = this.environment
								.getRequiredProperty("sumallweeklyfueldecarbonisationperiodfordrivergroupby");
					} else {
						completeRequest = this.environment
								.getRequiredProperty("sumallmonthlyfueldecarbonisationperiodfordrivergroupby");
					}
					completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
					completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
					completeRequest = completeRequest.replaceFirst("DATTTES2",
							Utils.DateFormat(date2, "yyyy-MM-dd") + "");
					allresukt = this.jdbcTemplate.queryForList(completeRequest);
				} catch (Exception ex5) {
				}
			}
		} catch (Exception ex6) {
		}
		return allresukt;
	}

	@Override
	public List<Map<String, Object>> periodecarbo(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codevehicle, final String codedriver, final String datedebut,
			final String datefin, final Integer periodicite) {
		final List<Integer> listypexception = new ArrayList<>();
		String sqlin = "";
		final String andsql = "";
		String completeRequest = "";
		final String allconstructsqlin = "";
		final Date date01 = Utils.StringToDate("", datedebut, "yyyy-MM-dd");
		Date date2 = Utils.StringToDate("", datefin, "yyyy-MM-dd");
		if (Objects.isNull(date2) || Objects.isNull(date01)) {
			return null;
		}
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date2);
		calendar.add(5, 1);
		date2 = calendar.getTime();
		final List<Integer> listdriver = new ArrayList<>();
		final List<Integer> listvehicle = new ArrayList<>();
		final List<Integer> listransporter = new ArrayList<>();
		final List<Integer> listafiliate = new ArrayList<>();
		final List<Integer> listclient = new ArrayList<>();
		final List<Integer> result = new ArrayList<>();
		final List<Resumexceptionclient> resumclient = new ArrayList<>();
		final List<Resumexceptionaffiliate> resumaff = new ArrayList<>();
		final List<Resumexceptiontransporter> resumtrans = new ArrayList<>();
		final List<Resumexceptionvehicle> resumveh = new ArrayList<>();
		final List<Resumexceptiondriver> resumdriver = new ArrayList<>();
		final List<Map<String, Object>> resultlist = null;
		List<Map<String, Object>> allresukt = new ArrayList<>();
		try {
			if (Objects.isNull(codedriver) || codedriver.equalsIgnoreCase("")) {
				if (Objects.isNull(codevehicle) || codevehicle.equalsIgnoreCase("")) {
					if (Objects.isNull(transporter) || transporter.equalsIgnoreCase("")) {
						if (Objects.isNull(codeafiliate) || codeafiliate.equalsIgnoreCase("")) {
							if (Objects.isNull(codeclient) || codeclient.equalsIgnoreCase("")) {
								if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
									sqlin = this.environment.getRequiredProperty("clientid.listidclient");
								} else {
									sqlin = this.environment
											.getRequiredProperty("clientid.listidfromuserightsbyuserid");
									sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
								}
								if (periodicite == 1) {
									completeRequest = this.environment
											.getRequiredProperty("sumalldailydecarbonisationperiodforclientgroupby");
								} else if (periodicite == 2) {
									completeRequest = this.environment
											.getRequiredProperty("sumallweeklydecarbonisationperiodforclientgroupby");
								} else {
									completeRequest = this.environment
											.getRequiredProperty("sumallmonthlydecarbonisationperiodforclientgroupby");
								}
								// completeRequest =
								// this.environment.getRequiredProperty("selectdecarbonisationdetailexception");
								completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
								completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
								completeRequest = completeRequest.replaceFirst("DATTTES2",
										Utils.DateFormat(date2, "yyyy-MM-dd") + "");
								allresukt = this.jdbcTemplate.queryForList(completeRequest);
							} else {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid) {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
										return allresukt;
									}
								}
								try {
									if (codeclient.equalsIgnoreCase("0")) {
										if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
											sqlin = this.environment.getRequiredProperty("clientid.listidclient");
										} else {
											sqlin = this.environment
													.getRequiredProperty("clientid.listidfromuserightsbyuserid");
											sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE,
													user.getUserid() + "");
										}
									} else {
										sqlin = codeclient;
									}
									if (periodicite == 1) {
										completeRequest = this.environment.getRequiredProperty(
												"sumalldailydecarbonisationperiodforclientgroupby");
									} else if (periodicite == 2) {
										completeRequest = this.environment.getRequiredProperty(
												"sumallweeklydecarbonisationperiodforclientgroupby");
									} else {
										completeRequest = this.environment.getRequiredProperty(
												"sumallmonthlydecarbonisationperiodforclientgroupby");
									}
									completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
									completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
									completeRequest = completeRequest.replaceFirst("DATTTES2",
											Utils.DateFormat(date2, "yyyy-MM-dd") + "");
									allresukt = this.jdbcTemplate.queryForList(completeRequest);
								} catch (Exception ex) {
								}
							}
						} else {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
									return allresukt;
								}
							}
							try {
								if (codeafiliate.equalsIgnoreCase("0")) {
									if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
										sqlin = this.environment.getRequiredProperty("affiliateid.listidaffiliate");
									} else {
										final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
										if (userroleid == this.clientid) {
											sqlin = this.environment
													.getRequiredProperty("affiliateid.cuslistidfromuserightsbyuserid");
										} else if (userroleid == this.affiliateid) {
											sqlin = this.environment
													.getRequiredProperty("affiliateid.listidfromuserightsbyuserid");
										}
										sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
									}
								} else {
									sqlin = codeafiliate;
								}
								if (periodicite == 1) {
									completeRequest = this.environment
											.getRequiredProperty("sumalldailydecarbonisationperiodforaffiliategroupby");
								} else if (periodicite == 2) {
									completeRequest = this.environment.getRequiredProperty(
											"sumallweeklydecarbonisationperiodforaffiliategroupby");
								} else {
									completeRequest = this.environment.getRequiredProperty(
											"sumallmonthlydecarbonisationperiodforaffiliategroupby");
								}
								completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
								completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
								completeRequest = completeRequest.replaceFirst("DATTTES2",
										Utils.DateFormat(date2, "yyyy-MM-dd") + "");
								allresukt = this.jdbcTemplate.queryForList(completeRequest);
							} catch (Exception ex2) {
							}
						}
					} else {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
								return allresukt;
							}
						}
						try {
							if (transporter.equalsIgnoreCase("0")) {
								if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
									sqlin = this.environment.getRequiredProperty("transporterid.listidtransporter");
								} else {
									final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
									if (userroleid == this.clientid) {
										sqlin = this.environment
												.getRequiredProperty("transporterid.cuslistidfromuserightsbyuserid");
									} else if (userroleid == this.affiliateid) {
										sqlin = this.environment
												.getRequiredProperty("transporterid.afflistidfromuserightsbyuserid");
									} else if (userroleid == this.transporterid) {
										sqlin = this.environment
												.getRequiredProperty("transporterid.listidfromuserightsbyuserid");
									}
									sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
								}
							} else {
								sqlin = transporter;
							}
							if (periodicite == 1) {
								completeRequest = this.environment
										.getRequiredProperty("sumalldailydecarbonisationperiodfortransportergroupby");
							} else if (periodicite == 2) {
								completeRequest = this.environment
										.getRequiredProperty("sumallweeklydecarbonisationperiodfortransportergroupby");
							} else {
								completeRequest = this.environment
										.getRequiredProperty("sumallmonthlydecarbonisationperiodfortransportergroupby");
							}
							completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
							completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
							completeRequest = completeRequest.replaceFirst("DATTTES2",
									Utils.DateFormat(date2, "yyyy-MM-dd") + "");
							allresukt = this.jdbcTemplate.queryForList(completeRequest);
						} catch (Exception ex3) {
						}
					}
				} else {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
							return allresukt;
						}
					}
					try {
						if (codevehicle.equalsIgnoreCase("0")) {
							if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
								sqlin = this.environment.getRequiredProperty("vehicleid.listidvehicle");
							} else {
								final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
								if (userroleid == this.clientid) {
									sqlin = this.environment
											.getRequiredProperty("vehicleid.cuslistidfromuserightsbyuserid");
								} else if (userroleid == this.affiliateid) {
									sqlin = this.environment
											.getRequiredProperty("vehicleid.afflistidfromuserightsbyuserid");
								} else if (userroleid == this.transporterid) {
									sqlin = this.environment
											.getRequiredProperty("vehicleid.listidfromuserightsbyuserid");
								}
								sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
							}
						} else {
							sqlin = codevehicle;
						}
						if (periodicite == 1) {
							completeRequest = this.environment
									.getRequiredProperty("sumalldailydecarbonisationperiodforvehiclegroupby");
						} else if (periodicite == 2) {
							completeRequest = this.environment
									.getRequiredProperty("sumallweeklydecarbonisationperiodforvehiclegroupby");
						} else {
							completeRequest = this.environment
									.getRequiredProperty("sumallmonthlydecarbonisationperiodforvehiclegroupby");
						}
						completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
						completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
						completeRequest = completeRequest.replaceFirst("DATTTES2",
								Utils.DateFormat(date2, "yyyy-MM-dd") + "");
						allresukt = this.jdbcTemplate.queryForList(completeRequest);
					} catch (Exception ex4) {
					}
				}
			} else {
				if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
						&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
						&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
						return allresukt;
					}
				}
				try {
					if (codedriver.equalsIgnoreCase("0")) {
						if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
							sqlin = this.environment.getRequiredProperty("driveridid.listidriverdecar");
						} else {
							final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
							if (userroleid == this.clientid) {
								sqlin = this.environment
										.getRequiredProperty("driveridid.cuslistidfromuserightsbyuserid");
							} else if (userroleid == this.affiliateid) {
								sqlin = this.environment
										.getRequiredProperty("driveridid.afflistidfromuserightsbyuserid");
							} else if (userroleid == this.transporterid) {
								sqlin = this.environment.getRequiredProperty("driveridid.listidfromuserightsbyuserid");
							}
							sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
						}
					} else {
						sqlin = codedriver;
					}
					if (periodicite == 1) {
						completeRequest = this.environment
								.getRequiredProperty("sumalldailydecarbonisationperiodfordrivergroupby");
					} else if (periodicite == 2) {
						completeRequest = this.environment
								.getRequiredProperty("sumallweeklydecarbonisationperiodfordrivergroupby");
					} else {
						completeRequest = this.environment
								.getRequiredProperty("sumallmonthlydecarbonisationperiodfordrivergroupby");
					}
					completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
					completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
					completeRequest = completeRequest.replaceFirst("DATTTES2",
							Utils.DateFormat(date2, "yyyy-MM-dd") + "");
					allresukt = this.jdbcTemplate.queryForList(completeRequest);
				} catch (Exception ex5) {
				}
			}
		} catch (Exception ex6) {
		}
		return allresukt;
	}

	@Override
	public ResumeResults summaryDecarbo(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codevehicle, final String codedriver, final String datedebut,
			final String datefin) {
		final Date date1 = Utils.StringToDate("", datedebut, "yyyy-MM-dd");
		Date date2 = Utils.StringToDate("", datefin, "yyyy-MM-dd");
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date2);
		cal.add(5, 1);
		date2 = cal.getTime();
		final long worktime = 0L;
		final long totaldistance = 0L;
		final long idleTime = 0L;
		final long pausetime = 0L;
		final long drivetime = 0L;
		final List<Integer> listdriver = new ArrayList<>();
		final List<Integer> listvehicle = new ArrayList<>();
		final List<Integer> listransporter = new ArrayList<>();
		final List<Integer> listafiliate = new ArrayList<>();
		final List<Integer> listclient = new ArrayList<>();
		final List<Integer> result = new ArrayList<>();
		final List<Resumexceptionclient> resumclient = new ArrayList<>();
		final List<Resumexceptionaffiliate> resumaff = new ArrayList<>();
		final List<Resumexceptiontransporter> resumtrans = new ArrayList<>();
		final List<Resumexceptionvehicle> resumveh = new ArrayList<>();
		final List<Resumexceptiondriver> resumdriver = new ArrayList<>();
		final List<Object[]> listobject = new ArrayList<>();
		final Reelroleusers role = user.getReelrole();
		final Userrole roles = role.getIdtyperole();
		final Integer valeur = roles.getUserroleid();
		String sqlin = "";
		Integer typeInfos = 0;
		try {
			Label_1490: {
				if (Objects.isNull(codedriver) || codedriver.equalsIgnoreCase("")) {
					if (Objects.isNull(codevehicle) || codevehicle.equalsIgnoreCase("")) {
						if (Objects.isNull(transporter) || transporter.equalsIgnoreCase("")) {
							if (Objects.isNull(codeafiliate) || codeafiliate.equalsIgnoreCase("")) {
								if (!Objects.isNull(codeclient)) {
									if (!codeclient.equalsIgnoreCase("")) {
										if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid) {
											if (user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
												break Label_1490;
											}
										}
										try {
											if (codeclient.equalsIgnoreCase("0")) {
												if (user.getReelrole().getIdtyperole()
														.getUserroleid() == this.superadminid) {
													sqlin = this.environment
															.getRequiredProperty("clientid.listidclient");
												} else {
													sqlin = this.environment.getRequiredProperty(
															"clientid.listidfromuserightsbyuserid");
													sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE,
															user.getUserid() + "");
												}
											} else {
												sqlin = codeclient;
											}
											typeInfos = 1;
										} catch (Exception ex2) {
										}
										break Label_1490;
									}
								}
								try {
									if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
										sqlin = this.environment.getRequiredProperty("clientid.listidclient");
									} else {
										try {
											sqlin = this.environment
													.getRequiredProperty("clientid.listidfromuserightsbyuserid");
											sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE,
													user.getUserid() + "");
										} catch (Exception ex3) {
										}
									}
									typeInfos = 1;
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							} else {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
										&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
										break Label_1490;
									}
								}
								try {
									if (codeafiliate.equalsIgnoreCase("0")) {
										if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
											sqlin = this.environment.getRequiredProperty("affiliateid.listidaffiliate");
										} else {
											final Integer userroleid = user.getReelrole().getIdtyperole()
													.getUserroleid();
											if (userroleid == this.clientid) {
												sqlin = this.environment.getRequiredProperty(
														"affiliateid.cuslistidfromuserightsbyuserid");
											} else if (userroleid == this.affiliateid) {
												sqlin = this.environment
														.getRequiredProperty("affiliateid.listidfromuserightsbyuserid");
											}
											sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE,
													user.getUserid() + "");
										}
									} else {
										sqlin = codeafiliate;
									}
									typeInfos = 2;
								} catch (Exception ex4) {
								}
							}
						} else {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
									break Label_1490;
								}
							}
							try {
								if (transporter.equalsIgnoreCase("0")) {
									if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
										sqlin = this.environment.getRequiredProperty("transporterid.listidtransporter");
									} else {
										final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
										if (userroleid == this.clientid) {
											sqlin = this.environment.getRequiredProperty(
													"transporterid.cuslistidfromuserightsbyuserid");
										} else if (userroleid == this.affiliateid) {
											sqlin = this.environment.getRequiredProperty(
													"transporterid.afflistidfromuserightsbyuserid");
										} else if (userroleid == this.transporterid) {
											sqlin = this.environment
													.getRequiredProperty("transporterid.listidfromuserightsbyuserid");
										}
										sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
									}
								} else {
									sqlin = transporter;
								}
								typeInfos = 3;
							} catch (Exception ex5) {
							}
						}
					} else {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
								break Label_1490;
							}
						}
						try {
							if (codevehicle.equalsIgnoreCase("0")) {
								if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
									sqlin = this.environment.getRequiredProperty("vehicleid.listidvehicle");
								} else {
									final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
									if (userroleid == this.clientid) {
										sqlin = this.environment
												.getRequiredProperty("vehicleid.cuslistidfromuserightsbyuserid");
									} else if (userroleid == this.affiliateid) {
										sqlin = this.environment
												.getRequiredProperty("vehicleid.afflistidfromuserightsbyuserid");
									} else if (userroleid == this.transporterid) {
										sqlin = this.environment
												.getRequiredProperty("vehicleid.listidfromuserightsbyuserid");
									}
									sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
								}
							} else {
								sqlin = codevehicle;
							}
							typeInfos = 4;
						} catch (Exception ex6) {
						}
					}
				} else {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
							break Label_1490;
						}
					}
					try {
						if (codedriver.equalsIgnoreCase("0")) {
							if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
								sqlin = this.environment.getRequiredProperty("driveridid.listidriverdecar");
							} else {
								final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
								if (userroleid == this.clientid) {
									sqlin = this.environment
											.getRequiredProperty("driveridid.cuslistidfromuserightsbyuserid");
								} else if (userroleid == this.affiliateid) {
									sqlin = this.environment
											.getRequiredProperty("driveridid.afflistidfromuserightsbyuserid");
								} else if (userroleid == this.transporterid) {
									sqlin = this.environment
											.getRequiredProperty("driveridid.listidfromuserightsbyuserid");
								}
								sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
							}
						} else {
							sqlin = codedriver;
						}
						typeInfos = 5;
					} catch (Exception ex7) {
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String completeRequest = "";
		List<Map<String, Object>> allresukt = new ArrayList<>();
		switch (typeInfos) {
		case 1: {
			completeRequest = this.environment.getRequiredProperty("sumalldecarbonisationperiodforclientgroupby");
			completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
			completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
			completeRequest = completeRequest.replaceFirst("DATTTES2", Utils.DateFormat(date2, "yyyy-MM-dd") + "");
			allresukt = this.jdbcTemplate.queryForList(completeRequest);
			break;
		}
		case 2: {
			completeRequest = this.environment.getRequiredProperty("sumalldecarbonisationperiodforaffiliategroupby");
			completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
			completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
			completeRequest = completeRequest.replaceFirst("DATTTES2", Utils.DateFormat(date2, "yyyy-MM-dd") + "");
			allresukt = this.jdbcTemplate.queryForList(completeRequest);
			break;
		}
		case 3: {
			completeRequest = this.environment.getRequiredProperty("sumalldecarbonisationperiodfortransportergroupby");
			completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
			completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
			completeRequest = completeRequest.replaceFirst("DATTTES2", Utils.DateFormat(date2, "yyyy-MM-dd") + "");
			allresukt = this.jdbcTemplate.queryForList(completeRequest);
			break;
		}
		case 4: {
			completeRequest = this.environment.getRequiredProperty("sumalldecarbonisationperiodforvehiclegroupby");
			completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
			completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
			completeRequest = completeRequest.replaceFirst("DATTTES2", Utils.DateFormat(date2, "yyyy-MM-dd") + "");
			allresukt = this.jdbcTemplate.queryForList(completeRequest);
			break;
		}
		case 5: {
			completeRequest = this.environment.getRequiredProperty("sumalldecarbonisationperiodfordrivergroupby");
			completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
			completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
			completeRequest = completeRequest.replaceFirst("DATTTES2", Utils.DateFormat(date2, "yyyy-MM-dd") + "");
			allresukt = this.jdbcTemplate.queryForList(completeRequest);
			break;
		}
		}
		return new ResumeResults(allresukt, typeInfos);
	}

}
