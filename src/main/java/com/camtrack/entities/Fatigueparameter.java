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
@Table(name = "fatigueparameter")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Fatigueparameter.findAll", query = "SELECT f FROM Fatigueparameter f"),
		@NamedQuery(name = "Fatigueparameter.findByCreatedon", query = "SELECT f FROM Fatigueparameter f WHERE f.createdon = :createdon"),
		@NamedQuery(name = "Fatigueparameter.findByCreatedby", query = "SELECT f FROM Fatigueparameter f WHERE f.createdby = :createdby"),
		@NamedQuery(name = "Fatigueparameter.findByUpdatedon", query = "SELECT f FROM Fatigueparameter f WHERE f.updatedon = :updatedon"),
		@NamedQuery(name = "Fatigueparameter.findByUpdatedby", query = "SELECT f FROM Fatigueparameter f WHERE f.updatedby = :updatedby"),
		@NamedQuery(name = "Fatigueparameter.findByStatus", query = "SELECT f FROM Fatigueparameter f WHERE f.status = :status"),
		@NamedQuery(name = "Fatigueparameter.findByFatigparamid", query = "SELECT f FROM Fatigueparameter f WHERE f.fatigparamid = :fatigparamid"),
		@NamedQuery(name = "Fatigueparameter.findByNames", query = "SELECT f FROM Fatigueparameter f WHERE f.names = :names") })
public class Fatigueparameter implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "createdby")
	private Integer createdby;
	@Column(name = "createdon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdon;
	@Id
	@Basic(optional = false)
	@Column(name = "fatigparamid")
	private Integer fatigparamid;
	@Column(name = "names")
	private String names;
	@Column(name = "status")
	private Integer status;
	@Column(name = "updatedby")
	private Integer updatedby;
	@Column(name = "updatedon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedon;

	public Fatigueparameter() {
	}

	public Fatigueparameter(final Integer fatigparamid) {
		this.fatigparamid = fatigparamid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Fatigueparameter)) {
			return false;
		}
		final Fatigueparameter other = (Fatigueparameter) object;
		return (this.fatigparamid != null || other.fatigparamid == null)
				&& (this.fatigparamid == null || this.fatigparamid.equals(other.fatigparamid));
	}

	public Integer getCreatedby() {
		return this.createdby;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public Integer getFatigparamid() {
		return this.fatigparamid;
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
		hash += ((this.fatigparamid != null) ? this.fatigparamid.hashCode() : 0);
		return hash;
	}

	public void setCreatedby(final Integer createdby) {
		this.createdby = createdby;
	}

	public void setCreatedon(final Date createdon) {
		this.createdon = createdon;
	}

	public void setFatigparamid(final Integer fatigparamid) {
		this.fatigparamid = fatigparamid;
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
		return "com.camtrack.entities.Fatigueparameter[ fatigparamid=" + this.fatigparamid + " ]";
	}
}
