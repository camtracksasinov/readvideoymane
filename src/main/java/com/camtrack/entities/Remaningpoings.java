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
@Table(name = "remaningpoings")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Remaningpoings.findAll", query = "SELECT r FROM Remaningpoings r"),
		@NamedQuery(name = "Remaningpoings.findByIds", query = "SELECT r FROM Remaningpoings r WHERE r.ids = :ids"),
		@NamedQuery(name = "Remaningpoings.findByRemainpoints", query = "SELECT r FROM Remaningpoings r WHERE r.remainpoints = :remainpoints"),
		@NamedQuery(name = "Remaningpoings.findByStartmonth", query = "SELECT r FROM Remaningpoings r WHERE r.startmonth = :startmonth"),
		@NamedQuery(name = "Remaningpoings.findByEndmonth", query = "SELECT r FROM Remaningpoings r WHERE r.endmonth = :endmonth"),
		@NamedQuery(name = "Remaningpoings.findByStartyear", query = "SELECT r FROM Remaningpoings r WHERE r.startyear = :startyear"),
		@NamedQuery(name = "Remaningpoings.findByEndyear", query = "SELECT r FROM Remaningpoings r WHERE r.endyear = :endyear") })
public class Remaningpoings implements Serializable {
	private static final long serialVersionUID = 1L;
	@JoinColumn(name = "driverid", referencedColumnName = "driverid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Driver driverid;
	@Column(name = "endmonth")
	private Short endmonth;
	@Column(name = "endyear")
	private Integer endyear;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ids")
	private Long ids;
	@Column(name = "remainpoints")
	private Double remainpoints;
	@Column(name = "startmonth")
	private Short startmonth;
	@Column(name = "startyear")
	private Integer startyear;

	public Remaningpoings() {
	}

	public Remaningpoings(final Long ids) {
		this.ids = ids;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Remaningpoings)) {
			return false;
		}
		final Remaningpoings other = (Remaningpoings) object;
		return (this.ids != null || other.ids == null) && (this.ids == null || this.ids.equals(other.ids));
	}

	public Driver getDriverid() {
		return this.driverid;
	}

	public Short getEndmonth() {
		return this.endmonth;
	}

	public Integer getEndyear() {
		return this.endyear;
	}

	public Long getIds() {
		return this.ids;
	}

	public Double getRemainpoints() {
		return this.remainpoints;
	}

	public Short getStartmonth() {
		return this.startmonth;
	}

	public Integer getStartyear() {
		return this.startyear;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.ids != null) ? this.ids.hashCode() : 0);
		return hash;
	}

	public void setDriverid(final Driver driverid) {
		this.driverid = driverid;
	}

	public void setEndmonth(final Short endmonth) {
		this.endmonth = endmonth;
	}

	public void setEndyear(final Integer endyear) {
		this.endyear = endyear;
	}

	public void setIds(final Long ids) {
		this.ids = ids;
	}

	public void setRemainpoints(final Double remainpoints) {
		this.remainpoints = remainpoints;
	}

	public void setStartmonth(final Short startmonth) {
		this.startmonth = startmonth;
	}

	public void setStartyear(final Integer startyear) {
		this.startyear = startyear;
	}

	@Override
	public String toString() {
		return "com.ymane.entities.Remaningpoings[ ids=" + this.ids + " ]";
	}
}
