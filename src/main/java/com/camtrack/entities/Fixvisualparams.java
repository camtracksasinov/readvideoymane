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
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "fixvisualparams")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Fixvisualparams.findAll", query = "SELECT f FROM Fixvisualparams f"),
		@NamedQuery(name = "Fixvisualparams.findByIds", query = "SELECT f FROM Fixvisualparams f WHERE f.ids = :ids"),
		@NamedQuery(name = "Fixvisualparams.findByNames", query = "SELECT f FROM Fixvisualparams f WHERE f.names = :names") })
public class Fixvisualparams implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ids")
	private Integer ids;
	@Column(name = "names")
	private String names;
	@JsonIgnore
	@OneToMany(mappedBy = "idfixe", fetch = FetchType.LAZY)
	private List<Visualparameter> visualparameterList;

	public Fixvisualparams() {
	}

	public Fixvisualparams(final Integer ids) {
		this.ids = ids;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Fixvisualparams)) {
			return false;
		}
		final Fixvisualparams other = (Fixvisualparams) object;
		return (this.ids != null || other.ids == null) && (this.ids == null || this.ids.equals(other.ids));
	}

	public Integer getIds() {
		return this.ids;
	}

	public String getNames() {
		return this.names;
	}

	@XmlTransient
	public List<Visualparameter> getVisualparameterList() {
		return this.visualparameterList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.ids != null) ? this.ids.hashCode() : 0);
		return hash;
	}

	public void setIds(final Integer ids) {
		this.ids = ids;
	}

	public void setNames(final String names) {
		this.names = names;
	}

	public void setVisualparameterList(final List<Visualparameter> visualparameterList) {
		this.visualparameterList = visualparameterList;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Fixvisualparams[ ids=" + this.ids + " ]";
	}
}
