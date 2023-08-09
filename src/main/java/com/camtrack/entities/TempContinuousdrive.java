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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "temp_continuousdrive")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "TempContinuousdrive.findAll", query = "SELECT t FROM TempContinuousdrive t"),
		@NamedQuery(name = "TempContinuousdrive.findByVehicleid", query = "SELECT t FROM TempContinuousdrive t WHERE t.vehicleid = :vehicleid"),
		@NamedQuery(name = "TempContinuousdrive.findByDriverid", query = "SELECT t FROM TempContinuousdrive t WHERE t.driverid = :driverid"),
		@NamedQuery(name = "TempContinuousdrive.findByTotaldistance", query = "SELECT t FROM TempContinuousdrive t WHERE t.totaldistance = :totaldistance"),
		@NamedQuery(name = "TempContinuousdrive.findByDate", query = "SELECT t FROM TempContinuousdrive t WHERE t.date = :date"),
		@NamedQuery(name = "TempContinuousdrive.findByDistanceunderexception", query = "SELECT t FROM TempContinuousdrive t WHERE t.distanceunderexception = :distanceunderexception"),
		@NamedQuery(name = "TempContinuousdrive.findByDriverkeycode", query = "SELECT t FROM TempContinuousdrive t WHERE t.driverkeycode = :driverkeycode"),
		@NamedQuery(name = "TempContinuousdrive.findByTotalduration", query = "SELECT t FROM TempContinuousdrive t WHERE t.totalduration = :totalduration"),
		@NamedQuery(name = "TempContinuousdrive.findByEnddatetime", query = "SELECT t FROM TempContinuousdrive t WHERE t.enddatetime = :enddatetime"),
		@NamedQuery(name = "TempContinuousdrive.findByEndpostion", query = "SELECT t FROM TempContinuousdrive t WHERE t.endpostion = :endpostion"),
		@NamedQuery(name = "TempContinuousdrive.findByStartposition", query = "SELECT t FROM TempContinuousdrive t WHERE t.startposition = :startposition"),
		@NamedQuery(name = "TempContinuousdrive.findByStartdatetime", query = "SELECT t FROM TempContinuousdrive t WHERE t.startdatetime = :startdatetime"),
		@NamedQuery(name = "TempContinuousdrive.findByExceptionid", query = "SELECT t FROM TempContinuousdrive t WHERE t.exceptionid = :exceptionid"),
		@NamedQuery(name = "TempContinuousdrive.findByExceptiontype", query = "SELECT t FROM TempContinuousdrive t WHERE t.exceptiontype = :exceptiontype"),
		@NamedQuery(name = "TempContinuousdrive.findByLevel", query = "SELECT t FROM TempContinuousdrive t WHERE t.level = :level"),
		@NamedQuery(name = "TempContinuousdrive.findByMaxbreak", query = "SELECT t FROM TempContinuousdrive t WHERE t.maxbreak = :maxbreak"),
		@NamedQuery(name = "TempContinuousdrive.findByNumofbreak", query = "SELECT t FROM TempContinuousdrive t WHERE t.numofbreak = :numofbreak"),
		@NamedQuery(name = "TempContinuousdrive.findByRequiredbreak", query = "SELECT t FROM TempContinuousdrive t WHERE t.requiredbreak = :requiredbreak"),
		@NamedQuery(name = "TempContinuousdrive.findByThreshold", query = "SELECT t FROM TempContinuousdrive t WHERE t.threshold = :threshold"),
		@NamedQuery(name = "TempContinuousdrive.findByTimeexceeded", query = "SELECT t FROM TempContinuousdrive t WHERE t.timeexceeded = :timeexceeded"),
		@NamedQuery(name = "TempContinuousdrive.findByAffiliateid", query = "SELECT t FROM TempContinuousdrive t WHERE t.affiliateid = :affiliateid"),
		@NamedQuery(name = "TempContinuousdrive.findByTransporterid", query = "SELECT t FROM TempContinuousdrive t WHERE t.transporterid = :transporterid"),
		@NamedQuery(name = "TempContinuousdrive.findByUniqueid", query = "SELECT t FROM TempContinuousdrive t WHERE t.uniqueid = :uniqueid"),
		@NamedQuery(name = "TempContinuousdrive.findByUnitid", query = "SELECT t FROM TempContinuousdrive t WHERE t.unitid = :unitid") })
public class TempContinuousdrive implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "affiliateid")
	private Integer affiliateid;
	@Column(name = "date")
	@Temporal(TemporalType.DATE)
	private Date date;
	@Column(name = "distanceunderexception")
	private BigDecimal distanceunderexception;
	@Column(name = "driverid")
	private Integer driverid;
	@Column(name = "driverkeycode")
	private Integer driverkeycode;
	@Column(name = "enddatetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date enddatetime;
	@Column(name = "endpostion")
	private String endpostion;
	@Column(name = "exceptionid")
	private Integer exceptionid;
	@Column(name = "exceptiontype")
	private String exceptiontype;
	@Column(name = "level")
	private String level;
	@Column(name = "maxbreak")
	private BigDecimal maxbreak;
	@Column(name = "numofbreak")
	private BigDecimal numofbreak;
	@Column(name = "requiredbreak")
	private BigDecimal requiredbreak;
	@Column(name = "startdatetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startdatetime;
	@Column(name = "startposition")
	private String startposition;
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
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "uniqueid")
	private Integer uniqueid;
	@Column(name = "unitid")
	private String unitid;
	@Column(name = "vehicleid")
	private Integer vehicleid;

	public TempContinuousdrive() {
	}

	public TempContinuousdrive(final Integer uniqueid) {
		this.uniqueid = uniqueid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof TempContinuousdrive)) {
			return false;
		}
		final TempContinuousdrive other = (TempContinuousdrive) object;
		return (this.uniqueid != null || other.uniqueid == null)
				&& (this.uniqueid == null || this.uniqueid.equals(other.uniqueid));
	}

	public Integer getAffiliateid() {
		return this.affiliateid;
	}

	public Date getDate() {
		return this.date;
	}

	public BigDecimal getDistanceunderexception() {
		return this.distanceunderexception;
	}

	public Integer getDriverid() {
		return this.driverid;
	}

	public Integer getDriverkeycode() {
		return this.driverkeycode;
	}

	public Date getEnddatetime() {
		return this.enddatetime;
	}

	public String getEndpostion() {
		return this.endpostion;
	}

	public Integer getExceptionid() {
		return this.exceptionid;
	}

	public String getExceptiontype() {
		return this.exceptiontype;
	}

	public String getLevel() {
		return this.level;
	}

	public BigDecimal getMaxbreak() {
		return this.maxbreak;
	}

	public BigDecimal getNumofbreak() {
		return this.numofbreak;
	}

	public BigDecimal getRequiredbreak() {
		return this.requiredbreak;
	}

	public Date getStartdatetime() {
		return this.startdatetime;
	}

	public String getStartposition() {
		return this.startposition;
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

	public Integer getUniqueid() {
		return this.uniqueid;
	}

	public String getUnitid() {
		return this.unitid;
	}

	public Integer getVehicleid() {
		return this.vehicleid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.uniqueid != null) ? this.uniqueid.hashCode() : 0);
		return hash;
	}

	public void setAffiliateid(final Integer affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public void setDistanceunderexception(final BigDecimal distanceunderexception) {
		this.distanceunderexception = distanceunderexception;
	}

	public void setDriverid(final Integer driverid) {
		this.driverid = driverid;
	}

	public void setDriverkeycode(final Integer driverkeycode) {
		this.driverkeycode = driverkeycode;
	}

	public void setEnddatetime(final Date enddatetime) {
		this.enddatetime = enddatetime;
	}

	public void setEndpostion(final String endpostion) {
		this.endpostion = endpostion;
	}

	public void setExceptionid(final Integer exceptionid) {
		this.exceptionid = exceptionid;
	}

	public void setExceptiontype(final String exceptiontype) {
		this.exceptiontype = exceptiontype;
	}

	public void setLevel(final String level) {
		this.level = level;
	}

	public void setMaxbreak(final BigDecimal maxbreak) {
		this.maxbreak = maxbreak;
	}

	public void setNumofbreak(final BigDecimal numofbreak) {
		this.numofbreak = numofbreak;
	}

	public void setRequiredbreak(final BigDecimal requiredbreak) {
		this.requiredbreak = requiredbreak;
	}

	public void setStartdatetime(final Date startdatetime) {
		this.startdatetime = startdatetime;
	}

	public void setStartposition(final String startposition) {
		this.startposition = startposition;
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

	public void setUniqueid(final Integer uniqueid) {
		this.uniqueid = uniqueid;
	}

	public void setUnitid(final String unitid) {
		this.unitid = unitid;
	}

	public void setVehicleid(final Integer vehicleid) {
		this.vehicleid = vehicleid;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.TempContinuousdrive[ uniqueid=" + this.uniqueid + " ]";
	}
}
