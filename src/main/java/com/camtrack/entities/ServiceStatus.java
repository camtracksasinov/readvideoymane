//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;

public class ServiceStatus implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dt;
	private String nm;

	public ServiceStatus(final String nm, final String dt) {
		this.nm = nm;
		this.dt = dt;
	}

	public String getDt() {
		return this.dt;
	}

	public String getNm() {
		return this.nm;
	}

	public void setDt(final String dt) {
		this.dt = dt;
	}

	public void setNm(final String nm) {
		this.nm = nm;
	}
}
