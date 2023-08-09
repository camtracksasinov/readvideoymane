//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.affiliate.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.camtrack.config.Utils;

public class AffiliateBean implements Serializable {
	private static final long serialVersionUID = 1L;
	String adresse1;
	String adresse2;
	Integer affiliateid;
	private Boolean alarmlevel;
	private Boolean alertlevel;
	Integer customerid;
	String email;
	List<Integer> listypealertid;
	String name;
	private Boolean recordlevel;
	private Integer status;
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

	public Integer getCustomerid() {
		return this.customerid;
	}

	public String getEmail() {
		return Utils.StringEscape(this.email);
	}

	public List<Integer> getListypealertid() {
		return this.listypealertid;
	}

	public String getName() {
		return Utils.StringEscape(this.name);
	}

	public Boolean getRecordlevel() {
		if (Objects.isNull(this.recordlevel)) {
			return false;
		}
		return this.recordlevel;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getTelephone() {
		return Utils.StringEscape(this.telephone);
	}

	public String getTimezone() {
		return this.timezone;
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

	public void setCustomerid(final Integer customerid) {
		this.customerid = customerid;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setListypealertid(final List<Integer> listypealertid) {
		this.listypealertid = listypealertid;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setRecordlevel(final Boolean recordlevel) {
		if (Objects.isNull(recordlevel)) {
			this.recordlevel = false;
		} else {
			this.recordlevel = recordlevel;
		}
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public void setTelephone(final String telephone) {
		this.telephone = telephone;
	}

	public void setTimezone(final String timezone) {
		this.timezone = timezone;
	}
}
