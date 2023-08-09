//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camtrack.entities.DetailsRawStatictics;
import com.camtrack.entities.User;
import com.camtrack.manualsubtractionandrecovery.service.ManualSubtractionAndRecoveryServiceInterface;
import com.camtrack.reports.bean.RawstatisticsBean;
import com.camtrack.reports.bean.ViewRawstatisticsBean;
import com.camtrack.reports.service.CCRawstatisticsService;
import com.camtrack.user.repository.UsersRepository;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RefreshScope
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class CCRawstatisticsController {
	@Autowired
	ManualSubtractionAndRecoveryServiceInterface manualServiceInterface;
	@Autowired
	CCRawstatisticsService service;
	@Autowired
	UsersRepository usersR;

	@RequestMapping(value = { "/rawstaticreport" }, method = { RequestMethod.POST })
	public ResponseEntity<?> getRawStaticList(@Parameter(hidden = true) Principal users,
			@RequestBody final RawstatisticsBean rawstat) throws Exception {
		try {
			User user = usersR.findByUserName(users.getName()).orElse(null);
			return ResponseEntity.status(HttpStatus.OK).body((Object) this.service.generateReport(rawstat, user));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/viewrawstatisticreport" }, method = { RequestMethod.GET })
	public List viewRawstatisticReeport(@RequestParam("clientid") final int clientid,
			@RequestParam("affiliateid") final int affiliateid, @RequestParam("transporterid") final int transporterid,
			@RequestParam("driverid") final int driverid, @RequestParam("startyear") final int startyear,
			@RequestParam("endyear") final int endyear, @RequestParam("startmonth") final int startmonth,
			@RequestParam("endmonth") final int endmonth) throws Exception {
		try {
			return this.manualServiceInterface.viewRawstatisticReeport(clientid, affiliateid, transporterid, driverid,
					startyear, endyear, startmonth, endmonth);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/viewstatistic" }, method = { RequestMethod.POST })
	public DetailsRawStatictics viwRewardRanking(@RequestBody ViewRawstatisticsBean viewrawstat) throws Exception {
		try {
			return this.service.detailrawstatistic(viewrawstat.getClientid(), viewrawstat.getAffiliateId(),
					viewrawstat.getTransporterid(), viewrawstat.getDriverid(), viewrawstat.getStartyear(),
					viewrawstat.getEndyear(), viewrawstat.getStartmonth(), viewrawstat.getEndmonth());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/recovid" }, method = { RequestMethod.GET })
	public List<Map<String, Object>> recovid(@RequestParam("clientid") final int clientid,
			@RequestParam("affiliateid") final int affiliateid, @RequestParam("transporterid") final int transporterid)
			throws Exception {
		try {
			return this.service.recovid(clientid, affiliateid, transporterid);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/removid" }, method = { RequestMethod.GET })
	public List<Map<String, Object>> removid(@RequestParam("clientid") final int clientid,
			@RequestParam("affiliateid") final int affiliateid, @RequestParam("transporterid") final int transporterid)
			throws Exception {
		try {
			return this.service.removid(clientid, affiliateid, transporterid);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
