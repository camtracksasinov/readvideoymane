//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.util.Objects;

public class RewardRankingBean {
	public static String listToString(final Integer[] getListids) {
		String result = null;
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

	private int endmonth;
	private int endyear;
	private Integer[] listaffiliateids;
	private Integer[] listclientids;
	private Integer[] listdriverids;
	private Integer[] listtransporterids;
	private int startmonth;

	private int startyear;

	public int getEndmonth() {
		return this.endmonth;
	}

	public int getEndyear() {
		return this.endyear;
	}

	public Integer[] getListaffiliateids() {
		if (Objects.isNull(this.listaffiliateids) || this.listaffiliateids.length == 0) {
			return null;
		}
		return this.listaffiliateids;
	}

	public Integer[] getListclientids() {
		if (Objects.isNull(this.listclientids) || this.listclientids.length == 0) {
			return null;
		}
		return this.listclientids;
	}

	public Integer[] getListdriverids() {
		if (Objects.isNull(this.listdriverids) || this.listdriverids.length == 0) {
			return null;
		}
		return this.listdriverids;
	}

	public Integer[] getListtransporterids() {
		if (Objects.isNull(this.listtransporterids) || this.listtransporterids.length == 0) {
			return null;
		}
		return this.listtransporterids;
	}

	public int getStartmonth() {
		return this.startmonth;
	}

	public int getStartyear() {
		return this.startyear;
	}

	public void setEndmonth(final int endmonth) {
		this.endmonth = endmonth;
	}

	public void setEndyear(final int endyear) {
		this.endyear = endyear;
	}

	public void setListaffiliateids(final Integer[] listaffiliateids) {
		this.listaffiliateids = listaffiliateids;
	}

	public void setListclientids(final Integer[] listclientids) {
		this.listclientids = listclientids;
	}

	public void setListdriverids(final Integer[] listdriverids) {
		this.listdriverids = listdriverids;
	}

	public void setListtransporterids(final Integer[] listtransporterids) {
		this.listtransporterids = listtransporterids;
	}

	public void setStartmonth(final int startmonth) {
		this.startmonth = startmonth;
	}

	public void setStartyear(final int startyear) {
		this.startyear = startyear;
	}
}
