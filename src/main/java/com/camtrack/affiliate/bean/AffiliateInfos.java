//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.affiliate.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.camtrack.config.Utils;

public class AffiliateInfos implements Serializable {
	private static final long serialVersionUID = 1L;
	String adresse1;
	String adresse2;
	Integer affiliateid;
	Integer affiliateId;
	Boolean alarmlevel;
	Boolean alertlevel;
	Integer cityid;
	Integer countryid;
	Integer customerid;
	String email;
	List<Integer> exceptiontypeidfromclients;
	String gpssystempassword;
	String gpssystemusername;
	String gpstoken;
	String gpstrackingsystem;
	String nameaffiliate;
	Boolean recordlevel;
	Integer stateid;
	String telephone;
	String timezone;

	public String getAdresse1() {
		return Utils.StringEscape(this.adresse1);
	}

	public String getAdresse2() {
		return Utils.StringEscape(this.adresse2);
	}

	public Integer getAffiliateid() {
		return this.affiliateid;
	}

	public Integer getAffiliateId() {
		return this.affiliateId;
	}

	public Boolean getAlarmlevel() {
		if (Objects.isNull(this.alarmlevel)) {
			return false;
		}
		return this.alarmlevel;
	}

	public Boolean getAlertlevel() {
		if (Objects.isNull(this.alertlevel)) {
			return false;
		}
		return this.alertlevel;
	}

	public Integer getCityid() {
		return this.cityid;
	}

	public Integer getCountryid() {
		return this.countryid;
	}

	public Integer getCustomerid() {
		return this.customerid;
	}

	public String getEmail() {
		return Utils.StringEscape(this.email);
	}

	public List<Integer> getExceptiontypeidfromclients() {
		return this.exceptiontypeidfromclients;
	}

	public String getGpssystempassword() {
		return Utils.StringEscape(this.gpssystempassword);
	}

	public String getGpssystemusername() {
		return Utils.StringEscape(this.gpssystemusername);
	}

	public String getGpstoken() {
		return Utils.StringEscape(this.gpstoken);
	}

	public String getGpstrackingsystem() {
		return Utils.StringEscape(this.gpstrackingsystem);
	}

	public String getNameaffiliate() {
		return Utils.StringEscape(this.nameaffiliate);
	}

	public Boolean getRecordlevel() {
		if (Objects.isNull(this.recordlevel)) {
			return false;
		}
		return this.recordlevel;
	}

	public Integer getStateid() {
		return this.stateid;
	}

	public String getTelephone() {
		return Utils.StringEscape(this.telephone);
	}

	public String getTimezone() {
		return Utils.StringEscape(this.timezone);
	}

	public void setAdresse1(final String adresse1) {
		this.adresse1 = adresse1;
	}

	public void setAdresse2(final String adresse2) {
		this.adresse2 = adresse2;
	}

	public void setAffiliateid(final Integer affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setAffiliateId(final Integer affiliateId) {
		this.affiliateId = affiliateId;
	}

	public void setAlarmlevel(final Boolean alarmlevel) {
		if (Objects.isNull(alarmlevel)) {
			this.alarmlevel = false;
		} else {
			this.alarmlevel = alarmlevel;
		}
	}

	public void setAlertlevel(final Boolean alertlevel) {
		if (Objects.isNull(alertlevel)) {
			this.alertlevel = false;
		} else {
			this.alertlevel = alertlevel;
		}
	}

	public void setCityid(final Integer cityid) {
		this.cityid = cityid;
	}

	public void setCountryid(final Integer countryid) {
		this.countryid = countryid;
	}

	public void setCustomerid(final Integer customerid) {
		this.customerid = customerid;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setExceptiontypeidfromclients(final List<Integer> exceptiontypeidfromclients) {
		this.exceptiontypeidfromclients = exceptiontypeidfromclients;
	}

	public void setGpssystempassword(final String gpssystempassword) {
		this.gpssystempassword = gpssystempassword;
	}

	public void setGpssystemusername(final String gpssystemusername) {
		this.gpssystemusername = gpssystemusername;
	}

	public void setGpstoken(final String gpstoken) {
		this.gpstoken = gpstoken;
	}

	public void setGpstrackingsystem(final String gpstrackingsystem) {
		this.gpstrackingsystem = gpstrackingsystem;
	}

	public void setNameaffiliate(final String nameaffiliate) {
		this.nameaffiliate = nameaffiliate;
	}

	public void setRecordlevel(final Boolean recordlevel) {
		if (Objects.isNull(recordlevel)) {
			this.recordlevel = false;
		} else {
			this.recordlevel = recordlevel;
		}
	}

	public void setStateid(final Integer stateid) {
		this.stateid = stateid;
	}

	public void setTelephone(final String telephone) {
		this.telephone = telephone;
	}

	public void setTimezone(final String timezone) {
		this.timezone = timezone;
	}
}
