//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.util.Objects;

public class ListIdLongBean {
	public static String listToString(final Long[] getListids) {
		String result = "";
		int k = 0;
		if (!Objects.isNull(getListids) && getListids.length > 0) {
			for (final Long integer : getListids) {
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

	private Long[] listids;

	public Long[] getListids() {
		return this.listids;
	}

	public void setListids(final Long[] listids) {
		this.listids = listids;
	}
}
