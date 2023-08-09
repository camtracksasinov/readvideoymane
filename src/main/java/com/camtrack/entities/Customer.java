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
@Table(name = "customer")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
		@NamedQuery(name = "Customer.findByCustomerid", query = "SELECT c FROM Customer c WHERE c.customerid = :customerid"),
		@NamedQuery(name = "Customer.findByName", query = "SELECT c FROM Customer c WHERE c.name = :name"),
		@NamedQuery(name = "Customer.findByAddressline1", query = "SELECT c FROM Customer c WHERE c.addressline1 = :addressline1"),
		@NamedQuery(name = "Customer.findByAddressline2", query = "SELECT c FROM Customer c WHERE c.addressline2 = :addressline2"),
		@NamedQuery(name = "Customer.findByCityid", query = "SELECT c FROM Customer c WHERE c.cityid = :cityid"),
		@NamedQuery(name = "Customer.findByStateid", query = "SELECT c FROM Customer c WHERE c.stateid = :stateid"),
		@NamedQuery(name = "Customer.findByCountryid", query = "SELECT c FROM Customer c WHERE c.countryid = :countryid"),
		@NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email"),
		@NamedQuery(name = "Customer.findByLogourl", query = "SELECT c FROM Customer c WHERE c.logourl = :logourl"),
		@NamedQuery(name = "Customer.findByThemetemplate", query = "SELECT c FROM Customer c WHERE c.themetemplate = :themetemplate"),
		@NamedQuery(name = "Customer.findByTelephone", query = "SELECT c FROM Customer c WHERE c.telephone = :telephone"),
		@NamedQuery(name = "Customer.findByLanguageid", query = "SELECT c FROM Customer c WHERE c.languageid = :languageid"),
		@NamedQuery(name = "Customer.findByNumberofusers", query = "SELECT c FROM Customer c WHERE c.numberofusers = :numberofusers"),
		@NamedQuery(name = "Customer.findByCreatedon", query = "SELECT c FROM Customer c WHERE c.createdon = :createdon"),
		@NamedQuery(name = "Customer.findByUpdatedon", query = "SELECT c FROM Customer c WHERE c.updatedon = :updatedon"),
		@NamedQuery(name = "Customer.findByUpdatedby", query = "SELECT c FROM Customer c WHERE c.updatedby = :updatedby"),
		@NamedQuery(name = "Customer.findByThemecolor", query = "SELECT c FROM Customer c WHERE c.themecolor = :themecolor"),
		@NamedQuery(name = "Customer.findByCreatedby", query = "SELECT c FROM Customer c WHERE c.createdby = :createdby"),
		@NamedQuery(name = "Customer.findByStatus", query = "SELECT c FROM Customer c WHERE c.status = :status") })
public class Customer implements Serializable {
	public static String log;
	private static final long serialVersionUID = 1L;
	static {
		Customer.log = "";
	}

	public static String getLog() {
		return Customer.log;
	}

	public static void init(final boolean createorupdate, final User user) {
		Customer.log = Utils.EntetesLog(createorupdate, user.getUsername(), "Customer");
	}

	public static void setLog(final String log) {
		Customer.log = log;
	}

	@Column(name = "addressline1")
	private String addressline1;
	@Column(name = "addressline2")
	private String addressline2;
	@JsonBackReference("alllert")
	@OneToOne(mappedBy = "clientid", fetch = FetchType.LAZY)
	private Allalertlevel allalertlevel;
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
	@JsonIgnore
	@OneToMany(mappedBy = "customerid", fetch = FetchType.LAZY)
	private List<Customeraffiliate> customeraffiliateList;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "customerid")
	private Integer customerid;
	@Column(name = "email")
	private String email;
	@JsonIgnore
	@OneToMany(mappedBy = "clientid", fetch = FetchType.LAZY)
	private List<Emailconfig> emailconfigList;
	@JsonIgnore
	@Column(name = "languageid")
	private Integer languageid;
	@JsonIgnore
	@Column(name = "logourl")
	private String logourl;
	@Column(name = "name")
	private String name;
	@Column(name = "numberofusers")
	private Integer numberofusers;
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "customerid", fetch = FetchType.LAZY)
	private List<Parameterconfig> parameterconfigList;
	@JsonIgnore
	@OneToMany(mappedBy = "customid", fetch = FetchType.LAZY)
	private List<Reportautomatics> reportautomaticsList;
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "customer", fetch = FetchType.LAZY)
	private List<Resumexceptionclient> resumexceptionclientList;
	@JsonIgnore
	@Column(name = "stateid")
	private Integer stateid;
	@JsonIgnore
	@Column(name = "status")
	private Integer status;
	@Column(name = "telephone")
	private String telephone;
	@JsonIgnore
	@Column(name = "themecolor")
	private String themecolor;
	@JsonIgnore
	@Column(name = "themetemplate")
	private String themetemplate;

	@JsonIgnore
	@Column(name = "updatedby")
	private Integer updatedby;

	@JsonIgnore
	@Column(name = "updatedon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedon;

	@JsonIgnore
	@OneToMany(mappedBy = "customerid", fetch = FetchType.LAZY)
	private List<Userights> userightsList;

	@JsonIgnore
	@OneToMany(mappedBy = "clientid", fetch = FetchType.LAZY)
	private List<Vehicletrips> vehicletripsList;

	public Customer() {
		this.numberofusers = 0;
	}

	public Customer(final Integer customerid) {
		this.numberofusers = 0;
		this.customerid = customerid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Customer)) {
			return false;
		}
		final Customer other = (Customer) object;
		return (this.customerid != null || other.customerid == null)
				&& (this.customerid == null || this.customerid.equals(other.customerid));
	}

	public String getAddressline1() {
		return this.addressline1;
	}

	public String getAddressline2() {
		return this.addressline2;
	}

	public Allalertlevel getAllalertlevel() {
		return this.allalertlevel;
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

	@XmlTransient
	public List<Customeraffiliate> getCustomeraffiliateList() {
		return this.customeraffiliateList;
	}

	public Integer getCustomerid() {
		return this.customerid;
	}

	public String getEmail() {
		return this.email;
	}

	public List<Emailconfig> getEmailconfigList() {
		return this.emailconfigList;
	}

	public Integer getLanguageid() {
		return this.languageid;
	}

	public String getLogourl() {
		return this.logourl;
	}

	public String getName() {
		return this.name;
	}

	public Integer getNumberofusers() {
		return this.numberofusers;
	}

	public List<Parameterconfig> getParameterconfigList() {
		return this.parameterconfigList;
	}

	public List<Reportautomatics> getReportautomaticsList() {
		return this.reportautomaticsList;
	}

	public List<Resumexceptionclient> getResumexceptionclientList() {
		return this.resumexceptionclientList;
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

	public String getThemecolor() {
		return this.themecolor;
	}

	public String getThemetemplate() {
		return this.themetemplate;
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

	public List<Vehicletrips> getVehicletripsList() {
		return this.vehicletripsList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.customerid != null) ? this.customerid.hashCode() : 0);
		return hash;
	}

	public void setAddressline1(final String addressline1) {
		this.addressline1 = addressline1;
	}

	public void setAddressline1(final String addressline1, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(addressline1)) {
				Customer.log = Utils.createnewlogs(Customer.log, addressline1 + "", "addressline1");
			}
		} else if (!Objects.isNull(addressline1) && !Objects.isNull(this.addressline1)) {
			Customer.log = Utils.updatelogsinfos(Customer.log, this.addressline1 + "", addressline1 + "",
					"addressline1");
		} else if (!Objects.isNull(addressline1)) {
			Customer.log = Utils.updatelogsinfos(Customer.log, "", addressline1 + "", "addressline1");
		}
		this.addressline1 = addressline1;
	}

	public void setAddressline2(final String addressline2) {
		this.addressline2 = addressline2;
	}

	public void setAddressline2(final String addressline2, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(addressline2)) {
				Customer.log = Utils.createnewlogs(Customer.log, addressline2 + "", "addressline2");
			}
		} else if (!Objects.isNull(addressline2) && !Objects.isNull(this.addressline2)) {
			Customer.log = Utils.updatelogsinfos(Customer.log, this.addressline2 + "", addressline2 + "",
					"addressline2");
		} else if (!Objects.isNull(addressline2)) {
			Customer.log = Utils.updatelogsinfos(Customer.log, "", addressline2 + "", "addressline2");
		}
		this.addressline2 = addressline2;
	}

	public void setAllalertlevel(final Allalertlevel allalertlevel) {
		this.allalertlevel = allalertlevel;
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

	public void setCustomeraffiliateList(final List<Customeraffiliate> customeraffiliateList) {
		this.customeraffiliateList = customeraffiliateList;
	}

	public void setCustomerid(final Integer customerid) {
		this.customerid = customerid;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setEmail(final String email, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(email)) {
				Customer.log = Utils.createnewlogs(Customer.log, email + "", "email");
			}
		} else if (!Objects.isNull(email) && !Objects.isNull(this.email)) {
			Customer.log = Utils.updatelogsinfos(Customer.log, this.email + "", email + "", "email");
		} else if (!Objects.isNull(email)) {
			Customer.log = Utils.updatelogsinfos(Customer.log, "", email + "", "email");
		}
		this.email = email;
	}

	public void setEmailconfigList(final List<Emailconfig> emailconfigList) {
		this.emailconfigList = emailconfigList;
	}

	public void setLanguageid(final Integer languageid) {
		this.languageid = languageid;
	}

	public void setLogourl(final String logourl) {
		this.logourl = logourl;
	}

	public void setLogourl(final String logourl, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(logourl)) {
				Customer.log = Utils.createnewlogs(Customer.log, logourl + "", "logourl");
			}
		} else if (!Objects.isNull(logourl) && !Objects.isNull(this.logourl)) {
			Customer.log = Utils.updatelogsinfos(Customer.log, this.logourl + "", logourl + "", "logourl");
		} else if (!Objects.isNull(logourl)) {
			Customer.log = Utils.updatelogsinfos(Customer.log, "", logourl + "", "logourl");
		}
		this.logourl = logourl;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setName(final String name, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(name)) {
				Customer.log = Utils.createnewlogs(Customer.log, name + "", "Customer name");
			}
		} else if (!Objects.isNull(name) && !Objects.isNull(this.name)) {
			Customer.log = Utils.updatelogsinfos(Customer.log, this.name + "", name + "", "Customer name");
		} else if (!Objects.isNull(name)) {
			Customer.log = Utils.updatelogsinfos(Customer.log, "", name + "", "Customer name");
		}
		this.name = name;
	}

	public void setNumberofusers(final Integer numberofusers) {
		this.numberofusers = numberofusers;
	}

	public void setNumberofusers(final Integer numberofusers, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(numberofusers)) {
				Customer.log = Utils.createnewlogs(Customer.log, numberofusers + "", "numberofusers");
			}
		} else if (!Objects.isNull(numberofusers) && !Objects.isNull(this.numberofusers)) {
			Customer.log = Utils.updatelogsinfos(Customer.log, this.numberofusers + "", numberofusers + "",
					"numberofusers");
		} else if (!Objects.isNull(numberofusers)) {
			Customer.log = Utils.updatelogsinfos(Customer.log, "", numberofusers + "", "numberofusers");
		}
		this.numberofusers = numberofusers;
	}

	public void setParameterconfigList(final List<Parameterconfig> parameterconfigList) {
		this.parameterconfigList = parameterconfigList;
	}

	public void setReportautomaticsList(final List<Reportautomatics> reportautomaticsList) {
		this.reportautomaticsList = reportautomaticsList;
	}

	public void setResumexceptionclientList(final List<Resumexceptionclient> resumexceptionclientList) {
		this.resumexceptionclientList = resumexceptionclientList;
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
				Customer.log = Utils.createnewlogs(Customer.log, "true", "Activate Customer: ");
			}
		} else if (!Objects.isNull(status) && !Objects.isNull(this.status)) {
			if (status == 1) {
				stats = this.status;
				if (stats != 1) {
					Customer.log = Utils.updatelogsinfos(Customer.log, "false", "true", " Activate Customer: ");
				}
			} else {
				stats = this.status;
				if (stats == 1) {
					Customer.log = Utils.updatelogsinfos(Customer.log, "true", "false", " Activate Customer: ");
				}
			}
		} else if (!Objects.isNull(status)) {
			if (status == 1) {
				Customer.log = Utils.updatelogsinfos(Customer.log, "", "true", "Activate Customer: ");
			} else {
				Customer.log = Utils.updatelogsinfos(Customer.log, "", "true", "Activate Customer: ");
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
				Customer.log = Utils.createnewlogs(Customer.log, telephone + "", "telephone");
			}
		} else if (!Objects.isNull(telephone) && !Objects.isNull(this.telephone)) {
			Customer.log = Utils.updatelogsinfos(Customer.log, this.telephone + "", telephone + "", "telephone");
		} else if (!Objects.isNull(telephone)) {
			Customer.log = Utils.updatelogsinfos(Customer.log, "", telephone + "", "telephone");
		}
		this.telephone = telephone;
	}

	public void setThemecolor(final String themecolor) {
		this.themecolor = themecolor;
	}

	public void setThemetemplate(final String themetemplate) {
		this.themetemplate = themetemplate;
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

	public void setVehicletripsList(final List<Vehicletrips> vehicletripsList) {
		this.vehicletripsList = vehicletripsList;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Customer[ customerid=" + this.customerid + " ]";
	}
}
