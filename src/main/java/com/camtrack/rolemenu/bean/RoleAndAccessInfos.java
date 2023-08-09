//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.rolemenu.bean;

import java.io.Serializable;
import java.util.List;

import com.camtrack.config.Utils;

public class RoleAndAccessInfos implements Serializable {
	private static final long serialVersionUID = 1L;
	List<RightsRole> listrights;
	private Integer roleId;
	private String rolename;

	public List<RightsRole> getListrights() {
		return this.listrights;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public String getRolename() {
		return Utils.StringEscape(this.rolename);
	}

	public void setListrights(final List<RightsRole> listrights) {
		this.listrights = listrights;
	}

	public void setRoleId(final Integer roleId) {
		this.roleId = roleId;
	}

	public void setRolename(final String rolename) {
		this.rolename = rolename;
	}
}
