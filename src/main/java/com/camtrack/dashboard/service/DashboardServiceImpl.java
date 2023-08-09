//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.dashboard.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.camtrack.affiliate.repository.CustomeraffiliateRepository;
import com.camtrack.bean.ResumeResult;
import com.camtrack.bean.ResumeResults;
import com.camtrack.config.StaticValues;
import com.camtrack.config.Utils;
import com.camtrack.dashboard.dao.DashboardDaoInterface;
import com.camtrack.entities.Activesonmindistances;
import com.camtrack.entities.Customer;
import com.camtrack.entities.Customeraffiliate;
import com.camtrack.entities.Driver;
import com.camtrack.entities.Drivertrips;
import com.camtrack.entities.Exceptions;
import com.camtrack.entities.Logusers;
import com.camtrack.entities.NbrtypeExceptions;
import com.camtrack.entities.Parameterconfig;
import com.camtrack.entities.Reelroleusers;
import com.camtrack.entities.Resumexceptionaffiliate;
import com.camtrack.entities.Resumexceptionclient;
import com.camtrack.entities.Resumexceptiondriver;
import com.camtrack.entities.Resumexceptiontransporter;
import com.camtrack.entities.Resumexceptionvehicle;
import com.camtrack.entities.Success;
import com.camtrack.entities.SummaryAllException;
import com.camtrack.entities.SummaryAllIntegerException;
import com.camtrack.entities.Transporter;
import com.camtrack.entities.User;
import com.camtrack.entities.Userrole;
import com.camtrack.entities.Vehicle;
import com.camtrack.entities.Worktime;
import com.camtrack.reports.bean.TotalDistances;
import com.camtrack.reports.bean.TrendBean;
import com.camtrack.reports.bean.TrendTotalDistanceBean;
import com.camtrack.transporter.repository.TransporterRepository;
import com.camtrack.user.repository.CustomerRepository;
import com.camtrack.user.repository.DriverRepository;
import com.camtrack.user.repository.DriveractivitysummaryRepository;
import com.camtrack.user.repository.DrivertripsRepository;
import com.camtrack.user.repository.ExceptionRepository;
import com.camtrack.user.repository.InvalidateexceptionRepository;
import com.camtrack.user.repository.LogusersRepository;
import com.camtrack.user.repository.ParameterconfigRepository;
import com.camtrack.user.repository.ParametertypeRepository;
import com.camtrack.user.repository.ResumexceptionaffiliateRepository;
import com.camtrack.user.repository.ResumexceptionclientRepository;
import com.camtrack.user.repository.ResumexceptiondriverRepository;
import com.camtrack.user.repository.ResumexceptiontransporterRepository;
import com.camtrack.user.repository.ResumexceptionvehicleRepository;
import com.camtrack.user.repository.UserightsRepository;
import com.camtrack.user.repository.UsersRepository;
import com.camtrack.user.repository.VehicleRepository;
import com.camtrack.user.repository.VehicleactivitysummaryRepository;

@Service("dashboardService")
@PropertySource({ "classpath:sqlqueries.properties" })
@CacheConfig(cacheNames = "dashboardcaches")
public class DashboardServiceImpl implements DashboardServiceInterface {
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
	UserightsRepository userRR;
	@Autowired
	UsersRepository usersR;
	@Autowired
	VehicleactivitysummaryRepository vehactsum;
	@Autowired
	VehicleRepository vehR;

	@Override
	@Cacheable(value = "activedriverandvehicle", key = "new org.springframework.cache.interceptor.SimpleKey(#user.userid, #codeclient,#codeafiliate,#transporter,#codevehicle,#codedriver,#datedebut,#datefin)")
	public ResponseEntity<?> activedriverandvehicle(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codevehicle, final String codedriver, final String datedebut,
			final String datefin) {
		final Vehicle veh = null;
		Date date1 = Utils.StringToDate("", datedebut, "yyyy-MM-dd");
		Date date2 = Utils.StringToDate("", datefin, "yyyy-MM-dd");
		if (Objects.isNull(date2) || Objects.isNull(date1)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.wrongdateformat, StaticValues.wrongdateformat_Int));
		}
		date1 = Utils.StringToDate("", datedebut + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
		date2 = Utils.StringToDateTimezone("", datefin + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
		List<Integer> listdriver = new ArrayList<>();
		final List<Integer> listdrivers = new ArrayList<>();
		List<Integer> listvehicle = new ArrayList<>();
		List<Integer> listransporter = new ArrayList<>();
		List<Integer> listafiliate = new ArrayList<>();
		List<Integer> listclient = new ArrayList<>();
		final List<Integer> result = new ArrayList<>();
		final List<Exceptions> resultexception = new ArrayList<>();
		final List<com.camtrack.entities.Exception> listexceptionothers = new ArrayList<>();
		final List<SummaryAllException> rankingtransporter = new ArrayList<>();
		final List<SummaryAllIntegerException> rankingInteger = new ArrayList<>();
		final Integer valeur = user.getReelrole().getIdtyperole().getUserroleid();
		String sqlin = "";
		try {
			Label_1845: {
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
											break Label_1845;
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
									if (Objects.isNull(listclient)) {
										listclient = new ArrayList<>();
									}
								}
							} else {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
										&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
										break Label_1845;
									}
								}
								try {
									if (codeafiliate.equalsIgnoreCase("0")) {
										listafiliate = new ArrayList<>();
										if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
											listafiliate = this.cusaffR.findAllActiveAffiliateId();
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
											listafiliate = this.jdbcTemplate.queryForList(sqlin, (Class) Integer.class);
										}
									} else {
										listafiliate = Stream.of(codeafiliate.split(",")).map(String::trim)
												.map(Integer::valueOf).collect(Collectors.toList());
									}
								} catch (Exception ex) {
									listafiliate = new ArrayList<>();
								}
								if (Objects.isNull(listafiliate)) {
									listafiliate = new ArrayList<>();
								}
							}
						} else {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
									break Label_1845;
								}
							}
							try {
								if (transporter.equalsIgnoreCase("0")) {
									listransporter = new ArrayList<>();
									if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
										listransporter = this.transR.findAllActiveIdTransporter();
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
										listransporter = this.jdbcTemplate.queryForList(sqlin, (Class) Integer.class);
									}
								} else {
									listransporter = Stream.of(transporter.split(",")).map(String::trim)
											.map(Integer::valueOf).collect(Collectors.toList());
								}
							} catch (Exception ex) {
								listransporter = new ArrayList<>();
							}
							if (Objects.isNull(listransporter)) {
								listransporter = new ArrayList<>();
							}
						}
					} else {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
								break Label_1845;
							}
						}
						try {
							if (codevehicle.equalsIgnoreCase("0")) {
								listvehicle = new ArrayList<>();
								if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
									listvehicle = this.vehR.findAllIdActiveVehicle();
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
									listvehicle = this.jdbcTemplate.queryForList(sqlin, (Class) Integer.class);
								}
							} else {
								listvehicle = Stream.of(codevehicle.split(",")).map(String::trim).map(Integer::valueOf)
										.collect(Collectors.toList());
							}
						} catch (Exception ex) {
							listvehicle = new ArrayList<>();
						}
						if (Objects.isNull(listvehicle)) {
							listvehicle = new ArrayList<>();
						}
					}
				} else {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
							break Label_1845;
						}
					}
					try {
						if (codedriver.equalsIgnoreCase("0")) {
							listdriver = new ArrayList<>();
							if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
								listdriver = this.driverR.AllActivesDriver();
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
								listdriver = this.jdbcTemplate.queryForList(sqlin, (Class) Integer.class);
							}
						} else {
							listdriver = Stream.of(codedriver.split(",")).map(String::trim).map(Integer::valueOf)
									.collect(Collectors.toList());
						}
					} catch (Exception ex) {
						listdriver = new ArrayList<>();
					}
					if (Objects.isNull(listdriver)) {
						listdriver = new ArrayList<>();
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		List<Integer> customveh = new ArrayList<>();
		List<Integer> listtrans = new ArrayList<>();
		List<Integer> listId = new ArrayList<>();
		final List<Vehicle> listveh = new ArrayList<>();
		final HashMap<Integer, List<Integer>> list = new HashMap<>();
		final HashMap<Integer, Double> minAffDist = new HashMap<>();
		Parameterconfig param = null;
		final int afforcusorother = 0;
		boolean isvehicle = false;
		boolean isdriver = false;
		if (!Objects.isNull(listdriver) && !listdriver.isEmpty()) {
			isdriver = true;
		} else if (!Objects.isNull(listvehicle) && !listvehicle.isEmpty()) {
			isvehicle = true;
		} else if (!Objects.isNull(listransporter) && !listransporter.isEmpty()) {
			final List<Transporter> listrans = this.transR.findAllById(listransporter);
			for (final Transporter transporterid : listrans) {
				final Customeraffiliate affiliate = transporterid.getAffiliateid();
				List<Integer> listint = new ArrayList<>();
				listint.add(transporterid.getTransporterid());
				customveh = this.vehR.findAllActiveIdVehicleTransporter(listint);
				if (list.containsKey(affiliate.getAffiliateid())) {
					listint = list.get(affiliate.getAffiliateid());
					listint.addAll(customveh);
					list.put(affiliate.getAffiliateid(), listint);
				} else {
					list.put(affiliate.getAffiliateid(), customveh);
				}
				final Customeraffiliate resultaff = this.cusaffR
						.findById(transporterid.getAffiliateid().getAffiliateid()).orElse(null);
				if (!Objects.isNull(resultaff)) {
					final List<Parameterconfig> listparam = this.paramconfigR.findAllParamconfig(
							resultaff.getCustomerid().getCustomerid(), resultaff.getAffiliateid(),
							Utils.activeFleetParameterid);
					if (!Objects.isNull(listparam) && !listparam.isEmpty()) {
						param = listparam.get(0);
					}
					if (!Objects.isNull(param)) {
						if (!Objects.isNull(param.getMinimumdistance())) {
							minAffDist.put(resultaff.getAffiliateid(), param.getMinimumdistance().doubleValue());
						} else {
							minAffDist.put(resultaff.getAffiliateid(), Utils.defaultmindistance);
						}
					} else {
						minAffDist.put(resultaff.getAffiliateid(), Utils.defaultmindistance);
					}
				}
			}
		} else if (!Objects.isNull(listafiliate) && !listafiliate.isEmpty()) {
			final List<Customeraffiliate> listcusaff = this.cusaffR.findAllById(listafiliate);
			for (final Customeraffiliate customeraffiliate : listcusaff) {
				listtrans = new ArrayList<>();
				listtrans.add(customeraffiliate.getAffiliateid());
				listtrans = this.transR.findAllActiveTransporterIdAffiliate(listtrans);
				if (!Objects.isNull(listtrans) && !listtrans.isEmpty()) {
					customveh = this.vehR.findAllActiveIdVehicleTransporter(listtrans);
					if (list.containsKey(customeraffiliate.getAffiliateid())) {
						final List<Integer> listint = list.get(customeraffiliate.getAffiliateid());
						listint.addAll(customveh);
						list.put(customeraffiliate.getAffiliateid(), listint);
					} else {
						list.put(customeraffiliate.getAffiliateid(), customveh);
					}
					final List<Parameterconfig> listparam = this.paramconfigR.findAllParamconfig(
							customeraffiliate.getCustomerid().getCustomerid(), customeraffiliate.getAffiliateid(),
							Utils.activeFleetParameterid);
					if (!Objects.isNull(listparam) && !listparam.isEmpty()) {
						param = listparam.get(0);
					}
					if (!Objects.isNull(param)) {
						if (!Objects.isNull(param.getMinimumdistance())) {
							minAffDist.put(customeraffiliate.getAffiliateid(),
									param.getMinimumdistance().doubleValue());
						} else {
							minAffDist.put(customeraffiliate.getAffiliateid(), Utils.defaultmindistance);
						}
					} else {
						minAffDist.put(customeraffiliate.getAffiliateid(), Utils.defaultmindistance);
					}
				}
			}
		} else if (!Objects.isNull(listclient) && !listclient.isEmpty()) {
			final List<Customer> listcustom = this.customR.findAllById(listclient);
			for (final Customer cus : listcustom) {
				listId = new ArrayList<>();
				listId.add(cus.getCustomerid());
				final List<Customeraffiliate> listcusaff = this.cusaffR.findAllActiveCustomer(listId);
				for (final Customeraffiliate affiliates : listcusaff) {
					listtrans = new ArrayList<>();
					listtrans.add(affiliates.getAffiliateid());
					listtrans = this.transR.findAllActiveTransporterIdAffiliate(listtrans);
					if (!Objects.isNull(listtrans) && !listtrans.isEmpty()) {
						customveh = this.vehR.findAllActiveIdVehicleTransporter(listtrans);
						if (list.containsKey(affiliates.getAffiliateid())) {
							final List<Integer> listint = list.get(affiliates.getAffiliateid());
							listint.addAll(customveh);
							list.put(affiliates.getAffiliateid(), listint);
						} else {
							list.put(affiliates.getAffiliateid(), customveh);
						}
						try {
							final List<Parameterconfig> listparam = this.paramconfigR.findAllParamconfig(
									cus.getCustomerid(), affiliates.getAffiliateid(), Utils.activeFleetParameterid);
							if (!Objects.isNull(listparam) && !listparam.isEmpty()) {
								param = listparam.get(0);
							}
						} catch (Exception ex2) {
							param = null;
						}
						if (!Objects.isNull(param)) {
							if (!Objects.isNull(param.getMinimumdistance())) {
								minAffDist.put(affiliates.getAffiliateid(), param.getMinimumdistance().doubleValue());
							} else {
								minAffDist.put(affiliates.getAffiliateid(), Utils.defaultmindistance);
							}
						} else {
							minAffDist.put(affiliates.getAffiliateid(), Utils.defaultmindistance);
						}
					}
				}
			}
		} else {
			final List<Customer> listcustom = this.userRR.findAllCustomerOfUserId(user.getUserid());
			for (final Customer cus : listcustom) {
				listId = new ArrayList<>();
				listId.add(cus.getCustomerid());
				final List<Customeraffiliate> listcusaff = this.cusaffR.findAllActiveCustomer(listId);
				for (final Customeraffiliate affiliates : listcusaff) {
					listtrans = new ArrayList<>();
					listtrans.add(affiliates.getAffiliateid());
					listtrans = this.transR.findAllActiveTransporterIdAffiliate(listtrans);
					if (!Objects.isNull(listtrans) && !listtrans.isEmpty()) {
						customveh = this.vehR.findAllActiveIdVehicleTransporter(listtrans);
						if (list.containsKey(affiliates.getAffiliateid())) {
							final List<Integer> listint = list.get(affiliates.getAffiliateid());
							listint.addAll(customveh);
							list.put(affiliates.getAffiliateid(), listint);
						} else {
							list.put(affiliates.getAffiliateid(), customveh);
						}
						final List<Parameterconfig> listparam = this.paramconfigR.findAllParamconfig(
								affiliates.getCustomerid().getCustomerid(), affiliates.getAffiliateid(),
								Utils.activeFleetParameterid);
						if (!Objects.isNull(listparam) && !listparam.isEmpty()) {
							param = listparam.get(0);
						}
						if (!Objects.isNull(param)) {
							if (!Objects.isNull(param.getMinimumdistance())) {
								minAffDist.put(affiliates.getAffiliateid(), param.getMinimumdistance().doubleValue());
							} else {
								minAffDist.put(affiliates.getAffiliateid(), Utils.defaultmindistance);
							}
						} else {
							minAffDist.put(affiliates.getAffiliateid(), Utils.defaultmindistance);
						}
					}
				}
			}
		}
		int nbrtotalveh = 0;
		int nbvehicleactif = 0;
		int nbdriveractif = 0;
		int nbdriverreel = 0;
		final List<Integer> allvehicleId = new ArrayList<>();
		List<Integer> alldriverid = new ArrayList<>();
		if (isdriver) {
			final BigDecimal big = new BigDecimal(Utils.defaultmindistance);
			Long vehordriver = this.vehactsum.listallvehicleactiffromdriver(listdriver, big, date1, date2);
			nbvehicleactif = vehordriver.intValue();
			vehordriver = this.vehactsum.listalldriveractiffromdriver(listdriver, big, date1, date2);
			nbdriveractif = vehordriver.intValue();
			nbdriverreel = listdriver.size();
			nbrtotalveh = nbvehicleactif;
		} else if (isvehicle) {
			final BigDecimal big = new BigDecimal(Utils.defaultmindistance);
			Long vehordriver = this.vehactsum.listalldriveractif(listvehicle, big, date1, date2);
			nbvehicleactif = vehordriver.intValue();
			vehordriver = this.vehactsum.listalldriveractiffromdriver2(listvehicle, big, date1, date2);
			nbdriveractif = vehordriver.intValue();
			nbdriverreel = vehordriver.intValue();
			nbrtotalveh = listvehicle.size();
		} else if (!list.isEmpty()) {
			for (final Map.Entry entry : list.entrySet()) {
				listId = (List<Integer>) entry.getValue();
				allvehicleId.addAll(listId);
				nbrtotalveh += listId.size();
				final Integer affiliatekey = Utils.castIntegerObject(entry.getKey());
				if (minAffDist.containsKey(affiliatekey)) {
					try {
						final BigDecimal big = new BigDecimal(minAffDist.get(entry.getKey()));
						Long vehordriver = this.vehactsum.listalldriveractif(listId, big, date1, date2);
						final Integer old = vehordriver.intValue();
						nbvehicleactif += old;
						vehordriver = this.vehactsum.listalldriveractiffromdriver2(listId, big, date1, date2);
						if (vehordriver.intValue() > 0) {
							++vehordriver;
							--vehordriver;
						}
						nbdriveractif += vehordriver.intValue();
					} catch (Exception ex4) {
					}
				}
			}
			alldriverid = this.driverR.alldriverUser(allvehicleId);
			if (!Objects.isNull(alldriverid) && !alldriverid.isEmpty()) {
				nbdriverreel = alldriverid.size();
			} else {
				nbdriverreel = 0;
			}
		}
		final ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		final HttpServletRequest req = sra.getRequest();
		final Logusers log = new Logusers(user.getName(), "AUTHENTICATION_SUCCESS", "Request On Vehicle Status",
				new Date(), req.getRemoteAddr());
		this.logRepo.saveAndFlush(log);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Activesonmindistances(nbvehicleactif, nbrtotalveh, nbdriveractif, nbdriverreel));
	}

	@Override
	@Cacheable(value = "activeordriverpersearch", key = "new org.springframework.cache.interceptor.SimpleKey(#user.userid, #codeclient,#codeafiliate,#transporter,#codevehicle,#codedriver,#datedebut,#datefin)")
	public ResumeResult actvehordrivpersearch(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codevehicle, final String codedriver, final String datedebut,
			final String datefin) {
		final List<Integer> listypexception = new ArrayList<>();
		String sqlin = "";
		final String andsql = "";
		String completeRequest = "";
		String allconstructsqlin = "";
		final Date date01 = Utils.StringToDate("", datedebut, "yyyy-MM-dd");
		Date date2 = Utils.StringToDate("", datefin, "yyyy-MM-dd");
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date2);
		calendar.add(5, 1);
		date2 = calendar.getTime();
		if (Objects.isNull(date2) || Objects.isNull(date01)) {
			return new ResumeResult((List) null, (List) null, StaticValues.wrongdateformat_Int);
		}
		final String minisql = " bigexception.activitydate >= '" + Utils.DateFormat(date01, "yyyy-MM-dd")
				+ "' and bigexception.activitydate < '" + Utils.DateFormat(date2, "yyyy-MM-dd") + "'";
		final List<Integer> listdriver = new ArrayList<>();
		final List<Integer> listvehicle = new ArrayList<>();
		final List<Integer> listransporter = new ArrayList<>();
		final List<Integer> listafiliate = new ArrayList<>();
		final List<Integer> listclient = new ArrayList<>();
		final List<Integer> result = new ArrayList<>();
		List<Map<String, Object>> resultlist = null;
		List<Map<String, Object>> allresukt = new ArrayList<>();
		final List<Map<String, Object>> allinvalidresukt = new ArrayList<>();
		Integer typeInfos = -1;
		try {
			if (Objects.isNull(codedriver) || codedriver.equalsIgnoreCase("")) {
				if (Objects.isNull(codevehicle) || codevehicle.equalsIgnoreCase("")) {
					if (Objects.isNull(transporter) || transporter.equalsIgnoreCase("")) {
						if (Objects.isNull(codeafiliate) || codeafiliate.equalsIgnoreCase("")) {
							if (!Objects.isNull(codeclient)) {
								if (!codeclient.equalsIgnoreCase("")) {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid) {
										if (user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
											return new ResumeResult(allresukt, (List) null, typeInfos);
										}
									}
									try {
										if (codeclient.equalsIgnoreCase("0")) {
											if (user.getReelrole().getIdtyperole()
													.getUserroleid() == this.superadminid) {
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
										allconstructsqlin = " bigexception.clientid in (" + sqlin + ") and " + minisql;
										completeRequest = this.environment.getRequiredProperty("activevehicle.client");
										completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
										resultlist = this.jdbcTemplate.queryForList(completeRequest);
										allresukt.addAll(resultlist);
										typeInfos = 1;
									} catch (Exception ex2) {
									}
									return new ResumeResult(allresukt, (List) null, typeInfos);
								}
							}
							try {
								if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
									sqlin = this.environment.getRequiredProperty("clientid.listidclient");
								} else {
									try {
										sqlin = this.environment
												.getRequiredProperty("clientid.listidfromuserightsbyuserid");
										sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
									} catch (Exception ex3) {
									}
								}
								allconstructsqlin = " bigexception.clientid in (" + sqlin + ") and " + minisql;
								completeRequest = this.environment.getRequiredProperty("activevehicle.client");
								completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
								resultlist = this.jdbcTemplate.queryForList(completeRequest);
								allresukt.addAll(resultlist);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
							typeInfos = 1;
						} else {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
									return new ResumeResult(allresukt, (List) null, typeInfos);
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
								completeRequest = this.environment.getRequiredProperty("activevehicle.affiliate");
								completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
								allresukt = this.jdbcTemplate.queryForList(completeRequest);
								typeInfos = 2;
							} catch (Exception ex4) {
							}
						}
					} else {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
								return new ResumeResult(allresukt, (List) null, typeInfos);
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
							completeRequest = this.environment.getRequiredProperty("activevehicle.transporter");
							completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
							allresukt = this.jdbcTemplate.queryForList(completeRequest);
							typeInfos = 3;
						} catch (Exception ex5) {
						}
					}
				} else {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
							return new ResumeResult(allresukt, (List) null, typeInfos);
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
						completeRequest = this.environment.getRequiredProperty("activevehicle.vehicle");
						completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
						allresukt = this.jdbcTemplate.queryForList(completeRequest);
						typeInfos = 4;
					} catch (Exception ex6) {
					}
				}
			} else {
				if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
						&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
						&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
						return new ResumeResult(allresukt, (List) null, typeInfos);
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
					completeRequest = this.environment.getRequiredProperty("activevehicle.driver");
					completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
					allresukt = this.jdbcTemplate.queryForList(completeRequest);
					typeInfos = 5;
				} catch (Exception ex7) {
				}
			}
		} catch (Exception ex8) {
		}
		return new ResumeResult(allresukt, (List) null, typeInfos);
	}

	public Customeraffiliate affiliateFromVehicle(final Vehicle veh) {
		Customeraffiliate aff = null;
		try {
			Integer transporterid = veh.getTransporterid().getTransporterid();
			final Transporter trans = this.transR.findById(transporterid).orElse(null);
			transporterid = trans.getAffiliateid().getAffiliateid();
			aff = this.cusaffR.findById(transporterid).orElse(null);
			return aff;
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public Map checkPermissions(final String userroleid, final String menuId) {
		return this.dashboardDaoInterface.checkPermissions(userroleid, menuId);
	}

	@Override
	public List fetchAllParentMenus(final String roleid) throws Exception {
		final List menusList = new ArrayList();
		final List<Map<String, Object>> parentmenus = this.dashboardDaoInterface.fetchAllMenus(Integer.parseInt(roleid),
				0);
		for (final Map map : parentmenus) {
			final List submneu = this.dashboardDaoInterface.fetchAllMenus(Integer.parseInt(roleid),
					(int) map.get("MenuId"));
			if (!submneu.isEmpty()) {
				map.put("submenulist", submneu);
			}
			if (Utils.castIntegerObject(map.get("menuhome")) == 1 || !submneu.isEmpty()) {
				menusList.add(map);
			}
		}
		return menusList;
	}

	@Override
	public List fetchVehicleByTransporter(final String transid) {
		return this.dashboardDaoInterface.fetchVehicleByTransporter(transid);
	}

	@Override
	@Cacheable(value = "resumeactivedrive", key = "new org.springframework.cache.interceptor.SimpleKey(#user.userid, #codeclient,#codeafiliate,#transporter,#codevehicle,#codedriver,#datedebut,#datefin)")
	public ResumeResult resumeactivedrive(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codevehicle, final String codedriver, final String datedebut,
			final String datefin) {
		final List<Integer> listypexception = new ArrayList<>();
		String sqlin = "";
		final String andsql = "";
		String completeRequest = "";
		String allconstructsqlin = "";
		final Date date01 = Utils.StringToDate("", datedebut, "yyyy-MM-dd");
		Date date2 = Utils.StringToDate("", datefin, "yyyy-MM-dd");
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date2);
		calendar.add(5, 1);
		date2 = calendar.getTime();
		if (Objects.isNull(date2) || Objects.isNull(date01)) {
			return new ResumeResult((List) null, (List) null, StaticValues.wrongdateformat_Int);
		}
		final String minisql = " bigexception.activitydate >= '" + Utils.DateFormat(date01, "yyyy-MM-dd")
				+ "' and bigexception.activitydate < '" + Utils.DateFormat(date2, "yyyy-MM-dd") + "'";
		final List<Integer> listdriver = new ArrayList<>();
		final List<Integer> listvehicle = new ArrayList<>();
		final List<Integer> listransporter = new ArrayList<>();
		final List<Integer> listafiliate = new ArrayList<>();
		final List<Integer> listclient = new ArrayList<>();
		final List<Integer> result = new ArrayList<>();
		List<Map<String, Object>> resultlist = null;
		List<Map<String, Object>> allresukt = new ArrayList<>();
		final List<Map<String, Object>> allinvalidresukt = new ArrayList<>();
		Integer typeInfos = -1;
		try {
			if (Objects.isNull(codedriver) || codedriver.equalsIgnoreCase("")) {
				if (Objects.isNull(codevehicle) || codevehicle.equalsIgnoreCase("")) {
					if (Objects.isNull(transporter) || transporter.equalsIgnoreCase("")) {
						if (Objects.isNull(codeafiliate) || codeafiliate.equalsIgnoreCase("")) {
							if (!Objects.isNull(codeclient)) {
								if (!codeclient.equalsIgnoreCase("")) {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid) {
										if (user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
											return new ResumeResult(allresukt, (List) null, typeInfos);
										}
									}
									try {
										if (codeclient.equalsIgnoreCase("0")) {
											if (user.getReelrole().getIdtyperole()
													.getUserroleid() == this.superadminid) {
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
										allconstructsqlin = " bigexception.clientid in (" + sqlin + ") and " + minisql;
										completeRequest = this.environment.getRequiredProperty("activedrive.client");
										completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
										resultlist = this.jdbcTemplate.queryForList(completeRequest);
										allresukt.addAll(resultlist);
										typeInfos = 1;
									} catch (Exception ex2) {
									}
									return new ResumeResult(allresukt, (List) null, typeInfos);
								}
							}
							try {
								if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
									sqlin = this.environment.getRequiredProperty("clientid.listidclient");
								} else {
									try {
										sqlin = this.environment
												.getRequiredProperty("clientid.listidfromuserightsbyuserid");
										sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
									} catch (Exception ex3) {
									}
								}
								allconstructsqlin = " bigexception.clientid in (" + sqlin + ") and " + minisql;
								completeRequest = this.environment.getRequiredProperty("activedrive.client");
								completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
								resultlist = this.jdbcTemplate.queryForList(completeRequest);
								allresukt.addAll(resultlist);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
							typeInfos = 1;
						} else {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
									return new ResumeResult(allresukt, (List) null, typeInfos);
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
								completeRequest = this.environment.getRequiredProperty("activedrive.affiliate");
								completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
								allresukt = this.jdbcTemplate.queryForList(completeRequest);
								typeInfos = 2;
							} catch (Exception ex4) {
							}
						}
					} else {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
								return new ResumeResult(allresukt, (List) null, typeInfos);
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
							completeRequest = this.environment.getRequiredProperty("activedrive.transporter");
							completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
							allresukt = this.jdbcTemplate.queryForList(completeRequest);
							typeInfos = 3;
						} catch (Exception ex5) {
						}
					}
				} else {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
							return new ResumeResult(allresukt, (List) null, typeInfos);
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
						completeRequest = this.environment.getRequiredProperty("activedrive.vehicle");
						completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
						allresukt = this.jdbcTemplate.queryForList(completeRequest);
						typeInfos = 4;
					} catch (Exception ex6) {
					}
				}
			} else {
				if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
						&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
						&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
						return new ResumeResult(allresukt, (List) null, typeInfos);
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
					completeRequest = this.environment.getRequiredProperty("activedrive.driver");
					completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
					allresukt = this.jdbcTemplate.queryForList(completeRequest);
					typeInfos = 5;
				} catch (Exception ex7) {
				}
			}
		} catch (Exception ex8) {
		}
		return new ResumeResult(allresukt, (List) null, typeInfos);
	}

	@Override
	@Cacheable(value = "resumedriverandvehicle", key = "new org.springframework.cache.interceptor.SimpleKey(#user.userid, #codeclient,#codeafiliate,#transporter,#codevehicle,#codedriver)")
	public ResumeResults resumelistdriveandvehicle(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codevehicle, final String codedriver) {
		final List<Integer> listransporter = new ArrayList<>();
		List<Integer> listafiliate = new ArrayList<>();
		List<Integer> listclient = new ArrayList<>();
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
			Label_1507: {
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
									typeInfos = 1;
								} else {
									Label_0468: {
										if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid) {
											if (user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
												break Label_0468;
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
										if (Objects.isNull(listclient)) {
											listclient = new ArrayList<>();
										}
									}
									typeInfos = 1;
								}
							} else {
								Label_0757: {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
											&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
										if (user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
											break Label_0757;
										}
									}
									try {
										if (codeafiliate.equalsIgnoreCase("0")) {
											listafiliate = new ArrayList<>();
											if (user.getReelrole().getIdtyperole()
													.getUserroleid() == this.superadminid) {
												listafiliate = this.cusaffR.findAllActiveAffiliateId();
											} else {
												final Integer userroleid = user.getReelrole().getIdtyperole()
														.getUserroleid();
												if (userroleid == this.clientid) {
													sqlin = this.environment.getRequiredProperty(
															"affiliateid.cuslistidfromuserightsbyuserid");
												} else if (userroleid == this.affiliateid) {
													sqlin = this.environment.getRequiredProperty(
															"affiliateid.listidfromuserightsbyuserid");
												}
												sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE,
														user.getUserid() + "");
												listafiliate = this.jdbcTemplate.queryForList(sqlin,
														(Class) Integer.class);
											}
										} else {
											listafiliate = Stream.of(codeafiliate.split(",")).map(String::trim)
													.map(Integer::valueOf).collect(Collectors.toList());
										}
									} catch (Exception ex) {
										listafiliate = new ArrayList<>();
									}
									if (Objects.isNull(listafiliate)) {
										listafiliate = new ArrayList<>();
									}
								}
								typeInfos = 2;
							}
						} else {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
									break Label_1507;
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
							} catch (Exception ex3) {
							}
						}
					} else {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
								break Label_1507;
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
						} catch (Exception ex4) {
						}
					}
				} else {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
							break Label_1507;
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
									sqlin = this.environment
											.getRequiredProperty("driveridid.listidfromuserightsbyuserid");
								}
								sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
							}
						} else {
							sqlin = codedriver;
						}
						typeInfos = 5;
					} catch (Exception ex5) {
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
			completeRequest = this.environment.getRequiredProperty("allvehicleanddriver.client");
			for (final Integer value : listclient) {
				completeRequest = completeRequest.replaceAll("CUSIDMER", value + "");
				allresukt.addAll(this.jdbcTemplate.queryForList(completeRequest));
			}
			break;
		}
		case 2: {
			completeRequest = this.environment.getRequiredProperty("allvehicleanddriver.affiliate");
			for (final Integer value : listafiliate) {
				completeRequest = completeRequest.replaceAll("AFFIDLIATE", value + "");
				allresukt.addAll(this.jdbcTemplate.queryForList(completeRequest));
			}
			break;
		}
		case 3: {
			completeRequest = this.environment.getRequiredProperty("allvehicleanddriver.transporter");
			completeRequest = completeRequest.replaceAll("TRANSIPPORTER", sqlin);
			allresukt = this.jdbcTemplate.queryForList(completeRequest);
			break;
		}
		case 4: {
			completeRequest = this.environment.getRequiredProperty("allvehicleanddriver.vehicle");
			completeRequest = completeRequest.replaceAll("IDVEHICLEID", sqlin);
			allresukt = this.jdbcTemplate.queryForList(completeRequest);
			break;
		}
		case 5: {
			completeRequest = this.environment.getRequiredProperty("allvehicleanddriver.driver");
			completeRequest = completeRequest.replaceAll("IDRIVERID", sqlin);
			allresukt = this.jdbcTemplate.queryForList(completeRequest);
			break;
		}
		}
		return new ResumeResults(allresukt, typeInfos);
	}

	@Override
	@Cacheable(value = "resumeworktime", key = "new org.springframework.cache.interceptor.SimpleKey(#user.userid, #codeclient,#codeafiliate,#transporter,#codevehicle,#codedriver,#datedebut,#datefin)")
	public ResumeResults resumeworktime(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codevehicle, final String codedriver, final String datedebut,
			final String datefin) {
		final Date date1 = Utils.StringToDate("", datedebut, "yyyy-MM-dd");
		final Date date2 = Utils.StringToDate("", datefin, "yyyy-MM-dd");
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date2);
		cal.add(5, 1);
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
			Label_1487: {
				if (Objects.isNull(codedriver) || codedriver.equalsIgnoreCase("")) {
					if (Objects.isNull(codevehicle) || codevehicle.equalsIgnoreCase("")) {
						if (Objects.isNull(transporter) || transporter.equalsIgnoreCase("")) {
							if (Objects.isNull(codeafiliate) || codeafiliate.equalsIgnoreCase("")) {
								if (!Objects.isNull(codeclient)) {
									if (!codeclient.equalsIgnoreCase("")) {
										if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid) {
											if (user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
												break Label_1487;
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
										break Label_1487;
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
										break Label_1487;
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
									break Label_1487;
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
								break Label_1487;
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
							break Label_1487;
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
			completeRequest = this.environment.getRequiredProperty("sumalldistanceperperiodforclientgroupby");
			completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
			completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
			completeRequest = completeRequest.replaceFirst("DATTTES2", Utils.DateFormat(date2, "yyyy-MM-dd") + "");
			allresukt = this.jdbcTemplate.queryForList(completeRequest);
			break;
		}
		case 2: {
			completeRequest = this.environment.getRequiredProperty("sumalldistanceperperiodforaffiliategroupby");
			completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
			completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
			completeRequest = completeRequest.replaceFirst("DATTTES2", Utils.DateFormat(date2, "yyyy-MM-dd") + "");
			allresukt = this.jdbcTemplate.queryForList(completeRequest);
			break;
		}
		case 3: {
			completeRequest = this.environment.getRequiredProperty("sumalldistanceperperiodfortransportergroupby");
			completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
			completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
			completeRequest = completeRequest.replaceFirst("DATTTES2", Utils.DateFormat(date2, "yyyy-MM-dd") + "");
			allresukt = this.jdbcTemplate.queryForList(completeRequest);
			break;
		}
		case 4: {
			completeRequest = this.environment.getRequiredProperty("sumalldistanceperperiodforvehiclegroupby");
			completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
			completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
			completeRequest = completeRequest.replaceFirst("DATTTES2", Utils.DateFormat(date2, "yyyy-MM-dd") + "");
			allresukt = this.jdbcTemplate.queryForList(completeRequest);
			break;
		}
		case 5: {
			completeRequest = this.environment.getRequiredProperty("sumalldistanceperperiodfordrivergroupby");
			completeRequest = completeRequest.replaceFirst("SQLINS", sqlin);
			completeRequest = completeRequest.replaceFirst("DATTTES1", datedebut + "");
			completeRequest = completeRequest.replaceFirst("DATTTES2", Utils.DateFormat(date2, "yyyy-MM-dd") + "");
			allresukt = this.jdbcTemplate.queryForList(completeRequest);
			break;
		}
		}
		return new ResumeResults(allresukt, typeInfos);
	}

	@Override
	@Cacheable(value = "resumeexception", key = "new org.springframework.cache.interceptor.SimpleKey(#user.userid, #codeclient,#codeafiliate,#transporter,#codevehicle,#codedriver,#datedebut,#datefin,#listidtypeexception,#record,#alert,#alarm)")
	public ResumeResult resumexception(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codevehicle, final String codedriver, final String datedebut,
			final String datefin, final String listidtypeexception, final Boolean record, final Boolean alert,
			final Boolean alarm) {
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
			return new ResumeResult((List) null, (List) null, StaticValues.Levelnotexists_Int);
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
			return new ResumeResult((List) null, (List) null, StaticValues.wrongdateformat_Int);
		}
		final String minisql = " bigexception.level in " + recordalertalarm + " and bigexception.exceptiontype in ("
				+ andsql + ") and bigexception.startdatetime >= '" + Utils.DateFormat(date01, "yyyy-MM-dd")
				+ "' and bigexception.startdatetime < '" + Utils.DateFormat(date2, "yyyy-MM-dd") + "'";
		List<Map<String, Object>> resultlist = null;
		List<Map<String, Object>> allresukt = new ArrayList<>();
		List<Map<String, Object>> allinvalidresukt = new ArrayList<>();
		final List<Integer> listdriver = new ArrayList<>();
		final List<Integer> listvehicle = new ArrayList<>();
		final List<Integer> listransporter = new ArrayList<>();
		final List<Integer> listafiliate = new ArrayList<>();
		List<Integer> listclient = new ArrayList<>();
		final List<Integer> result = new ArrayList<>();
		Integer typeInfos = -1;
		try {
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
								for (final Integer clientid : listclient) {
									try {
										sqlin = this.environment
												.getRequiredProperty("affiliateidperclientid.listidaffiliate");
										sqlin = sqlin.replaceFirst("CLIENT", clientid + "");
										allconstructsqlin = " bigexception.affiliateid in (" + sqlin + ") and "
												+ minisql;
										completeRequest = this.environment.getRequiredProperty("resumexception.client");
										completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
										completeRequest = completeRequest.replaceFirst("CLIENT", clientid + "");
										resultlist = this.jdbcTemplate.queryForList(completeRequest);
										allresukt.addAll(resultlist);
										completeRequest = this.environment
												.getRequiredProperty("resuminvalidexception.client");
										completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
										completeRequest = completeRequest.replaceFirst("CLIENT", clientid + "");
										resultlist = this.jdbcTemplate.queryForList(completeRequest);
										allinvalidresukt.addAll(resultlist);
									} catch (Exception ex) {
										ex.printStackTrace();
									}
								}
								typeInfos = 1;
							} else {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid) {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
										return new ResumeResult(allresukt, allinvalidresukt, typeInfos);
									}
								}
								try {
									if (codeclient.equalsIgnoreCase("0")) {
										listclient = new ArrayList<>();
										if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
											listclient = this.usersR.findAllActiveCustomerId();
										} else {
											listclient = this.userRR.findAllReelCustomerIdOfUserId(user.getUserid());
										}
									} else {
										listclient = Stream.of(codeclient.split(",")).map(String::trim)
												.map(Integer::valueOf).collect(Collectors.toList());
									}
									for (final Integer clientid : listclient) {
										try {
											sqlin = this.environment
													.getRequiredProperty("affiliateidperclientid.listidaffiliate");
											sqlin = sqlin.replaceFirst("CLIENT", clientid + "");
											allconstructsqlin = " bigexception.affiliateid in (" + sqlin + ") and "
													+ minisql;
											completeRequest = this.environment
													.getRequiredProperty("resumexception.client");
											completeRequest = completeRequest.replaceFirst("XXXXXXXXXX",
													allconstructsqlin);
											completeRequest = completeRequest.replaceFirst("CLIENT", clientid + "");
											resultlist = this.jdbcTemplate.queryForList(completeRequest);
											allresukt.addAll(resultlist);
											completeRequest = this.environment
													.getRequiredProperty("resuminvalidexception.client");
											completeRequest = completeRequest.replaceFirst("XXXXXXXXXX",
													allconstructsqlin);
											completeRequest = completeRequest.replaceFirst("CLIENT", clientid + "");
											resultlist = this.jdbcTemplate.queryForList(completeRequest);
											allinvalidresukt.addAll(resultlist);
										} catch (Exception ex) {
											ex.printStackTrace();
										}
									}
									typeInfos = 1;
								} catch (Exception ex4) {
								}
							}
						} else {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
									return new ResumeResult(allresukt, allinvalidresukt, typeInfos);
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
								completeRequest = this.environment.getRequiredProperty("resumexception.affiliate");
								completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
								allresukt = this.jdbcTemplate.queryForList(completeRequest);
								completeRequest = this.environment
										.getRequiredProperty("resuminvalidexception.affiliate");
								completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
								allinvalidresukt = this.jdbcTemplate.queryForList(completeRequest);
								typeInfos = 2;
							} catch (Exception ex2) {
								ex2.printStackTrace();
							}
						}
					} else {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
								return new ResumeResult(allresukt, allinvalidresukt, typeInfos);
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
							completeRequest = this.environment.getRequiredProperty("resumexception.transporter");
							completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
							allresukt = this.jdbcTemplate.queryForList(completeRequest);
							completeRequest = this.environment.getRequiredProperty("resuminvalidexception.transporter");
							completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
							allinvalidresukt = this.jdbcTemplate.queryForList(completeRequest);
							typeInfos = 3;
						} catch (Exception ex5) {
						}
					}
				} else {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
							return new ResumeResult(allresukt, allinvalidresukt, typeInfos);
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
						completeRequest = this.environment.getRequiredProperty("resumexception.vehicle");
						completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
						allresukt = this.jdbcTemplate.queryForList(completeRequest);
						completeRequest = this.environment.getRequiredProperty("resuminvalidexception.vehicle");
						completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
						allinvalidresukt = this.jdbcTemplate.queryForList(completeRequest);
						typeInfos = 4;
					} catch (Exception ex6) {
					}
				}
			} else {
				if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
						&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
						&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
						return new ResumeResult(allresukt, allinvalidresukt, typeInfos);
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
					completeRequest = this.environment.getRequiredProperty("resumexception.driver");
					completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
					allresukt = this.jdbcTemplate.queryForList(completeRequest);
					completeRequest = this.environment.getRequiredProperty("resuminvalidexception.driver");
					completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
					allinvalidresukt = this.jdbcTemplate.queryForList(completeRequest);
					typeInfos = 5;
				} catch (Exception ex7) {
				}
			}
		} catch (Exception ex2) {
			ex2.printStackTrace();
		}
		return new ResumeResult(allresukt, allinvalidresukt, typeInfos);
	}

	@Override
	@Cacheable(value = "resume100km", key = "new org.springframework.cache.interceptor.SimpleKey(#user.userid, #codeclient,#codeafiliate,#transporter,#codevehicle,#codedriver,#datedebut,#datefin,#listidtypeexception,#record,#alert,#alarm)")
	public ResumeResult resumexception100km(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codevehicle, final String codedriver, final String datedebut,
			final String datefin, final String listidtypeexception, final Boolean record, final Boolean alert,
			final Boolean alarm) {
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
			return new ResumeResult((List) null, (List) null, StaticValues.Levelnotexists_Int);
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
			return new ResumeResult((List) null, (List) null, StaticValues.wrongdateformat_Int);
		}
		final String date2String = Utils.DateFormat(date2, "yyyy-MM-dd");
		final String minisql = " bigexception.level in " + recordalertalarm + " and bigexception.exceptiontype in ("
				+ andsql + ") and bigexception.startdatetime >= '" + datedebut + "' and bigexception.startdatetime < '"
				+ date2String + "'";
		final List<Integer> listdriver = new ArrayList<>();
		final List<Integer> listvehicle = new ArrayList<>();
		final List<Integer> listransporter = new ArrayList<>();
		final List<Integer> listafiliate = new ArrayList<>();
		List<Integer> listclient = new ArrayList<>();
		final List<Integer> result = new ArrayList<>();
		final List<Resumexceptionclient> resumclient = new ArrayList<>();
		final List<Resumexceptionaffiliate> resumaff = new ArrayList<>();
		final List<Resumexceptiontransporter> resumtrans = new ArrayList<>();
		final List<Resumexceptionvehicle> resumveh = new ArrayList<>();
		final List<Resumexceptiondriver> resumdriver = new ArrayList<>();
		List<Map<String, Object>> resultlist = null;
		List<Map<String, Object>> allresukt = new ArrayList<>();
		List<Map<String, Object>> allinvalidresukt = new ArrayList<>();
		Integer typeInfos = -1;
		try {
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
									} catch (Exception ex) {
									}
								}
								for (final Integer clientid : listclient) {
									sqlin = this.environment
											.getRequiredProperty("affiliateidperclientid.listidaffiliate");
									sqlin = sqlin.replaceFirst("CLIENT", clientid + "");
									allconstructsqlin = " bigexception.affiliateid in (" + sqlin + ") and " + minisql;
									completeRequest = this.environment
											.getRequiredProperty("resumexception100km.client");
									completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
									completeRequest = completeRequest.replaceFirst("CLIENT", clientid + "");
									completeRequest = completeRequest.replaceFirst("CLIENT", clientid + "");
									completeRequest = completeRequest.replaceFirst("SQLIN", sqlin);
									completeRequest = completeRequest.replaceFirst("DATE1", datedebut);
									completeRequest = completeRequest.replaceFirst("DATE2", date2String);
									resultlist = this.jdbcTemplate.queryForList(completeRequest);
									allresukt.addAll(resultlist);

									completeRequest = this.environment
											.getRequiredProperty("resuminvalidexception100km.client");
									completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
									completeRequest = completeRequest.replaceFirst("CLIENT", clientid + "");
									completeRequest = completeRequest.replaceFirst("CLIENT", clientid + "");
									completeRequest = completeRequest.replaceFirst("SQLIN", sqlin);
									completeRequest = completeRequest.replaceFirst("DATE1", datedebut);
									completeRequest = completeRequest.replaceFirst("DATE2", date2String);
									resultlist = this.jdbcTemplate.queryForList(completeRequest);
									allinvalidresukt.addAll(resultlist);
								}
								typeInfos = 1;
							} else {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid) {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
										return new ResumeResult(allresukt, (List) null, typeInfos);
									}
								}
								try {
									if (codeclient.equalsIgnoreCase("0")) {
										listclient = new ArrayList<>();
										if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
											listclient = this.usersR.findAllActiveCustomerId();
										} else {
											listclient = this.userRR.findAllReelCustomerIdOfUserId(user.getUserid());
										}
									} else {
										listclient = Stream.of(codeclient.split(",")).map(String::trim)
												.map(Integer::valueOf).collect(Collectors.toList());
									}
									for (final Integer clientid : listclient) {
										sqlin = this.environment
												.getRequiredProperty("affiliateidperclientid.listidaffiliate");
										sqlin = sqlin.replaceFirst("CLIENT", clientid + "");
										allconstructsqlin = " bigexception.affiliateid in (" + sqlin + ") and "
												+ minisql;
										completeRequest = this.environment
												.getRequiredProperty("resumexception100km.client");
										completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
										completeRequest = completeRequest.replaceFirst("CLIENT", clientid + "");
										completeRequest = completeRequest.replaceFirst("CLIENT", clientid + "");
										completeRequest = completeRequest.replaceFirst("SQLIN", sqlin);
										completeRequest = completeRequest.replaceFirst("DATE1", datedebut);
										completeRequest = completeRequest.replaceFirst("DATE2", date2String);
										resultlist = this.jdbcTemplate.queryForList(completeRequest);
										allresukt.addAll(resultlist);

										completeRequest = this.environment
												.getRequiredProperty("resuminvalidexception100km.client");
										completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
										completeRequest = completeRequest.replaceFirst("CLIENT", clientid + "");
										completeRequest = completeRequest.replaceFirst("CLIENT", clientid + "");
										completeRequest = completeRequest.replaceFirst("SQLIN", sqlin);
										completeRequest = completeRequest.replaceFirst("DATE1", datedebut);
										completeRequest = completeRequest.replaceFirst("DATE2", date2String);
										resultlist = this.jdbcTemplate.queryForList(completeRequest);
										allinvalidresukt.addAll(resultlist);
									}
									typeInfos = 1;
								} catch (Exception ex2) {
								}
							}
						} else {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
									return new ResumeResult(allresukt, (List) null, typeInfos);
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
								completeRequest = this.environment.getRequiredProperty("resumexception100km.affiliate");
								completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
								completeRequest = completeRequest.replaceFirst("SQLIN", sqlin);
								completeRequest = completeRequest.replaceFirst("DATE1", datedebut);
								completeRequest = completeRequest.replaceFirst("DATE2", date2String);
								allresukt = this.jdbcTemplate.queryForList(completeRequest);

								completeRequest = this.environment
										.getRequiredProperty("resuminvalidexception100km.affiliate");
								completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
								completeRequest = completeRequest.replaceFirst("SQLIN", sqlin);
								completeRequest = completeRequest.replaceFirst("DATE1", datedebut);
								completeRequest = completeRequest.replaceFirst("DATE2", date2String);
								allinvalidresukt = this.jdbcTemplate.queryForList(completeRequest);
								// allinvalidresukt.addAll(resultlist);
								typeInfos = 2;
							} catch (Exception ex3) {
							}
						}
					} else {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
								return new ResumeResult(allresukt, (List) null, typeInfos);
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
							completeRequest = this.environment.getRequiredProperty("resumexception100km.transporter");
							completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
							completeRequest = completeRequest.replaceFirst("SQLIN", sqlin);
							completeRequest = completeRequest.replaceFirst("DATE1", datedebut);
							completeRequest = completeRequest.replaceFirst("DATE2", date2String);
							allresukt = this.jdbcTemplate.queryForList(completeRequest);

							completeRequest = this.environment
									.getRequiredProperty("resuminvalidexception100km.transporter");
							completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
							completeRequest = completeRequest.replaceFirst("SQLIN", sqlin);
							completeRequest = completeRequest.replaceFirst("DATE1", datedebut);
							completeRequest = completeRequest.replaceFirst("DATE2", date2String);
							allinvalidresukt = this.jdbcTemplate.queryForList(completeRequest);
							typeInfos = 3;
						} catch (Exception ex4) {
						}
					}
				} else {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
							return new ResumeResult(allresukt, (List) null, typeInfos);
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
						completeRequest = this.environment.getRequiredProperty("resumexception100km.vehicle");
						completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
						completeRequest = completeRequest.replaceFirst("SQLIN", sqlin);
						completeRequest = completeRequest.replaceFirst("DATE1", datedebut);
						completeRequest = completeRequest.replaceFirst("DATE2", date2String);
						allresukt = this.jdbcTemplate.queryForList(completeRequest);

						completeRequest = this.environment.getRequiredProperty("resuminvalidexception100km.vehicle");
						completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
						completeRequest = completeRequest.replaceFirst("SQLIN", sqlin);
						completeRequest = completeRequest.replaceFirst("DATE1", datedebut);
						completeRequest = completeRequest.replaceFirst("DATE2", date2String);
						allinvalidresukt = this.jdbcTemplate.queryForList(completeRequest);
						typeInfos = 4;
					} catch (Exception ex5) {
					}
				}
			} else {
				if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
						&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
						&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
						return new ResumeResult(allresukt, (List) null, typeInfos);
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
					completeRequest = this.environment.getRequiredProperty("resumexception100km.driver");
					completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
					completeRequest = completeRequest.replaceFirst("SQLIN", sqlin);
					completeRequest = completeRequest.replaceFirst("DATE1", datedebut);
					completeRequest = completeRequest.replaceFirst("DATE2", date2String);
					allresukt = this.jdbcTemplate.queryForList(completeRequest);

					completeRequest = this.environment.getRequiredProperty("resuminvalidexception100km.driver");
					completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
					completeRequest = completeRequest.replaceFirst("SQLIN", sqlin);
					completeRequest = completeRequest.replaceFirst("DATE1", datedebut);
					completeRequest = completeRequest.replaceFirst("DATE2", date2String);
					allinvalidresukt = this.jdbcTemplate.queryForList(completeRequest);
					typeInfos = 5;
				} catch (Exception ex6) {
				}
			}
		} catch (Exception ex7) {
		}
		return new ResumeResult(allresukt, allinvalidresukt, typeInfos);
	}

	public String smalltrends(final User user, final List<Integer> listdriver, final List<Integer> listvehicle,
			final List<Integer> listransporter, final List<Integer> listafiliate, final List<Integer> listclient,
			final Date date01, final Date date02, final List<String> listshortpametertype, final Boolean record,
			final Boolean alert, final Boolean alarm, final List<Integer> listidtypexcep,
			final List<Integer> listlevelexcep) {
		int position = -1;
		List<Resumexceptionclient> resumclient = new ArrayList<>();
		List<Resumexceptionaffiliate> resumaff = new ArrayList<>();
		List<Resumexceptiontransporter> resumtrans = new ArrayList<>();
		List<Resumexceptionvehicle> resumveh = new ArrayList<>();
		List<Resumexceptiondriver> resumdriver = new ArrayList<>();
		final long customer = 0L;
		if (!Objects.isNull(listdriver) && !listdriver.isEmpty()) {
			resumdriver = this.resumeDriverR.dailyresumebyperiod(listdriver, date01, date02);
			final Object[][] listobject = this.invalidR.countbydrivercomments(listdriver, date01, date02,
					listidtypexcep, listlevelexcep);
			position = 1;
		} else if (!Objects.isNull(listvehicle) && !listvehicle.isEmpty()) {
			resumveh = this.resumevehR.dailyresumebyperiod(listvehicle, date01, date02);
			final Object[][] listobject = this.invalidR.countbyvehiclecomments(listvehicle, date01, date02,
					listidtypexcep, listlevelexcep);
			position = 2;
		} else if (!Objects.isNull(listransporter) && !listransporter.isEmpty()) {
			resumtrans = this.resumetransR.dailyresumebyperiod(listransporter, date01, date02);
			final Object[][] listobject = this.invalidR.countbytransportercomments(listransporter, date01, date02,
					listidtypexcep, listlevelexcep);
			position = 3;
		} else if (!Objects.isNull(listafiliate) && !listafiliate.isEmpty()) {
			resumaff = this.resumeaffR.dailyresumebyperiod(listafiliate, date01, date02);
			final Object[][] listobject = this.invalidR.countbyAffiliatecomments(listransporter, date01, date02,
					listidtypexcep, listlevelexcep);
			position = 4;
		} else if (!Objects.isNull(listclient) && !listclient.isEmpty()) {
			resumclient = this.resumeclientR.dailyresumebyperiod(listclient, date01, date02);
			final Object[][] listobject = this.invalidR.countbyAffiliateOfClientcomments(listclient, date01, date02,
					listidtypexcep, listlevelexcep);
			position = 5;
		}
		final JSONObject jsonspeeding = new JSONObject();
		final JSONObject jsonHasrhbraking = new JSONObject();
		final JSONObject jsonSealBelt = new JSONObject();
		final JSONObject jsonDailyDrive = new JSONObject();
		final JSONObject jsonDailyRest = new JSONObject();
		final JSONObject jsonWeeklyDrive = new JSONObject();
		final JSONObject jsonWeeklyRest = new JSONObject();
		final JSONObject jsonWeeklyWordk = new JSONObject();
		final JSONObject jsonDailyWordk = new JSONObject();
		final JSONObject jsonHarshAcceleration = new JSONObject();
		final JSONObject jsonNightDriven = new JSONObject();
		final JSONObject jsonContiniousDrive = new JSONObject();
		final JSONObject jsonPhoneDistraction = new JSONObject();
		final JSONObject jsonFatigue = new JSONObject();
		final JSONObject jsonActiveFleet = new JSONObject();
		final JSONObject jsonNoID = new JSONObject();
		final JSONObject jsonInsufficientTripDuration = new JSONObject();
		final JSONObject jsonSmoking = new JSONObject();
		for (final String paramtypename : listshortpametertype) {
			switch (position) {
			case 1: {
				for (final Resumexceptiondriver resume : resumdriver) {
					final Driver driver = resume.getDriver();
					final String entities = driver.getName();
					final NbrtypeExceptions smalldata = Utils.tableaujson(paramtypename, entities, record, alert, alarm,
							new String[] { resume.getSpeedingfnbr(), resume.getHa(), resume.getHb(),
									resume.getNightdrive(), resume.getContiniuousdrive(), resume.getDailydrive(),
									resume.getDailywork(), resume.getDailyrest(), resume.getWeeklydrive(),
									resume.getWeeklyrest(), resume.getPhonedistraction(), resume.getSealbelt(),
									resume.getFatigue(), resume.getWeeklywork(), resume.getActivefleet(),
									resume.getNoid(), resume.getInsuffisianttripduration(), resume.getSmoking() },
							(BigDecimal) null);
					Double value;
					try {
						value = smalldata.getBnr();
						if (Utils.isZero(value, 0.0)) {
							value = null;
						}
					} catch (Exception ex) {
						value = null;
					}
					if (!Objects.isNull(value)) {
						if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
							if (jsonspeeding.has(entities)) {
								jsonspeeding.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonspeeding.getDouble(entities));
							} else {
								jsonspeeding.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
							if (jsonHarshAcceleration.has(entities)) {
								jsonHarshAcceleration.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonHarshAcceleration.getDouble(entities));
							} else {
								jsonHarshAcceleration.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
							if (jsonHasrhbraking.has(entities)) {
								jsonHasrhbraking.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonHasrhbraking.getDouble(entities));
							} else {
								jsonHasrhbraking.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
							if (jsonNightDriven.has(entities)) {
								jsonNightDriven.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonNightDriven.getDouble(entities));
							} else {
								jsonNightDriven.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
							if (jsonContiniousDrive.has(entities)) {
								jsonContiniousDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonContiniousDrive.getDouble(entities));
							} else {
								jsonContiniousDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
							if (jsonDailyDrive.has(entities)) {
								jsonDailyDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyDrive.getDouble(entities));
							} else {
								jsonDailyDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
							if (jsonDailyWordk.has(entities)) {
								jsonDailyWordk.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyWordk.getDouble(entities));
							} else {
								jsonDailyWordk.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
							if (jsonDailyRest.has(entities)) {
								jsonDailyRest.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyRest.getDouble(entities));
							} else {
								jsonDailyRest.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
							if (jsonWeeklyDrive.has(entities)) {
								jsonWeeklyDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyDrive.getDouble(entities));
							} else {
								jsonWeeklyDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
							if (jsonWeeklyRest.has(entities)) {
								jsonWeeklyRest.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyRest.getDouble(entities));
							} else {
								jsonWeeklyRest.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
							if (jsonPhoneDistraction.has(entities)) {
								jsonPhoneDistraction.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonPhoneDistraction.getDouble(entities));
							} else {
								jsonPhoneDistraction.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
							if (jsonSealBelt.has(entities)) {
								jsonSealBelt.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonSealBelt.getDouble(entities));
							} else {
								jsonSealBelt.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
							if (jsonFatigue.has(entities)) {
								jsonFatigue.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonFatigue.getDouble(entities));
							} else {
								jsonFatigue.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
							if (jsonWeeklyWordk.has(entities)) {
								jsonWeeklyWordk.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyWordk.getDouble(entities));
							} else {
								jsonWeeklyWordk.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
							if (jsonActiveFleet.has(entities)) {
								jsonActiveFleet.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonActiveFleet.getDouble(entities));
							} else {
								jsonActiveFleet.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
							if (jsonNoID.has(entities)) {
								jsonNoID.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonNoID.getDouble(entities));
							} else {
								jsonNoID.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
							if (jsonInsufficientTripDuration.has(entities)) {
								jsonInsufficientTripDuration.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonInsufficientTripDuration.getDouble(entities));
							} else {
								jsonInsufficientTripDuration.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else {
							if (!paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
								continue;
							}
							if (jsonSmoking.has(entities)) {
								jsonSmoking.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonSmoking.getDouble(entities));
							} else {
								jsonSmoking.put(smalldata.getEntities(), smalldata.getBnr());
							}
						}
					}
				}
				continue;
			}
			case 2: {
				for (final Resumexceptionvehicle resume2 : resumveh) {
					final Vehicle veh = resume2.getVehicle();
					final String entities = veh.getVehicledesc();
					final NbrtypeExceptions smalldata = Utils.tableaujson(paramtypename, entities, record, alert, alarm,
							new String[] { resume2.getSpeedingfnbr(), resume2.getHa(), resume2.getHb(),
									resume2.getNightdrive(), resume2.getContiniuousdrive(), resume2.getDailydrive(),
									resume2.getDailywork(), resume2.getDailyrest(), resume2.getWeeklydrive(),
									resume2.getWeeklyrest(), resume2.getPhonedistraction(), resume2.getSealbelt(),
									resume2.getFatigue(), resume2.getWeeklywork(), resume2.getActivefleet(),
									resume2.getNoid(), resume2.getInsuffisianttripduration(), resume2.getSmoking() },
							(BigDecimal) null);
					Double value;
					try {
						value = smalldata.getBnr();
						if (Utils.isZero(value, 0.0)) {
							value = null;
						}
					} catch (Exception ex) {
						value = null;
					}
					if (!Objects.isNull(value)) {
						if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
							if (jsonspeeding.has(entities)) {
								jsonspeeding.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonspeeding.getDouble(entities));
							} else {
								jsonspeeding.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
							if (jsonHarshAcceleration.has(entities)) {
								jsonHarshAcceleration.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonHarshAcceleration.getDouble(entities));
							} else {
								jsonHarshAcceleration.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
							if (jsonHasrhbraking.has(entities)) {
								jsonHasrhbraking.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonHasrhbraking.getDouble(entities));
							} else {
								jsonHasrhbraking.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
							if (jsonNightDriven.has(entities)) {
								jsonNightDriven.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonNightDriven.getDouble(entities));
							} else {
								jsonNightDriven.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
							if (jsonContiniousDrive.has(entities)) {
								jsonContiniousDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonContiniousDrive.getDouble(entities));
							} else {
								jsonContiniousDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
							if (jsonDailyDrive.has(entities)) {
								jsonDailyDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyDrive.getDouble(entities));
							} else {
								jsonDailyDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
							if (jsonDailyWordk.has(entities)) {
								jsonDailyWordk.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyWordk.getDouble(entities));
							} else {
								jsonDailyWordk.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
							if (jsonDailyRest.has(entities)) {
								jsonDailyRest.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyRest.getDouble(entities));
							} else {
								jsonDailyRest.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
							if (jsonWeeklyDrive.has(entities)) {
								jsonWeeklyDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyDrive.getDouble(entities));
							} else {
								jsonWeeklyDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
							if (jsonWeeklyRest.has(entities)) {
								jsonWeeklyRest.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyRest.getDouble(entities));
							} else {
								jsonWeeklyRest.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
							if (jsonPhoneDistraction.has(entities)) {
								jsonPhoneDistraction.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonPhoneDistraction.getDouble(entities));
							} else {
								jsonPhoneDistraction.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
							if (jsonSealBelt.has(entities)) {
								jsonSealBelt.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonSealBelt.getDouble(entities));
							} else {
								jsonSealBelt.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
							if (jsonFatigue.has(entities)) {
								jsonFatigue.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonFatigue.getDouble(entities));
							} else {
								jsonFatigue.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
							if (jsonWeeklyWordk.has(entities)) {
								jsonWeeklyWordk.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyWordk.getDouble(entities));
							} else {
								jsonWeeklyWordk.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
							if (jsonActiveFleet.has(entities)) {
								jsonActiveFleet.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonActiveFleet.getDouble(entities));
							} else {
								jsonActiveFleet.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
							if (jsonNoID.has(entities)) {
								jsonNoID.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonNoID.getDouble(entities));
							} else {
								jsonNoID.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
							if (jsonInsufficientTripDuration.has(entities)) {
								jsonInsufficientTripDuration.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonInsufficientTripDuration.getDouble(entities));
							} else {
								jsonInsufficientTripDuration.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else {
							if (!paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
								continue;
							}
							if (jsonSmoking.has(entities)) {
								jsonSmoking.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonSmoking.getDouble(entities));
							} else {
								jsonSmoking.put(smalldata.getEntities(), smalldata.getBnr());
							}
						}
					}
				}
				continue;
			}
			case 3: {
				for (final Resumexceptiontransporter resume3 : resumtrans) {
					final Transporter trans = resume3.getTransporter();
					final String entities = trans.getName();
					final NbrtypeExceptions smalldata = Utils.tableaujson(paramtypename, entities, record, alert, alarm,
							new String[] { resume3.getSpeedingfnbr(), resume3.getHa(), resume3.getHb(),
									resume3.getNightdrive(), resume3.getContiniuousdrive(), resume3.getDailydrive(),
									resume3.getDailywork(), resume3.getDailyrest(), resume3.getWeeklydrive(),
									resume3.getWeeklyrest(), resume3.getPhonedistraction(), resume3.getSealbelt(),
									resume3.getFatigue(), resume3.getWeeklywork(), resume3.getActivefleet(),
									resume3.getNoid(), resume3.getInsuffisianttripduration(), resume3.getSmoking() },
							(BigDecimal) null);
					Double value;
					try {
						value = smalldata.getBnr();
						if (Utils.isZero(value, 0.0)) {
							value = null;
						}
					} catch (Exception ex) {
						value = null;
					}
					if (!Objects.isNull(value)) {
						if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
							if (jsonspeeding.has(entities)) {
								jsonspeeding.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonspeeding.getDouble(entities));
							} else {
								jsonspeeding.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
							if (jsonHarshAcceleration.has(entities)) {
								jsonHarshAcceleration.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonHarshAcceleration.getDouble(entities));
							} else {
								jsonHarshAcceleration.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
							if (jsonHasrhbraking.has(entities)) {
								jsonHasrhbraking.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonHasrhbraking.getDouble(entities));
							} else {
								jsonHasrhbraking.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
							if (jsonNightDriven.has(entities)) {
								jsonNightDriven.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonNightDriven.getDouble(entities));
							} else {
								jsonNightDriven.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
							if (jsonContiniousDrive.has(entities)) {
								jsonContiniousDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonContiniousDrive.getDouble(entities));
							} else {
								jsonContiniousDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
							if (jsonDailyDrive.has(entities)) {
								jsonDailyDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyDrive.getDouble(entities));
							} else {
								jsonDailyDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
							if (jsonDailyWordk.has(entities)) {
								jsonDailyWordk.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyWordk.getDouble(entities));
							} else {
								jsonDailyWordk.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
							if (jsonDailyRest.has(entities)) {
								jsonDailyRest.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyRest.getDouble(entities));
							} else {
								jsonDailyRest.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
							if (jsonWeeklyDrive.has(entities)) {
								jsonWeeklyDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyDrive.getDouble(entities));
							} else {
								jsonWeeklyDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
							if (jsonWeeklyRest.has(entities)) {
								jsonWeeklyRest.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyRest.getDouble(entities));
							} else {
								jsonWeeklyRest.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
							if (jsonPhoneDistraction.has(entities)) {
								jsonPhoneDistraction.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonPhoneDistraction.getDouble(entities));
							} else {
								jsonPhoneDistraction.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
							if (jsonSealBelt.has(entities)) {
								jsonSealBelt.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonSealBelt.getDouble(entities));
							} else {
								jsonSealBelt.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
							if (jsonFatigue.has(entities)) {
								jsonFatigue.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonFatigue.getDouble(entities));
							} else {
								jsonFatigue.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
							if (jsonWeeklyWordk.has(entities)) {
								jsonWeeklyWordk.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyWordk.getDouble(entities));
							} else {
								jsonWeeklyWordk.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
							if (jsonActiveFleet.has(entities)) {
								jsonActiveFleet.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonActiveFleet.getDouble(entities));
							} else {
								jsonActiveFleet.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
							if (jsonNoID.has(entities)) {
								jsonNoID.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonNoID.getDouble(entities));
							} else {
								jsonNoID.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
							if (jsonInsufficientTripDuration.has(entities)) {
								jsonInsufficientTripDuration.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonInsufficientTripDuration.getDouble(entities));
							} else {
								jsonInsufficientTripDuration.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else {
							if (!paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
								continue;
							}
							if (jsonSmoking.has(entities)) {
								jsonSmoking.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonSmoking.getDouble(entities));
							} else {
								jsonSmoking.put(smalldata.getEntities(), smalldata.getBnr());
							}
						}
					}
				}
				continue;
			}
			case 4: {
				for (final Resumexceptionaffiliate resume4 : resumaff) {
					final Customeraffiliate aff = resume4.getCustomeraffiliate();
					final String entities = aff.getName();
					final NbrtypeExceptions smalldata = Utils.tableaujson(paramtypename, entities, record, alert, alarm,
							new String[] { resume4.getSpeedingfnbr(), resume4.getHa(), resume4.getHb(),
									resume4.getNightdrive(), resume4.getContiniuousdrive(), resume4.getDailydrive(),
									resume4.getDailywork(), resume4.getDailyrest(), resume4.getWeeklydrive(),
									resume4.getWeeklyrest(), resume4.getPhonedistraction(), resume4.getSealbelt(),
									resume4.getFatigue(), resume4.getWeeklywork(), resume4.getActivefleet(),
									resume4.getNoid(), resume4.getInsuffisianttripduration(), resume4.getSmoking() },
							(BigDecimal) null);
					Double value;
					try {
						value = smalldata.getBnr();
						if (Utils.isZero(value, 0.0)) {
							value = null;
						}
					} catch (Exception ex) {
						value = null;
					}
					if (!Objects.isNull(value)) {
						if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
							if (jsonspeeding.has(entities)) {
								jsonspeeding.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonspeeding.getDouble(entities));
							} else {
								jsonspeeding.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
							if (jsonHarshAcceleration.has(entities)) {
								jsonHarshAcceleration.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonHarshAcceleration.getDouble(entities));
							} else {
								jsonHarshAcceleration.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
							if (jsonHasrhbraking.has(entities)) {
								jsonHasrhbraking.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonHasrhbraking.getDouble(entities));
							} else {
								jsonHasrhbraking.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
							if (jsonNightDriven.has(entities)) {
								jsonNightDriven.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonNightDriven.getDouble(entities));
							} else {
								jsonNightDriven.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
							if (jsonContiniousDrive.has(entities)) {
								jsonContiniousDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonContiniousDrive.getDouble(entities));
							} else {
								jsonContiniousDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
							if (jsonDailyDrive.has(entities)) {
								jsonDailyDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyDrive.getDouble(entities));
							} else {
								jsonDailyDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
							if (jsonDailyWordk.has(entities)) {
								jsonDailyWordk.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyWordk.getDouble(entities));
							} else {
								jsonDailyWordk.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
							if (jsonDailyRest.has(entities)) {
								jsonDailyRest.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyRest.getDouble(entities));
							} else {
								jsonDailyRest.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
							if (jsonWeeklyDrive.has(entities)) {
								jsonWeeklyDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyDrive.getDouble(entities));
							} else {
								jsonWeeklyDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
							if (jsonWeeklyRest.has(entities)) {
								jsonWeeklyRest.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyRest.getDouble(entities));
							} else {
								jsonWeeklyRest.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
							if (jsonPhoneDistraction.has(entities)) {
								jsonPhoneDistraction.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonPhoneDistraction.getDouble(entities));
							} else {
								jsonPhoneDistraction.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
							if (jsonSealBelt.has(entities)) {
								jsonSealBelt.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonSealBelt.getDouble(entities));
							} else {
								jsonSealBelt.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
							if (jsonFatigue.has(entities)) {
								jsonFatigue.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonFatigue.getDouble(entities));
							} else {
								jsonFatigue.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
							if (jsonWeeklyWordk.has(entities)) {
								jsonWeeklyWordk.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyWordk.getDouble(entities));
							} else {
								jsonWeeklyWordk.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
							if (jsonActiveFleet.has(entities)) {
								jsonActiveFleet.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonActiveFleet.getDouble(entities));
							} else {
								jsonActiveFleet.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
							if (jsonNoID.has(entities)) {
								jsonNoID.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonNoID.getDouble(entities));
							} else {
								jsonNoID.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
							if (jsonInsufficientTripDuration.has(entities)) {
								jsonInsufficientTripDuration.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonInsufficientTripDuration.getDouble(entities));
							} else {
								jsonInsufficientTripDuration.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else {
							if (!paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
								continue;
							}
							if (jsonSmoking.has(entities)) {
								jsonSmoking.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonSmoking.getDouble(entities));
							} else {
								jsonSmoking.put(smalldata.getEntities(), smalldata.getBnr());
							}
						}
					}
				}
				continue;
			}
			case 5: {
				for (final Resumexceptionclient resume5 : resumclient) {
					final Customer custom = resume5.getClientid();
					final String entities = custom.getName();
					final NbrtypeExceptions smalldata = Utils.tableaujson(paramtypename, entities, record, alert, alarm,
							new String[] { resume5.getSpeedingfnbr(), resume5.getHa(), resume5.getHb(),
									resume5.getNightdrive(), resume5.getContiniuousdrive(), resume5.getDailydrive(),
									resume5.getDailywork(), resume5.getDailyrest(), resume5.getWeeklydrive(),
									resume5.getWeeklyrest(), resume5.getPhonedistraction(), resume5.getSealbelt(),
									resume5.getFatigue(), resume5.getWeeklywork(), resume5.getActivefleet(),
									resume5.getNoid(), resume5.getInsuffisianttripduration(), resume5.getSmoking() },
							(BigDecimal) null);
					Double value;
					try {
						value = smalldata.getBnr();
						if (Utils.isZero(value, 0.0)) {
							value = null;
						}
					} catch (Exception ex) {
						value = null;
					}
					if (!Objects.isNull(value)) {
						if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
							if (jsonspeeding.has(entities)) {
								jsonspeeding.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonspeeding.getDouble(entities));
							} else {
								jsonspeeding.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
							if (jsonHarshAcceleration.has(entities)) {
								jsonHarshAcceleration.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonHarshAcceleration.getDouble(entities));
							} else {
								jsonHarshAcceleration.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
							if (jsonHasrhbraking.has(entities)) {
								jsonHasrhbraking.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonHasrhbraking.getDouble(entities));
							} else {
								jsonHasrhbraking.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
							if (jsonNightDriven.has(entities)) {
								jsonNightDriven.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonNightDriven.getDouble(entities));
							} else {
								jsonNightDriven.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
							if (jsonContiniousDrive.has(entities)) {
								jsonContiniousDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonContiniousDrive.getDouble(entities));
							} else {
								jsonContiniousDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
							if (jsonDailyDrive.has(entities)) {
								jsonDailyDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyDrive.getDouble(entities));
							} else {
								jsonDailyDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
							if (jsonDailyWordk.has(entities)) {
								jsonDailyWordk.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyWordk.getDouble(entities));
							} else {
								jsonDailyWordk.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
							if (jsonDailyRest.has(entities)) {
								jsonDailyRest.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyRest.getDouble(entities));
							} else {
								jsonDailyRest.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
							if (jsonWeeklyDrive.has(entities)) {
								jsonWeeklyDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyDrive.getDouble(entities));
							} else {
								jsonWeeklyDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
							if (jsonWeeklyRest.has(entities)) {
								jsonWeeklyRest.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyRest.getDouble(entities));
							} else {
								jsonWeeklyRest.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
							if (jsonPhoneDistraction.has(entities)) {
								jsonPhoneDistraction.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonPhoneDistraction.getDouble(entities));
							} else {
								jsonPhoneDistraction.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
							if (jsonSealBelt.has(entities)) {
								jsonSealBelt.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonSealBelt.getDouble(entities));
							} else {
								jsonSealBelt.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
							if (jsonFatigue.has(entities)) {
								jsonFatigue.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonFatigue.getDouble(entities));
							} else {
								jsonFatigue.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
							if (jsonWeeklyWordk.has(entities)) {
								jsonWeeklyWordk.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyWordk.getDouble(entities));
							} else {
								jsonWeeklyWordk.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
							if (jsonActiveFleet.has(entities)) {
								jsonActiveFleet.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonActiveFleet.getDouble(entities));
							} else {
								jsonActiveFleet.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
							if (jsonNoID.has(entities)) {
								jsonNoID.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonNoID.getDouble(entities));
							} else {
								jsonNoID.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
							if (jsonInsufficientTripDuration.has(entities)) {
								jsonInsufficientTripDuration.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonInsufficientTripDuration.getDouble(entities));
							} else {
								jsonInsufficientTripDuration.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else {
							if (!paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
								continue;
							}
							if (jsonSmoking.has(entities)) {
								jsonSmoking.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonSmoking.getDouble(entities));
							} else {
								jsonSmoking.put(smalldata.getEntities(), smalldata.getBnr());
							}
						}
					}
				}
				continue;
			}
			}
		}
		final ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		final HttpServletRequest req = sra.getRequest();
		final Logusers log = new Logusers(user.getName(), "AUTHENTICATION_SUCCESS", "Resume Exception", new Date(),
				req.getRemoteAddr());
		this.logRepo.saveAndFlush(log);
		final JSONObject allresult = new JSONObject();
		if (jsonspeeding.length() > 0) {
			allresult.put(Utils.IntegerSpeeding + "", jsonspeeding);
		}
		if (jsonHarshAcceleration.length() > 0) {
			allresult.put(Utils.IntegerHarshAcceleration + "", jsonHarshAcceleration);
		}
		if (jsonHasrhbraking.length() > 0) {
			allresult.put(Utils.IntegerHarshBracking + "", jsonHasrhbraking);
		}
		if (jsonNightDriven.length() > 0) {
			allresult.put(Utils.IntegerNightDriven + "", jsonNightDriven);
		}
		if (jsonContiniousDrive.length() > 0) {
			allresult.put(Utils.IntegerContiniousDrive + "", jsonContiniousDrive);
		}
		if (jsonDailyDrive.length() > 0) {
			allresult.put(Utils.IntegerDailyDrive + "", jsonDailyDrive);
		}
		if (jsonDailyWordk.length() > 0) {
			allresult.put(Utils.IntegerDailyWordk + "", jsonDailyWordk);
		}
		if (jsonDailyRest.length() > 0) {
			allresult.put(Utils.IntegerDailyRest + "", jsonDailyRest);
		}
		if (jsonWeeklyDrive.length() > 0) {
			allresult.put(Utils.IntegerWeeklyDrive + "", jsonWeeklyDrive);
		}
		if (jsonWeeklyRest.length() > 0) {
			allresult.put(Utils.IntegerWeeklyRest + "", jsonWeeklyRest);
		}
		if (jsonPhoneDistraction.length() > 0) {
			allresult.put(Utils.IntegerPhoneDistraction + "", jsonPhoneDistraction);
		}
		if (jsonSealBelt.length() > 0) {
			allresult.put(Utils.IntegerSealBelt + "", jsonSealBelt);
		}
		if (jsonFatigue.length() > 0) {
			allresult.put(Utils.IntegerFatigue + "", jsonFatigue);
		}
		if (jsonWeeklyWordk.length() > 0) {
			allresult.put(Utils.IntegerWeeklyWordk + "", jsonWeeklyWordk);
		}
		if (jsonActiveFleet.length() > 0) {
			allresult.put(Utils.IntegerActiveFleet + "", jsonActiveFleet);
		}
		if (jsonNoID.length() > 0) {
			allresult.put(Utils.IntegerNoID + "", jsonNoID);
		}
		if (jsonInsufficientTripDuration.length() > 0) {
			allresult.put(Utils.IntegerInsufficientTripDuration + "", jsonInsufficientTripDuration);
		}
		if (jsonSmoking.length() > 0) {
			allresult.put(Utils.IntegerSmoking + "", jsonSmoking);
		}
		return allresult.toString();
	}

	public String smalltrends100km(final User user, final List<Integer> listdriver, final List<Integer> listvehicle,
			final List<Integer> listransporter, final List<Integer> listafiliate, final List<Integer> listclient,
			final Date date01, final Date date02, final List<String> listshortpametertype, final Boolean record,
			final Boolean alert, final Boolean alarm) {
		int position = -1;
		List<Resumexceptionclient> resumclient = new ArrayList<>();
		List<Resumexceptionaffiliate> resumaff = new ArrayList<>();
		List<Resumexceptiontransporter> resumtrans = new ArrayList<>();
		List<Resumexceptionvehicle> resumveh = new ArrayList<>();
		List<Resumexceptiondriver> resumdriver = new ArrayList<>();
		if (!Objects.isNull(listdriver) && !listdriver.isEmpty()) {
			resumdriver = this.resumeDriverR.dailyresumebyperiod(listdriver, date01, date02);
			position = 1;
		} else if (!Objects.isNull(listvehicle) && !listvehicle.isEmpty()) {
			resumveh = this.resumevehR.dailyresumebyperiod(listvehicle, date01, date02);
			position = 2;
		} else if (!Objects.isNull(listransporter) && !listransporter.isEmpty()) {
			resumtrans = this.resumetransR.dailyresumebyperiod(listransporter, date01, date02);
			position = 3;
		} else if (!Objects.isNull(listafiliate) && !listafiliate.isEmpty()) {
			resumaff = this.resumeaffR.dailyresumebyperiod(listafiliate, date01, date02);
			position = 4;
		} else if (!Objects.isNull(listclient) && !listclient.isEmpty()) {
			resumclient = this.resumeclientR.dailyresumebyperiod(listclient, date01, date02);
			position = 5;
		}
		final JSONObject jsonspeeding = new JSONObject();
		final JSONObject jsonHasrhbraking = new JSONObject();
		final JSONObject jsonSealBelt = new JSONObject();
		final JSONObject jsonDailyDrive = new JSONObject();
		final JSONObject jsonDailyRest = new JSONObject();
		final JSONObject jsonWeeklyDrive = new JSONObject();
		final JSONObject jsonWeeklyRest = new JSONObject();
		final JSONObject jsonWeeklyWordk = new JSONObject();
		final JSONObject jsonDailyWordk = new JSONObject();
		final JSONObject jsonHarshAcceleration = new JSONObject();
		final JSONObject jsonNightDriven = new JSONObject();
		final JSONObject jsonContiniousDrive = new JSONObject();
		final JSONObject jsonPhoneDistraction = new JSONObject();
		final JSONObject jsonFatigue = new JSONObject();
		final JSONObject jsonActiveFleet = new JSONObject();
		final JSONObject jsonNoID = new JSONObject();
		final JSONObject jsonInsufficientTripDuration = new JSONObject();
		final JSONObject jsonSmoking = new JSONObject();
		for (final String paramtypename : listshortpametertype) {
			switch (position) {
			case 1: {
				for (final Resumexceptiondriver resume : resumdriver) {
					final Driver driver = resume.getDriver();
					final String entities = driver.getName();
					final BigDecimal totaldistance = this.driveractsum.sumdistanceperperiodfordriver(date01, date02,
							driver.getDriverid());
					final NbrtypeExceptions smalldata = Utils.tableaujson(paramtypename, entities, record, alert, alarm,
							new String[] { resume.getSpeedingfnbr(), resume.getHa(), resume.getHb(),
									resume.getNightdrive(), resume.getContiniuousdrive(), resume.getDailydrive(),
									resume.getDailywork(), resume.getDailyrest(), resume.getWeeklydrive(),
									resume.getWeeklyrest(), resume.getPhonedistraction(), resume.getSealbelt(),
									resume.getFatigue(), resume.getWeeklywork(), resume.getActivefleet(),
									resume.getNoid(), resume.getInsuffisianttripduration(), resume.getSmoking() },
							totaldistance);
					Double value;
					try {
						value = smalldata.getBnr();
						if (Utils.isZero(value, 0.0)) {
							value = null;
						}
					} catch (Exception ex) {
						value = null;
					}
					if (!Objects.isNull(value)) {
						if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
							if (jsonspeeding.has(entities)) {
								jsonspeeding.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonspeeding.getDouble(entities));
							} else {
								jsonspeeding.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
							if (jsonHarshAcceleration.has(entities)) {
								jsonHarshAcceleration.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonHarshAcceleration.getDouble(entities));
							} else {
								jsonHarshAcceleration.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
							if (jsonHasrhbraking.has(entities)) {
								jsonHasrhbraking.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonHasrhbraking.getDouble(entities));
							} else {
								jsonHasrhbraking.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
							if (jsonNightDriven.has(entities)) {
								jsonNightDriven.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonNightDriven.getDouble(entities));
							} else {
								jsonNightDriven.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
							if (jsonContiniousDrive.has(entities)) {
								jsonContiniousDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonContiniousDrive.getDouble(entities));
							} else {
								jsonContiniousDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
							if (jsonDailyDrive.has(entities)) {
								jsonDailyDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyDrive.getDouble(entities));
							} else {
								jsonDailyDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
							if (jsonDailyWordk.has(entities)) {
								jsonDailyWordk.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyWordk.getDouble(entities));
							} else {
								jsonDailyWordk.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
							if (jsonDailyRest.has(entities)) {
								jsonDailyRest.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyRest.getDouble(entities));
							} else {
								jsonDailyRest.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
							if (jsonWeeklyDrive.has(entities)) {
								jsonWeeklyDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyDrive.getDouble(entities));
							} else {
								jsonWeeklyDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
							if (jsonWeeklyRest.has(entities)) {
								jsonWeeklyRest.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyRest.getDouble(entities));
							} else {
								jsonWeeklyRest.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
							if (jsonPhoneDistraction.has(entities)) {
								jsonPhoneDistraction.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonPhoneDistraction.getDouble(entities));
							} else {
								jsonPhoneDistraction.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
							if (jsonSealBelt.has(entities)) {
								jsonSealBelt.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonSealBelt.getDouble(entities));
							} else {
								jsonSealBelt.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
							if (jsonFatigue.has(entities)) {
								jsonFatigue.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonFatigue.getDouble(entities));
							} else {
								jsonFatigue.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
							if (jsonWeeklyWordk.has(entities)) {
								jsonWeeklyWordk.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyWordk.getDouble(entities));
							} else {
								jsonWeeklyWordk.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
							if (jsonActiveFleet.has(entities)) {
								jsonActiveFleet.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonActiveFleet.getDouble(entities));
							} else {
								jsonActiveFleet.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
							if (jsonNoID.has(entities)) {
								jsonNoID.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonNoID.getDouble(entities));
							} else {
								jsonNoID.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
							if (jsonInsufficientTripDuration.has(entities)) {
								jsonInsufficientTripDuration.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonInsufficientTripDuration.getDouble(entities));
							} else {
								jsonInsufficientTripDuration.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else {
							if (!paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
								continue;
							}
							if (jsonSmoking.has(entities)) {
								jsonSmoking.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonSmoking.getDouble(entities));
							} else {
								jsonSmoking.put(smalldata.getEntities(), smalldata.getBnr());
							}
						}
					}
				}
				continue;
			}
			case 2: {
				for (final Resumexceptionvehicle resume2 : resumveh) {
					final Vehicle veh = resume2.getVehicle();
					final String entities = veh.getVehicledesc();
					final BigDecimal totaldistance = this.vehactsum.sumdistanceperperiodforvehicle(date01, date02,
							veh.getVehicleid());
					final NbrtypeExceptions smalldata = Utils.tableaujson(paramtypename, entities, record, alert, alarm,
							new String[] { resume2.getSpeedingfnbr(), resume2.getHa(), resume2.getHb(),
									resume2.getNightdrive(), resume2.getContiniuousdrive(), resume2.getDailydrive(),
									resume2.getDailywork(), resume2.getDailyrest(), resume2.getWeeklydrive(),
									resume2.getWeeklyrest(), resume2.getPhonedistraction(), resume2.getSealbelt(),
									resume2.getFatigue(), resume2.getWeeklywork(), resume2.getActivefleet(),
									resume2.getNoid(), resume2.getInsuffisianttripduration(), resume2.getSmoking() },
							totaldistance);
					Double value;
					try {
						value = smalldata.getBnr();
						if (Utils.isZero(value, 0.0)) {
							value = null;
						}
					} catch (Exception ex) {
						value = null;
					}
					if (!Objects.isNull(value)) {
						if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
							if (jsonspeeding.has(entities)) {
								jsonspeeding.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonspeeding.getDouble(entities));
							} else {
								jsonspeeding.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
							if (jsonHarshAcceleration.has(entities)) {
								jsonHarshAcceleration.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonHarshAcceleration.getDouble(entities));
							} else {
								jsonHarshAcceleration.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
							if (jsonHasrhbraking.has(entities)) {
								jsonHasrhbraking.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonHasrhbraking.getDouble(entities));
							} else {
								jsonHasrhbraking.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
							if (jsonNightDriven.has(entities)) {
								jsonNightDriven.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonNightDriven.getDouble(entities));
							} else {
								jsonNightDriven.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
							if (jsonContiniousDrive.has(entities)) {
								jsonContiniousDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonContiniousDrive.getDouble(entities));
							} else {
								jsonContiniousDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
							if (jsonDailyDrive.has(entities)) {
								jsonDailyDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyDrive.getDouble(entities));
							} else {
								jsonDailyDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
							if (jsonDailyWordk.has(entities)) {
								jsonDailyWordk.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyWordk.getDouble(entities));
							} else {
								jsonDailyWordk.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
							if (jsonDailyRest.has(entities)) {
								jsonDailyRest.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyRest.getDouble(entities));
							} else {
								jsonDailyRest.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
							if (jsonWeeklyDrive.has(entities)) {
								jsonWeeklyDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyDrive.getDouble(entities));
							} else {
								jsonWeeklyDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
							if (jsonWeeklyRest.has(entities)) {
								jsonWeeklyRest.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyRest.getDouble(entities));
							} else {
								jsonWeeklyRest.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
							if (jsonPhoneDistraction.has(entities)) {
								jsonPhoneDistraction.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonPhoneDistraction.getDouble(entities));
							} else {
								jsonPhoneDistraction.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
							if (jsonSealBelt.has(entities)) {
								jsonSealBelt.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonSealBelt.getDouble(entities));
							} else {
								jsonSealBelt.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
							if (jsonFatigue.has(entities)) {
								jsonFatigue.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonFatigue.getDouble(entities));
							} else {
								jsonFatigue.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
							if (jsonWeeklyWordk.has(entities)) {
								jsonWeeklyWordk.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyWordk.getDouble(entities));
							} else {
								jsonWeeklyWordk.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
							if (jsonActiveFleet.has(entities)) {
								jsonActiveFleet.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonActiveFleet.getDouble(entities));
							} else {
								jsonActiveFleet.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
							if (jsonNoID.has(entities)) {
								jsonNoID.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonNoID.getDouble(entities));
							} else {
								jsonNoID.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
							if (jsonInsufficientTripDuration.has(entities)) {
								jsonInsufficientTripDuration.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonInsufficientTripDuration.getDouble(entities));
							} else {
								jsonInsufficientTripDuration.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else {
							if (!paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
								continue;
							}
							if (jsonSmoking.has(entities)) {
								jsonSmoking.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonSmoking.getDouble(entities));
							} else {
								jsonSmoking.put(smalldata.getEntities(), smalldata.getBnr());
							}
						}
					}
				}
				continue;
			}
			case 3: {
				for (final Resumexceptiontransporter resume3 : resumtrans) {
					final Transporter trans = resume3.getTransporter();
					final String entities = trans.getName();
					final BigDecimal totaldistance = this.vehactsum.sumdistanceperperiodfortransporter(date01, date02,
							trans.getTransporterid());
					final NbrtypeExceptions smalldata = Utils.tableaujson(paramtypename, entities, record, alert, alarm,
							new String[] { resume3.getSpeedingfnbr(), resume3.getHa(), resume3.getHb(),
									resume3.getNightdrive(), resume3.getContiniuousdrive(), resume3.getDailydrive(),
									resume3.getDailywork(), resume3.getDailyrest(), resume3.getWeeklydrive(),
									resume3.getWeeklyrest(), resume3.getPhonedistraction(), resume3.getSealbelt(),
									resume3.getFatigue(), resume3.getWeeklywork(), resume3.getActivefleet(),
									resume3.getNoid(), resume3.getInsuffisianttripduration(), resume3.getSmoking() },
							totaldistance);
					Double value;
					try {
						value = smalldata.getBnr();
						if (Utils.isZero(value, 0.0)) {
							value = null;
						}
					} catch (Exception ex) {
						value = null;
					}
					if (!Objects.isNull(value)) {
						if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
							if (jsonspeeding.has(entities)) {
								jsonspeeding.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonspeeding.getDouble(entities));
							} else {
								jsonspeeding.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
							if (jsonHarshAcceleration.has(entities)) {
								jsonHarshAcceleration.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonHarshAcceleration.getDouble(entities));
							} else {
								jsonHarshAcceleration.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
							if (jsonHasrhbraking.has(entities)) {
								jsonHasrhbraking.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonHasrhbraking.getDouble(entities));
							} else {
								jsonHasrhbraking.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
							if (jsonNightDriven.has(entities)) {
								jsonNightDriven.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonNightDriven.getDouble(entities));
							} else {
								jsonNightDriven.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
							if (jsonContiniousDrive.has(entities)) {
								jsonContiniousDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonContiniousDrive.getDouble(entities));
							} else {
								jsonContiniousDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
							if (jsonDailyDrive.has(entities)) {
								jsonDailyDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyDrive.getDouble(entities));
							} else {
								jsonDailyDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
							if (jsonDailyWordk.has(entities)) {
								jsonDailyWordk.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyWordk.getDouble(entities));
							} else {
								jsonDailyWordk.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
							if (jsonDailyRest.has(entities)) {
								jsonDailyRest.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyRest.getDouble(entities));
							} else {
								jsonDailyRest.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
							if (jsonWeeklyDrive.has(entities)) {
								jsonWeeklyDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyDrive.getDouble(entities));
							} else {
								jsonWeeklyDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
							if (jsonWeeklyRest.has(entities)) {
								jsonWeeklyRest.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyRest.getDouble(entities));
							} else {
								jsonWeeklyRest.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
							if (jsonPhoneDistraction.has(entities)) {
								jsonPhoneDistraction.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonPhoneDistraction.getDouble(entities));
							} else {
								jsonPhoneDistraction.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
							if (jsonSealBelt.has(entities)) {
								jsonSealBelt.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonSealBelt.getDouble(entities));
							} else {
								jsonSealBelt.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
							if (jsonFatigue.has(entities)) {
								jsonFatigue.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonFatigue.getDouble(entities));
							} else {
								jsonFatigue.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
							if (jsonWeeklyWordk.has(entities)) {
								jsonWeeklyWordk.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyWordk.getDouble(entities));
							} else {
								jsonWeeklyWordk.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
							if (jsonActiveFleet.has(entities)) {
								jsonActiveFleet.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonActiveFleet.getDouble(entities));
							} else {
								jsonActiveFleet.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
							if (jsonNoID.has(entities)) {
								jsonNoID.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonNoID.getDouble(entities));
							} else {
								jsonNoID.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
							if (jsonInsufficientTripDuration.has(entities)) {
								jsonInsufficientTripDuration.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonInsufficientTripDuration.getDouble(entities));
							} else {
								jsonInsufficientTripDuration.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else {
							if (!paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
								continue;
							}
							if (jsonSmoking.has(entities)) {
								jsonSmoking.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonSmoking.getDouble(entities));
							} else {
								jsonSmoking.put(smalldata.getEntities(), smalldata.getBnr());
							}
						}
					}
				}
				continue;
			}
			case 4: {
				for (final Resumexceptionaffiliate resume4 : resumaff) {
					final Customeraffiliate aff = resume4.getCustomeraffiliate();
					final String entities = aff.getName();
					final BigDecimal totaldistance = this.vehactsum.sumdistanceperperiodforaffiliate(date01, date02,
							aff.getAffiliateid());
					final NbrtypeExceptions smalldata = Utils.tableaujson(paramtypename, entities, record, alert, alarm,
							new String[] { resume4.getSpeedingfnbr(), resume4.getHa(), resume4.getHb(),
									resume4.getNightdrive(), resume4.getContiniuousdrive(), resume4.getDailydrive(),
									resume4.getDailywork(), resume4.getDailyrest(), resume4.getWeeklydrive(),
									resume4.getWeeklyrest(), resume4.getPhonedistraction(), resume4.getSealbelt(),
									resume4.getFatigue(), resume4.getWeeklywork(), resume4.getActivefleet(),
									resume4.getNoid(), resume4.getInsuffisianttripduration(), resume4.getSmoking() },
							totaldistance);
					Double value;
					try {
						value = smalldata.getBnr();
						if (Utils.isZero(value, 0.0)) {
							value = null;
						}
					} catch (Exception ex) {
						value = null;
					}
					if (!Objects.isNull(value)) {
						if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
							if (jsonspeeding.has(entities)) {
								jsonspeeding.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonspeeding.getDouble(entities));
							} else {
								jsonspeeding.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
							if (jsonHarshAcceleration.has(entities)) {
								jsonHarshAcceleration.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonHarshAcceleration.getDouble(entities));
							} else {
								jsonHarshAcceleration.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
							if (jsonHasrhbraking.has(entities)) {
								jsonHasrhbraking.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonHasrhbraking.getDouble(entities));
							} else {
								jsonHasrhbraking.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
							if (jsonNightDriven.has(entities)) {
								jsonNightDriven.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonNightDriven.getDouble(entities));
							} else {
								jsonNightDriven.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
							if (jsonContiniousDrive.has(entities)) {
								jsonContiniousDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonContiniousDrive.getDouble(entities));
							} else {
								jsonContiniousDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
							if (jsonDailyDrive.has(entities)) {
								jsonDailyDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyDrive.getDouble(entities));
							} else {
								jsonDailyDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
							if (jsonDailyWordk.has(entities)) {
								jsonDailyWordk.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyWordk.getDouble(entities));
							} else {
								jsonDailyWordk.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
							if (jsonDailyRest.has(entities)) {
								jsonDailyRest.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyRest.getDouble(entities));
							} else {
								jsonDailyRest.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
							if (jsonWeeklyDrive.has(entities)) {
								jsonWeeklyDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyDrive.getDouble(entities));
							} else {
								jsonWeeklyDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
							if (jsonWeeklyRest.has(entities)) {
								jsonWeeklyRest.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyRest.getDouble(entities));
							} else {
								jsonWeeklyRest.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
							if (jsonPhoneDistraction.has(entities)) {
								jsonPhoneDistraction.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonPhoneDistraction.getDouble(entities));
							} else {
								jsonPhoneDistraction.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
							if (jsonSealBelt.has(entities)) {
								jsonSealBelt.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonSealBelt.getDouble(entities));
							} else {
								jsonSealBelt.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
							if (jsonFatigue.has(entities)) {
								jsonFatigue.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonFatigue.getDouble(entities));
							} else {
								jsonFatigue.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
							if (jsonWeeklyWordk.has(entities)) {
								jsonWeeklyWordk.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyWordk.getDouble(entities));
							} else {
								jsonWeeklyWordk.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
							if (jsonActiveFleet.has(entities)) {
								jsonActiveFleet.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonActiveFleet.getDouble(entities));
							} else {
								jsonActiveFleet.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
							if (jsonNoID.has(entities)) {
								jsonNoID.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonNoID.getDouble(entities));
							} else {
								jsonNoID.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
							if (jsonInsufficientTripDuration.has(entities)) {
								jsonInsufficientTripDuration.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonInsufficientTripDuration.getDouble(entities));
							} else {
								jsonInsufficientTripDuration.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else {
							if (!paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
								continue;
							}
							if (jsonSmoking.has(entities)) {
								jsonSmoking.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonSmoking.getDouble(entities));
							} else {
								jsonSmoking.put(smalldata.getEntities(), smalldata.getBnr());
							}
						}
					}
				}
				continue;
			}
			case 5: {
				for (final Resumexceptionclient resume5 : resumclient) {
					final Customer custom = resume5.getClientid();
					final String entities = custom.getName();
					final List<Integer> listaff = this.cusaffR.findAllActiveCustomerAffID(custom.getCustomerid());
					BigDecimal totaldistance;
					if (!Objects.isNull(listaff) && !listaff.isEmpty()) {
						totaldistance = this.vehactsum.sumdistanceperperiodforclient(date01, date02, listaff);
					} else {
						totaldistance = BigDecimal.ONE;
					}
					final NbrtypeExceptions smalldata = Utils.tableaujson(paramtypename, entities, record, alert, alarm,
							new String[] { resume5.getSpeedingfnbr(), resume5.getHa(), resume5.getHb(),
									resume5.getNightdrive(), resume5.getContiniuousdrive(), resume5.getDailydrive(),
									resume5.getDailywork(), resume5.getDailyrest(), resume5.getWeeklydrive(),
									resume5.getWeeklyrest(), resume5.getPhonedistraction(), resume5.getSealbelt(),
									resume5.getFatigue(), resume5.getWeeklywork(), resume5.getActivefleet(),
									resume5.getNoid(), resume5.getInsuffisianttripduration(), resume5.getSmoking() },
							totaldistance);
					Double value;
					try {
						value = smalldata.getBnr();
						if (Utils.isZero(value, 0.0)) {
							value = null;
						}
					} catch (Exception ex) {
						value = null;
					}
					if (!Objects.isNull(value)) {
						if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
							if (jsonspeeding.has(entities)) {
								jsonspeeding.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonspeeding.getDouble(entities));
							} else {
								jsonspeeding.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
							if (jsonHarshAcceleration.has(entities)) {
								jsonHarshAcceleration.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonHarshAcceleration.getDouble(entities));
							} else {
								jsonHarshAcceleration.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
							if (jsonHasrhbraking.has(entities)) {
								jsonHasrhbraking.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonHasrhbraking.getDouble(entities));
							} else {
								jsonHasrhbraking.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
							if (jsonNightDriven.has(entities)) {
								jsonNightDriven.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonNightDriven.getDouble(entities));
							} else {
								jsonNightDriven.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
							if (jsonContiniousDrive.has(entities)) {
								jsonContiniousDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonContiniousDrive.getDouble(entities));
							} else {
								jsonContiniousDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
							if (jsonDailyDrive.has(entities)) {
								jsonDailyDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyDrive.getDouble(entities));
							} else {
								jsonDailyDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
							if (jsonDailyWordk.has(entities)) {
								jsonDailyWordk.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyWordk.getDouble(entities));
							} else {
								jsonDailyWordk.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
							if (jsonDailyRest.has(entities)) {
								jsonDailyRest.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonDailyRest.getDouble(entities));
							} else {
								jsonDailyRest.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
							if (jsonWeeklyDrive.has(entities)) {
								jsonWeeklyDrive.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyDrive.getDouble(entities));
							} else {
								jsonWeeklyDrive.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
							if (jsonWeeklyRest.has(entities)) {
								jsonWeeklyRest.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyRest.getDouble(entities));
							} else {
								jsonWeeklyRest.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
							if (jsonPhoneDistraction.has(entities)) {
								jsonPhoneDistraction.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonPhoneDistraction.getDouble(entities));
							} else {
								jsonPhoneDistraction.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
							if (jsonSealBelt.has(entities)) {
								jsonSealBelt.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonSealBelt.getDouble(entities));
							} else {
								jsonSealBelt.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
							if (jsonFatigue.has(entities)) {
								jsonFatigue.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonFatigue.getDouble(entities));
							} else {
								jsonFatigue.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
							if (jsonWeeklyWordk.has(entities)) {
								jsonWeeklyWordk.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonWeeklyWordk.getDouble(entities));
							} else {
								jsonWeeklyWordk.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
							if (jsonActiveFleet.has(entities)) {
								jsonActiveFleet.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonActiveFleet.getDouble(entities));
							} else {
								jsonActiveFleet.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
							if (jsonNoID.has(entities)) {
								jsonNoID.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonNoID.getDouble(entities));
							} else {
								jsonNoID.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
							if (jsonInsufficientTripDuration.has(entities)) {
								jsonInsufficientTripDuration.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonInsufficientTripDuration.getDouble(entities));
							} else {
								jsonInsufficientTripDuration.put(smalldata.getEntities(), smalldata.getBnr());
							}
						} else {
							if (!paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
								continue;
							}
							if (jsonSmoking.has(entities)) {
								jsonSmoking.put(smalldata.getEntities(),
										smalldata.getBnr() + jsonSmoking.getDouble(entities));
							} else {
								jsonSmoking.put(smalldata.getEntities(), smalldata.getBnr());
							}
						}
					}
				}
				continue;
			}
			}
		}
		final ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		final HttpServletRequest req = sra.getRequest();
		final Logusers log = new Logusers(user.getName(), "AUTHENTICATION_SUCCESS", "Resume Exception", new Date(),
				req.getRemoteAddr());
		this.logRepo.saveAndFlush(log);
		final JSONObject allresult = new JSONObject();
		if (jsonspeeding.length() > 0) {
			allresult.put(Utils.IntegerSpeeding + "", jsonspeeding);
		}
		if (jsonHarshAcceleration.length() > 0) {
			allresult.put(Utils.IntegerHarshAcceleration + "", jsonHarshAcceleration);
		}
		if (jsonHasrhbraking.length() > 0) {
			allresult.put(Utils.IntegerHarshBracking + "", jsonHasrhbraking);
		}
		if (jsonNightDriven.length() > 0) {
			allresult.put(Utils.IntegerNightDriven + "", jsonNightDriven);
		}
		if (jsonContiniousDrive.length() > 0) {
			allresult.put(Utils.IntegerContiniousDrive + "", jsonContiniousDrive);
		}
		if (jsonDailyDrive.length() > 0) {
			allresult.put(Utils.IntegerDailyDrive + "", jsonDailyDrive);
		}
		if (jsonDailyWordk.length() > 0) {
			allresult.put(Utils.IntegerDailyWordk + "", jsonDailyWordk);
		}
		if (jsonDailyRest.length() > 0) {
			allresult.put(Utils.IntegerDailyRest + "", jsonDailyRest);
		}
		if (jsonWeeklyDrive.length() > 0) {
			allresult.put(Utils.IntegerWeeklyDrive + "", jsonWeeklyDrive);
		}
		if (jsonWeeklyRest.length() > 0) {
			allresult.put(Utils.IntegerWeeklyRest + "", jsonWeeklyRest);
		}
		if (jsonPhoneDistraction.length() > 0) {
			allresult.put(Utils.IntegerPhoneDistraction + "", jsonPhoneDistraction);
		}
		if (jsonSealBelt.length() > 0) {
			allresult.put(Utils.IntegerSealBelt + "", jsonSealBelt);
		}
		if (jsonFatigue.length() > 0) {
			allresult.put(Utils.IntegerFatigue + "", jsonFatigue);
		}
		if (jsonWeeklyWordk.length() > 0) {
			allresult.put(Utils.IntegerWeeklyWordk + "", jsonWeeklyWordk);
		}
		if (jsonActiveFleet.length() > 0) {
			allresult.put(Utils.IntegerActiveFleet + "", jsonActiveFleet);
		}
		if (jsonNoID.length() > 0) {
			allresult.put(Utils.IntegerNoID + "", jsonNoID);
		}
		if (jsonInsufficientTripDuration.length() > 0) {
			allresult.put(Utils.IntegerInsufficientTripDuration + "", jsonInsufficientTripDuration);
		}
		if (jsonSmoking.length() > 0) {
			allresult.put(Utils.IntegerSmoking + "", jsonSmoking);
		}
		return allresult.toString();
	}

	@Override
	@Cacheable(value = "trendistance", key = "new org.springframework.cache.interceptor.SimpleKey(#user.userid, #codeclient,#codeafiliate,#transporter,#codevehicle,#codedriver,#datedebut,#datefin,#frequencetreands)")
	public TotalDistances trendistance(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codevehicle, final String codedriver, final String datedebut,
			final String datefin, final Integer frequencetreands) {
		final int k = 0;
		final List<TrendTotalDistanceBean> listbeantrend = new ArrayList<>();
		final List<Integer> listypexception = new ArrayList<>();
		String sqlin2 = "";
		final String andsql = "";
		final String completeRequest = "";
		final String allconstructsqlin = "";
		final String andsql2 = "";
		String completeRequest2 = "";
		String allconstructsqlin2 = "";
		final Date date01 = Utils.StringToDate("", datedebut, "yyyy-MM-dd");
		final Date date2 = Utils.StringToDate("", datefin, "yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		if (Objects.isNull(date2) || Objects.isNull(date01)) {
			final TrendTotalDistanceBean trend = new TrendTotalDistanceBean();
			trend.setTrendneouds(StaticValues.wrongdateformat);
			listbeantrend.add(trend);
			final TotalDistances total = new TotalDistances();
			total.setTotaldistance(listbeantrend);
			return total;
		}
		final List<Integer> listdriver = new ArrayList<>();
		final List<Integer> listvehicle = new ArrayList<>();
		final List<Integer> listransporter = new ArrayList<>();
		final List<Integer> listafiliate = new ArrayList<>();
		List<Integer> listclient = new ArrayList<>();
		final List<Integer> result = new ArrayList<>();
		final List<Resumexceptionclient> resumclient = new ArrayList<>();
		final List<Resumexceptionaffiliate> resumaff = new ArrayList<>();
		final List<Resumexceptiontransporter> resumtrans = new ArrayList<>();
		final List<Resumexceptionvehicle> resumveh = new ArrayList<>();
		final List<Resumexceptiondriver> resumdriver = new ArrayList<>();
		final List<Map<String, Object>> resultlist = null;
		List<Map<String, Object>> allresukt = new ArrayList<>();
		final List<Map<String, Object>> resultlist2 = null;
		List<Map<String, Object>> allresukt2 = new ArrayList<>();
		Integer typeInfos = -1;
		try {
			Label_1666: {
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
											break Label_1666;
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
										break Label_1666;
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
									break Label_1666;
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
								break Label_1666;
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
							break Label_1666;
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
		List<Object[]> listDate = null;
		if (frequencetreands == 1) {
			listDate = Utils.getReelMonthOrDaysBetweenDatesWithPeriods(date01, date2, true, 1);
		} else if (frequencetreands == 2) {
			listDate = Utils.getReelMonthOrDaysBetweenDatesWithPeriods(date01, date2, true, 7);
		} else if (frequencetreands == 3) {
			listDate = Utils.getReelMonthOrDaysBetweenDatesWithPeriods(date01, date2, false, 1);
		} else {
			if (frequencetreands != 4) {
				final TrendTotalDistanceBean trend = new TrendTotalDistanceBean();
				trend.setTrendneouds(StaticValues.frequencetreands);
				listbeantrend.add(trend);
				final TotalDistances total = new TotalDistances();
				total.setTotaldistance(listbeantrend);
				return total;
			}
			listDate = Utils.getReelMonthOrDaysBetweenDatesWithPeriods(date01, date2, false, 6);
		}
		for (final Object[] object : listDate) {
			final Date date3 = (Date) object[0];
			Date date4 = (Date) object[1];
			calendar = Calendar.getInstance();
			calendar.setTime(date4);
			if (frequencetreands == 1) {
				calendar.add(5, 1);
			} else if (frequencetreands == 2) {
				calendar.add(5, 7);
			} else if (frequencetreands == 3) {
				calendar.add(2, 1);
			} else if (frequencetreands == 4) {
				calendar.add(2, 6);
			}
			date4 = calendar.getTime();
			final TrendTotalDistanceBean bean = new TrendTotalDistanceBean();
			final String minisql2 = " bigexception.activitydate >= '" + Utils.DateFormat(date3, "yyyy-MM-dd")
					+ "' and bigexception.activitydate < '" + Utils.DateFormat(date4, "yyyy-MM-dd") + "'";
			allresukt = new ArrayList<>();
			if (typeInfos == 1) {
				allconstructsqlin2 = " bigexception.clientid in (" + sqlin2 + ") and " + minisql2;
				completeRequest2 = this.environment.getRequiredProperty("totaldistance.client");
				completeRequest2 = completeRequest2.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
				allresukt2 = this.jdbcTemplate.queryForList(completeRequest2);
			} else if (typeInfos == 2) {
				allconstructsqlin2 = " bigexception.affiliateid in (" + sqlin2 + ") and " + minisql2;
				completeRequest2 = this.environment.getRequiredProperty("totaldistance.affiliate");
				completeRequest2 = completeRequest2.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
				allresukt2 = this.jdbcTemplate.queryForList(completeRequest2);
			} else if (typeInfos == 3) {
				allconstructsqlin2 = " bigexception.transporterid in (" + sqlin2 + ") and " + minisql2;
				completeRequest2 = this.environment.getRequiredProperty("totaldistance.transporter");
				completeRequest2 = completeRequest2.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
				allresukt = this.jdbcTemplate.queryForList(completeRequest2);
			} else if (typeInfos == 4) {
				allconstructsqlin2 = " bigexception.vehicleid in (" + sqlin2 + ") and " + minisql2;
				completeRequest2 = this.environment.getRequiredProperty("totaldistance.vehicle");
				completeRequest2 = completeRequest2.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
				allresukt = this.jdbcTemplate.queryForList(completeRequest2);
			} else if (typeInfos == 5) {
				allconstructsqlin2 = " bigexception.driverid in (" + sqlin2 + ") and " + minisql2;
				completeRequest2 = this.environment.getRequiredProperty("totaldistance.driver");
				completeRequest2 = completeRequest2.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
				allresukt2 = this.jdbcTemplate.queryForList(completeRequest2);
			}
			bean.setTrendneouds(Utils.DateFormat(date3, "yyyy-MM-dd"));
			bean.setTotaldistance(allresukt2);
			listbeantrend.add(bean);
		}
		final TotalDistances total = new TotalDistances();
		total.setTotaldistance(listbeantrend);
		total.setTypeinfos(typeInfos);
		return total;
	}
//@Cacheable(value = "groupinvalidation",key = "new org.springframework.cache.interceptor.SimpleKey(#user.userid, #codeclient,#codeafiliate,#transporter,#codevehicle,#codedriver,#datedebut,#datefin,#listidtypeexception,#record,#alert,#alarm)")

	@Override
	@Cacheable(value = "trendresume", key = "new org.springframework.cache.interceptor.SimpleKey(#user.userid, #codeclient,#codeafiliate,#transporter,#codevehicle,#codedriver,#datedebut,#datefin,#listidtypeexception,#record,#alert,#alarm,#frequencetreands)")
	public List<TrendBean> trendresumexception(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codevehicle, final String codedriver, final String datedebut,
			final String datefin, final String listidtypeexception, final Boolean record, final Boolean alert,
			final Boolean alarm, final Integer frequencetreands) {
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
		final List<TrendBean> listbeantrend = new ArrayList<>();
		if (list.isEmpty()) {
			final TrendBean trend = new TrendBean();
			trend.setTrendneouds(StaticValues.Levelnotexists);
			trend.setData((List) null);
			return listbeantrend;
		}
		recordalertalarm = list.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(",", "(", ")"));
		final List<Integer> listypexception = new ArrayList<>();
		String sqlin = "";
		String sqlin2 = "";
		String andsql = "";
		String completeRequest = "";
		String allconstructsqlin = "";
		final String andsql2 = "";
		String completeRequest2 = "";
		String allconstructsqlin2 = "";
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
		final Date date2 = Utils.StringToDate("", datefin, "yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		if (Objects.isNull(date2) || Objects.isNull(date01)) {
			final TrendBean trend = new TrendBean();
			trend.setTrendneouds(StaticValues.wrongdateformat);
			trend.setData((List) null);
			return listbeantrend;
		}
		final List<Integer> listdriver = new ArrayList<>();
		final List<Integer> listvehicle = new ArrayList<>();
		final List<Integer> listransporter = new ArrayList<>();
		final List<Integer> listafiliate = new ArrayList<>();
		List<Integer> listclient = new ArrayList<>();
		final List<Integer> result = new ArrayList<>();
		final List<Resumexceptionclient> resumclient = new ArrayList<>();
		final List<Resumexceptionaffiliate> resumaff = new ArrayList<>();
		final List<Resumexceptiontransporter> resumtrans = new ArrayList<>();
		final List<Resumexceptionvehicle> resumveh = new ArrayList<>();
		final List<Resumexceptiondriver> resumdriver = new ArrayList<>();
		List<Map<String, Object>> resultlist = null;
		List<Map<String, Object>> allresukt = new ArrayList<>();
		final List<Map<String, Object>> resultlist2 = null;
		List<Map<String, Object>> allresukt2 = new ArrayList<>();
		List<Map<String, Object>> allresukt3 = new ArrayList<>();
		Integer typeInfos = -1;
		try {
			Label_1939: {
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
											sqlin2 = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE,
													user.getUserid() + "");
										} catch (Exception ex) {
										}
									}
									typeInfos = 1;
								} else {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid) {
										if (user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
											break Label_1939;
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
												sqlin2 = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE,
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
										break Label_1939;
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
								} catch (Exception ex3) {
								}
							}
						} else {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
									break Label_1939;
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
							} catch (Exception ex4) {
							}
						}
					} else {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
								break Label_1939;
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
						} catch (Exception ex5) {
						}
					}
				} else {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
							break Label_1939;
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
									sqlin = this.environment
											.getRequiredProperty("driveridid.listidfromuserightsbyuserid");
								}
								sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
							}
						} else {
							sqlin = codedriver;
						}
						typeInfos = 5;
					} catch (Exception ex6) {
					}
				}
			}
		} catch (Exception ex7) {
		}
		List<Object[]> listDate = null;
		if (frequencetreands == 1) {
			listDate = Utils.getReelMonthOrDaysBetweenDatesWithPeriods(date01, date2, true, 1);
		} else if (frequencetreands == 2) {
			listDate = Utils.getReelMonthOrDaysBetweenDatesWithPeriods(date01, date2, true, 7);
		} else if (frequencetreands == 3) {
			listDate = Utils.getReelMonthOrDaysBetweenDatesWithPeriods(date01, date2, false, 1);
		} else {
			if (frequencetreands != 4) {
				final TrendBean trend = new TrendBean();
				trend.setTrendneouds(StaticValues.frequencetreands);
				trend.setData((List) null);
				return listbeantrend;
			}
			listDate = Utils.getReelMonthOrDaysBetweenDatesWithPeriods(date01, date2, false, 6);
		}
		for (final Object[] object : listDate) {
			final Date date3 = (Date) object[0];
			Date date4 = (Date) object[1];
			calendar = Calendar.getInstance();
			calendar.setTime(date4);
			if (frequencetreands == 1) {
				calendar.add(5, 1);
			} else if (frequencetreands == 2) {
				calendar.add(5, 7);
			} else if (frequencetreands == 3) {
				calendar.add(2, 1);
			} else if (frequencetreands == 4) {
				calendar.add(2, 6);
			}
			date4 = calendar.getTime();
			final TrendBean bean = new TrendBean();
			final String minisql = " bigexception.level in " + recordalertalarm + " and bigexception.exceptiontype in ("
					+ andsql + ") and bigexception.startdatetime >= '" + Utils.DateFormat(date3, "yyyy-MM-dd")
					+ "' and bigexception.startdatetime < '" + Utils.DateFormat(date4, "yyyy-MM-dd") + "'";
			final String minisql2 = " bigexception.activitydate >= '" + Utils.DateFormat(date3, "yyyy-MM-dd")
					+ "' and bigexception.activitydate < '" + Utils.DateFormat(date4, "yyyy-MM-dd") + "'";
			allresukt = new ArrayList<>();
			allresukt3 = new ArrayList<>();
			if (typeInfos == 1) {
				allconstructsqlin2 = " bigexception.clientid in (" + sqlin2 + ") and " + minisql2;
				completeRequest2 = this.environment.getRequiredProperty("activevehicle.client");
				completeRequest2 = completeRequest2.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
				allresukt2 = this.jdbcTemplate.queryForList(completeRequest2);
				for (final Integer clientid : listclient) {
					sqlin = this.environment.getRequiredProperty("affiliateidperclientid.listidaffiliate");
					sqlin = sqlin.replaceFirst("CLIENT", clientid + "");
					allconstructsqlin = " bigexception.affiliateid in (" + sqlin + ") and " + minisql;
					completeRequest = this.environment.getRequiredProperty("resumexception.client");
					completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
					completeRequest = completeRequest.replaceFirst("CLIENT", clientid + "");
					resultlist = this.jdbcTemplate.queryForList(completeRequest);
					allresukt.addAll(resultlist);
					completeRequest = this.environment.getRequiredProperty("resuminvalidexception.client");
					completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
					completeRequest = completeRequest.replaceFirst("CLIENT", clientid + "");
					resultlist = this.jdbcTemplate.queryForList(completeRequest);
					allresukt3.addAll(resultlist);
				}
			} else if (typeInfos == 2) {
				allconstructsqlin = " bigexception.affiliateid in (" + sqlin + ") and " + minisql;
				completeRequest = this.environment.getRequiredProperty("resumexception.affiliate");
				completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
				allresukt = this.jdbcTemplate.queryForList(completeRequest);
				completeRequest = this.environment.getRequiredProperty("resuminvalidexception.affiliate");
				completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
				allresukt3 = this.jdbcTemplate.queryForList(completeRequest);
				allconstructsqlin2 = " bigexception.affiliateid in (" + sqlin + ") and " + minisql2;
				completeRequest2 = this.environment.getRequiredProperty("activevehicle.affiliate");
				completeRequest2 = completeRequest2.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
				allresukt2 = this.jdbcTemplate.queryForList(completeRequest2);
			} else if (typeInfos == 3) {
				allconstructsqlin = " bigexception.transporterid in (" + sqlin + ") and " + minisql;
				completeRequest = this.environment.getRequiredProperty("resumexception.transporter");
				completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
				allresukt = this.jdbcTemplate.queryForList(completeRequest);
				completeRequest = this.environment.getRequiredProperty("resuminvalidexception.transporter");
				completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
				allresukt3 = this.jdbcTemplate.queryForList(completeRequest);
				allconstructsqlin2 = " bigexception.transporterid in (" + sqlin + ") and " + minisql2;
				completeRequest2 = this.environment.getRequiredProperty("activevehicle.transporter");
				completeRequest2 = completeRequest2.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
				allresukt = this.jdbcTemplate.queryForList(completeRequest2);
			} else if (typeInfos == 4) {
				allconstructsqlin = " bigexception.vehicleid in (" + sqlin + ") and " + minisql;
				completeRequest = this.environment.getRequiredProperty("resumexception.vehicle");
				completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
				allresukt = this.jdbcTemplate.queryForList(completeRequest);
				completeRequest = this.environment.getRequiredProperty("resuminvalidexception.vehicle");
				completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
				allresukt3 = this.jdbcTemplate.queryForList(completeRequest);
				allconstructsqlin2 = " bigexception.vehicleid in (" + sqlin + ") and " + minisql2;
				completeRequest2 = this.environment.getRequiredProperty("activevehicle.vehicle");
				completeRequest2 = completeRequest2.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
				allresukt = this.jdbcTemplate.queryForList(completeRequest2);
			} else if (typeInfos == 5) {
				allconstructsqlin = " bigexception.driverid in (" + sqlin + ") and " + minisql;
				completeRequest = this.environment.getRequiredProperty("resumexception.driver");
				completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
				allresukt = this.jdbcTemplate.queryForList(completeRequest);
				completeRequest = this.environment.getRequiredProperty("resuminvalidexception.driver");
				completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
				allresukt3 = this.jdbcTemplate.queryForList(completeRequest);
				allconstructsqlin2 = " bigexception.driverid in (" + sqlin + ") and " + minisql2;
				completeRequest2 = this.environment.getRequiredProperty("activevehicle.driver");
				completeRequest2 = completeRequest2.replaceFirst("XXXXXXXXXX", allconstructsqlin2);
				allresukt2 = this.jdbcTemplate.queryForList(completeRequest2);
			}
			bean.setData(allresukt);
			bean.setTrendneouds(Utils.DateFormat(date3, "yyyy-MM-dd"));
			bean.setTypedata(typeInfos);
			bean.setActiveh(allresukt2);
			bean.setInvalidatedinfos(allresukt3);
			listbeantrend.add(bean);
		}
		return listbeantrend;
	}
//@Cacheable(value = "groupinvalidation",key = "new org.springframework.cache.interceptor.SimpleKey(#user.userid, #codeclient,#codeafiliate,#transporter,#codevehicle,#codedriver,#datedebut,#datefin,#listidtypeexception,#record,#alert,#alarm)")

	@Override
	@Cacheable(value = "trendresume100km", key = "new org.springframework.cache.interceptor.SimpleKey(#user.userid, #codeclient,#codeafiliate,#transporter,#codevehicle,#codedriver,#datedebut,#datefin,#listidtypeexception,#record,#alert,#alarm,#frequencetreands)")
	public List<TrendBean> trendresumexception100km(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codevehicle, final String codedriver, final String datedebut,
			final String datefin, final String listidtypeexception, final Boolean record, final Boolean alert,
			final Boolean alarm, final Integer frequencetreands) {
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
		final List<TrendBean> listbeantrend = new ArrayList<>();
		if (list.isEmpty()) {
			final TrendBean trend = new TrendBean();
			trend.setTrendneouds(StaticValues.Levelnotexists);
			trend.setData((List) null);
			return listbeantrend;
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
		final Date date2 = Utils.StringToDate("", datefin, "yyyy-MM-dd");
		final Calendar calendar = Calendar.getInstance();
		if (Objects.isNull(date2) || Objects.isNull(date01)) {
			final TrendBean trend = new TrendBean();
			trend.setTrendneouds(StaticValues.wrongdateformat);
			trend.setData((List) null);
			return listbeantrend;
		}
		final List<Integer> listdriver = new ArrayList<>();
		final List<Integer> listvehicle = new ArrayList<>();
		final List<Integer> listransporter = new ArrayList<>();
		final List<Integer> listafiliate = new ArrayList<>();
		List<Integer> listclient = new ArrayList<>();
		final List<Integer> result = new ArrayList<>();
		final List<Resumexceptionclient> resumclient = new ArrayList<>();
		final List<Resumexceptionaffiliate> resumaff = new ArrayList<>();
		final List<Resumexceptiontransporter> resumtrans = new ArrayList<>();
		final List<Resumexceptionvehicle> resumveh = new ArrayList<>();
		final List<Resumexceptiondriver> resumdriver = new ArrayList<>();
		List<Map<String, Object>> resultlist = null;
		List<Map<String, Object>> allresukt = new ArrayList<>();
		Integer typeInfos = -1;
		try {
			Label_1779: {
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
									typeInfos = 1;
								} else {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid) {
										if (user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
											break Label_1779;
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
										typeInfos = 1;
									} catch (Exception ex3) {
									}
								}
							} else {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
										&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
										break Label_1779;
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
									break Label_1779;
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
								break Label_1779;
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
							break Label_1779;
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
		} catch (Exception ex8) {
		}
		List<Object[]> listDate = null;
		if (frequencetreands == 1) {
			listDate = Utils.getReelMonthOrDaysBetweenDatesWithPeriods(date01, date2, true, 1);
		} else if (frequencetreands == 2) {
			listDate = Utils.getReelMonthOrDaysBetweenDatesWithPeriods(date01, date2, true, 7);
		} else if (frequencetreands == 3) {
			listDate = Utils.getReelMonthOrDaysBetweenDatesWithPeriods(date01, date2, false, 1);
		} else {
			if (frequencetreands != 4) {
				final TrendBean trend = new TrendBean();
				trend.setTrendneouds(StaticValues.frequencetreands);
				trend.setData((List) null);
				return listbeantrend;
			}
			listDate = Utils.getReelMonthOrDaysBetweenDatesWithPeriods(date01, date2, false, 6);
		}
		for (final Object[] object : listDate) {
			final Date date3 = (Date) object[0];
			Date date4 = (Date) object[1];
			calendar.setTime(date4);
			if (frequencetreands == 1) {
				calendar.add(5, 1);
			} else if (frequencetreands == 2) {
				calendar.add(5, 7);
			} else if (frequencetreands == 3) {
				calendar.add(2, 1);
			} else if (frequencetreands == 4) {
				calendar.add(2, 6);
			}
			date4 = calendar.getTime();
			final TrendBean bean = new TrendBean();
			final String date1String = Utils.DateFormat(date3, "yyyy-MM-dd");
			final String date2String = Utils.DateFormat(date4, "yyyy-MM-dd");
			final String minisql = " bigexception.level in " + recordalertalarm + " and bigexception.exceptiontype in ("
					+ andsql + ") and bigexception.startdatetime >= '" + date1String
					+ "' and bigexception.startdatetime < '" + date2String + "'";
			allresukt = new ArrayList<>();
			if (typeInfos == 1) {
				for (final Integer clientid : listclient) {
					sqlin = this.environment.getRequiredProperty("affiliateidperclientid.listidaffiliate");
					sqlin = sqlin.replaceFirst("CLIENT", clientid + "");
					allconstructsqlin = " bigexception.affiliateid in (" + sqlin + ") and " + minisql;
					completeRequest = this.environment.getRequiredProperty("resumexception100km.client");
					completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
					completeRequest = completeRequest.replaceFirst("CLIENT", clientid + "");
					completeRequest = completeRequest.replaceFirst("CLIENT", clientid + "");
					completeRequest = completeRequest.replaceFirst("SQLIN", sqlin);
					completeRequest = completeRequest.replaceFirst("DATE1", date1String);
					completeRequest = completeRequest.replaceFirst("DATE2", date2String);
					resultlist = this.jdbcTemplate.queryForList(completeRequest);
					allresukt.addAll(resultlist);
				}
			} else if (typeInfos == 2) {
				allconstructsqlin = " bigexception.affiliateid in (" + sqlin + ") and " + minisql;
				completeRequest = this.environment.getRequiredProperty("resumexception100km.affiliate");
				completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
			} else if (typeInfos == 3) {
				allconstructsqlin = " bigexception.transporterid in (" + sqlin + ") and " + minisql;
				completeRequest = this.environment.getRequiredProperty("resumexception100km.transporter");
				completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
			} else if (typeInfos == 4) {
				allconstructsqlin = " bigexception.vehicleid in (" + sqlin + ") and " + minisql;
				completeRequest = this.environment.getRequiredProperty("resumexception100km.vehicle");
				completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
			} else if (typeInfos == 5) {
				allconstructsqlin = " bigexception.driverid in (" + sqlin + ") and " + minisql;
				completeRequest = this.environment.getRequiredProperty("resumexception100km.driver");
				completeRequest = completeRequest.replaceFirst("XXXXXXXXXX", allconstructsqlin);
			}
			if (typeInfos != 1) {
				try {
					completeRequest = completeRequest.replaceFirst("SQLIN", sqlin);
					completeRequest = completeRequest.replaceFirst("DATE1", date1String);
					completeRequest = completeRequest.replaceFirst("DATE2", date2String);
					allresukt = this.jdbcTemplate.queryForList(completeRequest);
				} catch (Exception ex) {
					ex.printStackTrace();
					allresukt = new ArrayList<>();
				}
			}
			bean.setData(allresukt);
			bean.setTrendneouds(Utils.DateFormat(date3, "yyyy-MM-dd"));
			bean.setTypedata(typeInfos);
			listbeantrend.add(bean);
		}
		return listbeantrend;
	}

	public List<Drivertrips> vehicleTrips(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codevehicle, final String codedriver, final String datedebut,
			final String datefin) {
		Date date1 = Utils.StringToDate("", datedebut, "yyyy-MM-dd");
		Date date2 = Utils.StringToDate("", datefin, "yyyy-MM-dd");
		date1 = Utils.StringToDate("", datedebut + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
		date2 = Utils.StringToDateTimezone("", datefin + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
		List<Integer> listdriver = new ArrayList<>();
		final List<Integer> listdrivers = new ArrayList<>();
		List<Integer> listvehicle = new ArrayList<>();
		List<Integer> listransporter = new ArrayList<>();
		List<Integer> listafiliate = new ArrayList<>();
		List<Integer> listclient = new ArrayList<>();
		final List<Integer> result = new ArrayList<>();
		final List<Exceptions> resultexception = new ArrayList<>();
		final List<com.camtrack.entities.Exception> listexceptionothers = new ArrayList<>();
		final List<SummaryAllException> rankingtransporter = new ArrayList<>();
		final List<SummaryAllIntegerException> rankingInteger = new ArrayList<>();
		String sqlin = "";
		Label_1704: {
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
									} catch (Exception ex5) {
									}
								}
							} else {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid) {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
										break Label_1704;
									}
								}
								try {
									if (codeclient.equalsIgnoreCase("0")) {
										listclient = new ArrayList<>();
										if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
											listclient = this.usersR.findAllActiveCustomerId();
										} else {
											listclient = this.userRR.findAllReelCustomerIdOfUserId(user.getUserid());
										}
									} else {
										listclient = Stream.of(codeclient.split(",")).map(String::trim)
												.map(Integer::valueOf).collect(Collectors.toList());
									}
								} catch (Exception ex4) {
									listclient = new ArrayList<>();
								}
							}
						} else {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
									break Label_1704;
								}
							}
							try {
								if (codeafiliate.equalsIgnoreCase("0")) {
									listafiliate = new ArrayList<>();
									if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
										listafiliate = this.cusaffR.findAllActiveAffiliateId();
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
										listafiliate = this.jdbcTemplate.queryForList(sqlin, (Class) Integer.class);
									}
								} else {
									listafiliate = Stream.of(codeafiliate.split(",")).map(String::trim)
											.map(Integer::valueOf).collect(Collectors.toList());
								}
							} catch (Exception ex4) {
								listafiliate = new ArrayList<>();
							}
						}
					} else {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
								break Label_1704;
							}
						}
						try {
							if (transporter.equalsIgnoreCase("0")) {
								listransporter = new ArrayList<>();
								if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
									listransporter = this.transR.findAllActiveIdTransporter();
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
									listransporter = this.jdbcTemplate.queryForList(sqlin, (Class) Integer.class);
								}
							} else {
								listransporter = Stream.of(transporter.split(",")).map(String::trim)
										.map(Integer::valueOf).collect(Collectors.toList());
							}
						} catch (Exception ex4) {
							listransporter = new ArrayList<>();
						}
					}
				} else {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
							break Label_1704;
						}
					}
					try {
						if (codevehicle.equalsIgnoreCase("0")) {
							listvehicle = new ArrayList<>();
							if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
								listvehicle = this.vehR.findAllIdActiveVehicle();
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
								listdriver = this.jdbcTemplate.queryForList(sqlin, (Class) Integer.class);
							}
						} else {
							listvehicle = Stream.of(codevehicle.split(",")).map(String::trim).map(Integer::valueOf)
									.collect(Collectors.toList());
						}
					} catch (Exception ex4) {
						listvehicle = new ArrayList<>();
					}
				}
			} else {
				if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
						&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
						&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
						break Label_1704;
					}
				}
				try {
					if (codedriver.equalsIgnoreCase("0")) {
						listdriver = new ArrayList<>();
						if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
							listdriver = this.driverR.AllActivesDriver();
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
							listdriver = this.jdbcTemplate.queryForList(sqlin, (Class) Integer.class);
						}
					} else {
						listdriver = Stream.of(codedriver.split(",")).map(String::trim).map(Integer::valueOf)
								.collect(Collectors.toList());
					}
				} catch (Exception ex4) {
					listdriver = new ArrayList<>();
				}
			}
		}
		List<Integer> listId = new ArrayList<>();
		final int k = 0;
		List<Drivertrips> listvehtrips = new ArrayList<>();
		if (!listransporter.isEmpty()) {
			try {
				listvehtrips = this.tripsR.detailWorktimeTransporterList(listransporter, date1, date2);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (!listafiliate.isEmpty()) {
			final List<Customeraffiliate> listcusaff = this.cusaffR.findAllById(listafiliate);
			for (final Customeraffiliate customeraffiliate : listcusaff) {
				listId = new ArrayList<>();
				listId.add(customeraffiliate.getAffiliateid());
				final List<Transporter> listrans = this.transR.findAllActiveTransporterAffiliate(listId);
				listId = new ArrayList<>();
				for (final Transporter transporterid : listrans) {
					listId.add(transporterid.getTransporterid());
				}
				if (!listId.isEmpty()) {
					try {
						listvehtrips.addAll(this.tripsR.detailWorktimeTransporterList(listId, date1, date2));
					} catch (Exception ex2) {
						ex2.printStackTrace();
					}
				}
			}
		} else if (!listclient.isEmpty()) {
			final List<Customer> listcustom = this.customR.findAllById(listclient);
			for (final Customer cus : listcustom) {
				listId = new ArrayList<>();
				listId.add(cus.getCustomerid());
				final List<Customeraffiliate> listcusaff = this.cusaffR.findAllActiveCustomer(listId);
				for (final Customeraffiliate affiliates : listcusaff) {
					listId = new ArrayList<>();
					listId.add(affiliates.getAffiliateid());
					listId = this.transR.findAllActiveTransporterIdAffiliate(listId);
					if (!listId.isEmpty()) {
						try {
							listvehtrips.addAll(this.tripsR.detailWorktimeTransporterList(listId, date1, date2));
						} catch (Exception ex3) {
							ex3.printStackTrace();
						}
					}
				}
			}
		}
		return listvehtrips;
	}

	@Override
	@Cacheable(value = "worktime", key = "new org.springframework.cache.interceptor.SimpleKey(#user.userid, #codeclient,#codeafiliate,#transporter,#codevehicle,#codedriver,#datedebut,#datefin)")
	public ResponseEntity<?> worktime(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codevehicle, final String codedriver, final String datedebut,
			final String datefin) {
		final Date date1 = Utils.StringToDate("", datedebut, "yyyy-MM-dd");
		final Date date2 = Utils.StringToDate("", datefin, "yyyy-MM-dd");
		if (Objects.isNull(date2) || Objects.isNull(date1)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.wrongdateformat, StaticValues.wrongdateformat_Int));
		}
		Long worktime = 0L;
		Long totaldistance = 0L;
		Long idleTime = 0L;
		Long pausetime = 0L;
		Long drivetime = 0L;
		List<Integer> listdriver = new ArrayList<>();
		List<Integer> listvehicle = new ArrayList<>();
		List<Integer> listransporter = new ArrayList<>();
		List<Integer> listafiliate = new ArrayList<>();
		List<Integer> listclient = new ArrayList<>();
		final List<Integer> result = new ArrayList<>();
		final List<Resumexceptionclient> resumclient = new ArrayList<>();
		final List<Resumexceptionaffiliate> resumaff = new ArrayList<>();
		final List<Resumexceptiontransporter> resumtrans = new ArrayList<>();
		final List<Resumexceptionvehicle> resumveh = new ArrayList<>();
		final List<Resumexceptiondriver> resumdriver = new ArrayList<>();
		List<Object[]> listobject = new ArrayList<>();
		final Reelroleusers role = user.getReelrole();
		final Userrole roles = role.getIdtyperole();
		final Integer valeur = roles.getUserroleid();
		String sqlin = "";
		try {
			Label_1831: {
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
											break Label_1831;
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
									if (Objects.isNull(listclient)) {
										listclient = new ArrayList<>();
									}
								}
							} else {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
										&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
									if (user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
										break Label_1831;
									}
								}
								try {
									if (codeafiliate.equalsIgnoreCase("0")) {
										listafiliate = new ArrayList<>();
										if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
											listafiliate = this.cusaffR.findAllActiveAffiliateId();
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
											listafiliate = this.jdbcTemplate.queryForList(sqlin, (Class) Integer.class);
										}
									} else {
										listafiliate = Stream.of(codeafiliate.split(",")).map(String::trim)
												.map(Integer::valueOf).collect(Collectors.toList());
									}
								} catch (Exception ex) {
									listafiliate = new ArrayList<>();
								}
								if (Objects.isNull(listafiliate)) {
									listafiliate = new ArrayList<>();
								}
							}
						} else {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
									&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
								if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
									break Label_1831;
								}
							}
							try {
								if (transporter.equalsIgnoreCase("0")) {
									listransporter = new ArrayList<>();
									if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
										listransporter = this.transR.findAllActiveIdTransporter();
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
										listransporter = this.jdbcTemplate.queryForList(sqlin, (Class) Integer.class);
									}
								} else {
									listransporter = Stream.of(transporter.split(",")).map(String::trim)
											.map(Integer::valueOf).collect(Collectors.toList());
								}
							} catch (Exception ex) {
								listransporter = new ArrayList<>();
							}
							if (Objects.isNull(listransporter)) {
								listransporter = new ArrayList<>();
							}
						}
					} else {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
								break Label_1831;
							}
						}
						try {
							if (codevehicle.equalsIgnoreCase("0")) {
								listvehicle = new ArrayList<>();
								if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
									listvehicle = this.vehR.findAllIdActiveVehicle();
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
									listvehicle = this.jdbcTemplate.queryForList(sqlin, (Class) Integer.class);
								}
							} else {
								listvehicle = Stream.of(codevehicle.split(",")).map(String::trim).map(Integer::valueOf)
										.collect(Collectors.toList());
							}
						} catch (Exception ex) {
							listvehicle = new ArrayList<>();
						}
						if (Objects.isNull(listvehicle)) {
							listvehicle = new ArrayList<>();
						}
					}
				} else {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid) {
							break Label_1831;
						}
					}
					try {
						if (codedriver.equalsIgnoreCase("0")) {
							listdriver = new ArrayList<>();
							if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
								listdriver = this.driverR.AllActivesDriver();
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
								listdriver = this.jdbcTemplate.queryForList(sqlin, (Class) Integer.class);
							}
						} else {
							listdriver = Stream.of(codedriver.split(",")).map(String::trim).map(Integer::valueOf)
									.collect(Collectors.toList());
						}
					} catch (Exception ex) {
						listdriver = new ArrayList<>();
					}
					if (Objects.isNull(listdriver)) {
						listdriver = new ArrayList<>();
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (!Objects.isNull(listdriver) && !listdriver.isEmpty()) {
			listobject = this.vehactsum.sumalldistanceperperiodfordriver(date1, date2, listdriver);
		} else if (!Objects.isNull(listvehicle) && !listvehicle.isEmpty()) {
			listobject = this.vehactsum.sumalldistanceperperiodforvehicle(date1, date2, listvehicle);
		} else if (!Objects.isNull(listransporter) && !listransporter.isEmpty()) {
			listobject = this.vehactsum.sumalldistanceperperiodfortransporter(date1, date2, listransporter);
		} else if (!Objects.isNull(listafiliate) && !listafiliate.isEmpty()) {
			listobject = this.vehactsum.sumalldistanceperperiodforaffiliate(date1, date2, listafiliate);
		} else if (!Objects.isNull(listclient) && !listclient.isEmpty()) {
			listafiliate = this.cusaffR.findAllActiveCustomerAffID(listclient);
			if (!Objects.isNull(listafiliate) && !listafiliate.isEmpty()) {
				listobject = this.vehactsum.sumalldistanceperperiodforaffiliate(date1, date2, listafiliate);
			}
		}
		if (!listobject.isEmpty()) {
			final Object[] resultobject = listobject.get(0);
			if (!Objects.isNull(resultobject[1]) && resultobject[1] instanceof BigDecimal) {
				drivetime = ((BigDecimal) resultobject[1]).multiply(new BigDecimal(3600)).longValue();
			}
			if (!Objects.isNull(resultobject[3]) && resultobject[3] instanceof BigDecimal) {
				idleTime = ((BigDecimal) resultobject[3]).multiply(new BigDecimal(3600)).longValue();
			}
			if (!Objects.isNull(resultobject[4])) {
				if (resultobject[4] instanceof Double) {
					worktime = (new Double((double) resultobject[4])).longValue();
				}
				if (resultobject[4] instanceof BigDecimal) {
					worktime = ((BigDecimal) resultobject[4]).longValue();
				}
			}
			if (!Objects.isNull(resultobject[0])) {
				if (resultobject[0] instanceof BigDecimal) {
					totaldistance = ((BigDecimal) resultobject[0]).longValue();
				} else if (resultobject[0] instanceof Double) {
					totaldistance = (new Double((double) resultobject[0])).longValue();
				}
			}
		}
		pausetime = worktime - drivetime - idleTime;
		if (pausetime.intValue() < 0) {
			pausetime = 0L;
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Worktime(worktime, idleTime, pausetime, drivetime, totaldistance));
	}
}
