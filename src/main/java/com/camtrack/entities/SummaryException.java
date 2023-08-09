//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;

public class SummaryException implements Serializable {
	public String exception;
	public Long numbers;
	public String transporter;

	public SummaryException(final String exception, final String transporter, final Long numbers) {
		this.exception = exception;
		this.transporter = transporter;
		this.numbers = numbers;
	}

	public String getException() {
		return this.exception;
	}

	public Long getNumbers() {
		return this.numbers;
	}

	public String getTransporter() {
		return this.transporter;
	}

	public void setException(final String exception) {
		this.exception = exception;
	}

	public void setNumbers(final Long numbers) {
		this.numbers = numbers;
	}

	public void setTransporter(final String transporter) {
		this.transporter = transporter;
	}
}
