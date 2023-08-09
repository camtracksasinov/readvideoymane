//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.controller;

import java.security.Principal;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camtrack.bean.CreateParameterconfigInfos;
import com.camtrack.bean.ParameterconfigInfos;
import com.camtrack.configuartion.bean.ConfigurationBean;
import com.camtrack.configuartion.repository.FixrecoveryparamsRepository;
import com.camtrack.configuartion.repository.FixvisualparamsRepository;
import com.camtrack.configuartion.service.ConfigurationServiceInterface;
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
public class ConfigurationController {
	@Autowired
	ConfigurationServiceInterface configService;
	@Autowired
	FixrecoveryparamsRepository fixrecovR;
	@Autowired
	FixvisualparamsRepository fixvisualR;
	@Autowired
	UsersRepository usersR;

	@RequestMapping(value = { "/allfixrecoveryparam" }, method = { RequestMethod.GET })
	public ResponseEntity<?> allfixrecoveryparam(@Parameter(hidden = true) Principal userP) throws Exception {
		try {
			return ResponseEntity.status(HttpStatus.OK).body((Object) this.fixrecovR.findAll());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/allfixvisualparam" }, method = { RequestMethod.GET })
	public ResponseEntity<?> allfixvisualparam(@Parameter(hidden = true) Principal userP) throws Exception {
		try {
			return ResponseEntity.status(HttpStatus.OK).body((Object) this.fixvisualR.findAll());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/createorupdateconfig" }, method = { RequestMethod.POST })
	public ResponseEntity<?> createconfig(@RequestBody final CreateParameterconfigInfos config,
			@Parameter(hidden = true) Principal userP) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.configService.createparameterconfig(config, user);
	}

	@RequestMapping(value = { "/deactivateconfig" }, method = { RequestMethod.GET })
	public ResponseEntity<?> deactivateConfig(@RequestParam("paramconfigid") final Long paramconfigid,
			@Parameter(hidden = true) Principal userP) throws Exception {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		final Integer result = this.configService.deactivateConfig(paramconfigid, user);
		return ResponseEntity.status(HttpStatus.OK).body((Object) new Success("Status", result));
	}

	@RequestMapping(value = { "/fetchlistparams" }, method = { RequestMethod.GET })
	public ResponseEntity<?> fetchlistparams(@Parameter(hidden = true) Principal userP) throws Exception {
		try {
			final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
			return this.configService.fetchlistparams(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/fetchscpparambyid" }, method = { RequestMethod.GET })
	public ResponseEntity<?> getParameterSettings(@RequestParam("customerid") Integer customerid,
			@RequestParam("affid") Integer affid, @RequestParam("transid") Integer transid,
			@RequestParam("parametertypeid") Integer parametertypeid) throws Exception {
		try {
			if (Objects.isNull(customerid)) {
				customerid = 0;
			}

			if (Objects.isNull(affid)) {
				affid = 0;
			}

			if (Objects.isNull(transid)) {
				transid = 0;
			}

			if (Objects.isNull(parametertypeid)) {
				parametertypeid = 0;
			}
			final Map paramDetails = this.configService.getParameterById(customerid, affid, transid, parametertypeid);
			return ResponseEntity.status(HttpStatus.OK).body((Object) paramDetails);
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(value = { "/fetchlistparambyid" }, method = { RequestMethod.POST })
	public ResponseEntity<?> getParameterSettings(
			@RequestParam(value = "customerid", required = false) final Integer customerid,
			@RequestParam(value = "affid", required = false) final Integer affid,
			@RequestParam(value = "transporterid", required = false) final Integer transporterid,
			@RequestParam(value = "vehicleid", required = false) final Integer vehicleid,
			@RequestParam(value = "driverid", required = false) final Integer driverid) throws Exception {
		return this.configService.getListParameterById(customerid, affid, transporterid, vehicleid, driverid);
	}

	@RequestMapping(value = { "/listlevelalert" }, method = { RequestMethod.POST })
	public ResponseEntity<?> listlevelalert(@Parameter(hidden = true) Principal userP) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.configService.listlevelalert(user);
	}

	@RequestMapping(value = { "/savescpconfig" }, method = { RequestMethod.POST })
	public ResponseEntity<?> saveConfig(@RequestBody final ConfigurationBean config,
			@Parameter(hidden = true) Principal userP) throws Exception {
		try {
			final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
			return this.configService.saveConfiguration(config, user);
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(value = { "/updateconfig" }, method = { RequestMethod.POST })
	public ResponseEntity<?> updateConfig(@RequestBody final ParameterconfigInfos config,
			@Parameter(hidden = true) Principal userP) {
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.configService.updateparameterconfig(config, user);
	}
}
