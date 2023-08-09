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
@Table(name = "manualsubstraction")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Manualsubstraction.findAll", query = "SELECT m FROM Manualsubstraction m"),
		@NamedQuery(name = "Manualsubstraction.findByManualsubid", query = "SELECT m FROM Manualsubstraction m WHERE m.manualsubid = :manualsubid"),
		@NamedQuery(name = "Manualsubstraction.findByClientid", query = "SELECT m FROM Manualsubstraction m WHERE m.clientid = :clientid"),
		@NamedQuery(name = "Manualsubstraction.findByAffiliateid", query = "SELECT m FROM Manualsubstraction m WHERE m.affiliateid = :affiliateid"),
		@NamedQuery(name = "Manualsubstraction.findByTransporterid", query = "SELECT m FROM Manualsubstraction m WHERE m.transporterid = :transporterid"),
		@NamedQuery(name = "Manualsubstraction.findByDriverid", query = "SELECT m FROM Manualsubstraction m WHERE m.driverid = :driverid"),
		@NamedQuery(name = "Manualsubstraction.findByDateofocurrence", query = "SELECT m FROM Manualsubstraction m WHERE m.dateofocurrence = :dateofocurrence"),
		@NamedQuery(name = "Manualsubstraction.findByVisualparamid", query = "SELECT m FROM Manualsubstraction m WHERE m.visualparamid = :visualparamid"),
		@NamedQuery(name = "Manualsubstraction.findByPoints", query = "SELECT m FROM Manualsubstraction m WHERE m.points = :points"),
		@NamedQuery(name = "Manualsubstraction.findByCreatedon", query = "SELECT m FROM Manualsubstraction m WHERE m.createdon = :createdon"),
		@NamedQuery(name = "Manualsubstraction.findByCreatedby", query = "SELECT m FROM Manualsubstraction m WHERE m.createdby = :createdby"),
		@NamedQuery(name = "Manualsubstraction.findByUpdatedon", query = "SELECT m FROM Manualsubstraction m WHERE m.updatedon = :updatedon"),
		@NamedQuery(name = "Manualsubstraction.findByUpdatedby", query = "SELECT m FROM Manualsubstraction m WHERE m.updatedby = :updatedby"),
		@NamedQuery(name = "Manualsubstraction.findByStatus", query = "SELECT m FROM Manualsubstraction m WHERE m.status = :status") })
public class Manualsubstraction implements Serializable {
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
	@Column(name = "manualsubid")
	private Integer manualsubid;
	@Column(name = "points")
	private Integer points;
	@Column(name = "status")
	private Integer status;
	@Column(name = "transporterid")
	private Integer transporterid;
	@Column(name = "updatedby")
	private Integer updatedby;
	@Column(name = "updatedon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedon;
	@Column(name = "visualparamid")
	private Integer visualparamid;

	public Manualsubstraction() {
	}

	public Manualsubstraction(final Integer manualsubid) {
		this.manualsubid = manualsubid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Manualsubstraction)) {
			return false;
		}
		final Manualsubstraction other = (Manualsubstraction) object;
		return (this.manualsubid != null || other.manualsubid == null)
				&& (this.manualsubid == null || this.manualsubid.equals(other.manualsubid));
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

	public Integer getManualsubid() {
		return this.manualsubid;
	}

	public Integer getPoints() {
		return this.points;
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

	public Integer getVisualparamid() {
		return this.visualparamid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.manualsubid != null) ? this.manualsubid.hashCode() : 0);
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

	public void setManualsubid(final Integer manualsubid) {
		this.manualsubid = manualsubid;
	}

	public void setPoints(final Integer points) {
		this.points = points;
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

	public void setVisualparamid(final Integer visualparamid) {
		this.visualparamid = visualparamid;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Manualsubstraction[ manualsubid=" + this.manualsubid + " ]";
	}
}
