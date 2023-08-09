//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;

public class SummaryAllException implements Serializable {
	private static final long serialVersionUID = 1L;
	public String entities;
	public Integer ids;
	public Long numbers;

	public SummaryAllException() {
	}

	public SummaryAllException(final Integer ids, final String entities, final Long numbers) {
		this.entities = entities;
		this.numbers = numbers;
		this.ids = ids;
	}

	public String getEntities() {
		return this.entities;
	}

	public Integer getIds() {
		return this.ids;
	}

	public Long getNumbers() {
		return this.numbers;
	}

	public Integer NumbersInt() {
		try {
			return this.numbers.intValue();
		} catch (NullPointerException ex) {
			return 0;
		}
	}

	public void setEntities(final String entities) {
		this.entities = entities;
	}

	public void setIds(final Integer ids) {
		this.ids = ids;
	}

	public void setNumbers(final Long numbers) {
		this.numbers = numbers;
	}
}
