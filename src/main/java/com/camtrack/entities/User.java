//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.aerogear.security.otp.api.Base32;

import com.camtrack.config.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
		@NamedQuery(name = "User.findByUserid", query = "SELECT u FROM User u WHERE u.userid = :userid"),
		@NamedQuery(name = "User.findByCustomerid", query = "SELECT u FROM User u WHERE u.customerid = :customerid"),
		@NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name"),
		@NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
		@NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
		@NamedQuery(name = "User.findByCreatedon", query = "SELECT u FROM User u WHERE u.createdon = :createdon"),
		@NamedQuery(name = "User.findByUpdatedon", query = "SELECT u FROM User u WHERE u.updatedon = :updatedon"),
		@NamedQuery(name = "User.findByEmailid", query = "SELECT u FROM User u WHERE u.emailid = :emailid"),
		@NamedQuery(name = "User.findByEmailflag", query = "SELECT u FROM User u WHERE u.emailflag = :emailflag"),
		@NamedQuery(name = "User.findByLanguageid", query = "SELECT u FROM User u WHERE u.languageid = :languageid") })
public class User implements Serializable {
	public static String log;
	private static final long serialVersionUID = 1L;

	public static String getLog() {
		return User.log;
	}

	public static void init(final Boolean createorupdate, final User user) {
		User.log = Utils.EntetesLog(createorupdate, user.getUsername(), "User");

	}

	public static void setLog(final String log) {
		User.log = log;
	}

	@Column(name = "account_non_locked")
	private boolean accountNonLocked;
	@Column(name = "contacts")
	private String contacts;
	@Column(name = "createdby")
	private Integer createdby;
	@Column(name = "createdon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdon;
	@JsonIgnore
	@Column(name = "customerid")
	private Integer customerid;
	@JsonIgnore
	@OneToMany(mappedBy = "userid", fetch = FetchType.LAZY)
	private List<Emailconfig> emailconfigList;
	@Column(name = "emailflag")
	private Integer emailflag;
	@NotNull
	@NotEmpty
	@Email
	@Column(name = "emailid")
	private String emailid;
	@JsonIgnore
	@Column(name = "enabled")
	private Boolean enabled;
	@Column(name = "failed_attempt")
	private Short failedAttempt;
	@Column(name = "first")
	private Boolean first = true;
	@Column(name = "flockd")
	@Temporal(TemporalType.DATE)
	private Date flockd;
	@Column(name = "flockn")
	private Short flockn;
	@Column(name = "ips")
	private String ips;
	@Column(name = "isadmin")
	private Boolean isadmin = false;
	@Column(name = "isconn")
	private Boolean isconn = false;
	@JsonIgnore
	@JoinColumn(name = "languageid", referencedColumnName = "languageid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Language languageid;
	@Column(name = "lls")
	private String lls;
	@Column(name = "lock_time")
	private Date lockTime;
	/**
	 * @JsonIgnore
	 * @OneToMany(mappedBy = "idusers", fetch = FetchType.LAZY) private
	 *                     List<Lostpassword> lostpasswordList;
	 */
	@Column(name = "lpsw")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lpsw;
	@Column(name = "mfa")
	private Boolean mfa = false;
	@Column(name = "name")
	private String name;
	@Column(name = "nops")
	private String nops;
	@JsonIgnore
	@Column(name = "password")
	private String password;
	@JsonIgnore
	@Column(name = "pictures")
	private String pictures;
	@Column(name = "randoms")
	private String randoms;
	@Column(name = "rdates")
	@Temporal(TemporalType.TIMESTAMP)
	private Date rdates;
	@Column(name = "lpw")
	private Boolean lpw = false;
	@Column(name = "trust")
	private Boolean trust = false;

	@JsonIgnore
	@JoinColumn(name = "reelrole", referencedColumnName = "ids")
	@ManyToOne(fetch = FetchType.LAZY)
	private Reelroleusers reelrole;

	@JsonIgnore
	@OneToMany(mappedBy = "createby", fetch = FetchType.LAZY)
	private List<Reportautomatics> reportautomaticsList;

	@JsonIgnore
	@OneToMany(mappedBy = "updateby", fetch = FetchType.LAZY)
	private List<Reportautomatics> reportautomaticsList1;
	@JsonIgnore
	@OneToMany(mappedBy = "userid", fetch = FetchType.LAZY)
	private List<Userights> userightsList;

	public List<Userights> getUserightsList() {
		return userightsList;
	}

	public void setUserightsList(List<Userights> userightsList) {
		this.userightsList = userightsList;
	}

	public Boolean getTrust() {
		return trust;
	}

	public void setTrust(Boolean trust) {
		this.trust = trust;
	}

	@Column(name = "secret")
	private String secret;

	@Column(name = "sexe")
	private Character sexe;
	@JsonIgnore
	@Column(name = "status")
	private Integer status;

	@Column(name = "updatedby")
	private Integer updatedby;

	@Column(name = "updatedon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedon;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "userid")
	private Integer userid;

	@JsonIgnore
	@OneToMany(mappedBy = "userid", fetch = FetchType.LAZY)
	private List<Userlogsactivity> userlogsactivityList;

	@Column(name = "username")
	private String username;

	@Column(name = "vsspassword")
	private String vsspassword;

	public Boolean getLpw() {
		if (Objects.isNull(lpw)) {
			return false;
		}
		return lpw;
	}

	public void setLpw(Boolean lpw) {
		this.lpw = lpw;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof User)) {
			return false;
		}
		final User other = (User) object;
		return (this.userid != null || other.userid == null)
				&& (this.userid == null || this.userid.equals(other.userid));
	}

	public String getContacts() {
		return this.contacts;
	}

	public Integer getCreatedby() {
		return this.createdby;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public Integer getCustomerid() {
		return this.customerid;
	}

	public List<Emailconfig> getEmailconfigList() {
		return this.emailconfigList;
	}

	public Integer getEmailflag() {
		return this.emailflag;
	}

	public String getEmailid() {
		return this.emailid;
	}

	public Boolean getEnabled() {
		if (Objects.isNull(this.enabled)) {
			return false;
		}
		return this.enabled;
	}

	public Short getFailedAttempt() {
		return failedAttempt;
	}

	public Boolean getFirst() {
		return first;
	}

	public Date getFlockd() {
		if (Objects.isNull(flockd)) {
			return new Date();
		}
		return flockd;
	}

	public Short getFlockn() {
		if (Objects.isNull(flockn)) {
			return Short.valueOf("0");
		}
		return flockn;
	}

	public String getIps() {
		return ips;
	}

	public Boolean getIsadmin() {
		return isadmin;
	}

	public Boolean getIsconn() {
		return isconn;
	}

	public Language getLanguageid() {
		return this.languageid;
	}

	public String getLls() {
		return lls;
	}

	public Date getLockTime() {
		return lockTime;
	}

	/**
	 * public List<Lostpassword> getLostpasswordList() { return
	 * this.lostpasswordList; }
	 * 
	 * public void setLostpasswordList(final List<Lostpassword> lostpasswordList) {
	 * this.lostpasswordList = lostpasswordList; }
	 */

	public Date getLpsw() {
		return lpsw;
	}

	public Boolean getMfa() {
		return mfa;
	}

	public String getName() {
		return this.name;
	}

	public String getNops() {
		return nops;
	}

	public String getPassword() {
		return this.password;
	}

	public String getPictures() {
		return this.pictures;
	}

	public String getRandoms() {
		return randoms;
	}

	public Date getRdates() {
		return rdates;
	}

	public Reelroleusers getReelrole() {
		return this.reelrole;
	}

	public List<Reportautomatics> getReportautomaticsList() {
		return this.reportautomaticsList;
	}

	public List<Reportautomatics> getReportautomaticsList1() {
		return this.reportautomaticsList1;
	}

	public String getSecret() {
		return secret;
	}

	public Character getSexe() {
		return this.sexe;
	}

	public Integer getStatus() {
		return this.status;
	}

	public Integer getUpdatedby() {
		return this.updatedby;
	}

	public Date getUpdatedon() {
		return this.updatedon;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public List<Userlogsactivity> getUserlogsactivityList() {
		return this.userlogsactivityList;
	}

	public String getUsername() {
		return this.username;
	}

	public String getVsspassword() {
		return this.vsspassword;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.userid != null) ? this.userid.hashCode() : 0);
		return hash;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public Boolean isFirst() {
		return first;
	}

	public Boolean isIsconn() {
		return isconn;
	}

	public Boolean isMfa() {
		return mfa;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked, Boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(accountNonLocked)) {
				User.log = Utils.createnewlogs(User.log, accountNonLocked + "", "Unblock User");
			}
		} else if (!Objects.isNull(accountNonLocked) && !Objects.isNull(this.accountNonLocked)) {
			User.log = Utils.updatelogsinfos(User.log, this.accountNonLocked + "", accountNonLocked + "",
					"Unblock User");
		} else if (!Objects.isNull(accountNonLocked)) {
			User.log = Utils.updatelogsinfos(User.log, "", accountNonLocked + "", "Unblock User");
		}
		this.accountNonLocked = accountNonLocked;
	}

	public void setContacts(final String contacts) {
		this.contacts = contacts;
	}

	public void setContacts(final String contacts, final Boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(contacts)) {
				User.log = Utils.createnewlogs(User.log, contacts + "", "contacts");
			}
		} else if (!Objects.isNull(contacts) && !Objects.isNull(this.contacts)) {
			User.log = Utils.updatelogsinfos(User.log, this.contacts + "", contacts + "", "contacts");
		} else if (!Objects.isNull(contacts)) {
			User.log = Utils.updatelogsinfos(User.log, "", contacts + "", "contacts");
		}
		this.contacts = contacts;
	}

	public void setCreatedby(final Integer createdby) {
		this.createdby = createdby;
	}

	public void setCreatedon(final Date createdon) {
		this.createdon = createdon;
	}

	public void setCustomerid(final Integer customerid) {
		this.customerid = customerid;
	}

	public void setEmailconfigList(final List<Emailconfig> emailconfigList) {
		this.emailconfigList = emailconfigList;
	}

	public void setEmailflag(final Integer emailflag) {
		this.emailflag = emailflag;
	}

	public void setEmailid(final String emailid) {
		this.emailid = emailid;
	}

	public void setEmailid(final String email, final Boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(email)) {
				User.log = Utils.createnewlogs(User.log, email + "", "email");
			}
		} else if (!Objects.isNull(email) && !Objects.isNull(this.emailid)) {
			User.log = Utils.updatelogsinfos(User.log, this.emailid + "", email + "", "email");
		} else if (!Objects.isNull(email)) {
			User.log = Utils.updatelogsinfos(User.log, "", email + "", "email");
		}
		this.emailid = email;
	}

	public void setEnabled(final Boolean enabled) {
		this.enabled = enabled;
	}

	public void setEnabled(final Boolean enabled, final Boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(enabled)) {
				User.log = Utils.createnewlogs(User.log, enabled + "", "activation status");
			}
		} else if (!Objects.isNull(enabled) && !Objects.isNull(this.enabled)) {
			User.log = Utils.updatelogsinfos(User.log, this.enabled + "", enabled + "", "activation status");
		} else if (!Objects.isNull(enabled)) {
			User.log = Utils.updatelogsinfos(User.log, "", enabled + "", "activation status");
		}
		this.enabled = enabled;
	}

	public void setFailedAttempt(Short failedAttempt) {
		this.failedAttempt = failedAttempt;
	}

	public void setFirst(Boolean first) {
		this.first = first;
	}

	public void setFirst(Boolean first, Boolean createorupdate) {

		if (createorupdate) {
			if (!Objects.isNull(first)) {
				User.log = Utils.createnewlogs(User.log, first + "", "Réinit First connect");
			}
		} else if (!Objects.isNull(first) && !Objects.isNull(this.first)) {
			User.log = Utils.updatelogsinfos(User.log, this.first + "", first + "", "Réinit First connect");
		} else if (!Objects.isNull(first)) {
			User.log = Utils.updatelogsinfos(User.log, "", first + "", "Réinit First connect");
		}
		this.first = first;
	}

	public void setFlockd(Date flockd) {
		this.flockd = flockd;
	}

	public void setFlockn(Short flockn) {
		this.flockn = flockn;
	}

	public void setIps(String ips) {
		this.ips = ips;
	}

	public void setIsadmin(Boolean isadmin) {
		this.isadmin = isadmin;
	}

	public void setIsconn(Boolean isconn) {
		this.isconn = isconn;
	}

	public void setLanguageid(final Language languageid) {
		this.languageid = languageid;
	}

	public void setLls(String lls) {
		this.lls = lls;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	public void setLpsw(Date lpsw) {
		this.lpsw = lpsw;
	}

	public void setMfa(Boolean mfa) {
		this.mfa = mfa;
	}

	public void setMfa(Boolean mfa, Boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(mfa)) {
				User.log = Utils.createnewlogs(User.log, mfa + "", "mfa");
			}
		} else if (!Objects.isNull(mfa) && !Objects.isNull(this.mfa)) {
			User.log = Utils.updatelogsinfos(User.log, this.mfa + "", mfa + "", "mfa");
		} else if (!Objects.isNull(mfa)) {
			User.log = Utils.updatelogsinfos(User.log, "", mfa + "", "mfa");
		}
		this.mfa = mfa;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setName(final String name, final Boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(name)) {
				User.log = Utils.createnewlogs(User.log, name + "", "name");
			}
		} else if (!Objects.isNull(name) && !Objects.isNull(this.name)) {
			User.log = Utils.updatelogsinfos(User.log, this.name + "", name + "", "name");
		} else if (!Objects.isNull(name)) {
			User.log = Utils.updatelogsinfos(User.log, "", name + "", "name");
		}
		this.name = name;
	}

	public void setNops(String nops) {
		this.nops = nops;
	}

	public void setPassword(final String password, Integer limitdayspassword, Boolean createorupdate) {

		if (createorupdate) {
			if (!Objects.isNull(password)) {
				User.log = Utils.createnewlogs(User.log, "***" + "", "Password");
			}
		} else if (!Objects.isNull(password) && !Objects.isNull(this.password)) {
			User.log = Utils.updatelogsinfos(User.log, "***" + "", "***" + "", "Password");
		} else if (!Objects.isNull(password)) {
			User.log = Utils.updatelogsinfos(User.log, "", "***" + "", "Password");
		}
		this.password = password;
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, limitdayspassword);
		this.lpsw = cal.getTime();
		this.lpw = true;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

	public void setPictures(final String pictures, final Boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(pictures)) {
				User.log = Utils.createnewlogs(User.log, pictures + "", "pictures");
			}
		} else if (!Objects.isNull(pictures) && !Objects.isNull(this.pictures)) {
			User.log = Utils.updatelogsinfos(User.log, this.pictures + "", pictures + "", "pictures");
		} else if (!Objects.isNull(pictures)) {
			User.log = Utils.updatelogsinfos(User.log, "", pictures + "", "pictures");
		}
		this.pictures = pictures;
	}

	public void setRandoms(String randoms) {
		this.randoms = randoms;
	}

	public void setRdates(Date rdates) {
		this.rdates = rdates;
	}

	public void setReelrole(final Reelroleusers reelrole) {
		this.reelrole = reelrole;
	}

	public void setReelrole(final Reelroleusers reelrole, final Boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(reelrole)) {
				User.log = Utils.createnewlogs(User.log, reelrole.getRolenames() + "", "role");
			}
		} else if (!Objects.isNull(reelrole) && !Objects.isNull(this.reelrole)) {
			User.log = Utils.updatelogsinfos(User.log, this.reelrole.getRolenames() + "", reelrole.getRolenames() + "",
					"Role");
		} else if (!Objects.isNull(reelrole)) {
			User.log = Utils.updatelogsinfos(User.log, "", reelrole.getRolenames() + "", "Role");
		}
		this.reelrole = reelrole;
	}

	public void setReportautomaticsList(final List<Reportautomatics> reportautomaticsList) {
		this.reportautomaticsList = reportautomaticsList;
	}

	public void setReportautomaticsList1(final List<Reportautomatics> reportautomaticsList1) {
		this.reportautomaticsList1 = reportautomaticsList1;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public void setSexe(final Character sexe) {
		this.sexe = sexe;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public void setStatus(final Integer status, final Boolean createorupdate) {
		Integer stats = 2;
		if (createorupdate) {
			if (!Objects.isNull(status)) {
				User.log = Utils.createnewlogs(User.log, "true", "Activate User: ");
			}
		} else if (!Objects.isNull(status) && !Objects.isNull(this.status)) {
			if (status == 1) {
				stats = this.status;
				if (stats != 1) {
					User.log = Utils.updatelogsinfos(User.log, "false", "true", " Activate User: ");
				}
			} else {
				stats = this.status;
				if (stats == 1) {
					User.log = Utils.updatelogsinfos(User.log, "true", "false", " Activate User: ");
				}
			}
		} else if (!Objects.isNull(status)) {
			if (status == 1) {
				User.log = Utils.updatelogsinfos(User.log, "", "true", "Activate User: ");
			} else {
				User.log = Utils.updatelogsinfos(User.log, "", "true", "Activate User: ");
			}
		}
		this.status = status;
	}

	public void setUpdatedby(final Integer updatedby) {
		this.updatedby = updatedby;
	}

	public void setUpdatedon(final Date updatedon) {
		this.updatedon = updatedon;
	}

	public void setUserid(final Integer userid) {
		this.userid = userid;
	}

	public void setUserlogsactivityList(final List<Userlogsactivity> userlogsactivityList) {
		this.userlogsactivityList = userlogsactivityList;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public void setUsername(final String username, final Boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(username)) {
				User.log = Utils.createnewlogs(User.log, username + "", "username");
			}
		} else if (!Objects.isNull(username) && !Objects.isNull(this.username)) {
			User.log = Utils.updatelogsinfos(User.log, this.username + "", username + "", "username");
		} else if (!Objects.isNull(username)) {
			User.log = Utils.updatelogsinfos(User.log, "", username + "", "username");
		}
		this.secret = Base32.random();
		this.username = username;
	}

	public void setVsspassword(final String vsspassword) {
		this.vsspassword = vsspassword;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.User[ userid=" + this.userid + " ]";
	}
}
