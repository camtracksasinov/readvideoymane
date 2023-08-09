//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;

public class NbrtypeExceptions implements Serializable {
	private static final long serialVersionUID = 1L;
	private Double bnr;
	private String entities;

	public NbrtypeExceptions(final String entities, final Double bnr) {
		this.entities = entities;
		this.bnr = bnr;
	}

	public Double getBnr() {
		return this.bnr;
	}

	public String getEntities() {
		return this.entities;
	}

	public void setBnr(final Double bnr) {
		this.bnr = bnr;
	}

	public void setEntities(final String entities) {
		this.entities = entities;
	}
}
