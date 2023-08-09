//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.camtrack.entities.User;
import com.camtrack.user.bean.EmailConfigBean;
import com.camtrack.user.bean.UpdateEmailConfigBean;
import com.camtrack.user.repository.UsersRepository;
import com.camtrack.user.service.EmailConfigServiceInterface;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import springfox.documentation.annotations.ApiIgnore;

@PropertySource({ "classpath:message.properties" })
@RestController
@RefreshScope
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class EmailConfigController {
	@Autowired
	EmailConfigServiceInterface service;
	@Autowired
	UsersRepository usersR;

	@RequestMapping(value = { "/fetchlistuseremailparam" }, method = { RequestMethod.GET })
	public ResponseEntity<?> fetchlistuseremailparam(@Parameter(hidden = true) Principal userP) throws Exception {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.service.fetchlistuseremailparam(user);
	}

	@RequestMapping(value = { "/saveemailconfig" }, method = { RequestMethod.POST })
	public ResponseEntity<?> saveEmailConfig(@RequestBody final EmailConfigBean emailConfigBean,
			@Parameter(hidden = true) Principal userP) throws Exception {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.service.saveconfig(user, emailConfigBean);
	}

	@RequestMapping(value = { "/updateemailparam" }, method = { RequestMethod.GET })
	public ResponseEntity<?> updateemailparam(@RequestBody final UpdateEmailConfigBean updatemailparam,
			@Parameter(hidden = true) Principal userP) throws Exception {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.service.updateemailparam(user, updatemailparam);
	}
}
