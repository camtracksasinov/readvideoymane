//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "weeklydrive")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Weeklydrive.findAll", query = "SELECT w FROM Weeklydrive w"),
		@NamedQuery(name = "Weeklydrive.findByVehicleid", query = "SELECT w FROM Weeklydrive w WHERE w.weeklydrivePK.vehicleid = :vehicleid"),
		@NamedQuery(name = "Weeklydrive.findByDriverid", query = "SELECT w FROM Weeklydrive w WHERE w.weeklydrivePK.driverid = :driverid"),
		@NamedQuery(name = "Weeklydrive.findByTotaldistance", query = "SELECT w FROM Weeklydrive w WHERE w.totaldistance = :totaldistance"),
		@NamedQuery(name = "Weeklydrive.findByDate", query = "SELECT w FROM Weeklydrive w WHERE w.date = :date"),
		@NamedQuery(name = "Weeklydrive.findByDistanceunderexception", query = "SELECT w FROM Weeklydrive w WHERE w.distanceunderexception = :distanceunderexception"),
		@NamedQuery(name = "Weeklydrive.findByDriverkeycode", query = "SELECT w FROM Weeklydrive w WHERE w.driverkeycode = :driverkeycode"),
		@NamedQuery(name = "Weeklydrive.findByTotalduration", query = "SELECT w FROM Weeklydrive w WHERE w.totalduration = :totalduration"),
		@NamedQuery(name = "Weeklydrive.findByEnddatetime", query = "SELECT w FROM Weeklydrive w WHERE w.enddatetime = :enddatetime"),
		@NamedQuery(name = "Weeklydrive.findByEndpostion", query = "SELECT w FROM Weeklydrive w WHERE w.endpostion = :endpostion"),
		@NamedQuery(name = "Weeklydrive.findByStartposition", query = "SELECT w FROM Weeklydrive w WHERE w.startposition = :startposition"),
		@NamedQuery(name = "Weeklydrive.findByStartdatetime", query = "SELECT w FROM Weeklydrive w WHERE w.weeklydrivePK.startdatetime = :startdatetime"),
		@NamedQuery(name = "Weeklydrive.findByExceptionid", query = "SELECT w FROM Weeklydrive w WHERE w.exceptionid = :exceptionid"),
		@NamedQuery(name = "Weeklydrive.findByExceptiontype", query = "SELECT w FROM Weeklydrive w WHERE w.exceptiontype = :exceptiontype"),
		@NamedQuery(name = "Weeklydrive.findByLevel", query = "SELECT w FROM Weeklydrive w WHERE w.level = :level"),
		@NamedQuery(name = "Weeklydrive.findByMaxbreak", query = "SELECT w FROM Weeklydrive w WHERE w.maxbreak = :maxbreak"),
		@NamedQuery(name = "Weeklydrive.findByNumofbreak", query = "SELECT w FROM Weeklydrive w WHERE w.numofbreak = :numofbreak"),
		@NamedQuery(name = "Weeklydrive.findByRequiredbreak", query = "SELECT w FROM Weeklydrive w WHERE w.requiredbreak = :requiredbreak"),
		@NamedQuery(name = "Weeklydrive.findByThreshold", query = "SELECT w FROM Weeklydrive w WHERE w.threshold = :threshold"),
		@NamedQuery(name = "Weeklydrive.findByTimeexceeded", query = "SELECT w FROM Weeklydrive w WHERE w.timeexceeded = :timeexceeded") })
public class Weeklydrive implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "date")
	@Temporal(TemporalType.DATE)
	private Date date;
	@Column(name = "distanceunderexception")
	private BigDecimal distanceunderexception;
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
	@EmbeddedId
	protected WeeklydrivePK weeklydrivePK;

	public Weeklydrive() {
	}

	public Weeklydrive(final int vehicleid, final int driverid, final Date startdatetime) {
		this.weeklydrivePK = new WeeklydrivePK(vehicleid, driverid, startdatetime);
	}

	public Weeklydrive(final WeeklydrivePK weeklydrivePK) {
		this.weeklydrivePK = weeklydrivePK;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Weeklydrive)) {
			return false;
		}
		final Weeklydrive other = (Weeklydrive) object;
		return (this.weeklydrivePK != null || other.weeklydrivePK == null)
				&& (this.weeklydrivePK == null || this.weeklydrivePK.equals(other.weeklydrivePK));
	}

	public Date getDate() {
		return this.date;
	}

	public BigDecimal getDistanceunderexception() {
		return this.distanceunderexception;
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

	public WeeklydrivePK getWeeklydrivePK() {
		return this.weeklydrivePK;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.weeklydrivePK != null) ? this.weeklydrivePK.hashCode() : 0);
		return hash;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public void setDistanceunderexception(final BigDecimal distanceunderexception) {
		this.distanceunderexception = distanceunderexception;
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

	public void setWeeklydrivePK(final WeeklydrivePK weeklydrivePK) {
		this.weeklydrivePK = weeklydrivePK;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Weeklydrive[ weeklydrivePK=" + this.weeklydrivePK + " ]";
	}
}
