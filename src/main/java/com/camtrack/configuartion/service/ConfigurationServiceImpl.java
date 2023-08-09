//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.camtrack.affiliate.repository.CustomeraffiliateRepository;
import com.camtrack.bean.CreateParameterconfigInfos;
import com.camtrack.bean.ParameterconfigInfos;
import com.camtrack.config.StaticValues;
import com.camtrack.config.Utils;
import com.camtrack.configuartion.bean.ConfigurationBean;
import com.camtrack.configuartion.dao.ConfigurationDaoInterface;
import com.camtrack.entities.Customer;
import com.camtrack.entities.Customeraffiliate;
import com.camtrack.entities.Driver;
import com.camtrack.entities.Exceptionlevel;
import com.camtrack.entities.Listconfigtypes;
import com.camtrack.entities.Parameterconfig;
import com.camtrack.entities.Parametertype;
import com.camtrack.entities.Status;
import com.camtrack.entities.Success;
import com.camtrack.entities.Transporter;
import com.camtrack.entities.User;
import com.camtrack.entities.Userlogsactivity;
import com.camtrack.entities.Vehicle;
import com.camtrack.transporter.repository.TransporterRepository;
import com.camtrack.user.repository.CustomerRepository;
import com.camtrack.user.repository.DriverRepository;
import com.camtrack.user.repository.ExceptionlevelRepository;
import com.camtrack.user.repository.ListconfigtypesRepository;
import com.camtrack.user.repository.ParameterconfigRepository;
import com.camtrack.user.repository.ParametertypeRepository;
import com.camtrack.user.repository.StatusRepository;
import com.camtrack.user.repository.UserightsRepository;
import com.camtrack.user.repository.UserlogsactivityRepository;
import com.camtrack.user.repository.VehicleRepository;

@Service("configService")
public class ConfigurationServiceImpl implements ConfigurationServiceInterface {
	@Autowired
	ConfigurationDaoInterface configDao;
	@Autowired
	CustomeraffiliateRepository cusAffR;
	@Autowired
	CustomeraffiliateRepository customeraffR;
	@Autowired
	CustomerRepository customR;
	@Autowired
	DriverRepository driverR;
	@Autowired
	ExceptionlevelRepository exceptionalLevelR;
	@Autowired
	ListconfigtypesRepository listconfigs;
	@Autowired
	ParameterconfigRepository paramR;
	@Autowired
	ParametertypeRepository paramtypeR;
	@Autowired
	StatusRepository statR;
	@Autowired
	TransporterRepository transR;
	@Autowired
	UserlogsactivityRepository userlogRepo;
	@Autowired
	UserightsRepository userRightR;
	@Autowired
	VehicleRepository vehR;

	@Override
	public List CcAutSubSpeedMap(final int clientid, final int affiliateid) {
		return this.configDao.CcAutSubSpeedMap(clientid, affiliateid);
	}

	@Override
	public ResponseEntity<?> createparameterconfig(final CreateParameterconfigInfos config, final User user) {
		boolean createorupdate = true;
		final Long parameterconfig = config.getParameterconfigid();
		Parameterconfig configparam;
		if (!Objects.isNull(parameterconfig)) {
			createorupdate = false;
			configparam = this.paramR.findById(parameterconfig).orElse(null);
			if (Objects.isNull(configparam)) {
				return ResponseEntity.status(HttpStatus.OK).body(new Success(StaticValues.Parameterconfigsdontexists,
						StaticValues.Parameterconfigsdontexists_Int));
			}
		} else {
			configparam = new Parameterconfig();
		}
		Parameterconfig.init(createorupdate, user);
		if (!Objects.isNull(config.getAlarmdelayinput())) {
			configparam.setAlarmdelayinput(config.getAlarmdelayinput(), createorupdate);
		}
		if (!Objects.isNull(config.getAlarmthreshold())) {
			configparam.setAlarmthreshold(config.getAlarmthreshold(), createorupdate);
		}
		if (!Objects.isNull(config.getAlarmthresholdperc())) {
			configparam.setAlarmthresholdperc(config.getAlarmthresholdperc(), createorupdate);
		}
		if (!Objects.isNull(config.getAlertdelayinput())) {
			configparam.setAlertdelayinput(config.getAlertdelayinput(), createorupdate);
		}
		if (!Objects.isNull(config.getAlertthreshold())) {
			configparam.setAlertthreshold(config.getAlertthreshold(), createorupdate);
		}
		if (!Objects.isNull(config.getAlertthresholdperc())) {
			configparam.setAlertthresholdperc(config.getAlertthresholdperc(), createorupdate);
		}
		if (!Objects.isNull(config.getFrommonth())) {
			configparam.setFrommonth(config.getFrommonth());
		}
		if (!Objects.isNull(config.getRecordingdelayinput())) {
			configparam.setRecordingdelayinput(config.getRecordingdelayinput(), createorupdate);
		}
		if (!Objects.isNull(config.getRecordingthreshold())) {
			configparam.setRecordingthreshold(config.getRecordingthreshold(), createorupdate);
		}
		if (!Objects.isNull(config.getRecordingthresholdperc())) {
			configparam.setRecordingthresholdperc(config.getRecordingthresholdperc(), createorupdate);
		}
		if (!Objects.isNull(config.getRequiredresttime())) {
			configparam.setRequiredresttime(config.getRequiredresttime(), createorupdate);
		}
		if (!Objects.isNull(config.getRollingdays())) {
			configparam.setRollingdays(config.getRollingdays(), createorupdate);
		}
		if (!Objects.isNull(config.getThresholdlimit())) {
			configparam.setThresholdlimit(config.getThresholdlimit(), createorupdate);
		}
		if (!Objects.isNull(config.getTomonth())) {
			configparam.setTomonth(config.getTomonth());
		}
		if (!Objects.isNull(config.getFromtime())) {
			configparam
					.setFromtime(Utils.StringToDates(Utils.BigDecimalToDateString(config.getFromtime()), "HH:mm:ss"));
		}
		if (!Objects.isNull(config.getTotime())) {
			configparam.setTotime(Utils.StringToDates(Utils.BigDecimalToDateString(config.getTotime()), "HH:mm:ss"));
		}
		if (Objects.isNull(configparam.getThresholdlimit()) && Objects.isNull(config.getThresholdlimit())) {
			configparam.setThresholdlimit(new BigDecimal(-1));
		}
		if (!Objects.isNull(config.getTransporterid())) {
			final List<Integer> listransporterid = Arrays.asList(config.getTransporterid());
			if (!Objects.isNull(listransporterid) && !listransporterid.isEmpty()) {
				final List<Transporter> result = this.transR.findAllActiveTransporter(listransporterid);
				for (final Transporter trans : result) {
					final Customeraffiliate aff = trans.getAffiliateid();
					if (!Objects.isNull(aff)) {
						configparam.setClientaffiliateid(aff, createorupdate);
						final Customer cus = aff.getCustomerid();
						if (!Objects.isNull(cus)) {
							configparam.setCustomerid(cus, createorupdate);
						}
					}
					configparam.setTransporter(trans, createorupdate);
					this.paramR.saveAndFlush(configparam);
				}
			}
		}
		if (!Objects.isNull(config.getVehicleid())) {
			final List<Integer> vehicleid = Arrays.asList(config.getVehicleid());
			if (!Objects.isNull(vehicleid) && !vehicleid.isEmpty()) {
				final List<Vehicle> result2 = this.vehR.findListVehicleId(vehicleid);
				for (final Vehicle vehicle : result2) {
					final Transporter transp = vehicle.getTransporterid();
					if (!Objects.isNull(transp)) {
						configparam.setTransporter(transp, createorupdate);
						final Customeraffiliate aff = transp.getAffiliateid();
						if (!Objects.isNull(aff)) {
							configparam.setClientaffiliateid(aff, createorupdate);
							final Customer cus = aff.getCustomerid();
							if (!Objects.isNull(cus)) {
								configparam.setCustomerid(cus, createorupdate);
							}
						}
					}
					configparam.setVehicleid(vehicle, createorupdate);
					this.paramR.saveAndFlush(configparam);
				}
			}
		}
		if (Utils.countnumberligne(Parameterconfig.log) > 1) {
			final Listconfigtypes paramconfig = this.listconfigs.findById(Utils.ParamTypeconfig()).orElse(null);
			final Userlogsactivity loguser = new Userlogsactivity(paramconfig);
			loguser.setDates(new Date());
			loguser.setUserid(user);
			loguser.setLogsinfos(Parameterconfig.log);
			this.userlogRepo.saveAndFlush(loguser);
		}
		return ResponseEntity.status(HttpStatus.OK).body(new Success(StaticValues.success, StaticValues.success_Int));
	}

	@Override
	public Integer deactivateConfig(final Long configurationid, final User user) {
		return this.paramR.desactivateconfig(configurationid);
	}

	@Override
	public ResponseEntity<?> fetchlistparams(final User user) {
		final Integer userroleId = user.getReelrole().getIdtyperole().getUserroleid();
		if (userroleId == Utils.adminRoleId) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(Utils.converttoParameterconfigBean(this.paramR.findAllParamconfig()));
		}
		if (userroleId == Utils.clientRoleId) {
			final List<Integer> listcustomerid = this.userRightR.findAllReelCustomerIdOfUserId(user.getUserid());
			if (!Objects.isNull(listcustomerid) && !listcustomerid.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).body(
						Utils.converttoParameterconfigBean(this.paramR.findAllParamconfigOfCustomer(listcustomerid)));
			}
		} else if (userroleId == Utils.affiliateRoleId) {
			final List<Integer> listcustomerid = this.userRightR.findAllReelAffiliateIdOfUserId(user.getUserid());
			if (!Objects.isNull(listcustomerid) && !listcustomerid.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).body(
						Utils.converttoParameterconfigBean(this.paramR.findAllParamconfigOfAffiliate(listcustomerid)));
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ArrayList());
	}

	@Override
	public List getAffliates(final int clientid) {
		return this.configDao.getAffliates(clientid);
	}

	@Override
	public List getAffParameterMap(final int clientid, final int affiliateid) {
		return this.configDao.getAffParameterMap(clientid, affiliateid);
	}

	@Override
	public List getClientMap() {
		return this.configDao.getClientMap();
	}

	@Override
	public ResponseEntity<?> getListParameterById(final Integer customerid, final Integer affid,
			final Integer transporterid, final Integer vehicleid, final Integer driverid) {
		final List<Integer> recherche = new ArrayList<>();
		if (!Objects.isNull(driverid)) {
			recherche.add(driverid);
			return ResponseEntity.status(HttpStatus.OK)
					.body(Utils.converttoParameterconfigBean(this.paramR.findAllParamconfigOfDriver(recherche)));
		}
		if (!Objects.isNull(vehicleid)) {
			recherche.add(vehicleid);
			return ResponseEntity.status(HttpStatus.OK)
					.body(Utils.converttoParameterconfigBean(this.paramR.findAllParamconfigOfVehicle(recherche)));
		}
		if (!Objects.isNull(transporterid)) {
			recherche.add(transporterid);
			return ResponseEntity.status(HttpStatus.OK)
					.body(Utils.converttoParameterconfigBean(this.paramR.findAllParamconfigOfTransporter(recherche)));
		}
		if (!Objects.isNull(affid)) {
			recherche.add(affid);
			return ResponseEntity.status(HttpStatus.OK)
					.body(Utils.converttoParameterconfigBean(this.paramR.findAllParamconfigOfAffiliate(recherche)));
		}
		if (!Objects.isNull(customerid)) {
			recherche.add(customerid);
			return ResponseEntity.status(HttpStatus.OK)
					.body(Utils.converttoParameterconfigBean(this.paramR.findAllParamconfigOfCustomer(recherche)));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ArrayList());
	}

	@Override
	public Map getParameterById(final int clientid, final int affiliateid, final int transid,
			final int parametertypeid) {
		return this.configDao.getParameterById2(clientid, affiliateid, transid, parametertypeid);
	}

	@Override
	public List getParameterMap() {
		return this.configDao.getParameterMap();
	}

	@Override
	public ResponseEntity<?> listlevelalert(final User user) {
		return ResponseEntity.status(HttpStatus.OK).body(this.exceptionalLevelR.findAll());
	}

	@Override
	public List manualSubtractionMap(final int clientid, final int affiliateid) {
		return this.configDao.manualSubtractionMap(clientid, affiliateid);
	}

	@Override
	public boolean monthOverLap(final int frommonth, final int tomonth, final int configid, final int clientid,
			final int affiliateid) {
		return this.configDao.monthOverLap(frommonth, tomonth, configid, clientid, affiliateid);
	}

	@Override
	public List obcParamMap() {
		return this.configDao.obcParamMap();
	}

	@Override
	public List recoveryParam(final int clientid, final int affiliateid) {
		return this.configDao.recoveryParam(clientid, affiliateid);
	}

	@Override
	public ResponseEntity<?> saveConfiguration(final ConfigurationBean configurationBean, final User user)
			throws ParseException {
		return this.configDao.saveConfiguration(configurationBean, user);
	}

	@Override
	public ResponseEntity<?> updateparameterconfig(final ParameterconfigInfos config, final User user) {
		Date date = new Date();
		date = new Date();
		final Long parameterconfigid = config.getParameterconfigid();
		Parameterconfig configparam = null;
		Vehicle veh = null;
		Transporter trans = null;
		Driver driver = null;
		Customeraffiliate aff = null;
		Customer custom = null;
		boolean update = false;
		Parametertype paramtype = null;
		Boolean createorupdate;
		if (!Objects.isNull(parameterconfigid)) {
			configparam = this.paramR.findById(parameterconfigid).orElse(null);
			paramtype = configparam.getParametertypeid();
			createorupdate = false;
		} else {
			configparam = new Parameterconfig();
			final Integer parametertypeid = config.getParametertypeid();
			if (Objects.isNull(parametertypeid)) {
				return ResponseEntity.status(HttpStatus.OK).body(new Success(StaticValues.parametertypemustnotexists,
						StaticValues.parametertypemustnotexists_Int));
			}
			paramtype = this.paramtypeR.findById(parametertypeid).orElse(null);
			if (Objects.isNull(paramtype)) {
				return ResponseEntity.status(HttpStatus.OK).body(new Success(StaticValues.parametertypemustnotexists,
						StaticValues.parametertypemustnotexists_Int));
			}
			createorupdate = true;
		}
		if (!Objects.isNull(config.getVehicleid())) {
			veh = this.vehR.findById(config.getVehicleid()).orElse(null);
		}
		if (!Objects.isNull(config.getDriverid())) {
			driver = this.driverR.findById(config.getDriverid()).orElse(null);
		}
		if (!Objects.isNull(config.getTransporterid())) {
			trans = this.transR.findById(config.getTransporterid()).orElse(null);
		}
		if (!Objects.isNull(config.getClientaffiliateid())) {
			aff = this.cusAffR.findById(config.getClientaffiliateid()).orElse(null);
		}
		if (!Objects.isNull(config.getCustomerid())) {
			custom = this.customR.findById(config.getCustomerid()).orElse(null);
		}
		boolean first = false;
		Integer fromonth = config.getFrommonth();
		Integer tomonth = config.getTomonth();
		if (Objects.isNull(tomonth) || tomonth < 0 || tomonth > 12) {
			tomonth = 12;
		}
		if (Objects.isNull(fromonth) || fromonth < 0 || fromonth > 12) {
			fromonth = 0;
		}
		Parameterconfig.init(createorupdate, user);
		if (!Objects.isNull(driver)) {
			final List<Parameterconfig> listconfig = this.paramR.findByDriverConfig(driver.getDriverid(), paramtype,
					fromonth, tomonth);
			if (!Objects.isNull(listconfig) && !listconfig.isEmpty()) {
				configparam = listconfig.get(0);
				first = true;
				createorupdate = false;
				Parameterconfig.init(createorupdate, user);
			}
			configparam.setDriverid(driver, createorupdate);
			update = true;
		}
		if (!Objects.isNull(veh)) {
			if (!first) {
				final List<Parameterconfig> listconfig = this.paramR.findByVehicleConfig(veh.getVehicleid(), paramtype,
						fromonth, tomonth);
				if (!Objects.isNull(listconfig) && !listconfig.isEmpty()) {
					configparam = listconfig.get(0);
					first = true;
					createorupdate = false;
					Parameterconfig.init(createorupdate, user);
				}
			}
			configparam.setVehicleid(veh, createorupdate);
			update = true;
		}
		if (!Objects.isNull(trans)) {
			if (!first) {
				final List<Parameterconfig> listconfig = this.paramR.findByTransporterConfig(trans.getTransporterid(),
						paramtype, fromonth, tomonth);
				if (!Objects.isNull(listconfig) && !listconfig.isEmpty()) {
					configparam = listconfig.get(0);
					first = true;
					createorupdate = false;
					Parameterconfig.init(createorupdate, user);
				}
			}
			configparam.setTransporter(trans, createorupdate);
			update = true;
		}
		if (!Objects.isNull(aff)) {
			if (!first && Objects.isNull(trans)) {
				final List<Parameterconfig> listconfig = this.paramR.findByAffiliateConfig(aff.getAffiliateid(),
						paramtype.getParametertypeid(), fromonth, tomonth);
				if (!Objects.isNull(listconfig) && !listconfig.isEmpty()) {
					configparam = listconfig.get(0);
					first = true;
					createorupdate = false;
					Parameterconfig.init(createorupdate, user);
				}
			}
			configparam.setClientaffiliateid(aff, createorupdate);
			update = true;
		}
		if (!Objects.isNull(custom)) {
			if (!first && Objects.isNull(aff)) {
				final List<Parameterconfig> listconfig = this.paramR.findByCustomerConfig(custom.getCustomerid(),
						paramtype.getParametertypeid(), fromonth, tomonth);
				if (!Objects.isNull(listconfig) && !listconfig.isEmpty()) {
					configparam = listconfig.get(0);
					first = true;
					createorupdate = false;
					Parameterconfig.init(createorupdate, user);
				}
			}
			configparam.setCustomerid(custom, createorupdate);
			update = true;
		}
		if (!Objects.isNull(config.getPrevthreshold())) {
			configparam.setPrevthreshold(config.getPrevthreshold(), createorupdate);
			update = true;
		}
		if (!update) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.AllMajorParameterisNull, StaticValues.AllMajorParameterisNull_Int));
		}
		if (!Objects.isNull(config.getAlarmdelayinput())) {
			configparam.setAlarmdelayinput(config.getAlarmdelayinput(), createorupdate);
		}
		if (!Objects.isNull(config.getAlarmthreshold())) {
			configparam.setAlarmthreshold(config.getAlarmthreshold(), createorupdate);
		}
		if (!Objects.isNull(config.getAlarmthresholdperc())) {
			configparam.setAlarmthresholdperc(config.getAlarmthresholdperc(), createorupdate);
		}
		if (!Objects.isNull(config.getAlertdelayinput())) {
			configparam.setAlertdelayinput(config.getAlertdelayinput(), createorupdate);
		}
		if (!Objects.isNull(config.getAlertthreshold())) {
			configparam.setAlertthreshold(config.getAlertthreshold(), createorupdate);
		}
		if (!Objects.isNull(config.getAlertthresholdperc())) {
			configparam.setAlertthresholdperc(config.getAlertthresholdperc(), createorupdate);
		}
		configparam.setFrommonth(fromonth);
		if (!Objects.isNull(config.getMinimumdistance())) {
			configparam.setMinimumdistance(config.getMinimumdistance(), createorupdate);
		} else {
			configparam.setMinimumdistance(BigDecimal.ZERO);
		}
		if (!Objects.isNull(config.getRecordingdelayinput())) {
			configparam.setRecordingdelayinput(config.getRecordingdelayinput(), createorupdate);
		}
		if (!Objects.isNull(config.getRecordingthreshold())) {
			configparam.setRecordingthreshold(config.getRecordingthreshold(), createorupdate);
		}
		if (!Objects.isNull(config.getRecordingthresholdperc())) {
			configparam.setRecordingthresholdperc(config.getRecordingthresholdperc(), createorupdate);
		}
		if (!Objects.isNull(config.getRequiredresttime())) {
			configparam.setRequiredresttime(config.getRequiredresttime(), createorupdate);
		}
		if (!Objects.isNull(config.getRollingdays())) {
			configparam.setRollingdays(config.getRollingdays(), createorupdate);
		}
		if (!Objects.isNull(config.getThresholdlimit())) {
			configparam.setThresholdlimit(config.getThresholdlimit(), createorupdate);
		}
		configparam.setTomonth(tomonth);
		if (!Objects.isNull(config.getFromtime())) {
			try {
				configparam.setFromtime(
						Utils.StringToDates(Utils.BigDecimalToDateString(config.getFromtime()), "HH:mm:ss"));
			} catch (Exception ex) {
			}
		}
		if (!Objects.isNull(config.getTotime())) {
			try {
				configparam
						.setTotime(Utils.StringToDates(Utils.BigDecimalToDateString(config.getTotime()), "HH:mm:ss"));
			} catch (Exception ex2) {
			}
		}
		if (!Objects.isNull(paramtype)) {
			configparam.setParametertypeid(paramtype);
		}
		configparam.setUpdatedby(user);
		configparam.setUpdatedon(date);
		if (createorupdate) {
			configparam.setCreatedby(user);
			configparam.setCreatedon(date);
		}
		Status sta = this.statR.findById(1).orElse(null);
		if (!Objects.isNull(config.getAlertlevel())) {
			final Exceptionlevel level = this.exceptionalLevelR.findById(config.getAlertlevel()).orElse(null);
			if (!Objects.isNull(level)) {
				configparam.setDefaultlevelid(level);
			}
		} else {
			final Exceptionlevel level = this.exceptionalLevelR.findById(2).orElse(null);
			if (!Objects.isNull(level)) {
				configparam.setDefaultlevelid(level);
			}
		}
		if (Objects.isNull(configparam.getThresholdlimit()) && Objects.isNull(config.getThresholdlimit())) {
			configparam.setThresholdlimit(new BigDecimal(-1));
		}
		if (!Objects.isNull(config.getActivesalert())) {
			if (config.getActivesalert()) {
				if (!Objects.isNull(sta)) {
					configparam.setStatus(sta);
					configparam = this.paramR.saveAndFlush(configparam);
				}
			} else {
				sta = this.statR.findById(2).orElse(null);
				configparam.setStatus(sta);
				configparam = this.paramR.saveAndFlush(configparam);
			}
		} else if (!Objects.isNull(sta)) {
			configparam.setStatus(sta);
			configparam = this.paramR.saveAndFlush(configparam);
		}
		if (Utils.countnumberligne(Parameterconfig.log) > 1) {
			final Listconfigtypes paramconfig = this.listconfigs.findById(Utils.ParamTypeconfig()).orElse(null);
			final Userlogsactivity loguser = new Userlogsactivity(paramconfig);
			loguser.setDates(new Date());
			loguser.setUserid(user);
			loguser.setLogsinfos(Parameterconfig.log);
			this.userlogRepo.saveAndFlush(loguser);
		}
		return ResponseEntity.status(HttpStatus.OK).body(new Success(StaticValues.success, StaticValues.success_Int));
	}

	@Override
	public List visualParamMap(final int clientid, final int affiliateid) {
		return this.configDao.visualParamMap(clientid, affiliateid);
	}
}
