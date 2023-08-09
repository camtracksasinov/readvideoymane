//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class CustomerDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private String addressline1;
	private String addressline2;
	private Boolean alarmlevel;
	private Boolean alertlevel;
	private Integer customerid;
	private String email;
	private List<Integer> listtypealert;
	private String logourl;
	private String name;
	private Integer numberofusers;
	private Boolean recordlevel;
	private Integer status;
	private String telephone;

	public CustomerDao() {
		this.numberofusers = 0;
	}

	public String getAddressline1() {
		return this.addressline1;
	}

	public String getAddressline2() {
		return this.addressline2;
	}

	public Boolean getAlarmlevel() {
		return this.alarmlevel;
	}

	public Boolean getAlertlevel() {
		return this.alertlevel;
	}

	public Integer getCustomerid() {
		return this.customerid;
	}

	public String getEmail() {
		return this.email;
	}

	public List<Integer> getListtypealert() {
		return this.listtypealert;
	}

	public String getLogourl() {
		return this.logourl;
	}

	public String getName() {
		return this.name;
	}

	public Integer getNumberofusers() {
		return this.numberofusers;
	}

	public Boolean getRecordlevel() {
		return this.recordlevel;
	}

	public Integer getStatus() {
		return this.status;
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

	public void setCustomerid(final Integer customerid) {
		this.customerid = customerid;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setListtypealert(final List<Integer> listtypealert) {
		this.listtypealert = listtypealert;
	}

	public void setLogourl(final String logourl) {
		this.logourl = logourl;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setNumberofusers(final Integer numberofusers) {
		this.numberofusers = numberofusers;
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
}
