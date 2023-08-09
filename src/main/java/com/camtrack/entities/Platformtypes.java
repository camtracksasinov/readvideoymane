//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "platformtypes")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Platformtypes.findAll", query = "SELECT p FROM Platformtypes p"),
		@NamedQuery(name = "Platformtypes.findById", query = "SELECT p FROM Platformtypes p WHERE p.id = :id"),
		@NamedQuery(name = "Platformtypes.findByDescription", query = "SELECT p FROM Platformtypes p WHERE p.description = :description"),
		@NamedQuery(name = "Platformtypes.findByShortname", query = "SELECT p FROM Platformtypes p WHERE p.shortname = :shortname") })
public class Platformtypes implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	@OneToMany(mappedBy = "platformtypeid", fetch = FetchType.LAZY)
	private List<Customeraffliateplatform> customeraffliateplatformList;
	@Column(name = "description")
	private String description;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Column(name = "shortname")
	private String shortname;
	@JsonIgnore
	@OneToMany(mappedBy = "platformtype", fetch = FetchType.LAZY)
	private List<Telematicplatform> telematicplatformList;
	@JsonIgnore
	@OneToMany(mappedBy = "platformid", fetch = FetchType.LAZY)
	private List<Vehicletrips> vehicletripsList;

	public Platformtypes() {
	}

	public Platformtypes(final Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Platformtypes)) {
			return false;
		}
		final Platformtypes other = (Platformtypes) object;
		return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
	}

	@XmlTransient
	public List<Customeraffliateplatform> getCustomeraffliateplatformList() {
		return this.customeraffliateplatformList;
	}

	public String getDescription() {
		return this.description;
	}

	public Long getId() {
		return this.id;
	}

	public String getShortname() {
		return this.shortname;
	}

	@XmlTransient
	public List<Telematicplatform> getTelematicplatformList() {
		return this.telematicplatformList;
	}

	@XmlTransient
	public List<Vehicletrips> getVehicletripsList() {
		return this.vehicletripsList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.id != null) ? this.id.hashCode() : 0);
		return hash;
	}

	public void setCustomeraffliateplatformList(final List<Customeraffliateplatform> customeraffliateplatformList) {
		this.customeraffliateplatformList = customeraffliateplatformList;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setShortname(final String shortname) {
		this.shortname = shortname;
	}

	public void setTelematicplatformList(final List<Telematicplatform> telematicplatformList) {
		this.telematicplatformList = telematicplatformList;
	}

	public void setVehicletripsList(final List<Vehicletrips> vehicletripsList) {
		this.vehicletripsList = vehicletripsList;
	}

	@Override
	public String toString() {
		return "com.camtrack.trips.entities.Platformtypes[ id=" + this.id + " ]";
	}
}
