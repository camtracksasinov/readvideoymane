//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.usertype.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.camtrack.config.Response;
import com.camtrack.config.Utils;
import com.camtrack.entities.User;
import com.camtrack.user.repository.UsersRepository;
import com.camtrack.usertype.bean.UserTypeBean;
import com.camtrack.usertype.service.UserTypeServiceInterface;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import springfox.documentation.annotations.ApiIgnore;

@PropertySource({ "classpath:message.properties" })
@Controller
@RefreshScope
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class UserTypeController {
	@Autowired
	private Environment environment;
	@Autowired
	UsersRepository usersR;
	@Autowired
	UserTypeServiceInterface usertypeServiceInterface;

	@RequestMapping(value = { "/removeusertype" }, method = { RequestMethod.POST })
	@ResponseBody
	public Response deactivateUserType(@RequestBody final int item) throws Exception {
		try {
			final int i = this.usertypeServiceInterface.deactivateUserType(item);
			return new Response(1, this.environment.getProperty("deactivateusertype.success"));
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(0, this.environment.getProperty("deactivateusertype.failed"));
		}
	}

	@RequestMapping(value = { "/deleteusertypebyids/{ids}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Response deleteUserTypeByIds(@PathVariable("ids") final String[] ids) throws Exception {
		try {
			final List<String> lists = Arrays.asList(ids);
			for (final String l : lists) {
				this.usertypeServiceInterface.deleteUserTypeByIds(l);
			}
			return new Response(1, this.environment.getProperty("deactivateusertype.success"));
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(0, this.environment.getProperty("deactivateusertype.failed"));
		}
	}

	@RequestMapping(value = { "fetchusertypenames/{userTypeName}/{dumusertype}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Response existUserTypeName(@PathVariable("userTypeName") final String userTypeName,
			@PathVariable("dumusertype") final String dumusertype) throws Exception {
		try {
			final int count = this.usertypeServiceInterface.existUserTypeName(Utils.StringEscape(userTypeName),
					Utils.StringEscape(dumusertype));
			if (count != 0) {
				return new Response(1, this.environment.getProperty("UserTypeNameExist.success"));
			}
			return new Response(0, this.environment.getProperty("UserTypeNameExist.failed"));
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(0, this.environment.getProperty("UserTypeNameExist.failed"));
		}
	}

	@RequestMapping(value = { "/fetchusertypebyid" }, method = { RequestMethod.POST })
	@ResponseBody
	public Response getUserTypeById(@RequestBody final int usertypeid) throws Exception {
		try {
			UserTypeBean usertypebean = new UserTypeBean();
			usertypebean.setUserTypeId(usertypeid);
			usertypebean = this.usertypeServiceInterface.getUserTypeById(usertypebean);
			return new Response(1, this.environment.getProperty("getusertype.success"), usertypebean);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(0, this.environment.getProperty("getusertype.failed"));
		}
	}

	@RequestMapping(value = { "/loadusertypes" }, method = { RequestMethod.GET })
	public ResponseEntity<List> loadUserTypes() throws Exception {
		try {
			final List users = this.usertypeServiceInterface.loadUserTypes();
			if (users.isEmpty()) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity(users, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = { "/saveusertype" }, method = { RequestMethod.POST })
	@ResponseBody
	public Response saveUserType(@RequestBody final UserTypeBean usertype, final String language,
			@Parameter(hidden = true) Principal userP) throws Exception {
		try {
			final int count = this.usertypeServiceInterface.existUserTypeName(usertype.getUserTypeName(), "0");
			if (count > 0) {
				return new Response(2, "User type name exists");
			}
			final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
			usertype.setCreatedBy(String.valueOf(user.getUserid()));
			usertype.setUpdatedBy(String.valueOf(user.getUserid()));
			final int i = this.usertypeServiceInterface.saveUserType(usertype);
			if (i > 0) {
				return new Response(1, this.environment.getProperty("createUserType.success"));
			}
			return new Response(0, this.environment.getProperty("createUserType.failed"));
		} catch (Exception e) {
			return new Response(0, this.environment.getProperty("createUserType.failed"));
		}
	}

	@RequestMapping(value = { "/updateusertype" }, method = { RequestMethod.POST })
	@ResponseBody
	public Response updateUserType(@RequestBody final UserTypeBean usertype, @Parameter(hidden = true) Principal userP)
			throws Exception {
		try {
			final int count = this.usertypeServiceInterface.existUserTypeName(usertype.getUserTypeName(),
					usertype.getUserTypeId() + "");
			if (count > 0) {
				return new Response(2, "User type name exists");
			}
			final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
			usertype.setCreatedBy(String.valueOf(user.getUserid()));
			usertype.setUpdatedBy(String.valueOf(user.getUserid()));
			final int i = this.usertypeServiceInterface.updateUserType(usertype);
			if (i == 1) {
				return new Response(1, this.environment.getProperty("usertype.update.success"));
			}
			return new Response(0, this.environment.getProperty("usertype.update.failed"));
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(0, this.environment.getProperty("usertype.update.failed"));
		}
	}
}
