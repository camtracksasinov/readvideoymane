//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.config;

public class ConValue {
	public static Short AdmintNumber;
	public static Short AgentNumber;
	public static Short DatatypeFormula;
	public static Short DatatypeRegularPosition;
	public static Short DatatypeSOS;
	public static String ErrorHttps;
	public static final String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_DATE_Without_Hour = "yyyy-MM-dd";
	public static final Integer LENGTHBCRYT;
	public static final String Notification = "Vous etes sorti de votre zone de confinemment sis a :ZONEEXIT bien vouloir y retourner";
	public static final String Title_Notification = "Sortie de zone de confinement";
	public static Short UserNumber;

	static {
		LENGTHBCRYT = 4;
		ConValue.AgentNumber = 1;
		ConValue.AdmintNumber = 2;
		ConValue.UserNumber = 0;
		ConValue.DatatypeRegularPosition = 3;
		ConValue.DatatypeSOS = 2;
		ConValue.DatatypeFormula = 1;
		ConValue.ErrorHttps = "Error";
	}

	public static Integer getLengthbcryt() {
		return ConValue.LENGTHBCRYT;
	}
}
