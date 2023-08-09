//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camtrack.config.Utils;
import com.camtrack.reports.service.ScoreCardServiceInterface;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@PropertySource({ "classpath:message.properties" })
@RestController
@RefreshScope
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class ScoreCardController {
	@Autowired
	ScoreCardServiceInterface service;

	@RequestMapping(value = { "/fetchscoreparamsmap" }, method = { RequestMethod.GET })
	public List fetchParamsMap() throws Exception {
		try {
			return this.service.getScoreParameterMap();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/viewscorecard" }, method = { RequestMethod.GET })
	public List<Map<String, Object>> ScorecardDetailedView(@RequestParam("customerid") final int customerid,
			@RequestParam("affiliateid") final int affiliateid, @RequestParam("transporterid") final int transporterid,
			@RequestParam("vehicleid") final int vehicleid, @RequestParam("param") final int param,
			@RequestParam("startDate") final String startDate, @RequestParam("endDate") final String endDate)
			throws Exception {
		try {
			final List<Map<String, Object>> lst = this.service.showScoreCardDetailedReport(customerid, affiliateid,
					transporterid, vehicleid, param, Utils.StringEscape(startDate), Utils.StringEscape(endDate));
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/scorecard" }, method = { RequestMethod.GET })
	public List<Map<String, Object>> topRankingReport(@RequestParam("customerid") final int customerid,
			@RequestParam("affiliateid") final int affiliateid, @RequestParam("transporterid") final int transporterid,
			@RequestParam("vehicleid") final int vehicleid, @RequestParam("param") final int param,
			@RequestParam("startDate") final String startDate, @RequestParam("endDate") final String endDate)
			throws Exception {
		try {
			final List<Map<String, Object>> lst = this.service.showScoreCardReport(customerid, affiliateid,
					transporterid, vehicleid, param, Utils.StringEscape(startDate), Utils.StringEscape(endDate));
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
