//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "timerange")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Timerange.findAll", query = "SELECT t FROM Timerange t"),
		@NamedQuery(name = "Timerange.findByNames", query = "SELECT t FROM Timerange t WHERE t.names = :names"),
		@NamedQuery(name = "Timerange.findByIds", query = "SELECT t FROM Timerange t WHERE t.ids = :ids") })
public class Timerange implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer codes;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ids")
	private Short ids;
	@Column(name = "names")
	private String names;
	@JsonIgnore
	@OneToMany(mappedBy = "ranges", fetch = FetchType.LAZY)
	private List<Reportautomatics> reportautomaticsList;

	public Timerange() {
	}

	public Timerange(final Short ids) {
		this.ids = ids;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Timerange)) {
			return false;
		}
		final Timerange other = (Timerange) object;
		return (this.ids != null || other.ids == null) && (this.ids == null || this.ids.equals(other.ids));
	}

	public Integer getCodes() {
		return this.codes;
	}

	public Short getIds() {
		return this.ids;
	}

	public String getNames() {
		return this.names;
	}

	public List<Reportautomatics> getReportautomaticsList() {
		return this.reportautomaticsList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.ids != null) ? this.ids.hashCode() : 0);
		return hash;
	}

	public void setCodes(final Integer codes) {
		this.codes = codes;
	}

	public void setIds(final Short ids) {
		this.ids = ids;
	}

	public void setNames(final String names) {
		this.names = names;
	}

	public void setReportautomaticsList(final List<Reportautomatics> reportautomaticsList) {
		this.reportautomaticsList = reportautomaticsList;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Timerange[ ids=" + this.ids + " ]";
	}
}
