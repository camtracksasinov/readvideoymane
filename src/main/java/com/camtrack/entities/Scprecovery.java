//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "scprecovery")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Scprecovery.findAll", query = "SELECT s FROM Scprecovery s"),
		@NamedQuery(name = "Scprecovery.findByScprecoveryid", query = "SELECT s FROM Scprecovery s WHERE s.scprecoveryid = :scprecoveryid"),
		@NamedQuery(name = "Scprecovery.findByRecoveryid", query = "SELECT s FROM Scprecovery s WHERE s.recoveryid = :recoveryid"),
		@NamedQuery(name = "Scprecovery.findByClientid", query = "SELECT s FROM Scprecovery s WHERE s.clientid = :clientid"),
		@NamedQuery(name = "Scprecovery.findByAffiliateid", query = "SELECT s FROM Scprecovery s WHERE s.affiliateid = :affiliateid"),
		@NamedQuery(name = "Scprecovery.findByPoints", query = "SELECT s FROM Scprecovery s WHERE s.points = :points"),
		@NamedQuery(name = "Scprecovery.findByCreatedon", query = "SELECT s FROM Scprecovery s WHERE s.createdon = :createdon"),
		@NamedQuery(name = "Scprecovery.findByCreatedby", query = "SELECT s FROM Scprecovery s WHERE s.createdby = :createdby"),
		@NamedQuery(name = "Scprecovery.findByUpdatedon", query = "SELECT s FROM Scprecovery s WHERE s.updatedon = :updatedon"),
		@NamedQuery(name = "Scprecovery.findByUpdatedby", query = "SELECT s FROM Scprecovery s WHERE s.updatedby = :updatedby"),
		@NamedQuery(name = "Scprecovery.findByStatus", query = "SELECT s FROM Scprecovery s WHERE s.status = :status"),
		@NamedQuery(name = "Scprecovery.findByTransporterid", query = "SELECT s FROM Scprecovery s WHERE s.transporterid = :transporterid") })
public class Scprecovery implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "affiliateid")
	private Integer affiliateid;
	@Column(name = "clientid")
	private Integer clientid;
	@Column(name = "createdby")
	private Integer createdby;
	@Column(name = "createdon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdon;
	@Column(name = "nbmonths")
	private Short nbmonths;
	@Column(name = "points")
	private Integer points;
	@Column(name = "recoveryid")
	private Integer recoveryid;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "scprecoveryid")
	private Integer scprecoveryid;
	@Column(name = "status")
	private Integer status;
	@Column(name = "transporterid")
	private Integer transporterid;
	@Column(name = "updatedby")
	private Integer updatedby;
	@Column(name = "updatedon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedon;

	public Scprecovery() {
	}

	public Scprecovery(final Integer scprecoveryid) {
		this.scprecoveryid = scprecoveryid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Scprecovery)) {
			return false;
		}
		final Scprecovery other = (Scprecovery) object;
		return (this.scprecoveryid != null || other.scprecoveryid == null)
				&& (this.scprecoveryid == null || this.scprecoveryid.equals(other.scprecoveryid));
	}

	public Integer getAffiliateid() {
		return this.affiliateid;
	}

	public Integer getClientid() {
		return this.clientid;
	}

	public Integer getCreatedby() {
		return this.createdby;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public Short getNbmonths() {
		return this.nbmonths;
	}

	public Integer getPoints() {
		return this.points;
	}

	public Integer getRecoveryid() {
		return this.recoveryid;
	}

	public Integer getScprecoveryid() {
		return this.scprecoveryid;
	}

	public Integer getStatus() {
		return this.status;
	}

	public Integer getTransporterid() {
		return this.transporterid;
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
		hash += ((this.scprecoveryid != null) ? this.scprecoveryid.hashCode() : 0);
		return hash;
	}

	public void setAffiliateid(final Integer affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setClientid(final Integer clientid) {
		this.clientid = clientid;
	}

	public void setCreatedby(final Integer createdby) {
		this.createdby = createdby;
	}

	public void setCreatedon(final Date createdon) {
		this.createdon = createdon;
	}

	public void setNbmonths(final Short nbmonths) {
		this.nbmonths = nbmonths;
	}

	public void setPoints(final Integer points) {
		this.points = points;
	}

	public void setRecoveryid(final Integer recoveryid) {
		this.recoveryid = recoveryid;
	}

	public void setScprecoveryid(final Integer scprecoveryid) {
		this.scprecoveryid = scprecoveryid;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public void setTransporterid(final Integer transporterid) {
		this.transporterid = transporterid;
	}

	public void setUpdatedby(final Integer updatedby) {
		this.updatedby = updatedby;
	}

	public void setUpdatedon(final Date updatedon) {
		this.updatedon = updatedon;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Scprecovery[ scprecoveryid=" + this.scprecoveryid + " ]";
	}
}
