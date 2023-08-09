//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.io.Serializable;

import com.camtrack.config.Utils;

public class RoleBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer roleid;
	private String rolename;
	private Integer typeroleid;

	public RoleBean(final Integer roleid, final String rolename, final Integer typeroleid) {
		this.roleid = roleid;
		this.rolename = rolename;
		this.typeroleid = typeroleid;
	}

	public Integer getRoleid() {
		return this.roleid;
	}

	public String getRolename() {
		return Utils.StringEscape(this.rolename);
	}

	public Integer getTyperoleid() {
		return this.typeroleid;
	}

	public void setRoleid(final Integer roleid) {
		this.roleid = roleid;
	}

	public void setRolename(final String rolename) {
		this.rolename = rolename;
	}

	public void setTyperoleid(final Integer typeroleid) {
		this.typeroleid = typeroleid;
	}
}
