//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.exception.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camtrack.bean.ListAllIdWithtypeexceptionBean;
import com.camtrack.bean.ListInvalidExceptionBean;
import com.camtrack.config.Utils;
import com.camtrack.entities.User;
import com.camtrack.exception.dao.ExceptionInterface;
import com.camtrack.user.repository.UsersRepository;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping({ "/api/v2/" })
@RefreshScope
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class ExceptionController {
	@Autowired
	ExceptionInterface excepinterface;
	@Autowired
	UsersRepository usersR;

	@RequestMapping(value = { "/exceptionlevel" }, method = { RequestMethod.GET })
	public List<Map<String, Object>> exceptionlevel(@Parameter(hidden = true) Principal userP) throws Exception {
		return this.excepinterface.listexceptionlevel();
	}

	@RequestMapping(value = { "/allexceptiontype" }, method = { RequestMethod.GET })
	public ResponseEntity<?> fetchAllTypeException(@Parameter(hidden = true) Principal userP) throws Exception {
		try {
			return ResponseEntity.status(HttpStatus.OK).body((Object) this.excepinterface.listExceptionTypes());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = { "/filterexceptiontype" }, method = { RequestMethod.GET })
	public ResponseEntity<?> filterexception(@Parameter(hidden = true) Principal userP) throws Exception {
		try {
			return ResponseEntity.status(HttpStatus.OK).body((Object) this.excepinterface.filterexception());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping({ "/groupinvalidation" })
	public ResponseEntity<?> groupinvalidation(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListAllIdWithtypeexceptionBean allcompletesids,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.excepinterface.groupinvalidation(user,
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListclientids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListaffiliateids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListtransporterids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListvehicleids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListdriverids()),
				Utils.StringEscape(datedebut), Utils.StringEscape(datefin),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListidtypeexception()),
				allcompletesids.getRecord(), allcompletesids.getAlert(), allcompletesids.getAlarm());
	}

	@PostMapping({ "/invalidexception" })
	public List<Map<String, Object>> invalidexception(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListAllIdWithtypeexceptionBean allcompletesids,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.excepinterface.invalidexception(user,
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListclientids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListaffiliateids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListtransporterids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListvehicleids()),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListdriverids()),
				Utils.StringEscape(datedebut), Utils.StringEscape(datefin),
				ListAllIdWithtypeexceptionBean.listToString(allcompletesids.getListidtypeexception()),
				allcompletesids.getRecord(), allcompletesids.getAlert(), allcompletesids.getAlarm());
	}

	@PostMapping({ "/invalidorvalidexception" })
	public ResponseEntity<?> invalidexceptionupdatestatus(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListInvalidExceptionBean invalidorvalid) throws Exception {
		try {
			final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
			return this.excepinterface.invalidexceptionupdatestatus(user, invalidorvalid);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping({ "/detailsexception" })
	public List<Map<String, Object>> resumexception(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListAllIdWithtypeexceptionBean allcompletesids,
			@RequestParam(value = "datedebut", required = true) final String datedebut,
			@RequestParam(value = "datefin", required = true) final String datefin) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.excepinterface.detailsexception(user,
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
