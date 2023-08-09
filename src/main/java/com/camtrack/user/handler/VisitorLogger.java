//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.handler;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.camtrack.config.HttpRequestResponseUtils;
import com.camtrack.config.StaticValues;
import com.camtrack.entities.Accessrights;
import com.camtrack.entities.Controllertomenu;
import com.camtrack.entities.Menu;
import com.camtrack.entities.Reelroleusers;
import com.camtrack.entities.User;
import com.camtrack.entities.Visitor;
import com.camtrack.rolemenu.repository.AccessRightRepository;
import com.camtrack.rolemenu.repository.MenuRepository;
import com.camtrack.user.repository.ControllertomenuRepository;
import com.camtrack.user.repository.UsersRepository;
import com.camtrack.user.repository.VisitorRepository;

@Component
public class VisitorLogger implements HandlerInterceptor {
	@Autowired
	AccessRightRepository accessR;
	@Autowired
	ControllertomenuRepository ctrlR;
	@Autowired
	MenuRepository menuR;
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

		if (!Objects.isNull(page) && (page.contains("/noauths"))) {
			// visitor.setUsername(username);
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
		}
		String username = HttpRequestResponseUtils.getLoggedInUser();
		User user = userR.findByUserName(username).orElse(null);
		if (!Objects.isNull(user)) {
			if (!Objects.isNull(user.getTrust()) && user.getTrust() && ((Objects.isNull(user.getIsadmin()))
					|| ((!Objects.isNull(user.getIsadmin())) && (!user.getIsadmin())))) {
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
			} else {
				if (!(!Objects.isNull(user.getIps())
						&& (user.getIps().equalsIgnoreCase(HttpRequestResponseUtils.getClientIpAddress())))) {
					response.getWriter().write("{ \"errors\": \"" + StaticValues.SuspissionUser + "\",\"errorcode\":"
							+ StaticValues.SuspissionUser_Int + "}");
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.setStatus(400);
					return false;
				}

				Reelroleusers roles = user.getReelrole();
				boolean trouve = false;
				Integer lengths, k = 0;
				if (!Objects.isNull(roles)) {
					if ((roles.getIds() != superadminid)
							&& (!((!Objects.isNull(user.getIsadmin())) && (user.getIsadmin())))) {
						if (!Objects.isNull(page)) {
							Accessrights access;
							List<Controllertomenu> listcont = ctrlR.findByPages(page);
							Controllertomenu cont;
							Menu menu;
							Integer order;
							if (!Objects.isNull(listcont) && !listcont.isEmpty()) {
								lengths = listcont.size();
								while ((!trouve) && (k < lengths)) {
									cont = listcont.get(k);
									menu = cont.getMenuid();
									access = accessR.findAccessRight(roles.getIds(), menu.getMenuid()).orElse(null);
									if (Objects.isNull(access)) {
										try {
											menu = menuR.findByIDs(menu.getMenuid());
											access = accessR.findAccessRight(roles.getIds(), menu.getParentmenuid())
													.orElse(null);
										} catch (Exception ex) {
											access = null;
										}
									}

									if (!Objects.isNull(access)) {
										order = cont.getOrder().intValue();
										switch (order) {
										case 4:
											if (access.getDelete() || access.getEdit() || access.getAdd()
													|| access.getView()) {
												trouve = true;
											}
											break;
										case 3:
											if (access.getAdd() || access.getEdit() || access.getView()) {
												trouve = true;
											}
											break;
										case 2:
											if (access.getAdd() || access.getView()) {
												trouve = true;
											}
											break;
										case 1:
											if (access.getView()) {
												trouve = true;
											}
											break;
										default:
											break;
										}

									}
									k++;
								}
								// response.getWriter().write("{ \"errors\": \""+StaticValues.SuspissionUser
								// +"\",\"errorcode\":"+StaticValues.SuspissionUser_Int+"}");

								if (!trouve) {
									response.getWriter().write("{ \"errors\": \"" + StaticValues.InsuffisiantRight
											+ "\",\"errorcode\":" + StaticValues.InsuffisiantRight_Int + "}");
									response.setContentType("application/json");
									response.setCharacterEncoding("UTF-8");
									response.setStatus(400);
									return false;
								}
							}
						} else {
							// WrongService
							response.getWriter().write("{ \"errors\": \"" + StaticValues.WrongService
									+ "\",\"errorcode\":" + StaticValues.WrongService_Int + "}");
							response.setContentType("application/json");
							response.setCharacterEncoding("UTF-8");
							response.setStatus(400);
							return false;
						}
					}
				} else {
					response.getWriter().write("{ \"errors\": \"" + StaticValues.SuspissionUser + "\",\"errorcode\":"
							+ StaticValues.SuspissionUser_Int + "}");
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.setStatus(400);
					return false;
				}
			}
		}
		visitor.setUsername(username);
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
	}
}
