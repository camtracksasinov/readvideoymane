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
@Table(name = "vehicle")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Vehicle.findAll", query = "SELECT v FROM Vehicle v"),
		@NamedQuery(name = "Vehicle.findByVehicleid", query = "SELECT v FROM Vehicle v WHERE v.vehicleid = :vehicleid"),
		@NamedQuery(name = "Vehicle.findByVehicledesc", query = "SELECT v FROM Vehicle v WHERE v.vehicledesc = :vehicledesc"),
		@NamedQuery(name = "Vehicle.findByStatus", query = "SELECT v FROM Vehicle v WHERE v.status = :status"),
		@NamedQuery(name = "Vehicle.findByVehicleuniqueid", query = "SELECT v FROM Vehicle v WHERE v.vehicleuniqueid = :vehicleuniqueid"),
		@NamedQuery(name = "Vehicle.findByLastreceived", query = "SELECT v FROM Vehicle v WHERE v.lastreceived = :lastreceived"),
		@NamedQuery(name = "Vehicle.findByUnitid", query = "SELECT v FROM Vehicle v WHERE v.unitid = :unitid"),
		@NamedQuery(name = "Vehicle.findByRegno", query = "SELECT v FROM Vehicle v WHERE v.regno = :regno") })
public class Vehicle implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	@OneToMany(mappedBy = "vehicleid", fetch = FetchType.LAZY)
	private List<Driveractivitysummary> driveractivitysummaryList;
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "vehicleid", fetch = FetchType.LAZY)
	private List<Drivertrips> drivertripsList;
	@JsonIgnore
	@OneToMany(mappedBy = "vehicleid", fetch = FetchType.LAZY)
	private List<Emailconfig> emailconfigList;
	@OneToMany(mappedBy = "vehicleid", fetch = FetchType.LAZY)
	private List<Exception> exceptionList;
	@JsonIgnore
	@OneToMany(mappedBy = "vehicleid", fetch = FetchType.LAZY)
	private List<Invalidateexception> invalidateexceptionList;
	@Column(name = "lastreceived")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastreceived;
	@Column(name = "regno")
	private String regno;
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "vehicle", fetch = FetchType.LAZY)
	private List<Resumexceptionvehicle> resumexceptionvehicleList;
	@Column(name = "status")
	private Integer status;
	@JoinColumn(name = "transporterid", referencedColumnName = "transporterid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Transporter transporterid;
	@Column(name = "unitid")
	private String unitid;
	@JsonIgnore
	@OneToMany(mappedBy = "vehicleid", fetch = FetchType.LAZY)
	private List<Vehicleactivitysummary> vehicleactivitysummaryList;
	@Column(name = "vehicledesc")
	private String vehicledesc;
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "vehicle", fetch = FetchType.LAZY)
	private List<Vehicledriver> vehicledriverList;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "vehicleid")
	private Integer vehicleid;
	@JsonIgnore
	@OneToMany(mappedBy = "vehicleid", fetch = FetchType.LAZY)
	private List<Vehicletrips> vehicletripsList;
	@JoinColumn(name = "vehicletypeid", referencedColumnName = "ids")
	@ManyToOne(fetch = FetchType.LAZY)
	private Vehicletype vehicletypeid;

	@Column(name = "vehicleuniqueid")
	private String vehicleuniqueid;

	public Vehicle() {
	}

	public Vehicle(final Integer vehicleid) {
		this.vehicleid = vehicleid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Vehicle)) {
			return false;
		}
		final Vehicle other = (Vehicle) object;
		return (this.vehicleid != null || other.vehicleid == null)
				&& (this.vehicleid == null || this.vehicleid.equals(other.vehicleid));
	}

	public List<Driveractivitysummary> getDriveractivitysummaryList() {
		return this.driveractivitysummaryList;
	}

	public List<Drivertrips> getDrivertripsList() {
		return this.drivertripsList;
	}

	public List<Emailconfig> getEmailconfigList() {
		return this.emailconfigList;
	}

	@XmlTransient
	public List<Exception> getExceptionList() {
		return this.exceptionList;
	}

	public List<Invalidateexception> getInvalidateexceptionList() {
		return this.invalidateexceptionList;
	}

	public Date getLastreceived() {
		return this.lastreceived;
	}

	public String getRegno() {
		return this.regno;
	}

	public List<Resumexceptionvehicle> getResumexceptionvehicleList() {
		return this.resumexceptionvehicleList;
	}

	public Integer getStatus() {
		return this.status;
	}

	public Transporter getTransporterid() {
		return this.transporterid;
	}

	public String getUnitid() {
		return this.unitid;
	}

	public List<Vehicleactivitysummary> getVehicleactivitysummaryList() {
		return this.vehicleactivitysummaryList;
	}

	public String getVehicledesc() {
		return this.vehicledesc;
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

	public Vehicletype getVehicletypeid() {
		return vehicletypeid;
	}

	public String getVehicleuniqueid() {
		return this.vehicleuniqueid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.vehicleid != null) ? this.vehicleid.hashCode() : 0);
		return hash;
	}

	public void setDriveractivitysummaryList(final List<Driveractivitysummary> driveractivitysummaryList) {
		this.driveractivitysummaryList = driveractivitysummaryList;
	}

	public void setDrivertripsList(final List<Drivertrips> drivertripsList) {
		this.drivertripsList = drivertripsList;
	}

	public void setEmailconfigList(final List<Emailconfig> emailconfigList) {
		this.emailconfigList = emailconfigList;
	}

	public void setExceptionList(final List<Exception> exceptionList) {
		this.exceptionList = exceptionList;
	}

	public void setInvalidateexceptionList(final List<Invalidateexception> invalidateexceptionList) {
		this.invalidateexceptionList = invalidateexceptionList;
	}

	public void setLastreceived(final Date lastreceived) {
		this.lastreceived = lastreceived;
	}

	public void setRegno(final String regno) {
		this.regno = regno;
	}

	public void setResumexceptionvehicleList(final List<Resumexceptionvehicle> resumexceptionvehicleList) {
		this.resumexceptionvehicleList = resumexceptionvehicleList;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public void setTransporterid(final Transporter transporterid) {
		this.transporterid = transporterid;
	}

	public void setUnitid(final String unitid) {
		this.unitid = unitid;
	}

	public void setVehicleactivitysummaryList(final List<Vehicleactivitysummary> vehicleactivitysummaryList) {
		this.vehicleactivitysummaryList = vehicleactivitysummaryList;
	}

	public void setVehicledesc(final String vehicledesc) {
		this.vehicledesc = vehicledesc;
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

	public void setVehicletypeid(Vehicletype vehicletypeid) {
		this.vehicletypeid = vehicletypeid;
	}

	public void setVehicleuniqueid(final String vehicleuniqueid) {
		this.vehicleuniqueid = vehicleuniqueid;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Vehicle[ vehicleid=" + this.vehicleid + " ]";
	}
}
