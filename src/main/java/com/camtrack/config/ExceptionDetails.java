//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.config;

import java.io.Serializable;
import java.math.BigDecimal;

public class ExceptionDetails implements Serializable {
	private static final long serialVersionUID = 1L;
	private String affiliate;
	private String client;
	private String comments;
	private String driver;
	private String endates;
	private String endpoint;
	private String evidenceactions;
	private BigDecimal exceeded_missing_duration;
	private String exception;
	private BigDecimal exceptiondistances;
	private Integer exceptionId;
	private String exceptionlevel;
	private Integer exceptiontypeid;
	private String invalidatedate;
	private Integer invalidateuserid;
	private String invalidateusername;
	private BigDecimal maxbreak;
	private BigDecimal maxvalue;
	private BigDecimal nobreak;
	private String preventivesdates;
	private BigDecimal preventivetreshold;
	private BigDecimal requireddistances;
	private String startdatetimes;
	private String startpoint;
	private BigDecimal thresold;
	private BigDecimal totaldistances;
	private BigDecimal totalduration;
	private String transporter;
	private String vehicle;

	public String getAffiliate() {
		return this.affiliate;
	}

	public String getClient() {
		return this.client;
	}

	public String getComments() {
		return this.comments;
	}

	public String getDriver() {
		return this.driver;
	}

	public String getEndates() {
		return this.endates;
	}

	public String getEndpoint() {
		return this.endpoint;
	}

	public String getEvidenceactions() {
		return this.evidenceactions;
	}

	public BigDecimal getExceeded_missing_duration() {
		return this.exceeded_missing_duration;
	}

	public String getException() {
		return this.exception;
	}

	public BigDecimal getExceptiondistances() {
		return this.exceptiondistances;
	}

	public Integer getExceptionId() {
		return this.exceptionId;
	}

	public String getExceptionlevel() {
		return this.exceptionlevel;
	}

	public Integer getExceptiontypeid() {
		return this.exceptiontypeid;
	}

	public String getInvalidatedate() {
		return this.invalidatedate;
	}

	public Integer getInvalidateuserid() {
		return this.invalidateuserid;
	}

	public String getInvalidateusername() {
		return this.invalidateusername;
	}

	public BigDecimal getMaxbreak() {
		return this.maxbreak;
	}

	public BigDecimal getMaxvalue() {
		return this.maxvalue;
	}

	public BigDecimal getNobreak() {
		return this.nobreak;
	}

	public String getPreventivesdates() {
		return this.preventivesdates;
	}

	public BigDecimal getPreventivetreshold() {
		return this.preventivetreshold;
	}

	public BigDecimal getRequireddistances() {
		return this.requireddistances;
	}

	public String getStartdatetimes() {
		return this.startdatetimes;
	}

	public String getStartpoint() {
		return this.startpoint;
	}

	public BigDecimal getThresold() {
		return this.thresold;
	}

	public BigDecimal getTotaldistances() {
		return this.totaldistances;
	}

	public BigDecimal getTotalduration() {
		return this.totalduration;
	}

	public String getTransporter() {
		return this.transporter;
	}

	public String getVehicle() {
		return this.vehicle;
	}

	public void setAffiliate(final String affiliate) {
		this.affiliate = affiliate;
	}

	public void setClient(final String client) {
		this.client = client;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setDriver(final String driver) {
		this.driver = driver;
	}

	public void setEndates(final String endates) {
		this.endates = endates;
	}

	public void setEndpoint(final String endpoint) {
		this.endpoint = endpoint;
	}

	public void setEvidenceactions(final String evidenceactions) {
		this.evidenceactions = evidenceactions;
	}

	public void setExceeded_missing_duration(final BigDecimal exceeded_missing_duration) {
		this.exceeded_missing_duration = exceeded_missing_duration;
	}

	public void setException(final String exception) {
		this.exception = exception;
	}

	public void setExceptiondistances(final BigDecimal exceptiondistances) {
		this.exceptiondistances = exceptiondistances;
	}

	public void setExceptionId(final Integer exceptionId) {
		this.exceptionId = exceptionId;
	}

	public void setExceptionlevel(final String exceptionlevel) {
		this.exceptionlevel = exceptionlevel;
	}

	public void setExceptiontypeid(final Integer exceptiontypeid) {
		this.exceptiontypeid = exceptiontypeid;
	}

	public void setInvalidatedate(final String invalidatedate) {
		this.invalidatedate = invalidatedate;
	}

	public void setInvalidateuserid(final Integer invalidateuserid) {
		this.invalidateuserid = invalidateuserid;
	}

	public void setInvalidateusername(final String invalidateusername) {
		this.invalidateusername = invalidateusername;
	}

	public void setMaxbreak(final BigDecimal maxbreak) {
		this.maxbreak = maxbreak;
	}

	public void setMaxvalue(final BigDecimal maxvalue) {
		this.maxvalue = maxvalue;
	}

	public void setNobreak(final BigDecimal nobreak) {
		this.nobreak = nobreak;
	}

	public void setPreventivesdates(final String preventivesdates) {
		this.preventivesdates = preventivesdates;
	}

	public void setPreventivetreshold(final BigDecimal preventivetreshold) {
		this.preventivetreshold = preventivetreshold;
	}

	public void setRequireddistances(final BigDecimal requireddistances) {
		this.requireddistances = requireddistances;
	}

	public void setStartdatetimes(final String startdatetimes) {
		this.startdatetimes = startdatetimes;
	}

	public void setStartpoint(final String startpoint) {
		this.startpoint = startpoint;
	}

	public void setThresold(final BigDecimal thresold) {
		this.thresold = thresold;
	}

	public void setTotaldistances(final BigDecimal totaldistances) {
		this.totaldistances = totaldistances;
	}

	public void setTotalduration(final BigDecimal totalduration) {
		this.totalduration = totalduration;
	}

	public void setTransporter(final String transporter) {
		this.transporter = transporter;
	}

	public void setVehicle(final String vehicle) {
		this.vehicle = vehicle;
	}
}
