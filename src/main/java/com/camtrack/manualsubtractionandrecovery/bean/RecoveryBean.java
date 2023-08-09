//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.manualsubtractionandrecovery.bean;

import java.io.Serializable;
import java.util.Date;

public class RecoveryBean implements Serializable {
	private static final long serialVersionUID = 1L;
	Integer affid;
	Integer cust;
	Date dat;
	Integer drivid;
	Integer nbacc;
	Integer rcvp;
	Integer subp;
	Integer tcip;
	Integer trpid;

	public Integer getAffid() {
		return this.affid;
	}

	public Integer getCust() {
		return this.cust;
	}

	public Date getDat() {
		return this.dat;
	}

	public Integer getDrivid() {
		return this.drivid;
	}

	public Integer getNbacc() {
		return this.nbacc;
	}

	public Integer getRcvp() {
		return this.rcvp;
	}

	public Integer getSubp() {
		return this.subp;
	}

	public Integer getTcip() {
		return this.tcip;
	}

	public Integer getTrpid() {
		return this.trpid;
	}

	public void setAffid(final Integer affid) {
		this.affid = affid;
	}

	public void setCust(final Integer cust) {
		this.cust = cust;
	}

	public void setDat(final Date dat) {
		this.dat = dat;
	}

	public void setDrivid(final Integer drivid) {
		this.drivid = drivid;
	}

	public void setNbacc(final Integer nbacc) {
		this.nbacc = nbacc;
	}

	public void setRcvp(final Integer rcvp) {
		this.rcvp = rcvp;
	}

	public void setSubp(final Integer subp) {
		this.subp = subp;
	}

	public void setTcip(final Integer tcip) {
		this.tcip = tcip;
	}

	public void setTrpid(final Integer trpid) {
		this.trpid = trpid;
	}
}
