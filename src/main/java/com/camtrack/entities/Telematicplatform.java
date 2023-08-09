//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.math.BigInteger;

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
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "telematicplatform")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Telematicplatform.findAll", query = "SELECT t FROM Telematicplatform t"),
		@NamedQuery(name = "Telematicplatform.findById", query = "SELECT t FROM Telematicplatform t WHERE t.id = :id"),
		@NamedQuery(name = "Telematicplatform.findByLogin", query = "SELECT t FROM Telematicplatform t WHERE t.login = :login"),
		@NamedQuery(name = "Telematicplatform.findByPasswordortoken", query = "SELECT t FROM Telematicplatform t WHERE t.passwordortoken = :passwordortoken"),
		@NamedQuery(name = "Telematicplatform.findByAffiliateid", query = "SELECT t FROM Telematicplatform t WHERE t.affiliateid = :affiliateid"),
		@NamedQuery(name = "Telematicplatform.findByUtcoffset", query = "SELECT t FROM Telematicplatform t WHERE t.utcoffset = :utcoffset") })
public class Telematicplatform implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "affiliateid")
	private BigInteger affiliateid;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Column(name = "login")
	private String login;
	@Column(name = "passwordortoken")
	private String passwordortoken;
	@JoinColumn(name = "platformtype", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Platformtypes platformtype;
	@Column(name = "utcoffset")
	private Integer utcoffset;

	public Telematicplatform() {
	}

	public Telematicplatform(final Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Telematicplatform)) {
			return false;
		}
		final Telematicplatform other = (Telematicplatform) object;
		return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
	}

	public BigInteger getAffiliateid() {
		return this.affiliateid;
	}

	public Long getId() {
		return this.id;
	}

	public String getLogin() {
		return this.login;
	}

	public String getPasswordortoken() {
		return this.passwordortoken;
	}

	public Platformtypes getPlatformtype() {
		return this.platformtype;
	}

	public Integer getUtcoffset() {
		return this.utcoffset;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.id != null) ? this.id.hashCode() : 0);
		return hash;
	}

	public void setAffiliateid(final BigInteger affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public void setPasswordortoken(final String passwordortoken) {
		this.passwordortoken = passwordortoken;
	}

	public void setPlatformtype(final Platformtypes platformtype) {
		this.platformtype = platformtype;
	}

	public void setUtcoffset(final Integer utcoffset) {
		this.utcoffset = utcoffset;
	}

	@Override
	public String toString() {
		return "com.camtrack.trips.entities.Telematicplatform[ id=" + this.id + " ]";
	}
}
