//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "status")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Status.findAll", query = "SELECT s FROM Status s"),
		@NamedQuery(name = "Status.findByStatusid", query = "SELECT s FROM Status s WHERE s.statusid = :statusid"),
		@NamedQuery(name = "Status.findByDescription", query = "SELECT s FROM Status s WHERE s.description = :description") })
public class Status implements Serializable {
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "description")
	private String description;
	@JsonIgnore
	@OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
	private List<Exception> exceptionList;
	@JsonIgnore
	@OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
	private List<Invalidateexception> invalidateexceptionList;
	@JsonIgnore
	@OneToMany(mappedBy = "gstatus", fetch = FetchType.LAZY)
	private List<Invalidateexception> invalidateexceptionList1;
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "menustatusid", fetch = FetchType.LAZY)
	private List<Menu> menuList;
	@JsonIgnore
	@OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
	private List<Parametertype> parametertypeList;
	@JsonIgnore
	@OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
	private List<Roadtype> roadtypeList;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "statusid")
	private Integer statusid;
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "status", fetch = FetchType.LAZY)
	private List<Unittype> unittypeList;

	public Status() {
	}

	public Status(final Integer statusid) {
		this.statusid = statusid;
	}

	public Status(final Integer statusid, final String description) {
		this.statusid = statusid;
		this.description = description;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Status)) {
			return false;
		}
		final Status other = (Status) object;
		return (this.statusid != null || other.statusid == null)
				&& (this.statusid == null || this.statusid.equals(other.statusid));
	}

	public String getDescription() {
		return this.description;
	}

	@XmlTransient
	public List<Exception> getExceptionList() {
		return this.exceptionList;
	}

	public List<Invalidateexception> getInvalidateexceptionList() {
		return this.invalidateexceptionList;
	}

	public List<Invalidateexception> getInvalidateexceptionList1() {
		return this.invalidateexceptionList1;
	}

	public List<Menu> getMenuList() {
		return this.menuList;
	}

	public List<Parametertype> getParametertypeList() {
		return this.parametertypeList;
	}

	@XmlTransient
	public List<Roadtype> getRoadtypeList() {
		return this.roadtypeList;
	}

	public Integer getStatusid() {
		return this.statusid;
	}

	@XmlTransient
	public List<Unittype> getUnittypeList() {
		return this.unittypeList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.statusid != null) ? this.statusid.hashCode() : 0);
		return hash;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setExceptionList(final List<Exception> exceptionList) {
		this.exceptionList = exceptionList;
	}

	public void setInvalidateexceptionList(final List<Invalidateexception> invalidateexceptionList) {
		this.invalidateexceptionList = invalidateexceptionList;
	}

	public void setInvalidateexceptionList1(final List<Invalidateexception> invalidateexceptionList1) {
		this.invalidateexceptionList1 = invalidateexceptionList1;
	}

	public void setMenuList(final List<Menu> menuList) {
		this.menuList = menuList;
	}

	public void setParametertypeList(final List<Parametertype> parametertypeList) {
		this.parametertypeList = parametertypeList;
	}

	public void setRoadtypeList(final List<Roadtype> roadtypeList) {
		this.roadtypeList = roadtypeList;
	}

	public void setStatusid(final Integer statusid) {
		this.statusid = statusid;
	}

	public void setUnittypeList(final List<Unittype> unittypeList) {
		this.unittypeList = unittypeList;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Status[ statusid=" + this.statusid + " ]";
	}
}
