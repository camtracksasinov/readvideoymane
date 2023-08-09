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
public class ResumexceptionaffiliatePK implements Serializable {
	@Basic(optional = false)
	@Column(name = "affiliateid")
	private int affiliateid;
	@Basic(optional = false)
	@Column(name = "dates")
	@Temporal(TemporalType.DATE)
	private Date dates;
	@Basic(optional = false)
	@Column(name = "temporalites")
	private int temporalites;

	public ResumexceptionaffiliatePK() {
	}

	public ResumexceptionaffiliatePK(final int affiliateid, final Date dates, final int temporalites) {
		this.affiliateid = affiliateid;
		this.dates = dates;
		this.temporalites = temporalites;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof ResumexceptionaffiliatePK)) {
			return false;
		}
		final ResumexceptionaffiliatePK other = (ResumexceptionaffiliatePK) object;
		return this.affiliateid == other.affiliateid && (this.dates != null || other.dates == null)
				&& (this.dates == null || this.dates.equals(other.dates)) && this.temporalites == other.temporalites;
	}

	public int getAffiliateid() {
		return this.affiliateid;
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
		hash += this.affiliateid;
		hash += ((this.dates != null) ? this.dates.hashCode() : 0);
		hash += this.temporalites;
		return hash;
	}

	public void setAffiliateid(final int affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setDates(final Date dates) {
		this.dates = dates;
	}

	public void setTemporalites(final int temporalites) {
		this.temporalites = temporalites;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.ResumexceptionaffiliatePK[ affiliateid=" + this.affiliateid + ", dates="
				+ this.dates + ", temporalites=" + this.temporalites + " ]";
	}
}
