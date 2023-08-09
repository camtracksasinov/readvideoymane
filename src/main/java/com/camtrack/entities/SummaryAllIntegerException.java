//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;

public class SummaryAllIntegerException implements Serializable {
	private static final long serialVersionUID = 1L;
	public Integer customerid;
	public Long numbers;

	public SummaryAllIntegerException(final Integer customerid, final Long numbers) {
		this.customerid = customerid;
		this.numbers = numbers;
	}

	public Integer getCustomerid() {
		return this.customerid;
	}

	public Long getNumbers() {
		return this.numbers;
	}

	public Integer getNumbersInt() {
		try {
			return this.numbers.intValue();
		} catch (NullPointerException ex) {
			return 0;
		}
	}

	public void setCustomerid(final Integer customerid) {
		this.customerid = customerid;
	}

	public void setNumbers(final Long numbers) {
		this.numbers = numbers;
	}
}
