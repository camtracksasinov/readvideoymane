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
@Table(name = "manualaccicp")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Manualaccicp.findAll", query = "SELECT m FROM Manualaccicp m"),
		@NamedQuery(name = "Manualaccicp.findByManualsubrecid", query = "SELECT m FROM Manualaccicp m WHERE m.manualsubrecid = :manualsubrecid"),
		@NamedQuery(name = "Manualaccicp.findByClientid", query = "SELECT m FROM Manualaccicp m WHERE m.clientid = :clientid"),
		@NamedQuery(name = "Manualaccicp.findByAffiliateid", query = "SELECT m FROM Manualaccicp m WHERE m.affiliateid = :affiliateid"),
		@NamedQuery(name = "Manualaccicp.findByTransporterid", query = "SELECT m FROM Manualaccicp m WHERE m.transporterid = :transporterid"),
		@NamedQuery(name = "Manualaccicp.findByDriverid", query = "SELECT m FROM Manualaccicp m WHERE m.driverid = :driverid"),
		@NamedQuery(name = "Manualaccicp.findByDateofocurrence", query = "SELECT m FROM Manualaccicp m WHERE m.dateofocurrence = :dateofocurrence"),
		@NamedQuery(name = "Manualaccicp.findByNoofaccident", query = "SELECT m FROM Manualaccicp m WHERE m.noofaccident = :noofaccident"),
		@NamedQuery(name = "Manualaccicp.findByTransportericp", query = "SELECT m FROM Manualaccicp m WHERE m.transportericp = :transportericp"),
		@NamedQuery(name = "Manualaccicp.findByCreatedon", query = "SELECT m FROM Manualaccicp m WHERE m.createdon = :createdon"),
		@NamedQuery(name = "Manualaccicp.findByCreatedby", query = "SELECT m FROM Manualaccicp m WHERE m.createdby = :createdby"),
		@NamedQuery(name = "Manualaccicp.findByUpdatedon", query = "SELECT m FROM Manualaccicp m WHERE m.updatedon = :updatedon"),
		@NamedQuery(name = "Manualaccicp.findByUpdatedby", query = "SELECT m FROM Manualaccicp m WHERE m.updatedby = :updatedby"),
		@NamedQuery(name = "Manualaccicp.findByStatus", query = "SELECT m FROM Manualaccicp m WHERE m.status = :status") })
public class Manualaccicp implements Serializable {
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
	@Column(name = "manualsubrecid")
	private Integer manualsubrecid;
	@Column(name = "noofaccident")
	private Integer noofaccident;
	@Column(name = "status")
	private Integer status;
	@Column(name = "transportericp")
	private Integer transportericp;
	@Column(name = "transporterid")
	private Integer transporterid;
	@Column(name = "updatedby")
	private Integer updatedby;
	@Column(name = "updatedon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedon;

	public Manualaccicp() {
	}

	public Manualaccicp(final Integer manualsubrecid) {
		this.manualsubrecid = manualsubrecid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Manualaccicp)) {
			return false;
		}
		final Manualaccicp other = (Manualaccicp) object;
		return (this.manualsubrecid != null || other.manualsubrecid == null)
				&& (this.manualsubrecid == null || this.manualsubrecid.equals(other.manualsubrecid));
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

	public Integer getManualsubrecid() {
		return this.manualsubrecid;
	}

	public Integer getNoofaccident() {
		return this.noofaccident;
	}

	public Integer getStatus() {
		return this.status;
	}

	public Integer getTransportericp() {
		return this.transportericp;
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
		hash += ((this.manualsubrecid != null) ? this.manualsubrecid.hashCode() : 0);
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

	public void setManualsubrecid(final Integer manualsubrecid) {
		this.manualsubrecid = manualsubrecid;
	}

	public void setNoofaccident(final Integer noofaccident) {
		this.noofaccident = noofaccident;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public void setTransportericp(final Integer transportericp) {
		this.transportericp = transportericp;
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
		return "com.ymane.entities.Manualaccicp[ manualsubrecid=" + this.manualsubrecid + " ]";
	}
}
