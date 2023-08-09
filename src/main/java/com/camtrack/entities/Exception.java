//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "exception")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Exception.findAll", query = "SELECT e FROM Exception e"),
		@NamedQuery(name = "Exception.findByExceptionid", query = "SELECT e FROM Exception e WHERE e.exceptionid = :exceptionid"),
		@NamedQuery(name = "Exception.findByAffiliateid", query = "SELECT e FROM Exception e WHERE e.affiliateid = :affiliateid"),
		@NamedQuery(name = "Exception.findByTransporterid", query = "SELECT e FROM Exception e WHERE e.transporterid = :transporterid"),
		@NamedQuery(name = "Exception.findByDriverid", query = "SELECT e FROM Exception e WHERE e.driverid = :driverid"),
		@NamedQuery(name = "Exception.findByStartdatetime", query = "SELECT e FROM Exception e WHERE e.startdatetime = :startdatetime"),
		@NamedQuery(name = "Exception.findByEnddatetime", query = "SELECT e FROM Exception e WHERE e.enddatetime = :enddatetime"),
		@NamedQuery(name = "Exception.findByTotalduration", query = "SELECT e FROM Exception e WHERE e.totalduration = :totalduration"),
		@NamedQuery(name = "Exception.findByTotaldistance", query = "SELECT e FROM Exception e WHERE e.totaldistance = :totaldistance"),
		@NamedQuery(name = "Exception.findByLevel", query = "SELECT e FROM Exception e WHERE e.level = :level"),
		@NamedQuery(name = "Exception.findByThreshold", query = "SELECT e FROM Exception e WHERE e.threshold = :threshold"),
		@NamedQuery(name = "Exception.findByMaxvalue", query = "SELECT e FROM Exception e WHERE e.maxvalue = :maxvalue"),
		@NamedQuery(name = "Exception.findByDistanceunderexception", query = "SELECT e FROM Exception e WHERE e.distanceunderexception = :distanceunderexception"),
		@NamedQuery(name = "Exception.findByTimeexceeded", query = "SELECT e FROM Exception e WHERE e.timeexceeded = :timeexceeded"),
		@NamedQuery(name = "Exception.findByRequiredbreak", query = "SELECT e FROM Exception e WHERE e.requiredbreak = :requiredbreak"),
		@NamedQuery(name = "Exception.findByNumberofbreak", query = "SELECT e FROM Exception e WHERE e.numberofbreak = :numberofbreak"),
		@NamedQuery(name = "Exception.findByMaxbreak", query = "SELECT e FROM Exception e WHERE e.maxbreak = :maxbreak"),
		@NamedQuery(name = "Exception.findByStartgps", query = "SELECT e FROM Exception e WHERE e.startgps = :startgps"),
		@NamedQuery(name = "Exception.findByEndgps", query = "SELECT e FROM Exception e WHERE e.endgps = :endgps"),
		@NamedQuery(name = "Exception.findByVmax", query = "SELECT e FROM Exception e WHERE e.vmax = :vmax"),
		@NamedQuery(name = "Exception.findBySpeedid", query = "SELECT e FROM Exception e WHERE e.speedid = :speedid"),
		@NamedQuery(name = "Exception.findByInvaliddata", query = "SELECT e FROM Exception e WHERE e.invaliddata = :invaliddata"),
		@NamedQuery(name = "Exception.findByUnitid", query = "SELECT e FROM Exception e WHERE e.unitid = :unitid") })
public class Exception implements Serializable {
	public static String log;
	private static final long serialVersionUID = 1L;
	static {
		Exception.log = "";
	}

	public static void init(final User user) {
		Exception.log = Utils.InvalidatedException(user.getUsername(), "Reactivate An Invalidated Exception ");
	}

	@JoinColumn(name = "affiliateid", referencedColumnName = "affiliateid")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Customeraffiliate affiliateid;
	@Column(name = "distanceunderexception")
	private BigDecimal distanceunderexception;
	@JoinColumn(name = "driverid", referencedColumnName = "driverid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Driver driverid;
	@Column(name = "enddatetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date enddatetime;
	@Column(name = "endgps")
	private String endgps;
	@Column(name = "evidence")
	private String evidence;
	@Column(name = "exceptionday")
	@Temporal(TemporalType.DATE)
	private Date exceptionday;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "exceptionid")
	private Integer exceptionid;
	@JoinColumn(name = "exceptiontype", referencedColumnName = "parametertypeid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Parametertype exceptiontype;
	@Column(name = "gdistanceunderexception")
	private BigDecimal gdistanceunderexception;
	@Column(name = "genddatetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date genddatetime;
	@Column(name = "gendgps")
	private String gendgps;
	@Column(name = "ginvaliddata")
	private Integer ginvaliddata;
	@JoinColumn(name = "glevel", referencedColumnName = "exceptionlevelid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Exceptionlevel glevel;
	@Column(name = "gmaxbreak")
	private BigDecimal gmaxbreak;
	@Column(name = "gmaxval")
	private BigDecimal gmaxval;
	@Column(name = "gnumberofbreak")
	private BigDecimal gnumberofbreak;
	@Column(name = "gpreventivedatetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date gpreventivedatetime;
	@Column(name = "gpreventivethreshold")
	private BigDecimal gpreventivethreshold;
	@Column(name = "grequiredbreak")
	private BigDecimal grequiredbreak;
	@Column(name = "gstartdatetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date gstartdatetime;
	@Column(name = "gstartgps")
	private String gstartgps;
	@JoinColumn(name = "gstatus", referencedColumnName = "statusid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Status gstatus;
	@Column(name = "gthreshold")
	private Long gthreshold;
	@Column(name = "gtimeexceeded")
	private BigDecimal gtimeexceeded;
	@Column(name = "gtotaldistance")
	private BigDecimal gtotaldistance;
	@Column(name = "gtotalduration")
	private BigDecimal gtotalduration;
	@Column(name = "gvmax")
	private BigDecimal gvmax;
	@Column(name = "invaliddata")
	private Integer invaliddata;
	@Column(name = "isrestored")
	private Short isrestored;
	@JoinColumn(name = "level", referencedColumnName = "exceptionlevelid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Exceptionlevel level;
	@Column(name = "maxbreak")
	private BigDecimal maxbreak;
	@Column(name = "maxvalue")
	private BigDecimal maxvalue;
	@Column(name = "mergetreated")
	private Integer mergetreated;
	@Column(name = "numberofbreak")
	private BigDecimal numberofbreak;
	@Column(name = "preventivedatetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date preventivedatetime;
	@Column(name = "preventivethreshold")
	private BigDecimal preventivethreshold;
	@Column(name = "requiredbreak")
	private BigDecimal requiredbreak;
	@Column(name = "restauredate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date restauredate;
	@Column(name = "restoreduserid")
	private Integer restoreduserid;
	@Column(name = "speedid")
	private String speedid;
	@Column(name = "startdatetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startdatetime;
	@Column(name = "startgps")
	private String startgps;
	@JoinColumn(name = "status", referencedColumnName = "statusid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Status status;
	@Column(name = "threshold")
	private BigDecimal threshold;
	@Column(name = "timeexceeded")
	private BigDecimal timeexceeded;
	@Column(name = "totaldistance")
	private BigDecimal totaldistance;
	@Column(name = "totalduration")
	private BigDecimal totalduration;
	@JoinColumn(name = "transporterid", referencedColumnName = "transporterid")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Transporter transporterid;
	@Column(name = "treated")
	private Integer treated;
	@Column(name = "unitid")
	private String unitid;
	@Column(name = "usercoments")
	private String usercoments;

	@JoinColumn(name = "vehicleid", referencedColumnName = "vehicleid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Vehicle vehicleid;

	@Column(name = "vmax")
	private BigDecimal vmax;

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Exception)) {
			return false;
		}
		final Exception other = (Exception) object;
		return (this.exceptionid != null || other.exceptionid == null)
				&& (this.exceptionid == null || this.exceptionid.equals(other.exceptionid));
	}

	public Customeraffiliate getAffiliateid() {
		if (!Objects.isNull(this.affiliateid)) {
			Exception.log = Utils.createnewlogs(Exception.log, this.affiliateid.getName() + "", "Affiliate ");
		}
		return this.affiliateid;
	}

	public BigDecimal getDistanceunderexception() {
		return this.distanceunderexception;
	}

	public Driver getDriverid() {
		if (!Objects.isNull(this.driverid)) {
			Exception.log = Utils.createnewlogs(Exception.log, this.driverid.getName() + "", "Driver ");
		}
		return this.driverid;
	}

	public Date getEnddatetime() {
		return this.enddatetime;
	}

	public String getEndgps() {
		return this.endgps;
	}

	public String getEvidence() {
		return this.evidence;
	}

	public Date getExceptionday() {
		return this.exceptionday;
	}

	public Integer getExceptionid() {
		return this.exceptionid;
	}

	public Parametertype getExceptiontype() {
		if (!Objects.isNull(this.exceptiontype)) {
			Exception.log = Utils.createnewlogs(Exception.log, this.exceptiontype.getName() + "", "Parameter ");
		}
		return this.exceptiontype;
	}

	public BigDecimal getGdistanceunderexception() {
		return this.gdistanceunderexception;
	}

	public Date getGenddatetime() {
		return this.genddatetime;
	}

	public String getGendgps() {
		return this.gendgps;
	}

	public Integer getGinvaliddata() {
		return this.ginvaliddata;
	}

	public Exceptionlevel getGlevel() {
		return this.glevel;
	}

	public BigDecimal getGmaxbreak() {
		return this.gmaxbreak;
	}

	public BigDecimal getGmaxval() {
		return this.gmaxval;
	}

	public BigDecimal getGnumberofbreak() {
		return this.gnumberofbreak;
	}

	public Date getGpreventivedatetime() {
		return this.gpreventivedatetime;
	}

	public BigDecimal getGpreventivethreshold() {
		return this.gpreventivethreshold;
	}

	public BigDecimal getGrequiredbreak() {
		return this.grequiredbreak;
	}

	public Date getGstartdatetime() {
		return this.gstartdatetime;
	}

	public String getGstartgps() {
		return this.gstartgps;
	}

	public Status getGstatus() {
		return this.gstatus;
	}

	public Long getGthreshold() {
		return this.gthreshold;
	}

	public BigDecimal getGtimeexceeded() {
		return this.gtimeexceeded;
	}

	public BigDecimal getGtotaldistance() {
		return this.gtotaldistance;
	}

	public BigDecimal getGtotalduration() {
		return this.gtotalduration;
	}

	public BigDecimal getGvmax() {
		return this.gvmax;
	}

	public Integer getInvaliddata() {
		return this.invaliddata;
	}

	public Short getIsrestored() {
		return this.isrestored;
	}

	public Exceptionlevel getLevel() {
		if (!Objects.isNull(this.level)) {
			Exception.log = Utils.createnewlogs(Exception.log, this.level.getName() + "", "Exception Level ");
		}
		return this.level;
	}

	public BigDecimal getMaxbreak() {
		return this.maxbreak;
	}

	public BigDecimal getMaxvalue() {
		return this.maxvalue;
	}

	public Integer getMergetreated() {
		return this.mergetreated;
	}

	public BigDecimal getNumberofbreak() {
		return this.numberofbreak;
	}

	public Date getPreventivedatetime() {
		return this.preventivedatetime;
	}

	public BigDecimal getPreventivethreshold() {
		return this.preventivethreshold;
	}

	public BigDecimal getRequiredbreak() {
		return this.requiredbreak;
	}

	public Date getRestauredate() {
		return this.restauredate;
	}

	public Integer getRestoreduserid() {
		return this.restoreduserid;
	}

	public String getSpeedid() {
		return this.speedid;
	}

	public Date getStartdatetime() {
		return this.startdatetime;
	}

	public String getStartgps() {
		return this.startgps;
	}

	public Status getStatus() {
		return this.status;
	}

	public BigDecimal getThreshold() {
		return this.threshold;
	}

	public BigDecimal getTimeexceeded() {
		return this.timeexceeded;
	}

	public BigDecimal getTotaldistance() {
		return this.totaldistance;
	}

	public BigDecimal getTotalduration() {
		return this.totalduration;
	}

	public Transporter getTransporterid() {
		if (!Objects.isNull(this.transporterid)) {
			Exception.log = Utils.createnewlogs(Exception.log, this.transporterid.getName() + "", "Transporter ");
		}
		return this.transporterid;
	}

	public Integer getTreated() {
		return this.treated;
	}

	public String getUnitid() {
		return this.unitid;
	}

	public String getUsercoments() {
		return this.usercoments;
	}

	public Vehicle getVehicleid() {
		if (!Objects.isNull(this.vehicleid)) {
			Exception.log = Utils.createnewlogs(Exception.log, this.vehicleid.getVehicledesc() + "", "Vehicle ");
		}
		return this.vehicleid;
	}

	public BigDecimal getVmax() {
		return this.vmax;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.exceptionid != null) ? this.exceptionid.hashCode() : 0);
		return hash;
	}

	public void setAffiliateid(final Customeraffiliate affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setDistanceunderexception(final BigDecimal distanceunderexception) {
		this.distanceunderexception = distanceunderexception;
	}

	public void setDriverid(final Driver driverid) {
		this.driverid = driverid;
	}

	public void setEnddatetime(final Date enddatetime) {
		this.enddatetime = enddatetime;
	}

	public void setEndgps(final String endgps) {
		this.endgps = endgps;
	}

	public void setEvidence(final String evidence) {
		this.evidence = evidence;
	}

	public void setExceptionday(final Date exceptionday) {
		this.exceptionday = exceptionday;
	}

	public void setExceptionid(final Integer exceptionid) {
		this.exceptionid = exceptionid;
	}

	public void setExceptiontype(final Parametertype exceptiontype) {
		this.exceptiontype = exceptiontype;
	}

	public void setGdistanceunderexception(final BigDecimal gdistanceunderexception) {
		this.gdistanceunderexception = gdistanceunderexception;
	}

	public void setGenddatetime(final Date genddatetime) {
		this.genddatetime = genddatetime;
	}

	public void setGendgps(final String gendgps) {
		this.gendgps = gendgps;
	}

	public void setGinvaliddata(final Integer ginvaliddata) {
		this.ginvaliddata = ginvaliddata;
	}

	public void setGlevel(final Exceptionlevel glevel) {
		this.glevel = glevel;
	}

	public void setGmaxbreak(final BigDecimal gmaxbreak) {
		this.gmaxbreak = gmaxbreak;
	}

	public void setGmaxval(final BigDecimal gmaxval) {
		this.gmaxval = gmaxval;
	}

	public void setGnumberofbreak(final BigDecimal gnumberofbreak) {
		this.gnumberofbreak = gnumberofbreak;
	}

	public void setGpreventivedatetime(final Date gpreventivedatetime) {
		this.gpreventivedatetime = gpreventivedatetime;
	}

	public void setGpreventivethreshold(final BigDecimal gpreventivethreshold) {
		this.gpreventivethreshold = gpreventivethreshold;
	}

	public void setGrequiredbreak(final BigDecimal grequiredbreak) {
		this.grequiredbreak = grequiredbreak;
	}

	public void setGstartdatetime(final Date gstartdatetime) {
		this.gstartdatetime = gstartdatetime;
	}

	public void setGstartgps(final String gstartgps) {
		this.gstartgps = gstartgps;
	}

	public void setGstatus(final Status gstatus) {
		this.gstatus = gstatus;
	}

	public void setGthreshold(final Long gthreshold) {
		this.gthreshold = gthreshold;
	}

	public void setGtimeexceeded(final BigDecimal gtimeexceeded) {
		this.gtimeexceeded = gtimeexceeded;
	}

	public void setGtotaldistance(final BigDecimal gtotaldistance) {
		this.gtotaldistance = gtotaldistance;
	}

	public void setGtotalduration(final BigDecimal gtotalduration) {
		this.gtotalduration = gtotalduration;
	}

	public void setGvmax(final BigDecimal gvmax) {
		this.gvmax = gvmax;
	}

	public void setInvaliddata(final Integer invaliddata) {
		this.invaliddata = invaliddata;
	}

	public void setIsrestored(final Short isrestored) {
		this.isrestored = isrestored;
	}

	public void setLevel(final Exceptionlevel level) {
		this.level = level;
	}

	public void setMaxbreak(final BigDecimal maxbreak) {
		this.maxbreak = maxbreak;
	}

	public void setMaxvalue(final BigDecimal maxvalue) {
		this.maxvalue = maxvalue;
	}

	public void setMergetreated(final Integer mergetreated) {
		this.mergetreated = mergetreated;
	}

	public void setNumberofbreak(final BigDecimal numberofbreak) {
		this.numberofbreak = numberofbreak;
	}

	public void setPreventivedatetime(final Date preventivedatetime) {
		this.preventivedatetime = preventivedatetime;
	}

	public void setPreventivethreshold(final BigDecimal preventivethreshold) {
		this.preventivethreshold = preventivethreshold;
	}

	public void setRequiredbreak(final BigDecimal requiredbreak) {
		this.requiredbreak = requiredbreak;
	}

	public void setRestauredate(final Date restauredate) {
		this.restauredate = restauredate;
	}

	public void setRestoreduserid(final Integer restoreduserid) {
		this.restoreduserid = restoreduserid;
	}

	public void setSpeedid(final String speedid) {
		this.speedid = speedid;
	}

	public void setStartdatetime(final Date startdatetime) {
		this.startdatetime = startdatetime;
	}

	public void setStartgps(final String startgps) {
		this.startgps = startgps;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	public void setThreshold(final BigDecimal threshold) {
		this.threshold = threshold;
	}

	public void setTimeexceeded(final BigDecimal timeexceeded) {
		this.timeexceeded = timeexceeded;
	}

	public void setTotaldistance(final BigDecimal totaldistance) {
		this.totaldistance = totaldistance;
	}

	public void setTotalduration(final BigDecimal totalduration) {
		this.totalduration = totalduration;
	}

	public void setTransporterid(final Transporter transporterid) {
		this.transporterid = transporterid;
	}

	public void setTreated(final Integer treated) {
		this.treated = treated;
	}

	public void setUnitid(final String unitid) {
		this.unitid = unitid;
	}

	public void setUsercoments(final String usercoments) {
		if (!Objects.isNull(usercoments)) {
			Exception.log = Utils.createnewlogs(Exception.log, usercoments + "", "INVALIDATED COMMENTS ");
		}
		this.usercoments = usercoments;
	}

	public void setVehicleid(final Vehicle vehicleid) {
		this.vehicleid = vehicleid;
	}

	public void setVmax(final BigDecimal vmax) {
		this.vmax = vmax;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Exception[ exceptionid=" + this.exceptionid + " ]";
	}
}
