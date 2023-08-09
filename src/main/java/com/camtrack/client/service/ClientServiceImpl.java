//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.client.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.camtrack.client.bean.ClientBean;
import com.camtrack.client.dao.ClientDaoInterface;
import com.camtrack.config.Mailer;
import com.camtrack.config.StaticValues;
import com.camtrack.config.Utils;
import com.camtrack.entities.Allalertlevel;
import com.camtrack.entities.Customer;
import com.camtrack.entities.Customeraffiliate;
import com.camtrack.entities.Exceptionlevel;
import com.camtrack.entities.Listconfigtypes;
import com.camtrack.entities.Parameterconfig;
import com.camtrack.entities.Parametertype;
import com.camtrack.entities.Status;
import com.camtrack.entities.Success;
import com.camtrack.entities.User;
import com.camtrack.entities.Userlogsactivity;
import com.camtrack.user.repository.AllalertlevelRepository;
import com.camtrack.user.repository.CustomerRepository;
import com.camtrack.user.repository.ExceptionlevelRepository;
import com.camtrack.user.repository.ListconfigtypesRepository;
import com.camtrack.user.repository.OauthAccessTokenRepository;
import com.camtrack.user.repository.ParameterconfigRepository;
import com.camtrack.user.repository.ParametertypeRepository;
import com.camtrack.user.repository.StatusRepository;
import com.camtrack.user.repository.UserightsRepository;
import com.camtrack.user.repository.UserlogsactivityRepository;
import com.camtrack.user.repository.UsersRepository;
import com.camtrack.user.service.FileService;

@Service("clientService")
@CacheConfig(cacheNames = "clientcache")
public class ClientServiceImpl implements ClientServiceInterface {
	@Value("${userrrole.affiliateid}")
	private Integer affiliateid;
	@Autowired
	AllalertlevelRepository allalertLevel;
	@Autowired
	ClientDaoInterface clientDao;
	@Value("${userrrole.clientid}")
	private Integer clientid;
	@Autowired
	CustomerRepository custom;
	@Autowired
	CustomerRepository customR;
	@Autowired
	private Environment environment;
	@Autowired
	ExceptionlevelRepository excepLR;
	@Autowired
	FileService fileservices;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	ListconfigtypesRepository listconfig;
	@Autowired
	Mailer mailTemplate;
	@Autowired
	ParameterconfigRepository paramconfigR;
	@Autowired
	ParametertypeRepository paramtypeR;
	@Autowired
	StatusRepository statusR;
	@Value("${userrrole.superadminid}")
	private Integer superadminid;
	@Value("${userrrole.transporterid}")
	private Integer transporterid;
	@Autowired
	UserlogsactivityRepository userlogRepo;
	@Autowired
	UserightsRepository userRR;
	@Autowired
	UsersRepository usersR;
	@Autowired
	OauthAccessTokenRepository accesstokenR;

	@Override
	public Integer deactivateClient(final Integer userid) {
		return this.custom.updatestatuscustomer(userid);
	}

	@Override
	@Cacheable("fechclients")
	public List fetchClients() {
		final String query = this.environment.getRequiredProperty("client.fetchallreducecustomernames");
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public List getAllCitiesOfState(final int stateid) {
		return this.clientDao.getAllCitiesOfState(stateid);
	}

	@Override
	@Cacheable("fetchallcountries")
	public List getAllCounties() {
		return this.clientDao.getAllCounties();
	}

	@Override
	@Cacheable("fetchalllangues")
	public List getAllLanguages() {
		return this.clientDao.getAllLanguages();
	}

	@Override
	public List getAllStatesOfCountry(final int countryid) {
		return this.clientDao.getAllStatesOfCountry(countryid);
	}

	@Override
	public Map getClientById(final int clientid) {
		return this.clientDao.getClientById(clientid);
	}

	@Override
	public List getClientByListId(final String clientid) {
		return this.clientDao.getClientByListId(clientid);
	}

	@Override
	public ResponseEntity<?> hierachicallistclientuser(final User user) {
		List<Object[]> listclient = new ArrayList<>();
		if (user.getReelrole().getIdtyperole().getUserroleid() == this.superadminid) {
			listclient = this.customR.findNativelyAllActiveCustomerId();
		} else if (user.getReelrole().getIdtyperole().getUserroleid() == this.clientid) {
			listclient = this.userRR.findNAtiveAllCustomerHierarchieOfUserId(user.getUserid());
		} else if (user.getReelrole().getIdtyperole().getUserroleid() == this.affiliateid) {
			listclient = this.userRR.findNativeAllAffiliateUser(user.getUserid());
		} else if (user.getReelrole().getIdtyperole().getUserroleid() == this.transporterid) {
			listclient = this.userRR.findNativeAllTransporterUser(user.getUserid());
		}
		final JSONObject object = new JSONObject();
		for (final Object[] objects : listclient) {
			object.put(Utils.castIntegerObject(objects[0]) + "", Utils.ObjectToString(objects[1]));
		}
		return ResponseEntity.status(HttpStatus.OK).body(object.toString());
	}

	@Override
	public ResponseEntity<?> saveClient(final ClientBean referral, final User user) {
		Customer customer = new Customer();
		final Date date = new Date();
		final Integer clientId = referral.getClientId();
		Boolean createornot;
		if (!Objects.isNull(clientId)) {
			customer = this.custom.findById(clientId).orElse(null);
			createornot = false;
		} else {
			createornot = true;
		}
		if (!Objects.isNull(referral.getAddressline1())) {
			customer.setAddressline1(referral.getAddressline1(), createornot);
		}
		if (!Objects.isNull(referral.getAddressline2())) {
			customer.setAddressline2(referral.getAddressline2(), createornot);
		}
		if (!Objects.isNull(referral.getLanguageid())) {
			customer.setLanguageid(referral.getLanguageid());
		}
		if (!Objects.isNull(referral.getCityid())) {
			customer.setCityid(referral.getCityid());
		}
		if (!Objects.isNull(referral.getCountryid())) {
			customer.setCountryid(referral.getCountryid());
		}
		if (!Objects.isNull(referral.getLanguageid())) {
			customer.setCreatedby(user.getUserid());
		}
		if (!Objects.isNull(referral.getEmail())) {
			customer.setEmail(referral.getEmail(), createornot);
		}
		if (!Objects.isNull(referral.getNameclient())) {
			customer.setName(referral.getNameclient(), createornot);
		}
		if (!Objects.isNull(referral.getStateid())) {
			customer.setStateid(referral.getStateid());
		}
		if (!Objects.isNull(referral.getTelephone())) {
			customer.setTelephone(referral.getTelephone(), createornot);
		}
		customer.setCreatedon(date);
		customer.setStatus(1, createornot);
		customer.setUpdatedby(user.getUserid());
		customer.setUpdatedon(date);
		customer = this.custom.saveAndFlush(customer);
		final Date datelog = new Date();
		if (Utils.countnumberligne(Customer.log) > 1) {
			final Listconfigtypes paramconfig = this.listconfig.findById(Utils.ClientTypeconfig()).orElse(null);
			final Userlogsactivity loguser = new Userlogsactivity(paramconfig);
			loguser.setDates(datelog);
			loguser.setUserid(user);
			loguser.setLogsinfos(Customer.log);
			this.userlogRepo.saveAndFlush(loguser);
		}
		final List<Integer> typeintegerid = referral.getExceptiontypeid();
		final List<Long> paramconfigid = new ArrayList<>();
		if (!Objects.isNull(typeintegerid) && !typeintegerid.isEmpty()) {
			final List<Parametertype> listype = this.paramtypeR.findAllById(typeintegerid);
			final Status status = this.statusR.findById(1).orElse(null);
			final Listconfigtypes paramconfigs = this.listconfig.findById(Utils.ParamTypeconfig()).orElse(null);
			final Exceptionlevel level = this.excepLR.findById(2).orElse(null);
			for (final Parametertype parametertype : listype) {
				try {
					if (parametertype.getParametertypeid() == 33) {
					}
					Parameterconfig paramconfig2 = Utils.parameterconfigfromparametertypedefaults(parametertype,
							customer, (Customeraffiliate) null, status, createornot, user, level, this.paramconfigR);
					if (Objects.isNull(paramconfig2)) {
						continue;
					}
					if (Objects.isNull(paramconfig2.getFrommonth())) {
						paramconfig2.setFrommonth(0);
					}
					if (Objects.isNull(paramconfig2.getTomonth())) {
						paramconfig2.setTomonth(12);
					}
					if (!Objects.isNull(customer)) {
						paramconfig2.setCustomerid(customer);
					}
					try {
						paramconfig2 = this.paramconfigR.saveAndFlush(paramconfig2);
					} catch (Exception ex2) {
					}
					if (Utils.countnumberligne(Parameterconfig.log) > 1) {
						final Userlogsactivity loguser = new Userlogsactivity(paramconfigs);
						loguser.setDates(datelog);
						loguser.setUserid(user);
						loguser.setLogsinfos(Parameterconfig.log);
						this.userlogRepo.saveAndFlush(loguser);
					}
					paramconfigid.add(paramconfig2.getId());
				} catch (Exception ex) {
					Parameterconfig paramconfig2 = Utils.parameterconfigfromparametertypedefaults(parametertype,
							customer, (Customeraffiliate) null, status, createornot, user, level, this.paramconfigR);
					if (!Objects.isNull(paramconfig2)) {
						if (Objects.isNull(paramconfig2.getFrommonth())) {
							paramconfig2.setFrommonth(0);
						}
						if (Objects.isNull(paramconfig2.getTomonth())) {
							paramconfig2.setTomonth(12);
						}
						if (!Objects.isNull(customer)) {
							paramconfig2.setCustomerid(customer);
						}
						try {
							paramconfig2 = this.paramconfigR.saveAndFlush(paramconfig2);
						} catch (Exception ex3) {
						}
						if (Utils.countnumberligne(Parameterconfig.log) > 1) {
							final Userlogsactivity loguser = new Userlogsactivity(paramconfigs);
							loguser.setDates(datelog);
							loguser.setUserid(user);
							loguser.setLogsinfos(Parameterconfig.log);
							this.userlogRepo.saveAndFlush(loguser);
						}
						paramconfigid.add(paramconfig2.getId());
					}
					ex.printStackTrace();
				}
			}
		}
		Allalertlevel newlevel = this.allalertLevel.findCustomerLevelAlert(customer.getCustomerid()).orElse(null);
		if (Objects.isNull(newlevel)) {
			newlevel = new Allalertlevel();
			createornot = true;
		} else {
			createornot = false;
		}
		Allalertlevel.init(createornot, user);
		if (!Objects.isNull(referral.getAlarmlevel())) {
			newlevel.setAlarmlevel(referral.getAlarmlevel(), createornot);
		}
		if (!Objects.isNull(referral.getAlertlevel())) {
			newlevel.setAlertlevel(referral.getAlertlevel(), createornot);
		}
		if (!Objects.isNull(referral.getRecordlevel())) {
			newlevel.setRecordlevel(referral.getRecordlevel(), createornot);
		}
		newlevel.setClientid(customer, createornot);
		newlevel.setCreatedate(new Date());
		newlevel.setStatus(1);
		this.allalertLevel.saveAndFlush(newlevel);
		final Listconfigtypes paramconfigs2 = this.listconfig.findById(Utils.ParamTypeconfig()).orElse(null);
		if (Utils.countnumberligne(Allalertlevel.log) > 1) {
			final Userlogsactivity loguser = new Userlogsactivity(paramconfigs2);
			loguser.setDates(datelog);
			loguser.setUserid(user);
			loguser.setLogsinfos(Allalertlevel.log);
			this.userlogRepo.saveAndFlush(loguser);
		}
		final List<Integer> resultat = new ArrayList<>();
		resultat.add(customer.getCustomerid());
		return ResponseEntity.status(HttpStatus.OK).body(resultat);
	}

	@Override
	public int updateClient(final ClientBean referral) throws Exception {
		return this.clientDao.updateClient(referral);
	}

	@Override
	public ResponseEntity<?> updateLogoClient(final Integer clientId, final MultipartFile file, final User user) {
		final Customer cus = this.custom.findById(clientId).orElse(null);
		final boolean createornot = false;
		if (Objects.isNull(cus)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.codecustomerontexists, StaticValues.codecustomerontexists_Int));
		}
		if (!Objects.isNull(file)) {
			final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			final String filename = "client_" + cus.getCustomerid() + "." + extension;
			this.fileservices.uploadFile(file, filename);
			Customer.init(createornot, user);
			cus.setLogourl(filename, createornot);
			this.custom.saveAndFlush(cus);
			if (Utils.countnumberligne(Customer.log) > 1) {
				final Date datelog = new Date();
				final Listconfigtypes paramconfigs = this.listconfig.findById(Utils.UserTypeconfig()).orElse(null);
				final Userlogsactivity loguser = new Userlogsactivity(paramconfigs);
				loguser.setDates(datelog);
				loguser.setUserid(user);
				loguser.setLogsinfos(Customer.log);
				this.userlogRepo.saveAndFlush(loguser);
			}
			this.accesstokenR.deleteOauthAccessToken(user.getUsername());
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.success, StaticValues.success_Int));
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Success(StaticValues.Filedonestvalide, StaticValues.Filedonestvalide_Int));
	}

	@Override
	public int updateLogoUrl(final int customerid, final String logourl) {
		return this.clientDao.updateLogoUrl(customerid, logourl);
	}
}
