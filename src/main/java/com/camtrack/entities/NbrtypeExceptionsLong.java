//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;

public class NbrtypeExceptionsLong implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long bnr;
	private String entities;

	public NbrtypeExceptionsLong(final String entities, final Long bnr) {
		this.entities = entities;
		this.bnr = bnr;
	}

	public Long getBnr() {
		return this.bnr;
	}

	public String getEntities() {
		return this.entities;
	}

	public void setBnr(final Long bnr) {
		this.bnr = bnr;
	}

	public void setEntities(final String entities) {
		this.entities = entities;
	}
}
