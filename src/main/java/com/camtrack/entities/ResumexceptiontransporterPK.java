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
public class ResumexceptiontransporterPK implements Serializable {
	@Basic(optional = false)
	@Column(name = "dates")
	@Temporal(TemporalType.DATE)
	private Date dates;
	@Basic(optional = false)
	@Column(name = "temporalites")
	private int temporalites;
	@Basic(optional = false)
	@Column(name = "transporterid")
	private int transporterid;

	public ResumexceptiontransporterPK() {
	}

	public ResumexceptiontransporterPK(final int transporterid, final Date dates, final int temporalites) {
		this.transporterid = transporterid;
		this.dates = dates;
		this.temporalites = temporalites;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof ResumexceptiontransporterPK)) {
			return false;
		}
		final ResumexceptiontransporterPK other = (ResumexceptiontransporterPK) object;
		return this.transporterid == other.transporterid && (this.dates != null || other.dates == null)
				&& (this.dates == null || this.dates.equals(other.dates)) && this.temporalites == other.temporalites;
	}

	public Date getDates() {
		return this.dates;
	}

	public int getTemporalites() {
		return this.temporalites;
	}

	public int getTransporterid() {
		return this.transporterid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += this.transporterid;
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

	public void setTransporterid(final int transporterid) {
		this.transporterid = transporterid;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.ResumexceptiontransporterPK[ transporterid=" + this.transporterid + ", dates="
				+ this.dates + ", temporalites=" + this.temporalites + " ]";
	}
}
