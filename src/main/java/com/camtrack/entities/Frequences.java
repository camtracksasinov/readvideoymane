//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "frequences")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Frequences.findAll", query = "SELECT f FROM Frequences f"),
		@NamedQuery(name = "Frequences.findByIds", query = "SELECT f FROM Frequences f WHERE f.ids = :ids"),
		@NamedQuery(name = "Frequences.findByFrequences", query = "SELECT f FROM Frequences f WHERE f.frequences = :frequences"),
		@NamedQuery(name = "Frequences.findByUniqueid", query = "SELECT f FROM Frequences f WHERE f.uniqueid = :uniqueid") })
public class Frequences implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "frequences")
	private String frequences;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ids")
	private Short ids;
	@Column(name = "uniqueid")
	private String uniqueid;

	public Frequences() {
	}

	public Frequences(final Short ids) {
		this.ids = ids;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Frequences)) {
			return false;
		}
		final Frequences other = (Frequences) object;
		return (this.ids != null || other.ids == null) && (this.ids == null || this.ids.equals(other.ids));
	}

	public String getFrequences() {
		return this.frequences;
	}

	public Short getIds() {
		return this.ids;
	}

	public String getUniqueid() {
		return this.uniqueid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.ids != null) ? this.ids.hashCode() : 0);
		return hash;
	}

	public void setFrequences(final String frequences) {
		this.frequences = frequences;
	}

	public void setIds(final Short ids) {
		this.ids = ids;
	}

	public void setUniqueid(final String uniqueid) {
		this.uniqueid = uniqueid;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Frequences[ ids=" + this.ids + " ]";
	}
}
