//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class CommonUtil {
	public static String getAppURL(final HttpServletRequest request) {
		String url = null;
		try {
			final URI requestUri = new URI(request.getRequestURL().toString());
			url = new URI(requestUri.getScheme(), requestUri.getAuthority(), request.getContextPath(), null, null)
					.toString() + "/";
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return url;
	}

	public static List<WeekDaysBean> getRangeBetweenDates(final Date startdate, final Date enddate) {
		final List<WeekDaysBean> dates = new ArrayList<>();
		final Calendar calendar = new GregorianCalendar();
		calendar.setTime(startdate);
		final java.sql.Date beg = new java.sql.Date(calendar.getTime().getTime());
		calendar.setTime(enddate);
		final java.sql.Date end = new java.sql.Date(calendar.getTime().getTime());
		dates.add(new WeekDaysBean(beg.toString(), end.toString()));
		return dates;
	}

	public static List<WeekDaysBean> getWeekDaysBetweenDates(final Date startdate, final Date enddate) {
		final List<WeekDaysBean> dates = new ArrayList<>();
		final Calendar calendar = new GregorianCalendar();
		calendar.setTime(startdate);
		while (true) {
			final java.sql.Date beg = new java.sql.Date(calendar.getTime().getTime());
			calendar.add(5, 6);
			if (calendar.getTime().compareTo(enddate) > 0) {
				break;
			}
			final java.sql.Date end = new java.sql.Date(calendar.getTime().getTime());
			dates.add(new WeekDaysBean(beg.toString(), end.toString()));
			calendar.add(5, 1);
		}
		return dates;
	}

	public static List<WeekDaysBean> getWeekDaysBetweenDatesAsReverse(final Date startdate, final Date enddate) {
		final List<WeekDaysBean> dates = getWeekDaysBetweenDates(startdate, enddate);
		Collections.reverse(dates);
		return dates;
	}

	public static double roundTo3Digit(double value) {
		final int places = 3;
		final long factor = (long) Math.pow(10.0, places);
		value *= factor;
		final long tmp = Math.round(value);
		return tmp / factor;
	}

	public static double roundTo5Digit(double value) {
		final int places = 5;
		final long factor = (long) Math.pow(10.0, places);
		value *= factor;
		final long tmp = Math.round(value);
		return tmp / factor;
	}

}
