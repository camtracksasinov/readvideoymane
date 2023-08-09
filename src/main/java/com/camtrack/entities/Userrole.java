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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "userrole")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Userrole.findAll", query = "SELECT u FROM Userrole u"),
		@NamedQuery(name = "Userrole.findByUserroleid", query = "SELECT u FROM Userrole u WHERE u.userroleid = :userroleid"),
		@NamedQuery(name = "Userrole.findByName", query = "SELECT u FROM Userrole u WHERE u.name = :name"),
		@NamedQuery(name = "Userrole.findByStatus", query = "SELECT u FROM Userrole u WHERE u.status = :status"),
		@NamedQuery(name = "Userrole.findByHierarchy", query = "SELECT u FROM Userrole u WHERE u.hierarchy = :hierarchy") })
public class Userrole implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	@Column(name = "hierarchy")
	private Integer hierarchy;
	@Basic(optional = false)
	@Column(name = "name")
	private String name;
	@JsonIgnore
	@Basic(optional = false)
	@Column(name = "status")
	private int status;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@JsonProperty("typeroleid")
	@Column(name = "userroleid")
	private Integer userroleid;

	public Userrole() {
	}

	public Userrole(final Integer userroleid) {
		this.userroleid = userroleid;
	}

	public Userrole(final Integer userroleid, final String name, final int status) {
		this.userroleid = userroleid;
		this.name = name;
		this.status = status;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Userrole)) {
			return false;
		}
		final Userrole other = (Userrole) object;
		return (this.userroleid != null || other.userroleid == null)
				&& (this.userroleid == null || this.userroleid.equals(other.userroleid));
	}

	public Integer getHierarchy() {
		return this.hierarchy;
	}

	public String getName() {
		return this.name;
	}

	public int getStatus() {
		return this.status;
	}

	public Integer getUserroleid() {
		return this.userroleid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.userroleid != null) ? this.userroleid.hashCode() : 0);
		return hash;
	}

	public void setHierarchy(final Integer hierarchy) {
		this.hierarchy = hierarchy;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setStatus(final int status) {
		this.status = status;
	}

	public void setUserroleid(final Integer userroleid) {
		this.userroleid = userroleid;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Userrole[ userroleid=" + this.userroleid + " ]";
	}
}
