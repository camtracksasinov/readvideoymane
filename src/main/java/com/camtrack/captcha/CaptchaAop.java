package com.camtrack.captcha;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.camtrack.config.StaticValues;

import lombok.extern.slf4j.Slf4j;

//lombok annotation
@Slf4j
//spring annotations
@Aspect
@Component
public class CaptchaAop {

	@Autowired
	ValidateCaptcha service;

	// ensures that the below code is executed before the method(s)
	// annotated with the @RequiresCaptcha annotation
	// note - either keep the annotation class in the same package as the aspect
	// class
	// or use the fully qualified name for the annotation class.
	@Around("@annotation(com.camtrack.captcha.RequiresCaptcha)")
	public Object validateCaptchaResponse(final ProceedingJoinPoint point) throws Throwable {
		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		// receives the captcha header from the request
		final String captchaResponse = request.getHeader("g-recaptcha-response");
		if (Objects.isNull(captchaResponse)) {
			throw new ForbiddenException(
					"Parameter 'g-recaptcha-response' not present in header request please resent your Request with this parameter");
		}
		// sends it to the service method for validation
		final boolean isValidCaptcha = service.validateCaptcha(captchaResponse);
		if (!isValidCaptcha) {
			// log.info("Throwing forbidden exception as the captcha is invalid.");
			throw new ForbiddenException(StaticValues.InvalidCaptcha);
		}
		// if everything is ok the response is returned
		return point.proceed();
	}
}
