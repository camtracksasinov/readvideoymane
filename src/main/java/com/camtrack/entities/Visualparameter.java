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
@Table(name = "visualparameter")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Visualparameter.findAll", query = "SELECT v FROM Visualparameter v"),
		@NamedQuery(name = "Visualparameter.findByVisualparamid", query = "SELECT v FROM Visualparameter v WHERE v.visualparamid = :visualparamid"),
		@NamedQuery(name = "Visualparameter.findByVisuallabel", query = "SELECT v FROM Visualparameter v WHERE v.visuallabel = :visuallabel"),
		@NamedQuery(name = "Visualparameter.findByClientid", query = "SELECT v FROM Visualparameter v WHERE v.clientid = :clientid"),
		@NamedQuery(name = "Visualparameter.findByAffiliateid", query = "SELECT v FROM Visualparameter v WHERE v.affiliateid = :affiliateid"),
		@NamedQuery(name = "Visualparameter.findByCreatedon", query = "SELECT v FROM Visualparameter v WHERE v.createdon = :createdon"),
		@NamedQuery(name = "Visualparameter.findByCreatedby", query = "SELECT v FROM Visualparameter v WHERE v.createdby = :createdby"),
		@NamedQuery(name = "Visualparameter.findByUpdatedon", query = "SELECT v FROM Visualparameter v WHERE v.updatedon = :updatedon"),
		@NamedQuery(name = "Visualparameter.findByUpdatedby", query = "SELECT v FROM Visualparameter v WHERE v.updatedby = :updatedby"),
		@NamedQuery(name = "Visualparameter.findByStatus", query = "SELECT v FROM Visualparameter v WHERE v.status = :status"),
		@NamedQuery(name = "Visualparameter.findByVisualsublabel", query = "SELECT v FROM Visualparameter v WHERE v.visualsublabel = :visualsublabel"),
		@NamedQuery(name = "Visualparameter.findByTransporterid", query = "SELECT v FROM Visualparameter v WHERE v.transporterid = :transporterid") })
public class Visualparameter implements Serializable {
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
	private Fixvisualparams idfixe;
	@Column(name = "status")
	private Integer status;
	@Column(name = "transporterid")
	private Integer transporterid;
	@Column(name = "updatedby")
	private Integer updatedby;
	@Column(name = "updatedon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedon;
	@Column(name = "visuallabel")
	private String visuallabel;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "visualparamid")
	private Integer visualparamid;
	@Column(name = "visualsublabel")
	private String visualsublabel;

	public Visualparameter() {
	}

	public Visualparameter(final Integer visualparamid) {
		this.visualparamid = visualparamid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Visualparameter)) {
			return false;
		}
		final Visualparameter other = (Visualparameter) object;
		return (this.visualparamid != null || other.visualparamid == null)
				&& (this.visualparamid == null || this.visualparamid.equals(other.visualparamid));
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

	public Fixvisualparams getIdfixe() {
		return this.idfixe;
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

	public String getVisuallabel() {
		return this.visuallabel;
	}

	public Integer getVisualparamid() {
		return this.visualparamid;
	}

	public String getVisualsublabel() {
		return this.visualsublabel;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.visualparamid != null) ? this.visualparamid.hashCode() : 0);
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

	public void setIdfixe(final Fixvisualparams idfixe) {
		this.idfixe = idfixe;
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

	public void setVisuallabel(final String visuallabel) {
		this.visuallabel = visuallabel;
	}

	public void setVisualparamid(final Integer visualparamid) {
		this.visualparamid = visualparamid;
	}

	public void setVisualsublabel(final String visualsublabel) {
		this.visualsublabel = visualsublabel;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Visualparameter[ visualparamid=" + this.visualparamid + " ]";
	}
}
