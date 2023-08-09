//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SafetyRanking implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Map<String, Double>> ranking;
	private Integer typeinfos;

	public SafetyRanking(final Integer typeinfos, final List<Map<String, Double>> ranking) {
		this.typeinfos = typeinfos;
		this.ranking = ranking;
	}

	public List<Map<String, Double>> getRanking() {
		return this.ranking;
	}

	public Integer getTypeinfos() {
		return this.typeinfos;
	}

	public void setRanking(final List<Map<String, Double>> ranking) {
		this.ranking = ranking;
	}

	public void setTypeinfos(final Integer typeinfos) {
		this.typeinfos = typeinfos;
	}
}
