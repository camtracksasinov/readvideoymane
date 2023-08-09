//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.affiliate.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.camtrack.affiliate.bean.AffiliateBean;
import com.camtrack.affiliate.bean.AffiliateInfos;
import com.camtrack.affiliate.dao.AffiliateDaoInterface;
import com.camtrack.affiliate.repository.CustomeraffiliateRepository;
import com.camtrack.config.Mailer;
import com.camtrack.config.StaticValues;
import com.camtrack.config.Utils;
import com.camtrack.entities.Allalertlevel;
import com.camtrack.entities.Annexeaffiliateid;
import com.camtrack.entities.Annexeinfos;
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
import com.camtrack.user.repository.ParameterconfigRepository;
import com.camtrack.user.repository.ParametertypeRepository;
import com.camtrack.user.repository.StatusRepository;
import com.camtrack.user.repository.UserightsRepository;
import com.camtrack.user.repository.UserlogsactivityRepository;
import com.camtrack.user.service.FileService;

@Service("affiliateService")
public class AffiliateServiceImpl implements AffiliateServiceInterface {
	@Autowired
	AffiliateDaoInterface affiliateDao;
	@Value("${userrrole.affiliateid}")
	private Integer affiliateid;
	@Autowired
	AllalertlevelRepository alllevelRepo;
	@Value("${userrrole.clientid}")
	private Integer clientid;
	@Autowired
	CustomeraffiliateRepository customeraffR;
	@Autowired
	CustomerRepository customR;
	@Autowired
	private Environment environment;
	@Autowired
	ExceptionlevelRepository excepLR;
	@Autowired
	FileService fileservices;
	@Autowired
	JdbcTemplate jdbcTemplate;
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

	@Override
	public Integer deactivateAffiliate(final Integer affiliateid, final Integer userid) {
		return this.customeraffR.updatestatusaffiliate(affiliateid);
	}

	@Override
	public List fetchAffiliatesbycostomer(final int customerid) {
		return this.affiliateDao.fetchAffiliatesbycostomer(customerid);
	}

	@Override
	public List fetchAffiliatesbyListcostomer(final String listcustomerid) {
		return this.affiliateDao.fetchAffiliatesbyListcostomer(listcustomerid);
	}

	@Override
	public Map getAffiliateById(final int affiliateid) {
		return this.affiliateDao.getAffiliateById(affiliateid);
	}

	@Override
	public List getAffiliateByListId(final String listaffiliateid) {
		return this.affiliateDao.getAffiliateByListId(listaffiliateid);
	}

	@Override
	public ResponseEntity<?> hierachicallistaffiliateuser(final User user) {
		List<Customeraffiliate> listclient = new ArrayList<>();
		final String sqlin = "";
		try {
			listclient = this.customeraffR.findAllActiveCustomerAffiliate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		final JSONObject object = new JSONObject();
		final ObjectMapper obmaper = new ObjectMapper();
		for (final Customeraffiliate affiliate : listclient) {
			final Annexeaffiliateid anex = affiliate.getAnexeinfos();
			Annexeinfos infos;
			if (Objects.isNull(anex)) {
				infos = new Annexeinfos(affiliate.getName(), "", "", "", "", "", "", "");
			} else {
				infos = new Annexeinfos(affiliate.getName(), anex.getDirection(), anex.getZone(), anex.getCodepays(),
						anex.getPays(), anex.getCodefilialle(), anex.getFilliale(), anex.getBranch());
			}
			try {
				object.put(Utils.castIntegerObject(affiliate.getAffiliateid()) + "",
						new JSONObject(obmaper.writeValueAsString(infos)));
			} catch (JSONException | IOException ex3) {
				ex3.printStackTrace();
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(object.toString());
	}

	@Override
	public ResponseEntity<?> saveAffiliate(final AffiliateInfos referral, final User user) {
		Customer custome = null;
		Boolean createornot = true;
		Customeraffiliate customer = new Customeraffiliate();
		Integer affiliateId = referral.getAffiliateId();
		if (!Objects.isNull(affiliateId)) {
			customer = this.customeraffR.findById(affiliateId).orElse(null);
			createornot = false;
		} else {
			affiliateId = referral.getAffiliateid();
			if (!Objects.isNull(affiliateId)) {
				customer = this.customeraffR.findById(affiliateId).orElse(null);
				createornot = false;
			}
		}
		Customeraffiliate.init(createornot, user);
		try {
			custome = this.customR.findById(referral.getCustomerid()).orElse(null);
			if (Objects.isNull(custome)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.CodeCustomerIncorrect, StaticValues.CodeCustomerIncorrect_Int));
			}
			customer.setCustomerid(custome, createornot);
		} catch (Exception ex2) {
		}
		final Date date = new Date();
		customer.setCreatedby(user.getUserid());
		customer.setCreatedon(date);
		if (!Objects.isNull(referral.getAdresse1())) {
			customer.setAddressline1(referral.getAdresse1(), createornot);
		}
		if (!Objects.isNull(referral.getAdresse2())) {
			customer.setAddressline2(referral.getAdresse2(), createornot);
		}
		if (!Objects.isNull(referral.getCityid())) {
			customer.setCityid(referral.getCityid());
		}
		if (!Objects.isNull(referral.getStateid())) {
			customer.setStateid(referral.getStateid());
		}
		if (!Objects.isNull(referral.getTelephone())) {
			customer.setTelephone(referral.getTelephone(), createornot);
		}
		if (!Objects.isNull(referral.getCountryid())) {
			customer.setCountryid(referral.getCountryid());
		}
		if (!Objects.isNull(referral.getNameaffiliate())) {
			customer.setName(referral.getNameaffiliate(), createornot);
		}
		if (!Objects.isNull(referral.getEmail())) {
			customer.setEmail(referral.getEmail(), createornot);
		}
		if (!Objects.isNull(referral.getTimezone())) {
			customer.setAfftimezone(referral.getTimezone(), createornot);
		}
		customer.setStatus(1, createornot);
		customer.setUpdatedby(user.getUserid());
		customer.setUpdatedon(date);
		customer = this.customeraffR.saveAndFlush(customer);
		final Date datelog = new Date();
		Listconfigtypes paramconfigs = this.listconfig.findById(Utils.ClientTypeconfig()).orElse(null);
		if (Utils.countnumberligne(Customeraffiliate.log) > 1) {
			final Userlogsactivity loguser = new Userlogsactivity(paramconfigs);
			loguser.setDates(datelog);
			loguser.setUserid(user);
			loguser.setLogsinfos(Customeraffiliate.log);
			this.userlogRepo.saveAndFlush(loguser);
		}
		final List<Integer> affiliateidlist = new ArrayList<>();
		final List<Integer> typeintegeridforcustomer = this.paramconfigR
				.findAllParameteridForcustomer(custome.getCustomerid());
		final List<Integer> typeintegerid = referral.getExceptiontypeidfromclients();
		if (!Objects.isNull(typeintegerid) && !typeintegerid.isEmpty()) {
			final List<Integer> intersect = typeintegeridforcustomer.stream().filter(typeintegerid::contains)
					.collect(Collectors.toList());
			final List<Parametertype> listype = this.paramtypeR.findAllById(intersect);
			paramconfigs = this.listconfig.findById(Utils.ParamTypeconfig()).orElse(null);
			final Status status = this.statusR.findById(1).orElse(null);
			final Exceptionlevel level = this.excepLR.findById(2).orElse(null);
			for (final Parametertype parametertype : listype) {
				try {
					if (parametertype.getParametertypeid() == 35) {
					}
					Parameterconfig paramconfig = Utils.parameterconfigfromparametertypedefaults(parametertype,
							(Customer) null, customer, status, createornot, user, level, this.paramconfigR);
					if (!Objects.isNull(customer)) {
						paramconfig.setClientaffiliateid(customer);
					}
					paramconfig = this.paramconfigR.saveAndFlush(paramconfig);
					if (Utils.countnumberligne(Parameterconfig.log) <= 1) {
						continue;
					}
					final Userlogsactivity loguser = new Userlogsactivity(paramconfigs);
					loguser.setDates(datelog);
					loguser.setUserid(user);
					loguser.setLogsinfos(Parameterconfig.log);
					this.userlogRepo.saveAndFlush(loguser);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		Allalertlevel newlevel = this.alllevelRepo.findAffiliateAlert(customer.getAffiliateid()).orElse(null);
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
		newlevel.setAffiliateid(customer, createornot);
		affiliateidlist.add(customer.getAffiliateid());
		newlevel.setCreatedate(new Date());
		newlevel.setStatus(1);
		try {
			this.alllevelRepo.saveAndFlush(newlevel);
		} catch (Exception ex3) {
		}
		paramconfigs = this.listconfig.findById(Utils.UserAccessTypeconfig()).orElse(null);
		if (Utils.countnumberligne(Allalertlevel.log) > 1) {
			final Userlogsactivity loguser = new Userlogsactivity(paramconfigs);
			loguser.setDates(datelog);
			loguser.setUserid(user);
			loguser.setLogsinfos(Allalertlevel.log);
			this.userlogRepo.saveAndFlush(loguser);
		}
		return ResponseEntity.status(HttpStatus.OK).body(affiliateidlist);
	}

	@Override
	public int updateAffiliate(final AffiliateBean referral) {
		return this.affiliateDao.updateAffiliate(referral);
	}
}
