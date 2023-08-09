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
@Table(name = "vehicledriver")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Vehicledriver.findAll", query = "SELECT v FROM Vehicledriver v"),
		@NamedQuery(name = "Vehicledriver.findByVehicleid", query = "SELECT v FROM Vehicledriver v WHERE v.vehicledriverPK.vehicleid = :vehicleid"),
		@NamedQuery(name = "Vehicledriver.findByDriverid", query = "SELECT v FROM Vehicledriver v WHERE v.vehicledriverPK.driverid = :driverid"),
		@NamedQuery(name = "Vehicledriver.findByRegdatetime", query = "SELECT v FROM Vehicledriver v WHERE v.vehicledriverPK.regdatetime = :regdatetime"),
		@NamedQuery(name = "Vehicledriver.findByManual", query = "SELECT v FROM Vehicledriver v WHERE v.manual = :manual") })
public class Vehicledriver implements Serializable {
	private static final long serialVersionUID = 1L;
	@JoinColumn(name = "driverid", referencedColumnName = "driverid", insertable = false, updatable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Driver driver;
	@Column(name = "manual")
	private Boolean manual;
	@JoinColumn(name = "vehicleid", referencedColumnName = "vehicleid", insertable = false, updatable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Vehicle vehicle;
	@EmbeddedId
	protected VehicledriverPK vehicledriverPK;

	public Vehicledriver() {
	}

	public Vehicledriver(final int vehicleid, final int driverid, final Date regdatetime) {
		this.vehicledriverPK = new VehicledriverPK(vehicleid, driverid, regdatetime);
	}

	public Vehicledriver(final VehicledriverPK vehicledriverPK) {
		this.vehicledriverPK = vehicledriverPK;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Vehicledriver)) {
			return false;
		}
		final Vehicledriver other = (Vehicledriver) object;
		return (this.vehicledriverPK != null || other.vehicledriverPK == null)
				&& (this.vehicledriverPK == null || this.vehicledriverPK.equals(other.vehicledriverPK));
	}

	public Driver getDriver() {
		return this.driver;
	}

	public Boolean getManual() {
		return this.manual;
	}

	public Vehicle getVehicle() {
		return this.vehicle;
	}

	public VehicledriverPK getVehicledriverPK() {
		return this.vehicledriverPK;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.vehicledriverPK != null) ? this.vehicledriverPK.hashCode() : 0);
		return hash;
	}

	public void setDriver(final Driver driver) {
		this.driver = driver;
	}

	public void setManual(final Boolean manual) {
		this.manual = manual;
	}

	public void setVehicle(final Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public void setVehicledriverPK(final VehicledriverPK vehicledriverPK) {
		this.vehicledriverPK = vehicledriverPK;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Vehicledriver[ vehicledriverPK=" + this.vehicledriverPK + " ]";
	}
}
