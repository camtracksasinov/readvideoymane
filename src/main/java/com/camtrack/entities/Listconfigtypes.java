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
@Table(name = "listconfigtypes")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Listconfigtypes.findAll", query = "SELECT l FROM Listconfigtypes l"),
		@NamedQuery(name = "Listconfigtypes.findByIds", query = "SELECT l FROM Listconfigtypes l WHERE l.ids = :ids"),
		@NamedQuery(name = "Listconfigtypes.findByConfigtype", query = "SELECT l FROM Listconfigtypes l WHERE l.configtype = :configtype") })
public class Listconfigtypes implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "configtype")
	private String configtype;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ids")
	private Short ids;
	@JsonIgnore
	@OneToMany(mappedBy = "ldtypeconfig", fetch = FetchType.LAZY)
	private List<Userlogsactivity> userlogsactivityList;

	public Listconfigtypes() {
	}

	public Listconfigtypes(final Short ids) {
		this.ids = ids;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Listconfigtypes)) {
			return false;
		}
		final Listconfigtypes other = (Listconfigtypes) object;
		return (this.ids != null || other.ids == null) && (this.ids == null || this.ids.equals(other.ids));
	}

	public String getConfigtype() {
		return this.configtype;
	}

	public Short getIds() {
		return this.ids;
	}

	@XmlTransient
	public List<Userlogsactivity> getUserlogsactivityList() {
		return this.userlogsactivityList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.ids != null) ? this.ids.hashCode() : 0);
		return hash;
	}

	public void setConfigtype(final String configtype) {
		this.configtype = configtype;
	}

	public void setIds(final Short ids) {
		this.ids = ids;
	}

	public void setUserlogsactivityList(final List<Userlogsactivity> userlogsactivityList) {
		this.userlogsactivityList = userlogsactivityList;
	}

	@Override
	public String toString() {
		return "com.ymane.entities.Listconfigtypes[ ids=" + this.ids + " ]";
	}
}
