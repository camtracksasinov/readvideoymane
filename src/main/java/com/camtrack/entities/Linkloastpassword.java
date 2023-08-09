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
@Table(name = "linkloastpassword")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Linkloastpassword.findAll", query = "SELECT l FROM Linkloastpassword l"),
		@NamedQuery(name = "Linkloastpassword.findByValues", query = "SELECT l FROM Linkloastpassword l WHERE l.values = :values"),
		@NamedQuery(name = "Linkloastpassword.findByUniquevalues", query = "SELECT l FROM Linkloastpassword l WHERE l.uniquevalues = :uniquevalues"),
		@NamedQuery(name = "Linkloastpassword.findByIds", query = "SELECT l FROM Linkloastpassword l WHERE l.ids = :ids") })
public class Linkloastpassword implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ids")
	private Short ids;
	@JsonIgnore
	@OneToMany(mappedBy = "idlink", fetch = FetchType.LAZY)
	private List<Lostpassword> lostpasswordList;
	@Basic(optional = false)
	@Column(name = "uniquevalues")
	private short uniquevalues;
	@Column(name = "values")
	private String values;

	public Linkloastpassword() {
	}

	public Linkloastpassword(final Short ids) {
		this.ids = ids;
	}

	public Linkloastpassword(final Short ids, final short uniquevalues) {
		this.ids = ids;
		this.uniquevalues = uniquevalues;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Linkloastpassword)) {
			return false;
		}
		final Linkloastpassword other = (Linkloastpassword) object;
		return (this.ids != null || other.ids == null) && (this.ids == null || this.ids.equals(other.ids));
	}

	public Short getIds() {
		return this.ids;
	}

	@XmlTransient
	public List<Lostpassword> getLostpasswordList() {
		return this.lostpasswordList;
	}

	public short getUniquevalues() {
		return this.uniquevalues;
	}

	public String getValues() {
		return this.values;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.ids != null) ? this.ids.hashCode() : 0);
		return hash;
	}

	public void setIds(final Short ids) {
		this.ids = ids;
	}

	public void setLostpasswordList(final List<Lostpassword> lostpasswordList) {
		this.lostpasswordList = lostpasswordList;
	}

	public void setUniquevalues(final short uniquevalues) {
		this.uniquevalues = uniquevalues;
	}

	public void setValues(final String values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Linkloastpassword[ ids=" + this.ids + " ]";
	}
}
