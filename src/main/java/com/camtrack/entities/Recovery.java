//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "recovery")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Recovery.findAll", query = "SELECT r FROM Recovery r"),
		@NamedQuery(name = "Recovery.findByRecoveryid", query = "SELECT r FROM Recovery r WHERE r.recoveryid = :recoveryid"),
		@NamedQuery(name = "Recovery.findByRecoverylabel", query = "SELECT r FROM Recovery r WHERE r.recoverylabel = :recoverylabel"),
		@NamedQuery(name = "Recovery.findByClientid", query = "SELECT r FROM Recovery r WHERE r.clientid = :clientid"),
		@NamedQuery(name = "Recovery.findByAffiliateid", query = "SELECT r FROM Recovery r WHERE r.affiliateid = :affiliateid"),
		@NamedQuery(name = "Recovery.findByCreatedon", query = "SELECT r FROM Recovery r WHERE r.createdon = :createdon"),
		@NamedQuery(name = "Recovery.findByCreatedby", query = "SELECT r FROM Recovery r WHERE r.createdby = :createdby"),
		@NamedQuery(name = "Recovery.findByUpdatedon", query = "SELECT r FROM Recovery r WHERE r.updatedon = :updatedon"),
		@NamedQuery(name = "Recovery.findByUpdatedby", query = "SELECT r FROM Recovery r WHERE r.updatedby = :updatedby"),
		@NamedQuery(name = "Recovery.findByStatus", query = "SELECT r FROM Recovery r WHERE r.status = :status"),
		@NamedQuery(name = "Recovery.findByRecoverysublabel", query = "SELECT r FROM Recovery r WHERE r.recoverysublabel = :recoverysublabel"),
		@NamedQuery(name = "Recovery.findByNamelabel", query = "SELECT r FROM Recovery r WHERE r.namelabel = :namelabel"),
		@NamedQuery(name = "Recovery.findByTransporterid", query = "SELECT r FROM Recovery r WHERE r.transporterid = :transporterid") })
public class Recovery implements Serializable {
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
	@JoinColumn(name = "idfixe", referencedColumnName = "ids")
	@ManyToOne(fetch = FetchType.LAZY)
	private Fixrecoveryparams idfixe;
	@Column(name = "namelabel")
	private String namelabel;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "recoveryid")
	private Integer recoveryid;
	@Column(name = "recoverylabel")
	private String recoverylabel;
	@Column(name = "recoverysublabel")
	private String recoverysublabel;
	@Column(name = "status")
	private Integer status;
	@Column(name = "transporterid")
	private Integer transporterid;
	@Column(name = "updatedby")
	private Integer updatedby;
	@Column(name = "updatedon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedon;

	public Recovery() {
	}

	public Recovery(final Integer recoveryid) {
		this.recoveryid = recoveryid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Recovery)) {
			return false;
		}
		final Recovery other = (Recovery) object;
		return (this.recoveryid != null || other.recoveryid == null)
				&& (this.recoveryid == null || this.recoveryid.equals(other.recoveryid));
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

	public Fixrecoveryparams getIdfixe() {
		return this.idfixe;
	}

	public String getNamelabel() {
		return this.namelabel;
	}

	public Integer getRecoveryid() {
		return this.recoveryid;
	}

	public String getRecoverylabel() {
		return this.recoverylabel;
	}

	public String getRecoverysublabel() {
		return this.recoverysublabel;
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
		hash += ((this.recoveryid != null) ? this.recoveryid.hashCode() : 0);
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

	public void setIdfixe(final Fixrecoveryparams idfixe) {
		this.idfixe = idfixe;
	}

	public void setNamelabel(final String namelabel) {
		this.namelabel = namelabel;
	}

	public void setRecoveryid(final Integer recoveryid) {
		this.recoveryid = recoveryid;
	}

	public void setRecoverylabel(final String recoverylabel) {
		this.recoverylabel = recoverylabel;
	}

	public void setRecoverysublabel(final String recoverysublabel) {
		this.recoverysublabel = recoverysublabel;
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
		return "com.camtrack.entities.Recovery[ recoveryid=" + this.recoveryid + " ]";
	}
}
