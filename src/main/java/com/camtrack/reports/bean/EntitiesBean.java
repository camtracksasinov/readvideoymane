//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.bean;

import java.io.Serializable;

public class EntitiesBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer entitiesid;
	private Double values;

	public Integer getEntitiesid() {
		return this.entitiesid;
	}

	public Double getValues() {
		return this.values;
	}

	public void setEntitiesid(final Integer entitiesid) {
		this.entitiesid = entitiesid;
	}

	public void setValues(final Double values) {
		this.values = values;
	}
}
