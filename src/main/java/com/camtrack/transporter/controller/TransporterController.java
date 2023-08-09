//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.transporter.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camtrack.bean.ListIdBean;
import com.camtrack.config.StaticValues;
import com.camtrack.entities.Success;
import com.camtrack.entities.User;
import com.camtrack.entities.UserAccountStatus;
import com.camtrack.transporter.service.TransporterServiceInterface;
import com.camtrack.user.repository.UsersRepository;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import springfox.documentation.annotations.ApiIgnore;

@PropertySource({ "classpath:message.properties" })
@RestController
@RefreshScope
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class TransporterController {
	@Autowired
	private Environment environment;
	@Value("${roleprivacyid}")
	private Integer roleprivacyid;
	@Autowired
	TransporterServiceInterface transporterService;
	@Autowired
	UsersRepository usersR;

	@RequestMapping(value = { "/deactivatetransporter" }, method = { RequestMethod.GET })
	public ResponseEntity<?> deactivateTransporter(@RequestParam("transporterid") final Integer transporterid,
			final String language) throws Exception {
		try {
			this.transporterService.deactivateTransporter(transporterid);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.success, StaticValues.success_Int));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.OK).body((Object) new Success(StaticValues.codetransporterontexists,
					StaticValues.codetransporterontexists_Int));
		}
	}

	@RequestMapping(value = { "/fetchalldrivers" }, method = { RequestMethod.GET })
	public List fetchalldrivers(@Parameter(hidden = true) Principal userP) throws Exception {
		try {
			try {
				final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
				if (user.getReelrole().getIds() == roleprivacyid) {
					return new ArrayList<>();
				}
			} catch (Exception ex) {
				return new ArrayList<>();
			}
			return this.transporterService.fetchalldrivers();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/fetchallvehicle" }, method = { RequestMethod.GET })
	public List fetchallvehicle(@Parameter(hidden = true) Principal userP) throws Exception {
		try {
			try {
				final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
				if (user.getReelrole().getIds() == roleprivacyid) {
					return new ArrayList<>();
				}
			} catch (Exception ex) {
				return new ArrayList<>();
			}
			return this.transporterService.fetchallvehicles();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/fetchalltransporters" }, method = { RequestMethod.GET })
	public List fetchRefferals() throws Exception {
		try {
			return this.transporterService.fetchallTransporters();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/fetchdrivertranslist" }, method = { RequestMethod.POST })
	public ResponseEntity<?> fetchvehtranslist(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListIdBean listtransporterid) throws Exception {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		try {
			if (user.getReelrole().getIds() == roleprivacyid) {
				return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>());
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>());
		}
		if (Objects.isNull(user) || !user.getEnabled()) {
			return ResponseEntity.status(HttpStatus.OK).body((Object) new UserAccountStatus(true));
		}
		return this.transporterService.FetchDriverList(user,
				Arrays.stream(listtransporterid.getListids()).collect(Collectors.toList()));
	}
}
