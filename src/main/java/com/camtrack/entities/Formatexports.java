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
@Table(name = "formatexports")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Formatexports.findAll", query = "SELECT f FROM Formatexports f"),
		@NamedQuery(name = "Formatexports.findByIds", query = "SELECT f FROM Formatexports f WHERE f.ids = :ids"),
		@NamedQuery(name = "Formatexports.findByFormat", query = "SELECT f FROM Formatexports f WHERE f.format = :format"),
		@NamedQuery(name = "Formatexports.findByUniqueid", query = "SELECT f FROM Formatexports f WHERE f.uniqueid = :uniqueid") })
public class Formatexports implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "format")
	private String format;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ids")
	private Short ids;
	@Column(name = "uniqueid")
	private String uniqueid;

	public Formatexports() {
	}

	public Formatexports(final Short ids) {
		this.ids = ids;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Formatexports)) {
			return false;
		}
		final Formatexports other = (Formatexports) object;
		return (this.ids != null || other.ids == null) && (this.ids == null || this.ids.equals(other.ids));
	}

	public String getFormat() {
		return this.format;
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

	public void setFormat(final String format) {
		this.format = format;
	}

	public void setIds(final Short ids) {
		this.ids = ids;
	}

	public void setUniqueid(final String uniqueid) {
		this.uniqueid = uniqueid;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Formatexports[ ids=" + this.ids + " ]";
	}
}
