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
@Table(name = "userights")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Userights.findAll", query = "SELECT u FROM Userights u"),
		@NamedQuery(name = "Userights.findByCreatedon", query = "SELECT u FROM Userights u WHERE u.createdon = :createdon"),
		@NamedQuery(name = "Userights.findByCreatedby", query = "SELECT u FROM Userights u WHERE u.createdby = :createdby"),
		@NamedQuery(name = "Userights.findByUpdatedon", query = "SELECT u FROM Userights u WHERE u.updatedon = :updatedon"),
		@NamedQuery(name = "Userights.findByUpdatedby", query = "SELECT u FROM Userights u WHERE u.updatedby = :updatedby"),
		@NamedQuery(name = "Userights.findByIds", query = "SELECT u FROM Userights u WHERE u.ids = :ids") })
public class Userights implements Serializable {
	public static String log;
	private static final long serialVersionUID = 1L;
	static {
		Userights.log = "";
	}

	public static void init(final boolean createorupdate, final User user) {
		Userights.log = Utils.EntetesLog(createorupdate, user.getUsername(), "Userights");
	}

	@JoinColumn(name = "affiliateid", referencedColumnName = "affiliateid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Customeraffiliate affiliateid;
	@Column(name = "alarmlevel")
	private Boolean alarmlevel;
	@Column(name = "alertlevel")
	private Boolean alertlevel;
	@Column(name = "createdby")
	private Integer createdby;
	@Column(name = "createdon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdon;
	@JoinColumn(name = "customerid", referencedColumnName = "customerid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Customer customerid;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ids")
	private Integer ids;
	@JoinColumn(name = "idtypealert", referencedColumnName = "parametertypeid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Parametertype idtypealert;
	@Column(name = "recordlevel")
	private Boolean recordlevel;
	@Column(name = "status")
	private Short status;
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

	public Userights() {
	}

	public Userights(final Integer ids) {
		this.ids = ids;
	}

	public Userights(final Short status) {
		this.status = status;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Userights)) {
			return false;
		}
		final Userights other = (Userights) object;
		return (this.ids != null || other.ids == null) && (this.ids == null || this.ids.equals(other.ids));
	}

	public Customeraffiliate getAffiliateid() {
		return this.affiliateid;
	}

	public Boolean getAlarmlevel() {
		if (Objects.isNull(this.alarmlevel)) {
			return false;
		}
		return this.alarmlevel;
	}

	public Boolean getAlertlevel() {
		if (Objects.isNull(this.alertlevel)) {
			return false;
		}
		return this.alertlevel;
	}

	public Integer getCreatedby() {
		return this.createdby;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public Customer getCustomerid() {
		return this.customerid;
	}

	public Integer getIds() {
		return this.ids;
	}

	public Parametertype getIdtypealert() {
		return this.idtypealert;
	}

	public Boolean getRecordlevel() {
		if (Objects.isNull(this.recordlevel)) {
			return false;
		}
		return this.recordlevel;
	}

	public Short getStatus() {
		return this.status;
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

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.ids != null) ? this.ids.hashCode() : 0);
		return hash;
	}

	public void setAffiliateid(final Customeraffiliate affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setAffiliateid(final Customeraffiliate affiliateid, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(affiliateid)) {
				Userights.log = Utils.createnewlogs(Userights.log, affiliateid.getName() + "", "Affiliate");
			}
		} else if (!Objects.isNull(affiliateid) && !Objects.isNull(this.affiliateid)) {
			Userights.log = Utils.updatelogsinfos(Userights.log, this.affiliateid.getName() + "",
					affiliateid.getName() + "", "Affiliate");
		} else if (!Objects.isNull(affiliateid)) {
			Userights.log = Utils.updatelogsinfos(Userights.log, "", affiliateid.getName() + "", "Affiliate");
		}
		this.affiliateid = affiliateid;
	}

	public void setAlarmlevel(final Boolean alarmlevel) {
		this.alarmlevel = alarmlevel;
	}

	public void setAlarmlevel(final Boolean alarmlevel, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(alarmlevel)) {
				Userights.log = Utils.createnewlogs(Userights.log, alarmlevel + "", "Right Alarm Level");
			}
		} else if (!Objects.isNull(alarmlevel) && !Objects.isNull(this.alarmlevel)) {
			Userights.log = Utils.updatelogsinfos(Userights.log, this.alarmlevel + "", alarmlevel + "",
					"Right Alarm Level");
		} else if (!Objects.isNull(alarmlevel)) {
			Userights.log = Utils.updatelogsinfos(Userights.log, "", alarmlevel + "", "Right Alarm Level");
		}
		if (Objects.isNull(alarmlevel)) {
			this.alarmlevel = false;
		} else {
			this.alarmlevel = alarmlevel;
		}
	}

	public void setAlertlevel(final Boolean alertlevel) {
		this.alertlevel = alertlevel;
	}

	public void setAlertlevel(final Boolean alertlevel, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(alertlevel)) {
				Userights.log = Utils.createnewlogs(Userights.log, alertlevel + "", "Right Alert Level");
			}
		} else if (!Objects.isNull(alertlevel) && !Objects.isNull(this.alertlevel)) {
			Userights.log = Utils.updatelogsinfos(Userights.log, this.alertlevel + "", alertlevel + "",
					"Right Alert Level");
		} else if (!Objects.isNull(alertlevel)) {
			Userights.log = Utils.updatelogsinfos(Userights.log, "", alertlevel + "", "Right Alert Level");
		}
		if (Objects.isNull(alertlevel)) {
			this.alertlevel = false;
		} else {
			this.alertlevel = alertlevel;
		}
	}

	public void setCreatedby(final Integer createdby) {
		this.createdby = createdby;
	}

	public void setCreatedon(final Date createdon) {
		this.createdon = createdon;
	}

	public void setCustomerid(final Customer customerid) {
		this.customerid = customerid;
	}

	public void setCustomerid(final Customer customerid, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(customerid)) {
				Userights.log = Utils.createnewlogs(Userights.log, customerid.getName() + "", "Customer");
			}
		} else if (!Objects.isNull(customerid) && !Objects.isNull(this.customerid)) {
			Userights.log = Utils.updatelogsinfos(Userights.log, this.customerid.getName() + "",
					customerid.getName() + "", "Customer");
		} else if (!Objects.isNull(customerid)) {
			Userights.log = Utils.updatelogsinfos(Userights.log, "", customerid.getName() + "", "Customer");
		}
		this.customerid = customerid;
	}

	public void setIds(final Integer ids) {
		this.ids = ids;
	}

	public void setIdtypealert(final Parametertype idtypealert) {
		this.idtypealert = idtypealert;
	}

	public void setIdtypealert(final Parametertype idtypealert, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(idtypealert)) {
				Userights.log = Utils.createnewlogs(Userights.log, idtypealert.getName() + "", "Parameter Type");
			}
		} else if (!Objects.isNull(idtypealert) && !Objects.isNull(this.idtypealert)) {
			Userights.log = Utils.updatelogsinfos(Userights.log, this.idtypealert.getName() + "",
					idtypealert.getName() + "", "Parameter Type");
		} else if (!Objects.isNull(idtypealert)) {
			Userights.log = Utils.updatelogsinfos(Userights.log, "", idtypealert.getName() + "", "Parameter Type");
		}
		this.idtypealert = idtypealert;
	}

	public void setRecordlevel(final Boolean recordlevel) {
		this.recordlevel = recordlevel;
	}

	public void setRecordlevel(final Boolean recordlevel, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(recordlevel)) {
				Userights.log = Utils.createnewlogs(Userights.log, recordlevel + "", "Right Record Level");
			}
		} else if (!Objects.isNull(recordlevel) && !Objects.isNull(this.recordlevel)) {
			Userights.log = Utils.updatelogsinfos(Userights.log, this.recordlevel + "", recordlevel + "",
					"Right Record Level");
		} else if (!Objects.isNull(recordlevel)) {
			Userights.log = Utils.updatelogsinfos(Userights.log, "", recordlevel + "", "Right Record Level");
		}
		if (Objects.isNull(recordlevel)) {
			this.recordlevel = false;
		} else {
			this.recordlevel = recordlevel;
		}
	}

	public void setStatus(final Short status) {
		this.status = status;
	}

	public void setTransporterid(final Transporter transporterid) {
		this.transporterid = transporterid;
	}

	public void setTransporterid(final Transporter transporterid, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(transporterid)) {
				Userights.log = Utils.createnewlogs(Userights.log, transporterid.getName() + "", "Affiliate");
			}
		} else if (!Objects.isNull(transporterid) && !Objects.isNull(this.transporterid)) {
			Userights.log = Utils.updatelogsinfos(Userights.log, this.transporterid.getName() + "",
					transporterid.getName() + "", "Transporter");
		} else if (!Objects.isNull(transporterid)) {
			Userights.log = Utils.updatelogsinfos(Userights.log, "", transporterid.getName() + "", "Transporter");
		}
		this.transporterid = transporterid;
	}

	public void setUpdatedby(final Integer updatedby) {
		this.updatedby = updatedby;
	}

	public void setUpdatedon(final Date updatedon) {
		this.updatedon = updatedon;
	}

	public void setUserid(final User userid) {
		this.userid = userid;
	}

	public void setUserid(final User userid, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(userid)) {
				Userights.log = Utils.createnewlogs(Userights.log, userid.getName() + "", "User Name");
			}
		} else if (!Objects.isNull(userid) && !Objects.isNull(this.userid)) {
			Userights.log = Utils.updatelogsinfos(Userights.log, this.userid.getName() + "", userid.getName() + "",
					"User Name");
		} else if (!Objects.isNull(userid)) {
			Userights.log = Utils.updatelogsinfos(Userights.log, "", userid.getName() + "", "User Name");
		}
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Userights[ ids=" + this.ids + " ]";
	}
}
