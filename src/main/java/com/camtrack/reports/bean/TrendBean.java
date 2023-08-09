//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.camtrack.config.Utils;

public class TrendBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Map<String, Object>> activeh;
	private List<Map<String, Object>> data;
	private List<Map<String, Object>> invalidatedinfos;
	private String trendneouds;
	private Integer typedata;

	public List<Map<String, Object>> getActiveh() {
		return this.activeh;
	}

	public List<Map<String, Object>> getData() {
		return this.data;
	}

	public List<Map<String, Object>> getInvalidatedinfos() {
		return this.invalidatedinfos;
	}

	public String getTrendneouds() {
		return Utils.StringEscape(this.trendneouds);
	}

	public Integer getTypedata() {
		return this.typedata;
	}

	public void setActiveh(final List<Map<String, Object>> activeh) {
		this.activeh = activeh;
	}

	public void setData(final List<Map<String, Object>> data) {
		this.data = data;
	}

	public void setInvalidatedinfos(final List<Map<String, Object>> invalidatedinfos) {
		this.invalidatedinfos = invalidatedinfos;
	}

	public void setTrendneouds(final String trendneouds) {
		this.trendneouds = trendneouds;
	}

	public void setTypedata(final Integer typedata) {
		this.typedata = typedata;
	}
}
