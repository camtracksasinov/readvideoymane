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
@Table(name = "scpadasparameter")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Scpadasparameter.findAll", query = "SELECT s FROM Scpadasparameter s"),
		@NamedQuery(name = "Scpadasparameter.findByAdascpparamid", query = "SELECT s FROM Scpadasparameter s WHERE s.adascpparamid = :adascpparamid"),
		@NamedQuery(name = "Scpadasparameter.findByClientid", query = "SELECT s FROM Scpadasparameter s WHERE s.clientid = :clientid"),
		@NamedQuery(name = "Scpadasparameter.findByAffiliateid", query = "SELECT s FROM Scpadasparameter s WHERE s.affiliateid = :affiliateid"),
		@NamedQuery(name = "Scpadasparameter.findByRecordvalue", query = "SELECT s FROM Scpadasparameter s WHERE s.recordvalue = :recordvalue"),
		@NamedQuery(name = "Scpadasparameter.findByAlarmvalue", query = "SELECT s FROM Scpadasparameter s WHERE s.alarmvalue = :alarmvalue"),
		@NamedQuery(name = "Scpadasparameter.findByAlertvalue", query = "SELECT s FROM Scpadasparameter s WHERE s.alertvalue = :alertvalue"),
		@NamedQuery(name = "Scpadasparameter.findByCreatedon", query = "SELECT s FROM Scpadasparameter s WHERE s.createdon = :createdon"),
		@NamedQuery(name = "Scpadasparameter.findByCreatedby", query = "SELECT s FROM Scpadasparameter s WHERE s.createdby = :createdby"),
		@NamedQuery(name = "Scpadasparameter.findByUpdatedon", query = "SELECT s FROM Scpadasparameter s WHERE s.updatedon = :updatedon"),
		@NamedQuery(name = "Scpadasparameter.findByUpdatedby", query = "SELECT s FROM Scpadasparameter s WHERE s.updatedby = :updatedby"),
		@NamedQuery(name = "Scpadasparameter.findByStatus", query = "SELECT s FROM Scpadasparameter s WHERE s.status = :status"),
		@NamedQuery(name = "Scpadasparameter.findByTransporterid", query = "SELECT s FROM Scpadasparameter s WHERE s.transporterid = :transporterid") })
public class Scpadasparameter implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "adascpparamid")
	private Integer adascpparamid;
	@Column(name = "adasparamid")
	private Integer adasparamid;
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

	public Scpadasparameter() {
	}

	public Scpadasparameter(final Integer adascpparamid) {
		this.adascpparamid = adascpparamid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Scpadasparameter)) {
			return false;
		}
		final Scpadasparameter other = (Scpadasparameter) object;
		return (this.adascpparamid != null || other.adascpparamid == null)
				&& (this.adascpparamid == null || this.adascpparamid.equals(other.adascpparamid));
	}

	public Integer getAdascpparamid() {
		return this.adascpparamid;
	}

	public Integer getAdasparamid() {
		return this.adasparamid;
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
		hash += ((this.adascpparamid != null) ? this.adascpparamid.hashCode() : 0);
		return hash;
	}

	public void setAdascpparamid(final Integer adascpparamid) {
		this.adascpparamid = adascpparamid;
	}

	public void setAdasparamid(final Integer adasparamid) {
		this.adasparamid = adasparamid;
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
		return "com.camtrack.entities.Scpadasparameter[ adascpparamid=" + this.adascpparamid + " ]";
	}
}
