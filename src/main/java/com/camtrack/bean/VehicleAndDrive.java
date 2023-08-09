//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.util.Objects;

public class VehicleAndDrive {
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

	private Integer[] listaffiliateids;
	private Integer[] listclientids;

	private Integer[] listtransporterids;

	public Integer[] getListaffiliateids() {
		return this.listaffiliateids;
	}

	public Integer[] getListclientids() {
		return this.listclientids;
	}

	public Integer[] getListtransporterids() {
		return this.listtransporterids;
	}

	public void setListaffiliateids(final Integer[] listaffiliateids) {
		this.listaffiliateids = listaffiliateids;
	}

	public void setListclientids(final Integer[] listclientids) {
		this.listclientids = listclientids;
	}

	public void setListtransporterids(final Integer[] listtransporterids) {
		this.listtransporterids = listtransporterids;
	}
}
