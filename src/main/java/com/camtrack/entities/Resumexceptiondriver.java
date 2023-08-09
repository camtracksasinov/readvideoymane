//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "resumexceptiondriver")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Resumexceptiondriver.findAll", query = "SELECT r FROM Resumexceptiondriver r"),
		@NamedQuery(name = "Resumexceptiondriver.findByDriverid", query = "SELECT r FROM Resumexceptiondriver r WHERE r.resumexceptiondriverPK.driverid = :driverid"),
		@NamedQuery(name = "Resumexceptiondriver.findByDates", query = "SELECT r FROM Resumexceptiondriver r WHERE r.resumexceptiondriverPK.dates = :dates"),
		@NamedQuery(name = "Resumexceptiondriver.findByTemporalites", query = "SELECT r FROM Resumexceptiondriver r WHERE r.resumexceptiondriverPK.temporalites = :temporalites"),
		@NamedQuery(name = "Resumexceptiondriver.findBySpeedingfnbr", query = "SELECT r FROM Resumexceptiondriver r WHERE r.speedingfnbr = :speedingfnbr"),
		@NamedQuery(name = "Resumexceptiondriver.findByHb", query = "SELECT r FROM Resumexceptiondriver r WHERE r.hb = :hb"),
		@NamedQuery(name = "Resumexceptiondriver.findByHa", query = "SELECT r FROM Resumexceptiondriver r WHERE r.ha = :ha"),
		@NamedQuery(name = "Resumexceptiondriver.findByNightdrive", query = "SELECT r FROM Resumexceptiondriver r WHERE r.nightdrive = :nightdrive"),
		@NamedQuery(name = "Resumexceptiondriver.findByContiniuousdrive", query = "SELECT r FROM Resumexceptiondriver r WHERE r.continiuousdrive = :continiuousdrive"),
		@NamedQuery(name = "Resumexceptiondriver.findByDailydrive", query = "SELECT r FROM Resumexceptiondriver r WHERE r.dailydrive = :dailydrive"),
		@NamedQuery(name = "Resumexceptiondriver.findByDailyrest", query = "SELECT r FROM Resumexceptiondriver r WHERE r.dailyrest = :dailyrest"),
		@NamedQuery(name = "Resumexceptiondriver.findByWeeklydrive", query = "SELECT r FROM Resumexceptiondriver r WHERE r.weeklydrive = :weeklydrive"),
		@NamedQuery(name = "Resumexceptiondriver.findByWeeklyrest", query = "SELECT r FROM Resumexceptiondriver r WHERE r.weeklyrest = :weeklyrest"),
		@NamedQuery(name = "Resumexceptiondriver.findByFatigue", query = "SELECT r FROM Resumexceptiondriver r WHERE r.fatigue = :fatigue"),
		@NamedQuery(name = "Resumexceptiondriver.findByDailywork", query = "SELECT r FROM Resumexceptiondriver r WHERE r.dailywork = :dailywork"),
		@NamedQuery(name = "Resumexceptiondriver.findByWeeklywork", query = "SELECT r FROM Resumexceptiondriver r WHERE r.weeklywork = :weeklywork"),
		@NamedQuery(name = "Resumexceptiondriver.findByPhonedistraction", query = "SELECT r FROM Resumexceptiondriver r WHERE r.phonedistraction = :phonedistraction"),
		@NamedQuery(name = "Resumexceptiondriver.findBySealbelt", query = "SELECT r FROM Resumexceptiondriver r WHERE r.sealbelt = :sealbelt"),
		@NamedQuery(name = "Resumexceptiondriver.findByNoid", query = "SELECT r FROM Resumexceptiondriver r WHERE r.noid = :noid"),
		@NamedQuery(name = "Resumexceptiondriver.findByInsuffisianttripduration", query = "SELECT r FROM Resumexceptiondriver r WHERE r.insuffisianttripduration = :insuffisianttripduration"),
		@NamedQuery(name = "Resumexceptiondriver.findBySmoking", query = "SELECT r FROM Resumexceptiondriver r WHERE r.smoking = :smoking"),
		@NamedQuery(name = "Resumexceptiondriver.findByActivefleet", query = "SELECT r FROM Resumexceptiondriver r WHERE r.activefleet = :activefleet") })
public class Resumexceptiondriver implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "activefleet")
	private String activefleet;
	@Column(name = "continiuousdrive")
	private String continiuousdrive;
	@Column(name = "dailydrive")
	private String dailydrive;
	@Column(name = "dailyrest")
	private String dailyrest;
	@Column(name = "dailywork")
	private String dailywork;
	@JoinColumn(name = "driverid", referencedColumnName = "driverid", insertable = false, updatable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Driver driver;
	@Column(name = "fatigue")
	private String fatigue;
	@Column(name = "ha")
	private String ha;
	@Column(name = "hb")
	private String hb;
	@Column(name = "insuffisianttripduration")
	private String insuffisianttripduration;
	@Column(name = "nightdrive")
	private String nightdrive;
	@Column(name = "noid")
	private String noid;
	@Column(name = "phonedistraction")
	private String phonedistraction;
	@EmbeddedId
	protected ResumexceptiondriverPK resumexceptiondriverPK;
	@Column(name = "sealbelt")
	private String sealbelt;
	@Column(name = "smoking")
	private String smoking;
	@Column(name = "speedingfnbr")
	private String speedingfnbr;
	@Column(name = "weeklydrive")
	private String weeklydrive;
	@Column(name = "weeklyrest")
	private String weeklyrest;
	@Column(name = "weeklywork")
	private String weeklywork;

	public Resumexceptiondriver() {
	}

	public Resumexceptiondriver(final int driverid, final Date dates, final int temporalites) {
		this.resumexceptiondriverPK = new ResumexceptiondriverPK(driverid, dates, temporalites);
	}

	public Resumexceptiondriver(final ResumexceptiondriverPK resumexceptiondriverPK) {
		this.resumexceptiondriverPK = resumexceptiondriverPK;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Resumexceptiondriver)) {
			return false;
		}
		final Resumexceptiondriver other = (Resumexceptiondriver) object;
		return (this.resumexceptiondriverPK != null || other.resumexceptiondriverPK == null)
				&& (this.resumexceptiondriverPK == null
						|| this.resumexceptiondriverPK.equals(other.resumexceptiondriverPK));
	}

	public String getActivefleet() {
		return this.activefleet;
	}

	public String getContiniuousdrive() {
		return this.continiuousdrive;
	}

	public String getDailydrive() {
		return this.dailydrive;
	}

	public String getDailyrest() {
		return this.dailyrest;
	}

	public String getDailywork() {
		return this.dailywork;
	}

	public Driver getDriver() {
		return this.driver;
	}

	public String getFatigue() {
		return this.fatigue;
	}

	public String getHa() {
		return this.ha;
	}

	public String getHb() {
		return this.hb;
	}

	public String getInsuffisianttripduration() {
		return this.insuffisianttripduration;
	}

	public String getNightdrive() {
		return this.nightdrive;
	}

	public String getNoid() {
		return this.noid;
	}

	public String getPhonedistraction() {
		return this.phonedistraction;
	}

	public ResumexceptiondriverPK getResumexceptiondriverPK() {
		return this.resumexceptiondriverPK;
	}

	public String getSealbelt() {
		return this.sealbelt;
	}

	public String getSmoking() {
		return this.smoking;
	}

	public String getSpeedingfnbr() {
		return this.speedingfnbr;
	}

	public String getWeeklydrive() {
		return this.weeklydrive;
	}

	public String getWeeklyrest() {
		return this.weeklyrest;
	}

	public String getWeeklywork() {
		return this.weeklywork;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.resumexceptiondriverPK != null) ? this.resumexceptiondriverPK.hashCode() : 0);
		return hash;
	}

	public void setActivefleet(final String activefleet) {
		this.activefleet = activefleet;
	}

	public void setContiniuousdrive(final String continiuousdrive) {
		this.continiuousdrive = continiuousdrive;
	}

	public void setDailydrive(final String dailydrive) {
		this.dailydrive = dailydrive;
	}

	public void setDailyrest(final String dailyrest) {
		this.dailyrest = dailyrest;
	}

	public void setDailywork(final String dailywork) {
		this.dailywork = dailywork;
	}

	public void setDriver(final Driver driver) {
		this.driver = driver;
	}

	public void setFatigue(final String fatigue) {
		this.fatigue = fatigue;
	}

	public void setHa(final String ha) {
		this.ha = ha;
	}

	public void setHb(final String hb) {
		this.hb = hb;
	}

	public void setInsuffisianttripduration(final String insuffisianttripduration) {
		this.insuffisianttripduration = insuffisianttripduration;
	}

	public void setNightdrive(final String nightdrive) {
		this.nightdrive = nightdrive;
	}

	public void setNoid(final String noid) {
		this.noid = noid;
	}

	public void setPhonedistraction(final String phonedistraction) {
		this.phonedistraction = phonedistraction;
	}

	public void setResumexceptiondriverPK(final ResumexceptiondriverPK resumexceptiondriverPK) {
		this.resumexceptiondriverPK = resumexceptiondriverPK;
	}

	public void setSealbelt(final String sealbelt) {
		this.sealbelt = sealbelt;
	}

	public void setSmoking(final String smoking) {
		this.smoking = smoking;
	}

	public void setSpeedingfnbr(final String speedingfnbr) {
		this.speedingfnbr = speedingfnbr;
	}

	public void setWeeklydrive(final String weeklydrive) {
		this.weeklydrive = weeklydrive;
	}

	public void setWeeklyrest(final String weeklyrest) {
		this.weeklyrest = weeklyrest;
	}

	public void setWeeklywork(final String weeklywork) {
		this.weeklywork = weeklywork;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Resumexceptiondriver[ resumexceptiondriverPK=" + this.resumexceptiondriverPK
				+ " ]";
	}
}
