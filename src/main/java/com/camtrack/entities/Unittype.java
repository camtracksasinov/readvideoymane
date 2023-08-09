//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.math.BigInteger;

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
@Table(name = "unittype")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Unittype.findAll", query = "SELECT u FROM Unittype u"),
		@NamedQuery(name = "Unittype.findByUnittypeid", query = "SELECT u FROM Unittype u WHERE u.unittypeid = :unittypeid"),
		@NamedQuery(name = "Unittype.findByQuantity", query = "SELECT u FROM Unittype u WHERE u.quantity = :quantity"),
		@NamedQuery(name = "Unittype.findByUnit", query = "SELECT u FROM Unittype u WHERE u.unit = :unit"),
		@NamedQuery(name = "Unittype.findByShortname", query = "SELECT u FROM Unittype u WHERE u.shortname = :shortname") })
public class Unittype implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "quantity")
	private BigInteger quantity;
	@Column(name = "shortname")
	private String shortname;
	@JoinColumn(name = "status", referencedColumnName = "statusid")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Status status;
	@Column(name = "unit")
	private String unit;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "unittypeid")
	private Integer unittypeid;

	public Unittype() {
	}

	public Unittype(final Integer unittypeid) {
		this.unittypeid = unittypeid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Unittype)) {
			return false;
		}
		final Unittype other = (Unittype) object;
		return (this.unittypeid != null || other.unittypeid == null)
				&& (this.unittypeid == null || this.unittypeid.equals(other.unittypeid));
	}

	public BigInteger getQuantity() {
		return this.quantity;
	}

	public String getShortname() {
		return this.shortname;
	}

	public Status getStatus() {
		return this.status;
	}

	public String getUnit() {
		return this.unit;
	}

	public Integer getUnittypeid() {
		return this.unittypeid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.unittypeid != null) ? this.unittypeid.hashCode() : 0);
		return hash;
	}

	public void setQuantity(final BigInteger quantity) {
		this.quantity = quantity;
	}

	public void setShortname(final String shortname) {
		this.shortname = shortname;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	public void setUnit(final String unit) {
		this.unit = unit;
	}

	public void setUnittypeid(final Integer unittypeid) {
		this.unittypeid = unittypeid;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Unittype[ unittypeid=" + this.unittypeid + " ]";
	}
}
