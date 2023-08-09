//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.camtrack.config.AppConstants;
import com.camtrack.config.PropertyLoader;
import com.camtrack.config.Response;
import com.camtrack.configuartion.bean.AffiliateParameterMappingBean;
import com.camtrack.configuartion.service.AffiliateParameterMappingServiceInterface;
import com.camtrack.entities.User;
import com.camtrack.user.repository.UsersRepository;
import com.google.gson.Gson;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@PropertySource({ "classpath:message.properties" })
@RestController
@RefreshScope
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class AffiliateParameterMappingController {
	@Autowired
	AffiliateParameterMappingServiceInterface affiliateParameterMappingServiceInterface;
	@Autowired
	private Environment environment;
	@Autowired
	UsersRepository usersR;

	@RequestMapping(value = { "/fetchaffiliateparamsmap" }, method = { RequestMethod.GET })
	public List fetchAffiliateParamsMap(@RequestParam("customerid") final int customerid,
			@RequestParam("clientaffiliateid") final int affiliateid) throws Exception {
		try {
			return this.affiliateParameterMappingServiceInterface.getAffiliateParameterMap(customerid, affiliateid);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/mapaffiliateandparameter" }, method = { RequestMethod.POST })
	@ResponseBody
	public Response mapAffiliateAndParameter(@RequestParam("data") final String jsonData, final String language,
			@Parameter(hidden = true) Principal userP) throws Exception {
		try {
			final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
			final Gson gson = new Gson();
			final AffiliateParameterMappingBean mappingBean = (AffiliateParameterMappingBean) gson.fromJson(jsonData,
					(Class) AffiliateParameterMappingBean.class);
			mappingBean.setCreatedby(String.valueOf(user.getUserid()));
			mappingBean.setUpdatedby(String.valueOf(user.getUserid()));
			final int i = this.affiliateParameterMappingServiceInterface.mapAffiliateAndParameter(mappingBean);
			if (i > 0) {
				return new Response(AppConstants.HTTP_SUCCESS_CODE,
						PropertyLoader.getMessage(language, "parammapping.success"));
			}
			return new Response(AppConstants.HTTP_ERROR_CODE,
					PropertyLoader.getMessage(language, "parammapping.failed"));
		} catch (Exception e) {
			return new Response(AppConstants.HTTP_ERROR_CODE,
					PropertyLoader.getMessage(language, "parammapping.failed"));
		}
	}
}
