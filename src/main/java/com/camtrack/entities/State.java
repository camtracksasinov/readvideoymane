//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "state")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "State.findAll", query = "SELECT s FROM State s"),
		@NamedQuery(name = "State.findByStateid", query = "SELECT s FROM State s WHERE s.stateid = :stateid"),
		@NamedQuery(name = "State.findByName", query = "SELECT s FROM State s WHERE s.name = :name"),
		@NamedQuery(name = "State.findByShortname", query = "SELECT s FROM State s WHERE s.shortname = :shortname"),
		@NamedQuery(name = "State.findByCountryid", query = "SELECT s FROM State s WHERE s.countryid = :countryid"),
		@NamedQuery(name = "State.findByStatus", query = "SELECT s FROM State s WHERE s.status = :status") })
public class State implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "countryid")
	private Integer countryid;
	@Column(name = "name")
	private String name;
	@Column(name = "shortname")
	private String shortname;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "stateid")
	private Integer stateid;
	@Column(name = "status")
	private Integer status;

	public State() {
	}

	public State(final Integer stateid) {
		this.stateid = stateid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof State)) {
			return false;
		}
		final State other = (State) object;
		return (this.stateid != null || other.stateid == null)
				&& (this.stateid == null || this.stateid.equals(other.stateid));
	}

	public Integer getCountryid() {
		return this.countryid;
	}

	public String getName() {
		return this.name;
	}

	public String getShortname() {
		return this.shortname;
	}

	public Integer getStateid() {
		return this.stateid;
	}

	public Integer getStatus() {
		return this.status;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.stateid != null) ? this.stateid.hashCode() : 0);
		return hash;
	}

	public void setCountryid(final Integer countryid) {
		this.countryid = countryid;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setShortname(final String shortname) {
		this.shortname = shortname;
	}

	public void setStateid(final Integer stateid) {
		this.stateid = stateid;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.State[ stateid=" + this.stateid + " ]";
	}
}
