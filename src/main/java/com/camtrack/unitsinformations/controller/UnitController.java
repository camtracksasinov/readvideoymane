package com.camtrack.unitsinformations.controller;

import java.security.Principal;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.camtrack.bean.ListAllIdUnitsBean;
import com.camtrack.entities.User;
import com.camtrack.entities.UserAccountStatus;
import com.camtrack.unitsinformations.dao.UnitDaoInterface;
import com.camtrack.user.repository.UsersRepository;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

//import springfox.documentation.annotations.ApiIgnore;

@PropertySource({ "classpath:message.properties" })
@RestController
@RefreshScope
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class UnitController {
	@Autowired
	UnitDaoInterface unitDao;
	@Autowired
	UsersRepository userR;

	@PostMapping({ "/mylastpositions" })
	public ResponseEntity<?> mylastpositions(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListAllIdUnitsBean listid) {
		final User user = this.userR.findByUserName(userP.getName()).orElse(null);
		if (Objects.isNull(user) || !user.getEnabled()) {
			return ResponseEntity.status(HttpStatus.OK).body((Object) new UserAccountStatus(true));
		}
		return this.unitDao.mylastpositions(user, listid);

	}

	@PostMapping({ "/mytrucks" })
	public ResponseEntity<?> mytrucks(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListAllIdUnitsBean listid) {

		final User user = this.userR.findByUserName(userP.getName()).orElse(null);
		if (Objects.isNull(user) || !user.getEnabled()) {
			return ResponseEntity.status(HttpStatus.OK).body((Object) new UserAccountStatus(true));
		}
		return this.unitDao.mytrucks(user, listid);
	}

	@PostMapping({ "/truckbyid" })
	public ResponseEntity<?> mytrucksbyId(Integer trkid, @Parameter(hidden = true) Principal userP) {
		final User user = this.userR.findByUserName(userP.getName()).orElse(null);
		if (Objects.isNull(user) || !user.getEnabled()) {
			return ResponseEntity.status(HttpStatus.OK).body((Object) new UserAccountStatus(true));
		}
		return this.unitDao.mytrucksbyId(user, trkid);

	}

	@PostMapping({ "/positionbyid" })
	public ResponseEntity<?> positionbyid(Integer trkid, @Parameter(hidden = true) Principal userP) {
		final User user = this.userR.findByUserName(userP.getName()).orElse(null);
		if (Objects.isNull(user) || !user.getEnabled()) {
			return ResponseEntity.status(HttpStatus.OK).body((Object) new UserAccountStatus(true));
		}
		return this.unitDao.mylastpositionsbyId(user, trkid);

	}
}
