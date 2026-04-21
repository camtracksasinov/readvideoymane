//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.handler;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.camtrack.config.HttpRequestResponseUtils;
import com.camtrack.entities.Visitor;
import com.camtrack.user.repository.ControllertomenuRepository;
import com.camtrack.user.repository.UsersRepository;
import com.camtrack.user.repository.VisitorRepository;

@Component
public class VisitorLogger implements HandlerInterceptor {
	@Autowired
	ControllertomenuRepository ctrlR;
	@Value("${userrrole.superadminid}")
	private Integer superadminid;
	@Autowired
	UsersRepository userR;
	@Autowired
	VisitorRepository visitR;

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {
		final String ip = HttpRequestResponseUtils.getClientIpAddress();
		final String url = HttpRequestResponseUtils.getRequestUrl();
		final String page = HttpRequestResponseUtils.getRequestUri();
		final String refererPage = HttpRequestResponseUtils.getRefererPage();
		final String queryString = HttpRequestResponseUtils.getPageQueryString();
		final String userAgent = HttpRequestResponseUtils.getUserAgent();
		final String requestMethod = HttpRequestResponseUtils.getRequestMethod();
		final Visitor visitor = new Visitor();

		visitor.setIpuser(ip);
		visitor.setMethods(requestMethod);
		visitor.setUrl(url);
		visitor.setPages(page);
		visitor.setQuerystring(queryString);
		visitor.setRefererpage(refererPage);
		visitor.setUseragent(userAgent);
		visitor.setLoggedtime(new Date());
		visitor.setUniquevisit(true);
		this.visitR.saveAndFlush(visitor);
		return true;
}}
