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
@Table(name = "logusers")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Logusers.findAll", query = "SELECT l FROM Logusers l"),
		@NamedQuery(name = "Logusers.findByUsername", query = "SELECT l FROM Logusers l WHERE l.username = :username"),
		@NamedQuery(name = "Logusers.findByUrlnames", query = "SELECT l FROM Logusers l WHERE l.urlnames = :urlnames"),
		@NamedQuery(name = "Logusers.findByDates", query = "SELECT l FROM Logusers l WHERE l.dates = :dates"),
		@NamedQuery(name = "Logusers.findByIpuser", query = "SELECT l FROM Logusers l WHERE l.ipuser = :ipuser"),
		@NamedQuery(name = "Logusers.findByIds", query = "SELECT l FROM Logusers l WHERE l.ids = :ids") })
public class Logusers implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "authstatus")
	private String authstatus;
	@Column(name = "dates")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dates;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ids")
	private Long ids;
	@Column(name = "ipuser")
	private String ipuser;
	@Column(name = "urlnames")
	private String urlnames;
	@Column(name = "username")
	private String username;

	public Logusers() {
	}

	public Logusers(final Long ids) {
		this.ids = ids;
	}

	public Logusers(final String username, final String authstatus, final String urlnames, final Date dates,
			final String ipuser) {
		this.username = username;
		this.urlnames = urlnames;
		this.dates = dates;
		this.ipuser = ipuser;
		this.authstatus = authstatus;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Logusers)) {
			return false;
		}
		final Logusers other = (Logusers) object;
		return (this.ids != null || other.ids == null) && (this.ids == null || this.ids.equals(other.ids));
	}

	public String getAuthstatus() {
		return this.authstatus;
	}

	public Date getDates() {
		return this.dates;
	}

	public Long getIds() {
		return this.ids;
	}

	public String getIpuser() {
		return this.ipuser;
	}

	public String getUrlnames() {
		return this.urlnames;
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

	public void setAuthstatus(final String authstatus) {
		this.authstatus = authstatus;
	}

	public void setDates(final Date dates) {
		this.dates = dates;
	}

	public void setIds(final Long ids) {
		this.ids = ids;
	}

	public void setIpuser(final String ipuser) {
		this.ipuser = ipuser;
	}

	public void setUrlnames(final String urlnames) {
		this.urlnames = urlnames;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "com.services.mintrans.entities.Logusers[ ids=" + this.ids + " ]";
	}
}
