//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.bean;

import java.io.Serializable;
import java.util.Date;

import com.camtrack.config.Utils;

public class Customeraffiliate implements Serializable {
	private static final long serialVersionUID = 1L;
	private String addressline1;
	private String addressline2;
	private Integer affiliateid;
	private String afftimezone;
	private Integer cityid;
	private Integer countryid;
	private Integer createdby;
	private Date createdon;
	private Integer customerid;
	private String email;
	private String gpssystempassword;
	private String gpssystemusername;
	private String gpstoken;
	private String gpstrackingsystem;
	private String name;
	private Integer stateid;
	private Integer status;
	private String telephone;
	private Integer updatedby;
	private Date updatedon;

	public Customeraffiliate() {
	}

	public Customeraffiliate(final Integer affiliateid) {
		this.affiliateid = affiliateid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Customeraffiliate)) {
			return false;
		}
		final Customeraffiliate other = (Customeraffiliate) object;
		return (this.affiliateid != null || other.affiliateid == null)
				&& (this.affiliateid == null || this.affiliateid.equals(other.affiliateid));
	}

	public String getAddressline1() {
		return Utils.StringEscape(this.addressline1);
	}

	public String getAddressline2() {
		return Utils.StringEscape(this.addressline2);
	}

	public Integer getAffiliateid() {
		return this.affiliateid;
	}

	public String getAfftimezone() {
		return Utils.StringEscape(this.afftimezone);
	}

	public Integer getCityid() {
		return this.cityid;
	}

	public Integer getCountryid() {
		return this.countryid;
	}

	public Integer getCreatedby() {
		return this.createdby;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public Integer getCustomerid() {
		return this.customerid;
	}

	public String getEmail() {
		return Utils.StringEscape(this.email);
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

	// Utils.StringEscape(startdate), Utils.StringEscape(enddate)
	public String getName() {
		return Utils.StringEscape(this.name);
	}

	public Integer getStateid() {
		return this.stateid;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getTelephone() {
		return Utils.StringEscape(this.telephone);
	}

	public Integer getUpdatedby() {
		return this.updatedby;
	}

	public Date getUpdatedon() {
		return this.updatedon;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.affiliateid != null) ? this.affiliateid.hashCode() : 0);
		return hash;
	}

	public void setAddressline1(final String addressline1) {
		this.addressline1 = addressline1;
	}

	public void setAddressline2(final String addressline2) {
		this.addressline2 = addressline2;
	}

	public void setAffiliateid(final Integer affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setAfftimezone(final String afftimezone) {
		this.afftimezone = afftimezone;
	}

	public void setCityid(final Integer cityid) {
		this.cityid = cityid;
	}

	public void setCountryid(final Integer countryid) {
		this.countryid = countryid;
	}

	public void setCreatedby(final Integer createdby) {
		this.createdby = createdby;
	}

	public void setCreatedon(final Date createdon) {
		this.createdon = createdon;
	}

	public void setCustomerid(final Integer customerid) {
		this.customerid = customerid;
	}

	public void setEmail(final String email) {
		this.email = email;
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

	public void setName(final String name) {
		this.name = name;
	}

	public void setStateid(final Integer stateid) {
		this.stateid = stateid;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public void setTelephone(final String telephone) {
		this.telephone = telephone;
	}

	public void setUpdatedby(final Integer updatedby) {
		this.updatedby = updatedby;
	}

	public void setUpdatedon(final Date updatedon) {
		this.updatedon = updatedon;
	}

	@Override
	public String toString() {
		return "Customeraffiliate [affiliateid=" + this.affiliateid + ", customerid=" + this.customerid + ", name="
				+ this.name + ", addressline1=" + this.addressline1 + ", addressline2=" + this.addressline2
				+ ", cityid=" + this.cityid + ", stateid=" + this.stateid + ", countryid=" + this.countryid + ", email="
				+ this.email + ", telephone=" + this.telephone + ", status=" + this.status + ", createdon="
				+ this.createdon + ", createdby=" + this.createdby + ", updatedon=" + this.updatedon + ", updatedby="
				+ this.updatedby + ", gpstrackingsystem=" + this.gpstrackingsystem + ", gpssystemusername="
				+ this.gpssystemusername + ", gpssystempassword=" + this.gpssystempassword + ", gpstoken="
				+ this.gpstoken + ", afftimezone=" + this.afftimezone + "]";
	}
}
