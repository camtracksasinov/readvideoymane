//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.bean;

import java.io.Serializable;
import java.util.Date;

import com.camtrack.config.Utils;

public class Transporter implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer affiliateid;
	private Integer countryid;
	private Date createdon;
	private String name;
	private Integer status;
	private Integer transporterid;
	private String transportuniqueid;

	public Transporter() {
	}

	public Transporter(final Integer transporterid) {
		this.transporterid = transporterid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Transporter)) {
			return false;
		}
		final Transporter other = (Transporter) object;
		return (this.transporterid != null || other.transporterid == null)
				&& (this.transporterid == null || this.transporterid.equals(other.transporterid));
	}

	public Integer getAffiliateid() {
		return this.affiliateid;
	}

	public Integer getCountryid() {
		return this.countryid;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public String getName() {
		return Utils.StringEscape(this.name);
	}

	public Integer getStatus() {
		return this.status;
	}

	public Integer getTransporterid() {
		return this.transporterid;
	}

	public String getTransportuniqueid() {
		return Utils.StringEscape(this.transportuniqueid);
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.transporterid != null) ? this.transporterid.hashCode() : 0);
		return hash;
	}

	public void setAffiliateid(final Integer affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setCountryid(final Integer countryid) {
		this.countryid = countryid;
	}

	public void setCreatedon(final Date createdon) {
		this.createdon = createdon;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public void setTransporterid(final Integer transporterid) {
		this.transporterid = transporterid;
	}

	public void setTransportuniqueid(final String transportuniqueid) {
		this.transportuniqueid = transportuniqueid;
	}

	@Override
	public String toString() {
		return "Transporter [transporterid=" + this.transporterid + ", affiliateid=" + this.affiliateid + ", name="
				+ this.name + ", countryid=" + this.countryid + ", status=" + this.status + ", transportuniqueid="
				+ this.transportuniqueid + ", createdon=" + this.createdon + "]";
	}
}
