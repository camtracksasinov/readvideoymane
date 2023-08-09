//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camtrack.config.Utils;
import com.camtrack.reports.service.SummaryOfExceptionServiceInterface;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RefreshScope
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class SummaryOfExceptionController {
	@Autowired
	SummaryOfExceptionServiceInterface deServiceInterface;

	@RequestMapping(value = { "/getdetailedsummaryexceptionreport" }, method = { RequestMethod.GET })
	public List detailedExceptionReport(@RequestParam("parametertypeid") final int parametertypeid,
			@RequestParam("clientid") final int clientid, @RequestParam("affiliateid") final int affiliateid,
			@RequestParam("transporterid") final int transporterid, @RequestParam("vehicleid") final int vehicleid,
			@RequestParam("startdate") final String startdate, @RequestParam("enddate") final String enddate)
			throws Exception {
		try {
			return this.deServiceInterface.showDetailedExceptionReport(parametertypeid, clientid, affiliateid,
					transporterid, vehicleid, Utils.StringEscape(startdate), Utils.StringEscape(enddate));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/summaryofexceptionreport" }, method = { RequestMethod.GET })
	public List summaryofExceptionReport(@RequestParam("clientid") final int clientid,
			@RequestParam("affiliateid") final int affiliateid, @RequestParam("transporterid") final int transporterid,
			@RequestParam("vehicleid") final int vehicleid, @RequestParam("startdate") final String startdate,
			@RequestParam("enddate") final String enddate) throws Exception {
		try {
			return this.deServiceInterface.showSummaryOfExceptionReport(clientid, affiliateid, transporterid, vehicleid,
					Utils.StringEscape(startdate), Utils.StringEscape(enddate));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
