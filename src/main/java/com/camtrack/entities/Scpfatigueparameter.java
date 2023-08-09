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
@Table(name = "scpfatigueparameter")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Scpfatigueparameter.findAll", query = "SELECT s FROM Scpfatigueparameter s"),
		@NamedQuery(name = "Scpfatigueparameter.findByFatigscpparamid", query = "SELECT s FROM Scpfatigueparameter s WHERE s.fatigscpparamid = :fatigscpparamid"),
		@NamedQuery(name = "Scpfatigueparameter.findByClientid", query = "SELECT s FROM Scpfatigueparameter s WHERE s.clientid = :clientid"),
		@NamedQuery(name = "Scpfatigueparameter.findByAffiliateid", query = "SELECT s FROM Scpfatigueparameter s WHERE s.affiliateid = :affiliateid"),
		@NamedQuery(name = "Scpfatigueparameter.findByRecordvalue", query = "SELECT s FROM Scpfatigueparameter s WHERE s.recordvalue = :recordvalue"),
		@NamedQuery(name = "Scpfatigueparameter.findByAlarmvalue", query = "SELECT s FROM Scpfatigueparameter s WHERE s.alarmvalue = :alarmvalue"),
		@NamedQuery(name = "Scpfatigueparameter.findByAlertvalue", query = "SELECT s FROM Scpfatigueparameter s WHERE s.alertvalue = :alertvalue"),
		@NamedQuery(name = "Scpfatigueparameter.findByCreatedon", query = "SELECT s FROM Scpfatigueparameter s WHERE s.createdon = :createdon"),
		@NamedQuery(name = "Scpfatigueparameter.findByCreatedby", query = "SELECT s FROM Scpfatigueparameter s WHERE s.createdby = :createdby"),
		@NamedQuery(name = "Scpfatigueparameter.findByUpdatedon", query = "SELECT s FROM Scpfatigueparameter s WHERE s.updatedon = :updatedon"),
		@NamedQuery(name = "Scpfatigueparameter.findByUpdatedby", query = "SELECT s FROM Scpfatigueparameter s WHERE s.updatedby = :updatedby"),
		@NamedQuery(name = "Scpfatigueparameter.findByStatus", query = "SELECT s FROM Scpfatigueparameter s WHERE s.status = :status"),
		@NamedQuery(name = "Scpfatigueparameter.findByTransporterid", query = "SELECT s FROM Scpfatigueparameter s WHERE s.transporterid = :transporterid") })
public class Scpfatigueparameter implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "affiliateid")
	private Integer affiliateid;
	@Column(name = "alarmvalue")
	private Integer alarmvalue;
	@Column(name = "alertvalue")
	private Integer alertvalue;
	@Column(name = "clientid")
	private Integer clientid;
	@Column(name = "createdby")
	private Integer createdby;
	@Column(name = "createdon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdon;
	@Column(name = "fatigparamid")
	private Integer fatigparamid;
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fatigscpparamid")
	private Integer fatigscpparamid;
	@Column(name = "recordvalue")
	private Integer recordvalue;
	@Column(name = "status")
	private Integer status;
	@Column(name = "transporterid")
	private Integer transporterid;
	@Column(name = "updatedby")
	private Integer updatedby;
	@Column(name = "updatedon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedon;

	public Scpfatigueparameter() {
	}

	public Scpfatigueparameter(final Integer fatigscpparamid) {
		this.fatigscpparamid = fatigscpparamid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Scpfatigueparameter)) {
			return false;
		}
		final Scpfatigueparameter other = (Scpfatigueparameter) object;
		return (this.fatigscpparamid != null || other.fatigscpparamid == null)
				&& (this.fatigscpparamid == null || this.fatigscpparamid.equals(other.fatigscpparamid));
	}

	public Integer getAffiliateid() {
		return this.affiliateid;
	}

	public Integer getAlarmvalue() {
		return this.alarmvalue;
	}

	public Integer getAlertvalue() {
		return this.alertvalue;
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

	public Integer getFatigparamid() {
		return this.fatigparamid;
	}

	public Integer getFatigscpparamid() {
		return this.fatigscpparamid;
	}

	public Integer getRecordvalue() {
		return this.recordvalue;
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
		hash += ((this.fatigscpparamid != null) ? this.fatigscpparamid.hashCode() : 0);
		return hash;
	}

	public void setAffiliateid(final Integer affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setAlarmvalue(final Integer alarmvalue) {
		this.alarmvalue = alarmvalue;
	}

	public void setAlertvalue(final Integer alertvalue) {
		this.alertvalue = alertvalue;
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

	public void setFatigparamid(final Integer fatigparamid) {
		this.fatigparamid = fatigparamid;
	}

	public void setFatigscpparamid(final Integer fatigscpparamid) {
		this.fatigscpparamid = fatigscpparamid;
	}

	public void setRecordvalue(final Integer recordvalue) {
		this.recordvalue = recordvalue;
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
		return "com.camtrack.entities.Scpfatigueparameter[ fatigscpparamid=" + this.fatigscpparamid + " ]";
	}
}
