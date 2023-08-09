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
@Table(name = "listreports")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Listreports.findAll", query = "SELECT l FROM Listreports l"),
		@NamedQuery(name = "Listreports.findByIds", query = "SELECT l FROM Listreports l WHERE l.ids = :ids"),
		@NamedQuery(name = "Listreports.findByReportname", query = "SELECT l FROM Listreports l WHERE l.reportname = :reportname"),
		@NamedQuery(name = "Listreports.findByCodereports", query = "SELECT l FROM Listreports l WHERE l.codereports = :codereports") })
public class Listreports implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "codereports")
	private Integer codereports;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ids")
	private Integer ids;
	@Column(name = "reportname")
	private String reportname;

	public Listreports() {
	}

	public Listreports(final Integer ids) {
		this.ids = ids;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Listreports)) {
			return false;
		}
		final Listreports other = (Listreports) object;
		return (this.ids != null || other.ids == null) && (this.ids == null || this.ids.equals(other.ids));
	}

	public Integer getCodereports() {
		return this.codereports;
	}

	public Integer getIds() {
		return this.ids;
	}

	public String getReportname() {
		return this.reportname;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.ids != null) ? this.ids.hashCode() : 0);
		return hash;
	}

	public void setCodereports(final Integer codereports) {
		this.codereports = codereports;
	}

	public void setIds(final Integer ids) {
		this.ids = ids;
	}

	public void setReportname(final String reportname) {
		this.reportname = reportname;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Listreports[ ids=" + this.ids + " ]";
	}
}
