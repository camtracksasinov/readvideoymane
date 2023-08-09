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
@Table(name = "resumexceptiontransporter")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "Resumexceptiontransporter.findAll", query = "SELECT r FROM Resumexceptiontransporter r"),
		@NamedQuery(name = "Resumexceptiontransporter.findByTransporterid", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.resumexceptiontransporterPK.transporterid = :transporterid"),
		@NamedQuery(name = "Resumexceptiontransporter.findByDates", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.resumexceptiontransporterPK.dates = :dates"),
		@NamedQuery(name = "Resumexceptiontransporter.findByTemporalites", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.resumexceptiontransporterPK.temporalites = :temporalites"),
		@NamedQuery(name = "Resumexceptiontransporter.findBySpeedingfnbr", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.speedingfnbr = :speedingfnbr"),
		@NamedQuery(name = "Resumexceptiontransporter.findByHb", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.hb = :hb"),
		@NamedQuery(name = "Resumexceptiontransporter.findByHa", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.ha = :ha"),
		@NamedQuery(name = "Resumexceptiontransporter.findByNightdrive", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.nightdrive = :nightdrive"),
		@NamedQuery(name = "Resumexceptiontransporter.findByContiniuousdrive", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.continiuousdrive = :continiuousdrive"),
		@NamedQuery(name = "Resumexceptiontransporter.findByDailydrive", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.dailydrive = :dailydrive"),
		@NamedQuery(name = "Resumexceptiontransporter.findByDailyrest", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.dailyrest = :dailyrest"),
		@NamedQuery(name = "Resumexceptiontransporter.findByWeeklydrive", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.weeklydrive = :weeklydrive"),
		@NamedQuery(name = "Resumexceptiontransporter.findByWeeklyrest", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.weeklyrest = :weeklyrest"),
		@NamedQuery(name = "Resumexceptiontransporter.findByFatigue", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.fatigue = :fatigue"),
		@NamedQuery(name = "Resumexceptiontransporter.findByDailywork", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.dailywork = :dailywork"),
		@NamedQuery(name = "Resumexceptiontransporter.findByWeeklywork", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.weeklywork = :weeklywork"),
		@NamedQuery(name = "Resumexceptiontransporter.findByPhonedistraction", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.phonedistraction = :phonedistraction"),
		@NamedQuery(name = "Resumexceptiontransporter.findBySealbelt", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.sealbelt = :sealbelt"),
		@NamedQuery(name = "Resumexceptiontransporter.findByNoid", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.noid = :noid"),
		@NamedQuery(name = "Resumexceptiontransporter.findByInsuffisianttripduration", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.insuffisianttripduration = :insuffisianttripduration"),
		@NamedQuery(name = "Resumexceptiontransporter.findBySmoking", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.smoking = :smoking"),
		@NamedQuery(name = "Resumexceptiontransporter.findByActivefleet", query = "SELECT r FROM Resumexceptiontransporter r WHERE r.activefleet = :activefleet") })
public class Resumexceptiontransporter implements Serializable {
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
	protected ResumexceptiontransporterPK resumexceptiontransporterPK;
	@Column(name = "sealbelt")
	private String sealbelt;
	@Column(name = "smoking")
	private String smoking;
	@Column(name = "speedingfnbr")
	private String speedingfnbr;
	@JoinColumn(name = "transporterid", referencedColumnName = "transporterid", insertable = false, updatable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Transporter transporter;
	@Column(name = "weeklydrive")
	private String weeklydrive;
	@Column(name = "weeklyrest")
	private String weeklyrest;
	@Column(name = "weeklywork")
	private String weeklywork;

	public Resumexceptiontransporter() {
	}

	public Resumexceptiontransporter(final int transporterid, final Date dates, final int temporalites) {
		this.resumexceptiontransporterPK = new ResumexceptiontransporterPK(transporterid, dates, temporalites);
	}

	public Resumexceptiontransporter(final ResumexceptiontransporterPK resumexceptiontransporterPK) {
		this.resumexceptiontransporterPK = resumexceptiontransporterPK;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Resumexceptiontransporter)) {
			return false;
		}
		final Resumexceptiontransporter other = (Resumexceptiontransporter) object;
		return (this.resumexceptiontransporterPK != null || other.resumexceptiontransporterPK == null)
				&& (this.resumexceptiontransporterPK == null
						|| this.resumexceptiontransporterPK.equals(other.resumexceptiontransporterPK));
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

	public ResumexceptiontransporterPK getResumexceptiontransporterPK() {
		return this.resumexceptiontransporterPK;
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

	public Transporter getTransporter() {
		return this.transporter;
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
		hash += ((this.resumexceptiontransporterPK != null) ? this.resumexceptiontransporterPK.hashCode() : 0);
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

	public void setResumexceptiontransporterPK(final ResumexceptiontransporterPK resumexceptiontransporterPK) {
		this.resumexceptiontransporterPK = resumexceptiontransporterPK;
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

	public void setTransporter(final Transporter transporter) {
		this.transporter = transporter;
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
		return "com.camtrack.entities.Resumexceptiontransporter[ resumexceptiontransporterPK="
				+ this.resumexceptiontransporterPK + " ]";
	}
}
