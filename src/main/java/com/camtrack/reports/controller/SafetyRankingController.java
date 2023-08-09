//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camtrack.bean.ListAllIdWithtypeexceptionBean;
import com.camtrack.config.Utils;
import com.camtrack.entities.User;
import com.camtrack.reports.bean.SafetyRanking;
import com.camtrack.reports.service.TopRankingServiceInterface;
import com.camtrack.user.repository.UsersRepository;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

//import springfox.documentation.annotations.ApiIgnore;

@RestController
@RefreshScope
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class SafetyRankingController {
	@Autowired
	TopRankingServiceInterface topRankingServiceInterface;
	@Autowired
	UsersRepository usersR;

	@RequestMapping(value = { "/safetyrankingreport" }, method = { RequestMethod.POST })
	public SafetyRanking topRankingReport(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListAllIdWithtypeexceptionBean allcompletesids,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin) throws Exception {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.topRankingServiceInterface.safetyranking(user,
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListclientids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListaffiliateids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListtransporterids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListvehicleids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListdriverids()),
				Utils.StringEscape(datedebut), Utils.StringEscape(datefin),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListidtypeexception()),
				allcompletesids.getRecord(), allcompletesids.getAlert(), allcompletesids.getAlarm());
	}
}
