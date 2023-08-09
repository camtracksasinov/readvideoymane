//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;

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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "group_members")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "GroupMembers.findAll", query = "SELECT g FROM GroupMembers g"),
		@NamedQuery(name = "GroupMembers.findByUsername", query = "SELECT g FROM GroupMembers g WHERE g.username = :username"),
		@NamedQuery(name = "GroupMembers.findById", query = "SELECT g FROM GroupMembers g WHERE g.id = :id") })
public class GroupMembers implements Serializable {
	private static final long serialVersionUID = 1L;
	@JoinColumn(name = "group_id", referencedColumnName = "id")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Groups groupId;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Basic(optional = false)
	@Column(name = "username")
	private String username;

	public GroupMembers() {
	}

	public GroupMembers(final Long id) {
		this.id = id;
	}

	public GroupMembers(final Long id, final String username) {
		this.id = id;
		this.username = username;
	}

	public GroupMembers(final String username, final Groups grp) {
		this.username = username;
		this.groupId = grp;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof GroupMembers)) {
			return false;
		}
		final GroupMembers other = (GroupMembers) object;
		return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
	}

	public Groups getGroupId() {
		return this.groupId;
	}

	public Long getId() {
		return this.id;
	}

	public String getUsername() {
		return this.username;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.id != null) ? this.id.hashCode() : 0);
		return hash;
	}

	public void setGroupId(final Groups groupId) {
		this.groupId = groupId;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "com.services.mintrans.entities.GroupMembers[ id=" + this.id + " ]";
	}
}
