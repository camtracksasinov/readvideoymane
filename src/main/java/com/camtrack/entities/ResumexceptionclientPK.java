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
public class ResumexceptionclientPK implements Serializable {
	@Basic(optional = false)
	@Column(name = "clientid")
	private int clientid;
	@Basic(optional = false)
	@Column(name = "dates")
	@Temporal(TemporalType.DATE)
	private Date dates;
	@Basic(optional = false)
	@Column(name = "temporalites")
	private int temporalites;

	public ResumexceptionclientPK() {
	}

	public ResumexceptionclientPK(final int clientid, final Date dates, final int temporalites) {
		this.clientid = clientid;
		this.dates = dates;
		this.temporalites = temporalites;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof ResumexceptionclientPK)) {
			return false;
		}
		final ResumexceptionclientPK other = (ResumexceptionclientPK) object;
		return this.clientid == other.clientid && (this.dates != null || other.dates == null)
				&& (this.dates == null || this.dates.equals(other.dates)) && this.temporalites == other.temporalites;
	}

	public int getClientid() {
		return this.clientid;
	}

	public Date getDates() {
		return this.dates;
	}

	public int getTemporalites() {
		return this.temporalites;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += this.clientid;
		hash += ((this.dates != null) ? this.dates.hashCode() : 0);
		hash += this.temporalites;
		return hash;
	}

	public void setClientid(final int clientid) {
		this.clientid = clientid;
	}

	public void setDates(final Date dates) {
		this.dates = dates;
	}

	public void setTemporalites(final int temporalites) {
		this.temporalites = temporalites;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.ResumexceptionclientPK[ clientid=" + this.clientid + ", dates=" + this.dates
				+ ", temporalites=" + this.temporalites + " ]";
	}
}
