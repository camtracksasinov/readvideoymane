//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.manualsubtractionandrecovery.bean;

import java.io.Serializable;
import java.util.Date;

public class SubstractionBean implements Serializable {
	private static final long serialVersionUID = 1L;
	Integer affiliateid;
	Integer clientid;
	Date dateofocurrence;
	Integer driverid;
	Integer points;
	Integer transporterid;
	Integer visualparamid;

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

	public Integer getPoints() {
		return this.points;
	}

	public Integer getTransporterid() {
		return this.transporterid;
	}

	public Integer getVisualparamid() {
		return this.visualparamid;
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

	public void setPoints(final Integer points) {
		this.points = points;
	}

	public void setTransporterid(final Integer transporterid) {
		this.transporterid = transporterid;
	}

	public void setVisualparamid(final Integer visualparamid) {
		this.visualparamid = visualparamid;
	}
}
