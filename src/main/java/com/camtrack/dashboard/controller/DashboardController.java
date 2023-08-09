//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.dashboard.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.camtrack.bean.ListAllIdBean;
import com.camtrack.bean.ListAllIdWithtypeexceptionBean;
import com.camtrack.bean.ListIdBean;
import com.camtrack.bean.ResumeResult;
import com.camtrack.bean.ResumeResults;
import com.camtrack.bean.Worktime;
import com.camtrack.config.AppConstants;
import com.camtrack.config.PropertyLoader;
import com.camtrack.config.Utils;
import com.camtrack.dashboard.service.DashboardServiceInterface;
import com.camtrack.entities.User;
import com.camtrack.entities.UserAccountStatus;
import com.camtrack.reports.bean.TotalDistances;
import com.camtrack.reports.bean.TrendBean;
import com.camtrack.user.repository.UsersRepository;
import com.camtrack.user.service.UserServiceInterface;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import springfox.documentation.annotations.ApiIgnore;

@PropertySource({ "classpath:message.properties" })
@RestController
@RefreshScope
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {
	@Autowired
	DashboardServiceInterface dashboardServiceInterface;
	@Autowired
	UserServiceInterface userServiceInterface;
	@Autowired
	private Environment environment;
	@Value("${roleprivacyid}")
	private Integer roleprivacyid;
	@Autowired
	UsersRepository usersR;

	@RequestMapping(value = { "/accessright" }, method = { RequestMethod.POST })
	public Map accessright(@RequestBody final String menuURL, final String language,
			@Parameter(hidden = true) Principal userP) throws Exception {
		try {
			final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
			final Map permissions = this.dashboardServiceInterface
					.checkPermissions(String.valueOf(user.getReelrole().getIdtyperole()), menuURL);
			return permissions;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping({ "/activedriverandvehicle" })
	public ResponseEntity<?> activedriverandvehicle(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListAllIdBean allcompletesids,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		if (Objects.isNull(user) || !user.getEnabled()) {
			return ResponseEntity.status(HttpStatus.OK).body((Object) new UserAccountStatus(true));
		}
		return this.dashboardServiceInterface.activedriverandvehicle(user,
				ListAllIdBean.listToString(allcompletesids.getListclientids()),
				ListAllIdBean.listToString(allcompletesids.getListaffiliateids()),
				ListAllIdBean.listToString(allcompletesids.getListtransporterids()),
				ListAllIdBean.listToString(allcompletesids.getListvehicleids()),
				ListAllIdBean.listToString(allcompletesids.getListdriverids()), Utils.StringEscape(datedebut),
				Utils.StringEscape(datefin));
	}

	@PostMapping({ "/actvehordrivpersearch" })
	public ResumeResult actvehordrivpersearch(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListAllIdBean allcompletesids,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.dashboardServiceInterface.actvehordrivpersearch(user,
				ListAllIdBean.listToString(allcompletesids.getListclientids()),
				ListAllIdBean.listToString(allcompletesids.getListaffiliateids()),
				ListAllIdBean.listToString(allcompletesids.getListtransporterids()),
				ListAllIdBean.listToString(allcompletesids.getListvehicleids()),
				ListAllIdBean.listToString(allcompletesids.getListdriverids()), Utils.StringEscape(datedebut),
				Utils.StringEscape(datefin));
	}

	@RequestMapping(value = { "/dashboard" }, method = { RequestMethod.GET })
	public ResponseEntity<?> fetchAllMenus(@Parameter(hidden = true) Principal userP) throws Exception {
		try {
			final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
			final List parentMenus = this.dashboardServiceInterface
					.fetchAllParentMenus(String.valueOf(user.getReelrole().getIdtyperole().getUserroleid()));
			final List details = new ArrayList();
			details.add(parentMenus);
			final List userlist = new ArrayList();
			userlist.add(user);
			details.add(userlist);
			return new ResponseEntity(details, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * @RequestMapping(value = { "/fetchvehiclebytransporter" }, method = {
	 *                       RequestMethod.POST }) public ResponseEntity<?>
	 *                       fetchvehtranslist(@Parameter(hidden = true) Principal
	 *                       userP,
	 * @RequestBody final ListIdBean transporterid, final String language) throws
	 *              Exception { final User user =
	 *              this.usersR.findByUserName(userP.getName()).orElse(null); try {
	 *              if (user.getReelrole().getIds() == roleprivacyid) { return
	 *              ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>()); }
	 *              } catch (Exception ex) { return
	 *              ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>()); }
	 *              if (Objects.isNull(user) || !user.getEnabled()) { return
	 *              ResponseEntity.status(HttpStatus.OK).body((Object) new
	 *              UserAccountStatus(true)); } return
	 *              this.userServiceInterface.FetchVehicleList(user,
	 *              Arrays.stream(transporterid.getListids()).collect(Collectors.toList()));
	 *              }
	 */

	@RequestMapping(value = { "/fetchvehiclebytransporter" }, method = { RequestMethod.POST })
	public ResponseEntity<?> fetchvehicle(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListIdBean transporterid, final String language) throws Exception {
		try {
			final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
			try {
				if (user.getReelrole().getIds() == roleprivacyid) {
					return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>());
				}
			} catch (Exception ex) {
				return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>());
			}
			return new ResponseEntity(this.dashboardServiceInterface
					.fetchVehicleByTransporter(ListIdBean.listToString(transporterid.getListids())), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = { "/getcurrentlanguagefordashboard" }, method = { RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> getCurrentLanguage(String language, @Parameter(hidden = true) Principal userP)
			throws Exception {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		final String language2 = AppConstants.DEFAULT_LANGUAGE;
		if (Objects.isNull(language)) {
			language = language2;
		}
		final Map<String, Object> curLangDetails = new HashMap<>();
		curLangDetails.put("currentLanguage", language);
		curLangDetails.put("applabels", PropertyLoader.loadLabelsList(language));
		return curLangDetails;
	}

	@PostMapping({ "/resumeactivedrive" })
	public ResumeResult resumeactivedriveandvehs(@Parameter(hidden = true) Principal userP,
			@RequestBody final Worktime allcompletesids,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.dashboardServiceInterface.resumeactivedrive(user,
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListclientids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListaffiliateids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListtransporterids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListvehicleids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListdriverids()),
				Utils.StringEscape(datedebut), Utils.StringEscape(datefin));
	}

	@PostMapping({ "/resumeactiveveh" })
	public ResumeResult resumectiveveh(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListAllIdBean allcompletesids,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.dashboardServiceInterface.actvehordrivpersearch(user,
				ListAllIdBean.listToString(allcompletesids.getListclientids()),
				ListAllIdBean.listToString(allcompletesids.getListaffiliateids()),
				ListAllIdBean.listToString(allcompletesids.getListtransporterids()),
				ListAllIdBean.listToString(allcompletesids.getListvehicleids()),
				ListAllIdBean.listToString(allcompletesids.getListdriverids()), Utils.StringEscape(datedebut),
				Utils.StringEscape(datefin));
	}

	@PostMapping({ "/resumelistdriveandvehicle" })
	public ResumeResults resumelistdriveandvehicle(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListAllIdBean allcompletesids) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.dashboardServiceInterface.resumelistdriveandvehicle(user,
				ListAllIdBean.listToString(allcompletesids.getListclientids()),
				ListAllIdBean.listToString(allcompletesids.getListaffiliateids()),
				ListAllIdBean.listToString(allcompletesids.getListtransporterids()),
				ListAllIdBean.listToString(allcompletesids.getListvehicleids()),
				ListAllIdBean.listToString(allcompletesids.getListdriverids()));
	}

	@PostMapping({ "/resumeworktime" })
	public ResumeResults resumeworktime(@Parameter(hidden = true) Principal userP,
			@RequestBody final Worktime allcompletesids,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.dashboardServiceInterface.resumeworktime(user,
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListclientids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListaffiliateids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListtransporterids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListvehicleids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListdriverids()),
				Utils.StringEscape(datedebut), Utils.StringEscape(datefin));
	}

	@PostMapping({ "/resumexception" })
	public ResumeResult resumexception(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListAllIdWithtypeexceptionBean allcompletesids,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.dashboardServiceInterface.resumexception(user,
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListclientids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListaffiliateids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListtransporterids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListvehicleids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListdriverids()),
				Utils.StringEscape(datedebut), Utils.StringEscape(datefin),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListidtypeexception()),
				allcompletesids.getRecord(), allcompletesids.getAlert(), allcompletesids.getAlarm());
	}

	@PostMapping({ "/resumexception100km" })
	public ResumeResult resumexception100km(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListAllIdWithtypeexceptionBean allcompletesids,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.dashboardServiceInterface.resumexception100km(user,
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListclientids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListaffiliateids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListtransporterids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListvehicleids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListdriverids()),
				Utils.StringEscape(datedebut), Utils.StringEscape(datefin),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListidtypeexception()),
				allcompletesids.getRecord(), allcompletesids.getAlert(), allcompletesids.getAlarm());
	}

	@PostMapping({ "/trendistance" })
	public TotalDistances trendistance(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListAllIdBean allcompletesids,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin, final Integer frequencetreands) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.dashboardServiceInterface.trendistance(user,
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListclientids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListaffiliateids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListtransporterids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListvehicleids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListdriverids()),
				Utils.StringEscape(datedebut), Utils.StringEscape(datefin), frequencetreands);
	}

	@PostMapping({ "/trendresumexception" })
	public List<TrendBean> trendresumexception(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListAllIdWithtypeexceptionBean allcompletesids,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin, final Integer frequencetreands) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.dashboardServiceInterface.trendresumexception(user,
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListclientids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListaffiliateids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListtransporterids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListvehicleids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListdriverids()),
				Utils.StringEscape(datedebut), Utils.StringEscape(datefin),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListidtypeexception()),
				allcompletesids.getRecord(), allcompletesids.getAlert(), allcompletesids.getAlarm(), frequencetreands);
	}

	@PostMapping({ "/trendresumexception100km" })
	public List<TrendBean> trendresumexception100km(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListAllIdWithtypeexceptionBean allcompletesids,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin, final Integer frequencetreands) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.dashboardServiceInterface.trendresumexception100km(user,
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListclientids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListaffiliateids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListtransporterids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListvehicleids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListdriverids()),
				Utils.StringEscape(datedebut), Utils.StringEscape(datefin),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListidtypeexception()),
				allcompletesids.getRecord(), allcompletesids.getAlert(), allcompletesids.getAlarm(), frequencetreands);
	}

	@PostMapping({ "/worktime" })
	public ResponseEntity<?> worktime(@Parameter(hidden = true) Principal userP,
			@RequestBody final Worktime allcompletesids,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		if (Objects.isNull(user) || !user.getEnabled()) {
			return ResponseEntity.status(HttpStatus.OK).body((Object) new UserAccountStatus(true));
		}
		return this.dashboardServiceInterface.worktime(user,
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListclientids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListaffiliateids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListtransporterids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListvehicleids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListdriverids()),
				Utils.StringEscape(datedebut), Utils.StringEscape(datefin));
	}
}
