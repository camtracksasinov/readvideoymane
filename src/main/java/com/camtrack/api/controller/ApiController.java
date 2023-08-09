//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.camtrack.api.service.ApiService;
import com.camtrack.user.repository.UsersRepository;
import com.camtrack.user.service.UserServiceInterface;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Service
@EnableScheduling
@EnableTransactionManagement
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class ApiController {
	@Autowired
	ApiService service;
	@Autowired
	UserServiceInterface users;
	@Autowired
	UsersRepository usersR;
}
