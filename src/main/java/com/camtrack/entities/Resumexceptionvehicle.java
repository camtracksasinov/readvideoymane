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
@Table(name = "resumexceptionvehicle")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Resumexceptionvehicle.findAll", query = "SELECT r FROM Resumexceptionvehicle r"),
		@NamedQuery(name = "Resumexceptionvehicle.findByVehicleid", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.resumexceptionvehiclePK.vehicleid = :vehicleid"),
		@NamedQuery(name = "Resumexceptionvehicle.findByDates", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.resumexceptionvehiclePK.dates = :dates"),
		@NamedQuery(name = "Resumexceptionvehicle.findByTemporalites", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.resumexceptionvehiclePK.temporalites = :temporalites"),
		@NamedQuery(name = "Resumexceptionvehicle.findBySpeedingfnbr", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.speedingfnbr = :speedingfnbr"),
		@NamedQuery(name = "Resumexceptionvehicle.findByHb", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.hb = :hb"),
		@NamedQuery(name = "Resumexceptionvehicle.findByHa", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.ha = :ha"),
		@NamedQuery(name = "Resumexceptionvehicle.findByNightdrive", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.nightdrive = :nightdrive"),
		@NamedQuery(name = "Resumexceptionvehicle.findByContiniuousdrive", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.continiuousdrive = :continiuousdrive"),
		@NamedQuery(name = "Resumexceptionvehicle.findByDailydrive", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.dailydrive = :dailydrive"),
		@NamedQuery(name = "Resumexceptionvehicle.findByDailyrest", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.dailyrest = :dailyrest"),
		@NamedQuery(name = "Resumexceptionvehicle.findByWeeklydrive", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.weeklydrive = :weeklydrive"),
		@NamedQuery(name = "Resumexceptionvehicle.findByWeeklyrest", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.weeklyrest = :weeklyrest"),
		@NamedQuery(name = "Resumexceptionvehicle.findByFatigue", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.fatigue = :fatigue"),
		@NamedQuery(name = "Resumexceptionvehicle.findByDailywork", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.dailywork = :dailywork"),
		@NamedQuery(name = "Resumexceptionvehicle.findByWeeklywork", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.weeklywork = :weeklywork"),
		@NamedQuery(name = "Resumexceptionvehicle.findByPhonedistraction", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.phonedistraction = :phonedistraction"),
		@NamedQuery(name = "Resumexceptionvehicle.findBySealbelt", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.sealbelt = :sealbelt"),
		@NamedQuery(name = "Resumexceptionvehicle.findByNoid", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.noid = :noid"),
		@NamedQuery(name = "Resumexceptionvehicle.findByInsuffisianttripduration", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.insuffisianttripduration = :insuffisianttripduration"),
		@NamedQuery(name = "Resumexceptionvehicle.findBySmoking", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.smoking = :smoking"),
		@NamedQuery(name = "Resumexceptionvehicle.findByActivefleet", query = "SELECT r FROM Resumexceptionvehicle r WHERE r.activefleet = :activefleet") })
public class Resumexceptionvehicle implements Serializable {
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
	protected ResumexceptionvehiclePK resumexceptionvehiclePK;
	@Column(name = "sealbelt")
	private String sealbelt;
	@Column(name = "smoking")
	private String smoking;
	@Column(name = "speedingfnbr")
	private String speedingfnbr;
	@JoinColumn(name = "vehicleid", referencedColumnName = "vehicleid", insertable = false, updatable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Vehicle vehicle;
	@Column(name = "weeklydrive")
	private String weeklydrive;
	@Column(name = "weeklyrest")
	private String weeklyrest;
	@Column(name = "weeklywork")
	private String weeklywork;

	public Resumexceptionvehicle() {
	}

	public Resumexceptionvehicle(final int vehicleid, final Date dates, final int temporalites) {
		this.resumexceptionvehiclePK = new ResumexceptionvehiclePK(vehicleid, dates, temporalites);
	}

	public Resumexceptionvehicle(final ResumexceptionvehiclePK resumexceptionvehiclePK) {
		this.resumexceptionvehiclePK = resumexceptionvehiclePK;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Resumexceptionvehicle)) {
			return false;
		}
		final Resumexceptionvehicle other = (Resumexceptionvehicle) object;
		return (this.resumexceptionvehiclePK != null || other.resumexceptionvehiclePK == null)
				&& (this.resumexceptionvehiclePK == null
						|| this.resumexceptionvehiclePK.equals(other.resumexceptionvehiclePK));
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

	public ResumexceptionvehiclePK getResumexceptionvehiclePK() {
		return this.resumexceptionvehiclePK;
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

	public Vehicle getVehicle() {
		return this.vehicle;
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
		hash += ((this.resumexceptionvehiclePK != null) ? this.resumexceptionvehiclePK.hashCode() : 0);
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

	public void setResumexceptionvehiclePK(final ResumexceptionvehiclePK resumexceptionvehiclePK) {
		this.resumexceptionvehiclePK = resumexceptionvehiclePK;
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

	public void setVehicle(final Vehicle vehicle) {
		this.vehicle = vehicle;
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
		return "com.camtrack.entities.Resumexceptionvehicle[ resumexceptionvehiclePK=" + this.resumexceptionvehiclePK
				+ " ]";
	}
}
