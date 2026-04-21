//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.bean;

import java.io.Serializable;
import java.util.List;

public class UpdateEmailConfigBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer elementsid;
	private List<SmallWhattsappEmail> emailwhatsap;
	private List<Levelbean> levelbeam;
	private Integer typelementsid;

	public Integer getElementsid() {
		return this.elementsid;
	}

	public List<SmallWhattsappEmail> getEmailwhatsap() {
		return emailwhatsap;
	}

	public void setEmailwhatsap(List<SmallWhattsappEmail> emailwhatsap) {
		this.emailwhatsap = emailwhatsap;
	}

	public List<Levelbean> getLevelbeam() {
		return this.levelbeam;
	}

	public Integer getTypelementsid() {
		return this.typelementsid;
	}

	public void setElementsid(final Integer elementsid) {
		this.elementsid = elementsid;
	}

	public void setLevelbeam(final List<Levelbean> levelbeam) {
		this.levelbeam = levelbeam;
	}

	public void setTypelementsid(final Integer typelementsid) {
		this.typelementsid = typelementsid;
	}
}
