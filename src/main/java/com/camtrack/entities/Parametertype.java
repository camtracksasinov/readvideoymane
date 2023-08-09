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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "parametertype")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Parametertype.findAll", query = "SELECT p FROM Parametertype p"),
		@NamedQuery(name = "Parametertype.findByParametertypeid", query = "SELECT p FROM Parametertype p WHERE p.parametertypeid = :parametertypeid"),
		@NamedQuery(name = "Parametertype.findByName", query = "SELECT p FROM Parametertype p WHERE p.name = :name"),
		@NamedQuery(name = "Parametertype.findByShortname", query = "SELECT p FROM Parametertype p WHERE p.shortname = :shortname"),
		@NamedQuery(name = "Parametertype.findByLabel", query = "SELECT p FROM Parametertype p WHERE p.label = :label") })
public class Parametertype implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	@OneToMany(mappedBy = "paramtypeid", fetch = FetchType.LAZY)
	private List<Emailconfig> emailconfigList;
	@Column(name = "eventcode")
	private String eventcode;
	@JsonIgnore
	@OneToMany(mappedBy = "exceptiontype", fetch = FetchType.LAZY)
	private List<Exception> exceptionList;
	@JsonIgnore
	@OneToMany(mappedBy = "exceptiontype", fetch = FetchType.LAZY)
	private List<Invalidateexception> invalidateexceptionList;
	@JsonIgnore
	@Column(name = "label")
	private String label;
	@Column(name = "name")
	private String name;
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "parametertypeid", fetch = FetchType.LAZY)
	private List<Parameterconfig> parameterconfigList;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "parametertypeid")
	private Integer parametertypeid;
	@Column(name = "shortname")
	private String shortname;
	@Column(name = "sources")
	private String sources;
	@JsonIgnore
	@JoinColumn(name = "status", referencedColumnName = "statusid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Status status;
	@JsonIgnore
	@JoinColumn(name = "unittypeid", referencedColumnName = "unittypeid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Unittype unittypeid;
	@JsonIgnore
	@OneToMany(mappedBy = "idtypealert", fetch = FetchType.LAZY)
	private List<Userights> userightsList;

	public Parametertype() {
	}

	public Parametertype(final Integer parametertypeid) {
		this.parametertypeid = parametertypeid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Parametertype)) {
			return false;
		}
		final Parametertype other = (Parametertype) object;
		return (this.parametertypeid != null || other.parametertypeid == null)
				&& (this.parametertypeid == null || this.parametertypeid.equals(other.parametertypeid));
	}

	public List<Emailconfig> getEmailconfigList() {
		return this.emailconfigList;
	}

	public String getEventcode() {
		return this.eventcode;
	}

	@XmlTransient
	public List<Exception> getExceptionList() {
		return this.exceptionList;
	}

	public List<Invalidateexception> getInvalidateexceptionList() {
		return this.invalidateexceptionList;
	}

	public String getLabel() {
		return this.label;
	}

	public String getName() {
		return this.name;
	}

	@XmlTransient
	public List<Parameterconfig> getParameterconfigList() {
		return this.parameterconfigList;
	}

	public Integer getParametertypeid() {
		return this.parametertypeid;
	}

	public String getShortname() {
		return this.shortname;
	}

	public String getSources() {
		return this.sources;
	}

	public Status getStatus() {
		return this.status;
	}

	public Unittype getUnittypeid() {
		return this.unittypeid;
	}

	public List<Userights> getUserightsList() {
		return this.userightsList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.parametertypeid != null) ? this.parametertypeid.hashCode() : 0);
		return hash;
	}

	public void setEmailconfigList(final List<Emailconfig> emailconfigList) {
		this.emailconfigList = emailconfigList;
	}

	public void setEventcode(final String eventcode) {
		this.eventcode = eventcode;
	}

	public void setExceptionList(final List<Exception> exceptionList) {
		this.exceptionList = exceptionList;
	}

	public void setInvalidateexceptionList(final List<Invalidateexception> invalidateexceptionList) {
		this.invalidateexceptionList = invalidateexceptionList;
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

	public void setParametertypeid(final Integer parametertypeid) {
		this.parametertypeid = parametertypeid;
	}

	public void setShortname(final String shortname) {
		this.shortname = shortname;
	}

	public void setSources(final String sources) {
		this.sources = sources;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	public void setUnittypeid(final Unittype unittypeid) {
		this.unittypeid = unittypeid;
	}

	public void setUserightsList(final List<Userights> userightsList) {
		this.userightsList = userightsList;
	}

	@Override
	public String toString() {
		return "com.services.mintrans.entities.Parametertype[ parametertypeid=" + this.parametertypeid + " ]";
	}
}
