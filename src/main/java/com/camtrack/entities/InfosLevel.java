//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class InfosLevel implements Serializable {
	private static final long serialVersionUID = 1L;
	private BigDecimal distanceunderexception;
	private Long nbr;
	private BigDecimal totaldistance;
	private BigDecimal totalduration;

	public InfosLevel() {
	}

	public InfosLevel(final BigDecimal totalduration, final BigDecimal totaldistance,
			final BigDecimal distanceunderexception, final Long nbr) {
		this.totalduration = totalduration;
		this.totaldistance = totaldistance;
		this.distanceunderexception = distanceunderexception;
		this.nbr = nbr;
	}

	public BigDecimal getDistanceunderexception() {
		if (Objects.isNull(this.distanceunderexception)) {
			return BigDecimal.ZERO;
		}
		return this.distanceunderexception;
	}

	public Long getNbr() {
		if (Objects.isNull(this.nbr)) {
			return 0L;
		}
		return this.nbr;
	}

	public BigDecimal getTotaldistance() {
		if (Objects.isNull(this.totaldistance)) {
			return BigDecimal.ZERO;
		}
		return this.totaldistance;
	}

	public BigDecimal getTotalduration() {
		if (Objects.isNull(this.totalduration)) {
			return BigDecimal.ZERO;
		}
		return this.totalduration;
	}

	public void setDistanceunderexception(final BigDecimal distanceunderexception) {
		this.distanceunderexception = distanceunderexception;
	}

	public void setNbr(final Long nbr) {
		try {
			if (nbr.intValue() < 0) {
				this.nbr = 0L;
			}
		} catch (NullPointerException ex) {
			this.nbr = 0L;
		}

		this.nbr = nbr;
	}

	public void setTotaldistance(final BigDecimal totaldistance) {
		this.totaldistance = totaldistance;
	}

	public void setTotalduration(final BigDecimal totalduration) {
		this.totalduration = totalduration;
	}
}
