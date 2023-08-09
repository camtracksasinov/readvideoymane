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
@Table(name = "manualrecovery")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Manualrecovery.findAll", query = "SELECT m FROM Manualrecovery m"),
		@NamedQuery(name = "Manualrecovery.findByManualrecid", query = "SELECT m FROM Manualrecovery m WHERE m.manualrecid = :manualrecid"),
		@NamedQuery(name = "Manualrecovery.findByClientid", query = "SELECT m FROM Manualrecovery m WHERE m.clientid = :clientid"),
		@NamedQuery(name = "Manualrecovery.findByAffiliateid", query = "SELECT m FROM Manualrecovery m WHERE m.affiliateid = :affiliateid"),
		@NamedQuery(name = "Manualrecovery.findByTransporterid", query = "SELECT m FROM Manualrecovery m WHERE m.transporterid = :transporterid"),
		@NamedQuery(name = "Manualrecovery.findByDriverid", query = "SELECT m FROM Manualrecovery m WHERE m.driverid = :driverid"),
		@NamedQuery(name = "Manualrecovery.findByDateofocurrence", query = "SELECT m FROM Manualrecovery m WHERE m.dateofocurrence = :dateofocurrence"),
		@NamedQuery(name = "Manualrecovery.findByRecoveryid", query = "SELECT m FROM Manualrecovery m WHERE m.recoveryid = :recoveryid"),
		@NamedQuery(name = "Manualrecovery.findByPoints", query = "SELECT m FROM Manualrecovery m WHERE m.points = :points"),
		@NamedQuery(name = "Manualrecovery.findByCreatedon", query = "SELECT m FROM Manualrecovery m WHERE m.createdon = :createdon"),
		@NamedQuery(name = "Manualrecovery.findByCreatedby", query = "SELECT m FROM Manualrecovery m WHERE m.createdby = :createdby"),
		@NamedQuery(name = "Manualrecovery.findByUpdatedon", query = "SELECT m FROM Manualrecovery m WHERE m.updatedon = :updatedon"),
		@NamedQuery(name = "Manualrecovery.findByUpdatedby", query = "SELECT m FROM Manualrecovery m WHERE m.updatedby = :updatedby"),
		@NamedQuery(name = "Manualrecovery.findByStatus", query = "SELECT m FROM Manualrecovery m WHERE m.status = :status") })
public class Manualrecovery implements Serializable {
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
	@Column(name = "dateofocurrence")
	@Temporal(TemporalType.DATE)
	private Date dateofocurrence;
	@Column(name = "driverid")
	private Integer driverid;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "manualrecid")
	private Integer manualrecid;
	@Column(name = "points")
	private Integer points;
	@Column(name = "recoveryid")
	private Integer recoveryid;
	@Column(name = "status")
	private Integer status;
	@Column(name = "transporterid")
	private Integer transporterid;
	@Column(name = "updatedby")
	private Integer updatedby;
	@Column(name = "updatedon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedon;

	public Manualrecovery() {
	}

	public Manualrecovery(final Integer manualrecid) {
		this.manualrecid = manualrecid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Manualrecovery)) {
			return false;
		}
		final Manualrecovery other = (Manualrecovery) object;
		return (this.manualrecid != null || other.manualrecid == null)
				&& (this.manualrecid == null || this.manualrecid.equals(other.manualrecid));
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

	public Date getDateofocurrence() {
		return this.dateofocurrence;
	}

	public Integer getDriverid() {
		return this.driverid;
	}

	public Integer getManualrecid() {
		return this.manualrecid;
	}

	public Integer getPoints() {
		return this.points;
	}

	public Integer getRecoveryid() {
		return this.recoveryid;
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
		hash += ((this.manualrecid != null) ? this.manualrecid.hashCode() : 0);
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

	public void setDateofocurrence(final Date dateofocurrence) {
		this.dateofocurrence = dateofocurrence;
	}

	public void setDriverid(final Integer driverid) {
		this.driverid = driverid;
	}

	public void setManualrecid(final Integer manualrecid) {
		this.manualrecid = manualrecid;
	}

	public void setPoints(final Integer points) {
		this.points = points;
	}

	public void setRecoveryid(final Integer recoveryid) {
		this.recoveryid = recoveryid;
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
		return "com.camtrack.entities.Manualrecovery[ manualrecid=" + this.manualrecid + " ]";
	}
}
