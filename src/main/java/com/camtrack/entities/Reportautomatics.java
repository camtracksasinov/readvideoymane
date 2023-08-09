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
@Table(name = "reportautomatics")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Reportautomatics.findAll", query = "SELECT r FROM Reportautomatics r"),
		@NamedQuery(name = "Reportautomatics.findByIds", query = "SELECT r FROM Reportautomatics r WHERE r.ids = :ids"),
		@NamedQuery(name = "Reportautomatics.findByRequestbodyforservice", query = "SELECT r FROM Reportautomatics r WHERE r.requestbodyforservice = :requestbodyforservice"),
		@NamedQuery(name = "Reportautomatics.findByEmailist", query = "SELECT r FROM Reportautomatics r WHERE r.emailist = :emailist"),
		@NamedQuery(name = "Reportautomatics.findByMailsubject", query = "SELECT r FROM Reportautomatics r WHERE r.mailsubject = :mailsubject"),
		@NamedQuery(name = "Reportautomatics.findByMailbody", query = "SELECT r FROM Reportautomatics r WHERE r.mailbody = :mailbody"),
		@NamedQuery(name = "Reportautomatics.findByHourofrequest", query = "SELECT r FROM Reportautomatics r WHERE r.hourofrequest = :hourofrequest"),
		@NamedQuery(name = "Reportautomatics.findByDayofweekordayofmonth", query = "SELECT r FROM Reportautomatics r WHERE r.dayofweekordayofmonth = :dayofweekordayofmonth"),
		@NamedQuery(name = "Reportautomatics.findByCreateon", query = "SELECT r FROM Reportautomatics r WHERE r.createon = :createon"),
		@NamedQuery(name = "Reportautomatics.findByUpdateon", query = "SELECT r FROM Reportautomatics r WHERE r.updateon = :updateon") })
public class Reportautomatics implements Serializable {
	public static String log;
	private static final long serialVersionUID = 1L;

	public static String getLog() {
		return Reportautomatics.log;
	}

	public static void init(final boolean createorupdate, final User user) {
		Reportautomatics.log = Utils.EntetesLog(createorupdate, user.getUsername(), "Program Automatic Report");
	}

	public static void setLog(final String log) {
		Reportautomatics.log = log;
	}

	private Integer actives;
	@JoinColumn(name = "affid", referencedColumnName = "affiliateid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Customeraffiliate affid;
	@JoinColumn(name = "createby", referencedColumnName = "userid")
	@ManyToOne(fetch = FetchType.LAZY)
	private User createby;
	@Column(name = "createon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createon;
	@JoinColumn(name = "customid", referencedColumnName = "customerid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Customer customid;
	@Column(name = "dayofweekordayofmonth")
	private Short dayofweekordayofmonth;
	@Column(name = "emailist")
	private String emailist;
	@Column(name = "hourofrequest")
	private String hourofrequest;
	@JoinColumn(name = "idformat", referencedColumnName = "ids")
	@ManyToOne(fetch = FetchType.LAZY)
	private Formatexports idformat;
	@JoinColumn(name = "idfreq", referencedColumnName = "ids")
	@ManyToOne(fetch = FetchType.LAZY)
	private Frequences idfreq;
	@JoinColumn(name = "idreportname", referencedColumnName = "ids")
	@ManyToOne(fetch = FetchType.LAZY)
	private Listreports idreportname;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ids")
	private Long ids;
	@Column(name = "mailbody")
	private String mailbody;
	@Column(name = "mailsubject")
	private String mailsubject;
	@Temporal(TemporalType.DATE)
	private Date nextdate;
	@JoinColumn(name = "ranges", referencedColumnName = "ids")
	@ManyToOne(fetch = FetchType.LAZY)
	private Timerange ranges;
	@Column(name = "requestbodyforservice")
	private String requestbodyforservice;

	@JoinColumn(name = "transid", referencedColumnName = "transporterid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Transporter transid;

	@JoinColumn(name = "updateby", referencedColumnName = "userid")
	@ManyToOne(fetch = FetchType.LAZY)
	private User updateby;

	@Column(name = "updateon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateon;

	public Reportautomatics() {
	}

	public Reportautomatics(final Long ids) {
		this.ids = ids;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Reportautomatics)) {
			return false;
		}
		final Reportautomatics other = (Reportautomatics) object;
		return (this.ids != null || other.ids == null) && (this.ids == null || this.ids.equals(other.ids));
	}

	public Integer getActives() {
		return this.actives;
	}

	public Customeraffiliate getAffid() {
		return this.affid;
	}

	public User getCreateby() {
		return this.createby;
	}

	public Date getCreateon() {
		return this.createon;
	}

	public Customer getCustomid() {
		return this.customid;
	}

	public Short getDayofweekordayofmonth() {
		return this.dayofweekordayofmonth;
	}

	public String getEmailist() {
		return this.emailist;
	}

	public String getHourofrequest() {
		return this.hourofrequest;
	}

	public Formatexports getIdformat() {
		return this.idformat;
	}

	public Frequences getIdfreq() {
		return this.idfreq;
	}

	public Listreports getIdreportname() {
		return this.idreportname;
	}

	public Long getIds() {
		return this.ids;
	}

	public String getMailbody() {
		return this.mailbody;
	}

	public String getMailsubject() {
		return this.mailsubject;
	}

	public Date getNextdate() {
		return this.nextdate;
	}

	public Timerange getRanges() {
		return this.ranges;
	}

	public String getRequestbodyforservice() {
		return this.requestbodyforservice;
	}

	public Transporter getTransid() {
		return this.transid;
	}

	public User getUpdateby() {
		return this.updateby;
	}

	public Date getUpdateon() {
		return this.updateon;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.ids != null) ? this.ids.hashCode() : 0);
		return hash;
	}

	public Integer isActives() {
		return this.actives;
	}

	public void setActives(final Integer actives) {
		this.actives = actives;
	}

	public void setAffid(final Customeraffiliate affid) {
		this.affid = affid;
	}

	public void setCreateby(final User createby) {
		this.createby = createby;
	}

	public void setCreateon(final Date createon) {
		this.createon = createon;
	}

	public void setCustomid(final Customer customid) {
		this.customid = customid;
	}

	public void setDayofweekordayofmonth(final Short dayofweekordayofmonth) {
		this.dayofweekordayofmonth = dayofweekordayofmonth;
	}

	public void setDayofweekordayofmonth(final Short dayofweekordayofmonth, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(dayofweekordayofmonth)) {
				Reportautomatics.log = Utils.createnewlogs(Reportautomatics.log, dayofweekordayofmonth + "",
						"Day or Week");
			}
		} else if (!Objects.isNull(dayofweekordayofmonth) && !Objects.isNull(this.dayofweekordayofmonth)) {
			Reportautomatics.log = Utils.updatelogsinfos(Reportautomatics.log, this.dayofweekordayofmonth + "",
					dayofweekordayofmonth + "", "Day or Week");
		} else if (!Objects.isNull(dayofweekordayofmonth)) {
			Reportautomatics.log = Utils.updatelogsinfos(Reportautomatics.log, "", dayofweekordayofmonth + "",
					"Day or Week");
		}
		this.dayofweekordayofmonth = dayofweekordayofmonth;
	}

	public void setEmailist(final String emailist) {
		this.emailist = emailist;
	}

	public void setEmailist(final String emailist, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(emailist)) {
				Reportautomatics.log = Utils.createnewlogs(Reportautomatics.log, emailist + "", "Email List");
			}
		} else if (!Objects.isNull(emailist) && !Objects.isNull(this.emailist)) {
			Reportautomatics.log = Utils.updatelogsinfos(Reportautomatics.log, this.emailist + "", emailist + "",
					"Email List");
		} else if (!Objects.isNull(emailist)) {
			Reportautomatics.log = Utils.updatelogsinfos(Reportautomatics.log, "", emailist + "", "Email List");
		}
		this.emailist = emailist;
	}

	public void setHourofrequest(final String hourofrequest) {
		this.hourofrequest = hourofrequest;
	}

	public void setHourofrequest(final String hourofrequest, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(hourofrequest)) {
				Reportautomatics.log = Utils.createnewlogs(Reportautomatics.log, hourofrequest + "", "Hour Report");
			}
		} else if (!Objects.isNull(hourofrequest) && !Objects.isNull(this.hourofrequest)) {
			Reportautomatics.log = Utils.updatelogsinfos(Reportautomatics.log, this.hourofrequest + "",
					hourofrequest + "", "Hour Report");
		} else if (!Objects.isNull(hourofrequest)) {
			Reportautomatics.log = Utils.updatelogsinfos(Reportautomatics.log, "", hourofrequest + "", "Hour Report");
		}
		this.hourofrequest = hourofrequest;
	}

	public void setIdformat(final Formatexports idformat) {
		this.idformat = idformat;
	}

	public void setIdformat(final Formatexports idformat, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(idformat)) {
				Reportautomatics.log = Utils.createnewlogs(Reportautomatics.log, idformat.getFormat() + "",
						"Format Export");
			}
		} else if (!Objects.isNull(idformat) && !Objects.isNull(this.idformat)) {
			Reportautomatics.log = Utils.updatelogsinfos(Reportautomatics.log, this.idformat.getFormat() + "",
					idformat.getFormat() + "", "Format Export");
		} else if (!Objects.isNull(idformat)) {
			Reportautomatics.log = Utils.updatelogsinfos(Reportautomatics.log, "", idformat.getFormat() + "",
					"Format Export");
		}
		this.idformat = idformat;
	}

	public void setIdfreq(final Frequences idfreq) {
		this.idfreq = idfreq;
	}

	public void setIdfreq(final Frequences idfreq, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(idfreq)) {
				Reportautomatics.log = Utils.createnewlogs(Reportautomatics.log, idfreq.getFrequences() + "",
						"Frequences");
			}
		} else if (!Objects.isNull(idfreq) && !Objects.isNull(this.idfreq)) {
			Reportautomatics.log = Utils.updatelogsinfos(Reportautomatics.log, this.idfreq.getFrequences() + "",
					idfreq.getFrequences() + "", "Frequences");
		} else if (!Objects.isNull(idfreq)) {
			Reportautomatics.log = Utils.updatelogsinfos(Reportautomatics.log, "", idfreq.getFrequences() + "",
					"Frequences");
		}
		this.idfreq = idfreq;
	}

	public void setIdreportname(final Listreports idreportname) {
		this.idreportname = idreportname;
	}

	public void setIdreportname(final Listreports idreportname, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(idreportname)) {
				Reportautomatics.log = Utils.createnewlogs(Reportautomatics.log, idreportname.getReportname() + "",
						"Automatic Reports Name");
			}
		} else if (!Objects.isNull(idreportname) && !Objects.isNull(this.idreportname)) {
			Reportautomatics.log = Utils.updatelogsinfos(Reportautomatics.log, this.idreportname.getReportname() + "",
					idreportname.getReportname() + "", "Automatic Reports Name");
		} else if (!Objects.isNull(idreportname)) {
			Reportautomatics.log = Utils.updatelogsinfos(Reportautomatics.log, "", idreportname.getReportname() + "",
					"Automatic Reports Name");
		}
		this.idreportname = idreportname;
	}

	public void setIds(final Long ids) {
		this.ids = ids;
	}

	public void setMailbody(final String mailbody) {
		this.mailbody = mailbody;
	}

	public void setMailbody(final String mailbody, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(mailbody)) {
				Reportautomatics.log = Utils.createnewlogs(Reportautomatics.log, mailbody + "", "Mail Body");
			}
		} else if (!Objects.isNull(mailbody) && !Objects.isNull(this.mailbody)) {
			Reportautomatics.log = Utils.updatelogsinfos(Reportautomatics.log, this.mailbody + "", mailbody + "",
					"Body Mail");
		} else if (!Objects.isNull(mailbody)) {
			Reportautomatics.log = Utils.updatelogsinfos(Reportautomatics.log, "", mailbody + "", "Body Mail");
		}
		this.mailbody = mailbody;
	}

	public void setMailsubject(final String mailsubject) {
		this.mailsubject = mailsubject;
	}

	public void setMailsubject(final String mailsubject, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(mailsubject)) {
				Reportautomatics.log = Utils.createnewlogs(Reportautomatics.log, mailsubject + "", "Email Subject");
			}
		} else if (!Objects.isNull(mailsubject) && !Objects.isNull(this.mailsubject)) {
			Reportautomatics.log = Utils.updatelogsinfos(Reportautomatics.log, this.mailsubject + "", mailsubject + "",
					"Email Subject");
		} else if (!Objects.isNull(mailsubject)) {
			Reportautomatics.log = Utils.updatelogsinfos(Reportautomatics.log, "", mailsubject + "", "Email Subject");
		}
		this.mailsubject = mailsubject;
	}

	public void setNextdate(final Date nextdate) {
		this.nextdate = nextdate;
	}

	public void setRanges(final Timerange ranges) {
		this.ranges = ranges;
	}

	public void setRanges(final Timerange ranges, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(ranges)) {
				Reportautomatics.log = Utils.createnewlogs(Reportautomatics.log, ranges.getNames() + "",
						"Automatic Reports Range");
			}
		} else if (!Objects.isNull(ranges) && !Objects.isNull(this.ranges)) {
			Reportautomatics.log = Utils.updatelogsinfos(Reportautomatics.log, this.ranges.getNames() + "",
					ranges.getNames() + "", "Automatic Reports Range");
		} else if (!Objects.isNull(ranges)) {
			Reportautomatics.log = Utils.updatelogsinfos(Reportautomatics.log, "", ranges.getNames() + "",
					"Automatic Reports Range");
		}
		this.ranges = ranges;
	}

	public void setRequestbodyforservice(final String requestbodyforservice) {
		this.requestbodyforservice = requestbodyforservice;
	}

	public void setRequestbodyforservice(final String requestbodyforservice, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(requestbodyforservice)) {
				Reportautomatics.log = Utils.createnewlogs(Reportautomatics.log, requestbodyforservice + "",
						"Complet Request Param");
			}
		} else if (!Objects.isNull(requestbodyforservice) && !Objects.isNull(this.requestbodyforservice)) {
			Reportautomatics.log = Utils.updatelogsinfos(Reportautomatics.log, this.requestbodyforservice + "",
					requestbodyforservice + "", "Complet Request Param");
		} else if (!Objects.isNull(requestbodyforservice)) {
			Reportautomatics.log = Utils.updatelogsinfos(Reportautomatics.log, "", requestbodyforservice + "",
					"Complet Request Param");
		}
		this.requestbodyforservice = requestbodyforservice;
	}

	public void setTransid(final Transporter transid) {
		this.transid = transid;
	}

	public void setUpdateby(final User updateby) {
		this.updateby = updateby;
	}

	public void setUpdateon(final Date updateon) {
		this.updateon = updateon;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Reportautomatics[ ids=" + this.ids + " ]";
	}
}
