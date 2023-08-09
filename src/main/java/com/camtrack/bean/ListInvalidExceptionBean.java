//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.util.Objects;

import com.camtrack.config.Utils;

public class ListInvalidExceptionBean {
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

	private String comments;
	private Boolean invalidorvalid;
	private Integer[] listids;

	private String usercomments;

	public String getComments() {
		return Utils.StringEscape(this.comments);
	}

	public Boolean getInvalidorvalid() {
		return this.invalidorvalid;
	}

	public Integer[] getListids() {
		return this.listids;
	}

	public String getUsercomments() {
		return Utils.StringEscape(this.usercomments);
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setInvalidorvalid(final Boolean invalidorvalid) {
		this.invalidorvalid = invalidorvalid;
	}

	public void setListids(final Integer[] listids) {
		this.listids = listids;
	}

	public void setUsercomments(final String usercomments) {
		this.usercomments = usercomments;
	}
}
