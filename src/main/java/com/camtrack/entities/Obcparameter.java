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
@Table(name = "obcparameter")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Obcparameter.findAll", query = "SELECT o FROM Obcparameter o"),
		@NamedQuery(name = "Obcparameter.findByObclabel", query = "SELECT o FROM Obcparameter o WHERE o.obclabel = :obclabel"),
		@NamedQuery(name = "Obcparameter.findByCreatedon", query = "SELECT o FROM Obcparameter o WHERE o.createdon = :createdon"),
		@NamedQuery(name = "Obcparameter.findByCreatedby", query = "SELECT o FROM Obcparameter o WHERE o.createdby = :createdby"),
		@NamedQuery(name = "Obcparameter.findByUpdatedon", query = "SELECT o FROM Obcparameter o WHERE o.updatedon = :updatedon"),
		@NamedQuery(name = "Obcparameter.findByUpdatedby", query = "SELECT o FROM Obcparameter o WHERE o.updatedby = :updatedby"),
		@NamedQuery(name = "Obcparameter.findByStatus", query = "SELECT o FROM Obcparameter o WHERE o.status = :status"),
		@NamedQuery(name = "Obcparameter.findByObcparamid", query = "SELECT o FROM Obcparameter o WHERE o.obcparamid = :obcparamid"),
		@NamedQuery(name = "Obcparameter.findByNames", query = "SELECT o FROM Obcparameter o WHERE o.names = :names") })
public class Obcparameter implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "createdby")
	private Integer createdby;
	@Column(name = "createdon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdon;
	@Column(name = "names")
	private String names;
	@Column(name = "obclabel")
	private String obclabel;
	@Id
	@Basic(optional = false)
	@Column(name = "obcparamid")
	private Integer obcparamid;
	@Column(name = "status")
	private Integer status;
	@Column(name = "updatedby")
	private Integer updatedby;
	@Column(name = "updatedon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedon;

	public Obcparameter() {
	}

	public Obcparameter(final Integer obcparamid) {
		this.obcparamid = obcparamid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Obcparameter)) {
			return false;
		}
		final Obcparameter other = (Obcparameter) object;
		return (this.obcparamid != null || other.obcparamid == null)
				&& (this.obcparamid == null || this.obcparamid.equals(other.obcparamid));
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

	public String getObclabel() {
		return this.obclabel;
	}

	public Integer getObcparamid() {
		return this.obcparamid;
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
		hash += ((this.obcparamid != null) ? this.obcparamid.hashCode() : 0);
		return hash;
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

	public void setObclabel(final String obclabel) {
		this.obclabel = obclabel;
	}

	public void setObcparamid(final Integer obcparamid) {
		this.obcparamid = obcparamid;
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
		return "com.camtrack.entities.Obcparameter[ obcparamid=" + this.obcparamid + " ]";
	}
}
