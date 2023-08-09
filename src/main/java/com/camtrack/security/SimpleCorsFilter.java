// Decompiled by Procyon v0.5.30
// 

package com.camtrack.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(-1)
public class SimpleCorsFilter implements Filter {
	@Value("${listallalloworign}")
	private String listallalloworign;

	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {
		final HttpServletResponse response = (HttpServletResponse) res;
		final HttpServletRequest request = (HttpServletRequest) req;
		// response.setHeader("Access-Control-Allow-Origin", listallalloworign);
		// response.setHeader("Access-Control-Expose-Headers",
		// "xsrf-token, X-Total-Results, Authorization, Content-type,
		// Content-Disposition");
		/**
		 * response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS,
		 * DELETE"); response.setHeader("Access-Control-Max-Age", "3600");
		 * response.setHeader("Access-Control-Allow-Headers",Content-Length
		 * "Content-Type, Accept, X-Requested-With, remember-me, Authorization");
		 * response.setHeader("Access-Control-Expose-Headers", "xsrf-token,
		 * X-Total-Results, Authorization, Content-type, Content-Disposition");
		 */

		/**
		 **/
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(200);
		} else {
			// chain.doFilter(req, res);
			chain.doFilter(request, new HttpServletResponseWrapper((HttpServletResponse) response) {
				public void setHeader(String name, String value) {
					if (!(name.contains("X-RateLimit-Limit-") && name.contains("X-RateLimit-Remaining-")
							&& name.contains("X-RateLimit-Reset-") && name.contains("X-XSS-Protection"))) {
						super.setHeader(name, value);
					}
				}
			});
		}
	}

	public void init(final FilterConfig filterConfig) {
	}

	public void destroy() {
	}
}
