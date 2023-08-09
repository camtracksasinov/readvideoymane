//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.bean;

import java.util.Arrays;

public class AffiliateParameterMappingBean {
	int clientaffiliateid;
	String createdby;
	String createdon;
	int customerid;
	String[] label;
	int[] parameterid;
	String[] status;
	String updatedby;
	String updatedon;

	public AffiliateParameterMappingBean() {
	}

	public AffiliateParameterMappingBean(final int customerid, final int clientaffiliateid, final String[] label,
			final int[] parameterid, final String[] status, final String createdon, final String createdby,
			final String updatedon, final String updatedby) {
		this.customerid = customerid;
		this.clientaffiliateid = clientaffiliateid;
		this.label = label;
		this.parameterid = parameterid;
		this.status = status;
		this.createdon = createdon;
		this.createdby = createdby;
		this.updatedon = updatedon;
		this.updatedby = updatedby;
	}

	public int getClientaffiliateid() {
		return this.clientaffiliateid;
	}

	public String getCreatedby() {
		return this.createdby;
	}

	public String getCreatedon() {
		return this.createdon;
	}

	public int getCustomerid() {
		return this.customerid;
	}

	public String[] getLabel() {
		return this.label;
	}

	public int[] getParameterid() {
		return this.parameterid;
	}

	public String[] getStatus() {
		return this.status;
	}

	public String getUpdatedby() {
		return this.updatedby;
	}

	public String getUpdatedon() {
		return this.updatedon;
	}

	public void setClientaffiliateid(final int clientaffiliateid) {
		this.clientaffiliateid = clientaffiliateid;
	}

	public void setCreatedby(final String createdby) {
		this.createdby = createdby;
	}

	public void setCreatedon(final String createdon) {
		this.createdon = createdon;
	}

	public void setCustomerid(final int customerid) {
		this.customerid = customerid;
	}

	public void setLabel(final String[] label) {
		this.label = label;
	}

	public void setParameterid(final int[] parameterid) {
		this.parameterid = parameterid;
	}

	public void setStatus(final String[] status) {
		this.status = status;
	}

	public void setUpdatedby(final String updatedby) {
		this.updatedby = updatedby;
	}

	public void setUpdatedon(final String updatedon) {
		this.updatedon = updatedon;
	}

	@Override
	public String toString() {
		return "AffiliateParameterMappingBean [customerid=" + this.customerid + ", clientaffiliateid="
				+ this.clientaffiliateid + ", label=" + Arrays.toString(this.label) + ", parameterid="
				+ Arrays.toString(this.parameterid) + ", status=" + Arrays.toString(this.status) + ", createdon="
				+ this.createdon + ", createdby=" + this.createdby + ", updatedon=" + this.updatedon + ", updatedby="
				+ this.updatedby + "]";
	}
}
