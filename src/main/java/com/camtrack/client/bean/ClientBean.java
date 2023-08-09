//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.client.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.camtrack.config.Utils;

public class ClientBean implements Serializable {
	private static final long serialVersionUID = 1L;
	String addressline1;
	String addressline2;
	Boolean alarmlevel;
	Boolean alertlevel;
	Integer cityid;
	Integer clientId;
	Integer countryid;
	String email;
	List<Integer> exceptiontypeid;
	Integer languageid;
	String nameclient;
	Boolean recordlevel;
	Integer stateid;
	String telephone;

	public String getAddressline1() {
		return this.addressline1;
	}

	public String getAddressline2() {
		return this.addressline2;
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

	public Integer getClientId() {
		return this.clientId;
	}

	public Integer getCountryid() {
		return this.countryid;
	}

	public String getEmail() {
		return this.email;
	}

	public List<Integer> getExceptiontypeid() {
		return this.exceptiontypeid;
	}

	public Integer getLanguageid() {
		return this.languageid;
	}

	public String getNameclient() {
		return Utils.StringEscape(this.nameclient);
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
		return this.telephone;
	}

	public void setAddressline1(final String addressline1) {
		this.addressline1 = addressline1;
	}

	public void setAddressline2(final String addressline2) {
		this.addressline2 = addressline2;
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

	public void setClientId(final Integer clientId) {
		this.clientId = clientId;
	}

	public void setCountryid(final Integer countryid) {
		this.countryid = countryid;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setExceptiontypeid(final List<Integer> exceptiontypeid) {
		this.exceptiontypeid = exceptiontypeid;
	}

	public void setLanguageid(final Integer languageid) {
		this.languageid = languageid;
	}

	public void setNameclient(final String nameclient) {
		this.nameclient = nameclient;
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
}
