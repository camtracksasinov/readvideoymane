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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "vehicletrips")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Vehicletrips.findAll", query = "SELECT v FROM Vehicletrips v"),
		@NamedQuery(name = "Vehicletrips.findById", query = "SELECT v FROM Vehicletrips v WHERE v.id = :id"),
		@NamedQuery(name = "Vehicletrips.findByTotaldistance", query = "SELECT v FROM Vehicletrips v WHERE v.totaldistance = :totaldistance"),
		@NamedQuery(name = "Vehicletrips.findByUtclastupdated", query = "SELECT v FROM Vehicletrips v WHERE v.utclastupdated = :utclastupdated"),
		@NamedQuery(name = "Vehicletrips.findByStartdatetime", query = "SELECT v FROM Vehicletrips v WHERE v.startdatetime = :startdatetime"),
		@NamedQuery(name = "Vehicletrips.findByEnddatetime", query = "SELECT v FROM Vehicletrips v WHERE v.enddatetime = :enddatetime") })
public class Vehicletrips implements Serializable {
	private static final long serialVersionUID = 1L;
	@JoinColumn(name = "affiliateid", referencedColumnName = "affiliateid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Customeraffiliate affiliateid;
	@JoinColumn(name = "clientid", referencedColumnName = "customerid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Customer clientid;
	@Column(name = "days")
	@Temporal(TemporalType.DATE)
	private Date days;
	@JoinColumn(name = "driverid", referencedColumnName = "driverid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Driver driverid;
	@Column(name = "enddatetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date enddatetime;
	@Column(name = "fuels")
	private BigDecimal fuels;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Column(name = "months")
	@Temporal(TemporalType.DATE)
	private Date months;
	@JoinColumn(name = "platformid", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Platformtypes platformid;
	@Column(name = "quarters")
	@Temporal(TemporalType.DATE)
	private Date quarters;
	@Column(name = "semesters")
	@Temporal(TemporalType.DATE)
	private Date semesters;
	@Column(name = "startdatetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startdatetime;
	@Column(name = "totaldistance")
	private BigDecimal totaldistance;
	@JoinColumn(name = "transporterid", referencedColumnName = "transporterid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Transporter transporterid;
	@Column(name = "trimesters")
	@Temporal(TemporalType.DATE)
	private Date trimesters;
	@Column(name = "utclastupdated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date utclastupdated;
	@JoinColumn(name = "vehicleid", referencedColumnName = "vehicleid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Vehicle vehicleid;
	@Column(name = "weeks")
	@Temporal(TemporalType.DATE)
	private Date weeks;

	public Vehicletrips() {
	}

	public Vehicletrips(final Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Vehicletrips)) {
			return false;
		}
		final Vehicletrips other = (Vehicletrips) object;
		return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
	}

	public Customeraffiliate getAffiliateid() {
		return this.affiliateid;
	}

	public Customer getClientid() {
		return this.clientid;
	}

	public Date getDays() {
		return this.days;
	}

	public Driver getDriverid() {
		return this.driverid;
	}

	public Date getEnddatetime() {
		return this.enddatetime;
	}

	public BigDecimal getFuels() {
		return this.fuels;
	}

	public Long getId() {
		return this.id;
	}

	public Date getMonths() {
		return this.months;
	}

	public Platformtypes getPlatformid() {
		return this.platformid;
	}

	public Date getQuarters() {
		return this.quarters;
	}

	public Date getSemesters() {
		return this.semesters;
	}

	public Date getStartdatetime() {
		return this.startdatetime;
	}

	public BigDecimal getTotaldistance() {
		return this.totaldistance;
	}

	public Transporter getTransporterid() {
		return this.transporterid;
	}

	public Date getTrimesters() {
		return this.trimesters;
	}

	public Date getUtclastupdated() {
		return this.utclastupdated;
	}

	public Vehicle getVehicleid() {
		return this.vehicleid;
	}

	public Date getWeeks() {
		return this.weeks;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.id != null) ? this.id.hashCode() : 0);
		return hash;
	}

	public void setAffiliateid(final Customeraffiliate affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setClientid(final Customer clientid) {
		this.clientid = clientid;
	}

	public void setDays(final Date days) {
		this.days = days;
	}

	public void setDriverid(final Driver driverid) {
		this.driverid = driverid;
	}

	public void setEnddatetime(final Date enddatetime) {
		this.enddatetime = enddatetime;
	}

	public void setFuels(final BigDecimal fuels) {
		this.fuels = fuels;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setMonths(final Date months) {
		this.months = months;
	}

	public void setPlatformid(final Platformtypes platformid) {
		this.platformid = platformid;
	}

	public void setQuarters(final Date quarters) {
		this.quarters = quarters;
	}

	public void setSemesters(final Date semesters) {
		this.semesters = semesters;
	}

	public void setStartdatetime(final Date startdatetime) {
		this.startdatetime = startdatetime;
	}

	public void setTotaldistance(final BigDecimal totaldistance) {
		this.totaldistance = totaldistance;
	}

	public void setTransporterid(final Transporter transporterid) {
		this.transporterid = transporterid;
	}

	public void setTrimesters(final Date trimesters) {
		this.trimesters = trimesters;
	}

	public void setUtclastupdated(final Date utclastupdated) {
		this.utclastupdated = utclastupdated;
	}

	public void setVehicleid(final Vehicle vehicleid) {
		this.vehicleid = vehicleid;
	}

	public void setWeeks(final Date weeks) {
		this.weeks = weeks;
	}

	@Override
	public String toString() {
		return "com.camtrack.trips.entities.Vehicletrips[ id=" + this.id + " ]";
	}
}
