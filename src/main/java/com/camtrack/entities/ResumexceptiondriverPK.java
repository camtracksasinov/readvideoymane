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
public class ResumexceptiondriverPK implements Serializable {
	@Basic(optional = false)
	@Column(name = "dates")
	@Temporal(TemporalType.DATE)
	private Date dates;
	@Basic(optional = false)
	@Column(name = "driverid")
	private int driverid;
	@Basic(optional = false)
	@Column(name = "temporalites")
	private int temporalites;

	public ResumexceptiondriverPK() {
	}

	public ResumexceptiondriverPK(final int driverid, final Date dates, final int temporalites) {
		this.driverid = driverid;
		this.dates = dates;
		this.temporalites = temporalites;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof ResumexceptiondriverPK)) {
			return false;
		}
		final ResumexceptiondriverPK other = (ResumexceptiondriverPK) object;
		return this.driverid == other.driverid && (this.dates != null || other.dates == null)
				&& (this.dates == null || this.dates.equals(other.dates)) && this.temporalites == other.temporalites;
	}

	public Date getDates() {
		return this.dates;
	}

	public int getDriverid() {
		return this.driverid;
	}

	public int getTemporalites() {
		return this.temporalites;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += this.driverid;
		hash += ((this.dates != null) ? this.dates.hashCode() : 0);
		hash += this.temporalites;
		return hash;
	}

	public void setDates(final Date dates) {
		this.dates = dates;
	}

	public void setDriverid(final int driverid) {
		this.driverid = driverid;
	}

	public void setTemporalites(final int temporalites) {
		this.temporalites = temporalites;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.ResumexceptiondriverPK[ driverid=" + this.driverid + ", dates=" + this.dates
				+ ", temporalites=" + this.temporalites + " ]";
	}
}
