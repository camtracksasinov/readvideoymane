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
@Table(name = "lostpassword")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Lostpassword.findAll", query = "SELECT l FROM Lostpassword l"),
		@NamedQuery(name = "Lostpassword.findByLimitdate", query = "SELECT l FROM Lostpassword l WHERE l.limitdate = :limitdate"),
		@NamedQuery(name = "Lostpassword.findByIds", query = "SELECT l FROM Lostpassword l WHERE l.ids = :ids") })
public class Lostpassword implements Serializable {
	private static final long serialVersionUID = 1L;
	@JoinColumn(name = "idlink", referencedColumnName = "ids")
	@ManyToOne(fetch = FetchType.LAZY)
	private Linkloastpassword idlink;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ids")
	private Long ids;
	/**
	 * @JoinColumn(name = "idusers", referencedColumnName = "userid")
	 * @ManyToOne(fetch = FetchType.LAZY) private User idusers;
	 */
	@Basic(optional = false)
	@Column(name = "limitdate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date limitdate;

	public Lostpassword() {
	}

	public Lostpassword(final Long ids) {
		this.ids = ids;
	}

	public Lostpassword(final Long ids, final Date limitdate) {
		this.ids = ids;
		this.limitdate = limitdate;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Lostpassword)) {
			return false;
		}
		final Lostpassword other = (Lostpassword) object;
		return (this.ids != null || other.ids == null) && (this.ids == null || this.ids.equals(other.ids));
	}

	public Linkloastpassword getIdlink() {
		return this.idlink;
	}

	public Long getIds() {
		return this.ids;
	}

	/**
	 * public User getIdusers() { return this.idusers; }
	 * 
	 * public void setIdusers(final User idusers) { this.idusers = idusers; }
	 */

	public Date getLimitdate() {
		return this.limitdate;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.ids != null) ? this.ids.hashCode() : 0);
		return hash;
	}

	public void setIdlink(final Linkloastpassword idlink) {
		this.idlink = idlink;
	}

	public void setIds(final Long ids) {
		this.ids = ids;
	}

	public void setLimitdate(final Date limitdate) {
		this.limitdate = limitdate;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Lostpassword[ ids=" + this.ids + " ]";
	}
}
