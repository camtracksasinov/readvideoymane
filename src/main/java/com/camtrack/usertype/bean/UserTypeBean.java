//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.usertype.bean;

import com.camtrack.config.Utils;

public class UserTypeBean {
	String createdBy;
	String createdOn;
	String updatedBy;
	String updatedOn;
	int userTypeId;
	String userTypeName;
	int userTypeStatus;

	public String getCreatedBy() {
		return this.createdBy;
	}

	public String getCreatedOn() {
		return this.createdOn;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public String getUpdatedOn() {
		return this.updatedOn;
	}

	public int getUserTypeId() {
		return this.userTypeId;
	}

	public String getUserTypeName() {
		return Utils.StringEscape(this.userTypeName);
	}

	public int getUserTypeStatus() {
		return this.userTypeStatus;
	}

	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedOn(final String createdOn) {
		this.createdOn = createdOn;
	}

	public void setUpdatedBy(final String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setUpdatedOn(final String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public void setUserTypeId(final int userTypeId) {
		this.userTypeId = userTypeId;
	}

	public void setUserTypeName(final String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public void setUserTypeStatus(final int userTypeStatus) {
		this.userTypeStatus = userTypeStatus;
	}

	@Override
	public String toString() {
		return "UserTypeBean [userTypeId=" + this.userTypeId + ", userTypeName=" + this.userTypeName + ", createdOn="
				+ this.createdOn + ", createdBy=" + this.createdBy + ", updatedOn=" + this.updatedOn + ", updatedBy="
				+ this.updatedBy + ", userTypeStatus=" + this.userTypeStatus + "]";
	}
}
