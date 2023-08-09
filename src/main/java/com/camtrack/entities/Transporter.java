//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.camtrack.config.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "transporter")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Transporter.findAll", query = "SELECT t FROM Transporter t"),
		@NamedQuery(name = "Transporter.findByTransporterid", query = "SELECT t FROM Transporter t WHERE t.transporterid = :transporterid"),
		@NamedQuery(name = "Transporter.findByName", query = "SELECT t FROM Transporter t WHERE t.name = :name"),
		@NamedQuery(name = "Transporter.findByCountryid", query = "SELECT t FROM Transporter t WHERE t.countryid = :countryid"),
		@NamedQuery(name = "Transporter.findByStatus", query = "SELECT t FROM Transporter t WHERE t.status = :status"),
		@NamedQuery(name = "Transporter.findByTransportuniqueid", query = "SELECT t FROM Transporter t WHERE t.transportuniqueid = :transportuniqueid"),
		@NamedQuery(name = "Transporter.findByCreatedon", query = "SELECT t FROM Transporter t WHERE t.createdon = :createdon") })
public class Transporter implements Serializable {
	public static String log;
	private static final long serialVersionUID = 1L;
	static {
		Transporter.log = "";
	}

	public static void init(final boolean createorupdate, final User user) {
		Transporter.log = Utils.EntetesLog(createorupdate, user.getUsername(), "Transporter");
	}

	@JoinColumn(name = "affiliateid", referencedColumnName = "affiliateid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Customeraffiliate affiliateid;
	@JsonIgnore
	@Column(name = "countryid")
	private Integer countryid;
	@JsonIgnore
	@Column(name = "createdon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdon;
	@JsonIgnore
	@OneToMany(mappedBy = "transporterid", fetch = FetchType.LAZY)
	private List<Driveractivitysummary> driveractivitysummaryList;
	@JsonIgnore
	@OneToMany(mappedBy = "transporterid", fetch = FetchType.LAZY)
	private List<Driver> driverList;
	@JsonIgnore
	@OneToMany(mappedBy = "transporterid", fetch = FetchType.LAZY)
	private List<Emailconfig> emailconfigList;
	@JsonIgnore
	@OneToMany(mappedBy = "transporterid", fetch = FetchType.LAZY)
	private List<Exception> exceptionList;
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "transporterid", fetch = FetchType.LAZY)
	private List<Invalidateexception> invalidateexceptionList;
	@Column(name = "name")
	private String name;
	@JsonIgnore
	@OneToMany(mappedBy = "transid", fetch = FetchType.LAZY)
	private List<Reportautomatics> reportautomaticsList;
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "transporter", fetch = FetchType.LAZY)
	private List<Resumexceptiontransporter> resumexceptiontransporterList;
	@JsonIgnore
	@Column(name = "status")
	private Integer status;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "transporterid")
	private Integer transporterid;
	@JsonIgnore
	@Column(name = "transportuniqueid")
	private String transportuniqueid;
	@JsonIgnore
	@OneToMany(mappedBy = "transporterid", fetch = FetchType.LAZY)
	private List<Userights> userightsList;
	@JsonIgnore
	@OneToMany(mappedBy = "transporterid", fetch = FetchType.LAZY)
	private List<Vehicleactivitysummary> vehicleactivitysummaryList;

	@JsonIgnore
	@OneToMany(mappedBy = "transporterid", fetch = FetchType.LAZY)
	private List<Vehicle> vehicleList;

	@JsonIgnore
	@OneToMany(mappedBy = "transporterid", fetch = FetchType.LAZY)
	private List<Vehicletrips> vehicletripsList;

	public Transporter() {
	}

	public Transporter(final Integer transporterid) {
		this.transporterid = transporterid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Transporter)) {
			return false;
		}
		final Transporter other = (Transporter) object;
		return (this.transporterid != null || other.transporterid == null)
				&& (this.transporterid == null || this.transporterid.equals(other.transporterid));
	}

	public Customeraffiliate getAffiliateid() {
		return this.affiliateid;
	}

	public Integer getCountryid() {
		return this.countryid;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public List<Driveractivitysummary> getDriveractivitysummaryList() {
		return this.driveractivitysummaryList;
	}

	@XmlTransient
	public List<Driver> getDriverList() {
		return this.driverList;
	}

	public List<Emailconfig> getEmailconfigList() {
		return this.emailconfigList;
	}

	public List<Exception> getExceptionList() {
		return this.exceptionList;
	}

	public List<Invalidateexception> getInvalidateexceptionList() {
		return this.invalidateexceptionList;
	}

	public String getName() {
		return this.name;
	}

	public List<Reportautomatics> getReportautomaticsList() {
		return this.reportautomaticsList;
	}

	public List<Resumexceptiontransporter> getResumexceptiontransporterList() {
		return this.resumexceptiontransporterList;
	}

	public Integer getStatus() {
		return this.status;
	}

	public Integer getTransporterid() {
		return this.transporterid;
	}

	public String getTransportuniqueid() {
		return this.transportuniqueid;
	}

	public List<Userights> getUserightsList() {
		return this.userightsList;
	}

	public List<Vehicleactivitysummary> getVehicleactivitysummaryList() {
		return this.vehicleactivitysummaryList;
	}

	@XmlTransient
	public List<Vehicle> getVehicleList() {
		return this.vehicleList;
	}

	public List<Vehicletrips> getVehicletripsList() {
		return this.vehicletripsList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.transporterid != null) ? this.transporterid.hashCode() : 0);
		return hash;
	}

	public void setAffiliateid(final Customeraffiliate affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setCountryid(final Integer countryid) {
		this.countryid = countryid;
	}

	public void setCreatedon(final Date createdon) {
		this.createdon = createdon;
	}

	public void setDriveractivitysummaryList(final List<Driveractivitysummary> driveractivitysummaryList) {
		this.driveractivitysummaryList = driveractivitysummaryList;
	}

	public void setDriverList(final List<Driver> driverList) {
		this.driverList = driverList;
	}

	public void setEmailconfigList(final List<Emailconfig> emailconfigList) {
		this.emailconfigList = emailconfigList;
	}

	public void setExceptionList(final List<Exception> exceptionList) {
		this.exceptionList = exceptionList;
	}

	public void setInvalidateexceptionList(final List<Invalidateexception> invalidateexceptionList) {
		this.invalidateexceptionList = invalidateexceptionList;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setName(final String name, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(name)) {
				Transporter.log = Utils.createnewlogs(Transporter.log, name + "", "Transporter name");
			}
		} else if (!Objects.isNull(name) && !Objects.isNull(this.name)) {
			Transporter.log = Utils.updatelogsinfos(Transporter.log, this.name + "", name + "", "Transporter name");
		} else if (!Objects.isNull(name)) {
			Transporter.log = Utils.updatelogsinfos(Transporter.log, "", name + "", "Transporter name");
		}
		this.name = name;
	}

	public void setReportautomaticsList(final List<Reportautomatics> reportautomaticsList) {
		this.reportautomaticsList = reportautomaticsList;
	}

	public void setResumexceptiontransporterList(final List<Resumexceptiontransporter> resumexceptiontransporterList) {
		this.resumexceptiontransporterList = resumexceptiontransporterList;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public void setStatus(final Integer status, final boolean createorupdate) {
		Integer stats = 2;
		if (createorupdate) {
			if (!Objects.isNull(status)) {
				Transporter.log = Utils.createnewlogs(Transporter.log, "true", "Activate Transporter: ");
			}
		} else if (!Objects.isNull(status) && !Objects.isNull(this.status)) {
			if (status == 1) {
				stats = this.status;
				if (stats != 1) {
					Transporter.log = Utils.updatelogsinfos(Transporter.log, "false", "true",
							" Activate Transporter: ");
				}
			} else {
				stats = this.status;
				if (stats == 1) {
					Transporter.log = Utils.updatelogsinfos(Transporter.log, "true", "false",
							" Activate Transporter: ");
				}
			}
		} else if (!Objects.isNull(status)) {
			if (status == 1) {
				Transporter.log = Utils.updatelogsinfos(Transporter.log, "", "true", "Activate Transporter: ");
			} else {
				Transporter.log = Utils.updatelogsinfos(Transporter.log, "", "true", "Activate Transporter: ");
			}
		}
		this.status = status;
	}

	public void setTransporterid(final Integer transporterid) {
		this.transporterid = transporterid;
	}

	public void setTransportuniqueid(final String transportuniqueid) {
		this.transportuniqueid = transportuniqueid;
	}

	public void setUserightsList(final List<Userights> userightsList) {
		this.userightsList = userightsList;
	}

	public void setVehicleactivitysummaryList(final List<Vehicleactivitysummary> vehicleactivitysummaryList) {
		this.vehicleactivitysummaryList = vehicleactivitysummaryList;
	}

	public void setVehicleList(final List<Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}

	public void setVehicletripsList(final List<Vehicletrips> vehicletripsList) {
		this.vehicletripsList = vehicletripsList;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Transporter[ transporterid=" + this.transporterid + " ]";
	}
}
