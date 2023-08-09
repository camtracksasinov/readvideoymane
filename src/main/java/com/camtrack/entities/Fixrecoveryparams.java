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
@Table(name = "fixrecoveryparams")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Fixrecoveryparams.findAll", query = "SELECT f FROM Fixrecoveryparams f"),
		@NamedQuery(name = "Fixrecoveryparams.findByIds", query = "SELECT f FROM Fixrecoveryparams f WHERE f.ids = :ids"),
		@NamedQuery(name = "Fixrecoveryparams.findByNames", query = "SELECT f FROM Fixrecoveryparams f WHERE f.names = :names") })
public class Fixrecoveryparams implements Serializable {
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
	private List<Recovery> recoveryList;

	public Fixrecoveryparams() {
	}

	public Fixrecoveryparams(final Integer ids) {
		this.ids = ids;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Fixrecoveryparams)) {
			return false;
		}
		final Fixrecoveryparams other = (Fixrecoveryparams) object;
		return (this.ids != null || other.ids == null) && (this.ids == null || this.ids.equals(other.ids));
	}

	public Integer getIds() {
		return this.ids;
	}

	public String getNames() {
		return this.names;
	}

	@XmlTransient
	public List<Recovery> getRecoveryList() {
		return this.recoveryList;
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

	public void setRecoveryList(final List<Recovery> recoveryList) {
		this.recoveryList = recoveryList;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Fixrecoveryparams[ ids=" + this.ids + " ]";
	}
}
