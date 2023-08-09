//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.camtrack.bean.ListIdBean;
import com.camtrack.bean.Profilroles;
import com.camtrack.bean.Userallidroles;
import com.camtrack.config.AppConstants;
import com.camtrack.config.PropertyLoader;
import com.camtrack.config.Response;
import com.camtrack.config.Utils;
import com.camtrack.entities.User;
import com.camtrack.entities.UserAccountStatus;
import com.camtrack.user.bean.UserBean;
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
public class UserController {
	@Autowired
	private Environment environment;
	@Value("${roleprivacyid}")
	private Integer roleprivacyid;
	@Autowired
	UserServiceInterface userServiceInterface;
	@Autowired
	UsersRepository usersR;

	@RequestMapping(value = { "/alllisttypeconfig" }, method = { RequestMethod.POST })
	public ResponseEntity<?> alllisttypeconfig() throws Exception {
		try {
			return this.userServiceInterface.alllisttypeconfig();
		} catch (Exception ex) {
			return null;
		}
	}

	@PostMapping({ "/createorupdate" })
	public ResponseEntity<?> createorupdate(@Parameter(hidden = true) Principal userP,
			@RequestBody @Valid Userallidroles alluseroles) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		if (Objects.isNull(user) || !user.getEnabled()) {
			return ResponseEntity.status(HttpStatus.OK).body((Object) new UserAccountStatus(true));
		}
		return this.userServiceInterface.createorupdateuser(alluseroles, user);
	}

	@RequestMapping(value = { "/deactivateuser" }, method = { RequestMethod.POST })
	@ResponseBody
	public Response deactivateUser(@RequestParam("qid") final int qid, final String language) throws Exception {
		try {
			final int i = this.userServiceInterface.deactivateUser(qid);
			return new Response(AppConstants.HTTP_SUCCESS_CODE,
					PropertyLoader.getMessage(language, "deactivateuser.success"));
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(AppConstants.HTTP_ERROR_CODE,
					PropertyLoader.getMessage(language, "deactivateuser.failed"));
		}
	}

	@RequestMapping(value = { "/fetchtransporterofaffiliatelist" }, method = { RequestMethod.POST })
	public ResponseEntity<?> fechaffiliatelist(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListIdBean listaffiliateid) throws Exception {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		if (Objects.isNull(user) || !user.getEnabled()) {
			return ResponseEntity.status(HttpStatus.OK).body((Object) new UserAccountStatus(true));
		}
		final Integer[] listid = listaffiliateid.getListids();
		List<Integer> lis = null;
		if (!Objects.isNull(listid) && listid.length > 0) {
			lis = Arrays.stream(listid).collect(Collectors.toList());
		}
		return this.userServiceInterface.FetchTransporterList(user, lis);
	}

	@RequestMapping(value = { "/fetchaffiliateofclient" }, method = { RequestMethod.POST })
	public ResponseEntity<?> fechaffiliateofclient(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListIdBean listcustomerid) throws Exception {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		if (Objects.isNull(user) || !user.getEnabled()) {
			return ResponseEntity.status(HttpStatus.OK).body((Object) new UserAccountStatus(true));
		}
		final Integer[] listid = listcustomerid.getListids();
		List<Integer> lis = null;
		if (!Objects.isNull(listid) && listid.length > 0) {
			lis = Arrays.stream(listid).collect(Collectors.toList());
		}
		return this.userServiceInterface.FetchAffilatesList(user, lis);
	}

	@RequestMapping(value = { "/fetchusers" }, method = { RequestMethod.GET })
	public ResponseEntity<?> fechUsers(@Parameter(hidden = true) Principal userP) throws Exception {
		try {
			final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
			if (Objects.isNull(user) || !user.getEnabled()) {
				return ResponseEntity.status(HttpStatus.OK).body((Object) new UserAccountStatus(true));
			}
			return this.userServiceInterface.fechUsers(user);
		} catch (Exception ex) {
			return null;
		}
	}

	@RequestMapping(value = { "/fetchclientlist" }, method = { RequestMethod.GET })
	public ResponseEntity<?> FetchClientList(@Parameter(hidden = true) Principal userP) throws Exception {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		if (Objects.isNull(user) || !user.getEnabled()) {
			return ResponseEntity.status(HttpStatus.OK).body((Object) new UserAccountStatus(true));
		}
		return this.userServiceInterface.FetchClientList(user);
	}

	@RequestMapping(value = { "/fetchusersbysearchcriteria" }, method = { RequestMethod.POST })
	public ResponseEntity<?> FetchUsers(@RequestBody final ListIdBean listclientid,
			@RequestBody final ListIdBean listaffiliateid, @RequestBody final ListIdBean listtransporterid)
			throws Exception {
		try {
			final List userlist = this.userServiceInterface.getUsersBySearchCriteria(
					ListIdBean.listToString(listclientid.getListids()),
					ListIdBean.listToString(listaffiliateid.getListids()),
					ListIdBean.listToString(listtransporterid.getListids()));
			return new ResponseEntity(userlist, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = { "/fetchvehtranslist" }, method = { RequestMethod.POST })
	public ResponseEntity<?> fetchvehtranslist(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListIdBean listtransporterid) throws Exception {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		try {
			if (user.getReelrole().getIds() == roleprivacyid) {
				return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>());
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>());
		}
		if (Objects.isNull(user) || !user.getEnabled()) {
			return ResponseEntity.status(HttpStatus.OK).body((Object) new UserAccountStatus(true));
		}
		return this.userServiceInterface.FetchVehicleList(user,
				Arrays.stream(listtransporterid.getListids()).collect(Collectors.toList()));
	}

	@RequestMapping(value = { "/getdriverdetails/{vehicleid}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Response getDriverDetails(@PathVariable("vehicleid") final int vehicleid, final String language)
			throws Exception {
		try {
			final List driverList = this.userServiceInterface.getDriverDetailsByVehicleId(vehicleid);
			return new Response(AppConstants.HTTP_SUCCESS_CODE,
					PropertyLoader.getMessage(language, "getdriver.success"), driverList);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(AppConstants.HTTP_ERROR_CODE, PropertyLoader.getMessage(language, "getdriver.failed"));
		}
	}

	@RequestMapping(value = { "/fetchuserbyid/{userid}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Response getUser(@PathVariable("userid") final int userid, final String language) throws Exception {
		try {
			UserBean userBean = new UserBean();
			userBean.setUserid(userid);
			userBean = this.userServiceInterface.getUserById(userBean);
			return new Response(AppConstants.HTTP_SUCCESS_CODE, PropertyLoader.getMessage(language, "getuser.success"),
					userBean);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(AppConstants.HTTP_ERROR_CODE, PropertyLoader.getMessage(language, "getuser.failed"));
		}
	}

	@RequestMapping(value = { "/logout" }, method = { RequestMethod.POST })
	public ResponseEntity<?> logout(@Parameter(hidden = true) Principal userP) throws Exception {
		try {
			final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
			if (Objects.isNull(user) || !user.getEnabled()) {
				return ResponseEntity.status(HttpStatus.OK).body((Object) new UserAccountStatus(true));
			}
			return this.userServiceInterface.logout(user);
		} catch (Exception ex) {
			return null;
		}
	}

	@RequestMapping(value = { "/logusers" }, method = { RequestMethod.POST })
	public ResponseEntity<?> logusers(@Parameter(hidden = true) Principal userP,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin,
			@RequestBody final ListIdBean listuserids) throws Exception {
		try {
			final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
			if (Objects.isNull(user) || !user.getEnabled()) {
				return ResponseEntity.status(HttpStatus.OK).body((Object) new UserAccountStatus(true));
			}
			final Integer[] listid = listuserids.getListids();
			List<Integer> lis = null;
			if (!Objects.isNull(listid) && listid.length > 0) {
				lis = Arrays.stream(listid).collect(Collectors.toList());
			}
			return this.userServiceInterface.logusers(user, Utils.StringEscape(datedebut), Utils.StringEscape(datefin),
					lis);
		} catch (Exception ex) {
			return null;
		}
	}

	@PostMapping({ "/reelhierarchie" })
	public ResponseEntity<?> reelhierarchie(@Parameter(hidden = true) Principal userP, Integer typeinfos) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return ResponseEntity.status(HttpStatus.OK).body(userServiceInterface.hierarchie(user, typeinfos));
	}

	@RequestMapping(value = { "/activitiesusers" }, method = { RequestMethod.POST })
	public List<Map<String, Object>> resultactivity(@Parameter(hidden = true) Principal userP,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin,
			@RequestBody final ListIdBean listuserids,
			@RequestParam(value = "typeconfig", required = false) final Short typeconfig) throws Exception {
		try {
			final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
			return this.userServiceInterface.useractivity(user, Utils.StringEscape(datedebut),
					Utils.StringEscape(datefin), listuserids, typeconfig);
		} catch (Exception ex) {
			return null;
		}
	}

	@PostMapping({ "/updateprofil" })
	public ResponseEntity<?> updateprofil(@Parameter(hidden = true) Principal userP,
			@RequestBody final Profilroles alluseroles) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		if (Objects.isNull(user) || !user.getEnabled()) {
			return ResponseEntity.status(HttpStatus.OK).body((Object) new UserAccountStatus(true));
		}
		return this.userServiceInterface.Profilroles(alluseroles, user);
	}

	@PostMapping(value = { "/updatelogousers" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> updateRefferal(
			@Parameter(content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE) // Won't
																																	// work
																																	// without
																																	// OCTET_STREAM
																																	// as
																																	// the
																																	// mediaType.
			) @RequestParam(value = "file", required = false) MultipartFile file,
			@Parameter(hidden = true) Principal userP) throws Exception {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.userServiceInterface.updateLogoUser(file, user);
	}

}
