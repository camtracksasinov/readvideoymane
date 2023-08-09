//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.affiliate.controller;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.camtrack.affiliate.bean.AffiliateInfos;
import com.camtrack.affiliate.service.AffiliateServiceInterface;
import com.camtrack.bean.ListIdBean;
import com.camtrack.config.AppConstants;
import com.camtrack.config.PropertyLoader;
import com.camtrack.config.Response;
import com.camtrack.config.StaticValues;
import com.camtrack.config.Utils;
import com.camtrack.entities.Reelroleusers;
import com.camtrack.entities.Success;
import com.camtrack.entities.User;
import com.camtrack.user.repository.UsersRepository;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

//import springfox.documentation.annotations.ApiIgnore;

@PropertySource({ "classpath:message.properties" })
@RestController
@RefreshScope
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class AffiliateController {
	@Autowired
	AffiliateServiceInterface affiliateService;
	@Autowired
	UsersRepository usersR;

	@RequestMapping(value = { "/deactivateaffiliate" }, method = { RequestMethod.GET })
	public ResponseEntity<?> deactivateAffiliate(@RequestParam("affiliateid") final Integer affiliateid,
			final Principal userP) throws Exception {
		try {
			final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
			this.affiliateService.deactivateAffiliate(affiliateid, user.getUserid());
			return ResponseEntity.status(HttpStatus.OK)
					.body((Object) new Success(StaticValues.success, StaticValues.success_Int));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(
					(Object) new Success(StaticValues.CodeCustomerIncorrect, StaticValues.CodeCustomerIncorrect_Int));
		}
	}

	@RequestMapping(value = { "/fetchallaffiliates" }, method = { RequestMethod.GET })
	public List fetchRefferals(@RequestParam("customerid") final int customerid) throws Exception {
		try {
			return this.affiliateService.fetchAffiliatesbycostomer(customerid);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/fetchaffiliatebylistid" }, method = { RequestMethod.POST })
	@ResponseBody
	public Response getAffiliate(@RequestBody final ListIdBean listaffiliateid, final Principal userP)
			throws Exception {
		final String language = AppConstants.DEFAULT_LANGUAGE;
		try {
			final List affiliateDetails = this.affiliateService
					.getAffiliateByListId(ListIdBean.listToString(listaffiliateid.getListids()));
			return new Response(AppConstants.HTTP_SUCCESS_CODE, PropertyLoader.getMessage(language, "fetching.success"),
					affiliateDetails);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(AppConstants.HTTP_ERROR_CODE, PropertyLoader.getMessage(language, "fetching.failed"));
		}
	}

	@RequestMapping(value = { "/hierachicallistaffiliateuser" }, method = { RequestMethod.GET })
	public ResponseEntity<?> hierachicallistaffiliateuser(@Parameter(hidden = true) Principal userP) throws Exception {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.affiliateService.hierachicallistaffiliateuser(user);
	}

	@RequestMapping(value = { "/saveaffiliate" }, method = { RequestMethod.POST })
	public ResponseEntity<?> saveAffiliate(@RequestBody final AffiliateInfos affiliate,
			@Parameter(hidden = true) Principal userP) throws Exception {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.affiliateService.saveAffiliate(affiliate, user);
	}

	@RequestMapping(value = { "/testingcontrollertodelete" }, method = { RequestMethod.GET })
	public ResponseEntity<?> testingcontrollertodelete(@Parameter(hidden = true) Principal userP) throws Exception {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.affiliateService.hierachicallistaffiliateuser(user);
	}

	@RequestMapping(value = { "/updateaffiliate" }, method = { RequestMethod.POST })
	public ResponseEntity<?> updateRefferal(@RequestBody final AffiliateInfos affiliate, final Principal userP)
			throws Exception {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		final Reelroleusers role = user.getReelrole();
		if (!Objects.isNull(role) && (role.getIdtyperole().getUserroleid() == Utils.adminRoleId
				|| role.getIdtyperole().getUserroleid() == Utils.affiliateRoleId)) {
			return this.affiliateService.saveAffiliate(affiliate, user);
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body((Object) new Success(StaticValues.Userroleislimits, StaticValues.Userroleislimits_Int));
	}
}
