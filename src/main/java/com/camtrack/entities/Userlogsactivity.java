//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.Date;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "userlogsactivity")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Userlogsactivity.findAll", query = "SELECT u FROM Userlogsactivity u"),
		@NamedQuery(name = "Userlogsactivity.findByIds", query = "SELECT u FROM Userlogsactivity u WHERE u.ids = :ids"),
		@NamedQuery(name = "Userlogsactivity.findByLogsinfos", query = "SELECT u FROM Userlogsactivity u WHERE u.logsinfos = :logsinfos"),
		@NamedQuery(name = "Userlogsactivity.findByDates", query = "SELECT u FROM Userlogsactivity u WHERE u.dates = :dates") })
public class Userlogsactivity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "dates")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dates;
	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ids")
	private Long ids;
	@JoinColumn(name = "ldtypeconfig", referencedColumnName = "ids")
	@ManyToOne(fetch = FetchType.LAZY)
	private Listconfigtypes ldtypeconfig;
	@Column(name = "logsinfos")
	private String logsinfos;
	@JoinColumn(name = "userid", referencedColumnName = "userid")
	@ManyToOne(fetch = FetchType.LAZY)
	private User userid;

	public Userlogsactivity() {
		super();
	}

	public Userlogsactivity(final Listconfigtypes ldtypeconfig) {
		this.ldtypeconfig = ldtypeconfig;
	}

	public Userlogsactivity(final Long ids) {
		this.ids = ids;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Userlogsactivity)) {
			return false;
		}
		final Userlogsactivity other = (Userlogsactivity) object;
		return (this.ids != null || other.ids == null) && (this.ids == null || this.ids.equals(other.ids));
	}

	public Date getDates() {
		return this.dates;
	}

	public Long getIds() {
		return this.ids;
	}

	public Listconfigtypes getLdtypeconfig() {
		return this.ldtypeconfig;
	}

	public String getLogsinfos() {
		return this.logsinfos;
	}

	public User getUserid() {
		return this.userid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.ids != null) ? this.ids.hashCode() : 0);
		return hash;
	}

	public void setDates(final Date dates) {
		this.dates = dates;
	}

	public void setIds(final Long ids) {
		this.ids = ids;
	}

	public void setLdtypeconfig(final Listconfigtypes ldtypeconfig) {
		this.ldtypeconfig = ldtypeconfig;
	}

	public void setLogsinfos(final String logsinfos) {
		this.logsinfos = logsinfos;
	}

	public void setUserid(final User userid) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Userlogsactivity[ ids=" + this.ids + " ]";
	}
}
