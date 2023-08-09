//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.decarbonisation.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camtrack.bean.ListAllIdBean;
import com.camtrack.bean.ListAllIdWithtypeexceptionBean;
import com.camtrack.bean.ResumeResults;
import com.camtrack.config.Utils;
import com.camtrack.decarbonisation.dao.DecarboInterface;
import com.camtrack.entities.User;
import com.camtrack.user.repository.UsersRepository;

//import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import springfox.documentation.annotations.ApiIgnore;

@RestController
//@RequestMapping({ "/api/v2/" })
@RefreshScope
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class CarbonController {
	@Autowired
	DecarboInterface excepinterface;
	@Autowired
	UsersRepository usersR;

	@PostMapping({ "/periodecarbo" })
	public List<Map<String, Object>> decarbonisation(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListAllIdBean allcompletesids,
			@Parameter(description = "Possible value: 1 --> Daily,2 --> weekly, 3 --> Monthly") @RequestParam(value = "period", required = true) final Integer period,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.excepinterface.periodecarbo(user,
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListclientids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListaffiliateids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListtransporterids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListvehicleids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListdriverids()),
				Utils.StringEscape(datedebut), Utils.StringEscape(datefin), period);
	}

	@PostMapping({ "/detaildecarbonisation" })
	public List<Map<String, Object>> detaildecarbonisation(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListAllIdBean allcompletesids,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.excepinterface.detaildecarbonisation(user,
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListclientids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListaffiliateids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListtransporterids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListvehicleids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListdriverids()),
				Utils.StringEscape(datedebut), Utils.StringEscape(datefin));
	}

	@PostMapping({ "/fuelconsum" })
	public List<Map<String, Object>> fuelconsum(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListAllIdBean allcompletesids,
			@Parameter(description = "Possible value: 1 --> Daily,2 --> weekly, 3 --> Monthly") @RequestParam(value = "period", required = true) final Integer period,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.excepinterface.fuelconsum(user,
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListclientids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListaffiliateids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListtransporterids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListvehicleids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListdriverids()),
				Utils.StringEscape(datedebut), Utils.StringEscape(datefin), period);
	}

	@PostMapping({ "/decarbonisation" })
	public ResumeResults resumexception(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListAllIdBean allcompletesids,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.excepinterface.summaryDecarbo(user,
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListclientids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListaffiliateids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListtransporterids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListvehicleids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListdriverids()),
				Utils.StringEscape(datedebut), Utils.StringEscape(datefin));
	}
}
