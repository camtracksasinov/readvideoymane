//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Exceptions implements Serializable {
	private static final long serialVersionUID = 1L;
	private Customeraffiliate affiliateid;
	private String commentsinvalidate;
	private Customer customerid;
	private String deviceid;
	private BigDecimal distanceunderexception;
	private Driver driverid;
	private Date enddatetime;
	private String endgps;
	private String evidence;
	private Integer exceptionid;
	private Parametertype exceptiontype;
	private String invalidateuser;
	private Integer invaliddata;
	private Exceptionlevel level;
	private BigDecimal maxbreak;
	private BigDecimal maxvalue;
	private BigDecimal numberofbreak;
	private Date preventivedatetime;
	private BigDecimal preventivethreshold;
	private BigDecimal requiredbreak;
	private String speedid;
	private Date startdatetime;
	private String startgps;
	private BigDecimal threshold;
	private BigDecimal timeexceeded;
	private BigDecimal totaldistance;
	private BigDecimal totalduration;
	private Transporter transporterid;
	private String unitid;
	private Vehicle vehicleid;
	private BigDecimal vmax;

	public Customeraffiliate getAffiliateid() {
		return this.affiliateid;
	}

	public String getCommentsinvalidate() {
		return this.commentsinvalidate;
	}

	public Customer getCustomerid() {
		return this.customerid;
	}

	public String getDeviceid() {
		return this.deviceid;
	}

	public BigDecimal getDistanceunderexception() {
		return this.distanceunderexception;
	}

	public Driver getDriverid() {
		return this.driverid;
	}

	public Date getEnddatetime() {
		return this.enddatetime;
	}

	public String getEndgps() {
		return this.endgps;
	}

	public String getEvidence() {
		return this.evidence;
	}

	public Integer getExceptionid() {
		return this.exceptionid;
	}

	public Parametertype getExceptiontype() {
		return this.exceptiontype;
	}

	public String getInvalidateuser() {
		return this.invalidateuser;
	}

	public Integer getInvaliddata() {
		return this.invaliddata;
	}

	public Exceptionlevel getLevel() {
		return this.level;
	}

	public BigDecimal getMaxbreak() {
		return this.maxbreak;
	}

	public BigDecimal getMaxvalue() {
		return this.maxvalue;
	}

	public BigDecimal getNumberofbreak() {
		return this.numberofbreak;
	}

	public Date getPreventivedatetime() {
		return this.preventivedatetime;
	}

	public BigDecimal getPreventivethreshold() {
		return this.preventivethreshold;
	}

	public BigDecimal getRequiredbreak() {
		return this.requiredbreak;
	}

	public String getSpeedid() {
		return this.speedid;
	}

	public Date getStartdatetime() {
		return this.startdatetime;
	}

	public String getStartgps() {
		return this.startgps;
	}

	public BigDecimal getThreshold() {
		return this.threshold;
	}

	public BigDecimal getTimeexceeded() {
		return this.timeexceeded;
	}

	public BigDecimal getTotaldistance() {
		return this.totaldistance;
	}

	public BigDecimal getTotalduration() {
		return this.totalduration;
	}

	public Transporter getTransporterid() {
		return this.transporterid;
	}

	public String getUnitid() {
		return this.unitid;
	}

	public Vehicle getVehicleid() {
		return this.vehicleid;
	}

	public BigDecimal getVmax() {
		return this.vmax;
	}

	public void setAffiliateid(final Customeraffiliate affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setCommentsinvalidate(final String commentsinvalidate) {
		this.commentsinvalidate = commentsinvalidate;
	}

	public void setCustomerid(final Customer customerid) {
		this.customerid = customerid;
	}

	public void setDeviceid(final String deviceid) {
		this.deviceid = deviceid;
	}

	public void setDistanceunderexception(final BigDecimal distanceunderexception) {
		this.distanceunderexception = distanceunderexception;
	}

	public void setDriverid(final Driver driverid) {
		this.driverid = driverid;
	}

	public void setEnddatetime(final Date enddatetime) {
		this.enddatetime = enddatetime;
	}

	public void setEndgps(final String endgps) {
		this.endgps = endgps;
	}

	public void setEvidence(final String evidence) {
		this.evidence = evidence;
	}

	public void setExceptionid(final Integer exceptionid) {
		this.exceptionid = exceptionid;
	}

	public void setExceptiontype(final Parametertype exceptiontype) {
		this.exceptiontype = exceptiontype;
	}

	public void setInvalidateuser(final String invalidateuser) {
		this.invalidateuser = invalidateuser;
	}

	public void setInvaliddata(final Integer invaliddata) {
		this.invaliddata = invaliddata;
	}

	public void setLevel(final Exceptionlevel level) {
		this.level = level;
	}

	public void setMaxbreak(final BigDecimal maxbreak) {
		this.maxbreak = maxbreak;
	}

	public void setMaxvalue(final BigDecimal maxvalue) {
		this.maxvalue = maxvalue;
	}

	public void setNumberofbreak(final BigDecimal numberofbreak) {
		this.numberofbreak = numberofbreak;
	}

	public void setPreventivedatetime(final Date preventivedatetime) {
		this.preventivedatetime = preventivedatetime;
	}

	public void setPreventivethreshold(final BigDecimal preventivethreshold) {
		this.preventivethreshold = preventivethreshold;
	}

	public void setRequiredbreak(final BigDecimal requiredbreak) {
		this.requiredbreak = requiredbreak;
	}

	public void setSpeedid(final String speedid) {
		this.speedid = speedid;
	}

	public void setStartdatetime(final Date startdatetime) {
		this.startdatetime = startdatetime;
	}

	public void setStartgps(final String startgps) {
		this.startgps = startgps;
	}

	public void setThreshold(final BigDecimal threshold) {
		this.threshold = threshold;
	}

	public void setTimeexceeded(final BigDecimal timeexceeded) {
		this.timeexceeded = timeexceeded;
	}

	public void setTotaldistance(final BigDecimal totaldistance) {
		this.totaldistance = totaldistance;
	}

	public void setTotalduration(final BigDecimal totalduration) {
		this.totalduration = totalduration;
	}

	public void setTransporterid(final Transporter transporterid) {
		this.transporterid = transporterid;
	}

	public void setUnitid(final String unitid) {
		this.unitid = unitid;
	}

	public void setVehicleid(final Vehicle vehicleid) {
		this.vehicleid = vehicleid;
	}

	public void setVmax(final BigDecimal vmax) {
		this.vmax = vmax;
	}
}
