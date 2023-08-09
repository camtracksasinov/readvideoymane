//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;

public class SuccessMFA implements Serializable {
	private static final long serialVersionUID = 1L;
	private Boolean con1;
	private Integer errorcode;
	private String errors;
	private Boolean mfa;

	public SuccessMFA(String errors, Integer errorcode, Boolean mfa, Boolean con1) {
		super();
		this.errors = errors;
		this.errorcode = errorcode;
		this.mfa = mfa;
		this.con1 = con1;
	}

	public Boolean getCon1() {
		return con1;
	}

	public Integer getErrorcode() {
		return errorcode;
	}

	public String getErrors() {
		return errors;
	}

	public Boolean getMfa() {
		return mfa;
	}

	public void setCon1(Boolean con1) {
		this.con1 = con1;
	}

	public void setErrorcode(Integer errorcode) {
		this.errorcode = errorcode;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

	public void setMfa(Boolean mfa) {
		this.mfa = mfa;
	}

}
