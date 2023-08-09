//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.exception.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.camtrack.affiliate.repository.CustomeraffiliateRepository;
import com.camtrack.bean.ListInvalidExceptionBean;
import com.camtrack.config.ExceptionDetails;
import com.camtrack.config.StaticValues;
import com.camtrack.config.Utils;
import com.camtrack.dashboard.dao.DashboardDaoInterface;
import com.camtrack.entities.Customer;
import com.camtrack.entities.Customeraffiliate;
import com.camtrack.entities.Driver;
import com.camtrack.entities.Exceptionlevel;
import com.camtrack.entities.Invalidateexception;
import com.camtrack.entities.Listconfigtypes;
import com.camtrack.entities.NbrtypeExceptions;
import com.camtrack.entities.Parametertype;
import com.camtrack.entities.Resumexceptionaffiliate;
import com.camtrack.entities.Resumexceptionclient;
import com.camtrack.entities.Resumexceptiondriver;
import com.camtrack.entities.Resumexceptiontransporter;
import com.camtrack.entities.Resumexceptionvehicle;
import com.camtrack.entities.Success;
import com.camtrack.entities.SummaryException;
import com.camtrack.entities.Transporter;
import com.camtrack.entities.User;
import com.camtrack.entities.Userlogsactivity;
import com.camtrack.entities.Vehicle;
import com.camtrack.transporter.repository.TransporterRepository;
import com.camtrack.user.repository.CustomerRepository;
import com.camtrack.user.repository.DriverRepository;
import com.camtrack.user.repository.DrivertripsRepository;
import com.camtrack.user.repository.ExceptionRepository;
import com.camtrack.user.repository.ExceptionlevelRepository;
import com.camtrack.user.repository.InvalidateexceptionRepository;
import com.camtrack.user.repository.ListconfigtypesRepository;
import com.camtrack.user.repository.LogusersRepository;
import com.camtrack.user.repository.ParameterconfigRepository;
import com.camtrack.user.repository.ParametertypeRepository;
import com.camtrack.user.repository.ResumexceptionaffiliateRepository;
import com.camtrack.user.repository.ResumexceptionclientRepository;
import com.camtrack.user.repository.ResumexceptiondriverRepository;
import com.camtrack.user.repository.ResumexceptiontransporterRepository;
import com.camtrack.user.repository.ResumexceptionvehicleRepository;
import com.camtrack.user.repository.UserightsRepository;
import com.camtrack.user.repository.UserlogsactivityRepository;
import com.camtrack.user.repository.UsersRepository;
import com.camtrack.user.repository.VehicleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("exceptionservices")
@CacheConfig(cacheNames = "exceptioncaches")
public class ExceptionDaoImp implements ExceptionInterface {
	@Value("${userrrole.affiliateid}")
	private Integer affiliateid;
	@Autowired
	ParametertypeRepository allexceptiontype;
	@Value("${userrrole.clientid}")
	private Integer clientid;
	@Autowired
	CustomeraffiliateRepository cusaffR;
	@Autowired
	CustomerRepository customR;
	@Autowired
	DashboardDaoInterface dashboardDaoInterface;
	@Autowired
	DriverRepository driverR;
	@Autowired
	private Environment environment;
	@Autowired
	ExceptionRepository excepR;
	@Autowired
	InvalidateexceptionRepository invalidR;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	ExceptionlevelRepository levelR;
	@Autowired
	ListconfigtypesRepository listconfig;
	@Autowired
	LogusersRepository logRepo;
	@Autowired
	ParameterconfigRepository paramconfigR;
	@Autowired
	ParametertypeRepository paramertypeR;
	@Autowired
	ResumexceptionaffiliateRepository resumeaffR;
	@Autowired
	ResumexceptionclientRepository resumeclientR;
	@Autowired
	ResumexceptiondriverRepository resumeDriverR;
	@Autowired
	ResumexceptiontransporterRepository resumetransR;
	@Autowired
	ResumexceptionvehicleRepository resumevehR;
	@Value("${userrrole.superadminid}")
	private Integer superadminid;
	@Value("${userrrole.transporterid}")
	private Integer transporterid;
	@Autowired
	TransporterRepository transR;
	@Autowired
	DrivertripsRepository tripsR;
	@Autowired
	UserlogsactivityRepository userlogRepo;
	@Autowired
	UserightsRepository userRR;
	@Autowired
	UsersRepository usersR;
	@Autowired
	VehicleRepository vehR;

	@Override
	@Cacheable(value = "detailexception", key = "new org.springframework.cache.interceptor.SimpleKey(#user.userid, #codeclient,#codeafiliate,#transporter,#codevehicle,#codedriver,#datedebut,#datefin,#listidtypeexception,#record,#alert,#alarm)")
	public List<Map<String, Object>> detailsexception(final User user, final String codeclient,
			final String codeafiliate, final String transporter, final String codevehicle, final String codedriver,
			final String datedebut, final String datefin, final String listidtypeexception, final Boolean record,
			final Boolean alert, final Boolean alarm) {
		final int k = 0;
		String recordalertalarm = "";
		final List<Integer> list = new ArrayList<>();
		if (!Objects.isNull(record) && record) {
			list.add(1);
		}
		if (!Objects.isNull(alert) && alert) {
			list.add(2);
		}
		if (!Objects.isNull(alarm) && alarm) {
			list.add(3);
		}
		if (list.isEmpty()) {
			return null;
		}
		recordalertalarm = list.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(",", "(", ")"));
		final List<Integer> listypexception = new ArrayList<>();
		String sqlin = "";
		String andsql = "";
		String completeRequest = "";
		String allconstructsqlin = "";
		if (Objects.isNull(listidtypeexception) || listidtypeexception.equalsIgnoreCase("0")
				|| listidtypeexception.isEmpty()) {
			if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
				andsql = this.environment.getRequiredProperty("exceptiontypeid.listexceptiontype");
			} else {
				andsql = this.environment.getRequiredProperty("exceptiontypeid.listidfromuserightsbyuserid");
				andsql = andsql.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
			}
		} else {
			andsql = listidtypeexception;
		}
		final Date date01 = Utils.StringToDate("", datedebut, "yyyy-MM-dd");
		Date date2 = Utils.StringToDate("", datefin, "yyyy-MM-dd");
		if (Objects.isNull(date2) || Objects.isNull(date01)) {
			return null;
		}
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date2);
		calendar.add(5, 1);
		date2 = calendar.getTime();
		final String minisql = " bigexception.level in " + recordalertalarm + " and bigexception.exceptiontype in ("
				+ andsql + ") and bigexception.startdatetime >= '" + datedebut + "' and bigexception.startdatetime < '"
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
								completeRequest = this.environment.getRequiredProperty("selectdetailexception");
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
									completeRequest = this.environment.getRequiredProperty("selectdetailexception");
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
								completeRequest = this.environment.getRequiredProperty("selectdetailexception");
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
							completeRequest = this.environment.getRequiredProperty("selectdetailexception");
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
						completeRequest = this.environment.getRequiredProperty("selectdetailexception");
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
							sqlin = this.environment.getRequiredProperty("driveridid.listidriver");
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
					completeRequest = this.environment.getRequiredProperty("selectdetailexception");
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
	@Cacheable(value = "groupinvalidation", key = "new org.springframework.cache.interceptor.SimpleKey(#user.userid, #codeclient,#codeafiliate,#transporter,#codevehicle,#codedriver,#datedebut,#datefin,#listidtypeexception,#record,#alert,#alarm)")
	public ResponseEntity<?> groupinvalidation(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codevehicle, final String codedriver, final String datedebut,
			final String datefin, final String listidtypeexception, final Boolean record, final Boolean alert,
			final Boolean alarm) {
		final List<Integer> list = new ArrayList<>();
		if (record) {
			list.add(1);
		}
		if (alert) {
			list.add(2);
		}
		if (alarm) {
			list.add(3);
		}
		if (list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Success(StaticValues.Levelnotexists, StaticValues.Levelnotexists_Int));
		}
		final Date date01 = Utils.StringToDate("", datedebut, "yyyy-MM-dd");
		Date date2 = Utils.StringToDate("", datefin, "yyyy-MM-dd");
		if (Objects.isNull(date2) || Objects.isNull(date01)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Success(StaticValues.wrongdateformat, StaticValues.wrongdateformat_Int));
		}
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date2);
		calendar.add(5, 1);
		date2 = calendar.getTime();
		List<Integer> listdriver = new ArrayList<>();
		List<Integer> listvehicle = new ArrayList<>();
		List<Integer> listransporter = new ArrayList<>();
		List<Integer> listafiliate = new ArrayList<>();
		List<Integer> listclient = new ArrayList<>();
		List<Integer> result = new ArrayList<>();
		Integer userroleid = 0;
		String sqlin = "";
		try {
			Label_1756: {
				if (Objects.isNull(codedriver) || codedriver.equalsIgnoreCase("")) {
					if (Objects.isNull(codevehicle) || codevehicle.equalsIgnoreCase("")) {
						if (Objects.isNull(transporter) || transporter.equalsIgnoreCase("")) {
							if (Objects.isNull(codeafiliate) || codeafiliate.equalsIgnoreCase("")) {
								if (Objects.isNull(codeclient) || codeclient.equalsIgnoreCase("")) {
									if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
										listclient = this.usersR.findAllActiveCustomerId();
									} else {
										try {
											final List<Integer> listcostomerid = this.userRR
													.findAllCustomerIdOfUserId(user.getCustomerid());
											if (!Objects.isNull(listcostomerid) && !listcostomerid.isEmpty()) {
												listclient.addAll(listcostomerid);
											}
										} catch (Exception ex3) {
										}
									}
								} else {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid) {
										if (user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
											break Label_1756;
										}
									}
									try {
										if (codeclient.equalsIgnoreCase("0")) {
											listclient = new ArrayList<>();
											if (user.getReelrole().getIdtyperole()
													.getUserroleid() == this.superadminid) {
												listclient = this.usersR.findAllActiveCustomerId();
											} else {
												listclient = this.userRR
														.findAllReelCustomerIdOfUserId(user.getUserid());
											}
										} else {
											listclient = Stream.of(codeclient.split(",")).map(String::trim)
													.map(Integer::valueOf).collect(Collectors.toList());
										}
									} catch (Exception ex2) {
										listclient = new ArrayList<>();
									}
								}
							} else {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
										&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
										break Label_1756;
									}
								}
								try {
									if (codeafiliate.equalsIgnoreCase("0")) {
										listafiliate = new ArrayList<>();
										if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
											listafiliate = this.cusaffR.findAllActiveAffiliateId();
										} else {
											userroleid = user.getReelrole().getIdtyperole().getUserroleid();
											if (userroleid == this.clientid) {
												sqlin = this.environment.getRequiredProperty(
														"affiliateid.cuslistidfromuserightsbyuserid");
											} else if (userroleid == this.affiliateid) {
												sqlin = this.environment
														.getRequiredProperty("affiliateid.listidfromuserightsbyuserid");
											}
											sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE,
													user.getUserid() + "");
											listafiliate = this.jdbcTemplate.queryForList(sqlin, (Class) Integer.class);
										}
									} else {
										listafiliate = Stream.of(codeafiliate.split(",")).map(String::trim)
												.map(Integer::valueOf).collect(Collectors.toList());
									}
								} catch (Exception ex2) {
									listafiliate = new ArrayList<>();
								}
							}
						} else {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
									break Label_1756;
								}
							}
							try {
								if (transporter.equalsIgnoreCase("0")) {
									listransporter = new ArrayList<>();
									if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
										listransporter = this.transR.findAllActiveIdTransporter();
									} else {
										userroleid = user.getReelrole().getIdtyperole().getUserroleid();
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
										listransporter = this.jdbcTemplate.queryForList(sqlin, (Class) Integer.class);
									}
								} else {
									listransporter = Stream.of(transporter.split(",")).map(String::trim)
											.map(Integer::valueOf).collect(Collectors.toList());
								}
							} catch (Exception ex2) {
								listransporter = new ArrayList<>();
							}
						}
					} else {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
								break Label_1756;
							}
						}
						try {
							if (codevehicle.equalsIgnoreCase("0")) {
								listvehicle = new ArrayList<>();
								if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
									listvehicle = this.vehR.findAllIdActiveVehicle();
								} else {
									userroleid = user.getReelrole().getIdtyperole().getUserroleid();
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
									listvehicle = this.jdbcTemplate.queryForList(sqlin, (Class) Integer.class);
								}
							} else {
								listvehicle = Stream.of(codevehicle.split(",")).map(String::trim).map(Integer::valueOf)
										.collect(Collectors.toList());
							}
						} catch (Exception ex2) {
							listvehicle = new ArrayList<>();
						}
					}
				} else {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
							break Label_1756;
						}
					}
					try {
						if (codedriver.equalsIgnoreCase("0")) {
							listdriver = new ArrayList<>();
							if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
								listdriver = this.driverR.AllActivesDriver();
							} else {
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
							listdriver = Stream.of(codedriver.split(",")).map(String::trim).map(Integer::valueOf)
									.collect(Collectors.toList());
						}
					} catch (Exception ex2) {
						listdriver = new ArrayList<>();
					}
				}
			}
		} catch (Exception ex4) {
		}
		final List<SummaryException> listexception = new ArrayList<>();
		if (!listclient.isEmpty()) {
			result = this.cusaffR.findAllActiveCustomerAffID(listclient);
			if (!Objects.isNull(result) && !result.isEmpty()) {
				listafiliate.addAll(result);
			}
		}
		List<Integer> listypexception = new ArrayList<>();
		if (Objects.isNull(listidtypeexception) || listidtypeexception.equalsIgnoreCase("0")
				|| listidtypeexception.isEmpty()) {
			if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
				listypexception = this.paramertypeR.findAllExceptionIdSuperAdmin();
			} else {
				listypexception = this.userRR.findAllParametertypeIdOfUserId(user.getUserid());
			}
		} else {
			listypexception = Stream.of(listidtypeexception.split(",")).map(String::trim).map(Integer::valueOf)
					.collect(Collectors.toList());
		}
		final int limitexception = 0;
		final List<ExceptionDetails> listresult = new ArrayList<>();
		if (listvehicle.isEmpty()) {
			listvehicle.add(0);
		}
		if (listypexception.isEmpty()) {
			listypexception.add(0);
		}
		if (listransporter.isEmpty()) {
			listransporter.add(0);
		}
		if (listafiliate.isEmpty()) {
			listafiliate.add(0);
		}
		if (listdriver.isEmpty()) {
			listdriver.add(0);
		}
		final List<NbrtypeExceptions> listcount = new ArrayList<>();
		final Object[][] countbycomments;
		final Object[][] resultexception = countbycomments = this.invalidR.countbycomments(listvehicle, listdriver,
				listafiliate, listransporter, date01, date2, listypexception, list);
		for (final Object[] element : countbycomments) {
			try {
				final NbrtypeExceptions nbr = new NbrtypeExceptions(Utils.ObjectToString(element[1]),
						(double) Utils.castLongObject(element[0]));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(listcount);
	}

	@Override
	@Cacheable(value = "invalidatedexception", key = "new org.springframework.cache.interceptor.SimpleKey(#user.userid, #codeclient,#codeafiliate,#transporter,#codevehicle,#codedriver,#datedebut,#datefin,#listidtypeexception,#record,#alert,#alarm)")
	public List<Map<String, Object>> invalidexception(final User user, final String codeclient,
			final String codeafiliate, final String transporter, final String codevehicle, final String codedriver,
			final String datedebut, final String datefin, final String listidtypeexception, final Boolean record,
			final Boolean alert, final Boolean alarm) {
		final int k = 0;
		String recordalertalarm = "";
		final List<Integer> list = new ArrayList<>();
		if (!Objects.isNull(record) && record) {
			list.add(1);
		}
		if (!Objects.isNull(alert) && alert) {
			list.add(2);
		}
		if (!Objects.isNull(alarm) && alarm) {
			list.add(3);
		}
		if (list.isEmpty()) {
			return null;
		}
		recordalertalarm = list.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(",", "(", ")"));
		final List<Integer> listypexception = new ArrayList<>();
		String sqlin = "";
		String andsql = "";
		String completeRequest = "";
		String allconstructsqlin = "";
		if (Objects.isNull(listidtypeexception) || listidtypeexception.equalsIgnoreCase("0")
				|| listidtypeexception.isEmpty()) {
			if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
				andsql = this.environment.getRequiredProperty("exceptiontypeid.listexceptiontype");
			} else {
				andsql = this.environment.getRequiredProperty("exceptiontypeid.listidfromuserightsbyuserid");
				andsql = andsql.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
			}
		} else {
			andsql = listidtypeexception;
		}
		final Date date01 = Utils.StringToDate("", datedebut, "yyyy-MM-dd");
		Date date2 = Utils.StringToDate("", datefin, "yyyy-MM-dd");
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date2);
		calendar.add(5, 1);
		date2 = calendar.getTime();
		if (Objects.isNull(date2) || Objects.isNull(date01)) {
			return null;
		}
		final String minisql = " bigexception.level in " + recordalertalarm + " and bigexception.exceptiontype in ("
				+ andsql + ") and bigexception.startdatetime >= '" + datedebut + "' and bigexception.startdatetime < '"
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
								completeRequest = this.environment.getRequiredProperty("selectinvaliddetailexception");
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
											.getRequiredProperty("selectinvaliddetailexception");
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
										sqlin = this.environment
												.getRequiredProperty("affiliateid.listidfromuserightsbyuserid");
										sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
									}
								} else {
									sqlin = codeafiliate;
								}
								allconstructsqlin = " bigexception.affiliateid in (" + sqlin + ") and " + minisql;
								completeRequest = this.environment.getRequiredProperty("selectinvaliddetailexception");
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
									sqlin = this.environment
											.getRequiredProperty("transporterid.listidfromuserightsbyuserid");
									sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
								}
							} else {
								sqlin = transporter;
							}
							allconstructsqlin = " bigexception.transporterid in (" + sqlin + ") and " + minisql;
							completeRequest = this.environment.getRequiredProperty("selectinvaliddetailexception");
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
								sqlin = this.environment.getRequiredProperty("vehicleid.listidfromuserightsbyuserid");
								sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
							}
						} else {
							sqlin = codevehicle;
						}
						allconstructsqlin = " bigexception.vehicleid in (" + sqlin + ") and " + minisql;
						completeRequest = this.environment.getRequiredProperty("selectinvaliddetailexception");
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
							sqlin = this.environment.getRequiredProperty("driveridid.listidriver");
						} else {
							sqlin = this.environment.getRequiredProperty("driveridid.listidfromuserightsbyuserid");
							sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
						}
					} else {
						sqlin = codedriver;
					}
					allconstructsqlin = " bigexception.driverid in (" + sqlin + ") and " + minisql;
					completeRequest = this.environment.getRequiredProperty("selectinvaliddetailexception");
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
	public ResponseEntity<?> invalidexceptionupdatestatus(final User user,
			final ListInvalidExceptionBean invalidorvalid) {
		final List<Integer> listexceptionid = Arrays.asList(invalidorvalid.getListids());
		final String comments = invalidorvalid.getComments();
		final String userscomments = invalidorvalid.getUsercomments();
		final Boolean activeornot = invalidorvalid.getInvalidorvalid();
		if (listexceptionid.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Success(StaticValues.ListExceptionIDNotFound, StaticValues.ListExceptionIDNotFound_Int));
		}
		if (Objects.isNull(activeornot)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Success(StaticValues.Invalidorvalid, StaticValues.Invalidorvalid_Int));
		}
		final ObjectMapper objectMapper = new ObjectMapper();
		final List<Integer> exceptiontodelete = new ArrayList<>();
		final Date datenew = new Date();
		final Listconfigtypes paramconfigs = this.listconfig.findById(Utils.ExceptionTypeconfig()).orElse(null);
		if (activeornot) {
			final List<com.camtrack.entities.Exception> listexception = this.excepR
					.listretreiveexception(listexceptionid);
			for (final com.camtrack.entities.Exception exception : listexception) {
				try {
					final Invalidateexception invalid = Utils.ExceptionToInvalidate(exception, user);
					if (!Objects.isNull(comments)) {
						invalid.setComments(comments);
					}
					if (!Objects.isNull(userscomments)) {
						invalid.setUserscomments(userscomments);
					}
					invalid.setCreateby(user.getUserid());
					invalid.setUpdateby(user.getUserid());
					invalid.setCreatedate(datenew);
					invalid.setUpdatedate(datenew);
					this.invalidR.saveAndFlush(invalid);
					if (Utils.countnumberligne(Invalidateexception.log) > 1) {
						final Userlogsactivity loguser = new Userlogsactivity(paramconfigs);
						loguser.setDates(datenew);
						loguser.setUserid(user);
						loguser.setLogsinfos(Invalidateexception.log);
						this.userlogRepo.saveAndFlush(loguser);
					}
					exceptiontodelete.add(exception.getExceptionid());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (!exceptiontodelete.isEmpty()) {
					this.excepR.deleteexception(listexceptionid);
				}
			}
		} else {
			final List<Invalidateexception> listexception2 = this.invalidR.listretreiveexception(listexceptionid);
			for (final Invalidateexception exception2 : listexception2) {
				try {
					final com.camtrack.entities.Exception exceptions = Utils.InvalidateToException(exception2, user);
					exceptions.setRestoreduserid(user.getUserid());
					exceptions.setRestauredate(new Date());
					if (!Objects.isNull(userscomments)) {
						exceptions.setUsercoments(userscomments);
					}
					exceptions.setIsrestored(Short.valueOf("1"));
					this.excepR.saveAndFlush(exceptions);
					if (Utils.countnumberligne(com.camtrack.entities.Exception.log) > 1) {
						final Userlogsactivity loguser = new Userlogsactivity(paramconfigs);
						loguser.setDates(datenew);
						loguser.setUserid(user);
						loguser.setLogsinfos(com.camtrack.entities.Exception.log);
						this.userlogRepo.saveAndFlush(loguser);
					}
					exceptiontodelete.add(exception2.getExceptionid());
				} catch (Exception ex2) {
				}
				if (!exceptiontodelete.isEmpty()) {
					this.invalidR.deleteexception(listexceptionid);
				}
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(new Success(StaticValues.success, StaticValues.success_Int));
	}

	@Override
	@Cacheable("listexceptlevel")
	public List<Map<String, Object>> listexceptionlevel() {
		final String completeRequest = "select exceptionlevelid as id,name as nm from  exceptionlevel excepts  where excepts.exceptionlevelid <> 0";
		return this.jdbcTemplate.queryForList(completeRequest);
	}

	@Override
	@Cacheable("listexcepttype")
	public List<?> listExceptionTypes() {
		return this.allexceptiontype.findAllExceptionId();
	}

	public ResponseEntity<?> OLDinvalidexception(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codevehicle, final String codedriver, final String datedebut,
			final String datefin, final String listidtypeexception, final Boolean record, final Boolean alert,
			final Boolean alarm) {
		final List<Integer> list = new ArrayList<>();
		if (record) {
			list.add(1);
		}
		if (alert) {
			list.add(2);
		}
		if (alarm) {
			list.add(3);
		}
		if (list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Success(StaticValues.Levelnotexists, StaticValues.Levelnotexists_Int));
		}
		final Date date01 = Utils.StringToDate("", datedebut, "yyyy-MM-dd");
		Date date2 = Utils.StringToDate("", datefin, "yyyy-MM-dd");
		if (Objects.isNull(date2) || Objects.isNull(date01)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Success(StaticValues.wrongdateformat, StaticValues.wrongdateformat_Int));
		}
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date2);
		calendar.add(5, 1);
		date2 = calendar.getTime();
		List<Integer> listdriver = new ArrayList<>();
		List<Integer> listvehicle = new ArrayList<>();
		List<Integer> listransporter = new ArrayList<>();
		List<Integer> listafiliate = new ArrayList<>();
		List<Integer> listclient = new ArrayList<>();
		List<Integer> result = new ArrayList<>();
		try {
			Label_1399: {
				if (Objects.isNull(codedriver) || codedriver.equalsIgnoreCase("")) {
					if (Objects.isNull(codevehicle) || codevehicle.equalsIgnoreCase("")) {
						if (Objects.isNull(transporter) || transporter.equalsIgnoreCase("")) {
							if (Objects.isNull(codeafiliate) || codeafiliate.equalsIgnoreCase("")) {
								if (Objects.isNull(codeclient) || codeclient.equalsIgnoreCase("")) {
									if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
										listclient = this.usersR.findAllActiveCustomerId();
									} else {
										try {
											final List<Integer> listcostomerid = this.userRR
													.findAllCustomerIdOfUserId(user.getCustomerid());
											if (!Objects.isNull(listcostomerid) && !listcostomerid.isEmpty()) {
												listclient.addAll(listcostomerid);
											}
										} catch (Exception ex2) {
										}
									}
								} else {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid) {
										if (user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
											break Label_1399;
										}
									}
									try {
										if (codeclient.equalsIgnoreCase("0")) {
											listclient = new ArrayList<>();
											if (user.getReelrole().getIdtyperole()
													.getUserroleid() == this.superadminid) {
												listclient = this.usersR.findAllActiveCustomerId();
											} else {
												listclient = this.userRR
														.findAllReelCustomerIdOfUserId(user.getUserid());
											}
										} else {
											listclient = Stream.of(codeclient.split(",")).map(String::trim)
													.map(Integer::valueOf).collect(Collectors.toList());
										}
									} catch (Exception ex) {
										listclient = new ArrayList<>();
									}
								}
							} else {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
										&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
										break Label_1399;
									}
								}
								try {
									if (codeafiliate.equalsIgnoreCase("0")) {
										listafiliate = new ArrayList<>();
										if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
											listafiliate = this.cusaffR.findAllActiveAffiliateId();
										} else {
											listafiliate = this.userRR.findAllReelAffiliateIdOfUserId(user.getUserid());
										}
									} else {
										listafiliate = Stream.of(codeafiliate.split(",")).map(String::trim)
												.map(Integer::valueOf).collect(Collectors.toList());
									}
								} catch (Exception ex) {
									listafiliate = new ArrayList<>();
								}
							}
						} else {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
									break Label_1399;
								}
							}
							try {
								if (transporter.equalsIgnoreCase("0")) {
									listransporter = new ArrayList<>();
									if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
										listransporter = this.transR.findAllActiveIdTransporter();
									} else {
										listransporter = this.userRR.findAllReelTransporterIdOfUserId(user.getUserid());
									}
								} else {
									listransporter = Stream.of(transporter.split(",")).map(String::trim)
											.map(Integer::valueOf).collect(Collectors.toList());
								}
							} catch (Exception ex) {
								listransporter = new ArrayList<>();
							}
						}
					} else {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
								break Label_1399;
							}
						}
						try {
							if (codevehicle.equalsIgnoreCase("0")) {
								listvehicle = new ArrayList<>();
								if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
									listvehicle = this.vehR.findAllIdActiveVehicle();
								} else {
									final List<Integer> transid = this.userRR
											.findAllReelTransporterIdOfUserId(user.getUserid());
									if (!Objects.isNull(transid) && !transid.isEmpty()) {
										listvehicle = this.vehR.findAllActiveIdVehicleTransporter(transid);
									}
								}
							} else {
								listvehicle = Stream.of(codevehicle.split(",")).map(String::trim).map(Integer::valueOf)
										.collect(Collectors.toList());
							}
						} catch (Exception ex) {
							listvehicle = new ArrayList<>();
						}
					}
				} else {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
							break Label_1399;
						}
					}
					try {
						if (codedriver.equalsIgnoreCase("0")) {
							listdriver = new ArrayList<>();
							if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
								listdriver = this.driverR.AllActivesDriver();
							} else {
								final List<Integer> transid = this.userRR
										.findAllReelTransporterIdOfUserId(user.getUserid());
								if (!Objects.isNull(transid) && !transid.isEmpty()) {
									listdriver = this.driverR.AlldriverIdOfTransporter(transid);
								}
							}
						} else {
							listdriver = Stream.of(codedriver.split(",")).map(String::trim).map(Integer::valueOf)
									.collect(Collectors.toList());
						}
					} catch (Exception ex) {
						listdriver = new ArrayList<>();
					}
				}
			}
		} catch (Exception ex3) {
		}
		final List<SummaryException> listexception = new ArrayList<>();
		if (!listclient.isEmpty()) {
			result = this.cusaffR.findAllActiveCustomerAffID(listclient);
			if (!Objects.isNull(result) && !result.isEmpty()) {
				listafiliate.addAll(result);
			}
		}
		List<Integer> listypexception = new ArrayList<>();
		if (Objects.isNull(listidtypeexception) || listidtypeexception.equalsIgnoreCase("0")
				|| listidtypeexception.isEmpty()) {
			if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
				listypexception = this.paramertypeR.findAllExceptionIdSuperAdmin();
			} else {
				listypexception = this.userRR.findAllParametertypeIdOfUserId(user.getUserid());
			}
		} else {
			listypexception = Stream.of(listidtypeexception.split(",")).map(String::trim).map(Integer::valueOf)
					.collect(Collectors.toList());
		}
		final List<Object[]> listdates = Utils.getReelMonthOrDaysBetweenDates(date01, date2, true);
		Integer limitexception = 0;
		final List<ExceptionDetails> listresult = new ArrayList<>();
		if (listvehicle.isEmpty()) {
			listvehicle.add(0);
		}
		if (listypexception.isEmpty()) {
			listypexception.add(0);
		}
		if (listransporter.isEmpty()) {
			listransporter.add(0);
		}
		if (listafiliate.isEmpty()) {
			listafiliate.add(0);
		}
		if (listdriver.isEmpty()) {
			listdriver.add(0);
		}
		for (final Object[] objects : listdates) {
			final Date date3 = (Date) objects[0];
			final Date date4 = (Date) objects[1];
			limitexception = 0;
			for (List<Invalidateexception> resultexception = this.invalidR.alldetailsExceptionLevel(listvehicle,
					listdriver, listafiliate, listransporter, date3, date4, listypexception, list,
					limitexception); !Objects.isNull(resultexception)
							&& !resultexception.isEmpty(); resultexception = this.invalidR.alldetailsExceptionLevel(
									listvehicle, listdriver, listafiliate, listransporter, date3, date4,
									listypexception, list, limitexception)) {
				for (final Invalidateexception exception : resultexception) {
					final ExceptionDetails smalldetails = new ExceptionDetails();
					final Customeraffiliate affiliate = exception.getAffiliateid();
					if (!Objects.isNull(affiliate)) {
						smalldetails.setAffiliate(this.cusaffR.nameobject(affiliate.getAffiliateid()));
						final Customer cus = affiliate.getCustomerid();
						if (!Objects.isNull(cus)) {
							smalldetails.setClient(this.customR.nameobject(cus.getCustomerid()));
						}
					}
					final Transporter trans = exception.getTransporterid();
					if (!Objects.isNull(trans)) {
						smalldetails.setTransporter(this.transR.nameobject(trans.getTransporterid()));
					}
					final Driver driver = exception.getDriverid();
					if (!Objects.isNull(driver)) {
						smalldetails.setDriver(this.driverR.nameobject(driver.getDriverid()));
					}
					final Vehicle veh = exception.getVehicleid();
					if (!Objects.isNull(veh)) {
						smalldetails.setVehicle(this.vehR.nameobject(veh.getVehicleid()));
					}
					final Parametertype exceptiontype = exception.getExceptiontype();
					if (!Objects.isNull(exceptiontype)) {
						smalldetails.setException(exceptiontype.getName());
						smalldetails.setExceptiontypeid(exceptiontype.getParametertypeid());
					}
					smalldetails
							.setStartdatetimes(Utils.DateFormat(exception.getStartdatetime(), "yyyy-MM-dd HH:mm:ss"));
					smalldetails.setEndates(Utils.DateFormat(exception.getEnddatetime(), "yyyy-MM-dd HH:mm:ss"));
					smalldetails.setEndpoint(exception.getEndgps());
					smalldetails.setStartpoint(exception.getStartgps());
					smalldetails.setEvidenceactions(exception.getEvidence());
					final Integer exceptionid = exception.getExceptionid();
					if (!Objects.isNull(exceptionid) && exceptionid > limitexception) {
						limitexception = exceptionid;
					}
					smalldetails.setExceptionId(exceptionid);
					smalldetails.setExceptiondistances(exception.getDistanceunderexception());
					smalldetails.setMaxbreak(exception.getMaxbreak());
					smalldetails.setMaxvalue(exception.getMaxvalue());
					smalldetails.setNobreak(exception.getNumberofbreak());
					smalldetails.setPreventivesdates(
							Utils.DateFormat(exception.getPreventivedatetime(), "yyyy-MM-dd HH:mm:ss"));
					smalldetails.setPreventivetreshold(exception.getPreventivethreshold());
					smalldetails.setRequireddistances(exception.getRequiredbreak());
					smalldetails.setThresold(exception.getThreshold());
					smalldetails.setTotaldistances(exception.getTotaldistance());
					smalldetails.setExceeded_missing_duration(exception.getTimeexceeded());
					smalldetails.setTotalduration(exception.getTotalduration());
					smalldetails.setComments(exception.getComments());
					final Integer userid = exception.getCreateby();
					if (!Objects.isNull(userid)) {
						final String nameuser = this.usersR.findNameUser(userid);
						if (!Objects.isNull(nameuser)) {
							smalldetails.setInvalidateusername(nameuser);
						}
						smalldetails.setInvalidateuserid(userid);
					}
					smalldetails.setInvalidatedate(Utils.DateFormat(exception.getCreatedate(), "yyyy-MM-dd HH:mm:ss"));
					final Exceptionlevel level = exception.getLevel();
					if (!Objects.isNull(level)) {
						smalldetails.setExceptionlevel(level.getName());
					}
					listresult.add(smalldetails);
				}
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(listresult);
	}

	@Override
	@Cacheable("listreelexception")
	public List<?> filterexception() {
		// TODO Auto-generated method stub
		return this.allexceptiontype.filterexception();
	}

	/**
	 * @Override @Cacheable("listexcepttype") public List<?> listExceptionTypes() {
	 *           return this.allexceptiontype.findAllExceptionId(); }reelexception
	 */
}
