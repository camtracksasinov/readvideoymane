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
@Table(name = "exceptionlevel")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Exceptionlevel.findAll", query = "SELECT e FROM Exceptionlevel e"),
		@NamedQuery(name = "Exceptionlevel.findByExceptionlevelid", query = "SELECT e FROM Exceptionlevel e WHERE e.exceptionlevelid = :exceptionlevelid"),
		@NamedQuery(name = "Exceptionlevel.findByName", query = "SELECT e FROM Exceptionlevel e WHERE e.name = :name"),
		@NamedQuery(name = "Exceptionlevel.findByLabel", query = "SELECT e FROM Exceptionlevel e WHERE e.label = :label") })
public class Exceptionlevel implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "exceptionlevelid")
	private Integer exceptionlevelid;
	@JsonIgnore
	@OneToMany(mappedBy = "level", fetch = FetchType.LAZY)
	private List<Exception> exceptionList;
	@JsonIgnore
	@OneToMany(mappedBy = "level", fetch = FetchType.LAZY)
	private List<Invalidateexception> invalidateexceptionList;
	@JsonIgnore
	@OneToMany(mappedBy = "glevel", fetch = FetchType.LAZY)
	private List<Invalidateexception> invalidateexceptionList1;
	@JsonIgnore
	@Column(name = "label")
	private String label;
	@Column(name = "name")
	private String name;
	@JsonIgnore
	@OneToMany(mappedBy = "defaultlevelid", fetch = FetchType.LAZY)
	private List<Parameterconfig> parameterconfigList;

	public Exceptionlevel() {
	}

	public Exceptionlevel(final Integer exceptionlevelid) {
		this.exceptionlevelid = exceptionlevelid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Exceptionlevel)) {
			return false;
		}
		final Exceptionlevel other = (Exceptionlevel) object;
		return (this.exceptionlevelid != null || other.exceptionlevelid == null)
				&& (this.exceptionlevelid == null || this.exceptionlevelid.equals(other.exceptionlevelid));
	}

	public Integer getExceptionlevelid() {
		return this.exceptionlevelid;
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

	public String getLabel() {
		return this.label;
	}

	public String getName() {
		return this.name;
	}

	public List<Parameterconfig> getParameterconfigList() {
		return this.parameterconfigList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.exceptionlevelid != null) ? this.exceptionlevelid.hashCode() : 0);
		return hash;
	}

	public void setExceptionlevelid(final Integer exceptionlevelid) {
		this.exceptionlevelid = exceptionlevelid;
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

	public void setLabel(final String label) {
		this.label = label;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setParameterconfigList(final List<Parameterconfig> parameterconfigList) {
		this.parameterconfigList = parameterconfigList;
	}

	@Override
	public String toString() {
		return "com.services.mintrans.entities.Exceptionlevel[ exceptionlevelid=" + this.exceptionlevelid + " ]";
	}
}
