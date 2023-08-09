//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.bean;

public class RawstatisticsBean {
	private Integer[] affiliateId;
	private Integer[] clientid;
	private Integer[] driverid;
	private int endmonth;
	private int endyear;
	private int startmonth;
	private int startyear;
	private Integer level;
	private Integer[] transporterid;

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer[] getAffiliateId() {
		return this.affiliateId;
	}

	public Integer[] getClientid() {
		return this.clientid;
	}

	public Integer[] getDriverid() {
		return this.driverid;
	}

	public int getEndmonth() {
		return this.endmonth;
	}

	public int getEndyear() {
		return this.endyear;
	}

	public int getStartmonth() {
		return this.startmonth;
	}

	public int getStartyear() {
		return this.startyear;
	}

	public Integer[] getTransporterid() {
		return this.transporterid;
	}

	public void setAffiliateId(final Integer[] affiliateId) {
		this.affiliateId = affiliateId;
	}

	public void setClientid(final Integer[] clientid) {
		this.clientid = clientid;
	}

	public void setDriverid(final Integer[] driverid) {
		this.driverid = driverid;
	}

	public void setEndmonth(final int endmonth) {
		this.endmonth = endmonth;
	}

	public void setEndyear(final int endyear) {
		this.endyear = endyear;
	}

	public void setStartmonth(final int startmonth) {
		this.startmonth = startmonth;
	}

	public void setStartyear(final int startyear) {
		this.startyear = startyear;
	}

	public void setTransporterid(final Integer[] transporterid) {
		this.transporterid = transporterid;
	}
}
