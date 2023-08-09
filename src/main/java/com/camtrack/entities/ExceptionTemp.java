//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "exception_temp")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "ExceptionTemp.findAll", query = "SELECT e FROM ExceptionTemp e"),
		@NamedQuery(name = "ExceptionTemp.findByExceptionid", query = "SELECT e FROM ExceptionTemp e WHERE e.exceptionid = :exceptionid"),
		@NamedQuery(name = "ExceptionTemp.findByAffiliateid", query = "SELECT e FROM ExceptionTemp e WHERE e.affiliateid = :affiliateid"),
		@NamedQuery(name = "ExceptionTemp.findByTransporterid", query = "SELECT e FROM ExceptionTemp e WHERE e.transporterid = :transporterid"),
		@NamedQuery(name = "ExceptionTemp.findByVehicleid", query = "SELECT e FROM ExceptionTemp e WHERE e.vehicleid = :vehicleid"),
		@NamedQuery(name = "ExceptionTemp.findByDriverid", query = "SELECT e FROM ExceptionTemp e WHERE e.driverid = :driverid"),
		@NamedQuery(name = "ExceptionTemp.findByStartdatetime", query = "SELECT e FROM ExceptionTemp e WHERE e.startdatetime = :startdatetime"),
		@NamedQuery(name = "ExceptionTemp.findByEnddatetime", query = "SELECT e FROM ExceptionTemp e WHERE e.enddatetime = :enddatetime"),
		@NamedQuery(name = "ExceptionTemp.findByExceptiontype", query = "SELECT e FROM ExceptionTemp e WHERE e.exceptiontype = :exceptiontype"),
		@NamedQuery(name = "ExceptionTemp.findByTotalduration", query = "SELECT e FROM ExceptionTemp e WHERE e.totalduration = :totalduration"),
		@NamedQuery(name = "ExceptionTemp.findByTotaldistance", query = "SELECT e FROM ExceptionTemp e WHERE e.totaldistance = :totaldistance"),
		@NamedQuery(name = "ExceptionTemp.findByLevel", query = "SELECT e FROM ExceptionTemp e WHERE e.level = :level"),
		@NamedQuery(name = "ExceptionTemp.findByThreshold", query = "SELECT e FROM ExceptionTemp e WHERE e.threshold = :threshold"),
		@NamedQuery(name = "ExceptionTemp.findByMaxvalue", query = "SELECT e FROM ExceptionTemp e WHERE e.maxvalue = :maxvalue"),
		@NamedQuery(name = "ExceptionTemp.findByDistanceunderexception", query = "SELECT e FROM ExceptionTemp e WHERE e.distanceunderexception = :distanceunderexception"),
		@NamedQuery(name = "ExceptionTemp.findByTimeexceeded", query = "SELECT e FROM ExceptionTemp e WHERE e.timeexceeded = :timeexceeded"),
		@NamedQuery(name = "ExceptionTemp.findByStatus", query = "SELECT e FROM ExceptionTemp e WHERE e.status = :status"),
		@NamedQuery(name = "ExceptionTemp.findByRequiredbreak", query = "SELECT e FROM ExceptionTemp e WHERE e.requiredbreak = :requiredbreak"),
		@NamedQuery(name = "ExceptionTemp.findByNumberofbreak", query = "SELECT e FROM ExceptionTemp e WHERE e.numberofbreak = :numberofbreak"),
		@NamedQuery(name = "ExceptionTemp.findByMaxbreak", query = "SELECT e FROM ExceptionTemp e WHERE e.maxbreak = :maxbreak"),
		@NamedQuery(name = "ExceptionTemp.findByStartgps", query = "SELECT e FROM ExceptionTemp e WHERE e.startgps = :startgps"),
		@NamedQuery(name = "ExceptionTemp.findByEndgps", query = "SELECT e FROM ExceptionTemp e WHERE e.endgps = :endgps"),
		@NamedQuery(name = "ExceptionTemp.findByVmax", query = "SELECT e FROM ExceptionTemp e WHERE e.vmax = :vmax"),
		@NamedQuery(name = "ExceptionTemp.findBySpeedid", query = "SELECT e FROM ExceptionTemp e WHERE e.speedid = :speedid"),
		@NamedQuery(name = "ExceptionTemp.findByInvaliddata", query = "SELECT e FROM ExceptionTemp e WHERE e.invaliddata = :invaliddata"),
		@NamedQuery(name = "ExceptionTemp.findByUnitid", query = "SELECT e FROM ExceptionTemp e WHERE e.unitid = :unitid") })
public class ExceptionTemp implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "affiliateid")
	private Integer affiliateid;
	@Column(name = "distanceunderexception")
	private BigDecimal distanceunderexception;
	@Column(name = "driverid")
	private Integer driverid;
	@Column(name = "enddatetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date enddatetime;
	@Column(name = "endgps")
	private String endgps;
	@Id
	@Basic(optional = false)
	@Column(name = "exceptionid")
	private Integer exceptionid;
	@Column(name = "exceptiontype")
	private Integer exceptiontype;
	@Column(name = "invaliddata")
	private Integer invaliddata;
	@Column(name = "level")
	private Integer level;
	@Column(name = "maxbreak")
	private BigDecimal maxbreak;
	@Column(name = "maxvalue")
	private BigDecimal maxvalue;
	@Column(name = "numberofbreak")
	private BigDecimal numberofbreak;
	@Column(name = "requiredbreak")
	private BigDecimal requiredbreak;
	@Column(name = "speedid")
	private String speedid;
	@Column(name = "startdatetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startdatetime;
	@Column(name = "startgps")
	private String startgps;
	@Column(name = "status")
	private Integer status;
	@Column(name = "threshold")
	private BigDecimal threshold;
	@Column(name = "timeexceeded")
	private BigDecimal timeexceeded;
	@Column(name = "totaldistance")
	private BigDecimal totaldistance;
	@Column(name = "totalduration")
	private BigDecimal totalduration;
	@Column(name = "transporterid")
	private Integer transporterid;
	@Column(name = "unitid")
	private String unitid;
	@Column(name = "vehicleid")
	private Integer vehicleid;
	@Column(name = "vmax")
	private BigDecimal vmax;

	public ExceptionTemp() {
	}

	public ExceptionTemp(final Integer exceptionid) {
		this.exceptionid = exceptionid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof ExceptionTemp)) {
			return false;
		}
		final ExceptionTemp other = (ExceptionTemp) object;
		return (this.exceptionid != null || other.exceptionid == null)
				&& (this.exceptionid == null || this.exceptionid.equals(other.exceptionid));
	}

	public Integer getAffiliateid() {
		return this.affiliateid;
	}

	public BigDecimal getDistanceunderexception() {
		return this.distanceunderexception;
	}

	public Integer getDriverid() {
		return this.driverid;
	}

	public Date getEnddatetime() {
		return this.enddatetime;
	}

	public String getEndgps() {
		return this.endgps;
	}

	public Integer getExceptionid() {
		return this.exceptionid;
	}

	public Integer getExceptiontype() {
		return this.exceptiontype;
	}

	public Integer getInvaliddata() {
		return this.invaliddata;
	}

	public Integer getLevel() {
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

	public Integer getStatus() {
		return this.status;
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

	public Integer getTransporterid() {
		return this.transporterid;
	}

	public String getUnitid() {
		return this.unitid;
	}

	public Integer getVehicleid() {
		return this.vehicleid;
	}

	public BigDecimal getVmax() {
		return this.vmax;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.exceptionid != null) ? this.exceptionid.hashCode() : 0);
		return hash;
	}

	public void setAffiliateid(final Integer affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setDistanceunderexception(final BigDecimal distanceunderexception) {
		this.distanceunderexception = distanceunderexception;
	}

	public void setDriverid(final Integer driverid) {
		this.driverid = driverid;
	}

	public void setEnddatetime(final Date enddatetime) {
		this.enddatetime = enddatetime;
	}

	public void setEndgps(final String endgps) {
		this.endgps = endgps;
	}

	public void setExceptionid(final Integer exceptionid) {
		this.exceptionid = exceptionid;
	}

	public void setExceptiontype(final Integer exceptiontype) {
		this.exceptiontype = exceptiontype;
	}

	public void setInvaliddata(final Integer invaliddata) {
		this.invaliddata = invaliddata;
	}

	public void setLevel(final Integer level) {
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

	public void setStatus(final Integer status) {
		this.status = status;
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

	public void setTransporterid(final Integer transporterid) {
		this.transporterid = transporterid;
	}

	public void setUnitid(final String unitid) {
		this.unitid = unitid;
	}

	public void setVehicleid(final Integer vehicleid) {
		this.vehicleid = vehicleid;
	}

	public void setVmax(final BigDecimal vmax) {
		this.vmax = vmax;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.ExceptionTemp[ exceptionid=" + this.exceptionid + " ]";
	}
}
