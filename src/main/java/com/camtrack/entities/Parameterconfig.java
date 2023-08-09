//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
@Table(name = "parameterconfig")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Parameterconfig.findAll", query = "SELECT p FROM Parameterconfig p"),
		@NamedQuery(name = "Parameterconfig.findByThresholdlimit", query = "SELECT p FROM Parameterconfig p WHERE p.thresholdlimit = :thresholdlimit"),
		@NamedQuery(name = "Parameterconfig.findByRecordingthreshold", query = "SELECT p FROM Parameterconfig p WHERE p.recordingthreshold = :recordingthreshold"),
		@NamedQuery(name = "Parameterconfig.findByRecordingdelayinput", query = "SELECT p FROM Parameterconfig p WHERE p.recordingdelayinput = :recordingdelayinput"),
		@NamedQuery(name = "Parameterconfig.findByAlertthreshold", query = "SELECT p FROM Parameterconfig p WHERE p.alertthreshold = :alertthreshold"),
		@NamedQuery(name = "Parameterconfig.findByAlertdelayinput", query = "SELECT p FROM Parameterconfig p WHERE p.alertdelayinput = :alertdelayinput"),
		@NamedQuery(name = "Parameterconfig.findByAlarmthreshold", query = "SELECT p FROM Parameterconfig p WHERE p.alarmthreshold = :alarmthreshold"),
		@NamedQuery(name = "Parameterconfig.findByAlarmdelayinput", query = "SELECT p FROM Parameterconfig p WHERE p.alarmdelayinput = :alarmdelayinput"),
		@NamedQuery(name = "Parameterconfig.findByCreatedon", query = "SELECT p FROM Parameterconfig p WHERE p.createdon = :createdon"),
		@NamedQuery(name = "Parameterconfig.findByUpdatedon", query = "SELECT p FROM Parameterconfig p WHERE p.updatedon = :updatedon"),
		@NamedQuery(name = "Parameterconfig.findByFrommonth", query = "SELECT p FROM Parameterconfig p WHERE p.frommonth = :frommonth"),
		@NamedQuery(name = "Parameterconfig.findByMinimumdistance", query = "SELECT p FROM Parameterconfig p WHERE p.minimumdistance = :minimumdistance"),
		@NamedQuery(name = "Parameterconfig.findByRequiredresttime", query = "SELECT p FROM Parameterconfig p WHERE p.requiredresttime = :requiredresttime"),
		@NamedQuery(name = "Parameterconfig.findByFromtime", query = "SELECT p FROM Parameterconfig p WHERE p.fromtime = :fromtime"),
		@NamedQuery(name = "Parameterconfig.findByTotime", query = "SELECT p FROM Parameterconfig p WHERE p.totime = :totime"),
		@NamedQuery(name = "Parameterconfig.findByTomonth", query = "SELECT p FROM Parameterconfig p WHERE p.tomonth = :tomonth"),
		@NamedQuery(name = "Parameterconfig.findByRecordingthresholdperc", query = "SELECT p FROM Parameterconfig p WHERE p.recordingthresholdperc = :recordingthresholdperc"),
		@NamedQuery(name = "Parameterconfig.findByAlertthresholdperc", query = "SELECT p FROM Parameterconfig p WHERE p.alertthresholdperc = :alertthresholdperc"),
		@NamedQuery(name = "Parameterconfig.findByAlarmthresholdperc", query = "SELECT p FROM Parameterconfig p WHERE p.alarmthresholdperc = :alarmthresholdperc"),
		@NamedQuery(name = "Parameterconfig.findByRollingdays", query = "SELECT p FROM Parameterconfig p WHERE p.rollingdays = :rollingdays"),
		@NamedQuery(name = "Parameterconfig.findById", query = "SELECT p FROM Parameterconfig p WHERE p.id = :id") })
public class Parameterconfig implements Serializable {
	public static String log;
	private static final long serialVersionUID = 1L;
	static {
		Parameterconfig.log = "";
	}

	public static void init(final boolean createorupdate, final User user) {
		Parameterconfig.log = Utils.EntetesLog(createorupdate, user.getUsername(), "Parameter config");
	}

	@Column(name = "alarmdelayinput")
	private BigDecimal alarmdelayinput;
	@Column(name = "alarmthreshold")
	private BigDecimal alarmthreshold;
	@Column(name = "alarmthresholdperc")
	private BigDecimal alarmthresholdperc;
	@Column(name = "alertdelayinput")
	private BigDecimal alertdelayinput;
	@Column(name = "alertthreshold")
	private BigDecimal alertthreshold;
	@Column(name = "alertthresholdperc")
	private BigDecimal alertthresholdperc;
	@JoinColumn(name = "clientaffiliateid", referencedColumnName = "affiliateid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Customeraffiliate clientaffiliateid;
	@JoinColumn(name = "createdby", referencedColumnName = "userid")
	@ManyToOne(fetch = FetchType.LAZY)
	private User createdby;
	@Column(name = "createdon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdon;
	@JoinColumn(name = "customerid", referencedColumnName = "customerid")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Customer customerid;
	@JoinColumn(name = "defaultlevelid", referencedColumnName = "exceptionlevelid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Exceptionlevel defaultlevelid;
	@Column(name = "driverid")
	private Integer driverid;
	@Column(name = "frommonth")
	private Integer frommonth;
	@Column(name = "fromtime")
	@Temporal(TemporalType.TIME)
	private Date fromtime;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Column(name = "minimumdistance")
	private BigDecimal minimumdistance;
	@JoinColumn(name = "parametertypeid", referencedColumnName = "parametertypeid")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Parametertype parametertypeid;
	@Column(name = "prevthreshold")
	private BigDecimal prevthreshold;
	@Column(name = "recordingdelayinput")
	private BigDecimal recordingdelayinput;
	@Column(name = "recordingthreshold")
	private BigDecimal recordingthreshold;
	@Column(name = "recordingthresholdperc")
	private BigDecimal recordingthresholdperc;
	@Column(name = "requiredresttime")
	private BigDecimal requiredresttime;
	@Column(name = "rollingdays")
	private Integer rollingdays;
	@JoinColumn(name = "status", referencedColumnName = "statusid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Status status;
	@Column(name = "thresholdlimit")
	private BigDecimal thresholdlimit;
	@Column(name = "tomonth")
	private Integer tomonth;
	@Column(name = "totime")
	@Temporal(TemporalType.TIME)
	private Date totime;
	@Column(name = "transporter")
	private Integer transporter;
	@JoinColumn(name = "updatedby", referencedColumnName = "userid")
	@ManyToOne(fetch = FetchType.LAZY)
	private User updatedby;

	@Column(name = "updatedon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedon;

	@Column(name = "vehicleid")
	private Integer vehicleid;

	public Parameterconfig() {
	}

	public Parameterconfig(final BigDecimal thresholdlimit, final BigDecimal recordingthreshold,
			final BigDecimal recordingdelayinput, final BigDecimal alertthreshold, final BigDecimal alertdelayinput,
			final BigDecimal alarmthreshold, final BigDecimal alarmdelayinput, final Integer frommonth,
			final BigDecimal minimumdistance, final BigDecimal requiredresttime, final Date fromtime, final Date totime,
			final Integer tomonth, final BigDecimal recordingthresholdperc, final BigDecimal alertthresholdperc,
			final BigDecimal alarmthresholdperc, final Integer rollingdays, final Customer customerid,
			final Customeraffiliate clientaffiliateid, final Status status, final User users,
			final Parametertype parametertypeid, final Boolean createorupdate) {
		final Date date = new Date();
		this.thresholdlimit = thresholdlimit;
		this.recordingthreshold = recordingthreshold;
		this.recordingdelayinput = recordingdelayinput;
		this.alertthreshold = alertthreshold;
		this.alertdelayinput = alertdelayinput;
		this.alarmthreshold = alarmthreshold;
		this.alarmdelayinput = alarmdelayinput;
		this.createdon = date;
		this.updatedon = date;
		this.frommonth = frommonth;
		this.minimumdistance = minimumdistance;
		this.requiredresttime = requiredresttime;
		this.fromtime = fromtime;
		this.totime = totime;
		this.tomonth = tomonth;
		this.recordingthresholdperc = recordingthresholdperc;
		this.alertthresholdperc = alertthresholdperc;
		this.alarmthresholdperc = alarmthresholdperc;
		this.rollingdays = rollingdays;
		this.setCustomerid(customerid, createorupdate);
		this.setClientaffiliateid(clientaffiliateid, createorupdate);
		this.status = status;
		this.createdby = users;
		this.updatedby = users;
		this.setParametertypeid(parametertypeid, createorupdate);
	}

	public Parameterconfig(final Long id) {
		this.id = id;
	}

	public Parameterconfig(final Long id, final Parametertype parametertypeid) {
		this.id = id;
		this.parametertypeid = parametertypeid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Parameterconfig)) {
			return false;
		}
		final Parameterconfig other = (Parameterconfig) object;
		return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
	}

	public BigDecimal getAlarmdelayinput() {
		if (Objects.isNull(this.alarmdelayinput)) {
			return BigDecimal.ZERO;
		}
		return this.alarmdelayinput;
	}

	public BigDecimal getAlarmthreshold() {
		if (Objects.isNull(this.alarmthreshold)) {
			return BigDecimal.ZERO;
		}
		return this.alarmthreshold;
	}

	public BigDecimal getAlarmthresholdperc() {
		if (Objects.isNull(this.alarmthresholdperc)) {
			return BigDecimal.ZERO;
		}
		return this.alarmthresholdperc;
	}

	public BigDecimal getAlertdelayinput() {
		if (Objects.isNull(this.alertdelayinput)) {
			return BigDecimal.ZERO;
		}
		return this.alertdelayinput;
	}

	public BigDecimal getAlertthreshold() {
		if (Objects.isNull(this.alertthreshold)) {
			return BigDecimal.ZERO;
		}
		return this.alertthreshold;
	}

	public BigDecimal getAlertthresholdperc() {
		if (Objects.isNull(this.alertthresholdperc)) {
			return BigDecimal.ZERO;
		}
		return this.alertthresholdperc;
	}

	public Customeraffiliate getClientaffiliateid() {
		return this.clientaffiliateid;
	}

	public User getCreatedby() {
		return this.createdby;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public Customer getCustomerid() {
		return this.customerid;
	}

	public Exceptionlevel getDefaultlevelid() {
		return this.defaultlevelid;
	}

	public Integer getDriverid() {
		return this.driverid;
	}

	public Integer getFrommonth() {
		return this.frommonth;
	}

	public Date getFromtime() {
		return this.fromtime;
	}

	public Long getId() {
		return this.id;
	}

	public BigDecimal getMinimumdistance() {
		if (Objects.isNull(this.minimumdistance)) {
			return BigDecimal.ZERO;
		}
		return this.minimumdistance;
	}

	public Parametertype getParametertypeid() {
		return this.parametertypeid;
	}

	public BigDecimal getPrevthreshold() {
		return this.prevthreshold;
	}

	public BigDecimal getRecordingdelayinput() {
		if (Objects.isNull(this.recordingdelayinput)) {
			return BigDecimal.ZERO;
		}
		return this.recordingdelayinput;
	}

	public BigDecimal getRecordingthreshold() {
		if (Objects.isNull(this.recordingthreshold)) {
			return BigDecimal.ZERO;
		}
		return this.recordingthreshold;
	}

	public BigDecimal getRecordingthresholdperc() {
		if (Objects.isNull(this.recordingthresholdperc)) {
			return BigDecimal.ZERO;
		}
		return this.recordingthresholdperc;
	}

	public BigDecimal getRequiredresttime() {
		if (Objects.isNull(this.requiredresttime)) {
			return BigDecimal.ZERO;
		}
		return this.requiredresttime;
	}

	public Integer getRollingdays() {
		if (Objects.isNull(this.rollingdays)) {
			return 0;
		}
		return this.rollingdays;
	}

	public Status getStatus() {
		return this.status;
	}

	public BigDecimal getThresholdlimit() {
		if (Objects.isNull(this.thresholdlimit)) {
			return BigDecimal.ZERO;
		}
		return this.thresholdlimit;
	}

	public Integer getTomonth() {
		if (Objects.isNull(this.tomonth)) {
			return 1;
		}
		return this.tomonth;
	}

	public Date getTotime() {
		return this.totime;
	}

	public Integer getTransporter() {
		return this.transporter;
	}

	public User getUpdatedby() {
		return this.updatedby;
	}

	public Date getUpdatedon() {
		return this.updatedon;
	}

	public Integer getVehicleid() {
		return this.vehicleid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.id != null) ? this.id.hashCode() : 0);
		return hash;
	}

	public void setAlarmdelayinput(final BigDecimal alarmdelayinput) {
		this.alarmdelayinput = alarmdelayinput;
	}

	public void setAlarmdelayinput(final BigDecimal alarmdelayinput, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(alarmdelayinput)) {
				Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log, alarmdelayinput.toString() + "",
						"Alarm Delay Imput");
			}
		} else if (!Objects.isNull(alarmdelayinput) && !Objects.isNull(this.alarmdelayinput)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, this.alarmdelayinput.toString(),
					alarmdelayinput.toString() + "", "Alarm Delay Imput");
		} else if (!Objects.isNull(alarmdelayinput)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", alarmdelayinput.toString() + "",
					"Alarm Delay Imput");
		}
		this.alarmdelayinput = alarmdelayinput;
	}

	public void setAlarmthreshold(final BigDecimal alarmthreshold) {
		this.alarmthreshold = alarmthreshold;
	}

	public void setAlarmthreshold(final BigDecimal alarmthreshold, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(alarmthreshold)) {
				Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log, alarmthreshold.toString() + "",
						"Alarm Thresold");
			}
		} else if (!Objects.isNull(alarmthreshold) && !Objects.isNull(this.alarmthreshold)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, this.alarmthreshold.toString(),
					alarmthreshold.toString() + "", "Alarm Thresold");
		} else if (!Objects.isNull(alarmthreshold)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", alarmthreshold.toString() + "",
					"Alarm Thresold");
		}
		this.alarmthreshold = alarmthreshold;
	}

	public void setAlarmthresholdperc(final BigDecimal alarmthresholdperc) {
		this.alarmthresholdperc = alarmthresholdperc;
	}

	public void setAlarmthresholdperc(final BigDecimal alarmthresholdperc, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(alarmthresholdperc)) {
				Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log, alarmthresholdperc.toString() + "",
						"Alarm Thresold");
			}
		} else if (!Objects.isNull(alarmthresholdperc) && !Objects.isNull(this.alarmthresholdperc)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, this.alarmthresholdperc.toString(),
					alarmthresholdperc.toString() + "", "Alarm Thresold");
		} else if (!Objects.isNull(alarmthresholdperc)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", alarmthresholdperc.toString() + "",
					"Alarm Thresold");
		}
		this.alarmthresholdperc = alarmthresholdperc;
	}

	public void setAlertdelayinput(final BigDecimal alertdelayinput) {
		this.alertdelayinput = alertdelayinput;
	}

	public void setAlertdelayinput(final BigDecimal alertdelayinput, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(alertdelayinput)) {
				Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log, alertdelayinput.toString() + "",
						"Alert Delay Imput");
			}
		} else if (!Objects.isNull(alertdelayinput) && !Objects.isNull(this.alertdelayinput)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, this.alertdelayinput.toString(),
					alertdelayinput.toString() + "", "Alert Delay Imput");
		} else if (!Objects.isNull(alertdelayinput)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", alertdelayinput.toString() + "",
					"Alert Delay Imput");
		}
		this.alertdelayinput = alertdelayinput;
	}

	public void setAlertthreshold(final BigDecimal alertthreshold) {
		this.alertthreshold = alertthreshold;
	}

	public void setAlertthreshold(final BigDecimal alertthreshold, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(alertthreshold)) {
				Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log, alertthreshold.toString() + "",
						"Alert Thresold");
			}
		} else if (!Objects.isNull(alertthreshold) && !Objects.isNull(this.alertthreshold)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, this.alertthreshold.toString(),
					alertthreshold.toString() + "", "Alert Thresold");
		} else if (!Objects.isNull(alertthreshold)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", alertthreshold.toString() + "",
					"Alert Thresold");
		}
		this.alertthreshold = alertthreshold;
	}

	public void setAlertthresholdperc(final BigDecimal alertthresholdperc) {
		this.alertthresholdperc = alertthresholdperc;
	}

	public void setAlertthresholdperc(final BigDecimal alertthresholdperc, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(alertthresholdperc)) {
				Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log, alertthresholdperc.toString() + "",
						"Alert Thresold");
			}
		} else if (!Objects.isNull(alertthresholdperc) && !Objects.isNull(this.alertthresholdperc)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, this.alertthresholdperc.toString(),
					alertthresholdperc.toString() + "", "Alert Thresold");
		} else if (!Objects.isNull(alertthresholdperc)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", alertthresholdperc.toString() + "",
					"Alert Thresold");
		}
		this.alertthresholdperc = alertthresholdperc;
	}

	public void setClientaffiliateid(final Customeraffiliate clientaffiliateid) {
		if (!Objects.isNull(clientaffiliateid)) {
			this.clientaffiliateid = clientaffiliateid;
		}
	}

	public void setClientaffiliateid(final Customeraffiliate clientaffiliateid, final boolean createorupdate) {
		if (!Objects.isNull(this.customerid)) {
			if (createorupdate) {
				if (!Objects.isNull(clientaffiliateid)) {
					Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log, clientaffiliateid.getName() + "",
							"Affiliate");
				}
			} else if (!Objects.isNull(clientaffiliateid) && !Objects.isNull(this.clientaffiliateid)) {
				Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, this.clientaffiliateid.getName() + "",
						clientaffiliateid.getName() + "", "Affiliate");
			} else if (!Objects.isNull(clientaffiliateid)) {
				Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", clientaffiliateid.getName() + "",
						"Affiliate");
			}
			this.clientaffiliateid = clientaffiliateid;
		}
	}

	public void setCreatedby(final User createdby) {
		this.createdby = createdby;
	}

	public void setCreatedon(final Date createdon) {
		this.createdon = createdon;
	}

	public void setCustomerid(final Customer customerid) {
		if (!Objects.isNull(customerid)) {
			this.customerid = customerid;
		}
	}

	public void setCustomerid(final Customer customerid, final boolean createorupdate) {
		if (!Objects.isNull(customerid)) {
			if (createorupdate) {
				if (!Objects.isNull(customerid)) {
					Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log, customerid.getName() + "",
							"Customer");
				}
			} else if (!Objects.isNull(customerid) && !Objects.isNull(this.customerid)) {
				Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, this.customerid.getName() + "",
						customerid.getName() + "", "Customer");
			} else if (!Objects.isNull(customerid)) {
				Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", customerid.getName() + "",
						"Customer");
			}
			this.customerid = customerid;
		}
	}

	public void setDefaultlevelid(final Exceptionlevel defaultlevelid) {
		this.defaultlevelid = defaultlevelid;
	}

	public void setDriverid(final Driver driverid, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(driverid)) {
				Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log, driverid.getName() + "", "Transporter");
			}
		} else if (!Objects.isNull(driverid) && !Objects.isNull(this.driverid)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, this.driverid + "",
					driverid.getName() + "", "Driver");
		} else if (!Objects.isNull(driverid)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", driverid.getName() + "", "Driver");
		}
		this.driverid = driverid.getDriverid();
	}

	public void setDriverid(final Integer driverid) {
		this.driverid = driverid;
	}

	public void setFrommonth(final Integer frommonth) {
		this.frommonth = frommonth;
	}

	public void setFromtime(final Date fromtime) {
		this.fromtime = fromtime;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setMinimumdistance(final BigDecimal minimumdistance) {
		this.minimumdistance = minimumdistance;
	}

	public void setMinimumdistance(final BigDecimal minimumdistance, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(minimumdistance)) {
				Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log, minimumdistance.toString() + "",
						"Minimun Distance");
			}
		} else if (!Objects.isNull(minimumdistance) && !Objects.isNull(this.minimumdistance)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, this.minimumdistance.toString(),
					minimumdistance.toString() + "", "Minimun Distance");
		} else if (!Objects.isNull(minimumdistance)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", minimumdistance.toString() + "",
					"Minimun Distance");
		}
		this.minimumdistance = minimumdistance;
	}

	public void setParametertypeid(final Parametertype parametertypeid) {
		this.parametertypeid = parametertypeid;
	}

	public void setParametertypeid(final Parametertype parametertypeid, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(parametertypeid)) {
				Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log, parametertypeid.getName() + "",
						"Parameter Type");
			}
		} else if (!Objects.isNull(parametertypeid) && !Objects.isNull(this.parametertypeid)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, this.parametertypeid.getName() + "",
					parametertypeid.getName() + "", "Parameter Type");
		} else if (!Objects.isNull(parametertypeid)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", parametertypeid.getName() + "",
					"Parameter Type");
		}
		this.parametertypeid = parametertypeid;
	}

	public void setPrevthreshold(final BigDecimal prevthreshold) {
		this.prevthreshold = prevthreshold;
	}

	public void setPrevthreshold(final BigDecimal prevthreshold, final Boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(prevthreshold)) {
				Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log, prevthreshold + "",
						"Preventive Treshold");
			}
		} else if (!Objects.isNull(prevthreshold) && !Objects.isNull(this.driverid)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, this.prevthreshold + "",
					prevthreshold + "", "Preventive Treshold");
		} else if (!Objects.isNull(prevthreshold)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", prevthreshold + "",
					"Preventive Treshold");
		}
		this.prevthreshold = prevthreshold;
	}

	public void setRecordingdelayinput(final BigDecimal recordingdelayinput) {
		this.recordingdelayinput = recordingdelayinput;
	}

	public void setRecordingdelayinput(final BigDecimal recordingdelayinput, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(recordingdelayinput)) {
				Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log,
						String.valueOf(recordingdelayinput.setScale(5, RoundingMode.CEILING)) + "",
						"Record Delay Imput");
			}
		} else if (!Objects.isNull(recordingdelayinput) && !Objects.isNull(this.recordingdelayinput)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log,
					String.valueOf(this.recordingdelayinput.setScale(5, RoundingMode.CEILING)),
					String.valueOf(recordingdelayinput.setScale(5, RoundingMode.CEILING)) + "", "Record Delay Imput");
		} else if (!Objects.isNull(recordingdelayinput)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "",
					String.valueOf(recordingdelayinput.setScale(5, RoundingMode.CEILING)) + "", "Record Delay Imput");
		}
		this.recordingdelayinput = recordingdelayinput;
	}

	public void setRecordingthreshold(final BigDecimal recordingthreshold) {
		this.recordingthreshold = recordingthreshold;
	}

	public void setRecordingthreshold(final BigDecimal recordingthreshold, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(recordingthreshold)) {
				Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log, recordingthreshold.toString() + "",
						"Record Thresold");
			}
		} else if (!Objects.isNull(recordingthreshold) && !Objects.isNull(this.recordingthreshold)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, this.recordingthreshold.toString(),
					recordingthreshold.toString() + "", "Record Thresold");
		} else if (!Objects.isNull(recordingthreshold)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", recordingthreshold.toString() + "",
					"Record Thresold");
		}
		this.recordingthreshold = recordingthreshold;
	}

	public void setRecordingthresholdperc(final BigDecimal recordingthresholdperc) {
		this.recordingthresholdperc = recordingthresholdperc;
	}

	public void setRecordingthresholdperc(final BigDecimal recordingthresholdperc, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(recordingthresholdperc)) {
				Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log, recordingthresholdperc.toString() + "",
						"Record Thresold percent");
			}
		} else if (!Objects.isNull(recordingthresholdperc) && !Objects.isNull(this.recordingthresholdperc)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, this.recordingthresholdperc.toString(),
					recordingthresholdperc.toString() + "", "Record Thresold percent");
		} else if (!Objects.isNull(recordingthresholdperc)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", recordingthresholdperc.toString() + "",
					"Record Thresold percent");
		}
		this.recordingthresholdperc = recordingthresholdperc;
	}

	public void setRequiredresttime(final BigDecimal requiredresttime) {
		this.requiredresttime = requiredresttime;
	}

	public void setRequiredresttime(final BigDecimal requiredresttime, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(requiredresttime)) {
				Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log, requiredresttime.toString() + "",
						"Require Rest Time");
			}
		} else if (!Objects.isNull(requiredresttime) && !Objects.isNull(this.requiredresttime)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, this.requiredresttime.toString(),
					requiredresttime.toString() + "", "Require Rest Time");
		} else if (!Objects.isNull(requiredresttime)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", requiredresttime.toString() + "",
					"Require Rest Time");
		}
		this.requiredresttime = requiredresttime;
	}

	public void setRollingdays(final Integer rollingdays) {
		this.rollingdays = rollingdays;
	}

	public void setRollingdays(final Integer rollingdays, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(rollingdays)) {
				Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log, rollingdays + "", "Rolling Days");
			}
		} else if (!Objects.isNull(rollingdays) && !Objects.isNull(this.rollingdays)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, this.rollingdays + "", rollingdays + "",
					"Rolling Days");
		} else if (!Objects.isNull(rollingdays)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", rollingdays + "", "Rolling Days");
		}
		this.rollingdays = rollingdays;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	public void setStatus(final Status status, final boolean createorupdate) {
		Integer stats = 2;
		if (createorupdate) {
			if (!Objects.isNull(status)) {
				Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log, "true", "Activate Parameter: ");
			}
		} else if (!Objects.isNull(status) && !Objects.isNull(this.status)) {
			stats = status.getStatusid();
			if (stats == 1) {
				stats = this.status.getStatusid();
				if (stats != 1) {
					Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "false", "true",
							" Activate Parameter: ");
				}
			} else {
				stats = this.status.getStatusid();
				if (stats == 1) {
					Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "true", "false",
							" Activate Parameter: ");
				}
			}
		} else if (!Objects.isNull(status)) {
			stats = status.getStatusid();
			if (stats == 1) {
				Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", "true", "Activate Parameter: ");
			} else {
				Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", "true", "Activate Parameter: ");
			}
		}
		this.status = status;
	}

	public void setThresholdlimit(final BigDecimal thresholdlimit) {
		this.thresholdlimit = thresholdlimit;
	}

	public void setThresholdlimit(final BigDecimal thresholdlimit, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(thresholdlimit)) {
				Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log, thresholdlimit.toString() + "",
						"Thresold Limit");
			}
		} else if (!Objects.isNull(thresholdlimit) && !Objects.isNull(this.thresholdlimit)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, this.thresholdlimit.toString(),
					thresholdlimit.toString() + "", " Thresold Limit");
		} else if (!Objects.isNull(thresholdlimit)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", thresholdlimit.toString() + "",
					" Thresold Limit");
		}
		this.thresholdlimit = thresholdlimit;
	}

	public void setTomonth(final Integer tomonth) {
		this.tomonth = tomonth;
	}

	public void setTotime(final Date totime) {
		this.totime = totime;
	}

	public void setTransporter(final Integer transporter) {
		this.transporter = transporter;
	}

	public void setTransporter(final Transporter transporter, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(transporter)) {
				Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log, transporter.getName() + "",
						"Transporter");
			}
		} else if (!Objects.isNull(transporter) && !Objects.isNull(this.transporter)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, this.transporter + "",
					transporter.getName() + "", "Transporter");
		} else if (!Objects.isNull(transporter)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", transporter.getName() + "",
					"Transporter");
		}
		this.transporter = transporter.getTransporterid();
	}

	public void setUpdatedby(final User updatedby) {
		this.updatedby = updatedby;
	}

	public void setUpdatedon(final Date updatedon) {
		this.updatedon = updatedon;
	}

	public void setVehicleid(final Integer vehicleid) {
		this.vehicleid = vehicleid;
	}

	public void setVehicleid(final Vehicle vehicleid, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(vehicleid)) {
				Parameterconfig.log = Utils.createnewlogs(Parameterconfig.log, vehicleid.getVehicledesc() + "",
						"Vehicle");
			}
		} else if (!Objects.isNull(vehicleid) && !Objects.isNull(this.vehicleid)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, this.vehicleid + "",
					vehicleid.getVehicledesc() + "", "Vehicle");
		} else if (!Objects.isNull(vehicleid)) {
			Parameterconfig.log = Utils.updatelogsinfos(Parameterconfig.log, "", vehicleid.getVehicledesc() + "",
					"Vehicle");
		}
		this.vehicleid = vehicleid.getVehicleid();
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Parameterconfig[ id=" + this.id + " ]";
	}
}
