//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.security;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.WebApplicationInitializer;

public class WebAppInitializer implements WebApplicationInitializer {
	@Override
	public void onStartup(final ServletContext servletContext) throws ServletException {
		servletContext.addListener(HttpSessionEventPublisher.class);
	}
}
