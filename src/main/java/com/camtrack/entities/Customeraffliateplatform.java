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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "customeraffliateplatform")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "Customeraffliateplatform.findAll", query = "SELECT c FROM Customeraffliateplatform c"),
		@NamedQuery(name = "Customeraffliateplatform.findById", query = "SELECT c FROM Customeraffliateplatform c WHERE c.id = :id"),
		@NamedQuery(name = "Customeraffliateplatform.findByPlatformlogin", query = "SELECT c FROM Customeraffliateplatform c WHERE c.platformlogin = :platformlogin"),
		@NamedQuery(name = "Customeraffliateplatform.findByPlatformpasswordortoken", query = "SELECT c FROM Customeraffliateplatform c WHERE c.platformpasswordortoken = :platformpasswordortoken"),
		@NamedQuery(name = "Customeraffliateplatform.findByBaseurl", query = "SELECT c FROM Customeraffliateplatform c WHERE c.baseurl = :baseurl"),
		@NamedQuery(name = "Customeraffliateplatform.findByValidtransporters", query = "SELECT c FROM Customeraffliateplatform c WHERE c.validtransporters = :validtransporters"),
		@NamedQuery(name = "Customeraffliateplatform.findByLastknowntoken", query = "SELECT c FROM Customeraffliateplatform c WHERE c.lastknowntoken = :lastknowntoken"),
		@NamedQuery(name = "Customeraffliateplatform.findByLasttokenrefreshtime", query = "SELECT c FROM Customeraffliateplatform c WHERE c.lasttokenrefreshtime = :lasttokenrefreshtime") })
public class Customeraffliateplatform implements Serializable {
	private static final long serialVersionUID = 1L;
	@JoinColumn(name = "affiliateid", referencedColumnName = "affiliateid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Customeraffiliate affiliateid;
	@Column(name = "baseurl")
	private String baseurl;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Column(name = "lastknowntoken")
	private String lastknowntoken;
	@Column(name = "lasttokenrefreshtime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lasttokenrefreshtime;
	@Column(name = "platformlogin")
	private String platformlogin;
	@Column(name = "platformpasswordortoken")
	private String platformpasswordortoken;
	@JoinColumn(name = "platformtypeid", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Platformtypes platformtypeid;
	@Column(name = "validtransporters")
	private String validtransporters;

	public Customeraffliateplatform() {
	}

	public Customeraffliateplatform(final Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Customeraffliateplatform)) {
			return false;
		}
		final Customeraffliateplatform other = (Customeraffliateplatform) object;
		return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
	}

	public Customeraffiliate getAffiliateid() {
		return this.affiliateid;
	}

	public String getBaseurl() {
		return this.baseurl;
	}

	public Long getId() {
		return this.id;
	}

	public String getLastknowntoken() {
		return this.lastknowntoken;
	}

	public Date getLasttokenrefreshtime() {
		return this.lasttokenrefreshtime;
	}

	public String getPlatformlogin() {
		return this.platformlogin;
	}

	public String getPlatformpasswordortoken() {
		return this.platformpasswordortoken;
	}

	public Platformtypes getPlatformtypeid() {
		return this.platformtypeid;
	}

	public String getValidtransporters() {
		return this.validtransporters;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.id != null) ? this.id.hashCode() : 0);
		return hash;
	}

	public void setAffiliateid(final Customeraffiliate affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setBaseurl(final String baseurl) {
		this.baseurl = baseurl;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setLastknowntoken(final String lastknowntoken) {
		this.lastknowntoken = lastknowntoken;
	}

	public void setLasttokenrefreshtime(final Date lasttokenrefreshtime) {
		this.lasttokenrefreshtime = lasttokenrefreshtime;
	}

	public void setPlatformlogin(final String platformlogin) {
		this.platformlogin = platformlogin;
	}

	public void setPlatformpasswordortoken(final String platformpasswordortoken) {
		this.platformpasswordortoken = platformpasswordortoken;
	}

	public void setPlatformtypeid(final Platformtypes platformtypeid) {
		this.platformtypeid = platformtypeid;
	}

	public void setValidtransporters(final String validtransporters) {
		this.validtransporters = validtransporters;
	}

	@Override
	public String toString() {
		return "com.camtrack.trips.entities.Customeraffliateplatform[ id=" + this.id + " ]";
	}
}
