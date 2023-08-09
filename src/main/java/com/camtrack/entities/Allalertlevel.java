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
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "allalertlevel")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Allalertlevel.findAll", query = "SELECT a FROM Allalertlevel a"),
		@NamedQuery(name = "Allalertlevel.findByCreatedate", query = "SELECT a FROM Allalertlevel a WHERE a.createdate = :createdate"),
		@NamedQuery(name = "Allalertlevel.findByIds", query = "SELECT a FROM Allalertlevel a WHERE a.ids = :ids"),
		@NamedQuery(name = "Allalertlevel.findByStatus", query = "SELECT a FROM Allalertlevel a WHERE a.status = :status"),
		@NamedQuery(name = "Allalertlevel.findByAlertlevel", query = "SELECT a FROM Allalertlevel a WHERE a.alertlevel = :alertlevel"),
		@NamedQuery(name = "Allalertlevel.findByAlarmlevel", query = "SELECT a FROM Allalertlevel a WHERE a.alarmlevel = :alarmlevel"),
		@NamedQuery(name = "Allalertlevel.findByRecordlevel", query = "SELECT a FROM Allalertlevel a WHERE a.recordlevel = :recordlevel") })
public class Allalertlevel implements Serializable {
	public static String log;
	private static final long serialVersionUID = 1L;
	static {
		Allalertlevel.log = "";
	}

	public static void init(final boolean createorupdate, final User user) {
		Allalertlevel.log = Utils.EntetesLog(createorupdate, user.getUsername(), "User Right On Entities");
	}

	@JsonBackReference("alllertaffiliate")
	@JoinColumn(name = "affiliateid", referencedColumnName = "affiliateid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Customeraffiliate affiliateid;
	@Column(name = "alarmlevel")
	private Boolean alarmlevel;
	@Column(name = "alertlevel")
	private Boolean alertlevel;
	@JsonBackReference("alllert")
	@JoinColumn(name = "clientid", referencedColumnName = "customerid")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Customer clientid;
	@Column(name = "createdate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ids")
	private Integer ids;

	@Column(name = "recordlevel")
	private Boolean recordlevel;

	@Column(name = "status")
	private Integer status;

	public Allalertlevel() {
	}

	public Allalertlevel(final Integer ids) {
		this.ids = ids;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Allalertlevel)) {
			return false;
		}
		final Allalertlevel other = (Allalertlevel) object;
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

	public Customer getClientid() {
		return this.clientid;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public Integer getIds() {
		return this.ids;
	}

	public Boolean getRecordlevel() {
		if (Objects.isNull(this.recordlevel)) {
			return false;
		}
		return this.recordlevel;
	}

	public Integer getStatus() {
		return this.status;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.ids != null) ? this.ids.hashCode() : 0);
		return hash;
	}

	public void setAffiliateid(final Customeraffiliate affiliateid, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(affiliateid)) {
				Allalertlevel.log = Utils.createnewlogs(Allalertlevel.log, affiliateid.getName() + "",
						"Entities Name: Affiliate");
			}
		} else if (!Objects.isNull(affiliateid) && !Objects.isNull(this.affiliateid)) {
			Allalertlevel.log = Utils.updatelogsinfos(Allalertlevel.log, this.affiliateid.getName() + "",
					affiliateid.getName() + "", "Entities Name: Affiliate");
		} else if (!Objects.isNull(affiliateid)) {
			Allalertlevel.log = Utils.updatelogsinfos(Allalertlevel.log, "", affiliateid.getName() + "",
					"Entities Name: Affiliate");
		}
		this.affiliateid = affiliateid;
	}

	public void setAlarmlevel(final Boolean alarmlevel, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(alarmlevel)) {
				Allalertlevel.log = Utils.createnewlogs(Allalertlevel.log, alarmlevel + "",
						"Alarm Level Status On Entities");
			}
		} else if (!Objects.isNull(alarmlevel) && !Objects.isNull(this.alarmlevel)) {
			Allalertlevel.log = Utils.updatelogsinfos(Allalertlevel.log, this.alarmlevel + "", alarmlevel + "",
					"Alarm Level Status On Entities");
		} else if (!Objects.isNull(alarmlevel)) {
			Allalertlevel.log = Utils.updatelogsinfos(Allalertlevel.log, "", alarmlevel + "",
					"Alarm Level Status On Entities");
		}
		if (Objects.isNull(alarmlevel)) {
			this.alarmlevel = false;
		} else {
			this.alarmlevel = alarmlevel;
		}
	}

	public void setAlertlevel(final Boolean alertlevel, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(alertlevel)) {
				Allalertlevel.log = Utils.createnewlogs(Allalertlevel.log, alertlevel + "",
						"Alert Level Status On Entities");
			}
		} else if (!Objects.isNull(alertlevel) && !Objects.isNull(this.alertlevel)) {
			Allalertlevel.log = Utils.updatelogsinfos(Allalertlevel.log, this.alertlevel + "", alertlevel + "",
					"Alert Level Status On Entities");
		} else if (!Objects.isNull(alertlevel)) {
			Allalertlevel.log = Utils.updatelogsinfos(Allalertlevel.log, "", alertlevel + "",
					"Alert Level Status On Entities");
		}
		if (Objects.isNull(alertlevel)) {
			this.alertlevel = false;
		} else {
			this.alertlevel = alertlevel;
		}
	}

	public void setClientid(final Customer clientid, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(clientid)) {
				Allalertlevel.log = Utils.createnewlogs(Allalertlevel.log, clientid.getName() + "",
						"Entities Name: Customer ");
			}
		} else if (!Objects.isNull(clientid) && !Objects.isNull(this.clientid)) {
			Allalertlevel.log = Utils.updatelogsinfos(Allalertlevel.log, this.clientid.getName() + "",
					clientid.getName() + "", "Entities Name: Customer");
		} else if (!Objects.isNull(clientid)) {
			Allalertlevel.log = Utils.updatelogsinfos(Allalertlevel.log, "", clientid.getName() + "",
					"Entities Name: Customer");
		}
		this.clientid = clientid;
	}

	public void setCreatedate(final Date createdate) {
		this.createdate = createdate;
	}

	public void setIds(final Integer ids) {
		this.ids = ids;
	}

	public void setRecordlevel(final Boolean recordlevel, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(recordlevel)) {
				Allalertlevel.log = Utils.createnewlogs(Allalertlevel.log, recordlevel + "",
						"Record Level Status On Entities");
			}
		} else if (!Objects.isNull(recordlevel) && !Objects.isNull(this.recordlevel)) {
			Allalertlevel.log = Utils.updatelogsinfos(Allalertlevel.log, this.recordlevel + "", recordlevel + "",
					"Record Level Status On Entities");
		} else if (!Objects.isNull(recordlevel)) {
			Allalertlevel.log = Utils.updatelogsinfos(Allalertlevel.log, "", recordlevel + "",
					"Record Level Status On Entities");
		}
		if (Objects.isNull(recordlevel)) {
			this.recordlevel = false;
		} else {
			this.recordlevel = recordlevel;
		}
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Allalertlevel[ ids=" + this.ids + " ]";
	}
}
