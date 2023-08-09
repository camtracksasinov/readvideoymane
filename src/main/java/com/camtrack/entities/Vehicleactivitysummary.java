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
@Table(name = "vehicleactivitysummary")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Vehicleactivitysummary.findAll", query = "SELECT v FROM Vehicleactivitysummary v"),
		@NamedQuery(name = "Vehicleactivitysummary.findById", query = "SELECT v FROM Vehicleactivitysummary v WHERE v.id = :id"),
		@NamedQuery(name = "Vehicleactivitysummary.findByTotaldripduration", query = "SELECT v FROM Vehicleactivitysummary v WHERE v.totaldripduration = :totaldripduration"),
		@NamedQuery(name = "Vehicleactivitysummary.findByValidduration", query = "SELECT v FROM Vehicleactivitysummary v WHERE v.validduration = :validduration"),
		@NamedQuery(name = "Vehicleactivitysummary.findByTotaldistance", query = "SELECT v FROM Vehicleactivitysummary v WHERE v.totaldistance = :totaldistance"),
		@NamedQuery(name = "Vehicleactivitysummary.findByActivitydate", query = "SELECT v FROM Vehicleactivitysummary v WHERE v.activitydate = :activitydate"),
		@NamedQuery(name = "Vehicleactivitysummary.findByUtclastupdated", query = "SELECT v FROM Vehicleactivitysummary v WHERE v.utclastupdated = :utclastupdated"),
		@NamedQuery(name = "Vehicleactivitysummary.findByRequestupdate", query = "SELECT v FROM Vehicleactivitysummary v WHERE v.requestupdate = :requestupdate") })
public class Vehicleactivitysummary implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "activitydate")
	@Temporal(TemporalType.DATE)
	private Date activitydate;
	@JoinColumn(name = "affiliateid", referencedColumnName = "affiliateid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Customeraffiliate affiliateid;
	@JoinColumn(name = "driverid", referencedColumnName = "driverid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Driver driverid;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Column(name = "requestupdate")
	private Boolean requestupdate;
	@Column(name = "totaldistance")
	private BigDecimal totaldistance;
	@Column(name = "totaldripduration")
	private BigDecimal totaldripduration;
	@JoinColumn(name = "transporterid", referencedColumnName = "transporterid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Transporter transporterid;
	@Column(name = "utclastupdated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date utclastupdated;
	@Column(name = "validduration")
	private BigDecimal validduration;
	@JoinColumn(name = "vehicleid", referencedColumnName = "vehicleid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Vehicle vehicleid;

	public Vehicleactivitysummary() {
	}

	public Vehicleactivitysummary(final Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Vehicleactivitysummary)) {
			return false;
		}
		final Vehicleactivitysummary other = (Vehicleactivitysummary) object;
		return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
	}

	public Date getActivitydate() {
		return this.activitydate;
	}

	public Customeraffiliate getAffiliateid() {
		return this.affiliateid;
	}

	public Driver getDriverid() {
		return this.driverid;
	}

	public Long getId() {
		return this.id;
	}

	public Boolean getRequestupdate() {
		return this.requestupdate;
	}

	public BigDecimal getTotaldistance() {
		return this.totaldistance;
	}

	public BigDecimal getTotaldripduration() {
		return this.totaldripduration;
	}

	public Transporter getTransporterid() {
		return this.transporterid;
	}

	public Date getUtclastupdated() {
		return this.utclastupdated;
	}

	public BigDecimal getValidduration() {
		return this.validduration;
	}

	public Vehicle getVehicleid() {
		return this.vehicleid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.id != null) ? this.id.hashCode() : 0);
		return hash;
	}

	public void setActivitydate(final Date activitydate) {
		this.activitydate = activitydate;
	}

	public void setAffiliateid(final Customeraffiliate affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setDriverid(final Driver driverid) {
		this.driverid = driverid;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setRequestupdate(final Boolean requestupdate) {
		this.requestupdate = requestupdate;
	}

	public void setTotaldistance(final BigDecimal totaldistance) {
		this.totaldistance = totaldistance;
	}

	public void setTotaldripduration(final BigDecimal totaldripduration) {
		this.totaldripduration = totaldripduration;
	}

	public void setTransporterid(final Transporter transporterid) {
		this.transporterid = transporterid;
	}

	public void setUtclastupdated(final Date utclastupdated) {
		this.utclastupdated = utclastupdated;
	}

	public void setValidduration(final BigDecimal validduration) {
		this.validduration = validduration;
	}

	public void setVehicleid(final Vehicle vehicleid) {
		this.vehicleid = vehicleid;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Vehicleactivitysummary[ id=" + this.id + " ]";
	}
}
