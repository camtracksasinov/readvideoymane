//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Rankingresult implements Serializable {
	private static final long serialVersionUID = 1L;
	private String entities;
	private List<SummaryAllException> rankingtransporter;

	public Rankingresult() {
		this.entities = "";
		this.rankingtransporter = new ArrayList<>();
	}

	public String getEntities() {
		return this.entities;
	}

	public List<SummaryAllException> getRankingtransporter() {
		return this.rankingtransporter;
	}

	public void setEntities(final String entities) {
		this.entities = entities;
	}

	public void setRankingtransporter(final List<SummaryAllException> rankingtransporter) {
		this.rankingtransporter = rankingtransporter;
	}
}
