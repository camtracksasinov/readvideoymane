//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.io.Serializable;

import com.camtrack.config.Utils;

public class EntitiesNames implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nm;

	public Integer getId() {
		return this.id;
	}

	public String getNm() {
		return Utils.StringEscape(this.nm);
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public void setNm(final String nm) {
		this.nm = nm;
	}
}
