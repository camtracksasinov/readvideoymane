//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.bean;

import java.io.Serializable;
import java.util.Objects;

public class SmallWhattsappEmail implements Serializable {
	private static final long serialVersionUID = 1L;
	private String email;
	private Boolean emailb;
	private String whatsapphone;
	private Boolean whatsapb;

	public String getEmail() {
		if (Objects.isNull(email)) {
			return "";
		}
		return email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public Boolean getEmailb() {
		if (Objects.isNull(emailb)) {
			return false;
		}
		return emailb;
	}

	public void setEmailb(Boolean emailb) {

		this.emailb = emailb;
	}

	public String getWhatsapphone() {
		if (Objects.isNull(whatsapphone)) {
			return "";
		}
		return whatsapphone;
	}

	public void setWhatsapphone(String whatsapphone) {
		this.whatsapphone = whatsapphone;
	}

	public Boolean getWhatsapb() {
		if (Objects.isNull(whatsapb)) {
			return false;
		}
		return whatsapb;
	}

	public void setWhatsapb(Boolean whatsapb) {
		this.whatsapb = whatsapb;
	}

}
