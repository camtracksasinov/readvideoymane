//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;

public class Activesonmindistances implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer activesdrivers;
	private Integer activesvehs;
	private Integer alldrivers;
	private Integer allvehs;

	public Activesonmindistances(final Integer activesvehs, final Integer allvehs, final Integer activesdrivers,
			final Integer alldrivers) {
		this.activesvehs = activesvehs;
		this.allvehs = allvehs;
		this.activesdrivers = activesdrivers;
		this.alldrivers = alldrivers;
	}

	public Integer getActivesdrivers() {
		return this.activesdrivers;
	}

	public Integer getActivesvehs() {
		return this.activesvehs;
	}

	public Integer getAlldrivers() {
		return this.alldrivers;
	}

	public Integer getAllvehs() {
		return this.allvehs;
	}

	public void setActivesdrivers(final Integer activesdrivers) {
		this.activesdrivers = activesdrivers;
	}

	public void setActivesvehs(final Integer activesvehs) {
		this.activesvehs = activesvehs;
	}

	public void setAlldrivers(final Integer alldrivers) {
		this.alldrivers = alldrivers;
	}

	public void setAllvehs(final Integer allvehs) {
		this.allvehs = allvehs;
	}
}
