//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;

public class Success implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer errorcode;
	private String errors;

	public Success(final String errors) {
		this.errorcode = -1;
		this.errors = errors;
		this.errorcode = -1;
	}

	public Success(final String errors, final Integer errorcode) {
		this.errorcode = -1;
		this.errors = errors;
		this.errorcode = errorcode;
	}

	public Integer getErrorcode() {
		return this.errorcode;
	}

	public String getErrors() {
		return this.errors;
	}

	public void setErrorcode(final Integer errorcode) {
		this.errorcode = errorcode;
	}

	public void setErrors(final String errors) {
		this.errors = errors;
	}
}
