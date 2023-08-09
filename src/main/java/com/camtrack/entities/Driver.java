//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "driver")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Driver.findAll", query = "SELECT d FROM Driver d"),
		@NamedQuery(name = "Driver.findByDriverid", query = "SELECT d FROM Driver d WHERE d.driverid = :driverid"),
		@NamedQuery(name = "Driver.findByName", query = "SELECT d FROM Driver d WHERE d.name = :name"),
		@NamedQuery(name = "Driver.findByStatus", query = "SELECT d FROM Driver d WHERE d.status = :status"),
		@NamedQuery(name = "Driver.findByDriverkeycode", query = "SELECT d FROM Driver d WHERE d.driverkeycode = :driverkeycode"),
		@NamedQuery(name = "Driver.findByVehicleid", query = "SELECT d FROM Driver d WHERE d.vehicleid = :vehicleid"),
		@NamedQuery(name = "Driver.findByCreatedon", query = "SELECT d FROM Driver d WHERE d.createdon = :createdon") })
public class Driver implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	@JoinColumn(name = "affiliateid", referencedColumnName = "affiliateid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Customeraffiliate affiliateid;
	@JsonIgnore
	@Column(name = "createdon")
	@Temporal(TemporalType.DATE)
	private Date createdon;
	@JsonIgnore
	@OneToMany(mappedBy = "driverid", fetch = FetchType.LAZY)
	private List<Driveractivitysummary> driveractivitysummaryList;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "driverid")
	private Integer driverid;
	@Column(name = "driverkeycode")
	private String driverkeycode;
	@JsonIgnore
	@OneToMany(mappedBy = "driverid", fetch = FetchType.LAZY)
	private List<Drivertrips> drivertripsList;
	@JsonIgnore
	@OneToMany(mappedBy = "driverid", fetch = FetchType.LAZY)
	private List<Exception> exceptionList;
	@JsonIgnore
	@OneToMany(mappedBy = "driverid", fetch = FetchType.LAZY)
	private List<Invalidateexception> invalidateexceptionList;
	@Column(name = "name")
	private String name;
	@JsonIgnore
	@OneToMany(mappedBy = "driverid", fetch = FetchType.LAZY)
	private List<Remaningpoings> remaningpoingsList;
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "driver", fetch = FetchType.LAZY)
	private List<Resumexceptiondriver> resumexceptiondriverList;
	@JsonIgnore
	@Column(name = "status")
	private Integer status;
	@JoinColumn(name = "transporterid", referencedColumnName = "transporterid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Transporter transporterid;
	@JsonIgnore
	@OneToMany(mappedBy = "driverid", fetch = FetchType.LAZY)
	private List<Vehicleactivitysummary> vehicleactivitysummaryList;
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "driver", fetch = FetchType.LAZY)
	private List<Vehicledriver> vehicledriverList;
	@Column(name = "vehicleid")
	private Integer vehicleid;
	@JsonIgnore
	@OneToMany(mappedBy = "driverid", fetch = FetchType.LAZY)
	private List<Vehicletrips> vehicletripsList;

	public Driver() {
	}

	public Driver(final Integer driverid) {
		this.driverid = driverid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Driver)) {
			return false;
		}
		final Driver other = (Driver) object;
		return (this.driverid != null || other.driverid == null)
				&& (this.driverid == null || this.driverid.equals(other.driverid));
	}

	public Customeraffiliate getAffiliateid() {
		return this.affiliateid;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public List<Driveractivitysummary> getDriveractivitysummaryList() {
		return this.driveractivitysummaryList;
	}

	public Integer getDriverid() {
		return this.driverid;
	}

	public String getDriverkeycode() {
		return this.driverkeycode;
	}

	public List<Drivertrips> getDrivertripsList() {
		return this.drivertripsList;
	}

	public List<Exception> getExceptionList() {
		return this.exceptionList;
	}

	public List<Invalidateexception> getInvalidateexceptionList() {
		return this.invalidateexceptionList;
	}

	public String getName() {
		return this.name;
	}

	public List<Remaningpoings> getRemaningpoingsList() {
		return this.remaningpoingsList;
	}

	public List<Resumexceptiondriver> getResumexceptiondriverList() {
		return this.resumexceptiondriverList;
	}

	public Integer getStatus() {
		return this.status;
	}

	public Transporter getTransporterid() {
		return this.transporterid;
	}

	public List<Vehicleactivitysummary> getVehicleactivitysummaryList() {
		return this.vehicleactivitysummaryList;
	}

	@XmlTransient
	public List<Vehicledriver> getVehicledriverList() {
		return this.vehicledriverList;
	}

	public Integer getVehicleid() {
		return this.vehicleid;
	}

	public List<Vehicletrips> getVehicletripsList() {
		return this.vehicletripsList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.driverid != null) ? this.driverid.hashCode() : 0);
		return hash;
	}

	public void setAffiliateid(final Customeraffiliate affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setCreatedon(final Date createdon) {
		this.createdon = createdon;
	}

	public void setDriveractivitysummaryList(final List<Driveractivitysummary> driveractivitysummaryList) {
		this.driveractivitysummaryList = driveractivitysummaryList;
	}

	public void setDriverid(final Integer driverid) {
		this.driverid = driverid;
	}

	public void setDriverkeycode(final String driverkeycode) {
		this.driverkeycode = driverkeycode;
	}

	public void setDrivertripsList(final List<Drivertrips> drivertripsList) {
		this.drivertripsList = drivertripsList;
	}

	public void setExceptionList(final List<Exception> exceptionList) {
		this.exceptionList = exceptionList;
	}

	public void setInvalidateexceptionList(final List<Invalidateexception> invalidateexceptionList) {
		this.invalidateexceptionList = invalidateexceptionList;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setRemaningpoingsList(final List<Remaningpoings> remaningpoingsList) {
		this.remaningpoingsList = remaningpoingsList;
	}

	public void setResumexceptiondriverList(final List<Resumexceptiondriver> resumexceptiondriverList) {
		this.resumexceptiondriverList = resumexceptiondriverList;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public void setTransporterid(final Transporter transporterid) {
		this.transporterid = transporterid;
	}

	public void setVehicleactivitysummaryList(final List<Vehicleactivitysummary> vehicleactivitysummaryList) {
		this.vehicleactivitysummaryList = vehicleactivitysummaryList;
	}

	public void setVehicledriverList(final List<Vehicledriver> vehicledriverList) {
		this.vehicledriverList = vehicledriverList;
	}

	public void setVehicleid(final Integer vehicleid) {
		this.vehicleid = vehicleid;
	}

	public void setVehicletripsList(final List<Vehicletrips> vehicletripsList) {
		this.vehicletripsList = vehicletripsList;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Driver[ driverid=" + this.driverid + " ]";
	}
}
