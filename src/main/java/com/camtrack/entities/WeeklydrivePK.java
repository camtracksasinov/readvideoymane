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
public class WeeklydrivePK implements Serializable {
	@Basic(optional = false)
	@Column(name = "driverid")
	private int driverid;
	@Basic(optional = false)
	@Column(name = "startdatetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startdatetime;
	@Basic(optional = false)
	@Column(name = "vehicleid")
	private int vehicleid;

	public WeeklydrivePK() {
	}

	public WeeklydrivePK(final int vehicleid, final int driverid, final Date startdatetime) {
		this.vehicleid = vehicleid;
		this.driverid = driverid;
		this.startdatetime = startdatetime;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof WeeklydrivePK)) {
			return false;
		}
		final WeeklydrivePK other = (WeeklydrivePK) object;
		return this.vehicleid == other.vehicleid && this.driverid == other.driverid
				&& (this.startdatetime != null || other.startdatetime == null)
				&& (this.startdatetime == null || this.startdatetime.equals(other.startdatetime));
	}

	public int getDriverid() {
		return this.driverid;
	}

	public Date getStartdatetime() {
		return this.startdatetime;
	}

	public int getVehicleid() {
		return this.vehicleid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += this.vehicleid;
		hash += this.driverid;
		hash += ((this.startdatetime != null) ? this.startdatetime.hashCode() : 0);
		return hash;
	}

	public void setDriverid(final int driverid) {
		this.driverid = driverid;
	}

	public void setStartdatetime(final Date startdatetime) {
		this.startdatetime = startdatetime;
	}

	public void setVehicleid(final int vehicleid) {
		this.vehicleid = vehicleid;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.WeeklydrivePK[ vehicleid=" + this.vehicleid + ", driverid=" + this.driverid
				+ ", startdatetime=" + this.startdatetime + " ]";
	}
}
