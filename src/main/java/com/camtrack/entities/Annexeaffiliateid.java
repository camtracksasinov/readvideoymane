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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "annexeaffiliateid")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Annexeaffiliateid.findAll", query = "SELECT a FROM Annexeaffiliateid a"),
		@NamedQuery(name = "Annexeaffiliateid.findByDirection", query = "SELECT a FROM Annexeaffiliateid a WHERE a.direction = :direction"),
		@NamedQuery(name = "Annexeaffiliateid.findByZone", query = "SELECT a FROM Annexeaffiliateid a WHERE a.zone = :zone"),
		@NamedQuery(name = "Annexeaffiliateid.findByCodepays", query = "SELECT a FROM Annexeaffiliateid a WHERE a.codepays = :codepays"),
		@NamedQuery(name = "Annexeaffiliateid.findByPays", query = "SELECT a FROM Annexeaffiliateid a WHERE a.pays = :pays"),
		@NamedQuery(name = "Annexeaffiliateid.findByCodefilialle", query = "SELECT a FROM Annexeaffiliateid a WHERE a.codefilialle = :codefilialle"),
		@NamedQuery(name = "Annexeaffiliateid.findByFilliale", query = "SELECT a FROM Annexeaffiliateid a WHERE a.filliale = :filliale"),
		@NamedQuery(name = "Annexeaffiliateid.findByIds", query = "SELECT a FROM Annexeaffiliateid a WHERE a.ids = :ids") })
public class Annexeaffiliateid implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "branch")
	private String branch;
	@Column(name = "codefilialle")
	private String codefilialle;
	@Column(name = "codepays")
	private String codepays;
	@OneToMany(mappedBy = "anexeinfos", fetch = FetchType.LAZY)
	private List<Customeraffiliate> customeraffiliateList;
	@Column(name = "direction")
	private String direction;
	@Column(name = "filliale")
	private String filliale;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ids")
	private Integer ids;
	@Column(name = "pays")
	private String pays;
	@Column(name = "zone")
	private String zone;

	public Annexeaffiliateid() {
	}

	public Annexeaffiliateid(final Integer ids) {
		this.ids = ids;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Annexeaffiliateid)) {
			return false;
		}
		final Annexeaffiliateid other = (Annexeaffiliateid) object;
		return (this.ids != null || other.ids == null) && (this.ids == null || this.ids.equals(other.ids));
	}

	public String getBranch() {
		return this.branch;
	}

	public String getCodefilialle() {
		return this.codefilialle;
	}

	public String getCodepays() {
		return this.codepays;
	}

	@XmlTransient
	public List<Customeraffiliate> getCustomeraffiliateList() {
		return this.customeraffiliateList;
	}

	public String getDirection() {
		return this.direction;
	}

	public String getFilliale() {
		return this.filliale;
	}

	public Integer getIds() {
		return this.ids;
	}

	public String getPays() {
		return this.pays;
	}

	public String getZone() {
		return this.zone;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.ids != null) ? this.ids.hashCode() : 0);
		return hash;
	}

	public void setBranch(final String branch) {
		this.branch = branch;
	}

	public void setCodefilialle(final String codefilialle) {
		this.codefilialle = codefilialle;
	}

	public void setCodepays(final String codepays) {
		this.codepays = codepays;
	}

	public void setCustomeraffiliateList(final List<Customeraffiliate> customeraffiliateList) {
		this.customeraffiliateList = customeraffiliateList;
	}

	public void setDirection(final String direction) {
		this.direction = direction;
	}

	public void setFilliale(final String filliale) {
		this.filliale = filliale;
	}

	public void setIds(final Integer ids) {
		this.ids = ids;
	}

	public void setPays(final String pays) {
		this.pays = pays;
	}

	public void setZone(final String zone) {
		this.zone = zone;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Annexeaffiliateid[ ids=" + this.ids + " ]";
	}
}
