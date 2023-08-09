// Decompiled by Procyon v0.5.30
//

package com.camtrack.manualsubtractionandrecovery.bean;

import com.camtrack.config.Utils;

public class Manualsubnreclist {
	Integer accidentpoints;
	Integer clientaffiliateid;
	Integer customerid;
	Integer[] driverid;
	Integer[] manualrecid;
	Integer[] manualsubid;
	Integer manualsubrecid;
	String occurancedate;
	String[] reclabel;
	Integer[] recoveryid;
	Integer[] recpoints;
	Integer tcip;
	Integer transporterid;
	Integer[] vparamid;
	String[] vparamlabel;
	Integer[] vpoints;

	public Manualsubnreclist() {
	}

	public Manualsubnreclist(final Integer customerid, final Integer clientaffiliateid, final Integer transporterid,
			final Integer[] driverid, final String occurancedate, final Integer tcip, final Integer accidentpoints,
			final Integer[] vparamid, final Integer[] vpoints, final String[] vparamlabel, final Integer[] recoveryid,
			final Integer[] recpoints, final String[] reclabel) {
		this.customerid = customerid;
		this.clientaffiliateid = clientaffiliateid;
		this.transporterid = transporterid;
		this.driverid = driverid;
		this.occurancedate = occurancedate;
		this.tcip = tcip;
		this.accidentpoints = accidentpoints;
		this.vparamid = vparamid;
		this.vpoints = vpoints;
		this.vparamlabel = vparamlabel;
		this.recoveryid = recoveryid;
		this.recpoints = recpoints;
		this.reclabel = reclabel;
	}

	public Integer getAccidentpoints() {
		return this.accidentpoints;
	}

	public Integer getClientaffiliateid() {
		return this.clientaffiliateid;
	}

	public Integer getCustomerid() {
		return this.customerid;
	}

	public Integer[] getDriverid() {
		return this.driverid;
	}

	public Integer[] getManualrecid() {
		return this.manualrecid;
	}

	public Integer[] getManualsubid() {
		return this.manualsubid;
	}

	public Integer getManualsubrecid() {
		return this.manualsubrecid;
	}

	public String getOccurancedate() {
		return Utils.StringEscape(this.occurancedate);
	}

	public String[] getReclabel() {
		return this.reclabel;
	}

	public Integer[] getRecoveryid() {
		return this.recoveryid;
	}

	public Integer[] getRecpoints() {
		return this.recpoints;
	}

	public Integer getTcip() {
		return this.tcip;
	}

	public Integer getTransporterid() {
		return this.transporterid;
	}

	public Integer[] getVparamid() {
		return this.vparamid;
	}

	public String[] getVparamlabel() {
		return this.vparamlabel;
	}

	public Integer[] getVpoints() {
		return this.vpoints;
	}

	public void setAccidentpoints(final Integer accidentpoints) {
		this.accidentpoints = accidentpoints;
	}

	public void setClientaffiliateid(final Integer clientaffiliateid) {
		this.clientaffiliateid = clientaffiliateid;
	}

	public void setCustomerid(final Integer customerid) {
		this.customerid = customerid;
	}

	public void setDriverid(final Integer[] driverid) {
		this.driverid = driverid;
	}

	public void setManualrecid(final Integer[] manualrecid) {
		this.manualrecid = manualrecid;
	}

	public void setManualsubid(final Integer[] manualsubid) {
		this.manualsubid = manualsubid;
	}

	public void setManualsubrecid(final Integer manualsubrecid) {
		this.manualsubrecid = manualsubrecid;
	}

	public void setOccurancedate(final String occurancedate) {
		this.occurancedate = occurancedate;
	}

	public void setReclabel(final String[] reclabel) {
		this.reclabel = reclabel;
	}

	public void setRecoveryid(final Integer[] recoveryid) {
		this.recoveryid = recoveryid;
	}

	public void setRecpoints(final Integer[] recpoints) {
		this.recpoints = recpoints;
	}

	public void setTcip(final Integer tcip) {
		this.tcip = tcip;
	}

	public void setTransporterid(final Integer transporterid) {
		this.transporterid = transporterid;
	}

	public void setVparamid(final Integer[] vparamid) {
		this.vparamid = vparamid;
	}

	public void setVparamlabel(final String[] vparamlabel) {
		this.vparamlabel = vparamlabel;
	}

	public void setVpoints(final Integer[] vpoints) {
		this.vpoints = vpoints;
	}
}
