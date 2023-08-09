//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.client.controller;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.camtrack.client.bean.ClientBean;
import com.camtrack.client.service.ClientServiceInterface;
import com.camtrack.config.StaticValues;
import com.camtrack.config.Utils;
import com.camtrack.entities.Reelroleusers;
import com.camtrack.entities.Success;
import com.camtrack.entities.User;
import com.camtrack.user.repository.UsersRepository;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import springfox.documentation.annotations.ApiIgnore;

@PropertySource({ "classpath:message.properties" })
@RestController
@RefreshScope
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class ClientController {
	@Autowired
	ClientServiceInterface clientService;
	@Autowired
	private Environment environment;
	@Autowired
	UsersRepository usersR;

	@RequestMapping(value = { "/deactivateclient" }, method = { RequestMethod.GET })
	public ResponseEntity<?> deactivateClient(@RequestParam("customerid") final Integer customerid,
			@Parameter(hidden = true) Principal userP) throws Exception {
		try {
			this.clientService.deactivateClient(customerid);
			return ResponseEntity.status(HttpStatus.OK)
					.body((Object) new Success(StaticValues.success, StaticValues.success_Int));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(
					(Object) new Success(StaticValues.CodeCustomerIncorrect, StaticValues.CodeCustomerIncorrect_Int));
		}
	}

	@RequestMapping(value = { "/fetchallcitiesofstate" }, method = { RequestMethod.GET })
	public List fetchAllCitiesOfState(@RequestParam("stateid") final int stateid) throws Exception {
		try {
			return this.clientService.getAllCitiesOfState(stateid);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/fetchallcountries" }, method = { RequestMethod.GET })
	public List fetchAllCountries() throws Exception {
		try {
			return this.clientService.getAllCounties();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/fetchalllanguages" }, method = { RequestMethod.GET })
	public List fetchAllLanguages() throws Exception {
		try {
			return this.clientService.getAllLanguages();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/fetchallstatesofcountry" }, method = { RequestMethod.GET })
	public List fetchAllStatesOfCountry(@RequestParam("countryid") final int countryid) throws Exception {
		try {
			return this.clientService.getAllStatesOfCountry(countryid);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/fetchallreduceclients" }, method = { RequestMethod.GET })
	public List fetchRefferals() throws Exception {
		try {
			return this.clientService.fetchClients();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/hierachicallistclientuser" }, method = { RequestMethod.GET })
	public ResponseEntity<?> hierachicalclientuser(@Parameter(hidden = true) Principal userP) throws Exception {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.clientService.hierachicallistclientuser(user);
	}

	@RequestMapping(value = { "/saveorupdateclient" }, method = { RequestMethod.POST })
	public ResponseEntity<?> saveClient(@RequestBody final ClientBean clientinfos,
			@Parameter(hidden = true) Principal userP) throws Exception {
		try {
			final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
			final Reelroleusers role = user.getReelrole();
			if (!Objects.isNull(role) && role.getIdtyperole().getUserroleid() == Utils.adminRoleId) {
				return this.clientService.saveClient(clientinfos, user);
			}
			return ResponseEntity.status(HttpStatus.OK)
					.body((Object) new Success(StaticValues.Userroleislimits, StaticValues.Userroleislimits_Int));
		} catch (Exception e) {
			throw e;
		}
	}

	@PostMapping(value = { "/updatelogoclient" }, consumes = { "multipart/form-data" })
	public ResponseEntity<?> updatelogoclient(
			@Parameter(content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE) // Won't
																																	// work
																																	// without
																																	// OCTET_STREAM
																																	// as
																																	// the
																																	// mediaType.
			) @RequestParam(value = "file", required = false) final MultipartFile file, final Integer clientId,
			@Parameter(hidden = true) Principal userP) throws Exception {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.clientService.updateLogoClient(clientId, file, user);
	}
}
