//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.bean;

import java.io.Serializable;
import java.util.List;

public class TotalDistances implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<TrendTotalDistanceBean> totaldistance;
	private Integer typeinfos;

	public List<TrendTotalDistanceBean> getTotaldistance() {
		return this.totaldistance;
	}

	public Integer getTypeinfos() {
		return this.typeinfos;
	}

	public void setTotaldistance(final List<TrendTotalDistanceBean> totaldistance) {
		this.totaldistance = totaldistance;
	}

	public void setTypeinfos(final Integer typeinfos) {
		this.typeinfos = typeinfos;
	}
}
