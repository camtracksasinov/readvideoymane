//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.camtrack.entities.User;
import com.camtrack.reports.bean.RawstatisticsBean;
import com.camtrack.reports.bean.RewardRanking;
import com.camtrack.reports.service.RewardRankingServiceInterface;
import com.camtrack.user.repository.UsersRepository;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RefreshScope
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class RewardRankingController {
	@Autowired(required = true)
	RewardRankingServiceInterface service;
	@Autowired
	UsersRepository usersR;

	@RequestMapping(value = { "/rewardranking" }, method = { RequestMethod.POST })
	public List<RewardRanking> getRewardranking(@Parameter(hidden = true) Principal users,
			@RequestBody final RawstatisticsBean rawstat) throws Exception {
		try {
			User user = usersR.findByUserName(users.getName()).orElse(null);
			return service.getRewardranking(rawstat, user);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
