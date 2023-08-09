//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.application.bean;

import java.util.Arrays;

public class ApplicationBean {
	private int[] applnid;
	private int clientaffapplicationid;
	private int[] clientaffiliateid;
	private int clientid;

	public ApplicationBean() {
	}

	public ApplicationBean(final int clientaffapplicationid, final int[] applnid, final int clientid,
			final int[] clientaffiliateid) {
		this.clientaffapplicationid = clientaffapplicationid;
		this.applnid = applnid;
		this.clientid = clientid;
		this.clientaffiliateid = clientaffiliateid;
	}

	public int[] getApplnid() {
		return this.applnid;
	}

	public int getClientaffapplicationid() {
		return this.clientaffapplicationid;
	}

	public int[] getClientaffiliateid() {
		return this.clientaffiliateid;
	}

	public int getClientid() {
		return this.clientid;
	}

	public void setApplnid(final int[] applnid) {
		this.applnid = applnid;
	}

	public void setClientaffapplicationid(final int clientaffapplicationid) {
		this.clientaffapplicationid = clientaffapplicationid;
	}

	public void setClientaffiliateid(final int[] clientaffiliateid) {
		this.clientaffiliateid = clientaffiliateid;
	}

	public void setClientid(final int clientid) {
		this.clientid = clientid;
	}

	@Override
	public String toString() {
		return "ApplicationBean [clientaffapplicationid=" + this.clientaffapplicationid + ", applnid=" + this.applnid
				+ ", clientid=" + this.clientid + ", clientaffiliateid=" + Arrays.toString(this.clientaffiliateid)
				+ "]";
	}
}
