//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.config;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class AppConstants {
	public static int ACCELERATION;
	public static int CONTINUOUSDRIVE;
	public static int DAILYDRIVE;
	public static int DAILYREST;
	public static String DEFAULT_LANGUAGE;
	public static List<String> deletemailQueue;
	public static ArrayBlockingQueue exceptionQueue;
	public static int GREEN;
	public static int HARSHBRAKE;
	public static int HTTP_ERROR_CODE;
	public static int HTTP_SUCCESS_CODE;
	public static String MAILING_ENABLED_PCNAME;
	public static List<String> mailQueue;
	public static int NIGHTDRIVE;
	public static int RED;
	public static int ROLE_CUSTOMER_ADMIN;
	public static int ROLE_SUPER_ADMIN;
	public static int SPEEDING;
	public static String TIME_ZONE;
	public static int WEEKLYDRIVE;
	public static int WEEKLYREST;
	public static int YELLOW;

	static {
		AppConstants.DEFAULT_LANGUAGE = "English";
		AppConstants.HTTP_SUCCESS_CODE = 200;
		AppConstants.HTTP_ERROR_CODE = 500;
		AppConstants.ROLE_SUPER_ADMIN = 1;
		AppConstants.ROLE_CUSTOMER_ADMIN = 2;
		AppConstants.TIME_ZONE = "UTC";
		AppConstants.SPEEDING = 1;
		AppConstants.ACCELERATION = 2;
		AppConstants.HARSHBRAKE = 3;
		AppConstants.NIGHTDRIVE = 4;
		AppConstants.CONTINUOUSDRIVE = 5;
		AppConstants.DAILYDRIVE = 6;
		AppConstants.DAILYREST = 7;
		AppConstants.WEEKLYREST = 8;
		AppConstants.WEEKLYDRIVE = 9;
		AppConstants.GREEN = 1;
		AppConstants.YELLOW = 2;
		AppConstants.RED = 3;
		AppConstants.MAILING_ENABLED_PCNAME = "34.211.201.16";
		AppConstants.exceptionQueue = new ArrayBlockingQueue(100);
	}
}
