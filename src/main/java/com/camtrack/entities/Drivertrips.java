//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "drivertrips")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Drivertrips.findAll", query = "SELECT d FROM Drivertrips d"),
		@NamedQuery(name = "Drivertrips.findByStartdatetime", query = "SELECT d FROM Drivertrips d WHERE d.startdatetime = :startdatetime"),
		@NamedQuery(name = "Drivertrips.findByEnddatetime", query = "SELECT d FROM Drivertrips d WHERE d.enddatetime = :enddatetime"),
		@NamedQuery(name = "Drivertrips.findByStartposition", query = "SELECT d FROM Drivertrips d WHERE d.startposition = :startposition"),
		@NamedQuery(name = "Drivertrips.findByEndpositiion", query = "SELECT d FROM Drivertrips d WHERE d.endpositiion = :endpositiion"),
		@NamedQuery(name = "Drivertrips.findByDistancecovered", query = "SELECT d FROM Drivertrips d WHERE d.distancecovered = :distancecovered"),
		@NamedQuery(name = "Drivertrips.findByDriverkeycode", query = "SELECT d FROM Drivertrips d WHERE d.driverkeycode = :driverkeycode"),
		@NamedQuery(name = "Drivertrips.findByDrivername", query = "SELECT d FROM Drivertrips d WHERE d.drivername = :drivername"),
		@NamedQuery(name = "Drivertrips.findByStartplace", query = "SELECT d FROM Drivertrips d WHERE d.startplace = :startplace"),
		@NamedQuery(name = "Drivertrips.findByEndplace", query = "SELECT d FROM Drivertrips d WHERE d.endplace = :endplace"),
		@NamedQuery(name = "Drivertrips.findById", query = "SELECT d FROM Drivertrips d WHERE d.id = :id") })
public class Drivertrips implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "distancecovered")
	private Double distancecovered;
	@JoinColumn(name = "driverid", referencedColumnName = "driverid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Driver driverid;
	@Column(name = "driverkeycode")
	private String driverkeycode;
	@Column(name = "drivername")
	private String drivername;
	@Column(name = "enddatetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date enddatetime;
	@Column(name = "endplace")
	private BigInteger endplace;
	@Column(name = "endpositiion")
	private String endpositiion;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Column(name = "startdatetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startdatetime;
	@Column(name = "startplace")
	private BigInteger startplace;
	@Column(name = "startposition")
	private String startposition;
	@JoinColumn(name = "vehicleid", referencedColumnName = "vehicleid")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Vehicle vehicleid;

	public Drivertrips() {
	}

	public Drivertrips(final Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Drivertrips)) {
			return false;
		}
		final Drivertrips other = (Drivertrips) object;
		return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
	}

	public Double getDistancecovered() {
		return this.distancecovered;
	}

	public Driver getDriverid() {
		return this.driverid;
	}

	public String getDriverkeycode() {
		return this.driverkeycode;
	}

	public String getDrivername() {
		return this.drivername;
	}

	public Date getEnddatetime() {
		return this.enddatetime;
	}

	public BigInteger getEndplace() {
		return this.endplace;
	}

	public String getEndpositiion() {
		return this.endpositiion;
	}

	public Long getId() {
		return this.id;
	}

	public Date getStartdatetime() {
		return this.startdatetime;
	}

	public BigInteger getStartplace() {
		return this.startplace;
	}

	public String getStartposition() {
		return this.startposition;
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

	public void setDistancecovered(final Double distancecovered) {
		this.distancecovered = distancecovered;
	}

	public void setDriverid(final Driver driverid) {
		this.driverid = driverid;
	}

	public void setDriverkeycode(final String driverkeycode) {
		this.driverkeycode = driverkeycode;
	}

	public void setDrivername(final String drivername) {
		this.drivername = drivername;
	}

	public void setEnddatetime(final Date enddatetime) {
		this.enddatetime = enddatetime;
	}

	public void setEndplace(final BigInteger endplace) {
		this.endplace = endplace;
	}

	public void setEndpositiion(final String endpositiion) {
		this.endpositiion = endpositiion;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setStartdatetime(final Date startdatetime) {
		this.startdatetime = startdatetime;
	}

	public void setStartplace(final BigInteger startplace) {
		this.startplace = startplace;
	}

	public void setStartposition(final String startposition) {
		this.startposition = startposition;
	}

	public void setVehicleid(final Vehicle vehicleid) {
		this.vehicleid = vehicleid;
	}

	@Override
	public String toString() {
		return "com.services.mintrans.entities.Drivertrips[ id=" + this.id + " ]";
	}
}
