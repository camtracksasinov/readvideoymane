//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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

import com.camtrack.config.Utils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "emailconfig")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Emailconfig.findAll", query = "SELECT e FROM Emailconfig e"),
		@NamedQuery(name = "Emailconfig.findByConfigid", query = "SELECT e FROM Emailconfig e WHERE e.configid = :configid"),
		@NamedQuery(name = "Emailconfig.findByLevelid", query = "SELECT e FROM Emailconfig e WHERE e.levelid = :levelid"),
		@NamedQuery(name = "Emailconfig.findByMailflag", query = "SELECT e FROM Emailconfig e WHERE e.mailflag = :mailflag"),
		@NamedQuery(name = "Emailconfig.findByCreatedby", query = "SELECT e FROM Emailconfig e WHERE e.createdby = :createdby"),
		@NamedQuery(name = "Emailconfig.findByCreatedon", query = "SELECT e FROM Emailconfig e WHERE e.createdon = :createdon"),
		@NamedQuery(name = "Emailconfig.findByUpdatedby", query = "SELECT e FROM Emailconfig e WHERE e.updatedby = :updatedby"),
		@NamedQuery(name = "Emailconfig.findByUpdatedon", query = "SELECT e FROM Emailconfig e WHERE e.updatedon = :updatedon"),
		@NamedQuery(name = "Emailconfig.findByRecordstatus", query = "SELECT e FROM Emailconfig e WHERE e.recordstatus = :recordstatus"),
		@NamedQuery(name = "Emailconfig.findByAlertstatus", query = "SELECT e FROM Emailconfig e WHERE e.alertstatus = :alertstatus"),
		@NamedQuery(name = "Emailconfig.findByAlarmstatus", query = "SELECT e FROM Emailconfig e WHERE e.alarmstatus = :alarmstatus"),
		@NamedQuery(name = "Emailconfig.findByListotheremailreceiver", query = "SELECT e FROM Emailconfig e WHERE e.listotheremailreceiver = :listotheremailreceiver") })
public class Emailconfig implements Serializable {
	public static String log;
	private static final long serialVersionUID = 1L;
	static {
		Emailconfig.log = "";
	}

	public static void init(final boolean createorupdate, final User user) {
		Emailconfig.log = Utils.EntetesLog(createorupdate, user.getUsername(), "Email config");
	}

	@JoinColumn(name = "affiliateid", referencedColumnName = "affiliateid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Customeraffiliate affiliateid;
	@Column(name = "alarmstatus")
	private Boolean alarmstatus;
	@Column(name = "alertstatus")
	private Boolean alertstatus;
	@JoinColumn(name = "clientid", referencedColumnName = "customerid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Customer clientid;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "configid")
	private Long configid;
	@Column(name = "createdby")
	private Integer createdby;
	@Column(name = "createdon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdon;
	@Column(name = "levelid")
	private Integer levelid;
	@Column(name = "listotheremailreceiver")
	private String listotheremailreceiver;
	@Column(name = "mailflag")
	private Integer mailflag;
	@JoinColumn(name = "paramtypeid", referencedColumnName = "parametertypeid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Parametertype paramtypeid;
	@Column(name = "recordstatus")
	private Boolean recordstatus;
	@JoinColumn(name = "transporterid", referencedColumnName = "transporterid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Transporter transporterid;
	@Column(name = "updatedby")
	private Integer updatedby;
	@Column(name = "updatedon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedon;

	@JoinColumn(name = "userid", referencedColumnName = "userid")
	@ManyToOne(fetch = FetchType.LAZY)
	private User userid;

	@JoinColumn(name = "vehicleid", referencedColumnName = "vehicleid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Vehicle vehicleid;

	public Emailconfig() {
	}

	public Emailconfig(final Long configid) {
		this.configid = configid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Emailconfig)) {
			return false;
		}
		final Emailconfig other = (Emailconfig) object;
		return (this.configid != null || other.configid == null)
				&& (this.configid == null || this.configid.equals(other.configid));
	}

	public Customeraffiliate getAffiliateid() {
		return this.affiliateid;
	}

	public Boolean getAlarmstatus() {
		return this.alarmstatus;
	}

	public Boolean getAlertstatus() {
		return this.alertstatus;
	}

	public Customer getClientid() {
		return this.clientid;
	}

	public Long getConfigid() {
		return this.configid;
	}

	public Integer getCreatedby() {
		return this.createdby;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public Integer getLevelid() {
		return this.levelid;
	}

	public String getListotheremailreceiver() {
		return this.listotheremailreceiver;
	}

	public Integer getMailflag() {
		return this.mailflag;
	}

	public Parametertype getParamtypeid() {
		return this.paramtypeid;
	}

	public Boolean getRecordstatus() {
		return this.recordstatus;
	}

	public Transporter getTransporterid() {
		return this.transporterid;
	}

	public Integer getUpdatedby() {
		return this.updatedby;
	}

	public Date getUpdatedon() {
		return this.updatedon;
	}

	public User getUserid() {
		return this.userid;
	}

	public Vehicle getVehicleid() {
		return this.vehicleid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.configid != null) ? this.configid.hashCode() : 0);
		return hash;
	}

	public void setAffiliateid(final Customeraffiliate affiliateid, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(affiliateid)) {
				Emailconfig.log = Utils.createnewlogs(Emailconfig.log, affiliateid.getName() + "",
						"Email Rith On Affiliate");
			}
		} else if (!Objects.isNull(affiliateid) && !Objects.isNull(this.affiliateid)) {
			Emailconfig.log = Utils.updatelogsinfos(Emailconfig.log, this.affiliateid.getName() + "",
					affiliateid.getName() + "", "Email Rith On Affiliate");
		} else if (!Objects.isNull(affiliateid)) {
			Emailconfig.log = Utils.updatelogsinfos(Emailconfig.log, "", affiliateid.getName() + "",
					"Email Rith On Affiliate");
		}
		this.affiliateid = affiliateid;
	}

	public void setAlarmstatus(final Boolean alarmstatus, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(alarmstatus)) {
				Emailconfig.log = Utils.createnewlogs(Emailconfig.log, alarmstatus + "", "Right Alarm Level");
			}
		} else if (!Objects.isNull(alarmstatus) && !Objects.isNull(this.alarmstatus)) {
			Emailconfig.log = Utils.updatelogsinfos(Emailconfig.log, this.alarmstatus + "", alarmstatus + "",
					"Right Alarm Level");
		} else if (!Objects.isNull(alarmstatus)) {
			Emailconfig.log = Utils.updatelogsinfos(Emailconfig.log, "", alarmstatus + "", "Right Alarm Level");
		}
		this.alarmstatus = alarmstatus;
	}

	public void setAlertstatus(final Boolean alertstatus, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(alertstatus)) {
				Emailconfig.log = Utils.createnewlogs(Emailconfig.log, alertstatus + "", "Right Alert Level");
			}
		} else if (!Objects.isNull(alertstatus) && !Objects.isNull(this.alertstatus)) {
			Emailconfig.log = Utils.updatelogsinfos(Emailconfig.log, this.alertstatus + "", alertstatus + "",
					"Right Alert Level");
		} else if (!Objects.isNull(alertstatus)) {
			Emailconfig.log = Utils.updatelogsinfos(Emailconfig.log, "", alertstatus + "", "Right Alert Level");
		}
		this.alertstatus = alertstatus;
	}

	public void setClientid(final Customer clientid, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(clientid)) {
				Emailconfig.log = Utils.createnewlogs(Emailconfig.log, clientid.getName() + "",
						"Email Rith On Customer");
			}
		} else if (!Objects.isNull(clientid) && !Objects.isNull(this.clientid)) {
			Emailconfig.log = Utils.updatelogsinfos(Emailconfig.log, this.clientid.getName() + "",
					clientid.getName() + "", "Email Rith On Customer");
		} else if (!Objects.isNull(clientid)) {
			Emailconfig.log = Utils.updatelogsinfos(Emailconfig.log, "", clientid.getName() + "",
					"Email Rith On Customer");
		}
		this.clientid = clientid;
	}

	public void setConfigid(final Long configid) {
		this.configid = configid;
	}

	public void setCreatedby(final Integer createdby) {
		this.createdby = createdby;
	}

	public void setCreatedon(final Date createdon) {
		this.createdon = createdon;
	}

	public void setLevelid(final Integer levelid) {
		this.levelid = levelid;
	}

	public void setListotheremailreceiver(final String listotheremailreceiver, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(listotheremailreceiver)) {
				Emailconfig.log = Utils.createnewlogs(Emailconfig.log, listotheremailreceiver + "",
						"List Other Email On Configuration");
			}
		} else if (!Objects.isNull(listotheremailreceiver) && !Objects.isNull(this.listotheremailreceiver)) {
			Emailconfig.log = Utils.updatelogsinfos(Emailconfig.log, this.listotheremailreceiver + "",
					listotheremailreceiver + "", "List Other Email On Configuration");
		} else if (!Objects.isNull(listotheremailreceiver)) {
			Emailconfig.log = Utils.updatelogsinfos(Emailconfig.log, "", listotheremailreceiver + "",
					"List Other Email On Configuration");
		}
		this.listotheremailreceiver = listotheremailreceiver;
	}

	public void setMailflag(final Integer mailflag) {
		this.mailflag = mailflag;
	}

	public void setParamtypeid(final Parametertype paramtypeid, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(paramtypeid)) {
				Emailconfig.log = Utils.createnewlogs(Emailconfig.log, paramtypeid.getName() + "", "Parameter Type");
			}
		} else if (!Objects.isNull(paramtypeid) && !Objects.isNull(this.paramtypeid)) {
			Emailconfig.log = Utils.updatelogsinfos(Emailconfig.log, this.paramtypeid.getName() + "",
					paramtypeid.getName() + "", "Parameter Type");
		} else if (!Objects.isNull(paramtypeid)) {
			Emailconfig.log = Utils.updatelogsinfos(Emailconfig.log, "", paramtypeid.getName() + "", "Parameter Type");
		}
		this.paramtypeid = paramtypeid;
	}

	public void setRecordstatus(final Boolean recordstatus, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(recordstatus)) {
				Emailconfig.log = Utils.createnewlogs(Emailconfig.log, recordstatus + "", "Right Record Level");
			}
		} else if (!Objects.isNull(recordstatus) && !Objects.isNull(this.recordstatus)) {
			Emailconfig.log = Utils.updatelogsinfos(Emailconfig.log, this.recordstatus + "", recordstatus + "",
					"Right Record Level");
		} else if (!Objects.isNull(recordstatus)) {
			Emailconfig.log = Utils.updatelogsinfos(Emailconfig.log, "", recordstatus + "", "Right Record Level");
		}
		this.recordstatus = recordstatus;
	}

	public void setTransporterid(final Transporter transporterid, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(transporterid)) {
				Emailconfig.log = Utils.createnewlogs(Emailconfig.log, transporterid.getName() + "",
						"Email Rith On Transporter");
			}
		} else if (!Objects.isNull(transporterid) && !Objects.isNull(this.transporterid)) {
			Emailconfig.log = Utils.updatelogsinfos(Emailconfig.log, this.transporterid.getName() + "",
					transporterid.getName() + "", "Email Rith On Transporter");
		} else if (!Objects.isNull(transporterid)) {
			Emailconfig.log = Utils.updatelogsinfos(Emailconfig.log, "", transporterid.getName() + "",
					"Email Rith On Transporter");
		}
		this.transporterid = transporterid;
	}

	public void setUpdatedby(final Integer updatedby) {
		this.updatedby = updatedby;
	}

	public void setUpdatedon(final Date updatedon) {
		this.updatedon = updatedon;
	}

	public void setUserid(final User userid, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(userid)) {
				Emailconfig.log = Utils.createnewlogs(Emailconfig.log, userid.getName() + "", "User Name");
			}
		} else if (!Objects.isNull(userid) && !Objects.isNull(this.userid)) {
			Emailconfig.log = Utils.updatelogsinfos(Emailconfig.log, this.userid.getName() + "", userid.getName() + "",
					"User Name");
		} else if (!Objects.isNull(userid)) {
			Emailconfig.log = Utils.updatelogsinfos(Emailconfig.log, "", userid.getName() + "", "User Name");
		}
		this.userid = userid;
	}

	public void setVehicleid(final Vehicle vehicleid, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(vehicleid)) {
				Emailconfig.log = Utils.createnewlogs(Emailconfig.log, vehicleid.getVehicledesc() + "", "Vehicle Name");
			}
		} else if (!Objects.isNull(vehicleid) && !Objects.isNull(this.vehicleid)) {
			Emailconfig.log = Utils.updatelogsinfos(Emailconfig.log, this.vehicleid.getVehicledesc() + "",
					vehicleid.getVehicledesc() + "", "Vehicle Name");
		} else if (!Objects.isNull(vehicleid)) {
			Emailconfig.log = Utils.updatelogsinfos(Emailconfig.log, "", vehicleid.getVehicledesc() + "",
					"Vehicle Name");
		}
		this.vehicleid = vehicleid;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Emailconfig[ configid=" + this.configid + " ]";
	}
}
