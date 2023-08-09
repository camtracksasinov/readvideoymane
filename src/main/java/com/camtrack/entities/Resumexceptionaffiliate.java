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
@Table(name = "resumexceptionaffiliate")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "Resumexceptionaffiliate.findAll", query = "SELECT r FROM Resumexceptionaffiliate r"),
		@NamedQuery(name = "Resumexceptionaffiliate.findByAffiliateid", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.resumexceptionaffiliatePK.affiliateid = :affiliateid"),
		@NamedQuery(name = "Resumexceptionaffiliate.findByDates", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.resumexceptionaffiliatePK.dates = :dates"),
		@NamedQuery(name = "Resumexceptionaffiliate.findByTemporalites", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.resumexceptionaffiliatePK.temporalites = :temporalites"),
		@NamedQuery(name = "Resumexceptionaffiliate.findBySpeedingfnbr", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.speedingfnbr = :speedingfnbr"),
		@NamedQuery(name = "Resumexceptionaffiliate.findByHb", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.hb = :hb"),
		@NamedQuery(name = "Resumexceptionaffiliate.findByHa", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.ha = :ha"),
		@NamedQuery(name = "Resumexceptionaffiliate.findByNightdrive", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.nightdrive = :nightdrive"),
		@NamedQuery(name = "Resumexceptionaffiliate.findByContiniuousdrive", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.continiuousdrive = :continiuousdrive"),
		@NamedQuery(name = "Resumexceptionaffiliate.findByDailydrive", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.dailydrive = :dailydrive"),
		@NamedQuery(name = "Resumexceptionaffiliate.findByDailyrest", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.dailyrest = :dailyrest"),
		@NamedQuery(name = "Resumexceptionaffiliate.findByWeeklydrive", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.weeklydrive = :weeklydrive"),
		@NamedQuery(name = "Resumexceptionaffiliate.findByWeeklyrest", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.weeklyrest = :weeklyrest"),
		@NamedQuery(name = "Resumexceptionaffiliate.findByFatigue", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.fatigue = :fatigue"),
		@NamedQuery(name = "Resumexceptionaffiliate.findByDailywork", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.dailywork = :dailywork"),
		@NamedQuery(name = "Resumexceptionaffiliate.findByWeeklywork", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.weeklywork = :weeklywork"),
		@NamedQuery(name = "Resumexceptionaffiliate.findByPhonedistraction", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.phonedistraction = :phonedistraction"),
		@NamedQuery(name = "Resumexceptionaffiliate.findBySealbelt", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.sealbelt = :sealbelt"),
		@NamedQuery(name = "Resumexceptionaffiliate.findByNoid", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.noid = :noid"),
		@NamedQuery(name = "Resumexceptionaffiliate.findByInsuffisianttripduration", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.insuffisianttripduration = :insuffisianttripduration"),
		@NamedQuery(name = "Resumexceptionaffiliate.findBySmoking", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.smoking = :smoking"),
		@NamedQuery(name = "Resumexceptionaffiliate.findByActivefleet", query = "SELECT r FROM Resumexceptionaffiliate r WHERE r.activefleet = :activefleet") })
public class Resumexceptionaffiliate implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "activefleet")
	private String activefleet;
	@Column(name = "continiuousdrive")
	private String continiuousdrive;
	@JoinColumn(name = "affiliateid", referencedColumnName = "affiliateid", insertable = false, updatable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Customeraffiliate customeraffiliate;
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
	protected ResumexceptionaffiliatePK resumexceptionaffiliatePK;
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

	public Resumexceptionaffiliate() {
	}

	public Resumexceptionaffiliate(final int affiliateid, final Date dates, final int temporalites) {
		this.resumexceptionaffiliatePK = new ResumexceptionaffiliatePK(affiliateid, dates, temporalites);
	}

	public Resumexceptionaffiliate(final ResumexceptionaffiliatePK resumexceptionaffiliatePK) {
		this.resumexceptionaffiliatePK = resumexceptionaffiliatePK;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Resumexceptionaffiliate)) {
			return false;
		}
		final Resumexceptionaffiliate other = (Resumexceptionaffiliate) object;
		return (this.resumexceptionaffiliatePK != null || other.resumexceptionaffiliatePK == null)
				&& (this.resumexceptionaffiliatePK == null
						|| this.resumexceptionaffiliatePK.equals(other.resumexceptionaffiliatePK));
	}

	public String getActivefleet() {
		return this.activefleet;
	}

	public String getContiniuousdrive() {
		return this.continiuousdrive;
	}

	public Customeraffiliate getCustomeraffiliate() {
		return this.customeraffiliate;
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

	public ResumexceptionaffiliatePK getResumexceptionaffiliatePK() {
		return this.resumexceptionaffiliatePK;
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
		hash += ((this.resumexceptionaffiliatePK != null) ? this.resumexceptionaffiliatePK.hashCode() : 0);
		return hash;
	}

	public void setActivefleet(final String activefleet) {
		this.activefleet = activefleet;
	}

	public void setContiniuousdrive(final String continiuousdrive) {
		this.continiuousdrive = continiuousdrive;
	}

	public void setCustomeraffiliate(final Customeraffiliate customeraffiliate) {
		this.customeraffiliate = customeraffiliate;
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

	public void setResumexceptionaffiliatePK(final ResumexceptionaffiliatePK resumexceptionaffiliatePK) {
		this.resumexceptionaffiliatePK = resumexceptionaffiliatePK;
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
		return "com.camtrack.entities.Resumexceptionaffiliate[ resumexceptionaffiliatePK="
				+ this.resumexceptionaffiliatePK + " ]";
	}
}
