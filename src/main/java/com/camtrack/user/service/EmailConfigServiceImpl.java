//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.camtrack.affiliate.repository.CustomeraffiliateRepository;
import com.camtrack.config.StaticValues;
import com.camtrack.config.Utils;
import com.camtrack.entities.Customer;
import com.camtrack.entities.Customeraffiliate;
import com.camtrack.entities.Emailconfig;
import com.camtrack.entities.Listconfigtypes;
import com.camtrack.entities.Parametertype;
import com.camtrack.entities.Success;
import com.camtrack.entities.Transporter;
import com.camtrack.entities.User;
import com.camtrack.entities.Userlogsactivity;
import com.camtrack.entities.Vehicle;
import com.camtrack.transporter.repository.TransporterRepository;
import com.camtrack.user.bean.EmailConfigBean;
import com.camtrack.user.bean.Levelbean;
import com.camtrack.user.bean.ListEmailConfig;
import com.camtrack.user.bean.SmallEmailConfigBean;
import com.camtrack.user.bean.UpdateEmailConfigBean;
import com.camtrack.user.dao.EmailConfigDaoInterface;
import com.camtrack.user.repository.CustomerRepository;
import com.camtrack.user.repository.EmailconfigRepository;
import com.camtrack.user.repository.ListconfigtypesRepository;
import com.camtrack.user.repository.ParametertypeRepository;
import com.camtrack.user.repository.UserlogsactivityRepository;
import com.camtrack.user.repository.UsersRepository;
import com.camtrack.user.repository.VehicleRepository;

@Service("emailConfigService")
public class EmailConfigServiceImpl implements EmailConfigServiceInterface {
	@Autowired
	CustomeraffiliateRepository affR;
	@Autowired
	CustomerRepository customR;
	@Autowired
	EmailConfigDaoInterface dao;
	@Autowired
	EmailconfigRepository emailconfigRepo;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	ListconfigtypesRepository listconfig;
	@Autowired
	ParametertypeRepository paramtypeR;
	@Value("${userrrole.superadminid}")
	private Integer superadminid;
	@Autowired
	TransporterRepository transR;
	@Autowired
	UserlogsactivityRepository userlogRepo;
	@Autowired
	UsersRepository userR;
	@Autowired
	VehicleRepository vehR;

	private List<ListEmailConfig> fetchbyuser(final User user, final List<Long> listallconfigid,
			final Boolean getAllConfig) {
		List<String> listemail = new ArrayList<>();
		List<Integer> listcostomer = new ArrayList<>();
		List<Integer> listaffiliate = new ArrayList<>();
		List<Integer> listtransporter = new ArrayList<>();
		List<Integer> listvehicle = new ArrayList<>();
		final List<Integer> listparamtype = this.paramtypeR.findAllExceptionIdSuperAdmin();
		if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
			if (getAllConfig) {
				listemail = this.emailconfigRepo.findAllListNativeOnEmailconfig();
				listcostomer = this.emailconfigRepo.findAllListCustomerForSuperAdmin();
				listaffiliate = this.emailconfigRepo.findAllListAffiliateForSuperAdmin();
				listtransporter = this.emailconfigRepo.findAllListTransporterForSuperAdmin();
				listvehicle = this.emailconfigRepo.findAllListVehicleForSuperAdmin();
			} else if (!Objects.isNull(listallconfigid) && !listallconfigid.isEmpty()) {
				listemail = this.emailconfigRepo.findAllListNativeOnEmailconfigByListID(listallconfigid);
				if (Objects.isNull(listemail)) {
					listemail = new ArrayList<>();
				}
				listcostomer = this.emailconfigRepo.findAllListCustomerForSuperAdminByListID(listallconfigid);
				if (Objects.isNull(listcostomer)) {
					listcostomer = new ArrayList<>();
				}
				listaffiliate = this.emailconfigRepo.findAllListAffiliateForSuperAdminByListID(listallconfigid);
				if (Objects.isNull(listaffiliate)) {
					listaffiliate = new ArrayList<>();
				}
				listtransporter = this.emailconfigRepo.findAllListTransporterForSuperAdminByListID(listallconfigid);
				if (Objects.isNull(listtransporter)) {
					listtransporter = new ArrayList<>();
				}
				listvehicle = this.emailconfigRepo.findAllListVehicleForSuperAdminByListID(listallconfigid);
				if (Objects.isNull(listvehicle)) {
					listvehicle = new ArrayList<>();
				}
			} else {
				listemail = new ArrayList<>();
				listcostomer = new ArrayList<>();
				listaffiliate = new ArrayList<>();
				listtransporter = new ArrayList<>();
				listvehicle = new ArrayList<>();
			}
		}
		List<SmallEmailConfigBean> listbeanemails = new ArrayList<>();
		final List<ListEmailConfig> finalResult = new ArrayList<>();
		final int k = 0;
		for (final String email : listemail) {
			final ListEmailConfig smalllistconfig = new ListEmailConfig();
			listbeanemails = new ArrayList<>();
			for (final Integer custom : listcostomer) {
				if (!Objects.isNull(custom)) {
					final SmallEmailConfigBean smallbean = new SmallEmailConfigBean();
					final List<Levelbean> listlevelbean = new ArrayList<>();
					for (final Integer idtypeparam : listparamtype) {
						final Emailconfig emailconf = this.emailconfigRepo
								.findEmailconfigForCustomer(email, custom, idtypeparam).orElse(null);
						if (!Objects.isNull(emailconf)) {
							final Levelbean levelbean = new Levelbean();
							levelbean.setIdtypelement(idtypeparam);
							levelbean.setAlarm(emailconf.getAlarmstatus());
							levelbean.setAlert(emailconf.getAlertstatus());
							levelbean.setRecord(emailconf.getRecordstatus());
							listlevelbean.add(levelbean);
						}
					}
					if (listlevelbean.isEmpty()) {
						continue;
					}
					smallbean.setLevelbeam(listlevelbean);
					smallbean.setElementsid(custom);
					smallbean.setTypelementsid(1);
					listbeanemails.add(smallbean);
				}
			}
			for (final Integer affiliateid : listaffiliate) {
				if (!Objects.isNull(affiliateid)) {
					final SmallEmailConfigBean smallbean = new SmallEmailConfigBean();
					final List<Levelbean> listlevelbean = new ArrayList<>();
					for (final Integer idtypeparam : listparamtype) {
						final Emailconfig emailconf = this.emailconfigRepo
								.findEmailconfigForAffiliate(email, affiliateid, idtypeparam).orElse(null);
						if (!Objects.isNull(emailconf)) {
							final Levelbean levelbean = new Levelbean();
							levelbean.setIdtypelement(idtypeparam);
							levelbean.setAlarm(emailconf.getAlarmstatus());
							levelbean.setAlert(emailconf.getAlertstatus());
							levelbean.setRecord(emailconf.getRecordstatus());
							listlevelbean.add(levelbean);
						}
					}
					if (listlevelbean.isEmpty()) {
						continue;
					}
					smallbean.setLevelbeam(listlevelbean);
					smallbean.setElementsid(affiliateid);
					smallbean.setTypelementsid(2);
					listbeanemails.add(smallbean);
				}
			}
			for (final Integer transporterid : listtransporter) {
				if (!Objects.isNull(transporterid)) {
					final SmallEmailConfigBean smallbean = new SmallEmailConfigBean();
					final List<Levelbean> listlevelbean = new ArrayList<>();
					for (final Integer idtypeparam : listparamtype) {
						final Emailconfig emailconf = this.emailconfigRepo
								.findEmailconfigForTransporter(email, transporterid, idtypeparam).orElse(null);
						if (!Objects.isNull(emailconf)) {
							final Levelbean levelbean = new Levelbean();
							levelbean.setIdtypelement(idtypeparam);
							levelbean.setAlarm(emailconf.getAlarmstatus());
							levelbean.setAlert(emailconf.getAlertstatus());
							levelbean.setRecord(emailconf.getRecordstatus());
							listlevelbean.add(levelbean);
						}
					}
					if (listlevelbean.isEmpty()) {
						continue;
					}
					smallbean.setLevelbeam(listlevelbean);
					smallbean.setElementsid(transporterid);
					smallbean.setTypelementsid(3);
					listbeanemails.add(smallbean);
				}
			}
			for (final Integer vehicleid : listvehicle) {
				if (!Objects.isNull(vehicleid)) {
					final SmallEmailConfigBean smallbean = new SmallEmailConfigBean();
					final List<Levelbean> listlevelbean = new ArrayList<>();
					for (final Integer idtypeparam : listparamtype) {
						final Emailconfig emailconf = this.emailconfigRepo
								.findEmailconfigForVehicle(email, vehicleid, idtypeparam).orElse(null);
						if (!Objects.isNull(emailconf)) {
							final Levelbean levelbean = new Levelbean();
							levelbean.setIdtypelement(idtypeparam);
							levelbean.setAlarm(emailconf.getAlarmstatus());
							levelbean.setAlert(emailconf.getAlertstatus());
							levelbean.setRecord(emailconf.getRecordstatus());
							listlevelbean.add(levelbean);
						}
					}
					if (listlevelbean.isEmpty()) {
						continue;
					}
					smallbean.setLevelbeam(listlevelbean);
					smallbean.setElementsid(vehicleid);
					smallbean.setTypelementsid(4);
					listbeanemails.add(smallbean);
				}
			}
			smalllistconfig.setEmailuser(email);
			try {
				final Object[][] object = this.userR.findNativeByEmail(email);
				if (!Objects.isNull(object)) {
					smalllistconfig.setNameuser(Utils.ObjectToString(object[0][0]));
					smalllistconfig.setUserid(Utils.castIntegerObject(object[0][1]));
				}
			} catch (Exception ex) {
			}
			smalllistconfig.setListconfig(listbeanemails);
			finalResult.add(smalllistconfig);
		}
		return finalResult;
	}

	@Override
	public ResponseEntity<?> fetchlistuseremailparam(final User user) {
		return ResponseEntity.status(HttpStatus.OK).body(this.fetchbyuser(user, null, true));
	}

	@Override
	public EmailConfigBean getConfigByUserId(final Integer userid) {
		return null;
	}

	@Override
	public List getUsers(final int clientid, final int affiliateid, final int transporterid) {
		return this.dao.getUsers(clientid, affiliateid, transporterid);
	}

	@Override
	public ResponseEntity<?> saveconfig(final User user, final EmailConfigBean emailConfigBean) {
		Integer userid = emailConfigBean.getUserid();
		String email = emailConfigBean.getEmailuser();
		if (Objects.isNull(userid) && Objects.isNull(email)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.UserIdMustNoNull, StaticValues.UserIdMustNoNull_Int));
		}
		User reeluser = null;
		final List<String> listemail = new ArrayList<>();
		String wherevalue = "";
		String emailist = "";
		final int z = 0;
		String[] listemails;
		boolean trouve = false;
		if (!Objects.isNull(email)) {
			listemails = email.split(",");
			if (Objects.isNull(listemails) || listemails.length < 1) {
				listemails = email.split(";");
			}
			for (final String string : listemails) {
				listemail.add(string);
				if (z == 0) {
					emailist = "'" + string + "'";
				} else {
					emailist = emailist + ",'" + string + "'";
				}
			}
		}
		if (!Objects.isNull(userid)) {
			reeluser = this.userR.findById(userid).orElse(null);
			if (Objects.isNull(reeluser)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.UsersId, StaticValues.UsersId_Int));
			}
			String emailuser = reeluser.getEmailid();
			if (Objects.isNull(emailuser)) {
				emailuser = this.userR.emailUser(userid);
			}
			if (!Objects.isNull(emailuser)) {
				try {
					listemail.add(emailuser);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (!Objects.isNull(email)) {
				if (!Objects.isNull(emailuser)) {
					wherevalue = " listotheremailreceiver in (" + emailist + ",'" + emailuser + "') ";
				} else {
					wherevalue = " listotheremailreceiver in (" + emailist + ") ";
				}
			} else if (!Objects.isNull(emailuser)) {
				wherevalue = " listotheremailreceiver in ('" + emailuser + "') ";
			}
			if (!wherevalue.isEmpty()) {
				wherevalue = wherevalue + " or userid = " + userid + " ";
			} else {
				wherevalue = " userid = " + userid + " ";
			}
		} else if (!Objects.isNull(email)) {
			// email=email+"'";
			if (email.contains("\'")) {
				wherevalue = " listotheremailreceiver in (" + email + ") ";
			} else {
				wherevalue = " listotheremailreceiver in ('" + email + "') ";
			}

		}
		final Integer typeElements = emailConfigBean.getTypelementsid();
		if (Objects.isNull(typeElements) || (typeElements < 0 && typeElements > 5)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.TypeElementsError, StaticValues.TypeElementsError_Int));
		}
		final List<Integer> listElements = emailConfigBean.getListelementsid();
		List<Levelbean> listalertype = emailConfigBean.getLevelbeam();
		if (Objects.isNull(listalertype)) {
			listalertype = new ArrayList<>();
		}
		final Date date = new Date();
		final Date datelog = new Date();
		final List<Long> result = new ArrayList<>();
		final Listconfigtypes paramconfigs = this.listconfig.findById(Utils.EmailTypeconfig()).orElse(null);
		final String query = "update emailconfig set recordstatus = false, alertstatus = false , alarmstatus = false where "
				+ wherevalue;
		final Integer nbr = this.jdbcTemplate.update(query);
		for (final Integer typelementsid : listElements) {
			Emailconfig configemail = new Emailconfig();
			if (typeElements == Utils.typeElementsClientsid) {
				final Customer customer = this.customR.findById(typelementsid).orElse(null);
				if (Objects.isNull(customer)) {
					continue;
				}
				for (final Levelbean levelbean : listalertype) {
					final Integer alertype = levelbean.getIdtypelement();
					if (!Objects.isNull(alertype)) {
						final Parametertype parametertype = this.paramtypeR.findById(alertype).orElse(null);
						if (Objects.isNull(parametertype)) {
							continue;
						}
						for (final String emaillist : listemail) {
							configemail = this.emailconfigRepo
									.findEmailconfigForCustomer(emaillist.trim().toLowerCase(), typelementsid, alertype)
									.orElse(null);
							Boolean createorupdate;
							if (Objects.isNull(configemail)) {
								createorupdate = true;
								Emailconfig.init(createorupdate, user);
								configemail = new Emailconfig();
								configemail.setClientid(customer, createorupdate);
								configemail.setParamtypeid(parametertype, createorupdate);
								if (!Objects.isNull(reeluser)) {
									configemail.setUserid(reeluser, createorupdate);
								}
								configemail.setCreatedby(user.getUserid());
								configemail.setCreatedon(date);
								configemail.setListotheremailreceiver(emaillist.trim().toLowerCase(), createorupdate);
							} else {
								createorupdate = false;
								Emailconfig.init(createorupdate, user);
							}
							configemail.setUpdatedby(user.getUserid());
							configemail.setUpdatedon(date);
							configemail.setMailflag(1);
							configemail.setAlarmstatus(levelbean.getAlarm(), createorupdate);
							configemail.setAlertstatus(levelbean.getAlert(), createorupdate);
							configemail.setRecordstatus(levelbean.getRecord(), createorupdate);
							if (Utils.countnumberligne(Emailconfig.log) > 1) {
								final Userlogsactivity loguser = new Userlogsactivity(paramconfigs);
								loguser.setDates(datelog);
								loguser.setUserid(user);
								loguser.setLogsinfos(Emailconfig.log);
								this.userlogRepo.saveAndFlush(loguser);
							}
							configemail = this.emailconfigRepo.saveAndFlush(configemail);
							result.add(configemail.getConfigid());
						}
					}
				}
			} else if (typeElements == Utils.typeElementsAffiliateid) {
				final Customeraffiliate affiliate = this.affR.findById(typelementsid).orElse(null);
				if (Objects.isNull(affiliate)) {
					continue;
				}
				for (final Levelbean levelbean : listalertype) {
					final Integer alertype = levelbean.getIdtypelement();
					if (!Objects.isNull(alertype)) {
						final Parametertype parametertype = this.paramtypeR.findById(alertype).orElse(null);
						if (Objects.isNull(parametertype)) {
							continue;
						}
						for (final String emaillist : listemail) {
							configemail = this.emailconfigRepo.findEmailconfigForAffiliate(
									emaillist.toLowerCase().trim(), typelementsid, alertype).orElse(null);
							Boolean createorupdate;
							if (Objects.isNull(configemail)) {
								createorupdate = true;
								Emailconfig.init(createorupdate, user);
								configemail = new Emailconfig();
								configemail.setAffiliateid(affiliate, createorupdate);
								configemail.setParamtypeid(parametertype, createorupdate);
								if (!Objects.isNull(reeluser)) {
									configemail.setUserid(reeluser, createorupdate);
								}
								configemail.setCreatedby(user.getUserid());
								configemail.setCreatedon(date);
								configemail.setListotheremailreceiver(emaillist.trim().toLowerCase(), createorupdate);
							} else {
								createorupdate = false;
								Emailconfig.init(createorupdate, user);
							}
							configemail.setUpdatedby(user.getUserid());
							configemail.setUpdatedon(date);
							configemail.setMailflag(1);
							if (!Objects.isNull(levelbean.getAlarm())) {
								configemail.setAlarmstatus(levelbean.getAlarm(), createorupdate);
							}
							if (!Objects.isNull(levelbean.getAlert())) {
								configemail.setAlertstatus(levelbean.getAlert(), createorupdate);
							}
							if (!Objects.isNull(levelbean.getRecord())) {
								configemail.setRecordstatus(levelbean.getRecord(), createorupdate);
							}
							configemail = this.emailconfigRepo.saveAndFlush(configemail);
							if (Utils.countnumberligne(Emailconfig.log) > 1) {
								final Userlogsactivity loguser = new Userlogsactivity(paramconfigs);
								loguser.setDates(datelog);
								loguser.setUserid(user);
								loguser.setLogsinfos(Emailconfig.log);
								this.userlogRepo.saveAndFlush(loguser);
							}
							result.add(configemail.getConfigid());
						}
					}
				}
			} else if (typeElements == Utils.typeElementsTransporterid) {
				final Transporter trans = this.transR.findById(typelementsid).orElse(null);
				if (Objects.isNull(trans)) {
					continue;
				}
				for (final Levelbean levelbean : listalertype) {
					final Integer alertype = levelbean.getIdtypelement();
					if (!Objects.isNull(alertype)) {
						final Parametertype parametertype = this.paramtypeR.findById(alertype).orElse(null);
						if (Objects.isNull(parametertype)) {
							continue;
						}
						for (final String emaillist : listemail) {
							configemail = this.emailconfigRepo.findEmailconfigForTransporter(
									emaillist.trim().toLowerCase(), typelementsid, alertype).orElse(null);
							Boolean createorupdate;
							if (Objects.isNull(configemail)) {
								createorupdate = true;
								Emailconfig.init(createorupdate, user);
								configemail = new Emailconfig();
								configemail.setTransporterid(trans, createorupdate);
								configemail.setParamtypeid(parametertype, createorupdate);
								if (!Objects.isNull(reeluser)) {
									configemail.setUserid(reeluser, createorupdate);
								}
								configemail.setCreatedby(user.getUserid());
								configemail.setCreatedon(date);
								configemail.setListotheremailreceiver(emaillist.trim().toLowerCase(), createorupdate);
							} else {
								createorupdate = false;
								Emailconfig.init(createorupdate, user);
							}
							configemail.setUpdatedby(user.getUserid());
							configemail.setUpdatedon(date);
							configemail.setMailflag(1);
							if (!Objects.isNull(levelbean.getAlarm())) {
								configemail.setAlarmstatus(levelbean.getAlarm(), createorupdate);
							}
							if (!Objects.isNull(levelbean.getAlert())) {
								configemail.setAlertstatus(levelbean.getAlert(), createorupdate);
							}
							if (!Objects.isNull(levelbean.getRecord())) {
								configemail.setRecordstatus(levelbean.getRecord(), createorupdate);
							}
							configemail = this.emailconfigRepo.saveAndFlush(configemail);
							if (Utils.countnumberligne(Emailconfig.log) > 1) {
								final Userlogsactivity loguser = new Userlogsactivity(paramconfigs);
								loguser.setDates(datelog);
								loguser.setUserid(user);
								loguser.setLogsinfos(Emailconfig.log);
								this.userlogRepo.saveAndFlush(loguser);
							}
							result.add(configemail.getConfigid());
						}
					}
				}
			} else {
				if (typeElements != Utils.typeElementsVehiclesid) {
					continue;
				}
				final Vehicle veh = this.vehR.findById(typelementsid).orElse(null);
				if (Objects.isNull(veh)) {
					continue;
				}
				for (final Levelbean levelbean : listalertype) {
					final Integer alertype = levelbean.getIdtypelement();
					if (!Objects.isNull(alertype)) {
						final Parametertype parametertype = this.paramtypeR.findById(alertype).orElse(null);
						if (Objects.isNull(parametertype)) {
							continue;
						}
						for (final String emaillist : listemail) {
							configemail = this.emailconfigRepo
									.findEmailconfigForVehicle(emaillist.trim().toLowerCase(), typelementsid, alertype)
									.orElse(null);
							Boolean createorupdate;
							if (Objects.isNull(configemail)) {
								createorupdate = true;
								Emailconfig.init(createorupdate, user);
								configemail = new Emailconfig();
								configemail.setVehicleid(veh, createorupdate);
								configemail.setParamtypeid(parametertype, createorupdate);
								if (!Objects.isNull(reeluser)) {
									configemail.setUserid(reeluser, createorupdate);
								}
								configemail.setCreatedby(user.getUserid());
								configemail.setCreatedon(date);
								configemail.setListotheremailreceiver(emaillist.trim().toLowerCase(), createorupdate);
							} else {
								createorupdate = false;
								Emailconfig.init(createorupdate, user);
							}
							configemail.setUpdatedby(user.getUserid());
							configemail.setUpdatedon(date);
							configemail.setMailflag(1);
							if (!Objects.isNull(levelbean.getAlarm())) {
								configemail.setAlarmstatus(levelbean.getAlarm(), createorupdate);
							}
							if (!Objects.isNull(levelbean.getAlert())) {
								configemail.setAlertstatus(levelbean.getAlert(), createorupdate);
							}
							if (!Objects.isNull(levelbean.getRecord())) {
								configemail.setRecordstatus(levelbean.getRecord(), createorupdate);
							}
							configemail = this.emailconfigRepo.saveAndFlush(configemail);
							if (Utils.countnumberligne(Emailconfig.log) > 1) {
								final Userlogsactivity loguser = new Userlogsactivity(paramconfigs);
								loguser.setDates(datelog);
								loguser.setUserid(user);
								loguser.setLogsinfos(Emailconfig.log);
								this.userlogRepo.saveAndFlush(loguser);
							}
							result.add(configemail.getConfigid());
						}
					}
				}
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(this.fetchbyuser(user, result, false));
	}

	@Override
	public int updateConfig(final EmailConfigBean emailConfigBean) {
		return this.dao.updateConfig(emailConfigBean);
	}

	@Override
	public ResponseEntity<?> updateemailparam(final User user, final UpdateEmailConfigBean updatemailparam) {
		final Integer typeElements = updatemailparam.getTypelementsid();
		if (Objects.isNull(typeElements) || (typeElements < 0 && typeElements > 5)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.TypeElementsError, StaticValues.TypeElementsError_Int));
		}
		final Integer typelementsid = updatemailparam.getElementsid();
		if (Objects.isNull(typelementsid)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.ElementIdNonNull, StaticValues.ElementIdNonNull_Int));
		}
		List<Levelbean> listalertype = updatemailparam.getLevelbeam();
		if (Objects.isNull(listalertype)) {
			listalertype = new ArrayList<>();
		}
		final Date date = new Date();
		final String email = updatemailparam.getEmail();
		if (Objects.isNull(email)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.Emailincorrect, StaticValues.Emailincorrect_Int));
		}
		final Date datelog = new Date();
		final boolean createorupdate = false;
		final Listconfigtypes paramconfigs = this.listconfig.findById(Utils.EmailTypeconfig()).orElse(null);
		if (typeElements == Utils.typeElementsClientsid) {
			final Customer customer = this.customR.findById(typelementsid).orElse(null);
			if (Objects.isNull(customer)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.CodeCustomerIncorrect, StaticValues.CodeCustomerIncorrect_Int));
			}
			for (final Levelbean levelbean : listalertype) {
				final Integer alertype = levelbean.getIdtypelement();
				if (!Objects.isNull(alertype)) {
					final Parametertype parametertype = this.paramtypeR.findById(alertype).orElse(null);
					if (Objects.isNull(parametertype)) {
						continue;
					}
					Emailconfig configemail = this.emailconfigRepo
							.findEmailconfigForCustomer(email, customer.getCustomerid(), alertype).orElse(null);
					Emailconfig.init(createorupdate, user);
					if (Objects.isNull(configemail)) {
						continue;
					}
					configemail.setUpdatedby(user.getUserid());
					configemail.setUpdatedon(date);
					configemail.setMailflag(1);
					if (!Objects.isNull(levelbean.getAlarm())) {
						configemail.setAlarmstatus(levelbean.getAlarm(), createorupdate);
					}
					if (!Objects.isNull(levelbean.getAlert())) {
						configemail.setAlertstatus(levelbean.getAlert(), createorupdate);
					}
					if (!Objects.isNull(levelbean.getRecord())) {
						configemail.setRecordstatus(levelbean.getRecord(), createorupdate);
					}
					configemail = this.emailconfigRepo.saveAndFlush(configemail);
					if (Utils.countnumberligne(Emailconfig.log) <= 1) {
						continue;
					}
					final Userlogsactivity loguser = new Userlogsactivity(paramconfigs);
					loguser.setDates(datelog);
					loguser.setUserid(user);
					loguser.setLogsinfos(Emailconfig.log);
					this.userlogRepo.saveAndFlush(loguser);
				}
			}
		} else if (typeElements == Utils.typeElementsAffiliateid) {
			final Customeraffiliate affiliate = this.affR.findById(typelementsid).orElse(null);
			if (Objects.isNull(affiliate)) {
				return ResponseEntity.status(HttpStatus.OK).body(
						new Success(StaticValues.codeaffiliateontexists, StaticValues.codeaffiliateontexists_Int));
			}
			for (final Levelbean levelbean : listalertype) {
				final Integer alertype = levelbean.getIdtypelement();
				if (!Objects.isNull(alertype)) {
					final Parametertype parametertype = this.paramtypeR.findById(alertype).orElse(null);
					if (Objects.isNull(parametertype)) {
						continue;
					}
					Emailconfig configemail = this.emailconfigRepo
							.findEmailconfigForAffiliate(email, affiliate.getAffiliateid(), alertype).orElse(null);
					Emailconfig.init(createorupdate, user);
					if (Objects.isNull(configemail)) {
						continue;
					}
					configemail.setUpdatedby(user.getUserid());
					configemail.setUpdatedon(date);
					configemail.setMailflag(1);
					if (!Objects.isNull(levelbean.getAlarm())) {
						configemail.setAlarmstatus(levelbean.getAlarm(), createorupdate);
					}
					if (!Objects.isNull(levelbean.getAlert())) {
						configemail.setAlertstatus(levelbean.getAlert(), createorupdate);
					}
					if (!Objects.isNull(levelbean.getRecord())) {
						configemail.setRecordstatus(levelbean.getRecord(), createorupdate);
					}
					configemail = this.emailconfigRepo.saveAndFlush(configemail);
					if (Utils.countnumberligne(Emailconfig.log) <= 1) {
						continue;
					}
					final Userlogsactivity loguser = new Userlogsactivity(paramconfigs);
					loguser.setDates(datelog);
					loguser.setUserid(user);
					loguser.setLogsinfos(Emailconfig.log);
					this.userlogRepo.saveAndFlush(loguser);
				}
			}
		} else if (typeElements == Utils.typeElementsTransporterid) {
			final Transporter trans = this.transR.findById(typelementsid).orElse(null);
			if (Objects.isNull(trans)) {
				return ResponseEntity.status(HttpStatus.OK).body(
						new Success(StaticValues.codetransporterontexists, StaticValues.codetransporterontexists_Int));
			}
			for (final Levelbean levelbean : listalertype) {
				final Integer alertype = levelbean.getIdtypelement();
				if (!Objects.isNull(alertype)) {
					final Parametertype parametertype = this.paramtypeR.findById(alertype).orElse(null);
					if (Objects.isNull(parametertype)) {
						continue;
					}
					Emailconfig configemail = this.emailconfigRepo
							.findEmailconfigForTransporter(email, trans.getTransporterid(), alertype).orElse(null);
					Emailconfig.init(createorupdate, user);
					if (Objects.isNull(configemail)) {
						continue;
					}
					configemail.setUpdatedby(user.getUserid());
					configemail.setUpdatedon(date);
					configemail.setMailflag(1);
					if (!Objects.isNull(levelbean.getAlarm())) {
						configemail.setAlarmstatus(levelbean.getAlarm(), createorupdate);
					}
					if (!Objects.isNull(levelbean.getAlert())) {
						configemail.setAlertstatus(levelbean.getAlert(), createorupdate);
					}
					if (!Objects.isNull(levelbean.getRecord())) {
						configemail.setRecordstatus(levelbean.getRecord(), createorupdate);
					}
					configemail = this.emailconfigRepo.saveAndFlush(configemail);
					if (Utils.countnumberligne(Emailconfig.log) <= 1) {
						continue;
					}
					final Userlogsactivity loguser = new Userlogsactivity(paramconfigs);
					loguser.setDates(datelog);
					loguser.setUserid(user);
					loguser.setLogsinfos(Emailconfig.log);
					this.userlogRepo.saveAndFlush(loguser);
				}
			}
		} else if (typeElements == Utils.typeElementsVehiclesid) {
			final Vehicle veh = this.vehR.findById(typelementsid).orElse(null);
			if (Objects.isNull(veh)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.codevehicleontexists, StaticValues.codevehicleontexists_Int));
			}
			for (final Levelbean levelbean : listalertype) {
				final Integer alertype = levelbean.getIdtypelement();
				if (!Objects.isNull(alertype)) {
					final Parametertype parametertype = this.paramtypeR.findById(alertype).orElse(null);
					if (Objects.isNull(parametertype)) {
						continue;
					}
					Emailconfig configemail = this.emailconfigRepo
							.findEmailconfigForVehicle(email, veh.getVehicleid(), alertype).orElse(null);
					Emailconfig.init(createorupdate, user);
					if (Objects.isNull(configemail)) {
						continue;
					}
					configemail.setUpdatedby(user.getUserid());
					configemail.setUpdatedon(date);
					configemail.setMailflag(1);
					if (!Objects.isNull(levelbean.getAlarm())) {
						configemail.setAlarmstatus(levelbean.getAlarm(), createorupdate);
					}
					if (!Objects.isNull(levelbean.getAlert())) {
						configemail.setAlertstatus(levelbean.getAlert(), createorupdate);
					}
					if (!Objects.isNull(levelbean.getRecord())) {
						configemail.setRecordstatus(levelbean.getRecord(), createorupdate);
					}
					configemail = this.emailconfigRepo.saveAndFlush(configemail);
					if (Utils.countnumberligne(Emailconfig.log) <= 1) {
						continue;
					}
					final Userlogsactivity loguser = new Userlogsactivity(paramconfigs);
					loguser.setDates(datelog);
					loguser.setUserid(user);
					loguser.setLogsinfos(Emailconfig.log);
					this.userlogRepo.saveAndFlush(loguser);
				}
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(new Success(StaticValues.success, StaticValues.success_Int));
	}
}
