//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.manualsubtractionandrecovery.bean;

import java.io.Serializable;
import java.util.Date;

public class ManualaccicpBean implements Serializable {
	private static final long serialVersionUID = 1L;
	Integer affiliateid;
	Integer clientid;
	Date dateofocurrence;
	Integer driverid;
	Integer noofaccident;
	Integer transportericp;
	Integer transporterid;

	public Integer getAffiliateid() {
		return this.affiliateid;
	}

	public Integer getClientid() {
		return this.clientid;
	}

	public Date getDateofocurrence() {
		return this.dateofocurrence;
	}

	public Integer getDriverid() {
		return this.driverid;
	}

	public Integer getNoofaccident() {
		return this.noofaccident;
	}

	public Integer getTransportericp() {
		return this.transportericp;
	}

	public Integer getTransporterid() {
		return this.transporterid;
	}

	public void setAffiliateid(final Integer affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setClientid(final Integer clientid) {
		this.clientid = clientid;
	}

	public void setDateofocurrence(final Date dateofocurrence) {
		this.dateofocurrence = dateofocurrence;
	}

	public void setDriverid(final Integer driverid) {
		this.driverid = driverid;
	}

	public void setNoofaccident(final Integer noofaccident) {
		this.noofaccident = noofaccident;
	}

	public void setTransportericp(final Integer transportericp) {
		this.transportericp = transportericp;
	}

	public void setTransporterid(final Integer transporterid) {
		this.transporterid = transporterid;
	}
}
