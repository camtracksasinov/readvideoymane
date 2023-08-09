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
@Table(name = "language")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Language.findAll", query = "SELECT l FROM Language l"),
		@NamedQuery(name = "Language.findByLanguageid", query = "SELECT l FROM Language l WHERE l.languageid = :languageid"),
		@NamedQuery(name = "Language.findByName", query = "SELECT l FROM Language l WHERE l.name = :name") })
public class Language implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "languageid")
	private Integer languageid;
	@Column(name = "name")
	private String name;
	@JsonIgnore
	@OneToMany(mappedBy = "languageid", fetch = FetchType.LAZY)
	private List<User> usersList;

	public Language() {
	}

	public Language(final Integer languageid) {
		this.languageid = languageid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Language)) {
			return false;
		}
		final Language other = (Language) object;
		return (this.languageid != null || other.languageid == null)
				&& (this.languageid == null || this.languageid.equals(other.languageid));
	}

	public Integer getLanguageid() {
		return this.languageid;
	}

	public String getName() {
		return this.name;
	}

	@XmlTransient
	public List<User> getUsersList() {
		return this.usersList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.languageid != null) ? this.languageid.hashCode() : 0);
		return hash;
	}

	public void setLanguageid(final Integer languageid) {
		this.languageid = languageid;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setUsersList(final List<User> usersList) {
		this.usersList = usersList;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Language[ languageid=" + this.languageid + " ]";
	}
}
