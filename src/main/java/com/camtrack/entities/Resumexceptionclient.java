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
@Table(name = "resumexceptionclient")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Resumexceptionclient.findAll", query = "SELECT r FROM Resumexceptionclient r"),
		@NamedQuery(name = "Resumexceptionclient.findByClientid", query = "SELECT r FROM Resumexceptionclient r WHERE r.resumexceptionclientPK.clientid = :clientid"),
		@NamedQuery(name = "Resumexceptionclient.findByDates", query = "SELECT r FROM Resumexceptionclient r WHERE r.resumexceptionclientPK.dates = :dates"),
		@NamedQuery(name = "Resumexceptionclient.findByTemporalites", query = "SELECT r FROM Resumexceptionclient r WHERE r.resumexceptionclientPK.temporalites = :temporalites"),
		@NamedQuery(name = "Resumexceptionclient.findBySpeedingfnbr", query = "SELECT r FROM Resumexceptionclient r WHERE r.speedingfnbr = :speedingfnbr"),
		@NamedQuery(name = "Resumexceptionclient.findByHb", query = "SELECT r FROM Resumexceptionclient r WHERE r.hb = :hb"),
		@NamedQuery(name = "Resumexceptionclient.findByHa", query = "SELECT r FROM Resumexceptionclient r WHERE r.ha = :ha"),
		@NamedQuery(name = "Resumexceptionclient.findByNightdrive", query = "SELECT r FROM Resumexceptionclient r WHERE r.nightdrive = :nightdrive"),
		@NamedQuery(name = "Resumexceptionclient.findByContiniuousdrive", query = "SELECT r FROM Resumexceptionclient r WHERE r.continiuousdrive = :continiuousdrive"),
		@NamedQuery(name = "Resumexceptionclient.findByDailydrive", query = "SELECT r FROM Resumexceptionclient r WHERE r.dailydrive = :dailydrive"),
		@NamedQuery(name = "Resumexceptionclient.findByDailyrest", query = "SELECT r FROM Resumexceptionclient r WHERE r.dailyrest = :dailyrest"),
		@NamedQuery(name = "Resumexceptionclient.findByWeeklydrive", query = "SELECT r FROM Resumexceptionclient r WHERE r.weeklydrive = :weeklydrive"),
		@NamedQuery(name = "Resumexceptionclient.findByWeeklyrest", query = "SELECT r FROM Resumexceptionclient r WHERE r.weeklyrest = :weeklyrest"),
		@NamedQuery(name = "Resumexceptionclient.findByFatigue", query = "SELECT r FROM Resumexceptionclient r WHERE r.fatigue = :fatigue"),
		@NamedQuery(name = "Resumexceptionclient.findByDailywork", query = "SELECT r FROM Resumexceptionclient r WHERE r.dailywork = :dailywork"),
		@NamedQuery(name = "Resumexceptionclient.findByWeeklywork", query = "SELECT r FROM Resumexceptionclient r WHERE r.weeklywork = :weeklywork"),
		@NamedQuery(name = "Resumexceptionclient.findByPhonedistraction", query = "SELECT r FROM Resumexceptionclient r WHERE r.phonedistraction = :phonedistraction"),
		@NamedQuery(name = "Resumexceptionclient.findBySealbelt", query = "SELECT r FROM Resumexceptionclient r WHERE r.sealbelt = :sealbelt"),
		@NamedQuery(name = "Resumexceptionclient.findByNoid", query = "SELECT r FROM Resumexceptionclient r WHERE r.noid = :noid"),
		@NamedQuery(name = "Resumexceptionclient.findByInsuffisianttripduration", query = "SELECT r FROM Resumexceptionclient r WHERE r.insuffisianttripduration = :insuffisianttripduration"),
		@NamedQuery(name = "Resumexceptionclient.findBySmoking", query = "SELECT r FROM Resumexceptionclient r WHERE r.smoking = :smoking"),
		@NamedQuery(name = "Resumexceptionclient.findByActivefleet", query = "SELECT r FROM Resumexceptionclient r WHERE r.activefleet = :activefleet") })
public class Resumexceptionclient implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "activefleet")
	private String activefleet;
	@Column(name = "continiuousdrive")
	private String continiuousdrive;
	@JoinColumn(name = "clientid", referencedColumnName = "customerid", insertable = false, updatable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Customer customer;
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
	protected ResumexceptionclientPK resumexceptionclientPK;
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

	public Resumexceptionclient() {
	}

	public Resumexceptionclient(final int clientid, final Date dates, final int temporalites) {
		this.resumexceptionclientPK = new ResumexceptionclientPK(clientid, dates, temporalites);
	}

	public Resumexceptionclient(final ResumexceptionclientPK resumexceptionclientPK) {
		this.resumexceptionclientPK = resumexceptionclientPK;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Resumexceptionclient)) {
			return false;
		}
		final Resumexceptionclient other = (Resumexceptionclient) object;
		return (this.resumexceptionclientPK != null || other.resumexceptionclientPK == null)
				&& (this.resumexceptionclientPK == null
						|| this.resumexceptionclientPK.equals(other.resumexceptionclientPK));
	}

	public String getActivefleet() {
		return this.activefleet;
	}

	public Customer getClientid() {
		return this.customer;
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

	public ResumexceptionclientPK getResumexceptionclientPK() {
		return this.resumexceptionclientPK;
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
		hash += ((this.resumexceptionclientPK != null) ? this.resumexceptionclientPK.hashCode() : 0);
		return hash;
	}

	public void setActivefleet(final String activefleet) {
		this.activefleet = activefleet;
	}

	public void setClientid(final Customer customer) {
		this.customer = customer;
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

	public void setResumexceptionclientPK(final ResumexceptionclientPK resumexceptionclientPK) {
		this.resumexceptionclientPK = resumexceptionclientPK;
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
		return Messages.getString("Resumexceptionclient.0") + this.resumexceptionclientPK
				+ Messages.getString("Resumexceptionclient.1");
	}
}
