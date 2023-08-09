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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "reelroleusers")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Reelroleusers.findAll", query = "SELECT r FROM Reelroleusers r"),
		@NamedQuery(name = "Reelroleusers.findByIds", query = "SELECT r FROM Reelroleusers r WHERE r.ids = :ids"),
		@NamedQuery(name = "Reelroleusers.findByRolenames", query = "SELECT r FROM Reelroleusers r WHERE r.rolenames = :rolenames") })
public class Reelroleusers implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	@OneToMany(mappedBy = "reelroleusers", fetch = FetchType.LAZY)
	private List<Accessrights> accessrightsList;
	@Id
	@JsonProperty("roleid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ids")
	private Integer ids;
	@JsonIgnore
	@JoinColumn(name = "idtyperole", referencedColumnName = "userroleid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Userrole idtyperole;
	@Column(name = "rolenames")
	private String rolenames;
	@JsonIgnore
	@Basic(optional = false)
	@Column(name = "status")
	private int status;
	@JsonIgnore
	@OneToMany(mappedBy = "reelrole", fetch = FetchType.LAZY)
	private List<User> userList;

	public Reelroleusers() {
	}

	public Reelroleusers(final Integer ids) {
		this.ids = ids;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Reelroleusers)) {
			return false;
		}
		final Reelroleusers other = (Reelroleusers) object;
		return (this.ids != null || other.ids == null) && (this.ids == null || this.ids.equals(other.ids));
	}

	@XmlTransient
	public List<Accessrights> getAccessrightsList() {
		return this.accessrightsList;
	}

	public Integer getIds() {
		return this.ids;
	}

	public Userrole getIdtyperole() {
		return this.idtyperole;
	}

	public String getRolenames() {
		return this.rolenames;
	}

	public int getStatus() {
		return this.status;
	}

	public List<User> getUserList() {
		return this.userList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.ids != null) ? this.ids.hashCode() : 0);
		return hash;
	}

	public void setAccessrightsList(final List<Accessrights> accessrightsList) {
		this.accessrightsList = accessrightsList;
	}

	public void setIds(final Integer ids) {
		this.ids = ids;
	}

	public void setIdtyperole(final Userrole idtyperole) {
		this.idtyperole = idtyperole;
	}

	public void setRolenames(final String rolenames) {
		this.rolenames = rolenames;
	}

	public void setStatus(final int status) {
		this.status = status;
	}

	public void setUserList(final List<User> userList) {
		this.userList = userList;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Reelroleusers[ ids=" + this.ids + " ]";
	}
}
