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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.camtrack.config.Utils;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "customeraffiliate")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Customeraffiliate.findAll", query = "SELECT c FROM Customeraffiliate c"),
		@NamedQuery(name = "Customeraffiliate.findByAffiliateid", query = "SELECT c FROM Customeraffiliate c WHERE c.affiliateid = :affiliateid"),
		@NamedQuery(name = "Customeraffiliate.findByName", query = "SELECT c FROM Customeraffiliate c WHERE c.name = :name"),
		@NamedQuery(name = "Customeraffiliate.findByAddressline1", query = "SELECT c FROM Customeraffiliate c WHERE c.addressline1 = :addressline1"),
		@NamedQuery(name = "Customeraffiliate.findByAddressline2", query = "SELECT c FROM Customeraffiliate c WHERE c.addressline2 = :addressline2"),
		@NamedQuery(name = "Customeraffiliate.findByCityid", query = "SELECT c FROM Customeraffiliate c WHERE c.cityid = :cityid"),
		@NamedQuery(name = "Customeraffiliate.findByStateid", query = "SELECT c FROM Customeraffiliate c WHERE c.stateid = :stateid"),
		@NamedQuery(name = "Customeraffiliate.findByCountryid", query = "SELECT c FROM Customeraffiliate c WHERE c.countryid = :countryid"),
		@NamedQuery(name = "Customeraffiliate.findByEmail", query = "SELECT c FROM Customeraffiliate c WHERE c.email = :email"),
		@NamedQuery(name = "Customeraffiliate.findByTelephone", query = "SELECT c FROM Customeraffiliate c WHERE c.telephone = :telephone"),
		@NamedQuery(name = "Customeraffiliate.findByStatus", query = "SELECT c FROM Customeraffiliate c WHERE c.status = :status"),
		@NamedQuery(name = "Customeraffiliate.findByCreatedon", query = "SELECT c FROM Customeraffiliate c WHERE c.createdon = :createdon"),
		@NamedQuery(name = "Customeraffiliate.findByCreatedby", query = "SELECT c FROM Customeraffiliate c WHERE c.createdby = :createdby"),
		@NamedQuery(name = "Customeraffiliate.findByUpdatedon", query = "SELECT c FROM Customeraffiliate c WHERE c.updatedon = :updatedon"),
		@NamedQuery(name = "Customeraffiliate.findByUpdatedby", query = "SELECT c FROM Customeraffiliate c WHERE c.updatedby = :updatedby"),
		@NamedQuery(name = "Customeraffiliate.findByGpstrackingsystem", query = "SELECT c FROM Customeraffiliate c WHERE c.gpstrackingsystem = :gpstrackingsystem"),
		@NamedQuery(name = "Customeraffiliate.findByGpssystemusername", query = "SELECT c FROM Customeraffiliate c WHERE c.gpssystemusername = :gpssystemusername"),
		@NamedQuery(name = "Customeraffiliate.findByGpssystempassword", query = "SELECT c FROM Customeraffiliate c WHERE c.gpssystempassword = :gpssystempassword"),
		@NamedQuery(name = "Customeraffiliate.findByGpstoken", query = "SELECT c FROM Customeraffiliate c WHERE c.gpstoken = :gpstoken"),
		@NamedQuery(name = "Customeraffiliate.findByAfftimezone", query = "SELECT c FROM Customeraffiliate c WHERE c.afftimezone = :afftimezone") })
public class Customeraffiliate implements Serializable {
	public static String log;
	private static final long serialVersionUID = 1L;
	static {
		Customeraffiliate.log = "";
	}

	public static String getLog() {
		return Customeraffiliate.log;
	}

	public static void init(final boolean createorupdate, final User user) {
		Customeraffiliate.log = Utils.EntetesLog(createorupdate, user.getUsername(), "Affiliate");
	}

	public static void setLog(final String log) {
		Customeraffiliate.log = log;
	}

	@Column(name = "streamingurl")
	private String streamingurl;
	@JsonIgnore
	@Column(name = "addressline1")
	private String addressline1;
	@JsonIgnore
	@Column(name = "addressline2")
	private String addressline2;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "affiliateid")
	private Integer affiliateid;
	@JsonIgnore
	@Column(name = "afftimezone")
	private String afftimezone;
	@JsonBackReference("alllertaffiliate")
	@OneToOne(mappedBy = "affiliateid", fetch = FetchType.LAZY)
	private Allalertlevel allalertlevel;
	@JsonIgnore
	@OneToMany(mappedBy = "affiliateid", fetch = FetchType.LAZY)
	private List<Allalertlevel> allalertlevelList;
	@JsonIgnore
	@JoinColumn(name = "anexeinfos", referencedColumnName = "ids")
	@ManyToOne(fetch = FetchType.LAZY)
	private Annexeaffiliateid anexeinfos;
	@JsonIgnore
	@Column(name = "cityid")
	private Integer cityid;
	@JsonIgnore
	@Column(name = "countryid")
	private Integer countryid;
	@JsonIgnore
	@Column(name = "createdby")
	private Integer createdby;
	@JsonIgnore
	@Column(name = "createdon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdon;
	@JoinColumn(name = "customerid", referencedColumnName = "customerid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Customer customerid;
	@JsonIgnore
	@OneToMany(mappedBy = "affiliateid", fetch = FetchType.LAZY)
	private List<Driveractivitysummary> driveractivitysummaryList;
	@JsonIgnore
	@OneToMany(mappedBy = "affiliateid", fetch = FetchType.LAZY)
	private List<Driver> driverList;
	@JsonIgnore
	@Column(name = "email")
	private String email;
	@JsonIgnore
	@OneToMany(mappedBy = "affiliateid", fetch = FetchType.LAZY)
	private List<Emailconfig> emailconfigList;
	@JsonIgnore
	@OneToMany(mappedBy = "affiliateid", fetch = FetchType.LAZY)
	private List<Exception> exceptionList;
	@JsonIgnore
	@Column(name = "gpssystempassword")
	private String gpssystempassword;
	@JsonIgnore
	@Column(name = "gpssystemusername")
	private String gpssystemusername;
	@JsonIgnore
	@Column(name = "gpstoken")
	private String gpstoken;
	@JsonIgnore
	@Column(name = "gpstrackingsystem")
	private String gpstrackingsystem;
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "affiliateid", fetch = FetchType.LAZY)
	private List<Invalidateexception> invalidateexceptionList;
	@Column(name = "name")
	private String name;
	@JsonIgnore
	@OneToMany(mappedBy = "clientaffiliateid", fetch = FetchType.LAZY)
	private List<Parameterconfig> parameterconfigList;
	@JsonIgnore
	@OneToMany(mappedBy = "affid", fetch = FetchType.LAZY)
	private List<Reportautomatics> reportautomaticsList;
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "customeraffiliate", fetch = FetchType.LAZY)
	private List<Resumexceptionaffiliate> resumexceptionaffiliateList;
	@JsonIgnore
	@Column(name = "stateid")
	private Integer stateid;
	@JsonIgnore
	@Column(name = "status")
	private Integer status;
	@JsonIgnore
	@Column(name = "telephone")
	private String telephone;
	@JsonIgnore
	@OneToMany(mappedBy = "affiliateid", fetch = FetchType.LAZY)
	private List<Transporter> transporterList;
	@JsonIgnore
	@Column(name = "updatedby")
	private Integer updatedby;

	@JsonIgnore
	@Column(name = "updatedon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedon;

	@JsonIgnore
	@OneToMany(mappedBy = "affiliateid", fetch = FetchType.LAZY)
	private List<Userights> userightsList;

	@JsonIgnore
	@OneToMany(mappedBy = "affiliateid", fetch = FetchType.LAZY)
	private List<Vehicleactivitysummary> vehicleactivitysummaryList;

	@JsonIgnore
	@OneToMany(mappedBy = "affiliateid", fetch = FetchType.LAZY)
	private List<Vehicletrips> vehicletripsList;

	public Customeraffiliate() {
	}

	public Customeraffiliate(final Integer affiliateid) {
		this.affiliateid = affiliateid;
	}

	public String getStreamingurl() {
		return streamingurl;
	}

	public void setStreamingurl(String streamingurl, Boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(streamingurl)) {
				Customeraffiliate.log = Utils.createnewlogs(Customeraffiliate.log, streamingurl + "", "streamingurl");
			}
		} else if (!Objects.isNull(streamingurl) && !Objects.isNull(this.streamingurl)) {
			Customeraffiliate.log = Utils.updatelogsinfos(Customeraffiliate.log, this.streamingurl + "",
					streamingurl + "", "streamingurl");
		} else if (!Objects.isNull(streamingurl)) {
			Customeraffiliate.log = Utils.updatelogsinfos(Customeraffiliate.log, "", streamingurl + "", "streamingurl");
		}
		this.streamingurl = streamingurl;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Customeraffiliate)) {
			return false;
		}
		final Customeraffiliate other = (Customeraffiliate) object;
		return (this.affiliateid != null || other.affiliateid == null)
				&& (this.affiliateid == null || this.affiliateid.equals(other.affiliateid));
	}

	public String getAddressline1() {
		return this.addressline1;
	}

	public String getAddressline2() {
		return this.addressline2;
	}

	public Integer getAffiliateid() {
		return this.affiliateid;
	}

	public String getAfftimezone() {
		return this.afftimezone;
	}

	public Allalertlevel getAllalertlevel() {
		return this.allalertlevel;
	}

	public List<Allalertlevel> getAllalertlevelList() {
		return this.allalertlevelList;
	}

	public Annexeaffiliateid getAnexeinfos() {
		return this.anexeinfos;
	}

	public Integer getCityid() {
		return this.cityid;
	}

	public Integer getCountryid() {
		return this.countryid;
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

	public List<Driveractivitysummary> getDriveractivitysummaryList() {
		return this.driveractivitysummaryList;
	}

	public List<Driver> getDriverList() {
		return this.driverList;
	}

	public String getEmail() {
		return this.email;
	}

	public List<Emailconfig> getEmailconfigList() {
		return this.emailconfigList;
	}

	public List<Exception> getExceptionList() {
		return this.exceptionList;
	}

	public String getGpssystempassword() {
		return this.gpssystempassword;
	}

	public String getGpssystemusername() {
		return this.gpssystemusername;
	}

	public String getGpstoken() {
		return this.gpstoken;
	}

	public String getGpstrackingsystem() {
		return this.gpstrackingsystem;
	}

	public List<Invalidateexception> getInvalidateexceptionList() {
		return this.invalidateexceptionList;
	}

	public String getName() {
		return this.name;
	}

	public List<Parameterconfig> getParameterconfigList() {
		return this.parameterconfigList;
	}

	public List<Reportautomatics> getReportautomaticsList() {
		return this.reportautomaticsList;
	}

	public List<Resumexceptionaffiliate> getResumexceptionaffiliateList() {
		return this.resumexceptionaffiliateList;
	}

	public Integer getStateid() {
		return this.stateid;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getTelephone() {
		return this.telephone;
	}

	@XmlTransient
	public List<Transporter> getTransporterList() {
		return this.transporterList;
	}

	public Integer getUpdatedby() {
		return this.updatedby;
	}

	public Date getUpdatedon() {
		return this.updatedon;
	}

	public List<Userights> getUserightsList() {
		return this.userightsList;
	}

	public List<Vehicleactivitysummary> getVehicleactivitysummaryList() {
		return this.vehicleactivitysummaryList;
	}

	public List<Vehicletrips> getVehicletripsList() {
		return this.vehicletripsList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.affiliateid != null) ? this.affiliateid.hashCode() : 0);
		return hash;
	}

	public void setAddressline1(final String addressline1) {
		this.addressline1 = addressline1;
	}

	public void setAddressline1(final String addressline1, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(addressline1)) {
				Customeraffiliate.log = Utils.createnewlogs(Customeraffiliate.log, addressline1 + "", "addressline1");
			}
		} else if (!Objects.isNull(addressline1) && !Objects.isNull(this.addressline1)) {
			Customeraffiliate.log = Utils.updatelogsinfos(Customeraffiliate.log, this.addressline1 + "",
					addressline1 + "", "addressline1");
		} else if (!Objects.isNull(addressline1)) {
			Customeraffiliate.log = Utils.updatelogsinfos(Customeraffiliate.log, "", addressline1 + "", "addressline1");
		}
		this.addressline1 = addressline1;
	}

	public void setAddressline2(final String addressline2) {
		this.addressline2 = addressline2;
	}

	public void setAddressline2(final String addressline2, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(addressline2)) {
				Customeraffiliate.log = Utils.createnewlogs(Customeraffiliate.log, addressline2 + "", "addressline2");
			}
		} else if (!Objects.isNull(addressline2) && !Objects.isNull(this.addressline2)) {
			Customeraffiliate.log = Utils.updatelogsinfos(Customeraffiliate.log, this.addressline2 + "",
					addressline2 + "", "addressline2");
		} else if (!Objects.isNull(addressline2)) {
			Customeraffiliate.log = Utils.updatelogsinfos(Customeraffiliate.log, "", addressline2 + "", "addressline2");
		}
		this.addressline2 = addressline2;
	}

	public void setAffiliateid(final Integer affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setAfftimezone(final String afftimezone) {
		this.afftimezone = afftimezone;
	}

	public void setAfftimezone(final String afftimezone, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(afftimezone)) {
				Customeraffiliate.log = Utils.createnewlogs(Customeraffiliate.log, afftimezone + "", "Time Zone ");
			}
		} else if (!Objects.isNull(afftimezone) && !Objects.isNull(this.afftimezone)) {
			Customeraffiliate.log = Utils.updatelogsinfos(Customeraffiliate.log, this.afftimezone + "",
					afftimezone + "", "Time Zone");
		} else if (!Objects.isNull(afftimezone)) {
			Customeraffiliate.log = Utils.updatelogsinfos(Customeraffiliate.log, "", afftimezone + "", "Time Zone");
		}
		this.afftimezone = afftimezone;
	}

	public void setAllalertlevel(final Allalertlevel allalertlevel) {
		this.allalertlevel = allalertlevel;
	}

	public void setAllalertlevelList(final List<Allalertlevel> allalertlevelList) {
		this.allalertlevelList = allalertlevelList;
	}

	public void setAnexeinfos(final Annexeaffiliateid anexeinfos) {
		this.anexeinfos = anexeinfos;
	}

	public void setCityid(final Integer cityid) {
		this.cityid = cityid;
	}

	public void setCountryid(final Integer countryid) {
		this.countryid = countryid;
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
				Customeraffiliate.log = Utils.createnewlogs(Customeraffiliate.log, customerid.getName() + "",
						"Customer ");
			}
		} else if (!Objects.isNull(customerid) && !Objects.isNull(this.customerid)) {
			Customeraffiliate.log = Utils.updatelogsinfos(Customeraffiliate.log, this.customerid.getName() + "",
					customerid.getName() + "", "Customer");
		} else if (!Objects.isNull(customerid)) {
			Customeraffiliate.log = Utils.updatelogsinfos(Customeraffiliate.log, "", customerid.getName() + "",
					"Customer");
		}
		this.customerid = customerid;
	}

	public void setDriveractivitysummaryList(final List<Driveractivitysummary> driveractivitysummaryList) {
		this.driveractivitysummaryList = driveractivitysummaryList;
	}

	public void setDriverList(final List<Driver> driverList) {
		this.driverList = driverList;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setEmail(final String email, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(email)) {
				Customeraffiliate.log = Utils.createnewlogs(Customeraffiliate.log, email + "", "email");
			}
		} else if (!Objects.isNull(email) && !Objects.isNull(this.email)) {
			Customeraffiliate.log = Utils.updatelogsinfos(Customeraffiliate.log, this.email + "", email + "", "email");
		} else if (!Objects.isNull(email)) {
			Customeraffiliate.log = Utils.updatelogsinfos(Customeraffiliate.log, "", email + "", "email");
		}
		this.email = email;
	}

	public void setEmailconfigList(final List<Emailconfig> emailconfigList) {
		this.emailconfigList = emailconfigList;
	}

	public void setExceptionList(final List<Exception> exceptionList) {
		this.exceptionList = exceptionList;
	}

	public void setGpssystempassword(final String gpssystempassword) {
		this.gpssystempassword = gpssystempassword;
	}

	public void setGpssystemusername(final String gpssystemusername) {
		this.gpssystemusername = gpssystemusername;
	}

	public void setGpstoken(final String gpstoken) {
		this.gpstoken = gpstoken;
	}

	public void setGpstrackingsystem(final String gpstrackingsystem) {
		this.gpstrackingsystem = gpstrackingsystem;
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
				Customeraffiliate.log = Utils.createnewlogs(Customeraffiliate.log, name + "", "Affiliate name");
			}
		} else if (!Objects.isNull(name) && !Objects.isNull(this.name)) {
			Customeraffiliate.log = Utils.updatelogsinfos(Customeraffiliate.log, this.name + "", name + "",
					"Affiliate name");
		} else if (!Objects.isNull(name)) {
			Customeraffiliate.log = Utils.updatelogsinfos(Customeraffiliate.log, "", name + "", "Affiliate name");
		}
		this.name = name;
	}

	public void setParameterconfigList(final List<Parameterconfig> parameterconfigList) {
		this.parameterconfigList = parameterconfigList;
	}

	public void setReportautomaticsList(final List<Reportautomatics> reportautomaticsList) {
		this.reportautomaticsList = reportautomaticsList;
	}

	public void setResumexceptionaffiliateList(final List<Resumexceptionaffiliate> resumexceptionaffiliateList) {
		this.resumexceptionaffiliateList = resumexceptionaffiliateList;
	}

	public void setStateid(final Integer stateid) {
		this.stateid = stateid;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public void setStatus(final Integer status, final boolean createorupdate) {
		Integer stats = 2;
		if (createorupdate) {
			if (!Objects.isNull(status)) {
				Customeraffiliate.log = Utils.createnewlogs(Customeraffiliate.log, "true", "Activate Affiliate: ");
			}
		} else if (!Objects.isNull(status) && !Objects.isNull(this.status)) {
			if (status == 1) {
				stats = this.status;
				if (stats != 1) {
					Customeraffiliate.log = Utils.updatelogsinfos(Customeraffiliate.log, "false", "true",
							" Activate Affiliate: ");
				}
			} else {
				stats = this.status;
				if (stats == 1) {
					Customeraffiliate.log = Utils.updatelogsinfos(Customeraffiliate.log, "true", "false",
							" Activate Affiliate: ");
				}
			}
		} else if (!Objects.isNull(status)) {
			if (status == 1) {
				Customeraffiliate.log = Utils.updatelogsinfos(Customeraffiliate.log, "", "true",
						"Activate Affiliate: ");
			} else {
				Customeraffiliate.log = Utils.updatelogsinfos(Customeraffiliate.log, "", "true",
						"Activate Affiliate: ");
			}
		}
		this.status = status;
	}

	public void setTelephone(final String telephone) {
		this.telephone = telephone;
	}

	public void setTelephone(final String telephone, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(telephone)) {
				Customeraffiliate.log = Utils.createnewlogs(Customeraffiliate.log, telephone + "", "telephone");
			}
		} else if (!Objects.isNull(telephone) && !Objects.isNull(this.telephone)) {
			Customeraffiliate.log = Utils.updatelogsinfos(Customeraffiliate.log, this.telephone + "", telephone + "",
					"telephone");
		} else if (!Objects.isNull(telephone)) {
			Customeraffiliate.log = Utils.updatelogsinfos(Customeraffiliate.log, "", telephone + "", "telephone");
		}
		this.telephone = telephone;
	}

	public void setTransporterList(final List<Transporter> transporterList) {
		this.transporterList = transporterList;
	}

	public void setUpdatedby(final Integer updatedby) {
		this.updatedby = updatedby;
	}

	public void setUpdatedon(final Date updatedon) {
		this.updatedon = updatedon;
	}

	public void setUserightsList(final List<Userights> userightsList) {
		this.userightsList = userightsList;
	}

	public void setVehicleactivitysummaryList(final List<Vehicleactivitysummary> vehicleactivitysummaryList) {
		this.vehicleactivitysummaryList = vehicleactivitysummaryList;
	}

	public void setVehicletripsList(final List<Vehicletrips> vehicletripsList) {
		this.vehicletripsList = vehicletripsList;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Customeraffiliate[ affiliateid=" + this.affiliateid + " ]";
	}
}
