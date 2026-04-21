// Decompiled by Procyon v0.5.30
//

package com.camtrack.config;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.MessagingException;

import org.json.JSONObject;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.camtrack.bean.AutomaticreportBean;
import com.camtrack.bean.CreateUsers;
import com.camtrack.bean.ListAllIdWithtypeexceptionBean;
import com.camtrack.bean.ParameterconfigBean;
import com.camtrack.bean.ParameterconfigInfos2;
import com.camtrack.bean.RoleBean;
import com.camtrack.bean.VehicleBean;
import com.camtrack.config.passwordmanagement.Parameterconfigdefaultvalue;
import com.camtrack.entities.Accessrights;
import com.camtrack.entities.Allalertlevel;
import com.camtrack.entities.Customer;
import com.camtrack.entities.Customeraffiliate;
import com.camtrack.entities.Drivertrips;
import com.camtrack.entities.Exceptionlevel;
import com.camtrack.entities.Frequences;
import com.camtrack.entities.InfosLevel;
import com.camtrack.entities.Invalidateexception;
import com.camtrack.entities.Language;
import com.camtrack.entities.Leveldetails;
import com.camtrack.entities.Menu;
import com.camtrack.entities.NbrtypeExceptions;
import com.camtrack.entities.Parameterconfig;
import com.camtrack.entities.Parametertype;
import com.camtrack.entities.Reelroleusers;
import com.camtrack.entities.Resumexceptionaffiliate;
import com.camtrack.entities.Resumexceptionclient;
import com.camtrack.entities.Resumexceptiondriver;
import com.camtrack.entities.Resumexceptiontransporter;
import com.camtrack.entities.Resumexceptionvehicle;
import com.camtrack.entities.Status;
import com.camtrack.entities.SummaryException;
import com.camtrack.entities.Timerange;
import com.camtrack.entities.Transporter;
import com.camtrack.entities.User;
import com.camtrack.entities.Userrole;
import com.camtrack.entities.Vehicle;
import com.camtrack.user.bean.UserBean;
import com.camtrack.user.bean.UserInfos;
import com.camtrack.user.repository.AllalertlevelRepository;
import com.camtrack.user.repository.CustomerRepository;
import com.camtrack.user.repository.ParameterconfigRepository;
import com.camtrack.user.repository.UserightsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
	public static String ActiveFleet;
	public static Integer activeFleetParameterid;
	public static String ActiveFleetShorname;
	public static Integer adminRoleId;
	public static String Affiliatentities;
	public static Integer affiliateRoleId;
	public static Integer alarmlevelid;
	public static Integer alertlevelid;
	public static String boldbaliseinit;
	public static String boldbalisend;
	public static String ClientEntities;
	public static Integer clientRoleId;
	public static Integer coderawstatistics;
	public static Integer coderewardranking;
	public static String ContiniousDrive;
	public static String ContiniousDriveShorname;
	public static String DailyDrive;
	public static String DailyDriveShorname;
	public static String DailyRest;
	public static String DailyRestShorname;
	public static String DailyWordk;
	public static String DailyWordkShorname;
	public static Integer defaultlimitimeforgeofence;
	public static String defaultLinkUniqueID;
	public static Double defaultmindistance;
	static Pattern DOUBLE_PATTERN;
	public static String Fatigue;
	public static String FatigueShorname;
	public static String HarshAcceleration;
	public static String HarshAccelerationShorname;
	public static String HarshBracking;
	public static String HarshBrackingShorname;
	public static String InsufficientTripDuration;
	public static String InsufficientTripDurationShorname;
	public static Integer insuffisiantTripDuration;
	public static Integer IntegerActiveFleet;
	public static Integer IntegerContiniousDrive;
	public static Integer IntegerDailyDrive;
	public static Integer IntegerDailyRest;
	public static Integer IntegerDailyWordk;
	public static Integer IntegerFatigue;
	public static Integer IntegerHarshAcceleration;
	public static Integer IntegerHarshBracking;
	public static Integer IntegerInsufficientTripDuration;
	public static Integer IntegerNightDriven;
	public static Integer IntegerNoDriver;
	public static Integer IntegerNoID;
	public static Integer IntegerPhoneDistraction;
	public static Integer IntegerSealBelt;
	public static Integer IntegerSmoking;
	public static Integer IntegerSpeeding;
	public static Integer IntegerWeeklyDrive;
	public static Integer IntegerWeeklyRest;
	public static Integer IntegerWeeklyWordk;
	public static String LogParameter;
	public static Integer menuUserId;
	public static Double mindoubledistance;
	public static String NewinitLine;
	public static String NightDriven;
	public static String NightDrivenShorname;
	public static String NoDriver;
	public static String NoDriverShorname;
	public static String NoID;
	public static String NoIDShorname;
	private static Pattern PASSWORD_PATTERN;
	public static String PhoneDistraction;
	public static String PhoneDistractionShorname;
	public static String projectclientid;
	public static Integer recordlevelid;
	public static String SealBelt;
	public static String SealBeltShorname;
	public static String Smoking;
	public static String SmokingShorname;
	public static String Speeding;
	public static String SpeedingShorname;
	public static Double tankRefill;
	public static String Transporterntities;
	public static Integer transporterRoleId;
	public static Integer typeElementsAffiliateid;
	public static Integer typeElementsClientsid;
	public static Integer typeElementsTransporterid;
	public static Integer typeElementsVehiclesid;
	public static String UserIDSTRINGToREPLACE;
	public static String WeeklyDrive;
	public static String WeeklyDriveShorname;
	public static String WeeklyRest;
	public static String WeeklyRestShorname;
	public static String WeeklyWordk;
	public static String WeeklyWordkShorname;
	public static Integer limitRecordException;

	static {
		Utils.limitRecordException = 500;
		Utils.coderawstatistics = 1016;
		Utils.coderewardranking = 1017;
		Utils.LogParameter = "";
		Utils.boldbaliseinit = "<b>";
		Utils.boldbalisend = "</b>";
		Utils.NewinitLine = "<br/>";
		Utils.UserIDSTRINGToREPLACE = "SERID";
		Utils.recordlevelid = 1;
		Utils.alertlevelid = 2;
		Utils.alarmlevelid = 3;
		Utils.SealBeltShorname = "SD";
		Utils.DailyDriveShorname = "DD";
		Utils.DailyRestShorname = "DR";
		Utils.WeeklyDriveShorname = "WD";
		Utils.WeeklyRestShorname = "WR";
		Utils.WeeklyWordkShorname = "WW";
		Utils.DailyWordkShorname = "DW";
		Utils.SpeedingShorname = "SP";
		Utils.HarshAccelerationShorname = "HA";
		Utils.HarshBrackingShorname = "HB";
		Utils.NightDrivenShorname = "ND";
		Utils.ContiniousDriveShorname = "CD";
		Utils.PhoneDistractionShorname = "PD";
		Utils.FatigueShorname = "FT";
		Utils.ActiveFleetShorname = "AF";
		Utils.NoDriverShorname = "NoD";
		Utils.NoIDShorname = "NoID";
		Utils.InsufficientTripDurationShorname = "ITD";
		Utils.SmokingShorname = "SK";
		Utils.IntegerSealBelt = 11;
		Utils.IntegerDailyDrive = 6;
		Utils.IntegerDailyRest = 7;
		Utils.IntegerWeeklyDrive = 9;
		Utils.IntegerWeeklyRest = 8;
		Utils.IntegerWeeklyWordk = 17;
		Utils.IntegerDailyWordk = 16;
		Utils.IntegerSpeeding = 1;
		Utils.IntegerHarshAcceleration = 2;
		Utils.IntegerHarshBracking = 3;
		Utils.IntegerNightDriven = 4;
		Utils.IntegerContiniousDrive = 5;
		Utils.IntegerPhoneDistraction = 10;
		Utils.IntegerFatigue = 13;
		Utils.IntegerActiveFleet = 18;
		Utils.IntegerNoDriver = 19;
		Utils.IntegerNoID = 12;
		Utils.IntegerInsufficientTripDuration = 14;
		Utils.IntegerSmoking = 15;
		Utils.SealBelt = "SealBelt";
		Utils.DailyDrive = "DailyDrive";
		Utils.DailyRest = "DailyRest";
		Utils.WeeklyDrive = "WeeklyDrive";
		Utils.WeeklyRest = "WeeklyRest";
		Utils.WeeklyWordk = "WeeklyWordk";
		Utils.DailyWordk = "DailyWordk";
		Utils.Speeding = "Speeding";
		Utils.HarshAcceleration = "HarshAcceleration";
		Utils.HarshBracking = "HarshBracking";
		Utils.NightDriven = "NightDriven";
		Utils.ContiniousDrive = "ContiniousDrive";
		Utils.PhoneDistraction = "PhoneDistraction";
		Utils.Fatigue = "Fatigue";
		Utils.ActiveFleet = "ActiveFleet";
		Utils.NoDriver = "NoDriver";
		Utils.NoID = "NoID";
		Utils.InsufficientTripDuration = "InsufficientTripDuration";
		Utils.Smoking = "Smoking";
		Utils.ClientEntities = "CLIENT";
		Utils.Affiliatentities = "AFFILIATE";
		Utils.Transporterntities = "TRANSPORTER";
		Utils.typeElementsClientsid = 1;
		Utils.typeElementsAffiliateid = 2;
		Utils.typeElementsTransporterid = 3;
		Utils.typeElementsVehiclesid = 4;
		Utils.menuUserId = 31;
		Utils.adminRoleId = 1;
		Utils.clientRoleId = 2;
		Utils.affiliateRoleId = 3;
		Utils.transporterRoleId = 4;
		Utils.activeFleetParameterid = 18;
		Utils.insuffisiantTripDuration = 10;
		Utils.defaultlimitimeforgeofence = 10;
		Utils.defaultmindistance = 1.0;
		Utils.mindoubledistance = 1.0;
		Utils.projectclientid = "ymaneprod";
		Utils.defaultLinkUniqueID = "10";
		Utils.DOUBLE_PATTERN = Pattern.compile(
				"[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*");
		Utils.PASSWORD_PATTERN = Pattern
				.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[;|,?\\\\@#$%^&+!=])(?=.{8,}).*$");// ^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+!=])(?=.{8,}).*$
	}

	public static String DeleteLog(String username, final String objectsname) {
		String logs;

		logs = " Delete User <b>" + objectsname + "</b> by " + username + " Date <b>"
				+ DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss") + "</b>";

		return logs;
	}

	public static double round(Double value, int places) {
		if ((places < 0) || (Objects.isNull(value))) {
			return 0.0;
		}
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static List<MonthlyRecord> monhtlyfromperiod(int startYear0, int endYear0, int startMonth0, int endMonth0) {
		List<MonthlyRecord> rsult = new ArrayList<>();
		MonthlyRecord record;
		if ((endYear0 >= startMonth0) && (startMonth0 >= 1) & (startMonth0 <= 12)
				&& (endMonth0 >= 1) & (endMonth0 <= 12) && (startMonth0 <= endMonth0)) {
			int k, j;
			if (startYear0 == endYear0) {
				for (k = startMonth0; k < (endMonth0 + 1); k++) {
					record = new MonthlyRecord();
					record.setMonth(k);
					record.setYear(endYear0);
					rsult.add(record);
				}
			} else {
				for (k = startMonth0; k < 13; k++) {
					record = new MonthlyRecord();
					record.setMonth(k);
					record.setYear(startYear0);
					rsult.add(record);
				}
				k = startYear0 + 1;
				while (k < endYear0) {
					for (j = 1; j < 13; k++) {
						record = new MonthlyRecord();
						record.setMonth(j);
						record.setYear(k);
						rsult.add(record);
					}
					k++;
				}
				for (j = 1; j < (endMonth0 + 1); j++) {
					record = new MonthlyRecord();
					record.setMonth(j);
					record.setYear(endYear0);
					rsult.add(record);
				}
			}

			return rsult;
		} else {
			return new ArrayList<>();
		}
	}

	public static Date AddHoursToDate(Date date, Integer nbrHours) {
		Calendar maDate = Calendar.getInstance();
		maDate.setTime(date);
		// Calendar maDate = Calendar.getInstance();
		// maDate.setTime(date);
		maDate.add(Calendar.HOUR_OF_DAY, nbrHours);
		// maDate.add(5, -7);
		return maDate.getTime();
	}

	public static ListAllIdWithtypeexceptionBean autotoreporttolist(final AutomaticreportBean report) {
		final ListAllIdWithtypeexceptionBean result = new ListAllIdWithtypeexceptionBean();
		result.setAlarm(report.getAlarm());
		result.setAlert(report.getAlert());
		result.setRecord(report.getRecord());
		result.setListaffiliateids(report.getListaffiliateids());
		result.setListclientids(report.getListclientids());
		result.setListdriverids(report.getListdriverids());
		result.setListidtypeexception(report.getListidtypeexception());
		result.setListtransporterids(report.getListtransporterids());
		report.setListvehicleids(report.getListvehicleids());
		return result;
	}

	public static String BeginEmailBody(Integer hour) {
		return "Reset Password , please process thought this link (Expire in " + hour + " h): <br> ";
	}

	public static String BigDecimalToDateString(final BigDecimal data) {
		String result = "";
		if (Objects.isNull(data)) {
			return null;
		}
		Double value = data.doubleValue();
		Integer Heure;
		Integer intvalue = Heure = value.intValue();
		value = (value - value.intValue()) * 60.0;
		Integer minute;
		intvalue = (minute = value.intValue());
		value = (value - value.intValue()) * 60.0;
		Integer seconde;
		intvalue = (seconde = value.intValue());
		if (seconde > 57) {
			seconde = 0;
			++minute;
		}
		if (minute > 57) {
			minute = 0;
			++Heure;
		}
		result = Heure + "";
		if (Heure < 10) {
			result = "0" + Heure;
		} else {
			result = "" + Heure;
		}
		if (minute < 10) {
			result = result + ":0" + minute;
		} else {
			result = result + ":" + minute;
		}
		if (seconde < 10) {
			result = result + ":0" + seconde;
		} else {
			result = result + ":" + seconde;
		}
		return result;
	}

	public static String BigDecimalToTime(BigDecimal decimal) {
		String hour = "00";
		String min = "00";
		String seconds = "00";
		if (decimal.compareTo(BigDecimal.ZERO) > 0) {
			final Integer ints = decimal.intValue();
			if (ints > 9) {
				hour = "" + ints;
			} else {
				hour = "0" + ints;
			}
			decimal = decimal.subtract(new BigDecimal(ints));
		}
		decimal = decimal.multiply(new BigDecimal(60));
		if (decimal.compareTo(BigDecimal.ZERO) > 0) {
			final Integer ints = decimal.intValue();
			if (ints > 9) {
				min = "" + ints;
			} else {
				min = "0" + ints;
			}
			decimal = decimal.subtract(new BigDecimal(ints));
		}
		decimal = decimal.multiply(new BigDecimal(60));
		if (decimal.compareTo(BigDecimal.ZERO) > 0) {
			final Integer ints = decimal.intValue();
			if (ints > 9) {
				seconds = "" + ints;
			} else {
				seconds = "0" + ints;
			}
		}
		return hour + ":" + min + ":" + seconds;
	}

	public static Date[] calculperioddate(final Date date, final Timerange range, final Frequences freq) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		final Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		Date startdate = null;
		Date enddate = null;
		final Integer code = range.getCodes();
		final String freqs = freq.getUniqueid();
		Date nextdate = new Date();
		if (code == 1001) {
			cal2.add(5, 1);
			nextdate = cal2.getTime();
			if (freqs.equalsIgnoreCase("1001")) {
				cal.add(5, -1);
				startdate = cal.getTime();
				enddate = cal.getTime();
			} else if (freqs.equalsIgnoreCase("1002")) {
				startdate = getPreviousMondayOfDate(date);
				cal = Calendar.getInstance();
				cal.setTime(startdate);
				cal.add(5, 6);
				enddate = cal.getTime();
			} else if (freqs.equalsIgnoreCase("1003")) {
				cal.setTime(getPreviousMonthDate(date));
				cal.set(5, cal.getActualMinimum(5));
				startdate = cal.getTime();
				cal.set(5, cal.getActualMaximum(5));
				enddate = cal.getTime();
			}
		} else if (code == 1002) {
			cal2.add(5, 7);
			nextdate = cal2.getTime();
			cal = Calendar.getInstance();
			cal.setTime(date);
			if (freqs.equalsIgnoreCase("1001")) {
				startdate = cal.getTime();
				enddate = cal.getTime();
			} else if (freqs.equalsIgnoreCase("1002")) {
				cal.set(7, 2);
				startdate = cal.getTime();
				cal.set(7, 1);
				enddate = cal.getTime();
			} else if (freqs.equalsIgnoreCase("1003")) {
				cal.set(5, cal.getActualMinimum(5));
				startdate = cal.getTime();
				cal.set(5, cal.getActualMaximum(5));
				enddate = cal.getTime();
			}
		} else if (code == 1003) {
			cal2.add(2, 1);
			nextdate = cal2.getTime();
			if (freqs.equalsIgnoreCase("1001")) {
				cal.add(5, -1);
				startdate = cal.getTime();
				enddate = date;
			} else if (freqs.equalsIgnoreCase("1002")) {
				startdate = getPreviousMondayOfDate(date);
				cal.setTime(date);
				cal.set(7, 1);
				enddate = cal.getTime();
				enddate = date;
			} else if (freqs.equalsIgnoreCase("1003")) {
				cal.setTime(getPreviousMonthDate(date));
				cal.set(5, cal.getActualMinimum(5));
				startdate = cal.getTime();
				cal.setTime(date);
				cal.set(5, cal.getActualMaximum(5));
				enddate = cal.getTime();
			}
		}
		return new Date[] { startdate, enddate, nextdate };
	}

	public static String capitalize(final String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static BigDecimal castBigDecimalObject(final Object value) {
		BigDecimal ret = null;
		if (value != null) {
			if (value instanceof BigDecimal) {
				ret = (BigDecimal) value;
			} else if (value instanceof String) {
				ret = new BigDecimal((String) value);
			} else if (value instanceof BigInteger) {
				ret = new BigDecimal((BigInteger) value);
			} else {
				if (!(value instanceof Number)) {
					throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass()
							+ " into a BigDecimal.");
				}
				ret = new BigDecimal(((Number) value).doubleValue());
			}
		}
		return ret;
	}

	public static Double castDoubleObject(final Object object) {
		Double result = 0.0;
		try {
			if (object instanceof Double) {
				result = (double) object;
			} else if (object instanceof Integer) {
				result = ((Integer) object).doubleValue();
			} else if (object instanceof BigDecimal) {
				result = ((BigDecimal) object).doubleValue();
			} else if (object instanceof BigInteger) {
				result = ((BigInteger) object).doubleValue();
			} else if (object instanceof Long) {
				result = ((Long) object).doubleValue();
			} else if (object instanceof String) {
				result = Double.valueOf((String) object);
			}
		} catch (Exception e) {
			System.out.println("============= cannot cast");
		}
		return result;
	}

	public static Integer castIntegerObject(final Object object) {
		Integer result = 0;
		try {
			if (object instanceof Double) {
				result = ((Double) object).intValue();
			} else if (object instanceof Integer) {
				result = (Integer) object;
			} else if (object instanceof BigInteger) {
				result = ((BigInteger) object).intValue();
			} else if (object instanceof BigDecimal) {
				result = ((BigDecimal) object).intValue();
			} else if (object instanceof Long) {
				result = ((Long) object).intValue();
			} else if (object instanceof String) {
				result = Integer.valueOf((String) object);
			}
		} catch (Exception e) {
			System.out.println("============= cannot cast");
		}
		return result;
	}

	public static Long castLongObject(final Object object) {
		Long result = 0L;
		try {
			if (object instanceof Long) {
				result = (Long) object;
			} else if (object instanceof Double) {
				result = ((Double) object).longValue();
			} else if (object instanceof BigDecimal) {
				result = ((BigDecimal) object).longValue();
			} else if (object instanceof Integer) {
				result = (long) object;
			} else if (object instanceof BigInteger) {
				result = ((BigInteger) object).longValue();
			} else if (object instanceof String) {
				result = Long.valueOf((String) object);
			}
		} catch (Exception e) {
			System.out.println("============= cannot cast");
		}
		return result;
	}

	/**
	 * public static boolean chechpassword(String password) { return true;
	 *
	 * }
	 */

	public static boolean chechpassword(String password) {
		try {
			return PASSWORD_PATTERN.matcher(password).matches();
			// Boolean res = result.find();
			// return res;
		} catch (Exception ex) {
			return false;
		}

	}

	public static Short ClientTypeconfig() {
		return Short.valueOf("6");
	}

	public static Integer codeEntitiesBase() {
		return 1;
	}

	public static List<ParameterconfigInfos2> converttoParameterconfigBean(final List<Parameterconfig> listparam) {
		final List<ParameterconfigInfos2> result = new ArrayList<>();
		for (final Parameterconfig param : listparam) {
			result.add(fromConfigToBeans(param));
		}
		return result;
	}

	public static int countnumberligne(String str) {
		if (Objects.isNull(str) || str.isEmpty()) {
			return 0;
		}
		str = str.replace("\r" + Utils.NewinitLine, Utils.NewinitLine);
		str = str.replace("\r", Utils.NewinitLine);
		return str.length() - str.replace(Utils.NewinitLine, "").length() + 1;
	}

	public static String createJsonFromException(final List<SummaryException> listexception) {
		return createJsonFromExceptionOrigin(listexception).toString();
	}

	public static JSONObject createJsonFromExceptionOrigin(final List<SummaryException> listexception) {
		final String message = "{}";
		final JSONObject jsonfinal = new JSONObject();
		int k = 0;
		String oldexception = null;
		String newexception = null;
		JSONObject item = new JSONObject();
		for (final SummaryException summaryException : listexception) {
			if (k == 0) {
				newexception = summaryException.getException();
				if (!Objects.isNull(newexception)) {
					oldexception = newexception;
					final String transporter = summaryException.getTransporter();
					final Long value = summaryException.getNumbers();
					if (!Objects.isNull(transporter)) {
						item.put(transporter, value);
					}
				} else {
					k = -1;
				}
			} else {
				newexception = summaryException.getException();
				if (!Objects.isNull(newexception)) {
					if (oldexception.trim().equalsIgnoreCase(newexception.trim())) {
						final String transporter = summaryException.getTransporter();
						final Long value = summaryException.getNumbers();
						if (!Objects.isNull(transporter)) {
							item.put(transporter, value);
						}
					} else {
						jsonfinal.put(oldexception, item);
						item = new JSONObject();
						final String transporter = summaryException.getTransporter();
						final Long value = summaryException.getNumbers();
						if (!Objects.isNull(transporter)) {
							item.put(transporter, value);
						}
						oldexception = newexception;
					}
				}
			}
			++k;
		}
		if (!Objects.isNull(oldexception)) {
			jsonfinal.put(oldexception, item);
		}
		return jsonfinal;
	}

	public static String createnewlogs(final String log, final String data, final String params) {
		String result = log + " " + Utils.NewinitLine + " addressline1 " + Utils.boldbaliseinit + data
				+ Utils.boldbalisend;
		result = result.replaceAll("addressline1", capitalize(params));
		return result;
	}

	public static Date currentDate() {
		return new Date();
	}

	public static String dateformat() {
		return "yyyy-MM-dd HH:mm:ss";
	}

	public static String DateFormat(final Date date, final String format) {
		if (Objects.isNull(date)) {
			return null;
		}
		final SimpleDateFormat format2 = new SimpleDateFormat(format);
		return format2.format(date);
	}

	public static BigDecimal DateFormatBigDecimal(final Date date, final String format) {
		if (Objects.isNull(date)) {
			return null;
		}
		final SimpleDateFormat format2 = new SimpleDateFormat(format);
		final String value = format2.format(date);
		final String[] results = value.split(":");
		Double result = castDoubleObject(Double.valueOf(results[0]));
		result += Double.valueOf(castDoubleObject(Double.valueOf(results[1]) / 60.0));
		result += Double.valueOf(castDoubleObject(Double.valueOf(results[2]) / 3600.0));
		return BigDecimal.valueOf(result);
	}

	public static String decodingAES(String value, String key) {
		try {
			byte[] all = key.getBytes(StandardCharsets.UTF_8);// key.getBytes("UTF8");
			SecretKey myDesKey = new SecretKeySpec(all, 0, all.length, "ARCFOUR");

			// Creating object of Cipher
			Cipher desCipher;
			desCipher = Cipher.getInstance("ARCFOUR");
			desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
			byte[] textDecrypted = desCipher.doFinal(value.getBytes(StandardCharsets.UTF_8));

			// Converting decrypted byte array to string
			return new String(textDecrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static Integer defaulttimeZone() {
		return 1;
	}

	public static Short EmailTypeconfig() {
		return Short.valueOf("1");
	}

	public static String encodeBcriptPassword(final String password) {
		return new BCryptPasswordEncoder(4).encode(password);
	}

	public static String[] encodingDES(String values) {
		try {
			// Generating objects of KeyGenerator &
			// SecretKey
			KeyGenerator keygenerator = KeyGenerator.getInstance("ARCFOUR");
			SecretKey myDesKey = keygenerator.generateKey();

			// Creating object of Cipher
			Cipher desCipher;
			desCipher = Cipher.getInstance("ARCFOUR");

			// Creating byte array to store string
			byte[] text = values.getBytes("UTF8");

			// Encrypting text
			desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
			byte[] textEncrypted = desCipher.doFinal(text);

			// Converting encrypted byte array to string
			// String s = new String(myDesKey.getEncoded(), StandardCharsets.UTF_8);
			// String s = new String(textEncrypted, StandardCharsets.UTF_8);
			byte[] textEncrypted2 = myDesKey.getEncoded();
			return new String[] { new String(textEncrypted, StandardCharsets.UTF_8),
					new String(textEncrypted2, StandardCharsets.UTF_8) };
		} catch (Exception ex) {
			return null;
		}
	}

	public static String EntetesLog(final Boolean createorupdate, final String username, final String objectsname) {
		String logs;
		if (createorupdate) {
			logs = " Create New <b>" + objectsname + "</b> by " + username + " Date <b>"
					+ DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss") + "</b>";
		} else {
			logs = " Update <b>" + objectsname + "</b>  by " + username + " Date <b>"
					+ DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss") + "</b>";
		}
		return logs;
	}

	public static Invalidateexception ExceptionToInvalidate(final com.camtrack.entities.Exception invalid,
			final User user) {
		final Invalidateexception exception = new Invalidateexception();
		Invalidateexception.init(user);
		exception.setAffiliateid(invalid.getAffiliateid());
		exception.setDistanceunderexception(invalid.getDistanceunderexception());
		exception.setDriverid(invalid.getDriverid());
		exception.setEnddatetime(invalid.getEnddatetime());
		exception.setEndgps(invalid.getEndgps());
		exception.setEvidence(invalid.getEvidence());
		exception.setExceptionday(invalid.getExceptionday());
		exception.setExceptionid(invalid.getExceptionid());
		exception.setExceptiontype(invalid.getExceptiontype());
		exception.setGdistanceunderexception(invalid.getGdistanceunderexception());
		exception.setGenddatetime(invalid.getGenddatetime());
		exception.setGendgps(invalid.getGendgps());
		exception.setGinvaliddata(invalid.getGinvaliddata());
		exception.setGlevel(invalid.getGlevel());
		exception.setGmaxbreak(invalid.getGmaxbreak());
		exception.setGmaxval(invalid.getGmaxval());
		exception.setGnumberofbreak(invalid.getGnumberofbreak());
		exception.setGpreventivedatetime(invalid.getGpreventivedatetime());
		exception.setGpreventivethreshold(invalid.getGpreventivethreshold());
		exception.setGrequiredbreak(invalid.getGrequiredbreak());
		exception.setGstartdatetime(invalid.getGstartdatetime());
		exception.setGstartgps(invalid.getGstartgps());
		exception.setGstatus(invalid.getGstatus());
		exception.setGthreshold(invalid.getGthreshold());
		exception.setGtimeexceeded(invalid.getGtimeexceeded());
		exception.setGtotaldistance(invalid.getGtotaldistance());
		exception.setGtotalduration(invalid.getGtotalduration());
		exception.setGvmax(invalid.getGvmax());
		exception.setInvaliddata(invalid.getInvaliddata());
		exception.setLevel(invalid.getLevel());
		exception.setMaxbreak(invalid.getMaxbreak());
		exception.setMaxvalue(invalid.getMaxvalue());
		exception.setMergetreated(invalid.getMergetreated());
		exception.setNumberofbreak(invalid.getNumberofbreak());
		exception.setPreventivedatetime(invalid.getPreventivedatetime());
		exception.setPreventivethreshold(invalid.getPreventivethreshold());
		exception.setRequiredbreak(invalid.getRequiredbreak());
		exception.setSpeedid(invalid.getSpeedid());
		exception.setStartdatetime(invalid.getStartdatetime());
		exception.setStartgps(invalid.getStartgps());
		exception.setStatus(invalid.getStatus());
		exception.setThreshold(invalid.getThreshold());
		exception.setTimeexceeded(invalid.getTimeexceeded());
		exception.setTotaldistance(invalid.getTotaldistance());
		exception.setTotalduration(invalid.getTotalduration());
		exception.setTransporterid(invalid.getTransporterid());
		exception.setTreated(invalid.getTreated());
		exception.setUnitid(invalid.getUnitid());
		exception.setVehicleid(invalid.getVehicleid());
		exception.setVmax(invalid.getVmax());
		return exception;
	}

	public static Short ExceptionTypeconfig() {
		return Short.valueOf("10");
	}

	public static String frequency() {
		return "Frequency";
	}

	public static ParameterconfigBean fromConfigToBean(final Parameterconfig config) {
		final Parametertype type = config.getParametertypeid();
		final String paramtypename = type.getShortname();
		final ParameterconfigBean paramconfig = new ParameterconfigBean();
		try {
			paramconfig.setCustomerid(config.getCustomerid().getCustomerid());
		} catch (Exception ex) {
		}
		paramconfig.setParamerterconfigid(config.getId());
		final Customeraffiliate aff = config.getClientaffiliateid();
		if (!Objects.isNull(aff)) {
			paramconfig.setClientaffiliateid(aff.getAffiliateid());
		}
		paramconfig.setParametertypeid(type.getParametertypeid());
		try {
			paramconfig.setDefaultlevelId(config.getDefaultlevelid().getExceptionlevelid());
		} catch (Exception ex2) {
		}
		if (paramtypename.trim().equalsIgnoreCase("SP")) {
			paramconfig.setAlarmdelayinput(BigDecimalToTime(config.getAlarmdelayinput()));
			paramconfig.setAlarmthreshold(String.valueOf(config.getAlarmthreshold()));
			paramconfig.setAlarmthresholdperc(
					String.valueOf(config.getAlarmthresholdperc().multiply(new BigDecimal(100))));
			paramconfig.setAlertdelayinput(BigDecimalToTime(config.getAlarmdelayinput()));
			paramconfig.setAlertthreshold(String.valueOf(config.getAlertthreshold()));
			paramconfig.setAlertthresholdperc(
					String.valueOf(config.getAlertthresholdperc().multiply(new BigDecimal(100))));
			paramconfig.setMinimumdistance("0");
			paramconfig.setRecordingdelayinput(BigDecimalToTime(config.getRecordingdelayinput()));
			paramconfig.setRecordingthreshold(String.valueOf(config.getRecordingthreshold()));
			paramconfig.setRecordingthresholdperc(
					String.valueOf(config.getRecordingthresholdperc().multiply(new BigDecimal(100))));
			paramconfig.setThresholdlimit(String.valueOf(config.getThresholdlimit()));
			paramconfig.setRequiredresttime(String.valueOf(config.getRequiredresttime()));
			paramconfig.setFrommonth(config.getFrommonth());
			paramconfig.setTomonth(config.getTomonth());
		} else if (paramtypename.trim().equalsIgnoreCase("HA") || paramtypename.trim().equalsIgnoreCase("HB")) {
			paramconfig.setAlarmdelayinput(BigDecimalToTime(config.getAlarmdelayinput()));
			paramconfig.setAlarmthreshold(String.valueOf(config.getAlarmthreshold()));
			paramconfig.setAlarmthresholdperc(String.valueOf(config.getAlarmthresholdperc()));
			paramconfig.setAlertdelayinput(BigDecimalToTime(config.getAlarmdelayinput()));
			paramconfig.setAlertthreshold(String.valueOf(config.getAlertthreshold()));
			paramconfig.setAlertthresholdperc(String.valueOf(config.getAlertthresholdperc()));
			paramconfig.setMinimumdistance(String.valueOf(config.getMinimumdistance()));
			paramconfig.setRecordingdelayinput(BigDecimalToTime(config.getRecordingdelayinput()));
			paramconfig.setRecordingthreshold(String.valueOf(config.getRecordingthreshold()));
			paramconfig.setRecordingthresholdperc(String.valueOf(config.getRecordingthresholdperc()));
			paramconfig.setThresholdlimit(String.valueOf(config.getThresholdlimit()));
			paramconfig.setRequiredresttime(String.valueOf(config.getRequiredresttime()));
			paramconfig.setFrommonth(config.getFrommonth());
			paramconfig.setTomonth(config.getTomonth());
		} else if (paramtypename.trim().equalsIgnoreCase("ND")) {
			paramconfig.setAlarmdelayinput(BigDecimalToTime(config.getAlarmdelayinput()));
			paramconfig.setAlarmthreshold(String.valueOf(config.getAlarmthreshold()));
			paramconfig.setAlarmthresholdperc(String.valueOf(config.getAlarmthresholdperc()));
			paramconfig.setAlertdelayinput(BigDecimalToTime(config.getAlarmdelayinput()));
			paramconfig.setAlertthreshold(String.valueOf(config.getAlertthreshold()));
			paramconfig.setAlertthresholdperc(String.valueOf(config.getAlertthresholdperc()));
			paramconfig.setMinimumdistance(String.valueOf(config.getMinimumdistance()));
			paramconfig.setRecordingdelayinput(BigDecimalToTime(config.getRecordingdelayinput()));
			paramconfig.setRecordingthreshold(String.valueOf(config.getRecordingthreshold()));
			paramconfig.setRecordingthresholdperc(String.valueOf(config.getRecordingthresholdperc()));
			paramconfig.setThresholdlimit(String.valueOf(config.getThresholdlimit()));
			paramconfig.setRequiredresttime(String.valueOf(config.getRequiredresttime()));
			paramconfig.setFrommonth(config.getFrommonth());
			paramconfig.setTomonth(config.getTomonth());
			paramconfig.setFromtime(DateFormat(config.getFromtime(), "HH:mm:ss"));
			paramconfig.setTotime(DateFormat(config.getTotime(), "HH:mm:ss"));
			paramconfig.setFrommonth(config.getFrommonth());
			paramconfig.setTomonth(config.getTomonth());
		}
		if (paramtypename.trim().equalsIgnoreCase("CD")) {
			paramconfig.setAlarmdelayinput(String.valueOf(config.getAlarmdelayinput()));
			paramconfig.setAlarmthreshold(BigDecimalToTime(config.getAlarmthreshold()));
			paramconfig.setAlarmthresholdperc(String.valueOf(config.getAlarmthresholdperc()));
			paramconfig.setAlertdelayinput(String.valueOf(config.getAlertdelayinput()));
			paramconfig.setAlertthreshold(BigDecimalToTime(config.getAlertthreshold()));
			paramconfig.setAlertthresholdperc(String.valueOf(config.getAlertthresholdperc()));
			paramconfig.setMinimumdistance(String.valueOf(config.getMinimumdistance()));
			paramconfig.setRecordingdelayinput(String.valueOf(config.getRecordingdelayinput()));
			paramconfig.setRecordingthreshold(BigDecimalToTime(config.getRecordingthreshold()));
			paramconfig.setRecordingthresholdperc(String.valueOf(config.getRecordingthresholdperc()));
			paramconfig.setThresholdlimit(BigDecimalToTime(config.getThresholdlimit()));
			paramconfig.setRequiredresttime(BigDecimalToTime(config.getRequiredresttime()));
			paramconfig.setFrommonth(config.getFrommonth());
			paramconfig.setTomonth(config.getTomonth());
		} else if (paramtypename.trim().equalsIgnoreCase("DD")) {
			paramconfig.setAlarmdelayinput(String.valueOf(config.getAlarmdelayinput()));
			paramconfig.setAlarmthreshold(BigDecimalToTime(config.getAlarmthreshold()));
			paramconfig.setAlarmthresholdperc(String.valueOf(config.getAlarmthresholdperc()));
			paramconfig.setAlertdelayinput(String.valueOf(config.getAlertdelayinput()));
			paramconfig.setAlertthreshold(BigDecimalToTime(config.getAlertthreshold()));
			paramconfig.setAlertthresholdperc(String.valueOf(config.getAlertthresholdperc()));
			paramconfig.setMinimumdistance(String.valueOf(config.getMinimumdistance()));
			paramconfig.setRecordingdelayinput(String.valueOf(config.getRecordingdelayinput()));
			paramconfig.setRecordingthreshold(BigDecimalToTime(config.getRecordingthreshold()));
			paramconfig.setRecordingthresholdperc(String.valueOf(config.getRecordingthresholdperc()));
			paramconfig.setThresholdlimit(BigDecimalToTime(config.getThresholdlimit()));
			paramconfig.setRequiredresttime(String.valueOf(config.getRequiredresttime()));
			paramconfig.setFrommonth(config.getFrommonth());
			paramconfig.setTomonth(config.getTomonth());
		} else if (paramtypename.trim().equalsIgnoreCase("DR")) {
			paramconfig.setAlarmdelayinput(String.valueOf(config.getAlarmdelayinput()));
			paramconfig.setAlarmthreshold(BigDecimalToTime(config.getAlarmthreshold()));
			paramconfig.setAlarmthresholdperc(String.valueOf(config.getAlarmthresholdperc()));
			paramconfig.setAlertdelayinput(String.valueOf(config.getAlertdelayinput()));
			paramconfig.setAlertthreshold(BigDecimalToTime(config.getAlertthreshold()));
			paramconfig.setAlertthresholdperc(String.valueOf(config.getAlertthresholdperc()));
			paramconfig.setMinimumdistance(String.valueOf(config.getMinimumdistance()));
			paramconfig.setRecordingdelayinput(String.valueOf(config.getRecordingdelayinput()));
			paramconfig.setRecordingthreshold(BigDecimalToTime(config.getRecordingthreshold()));
			paramconfig.setRecordingthresholdperc(String.valueOf(config.getRecordingthresholdperc()));
			paramconfig.setThresholdlimit(BigDecimalToTime(config.getThresholdlimit()));
			paramconfig.setRequiredresttime(String.valueOf(config.getRequiredresttime()));
			paramconfig.setFrommonth(config.getFrommonth());
			paramconfig.setTomonth(config.getTomonth());
		} else if (paramtypename.trim().equalsIgnoreCase("WD")) {
			paramconfig.setAlarmdelayinput(String.valueOf(config.getAlarmdelayinput()));
			paramconfig.setAlarmthreshold(BigDecimalToTime(config.getAlarmthreshold()));
			paramconfig.setAlarmthresholdperc(String.valueOf(config.getAlarmthresholdperc()));
			paramconfig.setAlertdelayinput(String.valueOf(config.getAlertdelayinput()));
			paramconfig.setAlertthreshold(BigDecimalToTime(config.getAlertthreshold()));
			paramconfig.setAlertthresholdperc(String.valueOf(config.getAlertthresholdperc()));
			paramconfig.setMinimumdistance(String.valueOf(config.getMinimumdistance()));
			paramconfig.setRecordingdelayinput(String.valueOf(config.getRecordingdelayinput()));
			paramconfig.setRecordingthreshold(BigDecimalToTime(config.getRecordingthreshold()));
			paramconfig.setRecordingthresholdperc(String.valueOf(config.getRecordingthresholdperc()));
			paramconfig.setThresholdlimit(BigDecimalToTime(config.getThresholdlimit()));
			paramconfig.setRequiredresttime(String.valueOf(config.getRequiredresttime()));
			paramconfig.setFrommonth(config.getFrommonth());
			paramconfig.setTomonth(config.getTomonth());
			paramconfig.setRollingdays(config.getRollingdays());
		} else if (paramtypename.trim().equalsIgnoreCase("WR")) {
			paramconfig.setAlarmdelayinput(String.valueOf(config.getAlarmdelayinput()));
			paramconfig.setAlarmthreshold(BigDecimalToTime(config.getAlarmthreshold()));
			paramconfig.setAlarmthresholdperc(String.valueOf(config.getAlarmthresholdperc()));
			paramconfig.setAlertdelayinput(String.valueOf(config.getAlertdelayinput()));
			paramconfig.setAlertthreshold(BigDecimalToTime(config.getAlertthreshold()));
			paramconfig.setAlertthresholdperc(String.valueOf(config.getAlertthresholdperc()));
			paramconfig.setMinimumdistance(String.valueOf(config.getMinimumdistance()));
			paramconfig.setRecordingdelayinput(String.valueOf(config.getRecordingdelayinput()));
			paramconfig.setRecordingthreshold(BigDecimalToTime(config.getRecordingthreshold()));
			paramconfig.setRecordingthresholdperc(String.valueOf(config.getRecordingthresholdperc()));
			paramconfig.setThresholdlimit(BigDecimalToTime(config.getThresholdlimit()));
			paramconfig.setRequiredresttime(String.valueOf(config.getRequiredresttime()));
			paramconfig.setFrommonth(config.getFrommonth());
			paramconfig.setTomonth(config.getTomonth());
			paramconfig.setRollingdays(config.getRollingdays());
		} else if (paramtypename.trim().equalsIgnoreCase("PD")) {
			paramconfig.setAlarmdelayinput(String.valueOf(config.getAlarmdelayinput()));
			paramconfig.setAlarmthreshold(String.valueOf(config.getAlarmthreshold()));
			paramconfig.setAlarmthresholdperc(String.valueOf(config.getAlarmthresholdperc()));
			paramconfig.setAlertdelayinput(String.valueOf(config.getAlertdelayinput()));
			paramconfig.setAlertthreshold(String.valueOf(config.getAlertthreshold()));
			paramconfig.setAlertthresholdperc(String.valueOf(config.getAlarmthresholdperc()));
			paramconfig.setMinimumdistance(String.valueOf(config.getMinimumdistance()));
			paramconfig.setRollingdays(0);
			paramconfig.setRecordingthreshold(String.valueOf(config.getRecordingthreshold()));
			paramconfig.setRecordingdelayinput(String.valueOf(config.getRecordingdelayinput()));
			paramconfig.setRecordingthresholdperc(String.valueOf(config.getRecordingthresholdperc()));
			paramconfig.setThresholdlimit(String.valueOf(config.getThresholdlimit()));
			paramconfig.setRequiredresttime(String.valueOf(config.getRequiredresttime()));
			paramconfig.setFrommonth(config.getFrommonth());
			paramconfig.setTomonth(config.getTomonth());
		} else if (paramtypename.trim().equalsIgnoreCase("SB")) {
			paramconfig.setAlarmdelayinput(String.valueOf(config.getAlarmdelayinput()));
			paramconfig.setAlarmthreshold(String.valueOf(config.getAlarmthreshold()));
			paramconfig.setAlarmthresholdperc(String.valueOf(config.getAlarmthresholdperc()));
			paramconfig.setAlertdelayinput(String.valueOf(config.getAlertdelayinput()));
			paramconfig.setAlertthreshold(String.valueOf(config.getAlertthreshold()));
			paramconfig.setAlertthresholdperc(String.valueOf(config.getAlarmthresholdperc()));
			paramconfig.setMinimumdistance(String.valueOf(config.getMinimumdistance()));
			paramconfig.setRollingdays(0);
			paramconfig.setRecordingthreshold(String.valueOf(config.getRecordingthreshold()));
			paramconfig.setRecordingdelayinput(String.valueOf(config.getRecordingdelayinput()));
			paramconfig.setRecordingthresholdperc(String.valueOf(config.getRecordingthresholdperc()));
			paramconfig.setThresholdlimit(String.valueOf(config.getThresholdlimit()));
			paramconfig.setRequiredresttime(String.valueOf(config.getRequiredresttime()));
			paramconfig.setFrommonth(config.getFrommonth());
			paramconfig.setTomonth(config.getTomonth());
		} else if (paramtypename.trim().equalsIgnoreCase("FT")) {
			paramconfig.setAlarmdelayinput(String.valueOf(config.getAlarmdelayinput()));
			paramconfig.setAlarmthreshold(String.valueOf(config.getAlarmthreshold()));
			paramconfig.setAlarmthresholdperc(String.valueOf(config.getAlarmthresholdperc()));
			paramconfig.setAlertdelayinput(String.valueOf(config.getAlertdelayinput()));
			paramconfig.setAlertthreshold(String.valueOf(config.getAlertthreshold()));
			paramconfig.setAlertthresholdperc(String.valueOf(config.getAlarmthresholdperc()));
			paramconfig.setMinimumdistance(String.valueOf(config.getMinimumdistance()));
			paramconfig.setRollingdays(0);
			paramconfig.setRecordingthreshold(String.valueOf(config.getRecordingthreshold()));
			paramconfig.setRecordingdelayinput(String.valueOf(config.getRecordingdelayinput()));
			paramconfig.setRecordingthresholdperc(String.valueOf(config.getRecordingthresholdperc()));
			paramconfig.setThresholdlimit(String.valueOf(config.getThresholdlimit()));
			paramconfig.setRequiredresttime(String.valueOf(config.getRequiredresttime()));
			paramconfig.setFrommonth(config.getFrommonth());
			paramconfig.setTomonth(config.getTomonth());
		} else if (paramtypename.trim().equalsIgnoreCase("SB")) {
			paramconfig.setAlarmdelayinput(String.valueOf(config.getAlarmdelayinput()));
			paramconfig.setAlarmthreshold(String.valueOf(config.getAlarmthreshold()));
			paramconfig.setAlarmthresholdperc(String.valueOf(config.getAlarmthresholdperc()));
			paramconfig.setAlertdelayinput(String.valueOf(config.getAlertdelayinput()));
			paramconfig.setAlertthreshold(String.valueOf(config.getAlertthreshold()));
			paramconfig.setAlertthresholdperc(String.valueOf(config.getAlarmthresholdperc()));
			paramconfig.setMinimumdistance(String.valueOf(config.getMinimumdistance()));
			paramconfig.setRollingdays(0);
			paramconfig.setRecordingthreshold(String.valueOf(config.getRecordingthreshold()));
			paramconfig.setRecordingdelayinput(String.valueOf(config.getRecordingdelayinput()));
			paramconfig.setRecordingthresholdperc(String.valueOf(config.getRecordingthresholdperc()));
			paramconfig.setThresholdlimit(String.valueOf(config.getThresholdlimit()));
			paramconfig.setRequiredresttime(String.valueOf(config.getRequiredresttime()));
			paramconfig.setFrommonth(config.getFrommonth());
			paramconfig.setTomonth(config.getTomonth());
		}
		return paramconfig;
	}

	public static ParameterconfigInfos2 fromConfigToBeans(final Parameterconfig config) {
		final Parametertype type = config.getParametertypeid();
		final ParameterconfigInfos2 paramconfig = new ParameterconfigInfos2();
		try {
			paramconfig.setCustomerid(config.getCustomerid().getCustomerid());
		} catch (Exception ex) {
		}
		if (!Objects.isNull(config.getDefaultlevelid())) {
			paramconfig.setAlertlevel(config.getDefaultlevelid().getExceptionlevelid());
		}
		paramconfig.setParameterconfigid(config.getId());
		final Customeraffiliate aff = config.getClientaffiliateid();
		if (!Objects.isNull(aff)) {
			paramconfig.setClientaffiliateid(aff.getAffiliateid());
		}
		paramconfig.setParametertypeid(type.getParametertypeid());
		paramconfig.setAlarmdelayinput(config.getAlarmdelayinput());
		paramconfig.setAlarmthreshold(config.getAlarmthreshold());
		paramconfig.setAlarmthresholdperc(config.getAlarmthresholdperc());
		paramconfig.setAlertdelayinput(config.getAlertdelayinput());
		paramconfig.setAlertthreshold(config.getAlertthreshold());
		paramconfig.setAlertthresholdperc(config.getAlertthresholdperc());
		paramconfig.setMinimumdistance(config.getMinimumdistance());
		paramconfig.setRecordingdelayinput(config.getRecordingdelayinput());
		paramconfig.setRecordingthreshold(config.getRecordingthreshold());
		paramconfig.setRecordingthresholdperc(config.getRecordingthresholdperc());
		paramconfig.setThresholdlimit(config.getThresholdlimit());
		paramconfig.setRequiredresttime(config.getRequiredresttime());
		paramconfig.setFrommonth(config.getFrommonth());
		paramconfig.setTomonth(config.getTomonth());
		paramconfig.setRollingdays(config.getRollingdays());
		final Status stta = config.getStatus();
		if (!Objects.isNull(stta)) {
			if (stta.getStatusid() == 1) {
				paramconfig.setActivesalert(true);
			} else {
				paramconfig.setActivesalert(false);
			}
		} else {
			paramconfig.setActivesalert(false);
		}
		paramconfig.setFromtime(DateFormatBigDecimal(config.getFromtime(), "HH:mm:ss"));
		paramconfig.setTotime(DateFormatBigDecimal(config.getTotime(), "HH:mm:ss"));
		return paramconfig;
	}

	public static Double getNbr(final String entitiesvalue, final Boolean record, final Boolean alert,
			final Boolean alarm, final BigDecimal totaldistance) {
		final ObjectMapper objectMapper = new ObjectMapper();
		Long nbrs = 0L;
		try {
			if (!Objects.isNull(entitiesvalue)) {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(entitiesvalue,
						(Class) Leveldetails.class);
				if (!Objects.isNull(details)) {
					if (!Objects.isNull(record) && record) {
						final InfosLevel infolevel = details.getRecord();
						if (!Objects.isNull(infolevel)) {
							nbrs += infolevel.getNbr();
						}
					}
					if (!Objects.isNull(alert) && alert) {
						final InfosLevel infolevel = details.getAlert();
						if (!Objects.isNull(infolevel)) {
							nbrs += infolevel.getNbr();
						}
					}
					if (!Objects.isNull(alarm) && alarm) {
						final InfosLevel infolevel = details.getAlarm();
						if (!Objects.isNull(infolevel)) {
							nbrs += infolevel.getNbr();
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (Objects.isNull(nbrs) && nbrs.intValue() == 0) {
			return null;
		}
		if (Objects.isNull(totaldistance) || totaldistance.compareTo(BigDecimal.ZERO) == 0) {
			return (double) nbrs;
		}
		try {
			return nbrs / totaldistance.doubleValue() * 100.0;
		} catch (Exception ex) {
			return (double) nbrs;
		}
	}

	public static Date getNextMondayOfDate(final Date date) {
		final Calendar maDate = new GregorianCalendar();
		maDate.setTime(date);
		maDate.set(7, 2);
		maDate.add(5, -7);
		return maDate.getTime();
	}

	public static Date getNextSundayOfDate(final Date date) {
		final Calendar maDate = new GregorianCalendar();
		maDate.setTime(date);
		maDate.set(7, 1);
		maDate.add(5, -7);
		return maDate.getTime();
	}

	public static Date getPreviousMondayOfDate(final Date date) {
		final Calendar maDate = new GregorianCalendar();
		maDate.setTime(date);
		maDate.set(7, 2);
		maDate.add(5, -7);
		return maDate.getTime();
	}

	public static Date getPreviousMonthDate(final Date date) {
		final Calendar maDate = new GregorianCalendar();
		maDate.setTime(date);
		maDate.set(5, 2);
		maDate.add(2, -1);
		return maDate.getTime();
	}

	public static List<Object[]> getReelMonthOrDaysBetweenDates(final Date startdate, final Date enddate,
			final Boolean DaysOrMonth) {
		final List<Object[]> dates = new ArrayList<>();
		int k = 1;
		Date OldDate = startdate;
		while (OldDate.before(enddate)) {
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(startdate);
			final Date init = OldDate;
			if (!DaysOrMonth) {
				calendar.add(2, k);
				calendar.set(5, calendar.getActualMinimum(5));
			} else {
				calendar.add(5, k);
			}
			OldDate = calendar.getTime();
			final Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(OldDate);
			final Date dateEnd = calendar2.getTime();
			if (OldDate.after(enddate)) {
				dates.add(new Object[] { init, enddate });
			} else {
				dates.add(new Object[] { init, dateEnd });
			}
			++k;
		}
		return dates;
	}

	public static List<Object[]> getReelMonthOrDaysBetweenDatesWithPeriods(final Date startdate, final Date enddate,
			final Boolean DaysOrMonth, final Integer periodicite) {
		final List<Object[]> dates = new ArrayList<>();
		int k = 0;
		Calendar calendar;
		for (Date OldDate = startdate; OldDate.before(enddate)
				|| OldDate.compareTo(enddate) == 0; OldDate = calendar.getTime(), ++k) {
			final Date init = OldDate;
			calendar = Calendar.getInstance();
			calendar.setTime(init);
			if (!DaysOrMonth) {
				calendar.add(2, periodicite - 1);
			} else {
				calendar.add(5, periodicite - 1);
			}
			final Date dateEnd = calendar.getTime();
			dates.add(new Object[] { init, dateEnd });
			if (!DaysOrMonth) {
				calendar.add(2, 1);
			} else {
				calendar.add(5, 1);
			}
		}
		return dates;
	}

	public static Date getStartOfDay(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(11, 0);
		calendar.set(12, 0);
		calendar.set(13, 0);
		calendar.set(14, 0);
		return calendar.getTime();
	}

	public static List<Object[]> getTrendMonthBetweenDates2(final Date startdate, final Date enddate,
			final Boolean DaysOrMonth) {
		final List<Object[]> dates = new ArrayList<>();
		int k = 1;
		Date OldDate = startdate;
		while (OldDate.before(enddate)) {
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(startdate);
			final Date init = OldDate;
			if (!DaysOrMonth) {
				calendar.add(2, k);
				calendar.set(5, calendar.getActualMinimum(5));
			} else {
				calendar.add(5, k);
			}
			OldDate = calendar.getTime();
			final Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(OldDate);
			calendar2.add(13, -1);
			final Date dateEnd = calendar2.getTime();
			if (OldDate.after(enddate)) {
				dates.add(new Object[] { init, enddate });
			} else {
				dates.add(new Object[] { init, dateEnd });
			}
			++k;
		}
		return dates;
	}

	public static Integer hierarchiedebase() {
		return 9;
	}

	public static Double ignoreNullFuel(final Double canbusfuelconsume, final Double fuelnominate,
			final Double tankconsumption, final Double directconsumption) {
		if (!Objects.isNull(canbusfuelconsume) && canbusfuelconsume.intValue() != 0) {
			return canbusfuelconsume;
		}
		if (!Objects.isNull(tankconsumption) && tankconsumption.intValue() != 0) {
			return tankconsumption;
		}
		if (!Objects.isNull(fuelnominate)) {
			return fuelnominate;
		}
		if (!Objects.isNull(directconsumption) && directconsumption.intValue() != 0) {
			return directconsumption;
		}
		return 0.0;
	}

	public static String InvalidatedException(final String username, final String objectsname) {
		final String logs = objectsname + " by " + username + " Date " + DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
		return logs;
	}

	public static com.camtrack.entities.Exception InvalidateToException(final Invalidateexception invalid,
			final User user) {
		final com.camtrack.entities.Exception exception = new com.camtrack.entities.Exception();
		com.camtrack.entities.Exception.init(user);
		exception.setAffiliateid(invalid.getAffiliateid());
		exception.setDistanceunderexception(invalid.getDistanceunderexception());
		exception.setDriverid(invalid.getDriverid());
		exception.setEnddatetime(invalid.getEnddatetime());
		exception.setEndgps(invalid.getEndgps());
		exception.setEvidence(invalid.getEvidence());
		exception.setExceptionday(invalid.getExceptionday());
		exception.setExceptionid(invalid.getExceptionid());
		exception.setExceptiontype(invalid.getExceptiontype());
		exception.setGdistanceunderexception(invalid.getGdistanceunderexception());
		exception.setGenddatetime(invalid.getGenddatetime());
		exception.setGendgps(invalid.getGendgps());
		exception.setGinvaliddata(invalid.getGinvaliddata());
		exception.setGlevel(invalid.getGlevel());
		exception.setGmaxbreak(invalid.getGmaxbreak());
		exception.setGmaxval(invalid.getGmaxval());
		exception.setGnumberofbreak(invalid.getGnumberofbreak());
		exception.setGpreventivedatetime(invalid.getGpreventivedatetime());
		exception.setGpreventivethreshold(invalid.getGpreventivethreshold());
		exception.setGrequiredbreak(invalid.getGrequiredbreak());
		exception.setGstartdatetime(invalid.getGstartdatetime());
		exception.setGstartgps(invalid.getGstartgps());
		exception.setGstatus(invalid.getGstatus());
		exception.setGthreshold(invalid.getGthreshold());
		exception.setGtimeexceeded(invalid.getGtimeexceeded());
		exception.setGtotaldistance(invalid.getGtotaldistance());
		exception.setGtotalduration(invalid.getGtotalduration());
		exception.setGvmax(invalid.getGvmax());
		exception.setInvaliddata(invalid.getInvaliddata());
		exception.setLevel(invalid.getLevel());
		exception.setMaxbreak(invalid.getMaxbreak());
		exception.setMaxvalue(invalid.getMaxvalue());
		exception.setMergetreated(invalid.getMergetreated());
		exception.setNumberofbreak(invalid.getNumberofbreak());
		exception.setPreventivedatetime(invalid.getPreventivedatetime());
		exception.setPreventivethreshold(invalid.getPreventivethreshold());
		exception.setRequiredbreak(invalid.getRequiredbreak());
		exception.setSpeedid(invalid.getSpeedid());
		exception.setStartdatetime(invalid.getStartdatetime());
		exception.setStartgps(invalid.getStartgps());
		exception.setStatus(invalid.getStatus());
		exception.setThreshold(invalid.getThreshold());
		exception.setTimeexceeded(invalid.getTimeexceeded());
		exception.setTotaldistance(invalid.getTotaldistance());
		exception.setTotalduration(invalid.getTotalduration());
		exception.setTransporterid(invalid.getTransporterid());
		exception.setTreated(invalid.getTreated());
		exception.setUnitid(invalid.getUnitid());
		exception.setVehicleid(invalid.getVehicleid());
		exception.setVmax(invalid.getVmax());
		return exception;
	}

	public static boolean isDateSame(Calendar c1, Calendar c2) {
		return ((c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
				&& (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)));
	}

	public static boolean isDateSameHour(Calendar c1, Calendar c2) {
		return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
				&& c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)
				&& c1.get(Calendar.HOUR_OF_DAY) == c2.get(Calendar.HOUR_OF_DAY));
	}

	public static boolean isFloat(final String s) {
		try {
			return Utils.DOUBLE_PATTERN.matcher(s).matches();
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean isZero(final double value, final double threshold) {
		return value >= -threshold && value <= threshold;
	}

	

	

	public static String loadphase1() {
		return "Load Phase 1";
	}

	public static String loadphase2() {
		return "Load Phase 2";
	}

	public static String loadphase3() {
		return "Load Phase 3";
	}

	public static String MD5Generator(String stringToHash) {
		// Static getInstance method is called with hashing MD5
		if (Objects.isNull(stringToHash)) {
			return null;
		}
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			// digest() method is called to calculate message digest
			// of an input digest() return array of byte
			byte[] messageDigest = md.digest(stringToHash.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static Double minRefillAuthorise() {
		return 300.0;
	}

	public static Integer nullToZero(final Integer values) {
		if (Objects.isNull(values)) {
			return 0;
		}
		return values;
	}

	public static Boolean ObjectToBoolean(final Object o) {
		try {
			if (Objects.isNull(o)) {
				return null;
			}
			return (Boolean) o;
		} catch (Exception ex) {
			return null;
		}
	}

	public static Boolean ObjectToBoolean2(final Object o) {
		try {
			if (Objects.isNull(o)) {
				return false;
			}
			return (Boolean) o;
		} catch (Exception ex) {
			return false;
		}
	}

	public static String ObjectToString(final Object o) {
		try {
			if (Objects.isNull(o)) {
				return null;
			}
			return (String) o;
		} catch (Exception ex) {
			return null;
		}
	}

	public static Boolean ObjectToInteger(Object o) {
		try {
			if (Objects.isNull(o)) {
				return false;
			}
			Integer val = Integer.parseInt(String.valueOf(o));
			if (val == 1) {
				return true;
			}

			return false;
		} catch (Exception ex) {
			return false;
		}
	}

	public static Parameterconfig parameterconfigfromparametertypedefaults(final Parametertype typeparam,
			final Customer custom, final Customeraffiliate affiliate, final Status status, Boolean createorupdate,
			final User user, final Exceptionlevel level, final ParameterconfigRepository paramconfigR) {
		Parameterconfig paramconfig = null;
		List<Parameterconfig> listparam;
		if (!Objects.isNull(custom)) {
			final Integer customid = custom.getCustomerid();
			final Integer params = typeparam.getParametertypeid();
			listparam = paramconfigR.findByCustomerConfig(customid, params, 0, 12);
		} else {
			if (Objects.isNull(affiliate)) {
				return null;
			}
			listparam = paramconfigR.findByAffiliateConfig(affiliate.getAffiliateid(), typeparam.getParametertypeid(),
					0, 12);
		}
		final Date dat = new Date();
		if (!Objects.isNull(listparam) && !listparam.isEmpty()) {
			paramconfig = listparam.get(0);
			createorupdate = false;
		} else {
			paramconfig = new Parameterconfig();
			createorupdate = true;
			paramconfig.setCreatedby(user);
			paramconfig.setCreatedon(dat);
			paramconfig.setFrommonth(0);
			paramconfig.setTomonth(12);
		}
		Parameterconfig.init(createorupdate, user);
		if (!Objects.isNull(affiliate)) {
			paramconfig.setClientaffiliateid(affiliate, createorupdate);
		}
		if (!Objects.isNull(custom)) {
			paramconfig.setCustomerid(custom, createorupdate);
		}
		paramconfig.setParametertypeid(typeparam, createorupdate);
		paramconfig.setUpdatedby(user);
		paramconfig.setFrommonth(0);
		paramconfig.setTomonth(12);
		paramconfig.setUpdatedon(dat);
		paramconfig.setStatus(status);
		final String paramtypename = typeparam.getShortname();
		paramconfig.setStatus(status, createorupdate);
		final List<String> oneshot = new ArrayList<>();
		oneshot.add("Col");
		oneshot.add("DiS");
		oneshot.add("SD");
		oneshot.add("BD");
		oneshot.add("RO");
		oneshot.add("LDW");
		oneshot.add("FCW");
		oneshot.add("SPA");
		final List<String> oneshotfatigue = new ArrayList<>();
		oneshot.add("DiTrack");
		oneshot.add("FT");
		oneshot.add("NoD");
		oneshot.add("SB");
		oneshot.add("PH");
		oneshot.add("SK");
		if (paramtypename.trim().equalsIgnoreCase("SP")) {
			paramconfig.setAlarmdelayinput(Parameterconfigdefaultvalue.speeding_alarmdelayinput, createorupdate);
			paramconfig.setAlarmthreshold(Parameterconfigdefaultvalue.speeding_alarmhreshold, createorupdate);
			paramconfig.setAlarmthresholdperc(Parameterconfigdefaultvalue.speeding_alarmthresholdperc, createorupdate);
			paramconfig.setAlertdelayinput(Parameterconfigdefaultvalue.speeding_alertdelayinput, createorupdate);
			paramconfig.setAlertthreshold(Parameterconfigdefaultvalue.speeding_alertthreshold, createorupdate);
			paramconfig.setAlertthresholdperc(Parameterconfigdefaultvalue.speeding_alertthresholdperc, createorupdate);
			paramconfig.setMinimumdistance(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setRecordingdelayinput(Parameterconfigdefaultvalue.speeding_recordingdelayinput,
					createorupdate);
			paramconfig.setRecordingthreshold(Parameterconfigdefaultvalue.speeding_recordthreshold, createorupdate);
			paramconfig.setRecordingthresholdperc(Parameterconfigdefaultvalue.speeding_recordingthresholdperc,
					createorupdate);
			paramconfig.setThresholdlimit(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setRequiredresttime(BigDecimal.valueOf(0.0), createorupdate);
		} else if (paramtypename.trim().equalsIgnoreCase("HA")) {
			paramconfig.setAlarmdelayinput(Parameterconfigdefaultvalue.Harhsacc_max_alarmdelayinput, createorupdate);
			paramconfig.setAlarmthreshold(Parameterconfigdefaultvalue.Harhsacc_alarmhreshold, createorupdate);
			paramconfig.setAlarmthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlertdelayinput(Parameterconfigdefaultvalue.Harhsacc_maxduration_alertdelayinput,
					createorupdate);
			paramconfig.setAlertthreshold(Parameterconfigdefaultvalue.Harhsacc_alertthreshold, createorupdate);
			paramconfig.setAlertthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setMinimumdistance(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setRecordingdelayinput(Parameterconfigdefaultvalue.Harhsacc_minduration_recordingdelayinput,
					createorupdate);
			paramconfig.setRecordingthreshold(Parameterconfigdefaultvalue.Harhsacc_recordthreshold, createorupdate);
			paramconfig.setRecordingthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setThresholdlimit(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setRequiredresttime(BigDecimal.valueOf(0.0), createorupdate);
		} else if (paramtypename.trim().equalsIgnoreCase("HB")) {
			paramconfig.setAlarmdelayinput(Parameterconfigdefaultvalue.Harsbraking_max_alarmdelayinput, createorupdate);
			paramconfig.setAlarmthreshold(Parameterconfigdefaultvalue.Harsbraking_alarmhreshold, createorupdate);
			paramconfig.setAlarmthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlertdelayinput(Parameterconfigdefaultvalue.Harsbraking_maxduration_alertdelayinput,
					createorupdate);
			paramconfig.setAlertthreshold(Parameterconfigdefaultvalue.Harsbraking_alertthreshold, createorupdate);
			paramconfig.setAlertthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setMinimumdistance(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setRecordingdelayinput(Parameterconfigdefaultvalue.Harsbraking_minduration_recordingdelayinput,
					createorupdate);
			paramconfig.setRecordingthreshold(Parameterconfigdefaultvalue.Harsbraking_recordthreshold, createorupdate);
			paramconfig.setRecordingthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setThresholdlimit(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setRequiredresttime(BigDecimal.valueOf(0.0), createorupdate);
		} else if (paramtypename.trim().equalsIgnoreCase("ND")) {
			paramconfig.setAlarmdelayinput(Parameterconfigdefaultvalue.nightDriving_alarmhreshold, createorupdate);
			paramconfig.setAlarmthreshold(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlarmthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlertdelayinput(Parameterconfigdefaultvalue.nightDriving_alertthreshold, createorupdate);
			paramconfig.setAlertthreshold(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlertthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setMinimumdistance(Parameterconfigdefaultvalue.nightDriving_minimumdistance, createorupdate);
			paramconfig.setRecordingdelayinput(Parameterconfigdefaultvalue.nightDriving_recordthreshold,
					createorupdate);
			paramconfig.setRecordingthreshold(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setRecordingthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setThresholdlimit(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setRequiredresttime(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setFrommonth(Parameterconfigdefaultvalue.nightDriving_frommonth);
			paramconfig.setTomonth(Parameterconfigdefaultvalue.nightDriving_tommonth);
			paramconfig.setFromtime(StringToDates(Parameterconfigdefaultvalue.nightDriving_fromtime, "HH:mm:ss"));
			paramconfig.setTotime(StringToDates(Parameterconfigdefaultvalue.nightDriving_totime, "HH:mm:ss"));
		}
		if (paramtypename.trim().equalsIgnoreCase("CD")) {
			paramconfig.setAlarmdelayinput(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlarmthreshold(Parameterconfigdefaultvalue.continiuousdrive_alarmhreshold, createorupdate);
			paramconfig.setAlarmthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlertdelayinput(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlertthreshold(Parameterconfigdefaultvalue.continiuousdrive_alertthreshold, createorupdate);
			paramconfig.setAlertthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setMinimumdistance(Parameterconfigdefaultvalue.continiuousdrive_minimumdistance,
					createorupdate);
			paramconfig.setRecordingdelayinput(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setRecordingthreshold(Parameterconfigdefaultvalue.continiuousdrive_recordthreshold,
					createorupdate);
			paramconfig.setRecordingthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setThresholdlimit(Parameterconfigdefaultvalue.continiuousdrive_thresholdlimit, createorupdate);
			paramconfig.setRequiredresttime(Parameterconfigdefaultvalue.continiuousdrive_requirerestime,
					createorupdate);
		} else if (paramtypename.trim().equalsIgnoreCase("DD")) {
			paramconfig.setAlarmdelayinput(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlarmthreshold(Parameterconfigdefaultvalue.dailydrive_alarmhreshold, createorupdate);
			paramconfig.setAlarmthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlertdelayinput(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlertthreshold(Parameterconfigdefaultvalue.dailydrive_alertthreshold, createorupdate);
			paramconfig.setAlertthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setMinimumdistance(Parameterconfigdefaultvalue.dailydrive_minimumdistance, createorupdate);
			paramconfig.setRecordingdelayinput(Parameterconfigdefaultvalue.prev_dailydrive_thresholdlimit,
					createorupdate);
			paramconfig.setRecordingthreshold(Parameterconfigdefaultvalue.dailydrive_recordthreshold, createorupdate);
			paramconfig.setRecordingthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setThresholdlimit(Parameterconfigdefaultvalue.dailydrive_thresholdlimit, createorupdate);
			paramconfig.setRequiredresttime(BigDecimal.valueOf(0.0), createorupdate);
		} else if (paramtypename.trim().equalsIgnoreCase("DR")) {
			paramconfig.setAlarmdelayinput(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlarmthreshold(Parameterconfigdefaultvalue.dailyrest_alarmhreshold, createorupdate);
			paramconfig.setAlarmthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlertdelayinput(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlertthreshold(Parameterconfigdefaultvalue.dailyrest_alertthreshold, createorupdate);
			paramconfig.setAlertthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setMinimumdistance(Parameterconfigdefaultvalue.dailyrest_minimumdistance, createorupdate);
			paramconfig.setRecordingdelayinput(Parameterconfigdefaultvalue.prev_dailyrest_thresholdlimit,
					createorupdate);
			paramconfig.setRecordingthreshold(Parameterconfigdefaultvalue.dailyrest_recordthreshold, createorupdate);
			paramconfig.setRecordingthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setThresholdlimit(Parameterconfigdefaultvalue.dailyrest_thresholdlimit, createorupdate);
			paramconfig.setRequiredresttime(BigDecimal.valueOf(0.0), createorupdate);
		} else if (paramtypename.trim().equalsIgnoreCase("WD")) {
			paramconfig.setAlarmdelayinput(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlarmthreshold(Parameterconfigdefaultvalue.weeklyDrive_alarmhreshold, createorupdate);
			paramconfig.setAlarmthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlertdelayinput(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlertthreshold(Parameterconfigdefaultvalue.weeklyDrive_alertthreshold, createorupdate);
			paramconfig.setAlertthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setMinimumdistance(Parameterconfigdefaultvalue.weeklyDrive_minimumdistance, createorupdate);
			paramconfig.setRollingdays(Parameterconfigdefaultvalue.weeklyDrive_rollingdays, createorupdate);
			paramconfig.setRecordingthreshold(Parameterconfigdefaultvalue.weeklyDrive_recordthreshold, createorupdate);
			paramconfig.setRecordingthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setThresholdlimit(Parameterconfigdefaultvalue.weeklyDrive_thresholdlimit, createorupdate);
			paramconfig.setRequiredresttime(BigDecimal.valueOf(0.0), createorupdate);
		} else if (paramtypename.trim().equalsIgnoreCase("WR")) {
			paramconfig.setAlarmdelayinput(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlarmthreshold(Parameterconfigdefaultvalue.weeklyRest_alarmhreshold, createorupdate);
			paramconfig.setAlarmthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlertdelayinput(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlertthreshold(Parameterconfigdefaultvalue.weeklyRest_alertthreshold, createorupdate);
			paramconfig.setAlertthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setMinimumdistance(Parameterconfigdefaultvalue.weeklyRest_minimumdistance, createorupdate);
			paramconfig.setRollingdays(Parameterconfigdefaultvalue.weeklyRest_rollingdays, createorupdate);
			paramconfig.setRecordingthreshold(Parameterconfigdefaultvalue.weeklyRest_recordthreshold, createorupdate);
			paramconfig.setRecordingthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setThresholdlimit(Parameterconfigdefaultvalue.weeklyRest_thresholdlimit, createorupdate);
			paramconfig.setRequiredresttime(BigDecimal.valueOf(0.0), createorupdate);
		} else if (oneshot.contains(paramtypename.trim())) {
			paramconfig.setAlarmdelayinput(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlarmthreshold(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlarmthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlertdelayinput(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlertthreshold(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlertthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setMinimumdistance(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setRollingdays(0, createorupdate);
			paramconfig.setRecordingthreshold(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setRecordingdelayinput(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setRecordingthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setThresholdlimit(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setRequiredresttime(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setDefaultlevelid(level);
		} else if (oneshotfatigue.contains(paramtypename.trim())) {
			paramconfig.setAlarmdelayinput(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlarmthreshold(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlarmthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlertdelayinput(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlertthreshold(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setAlertthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setMinimumdistance(Parameterconfigdefaultvalue.noid_minimumdistance, createorupdate);
			paramconfig.setRollingdays(0, createorupdate);
			paramconfig.setRecordingthreshold(Parameterconfigdefaultvalue.fatigue_recordlimit, createorupdate);
			paramconfig.setRecordingdelayinput(Parameterconfigdefaultvalue.fatigue_recordingdelayinput, createorupdate);
			paramconfig.setRecordingthresholdperc(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setThresholdlimit(BigDecimal.valueOf(0.0), createorupdate);
			paramconfig.setRequiredresttime(BigDecimal.valueOf(0.0), createorupdate);
		}
		if (Objects.isNull(paramconfig.getMinimumdistance())) {
			paramconfig.setMinimumdistance(BigDecimal.ZERO);
		}
		paramconfig.setDefaultlevelid(level);
		if (Objects.isNull(paramconfig.getThresholdlimit())) {
			paramconfig.setThresholdlimit(new BigDecimal(-1));
		}
		return paramconfig;
	}

	public static Short ParamTypeconfig() {
		return Short.valueOf("2");
	}

	public static Integer IgnoreNull(Integer value) {
		if (Objects.isNull(value)) {
			return 0;
		}
		return value;
	}

	public static Integer recoveryIdInTabtaBaseByFrequenceId() {
		return 5;
	}

	public static Integer recoveryIdInTabtaBaseByNoVioloation() {
		return 10;
	}

	public static String removeEscapeChars(String remainingValue) {
		Matcher matcher = Pattern.compile("\\&([^;]{6})", Pattern.CASE_INSENSITIVE).matcher(remainingValue);
		while (matcher.find()) {
			String before = remainingValue.substring(0, matcher.start());
			String after = remainingValue.substring(matcher.start() + 1);
			remainingValue = (before + after);
		}
		return remainingValue;
	}

	

	public static boolean scpdescandtoTransporter() {
		return false;
	}

	public static Long secondesBetweenDate(final Date date1, final Date date2) {
		return ChronoUnit.SECONDS.between(date1.toInstant(), date2.toInstant());
	}

	public static void sendMail(final String subject, final String mailText, final String[] destinations,
			final JavaMailSender _mailSender) throws MessagingException, IOException, MailSendException {
		final SimpleMailMessage msge = new SimpleMailMessage();
		msge.setFrom("bisolutions@camtrack.pro");
		msge.setSubject(subject);
		msge.setText(mailText);
		msge.setTo(destinations);
		msge.setSentDate(new Date());
		_mailSender.send(msge);
	}

	public static String StringEscape(String escape) {
		if (Objects.isNull(escape)) {
			return null;
		}
		return escape.replace("\\", "\\\\").replace("\t", "\\t").replace("\b", "\\b").replace("\n", "\\n")
				.replace("\r", "\\r").replace("\f", "\\f").replace("\'", "\\'").replace("\"", "\\\"");

	}

	public static String StringFromList(final List<Integer> listinteger) {
		String ids = "";
		int k = 0;
		for (final Integer transporter : listinteger) {
			if (k == 0) {
				ids = transporter + "";
			} else {
				ids = ids + "," + transporter;
			}
			++k;
		}
		return ids;
	}

	public static BigDecimal StringtoBigDecimal(final String value) {
		final String[] hours = value.split(":");
		final Integer hour = Integer.valueOf(hours[0]);
		final BigDecimal hourss = new BigDecimal(hour);
		Integer min;
		try {
			min = Integer.valueOf(hours[1]);
		} catch (Exception ex) {
			min = 0;
		}
		BigDecimal mins = new BigDecimal(min);
		mins = mins.divide(new BigDecimal(60));
		Integer seconds;
		try {
			seconds = Integer.valueOf(hours[2]);
		} catch (Exception ex2) {
			seconds = 0;
		}
		BigDecimal secondss = new BigDecimal(seconds);
		secondss = secondss.divide(new BigDecimal(3600));
		return hourss.add(mins).add(secondss).setScale(6, RoundingMode.HALF_UP);
	}

	public static Date StringToDate(final String other, final String date, final String format) {
		try {
			final DateFormat formatter = new SimpleDateFormat(format);
			final Date result = formatter.parse(date);
			return result;
		} catch (Exception e) {

			try {
				final String[] dates = date.split("\\s+");
				final String date2 = dates[0].replaceAll(":", "-") + " " + dates[1];
				final DateFormat formatter2 = new SimpleDateFormat(format);
				final Date result2 = formatter2.parse(date2);
				return result2;
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
	}

	public static Date StringToDateInitDates(final String other, final String date, final String format,
			final Integer susbtractionhours) {
		try {
			final DateFormat formatter = new SimpleDateFormat(format);
			final Date result = formatter.parse(date);
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(result);
			calendar.add(5, susbtractionhours);
			return calendar.getTime();
		} catch (Exception e) {

			try {
				final String[] dates = date.split("\\s+");
				final String date2 = dates[0].replaceAll(":", "-") + " " + dates[1];
				final DateFormat formatter2 = new SimpleDateFormat(format);
				final Date result2 = formatter2.parse(date2);
				return result2;
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
	}

	public static Date StringToDates(final String date, final String format) {
		try {
			final DateFormat formatter = new SimpleDateFormat(format);
			final Date result = formatter.parse(date);
			return result;
		} catch (Exception e) {

			try {
				final String[] dates = date.split("\\s+");
				final String date2 = dates[0].replaceAll(":", "-") + " " + dates[1];
				final DateFormat formatter2 = new SimpleDateFormat(format);
				final Date result2 = formatter2.parse(date2);
				return result2;
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
	}

	public static Date StringToDateTimezone(final String UserTimezone, final String date, final String format) {
		try {
			final SimpleDateFormat sim = new SimpleDateFormat(format);
			final Date result = sim.parse(date);
			final Calendar calendat = Calendar.getInstance();
			calendat.setTime(currentDate());
			calendat.add(11, 1);
			final Date dates = calendat.getTime();
			if (result.after(dates)) {
				return dates;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static NbrtypeExceptions tableaujson(final String paramtypename, final String entities, final Boolean record,
			final Boolean alert, final Boolean alarm, final String[] resumeString, final BigDecimal totaldistance) {
		if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
			return new NbrtypeExceptions(entities, getNbr(resumeString[0], record, alert, alarm, totaldistance));
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
			return new NbrtypeExceptions(entities, getNbr(resumeString[1], record, alert, alarm, totaldistance));
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
			return new NbrtypeExceptions(entities, getNbr(resumeString[2], record, alert, alarm, totaldistance));
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
			return new NbrtypeExceptions(entities, getNbr(resumeString[3], record, alert, alarm, totaldistance));
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
			return new NbrtypeExceptions(entities, getNbr(resumeString[4], record, alert, alarm, totaldistance));
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
			return new NbrtypeExceptions(entities, getNbr(resumeString[5], record, alert, alarm, totaldistance));
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
			return new NbrtypeExceptions(entities, getNbr(resumeString[6], record, alert, alarm, totaldistance));
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
			return new NbrtypeExceptions(entities, getNbr(resumeString[7], record, alert, alarm, totaldistance));
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
			return new NbrtypeExceptions(entities, getNbr(resumeString[8], record, alert, alarm, totaldistance));
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
			return new NbrtypeExceptions(entities, getNbr(resumeString[9], record, alert, alarm, totaldistance));
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
			return new NbrtypeExceptions(entities, getNbr(resumeString[10], record, alert, alarm, totaldistance));
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
			return new NbrtypeExceptions(entities, getNbr(resumeString[11], record, alert, alarm, totaldistance));
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
			return new NbrtypeExceptions(entities, getNbr(resumeString[12], record, alert, alarm, totaldistance));
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
			return new NbrtypeExceptions(entities, getNbr(resumeString[13], record, alert, alarm, totaldistance));
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
			return new NbrtypeExceptions(entities, getNbr(resumeString[14], record, alert, alarm, totaldistance));
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
			return new NbrtypeExceptions(entities, getNbr(resumeString[15], record, alert, alarm, totaldistance));
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
			return new NbrtypeExceptions(entities, getNbr(resumeString[16], record, alert, alarm, totaldistance));
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
			return new NbrtypeExceptions(entities, getNbr(resumeString[17], record, alert, alarm, totaldistance));
		}
		return null;
	}

	public static Resumexceptionaffiliate tableaujsonExtract(final Resumexceptionaffiliate aff,
			final com.camtrack.entities.Exception exception, final Boolean addordelete) {
		final ObjectMapper objectMapper = new ObjectMapper();
		final long nbrs = 0L;
		final String paramtypename = exception.getExceptiontype().getShortname();
		boolean record = false;
		boolean alert = false;
		boolean alarm = false;
		final Integer level = exception.getLevel().getExceptionlevelid();
		if (level == 1) {
			record = true;
		} else if (level == 2) {
			alert = true;
		} else {
			alarm = true;
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getSpeedingfnbr(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				final Leveldetails details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getHa(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				final Leveldetails details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getHb(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				final Leveldetails details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getNightdrive(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
			}
		}
		Leveldetails details;
		if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getContiniuousdrive(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailydrive(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailywork(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailyrest(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getWeeklydrive(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailyrest(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getPhonedistraction(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getSealbelt(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getFatigue(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getWeeklywork(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getActivefleet(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getNoid(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getInsuffisianttripduration(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getSmoking(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else {
			details = null;
		}
		if (!Objects.isNull(details)) {
			InfosLevel infolevel;
			if (record) {
				infolevel = details.getRecord();
			} else if (alert) {
				infolevel = details.getAlert();
			} else {
				infolevel = details.getAlarm();
			}
			if (addordelete) {
				infolevel.setDistanceunderexception(
						infolevel.getDistanceunderexception().add(exception.getDistanceunderexception()));
				infolevel.setTotaldistance(infolevel.getTotaldistance().add(exception.getTotaldistance()));
				infolevel.setTotalduration(infolevel.getTotalduration().add(exception.getTotalduration()));
				infolevel.setNbr(infolevel.getNbr() + 1L);
			} else {
				infolevel.setDistanceunderexception(
						infolevel.getDistanceunderexception().subtract(exception.getDistanceunderexception()));
				infolevel.setTotaldistance(infolevel.getTotaldistance().subtract(exception.getTotaldistance()));
				infolevel.setTotalduration(infolevel.getTotalduration().subtract(exception.getTotalduration()));
				infolevel.setNbr(infolevel.getNbr() - 1L);
			}
			if (record) {
				details.setRecord(infolevel);
			} else if (alert) {
				details.setAlert(infolevel);
			} else {
				details.setAlarm(infolevel);
			}
			if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
				try {
					aff.setSpeedingfnbr(objectMapper.writeValueAsString(details));
				} catch (Exception ex2) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
				try {
					aff.setHa(objectMapper.writeValueAsString(details));
				} catch (Exception ex3) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
				try {
					aff.setHb(objectMapper.writeValueAsString(details));
				} catch (Exception ex4) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
				try {
					aff.setNightdrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex5) {
				}
			}
			if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
				try {
					aff.setContiniuousdrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex6) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
				try {
					aff.setDailydrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex7) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
				try {
					aff.setDailywork(objectMapper.writeValueAsString(details));
				} catch (Exception ex8) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
				try {
					aff.setDailyrest(objectMapper.writeValueAsString(details));
				} catch (Exception ex9) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
				try {
					aff.setWeeklydrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex10) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
				try {
					aff.setDailyrest(objectMapper.writeValueAsString(details));
				} catch (Exception ex11) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
				try {
					aff.setPhonedistraction(objectMapper.writeValueAsString(details));
				} catch (Exception ex12) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
				try {
					aff.setSealbelt(objectMapper.writeValueAsString(details));
				} catch (Exception ex13) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
				try {
					aff.setFatigue(objectMapper.writeValueAsString(details));
				} catch (Exception ex14) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
				try {
					aff.setWeeklywork(objectMapper.writeValueAsString(details));
				} catch (Exception ex15) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
				try {
					aff.setActivefleet(objectMapper.writeValueAsString(details));
				} catch (Exception ex16) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
				try {
					aff.setNoid(objectMapper.writeValueAsString(details));
				} catch (Exception ex17) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
				try {
					aff.setInsuffisianttripduration(objectMapper.writeValueAsString(details));
				} catch (Exception ex18) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
				try {
					aff.setSmoking(objectMapper.writeValueAsString(details));
				} catch (Exception ex19) {
				}
			}
		}
		return aff;
	}

	public static Resumexceptionclient tableaujsonExtractclient(final Resumexceptionclient aff,
			final com.camtrack.entities.Exception exception, final Boolean addordelete) {
		final ObjectMapper objectMapper = new ObjectMapper();
		final long nbrs = 0L;
		final String paramtypename = exception.getExceptiontype().getShortname();
		boolean record = false;
		boolean alert = false;
		boolean alarm = false;
		final Integer level = exception.getLevel().getExceptionlevelid();
		if (level == 1) {
			record = true;
		} else if (level == 2) {
			alert = true;
		} else {
			alarm = true;
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getSpeedingfnbr(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				final Leveldetails details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getHa(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				final Leveldetails details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getHb(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				final Leveldetails details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getNightdrive(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
			}
		}
		Leveldetails details;
		if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getContiniuousdrive(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailydrive(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailywork(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailyrest(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getWeeklydrive(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailyrest(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getPhonedistraction(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getSealbelt(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getFatigue(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getWeeklywork(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getActivefleet(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getNoid(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getInsuffisianttripduration(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getSmoking(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else {
			details = null;
		}
		if (!Objects.isNull(details)) {
			InfosLevel infolevel;
			if (record) {
				infolevel = details.getRecord();
			} else if (alert) {
				infolevel = details.getAlert();
			} else {
				infolevel = details.getAlarm();
			}
			if (addordelete) {
				infolevel.setDistanceunderexception(
						infolevel.getDistanceunderexception().add(exception.getDistanceunderexception()));
				infolevel.setTotaldistance(infolevel.getTotaldistance().add(exception.getTotaldistance()));
				infolevel.setTotalduration(infolevel.getTotalduration().add(exception.getTotalduration()));
				infolevel.setNbr(infolevel.getNbr() + 1L);
			} else {
				infolevel.setDistanceunderexception(
						infolevel.getDistanceunderexception().subtract(exception.getDistanceunderexception()));
				infolevel.setTotaldistance(infolevel.getTotaldistance().subtract(exception.getTotaldistance()));
				infolevel.setTotalduration(infolevel.getTotalduration().subtract(exception.getTotalduration()));
				infolevel.setNbr(infolevel.getNbr() - 1L);
			}
			if (record) {
				details.setRecord(infolevel);
			} else if (alert) {
				details.setAlert(infolevel);
			} else {
				details.setAlarm(infolevel);
			}
			if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
				try {
					aff.setSpeedingfnbr(objectMapper.writeValueAsString(details));
				} catch (Exception ex2) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
				try {
					aff.setHa(objectMapper.writeValueAsString(details));
				} catch (Exception ex3) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
				try {
					aff.setHb(objectMapper.writeValueAsString(details));
				} catch (Exception ex4) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
				try {
					aff.setNightdrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex5) {
				}
			}
			if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
				try {
					aff.setContiniuousdrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex6) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
				try {
					aff.setDailydrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex7) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
				try {
					aff.setDailywork(objectMapper.writeValueAsString(details));
				} catch (Exception ex8) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
				try {
					aff.setDailyrest(objectMapper.writeValueAsString(details));
				} catch (Exception ex9) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
				try {
					aff.setWeeklydrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex10) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
				try {
					aff.setDailyrest(objectMapper.writeValueAsString(details));
				} catch (Exception ex11) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
				try {
					aff.setPhonedistraction(objectMapper.writeValueAsString(details));
				} catch (Exception ex12) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
				try {
					aff.setSealbelt(objectMapper.writeValueAsString(details));
				} catch (Exception ex13) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
				try {
					aff.setFatigue(objectMapper.writeValueAsString(details));
				} catch (Exception ex14) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
				try {
					aff.setWeeklywork(objectMapper.writeValueAsString(details));
				} catch (Exception ex15) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
				try {
					aff.setActivefleet(objectMapper.writeValueAsString(details));
				} catch (Exception ex16) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
				try {
					aff.setNoid(objectMapper.writeValueAsString(details));
				} catch (Exception ex17) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
				try {
					aff.setInsuffisianttripduration(objectMapper.writeValueAsString(details));
				} catch (Exception ex18) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
				try {
					aff.setSmoking(objectMapper.writeValueAsString(details));
				} catch (Exception ex19) {
				}
			}
		}
		return aff;
	}

	public static Resumexceptiondriver tableaujsonExtractdriver(final Resumexceptiondriver aff,
			final com.camtrack.entities.Exception exception, final Boolean addordelete) {
		final ObjectMapper objectMapper = new ObjectMapper();
		final long nbrs = 0L;
		final String paramtypename = exception.getExceptiontype().getShortname();
		boolean record = false;
		boolean alert = false;
		boolean alarm = false;
		final Integer level = exception.getLevel().getExceptionlevelid();
		if (level == 1) {
			record = true;
		} else if (level == 2) {
			alert = true;
		} else {
			alarm = true;
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getSpeedingfnbr(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				final Leveldetails details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getHa(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				final Leveldetails details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getHb(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				final Leveldetails details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getNightdrive(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
			}
		}
		Leveldetails details;
		if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getContiniuousdrive(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailydrive(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailywork(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailyrest(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getWeeklydrive(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailyrest(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getPhonedistraction(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getSealbelt(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getFatigue(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getWeeklywork(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getActivefleet(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getNoid(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getInsuffisianttripduration(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getSmoking(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else {
			details = null;
		}
		if (!Objects.isNull(details)) {
			InfosLevel infolevel;
			if (record) {
				infolevel = details.getRecord();
			} else if (alert) {
				infolevel = details.getAlert();
			} else {
				infolevel = details.getAlarm();
			}
			if (addordelete) {
				infolevel.setDistanceunderexception(
						infolevel.getDistanceunderexception().add(exception.getDistanceunderexception()));
				infolevel.setTotaldistance(infolevel.getTotaldistance().add(exception.getTotaldistance()));
				infolevel.setTotalduration(infolevel.getTotalduration().add(exception.getTotalduration()));
				infolevel.setNbr(infolevel.getNbr() + 1L);
			} else {
				infolevel.setDistanceunderexception(
						infolevel.getDistanceunderexception().subtract(exception.getDistanceunderexception()));
				infolevel.setTotaldistance(infolevel.getTotaldistance().subtract(exception.getTotaldistance()));
				infolevel.setTotalduration(infolevel.getTotalduration().subtract(exception.getTotalduration()));
				infolevel.setNbr(infolevel.getNbr() - 1L);
			}
			if (record) {
				details.setRecord(infolevel);
			} else if (alert) {
				details.setAlert(infolevel);
			} else {
				details.setAlarm(infolevel);
			}
			if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
				try {
					aff.setSpeedingfnbr(objectMapper.writeValueAsString(details));
				} catch (Exception ex2) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
				try {
					aff.setHa(objectMapper.writeValueAsString(details));
				} catch (Exception ex3) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
				try {
					aff.setHb(objectMapper.writeValueAsString(details));
				} catch (Exception ex4) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
				try {
					aff.setNightdrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex5) {
				}
			}
			if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
				try {
					aff.setContiniuousdrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex6) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
				try {
					aff.setDailydrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex7) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
				try {
					aff.setDailywork(objectMapper.writeValueAsString(details));
				} catch (Exception ex8) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
				try {
					aff.setDailyrest(objectMapper.writeValueAsString(details));
				} catch (Exception ex9) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
				try {
					aff.setWeeklydrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex10) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
				try {
					aff.setDailyrest(objectMapper.writeValueAsString(details));
				} catch (Exception ex11) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
				try {
					aff.setPhonedistraction(objectMapper.writeValueAsString(details));
				} catch (Exception ex12) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
				try {
					aff.setSealbelt(objectMapper.writeValueAsString(details));
				} catch (Exception ex13) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
				try {
					aff.setFatigue(objectMapper.writeValueAsString(details));
				} catch (Exception ex14) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
				try {
					aff.setWeeklywork(objectMapper.writeValueAsString(details));
				} catch (Exception ex15) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
				try {
					aff.setActivefleet(objectMapper.writeValueAsString(details));
				} catch (Exception ex16) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
				try {
					aff.setNoid(objectMapper.writeValueAsString(details));
				} catch (Exception ex17) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
				try {
					aff.setInsuffisianttripduration(objectMapper.writeValueAsString(details));
				} catch (Exception ex18) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
				try {
					aff.setSmoking(objectMapper.writeValueAsString(details));
				} catch (Exception ex19) {
				}
			}
		}
		return aff;
	}

	public static Resumexceptiontransporter tableaujsonExtracttransporter(final Resumexceptiontransporter aff,
			final com.camtrack.entities.Exception exception, final Boolean addordelete) {
		final ObjectMapper objectMapper = new ObjectMapper();
		final long nbrs = 0L;
		final String paramtypename = exception.getExceptiontype().getShortname();
		boolean record = false;
		boolean alert = false;
		boolean alarm = false;
		final Integer level = exception.getLevel().getExceptionlevelid();
		if (level == 1) {
			record = true;
		} else if (level == 2) {
			alert = true;
		} else {
			alarm = true;
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getSpeedingfnbr(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				final Leveldetails details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getHa(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				final Leveldetails details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getHb(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				final Leveldetails details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getNightdrive(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
			}
		}
		Leveldetails details;
		if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getContiniuousdrive(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailydrive(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailywork(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailyrest(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getWeeklydrive(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailyrest(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getPhonedistraction(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getSealbelt(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getFatigue(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getWeeklywork(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getActivefleet(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getNoid(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getInsuffisianttripduration(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getSmoking(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else {
			details = null;
		}
		if (!Objects.isNull(details)) {
			InfosLevel infolevel;
			if (record) {
				infolevel = details.getRecord();
			} else if (alert) {
				infolevel = details.getAlert();
			} else {
				infolevel = details.getAlarm();
			}
			if (addordelete) {
				infolevel.setDistanceunderexception(
						infolevel.getDistanceunderexception().add(exception.getDistanceunderexception()));
				infolevel.setTotaldistance(infolevel.getTotaldistance().add(exception.getTotaldistance()));
				infolevel.setTotalduration(infolevel.getTotalduration().add(exception.getTotalduration()));
				infolevel.setNbr(infolevel.getNbr() + 1L);
			} else {
				infolevel.setDistanceunderexception(
						infolevel.getDistanceunderexception().subtract(exception.getDistanceunderexception()));
				infolevel.setTotaldistance(infolevel.getTotaldistance().subtract(exception.getTotaldistance()));
				infolevel.setTotalduration(infolevel.getTotalduration().subtract(exception.getTotalduration()));
				infolevel.setNbr(infolevel.getNbr() - 1L);
			}
			if (record) {
				details.setRecord(infolevel);
			} else if (alert) {
				details.setAlert(infolevel);
			} else {
				details.setAlarm(infolevel);
			}
			if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
				try {
					aff.setSpeedingfnbr(objectMapper.writeValueAsString(details));
				} catch (Exception ex2) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
				try {
					aff.setHa(objectMapper.writeValueAsString(details));
				} catch (Exception ex3) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
				try {
					aff.setHb(objectMapper.writeValueAsString(details));
				} catch (Exception ex4) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
				try {
					aff.setNightdrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex5) {
				}
			}
			if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
				try {
					aff.setContiniuousdrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex6) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
				try {
					aff.setDailydrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex7) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
				try {
					aff.setDailywork(objectMapper.writeValueAsString(details));
				} catch (Exception ex8) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
				try {
					aff.setDailyrest(objectMapper.writeValueAsString(details));
				} catch (Exception ex9) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
				try {
					aff.setWeeklydrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex10) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
				try {
					aff.setDailyrest(objectMapper.writeValueAsString(details));
				} catch (Exception ex11) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
				try {
					aff.setPhonedistraction(objectMapper.writeValueAsString(details));
				} catch (Exception ex12) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
				try {
					aff.setSealbelt(objectMapper.writeValueAsString(details));
				} catch (Exception ex13) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
				try {
					aff.setFatigue(objectMapper.writeValueAsString(details));
				} catch (Exception ex14) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
				try {
					aff.setWeeklywork(objectMapper.writeValueAsString(details));
				} catch (Exception ex15) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
				try {
					aff.setActivefleet(objectMapper.writeValueAsString(details));
				} catch (Exception ex16) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
				try {
					aff.setNoid(objectMapper.writeValueAsString(details));
				} catch (Exception ex17) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
				try {
					aff.setInsuffisianttripduration(objectMapper.writeValueAsString(details));
				} catch (Exception ex18) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
				try {
					aff.setSmoking(objectMapper.writeValueAsString(details));
				} catch (Exception ex19) {
				}
			}
		}
		return aff;
	}

	public static Resumexceptionvehicle tableaujsonExtractvehicle(final Resumexceptionvehicle aff,
			final com.camtrack.entities.Exception exception, final Boolean addordelete) {
		final ObjectMapper objectMapper = new ObjectMapper();
		final long nbrs = 0L;
		final String paramtypename = exception.getExceptiontype().getShortname();
		boolean record = false;
		boolean alert = false;
		boolean alarm = false;
		final Integer level = exception.getLevel().getExceptionlevelid();
		if (level == 1) {
			record = true;
		} else if (level == 2) {
			alert = true;
		} else {
			alarm = true;
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getSpeedingfnbr(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				final Leveldetails details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getHa(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				final Leveldetails details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getHb(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				final Leveldetails details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getNightdrive(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
			}
		}
		Leveldetails details;
		if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getContiniuousdrive(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailydrive(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailywork(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailyrest(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getWeeklydrive(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailyrest(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getPhonedistraction(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getSealbelt(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getFatigue(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getWeeklywork(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getActivefleet(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getNoid(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getInsuffisianttripduration(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getSmoking(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else {
			details = null;
		}
		if (!Objects.isNull(details)) {
			InfosLevel infolevel;
			if (record) {
				infolevel = details.getRecord();
			} else if (alert) {
				infolevel = details.getAlert();
			} else {
				infolevel = details.getAlarm();
			}
			if (addordelete) {
				infolevel.setDistanceunderexception(
						infolevel.getDistanceunderexception().add(exception.getDistanceunderexception()));
				infolevel.setTotaldistance(infolevel.getTotaldistance().add(exception.getTotaldistance()));
				infolevel.setTotalduration(infolevel.getTotalduration().add(exception.getTotalduration()));
				infolevel.setNbr(infolevel.getNbr() + 1L);
			} else {
				infolevel.setDistanceunderexception(
						infolevel.getDistanceunderexception().subtract(exception.getDistanceunderexception()));
				infolevel.setTotaldistance(infolevel.getTotaldistance().subtract(exception.getTotaldistance()));
				infolevel.setTotalduration(infolevel.getTotalduration().subtract(exception.getTotalduration()));
				infolevel.setNbr(infolevel.getNbr() - 1L);
			}
			if (record) {
				details.setRecord(infolevel);
			} else if (alert) {
				details.setAlert(infolevel);
			} else {
				details.setAlarm(infolevel);
			}
			if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
				try {
					aff.setSpeedingfnbr(objectMapper.writeValueAsString(details));
				} catch (Exception ex2) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
				try {
					aff.setHa(objectMapper.writeValueAsString(details));
				} catch (Exception ex3) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
				try {
					aff.setHb(objectMapper.writeValueAsString(details));
				} catch (Exception ex4) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
				try {
					aff.setNightdrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex5) {
				}
			}
			if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
				try {
					aff.setContiniuousdrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex6) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
				try {
					aff.setDailydrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex7) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
				try {
					aff.setDailywork(objectMapper.writeValueAsString(details));
				} catch (Exception ex8) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
				try {
					aff.setDailyrest(objectMapper.writeValueAsString(details));
				} catch (Exception ex9) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
				try {
					aff.setWeeklydrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex10) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
				try {
					aff.setDailyrest(objectMapper.writeValueAsString(details));
				} catch (Exception ex11) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
				try {
					aff.setPhonedistraction(objectMapper.writeValueAsString(details));
				} catch (Exception ex12) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
				try {
					aff.setSealbelt(objectMapper.writeValueAsString(details));
				} catch (Exception ex13) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
				try {
					aff.setFatigue(objectMapper.writeValueAsString(details));
				} catch (Exception ex14) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
				try {
					aff.setWeeklywork(objectMapper.writeValueAsString(details));
				} catch (Exception ex15) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
				try {
					aff.setActivefleet(objectMapper.writeValueAsString(details));
				} catch (Exception ex16) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
				try {
					aff.setNoid(objectMapper.writeValueAsString(details));
				} catch (Exception ex17) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
				try {
					aff.setInsuffisianttripduration(objectMapper.writeValueAsString(details));
				} catch (Exception ex18) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
				try {
					aff.setSmoking(objectMapper.writeValueAsString(details));
				} catch (Exception ex19) {
				}
			}
		}
		return aff;
	}

	public static Resumexceptionvehicle tableaujsonExtractvehicleinvalidate(final Resumexceptionvehicle aff,
			final com.camtrack.entities.Exception exception, final Boolean addordelete) {
		final ObjectMapper objectMapper = new ObjectMapper();
		final long nbrs = 0L;
		final String paramtypename = exception.getExceptiontype().getShortname();
		boolean record = false;
		boolean alert = false;
		boolean alarm = false;
		final Integer level = exception.getLevel().getExceptionlevelid();
		if (level == 1) {
			record = true;
		} else if (level == 2) {
			alert = true;
		} else {
			alarm = true;
		}
		if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getSpeedingfnbr(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				final Leveldetails details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getHa(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				final Leveldetails details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getHb(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				final Leveldetails details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
			try {
				final Leveldetails details = (Leveldetails) objectMapper.readValue(aff.getNightdrive(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
			}
		}
		Leveldetails details;
		if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getContiniuousdrive(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailydrive(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailywork(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailyrest(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getWeeklydrive(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getDailyrest(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getPhonedistraction(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getSealbelt(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getFatigue(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getWeeklywork(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getActivefleet(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getNoid(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getInsuffisianttripduration(),
						(Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else if (paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
			try {
				details = (Leveldetails) objectMapper.readValue(aff.getSmoking(), (Class) Leveldetails.class);
			} catch (Exception ex) {
				details = null;
			}
		} else {
			details = null;
		}
		if (!Objects.isNull(details)) {
			InfosLevel infolevel;
			if (record) {
				infolevel = details.getRecord();
			} else if (alert) {
				infolevel = details.getAlert();
			} else {
				infolevel = details.getAlarm();
			}
			if (addordelete) {
				infolevel.setDistanceunderexception(
						infolevel.getDistanceunderexception().add(exception.getDistanceunderexception()));
				infolevel.setTotaldistance(infolevel.getTotaldistance().add(exception.getTotaldistance()));
				infolevel.setTotalduration(infolevel.getTotalduration().add(exception.getTotalduration()));
				infolevel.setNbr(infolevel.getNbr() + 1L);
			} else {
				infolevel.setDistanceunderexception(
						infolevel.getDistanceunderexception().subtract(exception.getDistanceunderexception()));
				infolevel.setTotaldistance(infolevel.getTotaldistance().subtract(exception.getTotaldistance()));
				infolevel.setTotalduration(infolevel.getTotalduration().subtract(exception.getTotalduration()));
				infolevel.setNbr(infolevel.getNbr() - 1L);
			}
			if (record) {
				details.setRecord(infolevel);
			} else if (alert) {
				details.setAlert(infolevel);
			} else {
				details.setAlarm(infolevel);
			}
			if (paramtypename.trim().equalsIgnoreCase(Utils.SpeedingShorname)) {
				try {
					aff.setSpeedingfnbr(objectMapper.writeValueAsString(details));
				} catch (Exception ex2) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshAccelerationShorname)) {
				try {
					aff.setHa(objectMapper.writeValueAsString(details));
				} catch (Exception ex3) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.HarshBrackingShorname)) {
				try {
					aff.setHb(objectMapper.writeValueAsString(details));
				} catch (Exception ex4) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.NightDrivenShorname)) {
				try {
					aff.setNightdrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex5) {
				}
			}
			if (paramtypename.trim().equalsIgnoreCase(Utils.ContiniousDriveShorname)) {
				try {
					aff.setContiniuousdrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex6) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyDriveShorname)) {
				try {
					aff.setDailydrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex7) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyWordkShorname)) {
				try {
					aff.setDailywork(objectMapper.writeValueAsString(details));
				} catch (Exception ex8) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.DailyRestShorname)) {
				try {
					aff.setDailyrest(objectMapper.writeValueAsString(details));
				} catch (Exception ex9) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyDriveShorname)) {
				try {
					aff.setWeeklydrive(objectMapper.writeValueAsString(details));
				} catch (Exception ex10) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyRestShorname)) {
				try {
					aff.setDailyrest(objectMapper.writeValueAsString(details));
				} catch (Exception ex11) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.PhoneDistractionShorname)) {
				try {
					aff.setPhonedistraction(objectMapper.writeValueAsString(details));
				} catch (Exception ex12) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.SealBeltShorname)) {
				try {
					aff.setSealbelt(objectMapper.writeValueAsString(details));
				} catch (Exception ex13) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.FatigueShorname)) {
				try {
					aff.setFatigue(objectMapper.writeValueAsString(details));
				} catch (Exception ex14) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.WeeklyWordkShorname)) {
				try {
					aff.setWeeklywork(objectMapper.writeValueAsString(details));
				} catch (Exception ex15) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.ActiveFleetShorname)) {
				try {
					aff.setActivefleet(objectMapper.writeValueAsString(details));
				} catch (Exception ex16) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.NoIDShorname)) {
				try {
					aff.setNoid(objectMapper.writeValueAsString(details));
				} catch (Exception ex17) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.InsufficientTripDurationShorname)) {
				try {
					aff.setInsuffisianttripduration(objectMapper.writeValueAsString(details));
				} catch (Exception ex18) {
				}
			} else if (paramtypename.trim().equalsIgnoreCase(Utils.SmokingShorname)) {
				try {
					aff.setSmoking(objectMapper.writeValueAsString(details));
				} catch (Exception ex19) {
				}
			}
		}
		return aff;
	}

	public static String toString(final Object o, final String nullDefault) {
		return Objects.isNull(o) ? nullDefault : o.toString();
	}

	public static String totalFuelConsumedString() {
		return "Total Fuel Consumed";
	}

	public static Double truncate(final Double numbers) {
		double number = 0.0;
		if (Objects.isNull(numbers)) {
			return numbers;
		}
		number = numbers;
		final int integerPart = numbers.intValue();
		Double fractionalPart = numbers - integerPart;
		fractionalPart *= 1000000.0;
		final int fractPart = fractionalPart.intValue();
		fractionalPart = integerPart + fractPart / 100.0;
		return fractionalPart;
	}

	public static String updatelogsinfos(final String log, String oldinfos, String newdata, final String params) {
		String result = "";
		boolean bool = false;
		if (isFloat(oldinfos)) {
			final Double value = Double.valueOf(oldinfos);
			oldinfos = truncate(value).toString();
		} else {
			try {
				final BigDecimal money = new BigDecimal(oldinfos.replaceAll(",", ""));
				result = String.valueOf(money.setScale(5, RoundingMode.CEILING));
				bool = true;
			} catch (Exception ex) {
				bool = false;
			}
			if (bool) {
				oldinfos = result;
			}
		}
		if (isFloat(newdata)) {
			final Double value = Double.valueOf(newdata);
			newdata = truncate(value).toString();
		} else {
			try {
				final BigDecimal money = new BigDecimal(newdata.replaceAll(",", ""));
				result = String.valueOf(money.setScale(5, RoundingMode.CEILING));
				bool = true;
			} catch (Exception ex) {
				bool = false;
			}
			if (bool) {
				newdata = result;
			}
		}
		if (!Objects.isNull(oldinfos) && !Objects.isNull(newdata)
				&& !oldinfos.trim().equalsIgnoreCase(newdata.trim())) {
			result = log + " " + Utils.NewinitLine + " Old addressline1 " + Utils.boldbaliseinit + oldinfos
					+ Utils.boldbalisend + " New addressline1 " + Utils.boldbaliseinit + newdata + Utils.boldbalisend;
			result = result.replaceAll("addressline1", capitalize(params));
		} else if (Objects.isNull(oldinfos) && !Objects.isNull(newdata)) {
			result = log + " " + Utils.NewinitLine + " addressline1 " + Utils.boldbaliseinit + newdata
					+ Utils.boldbalisend;
			result = result.replaceAll("addressline1", capitalize(params));
		} else {
			result = log;
		}
		return result;
	}

	public static Short UserAccessTypeconfig() {
		return Short.valueOf("11");
	}

	

	public static Short UserTypeconfig() {
		return Short.valueOf("5");
	}

	public static List<VehicleBean> vehtovehbeans(final List<Vehicle> listvehicles) {
		final List<VehicleBean> result = new ArrayList<>();
		for (final Vehicle vehicle : listvehicles) {
			final VehicleBean vehicleBean = new VehicleBean();
			vehicleBean.setRegno(vehicle.getRegno());
			final Transporter trans = vehicle.getTransporterid();
			vehicleBean.setTransporterid(trans.getTransporterid());
			vehicleBean.setTransportername(trans.getName());
			vehicleBean.setUnitid(vehicle.getUnitid());
			vehicleBean.setVehicledesc(vehicle.getVehicledesc());
			vehicleBean.setVehicleid(vehicle.getVehicleid());
			vehicleBean.setVehicleuniqueid(vehicle.getVehicleuniqueid());
			vehicleBean.setStatus(vehicle.getStatus());
			try {
				vehicleBean.setTpv(vehicle.getVehicletypeid().getNames());
			} catch (Exception ex) {
				vehicleBean.setTpv("LV");
			}
			result.add(vehicleBean);
		}
		return result;
	}

	public static Long worktime(final Drivertrips worktimereport, final Date date1, final Date date2) {
		long totalHours = 0L;
		try {
			if (worktimereport.getStartdatetime().after(date1) && worktimereport.getEnddatetime().after(date2)
					&& worktimereport.getStartdatetime().before(date2)) {
				totalHours += Math.abs(secondesBetweenDate(worktimereport.getStartdatetime(), date2));
			} else if (worktimereport.getStartdatetime().after(date1)
					&& worktimereport.getEnddatetime().before(date2)) {
				totalHours += Math
						.abs(secondesBetweenDate(worktimereport.getStartdatetime(), worktimereport.getEnddatetime()));
			} else if (worktimereport.getStartdatetime().before(date1) && worktimereport.getEnddatetime().after(date1)
					&& worktimereport.getEnddatetime().before(date2)) {
				totalHours += Math.abs(secondesBetweenDate(date1, worktimereport.getEnddatetime()));
			} else if (worktimereport.getStartdatetime().before(date1)
					&& worktimereport.getEnddatetime().after(date2)) {
				totalHours += Math.abs(secondesBetweenDate(date1, date2));
			}
		} catch (Exception ex) {
			totalHours = 0L;
		}
		return totalHours;
	}

	public static Boolean NullToBolean(Boolean record) {
		if (Objects.isNull(record)) {
			return false;
		} else {
			return record;
		}
	}

	public static CreateUsers users(User users) {
		CreateUsers create = new CreateUsers();
		create.setContacts(users.getContacts());
		create.setEmailid(users.getEmailid());
		create.setMfa(users.getMfa());
		create.setName(users.getName());
		create.setUserid(users.getUserid());
		create.setUsername(users.getUsername());
		try {
			create.setLanguageid(users.getLanguageid().getName());
		} catch (Exception ex) {
			create.setLanguageid("fr");
		}
		return create;
	}
	
	public static UserBean userFromUserBean(User user)
	{
		UserBean bean=new UserBean();
		bean.setContacts(user.getContacts());
		bean.setEmail(user.getEmailid());
		bean.setEnabled(user.getEnabled());
		bean.setFirstconn(user.isFirst());
		bean.setMfa(user.getMfa());
		bean.setName(user.getName());
		bean.setUnblock(user.isAccountNonLocked());
		bean.setUserid(user.getUserid());
		bean.setSexe(user.getSexe());
		try {
			final Reelroleusers roles = user.getReelrole();
			final Userrole typeuser = roles.getIdtyperole();
			bean.setRoleid(roles.getIds());
			bean.setRolename(roles.getRolenames());
			bean.setTyperoleid(typeuser.getUserroleid());
			bean.setEntitiestype(typeuser.getName());
		} catch (Exception ex) {
		}
		return bean;
	}
}
