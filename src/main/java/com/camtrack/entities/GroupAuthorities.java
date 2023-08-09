//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "group_authorities")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "GroupAuthorities.findAll", query = "SELECT g FROM GroupAuthorities g"),
		@NamedQuery(name = "GroupAuthorities.findByGroupId", query = "SELECT g FROM GroupAuthorities g WHERE g.groupId = :groupId"),
		@NamedQuery(name = "GroupAuthorities.findByAuthority", query = "SELECT g FROM GroupAuthorities g WHERE g.authority = :authority") })
public class GroupAuthorities implements Serializable {
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "authority")
	private String authority;
	@Id
	@Basic(optional = false)
	@Column(name = "group_id")
	private Long groupId;
	@JoinColumn(name = "group_id", referencedColumnName = "id", insertable = false, updatable = false)
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	private Groups groups;

	public GroupAuthorities() {
	}

	public GroupAuthorities(final Long groupId) {
		this.groupId = groupId;
	}

	public GroupAuthorities(final Long groupId, final String authority) {
		this.groupId = groupId;
		this.authority = authority;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof GroupAuthorities)) {
			return false;
		}
		final GroupAuthorities other = (GroupAuthorities) object;
		return (this.groupId != null || other.groupId == null)
				&& (this.groupId == null || this.groupId.equals(other.groupId));
	}

	public String getAuthority() {
		return this.authority;
	}

	public Long getGroupId() {
		return this.groupId;
	}

	public Groups getGroups() {
		return this.groups;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.groupId != null) ? this.groupId.hashCode() : 0);
		return hash;
	}

	public void setAuthority(final String authority) {
		this.authority = authority;
	}

	public void setGroupId(final Long groupId) {
		this.groupId = groupId;
	}

	public void setGroups(final Groups groups) {
		this.groups = groups;
	}

	@Override
	public String toString() {
		return "com.services.mintrans.entities.GroupAuthorities[ groupId=" + this.groupId + " ]";
	}
}
