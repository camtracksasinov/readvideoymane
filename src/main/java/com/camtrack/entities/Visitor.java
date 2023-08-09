//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "visitor")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Visitor.findAll", query = "SELECT v FROM Visitor v"),
		@NamedQuery(name = "Visitor.findByIds", query = "SELECT v FROM Visitor v WHERE v.ids = :ids"),
		@NamedQuery(name = "Visitor.findByUsername", query = "SELECT v FROM Visitor v WHERE v.username = :username"),
		@NamedQuery(name = "Visitor.findByIpuser", query = "SELECT v FROM Visitor v WHERE v.ipuser = :ipuser"),
		@NamedQuery(name = "Visitor.findByMethods", query = "SELECT v FROM Visitor v WHERE v.methods = :methods"),
		@NamedQuery(name = "Visitor.findByUrl", query = "SELECT v FROM Visitor v WHERE v.url = :url"),
		@NamedQuery(name = "Visitor.findByPages", query = "SELECT v FROM Visitor v WHERE v.pages = :pages"),
		@NamedQuery(name = "Visitor.findByQuerystring", query = "SELECT v FROM Visitor v WHERE v.querystring = :querystring"),
		@NamedQuery(name = "Visitor.findByRefererpage", query = "SELECT v FROM Visitor v WHERE v.refererpage = :refererpage"),
		@NamedQuery(name = "Visitor.findByUseragent", query = "SELECT v FROM Visitor v WHERE v.useragent = :useragent"),
		@NamedQuery(name = "Visitor.findByLoggedtime", query = "SELECT v FROM Visitor v WHERE v.loggedtime = :loggedtime"),
		@NamedQuery(name = "Visitor.findByUniquevisit", query = "SELECT v FROM Visitor v WHERE v.uniquevisit = :uniquevisit") })
public class Visitor implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ids")
	private Long ids;
	@Column(name = "ipuser")
	private String ipuser;
	@Column(name = "loggedtime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date loggedtime;
	@Column(name = "methods")
	private String methods;
	@Column(name = "pages")
	private String pages;
	@Column(name = "querystring")
	private String querystring;
	@Column(name = "refererpage")
	private String refererpage;
	@Column(name = "uniquevisit")
	private Boolean uniquevisit;
	@Column(name = "url")
	private String url;
	@Column(name = "useragent")
	private String useragent;
	@Column(name = "username")
	private String username;

	public Visitor() {
	}

	public Visitor(final Long ids) {
		this.ids = ids;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Visitor)) {
			return false;
		}
		final Visitor other = (Visitor) object;
		return (this.ids != null || other.ids == null) && (this.ids == null || this.ids.equals(other.ids));
	}

	public Long getIds() {
		return this.ids;
	}

	public String getIpuser() {
		return this.ipuser;
	}

	public Date getLoggedtime() {
		return this.loggedtime;
	}

	public String getMethods() {
		return this.methods;
	}

	public String getPages() {
		return this.pages;
	}

	public String getQuerystring() {
		return this.querystring;
	}

	public String getRefererpage() {
		return this.refererpage;
	}

	public Boolean getUniquevisit() {
		return this.uniquevisit;
	}

	public String getUrl() {
		return this.url;
	}

	public String getUseragent() {
		return this.useragent;
	}

	public String getUsername() {
		return this.username;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.ids != null) ? this.ids.hashCode() : 0);
		return hash;
	}

	public void setIds(final Long ids) {
		this.ids = ids;
	}

	public void setIpuser(final String ipuser) {
		this.ipuser = ipuser;
	}

	public void setLoggedtime(final Date loggedtime) {
		this.loggedtime = loggedtime;
	}

	public void setMethods(final String methods) {
		this.methods = methods;
	}

	public void setPages(final String pages) {
		this.pages = pages;
	}

	public void setQuerystring(final String querystring) {
		this.querystring = querystring;
	}

	public void setRefererpage(final String refererpage) {
		this.refererpage = refererpage;
	}

	public void setUniquevisit(final Boolean uniquevisit) {
		this.uniquevisit = uniquevisit;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setUseragent(final String useragent) {
		this.useragent = useragent;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Visitor[ ids=" + this.ids + " ]";
	}
}
