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
@Table(name = "scpvisualparameter")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Scpvisualparameter.findAll", query = "SELECT s FROM Scpvisualparameter s"),
		@NamedQuery(name = "Scpvisualparameter.findByVisualscpparamid", query = "SELECT s FROM Scpvisualparameter s WHERE s.visualscpparamid = :visualscpparamid"),
		@NamedQuery(name = "Scpvisualparameter.findByVisualparamid", query = "SELECT s FROM Scpvisualparameter s WHERE s.visualparamid = :visualparamid"),
		@NamedQuery(name = "Scpvisualparameter.findByClientid", query = "SELECT s FROM Scpvisualparameter s WHERE s.clientid = :clientid"),
		@NamedQuery(name = "Scpvisualparameter.findByAffiliateid", query = "SELECT s FROM Scpvisualparameter s WHERE s.affiliateid = :affiliateid"),
		@NamedQuery(name = "Scpvisualparameter.findByPoints", query = "SELECT s FROM Scpvisualparameter s WHERE s.points = :points"),
		@NamedQuery(name = "Scpvisualparameter.findByCreatedon", query = "SELECT s FROM Scpvisualparameter s WHERE s.createdon = :createdon"),
		@NamedQuery(name = "Scpvisualparameter.findByCreatedby", query = "SELECT s FROM Scpvisualparameter s WHERE s.createdby = :createdby"),
		@NamedQuery(name = "Scpvisualparameter.findByUpdatedon", query = "SELECT s FROM Scpvisualparameter s WHERE s.updatedon = :updatedon"),
		@NamedQuery(name = "Scpvisualparameter.findByUpdatedby", query = "SELECT s FROM Scpvisualparameter s WHERE s.updatedby = :updatedby"),
		@NamedQuery(name = "Scpvisualparameter.findByStatus", query = "SELECT s FROM Scpvisualparameter s WHERE s.status = :status"),
		@NamedQuery(name = "Scpvisualparameter.findByTransporterid", query = "SELECT s FROM Scpvisualparameter s WHERE s.transporterid = :transporterid") })
public class Scpvisualparameter implements Serializable {
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
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "visualscpparamid")
	private Integer visualscpparamid;

	public Scpvisualparameter() {
	}

	public Scpvisualparameter(final Integer visualscpparamid) {
		this.visualscpparamid = visualscpparamid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Scpvisualparameter)) {
			return false;
		}
		final Scpvisualparameter other = (Scpvisualparameter) object;
		return (this.visualscpparamid != null || other.visualscpparamid == null)
				&& (this.visualscpparamid == null || this.visualscpparamid.equals(other.visualscpparamid));
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

	public Integer getVisualscpparamid() {
		return this.visualscpparamid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.visualscpparamid != null) ? this.visualscpparamid.hashCode() : 0);
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

	public void setVisualscpparamid(final Integer visualscpparamid) {
		this.visualscpparamid = visualscpparamid;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Scpvisualparameter[ visualscpparamid=" + this.visualscpparamid + " ]";
	}
}
