//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpRequestResponseUtils {
	private static final String[] IP_HEADER_CANDIDATES;

	static {
		IP_HEADER_CANDIDATES = new String[] { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
				"HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
				"HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };
	}

	public static String getClientIpAddress() {
		if (RequestContextHolder.getRequestAttributes() == null) {
			return "0.0.0.0";
		}
		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		for (final String header : HttpRequestResponseUtils.IP_HEADER_CANDIDATES) {
			final String ipList = request.getHeader(header);
			if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
				final String ip = ipList.split(",")[0];
				return ip;
			}
		}
		return request.getRemoteAddr();
	}

	public static String getLoggedInUser() {
		final String userJson = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				&& !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
			final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return user.getUsername();
		}
		return "";
	}

	public static String getPageQueryString() {
		if (RequestContextHolder.getRequestAttributes() == null) {
			return "";
		}
		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request.getQueryString();
	}

	public static String getRefererPage() {
		if (RequestContextHolder.getRequestAttributes() == null) {
			return "";
		}
		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		final String referer = request.getHeader("Referer");
		return (referer != null) ? referer : request.getHeader("referer");
	}

	public static String getRequestMethod() {
		if (RequestContextHolder.getRequestAttributes() == null) {
			return "";
		}
		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request.getMethod();
	}

	public static String getRequestUri() {
		if (RequestContextHolder.getRequestAttributes() == null) {
			return "";
		}
		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request.getRequestURI();
	}

	public static String getRequestUrl() {
		if (RequestContextHolder.getRequestAttributes() == null) {
			return "";
		}
		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request.getRequestURL().toString();
	}

	public static String getUserAgent() {
		if (RequestContextHolder.getRequestAttributes() == null) {
			return "";
		}
		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		final String userAgent = request.getHeader("User-Agent");
		return (userAgent != null) ? userAgent : request.getHeader("user-agent");
	}
}
