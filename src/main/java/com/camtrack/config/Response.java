//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.config;

public class Response {
	private Object data;
	private int errorCode;
	private String errorDescription;
	private String errorMessage;

	public Response(final int errorCode, final String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public Response(final int errorCode, final String errorMessage, final Object data) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.data = data;
	}

	public Response(final int errorCode, final String errorMessage, final String errorDescription) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errorDescription = errorDescription;
	}

	public Object getData() {
		return this.data;
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public String getErrorDescription() {
		return this.errorDescription;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setData(final Object data) {
		this.data = data;
	}

	public void setErrorCode(final int errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorDescription(final String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
