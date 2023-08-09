//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;

public class SuccessWithIDCreate implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer errorcode;
	private String errors;
	private String idcreate;

	public SuccessWithIDCreate(final String errors) {
		this.errorcode = -1;
		this.errors = errors;
		this.errorcode = -1;
	}

	public SuccessWithIDCreate(final String errors, final Integer errorcode, final String idcreate) {
		this.errorcode = -1;
		this.errors = errors;
		this.errorcode = errorcode;
		this.idcreate = idcreate;
	}

	public Integer getErrorcode() {
		return this.errorcode;
	}

	public String getErrors() {
		return this.errors;
	}

	public String getIdcreate() {
		return this.idcreate;
	}

	public void setErrorcode(final Integer errorcode) {
		this.errorcode = errorcode;
	}

	public void setErrors(final String errors) {
		this.errors = errors;
	}

	public void setIdcreate(final String idcreate) {
		this.idcreate = idcreate;
	}
}
