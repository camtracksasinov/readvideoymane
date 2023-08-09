//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.camtrack.application.bean.ApplicationBean;
import com.camtrack.application.service.ApplicationServiceInterface;
import com.camtrack.config.AppConstants;
import com.camtrack.config.PropertyLoader;
import com.camtrack.config.Response;
import com.google.gson.Gson;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RefreshScope
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class ApplicationController {
	@Autowired
	ApplicationServiceInterface applicationServiceInterface;

	@RequestMapping(value = { "/deactivateapplicationmapping" }, method = { RequestMethod.POST })
	@ResponseBody
	public Response deactivateApplicationMapping(
			@RequestParam("clientaffapplicationid") final int clientaffapplicationid, final String language)
			throws Exception {
		try {
			final int i = this.applicationServiceInterface.deactivateApplicationMapping(clientaffapplicationid);
			return new Response(AppConstants.HTTP_SUCCESS_CODE,
					PropertyLoader.getMessage(language, "deactivateappmapping.success"));
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(AppConstants.HTTP_ERROR_CODE,
					PropertyLoader.getMessage(language, "deactivateappmapping.failed"));
		}
	}

	@RequestMapping(value = { "/fetchallapplications" }, method = { RequestMethod.GET })
	public List fetchAllApplications() throws Exception {
		try {
			return this.applicationServiceInterface.fetchAllApplications();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/loadclientapplicationmap" }, method = { RequestMethod.GET })
	@ResponseBody
	public Response loadClientApplicationMap(@RequestParam("customerid") final int customerid,
			@RequestParam("affiliateid") final int affiliateid, @RequestParam("applicationid") final int applicationid,
			final String language) throws Exception {
		try {
			final List paramDetails = this.applicationServiceInterface.loadClientApplicationMap(customerid, affiliateid,
					applicationid);
			return new Response(AppConstants.HTTP_SUCCESS_CODE, PropertyLoader.getMessage(language, "fetching.success"),
					paramDetails);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/mapclientapplication" }, method = { RequestMethod.POST })
	@ResponseBody
	public Response mapClientApplication(@RequestParam("data") final String jsonData, final String language)
			throws Exception {
		try {
			final Gson gson = new Gson();
			final ApplicationBean applicationBean = (ApplicationBean) gson.fromJson(jsonData,
					(Class) ApplicationBean.class);
			System.out.println(applicationBean);
			final int i = this.applicationServiceInterface.mapClientApplication(applicationBean);
			if (i == 0) {
				return new Response(AppConstants.HTTP_SUCCESS_CODE,
						PropertyLoader.getMessage(language, "clientapplnmap.alreadyexist"));
			}
			if (i > 0) {
				return new Response(AppConstants.HTTP_SUCCESS_CODE,
						PropertyLoader.getMessage(language, "clientapplnmap.success"));
			}
			return new Response(AppConstants.HTTP_ERROR_CODE,
					PropertyLoader.getMessage(language, "clientapplnmap.failed"));
		} catch (Exception e) {
			System.out.println(e);
			return new Response(AppConstants.HTTP_ERROR_CODE,
					PropertyLoader.getMessage(language, "clientapplnmap.failed"));
		}
	}
}
