//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "adasparameter")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Adasparameter.findAll", query = "SELECT a FROM Adasparameter a"),
		@NamedQuery(name = "Adasparameter.findByCreatedon", query = "SELECT a FROM Adasparameter a WHERE a.createdon = :createdon"),
		@NamedQuery(name = "Adasparameter.findByCreatedby", query = "SELECT a FROM Adasparameter a WHERE a.createdby = :createdby"),
		@NamedQuery(name = "Adasparameter.findByUpdatedon", query = "SELECT a FROM Adasparameter a WHERE a.updatedon = :updatedon"),
		@NamedQuery(name = "Adasparameter.findByUpdatedby", query = "SELECT a FROM Adasparameter a WHERE a.updatedby = :updatedby"),
		@NamedQuery(name = "Adasparameter.findByStatus", query = "SELECT a FROM Adasparameter a WHERE a.status = :status"),
		@NamedQuery(name = "Adasparameter.findByAdasparamid", query = "SELECT a FROM Adasparameter a WHERE a.adasparamid = :adasparamid"),
		@NamedQuery(name = "Adasparameter.findByNames", query = "SELECT a FROM Adasparameter a WHERE a.names = :names") })
public class Adasparameter implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "adasparamid")
	private Integer adasparamid;
	@Column(name = "createdby")
	private Integer createdby;
	@Column(name = "createdon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdon;
	@Column(name = "names")
	private String names;
	@Column(name = "status")
	private Integer status;
	@Column(name = "updatedby")
	private Integer updatedby;
	@Column(name = "updatedon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedon;

	public Adasparameter() {
	}

	public Adasparameter(final Integer adasparamid) {
		this.adasparamid = adasparamid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Adasparameter)) {
			return false;
		}
		final Adasparameter other = (Adasparameter) object;
		return (this.adasparamid != null || other.adasparamid == null)
				&& (this.adasparamid == null || this.adasparamid.equals(other.adasparamid));
	}

	public Integer getAdasparamid() {
		return this.adasparamid;
	}

	public Integer getCreatedby() {
		return this.createdby;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public String getNames() {
		return this.names;
	}

	public Integer getStatus() {
		return this.status;
	}

	public Integer getUpdatedby() {
		return this.updatedby;
	}

	public Date getUpdatedon() {
		return this.updatedon;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.adasparamid != null) ? this.adasparamid.hashCode() : 0);
		return hash;
	}

	public void setAdasparamid(final Integer adasparamid) {
		this.adasparamid = adasparamid;
	}

	public void setCreatedby(final Integer createdby) {
		this.createdby = createdby;
	}

	public void setCreatedon(final Date createdon) {
		this.createdon = createdon;
	}

	public void setNames(final String names) {
		this.names = names;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public void setUpdatedby(final Integer updatedby) {
		this.updatedby = updatedby;
	}

	public void setUpdatedon(final Date updatedon) {
		this.updatedon = updatedon;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Adasparameter[ adasparamid=" + this.adasparamid + " ]";
	}
}
