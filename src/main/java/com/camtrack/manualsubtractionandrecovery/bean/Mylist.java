//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.manualsubtractionandrecovery.bean;

import java.util.Objects;

public class Mylist {
	public static String listToString(final Integer[] getListids) {
		String result = "";
		int k = 0;
		if (!Objects.isNull(getListids) && getListids.length > 0) {
			for (final Integer integer : getListids) {
				if (k == 0) {
					result = "" + integer;
				} else {
					result = result + "," + integer;
				}
				++k;
			}
		}
		return result;
	}

	Integer[] affiliateid;
	Integer[] customerid;
	Integer[] driverid;

	Integer[] transporterid;

	public Mylist() {
	}

	public Mylist(final Integer[] customerid, final Integer[] affiliateid, final Integer[] transporterid,
			final Integer[] driverid) {
		this.customerid = customerid;
		this.affiliateid = affiliateid;
		this.transporterid = transporterid;
		this.driverid = driverid;
	}

	public Integer[] getAffiliateid() {
		return this.affiliateid;
	}

	public Integer[] getCustomerid() {
		return this.customerid;
	}

	public Integer[] getDriverid() {
		return this.driverid;
	}

	public Integer[] getTransporterid() {
		return this.transporterid;
	}

	public void setAffiliateid(final Integer[] affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setCustomerid(final Integer[] customerid) {
		this.customerid = customerid;
	}

	public void setDriverid(final Integer[] driverid) {
		this.driverid = driverid;
	}

	public void setTransporterid(final Integer[] transporterid) {
		this.transporterid = transporterid;
	}
}
