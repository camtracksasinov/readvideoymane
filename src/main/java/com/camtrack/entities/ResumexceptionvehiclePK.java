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
public class ResumexceptionvehiclePK implements Serializable {
	@Basic(optional = false)
	@Column(name = "dates")
	@Temporal(TemporalType.DATE)
	private Date dates;
	@Basic(optional = false)
	@Column(name = "temporalites")
	private int temporalites;
	@Basic(optional = false)
	@Column(name = "vehicleid")
	private int vehicleid;

	public ResumexceptionvehiclePK() {
	}

	public ResumexceptionvehiclePK(final int vehicleid, final Date dates, final int temporalites) {
		this.vehicleid = vehicleid;
		this.dates = dates;
		this.temporalites = temporalites;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof ResumexceptionvehiclePK)) {
			return false;
		}
		final ResumexceptionvehiclePK other = (ResumexceptionvehiclePK) object;
		return this.vehicleid == other.vehicleid && (this.dates != null || other.dates == null)
				&& (this.dates == null || this.dates.equals(other.dates)) && this.temporalites == other.temporalites;
	}

	public Date getDates() {
		return this.dates;
	}

	public int getTemporalites() {
		return this.temporalites;
	}

	public int getVehicleid() {
		return this.vehicleid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += this.vehicleid;
		hash += ((this.dates != null) ? this.dates.hashCode() : 0);
		hash += this.temporalites;
		return hash;
	}

	public void setDates(final Date dates) {
		this.dates = dates;
	}

	public void setTemporalites(final int temporalites) {
		this.temporalites = temporalites;
	}

	public void setVehicleid(final int vehicleid) {
		this.vehicleid = vehicleid;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.ResumexceptionvehiclePK[ vehicleid=" + this.vehicleid + ", dates=" + this.dates
				+ ", temporalites=" + this.temporalites + " ]";
	}
}
