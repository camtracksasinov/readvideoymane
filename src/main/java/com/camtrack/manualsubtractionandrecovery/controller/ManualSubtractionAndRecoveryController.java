//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.manualsubtractionandrecovery.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camtrack.config.Utils;
import com.camtrack.entities.Success;
import com.camtrack.entities.User;
import com.camtrack.manualsubtractionandrecovery.bean.Manualsubnrec;
import com.camtrack.manualsubtractionandrecovery.bean.Manualsubnreclist;
import com.camtrack.manualsubtractionandrecovery.bean.Mylist;
import com.camtrack.manualsubtractionandrecovery.bean.RecoveryBean;
import com.camtrack.manualsubtractionandrecovery.service.ManualSubtractionAndRecoveryServiceInterface;
import com.camtrack.user.repository.UsersRepository;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import springfox.documentation.annotations.ApiIgnore;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class ManualSubtractionAndRecoveryController {
	@Autowired
	ManualSubtractionAndRecoveryServiceInterface manualServiceInterface;
	@Autowired
	UsersRepository usersR;

	@RequestMapping(value = { "/deactivaterecord" }, method = { RequestMethod.POST })
	public ResponseEntity<?> deactivateRecord(@RequestParam("customerid") final int customerid,
			@RequestParam("affid") final int affid, @RequestParam("transid") final int transid,
			@RequestParam("driverid") final int driverid, @RequestParam("occdate") final String occdate)
			throws Exception {
		try {
			final int i = this.manualServiceInterface.deactivateRecord(customerid, affid, transid, driverid,
					Utils.StringEscape(occdate));
			return ResponseEntity.status(HttpStatus.OK).body((Object) new Success("Success", i));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = { "/loadallmylist" }, method = { RequestMethod.POST })
	public List<RecoveryBean> fetchMyList(@Parameter(hidden = true) Principal userP, @RequestBody final Mylist mylist,
			@RequestParam("fromdate") final String fromdate, @RequestParam("todate") final String todate)
			throws Exception {
		try {
			final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
			return this.manualServiceInterface.fetchMyList(user, Mylist.listToString(mylist.getCustomerid()),
					Mylist.listToString(mylist.getAffiliateid()), Mylist.listToString(mylist.getTransporterid()),
					Mylist.listToString(mylist.getDriverid()), Utils.StringEscape(fromdate),
					Utils.StringEscape(todate));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/fetchRecoveryParametersmap" }, method = { RequestMethod.GET })
	public ResponseEntity<?> fetchRecoveryParametersMap(@RequestParam("customerid") final int clientid,
			@RequestParam("affid") final int affiliateid, @RequestParam("transid") final int transid,
			@RequestParam("driverid") final int driverid, @RequestParam("occdate") final String occdate)
			throws Exception {
		try {
			final Map paramDetails = this.manualServiceInterface.recoveryParamMap(clientid, affiliateid, transid,
					driverid, Utils.StringEscape(occdate));
			return ResponseEntity.status(HttpStatus.OK).body((Object) paramDetails);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/fetchresetfreqbyaffiliateid" }, method = { RequestMethod.GET })
	public ResponseEntity<?> fetchresetFreqByAffilaiteId(@RequestParam("affiliateid") final int affiliateid)
			throws Exception {
		try {
			final int resetfrequencyDetails = this.manualServiceInterface.fetchresetfreqbyaffiliateid(affiliateid);
			return ResponseEntity.status(HttpStatus.OK).body((Object) new Success("Success", resetfrequencyDetails));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/fetchvisualparametersmap" }, method = { RequestMethod.GET })
	public ResponseEntity<?> fetchVisualParametersMap(@RequestParam("customerid") final int clientid,
			@RequestParam("affid") final int affiliateid, @RequestParam("transid") final int transid,
			@RequestParam("driverid") final int driverid, @RequestParam("occdate") final String occdate)
			throws Exception {
		try {
			final Map paramDetails = this.manualServiceInterface.visualParamMap(clientid, affiliateid, transid,
					driverid, Utils.StringEscape(occdate));
			return ResponseEntity.status(HttpStatus.OK).body((Object) paramDetails);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/scpobcparameters" }, method = { RequestMethod.GET })
	public List obcparameters() throws Exception {
		try {
			return this.manualServiceInterface.scpobcparameters();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/savemanualsubtractionandrecovery" }, method = { RequestMethod.POST })
	public ResponseEntity<?> saveManualSubtractionAndRecovery(@Parameter(hidden = true) Principal userP,
			@RequestBody final Manualsubnrec manualsubnrecBean) throws Exception {
		try {
			final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
			final int i = this.manualServiceInterface.saveManualSubtractionAndRecovery(user, manualsubnrecBean);
			return ResponseEntity.status(HttpStatus.OK).body((Object) new Success("Success", i));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body((Object) new Success("Success", -1));
		}
	}

	@RequestMapping(value = { "/savelistmanualsubtractionandrecovery" }, method = { RequestMethod.POST })
	public ResponseEntity<?> savemanualsubtractionandrecovery(@Parameter(hidden = true) Principal userP,
			@RequestBody final Manualsubnreclist manualsubnrecBean) throws Exception {
		try {
			final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
			final int i = this.manualServiceInterface.saveManualSubtractionAndRecoverylist(user, manualsubnrecBean);
			return ResponseEntity.status(HttpStatus.OK).body((Object) new Success("Success", i));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body((Object) new Success("Success", -1));
		}
	}

	@RequestMapping(value = { "/scprecovery" }, method = { RequestMethod.GET })
	public List scprecovery() throws Exception {
		try {
			return this.manualServiceInterface.scprecovery();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/visualparameter" }, method = { RequestMethod.GET })
	public List visualparameter() throws Exception {
		try {
			return this.manualServiceInterface.visualparameter();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
