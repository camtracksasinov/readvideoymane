//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.util.Objects;

public class ListIdBean {
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

	private Integer[] listids;

	public Integer[] getListids() {
		return this.listids;
	}

	public void setListids(final Integer[] listids) {
		this.listids = listids;
	}
}
