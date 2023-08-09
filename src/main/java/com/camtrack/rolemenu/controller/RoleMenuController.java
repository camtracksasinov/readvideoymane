//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.rolemenu.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camtrack.bean.CreateNewUserRole;
import com.camtrack.entities.User;
import com.camtrack.rolemenu.service.RoleMenuServiceInterface;
import com.camtrack.user.repository.UsersRepository;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import springfox.documentation.annotations.ApiIgnore;

@PropertySource({ "classpath:message.properties" })
@RestController
@RefreshScope
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class RoleMenuController {
	@Autowired
	private Environment environment;
	@Autowired
	RoleMenuServiceInterface rolemenuServiceInterface;
	@Autowired
	UsersRepository usersR;

	@RequestMapping(value = { "/createorupdatenewrole" }, method = { RequestMethod.POST })
	public ResponseEntity<?> createnewrole(@Parameter(hidden = true) Principal userP,
			@RequestBody final CreateNewUserRole newrole) throws Exception {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.rolemenuServiceInterface.createorupdatenewrole(user, newrole);
	}

	@RequestMapping(value = { "/fetchallmenus" }, method = { RequestMethod.GET })
	public ResponseEntity<?> fetchAllMenus() throws Exception {
		return this.rolemenuServiceInterface.fetchALLMenus();
	}

	@RequestMapping(value = { "/fetchmenus" }, method = { RequestMethod.GET })
	public ResponseEntity<?> fetchMenus(@RequestParam("userRoleId") final int userRoleId) throws Exception {
		return this.rolemenuServiceInterface.fetchMenus(userRoleId);
	}

	@RequestMapping(value = { "/fetchuserrole" }, method = { RequestMethod.GET })
	public ResponseEntity<?> FetchUserRoles(@Parameter(hidden = true) Principal userP) throws Exception {
		try {
			final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
			return this.rolemenuServiceInterface.fetchUserRoles(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/listallrolewithaccess" }, method = { RequestMethod.GET })
	public ResponseEntity<?> listallrolewithaccess(@Parameter(hidden = true) Principal userP) throws Exception {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.rolemenuServiceInterface.listallrolewithaccess(user);
	}

	@RequestMapping(value = { "/listtyperole" }, method = { RequestMethod.GET })
	public ResponseEntity<?> listtyperole() throws Exception {
		return this.rolemenuServiceInterface.listtyperole();
	}
}
