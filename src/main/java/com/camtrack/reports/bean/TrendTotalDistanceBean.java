//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.camtrack.config.Utils;

public class TrendTotalDistanceBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Map<String, Object>> totaldistance;
	private String trendneouds;

	public List<Map<String, Object>> getTotaldistance() {
		return this.totaldistance;
	}

	public String getTrendneouds() {
		return Utils.StringEscape(this.trendneouds);
	}

	public void setTotaldistance(final List<Map<String, Object>> totaldistance) {
		this.totaldistance = totaldistance;
	}

	public void setTrendneouds(final String trendneouds) {
		this.trendneouds = trendneouds;
	}
}
