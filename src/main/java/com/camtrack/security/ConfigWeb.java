// Decompiled by Procyon v0.5.30
// 

package com.camtrack.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.camtrack.user.handler.VisitorLogger;

import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;

@Configuration
@EnableWebMvc
public class ConfigWeb implements WebMvcConfigurer {
	private static final String[] CLASSPATH_RESOURCE_LOCATIONS;

	@Autowired
	VisitorLogger visitorLogger;
	@Autowired
	AuthInterceptor intercep;
	@Value("${listallalloworign}")
	String listallalloworign;
	@Value("${upload.path}")
	String uploadpathfile;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		String[] ALLOW_ORIGIN = listallalloworign.split(";");
		registry.addMapping("/**").allowedOrigins(ALLOW_ORIGIN).allowCredentials(true).allowedMethods("*").maxAge(3600);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(new String[] { "/img/**" })
				.addResourceLocations(ConfigWeb.CLASSPATH_RESOURCE_LOCATIONS).setCachePeriod(3000);
	}

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		registry.addInterceptor((HandlerInterceptor) this.visitorLogger);
		registry.addInterceptor((HandlerInterceptor) this.intercep);
	}

	@Bean
	public SecretGenerator secretGenerator() {
		return new DefaultSecretGenerator(64);
	}

	@Bean
	public QrGenerator qrGenerator() {
		return new ZxingPngQrGenerator();
	}

	@Bean
	public CodeVerifier codeVerifier() {
		return new DefaultCodeVerifier(new DefaultCodeGenerator(), new SystemTimeProvider());
	}

	static {
		CLASSPATH_RESOURCE_LOCATIONS = new String[] { "classpath:/META-INF/resources/", "classpath:/resources/",
				"classpath:/static/", "classpath:/public/", "classpath:/custom/",
				"file:/usr/local/ymanegateway/uploadfiles/" };
	}

}
