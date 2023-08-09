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
@Table(name = "scpobcparameter")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Scpobcparameter.findAll", query = "SELECT s FROM Scpobcparameter s"),
		@NamedQuery(name = "Scpobcparameter.findByObcparamid", query = "SELECT s FROM Scpobcparameter s WHERE s.obcparamid = :obcparamid"),
		@NamedQuery(name = "Scpobcparameter.findByObcscpparamid", query = "SELECT s FROM Scpobcparameter s WHERE s.obcscpparamid = :obcscpparamid"),
		@NamedQuery(name = "Scpobcparameter.findByClientid", query = "SELECT s FROM Scpobcparameter s WHERE s.clientid = :clientid"),
		@NamedQuery(name = "Scpobcparameter.findByAffiliateid", query = "SELECT s FROM Scpobcparameter s WHERE s.affiliateid = :affiliateid"),
		@NamedQuery(name = "Scpobcparameter.findByRecordvalue", query = "SELECT s FROM Scpobcparameter s WHERE s.recordvalue = :recordvalue"),
		@NamedQuery(name = "Scpobcparameter.findByAlarmvalue", query = "SELECT s FROM Scpobcparameter s WHERE s.alarmvalue = :alarmvalue"),
		@NamedQuery(name = "Scpobcparameter.findByAlertvalue", query = "SELECT s FROM Scpobcparameter s WHERE s.alertvalue = :alertvalue"),
		@NamedQuery(name = "Scpobcparameter.findByCreatedon", query = "SELECT s FROM Scpobcparameter s WHERE s.createdon = :createdon"),
		@NamedQuery(name = "Scpobcparameter.findByCreatedby", query = "SELECT s FROM Scpobcparameter s WHERE s.createdby = :createdby"),
		@NamedQuery(name = "Scpobcparameter.findByUpdatedon", query = "SELECT s FROM Scpobcparameter s WHERE s.updatedon = :updatedon"),
		@NamedQuery(name = "Scpobcparameter.findByUpdatedby", query = "SELECT s FROM Scpobcparameter s WHERE s.updatedby = :updatedby"),
		@NamedQuery(name = "Scpobcparameter.findByStatus", query = "SELECT s FROM Scpobcparameter s WHERE s.status = :status"),
		@NamedQuery(name = "Scpobcparameter.findByTransporterid", query = "SELECT s FROM Scpobcparameter s WHERE s.transporterid = :transporterid") })
public class Scpobcparameter implements Serializable {
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
	@Column(name = "obcparamid")
	private Integer obcparamid;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "obcscpparamid")
	private Integer obcscpparamid;
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

	public Scpobcparameter() {
	}

	public Scpobcparameter(final Integer obcscpparamid) {
		this.obcscpparamid = obcscpparamid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Scpobcparameter)) {
			return false;
		}
		final Scpobcparameter other = (Scpobcparameter) object;
		return (this.obcscpparamid != null || other.obcscpparamid == null)
				&& (this.obcscpparamid == null || this.obcscpparamid.equals(other.obcscpparamid));
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

	public Integer getObcparamid() {
		return this.obcparamid;
	}

	public Integer getObcscpparamid() {
		return this.obcscpparamid;
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
		hash += ((this.obcscpparamid != null) ? this.obcscpparamid.hashCode() : 0);
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

	public void setObcparamid(final Integer obcparamid) {
		this.obcparamid = obcparamid;
	}

	public void setObcscpparamid(final Integer obcscpparamid) {
		this.obcscpparamid = obcscpparamid;
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
		return "com.camtrack.entities.Scpobcparameter[ obcscpparamid=" + this.obcscpparamid + " ]";
	}
}
