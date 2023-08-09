//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "\"groups\"")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Groups.findAll", query = "SELECT g FROM Groups g"),
		@NamedQuery(name = "Groups.findById", query = "SELECT g FROM Groups g WHERE g.id = :id"),
		@NamedQuery(name = "Groups.findByGroupName", query = "SELECT g FROM Groups g WHERE g.groupName = :groupName") })
public class Groups implements Serializable {
	private static final long serialVersionUID = 1L;
	@OneToOne(cascade = { CascadeType.ALL }, mappedBy = "groups", fetch = FetchType.LAZY)
	private GroupAuthorities groupAuthorities;
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "groupId", fetch = FetchType.LAZY)
	private List<GroupMembers> groupMembersList;
	@Basic(optional = false)
	@Column(name = "group_name")
	private String groupName;
	@Id
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	public Groups() {
	}

	public Groups(final Long id) {
		this.id = id;
	}

	public Groups(final Long id, final String groupName) {
		this.id = id;
		this.groupName = groupName;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Groups)) {
			return false;
		}
		final Groups other = (Groups) object;
		return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
	}

	public GroupAuthorities getGroupAuthorities() {
		return this.groupAuthorities;
	}

	@XmlTransient
	public List<GroupMembers> getGroupMembersList() {
		return this.groupMembersList;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public Long getId() {
		return this.id;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.id != null) ? this.id.hashCode() : 0);
		return hash;
	}

	public void setGroupAuthorities(final GroupAuthorities groupAuthorities) {
		this.groupAuthorities = groupAuthorities;
	}

	public void setGroupMembersList(final List<GroupMembers> groupMembersList) {
		this.groupMembersList = groupMembersList;
	}

	public void setGroupName(final String groupName) {
		this.groupName = groupName;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "com.services.mintrans.entities.Groups[ id=" + this.id + " ]";
	}
}
