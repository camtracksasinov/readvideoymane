//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.bean;

import java.io.Serializable;
import java.util.Date;

import com.camtrack.config.Utils;

public class Driver implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer affiliateid;
	private Date createdon;
	private Integer driverid;
	private String driverkeycode;
	private String name;
	private Integer status;
	private Integer transporterid;
	private Integer vehicleid;

	public Driver() {
	}

	public Driver(final Integer driverid) {
		this.driverid = driverid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Driver)) {
			return false;
		}
		final Driver other = (Driver) object;
		return (this.driverid != null || other.driverid == null)
				&& (this.driverid == null || this.driverid.equals(other.driverid));
	}

	public Integer getAffiliateid() {
		return this.affiliateid;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public Integer getDriverid() {
		return this.driverid;
	}

	public String getDriverkeycode() {
		return Utils.StringEscape(this.driverkeycode);
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

	public Integer getVehicleid() {
		return this.vehicleid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.driverid != null) ? this.driverid.hashCode() : 0);
		return hash;
	}

	public void setAffiliateid(final Integer affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setCreatedon(final Date createdon) {
		this.createdon = createdon;
	}

	public void setDriverid(final Integer driverid) {
		this.driverid = driverid;
	}

	public void setDriverkeycode(final String driverkeycode) {
		this.driverkeycode = driverkeycode;
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

	public void setVehicleid(final Integer vehicleid) {
		this.vehicleid = vehicleid;
	}

	@Override
	public String toString() {
		return "Driver [driverid=" + this.driverid + ", name=" + this.name + ", status=" + this.status
				+ ", affiliateid=" + this.affiliateid + ", transporterid=" + this.transporterid + ", driverkeycode="
				+ this.driverkeycode + ", vehicleid=" + this.vehicleid + ", createdon=" + this.createdon + "]";
	}
}
