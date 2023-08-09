//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.bean;

import com.camtrack.config.Utils;

public class UserRoleBean {
	private int hierarchy;
	private String name;
	private int status;
	private int userroleid;

	public UserRoleBean() {
	}

	public UserRoleBean(final int userroleid, final String name, final int status, final int hierarchy) {
		this.userroleid = userroleid;
		this.name = name;
		this.status = status;
		this.hierarchy = hierarchy;
	}

	public int getHierarchy() {
		return this.hierarchy;
	}

	public String getName() {
		return Utils.StringEscape(this.name);
	}

	public int getStatus() {
		return this.status;
	}

	public int getUserroleid() {
		return this.userroleid;
	}

	public void setHierarchy(final int hierarchy) {
		this.hierarchy = hierarchy;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setStatus(final int status) {
		this.status = status;
	}

	public void setUserroleid(final int userroleid) {
		this.userroleid = userroleid;
	}

	@Override
	public String toString() {
		return "UserRoleBean [userroleid=" + this.userroleid + ", name=" + this.name + ", status=" + this.status
				+ ", hierarchy=" + this.hierarchy + "]";
	}
}
