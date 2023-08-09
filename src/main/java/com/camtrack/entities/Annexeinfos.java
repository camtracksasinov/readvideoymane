//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;

public class Annexeinfos implements Serializable {
	private static final long serialVersionUID = 1L;
	private String branch;
	private String codefilialle;
	private String codepays;
	private String direction;
	private String filliale;
	private String internfillialename;
	private String pays;
	private String zone;

	public Annexeinfos(final String internfillialename, final String direction, final String zone,
			final String codepays, final String pays, final String codefilialle, final String filliale,
			final String branch) {
		this.internfillialename = internfillialename;
		this.direction = direction;
		this.zone = zone;
		this.codepays = codepays;
		this.pays = pays;
		this.codefilialle = codefilialle;
		this.filliale = filliale;
		this.branch = branch;
	}

	public String getBranch() {
		return this.branch;
	}

	public String getCodefilialle() {
		return this.codefilialle;
	}

	public String getCodepays() {
		return this.codepays;
	}

	public String getDirection() {
		return this.direction;
	}

	public String getFilliale() {
		return this.filliale;
	}

	public String getInternfillialename() {
		return this.internfillialename;
	}

	public String getPays() {
		return this.pays;
	}

	public String getZone() {
		return this.zone;
	}

	public void setBranch(final String branch) {
		this.branch = branch;
	}

	public void setCodefilialle(final String codefilialle) {
		this.codefilialle = codefilialle;
	}

	public void setCodepays(final String codepays) {
		this.codepays = codepays;
	}

	public void setDirection(final String direction) {
		this.direction = direction;
	}

	public void setFilliale(final String filliale) {
		this.filliale = filliale;
	}

	public void setInternfillialename(final String internfillialename) {
		this.internfillialename = internfillialename;
	}

	public void setPays(final String pays) {
		this.pays = pays;
	}

	public void setZone(final String zone) {
		this.zone = zone;
	}
}
