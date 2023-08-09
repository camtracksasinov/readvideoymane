//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.rolemenu.bean;

import com.camtrack.config.Utils;

public class RoleMenuBean {
	boolean[] addselect;
	int createdBy;
	String createdOn;
	boolean[] deleteselect;
	boolean[] editselect;
	int[] menuId;
	int updatedBy;
	String updatedOn;
	String userRoleId;
	boolean[] viewselect;

	public boolean[] getAddselect() {
		return this.addselect;
	}

	public int getCreatedBy() {
		return this.createdBy;
	}

	public String getCreatedOn() {
		return this.createdOn;
	}

	public boolean[] getDeleteselect() {
		return this.deleteselect;
	}

	public boolean[] getEditselect() {
		return this.editselect;
	}

	public int[] getMenuId() {
		return this.menuId;
	}

	public int getUpdatedBy() {
		return this.updatedBy;
	}

	public String getUpdatedOn() {
		return this.updatedOn;
	}

	public String getUserRoleId() {
		return Utils.StringEscape(this.userRoleId);
	}

	public boolean[] getViewselect() {
		return this.viewselect;
	}

	public void setAddselect(final boolean[] addselect) {
		this.addselect = addselect;
	}

	public void setCreatedBy(final int createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedOn(final String createdOn) {
		this.createdOn = createdOn;
	}

	public void setDeleteselect(final boolean[] deleteselect) {
		this.deleteselect = deleteselect;
	}

	public void setEditselect(final boolean[] editselect) {
		this.editselect = editselect;
	}

	public void setMenuId(final int[] menuId) {
		this.menuId = menuId;
	}

	public void setUpdatedBy(final int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setUpdatedOn(final String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public void setUserRoleId(final String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public void setViewselect(final boolean[] viewselect) {
		this.viewselect = viewselect;
	}
}
