/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author alanic
 */
@Entity
@Table(name = "vehicletype")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Vehicletype.findAll", query = "SELECT v FROM Vehicletype v"),
		@NamedQuery(name = "Vehicletype.findByIds", query = "SELECT v FROM Vehicletype v WHERE v.ids = :ids"),
		@NamedQuery(name = "Vehicletype.findByNames", query = "SELECT v FROM Vehicletype v WHERE v.names = :names") })
public class Vehicletype implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ids")
	private Integer ids;
	@Column(name = "names")
	private String names;
	@JsonIgnore
	@OneToMany(mappedBy = "vehicletypeid", fetch = FetchType.LAZY)
	private List<Vehicle> vehicleList;

	public Vehicletype() {
	}

	public Vehicletype(Integer ids) {
		this.ids = ids;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Vehicletype)) {
			return false;
		}
		Vehicletype other = (Vehicletype) object;
		if ((this.ids == null && other.ids != null) || (this.ids != null && !this.ids.equals(other.ids))) {
			return false;
		}
		return true;
	}

	public Integer getIds() {
		return ids;
	}

	public String getNames() {
		return names;
	}

	@XmlTransient
	public List<Vehicle> getVehicleList() {
		return vehicleList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (ids != null ? ids.hashCode() : 0);
		return hash;
	}

	public void setIds(Integer ids) {
		this.ids = ids;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public void setVehicleList(List<Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Vehicletype[ ids=" + ids + " ]";
	}

}
