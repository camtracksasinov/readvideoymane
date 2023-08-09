//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.camtrack.affiliate.repository.CustomeraffiliateRepository;
import com.camtrack.bean.AutomaticreportBean;
import com.camtrack.bean.ListAllIdBean;
import com.camtrack.config.StaticValues;
import com.camtrack.config.Utils;
import com.camtrack.entities.Accessrights;
import com.camtrack.entities.Customer;
import com.camtrack.entities.Customeraffiliate;
import com.camtrack.entities.Driver;
import com.camtrack.entities.Formatexports;
import com.camtrack.entities.Frequences;
import com.camtrack.entities.Listconfigtypes;
import com.camtrack.entities.Listreports;
import com.camtrack.entities.Reportautomatics;
import com.camtrack.entities.Success;
import com.camtrack.entities.Timerange;
import com.camtrack.entities.Transporter;
import com.camtrack.entities.User;
import com.camtrack.entities.Userlogsactivity;
import com.camtrack.entities.Vehicle;
import com.camtrack.reports.bean.RawstatisticsBean;
import com.camtrack.reports.repository.FormatexportsRepository;
import com.camtrack.reports.repository.FrequencesRepository;
import com.camtrack.reports.repository.ListreportsRepository;
import com.camtrack.reports.repository.ReportautomaticsRepository;
import com.camtrack.reports.repository.TimerangeRepository;
import com.camtrack.reports.service.AutomaticReportService;
import com.camtrack.transporter.repository.TransporterRepository;
import com.camtrack.user.repository.CustomerRepository;
import com.camtrack.user.repository.DriverRepository;
import com.camtrack.user.repository.ListconfigtypesRepository;
import com.camtrack.user.repository.UserlogsactivityRepository;
import com.camtrack.user.repository.VehicleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("reportservices")
public class AutomaticReportServiceImpl implements AutomaticReportService {
	@Value("${userrrole.affiliateid}")
	private Integer affiliateid;
	@Autowired
	CustomeraffiliateRepository affR;
	@Value("${userrrole.clientid}")
	private Integer clientid;
	@Autowired
	CustomerRepository customR;
	@Autowired
	DriverRepository drv;
	@Autowired
	private Environment environment;
	@Autowired
	FormatexportsRepository formatR;
	@Autowired
	FrequencesRepository freqR;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	ListconfigtypesRepository listconfig;
	@Autowired
	UserlogsactivityRepository logR;
	@Autowired
	ListreportsRepository reportnR;
	@Autowired
	ReportautomaticsRepository reportR;
	@Value("${userrrole.superadminid}")
	private Integer superadminid;
	@Autowired
	TimerangeRepository timerR;
	@Value("${userrrole.transporterid}")
	private Integer transporterid0;
	@Autowired
	TransporterRepository transR;
	@Autowired
	VehicleRepository vehR;

	@Autowired
	CacheManager cacheManager;

	@Override
	public List<Map<String, Object>> activelistallconfigs(final String nextdate, final String enddate,
			final String smallrequest) {
		Date dates = Utils.StringToDates(nextdate, "yyyy-MM-dd");
		if (Objects.isNull(dates)) {
			return null;
		}
		String completeRequest;
		if (!Objects.isNull(enddate)) {
			dates = Utils.StringToDates(enddate, "yyyy-MM-dd");
			if (Objects.isNull(dates)) {
				return null;
			}
			completeRequest = "select ids as idconf,idreportname as idreport,idfreq,idformat,requestbodyforservice as bodyreport,emailist as mailist,mailsubject as subj,mailbody as bodymail,hourofrequest as hrs,dayofweekordayofmonth as dayorweek,ranges as idranges,createon,updateon,createby,updateby from reportautomatics where actives = 1 and nextdate >= '"
					+ nextdate + "' and nextdate <='" + enddate + "'" + smallrequest;
		} else {
			completeRequest = "select ids as idconf,idreportname as idreport,idfreq,idformat,requestbodyforservice as bodyreport,emailist as mailist,mailsubject as subj,mailbody as bodymail,hourofrequest as hrs,dayofweekordayofmonth as dayorweek,ranges as idranges,createon,updateon,createby,updateby from reportautomatics where actives = 1 and nextdate <= '"
					+ nextdate + "'" + smallrequest;
		}
		return this.jdbcTemplate.queryForList(completeRequest);
	}

	@Override
	public ResponseEntity<?> autoreports(final User user, final AutomaticreportBean autobean, final String subject,
			final String emailist, final Integer idreportname, final Short idfrequence, final Short formatreport,
			final Short timerange, final String hourofrequest, Short dayofweekordayofmonth, final Date date,
			final String bodymail) {
		Long autoid = null;
		if (!Objects.isNull(autobean)) {
			autoid = autobean.getAutomaticreport();
		}
		final Date datecreate = new Date();
		Boolean createorupdate;
		Reportautomatics reportauto;
		if (Objects.isNull(autoid) && !Objects.isNull(autobean)) {
			createorupdate = true;
			reportauto = new Reportautomatics();
			reportauto.setCreateby(user);
			reportauto.setCreateon(datecreate);
			if (Objects.isNull(subject) || Objects.isNull(emailist) || Objects.isNull(idreportname)
					|| Objects.isNull(idfrequence) || Objects.isNull(formatreport) || Objects.isNull(timerange)
					|| Objects.isNull(hourofrequest)) {
				return ResponseEntity.status(HttpStatus.OK).body(
						new Success(StaticValues.ReportNonNullParameter, StaticValues.ReportNonNullParameter_Int));
			}
		} else {
			reportauto = this.reportR.findById(autoid).orElse(null);
			if (Objects.isNull(reportauto)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.ReportId, StaticValues.ReportId_Int));
			}
			createorupdate = false;
		}
		reportauto.setUpdateby(user);
		reportauto.setUpdateon(datecreate);
		Reportautomatics.init(createorupdate, user);
		if (!Objects.isNull(subject)) {
			reportauto.setMailsubject(subject, createorupdate);
		}
		if (!Objects.isNull(bodymail)) {
			reportauto.setMailbody(bodymail, createorupdate);
		}
		if (!Objects.isNull(hourofrequest)) {
			reportauto.setHourofrequest(hourofrequest, createorupdate);
		}
		Frequences freq = null;
		List<Frequences> listfreq = null;
		if (!Objects.isNull(idfrequence)) {
			listfreq = this.freqR.findbyFrequencesOrCodes(idfrequence, idfrequence + "");
			if (!Objects.isNull(listfreq) && !listfreq.isEmpty()) {
				freq = listfreq.get(0);
				reportauto.setIdfreq(freq, createorupdate);
			} else if (createorupdate) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.ReportParameterNotMacth.replaceFirst("XXXX", "Frequences"),
								StaticValues.ReportParameterNotMacth_Int));
			}
		}
		if (!createorupdate && Objects.isNull(freq)) {
			freq = reportauto.getIdfreq();
		}
		if (!Objects.isNull(dayofweekordayofmonth)) {
			if (dayofweekordayofmonth == 0) {
				dayofweekordayofmonth = 1;
			}
			if (freq.getUniqueid().equalsIgnoreCase("1002")
					&& (dayofweekordayofmonth > 7 || dayofweekordayofmonth < 0)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.ReportIdWeekly, StaticValues.ReportIdWeekly_Int));
			}
			if (freq.getUniqueid().equalsIgnoreCase("1003")
					&& (dayofweekordayofmonth > 31 || dayofweekordayofmonth < 0)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.ReportIdMonthly, StaticValues.ReportIdMonthly_Int));
			}
			reportauto.setDayofweekordayofmonth(dayofweekordayofmonth, createorupdate);
		}
		Formatexports formatexp = null;
		List<Formatexports> listformat = null;
		if (!Objects.isNull(formatreport)) {
			listformat = this.formatR.findbyFormatExports(formatreport, formatreport + "");
			if (!Objects.isNull(listformat) && !listformat.isEmpty()) {
				formatexp = listformat.get(0);
				reportauto.setIdformat(formatexp, createorupdate);
			} else if (createorupdate) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(
								StaticValues.ReportParameterNotMacth.replaceFirst("XXXX", "Format Of exports"),
								StaticValues.ReportParameterNotMacth_Int));
			}
		}
		Listreports reportname = null;
		List<Listreports> listreportname = null;
		if (!Objects.isNull(idreportname)) {
			listreportname = this.reportnR.findbyReportId(idreportname, idreportname);
			if (!Objects.isNull(listreportname) && !listreportname.isEmpty()) {
				reportname = listreportname.get(0);
				reportauto.setIdreportname(reportname, createorupdate);
			} else if (createorupdate) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.ReportParameterNotMacth.replaceFirst("XXXX", "report Name"),
								StaticValues.ReportParameterNotMacth_Int));
			}
		}
		Timerange timeranges = null;
		if (!Objects.isNull(timerange)) {
			timeranges = this.timerR.findById(timerange).orElse(null);
			if (!Objects.isNull(timeranges)) {
				reportauto.setRanges(timeranges, createorupdate);
			} else if (createorupdate) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.ReportParameterNotMacth.replaceFirst("XXXX", "Time range"),
								StaticValues.ReportParameterNotMacth_Int));
			}
		}
		String json = null;
		if (!Objects.isNull(autobean)) {
			final ObjectMapper mapper = new ObjectMapper();
			try {
				final Integer reportid = reportname.getCodereports();
				if (reportid == 1013 || reportid == 1012 || reportid == 1014 || reportid == 1015) {
					json = mapper.writeValueAsString(Utils.autotoreporttolist(autobean));
				} else if (reportid == 1011) {
					json = "{\"listids\":[]}";
				} else if (reportid == 1016) {
					final RawstatisticsBean rawstatistic = new RawstatisticsBean();
					rawstatistic.setAffiliateId(autobean.getListaffiliateids());
					rawstatistic.setClientid(autobean.getListclientids());
					rawstatistic.setDriverid(autobean.getListdriverids());
					rawstatistic.setTransporterid(autobean.getListtransporterids());
					final Date[] alldate = Utils.calculperioddate(date, timeranges, freq);
					Calendar cal = Calendar.getInstance();
					cal.setTime(alldate[0]);
					Integer yearormonth = cal.get(1);
					rawstatistic.setStartyear(yearormonth);
					yearormonth = cal.get(2) + 1;
					rawstatistic.setStartmonth(yearormonth);
					cal = Calendar.getInstance();
					cal.setTime(alldate[1]);
					yearormonth = cal.get(1);
					rawstatistic.setEndyear(yearormonth);
					yearormonth = cal.get(2) + 1;
					rawstatistic.setEndmonth(yearormonth);
					json = mapper.writeValueAsString(rawstatistic);
				}
				if (!Objects.isNull(json)) {
					reportauto.setRequestbodyforservice(json, createorupdate);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		reportauto.setActives(1);
		reportauto.setNextdate(date);
		List<Integer> lis = null;
		lis = null;
		Integer[] listid = autobean.getListclientids();
		if (!Objects.isNull(listid) && listid.length > 0) {
			lis = Arrays.stream(listid).collect(Collectors.toList());
		}
		final List<Integer> clientId0 = lis;
		lis = null;
		listid = autobean.getListaffiliateids();
		if (!Objects.isNull(listid) && listid.length > 0) {
			lis = Arrays.stream(listid).collect(Collectors.toList());
		}
		final List<Integer> affiliateId00 = lis;
		lis = null;
		listid = autobean.getListtransporterids();
		if (!Objects.isNull(listid) && listid.length > 0) {
			lis = Arrays.stream(listid).collect(Collectors.toList());
		}
		final List<Integer> transporterId = lis;
		lis = null;
		listid = autobean.getListvehicleids();
		if (!Objects.isNull(listid) && listid.length > 0) {
			lis = Arrays.stream(listid).collect(Collectors.toList());
		}
		final List<Integer> vehicleid = lis;
		lis = null;
		listid = autobean.getListdriverids();
		if (!Objects.isNull(listid) && listid.length > 0) {
			lis = Arrays.stream(listid).collect(Collectors.toList());
		}
		final List<Integer> driverId = lis;
		final List<Integer> listaffiliateid = null;
		Transporter trans = null;
		Customeraffiliate aff = null;
		Customer cus = null;
		String result = "_";
		result = "";
		if (!Objects.isNull(vehicleid) && !vehicleid.isEmpty()) {
			final Vehicle veh = this.vehR.findById(vehicleid.get(0)).orElse(null);
			trans = veh.getTransporterid();
			aff = trans.getAffiliateid();
			cus = aff.getCustomerid();
			final Integer transporterid = trans.getTransporterid();
			final Integer affid = aff.getAffiliateid();
			final Integer customid = cus.getCustomerid();
			result = " and transid = " + transporterid;
		} else if (!Objects.isNull(driverId) && !driverId.isEmpty()) {
			try {
				final Driver driv = this.drv.findById(driverId.get(0)).orElse(null);
				trans = driv.getTransporterid();
				aff = trans.getAffiliateid();
				cus = aff.getCustomerid();
				final Integer transporterid = trans.getTransporterid();
				final Integer affid = aff.getAffiliateid();
				final Integer customid = cus.getCustomerid();
				result = " and transid = " + transporterid;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (!Objects.isNull(transporterId) && !transporterId.isEmpty()) {
			try {
				trans = this.transR.findById(transporterId.get(0)).orElse(null);
				aff = trans.getAffiliateid();
				cus = aff.getCustomerid();
				final Integer transporterid = transporterId.get(0);
				final Integer affid = aff.getAffiliateid();
				final Integer customid = cus.getCustomerid();
				if (transporterId.size() == 1) {
					result = " and transid = " + transporterId.get(0);
				} else {
					result = " and affid = " + affid;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (!Objects.isNull(affiliateId00) && !affiliateId00.isEmpty()) {
			aff = this.affR.findById(affiliateId00.get(0)).orElse(null);
			cus = aff.getCustomerid();
			final Integer affid = affiliateId00.get(0);
			final Integer customid = cus.getCustomerid();
			if (affiliateId00.size() == 1) {
				result = " and affid = " + affiliateId00.get(0);
			} else {
				result = " and customid = " + customid;
			}
		} else if (!Objects.isNull(clientId0) && !clientId0.isEmpty() && clientId0.size() == 1) {
			final Integer customid = clientId0.get(0);
			result = " and customid = " + customid;
		}
		if (!Objects.isNull(trans)) {
			reportauto.setTransid(trans);
		}
		if (!Objects.isNull(aff)) {
			reportauto.setAffid(aff);
		}
		if (!Objects.isNull(cus)) {
			reportauto.setCustomid(cus);
		}
		reportauto.setEmailist(emailist);
		this.reportR.saveAndFlush(reportauto);
		if (Utils.countnumberligne(Reportautomatics.log) > 1) {
			final Date datelog = new Date();
			final Listconfigtypes paramconfigs = this.listconfig.findById(Utils.UserAccessTypeconfig()).orElse(null);
			final Userlogsactivity loguser = new Userlogsactivity(paramconfigs);
			loguser.setDates(datelog);
			loguser.setUserid(user);
			loguser.setLogsinfos(Accessrights.log);
			this.logR.saveAndFlush(loguser);
		}
		return ResponseEntity.status(HttpStatus.OK).body(new Success(StaticValues.success, StaticValues.success_Int));
	}

	@Override
	@Cacheable("listformatrepor")
	public List<Map<String, Object>> listformatrepor() {
		final String completeRequest = "select form.format as nm, form.uniqueid as id,ids from formatexports form";
		return this.jdbcTemplate.queryForList(completeRequest);
	}

	@Override
	@Cacheable("listfrequence")
	public List<Map<String, Object>> listfrequence() {
		final String completeRequest = "select freq.frequences as nm, freq.uniqueid as id,ids from frequences freq";
		return this.jdbcTemplate.queryForList(completeRequest);
	}

	@Override
	@Cacheable("listimerange")
	public List<Map<String, Object>> listimerange() {
		final String completeRequest = "select form.names as nm, form.ids from timerange form";
		return this.jdbcTemplate.queryForList(completeRequest);
	}

	@Override
	@Cacheable("lisreportname")
	public List<Map<String, Object>> listreportnames() {
		final String completeRequest = "select reportname as nm,ids as id,codereports as code  from listreports";
		return this.jdbcTemplate.queryForList(completeRequest);
	}

	@Override
	public List<Map<String, Object>> newactivelistallconfigs(final ListAllIdBean rawstat, final String startdate,
			final String endate, final User user) {
		List<Integer> lis = null;
		lis = null;
		Integer[] listid = rawstat.getListclientids();
		if (!Objects.isNull(listid) && listid.length > 0) {
			lis = Arrays.stream(listid).collect(Collectors.toList());
		}
		final List<Integer> clientId0 = lis;
		lis = null;
		listid = rawstat.getListaffiliateids();
		if (!Objects.isNull(listid) && listid.length > 0) {
			lis = Arrays.stream(listid).collect(Collectors.toList());
		}
		List<Integer> affiliateId00 = lis;
		lis = null;
		listid = rawstat.getListtransporterids();
		if (!Objects.isNull(listid) && listid.length > 0) {
			lis = Arrays.stream(listid).collect(Collectors.toList());
		}
		List<Integer> transporterId = lis;
		lis = null;
		listid = rawstat.getListvehicleids();
		if (!Objects.isNull(listid) && listid.length > 0) {
			lis = Arrays.stream(listid).collect(Collectors.toList());
		}
		final List<Integer> vehicleid = lis;
		lis = null;
		listid = rawstat.getListdriverids();
		if (!Objects.isNull(listid) && listid.length > 0) {
			lis = Arrays.stream(listid).collect(Collectors.toList());
		}
		final List<Integer> driverId = lis;
		final List<Integer> listaffiliateid = null;
		String result = "";
		String sqlin = "";
		if (!Objects.isNull(vehicleid) && !vehicleid.isEmpty()) {
			if (vehicleid.size() == 1 && vehicleid.get(0) == 0) {
				if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
						&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
						&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid0) {
						return this.activelistallconfigs(startdate, endate, result);
					}
				}
				try {
					if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
						return this.activelistallconfigs(startdate, endate, "");
					}
					final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
					if (userroleid == this.clientid) {
						sqlin = this.environment.getRequiredProperty("vehicleid.cuslistidfromuserightsbyuserid");
					} else if (userroleid == this.affiliateid) {
						sqlin = this.environment.getRequiredProperty("vehicleid.afflistidfromuserightsbyuserid");
					} else if (userroleid == this.transporterid0) {
						sqlin = this.environment.getRequiredProperty("vehicleid.listidfromuserightsbyuserid");
					}
					sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
					transporterId = this.jdbcTemplate.queryForList(sqlin, (Class) Integer.class);
					if (!Objects.isNull(transporterId) && !transporterId.isEmpty()) {
						result = " and transid = " + transporterId.get(0);
					}
				} catch (Exception ex2) {
				}
			} else {
				final Vehicle veh = this.vehR.findById(vehicleid.get(0)).orElse(null);
				final Transporter trans = veh.getTransporterid();
				final Integer transporterid = trans.getTransporterid();
				result = " and transid = " + transporterid;
			}
		} else if (!Objects.isNull(driverId) && !driverId.isEmpty()) {
			try {
				if (driverId.size() == 1 && driverId.get(0) == 0) {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid0) {
							return this.activelistallconfigs(startdate, endate, result);
						}
					}
					try {
						if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
							return this.activelistallconfigs(startdate, endate, "");
						}
						final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
						if (userroleid == this.clientid) {
							sqlin = this.environment.getRequiredProperty("driveridid.cuslistidfromuserightsbyuserid");
						} else if (userroleid == this.affiliateid) {
							sqlin = this.environment.getRequiredProperty("driveridid.afflistidfromuserightsbyuserid");
						} else if (userroleid == this.transporterid0) {
							sqlin = this.environment.getRequiredProperty("driveridid.listidfromuserightsbyuserid");
						}
						sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
						transporterId = this.jdbcTemplate.queryForList(sqlin, (Class) Integer.class);
						if (!Objects.isNull(transporterId) && !transporterId.isEmpty()) {
							result = " and transid = " + transporterId.get(0);
						}
					} catch (Exception ex3) {
					}
				} else {
					final Driver driv = this.drv.findById(driverId.get(0)).orElse(null);
					final Transporter trans = driv.getTransporterid();
					final Integer transporterid = trans.getTransporterid();
					result = " and transid = " + transporterid;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (!Objects.isNull(transporterId) && !transporterId.isEmpty()) {
			try {
				if (transporterId.size() == 1) {
					if (transporterId.get(0) == 0) {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid
								&& user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
							if (user.getReelrole().getIdtyperole().getUserroleid() != this.transporterid0) {
								return this.activelistallconfigs(startdate, endate, result);
							}
						}
						try {
							if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
								return this.activelistallconfigs(startdate, endate, "");
							}
							final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
							if (userroleid == this.clientid) {
								sqlin = this.environment
										.getRequiredProperty("transporterid.cuslistidfromuserightsbyuserid");
							} else if (userroleid == this.affiliateid) {
								sqlin = this.environment
										.getRequiredProperty("transporterid.afflistidfromuserightsbyuserid");
							} else if (userroleid == this.transporterid0) {
								sqlin = this.environment
										.getRequiredProperty("transporterid.listidfromuserightsbyuserid");
							}
							sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
							transporterId = this.jdbcTemplate.queryForList(sqlin, (Class) Integer.class);
							if (!Objects.isNull(transporterId) && !transporterId.isEmpty()) {
								final Transporter trans = this.transR.findById(transporterId.get(0)).orElse(null);
								final Customeraffiliate aff = trans.getAffiliateid();
								final Integer affid = aff.getAffiliateid();
								result = " and affid = " + affid;
							}
						} catch (Exception ex4) {
						}
					} else {
						result = " and transid = " + transporterId.get(0);
					}
				} else {
					final Transporter trans = this.transR.findById(transporterId.get(0)).orElse(null);
					final Customeraffiliate aff = trans.getAffiliateid();
					final Integer affid = aff.getAffiliateid();
					result = " and affid = " + affid;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (!Objects.isNull(affiliateId00) && !affiliateId00.isEmpty()) {
			if (affiliateId00.size() == 1) {
				if (affiliateId00.get(0) != 0) {
					result = " and affid = " + affiliateId00.get(0);
				} else {
					if (user.getReelrole().getIdtyperole().getUserroleid() != this.superadminid
							&& user.getReelrole().getIdtyperole().getUserroleid() != this.clientid) {
						if (user.getReelrole().getIdtyperole().getUserroleid() != this.affiliateid) {
							return this.activelistallconfigs(startdate, endate, result);
						}
					}
					try {
						if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
							return this.activelistallconfigs(startdate, endate, "");
						}
						final Integer userroleid = user.getReelrole().getIdtyperole().getUserroleid();
						if (userroleid == this.clientid) {
							sqlin = this.environment.getRequiredProperty("affiliateid.cuslistidfromuserightsbyuserid");
						} else if (userroleid == this.affiliateid) {
							sqlin = this.environment.getRequiredProperty("affiliateid.listidfromuserightsbyuserid");
						}
						sqlin = sqlin.replaceFirst(Utils.UserIDSTRINGToREPLACE, user.getUserid() + "");
						affiliateId00 = this.jdbcTemplate.queryForList(sqlin, (Class) Integer.class);
						if (!Objects.isNull(affiliateId00) && !affiliateId00.isEmpty()) {
							final Customeraffiliate aff = this.affR.findById(affiliateId00.get(0)).orElse(null);
							final Customer cus = aff.getCustomerid();
							final Integer affid = affiliateId00.get(0);
							final Integer customid = cus.getCustomerid();
							result = " and customid = " + customid;
						}
					} catch (Exception ex5) {
					}
				}
			} else {
				final Customeraffiliate aff = this.affR.findById(affiliateId00.get(0)).orElse(null);
				final Customer cus = aff.getCustomerid();
				final Integer affid = affiliateId00.get(0);
				final Integer customid = cus.getCustomerid();
				result = " and customid = " + customid;
			}
		} else if (!Objects.isNull(clientId0) && !clientId0.isEmpty()) {
			if (clientId0.size() != 1 || clientId0.get(0) == 0) {
				return this.activelistallconfigs(startdate, endate, "");
			}
			final Integer customid = clientId0.get(0);
			result = " and customid = " + customid;
		}
		return this.activelistallconfigs(startdate, endate, result);
	}

	@Override
	public ResponseEntity<?> updatereportstatus(final Long[] listconfigreportid) {
		final List<Long> listreport = Arrays.asList(listconfigreportid);
		final List<Reportautomatics> listreports = this.reportR.findAllById(listreport);
		final Integer coderwstatistics = 1016;
		final Integer coderewardranking = 1017;
		final List<Integer> listint = new ArrayList<>();
		listint.add(coderwstatistics);
		listint.add(coderewardranking);
		final Date date = new Date();
		final String daily = "1001";
		final String weekly = "1002";
		final String monthly = "1003";
		Calendar calendar = Calendar.getInstance();
		Integer yearormonth = 0;
		final ObjectMapper mapper = new ObjectMapper();
		for (final Reportautomatics reportautomatics : listreports) {
			calendar.setTime(date);
			final Listreports report = reportautomatics.getIdreportname();
			if (!listint.contains(report.getCodereports())) {
				final Frequences freq = reportautomatics.getIdfreq();
				if (freq.getUniqueid().equalsIgnoreCase(daily)) {
					calendar.add(5, 1);
					reportautomatics.setNextdate(calendar.getTime());
				} else if (freq.getUniqueid().equalsIgnoreCase(weekly)) {
					calendar.add(5, 7);
					reportautomatics.setNextdate(calendar.getTime());
				} else if (freq.getUniqueid().equalsIgnoreCase(monthly)) {
					calendar.add(2, 1);
					reportautomatics.setNextdate(calendar.getTime());
				}
				this.reportR.saveAndFlush(reportautomatics);
			} else {
				final String body = reportautomatics.getRequestbodyforservice();
				final Frequences freq = reportautomatics.getIdfreq();
				final Date[] listdate = Utils.calculperioddate(date, reportautomatics.getRanges(), freq);
				try {
					final RawstatisticsBean rawstatistic = (RawstatisticsBean) mapper.readValue(body,
							(Class) RawstatisticsBean.class);
					if (report.getCodereports() == 1016) {
						final Date[] alldate = Utils.calculperioddate(date, reportautomatics.getRanges(), freq);
						calendar = Calendar.getInstance();
						calendar.setTime(alldate[0]);
						yearormonth = calendar.get(1);
						rawstatistic.setStartyear(yearormonth);
						yearormonth = calendar.get(2) + 1;
						rawstatistic.setStartmonth(yearormonth);
						calendar = Calendar.getInstance();
						calendar.setTime(alldate[1]);
						yearormonth = calendar.get(1);
						rawstatistic.setEndyear(yearormonth);
						yearormonth = calendar.get(2) + 1;
						rawstatistic.setEndmonth(yearormonth);
						final String json = mapper.writeValueAsString(rawstatistic);
						reportautomatics.setRequestbodyforservice(json);
					}
					if (freq.getUniqueid().equalsIgnoreCase(daily)) {
						calendar.add(5, 1);
						reportautomatics.setNextdate(calendar.getTime());
					} else if (freq.getUniqueid().equalsIgnoreCase(weekly)) {
						calendar.add(5, 7);
						reportautomatics.setNextdate(calendar.getTime());
					} else if (freq.getUniqueid().equalsIgnoreCase(monthly)) {
						calendar.add(2, 1);
						reportautomatics.setNextdate(calendar.getTime());
					}
					this.reportR.saveAndFlush(reportautomatics);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
