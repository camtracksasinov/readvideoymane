//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;

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
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "roadtype")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Roadtype.findAll", query = "SELECT r FROM Roadtype r"),
		@NamedQuery(name = "Roadtype.findByRoadtypeid", query = "SELECT r FROM Roadtype r WHERE r.roadtypeid = :roadtypeid"),
		@NamedQuery(name = "Roadtype.findByName", query = "SELECT r FROM Roadtype r WHERE r.name = :name"),
		@NamedQuery(name = "Roadtype.findByShortname", query = "SELECT r FROM Roadtype r WHERE r.shortname = :shortname") })
public class Roadtype implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "name")
	private String name;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "roadtypeid")
	private Integer roadtypeid;
	@Column(name = "shortname")
	private String shortname;
	@JoinColumn(name = "status", referencedColumnName = "statusid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Status status;

	public Roadtype() {
	}

	public Roadtype(final Integer roadtypeid) {
		this.roadtypeid = roadtypeid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Roadtype)) {
			return false;
		}
		final Roadtype other = (Roadtype) object;
		return (this.roadtypeid != null || other.roadtypeid == null)
				&& (this.roadtypeid == null || this.roadtypeid.equals(other.roadtypeid));
	}

	public String getName() {
		return this.name;
	}

	public Integer getRoadtypeid() {
		return this.roadtypeid;
	}

	public String getShortname() {
		return this.shortname;
	}

	public Status getStatus() {
		return this.status;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.roadtypeid != null) ? this.roadtypeid.hashCode() : 0);
		return hash;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setRoadtypeid(final Integer roadtypeid) {
		this.roadtypeid = roadtypeid;
	}

	public void setShortname(final String shortname) {
		this.shortname = shortname;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Roadtype[ roadtypeid=" + this.roadtypeid + " ]";
	}
}
