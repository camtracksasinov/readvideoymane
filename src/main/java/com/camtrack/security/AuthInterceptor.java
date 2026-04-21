// Decompiled by Procyon v0.5.30
//

package com.camtrack.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse httpResponse,
			final Object handler, final ModelAndView modelAndView) throws Exception {
		// httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT,
		// OPTIONS, DELETE");
		httpResponse.setHeader("Access-Control-Allow-Headers", "*");
		// httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

		httpResponse.setHeader("Access-Control-Max-Age", "4800");
		// httpResponse.setHeader("Access-Control-Allow-Origin", "*");
	}

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {
		return true;
	}
}
