//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class VehicledriverPK implements Serializable {
	@Basic(optional = false)
	@Column(name = "driverid")
	private int driverid;
	@Basic(optional = false)
	@Column(name = "regdatetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date regdatetime;
	@Basic(optional = false)
	@Column(name = "vehicleid")
	private int vehicleid;

	public VehicledriverPK() {
	}

	public VehicledriverPK(final int vehicleid, final int driverid, final Date regdatetime) {
		this.vehicleid = vehicleid;
		this.driverid = driverid;
		this.regdatetime = regdatetime;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof VehicledriverPK)) {
			return false;
		}
		final VehicledriverPK other = (VehicledriverPK) object;
		return this.vehicleid == other.vehicleid && this.driverid == other.driverid
				&& (this.regdatetime != null || other.regdatetime == null)
				&& (this.regdatetime == null || this.regdatetime.equals(other.regdatetime));
	}

	public int getDriverid() {
		return this.driverid;
	}

	public Date getRegdatetime() {
		return this.regdatetime;
	}

	public int getVehicleid() {
		return this.vehicleid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += this.vehicleid;
		hash += this.driverid;
		hash += ((this.regdatetime != null) ? this.regdatetime.hashCode() : 0);
		return hash;
	}

	public void setDriverid(final int driverid) {
		this.driverid = driverid;
	}

	public void setRegdatetime(final Date regdatetime) {
		this.regdatetime = regdatetime;
	}

	public void setVehicleid(final int vehicleid) {
		this.vehicleid = vehicleid;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.VehicledriverPK[ vehicleid=" + this.vehicleid + ", driverid=" + this.driverid
				+ ", regdatetime=" + this.regdatetime + " ]";
	}
}
